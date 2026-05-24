package metropolesustentavel.model;

import java.util.List;

public class Nivel {
    private final int numero;
    private final String titulo;
    private final String descricao;
    private final List<TipoIndicador> indicadoresFoco;
    private final double metaMelhora;
    private final List<Acao> acoesDisponiveis;

    public Nivel(int numero, String titulo, String descricao,
                 List<TipoIndicador> indicadoresFoco, double metaMelhora,
                 List<Acao> acoesDisponiveis) {
        this.numero            = numero;
        this.titulo            = titulo;
        this.descricao         = descricao;
        this.indicadoresFoco   = indicadoresFoco;
        this.metaMelhora       = metaMelhora;
        this.acoesDisponiveis  = acoesDisponiveis;
    }

    public boolean verificarVitoria(Cidade cidade) {
        for (TipoIndicador t : indicadoresFoco)
            if (cidade.getIndicador(t).getPercentualMelhora() < metaMelhora) return false;
        return true;
    }

    public int getNumero()                   { return numero; }
    public String getTitulo()                { return titulo; }
    public String getDescricao()             { return descricao; }
    public List<TipoIndicador> getFoco()     { return indicadoresFoco; }
    public double getMeta()                  { return metaMelhora; }
    public List<Acao> getAcoesDisponiveis()  { return acoesDisponiveis; }
}
