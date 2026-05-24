package metropolesustentavel.view;

import metropolesustentavel.controller.GameController;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import java.awt.*;

public class JanelaJogo extends JFrame {

    private static final String TELA_INICIAL = "INICIAL";
    private static final String TELA_JOGO    = "JOGO";
    private static final String TELA_FIM     = "FIM";

    private final CardLayout card;
    private final JPanel painel;

    public JanelaJogo() {
        super("♻️ Metrópole Sustentável – APS 2026/1  |  v0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(950, 620));

        card   = new CardLayout();
        painel = new JPanel(card);
        painel.setBackground(Constantes.COR_FUNDO);

        mostrarInicial();
        add(painel);
        setVisible(true);
    }

    private void mostrarInicial() {
        TelaInicial tela = new TelaInicial();
        tela.adicionarListenerJogar(e -> iniciarJogo());
        painel.add(tela, TELA_INICIAL);
        card.show(painel, TELA_INICIAL);
    }

    private void iniciarJogo() {
        GameController gc = new GameController();
        TelaJogo telaJogo = new TelaJogo(gc);
        telaJogo.setOnVitoria(() -> mostrarFim(true,  gc.getCidade().getPontuacao(), ""));
        telaJogo.setOnDerrota(() -> mostrarFim(false, gc.getCidade().getPontuacao(),
                                               gc.getCidade().getMotivoDerrota()));
        painel.add(telaJogo, TELA_JOGO);
        card.show(painel, TELA_JOGO);
    }

    private void mostrarFim(boolean vitoria, int pontos, String motivo) {
        TelaFim fim = new TelaFim(vitoria, pontos, motivo);
        fim.adicionarListenerReiniciar(e -> {
            painel.removeAll();
            mostrarInicial();
        });
        painel.add(fim, TELA_FIM);
        card.show(painel, TELA_FIM);
    }
}
