package metropolesustentavel.view;

import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Prefeito desenhado em Java2D — visual aprimorado.
 *
 * Humor muda com a popularidade:
 *   >= 60 → Feliz   (sorriso largo, bochechas rosadas, sobrancelhas levantadas)
 *   >= 30 → Neutro  (expressão séria, postura firme)
 *   <  30 → Triste  (boca curvada, suor, olhos abatidos, gravata torta)
 *
 * Animação: "respiração" suave via Timer (torso sobe/desce levemente).
 */
public class PainelPrefeito extends JPanel {

    // ── Cores ────────────────────────────────────────────────────────────────
    private static final Color PELE        = new Color(255, 213, 170);
    private static final Color PELE_ESC    = new Color(210, 165, 115);
    private static final Color PELE_BOCH   = new Color(255, 160, 130);
    private static final Color TERNO       = new Color(28, 42, 65);
    private static final Color TERNO_MED   = new Color(38, 55, 82);
    private static final Color TERNO_CLR   = new Color(55, 75, 108);
    private static final Color GRAVATA_V   = new Color(20, 140, 70);   // feliz: verde
    private static final Color GRAVATA_A   = new Color(160, 40, 40);   // neutro/triste: vermelho
    private static final Color CAMISA      = new Color(240, 245, 255);
    private static final Color CABELO      = new Color(45, 30, 12);
    private static final Color CABELO_CIN  = new Color(100, 95, 90);   // cabelo com fio branco
    private static final Color CHAPEU_C    = new Color(22, 33, 52);
    private static final Color CHAPEU_F    = new Color(15, 22, 38);
    private static final Color FAIXA_VERDE = new Color(0, 160, 90);
    private static final Color OURO        = new Color(255, 200, 40);
    private static final Color OURO_ESC    = new Color(200, 145, 10);
    private static final Color SAPATO      = new Color(20, 15, 10);

    // ── Estado ───────────────────────────────────────────────────────────────
    private double popularidade = 60;
    private double respiro      = 0;   // 0..2π — animação de respiração
    private final Timer animTimer;

    public PainelPrefeito() {
        setOpaque(false);
        setPreferredSize(new Dimension(160, 260));

        animTimer = new Timer(40, e -> {
            respiro = (respiro + 0.06) % (2 * Math.PI);
            repaint();
        });
        animTimer.start();
    }

    public void setPopularidade(double pop) {
        this.popularidade = pop;
        repaint();
    }

    private String humor() {
        if (popularidade >= 60) return "feliz";
        if (popularidade >= 30) return "neutro";
        return "triste";
    }

    // ── Pintura principal ─────────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,        RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,   RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,           RenderingHints.VALUE_RENDER_QUALITY);

        int w  = getWidth();
        int h  = getHeight();
        double s  = Math.min(w, h) / 240.0;
        double cx = w / 2.0;

        // Offset de respiração (torso e cabeça sobem/descem 1px)
        double rb = Math.sin(respiro) * 1.2 * s;

        double ty  = h * 0.04 + rb;   // topo da cabeça
        double by  = ty + 80 * s;      // topo do torso

        desenharSombra(g2, cx, h, s);
        desenharSapatos(g2, cx, by, s);
        desenharPernas(g2, cx, by, s);
        desenharCorpo(g2, cx, ty, by, s);
        desenharCabeca(g2, cx, ty, s);
        desenharChapeu(g2, cx, ty, s);
        desenharMedalha(g2, cx, by, s);
        desenharPlaquinha(g2, cx, w, h, s);

        g2.dispose();
    }

    // ── Sombra ────────────────────────────────────────────────────────────────
    private void desenharSombra(Graphics2D g2, double cx, int h, double s) {
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fill(new Ellipse2D.Double(cx - 44 * s, h - 18 * s, 88 * s, 14 * s));
    }

    // ── Sapatos ───────────────────────────────────────────────────────────────
    private void desenharSapatos(Graphics2D g2, double cx, double by, double s) {
        double py = by + 118 * s;
        g2.setColor(SAPATO);
        // Sapato esquerdo
        g2.fill(new RoundRectangle2D.Double(cx - 34 * s, py, 26 * s, 10 * s, 5, 5));
        g2.fill(new Ellipse2D.Double(cx - 40 * s, py, 20 * s, 10 * s));
        // Sapato direito
        g2.fill(new RoundRectangle2D.Double(cx + 8 * s, py, 26 * s, 10 * s, 5, 5));
        g2.fill(new Ellipse2D.Double(cx + 20 * s, py, 20 * s, 10 * s));
        // Brilho
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fill(new Ellipse2D.Double(cx - 36 * s, py + 1 * s, 8 * s, 3 * s));
        g2.fill(new Ellipse2D.Double(cx + 22 * s, py + 1 * s, 8 * s, 3 * s));
    }

    // ── Pernas ────────────────────────────────────────────────────────────────
    private void desenharPernas(Graphics2D g2, double cx, double by, double s) {
        double py = by + 90 * s;
        g2.setColor(TERNO);
        g2.fill(new RoundRectangle2D.Double(cx - 32 * s, py, 22 * s, 32 * s, 6, 6));
        g2.fill(new RoundRectangle2D.Double(cx + 10 * s, py, 22 * s, 32 * s, 6, 6));
        // Vinco calça
        g2.setColor(TERNO_MED);
        g2.setStroke(new BasicStroke((float)(0.8 * s)));
        g2.draw(new Line2D.Double(cx - 21 * s, py + 2 * s, cx - 21 * s, py + 28 * s));
        g2.draw(new Line2D.Double(cx + 21 * s, py + 2 * s, cx + 21 * s, py + 28 * s));
        g2.setStroke(new BasicStroke(1));
    }

    // ── Corpo (terno, gravata, camisa, braços) ────────────────────────────────
    private void desenharCorpo(Graphics2D g2, double cx, double ty, double by, double s) {
        double bw = 72 * s;
        double bh = 95 * s;
        String h  = humor();

        // Torso principal com gradiente simulado
        g2.setColor(TERNO_MED);
        g2.fill(new RoundRectangle2D.Double(cx - bw * 0.50, by, bw, bh, 8, 8));
        g2.setColor(TERNO);
        g2.fill(new RoundRectangle2D.Double(cx - bw * 0.44, by, bw * 0.88, bh, 6, 6));

        // Linha de luz no ombro esquerdo
        g2.setColor(TERNO_CLR);
        g2.fill(new RoundRectangle2D.Double(cx - bw * 0.50, by, bw * 0.12, bh * 0.6, 4, 4));

        // Braços
        Color corBraco = h.equals("triste") ? TERNO_MED : TERNO;
        g2.setColor(corBraco);
        // Braço esquerdo (triste = levemente caído)
        double beDrop = h.equals("triste") ? 6 * s : 0;
        g2.fill(new RoundRectangle2D.Double(cx - bw * 0.50 - 14 * s, by + 4 * s + beDrop,
                                             15 * s, 55 * s, 7, 7));
        g2.fill(new RoundRectangle2D.Double(cx + bw * 0.50 - 1 * s,  by + 4 * s,
                                             15 * s, 55 * s, 7, 7));

        // Mãos
        g2.setColor(PELE);
        g2.fill(new Ellipse2D.Double(cx - bw * 0.50 - 12 * s, by + 54 * s + beDrop, 14 * s, 11 * s));
        g2.fill(new Ellipse2D.Double(cx + bw * 0.50 - 1 * s,  by + 54 * s,          14 * s, 11 * s));

        // Dedos esboçados
        g2.setColor(PELE_ESC);
        g2.setStroke(new BasicStroke((float)(0.7 * s)));
        for (int i = 0; i < 3; i++) {
            g2.draw(new Line2D.Double(cx - bw * 0.50 - 10 * s + i * 4 * s, by + 55 * s + beDrop,
                                      cx - bw * 0.50 - 10 * s + i * 4 * s, by + 63 * s + beDrop));
            g2.draw(new Line2D.Double(cx + bw * 0.50 + 2 * s + i * 4 * s, by + 55 * s,
                                      cx + bw * 0.50 + 2 * s + i * 4 * s, by + 63 * s));
        }
        g2.setStroke(new BasicStroke(1));

        // Pescoço
        g2.setColor(PELE);
        g2.fill(new RoundRectangle2D.Double(cx - 10 * s, ty + 76 * s, 20 * s, 14 * s, 5, 5));

        // Lapelas
        g2.setColor(TERNO_MED);
        g2.fillPolygon(
            new int[]{(int) cx, (int)(cx - bw * 0.30), (int)(cx - bw * 0.10)},
            new int[]{(int) by, (int)(by + 28 * s),    (int)(by + 32 * s)}, 3);
        g2.fillPolygon(
            new int[]{(int) cx, (int)(cx + bw * 0.30), (int)(cx + bw * 0.10)},
            new int[]{(int) by, (int)(by + 28 * s),    (int)(by + 32 * s)}, 3);

        // Camisa
        g2.setColor(CAMISA);
        g2.fillPolygon(
            new int[]{(int) cx, (int)(cx - bw * 0.10), (int)(cx + bw * 0.10)},
            new int[]{(int) by, (int)(by + 52 * s),    (int)(by + 52 * s)}, 3);

        // Botões da camisa
        g2.setColor(new Color(200, 210, 230));
        for (int i = 0; i < 4; i++)
            g2.fill(new Ellipse2D.Double(cx - 2.5 * s, by + (35 + i * 10) * s, 5 * s, 5 * s));

        // Gravata — verde se feliz, vermelha se não
        Color corGravata = h.equals("feliz") ? GRAVATA_V : GRAVATA_A;
        // Gravata torta se triste
        double gravX = h.equals("triste") ? cx + 3 * s : cx;
        int[] gx = {(int) gravX, (int)(gravX - 6*s), (int)(gravX - 7*s),
                    (int) gravX,  (int)(gravX + 7*s),  (int)(gravX + 6*s)};
        int[] gy = {(int)(by + 2*s),  (int)(by + 18*s), (int)(by + 32*s),
                    (int)(by + 46*s), (int)(by + 32*s), (int)(by + 18*s)};
        g2.setColor(corGravata.darker());
        int[] gxd = gx.clone(); for (int i=0;i<gxd.length;i++) gxd[i]+=1;
        g2.fillPolygon(gxd, gy, 6);
        g2.setColor(corGravata);
        g2.fillPolygon(gx, gy, 6);
        // Nó da gravata
        g2.setColor(corGravata.brighter());
        g2.fill(new RoundRectangle2D.Double(gravX - 5*s, by, 10*s, 7*s, 3, 3));
        // Detalhe listrado
        g2.setColor(new Color(255, 255, 255, 40));
        g2.setStroke(new BasicStroke((float)(1.2*s)));
        g2.draw(new Line2D.Double(gravX - 2*s, by + 8*s, gravX - 2*s, by + 30*s));
        g2.setStroke(new BasicStroke(1));

        // Bolso do paletó
        g2.setColor(TERNO_MED);
        g2.fill(new RoundRectangle2D.Double(cx - bw*0.40, by + 48*s, 22*s, 15*s, 3, 3));
        // Lenço no bolso
        g2.setColor(h.equals("feliz") ? new Color(240,240,255) : new Color(200,200,220));
        g2.fill(new RoundRectangle2D.Double(cx - bw*0.38, by + 46*s, 16*s, 8*s, 2, 2));

        // Abotoaduras
        g2.setColor(OURO);
        g2.fill(new Ellipse2D.Double(cx - bw*0.50 - 7*s, by + 52*s + beDrop, 5*s, 5*s));
        g2.fill(new Ellipse2D.Double(cx + bw*0.50 + 2*s,  by + 52*s,          5*s, 5*s));

        // Crachá dourado
        g2.setColor(OURO);
        g2.fill(new RoundRectangle2D.Double(cx + bw*0.10, by + 10*s, 22*s, 15*s, 4, 4));
        g2.setColor(OURO_ESC);
        g2.setFont(new Font("Segoe UI", Font.BOLD, Math.max(5, (int)(5*s))));
        g2.drawString("PREF.", (int)(cx + bw*0.11 + 1*s), (int)(by + 19*s));
        g2.setColor(new Color(255,255,255,120));
        g2.fill(new RoundRectangle2D.Double(cx + bw*0.11, by + 11*s, 8*s, 3*s, 2, 2));
    }

    // ── Cabeça ────────────────────────────────────────────────────────────────
    private void desenharCabeca(Graphics2D g2, double cx, double ty, double s) {
        double hw = 58 * s;
        double hh = 68 * s;
        double hx = cx - hw / 2;
        String h  = humor();

        // Pescoço sombra
        g2.setColor(PELE_ESC);
        g2.fill(new RoundRectangle2D.Double(cx - 8*s, ty + 74*s, 16*s, 8*s, 4, 4));

        // Orelhas
        g2.setColor(PELE);
        g2.fill(new Ellipse2D.Double(hx - 8*s,  ty + hh*0.32, 12*s, 15*s));
        g2.fill(new Ellipse2D.Double(hx + hw - 4*s, ty + hh*0.32, 12*s, 15*s));
        g2.setColor(PELE_ESC);
        g2.fill(new Ellipse2D.Double(hx - 5*s,  ty + hh*0.38, 6*s, 8*s));
        g2.fill(new Ellipse2D.Double(hx + hw - 1*s, ty + hh*0.38, 6*s, 8*s));

        // Cabeça principal
        g2.setColor(PELE);
        g2.fill(new Ellipse2D.Double(hx, ty + 12*s, hw, hh));

        // Sombra lateral suave
        g2.setColor(new Color(190, 145, 100, 55));
        g2.fill(new Ellipse2D.Double(hx + hw*0.55, ty + hh*0.20 + 12*s, hw*0.40, hh*0.70));

        // Cabelo base
        g2.setColor(CABELO);
        g2.fill(new Arc2D.Double(hx - 2*s, ty + 10*s, hw + 4*s, hh*0.52, 0, 180, Arc2D.PIE));
        // Laterais do cabelo
        g2.fill(new Ellipse2D.Double(hx - 3*s, ty + 14*s, 12*s, 22*s));
        g2.fill(new Ellipse2D.Double(hx + hw - 9*s, ty + 14*s, 12*s, 22*s));
        // Fio branco (envelhecido)
        g2.setColor(CABELO_CIN);
        g2.setStroke(new BasicStroke((float)(1.2*s)));
        g2.draw(new QuadCurve2D.Double(hx + 10*s, ty + 12*s, hx + hw*0.4, ty + 18*s, hx + hw*0.6, ty + 14*s));
        g2.setStroke(new BasicStroke(1));

        // ── Sobrancelhas ──
        g2.setColor(CABELO);
        g2.setStroke(new BasicStroke((float)(2.5*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        double sobY = ty + hh*0.36 + 12*s;
        switch (h) {
            case "feliz" -> {
                g2.draw(new QuadCurve2D.Double(cx-21*s, sobY+1*s, cx-14*s, sobY-6*s, cx-8*s,  sobY-2*s));
                g2.draw(new QuadCurve2D.Double(cx+8*s,  sobY-2*s, cx+14*s, sobY-6*s, cx+21*s, sobY+1*s));
            }
            case "neutro" -> {
                g2.draw(new Line2D.Double(cx-21*s, sobY, cx-8*s, sobY));
                g2.draw(new Line2D.Double(cx+8*s,  sobY, cx+21*s, sobY));
            }
            default -> {
                // Triste: sobrancelha franzida
                g2.draw(new QuadCurve2D.Double(cx-21*s, sobY-4*s, cx-14*s, sobY+4*s, cx-8*s, sobY+2*s));
                g2.draw(new QuadCurve2D.Double(cx+8*s,  sobY+2*s, cx+14*s, sobY+4*s, cx+21*s, sobY-4*s));
                // Rugas entre sobrancelhas
                g2.setStroke(new BasicStroke((float)(1.2*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(PELE_ESC);
                g2.draw(new Line2D.Double(cx - 3*s, sobY - 2*s, cx - 1*s, sobY + 4*s));
                g2.draw(new Line2D.Double(cx + 1*s, sobY - 2*s, cx + 3*s, sobY + 4*s));
            }
        }

        // ── Olhos ──
        double oy  = ty + hh*0.44 + 12*s;
        double orx = 6.0 * s;
        double ory = h.equals("feliz") ? 5.0*s : 6.0*s;

        // Pálpebra superior (triste = mais fechada)
        if (h.equals("triste")) {
            g2.setColor(PELE);
            g2.fill(new Ellipse2D.Double(cx - 23*s, oy - ory + 2*s, orx*2.2, ory));
            g2.fill(new Ellipse2D.Double(cx + 10*s, oy - ory + 2*s, orx*2.2, ory));
        }

        // Esclera
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(cx - 23*s, oy - ory, orx*2.2, ory*2));
        g2.fill(new Ellipse2D.Double(cx + 10*s, oy - ory, orx*2.2, ory*2));

        // Íris
        Color corIris = h.equals("feliz") ? new Color(60, 100, 200) : new Color(40, 70, 140);
        g2.setColor(corIris);
        g2.fill(new Ellipse2D.Double(cx - 20*s, oy - ory*0.7, orx*1.2, ory*1.2));
        g2.fill(new Ellipse2D.Double(cx + 12*s, oy - ory*0.7, orx*1.2, ory*1.2));

        // Pupila
        g2.setColor(new Color(15, 15, 15));
        g2.fill(new Ellipse2D.Double(cx - 18.5*s, oy - ory*0.5, orx*0.8, ory*0.8));
        g2.fill(new Ellipse2D.Double(cx + 13.5*s, oy - ory*0.5, orx*0.8, ory*0.8));

        // Brilho no olho
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(cx - 18*s, oy - ory*0.45, 2.5*s, 2.5*s));
        g2.fill(new Ellipse2D.Double(cx + 14*s, oy - ory*0.45, 2.5*s, 2.5*s));

        // Contorno olho
        g2.setColor(PELE_ESC);
        g2.setStroke(new BasicStroke((float)(0.8*s)));
        g2.draw(new Ellipse2D.Double(cx - 23*s, oy - ory, orx*2.2, ory*2));
        g2.draw(new Ellipse2D.Double(cx + 10*s, oy - ory, orx*2.2, ory*2));
        g2.setStroke(new BasicStroke(1));

        // Cílios superiores
        g2.setColor(CABELO);
        g2.setStroke(new BasicStroke((float)(0.8*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < 4; i++) {
            double ex = cx - 22*s + i * 4*s;
            g2.draw(new Line2D.Double(ex, oy - ory, ex - 1*s, oy - ory - 3*s));
            g2.draw(new Line2D.Double(ex + 23*s, oy - ory, ex + 24*s, oy - ory - 3*s));
        }
        g2.setStroke(new BasicStroke(1));

        // ── Nariz ──
        g2.setColor(PELE_ESC);
        g2.setStroke(new BasicStroke((float)(1.5*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        double nzy = ty + hh*0.58 + 12*s;
        g2.draw(new CubicCurve2D.Double(cx - 4*s, nzy - 8*s, cx - 8*s, nzy - 2*s,
                                         cx - 6*s, nzy + 2*s, cx,       nzy + 4*s));
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(PELE_ESC.getRed(), PELE_ESC.getGreen(), PELE_ESC.getBlue(), 120));
        g2.fill(new Ellipse2D.Double(cx - 8*s, nzy, 7*s, 5*s));
        g2.fill(new Ellipse2D.Double(cx + 1*s, nzy, 7*s, 5*s));

        // ── Boca ──
        g2.setStroke(new BasicStroke((float)(2.5*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        double by2 = ty + hh*0.74 + 12*s;
        switch (h) {
            case "feliz" -> {
                // Sorriso largo
                g2.setColor(PELE_ESC);
                g2.fill(new Arc2D.Double(cx-17*s, by2-3*s, 34*s, 20*s, 200, 140, Arc2D.CHORD));
                g2.setColor(new Color(200, 60, 60));
                g2.fill(new Arc2D.Double(cx-15*s, by2-1*s, 30*s, 16*s, 200, 140, Arc2D.CHORD));
                // Dentes
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(cx-11*s, by2+0.5*s, 22*s, 7*s, 3, 3));
                // Linha dentes
                g2.setColor(new Color(200,200,210));
                g2.setStroke(new BasicStroke((float)(0.7*s)));
                for (int i = -1; i <= 1; i++)
                    g2.draw(new Line2D.Double(cx + i*7*s, by2+1*s, cx + i*7*s, by2+7*s));
                g2.setStroke(new BasicStroke(1));
                // Bochechas rosadas
                g2.setColor(new Color(PELE_BOCH.getRed(), PELE_BOCH.getGreen(), PELE_BOCH.getBlue(), 90));
                g2.fill(new Ellipse2D.Double(hx + 2*s,       by2 - 6*s, 16*s, 9*s));
                g2.fill(new Ellipse2D.Double(hx + hw - 18*s, by2 - 6*s, 16*s, 9*s));
            }
            case "neutro" -> {
                g2.setColor(PELE_ESC);
                g2.draw(new Line2D.Double(cx - 11*s, by2 + 2*s, cx + 11*s, by2 + 2*s));
                // Canto da boca levemente curvado
                g2.setStroke(new BasicStroke((float)(1.5*s), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(new QuadCurve2D.Double(cx-11*s, by2+2*s, cx-13*s, by2+5*s, cx-11*s, by2+7*s));
                g2.draw(new QuadCurve2D.Double(cx+11*s, by2+2*s, cx+13*s, by2+5*s, cx+11*s, by2+7*s));
            }
            default -> {
                // Boca triste
                g2.setColor(PELE_ESC);
                g2.draw(new Arc2D.Double(cx-14*s, by2+2*s, 28*s, 16*s, 15, 150, Arc2D.OPEN));
                // Suor
                g2.setColor(new Color(120, 190, 255, 200));
                g2.fill(new Ellipse2D.Double(hx + hw - 6*s, ty + hh*0.42 + 12*s, 5*s, 9*s));
                g2.fill(new Ellipse2D.Double(hx + hw - 3*s, ty + hh*0.55 + 12*s, 4*s, 7*s));
                // Lágrima
                g2.setColor(new Color(100, 170, 255, 210));
                g2.fill(new Ellipse2D.Double(cx - 23*s, oy + 5*s, 5*s, 9*s));
            }
        }

        // Contorno cabeça suave
        g2.setColor(new Color(PELE_ESC.getRed(), PELE_ESC.getGreen(), PELE_ESC.getBlue(), 80));
        g2.setStroke(new BasicStroke((float)(1.2*s)));
        g2.draw(new Ellipse2D.Double(hx, ty + 12*s, hw, hh));
        g2.setStroke(new BasicStroke(1));
    }

    // ── Chapéu de cerimônia ───────────────────────────────────────────────────
    private void desenharChapeu(Graphics2D g2, double cx, double ty, double s) {
        // Aba
        g2.setColor(CHAPEU_F);
        g2.fill(new RoundRectangle2D.Double(cx - 40*s, ty + 8*s, 80*s, 10*s, 5, 5));

        // Corpo do chapéu
        g2.setColor(CHAPEU_C);
        g2.fill(new RoundRectangle2D.Double(cx - 26*s, ty - 30*s, 52*s, 42*s, 6, 6));

        // Faixa dourada
        g2.setColor(OURO);
        g2.fill(new RoundRectangle2D.Double(cx - 26*s, ty + 2*s, 52*s, 8*s, 3, 3));

        // Emblema ♻️ na faixa
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, Math.max(8, (int)(10*s))));
        g2.drawString("♻", (int)(cx - 7*s), (int)(ty + 9*s));

        // Brilho no chapéu
        g2.setColor(new Color(255, 255, 255, 25));
        g2.fill(new RoundRectangle2D.Double(cx - 22*s, ty - 28*s, 18*s, 36*s, 4, 4));

        // Fio decorativo
        g2.setColor(OURO_ESC);
        g2.setStroke(new BasicStroke((float)(1.0*s)));
        g2.draw(new RoundRectangle2D.Double(cx - 26*s, ty - 30*s, 52*s, 42*s, 6, 6));
        g2.setStroke(new BasicStroke(1));
    }

    // ── Medalha no peito ──────────────────────────────────────────────────────
    private void desenharMedalha(Graphics2D g2, double cx, double by, double s) {
        if (popularidade < 30) return; // sem medalha se impopular
        // Fita
        g2.setColor(new Color(0, 100, 200));
        g2.fill(new RoundRectangle2D.Double(cx - bw(s)*0.44 + 2*s, by + 62*s, 8*s, 16*s, 2, 2));
        // Disco dourado
        g2.setColor(OURO);
        g2.fill(new Ellipse2D.Double(cx - bw(s)*0.44 + 0.5*s, by + 74*s, 11*s, 11*s));
        g2.setColor(OURO_ESC);
        g2.setFont(new Font("Segoe UI", Font.BOLD, Math.max(5, (int)(5*s))));
        g2.drawString("★", (int)(cx - bw(s)*0.44 + 2.5*s), (int)(by + 82*s));
    }

    private double bw(double s) { return 72 * s; }

    // ── Plaquinha de humor ────────────────────────────────────────────────────
    private void desenharPlaquinha(Graphics2D g2, double cx, int w, int h, double s) {
        String humor = humor();
        Color cor;
        String emoji;
        String texto;
        switch (humor) {
            case "feliz"  -> { cor = Constantes.COR_VERDE;    emoji = "😄"; texto = " Empolgado!";   }
            case "neutro" -> { cor = Constantes.COR_AMARELO;  emoji = "😐"; texto = " Estável";      }
            default       -> { cor = Constantes.COR_VERMELHO; emoji = "😰"; texto = " Em crise!";    }
        }

        double pw = 90 * s;
        double px = cx - pw / 2;
        double py = h - 28 * s;

        // Fundo
        g2.setColor(new Color(10, 14, 22, 220));
        g2.fill(new RoundRectangle2D.Double(px, py, pw, 22*s, 10, 10));
        // Borda colorida
        g2.setColor(cor);
        g2.setStroke(new BasicStroke((float)(1.2*s)));
        g2.draw(new RoundRectangle2D.Double(px, py, pw, 22*s, 10, 10));
        g2.setStroke(new BasicStroke(1));

        // Emoji + texto
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, Math.max(9, (int)(9*s))));
        g2.setColor(cor);
        String full = emoji + texto;
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(full);
        g2.drawString(full, (int)(cx - tw / 2.0), (int)(py + 14*s));
    }
}
