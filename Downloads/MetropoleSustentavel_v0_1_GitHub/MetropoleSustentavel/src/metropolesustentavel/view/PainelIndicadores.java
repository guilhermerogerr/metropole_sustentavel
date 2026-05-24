package metropolesustentavel.view;

import metropolesustentavel.model.*;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.EnumMap;
import java.util.Map;

public class PainelIndicadores extends JPanel {

    private final Map<TipoIndicador, JProgressBar> barras = new EnumMap<>(TipoIndicador.class);
    private final Map<TipoIndicador, JLabel> rotulos      = new EnumMap<>(TipoIndicador.class);
    private JLabel lblMeta;
    private PainelDesempenho painelDesempenho;

    public PainelIndicadores() {
        setBackground(new Color(18, 22, 30));
        setLayout(new BorderLayout(12, 0));
        setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        // Esquerdo: lista de indicadores
        JPanel listaPanel = new JPanel(new BorderLayout(0, 8));
        listaPanel.setOpaque(false);

        JLabel titulo = new JLabel("INDICADORES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setForeground(Color.WHITE);
        listaPanel.add(titulo, BorderLayout.NORTH);

        JPanel barrasPanel = new JPanel(new GridLayout(TipoIndicador.values().length, 1, 0, 6));
        barrasPanel.setOpaque(false);

        for (TipoIndicador tipo : TipoIndicador.values()) {
            JPanel linha = new JPanel(new BorderLayout(8, 0));
            linha.setOpaque(false);

            JLabel icone = new JLabel(tipo.getIcone() + "  " + tipo.getNome());
            icone.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            icone.setForeground(new Color(200, 210, 225));
            icone.setPreferredSize(new Dimension(210, 20));

            JProgressBar barra = criarBarra(corParaTipo(tipo));
            JLabel val = new JLabel("0%");
            val.setFont(new Font("Segoe UI", Font.BOLD, 12));
            val.setForeground(Color.WHITE);
            val.setPreferredSize(new Dimension(40, 20));
            val.setHorizontalAlignment(SwingConstants.RIGHT);

            linha.add(icone, BorderLayout.WEST);
            linha.add(barra, BorderLayout.CENTER);
            linha.add(val,   BorderLayout.EAST);

            barras.put(tipo, barra);
            rotulos.put(tipo, val);
            barrasPanel.add(linha);
        }

        listaPanel.add(barrasPanel, BorderLayout.CENTER);

        // Label de meta
        lblMeta = new JLabel("Meta → ");
        lblMeta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMeta.setForeground(Constantes.COR_AMARELO);
        listaPanel.add(lblMeta, BorderLayout.SOUTH);

        // Direito: desempenho geral circular
        painelDesempenho = new PainelDesempenho();
        painelDesempenho.setPreferredSize(new Dimension(190, 0));

        add(listaPanel,       BorderLayout.CENTER);
        add(painelDesempenho, BorderLayout.EAST);
    }

    public void atualizar(Map<TipoIndicador, Indicador> indicadores, double popularidade) {
        for (TipoIndicador t : TipoIndicador.values()) {
            double v = indicadores.get(t).getValor();
            barras.get(t).setValue((int) v);
            rotulos.get(t).setText((int) v + "%");
        }
        // Calcula desempenho geral
        double desempenho =
            (100 - indicadores.get(TipoIndicador.POLUICAO).getValor())  * 0.20 +
            indicadores.get(TipoIndicador.ENERGIA).getValor()           * 0.15 +
            indicadores.get(TipoIndicador.MOBILIDADE).getValor()        * 0.15 +
            (100 - indicadores.get(TipoIndicador.RESIDUOS).getValor())  * 0.15 +
            indicadores.get(TipoIndicador.SATISFACAO).getValor()        * 0.20 +
            indicadores.get(TipoIndicador.SUSTENTABILIDADE).getValor()  * 0.15;
        painelDesempenho.setDesempenho((int) desempenho);
        repaint();
    }

    public void setMeta(String meta) {
        lblMeta.setText(meta);
    }

    private JProgressBar criarBarra(Color cor) {
        JProgressBar b = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 48, 62));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                int w = (int)(getWidth() * getValue() / 100.0);
                if (w > 4) {
                    g2.setColor(getForeground());
                    g2.fill(new RoundRectangle2D.Float(0, 0, w, getHeight(), 8, 8));
                }
                g2.dispose();
            }
        };
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setPreferredSize(new Dimension(0, 14));
        b.setForeground(cor);
        return b;
    }

    private Color corParaTipo(TipoIndicador t) {
        return switch (t) {
            case POLUICAO         -> new Color(231, 76, 60);
            case ENERGIA          -> new Color(255, 200, 0);
            case MOBILIDADE       -> new Color(255, 165, 0);
            case RESIDUOS         -> new Color(52, 200, 220);
            case SATISFACAO       -> new Color(180, 100, 255);
            case SUSTENTABILIDADE -> new Color(46, 204, 113);
        };
    }

    // ── Círculo de desempenho geral ──────────────────────────────────────────
    static class PainelDesempenho extends JPanel {
        private int desempenho = 0;

        PainelDesempenho() {
            setOpaque(false);
        }

        void setDesempenho(int d) { this.desempenho = d; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int cx = w / 2;

            // Fundo do card
            g2.setColor(new Color(25, 32, 44));
            g2.fill(new RoundRectangle2D.Float(2, 2, w - 4, h - 4, 14, 14));

            // Título no topo
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();
            String tit = "DESEMPENHO GERAL";
            g2.drawString(tit, cx - fm.stringWidth(tit)/2, 18);

            // Badge embaixo (reserva 42px)
            int badgeH   = 42;
            int badgeY   = h - badgeH - 4;

            // Área disponível para o arco
            int arcAreaTop = 24;
            int arcAreaH   = badgeY - arcAreaTop - 6;
            int r = Math.min(w / 2 - 16, arcAreaH / 2 - 6);
            int cy = arcAreaTop + arcAreaH / 2;

            // Cor do arco
            Color corArc = desempenho >= 60 ? new Color(46, 204, 113)
                         : desempenho >= 35 ? new Color(255, 200, 0)
                         : new Color(231, 76, 60);

            // Arco fundo
            g2.setColor(new Color(40, 52, 68));
            g2.setStroke(new BasicStroke(9, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(new Arc2D.Double(cx - r, cy - r, r * 2, r * 2, -220, 260, Arc2D.OPEN));

            // Arco progresso
            g2.setColor(corArc);
            double sweep = 260.0 * desempenho / 100.0;
            g2.draw(new Arc2D.Double(cx - r, cy - r, r * 2, r * 2, -220, -sweep, Arc2D.OPEN));
            g2.setStroke(new BasicStroke(1));

            // Percentual central
            g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
            g2.setColor(corArc);
            String pct = desempenho + "%";
            fm = g2.getFontMetrics();
            g2.drawString(pct, cx - fm.stringWidth(pct) / 2, cy + 7);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.setColor(new Color(140, 150, 165));
            String sub = "Desempenho";
            fm = g2.getFontMetrics();
            g2.drawString(sub, cx - fm.stringWidth(sub) / 2, cy + 20);

            // Badge embaixo
            String badge    = desempenho >= 60 ? "🏆 Continue assim!"
                            : desempenho >= 35 ? "📈 Melhorando!"
                            : "⚠ Atenção!";
            String badgeSub = desempenho >= 60 ? "A cidade está no caminho certo."
                            : desempenho >= 35 ? "Continue tomando decisões."
                            : "A cidade precisa de ajuda!";

            g2.setColor(new Color(30, 40, 55));
            g2.fill(new RoundRectangle2D.Float(6, badgeY, w - 12, badgeH, 8, 8));

            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            g2.setColor(corArc);
            fm = g2.getFontMetrics();
            g2.drawString(badge, cx - fm.stringWidth(badge) / 2, badgeY + 16);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.setColor(new Color(140, 150, 165));
            fm = g2.getFontMetrics();
            g2.drawString(badgeSub, cx - fm.stringWidth(badgeSub) / 2, badgeY + 30);

            g2.dispose();
        }
    }
}
