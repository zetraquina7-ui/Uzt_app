package com.example.ui.screens

import androidx.compose.ui.graphics.Color

object WorksheetsYear3 {
    val portugues = WorksheetDiscipline(
        id = "pt_3",
        title = "Português",
        emoji = "🇵🇹",
        color = Color(0xFF8B5CF6),
        worksheets = listOf(
            Worksheet(
                id = "pt_3_f1",
                numero = 1,
                title = "Classes de Palavras: Nomes, Adjetivos e Verbos",
                description = "Identifica e distingue nomes (próprios/comuns), adjetivos e verbos de ação.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "📝",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'O rio Tejo corre veloz', que classe de palavra é 'Tejo'?",
                        options = listOf("Nome Próprio", "Adjetivo", "Verbo"),
                        correctAnswer = "Nome Próprio",
                        explanation = "'Tejo' é o nome próprio de um rio português e escreve-se com maiúscula!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'As flores perfumadas abriram', 'perfumadas' é um adjetivo qualificativo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Perfumadas' qualifica e dá uma característica às flores."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'O Zé ___ um livro interessante', a palavra que falta para indicar a ação é um verbo.",
                        options = listOf("leu", "casa", "verde"),
                        correctAnswer = "leu",
                        explanation = "'Leu' é uma forma do verbo ler."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é um VERBO que indica movimento?",
                        options = listOf("🏃 Correr", "👟 Sapatilha", "🔴 Vermelho"),
                        correctAnswer = "🏃 Correr",
                        explanation = "'Correr' é uma ação / verbo! 🏃"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a classe da palavra 'brilhante'?",
                        options = listOf("Adjetivo", "Nome Comum", "Verbo"),
                        correctAnswer = "Adjetivo",
                        explanation = "'Brilhante' exprime uma qualidade de algo."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'escola' é um nome comum coletivo.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Escola' é um nome comum concreto individual."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Descobre o nome coletivo que indica um conjunto de árvores:",
                        options = listOf("floresta", "ramo", "folha"),
                        correctAnswer = "floresta",
                        explanation = "Floresta é o nome coletivo para um grupo numeroso de árvores!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica as palavras na sua respetiva classe gramatical:",
                        pairs = mapOf(
                            "Guimarães" to "Nome Próprio",
                            "estudar" to "Verbo",
                            "amigável" to "Adjetivo",
                            "caderno" to "Nome Comum"
                        ),
                        explanation = "Excelente distinção das classes de palavras!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f2",
                numero = 2,
                title = "Pronomes Pessoais e Possessivos",
                description = "Eu, tu, ele/ela, nós, vós, eles/elas e os determinantes/pronomes possessivos.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "👤",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual pronome pessoal substitui 'O Pedro e eu' na frase 'O Pedro e eu fomos ao cinema'?",
                        options = listOf("Nós", "Eles", "Vós"),
                        correctAnswer = "Nós",
                        explanation = "'Eu' mais outra pessoa forma a 1.ª pessoa do plural: 'Nós'!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os pronomes pessoais 'Ele' e 'Ela' pertencem à 3.ª pessoa do singular.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1.ª Eu, 2.ª Tu, 3.ª Ele/Ela."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa com o pronome pessoal adequado: '___ cantais muito bem no coro!'",
                        options = listOf("Vós", "Nós", "Eles"),
                        correctAnswer = "Vós",
                        explanation = "'Cantais' concorda com a 2.ª pessoa do plural: 'Vós'!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras indica POSSE (pertence a mim)?",
                        options = listOf("🎁 Meu", "🎁 Este", "🎁 Aquele"),
                        correctAnswer = "🎁 Meu",
                        explanation = "'Meu' indica posse e pertença da 1.ª pessoa! 🎁"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Esta mochila é tua', que tipo de palavra é 'tua'?",
                        options = listOf("Pronome Possessivo", "Pronome Pessoal", "Verbo"),
                        correctAnswer = "Pronome Possessivo",
                        explanation = "'Tua' indica que a mochila pertence à 2.ª pessoa (tu)."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'Eles jogam futebol', o pronome 'Eles' está na 3.ª pessoa do plural.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Eles' refere-se a várias outras pessoas."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o pronome possessivo da 1.ª pessoa do plural?",
                        options = listOf("nosso / nossa", "vosso / vossa", "seu / sua"),
                        correctAnswer = "nosso / nossa",
                        explanation = "'Nosso' refere-se a algo que pertence a 'Nós'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o sujeito ao pronome pessoal equivalente:",
                        pairs = mapOf(
                            "O Manuel e a Maria" to "Eles",
                            "A Joana" to "Ela",
                            "Tu e o teu irmão" to "Vós",
                            "Eu e a professora" to "Nós"
                        ),
                        explanation = "Perfeita substituição pronominal!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f3",
                numero = 3,
                title = "Tempos Verbais: Presente, Passado e Futuro",
                description = "Pretérito Perfeito (ação concluída), Pretérito Imperfeito (ação habitual) e Futuro.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⏳",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Ontem o Zé estudou para o teste', em que tempo verbal está o verbo 'estudou'?",
                        options = listOf("Pretérito Perfeito", "Presente", "Futuro"),
                        correctAnswer = "Pretérito Perfeito",
                        explanation = "'Estudou' indica uma ação passada totalmente terminada ontem!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A frase 'Quando era pequeno, eu brincava sempre no jardim' usa o Pretérito Imperfeito ('brincava').",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Pretérito Imperfeito indica uma ação habitual ou contínua no passado."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa no Futuro: 'No próximo sábado nós ___ um piquenique.'",
                        options = listOf("faremos", "fizemos", "fazemos"),
                        correctAnswer = "faremos",
                        explanation = "'Faremos' expressa uma ação que vai acontecer no futuro."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases está no TEMPO PRESENTE (a acontecer agora)?",
                        options = listOf("⏰ 'O professor explica a matéria.'", "⏪ 'O professor explicou ontem.'", "🔮 'O professor explicará amanhã.'"),
                        correctAnswer = "⏰ 'O professor explica a matéria.'",
                        explanation = "Indica um acontecimento no momento atual! ⏰"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se conjuga o verbo 'cantar' no Pretérito Perfeito para 'Eles'?",
                        options = listOf("cantaram", "cantavam", "cantarão"),
                        correctAnswer = "cantaram",
                        explanation = "No passado terminou em '-aram' (cantaram); no futuro seria '-arão' (cantarão)."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As formas verbais terminadas em '-arão' indicam tempo passado.",
                        correctAnswer = "false",
                        explanation = "Falso! '-arão' indica Futuro (ex: 'Eles cantarão amanhã'). No passado é '-aram'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a forma do verbo 'escrever' no Pretérito Imperfeito para 'Eu'?",
                        options = listOf("escrevia", "escrevi", "escreverei"),
                        correctAnswer = "escrevia",
                        explanation = "'Eu escrevia cartas todos os domingos'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o verbo ao seu tempo verbal correto:",
                        pairs = mapOf(
                            "comemos hoje" to "Presente",
                            "comemos ontem" to "Pretérito Perfeito",
                            "comíamos sempre" to "Pretérito Imperfeito",
                            "comeremos amanhã" to "Futuro"
                        ),
                        explanation = "Excelente domínio da conjugação dos tempos verbais!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f4",
                numero = 4,
                title = "Determinantes Artigos e Demonstrativos",
                description = "Artigos definidos (o, a, os, as) / indefinidos (um, uma) e demonstrativos (este, esse, aquele).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👉",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quais são os Determinantes Artigos Definidos?",
                        options = listOf("o, a, os, as", "um, uma, uns, umas", "este, esta, estes, estas"),
                        correctAnswer = "o, a, os, as",
                        explanation = "'o, a, os, as' definem seres conhecidos e precisos!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'Vi um pássaro na árvore', 'um' é um artigo indefinido.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Refere-se a um pássaro qualquer de forma não específica."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Para apontar para um objeto que está longe de quem fala e de quem ouve usamos: '___ castelo lá no cimo do monte.'",
                        options = listOf("Aquele", "Este", "Esse"),
                        correctAnswer = "Aquele",
                        explanation = "'Aquele' indica distância de ambos os interlocutores."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual determinante demonstrativo usamos para um objeto que temos na mão?",
                        options = listOf("✋ 'Este livro'", "👉 'Esse livro'", "🏔️ 'Aquele livro'"),
                        correctAnswer = "✋ 'Este livro'",
                        explanation = "'Este' refere-se ao que está junto da pessoa que fala! ✋"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'As andorinhas regressaram', que tipo de determinante é 'As'?",
                        options = listOf("Determinante Artigo Definido Feminino Plural", "Pronome Pessoal", "Nome Comum"),
                        correctAnswer = "Determinante Artigo Definido Feminino Plural",
                        explanation = "'As' antecede o nome feminino plural 'andorinhas'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os artigos 'uns' e 'umas' são determinantes artigos definidos.",
                        correctAnswer = "false",
                        explanation = "Falso! São determinantes artigos indefinidos plurais."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Completa: '___ cães ladravam durante a noite' com artigo indefinido masculino plural.",
                        options = listOf("Uns", "Os", "Estes"),
                        correctAnswer = "Uns",
                        explanation = "'Uns' é o artigo indefinido masculino plural."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os determinantes:",
                        pairs = mapOf(
                            "O" to "Artigo Definido",
                            "Uma" to "Artigo Indefinido",
                            "Esta" to "Demonstrativo (próximo de mim)",
                            "Aquele" to "Demonstrativo (afastado)"
                        ),
                        explanation = "Muito bem! Distinguiste todos os determinantes com clareza."
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f5",
                numero = 5,
                title = "Acentuação: Palavras Agudas, Graves e Esdrúxulas",
                description = "Identifica a sílaba tónica (mais forte) e classifica as palavras quanto à acentuação.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🎯",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde recai a sílaba tónica numa palavra AGUDA?",
                        options = listOf("Na última sílaba", "Na penúltima sílaba", "Na antepenúltima sílaba"),
                        correctAnswer = "Na última sílaba",
                        explanation = "Palavras agudas têm a força na última sílaba (ex: ca-fé, jar-dim)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Todas as palavras ESDRÚXULAS têm acento gráfico na antepenúltima sílaba.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: lâmpada, pássaro, médico, triângulo levam sempre acento."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra 'janela' (ja-NE-la) tem a sílaba tónica na penúltima sílaba, logo é uma palavra ___",
                        options = listOf("Grave", "Aguda", "Esdrúxula"),
                        correctAnswer = "Grave",
                        explanation = "A maioria das palavras da língua portuguesa são graves (penúltima sílaba forte)."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é ESDRÚXULA?",
                        options = listOf("📐 Triângulo", "☕ Café", "📖 Livro"),
                        correctAnswer = "📐 Triângulo",
                        explanation = "'Tri-ân-gu-lo' tem a força na antepenúltima sílaba e leva acento! 📐"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A palavra 'coração' (co-ra-ÇÃO) é classificada como:",
                        options = listOf("Aguda", "Grave", "Esdrúxula"),
                        correctAnswer = "Aguda",
                        explanation = "A última sílaba 'ção' é a mais forte."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'futebol' é grave porque não tem acento.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Fu-te-BOL' tem a força na última sílaba 'bol', por isso é aguda."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a sílaba tónica da palavra 'computador'?",
                        options = listOf("dor", "ta", "com"),
                        correctAnswer = "dor",
                        explanation = "'com-pu-ta-DOR' tem a força na última sílaba."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica as palavras quanto à posição da sílaba tónica:",
                        pairs = mapOf(
                            "sofá" to "Aguda (última sílaba)",
                            "menino" to "Grave (penúltima sílaba)",
                            "música" to "Esdrúxula (antepenúltima sílaba)",
                            "hospital" to "Aguda (última sílaba)"
                        ),
                        explanation = "Perfeita classificação quanto à acentuação tónica!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f6",
                numero = 6,
                title = "Relações Semânticas: Homónimos e Parónimos",
                description = "Palavras com mesmo som/escrita mas significados diferentes, e palavras parecidas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🔀",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Palavras que se escrevem e pronunciam de forma idêntica mas têm significados diferentes chamam-se...",
                        options = listOf("Homónimas", "Sinónimas", "Antónimas"),
                        correctAnswer = "Homónimas",
                        explanation = "Exemplo: 'canto' (da sala) e 'canto' (eu canto uma canção)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'banco' (para sentar) e 'banco' (de dinheiro) são palavras homónimas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Têm a mesma grafia e som com sentidos distintos."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O cavaleiro montou no cavalo; o homem educado é um ___ (parónimo).",
                        options = listOf("cavalheiro", "cavaleiro", "carpinteiro"),
                        correctAnswer = "cavalheiro",
                        explanation = "'Cavaleiro' (monta a cavalo) e 'Cavalheiro' (homem nobre/educado) são parónimos!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é a palavra homónima que serve para colher comida e também é uma fruta doce?",
                        options = listOf("🍐 Pera / Colher", "🍎 Maçã", "🍌 Banana"),
                        correctAnswer = "🍐 Pera / Colher",
                        explanation = "'Colher' (o talher) e 'Colher' (o verbo apanhar frutos)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que são palavras PARÓNIMAS?",
                        options = listOf("Palavras muito parecidas na escrita e som mas com significados diferentes", "Palavras com significado oposto", "Palavras que rimam sempre"),
                        correctAnswer = "Palavras muito parecidas na escrita e som mas com significados diferentes",
                        explanation = "Ex: comprimento (tamanho) e cumprimento (saudação)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "'Comprimento' mede o tamanho e 'Cumprimento' é uma saudação (olá).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! São palavras parónimas fundamentais."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Eu rio quando ouço uma anedota', a palavra 'rio' é um...",
                        options = listOf("Verbo (do verbo rir)", "Curso de água", "Adjetivo"),
                        correctAnswer = "Verbo (do verbo rir)",
                        explanation = "Aqui 'rio' é a 1.ª pessoa do presente do verbo rir!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Distingue o significado dos pares de parónimos:",
                        pairs = mapOf(
                            "Comprimento" to "Medida de tamanho (metros/cm)",
                            "Cumprimento" to "Saudação respeitosa / dar os parabéns",
                            "Cavaleiro" to "Pessoa que monta a cavalo",
                            "Cavalheiro" to "Homem bem-educado e cortês"
                        ),
                        explanation = "Excelente clareza sobre homónimos e parónimos!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f7",
                numero = 7,
                title = "Frase Simples: Sujeito e Predicado",
                description = "Quem faz a ação (Sujeito) e o que se diz sobre o sujeito com o verbo (Predicado).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧩",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Os alunos do 3.º ano estudaram matemática', quem é o SUJEITO?",
                        options = listOf("Os alunos do 3.º ano", "estudaram matemática", "matemática"),
                        correctAnswer = "Os alunos do 3.º ano",
                        explanation = "Perguntamos 'Quem estudou?': 'Os alunos do 3.º ano'!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O PREDICADO inclui sempre o verbo e as informações sobre o que o sujeito faz ou é.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Na frase 'O sol brilha no céu', o predicado é 'brilha no céu'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'O gato bebeu o leite', o predicado é ___",
                        options = listOf("bebeu o leite", "O gato", "o leite"),
                        correctAnswer = "bebeu o leite",
                        explanation = "'bebeu o leite' contém o verbo e a ação realizada."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o Sujeito na frase: 'A andorinha voa veloz nas nuvens 🦅'?",
                        options = listOf("🦅 A andorinha", "☁️ voa veloz", "🌤️ nas nuvens"),
                        correctAnswer = "🦅 A andorinha",
                        explanation = "'A andorinha' é quem realiza a ação de voar! 🦅"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o sujeito formado por mais de um elemento (ex: 'O Pedro e a Rita jogam')?",
                        options = listOf("Sujeito Composto", "Sujeito Simples", "Sujeito Nulo"),
                        correctAnswer = "Sujeito Composto",
                        explanation = "Tem mais do que um núcleo ('O Pedro e a Rita')."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'Choveu muito ontem à noite', não existe um sujeito que faça a chuva cair (verbo de fenómeno natural).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Verbos que indicam fenómenos da natureza não têm sujeito ativo."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Identifica o sujeito na frase 'No jardim, as flores floriram com o sol':",
                        options = listOf("as flores", "No jardim", "floriram"),
                        correctAnswer = "as flores",
                        explanation = "Quem floriu foram 'as flores'!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Separa o Sujeito do Predicado em cada frase:",
                        pairs = mapOf(
                            "O comboio rápido" to "chegou à estação a horas (Predicado)",
                            "A mãe do Zé" to "fez um bolo de cenoura delicioso (Predicado)",
                            "As estrelas cadentes" to "cruzaram o céu noturno (Predicado)",
                            "Todos os amigos" to "brincaram felizes no recreio (Predicado)"
                        ),
                        explanation = "Perfeita análise sintática de Sujeito e Predicado!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f8",
                numero = 8,
                title = "Recursos Expressivos: Comparação e Personificação",
                description = "Descobre comparações ('como', 'parece') e personificações (dar vida humana a coisas e animais).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✨",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Os olhos dela brilham como duas estrelas', que recurso expressivo temos?",
                        options = listOf("Comparação", "Personificação", "Onomatopeia"),
                        correctAnswer = "Comparação",
                        explanation = "Usa a palavra 'como' para comparar o brilho dos olhos com estrelas!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A frase 'O vento assobiava zangado pelas frestas da janela' é uma Personificação.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Atribui um sentimento humano ('zangado') e uma ação humana ('assobiar') ao vento."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra 'Tic-tac, tic-tac' que imita o som do relógio chama-se uma ___",
                        options = listOf("Onomatopeia", "Comparação", "Personificação"),
                        correctAnswer = "Onomatopeia",
                        explanation = "Onomatopeias reproduzem ruídos e sons da realidade!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases contém uma Personificação?",
                        options = listOf("🌳 'As árvores dançavam felizes com a brisa.'", "🌳 'As árvores têm folhas verdes.'", "🌳 'A árvore é de carvalho.'"),
                        correctAnswer = "🌳 'As árvores dançavam felizes com a brisa.'",
                        explanation = "Dançar e sentir felicidade são características humanas dadas às árvores! 🌳"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a onomatopeia que imita o ladrar de um cão?",
                        options = listOf("Au-au!", "Miau!", "Piu-piu!"),
                        correctAnswer = "Au-au!",
                        explanation = "'Au-au' representa o som do cão."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "'Ele é rápido como um relâmpago' é uma comparação.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Compara a velocidade dele à de um relâmpago."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na poesia, para que servem os recursos expressivos?",
                        options = listOf("Tornar o texto mais belo, expressivo e emocionante", "Fazer o texto ficar mais curto", "Escrever sem pontuação"),
                        correctAnswer = "Tornar o texto mais belo, expressivo e emocionante",
                        explanation = "Enriquecem a imaginação e a beleza artística da língua."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica o recurso expressivo de cada frase:",
                        pairs = mapOf(
                            "O mar cantava canções suaves" to "Personificação",
                            "Ela é doce como o mel" to "Comparação",
                            "Bum! A porta bateu com força" to "Onomatopeia",
                            "A lua sorria para a noite" to "Personificação"
                        ),
                        explanation = "Excelente sensibilidade literária e poética!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f9",
                numero = 9,
                title = "Ortografia: Porquê/Porque, Há/A, Mal/Mau",
                description = "Escreve com rigor e sem hesitações nas dúvidas mais frequentes da escrita.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🎯",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual usamos para RESPONDER e dar uma justificação: 'Não fui à praia ___ estava a chover'?",
                        options = listOf("porque (junto)", "por que (separado)", "porquê"),
                        correctAnswer = "porque (junto)",
                        explanation = "'Porque' junto e sem acento usa-se em respostas e explicações!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Usamos 'Há' (com H e acento) quando indica tempo passado decorrido (ex: 'Há três dias').",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Há' vem do verbo haver e indica tempo que já passou."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O lobo da história era muito ___ (o oposto de bom).",
                        options = listOf("mau", "mal", "mais"),
                        correctAnswer = "mau",
                        explanation = "'Mau' opõe-se a 'bom'; 'Mal' opõe-se a 'bem'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases está 100% CORRETA?",
                        options = listOf("✅ 'Daqui a duas semanas vamos de férias.'", "❌ 'Daqui há duas semanas vamos de férias.'", "❌ 'Daqui ah duas semanas.'"),
                        correctAnswer = "✅ 'Daqui a duas semanas vamos de férias.'",
                        explanation = "Tempo futuro usa a preposição 'a' simples! ✅"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Ele cantou muito ___ no espetáculo (oposto de bem)', escreve-se:",
                        options = listOf("mal", "mau", "mais"),
                        correctAnswer = "mal",
                        explanation = "O contrário de bem é mal (com l)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "'Mas' indica oposição (porém) e 'Mais' indica quantidade ou adição.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Quero brincar, mas tenho de estudar mais.'"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Completa: 'Gostei do livro, ___ prefiro a banda desenhada.'",
                        options = listOf("mas", "mais", "más"),
                        correctAnswer = "mas",
                        explanation = "'Mas' exprime contraste / oposição."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Escolhe a palavra certa para cada frase:",
                        pairs = mapOf(
                            "Ele comportou-se ___ (mal/mau)" to "mal (oposto de bem)",
                            "O João é um rapaz ___ (mal/mau)" to "mau (oposto de bom)",
                            "Cheguei ___ duas horas (há/a)" to "há (tempo decorrido)",
                            "Vou sair daqui ___ pouco (há/a)" to "a (tempo futuro)"
                        ),
                        explanation = "Excelente domínio da ortografia e das regras da língua!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f10",
                numero = 10,
                title = "Formação de Palavras: Prefixos e Sufixos",
                description = "Descobre como as palavras derivadas são criadas juntando partículas no início ou fim.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🔬",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é um PREFIXO na formação de palavras?",
                        options = listOf("Um elemento colocado ANTES do radical da palavra", "Um elemento colocado no fim", "Uma pontuação"),
                        correctAnswer = "Um elemento colocado ANTES do radical da palavra",
                        explanation = "Ex: feliz -> in-feliz (in- é prefixo de negação)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na palavra 'lealdade', o elemento '-dade' é um SUFIXO adicionado no final da palavra.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Leal + dade = lealdade."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra primitiva 'fazer' com o prefixo 're-' fica ___ (fazer de novo).",
                        options = listOf("refazer", "desfazer", "perfazer"),
                        correctAnswer = "refazer",
                        explanation = "'Re-' indica repetição: refazer = fazer novamente!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras foi formada por DERIVAÇÃO POR PREFIXAÇÃO?",
                        options = listOf("🌧️ 'Impermeável' (in + permeável)", "🌧️ 'Chuvinha'", "🌧️ 'Guarda-chuva'"),
                        correctAnswer = "🌧️ 'Impermeável' (in + permeável)",
                        explanation = "'Im-' é um prefixo colocado antes da raiz! 🌧️"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a palavra primitiva de 'infelizmente'?",
                        options = listOf("feliz", "mente", "infeliz"),
                        correctAnswer = "feliz",
                        explanation = "'Feliz' é a palavra base primitiva que recebeu prefixo (in-) e sufixo (-mente)!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'guarda-sol' é uma palavra composta por duas palavras ligadas por hífen.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Guarda + sol = palavra composta."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que sufixo podemos juntar à palavra 'real' para formar um advérbio de modo?",
                        options = listOf("-mente (realmente)", "-oso", "-eiro"),
                        correctAnswer = "-mente (realmente)",
                        explanation = "O sufixo '-mente' cria advérbios de modo!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Identifica o processo de formação:",
                        pairs = mapOf(
                            "desleal" to "Derivação por Prefixação (des-)",
                            "livreiro" to "Derivação por Sufixação (-eiro)",
                            "couve-flor" to "Composição por aglutinação/hífen",
                            "infelizmente" to "Prefixação e Sufixação"
                        ),
                        explanation = "Conhecimento estrutural impecável sobre formação de palavras!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f11",
                numero = 11,
                title = "Géneros Textuais: Notícia, Carta e Banda Desenhada",
                description = "Aprende as características do jornalismo, correspondência e banda desenhada.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Numa notícia de jornal, a que perguntas principais deve responder o primeiro parágrafo (lead)?",
                        options = listOf("O quê, Quem, Quando, Onde e Porquê", "Apenas quem é o autor", "Qual é a cor preferida do jornalista"),
                        correctAnswer = "O quê, Quem, Quando, Onde e Porquê",
                        explanation = "Uma boa notícia informa com clareza o acontecimento, intervenientes, tempo e local!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na Banda Desenhada (BD), as falas das personagens são escritas dentro de 'balões de fala'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os balões de fala e de pensamento ilustram os diálogos."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "No cabeçalho de uma carta pessoal devemos sempre escrever a data e o ___ de onde escrevemos.",
                        options = listOf("local / cidade", "preço da carta", "número do sapato"),
                        correctAnswer = "local / cidade",
                        explanation = "Ex: 'Lisboa, 15 de Outubro de 2026'."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que elemento visual da BD indica o pensamento silencioso de uma personagem?",
                        options = listOf("💭 Balão em forma de nuvenzinha", "💬 Balão com bico reto", "💥 Balão em ziguezague de grito"),
                        correctAnswer = "💭 Balão em forma de nuvenzinha",
                        explanation = "O balão em nuvem com bolinhas indica pensamento secreto! 💭"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a despedida afetuosa no final de uma carta?",
                        options = listOf("Saudação final / Despedida", "Título", "Legenda"),
                        correctAnswer = "Saudação final / Despedida",
                        explanation = "Ex: 'Com carinho e saudades, do teu amigo Zé.'"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O texto dramático ou de teatro é escrito com o nome das personagens antes de cada fala e indicações cénicas (didascálias).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O texto teatral é feito para ser representado por atores."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Cada quadrado de imagem que compõe a história de uma Banda Desenhada chama-se...",
                        options = listOf("Vinheta / Prancha", "Página", "Quadro de giz"),
                        correctAnswer = "Vinheta / Prancha",
                        explanation = "Uma vinheta é cada um dos retângulos ilustrados."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o género textual ao seu objetivo:",
                        pairs = mapOf(
                            "Notícia" to "Informar o público sobre factos reais",
                            "Carta pessoal" to "Comunicar com amigos ou familiares à distância",
                            "Banda Desenhada" to "Contar uma história divertida combinando texto e imagens",
                            "Poema" to "Expressar sentimentos através de ritmo e rima"
                        ),
                        explanation = "Excelente compreensão dos diversos géneros textuais!"
                    )
                )
            ),
            Worksheet(
                id = "pt_3_f12",
                numero = 12,
                title = "Grande Avaliação Final de Português do 3.º Ano",
                description = "Desafios integrados de gramática, compreensão e raciocínio textual.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_3_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'As crianças inteligentes resolveram o enigma com facilidade', qual é o Sujeito?",
                        options = listOf("As crianças inteligentes", "resolveram o enigma", "o enigma"),
                        correctAnswer = "As crianças inteligentes",
                        explanation = "'As crianças inteligentes' é o sujeito completo da ação!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'fácil' forma o superlativo absoluto sintético 'facílimo'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Fácil -> Facílimo; Difícil -> Dificílimo."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra 'árvore' quanto à acentuação tónica (ÁR-vo-re) é classificada como ___",
                        options = listOf("Esdrúxula", "Grave", "Aguda"),
                        correctAnswer = "Esdrúxula",
                        explanation = "Tem o acento tónico na antepenúltima sílaba!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases usa corretamente a pontuação de discurso direto com diálogo?",
                        options = listOf("💬 O Zé disse:\n— Vamos aprender!", "💬 O Zé disse vamos aprender", "💬 O Zé ??? vamos"),
                        correctAnswer = "💬 O Zé disse:\n— Vamos aprender!",
                        explanation = "Usa dois pontos e travessão de diálogo na linha seguinte! 💬"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o antónimo da palavra 'corajoso'?",
                        options = listOf("Cobarde / Medroso", "Valente", "Forte"),
                        correctAnswer = "Cobarde / Medroso",
                        explanation = "O oposto de quem tem coragem é quem tem medo ou cobardia."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O verbo 'partir' pertence à 3.ª conjugação (verbos terminados em '-ir').",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1.ª (-ar), 2.ª (-er), 3.ª (-ir)."
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os passos para redigir uma composição escrita de excelência:",
                        options = listOf("Planear as ideias num rascunho", "Escrever os parágrafos com pontuação", "Rever e corrigir a ortografia"),
                        correctAnswers = listOf("Planear as ideias num rascunho", "Escrever os parágrafos com pontuação", "Rever e corrigir a ortografia"),
                        correctAnswer = "Planear as ideias num rascunho-Escrever os parágrafos com pontuação-Rever e corrigir a ortografia",
                        explanation = "Planear, redigir e rever garante notas máximas!"
                    ),
                    WorksheetExercise(
                        id = "pt_3_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio final das classes gramaticais:",
                        pairs = mapOf(
                            "solidariedade" to "Nome Comum Abstrato",
                            "navegámos" to "Verbo no Pretérito Perfeito",
                            "velozmente" to "Advérbio de modo",
                            "esta" to "Determinante Demonstrativo"
                        ),
                        explanation = "Parabéns, Campeão da Língua Portuguesa do 3.º Ano!"
                    )
                )
            )
        )
    )

    val matematica = WorksheetDiscipline(
        id = "mat_3",
        title = "Matemática",
        emoji = "🧮",
        color = Color(0xFF10B981),
        worksheets = listOf(
            Worksheet(
                id = "mat_3_f1",
                numero = 1,
                title = "Números até 10 000: Decomposição e Valor Posicional",
                description = "Milhares, centenas, dezenas e unidades. Leitura por ordens e classes.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🔢",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "No número 4 582, qual é o algarismo das UNIDADES DE MILHAR?",
                        options = listOf("4", "5", "8"),
                        correctAnswer = "4",
                        explanation = "O 4 vale 4 000 (4 unidades de milhar)!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 7 305 lê-se 'sete mil trezentos e cinco'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Tem 7 milhares, 3 centenas, 0 dezenas e 5 unidades."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Decompõe 6 450: 6000 + 400 + ___",
                        options = listOf("50", "5", "500"),
                        correctAnswer = "50",
                        explanation = "6 000 + 400 + 50 = 6 450."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes números é o MAIOR de todos?",
                        options = listOf("🏆 9 850", "🥈 9 580", "🥉 8 999"),
                        correctAnswer = "🏆 9 850",
                        explanation = "9 850 tem mais centenas que 9 580! 🏆"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas centenas completas existem em 3 000?",
                        options = listOf("30 centenas", "3 centenas", "300 centenas"),
                        correctAnswer = "30 centenas",
                        explanation = "3 000 dividido por 100 = 30 centenas!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número imediatamente a seguir a 9 999 é 10 000 (dez mil).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 9 999 + 1 = 10 000 (uma dezena de milhar)."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os números do menor para o maior:",
                        options = listOf("3 250", "5 800", "7 120"),
                        correctAnswers = listOf("3 250", "5 800", "7 120"),
                        correctAnswer = "3 250-5 800-7 120",
                        explanation = "3250 < 5800 < 7120."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o número à sua decomposição:",
                        pairs = mapOf(
                            "2 438" to "2000 + 400 + 30 + 8",
                            "5 070" to "5000 + 70",
                            "8 902" to "8000 + 900 + 2",
                            "1 500" to "1000 + 500"
                        ),
                        explanation = "Excelente domínio do sistema de numeração decimal!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f2",
                numero = 2,
                title = "Adição e Subtração com Algoritmos Formais",
                description = "Operações com números de 3 e 4 algarismos, transporte e estratégias de cálculo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "➕",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 1 450 + 2 350?",
                        options = listOf("3 800", "3 700", "4 000"),
                        correctAnswer = "3 800",
                        explanation = "1450 + 2350 = 3800!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na subtração 5 000 - 1 200 o resultado é 3 800.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 5000 - 1200 = 3800."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Calcula: 2 700 + ___ = 3 000",
                        options = listOf("300", "200", "400"),
                        correctAnswer = "300",
                        explanation = "2700 + 300 = 3000."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "O Zé tinha 1 200 pontos no jogo e ganhou mais 850. Quantos tem?",
                        options = listOf("🎮 2 050 pontos", "🎮 1 950 pontos", "🎮 2 150 pontos"),
                        correctAnswer = "🎮 2 050 pontos",
                        explanation = "1200 + 850 = 2050 pontos! 🎮"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 4 680 - 680?",
                        options = listOf("4 000", "3 000", "4 600"),
                        correctAnswer = "4 000",
                        explanation = "Retirando 680 sobram 4000 exatos."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A propriedade comutativa da adição diz que a ordem das parcelas não altera a soma (ex: 200 + 500 = 500 + 200).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A ordem não altera o resultado da adição."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se somares 999 + 1 obténs:",
                        options = listOf("1 000", "100", "1 009"),
                        correctAnswer = "1 000",
                        explanation = "999 + 1 = 1000."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula mentalmente e associa:",
                        pairs = mapOf(
                            "1 500 + 1 500" to "3 000",
                            "4 000 - 1 500" to "2 500",
                            "6 200 + 800" to "7 000",
                            "10 000 - 2 000" to "8 000"
                        ),
                        explanation = "Cálculo mental de somas e subtrações impecável!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f3",
                numero = 3,
                title = "Tabuadas Avançadas: 3, 4, 6, 7, 8 e 9",
                description = "Consolidação das tabuadas completas e regularidades numéricas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✖️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 7 x 8?",
                        options = listOf("56", "54", "64"),
                        correctAnswer = "56",
                        explanation = "7 vezes 8 é igual a 56!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na tabuada do 9, a soma dos algarismos do resultado dá sempre 9 (ex: 9x2=18 -> 1+8=9; 9x5=45 -> 4+5=9).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É o truque fascinante da tabuada do 9!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Quanto é 6 x 7 = ___?",
                        options = listOf("42", "48", "36"),
                        correctAnswer = "42",
                        explanation = "6 x 7 = 42."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 9 x 9?",
                        options = listOf("🎯 81", "🎯 72", "🎯 90"),
                        correctAnswer = "🎯 81",
                        explanation = "9 x 9 = 81! 🎯"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 8 x 8?",
                        options = listOf("64", "62", "72"),
                        correctAnswer = "64",
                        explanation = "8 x 8 = 64."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "4 x 6 dá o mesmo resultado que 6 x 4 (Propriedade Comutativa).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ambos dão 24."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 7 x 7?",
                        options = listOf("49", "47", "56"),
                        correctAnswer = "49",
                        explanation = "7 x 7 = 49."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga a multiplicação ao produto correto:",
                        pairs = mapOf(
                            "6 x 8" to "48",
                            "7 x 9" to "63",
                            "8 x 9" to "72",
                            "4 x 8" to "32"
                        ),
                        explanation = "Dominas todas as tabuadas com extrema rapidez!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f4",
                numero = 4,
                title = "Algoritmo da Multiplicação por 1 e 2 Algarismos",
                description = "Armar e efetuar multiplicações (ex: 124 x 3 e 45 x 12) passo a passo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧮",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 123 x 3?",
                        options = listOf("369", "359", "379"),
                        correctAnswer = "369",
                        explanation = "3x3=9, 3x2=6, 3x1=3 -> 369!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Ao multiplicar qualquer número por 10, basta acrescentar um zero à direita (ex: 45 x 10 = 450).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Multiplicar por 10 avança uma casa decimal."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Calcula: 25 x 4 = ___",
                        options = listOf("100", "80", "90"),
                        correctAnswer = "100",
                        explanation = "25 x 4 = 100 (4 moedas de 25 cêntimos perfazem 1 euro)!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 200 x 5?",
                        options = listOf("💵 1 000", "💵 500", "💵 2 000"),
                        correctAnswer = "💵 1 000",
                        explanation = "200 x 5 = 1 000! 💵"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 50 x 20?",
                        options = listOf("1 000", "100", "500"),
                        correctAnswer = "1 000",
                        explanation = "5 x 2 = 10; juntando os dois zeros = 1 000!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na multiplicação de 32 x 11, o resultado é 352.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 32 x 10 = 320; 320 + 32 = 352."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Uma fábrica produz 150 caixas de sumos por dia. Quantas caixas produz em 4 dias?",
                        options = listOf("600 caixas", "450 caixas", "500 caixas"),
                        correctAnswer = "600 caixas",
                        explanation = "150 x 4 = 600 caixas!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Multiplica rapidamente:",
                        pairs = mapOf(
                            "12 x 5" to "60",
                            "15 x 6" to "90",
                            "40 x 8" to "320",
                            "25 x 8" to "200"
                        ),
                        explanation = "Muito bem! Excelente domínio do algoritmo da multiplicação."
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f5",
                numero = 5,
                title = "Introdução à Divisão: Partilha Equitativa e Resto",
                description = "Dividendo, divisor, quociente e resto. Divisão exata e não exata.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "➗",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se partilhares 24 rebuçados igualmente por 4 amigos, quantos recebe cada um?",
                        options = listOf("6 rebuçados", "5 rebuçados", "8 rebuçados"),
                        correctAnswer = "6 rebuçados",
                        explanation = "24 : 4 = 6 porque 6 x 4 = 24!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Numa divisão exata, o RESTO é sempre igual a 0.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Não sobra nada na partilha."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Se dividires 17 morangos por 3 crianças, cada uma recebe 5 morangos e o resto é ___",
                        options = listOf("2", "1", "0"),
                        correctAnswer = "2",
                        explanation = "5 x 3 = 15; 17 - 15 = 2 de resto!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 45 dividido por 5 (45 : 5)?",
                        options = listOf("🔟 9", "🔟 8", "🔟 7"),
                        correctAnswer = "🔟 9",
                        explanation = "45 : 5 = 9 porque 9 x 5 = 45!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o número que está a ser dividido na conta de dividir?",
                        options = listOf("Dividendo", "Divisor", "Quociente"),
                        correctAnswer = "Dividendo",
                        explanation = "Dividendo : Divisor = Quociente (e Resto)."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O resto de uma divisão pode ser maior ou igual ao divisor.",
                        correctAnswer = "false",
                        explanation = "Falso! O resto tem de ser SEMPRE menor do que o divisor."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 80 dividido por 10?",
                        options = listOf("8", "800", "18"),
                        correctAnswer = "8",
                        explanation = "80 : 10 = 8."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula a divisão exata:",
                        pairs = mapOf(
                            "36 : 6" to "6",
                            "56 : 7" to "8",
                            "72 : 8" to "9",
                            "40 : 5" to "8"
                        ),
                        explanation = "Excelente raciocínio de divisão e partilha!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f6",
                numero = 6,
                title = "Frações: Metades, Terços, Quartos e Décimos",
                description = "Numerador, denominador, representação em figuras e leitura de frações.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🍰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Numa fração como 3/4, o que representa o número de baixo (Denominador 4)?",
                        options = listOf("O número total de partes iguais em que a unidade foi dividida", "As partes que comemos", "O preço do bolo"),
                        correctAnswer = "O número total de partes iguais em que a unidade foi dividida",
                        explanation = "O denominador indica o número de partes em que o todo foi repartido!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A fração 1/2 representa a metade de um todo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1/2 lê-se 'um meio' ou metade."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Se comeres 3 fatias de uma piza dividida em 8 fatias iguais, comeste a fração ___",
                        options = listOf("3/8", "8/3", "1/8"),
                        correctAnswer = "3/8",
                        explanation = "3 fatias das 8 totais = 3/8 (três oitavos)."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual fração representa a unidade inteira completa?",
                        options = listOf("🎂 4/4", "🍰 1/4", "🧁 2/4"),
                        correctAnswer = "🎂 4/4",
                        explanation = "Quando o numerador é igual ao denominador (4/4 = 1), temos a unidade completa! 🎂"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se lê a fração 1/3?",
                        options = listOf("Um terço", "Um três", "Três quartos"),
                        correctAnswer = "Um terço",
                        explanation = "1/3 lê-se 'um terço'."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A fração 2/4 é equivalente à fração 1/2.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Duas partes de 4 correspondem exatamente a metade (1/2)."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual fração é maior: 1/2 ou 1/4?",
                        options = listOf("1/2", "1/4", "São iguais"),
                        correctAnswer = "1/2",
                        explanation = "Dividir em 2 partes dá fatias maiores do que dividir em 4 partes!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a fração à sua leitura por extenso:",
                        pairs = mapOf(
                            "1/2" to "Um meio (metade)",
                            "1/4" to "Um quarto",
                            "3/4" to "Três quartos",
                            "1/10" to "Um décimo"
                        ),
                        explanation = "Perfeito domínio dos conceitos fundamentais de frações!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f7",
                numero = 7,
                title = "Números Decimais e Dinheiro",
                description = "A vírgula, décimas e centésimas, preços e trocos no dia a dia.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💶",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "No número decimal 14,75€, o que representa o número 75 depois da vírgula?",
                        options = listOf("75 cêntimos (centésimas)", "75 euros inteiros", "75 notas"),
                        correctAnswer = "75 cêntimos (centésimas)",
                        explanation = "A parte decimal à direita da vírgula representa os cêntimos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "0,5 é o mesmo que a fração 1/2 (metade).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 0,5 = 5/10 = metade de 1."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Quanto é 2,50€ + 1,50€? Dá exatamente ___ €.",
                        options = listOf("4,00", "3,50", "5,00"),
                        correctAnswer = "4,00",
                        explanation = "2,50€ + 1,50€ = 4,00€!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes números decimais é MAIOR?",
                        options = listOf("📈 3,85", "📉 3,58", "📉 3,09"),
                        correctAnswer = "📈 3,85",
                        explanation = "3,85 tem 8 décimas, mais do que 3,58 (5 décimas)! 📈"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se lê o número decimal 0,7?",
                        options = listOf("Sete décimas", "Sete centésimas", "Sete inteiros"),
                        correctAnswer = "Sete décimas",
                        explanation = "A primeira casa decimal à direita da vírgula são as décimas."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "1 euro e 5 cêntimos escreve-se 1,50€.",
                        correctAnswer = "false",
                        explanation = "Falso! Escreve-se 1,05€. 1,50€ são 1 euro e 50 cêntimos."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Pagaste um brinquedo de 16,50€ com uma nota de 20€. Qual é o teu troco?",
                        options = listOf("3,50€", "4,50€", "3,00€"),
                        correctAnswer = "3,50€",
                        explanation = "20,00€ - 16,50€ = 3,50€ de troco."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a fração ao número decimal correspondente:",
                        pairs = mapOf(
                            "1/10" to "0,1 (uma décima)",
                            "5/10" to "0,5 (cinco décimas)",
                            "1/100" to "0,01 (uma centésima)",
                            "75/100" to "0,75 (setenta e cinco centésimas)"
                        ),
                        explanation = "Excelente compreensão dos números decimais!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f8",
                numero = 8,
                title = "Geometria: Retas e Ângulos (Reto, Agudo e Obtuso)",
                description = "Retas paralelas, perpendiculares, concorrentes e classificação de ângulos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📐",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam duas retas que nunca se cruzam, como os carris de um comboio?",
                        options = listOf("Retas Paralelas", "Retas Perpendiculares", "Retas Concorrentes"),
                        correctAnswer = "Retas Paralelas",
                        explanation = "As retas paralelas mantêm sempre a mesma distância e nunca se tocam!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um ÂNGULO RETO mede exatamente 90 graus (o canto de uma folha de papel ou de um esquadro).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O ângulo reto é a base da geometria ortogonal."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um ângulo mais fechado e MENOR que o ângulo reto chama-se ângulo ___",
                        options = listOf("Agudo", "Obtuso", "Raso"),
                        correctAnswer = "Agudo",
                        explanation = "Ângulo agudo: < 90 graus. Ângulo obtuso: > 90 graus."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que instrumento geométrico usamos para desenhar círculos e circunferências perfeitas?",
                        options = listOf("⭕ Compasso", "📏 Régua", "📐 Esquadro"),
                        correctAnswer = "⭕ Compasso",
                        explanation = "O compasso traça arcos e círculos com raio constante! ⭕"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Duas retas que se cruzam formando 4 ângulos retos de 90° chamam-se...",
                        options = listOf("Retas Perpendiculares", "Retas Paralelas", "Retas Curvas"),
                        correctAnswer = "Retas Perpendiculares",
                        explanation = "Formam uma cruz perfeita com ângulos retos."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um ângulo obtuso é maior e mais aberto do que um ângulo reto.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Mede entre 90° e 180°."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos ângulos retos tem um Retângulo?",
                        options = listOf("4 ângulos retos", "2 ângulos retos", "0 ângulos retos"),
                        correctAnswer = "4 ângulos retos",
                        explanation = "Todos os 4 cantos do retângulo são ângulos retos de 90°!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os ângulos e retas:",
                        pairs = mapOf(
                            "Ângulo de 90°" to "Ângulo Reto",
                            "Ângulo de 45°" to "Ângulo Agudo (< 90°)",
                            "Ângulo de 120°" to "Ângulo Obtuso (> 90°)",
                            "Carris de comboio" to "Retas Paralelas"
                        ),
                        explanation = "Perfeito! Dominas as retas e os ângulos geométricos."
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f9",
                numero = 9,
                title = "Perímetro de Polígonos e Área em Quadrícula",
                description = "Calcula o contorno (perímetro) e a superfície interior (área) de figuras planas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📏",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é o PERÍMETRO de uma figura geométrica plana?",
                        options = listOf("A soma do comprimento de todos os seus lados (o contorno)", "A cor da figura", "O peso da figura"),
                        correctAnswer = "A soma do comprimento de todos os seus lados (o contorno)",
                        explanation = "Perímetro é o comprimento da linha de contorno da figura!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um quadrado com lados de 5 cm tem um perímetro de 20 cm (5 + 5 + 5 + 5 = 20 cm).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 4 lados x 5 cm = 20 cm."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um retângulo tem comprimento de 6 cm e largura de 4 cm. O seu perímetro é ___ cm.",
                        options = listOf("20", "10", "24"),
                        correctAnswer = "20",
                        explanation = "6 + 4 + 6 + 4 = 20 cm!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se contares 12 quadradinhos no interior de uma figura na quadrícula, a sua ÁREA é de...",
                        options = listOf("🔲 12 unidades quadradas", "🔲 24 cm", "🔲 6 lados"),
                        correctAnswer = "🔲 12 unidades quadradas",
                        explanation = "A área corresponde ao número total de unidades de superfície cobertas! 🔲"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o perímetro de um triângulo equilátero com 8 cm de lado?",
                        options = listOf("24 cm", "16 cm", "32 cm"),
                        correctAnswer = "24 cm",
                        explanation = "3 lados iguais: 8 + 8 + 8 = 24 cm!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Duas figuras com formas diferentes podem ter exatamente a mesma área.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Figuras de formatos distintos podem ocupar o mesmo número de quadradinhos de área."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se a área de um quadrado é 9 quadradinhos, quantos quadradinhos tem de lado?",
                        options = listOf("3", "4", "2"),
                        correctAnswer = "3",
                        explanation = "3 x 3 = 9 quadradinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula o perímetro das figuras:",
                        pairs = mapOf(
                            "Quadrado de 4 cm de lado" to "P = 16 cm",
                            "Retângulo de 5 cm por 3 cm" to "P = 16 cm",
                            "Triângulo de lados 3, 4 e 5 cm" to "P = 12 cm",
                            "Hexágono regular de 2 cm de lado" to "P = 12 cm"
                        ),
                        explanation = "Excelente cálculo de perímetros e áreas!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f10",
                numero = 10,
                title = "Medidas de Tempo: Horas, Minutos, Décadas e Séculos",
                description = "Conversões temporais: 1 hora = 60 min, 1 minuto = 60 s, séculos e anos bissextos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⏱️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos minutos tem 1 hora e meia (1h 30min)?",
                        options = listOf("90 minutos", "60 minutos", "100 minutos"),
                        correctAnswer = "90 minutos",
                        explanation = "60 minutos + 30 minutos = 90 minutos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um SÉCULO é um período de tempo correspondente a exatamente 100 anos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1 século = 100 anos; 1 milénio = 1 000 anos."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Uma DÉCADA é um período de ___ anos.",
                        options = listOf("10", "5", "50"),
                        correctAnswer = "10",
                        explanation = "1 década = 10 anos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quantos dias tem um ano bissexto (com o dia 29 de Fevereiro)?",
                        options = listOf("📅 366 dias", "📅 365 dias", "📅 360 dias"),
                        correctAnswer = "📅 366 dias",
                        explanation = "De 4 em 4 anos o ano bissexto tem 366 dias! 📅"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos segundos tem 1 minuto inteiro?",
                        options = listOf("60 segundos", "100 segundos", "30 segundos"),
                        correctAnswer = "60 segundos",
                        explanation = "1 minuto = 60 segundos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "2 horas correspondem a 120 minutos (2 x 60 = 120).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 60 + 60 = 120 minutos."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em que século nos encontramos no ano 2026?",
                        options = listOf("Século XXI (21)", "Século XX (20)", "Século XIX (19)"),
                        correctAnswer = "Século XXI (21)",
                        explanation = "O século XXI vai do ano 2001 ao ano 2100!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as unidades de tempo:",
                        pairs = mapOf(
                            "1 hora" to "60 minutos",
                            "1 dia completo" to "24 horas",
                            "1 década" to "10 anos",
                            "1 século" to "100 anos"
                        ),
                        explanation = "Muito bem! Dominas o tempo e as suas unidades históricas e quotidianas."
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f11",
                numero = 11,
                title = "Estatística: Diagramas de Venn, Carroll e Gráficos",
                description = "Organização de conjuntos, diagramas lógicos e análise crítica de gráficos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "No Diagrama de Venn, o que representa a zona de interseção onde os dois círculos se sobrepõem?",
                        options = listOf("Elementos que cumprem as DUAS características ao mesmo tempo", "Elementos que não cumprem nenhuma", "Apenas elementos da direita"),
                        correctAnswer = "Elementos que cumprem as DUAS características ao mesmo tempo",
                        explanation = "A interseção partilha as propriedades de ambos os conjuntos!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Diagrama de Carroll usa uma tabela de dupla entrada para classificar elementos em categorias sim/não.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 'É número par / Não é par' e 'É maior que 50 / Menor que 50'."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A MODA de um conjunto de dados é o valor que aparece com ___ frequência (mais vezes).",
                        options = listOf("maior", "menor", "média"),
                        correctAnswer = "maior",
                        explanation = "Moda = o valor mais popular e frequente!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes diagramas é formado por círculos entrelaçados?",
                        options = listOf("⭕ Diagrama de Venn", "📊 Gráfico de barras", "🥧 Gráfico circular"),
                        correctAnswer = "⭕ Diagrama de Venn",
                        explanation = "O Diagrama de Venn usa círculos para ilustrar conjuntos e interseções! ⭕"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Num gráfico de linhas sobre a temperatura: Segunda (18°), Terça (22°), Quarta (20°). Que dia foi o mais quente?",
                        options = listOf("Terça-feira (22°)", "Segunda-feira", "Quarta-feira"),
                        correctAnswer = "Terça-feira (22°)",
                        explanation = "O ponto mais alto da linha é na Terça-feira com 22°C."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A amplitude de um conjunto de dados é a diferença entre o valor máximo e o valor mínimo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Amplitude = Máximo - Mínimo."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se as notas foram 4, 4, 5, 5, 5, qual é a moda?",
                        options = listOf("5", "4", "3"),
                        correctAnswer = "5",
                        explanation = "O 5 apareceu 3 vezes, logo é a moda."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os conceitos estatísticos:",
                        pairs = mapOf(
                            "Diagrama de Venn" to "Círculos de conjuntos",
                            "Diagrama de Carroll" to "Tabela de classificação binária",
                            "Moda" to "Dado com maior frequência",
                            "Amplitude" to "Valor Máximo menos Valor Mínimo"
                        ),
                        explanation = "Excelente análise de dados e representação estatística!"
                    )
                )
            ),
            Worksheet(
                id = "mat_3_f12",
                numero = 12,
                title = "Grande Desafio de Raciocínio Matemático do 3.º Ano",
                description = "Problemas em múltiplas etapas, raciocínio lógico e desafios olímpicos.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_3_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Uma turma de 24 alunos foi ao teatro. Cada bilhete custou 5€. Quanto pagaram no total todos os alunos?",
                        options = listOf("120€", "100€", "150€"),
                        correctAnswer = "120€",
                        explanation = "24 x 5€ = 120€ no total!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O dobro de 150 somado com o triplo de 100 é igual a 600.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! (2 x 150 = 300) + (3 x 100 = 300) = 600!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Descobre o número misterioso: É o quádruplo de 25 mais meia centena. É o ___",
                        options = listOf("150", "125", "200"),
                        correctAnswer = "150",
                        explanation = "Quádruplo de 25 = 100. Meia centena = 50. 100 + 50 = 150!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se dividires 100 laranjas por 4 caixas iguais, quantas laranjas ficam em cada caixa?",
                        options = listOf("🍊 25 laranjas", "🍊 20 laranjas", "🍊 50 laranjas"),
                        correctAnswer = "🍊 25 laranjas",
                        explanation = "100 : 4 = 25 laranjas por caixa! 🍊"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a soma dos ângulos de um triângulo qualquer?",
                        options = listOf("180 graus", "90 graus", "360 graus"),
                        correctAnswer = "180 graus",
                        explanation = "A soma dos 3 ângulos internos de qualquer triângulo é sempre 180°!"
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um número é divisível por 2 se o seu último algarismo for par (0, 2, 4, 6 ou 8).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Critério de divisibilidade por 2."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as quantias monetárias da menor para a maior:",
                        options = listOf("4,75€", "8,20€", "12,50€"),
                        correctAnswers = listOf("4,75€", "8,20€", "12,50€"),
                        correctAnswer = "4,75€-8,20€-12,50€",
                        explanation = "4,75€ < 8,20€ < 12,50€."
                    ),
                    WorksheetExercise(
                        id = "mat_3_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio das contas finais:",
                        pairs = mapOf(
                            "250 x 4" to "1 000",
                            "750 : 3" to "250",
                            "1 250 + 8750" to "10 000",
                            "9 x 90" to "810"
                        ),
                        explanation = "Parabéns, Campeão da Matemática do 3.º Ano!"
                    )
                )
            )
        )
    )

    val estudoMeio = WorksheetDiscipline(
        id = "em_3",
        title = "Estudo do Meio",
        emoji = "🌍🌱",
        color = Color(0xFFF59E0B),
        worksheets = listOf(
            Worksheet(
                id = "em_3_f1",
                numero = 1,
                title = "O Sistema Digestivo e a Nutrição",
                description = "O caminho dos alimentos: boca, esófago, estômago, intestinos e absorção.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🍎",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde tem início o processo da digestão dos alimentos?",
                        options = listOf("Na boca (com a mastigação e a saliva)", "No estômago", "No coração"),
                        correctAnswer = "Na boca (com a mastigação e a saliva)",
                        explanation = "Na boca os dentes trituram e a saliva inicia a digestão química!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No estômago, os sucos gástricos misturam os alimentos transformando-os no quimo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O estômago funciona como um misturador com ácidos gástricos."
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A absorção dos nutrientes para o sangue ocorre nas paredes do intestino ___",
                        options = listOf("delgado", "grosso", "médio"),
                        correctAnswer = "delgado",
                        explanation = "O intestino delgado é onde as vitaminas e nutrientes passam para o sangue."
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes órgãos é o maior tubo muscular que conduz a comida da boca ao estômago?",
                        options = listOf("👅 Esófago", "🫁 Pulmão", "🧠 Cérebro"),
                        correctAnswer = "👅 Esófago",
                        explanation = "O esófago conduz o bolo alimentar através de movimentos peristálticos!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que órgão absorve a água e forma as fezes com os restos não digeridos?",
                        options = listOf("Intestino grosso", "Boca", "Coração"),
                        correctAnswer = "Intestino grosso",
                        explanation = "O intestino grosso compacta os resíduos que serão eliminados."
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Mastigar devagar os alimentos facilita muito o trabalho de todo o sistema digestivo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Uma boa mastigação previne dores de estômago e má digestão."
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O fígado e o pâncreas ajudam a digestão produzindo...",
                        options = listOf("Sucos digestivos e bílis", "Sangue puro", "Ossos"),
                        correctAnswer = "Sucos digestivos e bílis",
                        explanation = "Ajudam a digerir gorduras e açúcares."
                    ),
                    WorksheetExercise(
                        id = "em_3_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Ordena o trajeto do alimento no tubo digestivo:",
                        pairs = mapOf(
                            "1.º Passo" to "Boca (mastigação)",
                            "2.º Passo" to "Esófago (transporte)",
                            "3.º Passo" to "Estômago (digestão gástrica)",
                            "4.º Passo" to "Intestino Delgado (absorção)"
                        ),
                        explanation = "Excelente conhecimento do funcionamento digestivo!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f2",
                numero = 2,
                title = "Sistemas Respiratório e Circulatório",
                description = "Inspiração, expiração, pulmões, coração, vasos sanguíneos e pulsação.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🫀",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o órgão muscular que funciona como uma bomba, fazendo circular o sangue por todo o corpo?",
                        options = listOf("Coração", "Fígado", "Pulmão"),
                        correctAnswer = "Coração",
                        explanation = "O coração bombeia o sangue oxigenado para todos os órgãos e tecidos!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na INSPIRAÇÃO os pulmões enchem-se de ar rico em oxigénio e o peito expande-se.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Na inspiração captamos oxigénio; na expiração expelimos dióxido de carbono."
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Os tubos finos que transportam o sangue do coração para todo o corpo chamam-se ___",
                        options = listOf("Artérias e veias", "Ossos", "Nervos"),
                        correctAnswer = "Artérias e veias",
                        explanation = "As artérias levam o sangue rico em oxigénio e as veias trazem-no de volta ao coração."
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes órgãos é o principal responsável pelas trocas gasosas da respiração?",
                        options = listOf("🫁 Pulmões", "🧠 Cérebro", "🦷 Dentes"),
                        correctAnswer = "🫁 Pulmões",
                        explanation = "Os pulmões realizam a troca de oxigénio por dióxido de carbono! 🫁"
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que acontece ao ritmo dos batimentos cardíacos quando corremos velozmente?",
                        options = listOf("Acelera para levar mais oxigénio aos músculos", "Pára completamente", "Fica mais lento"),
                        correctAnswer = "Acelera para levar mais oxigénio aos músculos",
                        explanation = "O coração bombeia mais rápido para abastecer os músculos de energia e oxigénio!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos inspirar o ar pelo nariz porque os pelos e mucosas nasais filtram e aquecem o ar.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O nariz purifica e climatiza o ar antes de chegar aos pulmões."
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde podemos sentir facilmente a nossa pulsação com os dedos?",
                        options = listOf("No pulso ou no pescoço", "Na ponta da unha", "Na sola do sapato"),
                        correctAnswer = "No pulso ou no pescoço",
                        explanation = "A artéria radial no pulso permite sentir os batimentos cardíacos."
                    ),
                    WorksheetExercise(
                        id = "em_3_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a função ao órgão vital:",
                        pairs = mapOf(
                            "Coração" to "Bombear o sangue pelo corpo",
                            "Pulmões" to "Trocas gasosas (oxigénio/CO2)",
                            "Traqueia" to "Canal que conduz o ar aos brônquios",
                            "Glóbulos vermelhos" to "Transportar oxigénio no sangue"
                        ),
                        explanation = "Perfeito! Compreendes a sincronia entre a respiração e a circulação sanguínea."
                    )
                )
            ),
            Worksheet(
                id = "em_3_f3",
                numero = 3,
                title = "O Sistema Excretor e a Pele",
                description = "Rins, bexiga, urina, suor e eliminação de toxinas do organismo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💧",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que órgãos em forma de feijão filtram o sangue para retirar impurezas e produzir a urina?",
                        options = listOf("Rins", "Pulmões", "Estômago"),
                        correctAnswer = "Rins",
                        explanation = "Os rins funcionam como filtros de alta precisão que purificam o nosso sangue!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A bexiga é o órgão elástico onde a urina é armazenada antes de ser expelida.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A bexiga guarda a urina até irmos à casa de banho."
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A pele elimina água e toxinas através do ___ quando fazemos esforço ou está calor.",
                        options = listOf("suor", "sangue", "cabelo"),
                        correctAnswer = "suor",
                        explanation = "O suor arrefece a temperatura do corpo e elimina substâncias residuais."
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o hábito mais importante para manter os rins saudáveis e a funcionar perfeitamente?",
                        options = listOf("💧 Beber bastante água ao longo do dia", "🥤 Beber refrigerantes com corantes", "🧂 Comer comida cheia de sal"),
                        correctAnswer = "💧 Beber bastante água ao longo do dia",
                        explanation = "A água limpa e hidrata os rins prevenindo infeções! 💧"
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o maior órgão de todo o corpo humano?",
                        options = listOf("A Pele", "O Fígado", "O Fémur"),
                        correctAnswer = "A Pele",
                        explanation = "A pele reveste todo o nosso corpo, protegendo-nos contra bactérias e calor!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Prender a urina durante muitas horas faz muito bem à saúde da bexiga.",
                        correctAnswer = "false",
                        explanation = "Falso! Prender a urina pode provocar infeções urinárias."
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os canais que transportam a urina dos rins até à bexiga chamam-se...",
                        options = listOf("Ureteros", "Brônquios", "Artérias"),
                        correctAnswer = "Ureteros",
                        explanation = "Os dois ureteros ligam cada rim à bexiga."
                    ),
                    WorksheetExercise(
                        id = "em_3_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a função excretora ao órgão respetivo:",
                        pairs = mapOf(
                            "Rins" to "Filtrar o sangue e produzir urina",
                            "Bexiga" to "Armazenar a urina",
                            "Glândulas sudoríparas" to "Produzir suor na pele",
                            "Uretra" to "Canal de saída da urina para o exterior"
                        ),
                        explanation = "Excelente conhecimento do sistema excretor humano!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f4",
                numero = 4,
                title = "Reprodução dos Animais e Metamorfoses",
                description = "Vivíparos, ovíparos, ovovivíparos e a fascinante metamorfose da borboleta e da rã.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🦋",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam os animais cujos embriões se desenvolvem DENTRO de ovos fora do corpo da mãe?",
                        options = listOf("Ovíparos", "Vivíparos", "Mamíferos"),
                        correctAnswer = "Ovíparos",
                        explanation = "Aves, tartarugas e a maioria dos répteis e peixes são ovíparos!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os animais vivíparos (como o cavalo, cão e humano) desenvolvem-se no útero materno até nascerem.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Nascem diretamente do ventre da mãe."
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O processo de transformação radical pelo qual passa a lagarta até virar borboleta chama-se ___",
                        options = listOf("Metamorfose", "Germinação", "Hibernação"),
                        correctAnswer = "Metamorfose",
                        explanation = "A metamorfose transforma a lagarta em crisálida e depois em bela borboleta!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é a primeira fase aquática da rã quando nasce do ovo com cauda?",
                        options = listOf("🐸 Girino", "🐸 Rã adulta", "🐸 Sapo com asas"),
                        correctAnswer = "🐸 Girino",
                        explanation = "O girino nada com cauda e brânquias antes de desenvolver pernas e pulmões! 🐸"
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destes animais é um exemplo de animal VIVÍPARO?",
                        options = listOf("Golfinho", "Galinha", "Crocodilo"),
                        correctAnswer = "Golfinho",
                        explanation = "O golfinho é um mamífero marinho vivíparo!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As aves chocam os ovos no ninho com o calor do seu corpo para que o embrião se desenvolva.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O choco garante a temperatura ideal para a eclosão."
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as fases da metamorfose da borboleta:",
                        options = listOf("Ovo", "Lagarta", "Crisálida / Casulo", "Borboleta adulta"),
                        correctAnswers = listOf("Ovo", "Lagarta", "Crisálida / Casulo", "Borboleta adulta"),
                        correctAnswer = "Ovo-Lagarta-Crisálida / Casulo-Borboleta adulta",
                        explanation = "Ovo -> Lagarta comilona -> Casulo -> Borboleta espetacular!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica quanto ao tipo de reprodução:",
                        pairs = mapOf(
                            "Águia" to "Ovíparo (põe ovos)",
                            "Vaca" to "Vivíparo (nasce da barriga)",
                            "Tartaruga marinha" to "Ovíparo (põe ovos na areia)",
                            "Gato" to "Vivíparo (mamífero)"
                        ),
                        explanation = "Perfeita compreensão da reprodução e metamorfoses animais!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f5",
                numero = 5,
                title = "As Plantas: Germinação e Fotossíntese",
                description = "Condições de germinação, clorofila, luz solar, dióxido de carbono e polinização.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌻",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o processo pelo qual as folhas verdes produzem o alimento da planta usando luz solar?",
                        options = listOf("Fotossíntese", "Digestão", "Transpiração"),
                        correctAnswer = "Fotossíntese",
                        explanation = "A fotossíntese capta luz solar, água e CO2 para produzir glicose e oxigénio!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O pigmento verde das folhas que absorve a luz do Sol chama-se CLOROFILA.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A clorofila dá a cor verde às folhas."
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "De que necessita uma semente para germinar com vigor? Água, ar e ___ adequada.",
                        options = listOf("temperatura / calor", "escuridão gelada", "sal"),
                        correctAnswer = "temperatura / calor",
                        explanation = "Humidade e temperatura amena despertam a semente adormecida."
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes insetos é o mais importante POLINIZADOR de flores na natureza?",
                        options = listOf("🐝 Abelha", "🦟 Mosquito", "🪰 Mosca"),
                        correctAnswer = "🐝 Abelha",
                        explanation = "As abelhas transportam o pólen entre flores permitindo a formação de frutos! 🐝"
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Durante o dia, que gás libertam as plantas para a atmosfera, essencial à vida humana?",
                        options = listOf("Oxigénio", "Dióxido de carbono", "Fumo"),
                        correctAnswer = "Oxigénio",
                        explanation = "As plantas purificam o ar libertando oxigénio puro!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A polinização é o transporte dos grãos de pólen da antera para o estigma da flor.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Permite a fecundação e produção de novas sementes."
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como viajam as sementes na natureza para conquistar novos terrenos?",
                        options = listOf("Pelo vento, água e agarradas ao pelo dos animais", "Apenas por comboio", "Ficam sempre no mesmo centímetro"),
                        correctAnswer = "Pelo vento, água e agarradas ao pelo dos animais",
                        explanation = "A dispersão das sementes garante a sobrevivência das espécies vegetais."
                    ),
                    WorksheetExercise(
                        id = "em_3_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o elemento vegetal à sua função na fotossíntese:",
                        pairs = mapOf(
                            "Luz Solar" to "Fonte de energia captada pela clorofila",
                            "Raízes" to "Absorção de água e sais minerais",
                            "Estomas das folhas" to "Trocas gasosas (absorve CO2 e liberta O2)",
                            "Flor" to "Órgão reprodutor que dará origem ao fruto"
                        ),
                        explanation = "Conhecimento botânico e ecológico de nível superior!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f6",
                numero = 6,
                title = "A Terra no Espaço: O Sistema Solar e a Lua",
                description = "Sol, os 8 planetas, movimentos de rotação e translação, fases da Lua e marés.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🪐",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o astro central e luminoso à volta do qual giram todos os planetas do Sistema Solar?",
                        options = listOf("O Sol (uma estrela)", "A Lua", "Marte"),
                        correctAnswer = "O Sol (uma estrela)",
                        explanation = "O Sol é a estrela no centro do nosso sistema planetário!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O movimento de ROTAÇÃO da Terra sobre o seu próprio eixo dura 24 horas e dá origem aos Dias e às Noites.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1 volta completa = 24 horas (1 dia)."
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O movimento de TRANSLAÇÃO da Terra à volta do Sol demora 365 dias e dá origem às 4 ___ do ano.",
                        options = listOf("estações", "semanas", "horas"),
                        correctAnswer = "estações",
                        explanation = "A inclinação do eixo e a translação geram Primavera, Verão, Outono e Inverno."
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o único satélite natural da Terra que brilha à noite ao refletir a luz solar?",
                        options = listOf("🌕 A Lua", "🪐 Saturno", "⭐ Estrela polar"),
                        correctAnswer = "🌕 A Lua",
                        explanation = "A Lua é o satélite natural que orbita a Terra! 🌕"
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas fases principais tem a Lua ao longo do seu ciclo de 28 dias?",
                        options = listOf("4 fases (Nova, Quarto Crescente, Cheia, Quarto Minguante)", "2 fases", "12 fases"),
                        correctAnswer = "4 fases (Nova, Quarto Crescente, Cheia, Quarto Minguante)",
                        explanation = "Lua Nova, Quarto Crescente, Lua Cheia e Quarto Minguante!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Terra é o 3.º planeta a contar a partir do Sol.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Mercúrio, Vénus, Terra, Marte, Júpiter, Saturno, Úrano, Neptuno."
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o maior planeta de todo o Sistema Solar?",
                        options = listOf("Júpiter", "Terra", "Mercúrio"),
                        correctAnswer = "Júpiter",
                        explanation = "Júpiter é o gigante gasoso do nosso sistema solar."
                    ),
                    WorksheetExercise(
                        id = "em_3_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os conceitos astronómicos:",
                        pairs = mapOf(
                            "Rotação da Terra" to "Origina o Dia e a Noite (24 horas)",
                            "Translação da Terra" to "Origina o Ano e as 4 Estações (365 dias)",
                            "Lua" to "Satélite natural da Terra",
                            "Sol" to "Estrela que emite luz e calor"
                        ),
                        explanation = "Excelente conhecimento sobre o Universo e o Sistema Solar!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f7",
                numero = 7,
                title = "O Relevo de Portugal: Serras, Planaltos e Planícies",
                description = "Serra da Estrela, montanhas do Norte/Centro, planaltos e as planícies do Sul.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⛰️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a serra com o ponto mais alto de Portugal Continental (Torre, com 1993 metros de altitude)?",
                        options = listOf("Serra da Estrela", "Serra do Gerês", "Serra de Sintra"),
                        correctAnswer = "Serra da Estrela",
                        explanation = "A Serra da Estrela acolhe a Torre e tem neve no inverno!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O relevo a Norte do rio Tejo é mais montanhoso e acidentado, enquanto a Sul predominam as grandes planícies.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Norte é montanhoso e o Sul (Alentejo) é plano."
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Uma extensão de terreno plano e pouco elevado chama-se uma ___ (típica do Alentejo).",
                        options = listOf("planície", "montanha", "ravina"),
                        correctAnswer = "planície",
                        explanation = "As planícies alentejanas são amplas e douradas com sobreiros."
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "O ponto mais alto de TODO o território português (2351 m) fica nos Açores e chama-se...",
                        options = listOf("🌋 Montanha do Pico", "⛰️ Serra da Estrela", "🏔️ Serra do Marão"),
                        correctAnswer = "🌋 Montanha do Pico",
                        explanation = "A Montanha do Pico na ilha do Pico (Açores) é o cume mais alto de Portugal! 🌋"
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama uma elevação de terreno plano no topo situada a grande altitude?",
                        options = listOf("Planalto", "Valadares", "Colina"),
                        correctAnswer = "Planalto",
                        explanation = "Exemplo: Planalto Mirandês em Trás-os-Montes."
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O vale é o terreno mais baixo situado entre duas montanhas, por onde costuma correr um rio.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os vales são férteis e abrigados dos ventos."
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o único Parque Nacional de Portugal, situado no Norte do país?",
                        options = listOf("Parque Nacional da Peneda-Gerês", "Parque da Ria Formosa", "Parque de Monsanto"),
                        correctAnswer = "Parque Nacional da Peneda-Gerês",
                        explanation = "O Gerês é o santuário natural protegido de Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as formas de relevo às suas características:",
                        pairs = mapOf(
                            "Montanha" to "Grande elevação de terreno com cume",
                            "Planície" to "Terreno plano e de baixa altitude",
                            "Planalto" to "Superfície plana a grande altitude",
                            "Vale" to "Zona baixa entre duas montanhas"
                        ),
                        explanation = "Perfeito domínio da geografia física de Portugal!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f8",
                numero = 8,
                title = "Rios e a Costa Marítima de Portugal",
                description = "Nascente, foz, afluentes, rios Tejo, Douro, Guadiana e Mondego, cabos e praias.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o rio mais extenso de Portugal Continental, que desagua no estuário junto a Lisboa?",
                        options = listOf("Rio Tejo", "Rio Douro", "Rio Guadiana"),
                        correctAnswer = "Rio Tejo",
                        explanation = "O Tejo nasce em Espanha e desagua no Mar da Palha em Lisboa!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O rio Mondego é o maior rio que nasce e corre inteiramente em território português.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Nasce na Serra da Estrela e desagua na Figueira da Foz."
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O local onde um rio desagua no mar ou num lago chama-se a ___ do rio.",
                        options = listOf("foz", "nascente", "margem"),
                        correctAnswer = "foz",
                        explanation = "Nascente = onde nasce; Foz = onde desagua no mar."
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual rio famoso do Norte de Portugal banha a cidade do Porto e as vinhas do Vinho do Porto?",
                        options = listOf("🍇 Rio Douro", "🏖️ Rio Sado", "🌾 Rio Guadiana"),
                        correctAnswer = "🍇 Rio Douro",
                        explanation = "O rio Douro esculpe as encostas dos socalcos vinhateiros! 🍇"
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama uma ponta de terra rochosa e alta que entra pelo mar adentro?",
                        options = listOf("Cabo (ex: Cabo da Roca, Cabo de S. Vicente)", "Praia", "Duna"),
                        correctAnswer = "Cabo (ex: Cabo da Roca, Cabo de S. Vicente)",
                        explanation = "O Cabo da Roca é o ponto mais ocidental do continente europeu!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um afluente é um rio mais pequeno que desagua num rio principal.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: o rio Zêzere é um grande afluente do Tejo."
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que oceano banha toda a costa continental e ilhas de Portugal?",
                        options = listOf("Oceano Atlântico", "Oceano Pacífico", "Oceano Índico"),
                        correctAnswer = "Oceano Atlântico",
                        explanation = "Portugal tem uma vasta costa voltada para o Oceano Atlântico!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o rio à respetiva cidade por onde passa:",
                        pairs = mapOf(
                            "Rio Douro" to "Porto",
                            "Rio Tejo" to "Lisboa / Santarém",
                            "Rio Mondego" to "Coimbra",
                            "Rio Guadiana" to "Mértola / Vila Real de S. António"
                        ),
                        explanation = "Excelente conhecimento hidrográfico e litoral de Portugal!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f9",
                numero = 9,
                title = "O Clima em Portugal e Fenómenos Meteorológicos",
                description = "Clima temperado mediterrânico e atlântico, temperaturas, pluviosidade e vento.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "☀️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que tipo geral de clima predomina em Portugal continental?",
                        options = listOf("Temperado Mediterrânico (com influência Atlântica)", "Polar Glacial", "Tropical Húmido"),
                        correctAnswer = "Temperado Mediterrânico (com influência Atlântica)",
                        explanation = "Invernos suaves e verões quentes e secos!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No Norte Litoral de Portugal chove mais do que no Sul (Algarve e Alentejo).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os ventos atlânticos trazem mais pluviosidade ao Noroeste peninsular."
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A quantidade de chuva que cai numa região durante o ano chama-se ___",
                        options = listOf("Pluviosidade / Precipitação", "Temperatura", "Trovoada"),
                        correctAnswer = "Pluviosidade / Precipitação",
                        explanation = "Mede-se em milímetros com o pluviómetro."
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual região portuguesa tem o clima mais quente e seco, com muitas horas de sol no Verão?",
                        options = listOf("🏖️ Algarve e Alentejo", "🌲 Minho", "🏔️ Trás-os-Montes"),
                        correctAnswer = "🏖️ Algarve e Alentejo",
                        explanation = "O Sul de Portugal beneficia de grande insolação e temperaturas quentes! 🏖️"
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a brisa húmida com nevoeiro frequente no arquipélago dos Açores?",
                        options = listOf("Clima Temperado Marítimo Oceânico", "Clima Desértico", "Clima Polar"),
                        correctAnswer = "Clima Temperado Marítimo Oceânico",
                        explanation = "Os Açores têm clima ameno, húmido e com vegetação luxuriante!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Madeira tem um clima subtropical ameno durante todo o ano, sendo chamada a ilha das flores.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Tem temperaturas agradáveis e amenas todo o ano."
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O nevoeiro matinal é provocado pela...",
                        options = listOf("Condensação do vapor de água junto ao solo frio", "Fumo de queimadas", "Poeira do vento"),
                        correctAnswer = "Condensação do vapor de água junto ao solo frio",
                        explanation = "Gotículas de água suspensas no ar próximo da superfície."
                    ),
                    WorksheetExercise(
                        id = "em_3_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a zona geográfica às suas características climáticas:",
                        pairs = mapOf(
                            "Norte e Minho" to "Mais chuvoso, fresco e verde",
                            "Interior Norte (Trás-os-Montes)" to "Invernos muito frios e verões muito quentes ('nove meses de inverno e três de inferno')",
                            "Alentejo e Algarve" to "Verões quentes, secos e ensolarados",
                            "Arquipélago da Madeira" to "Clima ameno e subtropical todo o ano"
                        ),
                        explanation = "Fantástica compreensão dos microclimas portugueses!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f10",
                numero = 10,
                title = "Recursos Naturais e Energias Renováveis",
                description = "Energia solar, eólica, hídrica, biomassa vs combustíveis fósseis poluentes.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "☀️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam as fontes de energia que não se esgotam na natureza e não poluem o ambiente?",
                        options = listOf("Energias Renováveis / Limpas", "Combustíveis fósseis", "Energias esgotáveis"),
                        correctAnswer = "Energias Renováveis / Limpas",
                        explanation = "Sol, vento, ondas e água dos rios são fontes renováveis inesgotáveis!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os painéis solares fotovoltaicos transformam a luz do Sol diretamente em eletricidade limpa.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Portugal é um dos países europeus com mais horas de sol para aproveitar."
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A energia produzida pela força da água acumulada nas barragens chama-se energia ___",
                        options = listOf("Hídrica / Hidroelétrica", "Eólica", "Nuclear"),
                        correctAnswer = "Hídrica / Hidroelétrica",
                        explanation = "A água faz girar as turbinas das centrais hidroelétricas."
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas é uma energia NÃO RENOVÁVEL que polui com fumo e gases de efeito de estufa?",
                        options = listOf("🛢️ Petróleo e Carvão", "☀️ Energia Solar", "🌬️ Energia Eólica"),
                        correctAnswer = "🛢️ Petróleo e Carvão",
                        explanation = "Os combustíveis fósseis esgotam-se e emitem gases com efeito de estufa! 🛢️"
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A energia Eólica aproveita a força de qual elemento natural?",
                        options = listOf("Do Vento", "Do Fogo", "Da Terra"),
                        correctAnswer = "Do Vento",
                        explanation = "As pás dos aerogeradores giram com o vento produzindo eletricidade."
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Poupar energia elétrica em casa ajuda a reduzir a pegada ecológica do planeta.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Desligar luzes e aparelhos em standby protege o meio ambiente."
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é a Biomassa?",
                        options = listOf("Energia obtida a partir de resíduos de madeira, florestas e agricultura", "Energia do vidro", "Energia do plástico"),
                        correctAnswer = "Energia obtida a partir de resíduos de madeira, florestas e agricultura",
                        explanation = "Aproveita sobras de limpezas florestais prevenindo incêndios."
                    ),
                    WorksheetExercise(
                        id = "em_3_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a fonte renovável ao seu recurso natural:",
                        pairs = mapOf(
                            "Energia Solar" to "Luz e calor do Sol",
                            "Energia Eólica" to "Força dos ventos",
                            "Energia Hídrica" to "Corrente e quedas de água nas barragens",
                            "Energia das Ondas / Marés" to "Movimento dos oceanos"
                        ),
                        explanation = "Excelente consciência ecológica e energética!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f11",
                numero = 11,
                title = "Património e Símbolos Nacionais de Portugal",
                description = "A Bandeira Nacional (verde e vermelha), o Hino 'A Portuguesa', a Esfera Armilar e os Castelos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🇵🇹",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quais são as duas cores oficiais da Bandeira Nacional de Portugal?",
                        options = listOf("Verde e Vermelho", "Azul e Branco", "Amarelo e Verde"),
                        correctAnswer = "Verde e Vermelho",
                        explanation = "O Verde simboliza a Esperança e o Vermelho a Coragem e bravura!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Hino Nacional de Portugal intitula-se 'A Portuguesa' e começa por 'Heróis do mar, nobre povo...'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Foi composto por Alfredo Keil com letra de Henrique Lopes de Mendonça."
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O instrumento astronómico dourado no centro da bandeira portuguesa, usado pelos navegadores, chama-se ___",
                        options = listOf("Esfera Armilar", "Telescópio", "Bússola"),
                        correctAnswer = "Esfera Armilar",
                        explanation = "A Esfera Armilar evoca a glória dos Descobrimentos Marítimos!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quantos Castelos amarelos estão representados na borda vermelha do Escudo de Portugal?",
                        options = listOf("🏰 7 Castelos", "🏰 5 Castelos", "🏰 12 Castelos"),
                        correctAnswer = "🏰 7 Castelos",
                        explanation = "O escudo tem 7 castelos e 5 quinas azuis! 🏰"
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que representam as 5 Quinas azuis no escudo de armas de Portugal?",
                        options = listOf("Os 5 reis mouros vencidos por D. Afonso Henriques na Batalha de Ourique", "As 5 maiores cidades", "Os 5 rios"),
                        correctAnswer = "Os 5 reis mouros vencidos por D. Afonso Henriques na Batalha de Ourique",
                        explanation = "As 5 quinas com besantes brancos são a marca heráldica mais antiga de Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Galo de Barcelos é um dos símbolos do artesanato e tradição lendária portuguesa mais famosos no mundo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O colorido galo de Barcelos representa a justiça e a sorte."
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Fado tradicional português, Património da Humanidade, é cantado ao som da...",
                        options = listOf("Guitarra Portuguesa e viola de fado", "Bateria eletrónica", "Flauta de bisel"),
                        correctAnswer = "Guitarra Portuguesa e viola de fado",
                        explanation = "A guitarra portuguesa com 12 cordas tem um timbre inconfundível!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os elementos dos símbolos nacionais:",
                        pairs = mapOf(
                            "Cor Verde" to "A Esperança no futuro",
                            "Cor Vermelha" to "A Coragem e o sangue dos heróis",
                            "Esfera Armilar" to "O mundo navegado nos Descobrimentos",
                            "7 Castelos" to "As fortalezas e praças fortificadas"
                        ),
                        explanation = "Orgulho e respeito pelos símbolos da nossa Pátria!"
                    )
                )
            ),
            Worksheet(
                id = "em_3_f12",
                numero = 12,
                title = "Direitos da Criança, Cidadania e Sociedade",
                description = "A Declaração dos Direitos da Criança, igualdade, inclusão, deveres e voluntariado.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🤝",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_3_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Segundo a Convenção dos Direitos da Criança, todas as crianças têm direito a...",
                        options = listOf("Educação, saúde, proteção, família e brincar", "Trabalhar em fábricas", "Comer apenas doces"),
                        correctAnswer = "Educação, saúde, proteção, família e brincar",
                        explanation = "Todas as crianças do mundo merecem crescer seguras, amadas e com escola!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Crianças de diferentes países, etnias ou com deficiências têm exatamente os mesmos direitos fundamentais.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O princípio da não discriminação e igualdade é universal."
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A par de termos direitos, também temos o dever cívico de respeitar os outros, os professores e o ___ escolar.",
                        options = listOf("material / património", "segredo", "telemóvel"),
                        correctAnswer = "material / património",
                        explanation = "Direitos e deveres caminham juntos para uma sociedade harmoniosa."
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas atitudes demonstra espírito de solidariedade e voluntariado?",
                        options = listOf("🤝 Ajudar um colega novo a integrar-se na escola", "😤 Rir de quem tem dificuldades", "📱 Ignorar toda a gente"),
                        correctAnswer = "🤝 Ajudar um colega novo a integrar-se na escola",
                        explanation = "A bondade, o respeito e a entreajuda tornam a nossa escola melhor! 🤝"
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como devemos resolver conflitos e desentendimentos no recreio?",
                        options = listOf("Pelo diálogo calmo e respeito mútuo", "Com gritos e empurrões", "Fugindo da escola"),
                        correctAnswer = "Pelo diálogo calmo e respeito mútuo",
                        explanation = "Conversar e saber ouvir resolve qualquer divergência em paz."
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Brincar e descansar é um direito oficial reconhecido a todas as crianças pela ONU.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O artigo 31.º consagra o direito ao lazer e ao jogo."
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A organização internacional da ONU dedicada exclusivamente a proteger as crianças chama-se...",
                        options = listOf("UNICEF", "NASA", "FIFA"),
                        correctAnswer = "UNICEF",
                        explanation = "A UNICEF defende os direitos das crianças em todo o mundo!"
                    ),
                    WorksheetExercise(
                        id = "em_3_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o Direito ao seu respetivo Dever:",
                        pairs = mapOf(
                            "Direito a estudar" to "Dever de fazer os trabalhos e ter atenção nas aulas",
                            "Direito a ter amigos" to "Dever de respeitar e não praticar bullying",
                            "Direito a viver num ambiente limpo" to "Dever de não deitar lixo para o chão",
                            "Direito a ser ouvido" to "Dever de escutar a opinião dos outros com respeito"
                        ),
                        explanation = "Parabéns, Campeão Cidadão do Estudo do Meio do 3.º Ano!"
                    )
                )
            )
        )
    )

    val year = WorksheetYear(
        id = 3,
        title = "3.º Ano",
        disciplines = listOf(portugues, matematica, estudoMeio)
    )
}
