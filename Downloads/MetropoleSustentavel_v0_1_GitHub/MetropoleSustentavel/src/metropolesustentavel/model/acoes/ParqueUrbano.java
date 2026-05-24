package metropolesustentavel.model.acoes;
import metropolesustentavel.model.*;
public class ParqueUrbano extends Acao {
    public ParqueUrbano() { super("Criar Parque Urbano 🌳", "Transforma áreas degradadas em parques com vegetação nativa.", Dificuldade.MEDIO); }
    protected void configurarEfeitos() {
        efeitos.put(TipoIndicador.SATISFACAO, +15.0);
        efeitos.put(TipoIndicador.POLUICAO,   -8.0);
        efeitosSecundarios.put(TipoIndicador.RESIDUOS, -5.0);
    }
}
