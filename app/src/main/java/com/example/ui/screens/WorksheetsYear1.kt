package com.example.ui.screens

import androidx.compose.ui.graphics.Color

object WorksheetsYear1 {
    val portugues = WorksheetDiscipline(
        id = "pt_1",
        title = "Português",
        emoji = "🇵🇹",
        color = Color(0xFF4F46E5),
        worksheets = listOf(
            // Ficha 1
            Worksheet(
                id = "pt_1_f1",
                numero = 1,
                title = "As Vogais",
                description = "Aprende e reconhece as vogais A, E, I, O, U em português de Portugal.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🅰️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas letras é uma vogal?",
                        options = listOf("F", "S", "A"),
                        correctAnswer = "A",
                        explanation = "As cinco vogais do português são: A, E, I, O e U! A letra A é uma delas."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a vogal que inicia a palavra 'Abelha'?",
                        options = listOf("O", "A", "U"),
                        correctAnswer = "A",
                        explanation = "Abelha começa com o som da vogal A! (A-be-lha)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A letra 'E' é uma consoante.",
                        correctAnswer = "false",
                        explanation = "A letra E é uma vogal (A, E, I, O, U) e não uma consoante."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e4",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra: ___ greja",
                        options = listOf("A", "I", "U"),
                        correctAnswer = "I",
                        explanation = "Igreja começa com a vogal I! (I-gre-ja)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe o animal que começa com a vogal 'O'!",
                        options = listOf("🐱 Gato", "🐏 Ovelha", "🐶 Cão"),
                        correctAnswer = "🐏 Ovelha",
                        explanation = "Ovelha começa com o som da vogal O! (O-ve-lha)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e6",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Qual é a última letra da palavra 'Bolo'?",
                        options = listOf("O", "E", "A"),
                        correctAnswer = "O",
                        explanation = "Bolo termina com o som da vogal O! (Bo-lo)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas vogais existem na nossa língua?",
                        options = listOf("3", "5", "10"),
                        correctAnswer = "5",
                        explanation = "As vogais são exatamente cinco: A, E, I, O e U."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f1_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra 'Uva' com a vogal correta: ___ va",
                        options = listOf("U", "I", "O"),
                        correctAnswer = "U",
                        explanation = "Uva começa com a vogal U! (U-va)."
                    )
                )
            ),
            // Ficha 2
            Worksheet(
                id = "pt_1_f2",
                numero = 2,
                title = "Reconhecer Letras",
                description = "Pratica a identificação das letras e sons do nosso alfabeto de forma controlada.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🔡",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas é a letra M?",
                        options = listOf("N", "M", "W"),
                        correctAnswer = "M",
                        explanation = "A letra M tem quatro traços! Escreve-se no início de Mãe ou Mala."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e2",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe o animal cujo nome começa com a letra L!",
                        options = listOf("🦁 Leão", "🐵 Macaco", "🐻 Urso"),
                        correctAnswer = "🦁 Leão",
                        explanation = "Leão começa com a letra L! (Le-ão)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas é a letra P?",
                        options = listOf("Q", "B", "P"),
                        correctAnswer = "P",
                        explanation = "A letra P tem um traço vertical e uma barriga no topo! Usada para escrever Pato."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A primeira letra do nosso alfabeto é a letra Z.",
                        correctAnswer = "false",
                        explanation = "A primeira letra do alfabeto é o A. O Z é a última letra!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e5",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Qual destas letras é uma consoante?",
                        options = listOf("I", "F", "U"),
                        correctAnswer = "F",
                        explanation = "A letra F é uma consoante. O I e o U são vogais!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e6",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "A palavra 'Mala' começa com que letra?",
                        options = listOf("N", "M", "L"),
                        correctAnswer = "M",
                        explanation = "Mala começa com a consoante M! (Ma-la)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Diz qual destas letras é a consoante S.",
                        options = listOf("C", "Z", "S"),
                        correctAnswer = "S",
                        explanation = "A letra S tem duas curvas onduladas, parece uma serpente!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f2_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra 'Pato' com a consoante correta: ___ ato",
                        options = listOf("P", "T", "M"),
                        correctAnswer = "P",
                        explanation = "Pato começa com a consoante P! (Pa-to)."
                    )
                )
            ),
            // Ficha 3
            Worksheet(
                id = "pt_1_f3",
                numero = 3,
                title = "Maiúsculas e Minúsculas",
                description = "Aprende a relacionar letras maiúsculas e minúsculas correspondentes.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🔠",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f3_e1",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada letra maiúscula à sua letra minúscula:",
                        pairs = mapOf(
                            "A" to "a",
                            "E" to "e",
                            "I" to "i",
                            "O" to "o"
                        ),
                        explanation = "Excelente! Associaste as vogais maiúsculas A, E, I, O às respetivas minúsculas a, e, i, o."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a letra minúscula correspondente a M?",
                        options = listOf("n", "m", "w"),
                        correctAnswer = "m",
                        explanation = "A maiúscula M corresponde à minúscula m!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a maiúscula da letra p?",
                        options = listOf("B", "P", "D"),
                        correctAnswer = "P",
                        explanation = "O p minúsculo corresponde ao P maiúsculo!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A letra maiúscula de 'g' é a letra 'G'.",
                        correctAnswer = "true",
                        explanation = "Sim, a letra g minúscula escreve-se G em maiúscula!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e5",
                        type = ExerciseType.MATCHING,
                        question = "Associa as consoantes maiúsculas às suas minúsculas:",
                        pairs = mapOf(
                            "P" to "p",
                            "M" to "m",
                            "L" to "l",
                            "T" to "t"
                        ),
                        explanation = "Muito bem! Relacionaste corretamente as consoantes maiúsculas com as minúsculas correspondentes."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a minúscula da letra D?",
                        options = listOf("b", "q", "d"),
                        correctAnswer = "d",
                        explanation = "A letra D maiúscula tem como minúscula a letra d!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e7",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "O nome próprio 'Rui' deve começar com que tipo de letra?",
                        options = listOf("Letra maiúscula (R)", "Letra minúscula (r)", "Qualquer uma"),
                        correctAnswer = "Letra maiúscula (R)",
                        explanation = "Os nomes de pessoas (nomes próprios) começam sempre com letra maiúscula! Ex: Rui."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f3_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a maiúscula da letra f?",
                        options = listOf("E", "F", "T"),
                        correctAnswer = "F",
                        explanation = "A minúscula f corresponde à maiúscula F!"
                    )
                )
            ),
            // Ficha 4
            Worksheet(
                id = "pt_1_f4",
                numero = 4,
                title = "Sílabas Simples",
                description = "Junta consoantes e vogais para formar sílabas simples e dar os primeiros passos na leitura.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📖",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se juntares a consoante M com a vogal A, que sílaba obténs?",
                        options = listOf("MA", "AM", "ME"),
                        correctAnswer = "MA",
                        explanation = "M + A = MA, como na palavra MALA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas sílabas inicia a palavra 'Pato'?",
                        options = listOf("PE", "PA", "PO"),
                        correctAnswer = "PA",
                        explanation = "Pato começa com a sílaba PA! (Pa-to)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e3",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra 'Lu-pa': LU - ___",
                        options = listOf("PA", "MA", "TA"),
                        correctAnswer = "PA",
                        explanation = "LU + PA forma LUPA, o objeto usado para aumentar imagens!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e4",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens começa com a sílaba 'LA'?",
                        options = listOf("🍊 Laranja", "🍎 Maçã", "🍌 Banana"),
                        correctAnswer = "🍊 Laranja",
                        explanation = "Laranja começa com a sílaba LA! (La-ran-ja)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas sílabas (bater de palmas) tem a palavra 'Ma-pa'?",
                        options = listOf("1", "2", "3"),
                        correctAnswer = "2",
                        explanation = "Ao dizer 'Ma-pa', damos 2 palmas. Tem 2 sílabas!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e6",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Qual é a sílaba que falta na palavra 'Me-sa': ___ - SA",
                        options = listOf("ME", "PE", "LE"),
                        correctAnswer = "ME",
                        explanation = "A sílaba em falta é ME para formar MESA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e7",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Pai' tem apenas 1 sílaba.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Dizemos 'Pai' de uma só vez, logo tem apenas 1 sílaba."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f4_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a sílaba inicial da palavra 'Lu-va'?",
                        options = listOf("LA", "LU", "LI"),
                        correctAnswer = "LU",
                        explanation = "Lu-va começa com a sílaba LU! (Lu-va)."
                    )
                )
            ),
            // Ficha 5
            Worksheet(
                id = "pt_1_f5",
                numero = 5,
                title = "Construir Palavras",
                description = "Combina e ordena sílabas simples para formar palavras reais de 1.º ano.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧱",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f5_e1",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as sílabas para formar a palavra: LA - MA (Mala)",
                        options = listOf("MA", "LA"),
                        correctAnswers = listOf("MA", "LA"),
                        explanation = "Ao juntar MA + LA, construímos a palavra MALA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e2",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as sílabas para formar a palavra: TO - PA (Pato)",
                        options = listOf("PA", "TO"),
                        correctAnswers = listOf("PA", "TO"),
                        explanation = "Ao juntar PA + TO, construímos a palavra PATO! 🦆"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que palavra formamos com a junção das sílabas LA + TA?",
                        options = listOf("MALA", "LATA", "PATO"),
                        correctAnswer = "LATA",
                        explanation = "LA + TA = LATA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e4",
                        type = ExerciseType.COMPLETING,
                        question = "Ajuda a terminar a palavra 'Lupa': LU - ___",
                        options = listOf("PA", "TA", "CA"),
                        correctAnswer = "PA",
                        explanation = "LU + PA = LUPA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se juntarmos as sílabas BO e LA, formamos a palavra BOLA.",
                        correctAnswer = "true",
                        explanation = "Sim! BO + LA = BOLA! ⚽"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e6",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as sílabas para formar a palavra: SA - CA (Casa)",
                        options = listOf("CA", "SA"),
                        correctAnswers = listOf("CA", "SA"),
                        explanation = "Ao juntar CA + SA, formamos CASA! 🏠"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a palavra correta para a junção de MA + PA?",
                        options = listOf("MAPA", "MALA", "PAPA"),
                        correctAnswer = "MAPA",
                        explanation = "MA + PA = MAPA! Usamos o mapa para nos localizarmos."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f5_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Que sílaba repete para formar a palavra 'Papa': PA - ___",
                        options = listOf("PA", "MA", "TA"),
                        correctAnswer = "PA",
                        explanation = "PA + PA = PAPA! O alimento quentinho dos bebés."
                    )
                )
            ),
            // Ficha 6
            Worksheet(
                id = "pt_1_f6",
                numero = 6,
                title = "Ler Palavras Simples",
                description = "Lê palavras curtas de forma autónoma e associa às respetivas imagens.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👀",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Lê a palavra e seleciona a opção correta: 'BOLA'",
                        options = listOf("BOLO", "BOLA", "COLA"),
                        correctAnswer = "BOLA",
                        explanation = "B-O (bo) L-A (la) forma BOLA! ⚽"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e2",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens corresponde à palavra 'Pato'?",
                        options = listOf("🐈 Gato", "🦆 Pato", "🐕 Cão"),
                        correctAnswer = "🦆 Pato",
                        explanation = "A palavra P-A-T-O lê-se PATO! 🦆"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras é a palavra 'Mala'?",
                        options = listOf("MALA", "MOLA", "MATA"),
                        correctAnswer = "MALA",
                        explanation = "M-A-L-A lê-se MALA! Usamo-la para levar coisas."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'CASA' lê-se com o som inicial idêntico à letra K.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O C antes do A tem o som 'K' (Ca-sa)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Identifica a escrita correta da palavra 'Lupa':",
                        options = listOf("LUTA", "LUPA", "LUNA"),
                        correctAnswer = "LUPA",
                        explanation = "L-U-P-A lê-se LUPA!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens corresponde à palavra 'Maçã'?",
                        options = listOf("🍎 Maçã", "🍌 Banana", "🍐 Pêra"),
                        correctAnswer = "🍎 Maçã",
                        explanation = "A palavra M-A-Ç-Ã lê-se Maçã!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Identifica a palavra 'Gato':",
                        options = listOf("GATO", "RATO", "PATO"),
                        correctAnswer = "GATO",
                        explanation = "G-A-T-O lê-se GATO! 🐈"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f6_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Lata' começa com o som da consoante L.",
                        correctAnswer = "true",
                        explanation = "Sim, L-A-T-A começa com o fonema L!"
                    )
                )
            ),
            // Ficha 7
            Worksheet(
                id = "pt_1_f7",
                numero = 7,
                title = "Letra Inicial",
                description = "Descobre com que som e letra começam os nomes dos objetos.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🏁",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Com que letra começa a palavra 'Bola'?",
                        options = listOf("D", "B", "P"),
                        correctAnswer = "B",
                        explanation = "Bola começa com a letra B! (B-o-la). ⚽"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a primeira letra da palavra 'Casa'?",
                        options = listOf("S", "K", "C"),
                        correctAnswer = "C",
                        explanation = "Casa começa com a letra C! (Ca-sa)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas frutas começa com a consoante 'M'?",
                        options = listOf("🍎 Maçã", "🍐 Pêra", "🍌 Banana"),
                        correctAnswer = "🍎 Maçã",
                        explanation = "Maçã começa com M! (Ma-çã)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Pato' começa com a letra 'T'.",
                        correctAnswer = "false",
                        explanation = "Falso! Pato começa com a letra P. A letra T está na segunda sílaba (pa-to)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Com que consoante começa a palavra 'Rato'?",
                        options = listOf("R", "S", "M"),
                        correctAnswer = "R",
                        explanation = "Rato começa com a consoante R! (Ra-to)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e6",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Qual é a primeira letra da palavra 'Lobo'?",
                        options = listOf("L", "O", "B"),
                        correctAnswer = "L",
                        explanation = "Lobo começa com a consoante L! (Lo-bo)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que letra inicia o nome do animal 'Gato'?",
                        options = listOf("C", "G", "J"),
                        correctAnswer = "G",
                        explanation = "Gato começa com a consoante G! (Ga-to)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f7_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a primeira letra de 'Dado': ___ ado",
                        options = listOf("D", "B", "T"),
                        correctAnswer = "D",
                        explanation = "Dado começa com a consoante D! (Da-do)."
                    )
                )
            ),
            // Ficha 8
            Worksheet(
                id = "pt_1_f8",
                numero = 8,
                title = "Letra Final",
                description = "Presta atenção ao final das palavras e identifica a última letra.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🛑",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a última letra da palavra 'Pato'?",
                        options = listOf("A", "O", "E"),
                        correctAnswer = "O",
                        explanation = "Pato termina com a vogal O! (Pa-to)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Com que letra termina a palavra 'Mala'?",
                        options = listOf("A", "E", "O"),
                        correctAnswer = "A",
                        explanation = "Mala termina com a vogal A! (Ma-la)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Sol' termina com a letra 'L'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A palavra Sol termina com a consoante L."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e4",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "Qual é a última letra da palavra 'Cão'?",
                        options = listOf("O", "A", "E"),
                        correctAnswer = "O",
                        explanation = "Cão termina com a vogal O! O acento til (~) fica no A (Cão)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a última letra da palavra 'Pai'?",
                        options = listOf("A", "I", "E"),
                        correctAnswer = "I",
                        explanation = "Pai termina com a vogal I! (Pai)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e6",
                        type = ExerciseType.LETTERS_WORDS,
                        question = "A palavra 'Leão' termina com que letra?",
                        options = listOf("O", "A", "M"),
                        correctAnswer = "O",
                        explanation = "Leão termina com a vogal O! (Le-ão)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e7",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Bolo' termina com a letra 'A'.",
                        correctAnswer = "false",
                        explanation = "Falso! Bolo termina com a vogal O! (Bo-lo)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f8_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a letra final da palavra 'Lápis'?",
                        options = listOf("I", "S", "Z"),
                        correctAnswer = "S",
                        explanation = "Lápis termina com a consoante S! (Lá-pis)."
                    )
                )
            ),
            // Ficha 9
            Worksheet(
                id = "pt_1_f9",
                numero = 9,
                title = "Sílabas e Palavras",
                description = "Pratica a divisão, contagem e ordenação de sílabas mais complexas.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧩",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se dividires a palavra 'Bo-la', qual é a primeira sílaba?",
                        options = listOf("LA", "BO", "BA"),
                        correctAnswer = "BO",
                        explanation = "A primeira sílaba de Bo-la é BO!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas sílabas podes contar na palavra 'Laranja'?",
                        options = listOf("2", "3", "4"),
                        correctAnswer = "3",
                        explanation = "La-ran-ja tem exatamente 3 sílabas (damos 3 palmas)!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e3",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as sílabas para formar a palavra: CA - BO - NE (Boneca)",
                        options = listOf("BO", "NE", "CA"),
                        correctAnswers = listOf("BO", "NE", "CA"),
                        explanation = "BO + NE + CA = BONECA! Um brinquedo muito bonito."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e4",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra: CO - ___ (para formar COPO)",
                        options = listOf("PO", "MO", "LO"),
                        correctAnswer = "PO",
                        explanation = "CO + PO forma a palavra COPO!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Pato' tem as mesmas sílabas que 'Topo', mas por ordem diferente.",
                        correctAnswer = "false",
                        explanation = "Falso! Pato tem as sílabas PA e TO. Topo tem TO e PO. A sílaba PO é diferente de PA."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas palavras tem 3 sílabas?",
                        options = listOf("Pato", "Menino", "Mala"),
                        correctAnswer = "Menino",
                        explanation = "Me-ni-no tem 3 sílabas. Pato e Mala têm apenas 2 sílabas!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as sílabas para formar: PA - SA - TO (Sapato)",
                        options = listOf("SA", "PA", "TO"),
                        correctAnswers = listOf("SA", "PA", "TO"),
                        explanation = "SA + PA + TO forma a palavra SAPATO! 👟"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f9_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a palavra 'Caneta': CA - ___ - TA",
                        options = listOf("NE", "ME", "PE"),
                        correctAnswer = "NE",
                        explanation = "CA + NE + TA = CANETA! Usamo-la para escrever."
                    )
                )
            ),
            // Ficha 10
            Worksheet(
                id = "pt_1_f10",
                numero = 10,
                title = "Palavras e Imagens",
                description = "Liga e associa as palavras escritas às respetivas imagens corretas.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🖼️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f10_e1",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens representa a palavra 'Casa'?",
                        options = listOf("🚗 Carro", "🏠 Casa", "🌳 Árvore"),
                        correctAnswer = "🏠 Casa",
                        explanation = "Muito bem! Leste 'Casa' e associaste corretamente à imagem da casa."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que palavra corresponde a esta imagem: 🐈?",
                        options = listOf("CÃO", "GATO", "RATO"),
                        correctAnswer = "GATO",
                        explanation = "A imagem mostra um gato amoroso! 🐈"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens corresponde à palavra 'Livro'?",
                        options = listOf("✏️ Lápis", "📖 Livro", "🎒 Mochila"),
                        correctAnswer = "📖 Livro",
                        explanation = "Muito bem! Leste 'Livro' e escolheste o livro de histórias."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que palavra corresponde a esta imagem: 🚗?",
                        options = listOf("CARRO", "MOTA", "BARCO"),
                        correctAnswer = "CARRO",
                        explanation = "A imagem mostra um carro em movimento!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A imagem 🍎 corresponde à palavra 'Maçã'.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É uma bela maçã vermelha!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe a imagem que corresponde à palavra 'Sol':",
                        options = listOf("☀️ Sol", "🌙 Lua", "⭐ Estrela"),
                        correctAnswer = "☀️ Sol",
                        explanation = "A palavra 'Sol' corresponde à imagem do Sol radiante!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que palavra corresponde a esta imagem: 🐟?",
                        options = listOf("PATO", "PEIXE", "GATO"),
                        correctAnswer = "PEIXE",
                        explanation = "A imagem mostra um peixinho que nada na água!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f10_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A imagem 🍌 corresponde à palavra 'Laranja'.",
                        correctAnswer = "false",
                        explanation = "Falso! A imagem mostra uma banana saborosa."
                    )
                )
            ),
            // Ficha 11
            Worksheet(
                id = "pt_1_f11",
                numero = 11,
                title = "Pequenas Frases",
                description = "Inicia a leitura de pequenas frases simples e aprende a estruturá-las.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "💬",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas frases diz exatamente 'O Rui lê.'?",
                        options = listOf("O Rui lê.", "A Ana lê.", "O Rui vê."),
                        correctAnswer = "O Rui lê.",
                        explanation = "Leste perfeitamente! A frase correta é: 'O Rui lê.'"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a frase: 'A Ana vê a ___' (com base na imagem ⚽)",
                        options = listOf("bola.", "casa.", "mala."),
                        correctAnswer = "bola.",
                        explanation = "Lendo a frase completa: 'A Ana vê a bola.'"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e3",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as palavras para formar a frase correta: mia. O gato",
                        options = listOf("O", "gato", "mia."),
                        correctAnswers = listOf("O", "gato", "mia."),
                        explanation = "Ordenando as palavras temos: 'O gato mia.'"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na frase 'A Ana vê a bola.', quem vê a bola é a Ana.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A Ana é o nome próprio de quem está a ver a bola."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que faz o Rui na frase 'O Rui lê.'?",
                        options = listOf("Corre", "Lê", "Dorme"),
                        correctAnswer = "Lê",
                        explanation = "O Rui está a ler um livro de histórias giras!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e6",
                        type = ExerciseType.COMPLETING,
                        question = "Completa o som do gato: 'O gato ___'",
                        options = listOf("mia.", "ladra.", "canta."),
                        correctAnswer = "mia.",
                        explanation = "A frase correta é: 'O gato mia.' (Miau!)"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e7",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as palavras para formar a frase correta: lê. O Rui",
                        options = listOf("O", "Rui", "lê."),
                        correctAnswers = listOf("O", "Rui", "lê."),
                        explanation = "A frase ordenada é: 'O Rui lê.'"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f11_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas frases descreve melhor a imagem de uma casa bonita (🏠)?",
                        options = listOf("A casa é bonita.", "A bola rola.", "O pato nada."),
                        correctAnswer = "A casa é bonita.",
                        explanation = "A frase 'A casa é bonita.' descreve perfeitamente a imagem da casa!"
                    )
                )
            ),
            // Ficha 12
            Worksheet(
                id = "pt_1_f12",
                numero = 12,
                title = "Revisão Geral de Português",
                description = "Uma ficha final completa para testar tudo o que aprendeste de Português!",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏅",
                exercises = listOf(
                    WorksheetExercise(
                        id = "pt_1_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destas letras NÃO é uma vogal?",
                        options = listOf("E", "M", "U"),
                        correctAnswer = "M",
                        explanation = "As vogais são A, E, I, O, U. A letra M é uma consoante!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a minúscula da consoante P?",
                        options = listOf("q", "d", "p"),
                        correctAnswer = "p",
                        explanation = "O P maiúsculo corresponde ao p minúsculo!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se juntares P-A com T-O, que palavra formamos?",
                        options = listOf("PAPA", "PATO", "GATO"),
                        correctAnswer = "PATO",
                        explanation = "P-A (pa) + T-O (to) = PATO! 🦆"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A palavra 'Mala' começa com a consoante M e termina com a vogal A.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! M-a-l-a começa com M e termina com A!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a primeira letra da palavra 'Casa'?",
                        options = listOf("K", "C", "S"),
                        correctAnswer = "C",
                        explanation = "Casa começa com a letra C!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe a fruta que começa com a vogal 'U'!",
                        options = listOf("🍇 Uva", "🍎 Maçã", "🍊 Laranja"),
                        correctAnswer = "🍇 Uva",
                        explanation = "Uva começa com a vogal U! (U-va)."
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas sílabas podes ouvir na palavra 'Sa-pa-to'?",
                        options = listOf("2", "3", "4"),
                        correctAnswer = "3",
                        explanation = "Bate palmas: Sa-pa-to! Tem 3 sílabas!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a frase correta: 'O gato ___'",
                        options = listOf("mia.", "come.", "corre."),
                        correctAnswer = "mia.",
                        explanation = "O gato mia!"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e9",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as palavras para formar a frase correta: a bola. Ana vê A",
                        options = listOf("A", "Ana", "vê", "a bola."),
                        correctAnswers = listOf("A", "Ana", "vê", "a bola."),
                        explanation = "Ordenando corretamente formamos a frase: 'A Ana vê a bola.'"
                    ),
                    WorksheetExercise(
                        id = "pt_1_f12_e10",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os nomes próprios (nomes de pessoas) escrevem-se com letra inicial maiúscula.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Nomes próprios de pessoas ou lugares escrevem-se sempre com letra inicial maiúscula."
                    )
                )
            )
        )
    )

    val matematica = WorksheetDiscipline(
        id = "mat_1",
        title = "Matemática",
        emoji = "🧮",
        color = Color(0xFF10B981),
        worksheets = listOf(
            // Ficha 1
            Worksheet(
                id = "mat_1_f1",
                numero = 1,
                title = "Números de 1 a 10",
                description = "Aprende a contar, sequências e comparação de números de 1 a 10.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🔢",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que número vem a seguir ao 4?",
                        options = listOf("3", "5", "7"),
                        correctAnswer = "5",
                        explanation = "Ao contar (1, 2, 3, 4, 5...), o número que vem imediatamente depois do 4 é o 5!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a sequência: 1, 2, __, 4, 5",
                        options = listOf("2", "3", "6"),
                        correctAnswer = "3",
                        explanation = "O número que falta para completar a sequência é o 3! (1, 2, 3, 4, 5)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 8 é maior do que o número 5.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 8 representa uma quantidade maior do que 5."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e4",
                        type = ExerciseType.COUNTING,
                        question = "Quantas estrelas vês a brilhar no céu?",
                        countItems = "⭐⭐⭐⭐⭐⭐",
                        options = listOf("4", "6", "8"),
                        correctAnswer = "6",
                        explanation = "Contando uma a uma: 1, 2, 3, 4, 5 e 6 estrelas cintilantes!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se tiveres 3 maçãs 🍎🍎🍎 e comeres 1, com quantas ficas?",
                        options = listOf("🍎 1 Maçã", "🍎🍎 2 Maçãs", "🍎🍎🍎 3 Maçãs"),
                        correctAnswer = "🍎🍎 2 Maçãs",
                        explanation = "Se tinhas 3 e tiras 1, sobram exatamente 2! (3 - 1 = 2)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destes números é o mais pequeno?",
                        options = listOf("9", "2", "6"),
                        correctAnswer = "2",
                        explanation = "O número 2 é o mais pequeno dos três. Vem logo no início da contagem."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e7",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número que vem antes do 10 é o 9.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Na contagem, antes de dizermos 10, dizemos 9."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa o caminho: 6, 7, 8, __, 10",
                        options = listOf("7", "9", "5"),
                        correctAnswer = "9",
                        explanation = "Depois do 8 vem o 9! (6, 7, 8, 9, 10)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e9",
                        type = ExerciseType.COUNTING,
                        question = "Quantos corações consegues contar?",
                        countItems = "💖💖💖💖💖",
                        options = listOf("3", "5", "6"),
                        correctAnswer = "5",
                        explanation = "Contámos exatamente 5 corações cor-de-rosa fofinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f1_e10",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o número correto de dedos numa mão completa?",
                        options = listOf("4", "5", "6"),
                        correctAnswer = "5",
                        explanation = "Cada uma das nossas mãos tem exatamente 5 dedos!"
                    )
                )
            ),
            // Ficha 2
            Worksheet(
                id = "mat_1_f2",
                numero = 2,
                title = "Somas e Subtrações",
                description = "Inicia-te nas contas simples de somar (+) e subtrair (-).",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "➕",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se juntares 2 lápis com mais 1 lápis, com quantos ficas?",
                        options = listOf("3", "4", "5"),
                        correctAnswer = "3",
                        explanation = "2 + 1 = 3 lápis no total!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a conta: 3 + 2 = ___",
                        options = listOf("4", "5", "6"),
                        correctAnswer = "5",
                        explanation = "Se tens 3 e adicionas 2, ficas com 5!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A conta 4 + 4 é igual a 8.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se contares 4 dedos de uma mão e 4 da outra, dá 8 dedos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se tiveres 5 chocolates e deres 2 à tua mãe, com quantos ficas?",
                        options = listOf("2", "3", "4"),
                        correctAnswer = "3",
                        explanation = "5 - 2 = 3. Sobram 3 chocolates deliciosos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e5",
                        type = ExerciseType.COMPLETING,
                        question = "Completa: 6 - 1 = ___",
                        options = listOf("4", "5", "6"),
                        correctAnswer = "5",
                        explanation = "Tirando 1 ao número 6, recuamos para o 5!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se fizeres 3 + 3 ficas com 7.",
                        correctAnswer = "false",
                        explanation = "Falso! 3 + 3 é igual a 6 e não a 7."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O João tinha 4 balões 🎈🎈🎈🎈. Dois balões voaram. Com quantos ficou?",
                        options = listOf("1", "2", "3"),
                        correctAnswer = "2",
                        explanation = "4 - 2 = 2. O João ficou com 2 balões!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a conta: 5 + 5 = ___",
                        options = listOf("8", "9", "10"),
                        correctAnswer = "10",
                        explanation = "5 + 5 é igual a 10! É o número total de dedos das duas mãos juntas."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e9",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se tiveres 2 gomas e te derem mais 2, ficas com 4 gomas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 2 + 2 = 4 gomas."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f2_e10",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o resultado de 7 - 3?",
                        options = listOf("3", "4", "5"),
                        correctAnswer = "4",
                        explanation = "Se tens 7 brinquedos e tiras 3, ficas com 4!"
                    )
                )
            ),
            // Ficha 3
            Worksheet(
                id = "mat_1_f3",
                numero = 3,
                title = "Formas Geométricas",
                description = "Descobre e distingue o círculo, quadrado, triângulo e retângulo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📐",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f3_e1",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas formas é redondinha como o Sol?",
                        options = listOf("🔴 Círculo", "🔺 Triângulo", "🟩 Quadrado"),
                        correctAnswer = "🔴 Círculo",
                        explanation = "O círculo é perfeitamente redondo como o Sol ou uma bola! 🔴"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e2",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos lados iguais tem um quadrado?",
                        options = listOf("3", "4", "5"),
                        correctAnswer = "4",
                        explanation = "O quadrado tem 4 lados exatamente iguais e 4 biquinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas formas parece uma fatia de pizza e tem 3 lados?",
                        options = listOf("🟩 Quadrado", "🔴 Círculo", "🔺 Triângulo"),
                        correctAnswer = "🔺 Triângulo",
                        explanation = "O triângulo tem 3 lados and 3 cantos! É a forma da fatia de pizza ou de um telhado. 🔺"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um retângulo tem 4 lados, mas não são todos iguais.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O retângulo tem 2 lados mais compridos e 2 mais curtos."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se uma forma não tem nenhum lado reto nem nenhum bico, qual é?",
                        options = listOf("Quadrado", "Círculo", "Triângulo"),
                        correctAnswer = "Círculo",
                        explanation = "É o círculo! Ele é todo curvo e não tem linhas retas nem cantos."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma caixa de sapatos parece-se com a forma de um triângulo.",
                        correctAnswer = "false",
                        explanation = "Falso! Uma caixa de sapatos parece-se com um retângulo."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e7",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada objeto à sua forma geométrica:",
                        pairs = mapOf(
                            "Bola" to "Círculo",
                            "Janela" to "Quadrado",
                            "Fatia de Pizza" to "Triângulo",
                            "Livro" to "Retângulo"
                        ),
                        explanation = "Boa! A bola é redonda (círculo), a janela é quadrangular (quadrado), a pizza é triangular (triângulo) e o livro é retangular (retângulo)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos cantos (bicos) tem um retângulo?",
                        options = listOf("3", "4", "5"),
                        correctAnswer = "4",
                        explanation = "Tal como o quadrado, o retângulo tem exatamente 4 cantos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e9",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O triângulo tem 4 lados.",
                        correctAnswer = "false",
                        explanation = "Falso! O triângulo tem apenas 3 lados (daí o nome 'tri-ângulo')."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f3_e10",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas imagens representa um Quadrado?",
                        options = listOf("🔺 Triângulo", "🟩 Quadrado", "🔴 Círculo"),
                        correctAnswer = "🟩 Quadrado",
                        explanation = "O quadrado é a forma verde 🟩, com 4 lados iguaizinhos!"
                    )
                )
            ),
            // Ficha 4
            Worksheet(
                id = "mat_1_f4",
                numero = 4,
                title = "Medidas e Comparações",
                description = "Aprende a diferenciar tamanhos, pesos, posições e capacidades.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📏",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "A girafa é um animal...",
                        options = listOf("Baixo", "Alto", "Leve"),
                        correctAnswer = "Alto",
                        explanation = "A girafa tem um pescoço muito comprido, por isso é um animal muito alto!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um eletrante é muito mais leve do que um gatinho.",
                        correctAnswer = "false",
                        explanation = "Falso! O elefante é enorme e muito pesado, enquanto o gatinho é leve."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes objetos está CHEIO?",
                        options = listOf("🥛 Copo de leite cheio", "🫙 Frasco vazio", "🍽️ Prato sem comida"),
                        correctAnswer = "🥛 Copo de leite cheio",
                        explanation = "O copo de leite está cheio, pronto a beber! 🥛"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se meteres água num balde e numa chávena, qual leva MAIS água?",
                        options = listOf("O balde", "A chávena", "Levam o mesmo"),
                        correctAnswer = "O balde",
                        explanation = "O balde é muito maior, logo tem mais capacidade de levar água do que a pequena chávena!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma formiga é muito mais pequena do que um cão.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A formiga é minúscula comparada com um cão."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes brinquedos é o mais PESADO?",
                        options = listOf("🎈 Balão de ar", "🧸 Urso de pelúcia", "🚲 Bicicleta de ferro"),
                        correctAnswer = "🚲 Bicicleta de ferro",
                        explanation = "A bicicleta feita de ferro é a mais pesada e difícil de carregar! 🚲"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde está o gatinho se ele subiu para cima do telhado?",
                        options = listOf("Em baixo", "Ao lado", "Em cima"),
                        correctAnswer = "Em cima",
                        explanation = "Subir para o telhado significa que o gatinho ficou em cima!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma caneta é mais comprida (mais longa) do que um grão de arroz.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A caneta mede muito mais centímetros do que um pequeno grão de arroz."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e9",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se colocares a bola dentro da caixa, onde fica a bola?",
                        options = listOf("Fora", "Dentro", "Atrás"),
                        correctAnswer = "Dentro",
                        explanation = "Fica guardada no interior da caixa, ou seja, está dentro!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f4_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena do MAIS PEQUENO para o MAIOR:",
                        options = listOf("Formiga", "Cão", "Elefante"),
                        correctAnswers = listOf("Formiga", "Cão", "Elefante"),
                        correctAnswer = "Formiga-Cão-Elefante",
                        explanation = "A formiga é a mais pequena, o cão é médio, e o elefante é o maior de todos!"
                    )
                )
            ),
            // Ficha 5
            Worksheet(
                id = "mat_1_f5",
                numero = 5,
                title = "Números até 20 e Dezenas",
                description = "Explora o grupo dos 10 (dezena), unidades e contagens divertidas até 20.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🔟",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas unidades formam exatamente UMA DEZENA?",
                        options = listOf("5 unidades", "10 unidades", "20 unidades"),
                        correctAnswer = "10 unidades",
                        explanation = "Uma dezena é um grupo de 10 unidades juntas!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a sequência: 10, 11, 12, __, 14, 15",
                        options = listOf("13", "16", "9"),
                        correctAnswer = "13",
                        explanation = "Depois do 12 vem o 13! (10, 11, 12, 13, 14, 15)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 15 é formado por 1 dezena e 5 unidades soltas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 10 + 5 = 15 (1 dezena e 5 unidades)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e4",
                        type = ExerciseType.COUNTING,
                        question = "Quantos lápis de cor vês no estojo?",
                        countItems = "✏️✏️✏️✏️✏️✏️✏️✏️✏️✏️✏️✏️",
                        options = listOf("10", "12", "15"),
                        correctAnswer = "12",
                        explanation = "Contando um a um: temos exatamente 12 lápis (uma dúzia)!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que número vem imediatamente antes do 20?",
                        options = listOf("18", "19", "21"),
                        correctAnswer = "19",
                        explanation = "Na contagem regressiva antes de 20 dizemos 19!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 20 é formado por 2 dezenas completas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 10 + 10 = 20, ou seja, 2 dezenas."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas opções representa o número 14?",
                        options = listOf("🔟 + 4 ⭐", "🔟 + 8 ⭐", "🔟 + 1 ⭐"),
                        correctAnswer = "🔟 + 4 ⭐",
                        explanation = "1 dezena (10) mais 4 unidades dá 14! 🔟+4⭐"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e8",
                        type = ExerciseType.COMPLETING,
                        question = "Contagem de 2 em 2: 2, 4, 6, 8, __, 12",
                        options = listOf("9", "10", "11"),
                        correctAnswer = "10",
                        explanation = "Avançando de 2 em 2: 2, 4, 6, 8, 10, 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e9",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se tiveres 1 dezena de rebuçados e ganhares mais 7, quantos tens?",
                        options = listOf("16", "17", "18"),
                        correctAnswer = "17",
                        explanation = "10 + 7 = 17 deliciosos rebuçados!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f5_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os números em ordem CRESCENTE (do menor para o maior):",
                        options = listOf("19", "11", "15"),
                        correctAnswers = listOf("11", "15", "19"),
                        correctAnswer = "11-15-19",
                        explanation = "O 11 é o menor, seguido de 15 e por fim 19!"
                    )
                )
            ),
            // Ficha 6
            Worksheet(
                id = "mat_1_f6",
                numero = 6,
                title = "Somas e Subtrações até 20",
                description = "Pratica o cálculo mental e resolve desafios matemáticos divertidos.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧮",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quanto é 10 + 6?",
                        options = listOf("14", "16", "18"),
                        correctAnswer = "16",
                        explanation = "10 mais 6 é igual a 16!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a conta: 15 - 5 = ___",
                        options = listOf("10", "11", "5"),
                        correctAnswer = "10",
                        explanation = "Se tens 15 e tiras 5, ficas com 10 certinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e3",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se tinhas 12 berlindes e perdeste 2, ficas com 10 berlindes.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 12 - 2 = 10 berlindes."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Zé apanhou 8 maçãs 🍎 e a Maria apanhou 4 🍏. Quantas apanharam juntos?",
                        options = listOf("10", "12", "14"),
                        correctAnswer = "12",
                        explanation = "8 + 4 = 12 maçãs no cesto!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas contas dá como resultado o número 20?",
                        options = listOf("10 + 10", "15 + 2", "12 + 4"),
                        correctAnswer = "10 + 10",
                        explanation = "10 + 10 = 20, uma conta redonda perfeita!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A conta 18 - 8 é igual a 10.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Ao tirar as 8 unidades a 18, sobra a dezena completa (10)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e7",
                        type = ExerciseType.COMPLETING,
                        question = "Completa: 7 + ___ = 10",
                        options = listOf("2", "3", "4"),
                        correctAnswer = "3",
                        explanation = "O amigo do 7 para fazer 10 é o 3! (7 + 3 = 10)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Havia 16 passarinhos no ramo. Voaram 3. Quantos ficaram?",
                        options = listOf("11", "13", "15"),
                        correctAnswer = "13",
                        explanation = "16 - 3 = 13 passarinhos no ramo!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e9",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O dobro de 5 é 10 (5 + 5 = 10).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O dobro significa somar o número a si próprio."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f6_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada conta ao seu resultado:",
                        pairs = mapOf(
                            "10 + 5" to "15",
                            "10 + 8" to "18",
                            "20 - 1" to "19",
                            "14 - 4" to "10"
                        ),
                        explanation = "Excelente cálculo mental! 10+5=15, 10+8=18, 20-1=19 e 14-4=10."
                    )
                )
            ),
            // Ficha 7
            Worksheet(
                id = "mat_1_f7",
                numero = 7,
                title = "Figuras e Sólidos Geométricos",
                description = "Distingue formas planas e corpos redondos (cubo, esfera, cilindro).",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧊",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Uma bola de futebol parece-se com qual destes sólidos geométricos?",
                        options = listOf("Cubo", "Esfera", "Cone"),
                        correctAnswer = "Esfera",
                        explanation = "A bola de futebol é redonda em todas as direções, logo é uma esfera!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Um dado de jogar com 6 faces quadradas é um Cubo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O cubo tem 6 faces quadradas e todas iguais."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes objetos tem o formato de um Cilindro?",
                        options = listOf("🥫 Lata de sumo / refrigerante", "📦 Caixa cúbica", "⚽ Bola de ténis"),
                        correctAnswer = "🥫 Lata de sumo / refrigerante",
                        explanation = "Uma lata é comprida e tem duas bases redondas, parecendo um cilindro! 🥫"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O chapéu de festa de aniversário em bico parece-se com um...",
                        options = listOf("Cone", "Cubo", "Esfera"),
                        correctAnswer = "Cone",
                        explanation = "O chapéu pontiagudo tem a forma geométrica de um cone!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma moeda pousada na mesa tem a face em forma de círculo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A superfície plana de uma moeda é um círculo perfeito."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos lados retos tem um Quadrado?",
                        options = listOf("3 lados", "4 lados iguais", "5 lados"),
                        correctAnswer = "4 lados iguais",
                        explanation = "O quadrado tem 4 lados exatamente do mesmo comprimento!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe a figura que tem apenas 3 lados e 3 cantos:",
                        options = listOf("🔺 Triângulo", "⬛ Quadrado", "⚪ Círculo"),
                        correctAnswer = "🔺 Triângulo",
                        explanation = "O triângulo tem 3 lados retos e 3 vértices! 🔺"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Os sólidos que conseguem rolar no chão são aqueles que têm superfícies curvas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A esfera e o cilindro rolam facilmente porque têm partes redondas e curvas."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e9",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Um tijolo de construção ou um estojo retangular têm a forma de...",
                        options = listOf("Paralelepípedo (Bloco retangular)", "Esfera", "Cone"),
                        correctAnswer = "Paralelepípedo (Bloco retangular)",
                        explanation = "Um tijolo ou caixa de sapatos é um paralelepípedo com faces retangulares!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f7_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa o objeto do dia a dia ao seu sólido:",
                        pairs = mapOf(
                            "Dado de jogo" to "Cubo",
                            "Planeta Terra" to "Esfera",
                            "Vela redonda" to "Cilindro",
                            "Cone de gelado" to "Cone"
                        ),
                        explanation = "Perfeito! Identificaste todos os sólidos no nosso dia a dia."
                    )
                )
            ),
            // Ficha 8
            Worksheet(
                id = "mat_1_f8",
                numero = 8,
                title = "Padrões e Sequências Divertidas",
                description = "Descobre o segredo das sequências lógicas com cores, formas e números.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🧩",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f8_e1",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que emoji vem a seguir no padrão: 🔴 🔵 🔴 🔵 🔴 ___?",
                        options = listOf("🔵 Círculo Azul", "🔴 Círculo Vermelho", "🟢 Círculo Verde"),
                        correctAnswer = "🔵 Círculo Azul",
                        explanation = "O padrão alterna vermelho e azul: o próximo é o azul 🔵!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e2",
                        type = ExerciseType.COMPLETING,
                        question = "Descobre o número que falta: 1, 3, 5, __, 9",
                        options = listOf("6", "7", "8"),
                        correctAnswer = "7",
                        explanation = "Esta é a sequência dos números ímpares (a somar 2): 1, 3, 5, 7, 9!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o fruto seguinte: 🍎 🍌 🍎 🍌 🍎 ___?",
                        options = listOf("🍌 Banana", "🍎 Maçã", "🍇 Uvas"),
                        correctAnswer = "🍌 Banana",
                        explanation = "O padrão é maçã, banana, maçã, banana... logo a seguir à maçã vem a banana 🍌!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na sequência 2, 4, 6, 8, 10 estamos sempre a somar 2 ao número anterior.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! É a sequência dos números pares de 2 em 2."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e5",
                        type = ExerciseType.COMPLETING,
                        question = "Completa o caminho: 5, 10, 15, __",
                        options = listOf("16", "20", "25"),
                        correctAnswer = "20",
                        explanation = "Avançando de 5 em 5: 5, 10, 15, 20!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é a forma seguinte no padrão: ⭐️ 🌙 ⭐️ 🌙 ⭐️ ___?",
                        options = listOf("🌙 Lua", "⭐️ Estrela", "☀️ Sol"),
                        correctAnswer = "🌙 Lua",
                        explanation = "A seguir à estrela vem a lua 🌙!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Na sequência decrescente 10, 9, 8, 7, __, qual é o próximo número?",
                        options = listOf("6", "5", "8"),
                        correctAnswer = "6",
                        explanation = "Estamos a recuar de 1 em 1: 10, 9, 8, 7, 6!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O padrão 🐶 🐱 🐶 🐱 🐶 🐱 repete-se de 2 em 2 animais.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O bloco que se repete é cão e gato."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e9",
                        type = ExerciseType.COMPLETING,
                        question = "Completa a sequência: 20, 18, 16, 14, __",
                        options = listOf("12", "13", "10"),
                        correctAnswer = "12",
                        explanation = "A subtrair 2 de cada vez: 20, 18, 16, 14, 12!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f8_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena a sequência de contagem correta:",
                        options = listOf("3, 6, 9", "12, 15, 18", "0, 0, 0"),
                        correctAnswers = listOf("3, 6, 9", "12, 15, 18", "0, 0, 0"),
                        correctAnswer = "3, 6, 9-12, 15, 18-0, 0, 0",
                        explanation = "Contando de 3 em 3: 3, 6, 9 depois 12, 15, 18!"
                    )
                )
            ),
            // Ficha 9
            Worksheet(
                id = "mat_1_f9",
                numero = 9,
                title = "Comparar Quantidades e Números",
                description = "Usa os sinais de maior (>), menor (<), igual (=) e pares/ímpares.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⚖️",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Entre o número 14 e o número 9, qual é o MAIOR?",
                        options = listOf("14", "9", "São iguais"),
                        correctAnswer = "14",
                        explanation = "14 tem 1 dezena e 4 unidades, logo é muito maior do que 9!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "7 é menor do que 12 (7 < 12).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O número 7 representa uma quantidade mais pequena do que 12."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e3",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual destes números é um número PAR?",
                        options = listOf("3", "6", "7"),
                        correctAnswer = "6",
                        explanation = "6 é par porque podemos dividir em 2 grupos iguais de 3 sem sobrar nada!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e4",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O número 5 é um número ÍMPAR.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se tentarmos fazer pares com 5 peças, sobra 1 peça solta."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e5",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes grupos tem MAIS elementos?",
                        options = listOf("🐱🐱🐱 3 Gatos", "🐶🐶🐶🐶🐶 5 Cães", "🐰 1 Coelho"),
                        correctAnswer = "🐶🐶🐶🐶🐶 5 Cães",
                        explanation = "5 cães é o grupo com maior quantidade! 🐶"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e6",
                        type = ExerciseType.COMPLETING,
                        question = "Qual é o sinal correto entre 8 e 8? 8 ___ 8",
                        options = listOf("=", ">", "<"),
                        correctAnswer = "=",
                        explanation = "8 é igual a 8, usamos o sinal de igual (=)!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o menor número desta lista: 18, 12, 16, 7?",
                        options = listOf("18", "12", "7"),
                        correctAnswer = "7",
                        explanation = "7 é o único número com menos de uma dezena, logo é o menor!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "10 + 5 é igual a 15.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! As duas expressões valem exatamente o mesmo."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e9",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os números do MAIOR para o MENOR (ordem decrescente):",
                        options = listOf("20", "8", "14"),
                        correctAnswers = listOf("20", "14", "8"),
                        correctAnswer = "20-14-8",
                        explanation = "Começamos no maior (20), depois o 14 e por fim o menor (8)!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f9_e10",
                        type = ExerciseType.MATCHING,
                        question = "Distingue os números em Pares e Ímpares:",
                        pairs = mapOf(
                            "Número 4" to "Par",
                            "Número 7" to "Ímpar",
                            "Número 10" to "Par",
                            "Número 9" to "Ímpar"
                        ),
                        explanation = "Muito bem! 4 e 10 são pares; 7 e 9 são ímpares."
                    )
                )
            ),
            // Ficha 10
            Worksheet(
                id = "mat_1_f10",
                numero = 10,
                title = "O Relógio, Horas e o Tempo",
                description = "Aprende a ler as horas certas no relógio e os dias da semana.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⏰",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "No relógio de ponteiros, qual é o ponteiro que aponta para as HORAS?",
                        options = listOf("O ponteiro pequeno", "O ponteiro grande", "Nenhum dos dois"),
                        correctAnswer = "O ponteiro pequeno",
                        explanation = "O ponteiro pequeno (mais curto) indica sempre as horas!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Quando o ponteiro grande está a apontar para o 12, temos uma hora certa (hora em ponto).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O 12 indica os 00 minutos, ou seja, hora em ponto."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se o ponteiro pequeno aponta para o 3 e o grande para o 12, que horas são?",
                        options = listOf("⏰ 3 horas em ponto", "⏰ 12 horas em ponto", "⏰ 6 horas"),
                        correctAnswer = "⏰ 3 horas em ponto",
                        explanation = "São exatamente 3 horas (03:00)! ⏰"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas horas tem um dia completo (dia e noite)?",
                        options = listOf("12 horas", "24 horas", "60 horas"),
                        correctAnswer = "24 horas",
                        explanation = "Um dia completo tem 24 horas divididas entre manhã, tarde e noite!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O dia da semana que vem logo a seguir ao Domingo é a Segunda-feira.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A Segunda-feira é o primeiro dia útil de aulas da semana."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos dias tem o fim de semana (Sábado e Domingo)?",
                        options = listOf("1 dia", "2 dias", "4 dias"),
                        correctAnswer = "2 dias",
                        explanation = "O fim de semana é composto por 2 dias de descanso: Sábado e Domingo!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Em que período do dia tomamos o pequeno-almoço e vamos para a escola?",
                        options = listOf("🌅 De manhã", "🌙 À meia-noite", "🌆 Ao final da tarde"),
                        correctAnswer = "🌅 De manhã",
                        explanation = "De manhã o Sol nasce e começamos o nosso dia cheio de energia! 🌅"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma hora tem 60 minutos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O ponteiro dos minutos dá uma volta inteira com 60 passos."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e9",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os momentos do teu dia do início ao fim:",
                        options = listOf("Acordar de manhã", "Almoçar ao meio-dia", "Dormir à noite"),
                        correctAnswers = listOf("Acordar de manhã", "Almoçar ao meio-dia", "Dormir à noite"),
                        correctAnswer = "Acordar de manhã-Almoçar ao meio-dia-Dormir à noite",
                        explanation = "Primeiro acordamos, depois almoçamos e no final vamos dormir!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f10_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa a atividade ao momento do dia mais comum:",
                        pairs = mapOf(
                            "Ver as estrelas no céu" to "Noite",
                            "Entrar na escola" to "Manhã",
                            "Fazer o lanche da tarde" to "Tarde",
                            "Almoçar na cantina" to "Meio-dia"
                        ),
                        explanation = "Excelente sentido de organização do tempo diário!"
                    )
                )
            ),
            // Ficha 11
            Worksheet(
                id = "mat_1_f11",
                numero = 11,
                title = "Dinheiro e Moedas de Euro (€)",
                description = "Descobre as moedas de Euro, compras simples e noções de poupança.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🪙",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a moeda oficial utilizada em Portugal e na Europa?",
                        options = listOf("Dólar", "Euro (€)", "Libra"),
                        correctAnswer = "Euro (€)",
                        explanation = "Em Portugal usamos o Euro (€) e os seus cêntimos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Duas moedas de 1 Euro juntas valem 2 Euros (1€ + 1€ = 2€).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 1€ + 1€ perfazem exatamente uma moeda de 2€."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Um caderno custa 3€. Se pagares com uma nota de 5€, quanto recebes de troco?",
                        options = listOf("🪙 2 Euros de troco", "🪙 1 Euro de troco", "🪙 4 Euros de troco"),
                        correctAnswer = "🪙 2 Euros de troco",
                        explanation = "5€ - 3€ = 2€ de troco que a caixa te devolve! 🪙"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas moedas de 50 cêntimos são precisas para fazer 1 Euro inteiro?",
                        options = listOf("2 moedas", "5 moedas", "10 moedas"),
                        correctAnswer = "2 moedas",
                        explanation = "50 cêntimos + 50 cêntimos = 100 cêntimos = 1 Euro!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Uma nota de 10€ vale mais dinheiro do que uma moeda de 2€.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! 10€ é cinco vezes maior do que 2€."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se comprares um gelado por 2€ e um sumo por 1€, quanto gastas no total?",
                        options = listOf("3€", "4€", "5€"),
                        correctAnswer = "3€",
                        explanation = "2€ + 1€ = 3€ no total da compra."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Onde deves guardar as tuas moedas para poupar dinheiro para o futuro?",
                        options = listOf("🐷 Mealheiro", "🗑️ Caixote do lixo", "🌧️ No chão da rua"),
                        correctAnswer = "🐷 Mealheiro",
                        explanation = "Guardar no mealheiro é a melhor forma de poupar para comprar algo especial! 🐷"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As notas de euro são feitas de papel especial e têm cores diferentes para cada valor.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A nota de 5€ é cinzenta, a de 10€ é vermelha, a de 20€ é azul, etc."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e9",
                        type = ExerciseType.COMPLETING,
                        question = "Se tens 4 moedas de 1€ no bolso, quantos euros tens? ___ €",
                        options = listOf("3", "4", "5"),
                        correctAnswer = "4",
                        explanation = "1 + 1 + 1 + 1 = 4 Euros!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f11_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os valores em dinheiro do MENOR para o MAIOR:",
                        options = listOf("50 cêntimos", "2 Euros", "10 Euros"),
                        correctAnswers = listOf("50 cêntimos", "2 Euros", "10 Euros"),
                        correctAnswer = "50 cêntimos-2 Euros-10 Euros",
                        explanation = "50 cêntimos é menor que 2€, e 2€ é menor que uma nota de 10€!"
                    )
                )
            ),
            // Ficha 12
            Worksheet(
                id = "mat_1_f12",
                numero = 12,
                title = "Grandes Desafios Matemáticos do Zé",
                description = "Põe à prova o teu raciocínio com enigmas e problemas lógicos!",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "🏆",
                exercises = listOf(
                    WorksheetExercise(
                        id = "mat_1_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Num ninho havia 5 passarinhos. Nasceram mais 3 e 2 voaram. Quantos ficaram?",
                        options = listOf("6", "8", "4"),
                        correctAnswer = "6",
                        explanation = "5 + 3 = 8 passarinhos. Depois 8 - 2 = 6 passarinhos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Se uma caixa tem 10 bombons e comes metade, sobram 5 bombons.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A metade de 10 é 5 (5 + 5 = 10)."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Se cada bicicleta tem 2 rodas 🚲, quantas rodas têm 3 bicicletas juntas?",
                        options = listOf("🚲🚲🚲 6 rodas", "🚲🚲 4 rodas", "🚲 2 rodas"),
                        correctAnswer = "🚲🚲🚲 6 rodas",
                        explanation = "2 + 2 + 2 = 6 rodas no total! 🚲"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e4",
                        type = ExerciseType.COMPLETING,
                        question = "Qual é o número secreto: É maior que 14 e menor que 16. É o ___",
                        options = listOf("13", "15", "17"),
                        correctAnswer = "15",
                        explanation = "O número que está no meio de 14 e 16 é o 15!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e5",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O Zé tem 7 anos e a sua irmã tem mais 3 anos. Quantos anos tem a irmã?",
                        options = listOf("9 anos", "10 anos", "11 anos"),
                        correctAnswer = "10 anos",
                        explanation = "7 + 3 = 10 anos de idade!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e6",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Somar zero a qualquer número não altera o valor desse número (ex: 8 + 0 = 8).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O zero representa nada adicionado, logo o valor mantém-se."
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é a figura geométrica que tem 4 cantos e 4 lados iguais?",
                        options = listOf("🟩 Quadrado", "🔺 Triângulo", "🔴 Círculo"),
                        correctAnswer = "🟩 Quadrado",
                        explanation = "O quadrado tem 4 lados exatamente do mesmo tamanho! 🟩"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e8",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Se tiveres 20 berlindes e deres 10 ao teu melhor amigo, com quantos ficas?",
                        options = listOf("5", "10", "15"),
                        correctAnswer = "10",
                        explanation = "20 - 10 = 10 berlindes guardados!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e9",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os passos para resolver um problema de matemática:",
                        options = listOf("Ler o problema com atenção", "Fazer a conta ou desenho", "Escrever a resposta final"),
                        correctAnswers = listOf("Ler o problema com atenção", "Fazer a conta ou desenho", "Escrever a resposta final"),
                        correctAnswer = "Ler o problema com atenção-Fazer a conta ou desenho-Escrever a resposta final",
                        explanation = "Primeiro compreendemos a pergunta, depois calculamos e por fim respondemos!"
                    ),
                    WorksheetExercise(
                        id = "mat_1_f12_e10",
                        type = ExerciseType.MATCHING,
                        question = "Liga cada conta ao seu resultado de génio da matemática:",
                        pairs = mapOf(
                            "5 + 5 + 5" to "15",
                            "10 + 10" to "20",
                            "12 - 2" to "10",
                            "9 + 0" to "9"
                        ),
                        explanation = "Parabéns, Campeão da Matemática do 1.º Ano! Completaste todos os desafios!"
                    )
                )
            )
        )
    )

    val estudoMeio = WorksheetDiscipline(
        id = "em_1",
        title = "Estudo do Meio",
        emoji = "🌍🌱",
        color = Color(0xFFF59E0B),
        worksheets = listOf(
            // Ficha 1
            Worksheet(
                id = "em_1_f1",
                numero = 1,
                title = "O Corpo Humano e os Sentidos",
                description = "Explora o teu corpo, os 5 sentidos e as funções de cada órgão.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🧍",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f1_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde ficam localizados os nossos olhos, o nariz e a boca?",
                        options = listOf("Nas pernas", "Na cabeça", "Nas costas"),
                        correctAnswer = "Na cabeça",
                        explanation = "Os olhos, o nariz, as orelhas e a boca fazem todos parte da nossa cara, que fica na cabeça!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O coração faz 'tum-tum' e bombeia o sangue por todo o corpo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O coração é um músculo incrível que funciona como o motor do nosso corpo."
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o órgão que usamos para VER as cores e as formas?",
                        options = listOf("👂 Orelhas / Ouvido", "👁️ Olhos", "👃 Nariz"),
                        correctAnswer = "👁️ Olhos",
                        explanation = "Usamos a nossa visão, através dos olhos, para ver tudo o que nos rodeia! 👁️"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas mãos temos no nosso corpo para agarrar brinquedos?",
                        options = listOf("1", "2", "3"),
                        correctAnswer = "2",
                        explanation = "Temos 2 braços e 2 mãos (a mão esquerda e a mão direita)!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Usamos o ouvido (audição) para cheirar o perfume das flores.",
                        correctAnswer = "false",
                        explanation = "Falso! Usamos o ouvido para ouvir os sons. Para cheirar as flores, usamos o nariz (olfato)!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que sentido ou órgão usamos para SABOREAR um gelado delicioso?",
                        options = listOf("👅 Língua (Paladar)", "🖐️ Mão (Tato)", "👁️ Olho (Visão)"),
                        correctAnswer = "👅 Língua (Paladar)",
                        explanation = "Através das papilas da nossa língua sentimos o sabor doce, salgado ou azedo! 👅"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Com que parte do corpo corremos e chutamos a bola?",
                        options = listOf("Com a cabeça", "Com os braços", "Com as pernas e pés"),
                        correctAnswer = "Com as pernas e pés",
                        explanation = "As pernas e os pés dão-nos equilíbrio e força para correr, saltar e chutar!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A nossa pele permite-nos sentir se um objeto é macio ou áspero.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O tato está espalhado por toda a nossa pele, especialmente nas pontas dos dedos."
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Escolhe o órgão do sentido que deteta o som da música 🎵!",
                        options = listOf("👃 Nariz", "👂 Orelha (Audição)", "👁️ Olhos"),
                        correctAnswer = "👂 Orelha (Audição)",
                        explanation = "As nossas orelhas e ouvidos captam as ondas sonoras e permitem-nos ouvir música! 👂"
                    ),
                    WorksheetExercise(
                        id = "em_1_f1_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada sentido ao seu órgão:",
                        pairs = mapOf(
                            "Visão" to "Olhos",
                            "Audição" to "Orelhas",
                            "Olfato" to "Nariz",
                            "Paladar" to "Língua"
                        ),
                        explanation = "Excelente! Associaste cada um dos sentidos aos respetivos órgãos do corpo humano."
                    )
                )
            ),
            // Ficha 2
            Worksheet(
                id = "em_1_f2",
                numero = 2,
                title = "Higiene e Saúde",
                description = "Bons hábitos diários para nos mantermos limpos, cheirosos e cheios de saúde.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🪥",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f2_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que deves usar para lavar bem os dentes depois de comer?",
                        options = listOf("Pente e gel", "Champô", "Escova e pasta de dentes"),
                        correctAnswer = "Escova e pasta de dentes",
                        explanation = "Devemos escovar bem os dentes com escova e pasta para evitar as cáries!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos lavar bem as mãos com água e sabão antes das refeições.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Lavar as mãos elimina os germes e vírus invisíveis que nos podem adoecer."
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "O que usamos para limpar e secar a nossa pele após um bom banho?",
                        options = listOf("🧼 Sabonete", "🛀 Toalha", "🪥 Escova de dentes"),
                        correctAnswer = "🛀 Toalha",
                        explanation = "Usamos uma toalha limpa e macia para nos secarmos muito bem! 🛀"
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas vezes por dia, no mínimo, deves lavar os dentes?",
                        options = listOf("1 vez", "Pelo menos 2 vezes", "De 10 em 10 dias"),
                        correctAnswer = "Pelo menos 2 vezes",
                        explanation = "Devemos escovar os dentes pelo menos duas vezes ao dia: ao levantar e antes de deitar!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Está correto partilhar a nossa escova de dentes com os amigos.",
                        correctAnswer = "false",
                        explanation = "Falso! A escova de dentes é de uso estritamente pessoal e não deve ser partilhada, para evitar contágios."
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes hábitos deves fazer todas as noites para o teu cérebro descansar?",
                        options = listOf("📺 Ver televisão até tarde", "😴 Dormir cedo (8 a 10 horas)", "🍬 Comer muitos doces"),
                        correctAnswer = "😴 Dormir cedo (8 a 10 horas)",
                        explanation = "Dormir bem permite ao nosso corpo e mente recarregar energias para o dia seguinte! 😴"
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que deves fazer quando tossires ou espirrares para proteger os outros?",
                        options = listOf("Tossir para o ar", "Cobrir a boca com o cotovelo ou lenço", "Não fazer nada"),
                        correctAnswer = "Cobrir a boca com o cotovelo ou lenço",
                        explanation = "Ao usar o cotovelo interno ou um lenço de papel, evitamos espalhar gotículas com vírus pelo ar."
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos tomar banho regularmente para manter o corpo limpo e livre de odores.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O banho limpa a sujidade da pele e deixa-nos frescos e cheirosos."
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que objeto usamos para manter o nosso cabelo bonito e desembaraçado?",
                        options = listOf("🪥 Escova de dentes", "🧼 Esponja", "🪮 Pente ou Escova de Cabelo"),
                        correctAnswer = "🪮 Pente ou Escova de Cabelo",
                        explanation = "O pente ou a escova de cabelo servem para pentear e cuidar do nosso cabelo! 🪮"
                    ),
                    WorksheetExercise(
                        id = "em_1_f2_e10",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Por que deves cortar as unhas regularmente?",
                        options = listOf("Para ficarem mais compridas", "Para não acumular sujidade e micróbios por baixo delas", "Para doerem"),
                        correctAnswer = "Para não acumular sujidade e micróbios por baixo delas",
                        explanation = "Unhas curtas e limpas evitam a acumulação de bactérias que nos podem contaminar ao comer."
                    )
                )
            ),
            // Ficha 3
            Worksheet(
                id = "em_1_f3",
                numero = 3,
                title = "A Família e a Escola",
                description = "Reconhece o papel dos teus familiares, as regras da escola e a convivência social.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🏫",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f3_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem são os pais do teu pai ou da tua mãe?",
                        options = listOf("Os teus tios", "Os teus avós", "Os teus primos"),
                        correctAnswer = "Os teus avós",
                        explanation = "Os pais dos nossos pais são os nossos queridos avós!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na escola, devemos respeitar os professores e ajudar os nossos colegas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O respeito e a entreajuda tornam a escola um sítio feliz e seguro para todos."
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Onde deves deitar os teus papéis e lixo quando estás na sala de aula?",
                        options = listOf("🧹 No chão", "🗑️ No caixote do lixo", "🎒 Dentro da mochila"),
                        correctAnswer = "🗑️ No caixote do lixo",
                        explanation = "Devemos manter a sala limpa deitando sempre o lixo no caixote! 🗑️"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama o filho do teu tio ou da tua tia?",
                        options = listOf("Teu irmão", "Teu primo", "Teu sobrinho"),
                        correctAnswer = "Teu primo",
                        explanation = "Os filhos dos nossos tios são os nossos primos, com quem adoramos brincar!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No recreio da escola, está correto empurrar os colegas na fila do escorrega.",
                        correctAnswer = "false",
                        explanation = "Falso! Devemos esperar pacientemente pela nossa vez para evitar acidentes e discussões."
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que divisão da escola é usada para fazer as refeições como o almoço?",
                        options = listOf("⚽ Ginásio / Recreio", "🏫 Sala de aula", "🍽️ Cantina / Refeitório"),
                        correctAnswer = "🍽️ Cantina / Refeitório",
                        explanation = "A cantina ou refeitório é o espaço preparado para almoçarmos com higiene e conforto! 🍽️"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a palavra 'mágica' que deves usar quando pedes um brinquedo emprestado?",
                        options = listOf("Dá-me já!", "Por favor", "Sai da frente"),
                        correctAnswer = "Por favor",
                        explanation = "Dizer 'Por favor' e 'Obrigado' são regras básicas de boa educação!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A nossa família é formada apenas pelas pessoas que vivem na mesma casa.",
                        correctAnswer = "false",
                        explanation = "Falso! Os tios, primos e avós também fazem parte da nossa família, mesmo que morem noutras casas."
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e9",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que deves fazer quando o professor está a falar com a turma?",
                        options = listOf("Gritar alto", "Ouvir com atenção e em silêncio", "Fazer desenhos na mesa"),
                        correctAnswer = "Ouvir com atenção e em silêncio",
                        explanation = "Ouvir quando os outros falam demonstra respeito e ajuda-nos a aprender a matéria!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f3_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os passos corretos para pedir a palavra na sala:",
                        options = listOf("Esperar que o professor dê autorização", "Falar com educação", "Levantar o dedo em silêncio"),
                        correctAnswers = listOf("Levantar o dedo em silêncio", "Esperar que o professor dê autorização", "Falar com educação"),
                        correctAnswer = "Levantar o dedo em silêncio-Esperar que o professor dê autorização-Falar com educação",
                        explanation = "Primeiro levantamos o dedo, esperamos a nossa vez e depois falamos com calma!"
                    )
                )
            ),
            // Ficha 4
            Worksheet(
                id = "em_1_f4",
                numero = 4,
                title = "Os Animais e as Plantas",
                description = "Aprende a distinguir animais, partes das plantas e como proteger a natureza.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🌱",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f4_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama um animal que pode viver connosco em casa, como o cão?",
                        options = listOf("Animal Selvagem", "Animal Doméstico", "Animal Aquático"),
                        correctAnswer = "Animal Doméstico",
                        explanation = "Os animais domésticos (como cães ou gatos) estão habituados a viver em harmonia com os humanos!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O leão é um exemplo de animal selvagem que vive livre na natureza.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O leão vive livre nas savanas e florestas e não deve ser mantido em casa."
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas partes da planta segura a planta à terra e absorve a água?",
                        options = listOf("🍃 Folhas", "🌸 Flores", "🪵 Raiz"),
                        correctAnswer = "🪵 Raiz",
                        explanation = "A raiz cresce debaixo da terra para segurar a planta e sugar a água e os nutrientes! 🪵"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que é que a maioria das plantas precisa para crescer saudável?",
                        options = listOf("Sumo e gomas", "Terra, água e luz do Sol", "Escuridão e vento frio"),
                        correctAnswer = "Terra, água e luz do Sol",
                        explanation = "Nas plantas necessitam de luz solar, água fresca e nutrientes para se desenvolverem!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Todos os animais nascem de ovos.",
                        correctAnswer = "false",
                        explanation = "Falso! Alguns animais nascem de ovos (como os pássaros), mas outros nascem da barriga da mãe (como os cães e nós)!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes animais voa e tem o corpo coberto de penas?",
                        options = listOf("🐟 Peixe", "🦜 Pássaro", "🐸 Rã"),
                        correctAnswer = "🦜 Pássaro",
                        explanation = "Os pássaros são aves, têm asas para voar e penas que os protegem! 🦜"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a parte colorida e cheirosa de uma planta, que depois pode dar fruto?",
                        options = listOf("O caule", "A flor", "A raiz"),
                        correctAnswer = "A flor",
                        explanation = "As flores embelezam a planta, atraem as abelhas e transformam-se em frutos saborosos!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos regar as plantas e não arrancar as suas folhas sem necessidade.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Devemos respeitar e cuidar de todos os seres vivos da nossa natureza."
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes animais vive sempre debaixo de água e respira por guelras?",
                        options = listOf("🐕 Cão", "🐟 Peixe", "🐒 Macaco"),
                        correctAnswer = "🐟 Peixe",
                        explanation = "Os peixes são animais aquáticos com escamas e barbatanas, perfeitamente adaptados à vida na água! 🐟"
                    ),
                    WorksheetExercise(
                        id = "em_1_f4_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as etapas de vida de uma planta (do início ao fim):",
                        options = listOf("Cresce e dá flores", "Semente na terra", "Nasce uma pequena raiz"),
                        correctAnswers = listOf("Semente na terra", "Nasce uma pequena raiz", "Cresce e dá flores"),
                        correctAnswer = "Semente na terra-Nasce uma pequena raiz-Cresce e dá flores",
                        explanation = "Primeiro plantamos a semente, depois ela cria raízes e finalmente cresce até dar flores!"
                    )
                )
            ),
            // Ficha 5
            Worksheet(
                id = "em_1_f5",
                numero = 5,
                title = "Alimentação Saudável",
                description = "A Roda dos Alimentos, frutas, legumes, água e refeições equilibradas.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🥗",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f5_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a bebida mais importante para manter o corpo hidratado e saudável?",
                        options = listOf("Refrigerantes com gás", "Água pura", "Sumos com muito açúcar"),
                        correctAnswer = "Água pura",
                        explanation = "A água é essencial à vida e devemos beber vários copos ao longo do dia!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos comer frutas e legumes todos os dias para ter vitaminas e crescer com força.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! A fruta e os vegetais fornecem vitaminas, fibras e minerais indispensáveis."
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destas opções é um lanche saudável para levar para a escola?",
                        options = listOf("🍎 Fruta fresca e pão integral", "🍬 Pacote de gomas doces", "🍟 Batatas fritas de pacote"),
                        correctAnswer = "🍎 Fruta fresca e pão integral",
                        explanation = "Uma maçã e uma sandes de pão com queijo dão energia saudável para estudar e brincar! 🍎"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é a refeição mais importante da manhã para começares o dia com energia?",
                        options = listOf("Pequeno-almoço", "Ceia da noite", "Lanche da meia-noite"),
                        correctAnswer = "Pequeno-almoço",
                        explanation = "O pequeno-almoço acorda o nosso corpo e prepara a nossa mente para as aulas!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A Roda dos Alimentos ensina-nos que devemos comer muitos doces e pouca sopa.",
                        correctAnswer = "false",
                        explanation = "Falso! A Roda dos Alimentos ensina que os doces devem ser consumidos raramente, enquanto a sopa e os legumes devem ser diários."
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes alimentos pertence ao grupo dos laticínios (ricos em cálcio para os ossos)?",
                        options = listOf("🥛 Leite / Iogurte", "🥩 Carne", "🥔 Batata"),
                        correctAnswer = "🥛 Leite / Iogurte",
                        explanation = "O leite, o queijo e o iogurte fortalecem os nossos ossos e dentes! 🥛"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Antes de comermos frutas cruas como a maçã ou pera, o que devemos fazer?",
                        options = listOf("Lavá-las muito bem com água limpa", "Pintá-las com lápis", "Deixá-las no chão"),
                        correctAnswer = "Lavá-las muito bem com água limpa",
                        explanation = "Lavar a fruta retira poeiras e impurezas para podermos comer com segurança!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O peixe e os ovos fornecem proteínas importantes para a construção dos nossos músculos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O peixe, ovos e carne ajudam o nosso corpo a crescer forte."
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes pratos é uma entrada rica em legumes para o almoço?",
                        options = listOf("🍲 Prato de sopa de legumes", "🎂 Bolo de aniversário", "🍭 Chupa-chupa"),
                        correctAnswer = "🍲 Prato de sopa de legumes",
                        explanation = "A sopa de legumes quentinha é reconfortante e cheia de saúde! 🍲"
                    ),
                    WorksheetExercise(
                        id = "em_1_f5_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada alimento ao seu grupo:",
                        pairs = mapOf(
                            "Laranja" to "Fruta",
                            "Cenoura" to "Hortícola (Legume)",
                            "Leite" to "Laticínio",
                            "Pão" to "Cereais"
                        ),
                        explanation = "Excelente! Conheces muito bem os grupos da Roda dos Alimentos."
                    )
                )
            ),
            // Ficha 6
            Worksheet(
                id = "em_1_f6",
                numero = 6,
                title = "Os Nossos Dentes e a Saúde Oral",
                description = "Cuida do teu sorriso: dentes de leite, escovagem correta e o dentista.",
                difficulty = WorksheetDifficulty.EASY,
                imageEmoji = "🦷",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f6_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chamam os primeiros dentes que nascem quando somos bebés e que depois caem?",
                        options = listOf("Dentes de ferro", "Dentes de leite", "Dentes de ouro"),
                        correctAnswer = "Dentes de leite",
                        explanation = "Os primeiros dentes são os dentes de leite, que mais tarde dão lugar aos dentes definitivos!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As cáries dentárias são pequenos buraquinhos provocados por bactérias e restos de açúcar nos dentes.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Se não escovarmos os dentes, o açúcar alimenta as bactérias que furam o esmalte."
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o médico especialista a quem devemos ir para examinar e tratar dos dentes?",
                        options = listOf("🦷 Médico Dentista", "👁️ Oftalmologista (Olhos)", "🐕 Veterinário"),
                        correctAnswer = "🦷 Médico Dentista",
                        explanation = "O dentista cuida da saúde do nosso sorriso e ensina-nos a escovar bem! 🦷"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Durante quanto tempo deves escovar os dentes para ficarem bem limpinhos?",
                        options = listOf("5 segundos", "Pelo menos 2 minutos", "1 hora"),
                        correctAnswer = "Pelo menos 2 minutos",
                        explanation = "Dois minutos (ou o tempo de cantar uma canção divertida) é o ideal para escovar todas as faces dos dentes!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos trocar de escova de dentes quando os seus pelos estiverem velhos ou estragados (a cada 3 meses).",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Uma escova em bom estado limpa muito melhor sem magoar as gengivas."
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes produtos usamos com a escova para proteger os dentes com flúor?",
                        options = listOf("🪥 Pasta de dentes", "🧼 Sabão da roupa", "🧴 Creme hidratante"),
                        correctAnswer = "🪥 Pasta de dentes",
                        explanation = "A pasta dentífrica com flúor protege o esmalte contra os ácidos! 🪥"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que movimento circular suave deves fazer com a escova nos dentes?",
                        options = listOf("Movimentos em círculos suaves e da gengiva para o dente", "Bater com força", "Não mexer a escova"),
                        correctAnswer = "Movimentos em círculos suaves e da gengiva para o dente",
                        explanation = "Movimentos circulares 'do vermelho da gengiva para o branco do dente' limpam sem agredir!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Comer rebuçados colados aos dentes antes de dormir sem escovar faz muito bem ao esmalte.",
                        correctAnswer = "false",
                        explanation = "Falso! O açúcar colado durante a noite é o maior causador de cáries."
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que fio fininho se usa para retirar restos de comida presos entre os dentes?",
                        options = listOf("🧵 Fio dentário", "🧶 Lã de tricotar", "🔌 Cabo elétrico"),
                        correctAnswer = "🧵 Fio dentário",
                        explanation = "O fio dentário limpa onde a escova não consegue chegar! 🧵"
                    ),
                    WorksheetExercise(
                        id = "em_1_f6_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena os passos para escovar os dentes:",
                        options = listOf("Colocar uma ervilha de pasta na escova", "Escovar em círculos durante 2 minutos", "Bochechar e enxaguar a boca"),
                        correctAnswers = listOf("Colocar uma ervilha de pasta na escova", "Escovar em círculos durante 2 minutos", "Bochechar e enxaguar a boca"),
                        correctAnswer = "Colocar uma ervilha de pasta na escova-Escovar em círculos durante 2 minutos-Bochechar e enxaguar a boca",
                        explanation = "Colocamos a pasta, escovamos minuciosamente e enxaguamos com água fresca!"
                    )
                )
            ),
            // Ficha 7
            Worksheet(
                id = "em_1_f7",
                numero = 7,
                title = "Os Sentidos no Dia a Dia",
                description = "Como usamos a visão, audição, tato, olfato e paladar para explorar o mundo.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "👀",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f7_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o sentido que nos alerta para o som da sirene de uma ambulância?",
                        options = listOf("Audição", "Paladar", "Olfato"),
                        correctAnswer = "Audição",
                        explanation = "Ouvimos a sirene através dos nossos ouvidos, avisando-nos para dar passagem!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Com o tato podemos sentir se um casaco é quente e macio ou áspero.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os recetores na nossa pele informam-nos sobre a textura e temperatura."
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que sentido usamos para sentir o cheiro a pão quente a sair do forno?",
                        options = listOf("👃 Olfato (Nariz)", "👁️ Visão (Olhos)", "👂 Audição (Ouvidos)"),
                        correctAnswer = "👃 Olfato (Nariz)",
                        explanation = "O nariz capta os odores no ar através do olfato! 👃"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quando chupamos um limão, que sabor ácido o nosso paladar identifica?",
                        options = listOf("Azedo / Ácido", "Muito Doce", "Salgado"),
                        correctAnswer = "Azedo / Ácido",
                        explanation = "O limão é caracterizado pelo seu sabor cítrico e azedo!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Devemos olhar diretamente para o Sol sem óculos escuros porque faz muito bem aos olhos.",
                        correctAnswer = "false",
                        explanation = "Falso! Olhar diretamente para o Sol pode queimar a retina e danificar seriamente a visão."
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes objetos usamos para proteger os nossos ouvidos de ruídos muito fortes?",
                        options = listOf("🎧 Protetores auriculares / Auscultadores", "🕶️ Óculos de sol", "🧤 Luvas de lã"),
                        correctAnswer = "🎧 Protetores auriculares / Auscultadores",
                        explanation = "Os protetores protegem a sensibilidade dos nossos ouvidos contra sons ensurdecedores! 🎧"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o sentido que nos permite ler as palavras deste ecrã ou de um livro?",
                        options = listOf("Visão", "Tato", "Olfato"),
                        correctAnswer = "Visão",
                        explanation = "Com os nossos olhos enxergamos as letras, cores e imagens!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "As pessoas cegas podem ler através do tato usando o sistema de relevo Braille.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Braille utiliza pontinhos em relevo sentidos com as pontas dos dedos."
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que sentido usas para provar uma fatia de melancia fresca e doce?",
                        options = listOf("👅 Paladar", "👂 Audição", "🖐️ Tato"),
                        correctAnswer = "👅 Paladar",
                        explanation = "A língua deteta a doçura e frescura da melancia! 👅"
                    ),
                    WorksheetExercise(
                        id = "em_1_f7_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa a sensação ao sentido que a identifica:",
                        pairs = mapOf(
                            "Gelo muito frio" to "Tato",
                            "Perfume da rosa" to "Olfato",
                            "Música da flauta" to "Audição",
                            "Arco-íris colorido" to "Visão"
                        ),
                        explanation = "Fantástico! Compreendes perfeitamente a importância dos teus 5 sentidos!"
                    )
                )
            ),
            // Ficha 8
            Worksheet(
                id = "em_1_f8",
                numero = 8,
                title = "O Clima e as Estações do Ano",
                description = "Descobre as quatro estações: Primavera, Verão, Outono e Inverno.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "⛅",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f8_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantas estações tem um ano completo?",
                        options = listOf("2 estações", "4 estações", "12 estações"),
                        correctAnswer = "4 estações",
                        explanation = "O ano tem 4 estações: Primavera, Verão, Outono e Inverno!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No Verão os dias são mais compridos, faz muito calor e vamos à praia.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Verão é a estação do calor, do Sol brilhante e das férias da praia."
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Em que estação as árvores ficam cheias de flores coloridas e nascem passarinhos?",
                        options = listOf("🌸 Primavera", "❄️ Inverno", "🍂 Outono"),
                        correctAnswer = "🌸 Primavera",
                        explanation = "Na Primavera a natureza renasce cheia de flores perfumadas e borboletas! 🌸"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Em que estação as folhas das árvores mudam de cor, secam e caem no chão?",
                        options = listOf("Outono", "Verão", "Primavera"),
                        correctAnswer = "Outono",
                        explanation = "No Outono as folhas ficam castanhas e amarelas e caem ao chão, e comemos castanhas assadas!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "No Inverno devemos usar calções de banho na rua para nos mantermos quentinhos.",
                        correctAnswer = "false",
                        explanation = "Falso! No Inverno faz frio e chuva, por isso usamos casacos grossos, gorro e cachecol."
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "O que usamos para nos proteger da chuva quando caminhamos na rua?",
                        options = listOf("☔ Guarda-chuva", "🕶️ Óculos de sol", "🩴 Chinelos de praia"),
                        correctAnswer = "☔ Guarda-chuva",
                        explanation = "O guarda-chuva e as galochas mantêm-nos secos em dias de chuva! ☔"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a água congelada que cai do céu em flocos branquinhos no Inverno?",
                        options = listOf("Neve", "Chuva de sumo", "Vento"),
                        correctAnswer = "Neve",
                        explanation = "A neve cai nas montanhas mais frias e podemos fazer bonecos de neve!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O Sol é uma estrela que nos dá luz e calor para podermos viver.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O Sol aquece a Terra e permite a vida das plantas, animais e pessoas."
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes fenómenos naturais tem 7 cores lindas e surge quando chove e faz sol ao mesmo tempo?",
                        options = listOf("🌈 Arco-íris", "⚡ Trovoada", "🌪️ Furacão"),
                        correctAnswer = "🌈 Arco-íris",
                        explanation = "O arco-íris aparece quando a luz solar atravessa as gotinhas de chuva! 🌈"
                    ),
                    WorksheetExercise(
                        id = "em_1_f8_e10",
                        type = ExerciseType.ORDERING,
                        question = "Ordena o ciclo das quatro estações do ano:",
                        options = listOf("Primavera", "Verão", "Outono"),
                        correctAnswers = listOf("Primavera", "Verão", "Outono"),
                        correctAnswer = "Primavera-Verão-Outono",
                        explanation = "Depois da Primavera vem o Verão, seguido do Outono e depois o Inverno!"
                    )
                )
            ),
            // Ficha 9
            Worksheet(
                id = "em_1_f9",
                numero = 9,
                title = "Segurança na Escola e na Estrada",
                description = "Regras de trânsito para peões, passadeiras, semáforos e prevenção de acidentes.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🚦",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f9_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Onde deves atravessar a rua com segurança quando és peão?",
                        options = listOf("Na passadeira", "A correr no meio dos carros", "Entre camiões estacionados"),
                        correctAnswer = "Na passadeira",
                        explanation = "As riscas brancas da passadeira indicam o local seguro e com prioridade para os peões!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Quando o semáforo de peões está Vermelho 🔴, deves parar e esperar no passeio.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O vermelho significa 'Perigo, pára!'. Só atravessamos no Verde 🟢."
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "O que deves colocar sempre logo que entras no carro antes de a viagem começar?",
                        options = listOf("💺 Cinto de segurança", "🎮 Consola de jogos", "🍭 Chupa-chupa"),
                        correctAnswer = "💺 Cinto de segurança",
                        explanation = "O cinto de segurança e a cadeirinha protegem a tua vida em qualquer travagem! 💺"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O que deves usar na cabeça quando andas de bicicleta ou trotinete?",
                        options = listOf("Capacete de proteção", "Gorro de lã fino", "Nada"),
                        correctAnswer = "Capacete de proteção",
                        explanation = "O capacete protege a cabeça contra pancadas em caso de queda!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Podemos mexer nas tomadas elétricas com as mãos molhadas.",
                        correctAnswer = "false",
                        explanation = "Falso! A eletricidade e a água são muito perigosas e podem dar choques elétricos graves. Nunca mexas em tomadas!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o número de telefone de emergência gratuito em Portugal para chamar bombeiros, polícia ou ambulância?",
                        options = listOf("🚨 112", "📞 999", "🔢 123"),
                        correctAnswer = "🚨 112",
                        explanation = "O 112 é o número europeu de emergência para qualquer socorro urgente! 🚨"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Antes de atravessar a passadeira, o que deves fazer com a cabeça?",
                        options = listOf("Olhar para a esquerda, para a direita e outra vez para a esquerda", "Olhar só para os sapatos", "Fechar os olhos"),
                        correctAnswer = "Olhar para a esquerda, para a direita e outra vez para a esquerda",
                        explanation = "Olhamos para ambos os lados para ter a certeza absoluta de que todos os carros pararam!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Na escola, devemos descer as escadas a correr e aos empurrões para chegar primeiro.",
                        correctAnswer = "false",
                        explanation = "Falso! Nas escadas devemos andar com calma e segurar no corrimão para evitar quedas."
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Onde devem os peões caminhar quando andam na rua junto à estrada?",
                        options = listOf("🚶 No passeio", "🚗 No meio da estrada", "🚲 Na linha do comboio"),
                        correctAnswer = "🚶 No passeio",
                        explanation = "O passeio é a zona exclusiva e protegida para os peões caminharem! 🚶"
                    ),
                    WorksheetExercise(
                        id = "em_1_f9_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa a cor do semáforo ao seu significado para o peão:",
                        pairs = mapOf(
                            "Boneco Vermelho" to "Parar no passeio",
                            "Boneco Verde" to "Avançar com atenção",
                            "Cinto no carro" to "Segurança na viagem",
                            "Capacete" to "Proteção na bicicleta"
                        ),
                        explanation = "Excelente! És um verdadeiro campeão da segurança e prevenção rodoviária!"
                    )
                )
            ),
            // Ficha 10
            Worksheet(
                id = "em_1_f10",
                numero = 10,
                title = "O Meu Passado e o Tempo",
                description = "O teu crescimento, a tua história pessoal, o calendário e os meses do ano.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "📅",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f10_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como eras quando nasceste, antes de aprenderes a andar e a falar?",
                        options = listOf("Um bebé pequenino", "Um adulto com barba", "Um velhinho"),
                        correctAnswer = "Um bebé pequenino",
                        explanation = "Todos nascemos como bebés indefesos e vamos crescendo dia após dia!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O dia do teu aniversário celebra a data e o mês em que nasceste.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Todos os anos celebramos mais um ano de vida no nosso aniversário."
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que objeto usamos na parede para ver os dias, semanas e meses do ano?",
                        options = listOf("📅 Calendário", "⏰ Despertador", "📏 Régua"),
                        correctAnswer = "📅 Calendário",
                        explanation = "O calendário mostra-nos os 12 meses e todos os dias do ano! 📅"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quantos meses tem um ano inteiro?",
                        options = listOf("12 meses", "7 meses", "24 meses"),
                        correctAnswer = "12 meses",
                        explanation = "O ano é composto por 12 meses: de Janeiro até Dezembro!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "'Ontem' significa o dia de amanhã que ainda vai acontecer.",
                        correctAnswer = "false",
                        explanation = "Falso! 'Ontem' é o dia que já passou (passado). O dia que vem a seguir é 'amanhã' (futuro)."
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e6",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Qual é o primeiro mês do ano civil?",
                        options = listOf("Janeiro", "Agosto", "Dezembro"),
                        correctAnswer = "Janeiro",
                        explanation = "Janeiro é o mês número 1, onde celebramos o Ano Novo!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e7",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Em que mês festejamos o Natal e as luzes de fim de ano?",
                        options = listOf("🎄 Dezembro", "☀️ Julho", "🌸 Abril"),
                        correctAnswer = "🎄 Dezembro",
                        explanation = "Dezembro é o último mês do ano e quando comemoramos o Natal em família! 🎄"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "À medida que crescemos, aprendemos coisas novas como ler, escrever e andar de bicicleta.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O crescimento físico e as nossas aprendizagens fazem parte da nossa evolução."
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e9",
                        type = ExerciseType.ORDERING,
                        question = "Ordena as fases da vida humana do início para o fim:",
                        options = listOf("Bebé no berço", "Criança na escola", "Adulto a trabalhar"),
                        correctAnswers = listOf("Bebé no berço", "Criança na escola", "Adulto a trabalhar"),
                        correctAnswer = "Bebé no berço-Criança na escola-Adulto a trabalhar",
                        explanation = "Primeiro somos bebés, depois crianças, jovens, adultos e idosos!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f10_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa os conceitos de tempo à sua ideia correta:",
                        pairs = mapOf(
                            "Ontem" to "Dia que já passou",
                            "Hoje" to "Dia presente que estamos a viver",
                            "Amanhã" to "Dia seguinte que vai chegar",
                            "Ano" to "Tem 12 meses"
                        ),
                        explanation = "Perfeito! Dominas a noção de passagem do tempo e da história pessoal!"
                    )
                )
            ),
            // Ficha 11
            Worksheet(
                id = "em_1_f11",
                numero = 11,
                title = "Meios de Transporte e Comunicação",
                description = "Transportes da terra, água e ar, cartas, telefone e a Internet.",
                difficulty = WorksheetDifficulty.MEDIUM,
                imageEmoji = "🚀",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f11_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O comboio desloca-se sobre carris na terra. É um meio de transporte...",
                        options = listOf("Terrestre", "Aquático / Marítimo", "Aéreo"),
                        correctAnswer = "Terrestre",
                        explanation = "Todos os transportes que andam por estradas ou carris na terra são terrestres!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O avião e o helicóptero voam no céu, logo são transportes aéreos.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Deslocam-se pelo ar a grande velocidade."
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes transportes navega sobre as águas dos rios e oceanos?",
                        options = listOf("🚢 Barco / Navio", "🚌 Autocarro", "🚗 Automóvel"),
                        correctAnswer = "🚢 Barco / Navio",
                        explanation = "Os barcos, navios e veleiros são meios de transporte aquáticos! 🚢"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "O autocarro e o metro que levam muitas pessoas ao mesmo tempo são transportes...",
                        options = listOf("Públicos / Coletivos", "Privados individuais", "Espaciais"),
                        correctAnswer = "Públicos / Coletivos",
                        explanation = "Transportes coletivos ajudam a reduzir o trânsito e a poluição nas cidades!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Andar a pé ou de bicicleta é ecológico porque não polui o ar com fumo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Não usam combustíveis fósseis e fazem muito bem à saúde e ao ambiente."
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Que aparelho usamos para falar com um familiar que está longe por voz ou vídeo?",
                        options = listOf("📱 Telemóvel / Telefone", "🪑 Cadeira", "🥣 Tigela"),
                        correctAnswer = "📱 Telemóvel / Telefone",
                        explanation = "O telemóvel liga-nos instantaneamente às pessoas que gostamos em qualquer parte do mundo! 📱"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Como se chama a mensagem escrita em papel guardada num envelope e entregue pelo carteiro?",
                        options = listOf("Carta postal", "Televisão", "Rádio"),
                        correctAnswer = "Carta postal",
                        explanation = "As cartas postais com selo são levadas pelos carteiros até à caixa de correio!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "A televisão, o rádio e os jornais são meios de comunicação social que informam muitas pessoas.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Transmitem notícias, desenhos animados e música para todo o país."
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual destes transportes rápidos viaja até ao espaço sideral fora do planeta Terra?",
                        options = listOf("🚀 Foguetão espacial", "🚜 Trator agrícola", "🚲 Troti"),
                        correctAnswer = "🚀 Foguetão espacial",
                        explanation = "Os foguetões levam os astronautas até à Estação Espacial e à Lua! 🚀"
                    ),
                    WorksheetExercise(
                        id = "em_1_f11_e10",
                        type = ExerciseType.MATCHING,
                        question = "Classifica cada meio de transporte pelo seu meio:",
                        pairs = mapOf(
                            "Avião" to "Aéreo (Céu)",
                            "Navio" to "Aquático (Mar)",
                            "Automóvel" to "Terrestre (Estrada)",
                            "Comboio" to "Terrestre (Carris)"
                        ),
                        explanation = "Excelente classificação de todos os meios de transporte e comunicação!"
                    )
                )
            ),
            // Ficha 12
            Worksheet(
                id = "em_1_f12",
                numero = 12,
                title = "Profissões e a Comunidade",
                description = "Descobre as diferentes profissões e como todos colaboram na sociedade.",
                difficulty = WorksheetDifficulty.CHALLENGE,
                imageEmoji = "👷",
                exercises = listOf(
                    WorksheetExercise(
                        id = "em_1_f12_e1",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem é o profissional corajoso que apaga incêndios e ajuda em inundações e resgates?",
                        options = listOf("Bombeiro", "Padeiro", "Pintor"),
                        correctAnswer = "Bombeiro",
                        explanation = "Os bombeiros estão sempre prontos para salvar vidas e proteger a floresta!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e2",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O professor na escola ensina as matérias, ajuda os alunos a aprender e a respeitar os outros.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Os professores dedicam-se à educação e formação das crianças."
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e3",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Qual é o profissional da saúde que examina os doentes nos hospitais e receita remédios?",
                        options = listOf("🩺 Médico / Enfermeiro", "👨‍🍳 Cozinheiro", "🔨 Carpinteiro"),
                        correctAnswer = "🩺 Médico / Enfermeiro",
                        explanation = "Os médicos e enfermeiros cuidam da nossa saúde com muito carinho! 🩺"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e4",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Quem cultiva a terra nos campos para produzir vegetais, batatas e cereais?",
                        options = listOf("Agricultor", "Astronauta", "Mecânico"),
                        correctAnswer = "Agricultor",
                        explanation = "Os agricultores trabalham no campo com amor à terra para produzir a nossa comida!"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e5",
                        type = ExerciseType.TRUE_FALSE,
                        question = "O padeiro faz pão quentinho e estaladiço na padaria todos os dias bem cedo.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! O padeiro acorda de madrugada para preparar o pão fresco para o pequeno-almoço."
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e6",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quem garante a ordem e a segurança dos cidadãos nas ruas da nossa cidade?",
                        options = listOf("👮 Polícia / Agente de autoridade", "🧑‍🎨 Escultor", "🧑‍🎤 Cantor"),
                        correctAnswer = "👮 Polícia / Agente de autoridade",
                        explanation = "A polícia protege as pessoas, regula o trânsito e ajuda quem precisa de auxílio! 👮"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e7",
                        type = ExerciseType.MULTIPLE_CHOICE,
                        question = "Que profissional cuida da saúde e trata dos animais doentes quando precisam de ajuda?",
                        options = listOf("Médico Veterinário", "Jardineiro", "Carteiro"),
                        correctAnswer = "Médico Veterinário",
                        explanation = "O veterinário é o médico dos nossos amigos animais! 🐶🐱"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e8",
                        type = ExerciseType.TRUE_FALSE,
                        question = "Todas as profissões são importantes e necessárias para o bom funcionamento da comunidade.",
                        correctAnswer = "true",
                        explanation = "Verdadeiro! Precisamos do trabalho de todos para vivermos com qualidade, segurança e conforto."
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e9",
                        type = ExerciseType.CHOOSE_IMAGE,
                        question = "Quem constrói e repara as casas e edifícios onde vivemos e estudamos?",
                        options = listOf("🧱 Pedreiro / Construtor civil", "🧑‍🏫 Professor", "🎣 Pescador"),
                        correctAnswer = "🧱 Pedreiro / Construtor civil",
                        explanation = "Os construtores e pedreiros constroem paredes sólidas com tijolos e cimento! 🧱"
                    ),
                    WorksheetExercise(
                        id = "em_1_f12_e10",
                        type = ExerciseType.MATCHING,
                        question = "Associa cada profissional ao seu instrumento de trabalho:",
                        pairs = mapOf(
                            "Médico" to "Estetoscópio",
                            "Padeiro" to "Forno e farinha",
                            "Pintor" to "Pincel e tintas",
                            "Jardineiro" to "Tesoura de podar e regador"
                        ),
                        explanation = "Parabéns, Campeão do Estudo do Meio do 1.º Ano! Conheces as pessoas e o mundo à tua volta!"
                    )
                )
            )
        )
    )

    val year = WorksheetYear(1, "1.º Ano", listOf(portugues, matematica, estudoMeio))
}
