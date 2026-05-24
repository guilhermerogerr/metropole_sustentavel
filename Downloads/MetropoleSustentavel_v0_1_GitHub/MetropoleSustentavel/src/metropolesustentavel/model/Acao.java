package metropolesustentavel.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Acao implements Aplicavel {
    protected final String nome;
    protected final String descricao;
    protected final Dificuldade dificuldade;
    protected final Map<TipoIndicador, Double> efeitos;
    protected final Map<TipoIndicador, Double> efeitosSecundarios;

    public Acao(String nome, String descricao, Dificuldade dificuldade) {
        this.nome               = nome;
        this.descricao          = descricao;
        this.dificuldade        = dificuldade;
        this.efeitos            = new HashMap<>();
        this.efeitosSecundarios = new HashMap<>();
        configurarEfeitos();
    }

    protected abstract void configurarEfeitos();

    @Override
    public void aplicar(Cidade cidade) {
        for (Map.Entry<TipoIndicador, Double> e : efeitos.entrySet())
            cidade.getIndicador(e.getKey()).ajustar(e.getValue());
        for (Map.Entry<TipoIndicador, Double> e : efeitosSecundarios.entrySet())
            cidade.getIndicador(e.getKey()).ajustar(e.getValue());
    }

    @Override
    public String getDescricaoImpacto() {
        StringBuilder sb = new StringBuilder("<html><body style='width:260px'>");
        sb.append("<b>").append(nome).append("</b><br><i>").append(descricao).append("</i><br><br>");
        sb.append("<u>Efeitos:</u><br>");
        for (Map.Entry<TipoIndicador, Double> e : efeitos.entrySet())
            sb.append(e.getValue() > 0 ? "▲ " : "▼ ").append(e.getKey().getNome())
              .append(String.format(": %+.0f<br>", e.getValue()));
        sb.append("</body></html>");
        return sb.toString();
    }

    public String getNome()             { return nome; }
    public String getDescricao()        { return descricao; }
    public Dificuldade getDificuldade() { return dificuldade; }
    public Map<TipoIndicador, Double> getEfeitos() { return new HashMap<>(efeitos); }
}
