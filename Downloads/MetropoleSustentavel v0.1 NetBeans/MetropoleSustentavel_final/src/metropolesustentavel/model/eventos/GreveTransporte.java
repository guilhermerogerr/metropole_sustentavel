package metropolesustentavel.model.eventos;
import metropolesustentavel.model.*;
public class GreveTransporte extends Evento {
    public GreveTransporte() { super("Greve de Transporte", "Motoristas paralisam ônibus e metrô.", 3, 0.15); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.MOBILIDADE, -25.0);
        efeitos.put(TipoIndicador.SATISFACAO, -18.0);
        efeitos.put(TipoIndicador.POLUICAO,   +12.0);
    }
}
