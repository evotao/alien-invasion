/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  a
 *  c
 *  i
 */
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public final class a
extends Frame {
    final int A = 24;
    final int B = 200;
    int C;
    int D;
    int E;
    int F;
    int G;
    int H;
    int I;
    Image J;
    boolean K = true;
    AudioClip L;
    boolean M = false;
    boolean N = true;
    String O = new String("JAVAGAMEPLAY.COM");
    int[] P;
    int[] Q;
    int[] R;
    Color[] S;
    boolean T;
    Font U;
    Image[] V;
    int W;
    int X;
    Font Y;
    float Z = 10.0f;
    int a;
    int b;
    float[] c;
    float d;
    int e;
    i[] f;
    i[] g;
    i[] h;
    public c[] i;

    a(int n2, int n3, int n4, int n5) {
        this.C = n2;
        this.D = n3;
        this.E = n4;
        this.F = n5;
        this.M = true;
        this.N = true;
        this.I = n2 / 2;
        this.U = this.getFont();
        this.Y = new Font("TimesRoman", 1, 16);
        this.P = new int[this.O.length()];
        this.N = true;
        this.Z = 10.0f;
        this.b = 0;
        this.c = new float[16];
        this.d = 0.08f;
        this.Q = new int[16];
        this.R = new int[16];
        int n6 = 0;
        while (n6 < 16) {
            this.c[n6] = (float)(5.0 + (double)n6 * 1.5);
            this.Q[n6] = 176;
            this.R[n6] = -1;
            ++n6;
        }
        this.T = true;
        this.S = new Color[25];
        n6 = 0;
        while (n6 < 25) {
            this.S[n6] = new Color(n6 * 10, n6 * 10, n6 * 10);
            ++n6;
        }
    }

    public void D(Graphics graphics, Color color) {
        double d2 = Math.random();
        if (d2 < 0.25) {
            graphics.setColor(color);
            return;
        }
        if (d2 < 0.5) {
            graphics.setColor(color.darker());
            return;
        }
        if (d2 < 0.75) {
            graphics.setColor(Color.black);
            return;
        }
        graphics.setColor(color.brighter());
    }

    public void H(int n2, float f2, float f3, int n3, int n4, int n5, int n6, float f4, float f5, Graphics graphics, int n7) {
        int n8 = 400;
        int n9 = 200;
        int n10 = 175;
        int n11 = 0;
        while (n11 < 24) {
            float f6 = this.f[n11].\u015d;
            float f7 = this.f[n11].\u015b;
            this.h[n11].\u015d = f6 * f2 - this.f[n11].\u015b * f3;
            this.h[n11].\u015b = f6 * f3 + f7 * f2;
            this.h[n11].\u015c = this.f[n11].\u015c;
            float f8 = this.h[n11].\u015c * f3 + this.h[n11].\u015d * f2;
            this.h[n11].\u015c = this.h[n11].\u015c * f2 - this.h[n11].\u015d * f3;
            this.h[n11].\u015d = f8;
            this.h[n11].\u015b = this.h[n11].\u015b;
            this.g[n11].\u015b = this.h[n11].\u015b + (float)n3;
            this.g[n11].\u015c = this.h[n11].\u015c + (float)n4;
            f6 = this.g[n11].\u015d = this.h[n11].\u015d + (float)n5;
            f7 = this.g[n11].\u015b;
            this.g[n11].\u015d = f6 * f4 - this.g[n11].\u015b * f5;
            this.g[n11].\u015b = f6 * f5 + f7 * f4;
            this.g[n11].\u015c = this.g[n11].\u015c;
            float f9 = (float)n8 / ((float)n8 - this.g[n11].\u015d);
            this.i[n11].o = (int)(f9 * this.g[n11].\u015b);
            this.i[n11].p = (int)(f9 * this.g[n11].\u015c);
            graphics.setColor(this.S[n7]);
            graphics.drawLine(this.i[n11].o + n9, this.i[n11].p + n10, this.i[n11].o + 1 + n9, this.i[n11].p + 1 + n10);
            this.i[n11].o = (int)((double)this.i[n11].o * 1.75);
            this.i[n11].p = (int)((double)this.i[n11].p * 1.75);
            graphics.setColor(this.S[Math.max(0, n7 - 2)]);
            graphics.drawLine(this.i[n11].o + n9, this.i[n11].p + n10, this.i[n11].o + 1 + n9, this.i[n11].p + 1 + n10);
            ++n11;
        }
    }

    public void F(Graphics graphics, Color color) {
        graphics.setColor(color);
        int n2 = 0;
        while (n2 < 24) {
            graphics.drawLine(this.i[n2].o, this.i[n2].p, this.i[n2].o + 1, this.i[n2].p + 1);
            ++n2;
        }
    }

    public Image A(int[] nArray, int n2, int n3, int n4, int n5) {
        int n6 = n5 - n4;
        int[] nArray2 = new int[n6 * n3];
        int n7 = 0;
        int n8 = n4;
        int n9 = 0;
        while (n9 < n3) {
            int n10 = 0;
            while (n10 < n6) {
                nArray2[n7] = nArray[n8];
                ++n7;
                ++n8;
                ++n10;
            }
            n8 += n2 - n5 + n4;
            ++n9;
        }
        Image image = this.createImage(new MemoryImageSource(n6, n3, nArray2, 0, n6));
        return image;
    }

    public void C(Image image, ImageObserver imageObserver) {
        this.V = new Image[15];
        int n2 = image.getWidth(imageObserver);
        int n3 = image.getHeight(imageObserver);
        int[] nArray = new int[n2 * n3];
        int[] nArray2 = new int[n2 * n3];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n3, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {}
        this.V[0] = this.A(nArray, n2, n3, 0, 2);
        this.V[1] = this.A(nArray, n2, n3, 23, 47);
        this.V[2] = this.A(nArray, n2, n3, 47, 71);
        this.V[3] = this.A(nArray, n2, n3, 71, 94);
        this.V[4] = this.A(nArray, n2, n3, 94, 118);
        this.V[5] = this.A(nArray, n2, n3, 118, 142);
        this.V[6] = this.A(nArray, n2, n3, 142, 165);
        this.V[7] = this.A(nArray, n2, n3, 165, 189);
        this.V[8] = this.A(nArray, n2, n3, 189, 213);
        this.V[9] = this.A(nArray, n2, n3, 213, 236);
        this.V[10] = this.A(nArray, n2, n3, 236, 260);
        this.V[11] = this.A(nArray, n2, n3, 260, 284);
        this.V[12] = this.A(nArray, n2, n3, 284, 307);
        this.V[13] = this.A(nArray, n2, n3, 307, 331);
        this.T = true;
        this.S = new Color[25];
        int n4 = 0;
        while (n4 < 25) {
            this.S[n4] = new Color(n4 * 10, n4 * 10, n4 * 10);
            ++n4;
        }
    }

    public void B() {
        int n2 = 0;
        while (n2 < 16) {
            int n3 = this.P[n2] + 5;
            ++n2;
        }
        this.f = new i[24];
        this.g = new i[24];
        this.h = new i[24];
        this.i = new c[24];
        int n4 = 0;
        while (n4 < 24) {
            this.f[n4] = new i();
            this.g[n4] = new i();
            this.h[n4] = new i();
            this.i[n4] = new c();
            ++n4;
        }
        this.f[0].\u015b = -25.0f;
        this.f[0].\u015c = -25.0f;
        this.f[0].\u015d = 75.0f;
        this.f[1].\u015b = -25.0f;
        this.f[1].\u015c = 25.0f;
        this.f[1].\u015d = 75.0f;
        this.f[2].\u015b = 25.0f;
        this.f[2].\u015c = -25.0f;
        this.f[2].\u015d = 75.0f;
        this.f[3].\u015b = 25.0f;
        this.f[3].\u015c = 25.0f;
        this.f[3].\u015d = 75.0f;
        this.f[4].\u015b = -25.0f;
        this.f[4].\u015c = -25.0f;
        this.f[4].\u015d = -75.0f;
        this.f[5].\u015b = -25.0f;
        this.f[5].\u015c = 25.0f;
        this.f[5].\u015d = -75.0f;
        this.f[6].\u015b = 25.0f;
        this.f[6].\u015c = -25.0f;
        this.f[6].\u015d = -75.0f;
        this.f[7].\u015b = 25.0f;
        this.f[7].\u015c = 25.0f;
        this.f[7].\u015d = -75.0f;
        this.f[8].\u015b = -25.0f;
        this.f[8].\u015c = -75.0f;
        this.f[8].\u015d = 25.0f;
        this.f[9].\u015b = -25.0f;
        this.f[9].\u015c = 75.0f;
        this.f[9].\u015d = 25.0f;
        this.f[10].\u015b = 25.0f;
        this.f[10].\u015c = -75.0f;
        this.f[10].\u015d = 25.0f;
        this.f[11].\u015b = 25.0f;
        this.f[11].\u015c = 75.0f;
        this.f[11].\u015d = 25.0f;
        this.f[12].\u015b = -25.0f;
        this.f[12].\u015c = -75.0f;
        this.f[12].\u015d = -25.0f;
        this.f[13].\u015b = -25.0f;
        this.f[13].\u015c = 75.0f;
        this.f[13].\u015d = -25.0f;
        this.f[14].\u015b = 25.0f;
        this.f[14].\u015c = -75.0f;
        this.f[14].\u015d = -25.0f;
        this.f[15].\u015b = 25.0f;
        this.f[15].\u015c = 75.0f;
        this.f[15].\u015d = -25.0f;
        this.f[16].\u015b = -75.0f;
        this.f[16].\u015c = -25.0f;
        this.f[16].\u015d = 25.0f;
        this.f[17].\u015b = -75.0f;
        this.f[17].\u015c = 25.0f;
        this.f[17].\u015d = 25.0f;
        this.f[18].\u015b = 75.0f;
        this.f[18].\u015c = -25.0f;
        this.f[18].\u015d = 25.0f;
        this.f[19].\u015b = 75.0f;
        this.f[19].\u015c = 25.0f;
        this.f[19].\u015d = 25.0f;
        this.f[20].\u015b = -75.0f;
        this.f[20].\u015c = -25.0f;
        this.f[20].\u015d = -25.0f;
        this.f[21].\u015b = -75.0f;
        this.f[21].\u015c = 25.0f;
        this.f[21].\u015d = -25.0f;
        this.f[22].\u015b = 75.0f;
        this.f[22].\u015c = -25.0f;
        this.f[22].\u015d = -25.0f;
        this.f[23].\u015b = 75.0f;
        this.f[23].\u015c = 25.0f;
        this.f[23].\u015d = -25.0f;
    }

    public int[] L(Graphics graphics, Font font, int[] nArray, String string) {
        graphics.setFont(font);
        int n2 = graphics.getFontMetrics().stringWidth(" ");
        String string2 = new String("J A V A G A M E P L A Y . C O M");
        int n3 = this.C / 2 - graphics.getFontMetrics().stringWidth(string2) / 2;
        int n4 = 0;
        while (n4 < 16) {
            nArray[n4] = n3;
            n3 += graphics.getFontMetrics().stringWidth(String.valueOf(string.charAt(n4)));
            n3 += n2;
            ++n4;
        }
        this.N = false;
        return nArray;
    }

    public void G(Graphics graphics, Font font, Color color, Color color2, Color color3, String string, int n2, int n3) {
        if (n3 > string.length()) {
            n3 = string.length();
        }
        graphics.setFont(font);
        n2 += graphics.getFontMetrics().getHeight() / 4;
        int n4 = 0;
        while (n4 < n3) {
            if (n4 < 4) {
                graphics.setColor(color3);
            } else if (n4 > 11) {
                graphics.setColor(color3);
            } else {
                graphics.setColor(color);
            }
            graphics.drawString(String.valueOf(string.charAt(n4)), this.P[n4], n2);
            ++n4;
        }
    }

    public void E(Graphics graphics, Font font, Color color, Color color2, String string, int n2) {
        String string2 = new String("J A V A G A M E P L A Y . C O M");
        graphics.setFont(font);
        int n3 = graphics.getFontMetrics().stringWidth(" ");
        n2 += graphics.getFontMetrics().getHeight();
        int n4 = this.C / 2 - graphics.getFontMetrics().stringWidth(string2) / 2;
        int n5 = 0;
        while (n5 < 16) {
            if (n5 < 4 || n5 > 11) {
                graphics.setColor(color2);
            } else {
                graphics.setColor(color);
            }
            graphics.drawString(String.valueOf(string.charAt(n5)), n4, n2);
            n4 += graphics.getFontMetrics().stringWidth(String.valueOf(string.charAt(n5)));
            n4 += n3;
            ++n5;
        }
    }

    public void K(Graphics graphics, Font font, Color color, String string, int n2) {
        string = new String("JAVAGAMEPLAY.COM");
        String string2 = new String("J A V A G A M E P L A Y . C O M");
        int n3 = this.C / 2 - graphics.getFontMetrics().stringWidth(string2) / 2;
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(string2, n3, n2);
        graphics.drawLine(this.C / 2, 0, this.C / 2, this.D);
        int n4 = graphics.getFontMetrics().stringWidth(" ");
        int n5 = string.length() / 2;
        int n6 = 0;
        int n7 = string.length() / 2 - 1;
        int n8 = this.C / 2;
        int n9 = this.C / 2;
        n9 = n8 -= n4 / 2;
        int n10 = 0;
        while (n10 < n5) {
            graphics.drawString(String.valueOf(string.charAt(n7 - n6)), n8 -= graphics.getFontMetrics().stringWidth(String.valueOf(string.charAt(n7 - n6))), n2 + 30);
            graphics.drawString(String.valueOf(string.charAt(n7 + n6 + 1)), n9 += graphics.getFontMetrics().stringWidth(String.valueOf(string.charAt(n7 + n6))), n2 + 30);
            ++n6;
            n8 -= n4;
            n9 += n4;
            ++n10;
        }
        n8 = this.C / 2 - graphics.getFontMetrics().stringWidth(string2) / 2;
        n10 = 0;
        while (n10 < 16) {
            if (n10 < 4 || n10 > 11) {
                graphics.setColor(Color.white.darker());
            } else {
                graphics.setColor(Color.white);
            }
            graphics.drawString(String.valueOf(string.charAt(n10)), n8, n2 + 60);
            n8 += graphics.getFontMetrics().stringWidth(String.valueOf(string.charAt(n10)));
            n8 += n4;
            ++n10;
        }
    }

    public void M(Graphics graphics, int n2, int n3) {
        ++this.b;
        this.H(this.b, (float)Math.cos((double)this.b * 0.0174444), (float)Math.sin((double)this.b * 0.0174444), 0, 0, -100, 0, 1.0f, 0.0f, graphics, n2);
    }

    public void J(Graphics graphics) {
        graphics.setFont(this.U);
        this.M = false;
    }

    public void I(Graphics graphics, ImageObserver imageObserver) {
        if (this.N) {
            this.P = this.L(graphics, this.Y, this.P, "JAVAGAMEPLAY.COM");
            this.B();
            return;
        }
        if (this.T) {
            ++this.a;
            if (this.a == 245) {
                this.T = false;
            }
        } else {
            --this.a;
            if (this.a == -25) {
                this.J(graphics);
            }
        }
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, this.C, this.D);
        if (this.a > 4) {
            this.G(graphics, this.Y, this.S[Math.round(this.a / 10)], Color.white.darker(), this.S[Math.max(Math.round(this.a / 10) - 6, 0)], "JAVAGAMEPLAY.COM", 175, 16);
        }
        this.M(graphics, Math.max(Math.round(this.a / 10) - 4, 0), Math.max(Math.round(this.a / 10) - 6, 0));
    }
}

