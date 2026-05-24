package metropolesustentavel.view;

import metropolesustentavel.model.PerguntaColetiva;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DialogoColetiva extends JDialog {

    private PerguntaColetiva.RespostaColetiva respostaEscolhida = null;
    private boolean tempoEsgotado = false;
    private Timer timer;
    private int segundosRestantes;
    private final PainelCronometro cronometro;

    public DialogoColetiva(JFrame parent, PerguntaColetiva pergunta, int segundos) {
        super(parent, "Coletiva de Imprensa", true);
        setUndecorated(true);
        this.segundosRestantes = segundos;

        JPanel root = new JPanel(new BorderLayout(0, 14)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(13, 17, 23));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(Constantes.COR_LARANJA);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 16, 16));
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(20, 26, 20, 26));

        // ── Topo ────────────────────────────────────────────────────────────
        JPanel topo = new JPanel(new BorderLayout(12, 0));
        topo.setOpaque(false);

        JLabel tag = new JLabel("🎤  COLETIVA DE IMPRENSA");
        tag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        tag.setForeground(Constantes.COR_LARANJA);

        JLabel lblJorn = new JLabel("📰  " + pergunta.getJornalista());
        lblJorn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblJorn.setForeground(Constantes.COR_TEXTO);

        JPanel esqTopo = new JPanel(new BorderLayout(0, 4));
        esqTopo.setOpaque(false);
        esqTopo.add(tag,     BorderLayout.NORTH);
        esqTopo.add(lblJorn, BorderLayout.CENTER);

        cronometro = new PainelCronometro(segundos);
        topo.add(esqTopo,    BorderLayout.CENTER);
        topo.add(cronometro, BorderLayout.EAST);

        // ── Balão de pergunta ────────────────────────────────────────────────
        JPanel balao = criarBalao(pergunta.getPergunta(), Constantes.COR_LARANJA);

        // ── Aviso ────────────────────────────────────────────────────────────
        JLabel lblAviso = new JLabel("⏰ Responda rápido! Silêncio é pior que resposta ruim.");
        lblAviso.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblAviso.setForeground(new Color(180, 100, 80));

        // ── Pergunta ─────────────────────────────────────────────────────────
        JLabel lblQ = new JLabel("Como o prefeito responde?");
        lblQ.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQ.setForeground(Constantes.COR_TEXTO_DIM);

        // ── Botões de resposta ────────────────────────────────────────────────
        JPanel painelBtns = new JPanel(new GridLayout(0, 1, 0, 8));
        painelBtns.setOpaque(false);
        for (PerguntaColetiva.RespostaColetiva resp : pergunta.getRespostas()) {
            Color corTipo = switch (resp.getTipo()) {
                case BOA      -> Constantes.COR_VERDE;
                case POLITICA -> Constantes.COR_AMARELO;
                case RUIM     -> Constantes.COR_VERMELHO;
            };
            JButton btn = criarBotaoResposta(resp.getText(), corTipo);
            btn.addActionListener(e -> {
                pararTimer();
                respostaEscolhida = resp;
                mostrarRepercussao(resp);
                dispose();
            });
            painelBtns.add(btn);
        }

        JPanel sul = new JPanel(new BorderLayout(0, 8));
        sul.setOpaque(false);
        sul.add(lblAviso,   BorderLayout.NORTH);
        sul.add(lblQ,       BorderLayout.CENTER);
        sul.add(painelBtns, BorderLayout.SOUTH);

        root.add(topo,  BorderLayout.NORTH);
        root.add(balao, BorderLayout.CENTER);
        root.add(sul,   BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setMinimumSize(new Dimension(560, 400));
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
                    "<html><b style='color:#e74c3c'>⏰ Prefeito ficou em silêncio!</b><br>" +
                    "A imprensa interpreta o silêncio como culpa.<br>" +
                    "Popularidade ↓↓  |  Satisfação ↓</html>",
                    "Silêncio Constrangedor", JOptionPane.WARNING_MESSAGE);
                dispose();
            }
        });
        timer.start();
    }

    private void pararTimer() {
        if (timer != null && timer.isRunning()) timer.stop();
    }

    private void mostrarRepercussao(PerguntaColetiva.RespostaColetiva resp) {
        String icone = switch (resp.getTipo()) {
            case BOA      -> "✅";
            case POLITICA -> "⚠️";
            case RUIM     -> "❌";
        };
        String popStr = resp.getDeltaPopularidade() >= 0
                ? "<span style='color:#2ecc71'>+" + (int) resp.getDeltaPopularidade() + " popularidade</span>"
                : "<span style='color:#e74c3c'>" + (int) resp.getDeltaPopularidade() + " popularidade</span>";
        JOptionPane.showMessageDialog(getOwner(),
            "<html><body style='width:360px;font-size:13px'>" +
            "<b>" + icone + " Repercussão na Imprensa</b><br><br>" +
            resp.getConsequencia() + "<br><br>" + popStr + "</body></html>",
            "Repercussão", JOptionPane.INFORMATION_MESSAGE);
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

    private JButton criarBotaoResposta(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()
                        ? new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 50)
                        : Constantes.COR_BORDA);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(cor);
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
        btn.setPreferredSize(new Dimension(500, 50));
        return btn;
    }

    public PerguntaColetiva.RespostaColetiva getRespostaEscolhida() { return respostaEscolhida; }
    public boolean isTempoEsgotado()                                 { return tempoEsgotado; }
}
