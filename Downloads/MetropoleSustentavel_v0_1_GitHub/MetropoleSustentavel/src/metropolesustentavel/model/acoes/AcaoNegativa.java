package metropolesustentavel.model.acoes;

import metropolesustentavel.model.*;
import java.util.Map;

/**
 * Ação negativa genérica — usada nas opções ruins das decisões.
 * Recebe os efeitos negativos diretamente no construtor.
 */
public class AcaoNegativa extends Acao {

    private final Map<TipoIndicador, Double> efeitosNegativos;

    public AcaoNegativa(String nome, String descricao, Map<TipoIndicador, Double> efeitos) {
        super(nome, descricao, Dificuldade.FACIL);
        this.efeitosNegativos = efeitos;
    }

    @Override
    protected void configurarEfeitos() {
        // Efeitos definidos via construtor
    }

    @Override
    public void aplicar(Cidade cidade) {
        for (Map.Entry<TipoIndicador, Double> e : efeitosNegativos.entrySet())
            cidade.getIndicador(e.getKey()).ajustar(e.getValue());
        cidade.ajustarPopularidade(-8);
    }

    @Override
    public String getDescricaoImpacto() {
        StringBuilder sb = new StringBuilder("<html><body style='width:260px'>");
        sb.append("<b style='color:#e74c3c'>❌ DECISÃO NEGATIVA</b><br><br>");
        sb.append("<u>Consequências:</u><br>");
        for (Map.Entry<TipoIndicador, Double> e : efeitosNegativos.entrySet())
            sb.append("▼ ").append(e.getKey().getNome())
              .append(String.format(": %+.0f<br>", e.getValue()));
        sb.append("▼ Popularidade: -8");
        sb.append("</body></html>");
        return sb.toString();
    }
}
