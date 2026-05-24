package metropolesustentavel.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Evento implements Aplicavel {
    protected final String nome;
    protected final String descricao;
    protected final int duracao;
    protected final double probabilidade;
    protected final Map<TipoIndicador, Double> efeitos;

    public Evento(String nome, String descricao, int duracao, double probabilidade) {
        this.nome          = nome;
        this.descricao     = descricao;
        this.duracao       = duracao;
        this.probabilidade = probabilidade;
        this.efeitos       = new HashMap<>();
        configurarEfeitos();
    }

    protected abstract void configurarEfeitos();

    @Override
    public void aplicar(Cidade cidade) {
        for (Map.Entry<TipoIndicador, Double> e : efeitos.entrySet())
            cidade.getIndicador(e.getKey()).ajustar(e.getValue());
    }

    @Override
    public String getDescricaoImpacto() {
        StringBuilder sb = new StringBuilder("<html><body style='width:260px'>");
        sb.append("<b style='color:#e74c3c'>⚠ EVENTO: ").append(nome).append("</b><br>");
        sb.append("<i>").append(descricao).append("</i><br><br>");
        for (Map.Entry<TipoIndicador, Double> e : efeitos.entrySet())
            sb.append(e.getValue() > 0 ? "▲ " : "▼ ").append(e.getKey().getNome())
              .append(String.format(": %+.0f<br>", e.getValue()));
        sb.append("</body></html>");
        return sb.toString();
    }

    public String getNome()          { return nome; }
    public String getDescricao()     { return descricao; }
    public double getProbabilidade() { return probabilidade; }
}
