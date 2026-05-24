package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class CicloviasExpansao extends Acao {
    public CicloviasExpansao() { super("Expandir Rede de Ciclovias 🚲", "Cria corredores de bicicleta seguros.", Dificuldade.FACIL); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.MOBILIDADE, +10.0);
        efeitos.put(TipoIndicador.SATISFACAO,  +8.0);
        efeitosSecundarios.put(TipoIndicador.POLUICAO, -6.0);
    }
}
