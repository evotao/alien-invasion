export async function loadImages(assets) {
  const images = {};
  await Promise.all(Object.entries(assets).filter(([, uri]) => uri.startsWith('data:image')).map(async ([key, uri]) => {
    const img = new Image(); img.src = uri; await img.decode(); images[key] = img;
  }));
  return images;
}

export class Renderer {
  constructor(canvas, images) { this.canvas = canvas; this.ctx = canvas.getContext('2d'); this.images = images; }
  image(key, x, y) { this.ctx.drawImage(this.images[key], Math.trunc(x), Math.trunc(y)); }
  color(color) { this.ctx.fillStyle = color; this.ctx.strokeStyle = color; }
  line(x1, y1, x2, y2) {
    const c = this.ctx;
    c.beginPath(); c.moveTo(Math.trunc(x1) + 0.5, Math.trunc(y1) + 0.5); c.lineTo(Math.trunc(x2) + 0.5, Math.trunc(y2) + 0.5); c.stroke();
  }
  oval(x, y, w, h, fill = false) {
    const c = this.ctx; c.beginPath(); c.ellipse(x + w / 2, y + h / 2, w / 2, h / 2, 0, 0, Math.PI * 2);
    if (fill) c.fill(); else c.stroke();
  }
  text(value, x, y) { this.ctx.fillText(value, x, y); }
  centered(value, y, size = 20) {
    this.ctx.font = size === 20 ? 'bold 20px "Times New Roman", serif' : 'bold 12px Arial, sans-serif';
    this.color('#f00'); this.text(value, 200 - this.ctx.measureText(value).width / 2, y);
  }
  explosion(x, y, frame) { if (frame >= 0 && frame < 17) this.image(`exp/exp-${String(frame + 1).padStart(2, '0')}.gif`, x, y); }
  render(g) {
    const c = this.ctx; c.clearRect(0, 0, 400, 350); this.color('#000'); c.fillRect(0, 0, 400, 350);
    this.color('#fff'); for (const s of g.stars) c.fillRect(s.x, s.y, 1, 1);
    this.image('gfx/earth.gif', 300, 50);
    if (g.attract) {
      this.hudText(g);
      if (g.attractTick < 200) {
        this.centered('Alien Invasion', 155); this.centered('by', 175, 12); this.centered('Ben Librojo', 195, 12);
      } else if (g.attractTick > 200 && g.attractTick < 300) this.centered('Game Over', 175);
      else if (g.attractTick > 300) { this.centered('Press Any Key', 165); this.centered('To Start', 185); }
      return;
    }
    for (const a of g.aliens) {
      if (a.dead) continue;
      if (!a.exploding) this.image(`gfx/ALIEN-${[1, 3, 2][a.type]}${'ABCD'[a.frame]}.GIF`, a.x, a.y);
      else {
        this.explosion(a.x - 6, a.y - 10, a.explosion);
        this.explosion(a.x + 1, a.y - 12, a.explosion - 4);
        this.explosion(a.x - 10, a.y - 9, a.explosion - 7);
        this.color('#ffc800'); for (const p of g.alienDebris) this.line(p.x, p.y, p.x + 1, p.y + 1);
      }
    }
    if (!g.dying) {
      const f = g.teleport.active ? g.teleport.frame : g.invincible > 0 ? g.invincibleFrame : -1;
      this.image(f < 0 ? 'gfx/n_player.gif' : `gfx/n_tp${(f + 1) * 2}.gif`, g.x, 295);
    } else {
      this.explosion(g.x - 3, 275, g.deathFrame);
      this.explosion(g.x + 11, 271, g.deathFrame - 3);
      this.explosion(g.x - 15, 277, g.deathFrame - 6);
      const shade = [255, 178, 124, 86, 60][Math.min(4, Math.trunc(g.deathFrame / 5))];
      this.color(`rgb(${shade},${shade},${shade})`);
      for (const p of g.playerDebris) this.oval(p.x, p.y, 5, 5, true);
    }
    c.font = 'bold 12px Arial, sans-serif';
    if (g.message.active) {
      this.color(g.message.flash === 1 ? '#fff' : '#c0c0c0');
      c.fillText(g.message.text, g.message.x, g.message.y, (g.soundOn ? 398 : 321) - g.message.x);
    }
    if (g.shielded() && !g.dying) { this.color(g.shieldFlash === 1 ? '#00f' : '#fff'); this.oval(g.x - 2, 295, 44, 50); }
    if (g.laser.active) {
      const l = g.laser, end = l.attached ? 295 : l.y + l.length;
      this.color('#ff0'); this.line(l.x, l.y, l.x, end);
      this.color('#f00'); this.line(l.x - 1, l.y, l.x - 1, end); this.line(l.x + 1, l.y, l.x + 1, end);
    }
    if (g.bomb.active) {
      const b = g.bomb;
      if (!b.bursting) this.image('gfx/n_cluster.gif', b.x, b.y);
      else { this.color(b.flash === 1 ? '#f00' : '#ff0'); const half = Math.trunc(b.diameter / 2); this.oval(b.x - half, b.y - half, b.diameter, b.diameter); }
    }
    if (g.seeker.active) {
      const s = g.seeker; this.image('gfx/n_missile.gif', s.x, s.y);
      if (s.target > -1 && !g.aliens[s.target].exploding) {
        const a = g.aliens[s.target]; this.color('#c0c0c0'); c.strokeRect(a.x + 0.5, a.y + 0.5, 40, 40);
      }
    }
    this.color('#0f0');
    for (let i = 0; i < (g.weapons.spread ? 5 : g.weapons.triple ? 3 : 1); i++) {
      const s = g.shots[i]; if (s.active) this.oval(s.x, s.y, 4, 4, true);
    }
    if (g.pickup.active) this.image('gfx/pickup.gif', g.pickup.x, g.pickup.y);
    this.color('#f00'); for (const a of g.aliens) if (a.firing) this.oval(a.shotX, a.shotY, 4, 4, true);
    this.hud(g);
    if (g.paused) this.centered('>> PAUSED <<', 175);
  }
  hudText(g) {
    this.ctx.font = 'bold 12px Arial, sans-serif'; this.color('#fff');
    this.ctx.fillText(`Score : ${g.score}`, 2, 347, 92); this.text(`Level : ${g.level}`, 2, 12);
    if (!g.soundOn) this.text('Sound : Off', 329, 12);
  }
  hud(g) {
    this.hudText(g);
    this.image('gfx/n_player_half.gif', 366, 331); this.text(`x${g.lives}`, 382, 346);
    this.color('#ff0'); this.line(99, 347, 99, 337);
    this.color('#fff'); this.text(`${g.weapons.laser} `, 103, 347);
    this.image('gfx/n_missile.gif', 130, 337); this.text(`${g.weapons.seeker} `, 138, 347);
    this.image('gfx/n_cluster.gif', 167, 337); this.text(`${g.weapons.bomb} `, 180, 347);
    this.image('gfx/n_player_half.gif', 212, 331);
    this.color(g.shieldFlash === 0 ? '#00f' : '#fff'); this.oval(209, 330, 21, 19);
    this.color('#c0c0c0'); this.ctx.strokeRect(240.5, 340.5, 100, 3);
    if (g.weapons.shield > 0) {
      this.color('#fff'); this.line(241, 341, 240 + Math.trunc(g.weapons.shield / 10), 341);
      this.line(241, 342, 240 + Math.trunc(g.weapons.shield / 10), 342);
    }
    if (this.debug) {
      this.color('#b20000'); this.text(`Current FPS : ${this.fps}`, 10, 50); this.text('sleepFor : 40', 10, 60);
    }
  }
  intro(milliseconds) {
    const c = this.ctx;
    this.color('#000'); c.fillRect(0, 0, 400, 350);
    const step = Math.floor(milliseconds / 5), intensity = step < 245 ? step : 490 - step;
    const shade = Math.max(0, Math.min(240, Math.trunc(intensity / 10) * 10));
    c.font = 'bold 16px "Times New Roman", serif';
    const title = 'JAVAGAMEPLAY.COM', spaced = [...title].join(' '), space = c.measureText(' ').width;
    let textX = 200 - c.measureText(spaced).width / 2;
    if (intensity > 4) for (let i = 0; i < title.length; i++) {
      const level = i < 4 || i > 11 ? Math.max(0, shade - 60) : shade;
      this.color(`rgb(${level},${level},${level})`); this.text(title[i], textX, 180);
      textX += c.measureText(title[i]).width + space;
    }
    const f = Math.fround, angle = step * 0.0174444, co = f(Math.cos(angle)), si = f(Math.sin(angle));
    const points = [];
    for (let axis = 0; axis < 3; axis++) for (const a of [-1, 1]) for (const b of [-1, 1]) for (const d of [-1, 1]) {
      const p = [a * 25, b * 25, d * 25]; p[axis] *= 3; points.push(p);
    }
    for (const [x, y, z] of points) {
      const hz = f(f(z * co) - f(x * si)), hx = f(f(z * si) + f(x * co));
      const gy = f(f(y * co) - f(hz * si)), gz = f(f(f(y * si) + f(hz * co)) - 100);
      const scale = f(400 / f(400 - gz)), px = Math.trunc(f(hx * scale)), py = Math.trunc(f(gy * scale));
      this.color(`rgb(${Math.max(0, shade - 40)},${Math.max(0, shade - 40)},${Math.max(0, shade - 40)})`);
      this.line(px + 200, py + 175, px + 201, py + 176);
      this.color(`rgb(${Math.max(0, shade - 60)},${Math.max(0, shade - 60)},${Math.max(0, shade - 60)})`);
      this.line(Math.trunc(px * 1.75) + 200, Math.trunc(py * 1.75) + 175, Math.trunc(px * 1.75) + 201, Math.trunc(py * 1.75) + 176);
    }
  }
}
