import { chromium, webkit } from 'playwright';
import assert from 'node:assert/strict';
import { resolve } from 'node:path';
import { mkdir, writeFile } from 'node:fs/promises';
import { pathToFileURL } from 'node:url';

await mkdir('artifacts', { recursive: true });
const engine = process.env.BROWSER || 'chromium';
const browser = engine === 'webkit' ? await webkit.launch({ headless: true }) : await chromium.launch({ channel: 'chrome', headless: true });
const report = [];
try {
  for (const viewport of [{ width: 1440, height: 1000 }, { width: 1366, height: 768 }, { width: 390, height: 844 }, { width: 320, height: 568 }, { width: 844, height: 390 }]) {
    const context = await browser.newContext({ viewport, hasTouch: viewport.width < 900 });
    const page = await context.newPage();
    if (engine === 'chromium') await context.setOffline(true);
    await context.route(/^https?:\/\//, route => route.abort());
    const errors = [], requests = [];
    page.on('pageerror', e => errors.push(e.message));
    page.on('request', r => { if (/^https?:/.test(r.url())) requests.push(r.url()); });
    await page.goto(pathToFileURL(resolve('index.html')).href + '#test');
    await page.waitForFunction(() => !!window.alienTest);
    await page.evaluate(() => alienTest.manual());
    await page.screenshot({ path: `artifacts/title-${viewport.width}x${viewport.height}.png` });
    await page.getByRole('button', { name: 'Start Game', exact: true }).click();
    const initial = await page.evaluate(() => ({ x: alienTest.game.x, lives: alienTest.game.lives, assets: Object.keys(alienTest.images).length }));
    assert.deepEqual(initial, { x: 160, lives: 3, assets: 40 });
    await page.keyboard.down('ArrowRight');
    await page.evaluate(() => alienTest.frame(5));
    await page.keyboard.up('ArrowRight');
    assert.equal(await page.evaluate(() => alienTest.game.x), 210);
    await page.keyboard.down('Space');
    await page.evaluate(() => alienTest.frame());
    await page.keyboard.up('Space');
    assert.equal(await page.evaluate(() => alienTest.game.shots[0].active), true);
    await page.keyboard.down('a'); await page.evaluate(() => alienTest.frame()); await page.keyboard.up('a');
    await page.keyboard.down('w'); await page.evaluate(() => alienTest.frame()); await page.keyboard.up('w');
    await page.keyboard.down('d'); await page.evaluate(() => alienTest.frame()); await page.keyboard.up('d');
    await page.evaluate(() => alienTest.frame(8));
    await page.keyboard.down('d'); await page.evaluate(() => alienTest.frame()); await page.keyboard.up('d');
    assert.equal(await page.evaluate(() => alienTest.game.bomb.bursting), true);
    assert.deepEqual(await page.evaluate(() => ['laser', 'seeker', 'bomb'].map(k => alienTest.game.weapons[k])), [4, 4, 4]);
    await page.keyboard.down('ArrowUp'); await page.evaluate(() => alienTest.frame(3)); await page.keyboard.up('ArrowUp');
    assert.equal(await page.evaluate(() => alienTest.game.weapons.shield), 985);
    await page.keyboard.down('ArrowDown'); await page.evaluate(() => alienTest.frame(9)); await page.keyboard.up('ArrowDown');
    assert.equal(await page.evaluate(() => alienTest.game.teleport.active), false);
    assert.equal(await page.evaluate(() => alienTest.game.x), 1);
    // A real pointer hold must survive moving off the control, then release.
    const left = await page.getByRole('button', { name: 'Move right', exact: true }).boundingBox();
    await page.mouse.move(left.x + left.width / 2, left.y + left.height / 2); await page.mouse.down();
    await page.evaluate(() => alienTest.frame(3));
    await page.mouse.move(0, 0); await page.mouse.up();
    assert.equal(await page.evaluate(() => alienTest.game.keys[1]), false);
    if (viewport.width < 900) {
      const beforeTap = await page.evaluate(() => alienTest.game.x);
      await page.getByRole('button', { name: 'Move right', exact: true }).tap();
      await page.evaluate(() => alienTest.frame());
      assert.equal(await page.evaluate(() => alienTest.game.x), beforeTap + 10, 'Fast touch taps must register');
    }
    await page.getByRole('button', { name: 'Pause', exact: true }).click();
    assert.equal(await page.evaluate(() => alienTest.game.paused), true);
    await page.screenshot({ path: `artifacts/paused-${viewport.width}x${viewport.height}.png` });
    await page.getByRole('button', { name: 'Resume', exact: true }).first().click();
    assert.equal(await page.evaluate(() => alienTest.game.paused), false);
    await page.getByRole('button', { name: 'Mute sound', exact: true }).click();
    assert.equal(await page.evaluate(() => alienTest.game.soundOn), false);
    await page.getByRole('button', { name: 'Enable sound', exact: true }).click();
    const audio = await page.evaluate(() => ({ state: alienTest.sound.context.state, buffers: alienTest.sound.buffers.size,
      rates: [...alienTest.sound.buffers.values()].map(b => b.sampleRate), playing: alienTest.sound.playing.size }));
    assert.equal(audio.state, 'running'); assert.equal(audio.buffers, 8); assert(audio.playing > 0);
    // Compose a real late-game state for inspecting every reused sprite family.
    await page.evaluate(() => {
      const g = alienTest.game;
      g.x = 172; g.invincible = 0; g.teleport.active = false; g.message.active = false;
      g.level = 12; g.score = 4380; g.weapons.triple = true; g.weapons.spread = true;
      g.aliens.forEach((a, i) => Object.assign(a, { x: [30,110,190,270,340][i], y: [65,110,55,155,95][i], frame: i % 4, type: i % 3, dead: false, exploding: i === 3, explosion: 6, firing: true, shotX: [60,130,220,290,355][i], shotY: [200,240,220,285,260][i] }));
      Object.assign(g.pickup, { active: true, x: 120, y: 240 });
      Object.assign(g.seeker, { active: true, x: 215, y: 160, target: 2 });
      Object.assign(g.bomb, { active: true, bursting: true, x: 100, y: 165, diameter: 60 });
      g.laser.active = false; g.fire(); g.shots.forEach(s => s.y -= 50); alienTest.draw();
    });
    const layout = await page.evaluate(() => {
      const rect = document.getElementById('game').getBoundingClientRect();
      const controls = [...document.querySelectorAll('button')].filter(e => e.getClientRects().length).map(e => {
        const r = e.getBoundingClientRect(); return { name: e.getAttribute('aria-label') || e.textContent, x: r.x, y: r.y, w: r.width, h: r.height };
      });
      const pixels = document.getElementById('game').getContext('2d').getImageData(0,0,400,350).data;
      let lit = 0; for (let i=0;i<pixels.length;i+=4) if (pixels[i]+pixels[i+1]+pixels[i+2] > 40) lit++;
      return { width: innerWidth, height: innerHeight, scrollWidth: document.documentElement.scrollWidth,
        scrollHeight: document.documentElement.scrollHeight, canvas: { x:rect.x,y:rect.y,width:rect.width,height:rect.height }, controls, lit };
    });
    assert.equal(layout.scrollWidth, viewport.width, 'Horizontal overflow');
    assert(layout.scrollHeight <= viewport.height + 1, 'Game should fit in the viewport');
    assert(layout.lit > 3000, `Blank canvas: ${layout.lit} pixels`);
    for (let i = 0; i < layout.controls.length; i++) for (let j = i + 1; j < layout.controls.length; j++) {
      const a = layout.controls[i], b = layout.controls[j];
      assert(!(a.x < b.x + b.w && a.x + a.w > b.x && a.y < b.y + b.h && a.y + a.h > b.y), `Controls overlap: ${a.name}, ${b.name}`);
    }
    await page.screenshot({ path: `artifacts/game-${viewport.width}x${viewport.height}.png`, fullPage: true });
    // Normal RAF timing, pause stability, and focus-loss cleanup.
    await page.evaluate(() => { alienTest.game.invincible = 100; alienTest.manual(false); });
    await page.waitForFunction(() => alienTest.game.invincible < 97);
    await page.getByRole('button', { name: 'Pause', exact: true }).click();
    const pausedState = await page.evaluate(() => JSON.stringify(alienTest.game, (key,value) => typeof value === 'function' ? undefined : value));
    await page.waitForTimeout(160);
    assert.equal(await page.evaluate(() => JSON.stringify(alienTest.game, (key,value) => typeof value === 'function' ? undefined : value)), pausedState);
    await page.keyboard.press('p');
    await page.keyboard.down('Space');
    await page.evaluate(() => window.dispatchEvent(new Event('blur')));
    assert.equal(await page.evaluate(() => alienTest.game.paused && alienTest.game.keys.every(k => !k)), true);
    await page.keyboard.up('Space');
    await page.evaluate(() => {
      alienTest.manual(); const g = alienTest.game; g.paused = false; g.lives = 0; g.die(); alienTest.frame(50);
    });
    assert.equal(await page.evaluate(() => alienTest.game.attract), true);
    assert.equal(await page.getByRole('button', { name: 'Game Over', exact: true }).isDisabled(), true);
    await page.evaluate(() => alienTest.frame(100));
    await page.getByRole('button', { name: 'Start Game', exact: true }).click();
    assert.deepEqual(await page.evaluate(() => ({ score: alienTest.game.score, lives: alienTest.game.lives, level: alienTest.game.level })), { score: 0, lives: 3, level: 0 });
    if (viewport.width === 1440 && await page.evaluate(() => document.fullscreenEnabled)) {
      await page.getByRole('button', { name: 'Full screen', exact: true }).click();
      await page.waitForFunction(() => !!document.fullscreenElement);
      await page.getByRole('button', { name: 'Exit full screen', exact: true }).click();
      await page.waitForFunction(() => !document.fullscreenElement);
    }
    assert.deepEqual(errors, []); assert.deepEqual(requests, [], 'Single HTML should make no network requests');
    report.push({ viewport, audio, layout, errors, requests });
    await context.close();
  }
  // Exercise the actual shipped startup, including the publisher animation.
  const context = await browser.newContext({ viewport: { width: 1200, height: 900 } });
  if (engine === 'chromium') await context.setOffline(true);
  await context.route(/^https?:\/\//, route => route.abort());
  const page = await context.newPage();
  const errors = []; page.on('pageerror', e => errors.push(e.message));
  await page.goto(pathToFileURL(resolve('index.html')).href);
  await page.waitForSelector('#load-state', { state: 'hidden' });
  await page.waitForTimeout(1000);
  await page.screenshot({ path: `artifacts/intro-${engine}.png` });
  assert.equal(await page.evaluate(() => window.alienTest), undefined);
  await page.getByRole('button', { name: 'Start Game', exact: true }).click();
  await page.keyboard.down('Space');
  await page.waitForTimeout(500);
  await page.keyboard.up('Space');
  await page.screenshot({ path: `artifacts/live-${engine}.png` });
  assert.equal(await page.locator('#status').innerText(), 'Level 0');
  assert.deepEqual(errors, []);
  await context.close();
  await writeFile(`artifacts/${engine}-report.json`, JSON.stringify(report, null, 2));
  console.log(`${engine} checks passed at ${report.length} desktop/mobile viewports. No page errors or network requests.`);
} finally { await browser.close(); }
