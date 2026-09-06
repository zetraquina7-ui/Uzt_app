package com.example.ui.screens

import androidx.compose.ui.graphics.Color

object WorksheetsYear2 {
    val portugues = WorksheetDiscipline(
        id = "pt_2",
        title = "Português",
        emoji = "🇵🇹",
        color = Color(0xFF3B82F6),
        worksheets = listOf(
            Worksheet(
                id = "pt_2_f1",
                numero = 1,
                title = "Sinónimos e Antónimos",
                description = "Descobre palavras com significados semelhantes (sinónimos) e contrários (antónimos).",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🔄",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o sinónimo da palavra 'alegre'?",
                        options = listOf("Triste", "Contente", "Zangado"),
                        correctAnswer = "Contente",
                        explanation = "Alegre e contente significam a mesma coisa, logo são sinónimos!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O antónimo de 'alto' é 'baixo'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Antónimos são palavras com significado oposto."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O sinónimo de 'começar' é ___",
                        options = listOf("iniciar", "terminar", "dormir"),
                        correctAnswer = "iniciar",
                        explanation = "Começar e iniciar são palavras sinónimas."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é o antónimo de 'dia'?",
                        options = listOf("🌙 Noite", "☀️ Sol", "⛅ Tarde"),
                        correctAnswer = "🌙 Noite",
                        explanation = "O contrário de dia é noite! 🌙"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o antónimo de 'rápido'?",
                        options = listOf("Veloz", "Lento", "Apressado"),
                        correctAnswer = "Lento",
                        explanation = "O oposto de alguém rápido é alguém lento."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As palavras 'bonito' e 'belo' são sinónimas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Têm o mesmo significado."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Descobre a palavra antónima de 'cheio':",
                        options = listOf("Vazio", "Grande", "Farto"),
                        correctAnswer = "Vazio",
                        explanation = "O contrário de um copo cheio é um copo vazio."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada palavra ao seu antónimo:",
                        pairs = mapOf(
                            "Quente" to "Frio",
                            "Claro" to "Escuro",
                            "Fácil" to "Difícil",
                            "Gordo" to "Magro"
                        ),
                        explanation = "Excelente ligação de antónimos!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f2",
                numero = 2,
                title = "Nomes Próprios e Nomes Comuns",
                description = "Aprende a distinguir nomes de pessoas/lugares (maiúsculas) de objetos e animais.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🏷️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras é um Nome Próprio?",
                        options = listOf("martelo", "Portugal", "janela"),
                        correctAnswer = "Portugal",
                        explanation = "Portugal é o nome de um país, começa com maiúscula e é um nome próprio!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os nomes próprios escrevem-se sempre com letra inicial maiúscula.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Nomes de pessoas, terras, rios e animais de estimação levam maiúscula."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas opções é um Nome Comum?",
                        options = listOf("🐱 gato", "🇵🇹 Lisboa", "👧 Maria"),
                        correctAnswer = "🐱 gato",
                        explanation = "'gato' é um nome comum de animal, escreve-se com minúscula."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e4",
                        type = ExerciseType.COMPLETING,
                        question = "Na frase 'O ___ foi à praia', qual completa com um nome próprio?",
                        options = listOf("Zé", "livro", "carro"),
                        correctAnswer = "Zé",
                        explanation = "'Zé' é o nome próprio do nosso amigo!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A palavra 'Porto' nesta frase refere-se à cidade. Que tipo de nome é?",
                        options = listOf("Nome Próprio", "Nome Comum", "Verbo"),
                        correctAnswer = "Nome Próprio",
                        explanation = "É o nome da cidade do Porto, logo é nome próprio."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'cadeira' é um nome próprio porque está na sala.",
                        correctAnswer = "false",
                        explanation = "Falso! Cadeira é um objeto comum, logo é um nome comum."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Encontra o nome próprio da lista:",
                        options = listOf("Sofia", "mesa", "lápis"),
                        correctAnswer = "Sofia",
                        explanation = "Sofia é o nome de uma menina."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica os nomes:",
                        pairs = mapOf(
                            "Manuel" to "Nome Próprio",
                            "caderno" to "Nome Comum",
                            "Coimbra" to "Nome Próprio",
                            "árvore" to "Nome Comum"
                        ),
                        explanation = "Muito bem! Distinguiste perfeitamente nomes comuns e próprios."
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f3",
                numero = 3,
                title = "Género e Número dos Nomes",
                description = "Masculino e Feminino, Singular e Plural nas palavras e frases.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "👫",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o feminino da palavra 'menino'?",
                        options = listOf("menina", "homem", "moço"),
                        correctAnswer = "menina",
                        explanation = "Menino está no masculino; menina está no feminino."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O plural da palavra 'flor' é 'flores'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! As palavras terminadas em 'r' fazem o plural acrescentando '-es'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O plural de 'cão' é ___",
                        options = listOf("cães", "cãos", "cãozinhos"),
                        correctAnswer = "cães",
                        explanation = "Cão no plural diz-se cães."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras está no Plural?",
                        options = listOf("📚 livros", "✏️ lápis", "🎒 mochila"),
                        correctAnswer = "📚 livros",
                        explanation = "'livros' refere-se a mais do que um livro! 📚"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o feminino de 'leão'?",
                        options = listOf("leoa", "leoazinha", "tigresa"),
                        correctAnswer = "leoa",
                        explanation = "O feminino de leão é leoa!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'maçãs' está no singular porque é apenas uma fruta.",
                        correctAnswer = "false",
                        explanation = "Falso! 'maçãs' termina em 's' e indica várias maçãs (plural)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o plural de 'jardim'?",
                        options = listOf("jardins", "jardimes", "jardis"),
                        correctAnswer = "jardins",
                        explanation = "As palavras terminadas em 'm' mudam para 'ns' no plural."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o Singular ao seu Plural correto:",
                        pairs = mapOf(
                            "farol" to "faróis",
                            "pão" to "pães",
                            "animal" to "animais",
                            "papel" to "papéis"
                        ),
                        explanation = "Excelente domínio da formação do plural!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f4",
                numero = 4,
                title = "Separação Silábica e Número de Sílabas",
                description = "Monossílabos, dissílabos, trissílabos e polissílabos com palmas!",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👏",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas sílabas tem a palavra 'BO-NE-CA'?",
                        options = listOf("2 sílabas", "3 sílabas (Trissílabo)", "4 sílabas"),
                        correctAnswer = "3 sílabas (Trissílabo)",
                        explanation = "Batendo palmas: bo-ne-ca (3 palmas = trissílabo)!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'sol' tem apenas 1 sílaba, por isso é um Monossílabo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Diz-se numa só emissão de voz."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A palavra 'borboleta' tem 4 sílabas (bor-bo-le-ta), logo é um ___",
                        options = listOf("Polissílabo", "Dissílabo", "Monossílabo"),
                        correctAnswer = "Polissílabo",
                        explanation = "Palavras com 4 ou mais sílabas chamam-se polissílabos."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras é um Dissílabo (2 sílabas)?",
                        options = listOf("🐱 ga-to", "☀️ sol", "🍫 cho-co-la-te"),
                        correctAnswer = "🐱 ga-to",
                        explanation = "'Gato' divide-se em ga-to (2 sílabas = dissílabo)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se separa corretamente a palavra 'passarinho' com 'ss'?",
                        options = listOf("pas-sa-ri-nho", "pa-ssa-ri-nho", "pass-ari-nho"),
                        correctAnswer = "pas-sa-ri-nho",
                        explanation = "Os dois 's' separam-se sempre: um fica numa sílaba e o outro na seguinte!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na separação silábica, os dois 'rr' de 'carro' ficam na mesma sílaba (ca-rro).",
                        correctAnswer = "false",
                        explanation = "Falso! O 'rr' separa-se sempre: car-ro."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A palavra 'pé' é classificada como:",
                        options = listOf("Monossílabo", "Trissílabo", "Polissílabo"),
                        correctAnswer = "Monossílabo",
                        explanation = "Tem apenas uma sílaba."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica quanto ao número de sílabas:",
                        pairs = mapOf(
                            "pão" to "Monossílabo (1)",
                            "casa" to "Dissílabo (2)",
                            "música" to "Trissílabo (3)",
                            "computador" to "Polissílabo (4+)"
                        ),
                        explanation = "Perfeita classificação silábica!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f5",
                numero = 5,
                title = "Tipos de Frase e Pontuação",
                description = "Frase declarativa, interrogativa, exclamativa e os sinais de pontuação.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "❓",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que sinal de pontuação usamos no final de uma pergunta?",
                        options = listOf("Ponto de interrogação (?)", "Ponto final (.)", "Ponto de exclamação (!)"),
                        correctAnswer = "Ponto de interrogação (?)",
                        explanation = "O ponto de interrogação (?) usa-se em perguntas!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A frase 'Que lindo dia de sol!' é uma frase exclamativa.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Exprime admiração e termina com ponto de exclamação (!)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A frase 'Eu gosto de ler livros.' termina com um ponto ___",
                        options = listOf("final", "de interrogação", "vírgula"),
                        correctAnswer = "final",
                        explanation = "É uma frase declarativa afirmativa, termina com ponto final (.)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frases é uma frase interrogativa?",
                        options = listOf("❓ Onde está a minha bola?", "⚽ Eu jogo futebol.", "🎉 Que golo fantástico!"),
                        correctAnswer = "❓ Onde está a minha bola?",
                        explanation = "Faz uma pergunta direta usando o ponto de interrogação ❓"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Para que serve a vírgula (,) numa frase?",
                        options = listOf("Fazer uma pequena pausa ou separar itens", "Terminar a história", "Gritar bem alto"),
                        correctAnswer = "Fazer uma pequena pausa ou separar itens",
                        explanation = "A vírgula indica uma pausa breve na leitura e separa elementos de uma lista."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma frase começa sempre com letra maiúscula.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A primeira letra de qualquer frase é sempre maiúscula."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que sinal usamos quando uma personagem vai começar a falar num diálogo?",
                        options = listOf("Travessão (-)", "Ponto e vírgula (;)", "Reticências (...)"),
                        correctAnswer = "Travessão (-)",
                        explanation = "O travessão (-) introduz a fala de uma personagem!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a frase ao seu tipo:",
                        pairs = mapOf(
                            "Como te chamas?" to "Frase Interrogativa",
                            "Hoje comi sopa." to "Frase Declarativa",
                            "Cuidado com o cão!" to "Frase Exclamativa",
                            "Não quero sair." to "Frase Negativa"
                        ),
                        explanation = "Muito bem! Identificaste todos os tipos de frases."
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f6",
                numero = 6,
                title = "Famílias de Palavras",
                description = "Descobre como as palavras 'filhas' nascem a partir de uma palavra primitiva.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌳",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras NÃO pertence à família de 'mar'?",
                        options = listOf("marujo", "marisco", "martelo"),
                        correctAnswer = "martelo",
                        explanation = "Martelo vem de ferramenta; marujo e marisco vêm da palavra 'mar'!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As palavras 'florista', 'floreira' e 'florecer' pertencem à família da palavra 'flor'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Partilham o mesmo radical 'flor-'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A pessoa que faz pão na padaria chama-se ___ (família de pão).",
                        options = listOf("padeiro", "pescador", "pastor"),
                        correctAnswer = "padeiro",
                        explanation = "Pão -> padaria -> padeiro pertencem à mesma família!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas opções é da família de 'dente'?",
                        options = listOf("🦷 Dentista", "👣 Sapato", "🧤 Luva"),
                        correctAnswer = "🦷 Dentista",
                        explanation = "Dentista, dentição e dentífrico vêm da palavra dente! 🦷"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a palavra primitiva da família: 'sapateiro, sapataria, sapatilha'?",
                        options = listOf("sapato", "salto", "sopa"),
                        correctAnswer = "sapato",
                        explanation = "'Sapato' é a palavra mãe (primitiva) que deu origem às outras."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'leiteiro' pertence à família da palavra 'livro'.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Leiteiro' pertence à família de 'leite'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Completa a família de 'pedra': pedra, pedreira, ___",
                        options = listOf("pedregulho", "madeira", "areia"),
                        correctAnswer = "pedregulho",
                        explanation = "Pedregulho é uma pedra grande!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga a palavra à sua família correta:",
                        pairs = mapOf(
                            "livraria" to "livro",
                            "peixeiro" to "peixe",
                            "solar" to "sol",
                            "geladeira" to "gelo"
                        ),
                        explanation = "Excelente associação das famílias de palavras!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f7",
                numero = 7,
                title = "Verbos e Tempos Verbais",
                description = "Identifica verbos de ação e descobre o Presente (hoje), Passado (ontem) e Futuro (amanhã).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🏃",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras indica uma AÇÃO (é um verbo)?",
                        options = listOf("correr", "caneta", "amarelo"),
                        correctAnswer = "correr",
                        explanation = "Correr é uma ação que podemos praticar, logo é um verbo!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'Ontem eu brinquei no parque', o verbo está no tempo Passado.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Brinquei' refere-se a uma ação que já aconteceu."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa no Presente: Hoje o Zé ___ uma história divertida.",
                        options = listOf("lê", "leu", "lerá"),
                        correctAnswer = "lê",
                        explanation = "'Hoje o Zé lê' expressa uma ação que acontece agora (presente)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Em que tempo verbal está a frase: 'Amanhã nós iremos ao zoológico 🦁'?",
                        options = listOf("🔮 Futuro", "⏪ Passado", "⏰ Presente"),
                        correctAnswer = "🔮 Futuro",
                        explanation = "'Iremos' acontecerá amanhã, logo está no tempo Futuro! 🔮"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o verbo na frase 'Os pássaros cantam no jardim'?",
                        options = listOf("cantam", "pássaros", "jardim"),
                        correctAnswer = "cantam",
                        explanation = "'Cantam' é a ação realizada pelos pássaros."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'mesa' é um verbo porque tem 4 pernas.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Mesa' é um nome comum de objeto, não é uma ação."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como fica o verbo 'comer' no passado para 'Eu': 'Ontem eu ___ uma maçã'?",
                        options = listOf("comi", "como", "comerei"),
                        correctAnswer = "comi",
                        explanation = "Ontem eu comi!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a frase ao seu tempo verbal:",
                        pairs = mapOf(
                            "Hoje eu estudo." to "Presente (Hoje)",
                            "Ontem eu nadei." to "Passado (Ontem)",
                            "Amanhã eu viajarei." to "Futuro (Amanhã)",
                            "Agora nós cantamos." to "Presente (Hoje)"
                        ),
                        explanation = "Excelente identificação dos tempos verbais!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f8",
                numero = 8,
                title = "Adjetivos Qualificativos",
                description = "Descobre palavras que atribuem qualidades e características aos nomes.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✨",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na frase 'O cãozinho é muito brincalhão', qual é o adjetivo?",
                        options = listOf("brincalhão", "cãozinho", "muito"),
                        correctAnswer = "brincalhão",
                        explanation = "'Brincalhão' dá uma característica ao cãozinho, logo é um adjetivo!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os adjetivos concordam em género e número com o nome (ex: menino alto / meninas altas).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se o nome for feminino e plural, o adjetivo também fica no feminino e plural."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A maçã vermelha é doce e ___ (adjetivo).",
                        options = listOf("saborosa", "comer", "árvore"),
                        correctAnswer = "saborosa",
                        explanation = "'Saborosa' é um adjetivo que qualifica a maçã."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras descreve uma característica da tartaruga 🐢?",
                        options = listOf("🐢 Lenta", "🐢 Correr", "🐢 Casco"),
                        correctAnswer = "🐢 Lenta",
                        explanation = "'Lenta' é o adjetivo que caracteriza a velocidade da tartaruga!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o adjetivo na frase 'A camisola azul está limpa'?",
                        options = listOf("azul e limpa", "camisola", "está"),
                        correctAnswer = "azul e limpa",
                        explanation = "Tanto 'azul' (cor) como 'limpa' (estado) são adjetivos!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'correr' é um adjetivo bonito.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Correr' é um verbo (ação), não um adjetivo."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Escolhe o adjetivo que melhor qualifica a palavra 'palhaço':",
                        options = listOf("engraçado", "circo", "sapato"),
                        correctAnswer = "engraçado",
                        explanation = "'Engraçado' é uma qualidade divertida do palhaço!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o nome ao seu adjetivo correspondente:",
                        pairs = mapOf(
                            "noite" to "estrelada",
                            "sorvete" to "gelado",
                            "leão" to "feroz",
                            "sol" to "brilhante"
                        ),
                        explanation = "Muito bem! Atribuíste os adjetivos com grande precisão."
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f9",
                numero = 9,
                title = "Ortografia: C/Ç, G/GU, CH, LH, NH",
                description = "Domina os sons especiais da língua portuguesa e escreve sem erros.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🔤",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Nunca se usa cedilha (ç) no início de palavras nem antes de quais vogais?",
                        options = listOf("e / i", "a / o", "u / a"),
                        correctAnswer = "e / i",
                        explanation = "Antes de 'e' e 'i' o 'c' já tem som de 's' (ex: cinema, cedo), nunca levando cedilha!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'fogueira' escreve-se com 'gu' para manter o som 'g'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Antes de 'e' e 'i' usamos 'gu' para soar como em 'gato'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra: coe___o (animal de orelhas compridas).",
                        options = listOf("lh", "nh", "ch"),
                        correctAnswer = "lh",
                        explanation = "Escreve-se coelho com 'lh'!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras está escrita de forma 100% CORRETA?",
                        options = listOf("🍫 Chocolate", "🍫 Xocolate", "🍫 Cocolate"),
                        correctAnswer = "🍫 Chocolate",
                        explanation = "'Chocolate' escreve-se com 'ch'! 🍫"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se escreve o animal pequenino que produz mel?",
                        options = listOf("abelha", "abeilha", "abexa"),
                        correctAnswer = "abelha",
                        explanation = "Escreve-se 'abelha' com 'lh'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'coração' escreve-se com 'ç' (cedilha).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Antes de 'ã' e 'o' para ter som de 's' usa-se 'ç'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Completa a palavra: gali___a",
                        options = listOf("nh", "lh", "ch"),
                        correctAnswer = "nh",
                        explanation = "Escreve-se galinha com 'nh'!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Completa com o dígrafo correto:",
                        pairs = mapOf(
                            "cha___eu (cobertura de cabeça)" to "p (chapéu)",
                            "ni___o (onde os pássaros põem ovos)" to "nh (ninho)",
                            "fo___a (da árvore)" to "lh (folha)",
                            "gui___arra (instrumento de cordas)" to "t (guitarra)"
                        ),
                        explanation = "Excelente domínio da ortografia dos dígrafos!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f10",
                numero = 10,
                title = "Ordem Alfabética e o Dicionário",
                description = "Organiza palavras pelo alfabeto e aprende a pesquisar no dicionário.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📖",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a terceira letra do alfabeto português?",
                        options = listOf("C", "B", "D"),
                        correctAnswer = "C",
                        explanation = "O alfabeto começa por: A, B, C... logo a terceira letra é o C!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e2",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as seguintes palavras por Ordem Alfabética:",
                        options = listOf("Banana", "Amora", "Cereja"),
                        correctAnswers = listOf("Amora", "Banana", "Cereja"),
                        correctAnswer = "Amora-Banana-Cereja",
                        explanation = "A vem antes de B, e B antes de C: Amora, Banana, Cereja!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se duas palavras começarem pela mesma letra, olhamos para a segunda letra para desempatar.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Por exemplo, 'Barco' vem antes de 'Bolo' porque 'a' vem antes de 'o'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f2_e4",
                        type = ExerciseType.COMPLETING,
                        question = "No dicionário, a palavra 'Dedo' surge ___ da palavra 'Fogo'.",
                        options = listOf("antes", "depois", "no mesmo sítio"),
                        correctAnswer = "antes",
                        explanation = "D vem antes de F no alfabeto."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras vem em PRIMEIRO LUGAR no dicionário?",
                        options = listOf("🥑 Abacate", "🍇 Uva", "🍉 Melancia"),
                        correctAnswer = "🥑 Abacate",
                        explanation = "'Abacate' começa com 'A', a primeira letra do alfabeto! 🥑"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O alfabeto português completo é composto por 26 letras.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Inclui as letras K, W e Y."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os nomes dos amigos por ordem alfabética:",
                        options = listOf("David", "Ana", "Carlos"),
                        correctAnswers = listOf("Ana", "Carlos", "David"),
                        correctAnswer = "Ana-Carlos-David",
                        explanation = "A (Ana) -> C (Carlos) -> D (David)."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Descobre qual letra vem logo a seguir no alfabeto:",
                        pairs = mapOf(
                            "A" to "B",
                            "M" to "N",
                            "S" to "T",
                            "X" to "Y"
                        ),
                        explanation = "Conheces o alfabeto de uma ponta à outra!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f11",
                numero = 11,
                title = "Textos Poéticos e Rimas",
                description = "Versos, estrofes e rimas sonoras em poesias infantis encantadoras.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🎭",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras rima com 'coração'?",
                        options = listOf("canção", "cadeira", "sapato"),
                        correctAnswer = "canção",
                        explanation = "Coração e canção terminam com o mesmo som '-ão', rimando na perfeição!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Cada linha de um poema chama-se um Verso.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Cada linha é um verso e um grupo de versos forma uma estrofe."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O macaco comilão comeu uma ___ (rima com comilão).",
                        options = listOf("romã", "banana", "refeição"),
                        correctAnswer = "refeição",
                        explanation = "Comilão rima com refeição (-ão)!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas palavras rima com 'Gato'?",
                        options = listOf("🦆 Pato", "🐶 Cão", "🐭 Ratoeira"),
                        correctAnswer = "🦆 Pato",
                        explanation = "Gato rima com Pato (-ato)! 🦆"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama um conjunto de versos separados por um espaço num poema?",
                        options = listOf("Estrofe", "Parágrafo", "Capítulo"),
                        correctAnswer = "Estrofe",
                        explanation = "Um grupo de versos é uma estrofe."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os trava-línguas são frases com palavras difíceis e sons parecidos para treinar a fala.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Exemplo: 'O rato roeu a rolha da garrafa do rei de Roma.'"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Descobre a palavra que rima com 'janela':",
                        options = listOf("amarela", "azul", "porta"),
                        correctAnswer = "amarela",
                        explanation = "Janela rima com amarela (-ela)!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga as palavras que rimam entre si:",
                        pairs = mapOf(
                            "jardim" to "pudim",
                            "castelo" to "amarelo",
                            "foguete" to "sorvete",
                            "balão" to "sabão"
                        ),
                        explanation = "Excelente ouvido musical para as rimas poéticas!"
                    )
                )
            ),
            Worksheet(
                id = "pt_2_f12",
                numero = 12,
                title = "Compreensão de Texto e Interpretação",
                description = "Lê pequenas histórias do Zé Traquina e responde a perguntas de interpretação.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_2_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Texto: 'O Zé encontrou um cachorrinho abandonado no jardim e deu-lhe água fresca.' Onde encontrou o Zé o animal?",
                        options = listOf("No jardim", "Na praia", "Dentro da escola"),
                        correctAnswer = "No jardim",
                        explanation = "O texto diz claramente: 'O Zé encontrou um cachorrinho no jardim'."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Zé deu água fresca ao cachorrinho.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Está escrito no pequeno texto."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Texto: 'A Rita vestiu a camisola amarela para ir ao parque.' A Rita vestiu a camisola ___",
                        options = listOf("amarela", "azul", "verde"),
                        correctAnswer = "amarela",
                        explanation = "A camisola era de cor amarela."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a personagem principal de uma história?",
                        options = listOf("Quem vive as maiores aventuras na narrativa", "Quem só aparece na última página", "O autor do livro"),
                        correctAnswer = "Quem vive as maiores aventuras na narrativa",
                        explanation = "A personagem principal (protagonista) é o centro da ação da história."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O título de um texto ajuda o leitor a saber sobre o que vai ler.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O título resume o tema principal da narrativa."
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e6",
                        type = ExerciseType.ORDERING,
                        question = "Ordena a estrutura de uma narrativa:",
                        options = listOf("Início / Introdução", "Desenvolvimento / Aventura", "Fim / Conclusão"),
                        correctAnswers = listOf("Início / Introdução", "Desenvolvimento / Aventura", "Fim / Conclusão"),
                        correctAnswer = "Início / Introdução-Desenvolvimento / Aventura-Fim / Conclusão",
                        explanation = "Toda a boa história tem introdução, aventura e um final feliz!"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se o texto diz que o esquilo guardou nozes para o inverno, o que come ele?",
                        options = listOf("🌰 Nozes e frutos secos", "🥩 Carne", "🍕 Pizza"),
                        correctAnswer = "🌰 Nozes e frutos secos",
                        explanation = "Os esquilos adoram nozes e bolotas! 🌰"
                    ),
                    WorksheetExercise(
                        id = "pt_2_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as partes de uma história à sua função:",
                        pairs = mapOf(
                            "Título" to "Nome da história",
                            "Personagens" to "Quem participa na ação",
                            "Espaço" to "Onde tudo acontece",
                            "Moral" to "O ensinamento final"
                        ),
                        explanation = "Parabéns, Campeão da Língua Portuguesa do 2.º Ano!"
                    )
                )
            )
        )
    )

    val matematica = WorksheetDiscipline(
        id = "mat_2",
        title = "Matemática",
        emoji = "🧮",
        color = Color(0xFF10B981),
        worksheets = listOf(
            Worksheet(
                id = "mat_2_f1",
                numero = 1,
                title = "Números até 100 e a Tabela dos 100",
                description = "Dezenas e unidades, leitura, escrita e contagens progressivas até 100.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "💯",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas dezenas completas existem no número 70?",
                        options = listOf("7 dezenas", "70 dezenas", "0 dezenas"),
                        correctAnswer = "7 dezenas",
                        explanation = "70 é formado por 7 grupos de 10 (7 dezenas e 0 unidades)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 84 é composto por 8 dezenas e 4 unidades.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 80 + 4 = 84."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a contagem de 10 em 10: 50, 60, 70, __, 90, 100",
                        options = listOf("80", "75", "85"),
                        correctAnswer = "80",
                        explanation = "De 10 em 10: depois de 70 vem o 80!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes números é o número CINQUENTA E SEIS por extenso?",
                        options = listOf("56", "65", "506"),
                        correctAnswer = "56",
                        explanation = "56 = cinquenta e seis."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o número que vem imediatamente a seguir ao 99?",
                        options = listOf("100", "98", "101"),
                        correctAnswer = "100",
                        explanation = "Depois do 99 alcançamos o 100 (uma centena)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 49 é maior do que 94.",
                        correctAnswer = "false",
                        explanation = "Falso! 49 tem apenas 4 dezenas, enquanto 94 tem 9 dezenas."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os números do menor para o maior:",
                        options = listOf("28", "64", "91"),
                        correctAnswers = listOf("28", "64", "91"),
                        correctAnswer = "28-64-91",
                        explanation = "28 é menor que 64, e 64 é menor que 91!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Decompõe os números em Dezenas e Unidades:",
                        pairs = mapOf(
                            "37" to "3 dezenas e 7 unidades",
                            "52" to "5 dezenas e 2 unidades",
                            "90" to "9 dezenas e 0 unidades",
                            "19" to "1 dezena e 9 unidades"
                        ),
                        explanation = "Excelente decomposição de números até 100!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f2",
                numero = 2,
                title = "Adição e Subtração até 100",
                description = "Cálculo mental, estratégias de soma e subtração com e sem transporte.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "➕",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 45 + 20?",
                        options = listOf("65", "55", "75"),
                        correctAnswer = "65",
                        explanation = "45 mais 2 dezenas (20) dá 65!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "80 - 30 é igual a 50.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 8 dezenas menos 3 dezenas são 5 dezenas (50)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa o cálculo: 34 + ___ = 40",
                        options = listOf("6", "7", "5"),
                        correctAnswer = "6",
                        explanation = "34 + 6 = 40 (o amigo do 4 para fazer a dezena é o 6)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Zé tinha 50 cartas de jogo e deu 15 ao amigo. Com quantas ficou?",
                        options = listOf("35", "40", "25"),
                        correctAnswer = "35",
                        explanation = "50 - 15 = 35 cartas guardadas."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 25 + 25?",
                        options = listOf("🪙 50", "🪙 40", "🪙 60"),
                        correctAnswer = "🪙 50",
                        explanation = "25 + 25 = 50 (meia centena)! 🪙"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A soma de 60 + 40 perfaz exatamente 100 (uma centena).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 6 dezenas + 4 dezenas = 10 dezenas = 100."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 78 - 8?",
                        options = listOf("70", "68", "80"),
                        correctAnswer = "70",
                        explanation = "Retirando as 8 unidades a 78, sobram 70 certinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada conta ao seu resultado exato:",
                        pairs = mapOf(
                            "50 + 25" to "75",
                            "100 - 10" to "90",
                            "33 + 33" to "66",
                            "48 - 20" to "28"
                        ),
                        explanation = "Excelente cálculo mental de somas e subtrações!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f3",
                numero = 3,
                title = "A Multiplicação e as Tabuadas do 2, 5 e 10",
                description = "Compreende a multiplicação como adição de parcelas iguais e treina as tabuadas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "✖️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A conta 2 + 2 + 2 + 2 é o mesmo que fazer...",
                        options = listOf("4 x 2", "2 + 4", "4 x 4"),
                        correctAnswer = "4 x 2",
                        explanation = "São 4 grupos de 2, ou seja, 4 x 2 = 8!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na tabuada do 5, todos os resultados terminam em 0 ou 5.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 5, 10, 15, 20, 25, 30... terminam sempre em 0 ou 5."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Quanto é 5 x 6 = ___?",
                        options = listOf("30", "25", "35"),
                        correctAnswer = "30",
                        explanation = "5 vezes 6 é igual a 30."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quanto é 10 x 4?",
                        options = listOf("🔟 40", "🔟 14", "🔟 400"),
                        correctAnswer = "🔟 40",
                        explanation = "10 x 4 = 40 (4 dezenas)! 🔟"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 2 x 9?",
                        options = listOf("18", "16", "20"),
                        correctAnswer = "18",
                        explanation = "2 x 9 = 9 + 9 = 18!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Qualquer número multiplicado por 1 dá como resultado esse mesmo número (ex: 7 x 1 = 7).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O 1 é o elemento neutro da multiplicação."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Zé comprou 3 caixas de lápis. Cada caixa tem 5 lápis. Quantos lápis tem no total?",
                        options = listOf("15", "8", "12"),
                        correctAnswer = "15",
                        explanation = "3 x 5 = 15 lápis de cor!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada multiplicação ao seu produto:",
                        pairs = mapOf(
                            "2 x 7" to "14",
                            "5 x 5" to "25",
                            "10 x 8" to "80",
                            "2 x 10" to "20"
                        ),
                        explanation = "Muito bem! Estás a dominar as primeiras tabuadas com distinção."
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f4",
                numero = 4,
                title = "Dobro, Triplo e Metade",
                description = "Multiplica por 2 para o dobro, por 3 para o triplo e divide por 2 para a metade.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⚖️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o DOBRO do número 6?",
                        options = listOf("12", "18", "3"),
                        correctAnswer = "12",
                        explanation = "O dobro calcula-se multiplicando por 2: 6 x 2 = 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A METADE de 20 é 10.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se partilharmos 20 ao meio em duas partes iguais, cada uma fica com 10."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O TRIPLO de 4 é ___ (4 x 3).",
                        options = listOf("12", "7", "16"),
                        correctAnswer = "12",
                        explanation = "O triplo é três vezes o número: 4 x 3 = 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "A Ana tem 8 morangos 🍓 e comeu metade. Quantos comeu?",
                        options = listOf("🍓 4 morangos", "🍓 2 morangos", "🍓 6 morangos"),
                        correctAnswer = "🍓 4 morangos",
                        explanation = "A metade de 8 é 4 (4 + 4 = 8)! 🍓"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o dobro de 15?",
                        options = listOf("30", "25", "35"),
                        correctAnswer = "30",
                        explanation = "15 + 15 = 30 (dobro de 15)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O triplo de 5 é 15 (5 + 5 + 5 = 15).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Multiplicamos 5 por 3."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A metade de 14 é:",
                        options = listOf("7", "8", "6"),
                        correctAnswer = "7",
                        explanation = "7 + 7 = 14, por isso a metade é 7."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga o número ao seu Dobro ou Metade:",
                        pairs = mapOf(
                            "Dobro de 8" to "16",
                            "Metade de 16" to "8",
                            "Triplo de 3" to "9",
                            "Metade de 50" to "25"
                        ),
                        explanation = "Excelente agilidade mental com dobro, triplo e metade!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f5",
                numero = 5,
                title = "Geometria, Polígonos e Simetria",
                description = "Lados, vértices, triângulos, quadriláteros e eixos de simetria.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📐",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam os 'bicos' ou 'cantos' onde dois lados se encontram num polígono?",
                        options = listOf("Vértices", "Retas", "Centros"),
                        correctAnswer = "Vértices",
                        explanation = "O ponto de encontro de dois lados chama-se vértice!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um triângulo tem sempre 3 lados e 3 vértices.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 'Tri' significa três."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um retângulo tem 4 lados e ___ vértices.",
                        options = listOf("4", "3", "6"),
                        correctAnswer = "4",
                        explanation = "Qualquer quadrilátero tem 4 lados e 4 vértices."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas figuras tem um Eixo de Simetria perfeito?",
                        options = listOf("🦋 Borboleta de asas abertas", "🍃 Folha torta", "🪨 Pedra irregular"),
                        correctAnswer = "🦋 Borboleta de asas abertas",
                        explanation = "A borboleta tem duas metades exatamente espelhadas e simétricas! 🦋"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama um polígono com 5 lados?",
                        options = listOf("Pentágono", "Hexágono", "Quadrado"),
                        correctAnswer = "Pentágono",
                        explanation = "Um polígono de 5 lados chama-se pentágono."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O círculo é um polígono porque tem lados retos.",
                        correctAnswer = "false",
                        explanation = "Falso! O círculo é uma linha curva fechada e não tem lados retos nem vértices."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos lados tem um Hexágono?",
                        options = listOf("6 lados", "5 lados", "8 lados"),
                        correctAnswer = "6 lados",
                        explanation = "O hexágono tem 6 lados (como as células de uma colmeia de abelhas)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a figura geométrica ao número de lados:",
                        pairs = mapOf(
                            "Triângulo" to "3 lados",
                            "Quadrado" to "4 lados iguais",
                            "Pentágono" to "5 lados",
                            "Hexágono" to "6 lados"
                        ),
                        explanation = "Perfeito! Conheces todos os polígonos e as suas propriedades."
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f6",
                numero = 6,
                title = "Sólidos Geométricos: Prismas e Pirâmides",
                description = "Faces, arestas, vértices e a diferença entre poliedros e corpos redondos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas faces quadradas tem um CUBO?",
                        options = listOf("6 faces", "4 faces", "8 faces"),
                        correctAnswer = "6 faces",
                        explanation = "O cubo tem 6 faces quadradas e todas iguais (como um dado de jogo)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A pirâmide tem uma base e as faces laterais são triangulares que se juntam no topo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Todas as faces laterais de uma pirâmide são triângulos."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A linha onde duas faces de um sólido se encontram chama-se ___",
                        options = listOf("Aresta", "Vértice", "Base"),
                        correctAnswer = "Aresta",
                        explanation = "O encontro de duas faces forma uma aresta."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes sólidos NÃO é um poliedro porque tem superfície curva?",
                        options = listOf("⚽ Esfera", "📦 Paralelepípedo", "🧊 Cubo"),
                        correctAnswer = "⚽ Esfera",
                        explanation = "A esfera é um corpo redondo não poliedro! ⚽"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos vértices tem um Cubo?",
                        options = listOf("8 vértices", "6 vértices", "12 vértices"),
                        correctAnswer = "8 vértices",
                        explanation = "O cubo tem 8 cantos (vértices), 6 faces e 12 arestas!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O cilindro tem duas bases planas circulares e uma superfície lateral curva.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Parece uma lata de refrigerante."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Um pacote de leite ou uma caixa de sapatos tem a forma de...",
                        options = listOf("Paralelepípedo / Prisma retangular", "Esfera", "Cone"),
                        correctAnswer = "Paralelepípedo / Prisma retangular",
                        explanation = "Tem faces retangulares opostas e paralelas."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o sólido geométrico à sua descrição:",
                        pairs = mapOf(
                            "Cubo" to "6 faces quadradas iguais",
                            "Esfera" to "Superfície totalmente curva",
                            "Cone" to "1 base circular e 1 vértice",
                            "Cilindro" to "2 bases circulares planas"
                        ),
                        explanation = "Fantástico! Distinguiste perfeitamente todos os sólidos geométricos."
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f7",
                numero = 7,
                title = "Medição de Comprimentos: Metro e Centímetro",
                description = "Usa a régua e a fita métrica: 1 metro = 100 centímetros (1 m = 100 cm).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📏",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos centímetros (cm) são precisos para fazer 1 Metro (m) inteiro?",
                        options = listOf("100 cm", "10 cm", "1000 cm"),
                        correctAnswer = "100 cm",
                        explanation = "1 metro é igual a exatamente 100 centímetros (1 m = 100 cm)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Usamos a régua graduada em centímetros para medir objetos pequenos como o lápis ou a borracha.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A régua escolar mede comprimentos em centímetros e milímetros."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Meio metro é igual a ___ centímetros.",
                        options = listOf("50", "25", "10"),
                        correctAnswer = "50",
                        explanation = "A metade de 100 cm é 50 cm!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes objetos mede habitualmente mais de 1 Metro de comprimento?",
                        options = listOf("🚗 Automóvel", "✏️ Lápis", "🪙 Moeda"),
                        correctAnswer = "🚗 Automóvel",
                        explanation = "Um automóvel mede cerca de 4 metros de comprimento! 🚗"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o símbolo abreviado do Centímetro?",
                        options = listOf("cm", "m", "kg"),
                        correctAnswer = "cm",
                        explanation = "'cm' é o símbolo oficial do centímetro."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Para medir o comprimento de uma linha com a régua devemos começar a contar a partir do número 0.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Alinhamos a ponta do objeto sempre com o traço do zero (0)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se uma fita tem 80 cm e cortamos 30 cm, com quantos centímetros ficamos?",
                        options = listOf("50 cm", "40 cm", "60 cm"),
                        correctAnswer = "50 cm",
                        explanation = "80 cm - 30 cm = 50 cm (meio metro)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a medida mais adequada ao objeto:",
                        pairs = mapOf(
                            "Comprimento de uma borracha" to "4 cm",
                            "Altura de uma porta" to "2 metros",
                            "Comprimento de uma sala de aula" to "8 metros",
                            "Comprimento de uma formiga" to "5 milímetros"
                        ),
                        explanation = "Excelente noção das grandezas e medidas de comprimento!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f8",
                numero = 8,
                title = "Massa (Quilograma) e Capacidade (Litro)",
                description = "Pesar com a balança (kg e g) e medir líquidos com recipientes graduados (l e cl).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⚖️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a unidade principal que usamos para pesar o peso (massa) do nosso corpo?",
                        options = listOf("Quilograma (kg)", "Litro (l)", "Metro (m)"),
                        correctAnswer = "Quilograma (kg)",
                        explanation = "Pesamo-nos na balança em quilogramas (kg)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Litro (l) é a unidade que usamos para medir líquidos como a água, leite ou sumo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A capacidade dos recipientes líquidos mede-se em litros."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Dois pacotes de meio quilo juntos pesam ___ quilo.",
                        options = listOf("1", "2", "3"),
                        correctAnswer = "1",
                        explanation = "Meio quilo (500g) + Meio quilo (500g) = 1 Quilograma inteiro (1 kg)."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que instrumento usamos para pesar a fruta e os legumes no supermercado?",
                        options = listOf("⚖️ Balança", "⏰ Relógio", "📏 Régua"),
                        correctAnswer = "⚖️ Balança",
                        explanation = "A balança mede a massa em quilogramas e gramas! ⚖️"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos copos de 25 cl são precisos para encher uma garrafa de 1 Litro (100 cl)?",
                        options = listOf("4 copos", "2 copos", "10 copos"),
                        correctAnswer = "4 copos",
                        explanation = "25 + 25 + 25 + 25 = 100 cl = 1 Litro!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um elefante pesa muito mais do que 1000 quilogramas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Um elefante adulto pode pesar entre 3000 kg e 6000 kg."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual pesa mais: 1 kg de ferro ou 1 kg de algodão?",
                        options = listOf("Pesam exatamente o mesmo (1 kg)", "1 kg de ferro", "1 kg de algodão"),
                        correctAnswer = "Pesam exatamente o mesmo (1 kg)",
                        explanation = "Ambos pesam exatamente 1 kg! A diferença é apenas o volume que ocupam."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica em Massa (peso) ou Capacidade (líquido):",
                        pairs = mapOf(
                            "Saco de arroz" to "Massa (kg)",
                            "Garrafa de água" to "Capacidade (l)",
                            "Pacote de farinha" to "Massa (kg)",
                            "Balde de sumo" to "Capacidade (l)"
                        ),
                        explanation = "Muito bem! Distinguiste massa de capacidade."
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f9",
                numero = 9,
                title = "Horas, Meias Horas e Quartos de Hora",
                description = "Lê as horas e minutos nos relógios de ponteiros e digitais.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⏰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quando o ponteiro grande dos minutos aponta para o 6, temos...",
                        options = listOf("Meia hora (30 minutos)", "Hora em ponto", "Um quarto de hora"),
                        correctAnswer = "Meia hora (30 minutos)",
                        explanation = "O número 6 marca 30 minutos (meia hora) de volta no mostrador!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O relógio digital que marca '08:30' indica 'oito horas e meia'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 30 minutos é a metade de uma hora de 60 minutos."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Um quarto de hora tem ___ minutos.",
                        options = listOf("15", "30", "45"),
                        correctAnswer = "15",
                        explanation = "60 minutos divididos por 4 partes = 15 minutos!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se o ponteiro pequeno está no 4 e o grande no 12, que horas são?",
                        options = listOf("⏰ 4:00 (Quatro horas em ponto)", "⏰ 12:20", "⏰ 6:00"),
                        correctAnswer = "⏰ 4:00 (Quatro horas em ponto)",
                        explanation = "São 4 horas em ponto! ⏰"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos minutos faltam para a hora seguinte se o relógio marca '10:45'?",
                        options = listOf("15 minutos (um quarto de hora)", "30 minutos", "5 minutos"),
                        correctAnswer = "15 minutos (um quarto de hora)",
                        explanation = "60 - 45 = 15 minutos (são quinze para as onze)!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma hora tem 60 minutos e meio dia tem 12 horas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Um dia completo tem 24 horas."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se a aula começa às 09:00 e dura 1 hora, a que horas termina?",
                        options = listOf("10:00", "09:30", "11:00"),
                        correctAnswer = "10:00",
                        explanation = "9 + 1 = 10 horas em ponto!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga a hora escrita ao seu relógio digital:",
                        pairs = mapOf(
                            "Duas e meia" to "02:30",
                            "Nove em ponto" to "09:00",
                            "Seis e um quarto" to "06:15",
                            "Quinze para as cinco" to "04:45"
                        ),
                        explanation = "Excelente leitura das horas analógicas e digitais!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f10",
                numero = 10,
                title = "Dinheiro, Notas e Moedas de Euro",
                description = "Calcula somas com dinheiro, trocos e valor das notas de 5€, 10€, 20€ e 50€.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💶",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas moedas de 2€ precisas para fazer uma nota de 10€?",
                        options = listOf("5 moedas", "2 moedas", "10 moedas"),
                        correctAnswer = "5 moedas",
                        explanation = "5 x 2€ = 10€!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "100 cêntimos equivalem exatamente a 1 Euro (1,00€).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1€ = 100 cêntimos."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Compraste um livro de 7€ e pagaste com 10€. O teu troco é ___ €.",
                        options = listOf("3", "2", "4"),
                        correctAnswer = "3",
                        explanation = "10€ - 7€ = 3€ de troco na caixa!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas notas tem o MENOR valor em dinheiro?",
                        options = listOf("💶 Nota de 5€", "💶 Nota de 20€", "💶 Nota de 50€"),
                        correctAnswer = "💶 Nota de 5€",
                        explanation = "A nota de 5€ é a nota de menor valor do Euro! 💶"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se tiveres 2 notas de 20€ e 1 nota de 10€, quanto dinheiro tens?",
                        options = listOf("50€", "40€", "30€"),
                        correctAnswer = "50€",
                        explanation = "20€ + 20€ + 10€ = 50€ no total!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Duas moedas de 50 cêntimos mais uma moeda de 1€ perfazem 2€.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 50c + 50c = 1€, e 1€ + 1€ = 2€."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A Maria quer comprar uma bola de 12€ e uma boneca de 8€. Quanto precisa no total?",
                        options = listOf("20€", "18€", "22€"),
                        correctAnswer = "20€",
                        explanation = "12€ + 8€ = 20€ certinhos."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Calcula o valor total das quantias:",
                        pairs = mapOf(
                            "4 moedas de 2€" to "8€",
                            "2 notas de 5€" to "10€",
                            "1 nota de 10€ + 1 de 20€" to "30€",
                            "4 moedas de 50 cêntimos" to "2€"
                        ),
                        explanation = "Excelente gestão financeira e cálculo de valores em Euros!"
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f11",
                numero = 11,
                title = "Tabelas, Pictogramas e Gráficos de Barras",
                description = "Organização de dados estatísticos, leitura de gráficos e contagem de votos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Num gráfico de barras sobre frutas preferidas, a barra da Maçã chega ao 14 e da Banana ao 8. Qual fruta teve mais votos?",
                        options = listOf("Maçã", "Banana", "Empataram"),
                        correctAnswer = "Maçã",
                        explanation = "A barra da Maçã é mais alta (14 > 8), logo teve mais votos!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Num pictograma, cada desenho ou símbolo pode representar 1 ou mais elementos (legenda).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Devemos sempre ler a legenda com atenção."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Se num pictograma 🌟 vale 2 votos, então 🌟🌟🌟 valem ___ votos.",
                        options = listOf("6", "3", "5"),
                        correctAnswer = "6",
                        explanation = "3 estrelas x 2 votos cada = 6 votos!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que tipo de gráfico usa colunas retangulares para comparar dados?",
                        options = listOf("📊 Gráfico de barras", "🥧 Tarte de maçã", "🎲 Dado de jogar"),
                        correctAnswer = "📊 Gráfico de barras",
                        explanation = "O gráfico de barras usa colunas para facilitar a comparação visual! 📊"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Numa tabela da turma: 10 alunos têm cão, 8 têm gato e 2 têm peixe. Quantos alunos têm animais?",
                        options = listOf("20 alunos", "18 alunos", "15 alunos"),
                        correctAnswer = "20 alunos",
                        explanation = "10 + 8 + 2 = 20 alunos no total!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O título de uma tabela indica o assunto que foi investigado na pesquisa.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O título resume o tema dos dados recolhidos."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a diferença de votos entre o 1.º lugar (15 votos) e o 2.º lugar (9 votos)?",
                        options = listOf("6 votos", "5 votos", "24 votos"),
                        correctAnswer = "6 votos",
                        explanation = "15 - 9 = 6 votos de diferença!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o termo estatístico ao seu significado:",
                        pairs = mapOf(
                            "Tabela" to "Grelha com linhas e colunas de dados",
                            "Gráfico de barras" to "Representação com colunas coloridas",
                            "Pictograma" to "Gráfico com desenhos e símbolos",
                            "Moda" to "A opção mais votada de todas"
                        ),
                        explanation = "Perfeito! Sabes interpretar dados e tabelas estatísticas com rigor."
                    )
                )
            ),
            Worksheet(
                id = "mat_2_f12",
                numero = 12,
                title = "Grandes Desafios Matemáticos do 2.º Ano",
                description = "Problemas de lógica em vários passos, estimativas e enigmas do Zé.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_2_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Zé tinha 40 berlindes. Ganhou 15 no jogo e deu 5 ao irmão. Com quantos berlindes ficou?",
                        options = listOf("50 berlindes", "45 berlindes", "55 berlindes"),
                        correctAnswer = "50 berlindes",
                        explanation = "40 + 15 = 55; depois 55 - 5 = 50 berlindes!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se 1 caderno custa 2€, com 20€ podes comprar exatamente 10 cadernos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 10 x 2€ = 20€."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Descobre o número misterioso: Tem 7 dezenas e o dobro de 3 unidades. É o ___",
                        options = listOf("76", "73", "67"),
                        correctAnswer = "76",
                        explanation = "7 dezenas = 70. Dobro de 3 = 6. 70 + 6 = 76!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se num autocarro vão 25 pessoas e saem 10 na paragem, quantas continuam viagem?",
                        options = listOf("🚌 15 pessoas", "🚌 20 pessoas", "🚌 35 pessoas"),
                        correctAnswer = "🚌 15 pessoas",
                        explanation = "25 - 10 = 15 pessoas a bordo! 🚌"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a soma dos 3 primeiros números pares (2, 4 e 6)?",
                        options = listOf("12", "10", "14"),
                        correctAnswer = "12",
                        explanation = "2 + 4 + 6 = 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A metade de 100 é 50.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 50 + 50 = 100."
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os passos para resolver um problema com sucesso:",
                        options = listOf("Compreender os dados", "Fazer as contas", "Verificar e redigir a resposta"),
                        correctAnswers = listOf("Compreender os dados", "Fazer as contas", "Verificar e redigir a resposta"),
                        correctAnswer = "Compreender os dados-Fazer as contas-Verificar e redigir a resposta",
                        explanation = "Lemos com atenção, calculamos com rigor e escrevemos a resposta!"
                    ),
                    WorksheetExercise(
                        id = "mat_2_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Desafio das contas rápidas:",
                        pairs = mapOf(
                            "5 x 10" to "50",
                            "45 + 55" to "100",
                            "80 - 15" to "65",
                            "3 x 8" to "24"
                        ),
                        explanation = "Parabéns, Mestre da Matemática do 2.º Ano! Conquistaste o troféu!"
                    )
                )
            )
        )
    )

    val estudoMeio = WorksheetDiscipline(
        id = "em_2",
        title = "Estudo do Meio",
        emoji = "🌍🌱",
        color = Color(0xFFF59E0B),
        worksheets = listOf(
            Worksheet(
                id = "em_2_f1",
                numero = 1,
                title = "O Esqueleto Humano e a Postura",
                description = "Os ossos, articulações, coluna vertebral e como cuidar da postura das costas.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🦴",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o conjunto de todos os ossos do nosso corpo que suporta o nosso organismo?",
                        options = listOf("Esqueleto", "Músculo", "Pele"),
                        correctAnswer = "Esqueleto",
                        explanation = "O esqueleto é a armação óssea que sustenta o nosso corpo e protege os órgãos vitais!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O crânio é uma caixa óssea resistente que protege o cérebro contra pancadas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O crânio protege o nosso cérebro."
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O ponto de união entre dois ossos que nos permite dobrar o braço ou a perna chama-se ___",
                        options = listOf("Articulação", "Músculo", "Tendão"),
                        correctAnswer = "Articulação",
                        explanation = "As articulações (joelho, cotovelo, ombro) dão flexibilidade ao corpo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes alimentos fortalece os ossos graças ao cálcio?",
                        options = listOf("🥛 Leite e laticínios", "🍭 Chupa-chupa", "🍟 Batatas fritas"),
                        correctAnswer = "🥛 Leite e laticínios",
                        explanation = "O leite, iogurte e queijo são ricos em cálcio para ossos fortes! 🥛"
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como deves transportar a tua mochila da escola para proteger a coluna vertebral?",
                        options = listOf("Apoiada nos dois ombros com peso equilibrado", "Num ombro só muito inclinada", "Segurada com um dedo"),
                        correctAnswer = "Apoiada nos dois ombros com peso equilibrado",
                        explanation = "Usar as duas alças bem ajustadas previne dores e desvios na coluna!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As costelas formam uma caixa que protege o coração e os pulmões.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A caixa torácica protege órgãos nobres e delicados."
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a articulação entre a perna e o pé?",
                        options = listOf("Tornozelo", "Cotovelo", "Punho"),
                        correctAnswer = "Tornozelo",
                        explanation = "O tornozelo permite movimentar o pé ao caminhar."
                    ),
                    WorksheetExercise(
                        id = "em_2_f1_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a parte do esqueleto ao órgão que protege:",
                        pairs = mapOf(
                            "Crânio" to "Cérebro",
                            "Costelas / Caixa Torácica" to "Coração e Pulmões",
                            "Coluna Vertebral" to "Medula Espinal",
                            "Pélvis / Bacia" to "Órgãos do abdómen"
                        ),
                        explanation = "Excelente conhecimento da anatomia e esqueleto humano!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f2",
                numero = 2,
                title = "Saúde, Descanso e Sono",
                description = "A importância das horas de sono, vacinas, ar livre e bem-estar físico.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "😴",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas horas deve uma criança da tua idade dormir por noite para acordar bem disposta?",
                        options = listOf("Cerca de 9 a 10 horas", "2 horas", "15 horas"),
                        correctAnswer = "Cerca de 9 a 10 horas",
                        explanation = "Dormir entre 9 a 10 horas recupera a energia do cérebro e ajuda o corpo a crescer!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As vacinas ensinam o nosso corpo a combater vírus e bactérias antes de ficarmos doentes.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Boletim de Vacinas em dia protege-nos a nós e aos nossos amigos."
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Antes de dormir, devemos evitar o uso de ___ para o cérebro descansar melhor.",
                        options = listOf("ecrãs e telemóveis", "livros de histórias", "cama fofa"),
                        correctAnswer = "ecrãs e telemóveis",
                        explanation = "A luz azul dos ecrãs desperta a mente e prejudica o sono profundo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas atividades faz muito bem à saúde e ao fortalecimento do coração?",
                        options = listOf("⚽ Praticar desporto e brincar ao ar livre", "🛋️ Passar o dia inteiro no sofá", "🍬 Comer rebuçados sem parar"),
                        correctAnswer = "⚽ Praticar desporto e brincar ao ar livre",
                        explanation = "O exercício físico estimula os músculos e a circulação! ⚽"
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quando temos febre ou estamos doentes, devemos consultar...",
                        options = listOf("O médico / centro de saúde", "O mecânico", "O jardineiro"),
                        correctAnswer = "O médico / centro de saúde",
                        explanation = "O médico avalia os sintomas e receita o tratamento adequado."
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Apanhar ar puro e sol com moderação ajuda o corpo a produzir vitamina D.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Sol com proteção solar é fonte vital de vitamina D para os ossos."
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que instrumento médico usamos para medir a temperatura e saber se temos febre?",
                        options = listOf("Termómetro", "Barómetro", "Régua"),
                        correctAnswer = "Termómetro",
                        explanation = "O termómetro mede a temperatura corporal em graus Celsius (°C)."
                    ),
                    WorksheetExercise(
                        id = "em_2_f2_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o hábito de saúde ao seu benefício:",
                        pairs = mapOf(
                            "Dormir 9 a 10 horas" to "Descanso e crescimento do corpo",
                            "Beber água pura" to "Hidratação do organismo",
                            "Lavar as mãos antes das refeições" to "Eliminação de micróbios",
                            "Praticar natação ou futebol" to "Fortalecimento muscular"
                        ),
                        explanation = "Excelente compreensão dos pilares da vida saudável!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f3",
                numero = 3,
                title = "Classificação dos Animais: Vertebrados e Invertebrados",
                description = "Mamíferos, Aves, Répteis, Peixes, Anfíbios e animais sem esqueleto interno.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🦁",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam os animais que possuem esqueleto interno e coluna vertebral?",
                        options = listOf("Vertebrados", "Invertebrados", "Plantas"),
                        correctAnswer = "Vertebrados",
                        explanation = "Os animais com ossos e coluna vertebral são animais vertebrados!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os mamíferos nascem da barriga da mãe e mamam leite materno quando são bebés.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ex: cão, gato, baleia, leão e o próprio ser humano."
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O corpo das aves está coberto de ___ que as ajudam a voar e a manter o calor.",
                        options = listOf("penas", "escamas", "pelos"),
                        correctAnswer = "penas",
                        explanation = "As aves têm penas, asas e bico."
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes animais é um ANFÍBIO que vive na água e na terra com pele nua e húmida?",
                        options = listOf("🐸 Rã / Sapo", "🦅 Águia", "🦁 Leão"),
                        correctAnswer = "🐸 Rã / Sapo",
                        explanation = "Os anfíbios têm pele nua e sofrem metamorfose! 🐸"
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destes animais é um animal INVERTEBRADO (não tem ossos nem coluna)?",
                        options = listOf("Caracol", "Cavalo", "Pardal"),
                        correctAnswer = "Caracol",
                        explanation = "O caracol, a minhoca e o polvo são animais invertebrados."
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os peixes respiram debaixo de água através das guelras (brânquias).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! As guelras filtram o oxigénio dissolvido na água."
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os répteis (como o lagarto e a tartaruga) têm o corpo coberto por...",
                        options = listOf("Escamas secas ou carapaça dura", "Penas macias", "Pelos grossos"),
                        correctAnswer = "Escamas secas ou carapaça dura",
                        explanation = "Têm escamas córneas e põem ovos com casca!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f3_e8",
                        type = ExerciseType.MATCHING,
                        question = "Classifica cada animal na sua classe de vertebrado:",
                        pairs = mapOf(
                            "Cão" to "Mamífero",
                            "Águia" to "Ave",
                            "Sardinha" to "Peixe",
                            "Lagarto" to "Réptil"
                        ),
                        explanation = "Parabéns! Classificaste todos os grupos de animais com precisão."
                    )
                )
            ),
            Worksheet(
                id = "em_2_f4",
                numero = 4,
                title = "Alimentação e Locomoção dos Animais",
                description = "Carnívoros, herbívoros, omnívoros e como se deslocam (voar, nadar, rastejar, marchar).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🐾",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Um animal que se alimenta exclusivamente de plantas e erva chama-se...",
                        options = listOf("Herbívoro", "Carnívoro", "Omnívoro"),
                        correctAnswer = "Herbívoro",
                        explanation = "A vaca, o coelho e a ovelha são animais herbívoros!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O ser humano e o porco são animais omnívoros porque comem alimentos de origem vegetal e animal.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os omnívoros têm uma dieta variada com plantas e carne."
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A cobra não tem patas, por isso desloca-se a ___ pelo chão.",
                        options = listOf("rastejar", "voar", "marchar"),
                        correctAnswer = "rastejar",
                        explanation = "A serpente rasteja ondulando o seu corpo longo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes animais é um CARNÍVORO feroz que caça presas para se alimentar?",
                        options = listOf("🐯 Tigre / Leão", "🐰 Coelho", "🐴 Cavalo"),
                        correctAnswer = "🐯 Tigre / Leão",
                        explanation = "Os grandes felinos são carnívoros predadores! 🐯"
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se desloca o golfinho e o tubarão no oceano?",
                        options = listOf("A nadar com barbatanas", "A saltar com 4 patas", "A voar no céu"),
                        correctAnswer = "A nadar com barbatanas",
                        explanation = "Nadam velozmente com a propulsão das suas barbatanas caudais."
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os morcegos são mamíferos que conseguem voar graças às suas asas membranosas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O morcego é o único mamífero com voo ativo sustentado."
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O canguru e a rã deslocam-se principalmente aos...",
                        options = listOf("Saltos", "Passos lentos", "Rastejando"),
                        correctAnswer = "Saltos",
                        explanation = "Têm membros posteriores fortes e compridos adaptados ao salto."
                    ),
                    WorksheetExercise(
                        id = "em_2_f4_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o animal ao seu tipo de alimentação:",
                        pairs = mapOf(
                            "Vaca" to "Herbívoro (come erva)",
                            "Lobo" to "Carnívoro (come carne)",
                            "Urso" to "Omnívoro (come peixe, frutos e bagas)",
                            "Águia" to "Carnívoro (caça pequenos animais)"
                        ),
                        explanation = "Excelente classificação dos regimes alimentares dos animais!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f5",
                numero = 5,
                title = "As Plantas: Órgãos e Ciclo de Vida",
                description = "Raiz, caule, folhas, flores, frutos, árvores de folha caduca e persistente.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌱",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a parte da planta que fica debaixo da terra e absorve água e sais minerais?",
                        options = listOf("Raiz", "Flor", "Caule"),
                        correctAnswer = "Raiz",
                        explanation = "A raiz fixa a planta ao solo e suga a água e nutrientes indispensáveis!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O caule (tronco) transporta a seiva da raiz até às folhas e sustenta os ramos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O caule é a coluna de transporte da planta."
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e3",
                        type = ExerciseType.COMPLETING,
                        question = "As árvores que perdem todas as folhas no Outono chamam-se árvores de folha ___",
                        options = listOf("caduca", "persistente", "eterna"),
                        correctAnswer = "caduca",
                        explanation = "Folha caduca: cai no outono/inverno. Folha persistente: fica verde o ano todo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que órgão da planta se transforma em FRUTO após ser polinizado?",
                        options = listOf("🌸 Flor", "🍂 Raiz", "🪵 Casca"),
                        correctAnswer = "🌸 Flor",
                        explanation = "A flor murcha e o seu ovário desenvolve-se no fruto suculento com sementes! 🌸"
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O pinheiro e o sobreiro mantêm as folhas verdes o ano todo. São árvores de folha...",
                        options = listOf("Persistente", "Caduca", "Seca"),
                        correctAnswer = "Persistente",
                        explanation = "Não perdem a folhagem toda ao mesmo tempo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As plantas produzem oxigénio limpo para nós respirarmos através da luz solar.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! As plantas são os grandes pulmões verdes do planeta Terra."
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "De onde nascem novas plantas na natureza?",
                        options = listOf("Das sementes que caem na terra fértil", "De pedras", "De moedas"),
                        correctAnswer = "Das sementes que caem na terra fértil",
                        explanation = "A semente germina com humidade e calor e dá origem a uma nova plantinha!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f5_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a parte da planta à sua função vital:",
                        pairs = mapOf(
                            "Raiz" to "Fixação e absorção de água",
                            "Folhas" to "Respiração e captação de luz",
                            "Caule / Tronco" to "Sustentação e transporte de seiva",
                            "Fruto" to "Proteção das sementes"
                        ),
                        explanation = "Conhecimento botânico impecável!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f6",
                numero = 6,
                title = "A Água na Natureza e os seus Estados Físicos",
                description = "Sólido, líquido, gasoso, evaporação, condensação e o ciclo da água.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💧",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em que estado físico se encontra o gelo e a neve nas montanhas frias?",
                        options = listOf("Estado Sólido", "Estado Líquido", "Estado Gasoso"),
                        correctAnswer = "Estado Sólido",
                        explanation = "O gelo é a água no estado sólido, com forma rígida!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A água do mar e dos rios evapora com o calor do Sol e sobe ao céu sob a forma de vapor de água.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É o fenómeno da evaporação."
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Quando o vapor de água arrefece no céu e forma nuvens com gotinhas, dá-se a ___",
                        options = listOf("Condensação", "Fervura", "Congelação"),
                        correctAnswer = "Condensação",
                        explanation = "O vapor transforma-se de novo em gotinhas líquidas (nuvens) por condensação."
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens representa a água em Estado Líquido?",
                        options = listOf("🌊 Onda do mar / Rio", "🧊 Cubos de gelo", "💨 Fumo de vapor na panela"),
                        correctAnswer = "🌊 Onda do mar / Rio",
                        explanation = "A água corrente dos rios e mares está no estado líquido! 🌊"
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o fenómeno em que as gotas das nuvens caem sobre a terra?",
                        options = listOf("Precipitação (Chuva)", "Evaporação", "Aquecimento"),
                        correctAnswer = "Precipitação (Chuva)",
                        explanation = "A chuva, granizo ou neve são formas de precipitação."
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos fechar a torneira enquanto lavamos os dentes para poupar este recurso precioso.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A água potável é finita e devemos evitar qualquer desperdício."
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A água potável é aquela que...",
                        options = listOf("É pura, limpa e própria para beber sem perigo", "Tem sal do mar", "Tem sabão da máquina"),
                        correctAnswer = "É pura, limpa e própria para beber sem perigo",
                        explanation = "A água potável é tratada e segura para a saúde humana."
                    ),
                    WorksheetExercise(
                        id = "em_2_f6_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa as etapas do ciclo da água:",
                        pairs = mapOf(
                            "Evaporação" to "A água sobe com o calor do Sol",
                            "Condensação" to "Formação das nuvens no céu",
                            "Precipitação" to "Queda da chuva sobre a terra",
                            "Infiltração" to "A água penetra no solo e lençóis freáticos"
                        ),
                        explanation = "Perfeito! Compreendes o ciclo infinito da água na Terra."
                    )
                )
            ),
            Worksheet(
                id = "em_2_f7",
                numero = 7,
                title = "O Ar, o Vento e a Atmosfera",
                description = "O ar invisível que nos rodeia, o vento em movimento e a proteção contra a poluição.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "💨",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é o VENTO na natureza?",
                        options = listOf("O ar em movimento", "Uma nuvem que caiu", "Uma árvore a abanar"),
                        correctAnswer = "O ar em movimento",
                        explanation = "O vento é simplesmente o ar a deslocar-se de um local para o outro!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O ar não se vê mas ocupa espaço (ex: quando enchemos um balão de ar).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O ar tem massa e ocupa o interior do balão."
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O gás presente no ar que os seres humanos precisam de respirar para viver chama-se ___",
                        options = listOf("Oxigénio", "Fumo", "Vapor de azeite"),
                        correctAnswer = "Oxigénio",
                        explanation = "O oxigénio (O2) é essencial à respiração de animais e pessoas."
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que aparelho limpo usa a força do vento para produzir eletricidade verde?",
                        options = listOf("🌬️ Aerogerador / Moinho de vento eólico", "🏭 Chaminé com fumo preto", "🚗 Escape de carro"),
                        correctAnswer = "🌬️ Aerogerador / Moinho de vento eólico",
                        explanation = "A energia eólica aproveita o vento sem poluir o planeta! 🌬️"
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como podemos ajudar a manter o ar das nossas cidades mais limpo?",
                        options = listOf("Andar mais a pé, de bicicleta e de transportes públicos", "Queimar plásticos na rua", "Andar sempre sozinho de carro grande"),
                        correctAnswer = "Andar mais a pé, de bicicleta e de transportes públicos",
                        explanation = "Menos fumo de carros significa ar mais puro e saudável para todos!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O fumo das fábricas e dos automóveis polui a atmosfera e prejudica os nossos pulmões.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A poluição do ar causa alergias e problemas respiratórios."
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que brinquedo leve voa no céu seguro por uma linha aproveitando o vento?",
                        options = listOf("Papagaio de papel", "Berlinde de vidro", "Boneco de chumbo"),
                        correctAnswer = "Papagaio de papel",
                        explanation = "O papagaio de papel flutua graciosamente nas correntes de ar!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f7_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa os instrumentos meteorológicos à sua medição:",
                        pairs = mapOf(
                            "Anemómetro" to "Mede a velocidade do vento",
                            "Catavento" to "Indica a direção do vento (Norte, Sul...)",
                            "Termómetro" to "Mede a temperatura do ar",
                            "Pluviómetro" to "Mede a quantidade de chuva"
                        ),
                        explanation = "Excelente conhecimento sobre o ar e a meteorologia!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f8",
                numero = 8,
                title = "O Solo, Rochas e Minerais",
                description = "Tipos de solo (arenoso, argiloso, fértil/humífero) e a utilidade das rochas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🪨",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o solo escuro, rico em húmus e nutrientes, ideal para a agricultura e cultivo de plantas?",
                        options = listOf("Solo fértil / Humífero", "Solo de areia pura da praia", "Solo de cimento"),
                        correctAnswer = "Solo fértil / Humífero",
                        explanation = "O solo humífero retém humidade e tem minerais ricos para o crescimento das plantas!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O granito e o mármore são rochas duras muito usadas na construção de casas e monumentos em Portugal.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O granito no Norte e o mármore no Alentejo são rochas ornamentais famosas."
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e3",
                        type = ExerciseType.COMPLETING,
                        question = "A areia que encontramos na praia é formada por pedacinhos minúsculos de ___ desgastadas pelas ondas.",
                        options = listOf("rochas e conchas", "plásticos", "madeira"),
                        correctAnswer = "rochas e conchas",
                        explanation = "A erosão marítima quebra as rochas ao longo de milhares de anos formando areia."
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que tipo de solo avermelhado e moldável usamos para fazer vasos de barro e telhas?",
                        options = listOf("🏺 Argila / Solo argiloso", "🏖️ Areia solta", "🪨 Calhau grande"),
                        correctAnswer = "🏺 Argila / Solo argiloso",
                        explanation = "A argila (barro) fica moldável com água e endurece ao cozer no forno! 🏺"
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam os animais que cavam túneis no solo ajudando a arejar a terra fértil?",
                        options = listOf("Minhocas", "Peixes", "Tubarões"),
                        correctAnswer = "Minhocas",
                        explanation = "As minhocas são grandes amigas da agricultura porque arejam e fertilizam o solo!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Deitar lixo e pilhas na terra contamina o solo e envenena as águas subterrâneas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os químicos tóxicos destroem a fertilidade do solo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A calçada portuguesa tão típica nas nossas ruas é feita com cubos de pedra de...",
                        options = listOf("Calcário e basalto", "Gesso mole", "Borracha"),
                        correctAnswer = "Calcário e basalto",
                        explanation = "A calçada branca e preta usa calcário e basalto trabalhados pelos mestres calceteiros!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f8_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa a rocha ou solo à sua utilização:",
                        pairs = mapOf(
                            "Mármore" to "Esculturas e bancadas nobres",
                            "Argila / Barro" to "Tijolos, telhas e loiça de barro",
                            "Areia" to "Fabrico de vidro e argamassas",
                            "Granito" to "Paredes de casas e calçadas"
                        ),
                        explanation = "Muito bem! Conheces as riquezas minerais e a importância dos solos."
                    )
                )
            ),
            Worksheet(
                id = "em_2_f9",
                numero = 9,
                title = "A Minha Terra: Aldeias, Vilas e Cidades",
                description = "Paisagens rurais e urbanas, serviços públicos e monumentos de Portugal.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🏙️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde há mais prédios altos, trânsito de automóveis e centros comerciais?",
                        options = listOf("Na Cidade (Paisagem Urbana)", "Na Aldeia pequena", "No meio da floresta"),
                        correctAnswer = "Na Cidade (Paisagem Urbana)",
                        explanation = "As cidades têm maior concentração de população, comércio e serviços."
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Nas aldeias o ambiente é mais tranquilo, com mais campos agrícolas, hortas e natureza.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A paisagem rural é marcada pela agricultura e calma."
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O edifício onde se reúnem os responsáveis eleitos para administrar o concelho chama-se ___ Municipal.",
                        options = listOf("Câmara", "Escola", "Hospital"),
                        correctAnswer = "Câmara",
                        explanation = "A Câmara Municipal gere os serviços e obras do município!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes serviços públicos é responsável por apagar fogos e prestar socorro?",
                        options = listOf("🚒 Quartel de Bombeiros", "🏦 Banco", "🎭 Cinema"),
                        correctAnswer = "🚒 Quartel de Bombeiros",
                        explanation = "Os bombeiros prestam socorro inestimável à população! 🚒"
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a capital de Portugal, situada junto ao rio Tejo?",
                        options = listOf("Lisboa", "Porto", "Faro"),
                        correctAnswer = "Lisboa",
                        explanation = "Lisboa é a capital e a maior cidade de Portugal!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos preservar e respeitar os monumentos históricos antigos porque contam a história dos nossos antepassados.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Castelos, igrejas e pontes antigas são património de todos nós."
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde podemos requisitar livros gratuitamente para ler em casa?",
                        options = listOf("Na Biblioteca Municipal", "Na bomba de gasolina", "No talho"),
                        correctAnswer = "Na Biblioteca Municipal",
                        explanation = "As bibliotecas públicas disponibilizam milhares de livros para empréstimo!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f9_e8",
                        type = ExerciseType.MATCHING,
                        question = "Distingue os serviços da comunidade:",
                        pairs = mapOf(
                            "Hospital / Centro de Saúde" to "Tratar dos doentes e feridos",
                            "Escola" to "Ensinar e educar as crianças",
                            "Polícia" to "Manter a segurança e ordem pública",
                            "Câmara Municipal" to "Administrar o concelho e jardins"
                        ),
                        explanation = "Excelente conhecimento sobre a organização da nossa terra!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f10",
                numero = 10,
                title = "Sinais de Trânsito e Prevenção Rodoviária",
                description = "Sinais de perigo, proibição e obrigação, regras no carro e no autocarro escolar.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🛑",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a forma dos sinais de trânsito que indicam PERIGO na estrada?",
                        options = listOf("Triangulares com borda vermelha ⚠️", "Redondos azuis", "Quadrados verdes"),
                        correctAnswer = "Triangulares com borda vermelha ⚠️",
                        explanation = "Os sinais triangulares avisam os condutores para perigos na via!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O sinal vermelho octogonal com a palavra 'STOP' obriga a parar completamente o veículo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É obrigatório parar e olhar antes de avançar."
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Os sinais de trânsito redondos com fundo azul indicam uma ___",
                        options = listOf("Obrigação", "Proibição", "Informação"),
                        correctAnswer = "Obrigação",
                        explanation = "Sinais azuis redondos indicam ações obrigatórias (ex: pista obrigatória de ciclistas)."
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes comportamentos é CORRETO dentro do autocarro escolar?",
                        options = listOf("🪑 Ir sentado com cinto apertado e em silêncio", "🏃 Correr no corredor a gritar", "🚪 Tentar abrir a porta em andamento"),
                        correctAnswer = "🪑 Ir sentado com cinto apertado e em silêncio",
                        explanation = "A segurança no transporte coletivo exige ir sentado com o cinto apertado! 🪑"
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quando sais do automóvel, por que lado deves sempre sair?",
                        options = listOf("Pelo lado do passeio (longe dos carros)", "Pelo lado da estrada", "Pela janela"),
                        correctAnswer = "Pelo lado do passeio (longe dos carros)",
                        explanation = "Sair para o passeio protege-te do fluxo de trânsito da estrada."
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um peão à noite na estrada deve usar roupas escuras sem nenhum refletor.",
                        correctAnswer = "false",
                        explanation = "Falso! Deve usar colete refletor ou roupas claras para ser visto pelos condutores."
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os sinais circulares com rebordo vermelho indicam...",
                        options = listOf("Proibição (algo que não se pode fazer)", "Permissão total", "Apenas uma sugestão"),
                        correctAnswer = "Proibição (algo que não se pode fazer)",
                        explanation = "Rebordo vermelho circular = Proibição estrita!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f10_e8",
                        type = ExerciseType.MATCHING,
                        question = "Associa o formato do sinal ao seu significado:",
                        pairs = mapOf(
                            "Triângulo vermelho" to "Sinal de Perigo ⚠️",
                            "Círculo com borda vermelha" to "Sinal de Proibição 🚫",
                            "Círculo azul" to "Sinal de Obrigação 🔵",
                            "Retângulo azul" to "Sinal de Informação ℹ️"
                        ),
                        explanation = "Conhecedor exímio do código da estrada e segurança infantil!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f11",
                numero = 11,
                title = "O Passado e as Histórias dos Avós",
                description = "Objetos antigos e modernos, como viviam os nossos antepassados e a árvore genealógica.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🕰️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como ouviam música e notícias os nossos bisavós quando não havia computadores nem telemóveis?",
                        options = listOf("Num rádio a pilhas ou grafonola", "Pelo smartwatch", "Pelo YouTube"),
                        correctAnswer = "Num rádio a pilhas ou grafonola",
                        explanation = "O rádio antigo de madeira era o centro de convívio das famílias à noite!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A árvore genealógica representa visualmente os membros da nossa família ao longo das gerações.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Mostra pais, avós, bisavós, tios e primos."
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Antigamente, as pessoas escreviam cartas à mão e usavam máquinas de ___ mecânicas.",
                        options = listOf("escrever", "lavar loiça", "fotocópias digitais"),
                        correctAnswer = "escrever",
                        explanation = "As máquinas de escrever tinham teclas de ferro e fita com tinta!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que objeto antigo se usava para iluminar as casas antes de haver lâmpadas elétricas?",
                        options = listOf("🕯️ Candeia de azeite / Vela", "💡 Lâmpada LED", "🔦 Lanterna a laser"),
                        correctAnswer = "🕯️ Candeia de azeite / Vela",
                        explanation = "Usavam-se candeias a petróleo, azeite ou velas! 🕯️"
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os pais dos teus pais são teus...",
                        options = listOf("Avós", "Tios", "Irmãos"),
                        correctAnswer = "Avós",
                        explanation = "Os teus avós maternos e paternos são os pais da tua mãe e do teu pai!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No tempo antigo, as roupas eram lavadas nos lavadouros públicos comunitários com sabão azul e branco.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! As mulheres juntavam-se no lavadouro para lavar a roupa à mão."
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Os brinquedos tradicionais antigos eram feitos principalmente de...",
                        options = listOf("Madeira, lata e trapos", "Plástico elétrico", "Ecrãs de vidro"),
                        correctAnswer = "Madeira, lata e trapos",
                        explanation = "Pecos, arcos de madeira, bonecas de trapos e piões de madeira!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f11_e8",
                        type = ExerciseType.MATCHING,
                        question = "Liga o objeto antigo ao seu correspondente moderno:",
                        pairs = mapOf(
                            "Carroça puxada a cavalo" to "Automóvel elétrico",
                            "Carta em papel postal" to "Correio eletrónico (E-mail)",
                            "Ferro de engomar a brasas de carvão" to "Ferro a vapor elétrico",
                            "Fogão a lenha" to "Placa de indução moderna"
                        ),
                        explanation = "Fantástica viagem pela evolução da história e da tecnologia!"
                    )
                )
            ),
            Worksheet(
                id = "em_2_f12",
                numero = 12,
                title = "Ambiente, Reciclagem e os 3 R's",
                description = "Reduzir, Reutilizar, Reciclar, os ecopontos e a proteção da natureza.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "♻️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_2_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que significam os famosos 3 R's da sustentabilidade?",
                        options = listOf("Reduzir, Reutilizar e Reciclar", "Rir, Rezar e Rolar", "Rasgar, Riscar e Roer"),
                        correctAnswer = "Reduzir, Reutilizar e Reciclar",
                        explanation = "Reduzir o consumo, reutilizar o que pudermos e reciclar o lixo separado!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No Ecoponto AMARELO colocamos embalagens de plástico, metal e pacotes de leite.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Plásticos, latas e pacotes de bebidas vão para o contentor amarelo."
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e3",
                        type = ExerciseType.COMPLETING,
                        question = "O papel, cartão, jornais e revistas devem ser colocados no Ecoponto ___",
                        options = listOf("AZUL", "VERDE", "VERMELHO"),
                        correctAnswer = "AZUL",
                        explanation = "O ecoponto azul é exclusivo para papel e cartão limpos!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Onde deves colocar as garrafas e frascos de vidro vazios?",
                        options = listOf("🟢 Ecoponto Verde (Vidrão)", "🟡 Ecoponto Amarelo", "🔵 Ecoponto Azul"),
                        correctAnswer = "🟢 Ecoponto Verde (Vidrão)",
                        explanation = "O vidrão verde recebe garrafas e frascos de vidro para derreter e criar novos frascos! 🟢"
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde se devem depositar as pilhas e baterias velhas gastas?",
                        options = listOf("No Pilhão (contentor especial vermelho)", "No contentor do lixo comum", "Enterradas na relva"),
                        correctAnswer = "No Pilhão (contentor especial vermelho)",
                        explanation = "As pilhas contêm metais pesados e devem ir sempre para o Pilhão!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O plástico demora centenas de anos a decompor-se se for atirado para a praia ou oceanos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O lixo plástico sufoca peixes e tartarugas marinas."
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Reutilizar significa...",
                        options = listOf("Dar uma nova utilidade a um objeto em vez de o deitar logo ao lixo", "Comprar 10 coisas novas iguais", "Queimar tudo"),
                        correctAnswer = "Dar uma nova utilidade a um objeto em vez de o deitar logo ao lixo",
                        explanation = "Exemplo: transformar um frasco de compota num porta-lápis colorido!"
                    ),
                    WorksheetExercise(
                        id = "em_2_f12_e8",
                        type = ExerciseType.MATCHING,
                        question = "Separa corretamente os resíduos no seu respetivo Ecoponto:",
                        pairs = mapOf(
                            "Garrafa de azeite de vidro" to "Ecoponto Verde (Vidro)",
                            "Caixa de sapatos de cartão" to "Ecoponto Azul (Papel)",
                            "Lata de refrigerante de alumínio" to "Ecoponto Amarelo (Metal/Plástico)",
                            "Pilhas gastas de lanterna" to "Pilhão Vermelho"
                        ),
                        explanation = "Parabéns, Guardião Oficial da Natureza do 2.º Ano!"
                    )
                )
            )
        )
    )

    val year = WorksheetYear(
        id = 2,
        title = "2.º Ano",
        disciplines = listOf(portugues, matematica, estudoMeio)
    )
}
