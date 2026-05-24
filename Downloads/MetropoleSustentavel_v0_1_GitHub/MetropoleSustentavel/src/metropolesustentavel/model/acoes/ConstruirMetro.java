package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class ConstruirMetro extends Acao {
    public ConstruirMetro() { super("Construir Estação de Metrô", "Expande o transporte público subterrâneo.", Dificuldade.DIFICIL); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.MOBILIDADE, +20.0);
        efeitos.put(TipoIndicador.POLUICAO,   -15.0);
        efeitosSecundarios.put(TipoIndicador.SATISFACAO, +10.0);
    }
}
