package metropolesustentavel.model;

import metropolesustentavel.model.acoes.*;
import java.util.*;

/**
 * Banco de decisões do jogo.
 * REGRA: toda decisão tem exatamente 3 opções:
 *   A) Boa      — melhora indicadores
 *   B) Neutra   — efeito moderado / troca de indicadores
 *   C) NEGATIVA — piora a cidade (marcada com ❌)
 */
public class BancoDeDecisoes {

    public static List<Decisao> getDecisoesNivel(int nivel) {
        return switch (nivel) {
            case 1  -> nivel1();
            case 2  -> nivel2();
            case 3  -> nivel3();
            default -> nivel4();
        };
    }

    // ── NÍVEL 1 ── Resíduos & Satisfação ────────────────────────────────────
    private static List<Decisao> nivel1() {
        List<Decisao> lista = new ArrayList<>();

        lista.add(new Decisao("Lixo nas Ruas",
            "Os moradores acordam com lixo espalhado pelas calçadas.\n" +
            "A coleta falhou três dias seguidos. A imprensa cobre o caso\n" +
            "e a população está furiosa. O que você decide fazer?",
            "🗑️", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Implementar coleta seletiva imediata ♻️",
                    "Caminhões de reciclagem passam pelos bairros. A situação melhora em dias.",
                    new ColetaSeletiva()),
                new Decisao.OpcaoDecisao(
                    "B) 🌳 Criar parques com compostagem",
                    "Resíduos orgânicos viram adubo. A cidade fica mais verde.",
                    new ParqueUrbano()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Ignorar e não fazer nada — \"o lixo some sozinho\"",
                    "O lixo se acumula ainda mais. Ratos e doenças surgem nos bairros.",
                    new AcaoNegativa("Omissão na Coleta",
                        "Prefeitura ignora crise do lixo.",
                        Map.of(TipoIndicador.RESIDUOS,  +18.0,
                               TipoIndicador.SATISFACAO, -15.0,
                               TipoIndicador.POLUICAO,   +10.0)))
            )));

        lista.add(new Decisao("Aterro Sanitário Lotado",
            "O único aterro atingiu capacidade máxima.\n" +
            "Sem ação, o lixo será despejado em áreas irregulares,\n" +
            "contaminando rios e solos. Prazo: 30 dias.",
            "🏭", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Modernizar tratamento de resíduos ♻️",
                    "Novo centro de triagem inaugurado. Aterro ganha anos de vida útil.",
                    new TratamentoEsgoto()),
                new Decisao.OpcaoDecisao(
                    "B) ♻️ Campanha urgente de reciclagem",
                    "Volume no aterro cai 30% em duas semanas.",
                    new ColetaSeletiva()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Despejar lixo em área de preservação ambiental",
                    "Escândalo ambiental! ONGs protestam e multas chegam à prefeitura.",
                    new AcaoNegativa("Despejo Ilegal",
                        "Lixo jogado em área de preservação.",
                        Map.of(TipoIndicador.RESIDUOS,   +20.0,
                               TipoIndicador.POLUICAO,   +15.0,
                               TipoIndicador.SATISFACAO,  -20.0)))
            )));

        lista.add(new Decisao("Greve da Limpeza Urbana",
            "Funcionários da limpeza cruzam os braços após anos sem reajuste.\n" +
            "O lixo acumula nas ruas e o risco de doenças aumenta.",
            "⚠️", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Negociar reajuste e retomar coleta imediatamente",
                    "Acordo fechado. Funcionários voltam ao trabalho satisfeitos.",
                    new ColetaSeletiva()),
                new Decisao.OpcaoDecisao(
                    "B) 🌳 Organizar mutirões comunitários de limpeza",
                    "Moradores se organizam. A satisfação sobe com o espírito comunitário.",
                    new ParqueUrbano()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Demitir todos os grevistas e não contratar substitutos",
                    "Caos total. Sem garis, o lixo toma as ruas. Crise de saúde pública.",
                    new AcaoNegativa("Demissão em Massa",
                        "Serviço de limpeza paralisado por demissões.",
                        Map.of(TipoIndicador.RESIDUOS,   +22.0,
                               TipoIndicador.SATISFACAO,  -25.0,
                               TipoIndicador.POLUICAO,    +12.0)))
            )));

        lista.add(new Decisao("Enchente no Bairro Popular",
            "Chuvas fortes inundaram o bairro mais populoso da cidade.\n" +
            "O lixo jogado irregularmente entupiu os bueiros.\n" +
            "Moradores perderam móveis e pedem socorro.",
            "🌧️", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Desobstruir bueiros e implantar coleta correta",
                    "Equipes trabalham 24h. A situação é controlada e o bairro recuperado.",
                    new TratamentoEsgoto()),
                new Decisao.OpcaoDecisao(
                    "B) 🌳 Criar parques de retenção de água",
                    "Solução sustentável de longo prazo. Novos parques protegem a cidade.",
                    new ParqueUrbano()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Culpar os moradores pelo lixo e não agir",
                    "Declaração do prefeito vira escândalo. Ninguém resolve o problema.",
                    new AcaoNegativa("Omissão na Enchente",
                        "Prefeitura abandona bairro alagado.",
                        Map.of(TipoIndicador.SATISFACAO,  -28.0,
                               TipoIndicador.RESIDUOS,    +15.0,
                               TipoIndicador.MOBILIDADE,  -10.0)))
            )));

        Collections.shuffle(lista);
        return lista;
    }

    // ── NÍVEL 2 ── Mobilidade & Poluição ─────────────────────────────────────
    private static List<Decisao> nivel2() {
        List<Decisao> lista = new ArrayList<>();

        lista.add(new Decisao("Engarrafamento Histórico",
            "42 km de fila — o maior congestionamento da história.\n" +
            "Trabalhadores perdem horas no trânsito todo dia.\n" +
            "A poluição do ar bate recorde.",
            "🚗", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Construir nova linha de metrô 🚇",
                    "Milhares abandonam o carro. O congestionamento despenca.",
                    new ConstruirMetro()),
                new Decisao.OpcaoDecisao(
                    "B) 🚲 Expandir ciclovias e rodízio",
                    "Menos carros nas ruas. Ciclistas aumentam 40% em um mês.",
                    new CicloviasExpansao()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Liberar mais faixas para carros destruindo calçadas",
                    "Pedestres ficam sem espaço. Mais carros = mais trânsito e poluição.",
                    new AcaoNegativa("Destruição de Calçadas",
                        "Mais pistas incentivam uso de carros.",
                        Map.of(TipoIndicador.MOBILIDADE,  -10.0,
                               TipoIndicador.POLUICAO,    +18.0,
                               TipoIndicador.SATISFACAO,  -12.0)))
            )));

        lista.add(new Decisao("Crise de Poluição do Ar",
            "Sensores de qualidade do ar emitem alerta vermelho.\n" +
            "Escolas fechadas. Hospitais com 60% mais casos respiratórios.\n" +
            "A OMS está monitorando a situação.",
            "🌫️", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Implementar taxa de carbono e rodízio urgente",
                    "Emissões caem rapidamente. A qualidade do ar melhora em uma semana.",
                    new TarifaCarbon()),
                new Decisao.OpcaoDecisao(
                    "B) 🚦 Restringir veículos e criar corredores verdes",
                    "Menos carros nas ruas. Poluição diminui progressivamente.",
                    new RodizioVeiculos()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Flexibilizar normas ambientais para atrair indústrias",
                    "Fábricas poluentes chegam. A fumaça cobre a cidade inteira.",
                    new AcaoNegativa("Flexibilização Ambiental",
                        "Normas ambientais afrouxadas para indústrias poluidoras.",
                        Map.of(TipoIndicador.POLUICAO,    +22.0,
                               TipoIndicador.ENERGIA,     -10.0,
                               TipoIndicador.SATISFACAO,  -18.0)))
            )));

        lista.add(new Decisao("Acidente na Rodovia Principal",
            "Acidente grave fechou a principal rodovia de acesso à cidade.\n" +
            "O desvio pelo centro dobrou o trânsito e a poluição.\n" +
            "Especialistas dizem que o problema é estrutural.",
            "🚧", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Investir em metrô como alternativa permanente",
                    "A crise vira oportunidade. O metrô recebe aprovação rápida.",
                    new ConstruirMetro()),
                new Decisao.OpcaoDecisao(
                    "B) 🚲 Expandir ciclovias e transporte alternativo",
                    "Parte da população abandona o carro definitivamente.",
                    new CicloviasExpansao()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Fechar a rodovia indefinidamente sem alternativas",
                    "Caos no trânsito. Comércio fecha e a economia local entra em colapso.",
                    new AcaoNegativa("Colapso de Transporte",
                        "Sem rodovia e sem alternativas, cidade paralisa.",
                        Map.of(TipoIndicador.MOBILIDADE,  -25.0,
                               TipoIndicador.SATISFACAO,  -20.0,
                               TipoIndicador.POLUICAO,    +15.0)))
            )));

        lista.add(new Decisao("Frota de Ônibus Sucateada",
            "60% dos ônibus da cidade estão quebrados.\n" +
            "Passageiros esperam mais de 1 hora no ponto.\n" +
            "A reclamação é a pauta principal das redes sociais.",
            "🚌", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Renovar frota com ônibus elétricos",
                    "Nova frota moderna e silenciosa. Passageiros voltam ao transporte público.",
                    new ConstruirMetro()),
                new Decisao.OpcaoDecisao(
                    "B) 🚦 Implementar BRT com faixas exclusivas",
                    "Ônibus rápidos e pontuais. A mobilidade melhora significativamente.",
                    new RodizioVeiculos()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Privatizar e abandonar linhas não lucrativas",
                    "Bairros periféricos ficam sem transporte. Desigualdade e revolta.",
                    new AcaoNegativa("Abandono do Transporte",
                        "Linhas cortadas deixam população sem ônibus.",
                        Map.of(TipoIndicador.MOBILIDADE,  -20.0,
                               TipoIndicador.SATISFACAO,  -22.0,
                               TipoIndicador.POLUICAO,    +12.0)))
            )));

        Collections.shuffle(lista);
        return lista;
    }

    // ── NÍVEL 3 ── Energia & Sustentabilidade ────────────────────────────────
    private static List<Decisao> nivel3() {
        List<Decisao> lista = new ArrayList<>();

        lista.add(new Decisao("Apagão em Pleno Verão",
            "Onda de calor sobrecarrega a rede elétrica.\n" +
            "Bairros sem luz por 12 horas. Hospitais nos geradores.\n" +
            "A população está no limite.",
            "⚡", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Construir usina de energia solar e eólica ☀️",
                    "Contrato emergencial. Matriz energética começa a mudar de verdade.",
                    new UsinaLimpa()),
                new Decisao.OpcaoDecisao(
                    "B) 💰 Aplicar taxa de carbono para reduzir consumo",
                    "Indústrias reduzem consumo. Pressão na rede elétrica cai.",
                    new TarifaCarbon()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Reativar usina termelétrica a carvão abandonada",
                    "Energia volta, mas fumaça preta cobre a cidade por meses.",
                    new AcaoNegativa("Reativação a Carvão",
                        "Usina poluidora volta a operar a todo vapor.",
                        Map.of(TipoIndicador.ENERGIA,     +5.0,
                               TipoIndicador.POLUICAO,   +25.0,
                               TipoIndicador.SATISFACAO, -15.0,
                               TipoIndicador.SUSTENTABILIDADE, -20.0)))
            )));

        lista.add(new Decisao("Petróleo vs. Energia Limpa",
            "Uma grande petrolífera quer instalar refinaria na cidade,\n" +
            "prometendo 10.000 empregos. Ambientalistas protestam nas ruas.\n" +
            "A decisão vai definir o futuro energético da cidade.",
            "🛢️", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Recusar e investir em usina limpa ☀️",
                    "8.000 empregos verdes criados. Poluição cai expressivamente.",
                    new UsinaLimpa()),
                new Decisao.OpcaoDecisao(
                    "B) 💰 Aplicar taxa de carbono severa na refinaria",
                    "Petrolífera desiste. Dinheiro financia energia renovável.",
                    new TarifaCarbon()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Aceitar a refinaria e ignorar o impacto ambiental",
                    "Dinheiro entra mas o ar fica irrespirável. Geração futura paga a conta.",
                    new AcaoNegativa("Aceitar Refinaria Poluidora",
                        "Refinaria instalada sem contrapartidas ambientais.",
                        Map.of(TipoIndicador.POLUICAO,    +28.0,
                               TipoIndicador.ENERGIA,     -8.0,
                               TipoIndicador.SATISFACAO,  -12.0,
                               TipoIndicador.SUSTENTABILIDADE, -22.0)))
            )));

        lista.add(new Decisao("Meta Climática Internacional",
            "A cidade foi convidada a integrar uma rede global de\n" +
            "cidades sustentáveis. Para isso precisa reduzir emissões\n" +
            "em 40% nos próximos meses. O prazo é curto.",
            "🌍", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Usina limpa + metrô em tempo recorde ☀️🚇",
                    "Investimento massivo. A cidade vira referência nacional.",
                    new UsinaLimpa()),
                new Decisao.OpcaoDecisao(
                    "B) ♻️ Coleta seletiva + parques + ciclovias",
                    "Ações populares de grande alcance. Meta cumprida com participação cidadã.",
                    new ColetaSeletiva()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Falsificar os dados de emissão para fingir cumprir",
                    "Fraude descoberta pela imprensa internacional. Escândalo global.",
                    new AcaoNegativa("Fraude nos Dados Climáticos",
                        "Manipulação de dados ambientais exposta.",
                        Map.of(TipoIndicador.SATISFACAO,  -30.0,
                               TipoIndicador.POLUICAO,    +15.0,
                               TipoIndicador.SUSTENTABILIDADE, -25.0)))
            )));

        Collections.shuffle(lista);
        return lista;
    }

    // ── NÍVEL 4 ── Todos os indicadores ──────────────────────────────────────
    private static List<Decisao> nivel4() {
        List<Decisao> lista = new ArrayList<>();

        lista.add(new Decisao("Cúpula Ambiental Mundial",
            "Líderes mundiais chegam à cidade para a Cúpula do Clima.\n" +
            "O mundo inteiro está assistindo. Esta é a chance de mostrar\n" +
            "que a metrópole é modelo de sustentabilidade.",
            "🌐", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Inaugurar usina limpa + metrô no mesmo dia",
                    "Evento histórico. Investimentos verdes chegam do mundo inteiro.",
                    new UsinaLimpa()),
                new Decisao.OpcaoDecisao(
                    "B) 🌳 Criar o maior parque urbano da América Latina",
                    "Símbolo verde. A satisfação popular atinge o maior índice da história.",
                    new ParqueUrbano()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Cancelar a cúpula para obras de propaganda",
                    "Constrangimento internacional. A cidade perde credibilidade global.",
                    new AcaoNegativa("Cancelamento da Cúpula",
                        "Cidade cancela evento climático mundial.",
                        Map.of(TipoIndicador.SATISFACAO,  -25.0,
                               TipoIndicador.SUSTENTABILIDADE, -20.0,
                               TipoIndicador.POLUICAO,    +10.0)))
            )));

        lista.add(new Decisao("Catástrofe Ambiental Iminente",
            "Cientistas alertam: sem ação imediata, crise de água,\n" +
            "energia e mobilidade acontecerão simultaneamente\n" +
            "nos próximos 60 dias.",
            "🆘", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Plano de emergência total: metrô + usina limpa",
                    "A cidade mobiliza recursos extras. A crise é evitada.",
                    new ConstruirMetro()),
                new Decisao.OpcaoDecisao(
                    "B) ♻️ Rodízio + coleta seletiva + parques",
                    "Três frentes simultâneas. Cada medida alivia uma parte da crise.",
                    new RodizioVeiculos()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Negar a catástrofe e chamar cientistas de alarmistas",
                    "Nada é feito. A cidade entra em colapso enquanto o prefeito nega.",
                    new AcaoNegativa("Negacionismo Ambiental",
                        "Prefeitura ignora alertas científicos.",
                        Map.of(TipoIndicador.POLUICAO,    +20.0,
                               TipoIndicador.ENERGIA,     -15.0,
                               TipoIndicador.MOBILIDADE,  -15.0,
                               TipoIndicador.SATISFACAO,  -20.0)))
            )));

        lista.add(new Decisao("Legado para o Futuro",
            "Com as eleições se aproximando, você tem uma última grande\n" +
            "decisão como prefeito. Que legado ambiental a cidade terá\n" +
            "para as próximas gerações?",
            "🏆", List.of(
                new Decisao.OpcaoDecisao(
                    "A) ✅ Metrô para toda a periferia + usina limpa 🚇☀️",
                    "Legado histórico. Mobilidade democrática e energia sustentável.",
                    new ConstruirMetro()),
                new Decisao.OpcaoDecisao(
                    "B) 🌳 Rede de parques conectando toda a cidade",
                    "Corredores verdes interligam bairros. Biodiversidade urbana ressurge.",
                    new ParqueUrbano()),
                new Decisao.OpcaoDecisao(
                    "C) ❌ Vender terrenos públicos para construtoras",
                    "Parques e áreas verdes viram condomínios. O legado é de destruição.",
                    new AcaoNegativa("Venda de Áreas Públicas",
                        "Terrenos públicos entregues para especulação imobiliária.",
                        Map.of(TipoIndicador.SATISFACAO,  -25.0,
                               TipoIndicador.POLUICAO,    +15.0,
                               TipoIndicador.SUSTENTABILIDADE, -25.0,
                               TipoIndicador.MOBILIDADE,  -10.0)))
            )));

        Collections.shuffle(lista);
        return lista;
    }
}
