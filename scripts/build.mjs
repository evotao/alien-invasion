import { readFile, writeFile, mkdir, readdir } from 'node:fs/promises';
import { execFileSync } from 'node:child_process';
import { resolve } from 'node:path';
import { createHash } from 'node:crypto';
import { build } from 'esbuild';

const root = resolve(import.meta.dirname, '..');
const original = resolve(root, 'reference/AlienInvasion');
await mkdir(original, { recursive: true });
execFileSync('unzip', ['-oq', resolve(root, 'reference/original.jar'), '-d', resolve(root, 'reference')]);
const assets = {};
const manifest = [];
for (const dir of ['gfx', 'exp', 'sfx', '']) {
  for (const file of (await readdir(resolve(original, dir))).sort()) {
    if (!/\.(gif|jpg|au)$/i.test(file)) continue;
    const key = dir ? `${dir}/${file}` : file;
    const data = await readFile(resolve(original, key));
    const type = /\.gif$/i.test(file) ? 'image/gif' : /\.jpg$/i.test(file) ? 'image/jpeg' : 'audio/basic';
    assets[key] = `data:${type};base64,${data.toString('base64')}`;
    manifest.push({ path: key, bytes: data.length, sha256: createHash('sha256').update(data).digest('hex') });
  }
}
await writeFile(resolve(root, 'src/assets.json'), JSON.stringify(assets));
await writeFile(resolve(root, 'reference/asset-manifest.json'), JSON.stringify(manifest, null, 2) + '\n');
const result = await build({ entryPoints: [resolve(root, 'src/app.js')], bundle: true, write: false, format: 'iife', target: 'es2020', minify: true });
const html = (await readFile(resolve(root, 'src/page.html'), 'utf8'))
  .replace('/* STYLES */', await readFile(resolve(root, 'src/style.css'), 'utf8'))
  .replace('/* SCRIPT */', result.outputFiles[0].text.replaceAll('</script', '<\\/script'));
await writeFile(resolve(root, 'index.html'), html);
console.log(`Built index.html: ${Buffer.byteLength(html)} bytes; ${manifest.length} original assets embedded.`);
