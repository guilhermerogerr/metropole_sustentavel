# Metrópole Sustentável 🏙️

Jogo de estratégia urbana desenvolvido em Java (Swing) como projeto acadêmico.

## Sobre o Jogo

O jogador assume o papel de prefeito de uma cidade e deve tomar decisões ao longo dos turnos para manter os indicadores urbanos (meio ambiente, mobilidade, saneamento, etc.) equilibrados, enfrentando eventos aleatórios e votações coletivas.

## Estrutura do Projeto

```
src/metropolesustentavel/
├── Main.java                   # Ponto de entrada
├── controller/
│   ├── GameController.java     # Lógica central do jogo
│   └── GeradorEventos.java     # Geração de eventos aleatórios
├── model/
│   ├── acoes/                  # Ações disponíveis ao jogador
│   ├── eventos/                # Eventos que ocorrem durante o jogo
│   ├── Cidade.java             # Estado da cidade
│   ├── Indicador.java          # Indicadores urbanos
│   └── ...
├── view/
│   ├── TelaInicial.java        # Tela de início
│   ├── TelaJogo.java           # Tela principal do jogo
│   ├── PainelMapa.java         # Painel do mapa da cidade
│   └── ...
└── util/
    └── Constantes.java         # Constantes do jogo
```

## Requisitos

- Java 17+
- NetBeans IDE (recomendado) ou qualquer IDE com suporte a projetos Ant

## Como Executar

1. Abra o projeto no NetBeans: `File > Open Project`
2. Clique em **Run** (F6)

Ou via linha de comando:
```bash
ant run
```

## Versão

v0.1 — Versão inicial
