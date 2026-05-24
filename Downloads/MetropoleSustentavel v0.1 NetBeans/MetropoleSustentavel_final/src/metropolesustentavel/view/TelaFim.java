package metropolesustentavel.view;

import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class TelaFim extends JPanel {

    private final JButton btnReiniciar;

    public TelaFim(boolean vitoria, int pontuacao, String motivo) {
        setLayout(new GridBagLayout());
        setBackground(Constantes.COR_FUNDO);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);

        String emoji  = vitoria ? "♻️" : "💔";
        String titulo = vitoria ? "Metrópole Sustentável!" : "Missão Fracassada";
        String sub    = vitoria
            ? "Você transformou a cidade em um modelo de sustentabilidade!"
            : motivo;
        Color corTit  = vitoria ? Constantes.COR_VERDE_NEON : Constantes.COR_VERMELHO;

        JLabel lEmoji = new JLabel(emoji, SwingConstants.CENTER);
        lEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        lEmoji.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lTitulo.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lTitulo.setForeground(corTit);
        lTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JTextArea lSub = new JTextArea(sub);
        lSub.setEditable(false); lSub.setFocusable(false);
        lSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lSub.setForeground(Constantes.COR_TEXTO_DIM);
        lSub.setBackground(Constantes.COR_FUNDO);
        lSub.setAlignmentX(CENTER_ALIGNMENT);
        lSub.setLineWrap(true); lSub.setWrapStyleWord(true);

        JLabel lPont = new JLabel("Pontuação final: " + pontuacao, SwingConstants.CENTER);
        lPont.setFont(new Font("Segoe UI Mono", Font.BOLD, 22));
        lPont.setForeground(Constantes.COR_AMARELO);
        lPont.setAlignmentX(CENTER_ALIGNMENT);

        btnReiniciar = criarBotao("♻️  JOGAR NOVAMENTE", Constantes.COR_VERDE, Color.WHITE);
        btnReiniciar.setAlignmentX(CENTER_ALIGNMENT);

        JButton btnSair = criarBotao("✕  SAIR", Constantes.COR_PAINEL, Constantes.COR_TEXTO_DIM);
        btnSair.setAlignmentX(CENTER_ALIGNMENT);
        btnSair.addActionListener(e -> System.exit(0));

        centro.add(lEmoji);
        centro.add(Box.createVerticalStrut(14));
        centro.add(lTitulo);
        centro.add(Box.createVerticalStrut(12));
        centro.add(lSub);
        centro.add(Box.createVerticalStrut(20));
        centro.add(lPont);
        centro.add(Box.createVerticalStrut(36));
        centro.add(btnReiniciar);
        centro.add(Box.createVerticalStrut(12));
        centro.add(btnSair);

        add(centro);
    }

    private JButton criarBotao(String txt, Color bg, Color fg) {
        JButton btn = new JButton(txt) {
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
        btn.setOpaque(false); btn.setContentAreaFilled(false); btn.setBorderPainted(false);
        btn.setBackground(bg); btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(260, 50));
        btn.setMaximumSize(new Dimension(260, 50));
        return btn;
    }

    public void adicionarListenerReiniciar(ActionListener l) { btnReiniciar.addActionListener(l); }
}
