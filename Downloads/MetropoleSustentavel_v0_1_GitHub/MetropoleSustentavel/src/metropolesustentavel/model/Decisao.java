package metropolesustentavel.model;

import java.util.List;

public class Decisao {
    private final String titulo;
    private final String situacao;
    private final String imagemEmoji;
    private final List<OpcaoDecisao> opcoes;

    public Decisao(String titulo, String situacao, String imagemEmoji, List<OpcaoDecisao> opcoes) {
        this.titulo      = titulo;
        this.situacao    = situacao;
        this.imagemEmoji = imagemEmoji;
        this.opcoes      = opcoes;
    }

    public String getTitulo()              { return titulo; }
    public String getSituacao()            { return situacao; }
    public String getImagemEmoji()         { return imagemEmoji; }
    public List<OpcaoDecisao> getOpcoes()  { return opcoes; }

    public static class OpcaoDecisao {
        private final String texto;
        private final String consequencia;
        private final Acao acao;

        public OpcaoDecisao(String texto, String consequencia, Acao acao) {
            this.texto        = texto;
            this.consequencia = consequencia;
            this.acao         = acao;
        }

        public String getTexto()        { return texto; }
        public String getConsequencia() { return consequencia; }
        public Acao getAcao()           { return acao; }
    }
}
