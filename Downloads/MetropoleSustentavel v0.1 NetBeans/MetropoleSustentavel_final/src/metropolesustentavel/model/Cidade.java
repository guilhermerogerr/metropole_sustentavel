package metropolesustentavel.model;

import java.util.*;

public class Cidade {
    private final Map<TipoIndicador, Indicador> indicadores;
    private Nivel nivelAtual;
    private int turnoAtual;
    private int pontuacao;
    private double popularidade;
    private final List<String> logEventos;

    public Cidade() {
        indicadores  = new EnumMap<>(TipoIndicador.class);
        logEventos   = new ArrayList<>();
        turnoAtual   = 1;
        pontuacao    = 0;
        popularidade = 60.0;
        inicializarIndicadores();
    }

    private void inicializarIndicadores() {
        indicadores.put(TipoIndicador.POLUICAO,        new Indicador(TipoIndicador.POLUICAO,        75));
        indicadores.put(TipoIndicador.ENERGIA,          new Indicador(TipoIndicador.ENERGIA,          30));
        indicadores.put(TipoIndicador.MOBILIDADE,       new Indicador(TipoIndicador.MOBILIDADE,       35));
        indicadores.put(TipoIndicador.RESIDUOS,         new Indicador(TipoIndicador.RESIDUOS,         70));
        indicadores.put(TipoIndicador.SATISFACAO,       new Indicador(TipoIndicador.SATISFACAO,       40));
        indicadores.put(TipoIndicador.SUSTENTABILIDADE, new Indicador(TipoIndicador.SUSTENTABILIDADE, 25));
    }

    public void aplicarAcao(Acao acao) {
        acao.aplicar(this);
        atualizarSustentabilidade();
        logEventos.add("[Turno " + turnoAtual + "] Ação: " + acao.getNome());
        pontuacao += acao.getDificuldade().getCusto() * 10;
        turnoAtual++;
    }

    public void aplicarEvento(Evento evento) {
        evento.aplicar(this);
        atualizarSustentabilidade();
        logEventos.add("[Turno " + turnoAtual + "] Evento: " + evento.getNome());
    }

    /** Penalidade por tempo esgotado — cidade piora automaticamente */
    public void aplicarPenalidadeTempo() {
        indicadores.get(TipoIndicador.SATISFACAO).ajustar(-8.0);
        indicadores.get(TipoIndicador.POLUICAO).ajustar(+6.0);
        indicadores.get(TipoIndicador.RESIDUOS).ajustar(+5.0);
        ajustarPopularidade(-10.0);
        atualizarSustentabilidade();
        logEventos.add("[Turno " + turnoAtual + "] ⏰ Tempo esgotado! Cidade piorou.");
        turnoAtual++;
    }

    public void ajustarPopularidade(double delta) {
        this.popularidade = Math.max(0, Math.min(100, this.popularidade + delta));
    }

    public void aplicarEfeitosColetiva(Map<TipoIndicador, Double> efeitos) {
        for (Map.Entry<TipoIndicador, Double> e : efeitos.entrySet())
            indicadores.get(e.getKey()).ajustar(e.getValue());
        atualizarSustentabilidade();
    }

    private void atualizarSustentabilidade() {
        double media =
            (100 - indicadores.get(TipoIndicador.POLUICAO).getValor())  * 0.25 +
            indicadores.get(TipoIndicador.ENERGIA).getValor()            * 0.20 +
            indicadores.get(TipoIndicador.MOBILIDADE).getValor()         * 0.20 +
            (100 - indicadores.get(TipoIndicador.RESIDUOS).getValor())   * 0.20 +
            indicadores.get(TipoIndicador.SATISFACAO).getValor()         * 0.15;
        indicadores.get(TipoIndicador.SUSTENTABILIDADE)
                   .ajustar(media - indicadores.get(TipoIndicador.SUSTENTABILIDADE).getValor());
    }

    public boolean verificarVitoria()  { return nivelAtual != null && nivelAtual.verificarVitoria(this); }

    public boolean verificarDerrota() {
        return indicadores.get(TipoIndicador.SATISFACAO).getValor() <= 5
            || indicadores.get(TipoIndicador.SUSTENTABILIDADE).getValor() <= 5
            || popularidade <= 0;
    }

    public String getMotivoDerrota() {
        if (popularidade <= 0)
            return "Você foi destituído!\nSua popularidade chegou a zero.";
        if (indicadores.get(TipoIndicador.SATISFACAO).getValor() <= 5)
            return "Colapso social!\nA satisfação popular chegou ao limite.";
        return "Colapso ambiental!\nA sustentabilidade chegou a zero.";
    }

    public Indicador getIndicador(TipoIndicador t) { return indicadores.get(t); }
    public Map<TipoIndicador, Indicador> getIndicadores() { return indicadores; }
    public Nivel getNivelAtual()        { return nivelAtual; }
    public void setNivelAtual(Nivel n)  { this.nivelAtual = n; }
    public int getTurnoAtual()          { return turnoAtual; }
    public int getPontuacao()           { return pontuacao; }
    public double getPopularidade()     { return popularidade; }
    public List<String> getLog()        { return new ArrayList<>(logEventos); }
}
