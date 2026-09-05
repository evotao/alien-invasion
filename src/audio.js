export const SOUND_FILES = { 0: 'Explosionv3.au', 1: 'pickup.au', 4: 'fire.au', 5: 'bl.au',
  7: 'missile2.au', 8: 'clust.au', 9: 'afterburner.au', 10: 'laser.au' };

export function decodeAu(bytes) {
  const data = new DataView(bytes.buffer, bytes.byteOffset, bytes.byteLength);
  if (data.getUint32(0) !== 0x2e736e64 || data.getUint32(12) !== 1 || data.getUint32(20) !== 1) throw new Error('Unsupported AU audio');
  const offset = data.getUint32(4);
  const size = Math.min(data.getUint32(8), bytes.length - offset);
  const samples = new Float32Array(size);
  for (let i = 0; i < size; i++) {
    const value = (~bytes[offset + i]) & 255;
    const magnitude = (((value & 15) * 8 + 132) << ((value >> 4) & 7)) - 132;
    samples[i] = (value & 128 ? -magnitude : magnitude) / 32768;
  }
  return { sampleRate: data.getUint32(16), samples };
}

export class Sound {
  constructor(assets) {
    this.assets = assets; this.context = null; this.buffers = new Map(); this.playing = new Map();
    this.enabled = true; this.volume = 0.5; this.suspended = false;
  }
  async unlock() {
    if (!this.context) {
      const Audio = globalThis.AudioContext || globalThis.webkitAudioContext;
      if (!Audio) return;
      this.context = new Audio();
      this.gain = this.context.createGain(); this.gain.connect(this.context.destination);
      for (const [id, file] of Object.entries(SOUND_FILES)) {
        const raw = atob(this.assets[`sfx/${file}`].split(',')[1]);
        const decoded = decodeAu(Uint8Array.from(raw, c => c.charCodeAt(0)));
        const buffer = this.context.createBuffer(1, decoded.samples.length, decoded.sampleRate);
        buffer.copyToChannel(decoded.samples, 0); this.buffers.set(Number(id), buffer);
      }
    }
    await this.context.resume();
    this.updateVolume();
    if (!this.playing.has(5)) this.play(5, true);
  }
  play(id, loop = false) {
    if (!this.context || !this.enabled || this.suspended || !this.buffers.has(id)) return;
    this.playing.get(id)?.stop();
    const source = this.context.createBufferSource();
    source.buffer = this.buffers.get(id); source.loop = loop; source.connect(this.gain);
    source.onended = () => { if (this.playing.get(id) === source) this.playing.delete(id); };
    this.playing.set(id, source); source.start();
  }
  updateVolume() { if (this.gain) this.gain.gain.value = this.enabled && !this.suspended ? this.volume : 0; }
  setEnabled(value) { this.enabled = value; this.updateVolume(); if (value && !this.playing.has(5)) this.play(5, true); }
  setSuspended(value) { this.suspended = value; this.updateVolume(); if (!value && !this.playing.has(5)) this.play(5, true); }
}
