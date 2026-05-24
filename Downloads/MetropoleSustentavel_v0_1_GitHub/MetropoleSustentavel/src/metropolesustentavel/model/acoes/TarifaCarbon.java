package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class TarifaCarbon extends Acao {
    public TarifaCarbon() { super("Implementar Taxa de Carbono", "Tributa empresas poluidoras.", Dificuldade.MEDIO); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.POLUICAO, -15.0);
        efeitos.put(TipoIndicador.ENERGIA,  +10.0);
        efeitosSecundarios.put(TipoIndicador.SATISFACAO, -8.0);
    }
}
