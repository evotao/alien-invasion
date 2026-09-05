import { chromium } from 'playwright';
import { pathToFileURL } from 'node:url';
import { resolve } from 'node:path';
import assert from 'node:assert/strict';
import { writeFile } from 'node:fs/promises';

const browser = await chromium.launch({ channel: 'chrome', headless: true });
const results = [];
try {
  const page = await browser.newPage();
  await page.goto(pathToFileURL(resolve('index.html')).href + '#test');
  await page.waitForFunction(() => !!window.alienTest);
  await page.evaluate(() => { alienTest.manual(); alienTest.start(); });
  for (const [width, height] of [[280,568],[320,568],[390,844],[600,768],[620,700],[639,768],[640,640],[641,700],[700,550],[768,600],[844,390],[1024,768],[1200,900],[1440,1000],[1920,1080]]) {
    await page.setViewportSize({ width, height });
    const layout = await page.evaluate(() => {
      const visible = [...document.querySelectorAll('header, h1, .brand, nav, canvas, .console, button, footer')].filter(e => e.getClientRects().length);
      const clipped = visible.filter(e => {
        const r=e.getBoundingClientRect(); return r.x < -1 || r.right > innerWidth+1 || r.bottom > innerHeight+1;
      }).map(e => e.id || e.className || e.tagName);
      return { width: innerWidth, height: innerHeight, scrollWidth: document.documentElement.scrollWidth, scrollHeight: document.documentElement.scrollHeight, clipped };
    });
    results.push(layout);
    assert.equal(layout.scrollWidth, width, JSON.stringify(layout));
    assert(layout.scrollHeight <= height + 1, JSON.stringify(layout));
    assert.deepEqual(layout.clipped, [], JSON.stringify(layout));
  }
  await writeFile('artifacts/layout-report.json', JSON.stringify(results, null, 2));
  console.log(`No clipping or overflow at ${results.length} viewport/breakpoint combinations.`);
} finally { await browser.close(); }
