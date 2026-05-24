package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class TratamentoEsgoto extends Acao {
    public TratamentoEsgoto() { super("Modernizar Tratamento de Esgoto ♻️", "Constrói ETEs modernas para tratar 100% do esgoto.", Dificuldade.DIFICIL); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.RESIDUOS,  -18.0);
        efeitos.put(TipoIndicador.SATISFACAO, +12.0);
        efeitosSecundarios.put(TipoIndicador.POLUICAO, -10.0);
        efeitosSecundarios.put(TipoIndicador.ENERGIA,   -8.0);
    }
}
