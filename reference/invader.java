/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  a
 *  b
 *  d
 *  e
 *  f
 *  g
 *  h
 *  invader
 *  j
 *  k
 *  l
 *  m
 *  n
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.ImageObserver;
import java.util.Date;

public class invader
extends Applet
implements Runnable {
    final int \u00c0 = 40;
    final int \u00c1 = 400;
    final int \u00c2 = 350;
    final int \u00c3 = 350;
    final int \u00c4 = 11;
    final int \u00c5 = 50;
    final int \u00c6 = 335;
    final int \u00c7 = 40;
    final int \u00c8 = 40;
    final int \u00c9 = 40;
    final int \u00ca = 40;
    final int \u00cb = 10;
    final int \u00cc = 295;
    final int \u00cd = 20;
    final int \u00ce = 4;
    final int \u00cf = 20;
    final int \u00d0 = 14;
    final int \u00d1 = 14;
    final int \u00d2 = 5;
    final int \u00d3 = 5;
    final int \u00d4 = 4;
    final int \u00d5 = 3;
    final int \u00d6 = 15;
    final int \u00d8 = 9;
    final int \u00d9 = 10;
    final int \u00da = 5;
    final int \u00db = 12;
    final int \u00dc = 3;
    final int \u00dd = 6;
    final int \u00de = 1000;
    final boolean \u00df = false;
    final int \u00e0 = 100;
    final int \u00e1 = 50;
    final int \u00e2 = 40;
    final int \u00e3 = 40;
    d[] \u00e4 = new d[20];
    d[] \u00e5 = new d[20];
    k[] \u00e6 = new k[5];
    m[] \u00e7 = new m[20];
    m \u00e8;
    n \u00e9 = new n();
    g \u00ea = new g();
    e \u00eb = new e();
    b \u00ec = new b();
    l \u00ed = new l();
    f \u00ee = new f();
    f \u00ef = new f();
    f \u00f0 = new f();
    f \u00f1 = new f();
    f \u00f2 = new f();
    j \u00f3 = new j();
    h \u00f4 = new h();
    final int \u00f5 = 106;
    final int \u00f6 = 108;
    final int \u00f8 = 32;
    final int \u00f9 = 112;
    final int \u00fa = 115;
    final int \u00fb = 97;
    final int \u00fc = 105;
    final int \u00fd = 107;
    final int \u00fe = 100;
    final int \u00ff = 119;
    final int \u0100 = 1006;
    final int \u0101 = 1007;
    final int \u0102 = 1004;
    final int \u0103 = 1005;
    final int \u0104 = 0;
    final int \u0105 = 1;
    final int \u0106 = 2;
    final int \u0107 = 3;
    final int \u0108 = 4;
    final int \u0109 = 5;
    final int \u010a = 6;
    final int \u010b = 7;
    final int \u010c = 8;
    boolean[] \u010d;
    String[] \u010e;
    int[] \u010f;
    Font \u0110;
    FontMetrics \u0111;
    public boolean \u0112 = true;
    public Image \u0113;
    public Graphics \u0114;
    Image[] \u0115;
    Image[] \u0116;
    Image[] \u0117;
    Image[] \u0118;
    Image[] \u0119;
    Image \u011a;
    Image \u011b;
    Image \u011c;
    Image \u011d;
    Image \u011e;
    Image \u011f;
    a \u0120;
    int \u0121;
    int \u0122;
    int \u0123;
    int \u0124;
    boolean \u0125 = false;
    int \u0126;
    int \u0127;
    int \u0128;
    int \u0129 = 1;
    boolean \u012a = false;
    boolean \u012b = true;
    boolean \u012c = true;
    boolean \u012d = false;
    boolean \u012e;
    int \u012f;
    int \u0130;
    int \u0131;
    int \u0132 = 2;
    int \u0133;
    Thread \u0134;
    AudioClip[] \u0135;
    AudioClip \u0136;
    MediaTracker \u0137 = new MediaTracker((Component)this);
    int \u0138;
    int \u0139 = 150;
    int \u013a;
    int \u013b = 100;
    int \u013c;
    boolean \u013d = false;
    Date \u013e = new Date();
    long \u013f;
    long \u0140;
    long \u0141;
    Font \u0142 = new Font("Dialog", 1, 12);
    Font \u0143 = new Font("TimesRoman", 1, 20);
    int \u0144;
    long \u0145;
    long \u0146 = -1L;
    long \u0147;
    long \u0148;
    long \u0149 = -100L;
    long \u014a;
    long \u014b = -777L;
    boolean \u014c = false;
    long \u014d = 40L;

    public void init() {
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e4[n2] = new d();
            this.\u00e5[n2] = new d();
            ++n2;
        }
        n2 = 0;
        while (n2 < 5) {
            this.\u00e6[n2] = new k();
            this.\u00e6[n2].\u0172 = false;
            ++n2;
        }
        n2 = 0;
        while (n2 < 20) {
            this.\u00e7[n2] = new m();
            ++n2;
        }
        if (this.\u0112) {
            Dimension dimension = this.size();
            this.\u0113 = this.createImage(dimension.width, dimension.height);
            this.\u0114 = this.\u0113.getGraphics();
        }
        this.\u0130 = 0;
        this.\u010d = new boolean[9];
        this.\u0135 = new AudioClip[14];
        this.\u00d9();
        this.\u00c0();
        this.\u00cf();
        this.\u00ce();
        this.m();
        this.\u0120 = new a(400, 350, 250, 25);
        this.\u013f = this.\u013e.getTime();
        this.\u0140 = this.\u013e.getTime();
    }

    public void m() {
        this.\u0135[0] = this.getAudioClip(this.getCodeBase(), "sfx/Explosionv3.au");
        this.\u0135[1] = this.getAudioClip(this.getCodeBase(), "sfx/pickup.au");
        this.\u0135[4] = this.getAudioClip(this.getCodeBase(), "sfx/fire.au");
        this.\u0135[5] = this.getAudioClip(this.getCodeBase(), "sfx/bl.au");
        this.\u0135[7] = this.getAudioClip(this.getCodeBase(), "sfx/missile2.au");
        this.\u0135[8] = this.getAudioClip(this.getCodeBase(), "sfx/clust.au");
        this.\u0135[9] = this.getAudioClip(this.getCodeBase(), "sfx/afterburner.au");
        this.\u0135[10] = this.getAudioClip(this.getCodeBase(), "sfx/laser.au");
        this.\u0135[1].play();
        this.\u0135[1].stop();
        this.\u0135[0].play();
        this.\u0135[0].stop();
        this.\u0135[4].play();
        this.\u0135[4].stop();
        this.\u0135[7].play();
        this.\u0135[7].stop();
        this.\u0135[8].play();
        this.\u0135[8].stop();
        this.\u0135[9].play();
        this.\u0135[9].stop();
        this.\u0135[10].play();
        this.\u0135[10].stop();
    }

    public void \u00d9() {
        this.\u0116 = new Image[4];
        this.\u0117 = new Image[4];
        this.\u0118 = new Image[4];
        this.\u0119 = new Image[4];
        this.\u0115 = new Image[17];
        this.\u0115[0] = this.getImage(this.getCodeBase(), "exp/exp-01.gif");
        this.\u0137.addImage(this.\u0115[0], 0);
        this.\u0115[1] = this.getImage(this.getCodeBase(), "exp/exp-02.gif");
        this.\u0137.addImage(this.\u0115[1], 0);
        this.\u0115[2] = this.getImage(this.getCodeBase(), "exp/exp-03.gif");
        this.\u0137.addImage(this.\u0115[2], 0);
        this.\u0115[3] = this.getImage(this.getCodeBase(), "exp/exp-04.gif");
        this.\u0137.addImage(this.\u0115[3], 0);
        this.\u0115[4] = this.getImage(this.getCodeBase(), "exp/exp-05.gif");
        this.\u0137.addImage(this.\u0115[4], 0);
        this.\u0115[5] = this.getImage(this.getCodeBase(), "exp/exp-06.gif");
        this.\u0137.addImage(this.\u0115[5], 0);
        this.\u0115[6] = this.getImage(this.getCodeBase(), "exp/exp-07.gif");
        this.\u0137.addImage(this.\u0115[6], 0);
        this.\u0115[7] = this.getImage(this.getCodeBase(), "exp/exp-08.gif");
        this.\u0137.addImage(this.\u0115[7], 0);
        this.\u0115[8] = this.getImage(this.getCodeBase(), "exp/exp-09.gif");
        this.\u0137.addImage(this.\u0115[8], 0);
        this.\u0115[9] = this.getImage(this.getCodeBase(), "exp/exp-10.gif");
        this.\u0137.addImage(this.\u0115[9], 0);
        this.\u0115[10] = this.getImage(this.getCodeBase(), "exp/exp-11.gif");
        this.\u0137.addImage(this.\u0115[10], 0);
        this.\u0115[11] = this.getImage(this.getCodeBase(), "exp/exp-12.gif");
        this.\u0137.addImage(this.\u0115[11], 0);
        this.\u0115[12] = this.getImage(this.getCodeBase(), "exp/exp-13.gif");
        this.\u0137.addImage(this.\u0115[12], 0);
        this.\u0115[13] = this.getImage(this.getCodeBase(), "exp/exp-14.gif");
        this.\u0137.addImage(this.\u0115[13], 0);
        this.\u0115[14] = this.getImage(this.getCodeBase(), "exp/exp-15.gif");
        this.\u0137.addImage(this.\u0115[14], 0);
        this.\u0115[15] = this.getImage(this.getCodeBase(), "exp/exp-16.gif");
        this.\u0137.addImage(this.\u0115[15], 0);
        this.\u0115[16] = this.getImage(this.getCodeBase(), "exp/exp-17.gif");
        this.\u0137.addImage(this.\u0115[16], 0);
        this.\u011f = this.getImage(this.getCodeBase(), "gfx/n_player_half.gif");
        this.\u0137.addImage(this.\u011f, 0);
        this.\u011e = this.getImage(this.getCodeBase(), "gfx/pickup.gif");
        this.\u0137.addImage(this.\u011e, 0);
        this.\u011a = this.getImage(this.getCodeBase(), "gfx/n_cluster.gif");
        this.\u0137.addImage(this.\u011a, 0);
        this.\u011b = this.getImage(this.getCodeBase(), "gfx/n_missile.gif");
        this.\u0137.addImage(this.\u011b, 0);
        this.\u0119[0] = this.getImage(this.getCodeBase(), "gfx/n_tp2.gif");
        this.\u0137.addImage(this.\u0119[0], 0);
        this.\u0119[1] = this.getImage(this.getCodeBase(), "gfx/n_tp4.gif");
        this.\u0137.addImage(this.\u0119[1], 0);
        this.\u0119[2] = this.getImage(this.getCodeBase(), "gfx/n_tp6.gif");
        this.\u0137.addImage(this.\u0119[2], 0);
        this.\u0119[3] = this.getImage(this.getCodeBase(), "gfx/n_tp8.gif");
        this.\u0137.addImage(this.\u0119[3], 0);
        this.\u0116[0] = this.getImage(this.getCodeBase(), "gfx/ALIEN-1A.GIF");
        this.\u0137.addImage(this.\u0116[0], 0);
        this.\u0116[1] = this.getImage(this.getCodeBase(), "gfx/ALIEN-1B.GIF");
        this.\u0137.addImage(this.\u0116[1], 0);
        this.\u0116[2] = this.getImage(this.getCodeBase(), "gfx/ALIEN-1C.GIF");
        this.\u0137.addImage(this.\u0116[2], 0);
        this.\u0116[3] = this.getImage(this.getCodeBase(), "gfx/ALIEN-1D.GIF");
        this.\u0137.addImage(this.\u0116[3], 0);
        this.\u0117[0] = this.getImage(this.getCodeBase(), "gfx/ALIEN-3A.GIF");
        this.\u0137.addImage(this.\u0117[0], 0);
        this.\u0117[1] = this.getImage(this.getCodeBase(), "gfx/ALIEN-3B.GIF");
        this.\u0137.addImage(this.\u0117[1], 0);
        this.\u0117[2] = this.getImage(this.getCodeBase(), "gfx/ALIEN-3C.GIF");
        this.\u0137.addImage(this.\u0117[2], 0);
        this.\u0117[3] = this.getImage(this.getCodeBase(), "gfx/ALIEN-3D.GIF");
        this.\u0137.addImage(this.\u0117[3], 0);
        this.\u0118[0] = this.getImage(this.getCodeBase(), "gfx/ALIEN-2A.GIF");
        this.\u0137.addImage(this.\u0118[0], 0);
        this.\u0118[1] = this.getImage(this.getCodeBase(), "gfx/ALIEN-2B.GIF");
        this.\u0137.addImage(this.\u0118[1], 0);
        this.\u0118[2] = this.getImage(this.getCodeBase(), "gfx/ALIEN-2C.GIF");
        this.\u0137.addImage(this.\u0118[2], 0);
        this.\u0118[3] = this.getImage(this.getCodeBase(), "gfx/ALIEN-2D.GIF");
        this.\u0137.addImage(this.\u0118[3], 0);
        this.\u011c = this.getImage(this.getCodeBase(), "gfx/n_player.gif");
        this.\u0137.addImage(this.\u011c, 0);
        this.\u011d = this.getImage(this.getCodeBase(), "gfx/earth.gif");
        this.\u0137.addImage(this.\u011d, 0);
    }

    public void \u00ce() {
        this.\u010e = new String[14];
        this.\u010e[0] = ">> PAUSED <<";
        this.\u010e[1] = "Game Over";
        this.\u010e[2] = "Score : ";
        this.\u010e[3] = "Press Any Key";
        this.\u010e[4] = "To Start";
        this.\u010e[5] = "Alien Invasion";
        this.\u010e[6] = "by";
        this.\u010e[7] = "Ben Librojo";
        this.\u010e[8] = "Ships : ";
        this.\u010e[9] = "Sound : Off";
        this.\u010e[10] = "Level : ";
        this.\u010e[11] = "www.JavaGamePlay.com";
        this.\u010e[13] = "Loading resources, please wait...";
        this.\u010f = new int[14];
        this.\u010f[0] = 88;
        this.\u010f[1] = 68;
        this.\u010f[2] = 39;
        this.\u010f[3] = 85;
        this.\u010f[4] = 48;
        this.\u010f[5] = 124;
        this.\u010f[6] = 15;
        this.\u010f[7] = 67;
        this.\u010f[11] = 205;
        this.\u010f[12] = 125;
        this.\u010f[13] = 163;
    }

    public void s() {
        int n2;
        int n3 = 0;
        this.\u012e = true;
        int n4 = 0;
        while (n4 < 5) {
            if (this.\u00e6[n4].\u0166 < this.\u00e6[n3].\u0166) {
                n3 = n4;
            }
            ++n4;
        }
        this.\u012f = n2 = this.\u00e6[n3].\u0166 + 40;
        n4 = 0;
        while (n4 < 5) {
            this.\u00e6[n4].\u0166 -= n2;
            ++n4;
        }
    }

    public void \u00cc() {
        int n2 = 0;
        while (n2 < 5) {
            this.\u00e6[n2].\u0166 += 5;
            ++n2;
        }
        this.\u012f -= 5;
        if (this.\u012f < 1) {
            this.\u012e = false;
        }
    }

    public void \u00c0() {
        int n2 = 0;
        int n3 = 0;
        this.\u0138 = 0;
        this.\u013d = Math.random() > 0.2;
        if (this.\u013d) {
            ++this.\u013c;
            if (this.\u013c == 3) {
                this.\u013c = 0;
            }
            n2 = (int)(Math.random() * 10.0) + 1;
            n3 = (int)(Math.random() * 5.0) + 1;
        }
        this.\u0139 = (int)(Math.random() * (double)this.\u013b) + 100;
        int n4 = (int)(Math.random() * 20.0);
        int n5 = (int)(Math.random() * 20.0);
        int n6 = 0;
        while (n6 < 5) {
            this.\u00e6[n6].\u0165 = 300 - (30 * n6 - n4);
            this.\u00e6[n6].\u0166 = this.\u0139 - (30 * n6 + n5);
            this.\u00e6[n6].\u016e = false;
            this.\u00e6[n6].\u016f = false;
            this.\u00e6[n6].\u0170 = 0;
            this.\u00e6[n6].\u016c = 0;
            if (this.\u013d) {
                this.\u00e6[n6].\u0169 = 1;
                this.\u00e6[n6].\u016a = 1;
                this.\u00e6[n6].\u0167 = n2;
                this.\u00e6[n6].\u0168 = n3;
                this.\u00e6[n6].\u016d = this.\u013c;
            } else {
                double d2 = Math.random();
                this.\u00e6[n6].\u0169 = d2 > 0.5 ? -1 : 1;
                d2 = Math.random();
                this.\u00e6[n6].\u016a = d2 > 0.5 ? -1 : 1;
                this.\u00e6[n6].\u0167 = (int)(Math.random() * 10.0) + 1;
                this.\u00e6[n6].\u0168 = (int)(Math.random() * 5.0) + 1;
                d2 = Math.random();
                this.\u00e6[n6].\u016d = d2 < 0.3 ? 0 : (d2 > 0.3 && d2 < 0.6 ? 1 : 2);
            }
            ++n6;
        }
        this.s();
    }

    public void \u00cf() {
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e7[n2].\u017c = (int)(Math.random() * 400.0);
            this.\u00e7[n2].\u017d = (int)(Math.random() * 350.0);
            this.\u00e7[n2].\u017e = (int)(Math.random() * 5.0 + 1.0);
            ++n2;
        }
    }

    public void b() {
        this.\u0121 = 160;
        this.\u0125 = false;
        this.\u0126 = 0;
        this.\u0127 = 0;
        this.\u00f3.\u015e = false;
        this.\u00f3.\u015f = false;
        this.\u00f3.\u0161 = 5;
        this.\u00f3.\u0160 = 5;
        this.\u00f3.\u0162 = 5;
        this.\u00f3.\u0163 = 1000;
        this.\u00ea.\u0151 = 3;
        this.\u00ec.n = 40;
        this.\u00ed.\u017b = true;
        this.\u00e9.\u0185 = 100;
        this.\u0123 = 100;
        this.p(130, "Temporary Invincibility", 100);
        this.\u0124 = 0;
    }

    public void \u00d4(int n2) {
        if (!this.\u00e6[n2].\u016e && !this.\u00e6[n2].\u016f) {
            this.\u00e6[n2].\u0174 = this.\u00e6[n2].\u0165 + 20;
            this.\u00e6[n2].\u0175 = this.\u00e6[n2].\u0166 + 40;
            this.\u00e6[n2].\u0172 = true;
            this.\u00e6[n2].\u0173 = false;
        }
    }

    public int \u00d6() {
        return this.\u0121 + 20;
    }

    public void a(int n2) {
        if (this.\u013a > 0) {
            if (this.\u00e6[n2].\u0174 > this.\u00d6()) {
                if (this.\u00e6[n2].\u0174 > this.\u00d6() + this.\u013a) {
                    this.\u00e6[n2].\u0174 -= this.\u013a;
                    return;
                }
            } else if (this.\u00e6[n2].\u0174 - this.\u013a < this.\u00d6() && this.\u00e6[n2].\u0174 < this.\u00d6() - this.\u013a) {
                this.\u00e6[n2].\u0174 += this.\u013a;
            }
        }
    }

    public boolean y() {
        return this.\u010d[5] && this.\u00f3.\u0163 > 0;
    }

    public void \u00cd() {
        int n2 = 0;
        while (n2 < 5) {
            this.\u00e6[n2].\u0175 += this.\u00e6[n2].\u0176;
            if (!this.\u0125 && !this.\u00e6[n2].\u0173) {
                this.a(n2);
            }
            if (this.T(this.\u00e6[n2].\u0174, this.\u00e6[n2].\u0175, this.\u0121, 295)) {
                this.\u00e6[n2].\u0172 = false;
                if (!this.y() && !this.\u00ed.\u0177 && this.\u0123 < 1) {
                    this.q();
                }
            }
            if (this.\u00e6[n2].\u0175 > 344) {
                this.\u00e6[n2].\u0172 = false;
            }
            ++n2;
        }
    }

    public void q() {
        this.\u00f4.\u0154 = false;
        if (this.\u012c) {
            this.\u00cb(0);
        }
        this.\u0125 = true;
        this.\u0126 = 0;
        this.\u0127 = 0;
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e4[n2].q = this.\u0121 + 20;
            this.\u00e4[n2].r = 315;
            this.\u00e4[n2].s = (int)(Math.random() * 20.0 + 2.0) - 10;
            this.\u00e4[n2].t = -((int)(Math.random() * 10.0)) + 2;
            ++n2;
        }
    }

    public boolean T(int n2, int n3, int n4, int n5) {
        return n2 > n4 && n2 < n4 + 40 && n3 > n5 && n3 < 335 && !this.\u0125;
    }

    public void \u00dd() {
        int n2 = 0;
        while (n2 < 5) {
            if (!this.\u00e6[n2].\u0172 && !this.\u012e) {
                this.\u00d4(n2);
            }
            ++n2;
        }
    }

    public void \u00d8(Graphics graphics, int n2, int n3, int n4) {
        if (Math.random() > 0.5) {
            graphics.setColor(Color.yellow);
        } else {
            graphics.setColor(Color.red);
        }
        graphics.fillOval(n2 - n4 / 2, n3 - n4 / 2, n4, n4);
    }

    public void S(Graphics graphics, int n2, int n3, int n4) {
        graphics.drawImage(this.\u0115[n4], n2, n3, (ImageObserver)this);
    }

    public void \u00d2(Graphics graphics) {
        graphics.setColor(Color.white);
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e8 = this.\u00e7[n2];
            graphics.drawLine(this.\u00e8.\u017c, this.\u00e8.\u017d, this.\u00e8.\u017c, this.\u00e8.\u017d);
            ++n2;
        }
    }

    public void i() {
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e7[n2].\u017d += this.\u00e7[n2].\u017e;
            if (this.\u00e7[n2].\u017d > 335) {
                this.\u00e7[n2].\u017d = 0;
                this.\u00e7[n2].\u017c = (int)(Math.random() * 400.0);
            }
            ++n2;
        }
    }

    public void Y() {
        if (this.\u0126 < 17) {
            this.S(this.\u0114, this.\u0121 - 3, 275, this.\u0126);
        }
        if (this.\u0126 > 2 && this.\u0126 < 20) {
            this.S(this.\u0114, this.\u0121 + 11, 271, this.\u0126 - 3);
        }
        if (this.\u0126 > 5 && this.\u0126 < 23) {
            this.S(this.\u0114, this.\u0121 - 15, 277, this.\u0126 - 6);
        }
        if (this.\u0126 < 5) {
            this.\u0114.setColor(Color.white);
        } else if (this.\u0126 < 10) {
            this.\u0114.setColor(Color.white.darker());
        } else if (this.\u0126 < 15) {
            this.\u0114.setColor(Color.white.darker().darker());
        } else if (this.\u0126 < 20) {
            this.\u0114.setColor(Color.white.darker().darker().darker());
        } else {
            this.\u0114.setColor(Color.white.darker().darker().darker().darker());
        }
        int n2 = 0;
        while (n2 < 20) {
            this.\u0114.fillOval(this.\u00e4[n2].q, this.\u00e4[n2].r, 5, 5);
            ++n2;
        }
    }

    public void Z(int n2) {
        if (this.\u00e6[n2].\u0170 < 17) {
            this.S(this.\u0114, this.\u00e6[n2].\u0165 - 6, this.\u00e6[n2].\u0166 - 10, this.\u00e6[n2].\u0170);
        }
        if (this.\u00e6[n2].\u0170 > 3 && this.\u00e6[n2].\u0170 < 21) {
            this.S(this.\u0114, this.\u00e6[n2].\u0165 + 1, this.\u00e6[n2].\u0166 - 12, this.\u00e6[n2].\u0170 - 4);
        }
        if (this.\u00e6[n2].\u0170 > 6 && this.\u00e6[n2].\u0170 < 24) {
            this.S(this.\u0114, this.\u00e6[n2].\u0165 - 10, this.\u00e6[n2].\u0166 - 9, this.\u00e6[n2].\u0170 - 7);
        }
        this.\u0114.setColor(Color.orange);
        int n3 = 0;
        while (n3 < 20) {
            this.\u0114.drawLine(this.\u00e5[n3].q, this.\u00e5[n3].r, this.\u00e5[n3].q + 1, this.\u00e5[n3].r + 1);
            ++n3;
        }
    }

    public void \u00dc(Graphics graphics) {
        graphics.setFont(this.\u0143);
        ++this.\u0131;
        if (this.\u0133 > 0) {
            --this.\u0133;
        }
        this.e(graphics);
        if (this.\u0131 < 200) {
            graphics.setFont(this.\u0143);
            this.W(graphics, 5, 155);
            graphics.setFont(this.\u0142);
            this.W(graphics, 6, 175);
            this.W(graphics, 7, 195);
            graphics.setFont(this.\u0143);
        } else if (this.\u0131 > 200 && this.\u0131 < 300) {
            graphics.setFont(this.\u0143);
            this.W(graphics, 1, 175);
        } else if (this.\u0131 > 300) {
            graphics.setFont(this.\u0143);
            this.W(graphics, 3, 165);
            this.W(graphics, 4, 185);
        }
        if (this.\u0131 > 400) {
            this.\u0131 = 0;
        }
    }

    public void O(Graphics graphics) {
        int n2 = 0;
        while (n2 < 5) {
            if (!this.\u00e6[n2].\u016e) {
                if (!this.\u00e6[n2].\u016f) {
                    if (this.\u00e6[n2].\u016d == 0) {
                        graphics.drawImage(this.\u0116[this.\u00e6[n2].\u016b], this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, (ImageObserver)this);
                    } else if (this.\u00e6[n2].\u016d == 1) {
                        graphics.drawImage(this.\u0117[this.\u00e6[n2].\u016b], this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, (ImageObserver)this);
                    } else {
                        graphics.drawImage(this.\u0118[this.\u00e6[n2].\u016b], this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, (ImageObserver)this);
                    }
                } else {
                    this.Z(n2);
                }
            }
            ++n2;
        }
    }

    public boolean \u00db() {
        return this.\u00ee.x || this.\u00ef.x || this.\u00f0.x || this.\u00f1.x || this.\u00f2.x;
    }

    public void \u00c1(Graphics graphics) {
        if (this.\u0122 == 0) {
            graphics.setColor(Color.blue);
        } else {
            graphics.setColor(Color.white);
        }
        if (!this.\u0125) {
            graphics.drawOval(this.\u0121 - 2, 295, 44, 50);
        }
    }

    public void \u00e0(Graphics graphics) {
        if (!this.\u00e9.\u0180) {
            graphics.drawImage(this.\u011a, this.\u00e9.\u0181, this.\u00e9.\u0182, (ImageObserver)this);
            return;
        }
        if (this.\u00e9.\u0184 == 0) {
            graphics.setColor(Color.red);
        } else {
            graphics.setColor(Color.yellow);
        }
        this.\u00e9.\u0184 = 1 - this.\u00e9.\u0184;
        graphics.drawOval(this.\u00e9.\u0181 - this.\u00e9.\u0183 / 2, this.\u00e9.\u0182 - this.\u00e9.\u0183 / 2, this.\u00e9.\u0183, this.\u00e9.\u0183);
    }

    public void \u00da(Graphics graphics) {
        graphics.drawImage(this.\u011b, this.\u00ea.\u014f, this.\u00ea.\u0150, (ImageObserver)this);
        if (this.\u00ea.\u0152 > -1 && !this.\u00e6[this.\u00ea.\u0152].\u016f) {
            graphics.setColor(Color.lightGray);
            graphics.drawRect(this.\u00e6[this.\u00ea.\u0152].\u0165, this.\u00e6[this.\u00ea.\u0152].\u0166, 40, 40);
        }
    }

    public void h(Graphics graphics) {
        this.\u00c1(graphics);
        this.\u00f3.\u0163 -= 5;
    }

    public void Q(Graphics graphics) {
        if (this.\u00f4.\u0154) {
            if (this.\u00f4.\u0158 == 0) {
                graphics.setColor(Color.white);
            } else {
                graphics.setColor(Color.lightGray);
            }
            this.\u00f4.\u0158 = 1 - this.\u00f4.\u0158;
            graphics.drawString(this.\u00f4.\u0157, this.\u00f4.\u0159, this.\u00f4.\u015a);
            ++this.\u00f4.\u0155;
            if (this.\u00f4.\u0155 > this.\u00f4.\u0156) {
                this.\u00f4.\u0154 = false;
            }
        }
    }

    public void t(Graphics graphics) {
        ++this.\u0124;
        if (this.\u0124 > 3) {
            this.\u0124 = 0;
        }
        graphics.drawImage(this.\u0119[this.\u0124], this.\u0121, 295, (ImageObserver)this);
    }

    public void n(Graphics graphics) {
        Dimension dimension = this.size();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
        if (this.\u0120.M) {
            this.\u0120.I(graphics, (ImageObserver)this);
            return;
        }
        this.\u00d2(this.\u0114);
        graphics.drawImage(this.\u011d, 300, 50, (ImageObserver)this);
        if (this.\u012b) {
            this.\u00dc(graphics);
            return;
        }
        this.O(graphics);
        if (!this.\u0125) {
            if (this.\u00ed.\u0177) {
                graphics.drawImage(this.\u0119[this.\u00ed.\u017a], this.\u0121, 295, (ImageObserver)this);
            } else if (this.\u0123 > 0) {
                this.t(graphics);
            } else {
                graphics.drawImage(this.\u011c, this.\u0121, 295, (ImageObserver)this);
            }
        } else {
            this.Y();
        }
        this.Q(this.\u0114);
        if (this.y()) {
            this.h(graphics);
        }
        if (this.\u00ec.m) {
            graphics.setColor(Color.yellow);
            if (this.\u00ec.l) {
                graphics.drawLine(this.\u00ec.j, this.\u00ec.k, this.\u00ec.j, 295);
                graphics.setColor(Color.red);
                graphics.drawLine(this.\u00ec.j - 1, this.\u00ec.k, this.\u00ec.j - 1, 295);
                graphics.drawLine(this.\u00ec.j + 1, this.\u00ec.k, this.\u00ec.j + 1, 295);
            } else {
                graphics.drawLine(this.\u00ec.j, this.\u00ec.k, this.\u00ec.j, this.\u00ec.k + this.\u00ec.n);
                graphics.setColor(Color.red);
                graphics.drawLine(this.\u00ec.j - 1, this.\u00ec.k, this.\u00ec.j - 1, this.\u00ec.k + this.\u00ec.n);
                graphics.drawLine(this.\u00ec.j + 1, this.\u00ec.k, this.\u00ec.j + 1, this.\u00ec.k + this.\u00ec.n);
            }
        }
        if (this.\u00e9.\u017f) {
            this.\u00e0(graphics);
        }
        if (this.\u00ea.\u014e) {
            this.\u00da(graphics);
        }
        if (this.\u00db()) {
            graphics.setColor(Color.green);
            if (this.\u00ee.x) {
                graphics.fillOval(this.\u00ee.y, this.\u00ee.z, 4, 4);
            }
            if (this.\u00f3.\u015e) {
                if (this.\u00ef.x) {
                    graphics.fillOval(this.\u00ef.y, this.\u00ef.z, 4, 4);
                }
                if (this.\u00f0.x) {
                    graphics.fillOval(this.\u00f0.y, this.\u00f0.z, 4, 4);
                }
                if (this.\u00f3.\u015f) {
                    if (this.\u00f1.x) {
                        graphics.fillOval(this.\u00f1.y, this.\u00f1.z, 4, 4);
                    }
                    if (this.\u00f2.x) {
                        graphics.fillOval(this.\u00f2.y, this.\u00f2.z, 4, 4);
                    }
                }
            }
        }
        if (this.\u00eb.u) {
            graphics.drawImage(this.\u011e, this.\u00eb.v, this.\u00eb.w, (ImageObserver)this);
        }
        int n2 = 0;
        while (n2 < 5) {
            if (this.\u00e6[n2].\u0172) {
                graphics.setColor(Color.red);
                graphics.fillOval(this.\u00e6[n2].\u0174, this.\u00e6[n2].\u0175, 4, 4);
            }
            ++n2;
        }
        this.P(this.\u0114);
    }

    public void x(Graphics graphics) {
        System.out.println("ok  : ");
        int n2 = 0;
        while (n2 < 14) {
            System.out.println(graphics.getFontMetrics().stringWidth(this.\u010e[n2]));
            ++n2;
        }
    }

    public void paint(Graphics graphics) {
        if (!this.\u0137.checkID(0)) {
            graphics.setColor(Color.red);
            this.W(graphics, 13, 175);
            return;
        }
        this.\u00d1();
        this.n(this.\u0114);
        if (this.\u012a) {
            this.W(this.\u0114, 0, 175);
        }
        graphics.drawImage(this.\u0113, 0, 0, (ImageObserver)this);
        this.\u0147 = (System.currentTimeMillis() - this.\u0146) / 1000L;
        ++this.\u0145;
        if (this.\u0149 != this.\u0147) {
            this.\u014b = this.\u014a;
            this.\u014a = 0L;
            this.\u0149 = this.\u0147;
            if (this.\u014b < 40L && this.\u014d > 40L) {
                this.\u014d -= 2L;
                return;
            }
            if (this.\u014b > 40L) {
                this.\u014d += 2L;
                return;
            }
        } else {
            ++this.\u014a;
        }
    }

    public void start() {
        if (this.\u0134 == null) {
            this.\u0134 = new Thread((Runnable)this);
            this.\u0134.start();
            this.\u0135[5].loop();
        }
    }

    public boolean j(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        return this.\u00db() && !this.\u00e6[n2].\u016e && n3 > n5 && n3 < n7 && n4 > n6 && n4 < n8 && !this.\u00e6[n2].\u016f;
    }

    public void X(int n2) {
        if (n2 == 1) {
            this.\u00ee.x = false;
            return;
        }
        if (n2 == 2) {
            this.\u00ef.x = false;
            return;
        }
        if (n2 == 3) {
            this.\u00f0.x = false;
            return;
        }
        if (n2 == 4) {
            this.\u00f1.x = false;
            return;
        }
        if (n2 == 5) {
            this.\u00f2.x = false;
        }
    }

    public void v(int n2, int n3) {
        this.\u00eb.u = true;
        this.\u00eb.v = n2 - 10;
        this.\u00eb.w = n3;
    }

    public boolean R() {
        boolean bl = true;
        int n2 = 0;
        while (n2 < 5) {
            if (!this.\u00e6[n2].\u016f && !this.\u00e6[n2].\u016e) {
                bl = false;
            }
            ++n2;
        }
        return bl;
    }

    public void \u00c5(int n2, int n3) {
        this.\u00e6[n2].\u016f = true;
        this.\u00cb(0);
        if (this.R()) {
            this.v(this.\u00e6[n2].\u0165 + 20, this.\u00e6[n2].\u0166 + 20);
        }
        this.\u0128 = this.\u0128 + 20 + 2 * this.\u00e6[n2].\u0167;
        this.\u00e6[n2].\u016f = true;
        this.\u00e6[n2].\u0170 = 0;
        this.\u00e6[n2].\u0171 = 0;
        if (n3 > 0) {
            this.X(n3);
        }
        int n4 = 0;
        while (n4 < 20) {
            this.\u00e5[n4].q = this.\u00e6[n2].\u0165 + 20;
            this.\u00e5[n4].r = this.\u00e6[n2].\u0166 + 20;
            this.\u00e5[n4].s = (int)(Math.random() * 20.0 + 2.0) - 10;
            this.\u00e5[n4].t = (int)(Math.random() * 20.0 + 2.0) - 10;
            ++n4;
        }
    }

    public void \u00cb(int n2) {
        if (this.\u012c && this.\u0135[n2] != null) {
            this.\u0135[n2].play();
        }
    }

    public void \u00e2() {
        if (this.\u00ee.x) {
            this.\u00ee.z -= 10;
        }
        if (this.\u00f3.\u015e) {
            if (this.\u00ef.x) {
                this.\u00ef.z -= 10;
            }
            if (this.\u00f0.x) {
                this.\u00f0.z -= 10;
            }
        }
        if (this.\u00f3.\u015f) {
            if (this.\u00ef.x) {
                --this.\u00ef.y;
                if (this.\u00ef.y < 0) {
                    this.\u00ef.x = false;
                }
            }
            if (this.\u00f0.x) {
                ++this.\u00f0.y;
                if (this.\u00f0.y > 400) {
                    this.\u00f0.x = false;
                }
            }
            if (this.\u00f1.x) {
                this.\u00f1.z -= 10;
                this.\u00f1.y -= 2;
                if (this.\u00f1.y < 0) {
                    this.\u00f1.x = false;
                }
            }
            if (this.\u00f2.x) {
                this.\u00f2.z -= 10;
                this.\u00f2.y += 2;
                if (this.\u00f2.y > 400) {
                    this.\u00f2.x = false;
                }
            }
        }
        int n2 = 0;
        while (n2 < 5) {
            if (this.\u00ee.x && this.j(n2, this.\u00ee.y, this.\u00ee.z, this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, this.\u00e6[n2].\u0165 + 40, this.\u00e6[n2].\u0166 + 40)) {
                this.\u00c5(n2, 1);
            }
            if (this.\u00f3.\u015e) {
                if (this.\u00ef.x && this.j(n2, this.\u00ef.y, this.\u00ef.z, this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, this.\u00e6[n2].\u0165 + 40, this.\u00e6[n2].\u0166 + 40)) {
                    this.\u00c5(n2, 2);
                }
                if (this.\u00f0.x && this.j(n2, this.\u00f0.y, this.\u00f0.z, this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, this.\u00e6[n2].\u0165 + 40, this.\u00e6[n2].\u0166 + 40)) {
                    this.\u00c5(n2, 3);
                }
            }
            if (this.\u00f3.\u015f) {
                if (this.\u00f1.x && this.j(n2, this.\u00f1.y, this.\u00f1.z, this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, this.\u00e6[n2].\u0165 + 40, this.\u00e6[n2].\u0166 + 40)) {
                    this.\u00c5(n2, 4);
                }
                if (this.\u00f2.x && this.j(n2, this.\u00f2.y, this.\u00f2.z, this.\u00e6[n2].\u0165, this.\u00e6[n2].\u0166, this.\u00e6[n2].\u0165 + 40, this.\u00e6[n2].\u0166 + 40)) {
                    this.\u00c5(n2, 5);
                }
            }
            ++n2;
        }
        if (this.\u00ee.z < 10) {
            this.\u00ee.x = false;
        }
        if (this.\u00ef.z < 10) {
            this.\u00ef.x = false;
        }
        if (this.\u00f0.z < 10) {
            this.\u00f0.x = false;
        }
        if (this.\u00f1.z < 10) {
            this.\u00f1.x = false;
        }
        if (this.\u00f2.z < 10) {
            this.\u00f2.x = false;
        }
    }

    public void N() {
        ++this.\u0127;
        if (this.\u0127 > 1) {
            ++this.\u0126;
            this.\u0127 = 0;
        }
        int n2 = 0;
        while (n2 < 20) {
            this.\u00e4[n2].q += this.\u00e4[n2].s;
            this.\u00e4[n2].r += this.\u00e4[n2].t;
            ++n2;
        }
        if (this.\u0126 > 24) {
            this.\u0125 = false;
            --this.\u0132;
            if (this.\u0132 < 0) {
                this.\u012b = true;
                this.\u0133 = 100;
                this.\u0131 = 201;
            }
            this.b();
        }
    }

    public void \u00ca() {
        int n2 = 0;
        while (n2 < 5) {
            ++this.\u00e6[n2].\u016c;
            if (this.\u00e6[n2].\u016c > 10) {
                this.\u00e6[n2].\u016c = 0;
                ++this.\u00e6[n2].\u016b;
                if (this.\u00e6[n2].\u016b == 4) {
                    this.\u00e6[n2].\u016b = 0;
                }
            }
            ++n2;
        }
    }

    public void \u00df(int n2) {
        int n3 = 0;
        while (n3 < 20) {
            this.\u00e5[n3].q += this.\u00e5[n3].s;
            this.\u00e5[n3].r += this.\u00e5[n3].t;
            ++n3;
        }
        ++this.\u00e6[n2].\u0170;
        if (this.\u00e6[n2].\u0170 > 24) {
            this.\u00e6[n2].\u016f = false;
            this.\u00e6[n2].\u016e = true;
            ++this.\u0138;
            if (this.\u0138 == 5) {
                this.l();
                if (!this.\u00e9.\u0180) {
                    this.\u00c0();
                    return;
                }
                this.\u012d = true;
            }
        }
    }

    public void l() {
        ++this.\u0130;
        if (this.\u013a < 4) {
            if (this.\u0130 == 5) {
                ++this.\u013a;
                return;
            }
            if (this.\u0130 == 10) {
                ++this.\u013a;
                return;
            }
            if (this.\u0130 == 15) {
                ++this.\u013a;
                return;
            }
            if (this.\u0130 == 20) {
                ++this.\u013a;
                return;
            }
        } else if (this.\u013b < 210) {
            this.\u013b += 20;
        }
    }

    public void d() {
        this.\u00ec.k -= 15;
        if (this.\u00ec.l && this.\u00ec.k + this.\u00ec.n < 295) {
            this.\u00ec.l = false;
        }
        int n2 = 0;
        while (n2 < 5) {
            if (!this.\u00e6[n2].\u016f && !this.\u00e6[n2].\u016e && this.\u00ec.j > this.\u00e6[n2].\u0165 && this.\u00ec.j < this.\u00e6[n2].\u0165 + 40) {
                if (this.\u00e6[n2].\u0166 > this.\u00ec.k && this.\u00e6[n2].\u0166 + 40 < this.\u00ec.k + this.\u00ec.n) {
                    this.\u00c5(n2, 0);
                } else if (this.\u00ec.k > this.\u00e6[n2].\u0166 && this.\u00ec.k < this.\u00e6[n2].\u0166 + 40) {
                    this.\u00c5(n2, 0);
                } else if (!this.\u00ec.l && this.\u00ec.k + this.\u00ec.n > this.\u00e6[n2].\u0166 && this.\u00ec.k + this.\u00ec.n < this.\u00e6[n2].\u0166 + 40) {
                    this.\u00c5(n2, 0);
                }
            }
            ++n2;
        }
        if (this.\u00ec.k + this.\u00ec.n < 10) {
            this.\u00ec.m = false;
        }
    }

    public void \u00c4() {
        if (this.\u012e) {
            this.\u00cc();
        }
        if (!this.\u012d) {
            int n2 = 0;
            while (n2 < 5) {
                if (this.\u00e6[n2].\u016f) {
                    this.\u00df(n2);
                } else {
                    if (this.\u00e6[n2].\u0169 == 1 && this.\u00e6[n2].\u0165 > 350) {
                        this.\u00e6[n2].\u0169 = -1;
                        this.\u00e6[n2].\u0165 = 350;
                    }
                    if (this.\u00e6[n2].\u0169 == -1 && this.\u00e6[n2].\u0165 < 11) {
                        this.\u00e6[n2].\u0169 = 1;
                        this.\u00e6[n2].\u0165 = 11;
                    }
                    if (this.\u00e6[n2].\u016a == 1 && this.\u00e6[n2].\u0166 > this.\u0139) {
                        this.\u00e6[n2].\u016a = -1;
                        this.\u00e6[n2].\u0166 = this.\u0139;
                    }
                    if (this.\u00e6[n2].\u016a == -1 && this.\u00e6[n2].\u0166 < 50) {
                        this.\u00e6[n2].\u016a = 1;
                        this.\u00e6[n2].\u0166 = 50;
                    }
                    this.\u00e6[n2].\u0165 += this.\u00e6[n2].\u0167 * this.\u00e6[n2].\u0169;
                    this.\u00e6[n2].\u0166 += this.\u00e6[n2].\u0168 * this.\u00e6[n2].\u016a;
                    this.\u00dd();
                    this.\u00ca();
                }
                ++n2;
            }
        }
    }

    public void r() {
        this.\u00ed.\u017a = this.\u00ed.\u0179++;
        if (this.\u00ed.\u0178) {
            if (this.\u00ed.\u0179 > 3) {
                this.\u00ed.\u0179 = 3;
                this.\u00ed.\u0178 = false;
                if (this.\u0121 < 180) {
                    this.\u0121 = 359;
                    return;
                }
                this.\u0121 = 1;
                return;
            }
        } else {
            --this.\u00ed.\u0179;
            if (this.\u00ed.\u0179 < 0) {
                this.\u00ed.\u0177 = false;
            }
        }
    }

    public int \u00c6(int n2) {
        if (n2 > 0) {
            return n2;
        }
        return -n2;
    }

    public void \u00de() {
        this.\u00ea.\u0150 -= 12;
        if (this.\u00ea.\u0150 < 10) {
            this.\u00ea.\u014e = false;
        }
        if (this.\u00ea.\u0152 > -1) {
            if (this.\u00e6[this.\u00ea.\u0152].\u0165 + 20 > this.\u00ea.\u014f) {
                this.\u00ea.\u014f += this.\u00ea.\u0151;
            } else if (this.\u00e6[this.\u00ea.\u0152].\u0165 + 20 < this.\u00ea.\u014f) {
                this.\u00ea.\u014f -= 3;
            }
        }
        int n2 = 0;
        while (n2 < 5) {
            if (!this.\u00e6[n2].\u016e && !this.\u00e6[n2].\u016f && this.\u00ea.\u014f + 24 > this.\u00e6[n2].\u0165 && this.\u00ea.\u014f < this.\u00e6[n2].\u0165 + 40 && this.\u00ea.\u0150 + 10 > this.\u00e6[n2].\u0166 && this.\u00ea.\u0150 < this.\u00e6[n2].\u0166 + 40) {
                this.\u00c5(n2, 0);
                this.\u00ea.\u014e = false;
            }
            ++n2;
        }
    }

    public void g() {
        if (!this.\u00e9.\u0180) {
            this.\u00e9.\u0182 -= 10;
            if (this.\u00e9.\u0182 < 10) {
                this.\u00e9.\u017f = false;
                return;
            }
        } else {
            int n2 = 0;
            while (n2 < 5) {
                if (!this.\u00e6[n2].\u016f && !this.\u00e6[n2].\u016e && this.\u00e6[n2].\u0165 + 40 > this.\u00e9.\u0181 - this.\u00e9.\u0183 / 2 && this.\u00e6[n2].\u0165 < this.\u00e9.\u0181 + this.\u00e9.\u0183 / 2 && this.\u00e6[n2].\u0166 + 40 > this.\u00e9.\u0182 - this.\u00e9.\u0183 / 2 && this.\u00e6[n2].\u0166 < this.\u00e9.\u0182 + this.\u00e9.\u0183 / 2) {
                    this.\u00c5(n2, 0);
                }
                ++n2;
            }
            this.\u00e9.\u0183 += 5;
            if (this.\u00e9.\u0183 > this.\u00e9.\u0185) {
                this.\u00e9.\u017f = false;
                this.\u00e9.\u0180 = false;
                this.\u00e9.\u0186 = false;
                if (this.\u012d) {
                    this.\u012d = false;
                    this.\u00c0();
                }
            }
        }
    }

    public boolean \u00c3(int n2, int n3) {
        return this.\u00eb.v + 20 > n2 && this.\u00eb.v < n2 + 40 && this.\u00eb.w + 20 > n3 && this.\u00eb.w < n3 + 40;
    }

    public void \u00c7() {
        this.\u00cb(1);
        if (Math.random() < 0.75 || this.\u00f3.\u015e && this.\u00f3.\u015f) {
            double d2 = Math.random();
            if (d2 < 0.2) {
                this.\u00f3.\u0161 += 5;
                this.p(110, "Picked Up Cluster Bomb Ammo", 50);
                return;
            }
            if (d2 > 0.2 && d2 < 0.4) {
                this.\u00f3.\u0162 += 5;
                this.p(110, "Picked Up Heat Seeker Ammo", 50);
                return;
            }
            if (d2 > 0.4 && d2 < 0.6) {
                this.\u00f3.\u0160 += 5;
                this.p(125, "Picked Up Some Laser Ammo", 50);
                return;
            }
            if (d2 > 0.6 && d2 < 0.8) {
                this.\u00f3.\u0163 = 1000;
                this.p(110, "Shield Now At Full Strength", 50);
                return;
            }
            if (d2 > 0.8 && d2 < 0.87) {
                ++this.\u00ea.\u0151;
                this.p(100, "Picked Up Heat Seeker Upgrade : " + (this.\u00ea.\u0151 - 2), 50);
                return;
            }
            if (d2 > 0.87 && d2 < 0.94) {
                this.\u00ec.n += 40;
                this.p(100, "Picked Up Laser Weapon Upgrade : " + this.\u00ec.n / 40, 50);
                return;
            }
            this.\u00e9.\u0185 += 50;
            this.p(100, "Picked Up Cluster Bomb Upgrade : " + (this.\u00e9.\u0185 - 100) / 50, 50);
            return;
        }
        if (this.\u00f3.\u015e) {
            this.\u00f3.\u015f = true;
            this.p(100, "Picked Up Spreader Fire Upgrade", 50);
            return;
        }
        this.\u00f3.\u015e = true;
        this.p(105, "Picked Up Triple Fire Upgrade", 50);
    }

    public void p(int n2, String string, int n3) {
        this.\u00f4.\u0154 = true;
        this.\u00f4.\u0156 = n3;
        this.\u00f4.\u0155 = 0;
        this.\u00f4.\u0157 = string;
        this.\u00f4.\u0159 = n2;
        this.\u00f4.\u015a = 12;
        this.\u00f4.\u0158 = 0;
    }

    public void U() {
        this.\u00eb.w += 6;
        if (this.\u00c3(this.\u0121, 295)) {
            this.\u00c7();
            this.\u00eb.u = false;
            return;
        }
        if (this.\u00eb.w > 350) {
            this.\u00eb.u = false;
        }
    }

    public void run() {
        try {
            this.\u0137.waitForID(0);
        }
        catch (InterruptedException interruptedException) {}
        long l2 = System.currentTimeMillis();
        while (this.\u0134 != null) {
            l2 = System.currentTimeMillis();
            this.\u0141 = l2 - this.\u0140;
            try {
                if (this.\u0120.M) {
                    Thread.sleep(5L);
                } else {
                    Thread.sleep(this.\u014d);
                }
            }
            catch (InterruptedException interruptedException) {}
            this.\u0140 = System.currentTimeMillis();
            if (!this.\u014c) {
                this.\u0146 = System.currentTimeMillis();
                this.\u014c = true;
            }
            this.repaint();
        }
    }

    public void \u00d1() {
        if (this.\u012b) {
            this.i();
            return;
        }
        if (!this.\u012a) {
            this.i();
            this.\u00c4();
            this.\u00cd();
            this.\u00e2();
            this.d();
            if (this.\u00e9.\u017f) {
                this.g();
            }
            if (this.\u00ea.\u014e) {
                this.\u00de();
            }
            if (this.\u00ed.\u0177) {
                this.r();
            }
            if (this.\u00eb.u) {
                this.U();
            }
            this.\u00d5();
            if (this.\u0125) {
                this.N();
            }
            if (this.\u0123 > 0) {
                --this.\u0123;
            }
        }
    }

    public void stop() {
        this.\u0134.stop();
        this.\u0134 = null;
        this.\u0135[5].stop();
    }

    public void e(Graphics graphics) {
        int n2 = 2;
        graphics.setFont(this.\u0142);
        int n3 = 347;
        graphics.setColor(Color.white);
        graphics.drawString(String.valueOf(this.\u010e[2]) + this.\u0128, n2, n3);
        if (!this.\u012c) {
            n2 = 329;
            n3 = 12;
            graphics.drawString(this.\u010e[9], n2, n3);
        }
        n2 = 2;
        n3 = 12;
        graphics.setColor(Color.white);
        graphics.drawString(String.valueOf(this.\u010e[10]) + this.\u0130, n2, n3);
    }

    public void \u00c8(Graphics graphics) {
        graphics.setColor(Color.yellow);
        graphics.drawLine(99, 347, 99, 337);
        if (this.\u00f3.\u0160 == -1) {
            graphics.setColor(Color.lightGray);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString(String.valueOf(this.\u00f3.\u0160) + " ", 103, 347);
        graphics.drawImage(this.\u011b, 130, 337, (ImageObserver)this);
        if (this.\u00f3.\u0162 == -1) {
            graphics.setColor(Color.lightGray);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString(String.valueOf(this.\u00f3.\u0162) + " ", 138, 347);
        graphics.drawImage(this.\u011a, 167, 337, (ImageObserver)this);
        if (this.\u00f3.\u0161 == -1) {
            graphics.setColor(Color.lightGray);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString(String.valueOf(this.\u00f3.\u0161) + " ", 180, 347);
    }

    public void k(Graphics graphics) {
        this.\u0122 = 1 - this.\u0122;
        graphics.drawImage(this.\u011f, 212, 331, (ImageObserver)this);
        if (this.\u0122 == 0) {
            graphics.setColor(Color.blue);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawOval(209, 330, 21, 19);
        graphics.setColor(Color.lightGray);
        graphics.drawRect(240, 340, 100, 3);
        if (this.\u00f3.\u0163 > 0) {
            graphics.setColor(Color.white);
            graphics.drawLine(241, 341, 240 + this.\u00f3.\u0163 / 10, 341);
            graphics.drawLine(241, 342, 240 + this.\u00f3.\u0163 / 10, 342);
        }
    }

    public void o(Graphics graphics) {
        graphics.setColor(Color.red.darker());
        graphics.drawString("Current FPS : " + this.\u014b, 10, 50);
        graphics.drawString("sleepFor : " + this.\u014d, 10, 60);
    }

    public void P(Graphics graphics) {
        graphics.setFont(this.\u0142);
        this.e(graphics);
        int n2 = 347;
        int n3 = 366;
        graphics.drawImage(this.\u011f, n3, 331, (ImageObserver)this);
        graphics.drawString("x" + this.\u0132, 382, 346);
        this.\u00c8(graphics);
        this.k(graphics);
        if (this.\u0144 == 1) {
            this.o(graphics);
        }
    }

    public void V() {
        this.\u012b = false;
        this.\u0132 = 3;
        this.\u0128 = 0;
        this.\u00c0();
        int n2 = 0;
        while (n2 < 5) {
            this.\u00e6[n2].\u0172 = false;
            ++n2;
        }
        this.\u013a = 0;
        this.\u013b = 100;
        this.\u00eb.u = false;
        this.b();
        this.\u0130 = 0;
        this.\u012d = false;
    }

    public boolean keyDown(Event event, int n2) {
        if (this.\u012b && this.\u0133 == 0 && !this.\u0120.M) {
            this.V();
        } else if (this.\u012a) {
            this.\u012a = false;
        } else {
            if (n2 == 106) {
                this.\u010d[0] = true;
            }
            if (n2 == 1006) {
                this.\u010d[0] = true;
            }
            if (n2 == 108) {
                this.\u010d[1] = true;
            }
            if (n2 == 1007) {
                this.\u010d[1] = true;
            }
            if (n2 == 32) {
                this.\u010d[2] = true;
            }
            if (n2 == 112) {
                this.\u010d[3] = true;
            }
            if (n2 == 97) {
                this.\u010d[4] = true;
            }
            if (n2 == 105) {
                this.\u010d[5] = true;
            }
            if (n2 == 1004) {
                this.\u010d[5] = true;
            }
            if (n2 == 107) {
                this.\u010d[6] = true;
            }
            if (n2 == 1005) {
                this.\u010d[6] = true;
            }
            if (n2 == 100) {
                this.\u010d[7] = true;
            }
            if (n2 == 119) {
                this.\u010d[8] = true;
            }
        }
        if (n2 == 115) {
            this.w();
        } else if (n2 == 4) {
            this.\u0144 = 1 - this.\u0144;
        }
        return true;
    }

    public boolean keyUp(Event event, int n2) {
        if (n2 == 106) {
            this.\u010d[0] = false;
        }
        if (n2 == 1006) {
            this.\u010d[0] = false;
        }
        if (n2 == 108) {
            this.\u010d[1] = false;
        }
        if (n2 == 1007) {
            this.\u010d[1] = false;
        }
        if (n2 == 32) {
            this.\u010d[2] = false;
        }
        if (n2 == 112) {
            this.\u010d[3] = false;
        }
        if (n2 == 97) {
            this.\u010d[4] = false;
        }
        if (n2 == 105) {
            this.\u010d[5] = false;
        }
        if (n2 == 1004) {
            this.\u010d[5] = false;
        }
        if (n2 == 107 || n2 == 1005) {
            this.\u010d[6] = false;
            this.\u00ed.\u017b = true;
        }
        if (n2 == 119) {
            this.\u010d[8] = false;
        }
        if (n2 == 100) {
            this.\u010d[7] = false;
            if (this.\u00e9.\u017f) {
                this.\u00e9.\u0186 = true;
            }
        }
        return true;
    }

    public void u() {
        if (this.\u0121 > 9) {
            this.\u0121 -= 10;
        }
    }

    public void \u00c9() {
        if (this.\u0121 < 350) {
            this.\u0121 += 10;
        }
    }

    public void c() {
        this.\u00cb(10);
        this.\u00ec.m = true;
        this.\u00ec.l = true;
        this.\u00ec.j = this.\u0121 + 20;
        this.\u00ec.k = 295;
        --this.\u00f3.\u0160;
    }

    public void \u00c2() {
        this.\u00cb(4);
        this.\u00ee.x = true;
        this.\u00ee.y = this.\u0121 + 19;
        this.\u00ee.z = 289;
        if (this.\u00f3.\u015e) {
            this.\u00ef.x = true;
            this.\u00f0.x = true;
            this.\u00ef.z = 300;
            this.\u00f0.z = 300;
            this.\u00ef.y = this.\u0121;
            this.\u00f0.y = this.\u0121 + 39;
        }
        if (this.\u00f3.\u015f) {
            this.\u00f1.x = true;
            this.\u00f2.x = true;
            this.\u00f1.z = 305;
            this.\u00f2.z = 305;
            this.\u00f1.y = this.\u0121 - 5;
            this.\u00f2.y = this.\u0121 + 44;
        }
    }

    public void \u00d3() {
        if (this.\u00f3.\u0161 > 0) {
            this.\u00cb(8);
            this.\u00e9.\u017f = true;
            this.\u00e9.\u0180 = false;
            this.\u00e9.\u0183 = 5;
            this.\u00e9.\u0181 = this.\u0121 + 15;
            this.\u00e9.\u0182 = 290;
            this.\u00e9.\u0186 = false;
            this.\u00e9.\u0184 = 0;
            --this.\u00f3.\u0161;
        }
    }

    public int z() {
        int n2 = -1;
        int n3 = 1000;
        int n4 = 0;
        while (n4 < 5) {
            if (!this.\u00e6[n4].\u016e && !this.\u00e6[n4].\u016f && this.\u00c6(this.\u00e6[n4].\u0165 - this.\u0121) < n3) {
                n3 = this.\u00c6(this.\u00e6[n4].\u0165 - this.\u0121);
                n2 = n4;
            }
            ++n4;
        }
        return n2;
    }

    public void f() {
        this.\u00cb(7);
        this.\u00ea.\u014e = true;
        this.\u00ea.\u014f = this.\u0121 + 18;
        this.\u00ea.\u0150 = 289;
        this.\u00ea.\u0152 = this.z();
        --this.\u00f3.\u0162;
    }

    public void \u00d0() {
        this.\u00cb(9);
        this.\u00e9.\u0180 = true;
    }

    public void \u00e1() {
        this.\u00ed.\u0177 = true;
        this.\u00ed.\u0178 = true;
        this.\u00ed.\u0179 = 0;
        this.\u00ed.\u017a = 0;
        this.\u00ed.\u017b = false;
        int n2 = 0;
        while (n2 < 5) {
            if (this.\u00e6[n2].\u0172) {
                this.\u00e6[n2].\u0173 = true;
            }
            ++n2;
        }
    }

    public void \u00d5() {
        if (!this.\u0125) {
            if (this.\u010d[0]) {
                this.u();
            }
            if (this.\u010d[1]) {
                this.\u00c9();
            }
            if (!this.\u010d[5]) {
                if (this.\u010d[4] && !this.\u00ec.m && this.\u00f3.\u0160 > 0) {
                    this.c();
                }
                if (this.\u010d[2] && !this.\u00db()) {
                    this.\u00c2();
                }
                if (this.\u010d[7]) {
                    if (!this.\u00e9.\u017f) {
                        this.\u00d3();
                    } else if (!this.\u00e9.\u0180 && this.\u00e9.\u0186) {
                        this.\u00d0();
                    }
                }
                if (this.\u010d[8] && !this.\u00ea.\u014e && this.\u00f3.\u0162 > 0) {
                    this.f();
                }
            }
            if (!this.\u00ed.\u0177 && this.\u010d[6] && this.\u00ed.\u017b) {
                this.\u00e1();
            }
        }
        if (this.\u010d[3] && !this.\u012b) {
            this.\u012a = true;
        }
    }

    public void W(Graphics graphics, int n2, int n3) {
        int n4 = 200 - graphics.getFontMetrics().stringWidth(this.\u010e[n2]) / 2;
        graphics.setColor(Color.red);
        graphics.drawString(this.\u010e[n2], n4, n3);
    }

    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    public void w() {
        if (this.\u012c) {
            this.\u012c = false;
            return;
        }
        this.\u012c = true;
    }
}

