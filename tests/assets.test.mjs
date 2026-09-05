import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';
import { createHash } from 'node:crypto';
import { execFileSync } from 'node:child_process';

test('The single page embeds every original media file without altering its bytes', () => {
  const archive = readFileSync('reference/original.jar');
  assert.equal(createHash('sha256').update(archive).digest('hex'), 'acf97236f242768c561aa6e744ea586dda7c54098fce760e6a2d775465f36c56');
  const files = execFileSync('unzip', ['-Z1', 'reference/original.jar']).toString().trim().split('\n').filter(f => /\.(gif|jpg|au)$/i.test(f));
  const assets = JSON.parse(readFileSync('src/assets.json'));
  const html = readFileSync('index.html', 'utf8');
  assert.equal(files.length, 48); assert.equal(Object.keys(assets).length, 48);
  for (const file of files) {
    const data = execFileSync('unzip', ['-p', 'reference/original.jar', file]);
    const uri = assets[file.replace('AlienInvasion/', '')];
    assert.deepEqual(Buffer.from(uri.split(',')[1], 'base64'), data, file);
    assert(html.includes(uri), `${file} not embedded in HTML`);
  }
  assert(!/<script[^>]+src\s*=|<link[^>]+rel="stylesheet"/i.test(html));
});
