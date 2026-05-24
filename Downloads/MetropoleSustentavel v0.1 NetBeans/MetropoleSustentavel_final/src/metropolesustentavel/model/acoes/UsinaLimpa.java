package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class UsinaLimpa extends Acao {
    public UsinaLimpa() { super("Criar Usina de Energia Limpa", "Instala painéis solares e turbinas eólicas.", Dificuldade.DIFICIL); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.ENERGIA,  +25.0);
        efeitos.put(TipoIndicador.POLUICAO, -20.0);
        efeitosSecundarios.put(TipoIndicador.SATISFACAO, +8.0);
    }
}
