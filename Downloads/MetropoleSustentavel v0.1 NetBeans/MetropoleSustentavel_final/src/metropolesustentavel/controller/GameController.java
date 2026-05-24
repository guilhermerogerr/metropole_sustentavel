package metropolesustentavel.controller;

import metropolesustentavel.model.*;
import metropolesustentavel.model.acoes.*;
import metropolesustentavel.util.Constantes;

import java.util.*;

public class GameController {

    private final Cidade cidade;
    private final List<Nivel> niveis;
    private int indiceNivelAtual;
    private final GeradorEventos geradorEventos;
    private String mensagemUltimoEvento;

    // Decisões
    private List<Decisao> decisoesNivel;
    private int indiceDecisao;

    // Coletiva
    private final List<PerguntaColetiva> perguntasColetiva;
    private int indiceColetiva;
    private int totalColetivasRealizadas = 0;
    private static final int MAX_COLETIVAS = 3;

    // Controle de turno
    private int turnosDesdeCriacaoNivel = 0;

    public GameController() {
        this.cidade            = new Cidade();
        this.niveis            = criarNiveis();
        this.indiceNivelAtual  = 0;
        this.geradorEventos    = new GeradorEventos();
        this.perguntasColetiva = BancoColetiva.getPerguntas();
        this.indiceColetiva    = 0;
        cidade.setNivelAtual(niveis.get(0));
        carregarDecisoesNivel();
    }

    private List<Nivel> criarNiveis() {
        List<Nivel> lista = new ArrayList<>();
        lista.add(new Nivel(1, "Despertar Verde ♻️",
            "A cidade enfrenta lixo acumulado e insatisfação popular.",
            List.of(TipoIndicador.RESIDUOS, TipoIndicador.SATISFACAO), Constantes.META_NIVEL_1,
            List.of(new ColetaSeletiva(), new ParqueUrbano(), new RodizioVeiculos(),
                    new CicloviasExpansao(), new TratamentoEsgoto())));
        lista.add(new Nivel(2, "Cidade em Movimento 🚌",
            "O trânsito travou e a poluição atinge recordes.",
            List.of(TipoIndicador.MOBILIDADE, TipoIndicador.POLUICAO), Constantes.META_NIVEL_2,
            List.of(new ConstruirMetro(), new RodizioVeiculos(), new CicloviasExpansao(),
                    new TarifaCarbon(), new ParqueUrbano())));
        lista.add(new Nivel(3, "Energia do Futuro ⚡",
            "O consumo energético é insustentável.",
            List.of(TipoIndicador.ENERGIA, TipoIndicador.SUSTENTABILIDADE), Constantes.META_NIVEL_3,
            List.of(new UsinaLimpa(), new TarifaCarbon(), new ConstruirMetro(),
                    new ColetaSeletiva(), new ParqueUrbano())));
        lista.add(new Nivel(4, "Metrópole Sustentável ♻️🌿",
            "O desafio final: equilibrar TODOS os indicadores!",
            List.of(TipoIndicador.POLUICAO, TipoIndicador.ENERGIA,
                    TipoIndicador.MOBILIDADE, TipoIndicador.SATISFACAO), Constantes.META_NIVEL_4,
            List.of(new ConstruirMetro(), new UsinaLimpa(), new ColetaSeletiva(),
                    new RodizioVeiculos(), new ParqueUrbano(), new CicloviasExpansao(),
                    new TarifaCarbon(), new TratamentoEsgoto())));
        return lista;
    }

    private void carregarDecisoesNivel() {
        decisoesNivel           = BancoDeDecisoes.getDecisoesNivel(indiceNivelAtual + 1);
        indiceDecisao           = 0;
        turnosDesdeCriacaoNivel = 0;
        totalColetivasRealizadas = 0; // zera a cada nível
    }

    public Decisao getProximaDecisao() {
        if (decisoesNivel == null || decisoesNivel.isEmpty()) return null;
        return decisoesNivel.get(indiceDecisao % decisoesNivel.size());
    }

    public PerguntaColetiva getProximaColetiva() {
        if (perguntasColetiva == null || perguntasColetiva.isEmpty()) return null;
        return perguntasColetiva.get(indiceColetiva % perguntasColetiva.size());
    }

    /** Retorna true se é hora de coletiva (a cada 3 turnos, máximo 3 por jogo) */
    public boolean isHoraColetiva() {
        if (totalColetivasRealizadas >= MAX_COLETIVAS) return false;
        return turnosDesdeCriacaoNivel > 0 && turnosDesdeCriacaoNivel % Constantes.TURNOS_ENTRE_COLETIVAS == 0;
    }

    public void executarAcao(Acao acao) {
        cidade.aplicarAcao(acao);
        mensagemUltimoEvento = null;
        indiceDecisao++;
        turnosDesdeCriacaoNivel++;

        Evento evento = geradorEventos.sortearEvento(cidade);
        if (evento != null) {
            cidade.aplicarEvento(evento);
            mensagemUltimoEvento = evento.getDescricaoImpacto();
        }
    }

    public void executarColetiva(PerguntaColetiva.RespostaColetiva resposta) {
        cidade.ajustarPopularidade(resposta.getDeltaPopularidade());
        cidade.aplicarEfeitosColetiva(resposta.getEfeitos());
        indiceColetiva++;
        totalColetivasRealizadas++;
    }

    /** Chamado quando o tempo da decisão esgota */
    public void aplicarPenalidadeTempo() {
        cidade.aplicarPenalidadeTempo();
        indiceDecisao++;
        turnosDesdeCriacaoNivel++;
    }

    /** Chamado quando o tempo da coletiva esgota */
    public void aplicarPenalidadeColetivaSemResposta() {
        // Sem resposta = pior penalidade
        cidade.ajustarPopularidade(-20.0);
        cidade.getIndicador(TipoIndicador.SATISFACAO).ajustar(-12.0);
        indiceColetiva++;
        totalColetivasRealizadas++;
    }

    public boolean verificarVitoriaNivel() { return cidade.verificarVitoria(); }
    public boolean verificarDerrota()      { return cidade.verificarDerrota(); }

    public boolean avancarNivel() {
        if (indiceNivelAtual < niveis.size() - 1) {
            indiceNivelAtual++;
            cidade.setNivelAtual(niveis.get(indiceNivelAtual));
            carregarDecisoesNivel();
            return true;
        }
        return false;
    }

    public boolean isUltimoNivel()          { return indiceNivelAtual >= niveis.size() - 1; }
    public int getColetivasRestantes()      { return MAX_COLETIVAS - totalColetivasRealizadas; }
    public Cidade getCidade()               { return cidade; }
    public Nivel getNivelAtual()            { return cidade.getNivelAtual(); }
    public int getIndiceNivel()             { return indiceNivelAtual; }
    public int getTotalNiveis()             { return niveis.size(); }
    public String getMensagemUltimoEvento() { return mensagemUltimoEvento; }
}
