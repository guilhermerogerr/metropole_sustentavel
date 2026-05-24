package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class ColetaSeletiva extends Acao {
    public ColetaSeletiva() { super("Melhorar Coleta Seletiva ♻️", "Implanta postos de reciclagem e campanhas.", Dificuldade.MEDIO); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.RESIDUOS,  -20.0);
        efeitos.put(TipoIndicador.SATISFACAO, +12.0);
        efeitosSecundarios.put(TipoIndicador.POLUICAO, -8.0);
    }
}
