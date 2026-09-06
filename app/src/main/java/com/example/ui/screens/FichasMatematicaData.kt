package com.example.ui.screens

import com.example.Ficha
import com.example.FichaBloco

object FichasMatematicaData {
    val fichasMatematica1Ano = listOf(
        Ficha(
            1, "Ficha 1: Relações de posição", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Onde está a ovelha? Assinala:"),
                FichaBloco.CaixaPalavras(listOf("à frente da sebe", "atrás da sebe", "à frente da árvore", "em cima da árvore", "por detrás da árvore")),
                FichaBloco.Instrucao("Onde estão os morangos? Assinala:"),
                FichaBloco.CaixaPalavras(listOf("dentro do frasco", "fora do frasco", "em cima do frasco")),
                FichaBloco.Instrucao("Onde está a bola? Assinala:"),
                FichaBloco.CaixaPalavras(listOf("atrás do urso", "à frente do urso", "à direita do urso", "à esquerda do urso"))
            )
        ),
        Ficha(
            2, "Ficha 2: Correspondências um a um", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Ratinhos: 🐭🐭🐭🐭🐭  e  Queijos: 🧀🧀🧀🧀 (5 ratinhos, 4 queijos)"),
                FichaBloco.Instrucao("Liga cada ratinho ao seu queijo. Assinala:"),
                FichaBloco.CaixaPalavras(listOf("há mais ratinhos", "há menos ratinhos", "há tantos ratinhos como queijos")),
                FichaBloco.Instrucao("Meninos: 🧒🧒🧒🧒🧒  e  Bolas: ⚽⚽⚽⚽⚽ (5 meninos, 5 bolas)"),
                FichaBloco.Instrucao("Liga cada menino à sua bola. Assinala:"),
                FichaBloco.CaixaPalavras(listOf("há mais meninos", "há menos meninos", "há tantos meninos como bolas")),
                FichaBloco.Instrucao("Meninas: 👧👧👧👧👧  e  Bonecas: 🪆🪆🪆🪆 (5 meninas, 4 bonecas)"),
                FichaBloco.Instrucao("Liga cada menina à sua boneca. Assinala:"),
                FichaBloco.CaixaPalavras(listOf("há mais meninas", "há menos meninas", "há tantas meninas como bonecas"))
            )
        ),
        Ficha(
            3, "Ficha 3: Números até 5", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Conta e escreve o número certo para cada grupo de emojis:"),
                FichaBloco.Instrucao("🥛🥛🥛"),
                FichaBloco.CaixinhasCompletar(listOf("Iogurtes: _")),
                FichaBloco.Instrucao("💎💎💎💎💎"),
                FichaBloco.CaixinhasCompletar(listOf("Diamantes: _")),
                FichaBloco.Instrucao("🐚🐚"),
                FichaBloco.CaixinhasCompletar(listOf("Conchas: _")),
                FichaBloco.Instrucao("🍍🍍🍍🍍"),
                FichaBloco.CaixinhasCompletar(listOf("Ananases: _")),
                FichaBloco.Instrucao("🦪"),
                FichaBloco.CaixinhasCompletar(listOf("Ostras: _")),
                FichaBloco.Instrucao("💍💍"),
                FichaBloco.CaixinhasCompletar(listOf("Anéis: _")),
                FichaBloco.Instrucao("🍒🍒🍒🍒🍒"),
                FichaBloco.CaixinhasCompletar(listOf("Cerejas: _")),
                FichaBloco.Instrucao("🏆🏆🏆"),
                FichaBloco.CaixinhasCompletar(listOf("Troféus: _")),
                FichaBloco.Instrucao("Conta os animais do mar e regista o número:"),
                FichaBloco.Instrucao("Polvos: 🐙🐙🐙  | Caranguejos: 🦀🦀  | Peixes: 🐟🐟🐟🐟  | Estrelas: ⭐⭐⭐⭐⭐  | Cavalo-marinho: 🐴"),
                FichaBloco.CaixinhasCompletar(listOf("Polvos: _", "Caranguejos: _", "Peixes: _", "Estrelas: _", "Cavalos-marinhos: _")),
                FichaBloco.Instrucao("Completa a sequência numérica na lagarta (de 0 a 5):"),
                FichaBloco.CaixinhasCompletar(listOf("0", "_", "2", "_", "4", "_"))
            )
        ),
        Ficha(
            4, "Ficha 4: Representação de conjuntos", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Escreve o cardinal de cada conjunto de brinquedos:"),
                FichaBloco.Instrucao("Conjunto de ursos: 🧸🧸"),
                FichaBloco.CaixinhasCompletar(listOf("Cardinal: _")),
                FichaBloco.Instrucao("Conjunto de bolas: ⚽⚽⚽⚽⚽"),
                FichaBloco.CaixinhasCompletar(listOf("Cardinal: _")),
                FichaBloco.Instrucao("Conjunto de piões: 🌀🌀🌀🌀"),
                FichaBloco.CaixinhasCompletar(listOf("Cardinal: _")),
                FichaBloco.Instrucao("Conjunto de carros: 🚗🚗🚗"),
                FichaBloco.CaixinhasCompletar(listOf("Cardinal: _")),
                FichaBloco.Instrucao("Observa o Conjunto A (🐚 conchas) e o Conjunto B (🦪 búzios). Completa com pertence (∈) ou não pertence (∉):"),
                FichaBloco.CaixinhasCompletar(listOf("Uma concha 🐚 _ Conjunto A", "Uma concha 🐚 _ Conjunto B", "Um búzio 🦪 _ Conjunto A", "Um búzio 🦪 _ Conjunto B"))
            )
        ),
        Ficha(
            5, "Ficha 5: Adição", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa as adições inspiradas nas peças de dominó (esquerda|direita):"),
                FichaBloco.CaixinhasCompletar(listOf("2|2 → 2 + 2 = _", "3|1 → 3 + 1 = _", "4|0 → 4 + 0 = _", "2|3 → 2 + 3 = _")),
                FichaBloco.Instrucao("Conta os dedos levantados das mãos e efetua as adições:"),
                FichaBloco.CaixinhasCompletar(listOf("✋ (5) + ☝️ (1) = _", "✌️ (2) + ✌️ (2) = _", "✋ (5) + ✌️ (2) = _", "🤚 (5) + ✋ (5) = _")),
                FichaBloco.Instrucao("Conta as maçãs e as estrelas, depois resolve:"),
                FichaBloco.Instrucao("🍎🍎 + 🍎🍎🍎"),
                FichaBloco.CaixinhasCompletar(listOf("2 + 3 = _")),
                FichaBloco.Instrucao("⭐ + ⭐⭐⭐⭐"),
                FichaBloco.CaixinhasCompletar(listOf("1 + 4 = _")),
                FichaBloco.Instrucao("Completa a sequência de acordo com os valores (+2, +0, +1, +1):"),
                FichaBloco.CaixinhasCompletar(listOf("Início: 1", "+2 → _", "+0 → _", "+1 → _", "+1 → _"))
            )
        ),
        Ficha(
            6, "Ficha 6: Números até 10", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Conta os objetos e assinala o número correto:"),
                FichaBloco.Instrucao("🧢🧢🧢🧢🧢🧢 (6 bonés)"),
                FichaBloco.CaixaPalavras(listOf("5", "6", "7")),
                FichaBloco.Instrucao("🔍🔍🔍🔍🔍🔍🔍 (7 lupas)"),
                FichaBloco.CaixaPalavras(listOf("6", "7", "8")),
                FichaBloco.Instrucao("✂️✂️✂️✂️✂️✂️✂️✂️ (8 tesouras)"),
                FichaBloco.CaixaPalavras(listOf("7", "8", "9")),
                FichaBloco.Instrucao("🌺🌺🌺🌺🌺🌺🌺🌺🌺 (9 papoilas)"),
                FichaBloco.CaixaPalavras(listOf("8", "9", "10")),
                FichaBloco.Instrucao("🐟🐟🐟🐟🐟🐟🐟🐟🐟🐟 (10 peixes)"),
                FichaBloco.CaixaPalavras(listOf("9", "10", "11")),
                FichaBloco.Instrucao("Conta os lápis e regista o número:"),
                FichaBloco.CaixinhasCompletar(listOf("Vermelhos ✏️✏️✏️✏️✏️✏️: _", "Azuis ✏️✏️✏️✏️✏️✏️✏️: _", "Verdes ✏️✏️✏️✏️✏️✏️✏️✏️: _", "Amarelos ✏️✏️✏️✏️✏️✏️✏️✏️✏️: _")),
                FichaBloco.Instrucao("Completa as sequências de numeração:"),
                FichaBloco.CaixinhasCompletar(listOf("0", "_", "_", "3", "_", "_", "_", "_", "_", "9")),
                FichaBloco.CaixinhasCompletar(listOf("10", "_", "_", "_", "_", "5", "_", "_", "_", "_", "_"))
            )
        ),
        Ficha(
            7, "Ficha 7: Adição", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Adição com peças de dominó (esquerda|direita):"),
                FichaBloco.CaixinhasCompletar(listOf("5|1 → 5 + 1 = _", "4|4 → 4 + 4 = _", "3|6 → 3 + 6 = _", "2|5 → 2 + 5 = _", "6|4 → 6 + 4 = _")),
                FichaBloco.Instrucao("Efetua as somas o mais rápido que conseguires:"),
                FichaBloco.CaixinhasCompletar(listOf("2+3 = _", "5+1 = _", "3+7 = _", "4+4 = _", "2+4 = _", "7+3 = _", "2+6 = _", "6+4 = _", "5+2 = _")),
                FichaBloco.CaixinhasCompletar(listOf("1+8 = _", "5+3 = _", "4+5 = _", "6+2 = _", "8+2 = _", "6+1 = _", "1+9 = _", "10+0 = _", "1+5 = _")),
                FichaBloco.CaixinhasCompletar(listOf("0+10 = _", "4+6 = _", "3+3 = _", "2+8 = _", "5+5 = _", "0+9 = _", "2+5 = _", "6+3 = _", "2+2 = _"))
            )
        ),
        Ficha(
            8, "Ficha 8: Relações de ordem (>, <, =)", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Compara os seguintes pares de números com >, < ou =:"),
                FichaBloco.CaixinhasCompletar(listOf("9 _ 2", "4 _ 7", "7 _ 5", "8 _ 8", "6 _ 3", "3 _ 4")),
                FichaBloco.Instrucao("Ordena por ordem CRESCENTE (1, 3, 4, 5, 8, 9):"),
                FichaBloco.CaixinhasCompletar(listOf("_ < _ < _ < _ < _ < _")),
                FichaBloco.Instrucao("Ordena por ordem DECRESCENTE (10, 9, 8, 5, 3, 2):"),
                FichaBloco.CaixinhasCompletar(listOf("_ > _ > _ > _ > _ > _")),
                FichaBloco.Instrucao("Calcula mentalmente e preenche com >, < ou =:"),
                FichaBloco.CaixinhasCompletar(listOf("3+4 _ 5+2", "7+2 _ 5+3", "2+7 _ 3+7", "5+4 _ 8+2", "4+2 _ 5+1", "6+3 _ 4+4", "4+4 _ 6+2", "7+1 _ 6+2", "5+4 _ 7+3"))
            )
        ),
        Ficha(
            9, "Ficha 9: Diagramas de Venn", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Classifica os números (4, 7, 2, 6, 9, 3, 8, 1):"),
                FichaBloco.CaixinhasCompletar(listOf("Maiores do que 5: _ e _ e _ e _", "Menores do que 5: _ e _ e _ e _")),
                FichaBloco.Instrucao("Classifica os objetos em Material Escolar ou Brinquedos:"),
                FichaBloco.Instrucao("Objetos: ⚽ bola, 🚗 carro, 🧼 borracha, 🌀 pião, ✏️ lápis"),
                FichaBloco.CaixinhasCompletar(listOf("Material Escolar: _ e _", "Brinquedos: _ e _ e _"))
            )
        ),
        Ficha(
            10, "Ficha 10: Sólidos geométricos", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Associa os objetos ao seu sólido geométrico correspondente:"),
                FichaBloco.Instrucao("Objetos: tenda ⛺, caixa de cereais 📦, gelado 🍦, globo 🌐, garrafa 🍾, peça dominó 🁚, balão 🎈, bola ⚽, mala 💼, caixote lixo 🗑️, cubo de gelo 🧊"),
                FichaBloco.CaixinhasCompletar(listOf("Assemelha-se a CUBO: _ e _", "Assemelha-se a CILINDRO: _ e _", "Assemelha-se a CONE: _ e _", "Assemelha-se a ESFERA: _ e _ e _", "Assemelha-se a PARALELÉPIPEDO: _ e _"))
            )
        ),
        Ficha(
            11, "Ficha 11: Sólidos geométricos", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Escreve o nome do sólido (cubo, cilindro, paralelepípedo, esfera) para cada objeto:"),
                FichaBloco.CaixinhasCompletar(listOf("Balão 🎈: _", "Copo 🥛: _", "Dominó 🁚: _", "Caixote 🗑️: _")),
                FichaBloco.CaixinhasCompletar(listOf("Bola ⚽: _", "Mala 💼: _", "Globo 🌐: _", "Dado 🎲: _")),
                FichaBloco.CaixinhasCompletar(listOf("Caixa cereais: _", "Presente 🎁: _", "Tambor 🥁: _", "Rolo papel 🧻: _")),
                FichaBloco.Instrucao("Indica se as superfícies são PLANAS ou CURVAS:"),
                FichaBloco.CaixinhasCompletar(listOf("Cilindro: _", "Globo: _", "Gelado: _", "Cubo: _"))
            )
        ),
        Ficha(
            12, "Ficha 12: Figuras geométricas", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Identifica o número de lados e de vértices das seguintes figuras:"),
                FichaBloco.CaixinhasCompletar(listOf("Triângulo: lados = _, vértices = _", "Quadrado: lados = _, vértices = _", "Retângulo: lados = _, vértices = _", "Círculo: lados = _, vértices = _")),
                FichaBloco.Instrucao("Desenha figuras geométricas (quadrado, retângulo e triângulo) no papel ponteado abaixo:"),
                FichaBloco.EspacoDesenho(160, "pontos")
            )
        ),
        Ficha(
            13, "Ficha 13: Noção de dezena", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Temos 13 maçãs no total: 🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎"),
                FichaBloco.Instrucao("Rodeamos mentalmente 1 dezena (10 maçãs). Responde:"),
                FichaBloco.CaixinhasCompletar(listOf("Quantas maçãs sobraram? _", "Quantas faltam para obteres outra dezena? _")),
                FichaBloco.Instrucao("Completa os conjuntos até obteres uma dezena (10):"),
                FichaBloco.CaixinhasCompletar(listOf("Laranjas: 🍊🍊🍊🍊🍊🍊 + _ = 10", "Morangos: 🍓🍓🍓🍓 + _ = 10", "Uvas: 🍇🍇🍇🍇🍇🍇🍇 + _ = 10")),
                FichaBloco.Instrucao("Completa as contas para obter 10:"),
                FichaBloco.CaixinhasCompletar(listOf("3 + _ = 10", "2 + _ = 10", "10 + _ = 10", "5 + _ = 10", "4 + _ = 10"))
            )
        ),
        Ficha(
            14, "Ficha 14: Números até 15", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa as sequências de numeração crescente e decrescente:"),
                FichaBloco.CaixinhasCompletar(listOf("13, 12, 11, _, 9, _, 7, 6", "12, _, 10, 9, 8, _, 6, _", "15, 14, _, 12, _, 10, _, 8")),
                FichaBloco.CaixinhasCompletar(listOf("14, 13, _, 11, 10, _, 8, _", "11, _, 9, _, 7, _, 5, 4")),
                FichaBloco.Instrucao("Descobre o número em falta para obter 15 cerejas 🍒:"),
                FichaBloco.CaixinhasCompletar(listOf("10 + _ = 15", "_ + 7 = 15", "_ + 2 = 15", "14 + _ = 15", "_ + 6 = 15")),
                FichaBloco.Instrucao("Completa a decomposição dos números:"),
                FichaBloco.CaixinhasCompletar(listOf("12 = 10 + _", "15 = 10 + _", "12 = 6 + _", "15 = 5 + _"))
            )
        ),
        Ficha(
            15, "Ficha 15: Adição estratégica", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Soma primeiro as duas parcelas que dão 10 e depois a terceira parcela:"),
                FichaBloco.CaixinhasCompletar(listOf("7 + 6 + 4 = 10 + 7 = _", "3 + 8 + 7 = 10 + 8 = _", "2 + 8 + 7 = 10 + 7 = _", "6 + 7 + 3 = 10 + 6 = _")),
                FichaBloco.CaixinhasCompletar(listOf("9 + 9 + 1 = 10 + 9 = _", "6 + 2 + 4 = 10 + 6 = _", "5 + 9 + 5 = 10 + 9 = _", "1 + 2 + 9 = 10 + 2 = _")),
                FichaBloco.CaixinhasCompletar(listOf("3 + 5 + 7 = 10 + 5 = _", "2 + 9 + 1 = 10 + 2 = _", "8 + 6 + 2 = 10 + 6 = _", "4 + 5 + 5 = 10 + 4 = _")),
                FichaBloco.Instrucao("Efetua as somas usando saltos na reta numérica:"),
                FichaBloco.CaixinhasCompletar(listOf("Começa no 4, salta 2: 4 + 2 = _", "Começa no 7, salta 3: 7 + 3 = _", "Começa no 5, salta 4: 5 + 4 = _"))
            )
        ),
        Ficha(
            16, "Ficha 16: Subtração", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Conta os bichinhos e retira a quantidade pedida (X):"),
                FichaBloco.CaixinhasCompletar(listOf("🐜🐜🐜🐜🐜🐜 (6 formigas, retira 1) → 6 - 1 = _", "🐛🐛🐛🐛🐛🐛🐛 (7 pulgões, retira 5) → 7 - 5 = _", "🐝🐝🐝🐝🐝🐝 (6 abelhas, retira 4) → 6 - 4 = _")),
                FichaBloco.Instrucao("Resolve com a ajuda da reta numérica:"),
                FichaBloco.TextoLeitura("O João tinha 8 carrinhos mas perdeu 3. Com quantos ficou?"),
                FichaBloco.CaixinhasCompletar(listOf("8 - 3 = _")),
                FichaBloco.TextoLeitura("A Patrícia tinha 12 bombons e ofereceu 4 à amiga. Com quantos ficou?"),
                FichaBloco.CaixinhasCompletar(listOf("12 - 4 = _"))
            )
        ),
        Ficha(
            17, "Ficha 17: Tempo", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Observa a agenda semanal da Rita e responde:"),
                FichaBloco.TabelaDuasColunas(
                    listOf("Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"),
                    listOf("Natação 🏊", "Música 🎵", "Dança 💃", "Natação 🏊", "Música 🎵")
                ),
                FichaBloco.CaixinhasCompletar(listOf("Dias de Natação: _ª-feira e _ª-feira", "Dias de Música: _ª-feira e _ª-feira", "Dia de Dança: _ª-feira")),
                FichaBloco.Instrucao("No calendário de Janeiro, que atividade tem no dia 17? E no dia 25?"),
                FichaBloco.CaixinhasCompletar(listOf("Atividade do dia 17: _", "Atividade do dia 25: _"))
            )
        ),
        Ficha(
            18, "Ficha 18: Números até 20", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Ordena os números 8, 10, 7, 15, 12 por ordem DECRESCENTE:"),
                FichaBloco.CaixinhasCompletar(listOf("_ > _ > _ > _ > _")),
                FichaBloco.Instrucao("Ordena os números 13, 15, 6, 20, 9 por ordem CRESCENTE:"),
                FichaBloco.CaixinhasCompletar(listOf("_ < _ < _ < _ < _")),
                FichaBloco.Instrucao("Compara usando os sinais >, < ou =:"),
                FichaBloco.CaixinhasCompletar(listOf("10 _ 12", "15 _ 11", "13 _ 16", "8+8 _ 16", "5+5 _ 14", "6+6 _ 13")),
                FichaBloco.Instrucao("Descobre o número em falta para completar a soma:"),
                FichaBloco.CaixinhasCompletar(listOf("4 + 3 + _ = 10", "9 + 1 + _ = 20", "7 + 3 + _ = 15", "16 = 8 + 4 + _"))
            )
        ),
        Ficha(
            19, "Ficha 19: Relação entre adição e subtração", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa as relações inversas entre somar e subtrair:"),
                FichaBloco.CaixinhasCompletar(listOf("Se 6 + 4 = 10, então 10 - 4 = _ e 10 - 6 = _", "Se 5 + 6 = 11, então 11 - 5 = _ e 11 - 6 = _", "Se 7 + 5 = 12, então 12 - 5 = _ e 12 - 7 = _")),
                FichaBloco.Instrucao("Efetua com rapidez mental:"),
                FichaBloco.CaixinhasCompletar(listOf("10 + 6 = _", "10 + 7 = _", "10 + 8 = _", "10 + 9 = _")),
                FichaBloco.CaixinhasCompletar(listOf("16 - 6 = _", "17 - 7 = _", "18 - 8 = _", "19 - 8 = _"))
            )
        ),
        Ficha(
            20, "Ficha 20: Segmentos de reta", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Desenha dois segmentos de reta no papel ponteado abaixo:"),
                FichaBloco.EspacoDesenho(160, "pontos"),
                FichaBloco.Instrucao("Identifica os extremos do segmento de reta [CD]:"),
                FichaBloco.CaixinhasCompletar(listOf("Os extremos são as letras: _ e _"))
            )
        ),
        Ficha(
            21, "Ficha 21: Figuras geometricamente iguais", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Tenta copiar as figuras [ABCD] e [EFGH] para o papel ponteado de forma geometricamente idêntica:"),
                FichaBloco.EspacoDesenho(160, "pontos"),
                FichaBloco.Instrucao("Compara os comprimentos dos lados correspondentes:"),
                FichaBloco.CaixinhasCompletar(listOf("Os lados [AB] e [EG] são (IGUAIS ou DIFERENTES): _", "Os lados [AD] e [EF] são (IGUAIS ou DIFERENTES): _"))
            )
        ),
        Ficha(
            22, "Ficha 22: Resolver problemas usando a reta numérica", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Utiliza a reta numérica de 0 a 20 mentalmente para resolver os problemas:"),
                FichaBloco.TextoLeitura("O Pedro já colou 13 autocolantes. O pai deu-lhe mais 6. Com quantos ficou?"),
                FichaBloco.CaixinhasCompletar(listOf("13 + 6 = _")),
                FichaBloco.TextoLeitura("A Joana tem uma construção com 12 cubos. Juntou mais 5. Com quantos ficou?"),
                FichaBloco.CaixinhasCompletar(listOf("12 + 5 = _")),
                FichaBloco.TextoLeitura("A caixa tinha 18 bombons, comeram-se 5. Com quantos ficou?"),
                FichaBloco.CaixinhasCompletar(listOf("18 - 5 = _")),
                FichaBloco.TextoLeitura("Havia 20 livros, emprestaram-se 6. Quantos ficaram?"),
                FichaBloco.CaixinhasCompletar(listOf("20 - 6 = _"))
            )
        ),
        Ficha(
            23, "Ficha 23: Números até 40", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Decompõe os números em dezenas e unidades:"),
                FichaBloco.CaixinhasCompletar(listOf("37 = 30 + _", "24 = 20 + _", "30 = 30 + _", "33 = 30 + _", "38 = 30 + _")),
                FichaBloco.Instrucao("Ordena os números anteriores (37, 24, 30, 33, 38) por ordem CRESCENTE:"),
                FichaBloco.CaixinhasCompletar(listOf("_ < _ < _ < _ < _")),
                FichaBloco.Instrucao("Completa as sequências de contagem:"),
                FichaBloco.CaixinhasCompletar(listOf("De 2 em 2: 20, 22, _, _, _, _, _, _")),
                FichaBloco.CaixinhasCompletar(listOf("De 5 em 5: 5, 10, _, _, _, _, _, _"))
            )
        ),
        Ficha(
            24, "Ficha 24: Pictogramas", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Observa a quantidade de livros lidos por cada turma (cada 📚 representa 1 livro lido):"),
                FichaBloco.TabelaDuasColunas(
                    listOf("1.º A", "1.º B", "1.º C", "1.º D"),
                    listOf("📚📚📚📚📚 (5)", "📚📚📚 (3)", "📚📚📚📚 (4)", "📚📚📚📚📚📚 (6)")
                ),
                FichaBloco.Instrucao("Responde de acordo com o pictograma:"),
                FichaBloco.CaixinhasCompletar(listOf("Qual a turma que leu mais livros? 1.º _", "Quantos livros leu o 1.º C? _", "Quantos livros leram o 1.º A + 1.º B juntos? _", "Qual o total de livros lidos por todas as turmas? _"))
            )
        ),
        Ficha(
            25, "Ficha 25: Gráficos de pontos", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Alunos inscritos na natação por turma (cada ⚫ representa 1 aluno):"),
                FichaBloco.TabelaDuasColunas(
                    listOf("1.º A", "1.º B", "1.º C", "1.º D"),
                    listOf("⚫⚫⚫⚫⚫⚫⚫⚫ (8)", "⚫⚫⚫⚫⚫ (5)", "⚫⚫⚫⚫⚫⚫ (6)", "⚫⚫⚫⚫⚫⚫⚫ (7)")
                ),
                FichaBloco.Instrucao("Responde de acordo com o gráfico de pontos:"),
                FichaBloco.CaixinhasCompletar(listOf("Qual a turma com mais alunos na natação? _", "Quantos alunos praticam no 1.º C? _", "Quantos alunos praticam no total (todas as turmas)? _"))
            )
        ),
        Ficha(
            26, "Ficha 26: Medir distâncias e comprimentos", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Medindo o comprimento de objetos escolares usando clipes (📎) como unidade básica:"),
                FichaBloco.CaixinhasCompletar(listOf("Lápis ✏️ mede: _ clipes", "Pincel 🖌️ mede: _ clipes", "Fósforo 🥢 mede: _ clipes", "Pente 💈 mede: _ clipes")),
                FichaBloco.Instrucao("Indica o objeto mais comprido e o mais curto:"),
                FichaBloco.CaixinhasCompletar(listOf("Mais comprido: _", "Mais curto: _"))
            )
        ),
        Ficha(
            27, "Ficha 27: Números até 60", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa a tabela com a decomposição por dezenas e unidades:"),
                FichaBloco.CaixinhasCompletar(listOf("42 = quarenta e dois = 10+10+10+10+2 = _ dezenas e _ unidades", "51 = cinquenta e um = 10+10+10+10+10+1 = _ dezenas e _ unidades", "59 = cinquenta e nove = _ dezenas e _ unidades")),
                FichaBloco.Instrucao("Compara usando >, < ou =:"),
                FichaBloco.CaixinhasCompletar(listOf("23+20 _ 40+7", "34+12 _ 48", "38+12 _ 59-9")),
                FichaBloco.Instrucao("Desafios de numeração:"),
                FichaBloco.CaixinhasCompletar(listOf("Dezenas = 3, Unidades = 7. Que número é? _", "Faltam 9 unidades para completar 3 dezenas. Que número tenho? _"))
            )
        ),
        Ficha(
            28, "Ficha 28: Adição", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Pirâmides numéricas. Soma dois blocos vizinhos para obter o bloco de cima:"),
                FichaBloco.CaixinhasCompletar(listOf("Base: [10] e [12] → Topo: _", "Base: [15] e [15] → Topo: _", "Base: [11] e [14] → Topo: _")),
                FichaBloco.Instrucao("Efetua somas usando decomposição (ex: 42 + 13 = 40+10 + 2+3 = 55):"),
                FichaBloco.CaixinhasCompletar(listOf("42 + 13 = _", "21 + 35 = _", "28 + 31 = _", "36 + 13 = _")),
                FichaBloco.Instrucao("Resolve o problema de adição:"),
                FichaBloco.TextoLeitura("A mãe precisou de 12 ovos para um bolo e 14 para uma torta. De quantos precisou?"),
                FichaBloco.CaixinhasCompletar(listOf("12 + 14 = _"))
            )
        ),
        Ficha(
            29, "Ficha 29: Subtração", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Efetua as subtrações decompondo as dezenas e unidades:"),
                FichaBloco.CaixinhasCompletar(listOf("46 - 13 = _", "28 - 12 = _", "36 - 14 = _", "39 - 25 = _")),
                FichaBloco.Instrucao("Resolve os problemas abaixo:"),
                FichaBloco.TextoLeitura("O álbum leva 56 fotografias, a Rosa já colocou 28. Quantas faltam?"),
                FichaBloco.CaixinhasCompletar(listOf("56 - 28 = _")),
                FichaBloco.TextoLeitura("A caderneta leva 60 cromos, o João já tem 47. Quantos faltam?"),
                FichaBloco.CaixinhasCompletar(listOf("60 - 47 = _"))
            )
        ),
        Ficha(
            30, "Ficha 30: Números até 80", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa as sequências de salto de números:"),
                FichaBloco.CaixinhasCompletar(listOf("8, 18, 28, _, _, _, _, _", "45, 50, 55, _, _, _, _, _", "80, 70, 60, _, _, _, _, _")),
                FichaBloco.Instrucao("Preenche a correspondência de Dezenas e Unidades:"),
                FichaBloco.CaixinhasCompletar(listOf("58 = _ dezenas e _ unidades = 50 + _", "72 = _ dezenas e _ unidades = 70 + _", "64 = _ dezenas e _ unidades = 60 + _"))
            )
        ),
        Ficha(
            31, "Ficha 31: Adição — representação vertical", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Efetua as somas verticais (escreve o resultado final):"),
                FichaBloco.CaixinhasCompletar(listOf("46 + 13 = _", "43 + 25 = _", "63 + 16 = _", "35 + 34 = _", "52 + 17 = _")),
                FichaBloco.CaixinhasCompletar(listOf("46 + 23 = _", "38 + 31 = _", "41 + 37 = _", "45 + 33 = _", "21 + 47 = _")),
                FichaBloco.CaixinhasCompletar(listOf("47 + 32 = _", "54 + 24 = _", "46 + 11 = _", "56 + 23 = _", "42 + 37 = _"))
            )
        ),
        Ficha(
            32, "Ficha 32: Medir áreas", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Imagina uma quadrícula. Conta o número de quadradinhos (⬜) de cada figura:"),
                FichaBloco.CaixinhasCompletar(listOf("Figura A (forma de H) tem: _ quadradinhos", "Figura B tem: _ quadradinhos", "Figura C tem: _ quadradinhos")),
                FichaBloco.Instrucao("Desenha uma figura equivalente à figura A (com a mesma área) no quadriculado abaixo:"),
                FichaBloco.EspacoDesenho(160, "quadrados")
            )
        ),
        Ficha(
            33, "Ficha 33: Números até 100", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Completa a sequência de 10 em 10:"),
                FichaBloco.CaixinhasCompletar(listOf("23, 33, 43, _, _, _, _, _", "100, 90, 80, _, _, _, _, _")),
                FichaBloco.Instrucao("Ordena os números por ordem CRESCENTE: 45, 28, 76, 54, 82, 67, 93, 39"),
                FichaBloco.CaixinhasCompletar(listOf("_ < _ < _ < _ < _ < _ < _ < _")),
                FichaBloco.Instrucao("Decompõe os números em Dezenas e Unidades:"),
                FichaBloco.CaixinhasCompletar(listOf("68 = 60 + _", "86 = 80 + _", "73 = 70 + _", "37 = 30 + _", "45 = 40 + _"))
            )
        ),
        Ficha(
            34, "Ficha 34: Adição — representação vertical (com transporte)", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Efetua as somas verticais com transporte:"),
                FichaBloco.CaixinhasCompletar(listOf("49 + 12 = _", "48 + 13 = _", "38 + 35 = _", "46 + 16 = _")),
                FichaBloco.CaixinhasCompletar(listOf("46 + 25 = _", "47 + 37 = _", "56 + 38 = _", "68 + 16 = _")),
                FichaBloco.CaixinhasCompletar(listOf("45 + 47 = _", "49 + 37 = _", "35 + 38 = _", "51 + 49 = _"))
            )
        ),
        Ficha(
            35, "Ficha 35: Dinheiro", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Conta as moedas e notas e regista o total em Euros (€):"),
                FichaBloco.CaixinhasCompletar(listOf("2€ + 2€ + 2€ + 1€ = _ €", "10€ + 2€ + 1€ = _ €", "10€ + 5€ + 1€ = _ €", "20€ + 20€ + 10€ = _ €")),
                FichaBloco.TextoLeitura("O Pedro tem: 10€ + 5€ + 2€ + 2€ + 1€ + 1€ no mealheiro. Quer comprar patins que custam 25€. Tem dinheiro suficiente?"),
                FichaBloco.CaixinhasCompletar(listOf("Dinheiro total do Pedro: _ €", "Tem suficiente? (Escreve SIM ou NAO): _"))
            )
        ),
        Ficha(
            36, "Ficha 36: Grelha dos Números até 100", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("Imagina a tabela completa de 1 a 100. Descobre os números em falta nas linhas seguintes:"),
                FichaBloco.CaixinhasCompletar(listOf("Linha do 10: 11, 12, _, 14, 15, _, 17, 18, _, 20", "Linha do 30: 31, _, 33, _, 35, 36, _, 38, 39, _", "Linha do 90: 91, 92, _, 94, _, 96, 97, _, 99, _"))
            )
        ),
        Ficha(
            37, "Ficha 37: TPC Natal", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("TPC de Natal! Escreve por extenso o número correspondente à contagem visual de quadrados:"),
                FichaBloco.CaixinhasCompletar(listOf("⬜⬜⬜ (3) → _", "⬜⬜⬜⬜⬜ (5) → _", "⬜⬜⬜⬜⬜⬜⬜ (7) → _")),
                FichaBloco.Instrucao("Assinala o cardinal e se pertence (∈) ou não pertence (∉):"),
                FichaBloco.Instrucao("Conjunto A (joaninhas 🐞🐞🐞), Conjunto B (pulgões 🐛🐛), Conjunto C (formigas 🐜🐜🐜🐜)"),
                FichaBloco.CaixinhasCompletar(listOf("Uma joaninha 🐞 _ Conjunto A", "Um pulgão 🐛 _ Conjunto C")),
                FichaBloco.Instrucao("Liga os pontos de 1 a 10 no Canvas para revelar o desenho de Natal (barco à vela ⛵):"),
                FichaBloco.EspacoDesenho(160, "pontos")
            )
        ),
        Ficha(
            38, "Ficha 38: TPC Carnaval", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("TPC de Carnaval! Completa a tabela de numeração do 11 ao 20:"),
                FichaBloco.CaixinhasCompletar(listOf("11 = onze = 10 + 1 = _ dezena e _ unidade", "15 = quinze = 10 + _ = _ dezena e _ unidades", "20 = vinte = _ + _ = _ dezenas e _ unidades")),
                FichaBloco.Instrucao("Descobre o número maior em cada grupo:"),
                FichaBloco.CaixinhasCompletar(listOf("Grupo (20, 12, 13) → Maior: _", "Grupo (12, 15) → Maior: _", "Grupo (17, 10) → Maior: _")),
                FichaBloco.Instrucao("Assinala: Uma semana tem quantos dias?"),
                FichaBloco.CaixaPalavras(listOf("5 dias", "2 dias", "7 dias"))
            )
        ),
        Ficha(
            39, "Ficha 39: TPC Páscoa", "Matemática", "#10B981",
            listOf(
                FichaBloco.Instrucao("TPC de Páscoa! Completa a tabela do 21 ao 40 (decomposição):"),
                FichaBloco.CaixinhasCompletar(listOf("21 = vinte e um = 20 + _", "32 = trinta e dois = 30 + _", "40 = quarenta = _ + _")),
                FichaBloco.Instrucao("Completa com os vizinhos (antes, depois e entre):"),
                FichaBloco.CaixinhasCompletar(listOf("Antes de 38 é _ e depois é _", "Entre 20 e 22 está o: _", "Entre 28 e 30 está o: _")),
                FichaBloco.Instrucao("Efetua os cálculos em esquema radial à volta do número 40:"),
                FichaBloco.CaixinhasCompletar(listOf("30 + 10 = _", "45 - 5 = _", "20 + 20 = _", "50 - 10 = _"))
            )
        )
    )
}
