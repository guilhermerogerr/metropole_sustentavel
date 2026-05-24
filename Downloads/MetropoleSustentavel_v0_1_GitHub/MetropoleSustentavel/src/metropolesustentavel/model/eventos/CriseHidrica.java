package metropolesustentavel.model.eventos;
import metropolesustentavel.model.*;
public class CriseHidrica extends Evento {
    public CriseHidrica() { super("Crise Hídrica", "Escassez de água reduz o abastecimento.", 2, 0.20); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.SATISFACAO, -20.0);
        efeitos.put(TipoIndicador.ENERGIA,    -15.0);
        efeitos.put(TipoIndicador.RESIDUOS,   +10.0);
    }
}
