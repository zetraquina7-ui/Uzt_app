package com.example.ui.screens

import androidx.compose.ui.graphics.Color

object WorksheetsYear4 {
    val portugues = WorksheetDiscipline(
        id = "pt_4",
        title = "Português",
        emoji = "🇵🇹",
        color = Color(0xFFEC4899),
        worksheets = listOf(
            Worksheet(
                id = "pt_4_f1",
                numero = 1,
                title = "Funções Sintáticas: Sujeito, Predicado e Vocativo",
                description = "Sujeito Simples e Composto, Predicado verbal e o Vocativo no chamamento.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🗣️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Ó Zé, vem depressa ver isto!', qual é a função sintática da expressão 'Ó Zé'?",
                        options = listOf("Vocativo", "Sujeito", "Predicado"),
                        correctAnswer = "Vocativo",
                        explanation = "O Vocativo serve para chamar, interpelar ou invocar alguém e isola-se por vírgulas!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'O cão e o gato dormem no tapete', o sujeito é Composto porque tem dois núcleos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'O cão e o gato' é um Sujeito Composto."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'Os atletas portugueses venceram a medalha de ouro', o predicado é ___",
                        options = listOf("venceram a medalha de ouro", "Os atletas portugueses", "de ouro"),
                        correctAnswer = "venceram a medalha de ouro",
                        explanation = "O Predicado inclui o verbo principal e todos os seus complementos!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases contém um Vocativo corretamente pontuado com vírgula?",
                        options = listOf("👋 'Meninos, abram o livro na página 20.'", "👋 'Meninos abram o livro'", "👋 'Os meninos abriram o livro.'"),
                        correctAnswer = "👋 'Meninos, abram o livro na página 20.'",
                        explanation = "O vocativo 'Meninos,' separa-se por vírgula da restante frase! 👋"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o Sujeito na frase 'Ontem à tarde, a Maria comprou um estojo novo'?",
                        options = listOf("a Maria", "Ontem à tarde", "um estojo novo"),
                        correctAnswer = "a Maria",
                        explanation = "Quem comprou o estojo foi 'a Maria' (Sujeito Simples)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'Nevou na serra durante toda a noite', o sujeito é nulo impessoal.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O verbo nevar é um verbo impessoal que descreve um fenómeno da natureza."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se classifica o sujeito em 'Comemos um bolo delicioso' (nós subentendido)?",
                        options = listOf("Sujeito Nulo Subentendido", "Sujeito Composto", "Sujeito Inexistente"),
                        correctAnswer = "Sujeito Nulo Subentendido",
                        explanation = "A terminação verbal '-emos' identifica claramente a pessoa 'Nós'."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a função sintática ao segmento destacado:",
                        pairs = mapOf(
                            "'Senhora Professora,' posso entrar?" to "Vocativo",
                            "'A lua cheia' ilumina a floresta" to "Sujeito Simples",
                            "'O sol e a chuva' criam o arco-íris" to "Sujeito Composto",
                            "O barco 'atracou no cais à hora marcada'" to "Predicado"
                        ),
                        explanation = "Excelente análise sintática!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f2",
                numero = 2,
                title = "Complemento Direto e Indireto",
                description = "Perguntas 'O quê?' (Complemento Direto) e 'A quem?' (Complemento Indireto).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🎯",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'O Zé ofereceu um postal à mãe', qual é o COMPLEMENTO DIRETO (responde a 'O quê?')?",
                        options = listOf("um postal", "à mãe", "O Zé"),
                        correctAnswer = "um postal",
                        explanation = "O Zé ofereceu o quê? 'Um postal' (Complemento Direto)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase anterior, 'à mãe' é o Complemento Indireto porque responde à pergunta 'A quem?'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Complemento Indireto é introduzido pela preposição 'a' e indica o destinatário."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'O escritor escreveu um romance histórico', 'um romance histórico' é o Complemento ___",
                        options = listOf("Direto", "Indireto", "Oblíquo"),
                        correctAnswer = "Direto",
                        explanation = "Responde à pergunta: 'O escritor escreveu o quê?' -> Complemento Direto."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual pronome substitui o Complemento Direto em: 'Eu li o livro' -> 'Eu li-___'?",
                        options = listOf("📖 o", "📖 lhe", "📖 me"),
                        correctAnswer = "📖 o",
                        explanation = "O Complemento Direto de 3.ª pessoa é substituído por 'o, a, os, as' (Eu li-o)! 📖"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual pronome substitui o Complemento Indireto em: 'Eu telefonei à Rita' -> 'Eu telefonei-___'?",
                        options = listOf("lhe", "a", "la"),
                        correctAnswer = "lhe",
                        explanation = "O Complemento Indireto é substituído pelo pronome pessoal 'lhe / lhes' (telefonei-lhe)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Complemento Direto pode ser substituído pelo pronome 'lhe'.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Lhe' substitui apenas o Complemento Indireto. O Complemento Direto usa 'o, a, os, as'."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Identifica o Complemento Direto em 'O padeiro amassou a farinha com mestria':",
                        options = listOf("a farinha", "com mestria", "O padeiro"),
                        correctAnswer = "a farinha",
                        explanation = "Amassou o quê? 'A farinha'!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os complementos destacados:",
                        pairs = mapOf(
                            "O Pedro enviou 'uma encomenda'" to "Complemento Direto (O quê?)",
                            "O Pedro enviou a encomenda 'ao avô'" to "Complemento Indireto (A quem?)",
                            "A Rita aplaudiu 'o pianista'" to "Complemento Direto (Quem?)",
                            "O médico explicou o tratamento 'ao doente'" to "Complemento Indireto (A quem?)"
                        ),
                        explanation = "Perfeita identificação de complementos diretos e indiretos!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f3",
                numero = 3,
                title = "Graus dos Adjetivos e dos Nomes",
                description = "Grau Comparativo (superioridade, igualdade, inferioridade) e Superlativo Absoluto/Relativo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌟",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em 'O leopardo é mais veloz que a lebre', qual é o grau do adjetivo?",
                        options = listOf("Comparativo de Superioridade", "Comparativo de Igualdade", "Superlativo Absoluto"),
                        correctAnswer = "Comparativo de Superioridade",
                        explanation = "Usa 'mais... do que / que', logo é Comparativo de Superioridade!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A frase 'O teste de matemática foi facílimo' está no Grau Superlativo Absoluto Sintético.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A terminação '-íssimo' ou '-ílimo' indica Superlativo Absoluto Sintético."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A frase 'Este livro é tão interessante como aquele' está no Grau Comparativo de ___",
                        options = listOf("Igualdade", "Superioridade", "Inferioridade"),
                        correctAnswer = "Igualdade",
                        explanation = "'Tão... como' exprime Comparativo de Igualdade!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases expressa o Grau Superlativo Absoluto Analítico?",
                        options = listOf("✨ 'A sobremesa estava muito deliciosa.'", "✨ 'A sobremesa é mais doce que o café.'", "✨ 'A sobremesa é doce.'"),
                        correctAnswer = "✨ 'A sobremesa estava muito deliciosa.'",
                        explanation = "O uso do advérbio 'muito / extremamente' forma o Superlativo Absoluto Analítico! ✨"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o Superlativo Absoluto Sintético irregular do adjetivo 'bom'?",
                        options = listOf("Ótimo / Boníssimo", "Mais bom", "Melhorzinho"),
                        correctAnswer = "Ótimo / Boníssimo",
                        explanation = "Bom -> Ótimo / Boníssimo; Mau -> Péssimo; Grande -> Máximo; Pequeno -> Mínimo!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Em 'A Joana é a aluna mais estudiosa da turma', o adjetivo está no Grau Superlativo Relativo de Superioridade.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Destaca a Joana relativamente a todo o grupo da turma."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o diminutivo erudito da palavra 'rio'?",
                        options = listOf("Ribeiro / Riacho", "Riosote", "Riozão"),
                        correctAnswer = "Ribeiro / Riacho",
                        explanation = "Ribeiro e riacho são diminutivos da palavra rio."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os graus dos adjetivos:",
                        pairs = mapOf(
                            "mais alto do que" to "Comparativo de Superioridade",
                            "tão inteligente como" to "Comparativo de Igualdade",
                            "menos rápido do que" to "Comparativo de Inferioridade",
                            "rapidíssimo" to "Superlativo Absoluto Sintético"
                        ),
                        explanation = "Excelente domínio de todos os graus dos adjetivos!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f4",
                numero = 4,
                title = "Conjugação Verbal Completa: Modo Indicativo",
                description = "Presente, Pretérito Perfeito, Pretérito Imperfeito, Mais-que-Perfeito e Futuro.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📖",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Quando cheguei a casa, o meu pai já cozinhara o jantar', em que tempo verbal está 'cozinhara'?",
                        options = listOf("Pretérito Mais-que-Perfeito Simples", "Pretérito Perfeito", "Futuro"),
                        correctAnswer = "Pretérito Mais-que-Perfeito Simples",
                        explanation = "O Pretérito Mais-que-Perfeito (cozinhara / tinha cozinhado) indica uma ação passada anterior a outra também passada!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Modo Indicativo é o modo verbal que expressa factos certos, reais e seguros.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ao contrário do Condicional ou Conjuntivo, o Indicativo expressa certezas."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Conjuga o verbo 'fazer' no Futuro do Indicativo para 'Eles': 'Amanhã eles ___ a apresentação.'",
                        options = listOf("farão", "fizeram", "faziam"),
                        correctAnswer = "farão",
                        explanation = "Futuro de 3.ª pessoa do plural termina em '-ão' (farão)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual forma verbal está no Pretérito Imperfeito do Indicativo?",
                        options = listOf("⏳ 'Eu sonhava todas as noites.'", "⏳ 'Eu sonhei ontem.'", "⏳ 'Eu sonharei amanhã.'"),
                        correctAnswer = "⏳ 'Eu sonhava todas as noites.'",
                        explanation = "'Sonhava' termina em '-ava', marca típica do Pretérito Imperfeito da 1.ª conjugação! ⏳"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a 2.ª pessoa do plural do verbo 'partir' no Presente do Indicativo?",
                        options = listOf("vós partis", "vós partistes", "vós partireis"),
                        correctAnswer = "vós partis",
                        explanation = "Presente: eu parto, tu partes, ele parte, nós partimos, vós partis, eles partem."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os verbos 'ser', 'ir' e 'haver' são verbos irregulares porque alteram o seu radical na conjugação.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! (ex: eu sou, eu fui, eu era mudam de radical)."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que indica a forma nominal de INFINITIVO de um verbo?",
                        options = listOf("O nome base do verbo terminado em -ar, -er, -ir", "Uma ação no passado", "Uma ordem"),
                        correctAnswer = "O nome base do verbo terminado em -ar, -er, -ir",
                        explanation = "Ex: cantar, correr, sorrir são infinitivos."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a forma verbal ao seu tempo do Modo Indicativo:",
                        pairs = mapOf(
                            "nós estudamos (agora)" to "Presente",
                            "nós estudámos (ontem)" to "Pretérito Perfeito",
                            "nós estudávamos (antigamente)" to "Pretérito Imperfeito",
                            "nós estudaremos (amanhã)" to "Futuro"
                        ),
                        explanation = "Conjugação verbal rigorosa e sem falhas!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f5",
                numero = 5,
                title = "Advérbios e Locuções Adverbiais",
                description = "Advérbios de tempo, lugar, modo, negação, afirmação e quantidade.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧭",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Ele respondeu calmamente às perguntas do professor', 'calmamente' é um advérbio de...",
                        options = listOf("Modo", "Lugar", "Tempo"),
                        correctAnswer = "Modo",
                        explanation = "Indica o modo como a ação foi realizada (como respondeu? calmamente)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As palavras 'aqui', 'ali', 'longe' e 'dentro' são advérbios de LUGAR.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Respondem à pergunta 'Onde?'."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'Ontem acordei muito cedo', 'ontem' e 'cedo' são advérbios de ___",
                        options = listOf("Tempo", "Modo", "Afirmação"),
                        correctAnswer = "Tempo",
                        explanation = "Situam a ação no tempo."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é um advérbio de QUANTIDADE / INTENSIDADE?",
                        options = listOf("📊 'bastante'", "📊 'talvez'", "📊 'nunca'"),
                        correctAnswer = "📊 'bastante'",
                        explanation = "'Bastante', 'muito', 'pouco' indicam quantidade e intensidade! 📊"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Sim, com certeza irei à festa', que tipo de advérbio é 'Sim'?",
                        options = listOf("Advérbio de Afirmação", "Advérbio de Negação", "Advérbio de Dúvida"),
                        correctAnswer = "Advérbio de Afirmação",
                        explanation = "'Sim', 'certamente', 'efetivamente' são advérbios afirmativos."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os advérbios são palavras invariáveis (não mudam para o plural nem feminino).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os advérbios mantêm sempre a mesma forma invariável."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a locução adverbial que significa 'em silêncio'?",
                        options = listOf("às escondidas / em silêncio", "à pressa", "com certeza"),
                        correctAnswer = "às escondidas / em silêncio",
                        explanation = "Locuções adverbiais são grupos de palavras com valor de advérbio."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os advérbios na sua subclasse:",
                        pairs = mapOf(
                            "ontem / amanhã" to "Advérbio de Tempo",
                            "perto / além" to "Advérbio de Lugar",
                            "depressa / devagar" to "Advérbio de Modo",
                            "não / jamais" to "Advérbio de Negação"
                        ),
                        explanation = "Perfeita identificação e classificação dos advérbios!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f6",
                numero = 6,
                title = "Discurso Direto e Discurso Indireto",
                description = "Transformação de diálogos diretos (travessão, dois pontos) em relato indireto.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💬",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se transcrevem com rigor as palavras exatas pronunciadas por uma personagem?",
                        options = listOf("Em Discurso Direto (com travessão ou aspas)", "Em Discurso Indireto", "Em Resumo"),
                        correctAnswer = "Em Discurso Direto (com travessão ou aspas)",
                        explanation = "O discurso direto reproduz fielmente as palavras da fala!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No Discurso Indireto o narrador conta o que as personagens disseram usando 'que' ou 'se' (ex: O Zé disse que ia estudar).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Não há travessões nem aspas de diálogo."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Transforma para indireto: O Pedro afirmou: '— Eu tenho fome.' -> O Pedro afirmou que ___ fome.",
                        options = listOf("tinha", "tenho", "terei"),
                        correctAnswer = "tinha",
                        explanation = "No relato indireto o presente ('tenho') passa a pretérito imperfeito ('tinha')."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual pontuação é indispensável antes de abrir uma fala em Discurso Direto?",
                        options = listOf("💬 Dois pontos (:) e mudança de linha com travessão (—)", "💬 Ponto de exclamação no meio da frase", "💬 Ponto e vírgula sem travessão"),
                        correctAnswer = "💬 Dois pontos (:) e mudança de linha com travessão (—)",
                        explanation = "A regra clássica do diálogo: verbo introdutor : mudança de parágrafo — fala! 💬"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Verbos como 'perguntar', 'responder', 'exclamar', 'afirmar' chamam-se verbos...",
                        options = listOf("Introdutores de discurso / Verbos declarativos", "Auxiliares", "Imcompatíveis"),
                        correctAnswer = "Introdutores de discurso / Verbos declarativos",
                        explanation = "Introduzem a fala das personagens no texto narrativo."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na passagem para discurso indireto, pronomes da 1.ª pessoa (eu/meu) mudam para a 3.ª pessoa (ele/seu).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 'O meu cão' -> 'o seu cão'."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Passa para indireto: O professor perguntou: '— Quem fez o trabalho?'",
                        options = listOf("O professor perguntou quem tinha feito o trabalho.", "O professor gritou que sim.", "O professor fez o trabalho."),
                        correctAnswer = "O professor perguntou quem tinha feito o trabalho.",
                        explanation = "Mantém o sentido interrogativo na forma relatada."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Distingue o tipo de discurso:",
                        pairs = mapOf(
                            "O João disse: — Eu adoro ler livros!" to "Discurso Direto",
                            "O João disse que adorava ler livros." to "Discurso Indireto",
                            "A Rita perguntou: — Vens comigo?" to "Discurso Direto",
                            "A Rita perguntou se eu ia com ela." to "Discurso Indireto"
                        ),
                        explanation = "Excelente mestria na transposição do discurso direto e indireto!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f7",
                numero = 7,
                title = "Hiperónimos, Hipónimos e Campos Semânticos",
                description = "Termos gerais (hiperónimos) vs termos específicos (hipónimos) e redes de palavras.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🗂️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A palavra 'Fruta' é um termo geral em relação a 'Maçã, Pera, Laranja'. Que tipo de palavra é 'Fruta'?",
                        options = listOf("Hiperónimo", "Hipónimo", "Sinónimo"),
                        correctAnswer = "Hiperónimo",
                        explanation = "Hiperónimo é o termo mais abrangente que engloba outros termos!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As palavras 'rosa', 'cravo', 'tulipa' e 'orquídea' são HIPÓNIMOS do hiperónimo 'flor'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Hipónimo é a palavra específica de um grupo maior."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O conjunto de todas as palavras associadas ao universo de 'Escola' (livro, professor, recreio, quadro, aluno) forma um Campo ___",
                        options = listOf("Lexical", "Invisível", "Geométrico"),
                        correctAnswer = "Lexical",
                        explanation = "O campo lexical reúne palavras relacionadas com o mesmo tema ou área da vida."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é um HIPÓNIMO de 'Meio de Transporte'?",
                        options = listOf("🚗 Automóvel", "🍎 Maçã", "👕 Camisola"),
                        correctAnswer = "🚗 Automóvel",
                        explanation = "'Automóvel' é uma espécie específica de meio de transporte! 🚗"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o Hiperónimo comum a 'Cão, Gato, Elefante, Golfinho'?",
                        options = listOf("Animais / Mamíferos", "Aves", "Plantas"),
                        correctAnswer = "Animais / Mamíferos",
                        explanation = "É a classe geral que engloba todos estes seres."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Campo Semântico refere-se aos vários significados que uma mesma palavra pode assumir em contextos diferentes.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 'estrela' de cinema vs 'estrela' no céu."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras NÃO pertence ao campo lexical de 'Música'?",
                        options = listOf("Trator", "Guitarra", "Pauta musical"),
                        correctAnswer = "Trator",
                        explanation = "Trator pertence à agricultura, não à música."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o Hiperónimo aos seus respetivos Hipónimos:",
                        pairs = mapOf(
                            "Instrumentos Musicais" to "Piano, Flauta, Violino, Bateria",
                            "Cores" to "Azul, Amarelo, Carmesim, Verde",
                            "Sentimentos" to "Alegria, Saudade, Esperança, Amor",
                            "Profissões" to "Médico, Professor, Engenheiro, Pintor"
                        ),
                        explanation = "Muito bem! Compreendes a hierarquia e organização das palavras."
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f8",
                numero = 8,
                title = "Recursos Expressivos Literários Avançados",
                description = "Metáfora, Comparação, Personificação, Anáfora e Aliteração.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✨",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'Aquela criança é um raio de sol na nossa vida', que recurso expressivo temos (comparação direta sem palavra de ligação)?",
                        options = listOf("Metáfora", "Onomatopeia", "Rima"),
                        correctAnswer = "Metáfora",
                        explanation = "A metáfora transfere o significado de uma palavra para outra sem usar 'como' nem 'parece'!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A ANÁFORA é a repetição intencional da mesma palavra ou expressão no início de vários versos ou frases.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 'Canta o vento, canta o mar, canta o meu coração.'"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A repetição expressiva do mesmo som consonântico ('O rato roeu a rolha...') chama-se ___",
                        options = listOf("Aliteração", "Metáfora", "Hipónimo"),
                        correctAnswer = "Aliteração",
                        explanation = "A aliteração explora a musicalidade dos sons das consoantes!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes versos poéticos contém uma Metáfora?",
                        options = listOf("💎 'O mar é uma esmeralda líquida gigante.'", "🌊 'O mar parece uma piscina.'", "🏖️ 'Fui ao mar tomar banho.'"),
                        correctAnswer = "💎 'O mar é uma esmeralda líquida gigante.'",
                        explanation = "Identifica o mar diretamente como uma esmeralda líquida! 💎"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a diferença essencial entre Comparação e Metáfora?",
                        options = listOf("A Comparação usa elementos de ligação ('como', 'parece'), a Metáfora faz a identificação direta", "São rigorosamente iguais", "A metáfora só existe em prosa"),
                        correctAnswer = "A Comparação usa elementos de ligação ('como', 'parece'), a Metáfora faz a identificação direta",
                        explanation = "A metáfora é uma comparação condensada e poética sem o 'como'."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A frase 'O tempo voa quando nos divertimos' é uma expressão metafórica.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O tempo não tem asas, mas passa rapidamente como se voasse."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'O sol abraçou a cidade com calor', o recurso expressivo é...",
                        options = listOf("Personificação", "Aliteração", "Onomatopeia"),
                        correctAnswer = "Personificação",
                        explanation = "O Sol realiza o ato humano de abraçar."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os recursos literários:",
                        pairs = mapOf(
                            "Os seus dentes são pérolas brilhantes" to "Metáfora",
                            "Chora a noite, chora o vento, chora a terra" to "Anáfora (repetição inicial)",
                            "O vento sussurrava segredos aos pinheiros" to "Personificação",
                            "Forte como um touro selvagem" to "Comparação com 'como'"
                        ),
                        explanation = "Sensibilidade e mestria literária excecionais!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f9",
                numero = 9,
                title = "Ortografia Rigorosa e Acentuação Gráfica",
                description = "Regras do Novo Acordo Ortográfico, hífen, acento circunflexo, agudo e crase (à/há).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🎯",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras perdeu o acento gráfico nos ditongos 'ei' e 'oi' em palavras graves segundo o Acordo Ortográfico?",
                        options = listOf("ideia (antigamente ideia com acento)", "café", "baú"),
                        correctAnswer = "ideia (antigamente ideia com acento)",
                        explanation = "Palavras como ideia, colmeia, jiboia, heroico deixaram de levar acento nas graves!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'herói' continua a levar acento agudo porque é uma palavra AGUDA.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Nas agudas os ditongos abertos continuam acentuados (herói, troféu)."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Vou ___ praia passar a tarde de sol (crase da preposição a + artigo a).",
                        options = listOf("à", "há", "a"),
                        correctAnswer = "à",
                        explanation = "A preposição 'a' + artigo 'a' funde-se na contração 'à' com acento grave!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual palavra está escrita de forma 100% CORRETA?",
                        options = listOf("✅ 'Exceção'", "❌ 'Excessão'", "❌ 'Eceção'"),
                        correctAnswer = "✅ 'Exceção'",
                        explanation = "'Exceção' escreve-se com 'xc' e 'ç'! ✅"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se escreve o verbo 'trazer' no Pretérito Perfeito para 'Ele': 'Ontem ele ___ o livro'?",
                        options = listOf("trouxe", "trouce", "trouche"),
                        correctAnswer = "trouxe",
                        explanation = "Escreve-se 'trouxe' com 'x' que soa como 'ss'!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O acento circunflexo (^) indica que a vogal tem um som fechado ou nasal (ex: avô, lâmpada).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ao contrário do acento agudo (´) que indica som aberto (avó)."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras com hífen está escrita de forma correta?",
                        options = listOf("guarda-chuva", "guardachuva", "guarda chuva"),
                        correctAnswer = "guarda-chuva",
                        explanation = "'Guarda-chuva' tem hífen obrigatório."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Distingue o uso das palavras homófonas:",
                        pairs = mapOf(
                            "Ele foi ___ pé (preposição)" to "a",
                            "Moro aqui ___ cinco anos (tempo passado)" to "há",
                            "Fui ___ biblioteca estudar (contração a+a)" to "à",
                            "Ah! Que maravilha! (interjeição)" to "Ah!"
                        ),
                        explanation = "Rigor ortográfico impecável!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f10",
                numero = 10,
                title = "Tipologia e Géneros Textuais: Texto Dramático e Narrativo",
                description = "Estrutura do conto tradicional, fábula (moral), lenda e o texto de teatro (didascálias).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🎭",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam as instruções em itálico ou entre parênteses num texto de teatro que indicam os movimentos e emoções dos atores?",
                        options = listOf("Didascálias / Indicações cénicas", "Rimas", "Estrofes"),
                        correctAnswer = "Didascálias / Indicações cénicas",
                        explanation = "As didascálias orientam o encenador e os atores sobre cenário, gestos e voz!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma FÁBULA é uma narrativa curta protagonizada por animais que transmitem uma lição de moral final.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 'A Cigarra e a Formiga', 'A Lebre e a Tartaruga' de La Fontaine/Esopo."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A LENDA é uma narrativa tradicional que mistura factos históricos reais com elementos ___ e mágicos.",
                        options = listOf("fantásticos / lendários", "científicos", "matemáticos"),
                        correctAnswer = "fantásticos / lendários",
                        explanation = "Ex: A Lenda do Milagre das Rosas da Rainha Santa Isabel."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas obras é um exemplo de conto tradicional infantil?",
                        options = listOf("🧚 'A Carochinha e o João Ratão'", "📰 'Jornal de Notícias'", "📕 'Dicionário de Português'"),
                        correctAnswer = "🧚 'A Carochinha e o João Ratão'",
                        explanation = "É um dos mais famosos contos do património oral português! 🧚"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Numa narrativa, quem conta a história em 1.ª ou 3.ª pessoa chama-se...",
                        options = listOf("Narrador (participante ou não participante)", "Autor", "Editor"),
                        correctAnswer = "Narrador (participante ou não participante)",
                        explanation = "O narrador é a voz criada para conduzir o relato."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O clímax é o momento de maior suspense e tensão dramática de uma história.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É o ponto alto que antecede o desfecho final."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O texto poético distingue-se por ser organizado em...",
                        options = listOf("Estrofes e versos com ritmo e musicalidade", "Parágrafos longos de jornal", "Tabelas de números"),
                        correctAnswer = "Estrofes e versos com ritmo e musicalidade",
                        explanation = "A poesia explora o ritmo, métrica e emoção lírica."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o género textual às suas características definidoras:",
                        pairs = mapOf(
                            "Fábula" to "Animais personificados com ensinamento moral",
                            "Texto Dramático" to "Falas das personagens e didascálias para representação",
                            "Lenda" to "Tradição popular com magia e explicação de origens",
                            "Texto Informativo" to "Exposição clara e objetiva de factos reais"
                        ),
                        explanation = "Excelente domínio da teoria dos géneros literários!"
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f11",
                numero = 11,
                title = "Produção Escrita: Coesão, Coerência e Conectores",
                description = "Uso de conectores discursivos (portanto, além disso, no entanto) e paragrafação.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✍️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destes conectores usamos para exprimir uma CONCLUSÃO num texto?",
                        options = listOf("Portanto / Em conclusão", "Em primeiro lugar", "Porém"),
                        correctAnswer = "Portanto / Em conclusão",
                        explanation = "'Portanto', 'logo', 'assim' e 'em conclusão' fecham um raciocínio com lógica!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O conector 'No entanto' serve para introduzir uma ideia de oposição ou contraste (como 'porém' ou 'contudo').",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Exprime contraste entre duas ideias."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Para acrescentar informação num parágrafo podemos usar o conector: '___ disso, as crianças aprenderam muito.'",
                        options = listOf("Além", "Em vez", "Apesar"),
                        correctAnswer = "Além",
                        explanation = "'Além disso' é um conector aditivo enriquecedor."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Como deve começar cada novo parágrafo no texto escrito?",
                        options = listOf("📝 Com avanço de parágrafo e letra maiúscula", "📝 Com ponto de interrogação no início", "📝 No meio da folha sem ordem"),
                        correctAnswer = "📝 Com avanço de parágrafo e letra maiúscula",
                        explanation = "O recuo de parágrafo e maiúscula organizam visualmente o raciocínio! 📝"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Para ordenar acontecimentos no tempo usamos:",
                        options = listOf("Primeiro, depois, em seguida, finalmente", "Nunca, jamais, nada", "Sim, com certeza, talvez"),
                        correctAnswer = "Primeiro, depois, em seguida, finalmente",
                        explanation = "São marcadores temporais cronológicos essenciais à narrativa."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A repetição contínua da mesma palavra empobrece o texto e pode ser evitada com o uso de pronomes e sinónimos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Substituir nomes por pronomes e sinónimos melhora a coesão textual."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o conector que expressa causa e explicação?",
                        options = listOf("Visto que / Porque / Dado que", "Embora", "Caso"),
                        correctAnswer = "Visto que / Porque / Dado que",
                        explanation = "Introduzem a razão ou causa de um facto."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o conector à sua relação lógica:",
                        pairs = mapOf(
                            "Além disso / Também" to "Adição de informação",
                            "Porém / No entanto / Todavia" to "Oposição e Contraste",
                            "Porque / Visto que" to "Causa e Justificação",
                            "Por conseguinte / Portanto" to "Conclusão e Consequência"
                        ),
                        explanation = "Muito bem! Estás preparado para redigir textos com coesão e brilhantismo."
                    )
                )
            ),
            Worksheet(
                id = "pt_4_f12",
                numero = 12,
                title = "Grande Prova Final de Língua Portuguesa do 4.º Ano",
                description = "Exame de consolidação de todo o 1.º Ciclo do Ensino Básico com distinção!",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🎓",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_4_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'O sábio professor ofereceu um livro precioso aos seus alunos dedicados', quem é o COMPLEMENTO INDIRETO?",
                        options = listOf("aos seus alunos dedicados", "um livro precioso", "O sábio professor"),
                        correctAnswer = "aos seus alunos dedicados",
                        explanation = "Ofereceu a quem? 'Aos seus alunos dedicados' (Complemento Indireto)!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'precioso' é um adjetivo qualificativo que concorda em género e número com 'livro'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ambos estão no masculino singular."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra 'oxigénio' (o-xi-GÉ-ni-o) quanto à posição da sílaba tónica é uma palavra ___",
                        options = listOf("Esdrúxula", "Grave", "Aguda"),
                        correctAnswer = "Esdrúxula",
                        explanation = "Tem o acento tónico na antepenúltima sílaba!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual frase contém uma oração com verbo conjugado no Pretérito Mais-que-Perfeito?",
                        options = listOf("📜 'Quando chegaste, eu já terminara o teste.'", "📜 'Eu termino agora.'", "📜 'Eu terminarei amanhã.'"),
                        correctAnswer = "📜 'Quando chegaste, eu já terminara o teste.'",
                        explanation = "'Terminara' é o Pretérito Mais-que-Perfeito simples! 📜"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o antónimo e o sinónimo de 'efémero' (passageiro)?",
                        options = listOf("Sinónimo: passageiro / Antónimo: eterno", "Sinónimo: feio / Antónimo: belo", "Sinónimo: rápido / Antónimo: lento"),
                        correctAnswer = "Sinónimo: passageiro / Antónimo: eterno",
                        explanation = "Efémero é o que dura pouco tempo; o contrário é eterno ou duradouro."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Em 'O mar rugia como uma fera indomável', temos simultaneamente uma Personificação e uma Comparação.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O mar ruge (personificação) e usa 'como' para comparar com fera."
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as etapas completas do ciclo de redação:",
                        options = listOf("Planificação mental e rascunho", "Textualização com parágrafos e conectores", "Revisão ortográfica, sintática e pontuação final"),
                        correctAnswers = listOf("Planificação mental e rascunho", "Textualização com parágrafos e conectores", "Revisão ortográfica, sintática e pontuação final"),
                        correctAnswer = "Planificação mental e rascunho-Textualização com parágrafos e conectores-Revisão ortográfica, sintática e pontuação final",
                        explanation = "O método de escrita dos grandes escritores portugueses!"
                    ),
                    WorksheetExercise(
                        id = "pt_4_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio Supremo de Português:",
                        pairs = mapOf(
                            "Lápis" to "Nome Comum Sobrecomum/Invariável no plural",
                            "Cantávamos" to "1.ª pessoa do plural do Pretérito Imperfeito",
                            "Nós" to "Pronome Pessoal Sujeito",
                            "Magnífico" to "Adjetivo no Grau Normal (Superlativo de Bom/Grande)"
                        ),
                        explanation = "Parabéns com Louvor! Concluíste o Programa Nacional de Português do 1.º Ciclo!"
                    )
                )
            )
        )
    )

    val matematica = WorksheetDiscipline(
        id = "mat_4",
        title = "Matemática",
        emoji = "🧮",
        color = Color(0xFF10B981),
        worksheets = listOf(
            Worksheet(
                id = "mat_4_f1",
                numero = 1,
                title = "Grandes Números até 1 000 000 (Um Milhão)",
                description = "Classes das unidades, dos milhares e dos milhões. Decomposição e valor posicional.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "💰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos milhares são precisos para formar 1 Milhão (1 000 000)?",
                        options = listOf("1 000 milhares (mil milhares)", "100 milhares", "10 milhares"),
                        correctAnswer = "1 000 milhares (mil milhares)",
                        explanation = "1 000 x 1 000 = 1 000 000 (Um Milhão)!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No número 458 320, o algarismo 5 ocupa a ordem das DEZENAS DE MILHAR e vale 50 000.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 5 x 10 000 = 50 000 unidades."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Lê o número por extenso: 750 000 lê-se 'Setecentos e cinquenta ___'.",
                        options = listOf("mil", "milhões", "centenas"),
                        correctAnswer = "mil",
                        explanation = "750 milhares = 750 mil."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes números é exatamente UM MILHÃO E MEIO?",
                        options = listOf("💵 1 500 000", "💵 1 050 000", "💵 1 005 000"),
                        correctAnswer = "💵 1 500 000",
                        explanation = "1 500 000 = Um Milhão e quinhentos mil! 💵"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o número que antecede imediatamente (o antecessor de) 100 000?",
                        options = listOf("99 999", "99 990", "90 000"),
                        correctAnswer = "99 999",
                        explanation = "100 000 - 1 = 99 999!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Arredondar 38 720 à centena de milhar mais próxima dá 0, e à dezena de milhar dá 40 000.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 38 720 está mais próximo de 40 000 do que de 30 000."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os grandes números por ordem decrescente (do maior para o menor):",
                        options = listOf("950 000", "820 500", "640 000"),
                        correctAnswers = listOf("950 000", "820 500", "640 000"),
                        correctAnswer = "950 000-820 500-640 000",
                        explanation = "950 000 > 820 500 > 640 000."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Decompõe os números nas suas ordens:",
                        pairs = mapOf(
                            "345 000" to "300 000 + 40 000 + 5 000",
                            "1 200 000" to "1 Milhão + 2 Centenas de Milhar",
                            "708 040" to "700 000 + 8 000 + 40",
                            "99 999" to "9 dezenas de milhar + 9 milhares + 999"
                        ),
                        explanation = "Excelente domínio da classe dos milhões e milhares!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f2",
                numero = 2,
                title = "As Quatro Operações e Propriedades Algébricas",
                description = "Propriedade comutativa, associativa e distributiva da multiplicação sobre a adição.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧮",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A expressão 5 x (20 + 4) = (5 x 20) + (5 x 4) exemplifica qual propriedade matemática?",
                        options = listOf("Propriedade Distributiva da Multiplicação", "Propriedade Comutativa", "Elemento Neutro"),
                        correctAnswer = "Propriedade Distributiva da Multiplicação",
                        explanation = "O 5 distribui-se pelo 20 e pelo 4: 100 + 20 = 120!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O zero (0) é o elemento absorvente da multiplicação (qualquer número x 0 = 0).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: 1 548 x 0 = 0."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Calcula usando cálculo mental: 25 x 40 = ___",
                        options = listOf("1 000", "100", "10 000"),
                        correctAnswer = "1 000",
                        explanation = "25 x 4 = 100; multiplicando por 10 = 1 000!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Numa expressão numérica mista como 10 + 5 x 4, o que devemos resolver PRIMEIRO?",
                        options = listOf("✖️ A Multiplicação (5 x 4)", "➕ A Adição (10 + 5)", "Tanto faz"),
                        correctAnswer = "✖️ A Multiplicação (5 x 4)",
                        explanation = "A multiplicação tem prioridade sobre a adição: 10 + 20 = 30! ✖️"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 3 500 x 20?",
                        options = listOf("70 000", "7 000", "700 000"),
                        correctAnswer = "70 000",
                        explanation = "35 x 2 = 70; juntando os três zeros = 70 000!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A divisão de um número por zero é uma operação impossível na matemática.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Não se pode dividir por zero."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Calcula: (50 - 20) x (10 + 2) =",
                        options = listOf("360", "300", "400"),
                        correctAnswer = "360",
                        explanation = "30 x 12 = 360!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a propriedade ao seu exemplo:",
                        pairs = mapOf(
                            "a + b = b + a" to "Propriedade Comutativa da Adição",
                            "a x (b + c) = a x b + a x c" to "Propriedade Distributiva",
                            "(a x b) x c = a x (b x c)" to "Propriedade Associativa",
                            "a x 1 = a" to "Elemento Neutro da Multiplicação"
                        ),
                        explanation = "Perfeita compreensão das leis do cálculo aritmético!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f3",
                numero = 3,
                title = "Divisão com Divisor de Dois Algarismos",
                description = "Algoritmo longo da divisão inteira, estimativa de quociente e verificação (prova real).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "➗",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a fórmula da PROVA REAL da divisão (Relação Fundamental da Divisão)?",
                        options = listOf("Dividendo = (Divisor x Quociente) + Resto", "Dividendo = Divisor + Quociente", "Dividendo = Quociente - Resto"),
                        correctAnswer = "Dividendo = (Divisor x Quociente) + Resto",
                        explanation = "Dividendo = Divisor x Quociente + Resto (com Resto < Divisor)!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Numa divisão por 25, o maior resto possível é 24.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O resto tem de ser sempre estritamente menor que o divisor (24 < 25)."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Calcula: 480 dividido por 12 dá quociente ___ e resto 0.",
                        options = listOf("40", "4", "400"),
                        correctAnswer = "40",
                        explanation = "48 : 12 = 4; logo 480 : 12 = 40!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 1 000 dividido por 25?",
                        options = listOf("🪙 40", "🪙 25", "🪙 50"),
                        correctAnswer = "🪙 40",
                        explanation = "100 : 25 = 4; logo 1000 : 25 = 40! 🪙"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se 300 livros forem empacotados em caixas de 15 livros, de quantas caixas precisamos?",
                        options = listOf("20 caixas", "15 caixas", "30 caixas"),
                        correctAnswer = "20 caixas",
                        explanation = "300 : 15 = 20 caixas completas."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "750 dividido por 50 dá o mesmo quociente que 75 dividido por 5 (cortando um zero em ambos).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Cortar o mesmo número de zeros no dividendo e divisor mantém o quociente inalterado (75 : 5 = 15)."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Calcula o resto da divisão de 105 por 10:",
                        options = listOf("5", "10", "0"),
                        correctAnswer = "5",
                        explanation = "10 x 10 = 100; resto = 105 - 100 = 5!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula os quocientes exatos:",
                        pairs = mapOf(
                            "600 : 20" to "30",
                            "960 : 32" to "30",
                            "1 250 : 25" to "50",
                            "840 : 12" to "70"
                        ),
                        explanation = "Excelente mestria no algoritmo da divisão!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f4",
                numero = 4,
                title = "Frações Equivalentes e Adição/Subtração de Frações",
                description = "Simplificação, frações equivalentes e operações com o mesmo denominador.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🍰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Para encontrar uma FRAÇÃO EQUIVALENTE a 2/3, podemos multiplicar o numerador e o denominador por 2, obtendo...",
                        options = listOf("4/6", "3/4", "2/6"),
                        correctAnswer = "4/6",
                        explanation = "2x2 / 3x2 = 4/6! Representam exatamente a mesma quantidade."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Quanto é 2/5 + 1/5? Dá 3/5.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Mantemos o mesmo denominador (5) e somamos os numeradores (2 + 1 = 3)."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Subtrai: 7/8 - 3/8 = ___ (simplificado: 1/2).",
                        options = listOf("4/8", "10/8", "4/0"),
                        correctAnswer = "4/8",
                        explanation = "7/8 - 3/8 = 4/8, que simplificando a dividir por 4 dá 1/2!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frações é irredutível e equivale a 50/100?",
                        options = listOf("🍰 1/2", "🍰 2/5", "🍰 3/4"),
                        correctAnswer = "🍰 1/2",
                        explanation = "50/100 = 5/10 = 1/2! 🍰"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A fração 12/4 representa que número inteiro?",
                        options = listOf("3", "4", "8"),
                        correctAnswer = "3",
                        explanation = "12 a dividir por 4 é igual a 3 inteiros!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Numa fração própria (como 3/7), o numerador é sempre menor do que o denominador, logo vale menos de 1 unidade.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Frações impróprias têm numerador maior que o denominador e valem mais que 1."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 1 unidade inteira menos 1/4 (1 - 1/4)?",
                        options = listOf("3/4", "1/2", "2/4"),
                        correctAnswer = "3/4",
                        explanation = "4/4 - 1/4 = 3/4!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as frações equivalentes:",
                        pairs = mapOf(
                            "1/3" to "2/6",
                            "3/5" to "6/10",
                            "3/4" to "75/100",
                            "2/8" to "1/4"
                        ),
                        explanation = "Perfeito domínio de frações equivalentes e operações!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f5",
                numero = 5,
                title = "Decimais e Percentagens: 10%, 25%, 50%, 75% e 100%",
                description = "Relação entre fração, número decimal e percentagem nas compras e descontos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🏷️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A percentagem de 50% corresponde a qual fração e número decimal?",
                        options = listOf("1/2 (Metade) e 0,50", "1/4 e 0,25", "1/10 e 0,10"),
                        correctAnswer = "1/2 (Metade) e 0,50",
                        explanation = "50% = 50/100 = 1/2 = 0,50 (a metade de um todo)!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um desconto de 25% numa camisola de 40€ significa que poupas 10€ (40 : 4 = 10€).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 25% é a quarta parte (1/4). 40 : 4 = 10€ de desconto."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A fração 3/4 corresponde a ___ % de uma quantidade.",
                        options = listOf("75", "25", "50"),
                        correctAnswer = "75",
                        explanation = "3/4 = 75/100 = 75%!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 10% de 200€?",
                        options = listOf("💶 20€", "💶 10€", "💶 50€"),
                        correctAnswer = "💶 20€",
                        explanation = "10% de 200€ é o mesmo que dividir 200 por 10 = 20€! 💶"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "100% de qualquer quantia representa...",
                        options = listOf("A totalidade inteira completa", "A metade", "Zero"),
                        correctAnswer = "A totalidade inteira completa",
                        explanation = "100% = 100/100 = o total absoluto."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "0,75 é maior do que 0,8.",
                        correctAnswer = "false",
                        explanation = "Falso! 0,8 = 0,80, que é maior do que 0,75."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 0,25 + 0,75?",
                        options = listOf("1,00 (1 inteiro)", "0,100", "0,50"),
                        correctAnswer = "1,00 (1 inteiro)",
                        explanation = "25 centésimas + 75 centésimas = 100 centésimas = 1 unidade inteira!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a percentagem à respetiva fração irredutível:",
                        pairs = mapOf(
                            "50%" to "1/2 (metade)",
                            "25%" to "1/4 (um quarto)",
                            "75%" to "3/4 (três quartos)",
                            "10%" to "1/10 (um décimo)"
                        ),
                        explanation = "Excelente agilidade na conversão de percentagens e decimais!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f6",
                numero = 6,
                title = "Geometria Espacial: Poliedros, Prismas e Planificações",
                description = "A Relação de Euler (F + V = A + 2), prismas, pirâmides e planificações de sólidos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📦",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a célebre Relação de Euler para qualquer poliedro convexo?",
                        options = listOf("Faces + Vértices = Arestas + 2 (F + V = A + 2)", "Faces = Arestas", "Vértices = 2 x Faces"),
                        correctAnswer = "Faces + Vértices = Arestas + 2 (F + V = A + 2)",
                        explanation = "F + V = A + 2 (ex: no cubo F=6, V=8, A=12 -> 6+8 = 12+2 = 14)!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um Prisma Triangular tem 2 bases triangulares iguais e 3 faces laterais retangulares.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Tem 5 faces, 6 vértices e 9 arestas."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A planificação de um cubo é composta por 6 ___ exatamente iguais.",
                        options = listOf("quadrados", "triângulos", "círculos"),
                        correctAnswer = "quadrados",
                        explanation = "O cubo abre-se numa cruz de 6 quadrados."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes sólidos tem 1 vértice no topo (ápice) e faces laterais triangulares?",
                        options = listOf("🔺 Pirâmide", "📦 Prisma retangular", "🥫 Cilindro"),
                        correctAnswer = "🔺 Pirâmide",
                        explanation = "A pirâmide afunila para um único vértice no topo! 🔺"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas arestas tem uma Pirâmide de Base Quadrada?",
                        options = listOf("8 arestas", "5 arestas", "6 arestas"),
                        correctAnswer = "8 arestas",
                        explanation = "4 arestas na base quadrada + 4 arestas laterais = 8 arestas!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O cone e o cilindro são poliedros porque têm faces planas.",
                        correctAnswer = "false",
                        explanation = "Falso! São corpos redondos (não poliedros) porque têm superfícies curvas de revolução."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos vértices tem um Prisma Hexagonal?",
                        options = listOf("12 vértices", "6 vértices", "18 vértices"),
                        correctAnswer = "12 vértices",
                        explanation = "6 vértices na base superior + 6 vértices na base inferior = 12 vértices!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula o número de faces, vértices e arestas:",
                        pairs = mapOf(
                            "Cubo" to "6 Faces, 8 Vértices, 12 Arestas",
                            "Pirâmide Quadrangular" to "5 Faces, 5 Vértices, 8 Arestas",
                            "Prisma Triangular" to "5 Faces, 6 Vértices, 9 Arestas",
                            "Tetraedro (Pirâmide Triangular)" to "4 Faces, 4 Vértices, 6 Arestas"
                        ),
                        explanation = "Perfeito conhecimento dos sólidos geométricos e suas propriedades!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f7",
                numero = 7,
                title = "Geometria Plana: Classificação de Triângulos e Ângulos",
                description = "Equilátero, Isósceles, Escaleno; Retângulo, Acutângulo e Obtusângulo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📐",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se classifica um triângulo com os 3 LADOS EXATAMENTE IGUAIS?",
                        options = listOf("Triângulo Equilátero", "Triângulo Isósceles", "Triângulo Escaleno"),
                        correctAnswer = "Triângulo Equilátero",
                        explanation = "Equilátero = 3 lados de igual comprimento e 3 ângulos de 60°!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um Triângulo Retângulo possui obrigatoriamente um ângulo reto de 90°.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O lado oposto ao ângulo reto chama-se hipotenusa."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um triângulo com 2 lados iguais e 1 diferente chama-se triângulo ___",
                        options = listOf("Isósceles", "Escaleno", "Equilátero"),
                        correctAnswer = "Isósceles",
                        explanation = "Isósceles = 2 lados iguais!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes triângulos tem todos os 3 lados de medidas completamente DIFERENTES?",
                        options = listOf("📐 Triângulo Escaleno", "📐 Triângulo Equilátero", "📐 Triângulo Isósceles"),
                        correctAnswer = "📐 Triângulo Escaleno",
                        explanation = "Escaleno = 3 lados de comprimentos desiguais! 📐"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Um triângulo com os 3 ângulos menores que 90° (todos agudos) chama-se...",
                        options = listOf("Triângulo Acutângulo", "Triângulo Obtusângulo", "Triângulo Retângulo"),
                        correctAnswer = "Triângulo Acutângulo",
                        explanation = "Acutângulo = 3 ângulos agudos."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A soma dos ângulos internos de qualquer quadrilátero (ex: quadrado ou retângulo) é 360°.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 4 x 90° = 360°."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se dois ângulos de um triângulo medem 50° e 70°, quanto mede o terceiro ângulo?",
                        options = listOf("60°", "50°", "80°"),
                        correctAnswer = "60°",
                        explanation = "180° - (50° + 70° = 120°) = 60°!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os triângulos quanto aos seus lados:",
                        pairs = mapOf(
                            "3 lados iguais" to "Equilátero",
                            "2 lados iguais" to "Isósceles",
                            "3 lados diferentes" to "Escaleno",
                            "1 ângulo reto de 90°" to "Triângulo Retângulo"
                        ),
                        explanation = "Excelente classificação de triângulos e polígonos!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f8",
                numero = 8,
                title = "Áreas e Perímetros de Quadrados e Retângulos",
                description = "Fórmula da Área (A = c x l ou l x l) em metros quadrados (m²) e centímetros quadrados (cm²).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🟩",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a fórmula matemática para calcular a ÁREA de um Retângulo?",
                        options = listOf("Área = Comprimento x Largura (A = c x l)", "Área = 2 x (c + l)", "Área = c + l"),
                        correctAnswer = "Área = Comprimento x Largura (A = c x l)",
                        explanation = "Multiplicamos o comprimento pela largura para obter a área em unidades quadradas!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um quadrado com 6 metros de lado tem uma área de 36 m² (6 x 6 = 36 m²).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Área do quadrado = lado x lado = 36 m²."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um campo de futebol retangular tem 100 m de comprimento e 50 m de largura. A sua área é ___ m².",
                        options = listOf("5 000", "300", "500"),
                        correctAnswer = "5 000",
                        explanation = "100 x 50 = 5 000 metros quadrados!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o PERÍMETRO de uma sala retangular de 8 m por 5 m?",
                        options = listOf("📐 26 metros", "📐 40 m²", "📐 13 metros"),
                        correctAnswer = "📐 26 metros",
                        explanation = "Perímetro = 8 + 5 + 8 + 5 = 26 metros de rodapé! 📐"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos centímetros quadrados (cm²) tem 1 decímetro quadrado (1 dm²)?",
                        options = listOf("100 cm²", "10 cm²", "1000 cm²"),
                        correctAnswer = "100 cm²",
                        explanation = "10 cm x 10 cm = 100 cm²!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A unidade principal de medida de área no Sistema Internacional é o Metro Quadrado (m²).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Representa a superfície de um quadrado de 1 metro de lado."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se a área de um retângulo é 48 cm² e o seu comprimento é 8 cm, qual é a sua largura?",
                        options = listOf("6 cm", "8 cm", "4 cm"),
                        correctAnswer = "6 cm",
                        explanation = "48 : 8 = 6 cm de largura!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula a área de cada superfície:",
                        pairs = mapOf(
                            "Quadrado de 7 cm de lado" to "Área = 49 cm²",
                            "Retângulo de 12 m por 5 m" to "Área = 60 m²",
                            "Quadrado de 10 m de lado" to "Área = 100 m²",
                            "Retângulo de 20 cm por 4 cm" to "Área = 80 cm²"
                        ),
                        explanation = "Cálculo de áreas e perímetros perfeito!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f9",
                numero = 9,
                title = "Medidas de Volume, Capacidade e Massa",
                description = "Litros, decilitros, mililitros (1 l = 1000 ml), toneladas (1 t = 1000 kg), gramas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧪",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos mililitros (ml) existem em 1 Litro (l) de água?",
                        options = listOf("1 000 ml", "100 ml", "10 000 ml"),
                        correctAnswer = "1 000 ml",
                        explanation = "1 Litro = 1 000 mililitros (1 l = 1000 ml)!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "1 Tonelada (t) equivale a exatamente 1 000 Quilogramas (1 t = 1 000 kg).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Usada para pesar camiões, contentores e animais pesados."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Meio litro de sumo corresponde a ___ ml.",
                        options = listOf("500", "250", "750"),
                        correctAnswer = "500",
                        explanation = "1 000 : 2 = 500 ml!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quantos gramas (g) tem 1 Quilograma (kg)?",
                        options = listOf("⚖️ 1 000 g", "⚖️ 100 g", "⚖️ 500 g"),
                        correctAnswer = "⚖️ 1 000 g",
                        explanation = "1 kg = 1 000 gramas! ⚖️"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Um camião transporta 3,5 toneladas de areia. Quantos quilogramas transporta?",
                        options = listOf("3 500 kg", "350 kg", "35 000 kg"),
                        correctAnswer = "3 500 kg",
                        explanation = "3,5 x 1 000 = 3 500 kg!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "1 metro cúbico (1 m³) tem uma capacidade equivalente a 1 000 litros de água.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1 m³ = 1 000 l."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se tomares um xarope de 5 ml, 3 vezes por dia, durante 4 dias, quanto xarope tomas no total?",
                        options = listOf("60 ml", "15 ml", "120 ml"),
                        correctAnswer = "60 ml",
                        explanation = "5 x 3 = 15 ml por dia; 15 x 4 = 60 ml no total!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as equivalências de medidas:",
                        pairs = mapOf(
                            "1 Litro" to "1 000 ml",
                            "1 Quilograma" to "1 000 g",
                            "1 Tonelada" to "1 000 kg",
                            "1 Centilitro" to "10 ml"
                        ),
                        explanation = "Muito bem! Conversões de medidas realizadas com rigor."
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f10",
                numero = 10,
                title = "Tratamento de Dados: Média Aritmética e Gráficos",
                description = "Calcula a Média (soma a dividir pelo número de dados), Moda e interpreta tabelas complexas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se calcula a MÉDIA ARITMÉTICA de um conjunto de valores?",
                        options = listOf("Somam-se todos os valores e divide-se pelo número total de valores", "Escolhe-se o valor mais alto", "Multiplicam-se todos os valores"),
                        correctAnswer = "Somam-se todos os valores e divide-se pelo número total de valores",
                        explanation = "Média = Soma total / Número de dados!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Zé tirou 4, 5 e 3 nos testes. A sua média é 4 (4 + 5 + 3 = 12; 12 : 3 = 4).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A média das 3 notas é exatamente 4."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Num gráfico circular (em tarte), a circunferência completa corresponde a ___ % dos dados.",
                        options = listOf("100", "50", "360"),
                        correctAnswer = "100",
                        explanation = "O círculo total representa 100% dos dados analisados."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é a MÉDIA das idades: 8 anos, 10 anos e 12 anos?",
                        options = listOf("🎂 10 anos", "🎂 9 anos", "🎂 11 anos"),
                        correctAnswer = "🎂 10 anos",
                        explanation = "8 + 10 + 12 = 30; 30 : 3 = 10 anos! 🎂"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A diferença entre a temperatura máxima (28°C) e a mínima (14°C) chama-se...",
                        options = listOf("Amplitude Térmica (14°C)", "Média", "Moda"),
                        correctAnswer = "Amplitude Térmica (14°C)",
                        explanation = "28 - 14 = 14°C de amplitude!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A média de um grupo de números tem de ser sempre maior do que todos os números.",
                        correctAnswer = "false",
                        explanation = "Falso! A média situa-se sempre entre o valor mínimo e o valor máximo."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se 4 amigos têm 20€, 30€, 10€ e 40€, quanto dinheiro têm em média cada um?",
                        options = listOf("25€", "20€", "30€"),
                        correctAnswer = "25€",
                        explanation = "100€ no total : 4 amigos = 25€ de média cada um!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os conceitos de estatística:",
                        pairs = mapOf(
                            "Média" to "Soma de todos a dividir pelo número total",
                            "Moda" to "O valor com maior repetição",
                            "Amplitude" to "Valor Máximo menos Valor Mínimo",
                            "Gráfico Circular" to "Divisão proporcional de 100% num círculo"
                        ),
                        explanation = "Excelente análise estatística e tratamento de dados!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f11",
                numero = 11,
                title = "Problemas de Lógica, Padrões e Pensamento Computacional",
                description = "Sequências numéricas, equações simples com incógnita e algoritmos lógicos.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🧠",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Descobre o termo que falta na sequência geométrica: 2, 4, 8, 16, 32, ___",
                        options = listOf("64", "48", "60"),
                        correctAnswer = "64",
                        explanation = "A sequência duplica a cada passo (x 2): 32 x 2 = 64!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se 3 caixas de lápis iguais custam 18€, então 5 caixas iguais custam 30€.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 18 : 3 = 6€ por caixa; 5 x 6€ = 30€."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Resolve o enigma: Se 2x + 10 = 50, então x = ___",
                        options = listOf("20", "25", "30"),
                        correctAnswer = "20",
                        explanation = "2x = 50 - 10 = 40; x = 40 : 2 = 20!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual número completa a sequência de quadrados perfeitos: 1, 4, 9, 16, 25, ___?",
                        options = listOf("🔢 36 (6 x 6)", "🔢 30", "🔢 49"),
                        correctAnswer = "🔢 36 (6 x 6)",
                        explanation = "1², 2², 3², 4², 5², 6² = 36! 🔢"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Num parque há bicicletas (2 rodas) e triciclos (3 rodas). Se há 10 veículos e 24 rodas no total, quantas bicicletas há?",
                        options = listOf("6 bicicletas (e 4 triciclos)", "5 bicicletas", "8 bicicletas"),
                        correctAnswer = "6 bicicletas (e 4 triciclos)",
                        explanation = "6 x 2 rodas = 12 rodas; 4 x 3 rodas = 12 rodas; Total = 24 rodas e 10 veículos!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 17 é um número primo porque só é divisível por 1 e por si próprio.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Números primos têm exatamente 2 divisores (1 e ele próprio)."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o menor múltiplo comum (m.m.c.) entre 4 e 6?",
                        options = listOf("12", "24", "8"),
                        correctAnswer = "12",
                        explanation = "Múltiplos de 4 (4, 8, 12...); Múltiplos de 6 (6, 12...); O menor comum é 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os números em Primos ou Compostos:",
                        pairs = mapOf(
                            "13" to "Número Primo (divisível por 1 e 13)",
                            "15" to "Número Composto (divisível por 1, 3, 5, 15)",
                            "19" to "Número Primo (divisível por 1 e 19)",
                            "24" to "Número Composto (múltiplos divisores)"
                        ),
                        explanation = "Raciocínio lógico e matemático de nível olímpico!"
                    )
                )
            ),
            Worksheet(
                id = "mat_4_f12",
                numero = 12,
                title = "Grande Desafio Olímpico de Matemática do 4.º Ano",
                description = "A grande consagração matemática do 1.º Ciclo do Ensino Básico!",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_4_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Uma escola com 350 alunos organizou uma visita de estudo em autocarros de 55 lugares. Quantos autocarros são precisos para levar todos os alunos?",
                        options = listOf("7 autocarros", "6 autocarros", "8 autocarros"),
                        correctAnswer = "7 autocarros",
                        explanation = "350 : 55 = 6 com resto 20. São precisos 7 autocarros para levar todos!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma piscina retangular tem 25 m de comprimento, 10 m de largura e 2 m de profundidade. O seu volume é 500 m³ (500 000 litros).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! V = 25 x 10 x 2 = 500 m³ = 500 000 litros de água!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A soma dos ângulos internos de um triângulo é 180° e de um quadrado é ___ °.",
                        options = listOf("360", "270", "180"),
                        correctAnswer = "360",
                        explanation = "Qualquer quadrilátero soma 360° internos!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 3/4 de 1 000€?",
                        options = listOf("💰 750€", "💰 250€", "💰 500€"),
                        correctAnswer = "💰 750€",
                        explanation = "1 000 : 4 = 250; 250 x 3 = 750€! 💰"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se 15 operários constroem um muro em 6 dias, quantos dias demorariam 30 operários ao mesmo ritmo?",
                        options = listOf("3 dias", "12 dias", "6 dias"),
                        correctAnswer = "3 dias",
                        explanation = "Com o dobro dos trabalhadores (proporcionalidade inversa), demoram metade do tempo: 3 dias!"
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 2 é o único número par que é também um número primo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Todos os outros pares são divisíveis por 2 além de 1 e de si próprios."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as frações da menor para a maior:",
                        options = listOf("1/4", "1/2", "3/4"),
                        correctAnswers = listOf("1/4", "1/2", "3/4"),
                        correctAnswer = "1/4-1/2-3/4",
                        explanation = "0,25 < 0,50 < 0,75."
                    ),
                    WorksheetExercise(
                        id = "mat_4_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio das contas olímpicas:",
                        pairs = mapOf(
                            "125 x 8" to "1 000",
                            "5 000 : 25" to "200",
                            "0,75 + 0,25" to "1,00",
                            "15% de 200" to "30"
                        ),
                        explanation = "Parabéns, Mestre Supremo da Matemática do 4.º Ano! Conquistaste a Medalha de Ouro!"
                    )
                )
            )
        )
    )

    val estudoMeio = WorksheetDiscipline(
        id = "em_4",
        title = "Estudo do Meio",
        emoji = "🌍🌱",
        color = Color(0xFFF59E0B),
        worksheets = listOf(
            Worksheet(
                id = "em_4_f1",
                numero = 1,
                title = "História de Portugal: As Origens e a Romanização",
                description = "Os Lusitanos, Viriato, a chegada dos Romanos, estradas, pontes, latim e monumentos.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🏛️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem foi o heróico chefe dos Lusitanos que combateu bravamente a invasão dos exércitos romanos na Península Ibérica?",
                        options = listOf("Viriato", "D. Afonso Henriques", "Júlio César"),
                        correctAnswer = "Viriato",
                        explanation = "Viriato liderou a resistência lusitana nas serras com táticas de guerrilha!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os Romanos trouxeram para a Península a sua língua, o Latim, da qual derivou a Língua Portuguesa.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O português, espanhol, francês e italiano nasceram do latim vulgar romano."
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A grande cidade romana com ruínas de mosaicos e casas perto de Coimbra chama-se ___",
                        options = listOf("Conímbriga", "Bracara Augusta", "Lisboa"),
                        correctAnswer = "Conímbriga",
                        explanation = "As ruínas de Conímbriga são o maior complexo arqueológico romano em Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual famoso templo romano com colunas de granito e mármore se ergue na cidade de Évora?",
                        options = listOf("🏛️ Templo Romano de Évora (Templo de Diana)", "🏰 Castelo de Guimarães", "⛪ Mosteiro dos Jerónimos"),
                        correctAnswer = "🏛️ Templo Romano de Évora (Templo de Diana)",
                        explanation = "O Templo Romano de Évora data do século I d.C. e é Património Mundial! 🏛️"
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que importantes inovações construíram os romanos que ligavam todo o império?",
                        options = listOf("Estradas pavimentadas, pontes de pedra e aquedutos de água", "Caminhos de ferro", "Foguetões"),
                        correctAnswer = "Estradas pavimentadas, pontes de pedra e aquedutos de água",
                        explanation = "A rede viária e aquedutos permitiram um enorme desenvolvimento económico e urbano."
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os povos bárbaros (Suevos e Visigodos) e mais tarde os Muçulmanos (Árabes) também ocuparam a Península Ibérica.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Deixaram grande herança na agricultura (nora, azenha, rega) e na língua."
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Palavras começadas por 'Al-' como 'almofada, azeite, algarve, alcachofra' têm origem...",
                        options = listOf("Árabe / Muçulmana", "Inglesa", "Chinesa"),
                        correctAnswer = "Árabe / Muçulmana",
                        explanation = "A presença árabe de mais de 500 anos influenciou fortemente o vocabulário português!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o povo antigo ao seu legado histórico:",
                        pairs = mapOf(
                            "Lusitanos" to "Povo guerreiro pastoril e Viriato",
                            "Romanos" to "Latim, pontes, calçadas e direito romano",
                            "Visigodos" to "Reino cristão peninsular",
                            "Árabes / Mouros" to "Noras de água, azulejos e palavras com 'Al-''"
                        ),
                        explanation = "Excelente conhecimento sobre as origens e raízes históricas de Portugal!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f2",
                numero = 2,
                title = "A Fundação de Portugal e a Reconquista",
                description = "Condado Portucalense, D. Afonso Henriques, Batalha de S. Mamede (1128) e Tratado de Zamora (1143).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⚔️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem foi o Primeiro Rei de Portugal, conhecido como 'O Conquistador'?",
                        options = listOf("D. Afonso Henriques", "D. Dinis", "D. João I"),
                        correctAnswer = "D. Afonso Henriques",
                        explanation = "D. Afonso Henriques fundou o Reino de Portugal e foi o seu primeiro monarca!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Em 1128, na Batalha de São Mamede em Guimarães, D. Afonso Henriques assumiu o governo do Condado Portucalense.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Por isso Guimarães é celebrada como o 'Berço da Nação'."
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A independência de Portugal foi reconhecida pelo Reino de Leão em 1143 no Tratado de ___",
                        options = listOf("Zamora", "Tordesilhas", "Lisboa"),
                        correctAnswer = "Zamora",
                        explanation = "O Tratado de Zamora de 1143 selou a certidão de nascimento de Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Em que cidade histórica nasceu D. Afonso Henriques e onde fica o Castelo berço de Portugal?",
                        options = listOf("🏰 Guimarães", "🏖️ Faro", "🌾 Beja"),
                        correctAnswer = "🏰 Guimarães",
                        explanation = "'Aqui nasceu Portugal' - Guimarães! 🏰"
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em que documento papal em 1179 o Papa Alexandre III reconheceu oficialmente D. Afonso Henriques como Rei de Portugal?",
                        options = listOf("Bula Manifestis Probatum", "Tratado de Paris", "Carta de Foral"),
                        correctAnswer = "Bula Manifestis Probatum",
                        explanation = "A Bula Papal consagrou a independência de Portugal perante toda a cristandade europeia!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A conquista definitiva do Algarve aos mouros em 1249 no reinado de D. Afonso III fixou as fronteiras terrestres de Portugal.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Portugal tem uma das fronteiras mais antigas e estáveis do mundo."
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Rei D. Dinis ficou conhecido por...",
                        options = listOf("Fundar a 1.ª Universidade em 1290 e plantar o Pinhal de Leiria", "Descobrir o Brasil", "Perder a independência"),
                        correctAnswer = "Fundar a 1.ª Universidade em 1290 e plantar o Pinhal de Leiria",
                        explanation = "D. Dinis, o 'Lavrador' e 'Poeta', oficializou a língua portuguesa e criou a Universidade!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Ordena cronologicamente os acontecimentos fundadores:",
                        pairs = mapOf(
                            "1128" to "Batalha de São Mamede (Guimarães)",
                            "1143" to "Tratado de Zamora (Independência)",
                            "1179" to "Bula Papal Manifestis Probatum",
                            "1249" to "Conquista do Algarve e fronteiras definitivas"
                        ),
                        explanation = "Fantástico domínio da fundação da nacionalidade portuguesa!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f3",
                numero = 3,
                title = "A Epopeia dos Descobrimentos Marítimos",
                description = "Caravelas, Infante D. Henrique, Vasco da Gama, Pedro Álvares Cabral e a Rota das Especiarias.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⛵",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem foi o príncipe português que impulsionou e organizou a grande era da expansão marítima a partir de Sagres?",
                        options = listOf("Infante D. Henrique (O Navegador)", "D. Pedro IV", "D. Sebastião"),
                        correctAnswer = "Infante D. Henrique (O Navegador)",
                        explanation = "O Infante D. Henrique reuniu sábios, cartógrafos e navegadores para explorar o oceano desconhecido!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Caravela portuguesa com velas triangulares (latinas) conseguia navegar contra o vento através do bolinar.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A caravela foi a joia tecnológica náutica dos marinheiros portugueses."
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O navegador português que dobrou pela primeira vez o temível Cabo das Tormentas (Cabo da Boa Esperança) em 1488 foi Bartolomeu ___",
                        options = listOf("Dias", "Gama", "Cabral"),
                        correctAnswer = "Dias",
                        explanation = "Bartolomeu Dias abriu a passagem para o Oceano Índico!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quem liderou a armada portuguesa que descobriu o Caminho Marítimo para a Índia em 1498?",
                        options = listOf("⛵ Vasco da Gama", "⛵ Cristóvão Colombo", "⛵ Fernão de Magalhães"),
                        correctAnswer = "⛵ Vasco da Gama",
                        explanation = "Vasco da Gama chegou a Calecute na Índia, ligando a Europa à Ásia por mar! ⛵"
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em 1500, a armada de Pedro Álvares Cabral chegou à 'Terra de Vera Cruz', hoje chamada...",
                        options = listOf("Brasil", "Moçambique", "Austrália"),
                        correctAnswer = "Brasil",
                        explanation = "Pedro Álvares Cabral alcançou o Brasil em Abril de 1500!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Mosteiro dos Jerónimos e a Torre de Belém em Lisboa foram construídos no estilo Manuelino para celebrar os Descobrimentos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! São monumentos de pedra decorados com cordas, âncoras e esferas armilares."
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem escreveu a grande obra épica 'Os Lusíadas' que canta a viagem de Vasco da Gama e a bravura portuguesa?",
                        options = listOf("Luís Vaz de Camões", "Fernando Pessoa", "Eça de Queirós"),
                        correctAnswer = "Luís Vaz de Camões",
                        explanation = "Camões publicou 'Os Lusíadas' em 1572, imortalizando a história de Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o navegador à sua grande façanha marítima:",
                        pairs = mapOf(
                            "Bartolomeu Dias" to "Dobrou o Cabo da Boa Esperança (1488)",
                            "Vasco da Gama" to "Descobriu o Caminho Marítimo para a Índia (1498)",
                            "Pedro Álvares Cabral" to "Chegou ao Brasil (1500)",
                            "Fernão de Magalhães" to "Comandou a 1.ª Viagem de Circum-navegação da Terra"
                        ),
                        explanation = "Glória e orgulho nos navegadores portugueses que deram novos mundos ao mundo!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f4",
                numero = 4,
                title = "A Restauração de 1640 e o Terramoto de 1755",
                description = "O domínio Filipino, o 1.º de Dezembro de 1640, o Terramoto de Lisboa e o Marquês de Pombal.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👑",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que se celebra em Portugal no feriado nacional do 1.º de Dezembro?",
                        options = listOf("A Restauração da Independência (1640)", "O Natal", "O 25 de Abril"),
                        correctAnswer = "A Restauração da Independência (1640)",
                        explanation = "Em 1640 os 40 Conjurados restauraram a soberania nacional aclamando D. João IV rei!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Entre 1580 e 1640 Portugal esteve sob o domínio dos reis espanhóis (Dinastia Filipina).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Foram 60 anos de União Ibérica iniciada com Filipe II de Espanha."
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A 1 de Novembro de 1755, a cidade de Lisboa foi devastada por um violento ___ seguido de maremoto e incêndios.",
                        options = listOf("Terramoto / Sismo", "Ciclone", "Vulcão"),
                        correctAnswer = "Terramoto / Sismo",
                        explanation = "O Grande Terramoto de Lisboa de 1755 destruiu grande parte da capital."
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quem foi o enérgico ministro de D. José I que reconstruiu a 'Baixa Pombalina' com edifícios anti-sísmicos (gaiola pombalina)?",
                        options = listOf("🏛️ Marquês de Pombal", "👑 D. João VI", "⚔️ D. Afonso Henriques"),
                        correctAnswer = "🏛️ Marquês de Pombal",
                        explanation = "Sebastião José de Carvalho e Melo (Marquês de Pombal) reconstruiu a moderna Baixa de Lisboa! 🏛️"
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual famosa frase é atribuída ao Marquês de Pombal logo após o terramoto de 1755?",
                        options = listOf("'Enterrar os mortos e cuidar dos vivos'", "'Navegar é preciso'", "'Aqui nasceu Portugal'"),
                        correctAnswer = "'Enterrar os mortos e cuidar dos vivos'",
                        explanation = "Mostrou a sua determinação e liderança imediata face à tragédia."
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A 'Gaiola Pombalina' é uma estrutura de madeira flexível no interior das paredes para resistir a futuros terramotos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Foi uma das primeiras engenharias de construção anti-sísmica do mundo."
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A grande praça aberta junto ao Tejo em Lisboa construída na Baixa Pombalina chama-se...",
                        options = listOf("Praça do Comércio (Terreiro do Paço)", "Praça de Espanha", "Campo Grande"),
                        correctAnswer = "Praça do Comércio (Terreiro do Paço)",
                        explanation = "Com a estátua equestre de D. José I e o Arco da Rua Augusta!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a data histórica ao acontecimento:",
                        pairs = mapOf(
                            "1640" to "Restauração da Independência de Portugal",
                            "1755" to "Grande Terramoto de Lisboa",
                            "Marquês de Pombal" to "Reconstrução da Baixa com avenidas retas",
                            "D. João IV" to "Aclamação do Duque de Bragança como Rei"
                        ),
                        explanation = "Excelente domínio da História Moderna de Portugal!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f5",
                numero = 5,
                title = "A República (1910) e a Democracia (25 de Abril de 1974)",
                description = "O 5 de Outubro de 1910, o Estado Novo, a Revolução dos Cravos e a conquista da Liberdade.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌺",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que se comemora no feriado do 5 de Outubro de 1910 em Portugal?",
                        options = listOf("A Implantação da República (fim da Monarquia)", "O Dia de Portugal", "O Ano Novo"),
                        correctAnswer = "A Implantação da República (fim da Monarquia)",
                        explanation = "Portugal deixou de ter Reis e passou a ser governado por Presidentes eleitos!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Revolução do 25 de Abril de 1974 ficou conhecida mundialmente como a 'Revolução dos Cravos'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os soldados colocaram cravos vermelhos nos canos das espingardas."
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A senha musical transmitida na rádio que confirmou o arranque do 25 de Abril foi 'Grândola, Vila ___' de Zeca Afonso.",
                        options = listOf("Morena", "Bela", "Verde"),
                        correctAnswer = "Morena",
                        explanation = "'Grândola, Vila Morena, Terra da Fraternidade...'!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual flor símbolo da paz e liberdade foi distribuída aos militares em Lisboa em 1974?",
                        options = listOf("🌺 Cravo Vermelho", "🌻 Girassol", "🌹 Rosa branca"),
                        correctAnswer = "🌺 Cravo Vermelho",
                        explanation = "O cravo vermelho simboliza a Revolução pacífica de Abril! 🌺"
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que conquistaram os cidadãos portugueses com o 25 de Abril de 1974?",
                        options = listOf("Democracia, Liberdade de expressão, eleições livres e fim da guerra colonial", "Apenas uma bandeira nova", "O direito a não ir à escola"),
                        correctAnswer = "Democracia, Liberdade de expressão, eleições livres e fim da guerra colonial",
                        explanation = "Restaurou todos os direitos cívicos, liberdade de imprensa e voto livre universal!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Presidente da República Portuguesa e os Deputados da Assembleia são eleitos democraticamente pelo voto secreto de todos os cidadãos maiores de 18 anos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O sufrágio universal é a base da democracia portuguesa."
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde se reúne o Parlamento de Portugal (Assembleia da República) em Lisboa?",
                        options = listOf("No Palácio de São Bento", "No Palácio de Belém", "No Castelo de S. Jorge"),
                        correctAnswer = "No Palácio de São Bento",
                        explanation = "Em São Bento reúnem-se os 230 deputados eleitos pelo povo português."
                    ),
                    WorksheetExercise(
                        id = "em_4_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as grandes datas cívicas de Portugal:",
                        pairs = mapOf(
                            "5 de Outubro de 1910" to "Implantação da República",
                            "25 de Abril de 1974" to "Revolução dos Cravos e Democracia",
                            "10 de Junho" to "Dia de Portugal, de Camões e das Comunidades",
                            "1.º de Dezembro" to "Restauração da Independência"
                        ),
                        explanation = "Conhecimento cívico e democrático de excelência!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f6",
                numero = 6,
                title = "Regiões Autónomas: Açores e Madeira",
                description = "Os arquipélagos vulcânicos atlânticos, ilhas, laurissilva, lagoas e autogoverno.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌋",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas ilhas habitadas compõem o Arquipélago dos Açores no meio do Oceano Atlântico?",
                        options = listOf("9 Ilhas", "7 Ilhas", "12 Ilhas"),
                        correctAnswer = "9 Ilhas",
                        explanation = "Os Açores são formados por 9 ilhas divididas em 3 grupos: Ocidental, Central e Oriental!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A floresta Laurissilva da Madeira é uma floresta húmida primitiva milenar classificada como Património Mundial da UNESCO.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É um tesouro natural único de vegetação relíquia."
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A capital da Região Autónoma da Madeira, famosa pelo fogo-de-artifício de Ano Novo e flores, é o ___",
                        options = listOf("Funchal", "Porto Santo", "Ponta Delgada"),
                        correctAnswer = "Funchal",
                        explanation = "O Funchal é a bela capital madeirense!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que prato tradicional açoriano é cozinhado debaixo da terra com o calor do vapor dos vulcões nas Furnas?",
                        options = listOf("🍲 Cozido das Furnas (São Miguel)", "🥘 Francesinha", "🍢 Espetada em pau de louro"),
                        correctAnswer = "🍲 Cozido das Furnas (São Miguel)",
                        explanation = "O Cozido das Furnas é cozinhado lentamente no calor geotérmico da terra! 🍲"
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quais são as duas ilhas habitadas que formam o Arquipélago da Madeira?",
                        options = listOf("Ilha da Madeira e Ilha de Porto Santo", "São Miguel e Terceira", "Pico e Faial"),
                        correctAnswer = "Ilha da Madeira e Ilha de Porto Santo",
                        explanation = "A Madeira e a Ilha Dourada de Porto Santo (com praias de areia terapêutica)!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os Açores e a Madeira têm Governos e Assembleias Regionais próprios (Autonomia Político-Administrativa).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! São Regiões Autónomas da República Portuguesa."
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A Lagoa das Sete Cidades na ilha de São Miguel é famosa pelas suas duas cores...",
                        options = listOf("Uma lagoa Verde e uma lagoa Azul", "Vermelha e Amarela", "Preta e Branca"),
                        correctAnswer = "Uma lagoa Verde e uma lagoa Azul",
                        explanation = "Uma paisagem natural deslumbrante no interior de uma caldeira vulcânica!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o elemento ao arquipélago correto:",
                        pairs = mapOf(
                            "Montanha do Pico (2351 m)" to "Arquipélago dos Açores",
                            "Floresta Laurissilva e Levadas" to "Arquipélago da Madeira",
                            "Lagoa do Fogo e Furnas" to "Arquipélago dos Açores",
                            "Carros de Cesto do Monte" to "Arquipélago da Madeira"
                        ),
                        explanation = "Perfeito conhecimento das nossas pérolas atlânticas!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f7",
                numero = 7,
                title = "Portugal na Europa (União Europeia) e no Mundo (CPLP)",
                description = "A União Europeia (UE), moeda Euro, países vizinhos e a Comunidade dos Países de Língua Portuguesa.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🇪🇺",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em que ano aderiu Portugal à União Europeia (na altura CEE)?",
                        options = listOf("1986", "2000", "1974"),
                        correctAnswer = "1986",
                        explanation = "Portugal aderiu à União Europeia em 1 de Janeiro de 1986!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A moeda oficial partilhada por Portugal e pela maioria dos países da União Europeia é o Euro (€).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Euro entrou em circulação no dia 1 de Janeiro de 2002."
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A Comunidade de Países que partilham a Língua Portuguesa como língua oficial chama-se ___",
                        options = listOf("CPLP", "ONU", "OTAN"),
                        correctAnswer = "CPLP",
                        explanation = "CPLP = Comunidade dos Países de Língua Portuguesa (mais de 260 milhões de falantes)!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes países é o único país com o qual Portugal faz fronteira terrestre?",
                        options = listOf("🇪🇸 Espanha", "🇫🇷 França", "🇮🇹 Itália"),
                        correctAnswer = "🇪🇸 Espanha",
                        explanation = "Portugal e Espanha partilham a Península Ibérica e a 'Raia' fronteiriça! 🇪🇸"
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o maior país de língua oficial portuguesa no continente americano?",
                        options = listOf("Brasil", "Angola", "Cabo Verde"),
                        correctAnswer = "Brasil",
                        explanation = "O Brasil é o país com a maior população de língua portuguesa no planeta!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Espaço Schengen da União Europeia permite a livre circulação de pessoas sem controlo de fronteiras internas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Podemos viajar entre países europeus com liberdade."
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas estrelas douradas em círculo tem a bandeira azul da União Europeia?",
                        options = listOf("12 estrelas", "27 estrelas", "15 estrelas"),
                        correctAnswer = "12 estrelas",
                        explanation = "O círculo de 12 estrelas simboliza a união, solidariedade e harmonia dos povos europeus."
                    ),
                    WorksheetExercise(
                        id = "em_4_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o país de língua portuguesa ao seu continente:",
                        pairs = mapOf(
                            "Angola e Moçambique" to "África",
                            "Brasil" to "América do Sul",
                            "Portugal" to "Europa",
                            "Timor-Leste" to "Ásia / Oceânia"
                        ),
                        explanation = "Excelente visão geopolítica de Portugal na Europa e no Mundo!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f8",
                numero = 8,
                title = "O Sistema Reprodutor e a Transmissão da Vida",
                description = "Células sexuais (óvulo e espermatozoide), fecundação, gravidez, parto e puberdade.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👶",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o encontro e união do óvulo feminino com o espermatozoide masculino?",
                        options = listOf("Fecundação", "Digestão", "Respiração"),
                        correctAnswer = "Fecundação",
                        explanation = "A fecundação dá origem à primeira célula de um novo ser humano: o ovo ou zigoto!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O embrião humano desenvolve-se no interior do Útero da mãe durante cerca de 9 meses (gravidez).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O útero acolhe, protege e alimenta o feto durante 40 semanas."
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O cordão que liga o feto à placenta da mãe para receber nutrientes e oxigénio chama-se cordão ___",
                        options = listOf("umbilical", "vertebral", "espinhal"),
                        correctAnswer = "umbilical",
                        explanation = "O cordão umbilical é o canal de vida entre mãe e filho."
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que célula reprodutiva feminina é produzida nos ovários?",
                        options = listOf("🥚 Óvulo", "🧬 Espermatozoide", "🩸 Glóbulo vermelho"),
                        correctAnswer = "🥚 Óvulo",
                        explanation = "O óvulo é a célula reprodutora feminina! 🥚"
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A fase de transição da infância para a adolescência em que o corpo muda chama-se...",
                        options = listOf("Puberdade", "Metamorfose", "Geriatria"),
                        correctAnswer = "Puberdade",
                        explanation = "Na puberdade ocorrem transformações físicas e hormonais normais do crescimento."
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O leite materno é o alimento ideal e mais completo para os bebés nos primeiros meses de vida.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Fornece todos os nutrientes e anticorpos essenciais."
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O nascimento de um bebé no final do período de gestação chama-se...",
                        options = listOf("Parto", "Germinação", "Eclosão"),
                        correctAnswer = "Parto",
                        explanation = "O parto assinala o nascimento de uma nova vida humana no mundo."
                    ),
                    WorksheetExercise(
                        id = "em_4_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os conceitos da reprodução humana:",
                        pairs = mapOf(
                            "Fecundação" to "União do óvulo com o espermatozoide",
                            "Útero" to "Órgão materno onde o feto cresce",
                            "Placenta e Cordão Umbilical" to "Nutrição e oxigenação do bebé",
                            "Gestação / Gravidez" to "Período de cerca de 9 meses até ao parto"
                        ),
                        explanation = "Excelente conhecimento biológico com respeito e rigor científico!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f9",
                numero = 9,
                title = "Integração dos Sistemas do Corpo Humano",
                description = "O Sistema Nervoso (cérebro e medula) a coordenar Digestão, Circulação e Respiração.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧠",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o órgão central do Sistema Nervoso que processa pensamentos, memórias e comanda todo o corpo?",
                        options = listOf("Cérebro", "Coração", "Estômago"),
                        correctAnswer = "Cérebro",
                        explanation = "O cérebro é o 'computador mestre' que comanda todas as funções do nosso corpo!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os nervos funcionam como fios elétricos biológicos que transmitem mensagens instantâneas entre o cérebro e os músculos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os impulsos nervosos viajam a centenas de quilómetros por hora."
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A coluna vertebral abriga e protege a medula ___, canal principal de comunicação nervosa.",
                        options = listOf("espinal / espinhal", "óssea", "digestiva"),
                        correctAnswer = "espinal / espinhal",
                        explanation = "A medula espinal liga o cérebro a todos os nervos do corpo."
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual sistema capta o oxigénio que o sangue circulatório levará às células do cérebro?",
                        options = listOf("🫁 Sistema Respiratório", "🦴 Sistema Ósseo", "🦷 Sistema Dentário"),
                        correctAnswer = "🫁 Sistema Respiratório",
                        explanation = "Os sistemas respiratório e circulatório trabalham em harmonia perfeita! 🫁"
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a resposta automática e imediata de retirar a mão do lume quente sem pensar?",
                        options = listOf("Ato Reflexo", "Digestão voluntária", "Sono"),
                        correctAnswer = "Ato Reflexo",
                        explanation = "O ato reflexo protege o corpo de queimaduras e perigos imediatos!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O cérebro precisa de oxigénio, água, glicose e bom sono para ter excelente concentração e memória.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Uma mente saudável precisa de boa nutrição e descanso."
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os 5 órgãos dos sentidos enviam sinais ao cérebro através de...",
                        options = listOf("Nervos sensitivos", "Veias de ar", "Ossos ocos"),
                        correctAnswer = "Nervos sensitivos",
                        explanation = "Os recetores sensoriais transmitem luz, som, cheiro, sabor e tato ao cérebro."
                    ),
                    WorksheetExercise(
                        id = "em_4_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o sistema à sua missão no organismo:",
                        pairs = mapOf(
                            "Sistema Nervoso" to "Coordenação, sentidos e comando central",
                            "Sistema Circulatório" to "Transporte de sangue, oxigénio e nutrientes",
                            "Sistema Respiratório" to "Trocas gasosas de oxigénio e CO2",
                            "Sistema Digestivo" to "Transformação e absorção dos alimentos"
                        ),
                        explanation = "Compreensão exemplar da unidade biológica do corpo humano!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f10",
                numero = 10,
                title = "Eletricidade e Circuitos Elétricos Simples",
                description = "Gerador (pilha), recetor (lâmpada), fios condutores, interruptores e segurança.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⚡",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quais são os componentes básicos necessários para montar um CIRCUITO ELÉTRICO simples?",
                        options = listOf("Pilha (fonte), fios condutores, interruptor e lâmpada", "Apenas uma pilha solta", "Uma folha de papel"),
                        correctAnswer = "Pilha (fonte), fios condutores, interruptor e lâmpada",
                        explanation = "A corrente elétrica necessita de uma fonte, caminho condutor fechado e um recetor!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A corrente elétrica só circula quando o circuito está FECHADO.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se o circuito estiver aberto (interruptor desligado), a lâmpada apaga-se."
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Os materiais pelos quais a eletricidade passa com facilidade (como o cobre, ferro e alumínio) chamam-se materiais ___",
                        options = listOf("Bons Condutores", "Isoladores", "Plásticos"),
                        correctAnswer = "Bons Condutores",
                        explanation = "Os metais são excelentes condutores de eletricidade."
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes materiais é um ISOLADOR elétrico seguro usado para revestir os fios?",
                        options = listOf("🔌 Borracha / Plástico", "🥄 Colher de prata", "🪙 Moeda de cobre"),
                        correctAnswer = "🔌 Borracha / Plástico",
                        explanation = "O plástico e a borracha não conduzem eletricidade, evitando choques! 🔌"
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que faz o interruptor de uma parede ou candeeiro?",
                        options = listOf("Abre ou fecha o circuito para ligar e desligar a luz", "Produz eletricidade", "Gera vento"),
                        correctAnswer = "Abre ou fecha o circuito para ligar e desligar a luz",
                        explanation = "Controla o fluxo da corrente de forma prática e segura."
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A água conduz eletricidade, por isso NUNCA devemos tocar em aparelhos elétricos com as mãos molhadas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A humidade aumenta o risco de choques elétricos graves."
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Num circuito com duas lâmpadas em série, se uma lâmpada fundir o que acontece à outra?",
                        options = listOf("Apaga-se porque o circuito fica interrompido", "Fica mais brilhante", "Explode"),
                        correctAnswer = "Apaga-se porque o circuito fica interrompido",
                        explanation = "Num circuito em série a corrente tem um único caminho contínuo."
                    ),
                    WorksheetExercise(
                        id = "em_4_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os materiais em Condutores ou Isoladores:",
                        pairs = mapOf(
                            "Fio de Cobre" to "Bom Condutor elétrico",
                            "Borracha das luvas" to "Isolador elétrico",
                            "Água com sal" to "Condutor de eletricidade",
                            "Madeira seca / Vidro" to "Isolador elétrico"
                        ),
                        explanation = "Excelente conhecimento sobre física da eletricidade e segurança!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f11",
                numero = 11,
                title = "Magnetismo, Ímanes e Forças da Natureza",
                description = "Polos Norte e Sul dos ímanes, atração e repulsão, bússola, gravidade e atrito.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧲",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que acontece quando aproximamos dois POLOS IGUAIS de dois ímanes (Norte com Norte ou Sul com Sul)?",
                        options = listOf("Repelem-se (afastam-se com força)", "Atraem-se e colam-se", "Não acontece nada"),
                        correctAnswer = "Repelem-se (afastam-se com força)",
                        explanation = "Polos iguais repelem-se; polos opostos atraem-se (Norte atrai Sul)!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Terra comporta-se como um gigantesco íman com o seu próprio campo magnético.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É por isso que a agulha magnética da bússola aponta sempre para o Norte magnético."
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A força invisível que atrai todos os objetos para o centro da Terra chama-se Força da ___",
                        options = listOf("Gravidade", "Eletricidade", "Fricção"),
                        correctAnswer = "Gravidade",
                        explanation = "A gravidade descoberta por Isaac Newton faz a maçã cair e mantém-nos no chão!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que metal é fortemente atraído pelos ímanes?",
                        options = listOf("🧲 Ferro / Aço", "🪵 Madeira", "🏺 Vidro"),
                        correctAnswer = "🧲 Ferro / Aço",
                        explanation = "Os materiais ferromagnéticos (ferro, níquel, cobalto) colam-se aos ímanes! 🧲"
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é a Força de Atrito?",
                        options = listOf("A resistência ao movimento que surge no contacto entre duas superfícies", "A força da água do mar", "A luz do sol"),
                        correctAnswer = "A resistência ao movimento que surge no contacto entre duas superfícies",
                        explanation = "O atrito permite travar as rodas dos carros e caminhar sem escorregar!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No espaço em órbita os astronautas flutuam devido à microgravidade.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Estão em queda livre contínua em torno da Terra."
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o instrumento de navegação secular cuja agulha magnética se alinha com o campo da Terra?",
                        options = listOf("Bússola", "Barómetro", "Termómetro"),
                        correctAnswer = "Bússola",
                        explanation = "A bússola permitiu aos marinheiros portugueses navegar em alto mar!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a força física à sua manifestação no dia a dia:",
                        pairs = mapOf(
                            "Gravidade" to "Faz os objetos caírem para o chão",
                            "Magnetismo" to "Atrai clipes de ferro ao íman",
                            "Atrito" to "Permite travar a bicicleta com os calços",
                            "Empuxo" to "Faz os navios flutuarem na água"
                        ),
                        explanation = "Fantástico entendimento das forças físicas da natureza!"
                    )
                )
            ),
            Worksheet(
                id = "em_4_f12",
                numero = 12,
                title = "Grande Quiz Final de História, Geografia e Ciências do 4.º Ano",
                description = "O grande encerramento com chave de ouro do Estudo do Meio do 1.º Ciclo!",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🎓",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_4_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem foi o 1.º Rei de Portugal (1143) e quem descobriu o Caminho Marítimo para a Índia (1498)?",
                        options = listOf("D. Afonso Henriques e Vasco da Gama", "D. Dinis e Pedro Álvares Cabral", "Viriato e Camões"),
                        correctAnswer = "D. Afonso Henriques e Vasco da Gama",
                        explanation = "Dois dos maiores heróis fundadores e navegadores da história lusitana!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O ponto mais alto de Portugal é a Montanha do Pico (2351 m) nos Açores e no Continente é a Torre na Serra da Estrela (1993 m).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Geografia de Portugal na ponta da língua."
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A capital de Portugal é Lisboa e a moeda comum europeia é o ___",
                        options = listOf("Euro (€)", "Escudo", "Dólar"),
                        correctAnswer = "Euro (€)",
                        explanation = "Lisboa e o Euro são a capital e moeda oficiais."
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual foi a grande revolução pacífica que trouxe a liberdade e democracia a Portugal a 25 de Abril de 1974?",
                        options = listOf("🌺 Revolução dos Cravos", "⚔️ Batalha de Aljubarrota", "⛵ Conquista de Ceuta"),
                        correctAnswer = "🌺 Revolução dos Cravos",
                        explanation = "A 25 de Abril de 1974 nasceu a democracia portuguesa contemporânea! 🌺"
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O sangue oxigenado é bombeado pelo coração para as artérias, enquanto os rins filtram o sangue produzindo...",
                        options = listOf("Urina", "Saliva", "Suor"),
                        correctAnswer = "Urina",
                        explanation = "Os rins eliminam resíduos através da urina."
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os metais são bons condutores elétricos e o plástico é um isolador de segurança.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Princípio básico dos circuitos e cablagens elétricas."
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as grandes épocas da História de Portugal:",
                        options = listOf("Presença Romana e Lusitanos", "Fundação de Portugal (1143)", "Era dos Descobrimentos (séculos XV-XVI)", "Democracia e 25 de Abril (1974)"),
                        correctAnswers = listOf("Presença Romana e Lusitanos", "Fundação de Portugal (1143)", "Era dos Descobrimentos (séculos XV-XVI)", "Democracia e 25 de Abril (1974)"),
                        correctAnswer = "Presença Romana e Lusitanos-Fundação de Portugal (1143)-Era dos Descobrimentos (séculos XV-XVI)-Democracia e 25 de Abril (1974)",
                        explanation = "Uma história brilhante de quase 900 anos de Nação!"
                    ),
                    WorksheetExercise(
                        id = "em_4_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio de Consagração Final:",
                        pairs = mapOf(
                            "Viriato" to "Chefe Lusitano que resistiu a Roma",
                            "D. Afonso Henriques" to "Fundador e 1.º Rei de Portugal",
                            "Vasco da Gama" to "Caminho Marítimo para a Índia",
                            "25 de Abril de 1974" to "Conquista da Liberdade e Democracia"
                        ),
                        explanation = "Parabéns, Doutor de Honra do Estudo do Meio do 1.º Ciclo! Conquistaste a Excelência!"
                    )
                )
            )
        )
    )

    val year = WorksheetYear(
        id = 4,
        title = "4.º Ano",
        disciplines = listOf(portugues, matematica, estudoMeio)
    )
}
