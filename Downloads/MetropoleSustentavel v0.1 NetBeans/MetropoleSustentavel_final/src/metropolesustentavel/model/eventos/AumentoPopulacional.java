package metropolesustentavel.model.eventos;
import metropolesustentavel.model.*;
public class AumentoPopulacional extends Evento {
    public AumentoPopulacional() { super("Explosão Populacional", "Migração intensa pressiona toda a infraestrutura.", 1, 0.25); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.RESIDUOS,  +18.0);
        efeitos.put(TipoIndicador.POLUICAO,  +12.0);
        efeitos.put(TipoIndicador.MOBILIDADE,-10.0);
        efeitos.put(TipoIndicador.ENERGIA,    -8.0);
    }
}
