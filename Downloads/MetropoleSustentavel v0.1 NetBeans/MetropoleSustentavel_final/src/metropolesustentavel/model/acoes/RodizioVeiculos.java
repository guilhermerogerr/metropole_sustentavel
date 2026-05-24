package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class RodizioVeiculos extends Acao {
    public RodizioVeiculos() { super("Implementar Rodízio de Veículos", "Restringe circulação por placa em horários de pico.", Dificuldade.FACIL); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.POLUICAO,  -12.0);
        efeitos.put(TipoIndicador.MOBILIDADE, +8.0);
        efeitosSecundarios.put(TipoIndicador.SATISFACAO, -5.0);
    }
}
