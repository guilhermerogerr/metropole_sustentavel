package metropolesustentavel.view;

import metropolesustentavel.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Painel de countdown circular reutilizável.
 * Exibido no topo dos diálogos de decisão e coletiva.
 */
public class PainelCronometro extends JPanel {

    private int segundosTotal;
    private int segundosRestantes;
    private boolean urgente;

    public PainelCronometro(int segundosTotal) {
        this.segundosTotal    = segundosTotal;
        this.segundosRestantes = segundosTotal;
        setOpaque(false);
        setPreferredSize(new Dimension(70, 70));
    }

    public void setSegundosRestantes(int s) {
        this.segundosRestantes = s;
        this.urgente = s <= 10;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2, cy = getHeight() / 2, r = Math.min(cx, cy) - 4;

        // Fundo do círculo
        g2.setColor(Constantes.COR_BORDA);
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);

        // Arco do tempo restante
        double pct = (double) segundosRestantes / segundosTotal;
        Color corArco = urgente ? Constantes.COR_VERMELHO
                : pct > 0.5 ? Constantes.COR_VERDE : Constantes.COR_AMARELO;

        g2.setColor(corArco);
        g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(new Arc2D.Double(cx - r + 3, cy - r + 3, (r - 3) * 2, (r - 3) * 2,
                90, -360 * pct, Arc2D.OPEN));

        // Número central
        g2.setColor(urgente ? Constantes.COR_VERMELHO : Constantes.COR_TEXTO);
        g2.setFont(new Font("Segoe UI", Font.BOLD, urgente ? 18 : 16));
        FontMetrics fm = g2.getFontMetrics();
        String txt = String.valueOf(segundosRestantes);
        g2.drawString(txt, cx - fm.stringWidth(txt) / 2, cy + fm.getAscent() / 2 - 2);

        g2.dispose();
    }
}
