import test from 'node:test';
import assert from 'node:assert/strict';
import { spawn, execFileSync } from 'node:child_process';
import { readFileSync, mkdirSync } from 'node:fs';
import { createInterface } from 'node:readline';
import { Game } from '../src/engine.js';

const schema = JSON.parse(readFileSync(new URL('state-map.json', import.meta.url)));
const keys = [106, 108, 32, 112, 97, 105, 107, 100, 119];
const methods = { start: 'V', resetShip: 'b', newWave: '00c0', die: 'q', kill: '00c5',
  fire: '00c2', fireLaser: 'c', fireBomb: '00d3', fireSeeker: 'f', teleportShip: '00e1',
  updateTeleport: 'r', collect: '00c7', progressLevel: 'l', controls: '00d5',
  updateAliens: '00c4', updateEnemyShots: '00cd', updateShots: '00e2', updateLaser: 'd',
  updateBomb: 'g', updateSeeker: '00de', updatePickup: 'U', updateDeath: 'N', tick: '00d1' };
function javaRandom(seed) {
  let state = (BigInt(seed) ^ 0x5deece66dn) & ((1n << 48n) - 1n);
  function next(bits) { state = (state * 0x5deece66dn + 11n) & ((1n << 48n) - 1n); return Number(state >> BigInt(48 - bits)); }
  return () => (next(26) * 134217728 + next(27)) / 9007199254740992;
}
function patch(target, values) {
  for (const [k, v] of Object.entries(values)) {
    if (v && typeof v === 'object') patch(target[k], v); else target[k] = v;
  }
}
function snapshot(g) { return Object.fromEntries(Object.keys(schema).map(k => [k, g[k]])); }
mkdirSync('reference/classes', { recursive: true });
execFileSync('javac', ['-nowarn', '-cp', 'reference/AlienInvasion:tools/gson.jar', '-d', 'reference/classes', 'reference/m.java', 'tests/Oracle.java']);
execFileSync('javac', ['--add-exports', 'java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED', '-d', 'reference/classes', 'tests/StripDebug.java']);
execFileSync('java', ['--add-exports', 'java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED', '-cp', 'reference/classes', 'StripDebug']);

async function oracleRun(commands) {
  const child = spawn('java', ['--add-opens', 'java.base/java.lang=ALL-UNNAMED', '-Djava.awt.headless=false', '-cp', 'reference/classes:reference/classes/clean:tools/gson.jar', 'Oracle']);
  let error = '';
  child.stdin.on('error', () => {});
  child.stderr.on('data', b => { error += b; });
  const results = [];
  const lines = createInterface({ input: child.stdout });
  lines.on('line', line => results.push(JSON.parse(line)));
  const done = new Promise((resolve, reject) => child.on('close', code => code ? reject(new Error(error)) : resolve(results)));
  child.stdin.end(commands.map(c => JSON.stringify(c)).join('\n') + '\n');
  return done;
}

test('Java bytecode and JavaScript produce identical state and sounds', { timeout: 120000 }, async () => {
  const commands = [];
  const add = (command) => commands.push(command);
  const init = seed => { add({ seed }); add({ action: 'start' }); };
  init(42);
  for (let i = 0; i < 500; i++) {
    if (i % 37 === 0) add({ downIndex: i % 9 });
    if (i % 43 === 0) add({ upIndex: i % 9 });
    add({ frames: 1 });
  }
  // Every weapon, upgrade, pickup outcome, shield, teleport and respawn path.
  for (let seed = 0; seed < 45; seed++) {
    init(seed);
    for (let i = 0; i < 15; i++) add({ action: 'collect' });
    add({ patch: { invincible: 0, weapons: { triple: true, spread: true } } });
    for (let i = 0; i < 9; i++) {
      if (i === 3) continue;
      add({ downIndex: i }); add({ frames: 4 }); add({ upIndex: i });
    }
    add({ downIndex: 7 }); add({ frames: 20 }); add({ upIndex: 7 });
    for (let i = 0; i < 15; i++) add({ action: 'collect' });
    add({ action: 'die' });
    for (let i = 0; i < 55; i++) add({ frames: 1 });
  }
  init(99);
  for (let level = 0; level < 30; level++) {
    add({ patch: { invincible: 10000 } });
    for (let i = 0; i < 5; i++) add({ action: 'kill', args: [i, 0] });
    for (let i = 0; i < 26; i++) add({ frames: 1 });
  }
  for (let life = 0; life < 4; life++) { add({ action: 'die' }); add({ frames: 50 }); }
  add({ frames: 401 }); add({ downIndex: 2 });
  // A blast that outlives the last alien's death must delay the next wave.
  init(712);
  add({ patch: { invincible: 10000, entering: false,
    aliens: Array.from({ length: 5 }, () => ({ x: 180, y: 100, vx: 0, vy: 0 })),
    bomb: { active: true, bursting: true, x: 200, y: 120, diameter: 120, max: 400 } } });
  for (let i = 0; i < 70; i++) add({ frames: 1 });
  // Long uninterrupted play traces include input release, depleted weapons,
  // collisions, deaths, pickup misses, game over and subsequent starts.
  for (const seed of [5, 1002, 8761]) {
    init(seed);
    for (let i = 0; i < 1500; i++) {
      if (i % 19 === 0) add({ downIndex: [0, 1, 2, 4, 5, 6, 7, 8][Math.trunc(i / 19) % 8] });
      if (i % 23 === 0) add({ upIndex: [0, 1, 2, 4, 5, 6, 7, 8][Math.trunc(i / 23) % 8] });
      add({ frames: 1 });
    }
  }
  const wire = commands.map(c => ({ ...c, ...(c.action ? { method: methods[c.action] } : {}),
    ...(c.downIndex !== undefined ? { down: keys[c.downIndex] } : {}), ...(c.upIndex !== undefined ? { up: keys[c.upIndex] } : {}) }));
  const expected = await oracleRun(wire);
  assert.equal(expected.length, commands.length);
  let game, sounds;
  const pickups = new Set(); let sawWaveWait = false, sawGameOver = false;
  for (let i = 0; i < commands.length; i++) {
    const c = commands[i]; sounds = [];
    if (c.seed !== undefined) game = new Game(javaRandom(c.seed), id => sounds.push(id));
    if (c.patch) patch(game, c.patch);
    if (c.action) game[c.action](...(c.args ?? []));
    if (c.downIndex !== undefined) game.keyDown(c.downIndex);
    if (c.upIndex !== undefined) game.keyUp(c.upIndex);
    for (let f = 0; f < (c.frames ?? 0); f++) game.frame();
    if (c.action === 'collect') pickups.add(game.message.text.replace(/ : \d+$/, ''));
    sawWaveWait ||= game.waitingWave; sawGameOver ||= game.lives < 0 && game.attract;
    assert.deepEqual(JSON.parse(JSON.stringify({ state: snapshot(game), sounds })), expected[i], `Command ${i}: ${JSON.stringify(c)}`);
  }
  assert.equal(pickups.size, 9, 'Every pickup outcome was exercised');
  assert(sawWaveWait, 'Delayed wave while cluster bomb expands was exercised');
  assert(sawGameOver, 'Game-over transition was exercised');
  console.log(`Compared ${commands.length} snapshots of original bytecode and JS, including all ${Object.keys(schema).length} state groups.`);
});
