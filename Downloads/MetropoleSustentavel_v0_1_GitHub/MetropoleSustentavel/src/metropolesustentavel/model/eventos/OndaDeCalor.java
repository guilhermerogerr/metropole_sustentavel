package metropolesustentavel.model.eventos;
import metropolesustentavel.model.*;
public class OndaDeCalor extends Evento {
    public OndaDeCalor() { super("Onda de Calor", "Temperaturas recordes sobrecarregam a rede elétrica.", 2, 0.20); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.ENERGIA,    -20.0);
        efeitos.put(TipoIndicador.SATISFACAO, -15.0);
        efeitos.put(TipoIndicador.POLUICAO,    +8.0);
    }
}
