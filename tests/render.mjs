import { chromium } from 'playwright';
import { execFileSync } from 'node:child_process';
import { readFileSync, writeFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { pathToFileURL } from 'node:url';
import assert from 'node:assert/strict';
import { PNG } from 'pngjs';

const commands = [{ seed: 42 }, { method: 'V' }, { frames: 35 }], fixtures = [];
for (let frame = 0; frame < 4; frame++) {
  const file = `artifacts/java-render-${frame}.png`;
  commands.push({ patch: {
    x: 160, invincible: 0, dying: false, message: { active: false },
    aliens: Array.from({ length: 5 }, (_, i) => ({ x: 15 + i * 75, y: 80 + (i % 2) * 60,
      type: i % 3, frame, dead: false, exploding: i === 4, explosion: frame * 5,
      firing: true, shotX: 40 + i * 75, shotY: 220 + i * 10 })),
    teleport: { active: frame > 0, frame },
    seeker: { active: true, x: 115, y: 195, target: 1 },
    pickup: { active: true, x: 300, y: 260 },
    bomb: { active: true, bursting: frame % 2 === 0, x: 50, y: 160, diameter: 75 },
    laser: { active: true, attached: false, x: 185, y: 100, length: 140 },
    weapons: { triple: true, spread: true },
    shots: Array.from({ length: 5 }, (_, i) => ({ active: true, x: 140 + i * 20, y: 250 + i * 5 }))
  }, screenshot: file });
  fixtures.push({ command: commands.length - 1, file });
}
commands.push({ patch: { dying: true, deathFrame: 9, teleport: { active: false } }, screenshot: 'artifacts/java-death.png' });
fixtures.push({ command: commands.length - 1, file: 'artifacts/java-death.png' });
const output = execFileSync('java', ['--add-opens', 'java.base/java.lang=ALL-UNNAMED', '-Djava.awt.headless=false', '-cp',
  'reference/classes:reference/classes/clean:tools/gson.jar', 'Oracle'], { input: commands.map(c => JSON.stringify(c)).join('\n') + '\n', maxBuffer: 5e6 });
const snapshots = output.toString().trim().split('\n').map(line => JSON.parse(line));
const browser = await chromium.launch({ channel: 'chrome', headless: true });
const report = [];
try {
  const page = await browser.newPage();
  await page.goto(pathToFileURL(resolve('index.html')).href + '#test');
  await page.waitForFunction(() => !!window.alienTest);
  await page.evaluate(() => alienTest.manual());
  for (const fixture of fixtures) {
    const uri = await page.evaluate(state => {
      Object.assign(alienTest.game, state); alienTest.draw(); return document.getElementById('game').toDataURL();
    }, snapshots[fixture.command].state);
    const jsFile = fixture.file.replace('java-', 'browser-');
    writeFileSync(jsFile, Buffer.from(uri.split(',')[1], 'base64'));
    const reference = PNG.sync.read(readFileSync(fixture.file)), port = PNG.sync.read(readFileSync(jsFile));
    let different = 0, inspected = 0;
    // Java and browsers use different font/oval rasterizers. Compare the entire
    // playfield away from text, including original sprite pixels and positions.
    for (let y = 20; y < 329; y++) for (let x = 0; x < 400; x++) {
      const i = (y * 400 + x) * 4; inspected++;
      if ([0,1,2].some(k => Math.abs(reference.data[i+k] - port.data[i+k]) > 16)) different++;
    }
    const ratio = different / inspected;
    report.push({ reference: fixture.file, browser: jsFile, different, inspected, ratio });
    assert(ratio < 0.01, `${jsFile}: ${(ratio * 100).toFixed(2)}% mismatched pixels`);
  }
  writeFileSync('artifacts/render-report.json', JSON.stringify(report, null, 2));
  console.log(report.map(r => `${r.browser}: ${(100 * (1 - r.ratio)).toFixed(3)}% matching playfield pixels`).join('\n'));
} finally { await browser.close(); }
