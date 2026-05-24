package metropolesustentavel.view;

import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class TelaInicial extends JPanel {

    private final JButton btnJogar;
    private final JButton btnSair;

    public TelaInicial() {
        setLayout(new GridBagLayout());
        setBackground(Constantes.COR_FUNDO);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);

        JLabel icone = new JLabel("♻️", SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        icone.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Metrópole Sustentável", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titulo.setForeground(Constantes.COR_VERDE_NEON);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Governe. Decida. Salve a cidade.", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(Constantes.COR_TEXTO_DIM);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        JTextArea desc = new JTextArea(
            "Você é o prefeito de uma metrópole em crise ambiental.\n" +
            "Tome decisões estratégicas contra o relógio, enfrente a imprensa\n" +
            "em coletivas cronometradas e gerencie sua popularidade.\n\n" +
            "♻️  4 níveis  ·  Decisões com timer  ·  Coletiva a cada 3 turnos  ·  Cidade animada"
        );
        desc.setEditable(false);
        desc.setFocusable(false);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(Constantes.COR_TEXTO_DIM);
        desc.setBackground(Constantes.COR_FUNDO);
        desc.setAlignmentX(CENTER_ALIGNMENT);

        btnJogar = criarBotao("▶  JOGAR", Constantes.COR_VERDE, Color.WHITE);
        btnJogar.setAlignmentX(CENTER_ALIGNMENT);

        btnSair = criarBotao("✕  SAIR", Constantes.COR_PAINEL, Constantes.COR_TEXTO_DIM);
        btnSair.setAlignmentX(CENTER_ALIGNMENT);
        btnSair.addActionListener(e -> System.exit(0));

        JLabel versao = new JLabel("v0.1", SwingConstants.CENTER);
        versao.setFont(new Font("Segoe UI Mono", Font.BOLD, 11));
        versao.setForeground(new Color(46, 204, 113, 160));
        JLabel creditos = new JLabel("APS 2026/1 · UNIP · Ciência da Computação · POO", SwingConstants.CENTER);
        creditos.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        creditos.setForeground(new Color(50, 60, 70));
        creditos.setAlignmentX(CENTER_ALIGNMENT);
        creditos.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        creditos.setForeground(new Color(50, 60, 70));
        creditos.setAlignmentX(CENTER_ALIGNMENT);

        centro.add(icone);
        centro.add(Box.createVerticalStrut(12));
        centro.add(titulo);
        centro.add(Box.createVerticalStrut(8));
        centro.add(sub);
        centro.add(Box.createVerticalStrut(28));
        centro.add(desc);
        centro.add(Box.createVerticalStrut(36));
        centro.add(btnJogar);
        centro.add(Box.createVerticalStrut(12));
        centro.add(btnSair);
        centro.add(Box.createVerticalStrut(32));
        centro.add(versao);
        centro.add(Box.createVerticalStrut(4));
        centro.add(creditos);

        add(centro);
    }

    private JButton criarBotao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(260, 50));
        btn.setMaximumSize(new Dimension(260, 50));
        return btn;
    }

    public void adicionarListenerJogar(ActionListener l) { btnJogar.addActionListener(l); }
}
