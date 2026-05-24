package metropolesustentavel.view;

import metropolesustentavel.controller.GameController;
import metropolesustentavel.model.*;
import metropolesustentavel.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TelaJogo extends JPanel {

    private final GameController controller;
    private final PainelIndicadores painelIndicadores;
    private final PainelMapa        painelMapa;
    private final PainelGabinete    painelGabinete;

    private JLabel   lblNivel;
    private JLabel   lblMeta;
    private JLabel   lblTurnoNum;
    private JProgressBar barraTurno;
    private JTextArea    areaLog;

    private Runnable onVitoria;
    private Runnable onDerrota;

    public TelaJogo(GameController controller) {
        this.controller = controller;
        setBackground(new Color(12, 15, 22));
        setLayout(new BorderLayout(0, 0));

        // ── TOPO: indicadores ─────────────────────────────────────────────────
        painelIndicadores = new PainelIndicadores();
        painelIndicadores.setPreferredSize(new Dimension(0, 165));
        add(painelIndicadores, BorderLayout.NORTH);

        // ── CENTRO: cidade (esq) + gabinete (dir) ─────────────────────────────
        JSplitPane centro = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centro.setDividerSize(4);
        centro.setBorder(null);
        centro.setBackground(new Color(12, 15, 22));
        centro.setDividerLocation(520);
        centro.setResizeWeight(0.50);

        // Esquerdo — cidade animada
        painelMapa = new PainelMapa();
        JPanel esq = new JPanel(new BorderLayout());
        esq.setOpaque(false);
        esq.add(painelMapa, BorderLayout.CENTER);

        // Direito — gabinete do prefeito
        painelGabinete = new PainelGabinete();
        JPanel dir = new JPanel(new BorderLayout());
        dir.setOpaque(false);
        dir.add(painelGabinete, BorderLayout.CENTER);

        centro.setLeftComponent(esq);
        centro.setRightComponent(dir);
        add(centro, BorderLayout.CENTER);

        // ── RODAPÉ: próxima ação + categorias + turno ─────────────────────────
        add(criarRodape(), BorderLayout.SOUTH);

        carregarNivel();
    }

    // ── RODAPÉ ────────────────────────────────────────────────────────────────
    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new BorderLayout(12, 0));
        rodape.setBackground(new Color(15, 20, 30));
        rodape.setBorder(new EmptyBorder(10, 16, 10, 16));
        rodape.setPreferredSize(new Dimension(0, 160));

        // ── Esquerda: info + categorias ──────────────────────────────────────
        JPanel esqRod = new JPanel(new BorderLayout(0, 8));
        esqRod.setOpaque(false);

        // Título + descrição
        JPanel tituloInfo = new JPanel(new BorderLayout(0, 3));
        tituloInfo.setOpaque(false);

        lblNivel = new JLabel("♻️  PRÓXIMA AÇÃO");
        lblNivel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNivel.setForeground(Constantes.COR_VERDE_NEON);

        lblMeta = new JLabel();
        lblMeta.setText("<html><body style='width:340px;color:#8b949e;font-size:11px'>" +
            "No turno você toma uma decisão contra o relógio.<br>" +
            "A cada 3 turnos, uma <span style='color:#f0883e'><b>coletiva de imprensa</b></span> acontece!<br>" +
            "Se o tempo esgotar, a cidade piora automaticamente.</body></html>");

        tituloInfo.add(lblNivel, BorderLayout.NORTH);
        tituloInfo.add(lblMeta,  BorderLayout.CENTER);

        // Categorias de ação (ícones)
        JPanel categorias = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        categorias.setOpaque(false);
        categorias.add(criarCategoria("🚧", "INFRAESTRUTURA"));
        categorias.add(criarCategoria("🌳", "MEIO AMBIENTE"));
        categorias.add(criarCategoria("❤️", "BEM-ESTAR"));

        esqRod.add(tituloInfo, BorderLayout.CENTER);
        esqRod.add(categorias, BorderLayout.SOUTH);

        // ── Direita: turno atual + botão ─────────────────────────────────────
        JPanel dirRod = new JPanel(new BorderLayout(0, 8));
        dirRod.setOpaque(false);
        dirRod.setPreferredSize(new Dimension(200, 0));

        // Card de turno
        JPanel cardTurno = new JPanel(new BorderLayout(0, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(22, 30, 44));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(new Color(40, 55, 80));
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10));
                g2.dispose();
            }
        };
        cardTurno.setOpaque(false);
        cardTurno.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel lblTurnoTitulo = new JLabel("TURNO ATUAL");
        lblTurnoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTurnoTitulo.setForeground(Constantes.COR_TEXTO_DIM);

        lblTurnoNum = new JLabel("1 / 3");
        lblTurnoNum.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTurnoNum.setForeground(Color.WHITE);

        JLabel lblTempoRestante = new JLabel("Tempo restante");
        lblTempoRestante.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblTempoRestante.setForeground(Constantes.COR_TEXTO_DIM);

        barraTurno = new JProgressBar(0, 3) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(35, 45, 60));
                g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),6,6));
                int fw = (int)(getWidth() * getValue() / (double)getMaximum());
                if (fw > 0) {
                    g2.setColor(Constantes.COR_VERDE);
                    g2.fill(new RoundRectangle2D.Float(0,0,fw,getHeight(),6,6));
                }
                g2.dispose();
            }
        };
        barraTurno.setOpaque(false);
        barraTurno.setBorderPainted(false);
        barraTurno.setPreferredSize(new Dimension(0, 8));
        barraTurno.setValue(1);

        // Ícone calendário
        JPanel turnoTop = new JPanel(new BorderLayout());
        turnoTop.setOpaque(false);
        JLabel calIcon = new JLabel("📅  ");
        calIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        turnoTop.add(calIcon,        BorderLayout.WEST);
        turnoTop.add(lblTurnoNum,    BorderLayout.CENTER);

        cardTurno.add(lblTurnoTitulo,  BorderLayout.NORTH);
        cardTurno.add(turnoTop,        BorderLayout.CENTER);
        cardTurno.add(lblTempoRestante,BorderLayout.SOUTH);

        // Botão TOMAR DECISÃO
        JButton btnDecisao = new JButton("⬛  TOMAR DECISÃO") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover()
                        ? Constantes.COR_VERDE.darker()
                        : Constantes.COR_VERDE;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnDecisao.setOpaque(false);
        btnDecisao.setContentAreaFilled(false);
        btnDecisao.setBorderPainted(false);
        btnDecisao.setForeground(Color.WHITE);
        btnDecisao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDecisao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDecisao.setPreferredSize(new Dimension(0, 42));
        btnDecisao.addActionListener(e -> aoTomarDecisao());

        dirRod.add(cardTurno,  BorderLayout.CENTER);
        dirRod.add(barraTurno, BorderLayout.SOUTH);

        // ── Log compacto ──────────────────────────────────────────────────────
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Segoe UI Mono", Font.PLAIN, 9));
        areaLog.setForeground(new Color(80, 90, 110));
        areaLog.setBackground(new Color(15, 20, 30));
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);
        JScrollPane scLog = new JScrollPane(areaLog);
        scLog.setBorder(null);
        scLog.setOpaque(false);
        scLog.getViewport().setOpaque(false);
        scLog.setPreferredSize(new Dimension(140, 0));

        rodape.add(esqRod,  BorderLayout.CENTER);
        rodape.add(dirRod,  BorderLayout.EAST);
        rodape.add(scLog,   BorderLayout.WEST);

        // ── Botão grande na parte inferior ────────────────────────────────────
        JPanel painelBtn = new JPanel(new BorderLayout());
        painelBtn.setBackground(new Color(10, 14, 20));
        painelBtn.setBorder(new EmptyBorder(6, 16, 6, 16));
        painelBtn.add(btnDecisao, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout(0, 0));
        wrapper.setOpaque(false);
        wrapper.add(rodape,    BorderLayout.CENTER);
        wrapper.add(painelBtn, BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel criarCategoria(String emoji, String nome) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);

        JLabel ico = new JLabel(emoji, SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        JLabel lbl = new JLabel(nome, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lbl.setForeground(new Color(160, 170, 185));

        p.add(ico, BorderLayout.CENTER);
        p.add(lbl, BorderLayout.SOUTH);
        return p;
    }

    // ── Lógica de turno ───────────────────────────────────────────────────────
    private void aoTomarDecisao() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (controller.isHoraColetiva()) {
            adicionarLog("🎤 COLETIVA convocada!");
            PerguntaColetiva pergunta = controller.getProximaColetiva();
            DialogoColetiva dlg = new DialogoColetiva(frame, pergunta, Constantes.TEMPO_COLETIVA);
            dlg.setVisible(true);
            if (dlg.isTempoEsgotado()) {
                controller.aplicarPenalidadeColetivaSemResposta();
                adicionarLog("⏰ Silêncio! Popularidade despencou.");
            } else {
                PerguntaColetiva.RespostaColetiva resp = dlg.getRespostaEscolhida();
                if (resp != null) {
                    controller.executarColetiva(resp);
                    adicionarLog("🎤 Coletiva: " + resp.getTipo().name());
                }
            }
            atualizarUI();
            verificarEstadoJogo();
            return;
        }

        Decisao decisao = controller.getProximaDecisao();
        if (decisao == null) return;

        DialogoDecisao dlg = new DialogoDecisao(frame, decisao, Constantes.TEMPO_DECISAO);
        dlg.setVisible(true);

        if (dlg.isTempoEsgotado()) {
            controller.aplicarPenalidadeTempo();
            adicionarLog("⏰ Tempo esgotado! Cidade piorou.");
        } else {
            Decisao.OpcaoDecisao opcao = dlg.getOpcaoEscolhida();
            if (opcao != null) {
                controller.executarAcao(opcao.getAcao());
                adicionarLog("✅ " + opcao.getAcao().getNome());
                String ev = controller.getMensagemUltimoEvento();
                if (ev != null) {
                    adicionarLog("⚠ Evento ocorreu!");
                    JOptionPane.showMessageDialog(this, ev, "⚠ Evento!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        atualizarUI();
        verificarEstadoJogo();
    }

    private void verificarEstadoJogo() {
        if (controller.verificarDerrota()) {
            if (onDerrota != null) onDerrota.run();
            return;
        }
        if (controller.verificarVitoriaNivel()) {
            if (controller.isUltimoNivel()) {
                if (onVitoria != null) onVitoria.run();
            } else {
                JOptionPane.showMessageDialog(this,
                    "<html><center><b style='font-size:14px;color:#2ecc71'>🎉 Nível Concluído!</b><br><br>" +
                    "Você atingiu a meta ambiental!<br>" +
                    "<b>Avançando para o próximo nível...</b></center></html>",
                    "Nível Concluído!", JOptionPane.INFORMATION_MESSAGE);
                controller.avancarNivel();
                carregarNivel();
            }
        }
    }

    private void carregarNivel() {
        Nivel nivel = controller.getNivelAtual();
        adicionarLog("▶ Nível " + nivel.getNumero() + ": " + nivel.getTitulo());
        atualizarUI();
    }

    private void atualizarUI() {
        Cidade cidade = controller.getCidade();
        Nivel  nivel  = controller.getNivelAtual();

        // Indicadores
        painelIndicadores.atualizar(cidade.getIndicadores(), cidade.getPopularidade());

        // Meta
        StringBuilder sb = new StringBuilder("Meta → ");
        for (TipoIndicador t : nivel.getFoco()) {
            double pct = cidade.getIndicador(t).getPercentualMelhora();
            sb.append(t.getNome())
              .append(String.format(": %.0f%% / %.0f%%   ", pct, nivel.getMeta()));
        }
        painelIndicadores.setMeta(sb.toString());

        // Nível no rodapé
        lblNivel.setText("♻️  PRÓXIMA AÇÃO  —  Nível " + nivel.getNumero() + ": " + nivel.getTitulo());

        // Atualiza info de coletivas restantes
        int coletivasRestantes = controller.getColetivasRestantes();
        String infoColetiva = coletivasRestantes > 0
            ? "A cada 3 turnos, uma <span style='color:#f0883e'><b>coletiva de imprensa</b></span> acontece! (<b style='color:#f0883e'>" + coletivasRestantes + " restante(s)</b>)<br>"
            : "Todas as <b style='color:#2ecc71'>3 coletivas</b> já foram realizadas!<br>";
        lblMeta.setText("<html><body style='width:360px;color:#8b949e;font-size:11px'>" +
            "No turno você toma uma decisão contra o relógio.<br>" +
            infoColetiva +
            "Se o tempo esgotar, a cidade piora automaticamente.</body></html>");

        // Turno
        int turno = cidade.getTurnoAtual();
        int posNaSequencia = ((turno - 1) % Constantes.TURNOS_ENTRE_COLETIVAS) + 1;
        lblTurnoNum.setText(posNaSequencia + " / " + Constantes.TURNOS_ENTRE_COLETIVAS);
        barraTurno.setMaximum(Constantes.TURNOS_ENTRE_COLETIVAS);
        barraTurno.setValue(posNaSequencia);

        // Mapa (cidade animada com céu)
        painelMapa.atualizar(cidade.getIndicadores(), nivel.getNumero());

        // Gabinete (prefeito)
        painelGabinete.setPopularidade(cidade.getPopularidade());
    }

    private void adicionarLog(String msg) {
        areaLog.append(msg + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    public void setOnVitoria(Runnable r) { this.onVitoria = r; }
    public void setOnDerrota(Runnable r) { this.onDerrota = r; }
}
