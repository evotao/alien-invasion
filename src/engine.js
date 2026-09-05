// Rules ported from invader.class. Coordinates and ordering follow the 1999 applet.
export const STEP_MS = 40;
const record = (defaults) => ({ ...defaults });
const particle = () => ({ x: 0, y: 0, vx: 0, vy: 0 });
const bullet = () => ({ active: false, x: 0, y: 0 });
const alien = () => ({ x: 0, y: 0, vx: 0, vy: 0, dx: 0, dy: 0, frame: 0, anim: 0,
  type: 0, dead: false, exploding: false, explosion: 0, explosionTick: 0,
  firing: false, lostTrack: false, shotX: 0, shotY: 0, shotSpeed: 5 });

export class Game {
  constructor(random = Math.random, sound = () => {}) {
    this.random = random;
    this.sound = sound;
    this.playerDebris = Array.from({ length: 20 }, particle);
    this.alienDebris = Array.from({ length: 20 }, particle);
    this.aliens = Array.from({ length: 5 }, alien);
    this.stars = Array.from({ length: 20 }, () => ({ x: 0, y: 0, speed: 0 }));
    this.bomb = { active: false, bursting: false, x: 0, y: 0, diameter: 0, flash: 0, max: 100, released: false };
    this.seeker = { active: false, x: 0, y: 0, speed: 0, target: -1, unused: false };
    this.pickup = { active: false, x: 0, y: 0 };
    this.laser = { x: 0, y: 0, attached: false, active: false, length: 0 };
    this.teleport = { active: false, outgoing: true, tick: 0, frame: 0, ready: true };
    this.shots = Array.from({ length: 5 }, bullet);
    this.weapons = { triple: false, spread: false, laser: 5, bomb: 5, seeker: 5, shield: 1000, unused: -1 };
    this.message = { active: false, tick: 0, duration: 200, text: 'FartKnocker', flash: 0, x: 0, y: 0 };
    this.keys = Array(9).fill(false);
    this.x = 0; this.shieldFlash = 0; this.invincible = 0; this.invincibleFrame = 0;
    this.dying = false; this.deathFrame = 0; this.deathTick = 0;
    this.score = 0; this.paused = false; this.attract = true; this.soundOn = true;
    this.waitingWave = false; this.entering = false; this.entryRemaining = 0;
    this.level = 0; this.attractTick = 0; this.lives = 2; this.restartDelay = 0;
    this.killed = 0; this.ceiling = 150; this.tracking = 0; this.depth = 100;
    this.waveType = 0; this.formation = false;
    this.newWave();
    for (const star of this.stars) {
      star.x = Math.trunc(this.random() * 400);
      star.y = Math.trunc(this.random() * 350);
      star.speed = Math.trunc(this.random() * 5 + 1);
    }
  }

  play(id) { if (this.soundOn) this.sound(id); }

  newWave() {
    let vx = 0, vy = 0;
    this.killed = 0;
    this.formation = this.random() > 0.2;
    if (this.formation) {
      this.waveType = (this.waveType + 1) % 3;
      vx = Math.trunc(this.random() * 10) + 1;
      vy = Math.trunc(this.random() * 5) + 1;
    }
    this.ceiling = Math.trunc(this.random() * this.depth) + 100;
    const ox = Math.trunc(this.random() * 20), oy = Math.trunc(this.random() * 20);
    this.aliens.forEach((a, i) => {
      a.x = 300 - (30 * i - ox); a.y = this.ceiling - (30 * i + oy);
      a.dead = false; a.exploding = false; a.explosion = 0; a.anim = 0;
      if (this.formation) {
        a.dx = 1; a.dy = 1; a.vx = vx; a.vy = vy; a.type = this.waveType;
      } else {
        a.dx = this.random() > 0.5 ? -1 : 1;
        a.dy = this.random() > 0.5 ? -1 : 1;
        a.vx = Math.trunc(this.random() * 10) + 1;
        a.vy = Math.trunc(this.random() * 5) + 1;
        const r = this.random(); a.type = r < 0.3 ? 0 : r > 0.3 && r < 0.6 ? 1 : 2;
      }
    });
    this.entering = true;
    this.entryRemaining = Math.min(...this.aliens.map(a => a.y)) + 40;
    for (const a of this.aliens) a.y -= this.entryRemaining;
  }

  resetShip() {
    this.x = 160; this.dying = false; this.deathFrame = 0; this.deathTick = 0;
    Object.assign(this.weapons, { triple: false, spread: false, bomb: 5, laser: 5, seeker: 5, shield: 1000 });
    this.seeker.speed = 3; this.laser.length = 40; this.teleport.ready = true;
    this.bomb.max = 100; this.invincible = 100; this.invincibleFrame = 0;
    this.say(130, 'Temporary Invincibility', 100);
  }

  start() {
    this.attract = false; this.lives = 3; this.score = 0;
    this.newWave();
    for (const a of this.aliens) a.firing = false;
    this.tracking = 0; this.depth = 100; this.pickup.active = false;
    this.resetShip(); this.level = 0; this.waitingWave = false;
  }

  say(x, text, duration) {
    Object.assign(this.message, { active: true, duration, tick: 0, text, x, y: 12, flash: 0 });
  }

  shielded() { return this.keys[5] && this.weapons.shield > 0; }
  shotsActive() { return this.shots.some(s => s.active); }
  playerHit(x, y) { return x > this.x && x < this.x + 40 && y > 295 && y < 335 && !this.dying; }

  die() {
    this.message.active = false; this.play(0);
    this.dying = true; this.deathFrame = 0; this.deathTick = 0;
    for (const p of this.playerDebris) {
      p.x = this.x + 20; p.y = 315;
      p.vx = Math.trunc(this.random() * 20 + 2) - 10;
      p.vy = -Math.trunc(this.random() * 10) + 2;
    }
  }

  kill(index, shot = 0) {
    const a = this.aliens[index];
    a.exploding = true; this.play(0);
    if (this.aliens.every(a => a.exploding || a.dead)) {
      Object.assign(this.pickup, { active: true, x: a.x + 10, y: a.y + 20 });
    }
    this.score += 20 + 2 * a.vx;
    a.exploding = true; a.explosion = 0; a.explosionTick = 0;
    if (shot > 0) this.shots[shot - 1].active = false;
    for (const p of this.alienDebris) {
      p.x = a.x + 20; p.y = a.y + 20;
      p.vx = Math.trunc(this.random() * 20 + 2) - 10;
      p.vy = Math.trunc(this.random() * 20 + 2) - 10;
    }
  }

  progressLevel() {
    ++this.level;
    if (this.tracking < 4) {
      if ([5, 10, 15, 20].includes(this.level)) ++this.tracking;
    } else if (this.depth < 210) this.depth += 20;
  }

  updateAliens() {
    if (this.entering) {
      for (const a of this.aliens) a.y += 5;
      this.entryRemaining -= 5;
      if (this.entryRemaining < 1) this.entering = false;
    }
    if (this.waitingWave) return;
    for (const a of this.aliens) {
      if (a.exploding) {
        for (const p of this.alienDebris) { p.x += p.vx; p.y += p.vy; }
        if (++a.explosion > 24) {
          a.exploding = false; a.dead = true;
          if (++this.killed === 5) {
            this.progressLevel();
            if (!this.bomb.bursting) this.newWave();
            else this.waitingWave = true;
          }
        }
      } else {
        if (a.dx === 1 && a.x > 350) { a.dx = -1; a.x = 350; }
        if (a.dx === -1 && a.x < 11) { a.dx = 1; a.x = 11; }
        if (a.dy === 1 && a.y > this.ceiling) { a.dy = -1; a.y = this.ceiling; }
        if (a.dy === -1 && a.y < 50) { a.dy = 1; a.y = 50; }
        a.x += a.vx * a.dx; a.y += a.vy * a.dy;
        // The original updates all firing/animation slots inside each alien's move.
        for (const b of this.aliens) {
          if (!b.firing && !this.entering && !b.dead && !b.exploding) {
            b.shotX = b.x + 20; b.shotY = b.y + 40;
            b.firing = true; b.lostTrack = false;
          }
        }
        for (const b of this.aliens) {
          if (++b.anim > 10) { b.anim = 0; b.frame = (b.frame + 1) % 4; }
        }
      }
    }
  }

  updateEnemyShots() {
    for (const a of this.aliens) {
      a.shotY += a.shotSpeed;
      if (!this.dying && !a.lostTrack && this.tracking > 0) {
        if (a.shotX > this.x + 20) {
          if (a.shotX > this.x + 20 + this.tracking) a.shotX -= this.tracking;
        } else if (a.shotX - this.tracking < this.x + 20 && a.shotX < this.x + 20 - this.tracking) {
          a.shotX += this.tracking;
        }
      }
      // Inactive slots also advance and collide in the original binary.
      if (this.playerHit(a.shotX, a.shotY)) {
        a.firing = false;
        if (!this.shielded() && !this.teleport.active && this.invincible < 1) this.die();
      }
      if (a.shotY > 344) a.firing = false;
    }
  }

  updateShots() {
    const [s, l, r, ll, rr] = this.shots;
    if (s.active) s.y -= 10;
    if (this.weapons.triple) {
      if (l.active) l.y -= 10;
      if (r.active) r.y -= 10;
    }
    if (this.weapons.spread) {
      if (l.active && --l.x < 0) l.active = false;
      if (r.active && ++r.x > 400) r.active = false;
      if (ll.active) { ll.y -= 10; ll.x -= 2; if (ll.x < 0) ll.active = false; }
      if (rr.active) { rr.y -= 10; rr.x += 2; if (rr.x > 400) rr.active = false; }
    }
    this.aliens.forEach((a, i) => {
      const count = this.weapons.spread ? 5 : this.weapons.triple ? 3 : 1;
      for (let j = 0; j < count; j++) {
        const b = this.shots[j];
        if (b.active && !a.dead && !a.exploding && b.x > a.x && b.x < a.x + 40 && b.y > a.y && b.y < a.y + 40) this.kill(i, j + 1);
      }
    });
    for (const b of this.shots) if (b.y < 10) b.active = false;
  }

  updateLaser() {
    const l = this.laser;
    l.y -= 15;
    if (l.attached && l.y + l.length < 295) l.attached = false;
    this.aliens.forEach((a, i) => {
      if (!a.dead && !a.exploding && l.x > a.x && l.x < a.x + 40) {
        if ((a.y > l.y && a.y + 40 < l.y + l.length) || (l.y > a.y && l.y < a.y + 40) ||
          (!l.attached && l.y + l.length > a.y && l.y + l.length < a.y + 40)) this.kill(i);
      }
    });
    if (l.y + l.length < 10) l.active = false;
  }

  updateBomb() {
    const b = this.bomb;
    if (!b.bursting) {
      b.y -= 10;
      if (b.y < 10) b.active = false;
    } else {
      const half = Math.trunc(b.diameter / 2);
      this.aliens.forEach((a, i) => {
        if (!a.dead && !a.exploding && a.x + 40 > b.x - half && a.x < b.x + half && a.y + 40 > b.y - half && a.y < b.y + half) this.kill(i);
      });
      b.diameter += 5;
      if (b.diameter > b.max) {
        b.active = false; b.bursting = false; b.released = false;
        if (this.waitingWave) { this.waitingWave = false; this.newWave(); }
      }
    }
  }

  updateSeeker() {
    const s = this.seeker;
    s.y -= 12;
    if (s.y < 10) s.active = false;
    if (s.target > -1) {
      const a = this.aliens[s.target];
      if (a.x + 20 > s.x) s.x += s.speed;
      else if (a.x + 20 < s.x) s.x -= 3;
    }
    this.aliens.forEach((a, i) => {
      if (!a.dead && !a.exploding && s.x + 24 > a.x && s.x < a.x + 40 && s.y + 10 > a.y && s.y < a.y + 40) {
        this.kill(i); s.active = false;
      }
    });
  }

  updateTeleport() {
    const t = this.teleport;
    // CFR incorrectly hoists tick++ out of this branch; verified against javap.
    t.frame = t.tick;
    if (t.outgoing) {
      if (++t.tick > 3) {
        t.tick = 3; t.outgoing = false; this.x = this.x < 180 ? 359 : 1;
      }
    } else if (--t.tick < 0) t.active = false;
  }

  collect() {
    this.play(1);
    const w = this.weapons;
    if (this.random() < 0.75 || (w.triple && w.spread)) {
      const r = this.random();
      if (r < 0.2) { w.bomb += 5; this.say(110, 'Picked Up Cluster Bomb Ammo', 50); }
      else if (r > 0.2 && r < 0.4) { w.seeker += 5; this.say(110, 'Picked Up Heat Seeker Ammo', 50); }
      else if (r > 0.4 && r < 0.6) { w.laser += 5; this.say(125, 'Picked Up Some Laser Ammo', 50); }
      else if (r > 0.6 && r < 0.8) { w.shield = 1000; this.say(110, 'Shield Now At Full Strength', 50); }
      else if (r > 0.8 && r < 0.87) { ++this.seeker.speed; this.say(100, `Picked Up Heat Seeker Upgrade : ${this.seeker.speed - 2}`, 50); }
      else if (r > 0.87 && r < 0.94) { this.laser.length += 40; this.say(100, `Picked Up Laser Weapon Upgrade : ${Math.trunc(this.laser.length / 40)}`, 50); }
      else { this.bomb.max += 50; this.say(100, `Picked Up Cluster Bomb Upgrade : ${Math.trunc((this.bomb.max - 100) / 50)}`, 50); }
    } else if (w.triple) {
      w.spread = true; this.say(100, 'Picked Up Spreader Fire Upgrade', 50);
    } else {
      w.triple = true; this.say(105, 'Picked Up Triple Fire Upgrade', 50);
    }
  }

  updatePickup() {
    const p = this.pickup;
    p.y += 6;
    if (p.x + 20 > this.x && p.x < this.x + 40 && p.y + 20 > 295 && p.y < 335) {
      this.collect(); p.active = false;
    } else if (p.y > 350) p.active = false;
  }

  fire() {
    this.play(4);
    Object.assign(this.shots[0], { active: true, x: this.x + 19, y: 289 });
    if (this.weapons.triple) {
      Object.assign(this.shots[1], { active: true, x: this.x, y: 300 });
      Object.assign(this.shots[2], { active: true, x: this.x + 39, y: 300 });
    }
    if (this.weapons.spread) {
      Object.assign(this.shots[3], { active: true, x: this.x - 5, y: 305 });
      Object.assign(this.shots[4], { active: true, x: this.x + 44, y: 305 });
    }
  }

  fireLaser() {
    this.play(10);
    Object.assign(this.laser, { active: true, attached: true, x: this.x + 20, y: 295 });
    --this.weapons.laser;
  }

  fireBomb() {
    if (this.weapons.bomb < 1) return;
    this.play(8);
    Object.assign(this.bomb, { active: true, bursting: false, diameter: 5, x: this.x + 15, y: 290, released: false, flash: 0 });
    --this.weapons.bomb;
  }

  nearestAlien() {
    let target = -1, distance = 1000;
    this.aliens.forEach((a, i) => {
      if (!a.dead && !a.exploding && Math.abs(a.x - this.x) < distance) {
        distance = Math.abs(a.x - this.x); target = i;
      }
    });
    return target;
  }

  fireSeeker() {
    this.play(7);
    Object.assign(this.seeker, { active: true, x: this.x + 18, y: 289, target: this.nearestAlien() });
    --this.weapons.seeker;
  }

  teleportShip() {
    Object.assign(this.teleport, { active: true, outgoing: true, tick: 0, frame: 0, ready: false });
    for (const a of this.aliens) if (a.firing) a.lostTrack = true;
  }

  controls() {
    const k = this.keys;
    if (!this.dying) {
      if (k[0] && this.x > 9) this.x -= 10;
      if (k[1] && this.x < 350) this.x += 10;
      if (!k[5]) {
        if (k[4] && !this.laser.active && this.weapons.laser > 0) this.fireLaser();
        if (k[2] && !this.shotsActive()) this.fire();
        if (k[7]) {
          if (!this.bomb.active) this.fireBomb();
          else if (!this.bomb.bursting && this.bomb.released) { this.play(9); this.bomb.bursting = true; }
        }
        if (k[8] && !this.seeker.active && this.weapons.seeker > 0) this.fireSeeker();
      }
      if (!this.teleport.active && k[6] && this.teleport.ready) this.teleportShip();
    }
    if (k[3] && !this.attract) this.paused = true;
  }

  updateDeath() {
    if (++this.deathTick > 1) { ++this.deathFrame; this.deathTick = 0; }
    for (const p of this.playerDebris) { p.x += p.vx; p.y += p.vy; }
    if (this.deathFrame > 24) {
      this.dying = false;
      if (--this.lives < 0) { this.attract = true; this.restartDelay = 100; this.attractTick = 201; }
      this.resetShip();
    }
  }

  updateStars() {
    for (const s of this.stars) {
      s.y += s.speed;
      if (s.y > 335) { s.y = 0; s.x = Math.trunc(this.random() * 400); }
    }
  }

  tick() {
    if (this.attract) { this.updateStars(); return; }
    if (this.paused) return;
    this.updateStars(); this.updateAliens(); this.updateEnemyShots(); this.updateShots(); this.updateLaser();
    if (this.bomb.active) this.updateBomb();
    if (this.seeker.active) this.updateSeeker();
    if (this.teleport.active) this.updateTeleport();
    if (this.pickup.active) this.updatePickup();
    this.controls();
    if (this.dying) this.updateDeath();
    if (this.invincible > 0) --this.invincible;
  }

  // AWT paint advanced these counters. Keep them at simulation rate, not monitor rate.
  visualTick() {
    if (this.attract) {
      ++this.attractTick;
      if (this.restartDelay > 0) --this.restartDelay;
      if (this.attractTick > 400) this.attractTick = 0;
      return;
    }
    if (!this.dying && !this.teleport.active && this.invincible > 0) this.invincibleFrame = (this.invincibleFrame + 1) % 4;
    if (this.message.active) {
      this.message.flash = 1 - this.message.flash;
      if (++this.message.tick > this.message.duration) this.message.active = false;
    }
    if (this.shielded()) this.weapons.shield -= 5;
    if (this.bomb.active && this.bomb.bursting) this.bomb.flash = 1 - this.bomb.flash;
    this.shieldFlash = 1 - this.shieldFlash;
  }

  frame() { this.tick(); this.visualTick(); }

  keyDown(index) {
    if (this.attract && this.restartDelay === 0) this.start();
    else if (this.paused) this.paused = false;
    else if (index >= 0) this.keys[index] = true;
  }

  keyUp(index) {
    if (index >= 0) this.keys[index] = false;
    if (index === 6) this.teleport.ready = true;
    if (index === 7 && this.bomb.active) this.bomb.released = true;
  }

  releaseAll() { for (let i = 0; i < 9; i++) this.keyUp(i); }
}
