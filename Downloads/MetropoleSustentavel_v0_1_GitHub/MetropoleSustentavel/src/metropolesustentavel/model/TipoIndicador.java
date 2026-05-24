package metropolesustentavel.model;

public enum TipoIndicador {
    POLUICAO("Poluição", "🌫️"),
    ENERGIA("Eficiência Energética", "⚡"),
    MOBILIDADE("Mobilidade Urbana", "🚌"),
    RESIDUOS("Gestão de Resíduos", "♻️"),
    SATISFACAO("Satisfação Popular", "😊"),
    SUSTENTABILIDADE("Sustentabilidade", "🌿");

    private final String nome;
    private final String icone;

    TipoIndicador(String nome, String icone) {
        this.nome  = nome;
        this.icone = icone;
    }

    public String getNome()  { return nome; }
    public String getIcone() { return icone; }
}
