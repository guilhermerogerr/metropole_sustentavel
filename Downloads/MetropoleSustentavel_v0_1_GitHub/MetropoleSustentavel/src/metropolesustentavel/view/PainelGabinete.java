package metropolesustentavel.view;

import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Painel que desenha o gabinete do prefeito:
 * fundo de escritório escuro, janela com cidade ao fundo,
 * mesa de madeira, bandeira, luminária e o prefeito centralizado.
 */
public class PainelGabinete extends JPanel {

    private final PainelPrefeito painelPrefeito;
    private double popularidade = 60;
    private String frase = "Vamos juntos construir uma cidade melhor para todos!";
    private final Timer animTimer;
    private double tick = 0;

    public PainelGabinete() {
        setLayout(null); // absoluto para posicionar o prefeito
        setOpaque(false);

        painelPrefeito = new PainelPrefeito();
        add(painelPrefeito);

        animTimer = new Timer(40, e -> { tick += 0.03; repaint(); });
        animTimer.start();
    }

    public void setPopularidade(double pop) {
        this.popularidade = pop;
        painelPrefeito.setPopularidade(pop);
        atualizarFrase(pop);
        repaint();
    }

    private void atualizarFrase(double pop) {
        if (pop >= 70)      frase = "Vamos juntos construir uma cidade melhor para todos!";
        else if (pop >= 50) frase = "Estamos no caminho certo. Continue confiando!";
        else if (pop >= 30) frase = "Precisamos de ações mais firmes pela cidade.";
        else                frase = "A situação é grave. Precisamos agir imediatamente!";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        desenharFundoEscritorio(g2, w, h);
        desenharJanelaFundo(g2, w, h);
        desenharParede(g2, w, h);
        desenharMesa(g2, w, h);
        desenharBandeira(g2, w, h);
        desenharLuminaria(g2, w, h);
        desenharQuadro(g2, w, h);
        desenharAcessoriosMesa(g2, w, h);
        desenharBalaoFala(g2, w, h);

        g2.dispose();

        // Posiciona prefeito dinamicamente
        int pw = (int)(w * 0.28), ph = (int)(h * 0.62);
        int px = (w - pw) / 2, py = (int)(h * 0.22);
        painelPrefeito.setBounds(px, py, pw, ph);
    }

    private void desenharFundoEscritorio(Graphics2D g2, int w, int h) {
        // Teto e paredes escuras
        GradientPaint gp = new GradientPaint(0, 0, new Color(25, 20, 15),
                                             0, h, new Color(18, 14, 10));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.setPaint(null);

        // Rodapé
        g2.setColor(new Color(35, 28, 20));
        g2.fillRect(0, h - 8, w, 8);
    }

    private void desenharJanelaFundo(Graphics2D g2, int w, int h) {
        int wx = w/2 - 140, wy = 15, ww = 280, wh = (int)(h * 0.52);

        // Moldura da janela
        g2.setColor(new Color(55, 45, 32));
        g2.fillRoundRect(wx - 8, wy - 8, ww + 16, wh + 16, 6, 6);

        // Céu (azul) — fica mais azul com popularidade alta
        int blueIntensity = (int)(80 + popularidade * 1.2);
        blueIntensity = Math.min(blueIntensity, 200);
        GradientPaint ceu = new GradientPaint(wx, wy, new Color(30, 80, blueIntensity),
                                              wx, wy + wh*0.6f, new Color(80, 160, 220));
        g2.setPaint(ceu);
        g2.fillRect(wx, wy, ww, wh);
        g2.setPaint(null);

        // Nuvens animadas na janela
        g2.setColor(new Color(255, 255, 255, 120));
        double ox1 = (tick * 8) % (ww + 60) - 30;
        double ox2 = (tick * 5 + ww * 0.4) % (ww + 60) - 30;
        g2.fillOval((int)(wx + ox1),      wy + 18, 55, 18);
        g2.fillOval((int)(wx + ox1 + 30), wy + 12, 40, 15);
        g2.fillOval((int)(wx + ox2),      wy + 30, 45, 14);

        // Prédios da cidade na janela
        int[] altPred = {80, 110, 90, 130, 95, 115, 85, 105};
        int[] largPred = {28, 32, 25, 38, 30, 35, 26, 33};
        int xBase = wx + 10;
        int yBase = wy + wh;
        for (int i = 0; i < altPred.length; i++) {
            // Prédio
            Color corPred = i % 2 == 0 ? new Color(60, 80, 120) : new Color(80, 100, 140);
            g2.setColor(corPred);
            g2.fillRect(xBase, yBase - altPred[i], largPred[i], altPred[i]);
            // Janelas
            g2.setColor(new Color(255, 240, 150, 160));
            for (int r = 0; r < 4; r++)
                for (int c = 0; c < 2; c++)
                    g2.fillRect(xBase + 4 + c * 10, yBase - altPred[i] + 8 + r * 16, 6, 8);
            xBase += largPred[i] + 4;
            if (xBase > wx + ww - 20) break;
        }

        // Turbina eólica (aparece com energia alta)
        if (popularidade > 40) {
            int tx = wx + ww - 55, ty = wy + wh - 100;
            g2.setColor(new Color(200, 210, 220));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(tx, ty, tx, ty + 65);
            // Pás giratórias
            g2.setColor(new Color(230, 235, 240));
            double ang = tick * 1.5;
            for (int i = 0; i < 3; i++) {
                double a = ang + i * Math.PI * 2 / 3;
                g2.drawLine(tx, ty, (int)(tx + Math.cos(a) * 22), (int)(ty + Math.sin(a) * 22));
            }
            g2.setStroke(new BasicStroke(1));
        }

        // Vegetação embaixo da janela
        g2.setColor(new Color(30, 120, 60));
        g2.fillRect(wx, wy + wh - 18, ww, 18);
        g2.setColor(new Color(40, 150, 75));
        for (int i = 0; i < 6; i++) {
            int tx2 = wx + 15 + i * 45;
            g2.fillOval(tx2, wy + wh - 26, 22, 18);
        }

        // Grade da janela
        g2.setColor(new Color(55, 45, 32));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(wx + ww/2, wy, wx + ww/2, wy + wh);
        g2.drawLine(wx, wy + wh/2, wx + ww, wy + wh/2);
        g2.setStroke(new BasicStroke(1));

        // Moldura externa
        g2.setColor(new Color(80, 65, 45));
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(wx - 8, wy - 8, ww + 16, wh + 16, 6, 6);
        g2.setStroke(new BasicStroke(1));
    }

    private void desenharParede(Graphics2D g2, int w, int h) {
        // Iluminação ambiente (spots no teto)
        for (int i = 0; i < 3; i++) {
            int lx = w / 4 + i * w / 4;
            GradientPaint spot = new GradientPaint(lx, 0, new Color(255, 220, 160, 60),
                                                   lx, h / 3, new Color(255, 220, 160, 0));
            g2.setPaint(spot);
            g2.fill(new Ellipse2D.Double(lx - 60, 0, 120, h / 3));
        }
        g2.setPaint(null);
    }

    private void desenharMesa(Graphics2D g2, int w, int h) {
        int mx = 20, my = (int)(h * 0.72), mw = w - 40, mh = (int)(h * 0.18);

        // Sombra da mesa
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(mx + 6, my + 6, mw, mh, 8, 8);

        // Superfície da mesa (madeira escura)
        GradientPaint mesa = new GradientPaint(mx, my, new Color(100, 65, 30),
                                               mx, my + mh, new Color(70, 42, 18));
        g2.setPaint(mesa);
        g2.fillRoundRect(mx, my, mw, mh, 8, 8);
        g2.setPaint(null);

        // Reflexo na mesa
        g2.setColor(new Color(255, 200, 130, 25));
        g2.fillRoundRect(mx + 10, my + 4, mw - 20, mh / 3, 6, 6);

        // Borda frontal da mesa
        g2.setColor(new Color(55, 32, 12));
        g2.fillRoundRect(mx, my + mh - 10, mw, 16, 4, 4);

        // Plaquinha "GABINETE DO PREFEITO"
        int px2 = w/2 - 90, py2 = my + mh - 30;
        g2.setColor(new Color(180, 140, 50));
        g2.fillRoundRect(px2, py2, 180, 28, 5, 5);
        g2.setColor(new Color(120, 90, 20));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(px2, py2, 180, 28, 5, 5);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(50, 30, 5));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
        g2.drawString("GABINETE DO PREFEITO", px2 + 14, py2 + 17);
    }

    private void desenharBandeira(Graphics2D g2, int w, int h) {
        int bx = 45, by = (int)(h * 0.20);
        // Mastro
        g2.setColor(new Color(180, 150, 80));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(bx, by, bx, (int)(h * 0.75));
        g2.setStroke(new BasicStroke(1));
        // Esfera dourada no topo
        g2.setColor(new Color(220, 180, 60));
        g2.fill(new Ellipse2D.Double(bx - 5, by - 5, 10, 10));
        // Bandeira (branco e verde)
        int fw = 55, fh = 70;
        g2.setColor(Color.WHITE);
        g2.fillRect(bx + 1, by, fw, fh);
        g2.setColor(new Color(0, 140, 70));
        g2.fillRect(bx + 1, by + fh/3, fw, fh/3);
        // Brasão simplificado
        g2.setColor(new Color(0, 80, 160));
        g2.fill(new Ellipse2D.Double(bx + fw/2 - 8, by + fh/2 - 10, 16, 16));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        g2.drawString("♻", bx + fw/2 - 5, by + fh/2 + 3);
    }

    private void desenharLuminaria(Graphics2D g2, int w, int h) {
        int lx = (int)(w * 0.30), ly = (int)(h * 0.70);
        // Haste
        g2.setColor(new Color(60, 48, 30));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(lx, ly, lx + 10, ly - 45);
        g2.setStroke(new BasicStroke(1));
        // Cúpula verde
        g2.setColor(new Color(20, 100, 50));
        g2.fill(new Arc2D.Double(lx - 12, ly - 58, 45, 28, 0, 180, Arc2D.PIE));
        g2.setColor(new Color(30, 130, 65));
        g2.fill(new Arc2D.Double(lx - 10, ly - 55, 40, 22, 0, 180, Arc2D.PIE));
        // Luz
        GradientPaint luz = new GradientPaint(lx + 10, ly - 30, new Color(255, 240, 180, 80),
                                              lx + 10, ly + 20, new Color(255, 240, 180, 0));
        g2.setPaint(luz);
        g2.fill(new Ellipse2D.Double(lx - 20, ly - 32, 70, 55));
        g2.setPaint(null);
        // Base
        g2.setColor(new Color(180, 140, 50));
        g2.fill(new RoundRectangle2D.Double(lx - 5, ly, 30, 8, 4, 4));
    }

    private void desenharQuadro(Graphics2D g2, int w, int h) {
        int qx = w - 160, qy = (int)(h * 0.22);
        // Moldura
        g2.setColor(new Color(80, 60, 30));
        g2.fillRoundRect(qx, qy, 130, 85, 6, 6);
        // Fundo do quadro
        g2.setColor(new Color(20, 35, 20));
        g2.fillRoundRect(qx + 6, qy + 6, 118, 73, 4, 4);
        // Texto do quadro
        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        g2.setColor(new Color(46, 204, 113));
        g2.drawString("NOSSA CIDADE,", qx + 12, qy + 26);
        g2.drawString("NOSSA GENTE,", qx + 12, qy + 42);
        g2.drawString("NOSSO FUTURO!", qx + 12, qy + 58);
        // Ícone cidade
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        g2.drawString("🏙", qx + 85, qy + 62);
    }

    private void desenharAcessoriosMesa(Graphics2D g2, int w, int h) {
        int my = (int)(h * 0.72);

        // Foto de família (lado direito)
        int fx = w - 130, fy = my - 45;
        g2.setColor(new Color(160, 130, 90));
        g2.fillRoundRect(fx, fy, 55, 42, 4, 4);
        g2.setColor(new Color(80, 100, 130));
        g2.fillRoundRect(fx + 4, fy + 4, 47, 34, 2, 2);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        g2.drawString("👨‍👩‍👧", fx + 10, fy + 28);

        // Caneca "Nossa Cidade"
        int cx2 = w - 65, cy2 = my - 38;
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(cx2, cy2, 30, 35, 4, 4);
        g2.setColor(new Color(0, 120, 60));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 6));
        g2.drawString("NOSSA", cx2 + 3, cy2 + 14);
        g2.drawString("CIDADE", cx2 + 2, cy2 + 22);
        // Alça
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(cx2 + 26, cy2 + 8, 12, 16, -90, 180);
        g2.setStroke(new BasicStroke(1));

        // Tablet/notebook
        int tx = (int)(w * 0.60), ty = my - 28;
        g2.setColor(new Color(30, 35, 45));
        g2.fillRoundRect(tx, ty, 70, 50, 6, 6);
        g2.setColor(new Color(40, 120, 200));
        g2.fillRoundRect(tx + 4, ty + 4, 62, 38, 4, 4);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        g2.drawString("📊", tx + 20, ty + 30);
    }

    private void desenharBalaoFala(Graphics2D g2, int w, int h) {
        int bw = 280, bh = 58;
        int bx = w/2 - bw/2, by = (int)(h * 0.06);

        // Sombra do balão
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fill(new RoundRectangle2D.Double(bx + 3, by + 3, bw, bh, 14, 14));

        // Fundo do balão
        g2.setColor(new Color(25, 35, 50, 220));
        g2.fill(new RoundRectangle2D.Double(bx, by, bw, bh, 14, 14));

        // Borda brilhante
        g2.setColor(new Color(80, 140, 255, 120));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(bx, by, bw, bh, 14, 14));
        g2.setStroke(new BasicStroke(1));

        // Estrelinhas decorativas
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        g2.drawString("✨", bx + 8, by + 18);
        g2.drawString("✨", bx + bw - 22, by + 18);

        // Texto do balão (duas linhas)
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();

        // Quebra em duas linhas se necessário
        String[] partes = quebrarFrase(frase, fm, bw - 30);
        int lineH = fm.getHeight();
        int totalH = partes.length * lineH;
        int startY = by + (bh - totalH) / 2 + fm.getAscent();
        for (String parte : partes) {
            g2.drawString(parte, bx + (bw - fm.stringWidth(parte)) / 2, startY);
            startY += lineH;
        }
    }

    private String[] quebrarFrase(String frase, FontMetrics fm, int maxW) {
        if (fm.stringWidth(frase) <= maxW) return new String[]{frase};
        int meio = frase.length() / 2;
        int espaco = frase.indexOf(' ', meio);
        if (espaco < 0) return new String[]{frase};
        return new String[]{frase.substring(0, espaco), frase.substring(espaco + 1)};
    }
}
