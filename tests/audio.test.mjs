import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';
import { execFileSync } from 'node:child_process';
import { decodeAu, SOUND_FILES } from '../src/audio.js';

test('All eight original AU files decode sample-for-sample like FFmpeg', () => {
  for (const file of Object.values(SOUND_FILES)) {
    const path = `reference/AlienInvasion/sfx/${file}`;
    const decoded = decodeAu(readFileSync(path));
    const pcm = execFileSync('ffmpeg', ['-v', 'error', '-i', path, '-f', 's16le', '-acodec', 'pcm_s16le', '-']);
    assert.equal(decoded.samples.length, pcm.length / 2, file);
    for (let i = 0; i < decoded.samples.length; i++) assert.ok(decoded.samples[i] * 32768 === pcm.readInt16LE(i * 2), `${file}: sample ${i}`);
  }
});
