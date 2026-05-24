package metropolesustentavel.model;

import java.util.List;
import java.util.Map;

public class PerguntaColetiva {
    public enum TipoResposta { BOA, POLITICA, RUIM }

    private final String jornalista;
    private final String pergunta;
    private final List<RespostaColetiva> respostas;

    public PerguntaColetiva(String jornalista, String pergunta, List<RespostaColetiva> respostas) {
        this.jornalista = jornalista;
        this.pergunta   = pergunta;
        this.respostas  = respostas;
    }

    public String getJornalista()                    { return jornalista; }
    public String getPergunta()                      { return pergunta; }
    public List<RespostaColetiva> getRespostas()     { return respostas; }

    public static class RespostaColetiva {
        private final String texto;
        private final TipoResposta tipo;
        private final String consequencia;
        private final Map<TipoIndicador, Double> efeitos;
        private final double deltaPopularidade;

        public RespostaColetiva(String texto, TipoResposta tipo, String consequencia,
                                Map<TipoIndicador, Double> efeitos, double deltaPopularidade) {
            this.texto             = texto;
            this.tipo              = tipo;
            this.consequencia      = consequencia;
            this.efeitos           = efeitos;
            this.deltaPopularidade = deltaPopularidade;
        }

        public String getText()                          { return texto; }
        public TipoResposta getTipo()                    { return tipo; }
        public String getConsequencia()                  { return consequencia; }
        public Map<TipoIndicador, Double> getEfeitos()   { return efeitos; }
        public double getDeltaPopularidade()             { return deltaPopularidade; }
    }
}
