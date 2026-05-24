package metropolesustentavel.model;

public enum Dificuldade {
    FACIL("Fácil", 1, new java.awt.Color(46, 204, 113)),
    MEDIO("Médio", 2, new java.awt.Color(240, 136, 62)),
    DIFICIL("Difícil", 3, new java.awt.Color(231, 76, 60));

    private final String nome;
    private final int custo;
    private final java.awt.Color cor;

    Dificuldade(String nome, int custo, java.awt.Color cor) {
        this.nome  = nome;
        this.custo = custo;
        this.cor   = cor;
    }

    public String getNome()        { return nome; }
    public int getCusto()          { return custo; }
    public java.awt.Color getCor() { return cor; }
}
