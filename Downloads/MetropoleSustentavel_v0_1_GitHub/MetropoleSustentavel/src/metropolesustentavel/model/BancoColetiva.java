package metropolesustentavel.model;

import java.util.*;

public class BancoColetiva {

    public static List<PerguntaColetiva> getPerguntas() {
        List<PerguntaColetiva> lista = new ArrayList<>();

        lista.add(new PerguntaColetiva("Ana Lima · Jornal da Cidade",
            "Prefeito, os rios continuam poluídos e a população está adoecendo.\nO que o senhor está fazendo a respeito?",
            List.of(
                new PerguntaColetiva.RespostaColetiva(
                    "✅ \"Investimos R$80mi em saneamento. Em 60 dias os rios estarão limpos.\"",
                    PerguntaColetiva.TipoResposta.BOA,
                    "A imprensa repercute positivamente. A população confia na gestão.",
                    Map.of(TipoIndicador.SATISFACAO, +12.0, TipoIndicador.RESIDUOS, -10.0), +15.0),
                new PerguntaColetiva.RespostaColetiva(
                    "⚠️ \"Estamos analisando todas as alternativas com nossos técnicos.\"",
                    PerguntaColetiva.TipoResposta.POLITICA,
                    "Resposta evasiva. A imprensa critica a falta de comprometimento.",
                    Map.of(TipoIndicador.SATISFACAO, -5.0), -5.0),
                new PerguntaColetiva.RespostaColetiva(
                    "❌ \"A mídia exagera. Os rios estão dentro dos padrões normais.\"",
                    PerguntaColetiva.TipoResposta.RUIM,
                    "Escândalo! Vídeos de rios sujos viralizam. A rejeição dispara.",
                    Map.of(TipoIndicador.SATISFACAO, -20.0, TipoIndicador.RESIDUOS, +10.0), -25.0))));

        lista.add(new PerguntaColetiva("Carlos Melo · TV Verde",
            "O transporte público colapsou. Trabalhadores perdem 3 horas\nno trânsito por dia. Quando isso vai mudar?",
            List.of(
                new PerguntaColetiva.RespostaColetiva(
                    "✅ \"Assinaremos hoje o contrato para nova linha de metrô. Prazo: 18 meses.\"",
                    PerguntaColetiva.TipoResposta.BOA,
                    "Anúncio histórico! Confiança na prefeitura aumenta.",
                    Map.of(TipoIndicador.MOBILIDADE, +15.0, TipoIndicador.SATISFACAO, +10.0), +18.0),
                new PerguntaColetiva.RespostaColetiva(
                    "⚠️ \"O problema do trânsito é nacional. Precisamos de verbas federais.\"",
                    PerguntaColetiva.TipoResposta.POLITICA,
                    "Culpa jogada no governo federal. Sem efeito prático.",
                    Map.of(TipoIndicador.MOBILIDADE, -3.0), -8.0),
                new PerguntaColetiva.RespostaColetiva(
                    "❌ \"Quem não quer enfrentar trânsito que acorde mais cedo.\"",
                    PerguntaColetiva.TipoResposta.RUIM,
                    "Fala vira meme nacional. Popularidade despenca.",
                    Map.of(TipoIndicador.SATISFACAO, -25.0, TipoIndicador.MOBILIDADE, -8.0), -30.0))));

        lista.add(new PerguntaColetiva("Beatriz Souza · Rádio Metrópole",
            "A qualidade do ar piorou 40% este ano. Crianças e idosos\nestão sendo internados. O que será feito?",
            List.of(
                new PerguntaColetiva.RespostaColetiva(
                    "✅ \"Implementaremos taxa de carbono e rodízio já na próxima semana.\"",
                    PerguntaColetiva.TipoResposta.BOA,
                    "Medidas concretas e rápidas. A saúde pública agradece.",
                    Map.of(TipoIndicador.POLUICAO, -18.0, TipoIndicador.SATISFACAO, +8.0), +12.0),
                new PerguntaColetiva.RespostaColetiva(
                    "⚠️ \"Vamos criar uma comissão especial para estudar o problema.\"",
                    PerguntaColetiva.TipoResposta.POLITICA,
                    "Mais uma comissão. A população já não acredita nisso.",
                    Map.of(TipoIndicador.SATISFACAO, -8.0), -10.0),
                new PerguntaColetiva.RespostaColetiva(
                    "❌ \"A poluição vem de outras cidades. Nossa gestão não tem culpa.\"",
                    PerguntaColetiva.TipoResposta.RUIM,
                    "Cientistas e médicos refutam publicamente. Crise de credibilidade.",
                    Map.of(TipoIndicador.POLUICAO, +12.0, TipoIndicador.SATISFACAO, -22.0), -28.0))));

        lista.add(new PerguntaColetiva("Roberto Faria · Portal Ambiental",
            "Uma construtora quer derrubar o último parque da cidade\npara construir um shopping. O senhor vai autorizar?",
            List.of(
                new PerguntaColetiva.RespostaColetiva(
                    "✅ \"Não autorizo. O parque vira área de proteção permanente.\"",
                    PerguntaColetiva.TipoResposta.BOA,
                    "Ambientalistas celebram. Área verde protegida por lei.",
                    Map.of(TipoIndicador.SATISFACAO, +15.0, TipoIndicador.POLUICAO, -8.0), +20.0),
                new PerguntaColetiva.RespostaColetiva(
                    "⚠️ \"Estamos em diálogo para encontrar uma solução equilibrada.\"",
                    PerguntaColetiva.TipoResposta.POLITICA,
                    "Suspeita de propina circula nas redes sociais.",
                    Map.of(TipoIndicador.SATISFACAO, -10.0), -12.0),
                new PerguntaColetiva.RespostaColetiva(
                    "❌ \"O desenvolvimento econômico precisa ser prioridade.\"",
                    PerguntaColetiva.TipoResposta.RUIM,
                    "Protesto de milhares nas ruas. O parque vira símbolo de resistência.",
                    Map.of(TipoIndicador.SATISFACAO, -25.0, TipoIndicador.POLUICAO, +15.0), -30.0))));

        lista.add(new PerguntaColetiva("Mariana Costa · Jornal Nacional",
            "Lixo hospitalar está sendo descartado irregularmente\nperto de escolas. Quem é o responsável?",
            List.of(
                new PerguntaColetiva.RespostaColetiva(
                    "✅ \"Assumo a responsabilidade. Já afastei o secretário e abrimos inquérito.\"",
                    PerguntaColetiva.TipoResposta.BOA,
                    "Transparência valorizada pela população mesmo diante do erro.",
                    Map.of(TipoIndicador.SATISFACAO, +8.0, TipoIndicador.RESIDUOS, -12.0), +10.0),
                new PerguntaColetiva.RespostaColetiva(
                    "⚠️ \"Estamos apurando os fatos. Não vou antecipar conclusões.\"",
                    PerguntaColetiva.TipoResposta.POLITICA,
                    "Resposta padrão que não satisfaz ninguém.",
                    Map.of(TipoIndicador.SATISFACAO, -6.0), -8.0),
                new PerguntaColetiva.RespostaColetiva(
                    "❌ \"Isso é sabotagem política da oposição para me prejudicar.\"",
                    PerguntaColetiva.TipoResposta.RUIM,
                    "Vídeos das provas aparecem ao vivo. Situação insustentável.",
                    Map.of(TipoIndicador.SATISFACAO, -28.0, TipoIndicador.RESIDUOS, +15.0), -35.0))));

        Collections.shuffle(lista);
        return lista;
    }
}
