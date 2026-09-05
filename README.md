# Alien Invasion

[Play in your browser](https://evotao.github.io/alien-invasion/)

A standalone browser port of Ben Librojo's 1999 Java applet, recovered from
`AlienInvasion.zip`. Open `index.html` directly. It needs no server, Java runtime,
installation, internet connection, or external assets.

All 48 original media files are embedded: 40 images and eight AU sound effects.
The GIFs and JPEG are unchanged. The original mu-law audio is decoded directly
into Web Audio buffers at its original sample rate. Sound begins after the first
interaction, as required by browsers.

## Controls

| Action | Original Keys |
| --- | --- |
| Move left / right | Left / Right arrows, or J / L |
| Fire | Space |
| Laser | A |
| Heat seeker | W |
| Launch cluster bomb | D |
| Detonate cluster bomb | Release D, then press D again |
| Shield | Hold Up arrow or I |
| Teleport | Down arrow or K |
| Pause / resume | P; any game key also resumes |
| Sound | S |
| Original FPS diagnostic | Ctrl+D |

The buttons support mouse, keyboard, and simultaneous touch holds. The playfield
also supports dragging the ship on touchscreens, at the original movement speed.
Esc is an additional pause key. The toolbar includes a new-game button,
full-screen control, mute, and volume. The best score is stored locally.

## Fidelity

The rules are translated from the archived `invader.class`, with the original
400 x 350 coordinate system and a fixed 40 ms simulation step. This preserves:

- Five-alien waves, three animated alien types, randomized formations and motion.
- Exact projectile speeds, collision bounds, targeting, and update order.
- Single, triple, and five-shot spread fire with one volley at a time.
- Lasers, heat seekers, remotely detonated cluster bombs, and their upgrades.
- Shield energy, firing restrictions, teleport phases, and tracking disruption.
- All nine pickup outcomes and their original probabilities.
- Scoring, 100-tick respawn protection, upgrade resets, and four starting ships.
- Difficulty increases at levels 5, 10, 15, and 20, followed by deeper waves.
- Delayed waves during cluster blasts, explosion timing, debris, and messages.
- Original HUD, Earth, starfield, title/game-over attract cycle, and publisher intro.

The port also retains unusual behavior confirmed in the binary, including the
seeker's asymmetric steering, square cluster-blast hit bounds, updates of inactive
projectile slots, and the original order of wave initialization and death resets.

Browser adaptations: the viewport scales without changing game coordinates;
pause and focus loss freeze the entire game and mute audio; mute affects the
background loop too; fast touch taps are retained for one simulation step.
Browser fonts and primitive rasterization have small cosmetic differences from
AWT. Graphics assets themselves are unchanged.

## Verification

- `npm test`: compares JavaScript with the actual original bytecode over 11,734
  checkpoints, checking all 37 mapped state groups and sound events. It includes
  all nine pickups, long play traces, late levels, game over, and blast-delayed waves.
- The audio test compares every decoded sample in all eight AU files with FFmpeg.
- `npm run test:render`: compares five rendered playfields with Java screenshots.
  More than 99.5% of inspected pixels match within the stated color tolerance.
- `npm run test:browser`: checks offline file loading, input, audio, full screen,
  pause, focus loss, game over/restart, touch taps, layout, and canvas content at
  five desktop/mobile viewports in Chrome.
- `PLAYWRIGHT_BROWSERS_PATH=tools/browsers BROWSER=webkit npm run test:browser`
  runs the same workflows in WebKit. WebKit's automation offline mode rejects
  file URLs, so this test blocks HTTP(S) requests instead. Both engines issue zero
  network requests. Reports and screenshots are in `artifacts/`.
- `node tests/layout.mjs` checks clipping and overflow at 15 screen sizes,
  including the responsive breakpoints.

The Java oracle requires JDK 18 (an applet-capable JDK), FFmpeg, and Gson 2.11.
The archive omits `m.class`, a star record containing three integers; its complete
field use is present in `invader.class`, and `reference/m.java` supplies it.
The old obfuscator also wrote invalid local-variable debug records. `StripDebug`
removes debug attributes with ASM so a current JVM can load the original game
instructions. The tests execute those instructions, not recompiled decompiler
output. CFR's incorrect hoisting of teleport's increment was checked against
`javap` and corrected in the JavaScript translation.

## Source And Build

- `src/engine.js`: gameplay, independent of browser APIs.
- `src/renderer.js`: original playfield and intro rendering.
- `src/audio.js`: original AU decoder and Web Audio playback.
- `src/app.js`, `src/style.css`, `src/page.html`: browser interface and input.
- `scripts/build.mjs`: embeds assets and bundles everything into `index.html`.
- `reference/`: original archive, extracted files, decompiled reference, and media hashes.
- `tests/`: Java oracle, state mapping, gameplay/audio checks, and browser checks.

Run `npm ci` followed by `npm run build` to rebuild. The generated `index.html`
is the complete distributable; none of the project folders are needed to play.
`npm test` compiles the oracle before `npm run test:render` is run.

## GitHub Pages

GitHub Pages serves the committed `index.html` directly from the root of `main`.
The `.nojekyll` marker keeps it a static site. To publish a game change, run
`npm run build`, commit the updated source and `index.html`, and push `main`.

Original archive SHA-256:
`acf97236f242768c561aa6e744ea586dda7c54098fce760e6a2d775465f36c56`.
Original game and assets: copyright 1999 Ben Librojo / JavaGamePlay.com.
