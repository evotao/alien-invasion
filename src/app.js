import assets from './assets.json';
import { Game, STEP_MS } from './engine.js';
import { Renderer, loadImages } from './renderer.js';
import { Sound } from './audio.js';
import { createIcons, Pause, Play, RotateCcw, Volume2, Volume1, VolumeX, Maximize, Minimize,
  ArrowLeft, ArrowRight, Shield, ArrowLeftRight, Zap, Rocket, Orbit, Crosshair } from 'lucide';

const icons = { Pause, Play, RotateCcw, Volume2, Volume1, VolumeX, Maximize, Minimize,
  ArrowLeft, ArrowRight, Shield, ArrowLeftRight, Zap, Rocket, Orbit, Crosshair };
const $ = id => document.getElementById(id);
const canvas = $('game'), sound = new Sound(assets), testMode = location.hash === '#test';
let game = new Game(Math.random, id => sound.play(id)), renderer, ready = false;
let introStart = 0, intro = true, lastTime = 0, accumulator = 0, best = 0, manual = false;
let touchTarget = null, touchPointer = null;
const held = new Map(), buttons = [...document.querySelectorAll('[data-control]')];
const pointerStarts = new Map(), taps = new Set();
let frameSerial = 0;
let diagnostic = false, fps = 25, fpsFrames = 0, fpsSince = performance.now();
const keyMap = { ArrowLeft: 0, KeyJ: 0, ArrowRight: 1, KeyL: 1, Space: 2,
  KeyP: 3, KeyA: 4, ArrowUp: 5, KeyI: 5, ArrowDown: 6, KeyK: 6, KeyD: 7, KeyW: 8 };
const stored = key => { try { return localStorage.getItem(key); } catch { return null; } };
const store = (key, value) => { try { localStorage.setItem(key, String(value)); } catch {} };
best = Number(stored('alien-invasion.best')) || 0;
sound.enabled = stored('alien-invasion.sound') !== 'false';
const storedVolume = Number(stored('alien-invasion.volume') ?? 50);
sound.volume = Math.min(100, Math.max(0, storedVolume)) / 100;
game.soundOn = sound.enabled; $('volume').value = String(sound.volume * 100);
$('title-art').src = assets['aititle.jpg'];
const favicon = document.createElement('link'); favicon.rel = 'icon'; favicon.href = assets['gfx/n_player_half.gif']; document.head.append(favicon);
createIcons({ icons });
$('fullscreen').hidden = !document.fullscreenEnabled;

function setIcon(id, name) {
  const button = $(id);
  if (button.dataset.currentIcon === name) return;
  button.dataset.currentIcon = name;
  const placeholder = document.createElement('i'); placeholder.dataset.lucide = name;
  button.replaceChildren(placeholder); createIcons({ icons });
}
function unlock() { sound.unlock().catch(() => { $('status').textContent = 'Audio unavailable'; }); }
function release() {
  held.clear(); pointerStarts.clear(); taps.clear(); game.releaseAll(); touchTarget = null; touchPointer = null;
  for (const b of buttons) b.classList.remove('held');
}
function sync() {
  if (game.score > best) { best = game.score; store('alien-invasion.best', best); }
  $('best').textContent = `Best ${best}`;
  $('pause').disabled = !ready || game.attract || intro;
  $('pause').setAttribute('aria-label', game.paused ? 'Resume' : 'Pause');
  $('pause').title = game.paused ? 'Resume (P)' : 'Pause (P)';
  setIcon('pause', game.paused ? 'play' : 'pause');
  setIcon('sound', sound.enabled ? 'volume-2' : 'volume-x');
  $('sound').setAttribute('aria-label', sound.enabled ? 'Mute sound' : 'Enable sound');
  $('sound').setAttribute('aria-pressed', String(!sound.enabled));
  for (const type of ['laser', 'seeker', 'bomb']) $(type + '-count').textContent = game.weapons[type];
  document.querySelector('[data-control="7"]').classList.toggle('armed', game.bomb.active && !game.bomb.bursting);
  const state = game.paused ? 'Paused' : game.attract ? (game.restartDelay ? 'Game over' : 'Ready') : `Level ${game.level}`;
  if ($('status').textContent !== state) $('status').textContent = state;
  const showStart = ready && !intro && (game.attract || game.paused);
  $('curtain').hidden = !showStart;
  $('begin').disabled = game.attract && game.restartDelay > 0;
  $('begin').querySelector('span').textContent = game.paused ? 'Resume' : game.restartDelay ? 'Game Over' : 'Start Game';
  canvas.setAttribute('aria-label', `Alien Invasion. ${state}. Score ${game.score}. ${game.lives + 1} ships.`);
}
function draw() {
  if (renderer) {
    renderer.debug = diagnostic; renderer.fps = fps;
    if (intro) renderer.intro(performance.now() - introStart); else renderer.render(game);
  }
  sync();
}
function startGame(fresh = false) {
  if (!ready) return;
  unlock(); release(); intro = false;
  if (fresh) { game = new Game(Math.random, id => sound.play(id)); game.soundOn = sound.enabled; }
  if (game.paused && !fresh) game.paused = false;
  else if (fresh || (game.attract && game.restartDelay === 0)) game.start();
  sound.setSuspended(false); lastTime = performance.now(); accumulator = 0;
  canvas.focus({ preventScroll: true }); draw();
}
function pause() {
  if (game.attract || intro || !ready) return;
  release(); game.paused = !game.paused; sound.setSuspended(game.paused);
  accumulator = 0; lastTime = performance.now(); draw();
}
function toggleSound() {
  unlock(); sound.setEnabled(!sound.enabled); game.soundOn = sound.enabled;
  store('alien-invasion.sound', sound.enabled); draw();
}
function hold(index, source) {
  if (!ready) return;
  if (intro || game.attract || game.paused) startGame();
  if (game.attract) return;
  if (!held.has(index)) held.set(index, new Set());
  held.get(index).add(source); game.keys[index] = true;
  buttons.find(b => +b.dataset.control === index)?.classList.add('held');
  unlock();
}
function unhold(index, source) {
  held.get(index)?.delete(source);
  if (!held.get(index)?.size) { game.keyUp(index); buttons.find(b => +b.dataset.control === index)?.classList.remove('held'); }
}
for (const button of buttons) {
  const index = Number(button.dataset.control);
  button.addEventListener('pointerdown', e => {
    if (e.button !== 0) return;
    e.preventDefault(); button.setPointerCapture(e.pointerId); hold(index, `pointer${e.pointerId}`);
    pointerStarts.set(e.pointerId, frameSerial);
  });
  for (const event of ['pointerup', 'pointercancel', 'lostpointercapture']) button.addEventListener(event, e => {
    if (event === 'pointerup' && pointerStarts.get(e.pointerId) === frameSerial) taps.add(index);
    pointerStarts.delete(e.pointerId); unhold(index, `pointer${e.pointerId}`);
  });
  // Keyboard/assistive activation gets one simulation step; pointer holds use capture.
  button.addEventListener('click', e => {
    if (e.detail !== 0) return;
    hold(index, 'activation'); setTimeout(() => unhold(index, 'activation'), STEP_MS + 10);
  });
}
$('begin').addEventListener('click', () => startGame());
$('pause').addEventListener('click', pause);
$('restart').addEventListener('click', () => startGame(true));
$('sound').addEventListener('click', toggleSound);
$('volume').addEventListener('input', e => { unlock(); sound.volume = Number(e.target.value) / 100; sound.updateVolume(); store('alien-invasion.volume', e.target.value); });
$('fullscreen').addEventListener('click', async () => {
  try {
    if (document.fullscreenElement) await document.exitFullscreen();
    else await document.documentElement.requestFullscreen();
  } catch { $('status').textContent = 'Full screen unavailable'; }
});
document.addEventListener('fullscreenchange', () => {
  setIcon('fullscreen', document.fullscreenElement ? 'minimize' : 'maximize');
  $('fullscreen').setAttribute('aria-label', document.fullscreenElement ? 'Exit full screen' : 'Full screen');
  $('fullscreen').title = document.fullscreenElement ? 'Exit full screen' : 'Full screen';
});
document.addEventListener('keydown', e => {
  if (e.ctrlKey && e.code === 'KeyD') { e.preventDefault(); if (!e.repeat) { diagnostic = !diagnostic; draw(); } return; }
  if (!ready || e.target instanceof HTMLInputElement || e.ctrlKey || e.metaKey || e.altKey || e.code === 'Tab') return;
  const index = keyMap[e.code];
  if (e.code === 'KeyS') { e.preventDefault(); if (!e.repeat) toggleSound(); return; }
  if (e.code === 'Escape' && document.fullscreenElement) return;
  if (e.code === 'Escape' && !game.attract && !intro) { e.preventDefault(); if (!e.repeat) pause(); return; }
  if (index === 3 && !game.attract && !intro) { e.preventDefault(); if (!e.repeat) pause(); return; }
  if ((intro || game.attract || game.paused) && e.target.tagName !== 'BUTTON') {
    e.preventDefault(); if (!e.repeat) startGame(); return;
  }
  if (index !== undefined) { e.preventDefault(); hold(index, e.code); }
});
document.addEventListener('keyup', e => { const index = keyMap[e.code]; if (index !== undefined) unhold(index, e.code); });
canvas.addEventListener('pointerdown', e => {
  if (!ready) return;
  if (intro || game.attract || game.paused) { startGame(); return; }
  canvas.focus({ preventScroll: true }); unlock();
  if (e.pointerType !== 'mouse') {
    touchPointer = e.pointerId; canvas.setPointerCapture(e.pointerId);
    const r = canvas.getBoundingClientRect(); touchTarget = (e.clientX - r.left) * 400 / r.width - 20;
  }
});
canvas.addEventListener('pointermove', e => {
  if (e.pointerId === touchPointer) { const r = canvas.getBoundingClientRect(); touchTarget = (e.clientX - r.left) * 400 / r.width - 20; }
});
for (const event of ['pointerup', 'pointercancel', 'lostpointercapture']) canvas.addEventListener(event, e => {
  if (e.pointerId === touchPointer) { touchTarget = null; touchPointer = null; unhold(0, 'drag'); unhold(1, 'drag'); }
});
function suspend() {
  release(); if (ready && !game.attract && !intro) game.paused = true;
  sound.setSuspended(true); accumulator = 0; if (ready) draw();
}
window.addEventListener('blur', suspend);
document.addEventListener('visibilitychange', () => { if (document.hidden) suspend(); });
window.addEventListener('pagehide', () => { sound.setSuspended(true); });

function advance() {
  for (const index of taps) game.keys[index] = true;
  game.frame(); ++frameSerial;
  ++fpsFrames;
  if (performance.now() - fpsSince >= 1000) { fps = fpsFrames; fpsFrames = 0; fpsSince = performance.now(); }
  for (const index of taps) if (!held.get(index)?.size) game.keyUp(index);
  taps.clear();
}

function animate(time) {
  requestAnimationFrame(animate);
  if (!ready || manual || document.hidden) return;
  if (intro) {
    if (time - introStart >= 2580) { intro = false; lastTime = time; }
    draw(); return;
  }
  accumulator += Math.min(time - lastTime, 200); lastTime = time;
  // Browser pause freezes both paint counters and simulation, including audio.
  if (game.paused) { accumulator = 0; return; }
  let changed = false;
  while (accumulator >= STEP_MS) {
    if (touchTarget !== null) {
      const difference = touchTarget - game.x;
      if (difference < -5) hold(0, 'drag'); else unhold(0, 'drag');
      if (difference > 5) hold(1, 'drag'); else unhold(1, 'drag');
    }
    advance(); accumulator -= STEP_MS; changed = true;
  }
  if (changed) draw();
}
loadImages(assets).then(images => {
  renderer = new Renderer(canvas, images); ready = true; $('load-state').hidden = true;
  introStart = performance.now(); lastTime = introStart;
  if (testMode) {
    intro = false;
    window.alienTest = {
      get game() { return game; }, get sound() { return sound; }, get images() { return images; },
      frame(n = 1) { for (let i = 0; i < n; i++) advance(); draw(); },
      manual(value = true) { manual = value; accumulator = 0; lastTime = performance.now(); },
      draw, start: startGame, intro(time) { renderer.intro(time); },
    };
  }
  draw(); requestAnimationFrame(animate);
}).catch(error => { $('load-state').textContent = 'Unable to load game assets.'; console.error(error); });
