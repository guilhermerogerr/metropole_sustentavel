package metropolesustentavel.view;

import metropolesustentavel.model.Decisao;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DialogoDecisao extends JDialog {

    private Decisao.OpcaoDecisao opcaoEscolhida = null;
    private boolean tempoEsgotado = false;
    private Timer timer;
    private int segundosRestantes;
    private final PainelCronometro cronometro;

    public DialogoDecisao(JFrame parent, Decisao decisao, int segundos) {
        super(parent, "Decisão", true);
        setUndecorated(true);
        this.segundosRestantes = segundos;

        JPanel root = new JPanel(new BorderLayout(0, 14)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(13, 17, 23));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(Constantes.COR_AZUL);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 16, 16));
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(20, 26, 20, 26));

        // ── Topo: emoji + título + cronômetro ──────────────────────────────
        JPanel topo = new JPanel(new BorderLayout(12, 0));
        topo.setOpaque(false);

        JPanel esqTopo = new JPanel(new BorderLayout(8, 4));
        esqTopo.setOpaque(false);

        JLabel emoji = new JLabel(decisao.getImagemEmoji());
        emoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));

        JLabel lblTag = new JLabel("⚡ DECISÃO URGENTE");
        lblTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTag.setForeground(Constantes.COR_AZUL);

        JLabel lblTitulo = new JLabel(decisao.getTitulo());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setForeground(Constantes.COR_TEXTO);

        JPanel txtTopo = new JPanel(new BorderLayout(0, 2));
        txtTopo.setOpaque(false);
        txtTopo.add(lblTag,    BorderLayout.NORTH);
        txtTopo.add(lblTitulo, BorderLayout.CENTER);

        esqTopo.add(emoji,   BorderLayout.WEST);
        esqTopo.add(txtTopo, BorderLayout.CENTER);

        cronometro = new PainelCronometro(segundos);
        topo.add(esqTopo,   BorderLayout.CENTER);
        topo.add(cronometro, BorderLayout.EAST);

        // ── Situação ────────────────────────────────────────────────────────
        JPanel balao = criarBalao(decisao.getSituacao(), Constantes.COR_AZUL);

        // ── Aviso de tempo ──────────────────────────────────────────────────
        JLabel lblAviso = new JLabel("⏰ Decida rápido! Se o tempo esgotar a cidade piora automaticamente.");
        lblAviso.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblAviso.setForeground(new Color(180, 100, 80));

        // ── Pergunta ────────────────────────────────────────────────────────
        JLabel lblQ = new JLabel("O que você decide fazer?");
        lblQ.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQ.setForeground(Constantes.COR_TEXTO_DIM);

        // ── Botões ──────────────────────────────────────────────────────────
        JPanel painelBtns = new JPanel(new GridLayout(0, 1, 0, 8));
        painelBtns.setOpaque(false);
        for (Decisao.OpcaoDecisao op : decisao.getOpcoes()) {
            JButton btn = criarBotaoOpcao(op.getTexto(), Constantes.COR_AZUL);
            btn.addActionListener(e -> {
                pararTimer();
                opcaoEscolhida = op;
                mostrarConsequencia(op.getConsequencia(), "✅ Decisão tomada!", Constantes.COR_VERDE);
                dispose();
            });
            painelBtns.add(btn);
        }

        JPanel sul = new JPanel(new BorderLayout(0, 8));
        sul.setOpaque(false);
        sul.add(lblAviso,    BorderLayout.NORTH);
        sul.add(lblQ,        BorderLayout.CENTER);
        sul.add(painelBtns,  BorderLayout.SOUTH);

        root.add(topo,  BorderLayout.NORTH);
        root.add(balao, BorderLayout.CENTER);
        root.add(sul,   BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(540, 360));
        setLocationRelativeTo(parent);

        iniciarTimer();
    }

    private void iniciarTimer() {
        timer = new Timer(1000, e -> {
            segundosRestantes--;
            cronometro.setSegundosRestantes(segundosRestantes);
            if (segundosRestantes <= 0) {
                pararTimer();
                tempoEsgotado = true;
                JOptionPane.showMessageDialog(this,
                    "<html><b style='color:#e74c3c'>⏰ Tempo esgotado!</b><br>" +
                    "Sem decisão, a cidade piorou automaticamente.<br>" +
                    "Poluição ↑  |  Satisfação ↓  |  Popularidade ↓</html>",
                    "Tempo Esgotado", JOptionPane.WARNING_MESSAGE);
                dispose();
            }
        });
        timer.start();
    }

    private void pararTimer() {
        if (timer != null && timer.isRunning()) timer.stop();
    }

    private JPanel criarBalao(String texto, Color corBorda) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(22, 27, 34));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(new Color(corBorda.getRed(), corBorda.getGreen(), corBorda.getBlue(), 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10));
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(12, 14, 12, 14));
        JTextArea ta = new JTextArea(texto);
        ta.setEditable(false); ta.setFocusable(false);
        ta.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        ta.setForeground(Constantes.COR_TEXTO);
        ta.setBackground(new Color(0,0,0,0)); ta.setOpaque(false);
        ta.setLineWrap(true); ta.setWrapStyleWord(true);
        p.add(ta, BorderLayout.CENTER);
        return p;
    }

    private JButton criarBotaoOpcao(String texto, Color corDestaque) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()
                        ? new Color(corDestaque.getRed(), corDestaque.getGreen(), corDestaque.getBlue(), 50)
                        : Constantes.COR_BORDA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(corDestaque);
                g2.fill(new RoundRectangle2D.Float(0, 0, 4, getHeight(), 4, 4));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setOpaque(false); btn.setContentAreaFilled(false); btn.setBorderPainted(false);
        btn.setForeground(Constantes.COR_TEXTO);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 14, 10, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(480, 48));
        return btn;
    }

    private void mostrarConsequencia(String texto, String titulo, Color cor) {
        JOptionPane.showMessageDialog(getOwner(),
            "<html><body style='width:340px;font-size:13px'>" + titulo + "<br><br>" + texto + "</body></html>",
            "Consequência", JOptionPane.INFORMATION_MESSAGE);
    }

    public Decisao.OpcaoDecisao getOpcaoEscolhida() { return opcaoEscolhida; }
    public boolean isTempoEsgotado()                 { return tempoEsgotado; }
}
