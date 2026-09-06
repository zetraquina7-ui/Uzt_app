package com.example.data

import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object ZeAILocalKnowledgeBase {

    fun normalizeQuery(text: String): String {
        if (text.isBlank()) return ""
        val nfdNormalizedString = Normalizer.normalize(text.trim().lowercase(), Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        val withoutAccents = pattern.matcher(nfdNormalizedString).replaceAll("")
        return withoutAccents.replace(Regex("[^a-z0-9\\s]"), " ").replace(Regex("\\s+"), " ").trim()
    }

    fun detectCategory(query: String): String {
        val norm = normalizeQuery(query)
        return when {
            norm.contains("ola") || norm.contains("oi") || norm.contains("bom dia") || norm.contains("boa tarde") || norm.contains("boa noite") || norm.contains("quem es") || norm.contains("teu nome") || norm.contains("idade") || norm.contains("anos tens") -> "saudacao"
            norm.contains("soma") || norm.contains("mais") || norm.contains("menos") || norm.contains("vezes") || norm.contains("dividir") || norm.contains("conta") || norm.contains("matematica") || norm.contains("numero") || norm.contains("tabuada") -> "matematica"
            norm.contains("dinossauro") || norm.contains("t rex") || norm.contains("cao") || norm.contains("gato") || norm.contains("elefante") || norm.contains("baleia") || norm.contains("golfinho") || norm.contains("animal") || norm.contains("animais") || norm.contains("ave") || norm.contains("passaro") || norm.contains("leao") -> "animais"
            norm.contains("planeta") || norm.contains("espaco") || norm.contains("lua") || norm.contains("sol") || norm.contains("estrela") || norm.contains("astronauta") || norm.contains("marte") || norm.contains("universo") || norm.contains("galaxia") -> "espaco"
            norm.contains("planta") || norm.contains("arvore") || norm.contains("agua") || norm.contains("chuva") || norm.contains("vento") || norm.contains("arco iris") || norm.contains("vulcao") || norm.contains("ciencia") || norm.contains("terra") || norm.contains("corpo") || norm.contains("coracao") || norm.contains("cerebro") -> "ciencia"
            norm.contains("escola") || norm.contains("estudar") || norm.contains("professora") || norm.contains("professor") || norm.contains("livro") || norm.contains("ler") || norm.contains("trabalho de casa") || norm.contains("aula") -> "escola"
            norm.contains("piada") || norm.contains("rir") || norm.contains("engracado") || norm.contains("anedota") -> "piada"
            norm.contains("adivinha") || norm.contains("enigma") || norm.contains("charada") -> "adivinha"
            norm.contains("triste") || norm.contains("chateado") || norm.contains("zangado") || norm.contains("medo") || norm.contains("amigo") || norm.contains("amizade") || norm.contains("gosto de ti") || norm.contains("abraco") -> "emocoes"
            norm.contains("historia") || norm.contains("conto") || norm.contains("conto de fadas") -> "historia"
            else -> "curiosidade"
        }
    }

    /**
     * Retorna a lista de frases educativas e divertidas pré-carregadas para o ZéAI.
     * Todas em Português de Portugal (PT-PT), adequadas ao 1.º e 2.º Ciclo.
     */
    fun getInitialSeedData(): List<ZeAICacheEntity> {
        val list = mutableListOf<ZeAICacheEntity>()

        fun addSeed(query: String, category: String, response: String) {
            list.add(
                ZeAICacheEntity(
                    normalizedQuery = normalizeQuery(query),
                    originalQuery = query,
                    category = category,
                    response = response,
                    useCount = 1,
                    isPrepopulated = true
                )
            )
        }

        // ==========================================
        // 1. Saudações e Apresentação
        // ==========================================
        addSeed(
            "ola",
            "saudacao",
            "Olá amiguinho! 🎈 Que bom ver-te aqui no Universo Zé Traquina! O que vamos descobrir e aprender hoje?"
        )
        addSeed(
            "bom dia",
            "saudacao",
            "Bom dia, campeão! ☀️ Um novo dia cheio de energia e curiosidade! Estás pronto para nos divertirmos muito?"
        )
        addSeed(
            "boa tarde",
            "saudacao",
            "Boa tarde, amigo! 🌤️ Como está a correr o teu dia? Já lanchaste ou preferes fazermos um jogo fixe?"
        )
        addSeed(
            "boa noite",
            "saudacao",
            "Boa noite! 🌙 Espero que tenhas tido um dia fantástico! Descansa bem para amanhã termos mais aventuras!"
        )
        addSeed(
            "quem es tu",
            "saudacao",
            "Eu sou o Zé Traquina! Tenho 7 anos, ando no 2.º ano, adoro futebol, ciências, desenhar e falar com grandes amigos como tu! 🎒⚽"
        )
        addSeed(
            "quantos anos tens",
            "saudacao",
            "Tenho 7 anos! E tenho muita energia para inventar brincadeiras e aprender tudo sobre o mundo! 🚀"
        )

        // ==========================================
        // 2. Matemática e Contas
        // ==========================================
        addSeed(
            "matematica",
            "matematica",
            "A matemática é como magia com números! 🧮 Sabias que somar é juntar amigos e subtrair é partilhar doces? Diz-me uma conta para resolvermos juntos!"
        )
        addSeed(
            "quanto e dois mais dois",
            "matematica",
            "Dois mais dois são quatro! 2 + 2 = 4! ✌️✌️ Muito bem, acertaste em cheio!"
        )
        addSeed(
            "quanto e cinco mais cinco",
            "matematica",
            "Cinco mais cinco são dez! 🖐️🖐️ Junta as duas mãos e conta os teus dez dedos mágicos!"
        )
        addSeed(
            "tabuada",
            "matematica",
            "A tabuada é um super-poder para calcular rápido! Por exemplo, na tabuada do 2 vamos sempre de 2 em 2: 2, 4, 6, 8, 10... É facílimo! ⚡"
        )
        addSeed(
            "formas geometricas",
            "matematica",
            "O círculo é redondo como a bola de futebol, o quadrado tem 4 lados iguaizinhos e o triângulo tem 3 bicos divertidos! 🔺🟦🟡"
        )

        // ==========================================
        // 3. Animais e Natureza
        // ==========================================
        addSeed(
            "dinossauro",
            "animais",
            "Os dinossauros eram fantásticos! 🦕 O T-Rex tinha dentes enormes do tamanho de bananas gigantes e o Triceratops tinha três cornos protetores! Qual é o teu preferido?"
        )
        addSeed(
            "golfinhos",
            "animais",
            "Sabias que os golfinhos têm nomes próprios uns para os outros e assobiam para se chamar na água? São animais super inteligentes e amigos! 🐬🌊"
        )
        addSeed(
            "elefante",
            "animais",
            "Os elefantes têm trombas mágicas com milhares de músculos e conseguem sentir as pegadas dos amigos através do chão com as patas! 🐘✨"
        )
        addSeed(
            "animais",
            "animais",
            "Eu adoro animais! Há animais que voam como as águias, outros que nadam como os polvos de três corações e outros que correm velozes como a chita! 🐾🦁"
        )
        addSeed(
            "caes e gatos",
            "animais",
            "Os cães abanam a cauda de contentamento e os gatos ronronam quando recebem festinhas carinhosas! Cuidar dos nossos bichinhos é muito importante! 🐶🐱"
        )

        // ==========================================
        // 4. Espaço e Astronomia
        // ==========================================
        addSeed(
            "espaco",
            "espaco",
            "O espaço é gigante e fascinante! 🌌 Sabias que no espaço não há som porque não há ar para transportar a voz? Os astronautas usam rádios nos fatos espaciais! 🚀"
        )
        addSeed(
            "lua",
            "espaco",
            "A Lua é o nosso vizinho brilhante no céu! Ela não tem luz própria, reflete a luz do Sol e parece que muda de forma conforme gira à volta da Terra! 🌕"
        )
        addSeed(
            "sol",
            "espaco",
            "O Sol é uma estrela gigante e quentinha! É tão grande que caberiam mais de um milhão de planetas Terra dentro dele! ☀️🌍"
        )
        addSeed(
            "planeta marte",
            "espaco",
            "Marte é chamado o Planeta Vermelho por causa do pó de ferro no solo! E sabias que lá o pôr do sol é azul? Espetacular, não é? 🪐🔴"
        )

        // ==========================================
        // 5. Ciência e Corpo Humano
        // ==========================================
        addSeed(
            "corpo humano",
            "ciencia",
            "O nosso coração bate cerca de 100 mil vezes por dia para enviar energia a todo o corpo! E o nosso cérebro aprende tudo a brincar! 🫀🧠"
        )
        addSeed(
            "arco iris",
            "ciencia",
            "O arco-íris aparece quando a luz do Sol atravessa as gotinhas de chuva como se fossem prismas de cristal coloridos! 🌈 Tem 7 cores maravilhosas!"
        )
        addSeed(
            "plantas e arvores",
            "ciencia",
            "As plantas bebem água pelas raízes e apanham a luz do sol para fazer o seu próprio alimento! Além disso, dão-nos oxigénio puro para respirar! 🌱🌳"
        )
        addSeed(
            "agua e chuva",
            "ciencia",
            "A água faz uma viagem mágica: o Sol aquece os rios e mares, a água sobe em vapor, forma nuvens fofas e depois cai em forma de chuva para regar a Terra! 🌧️💧"
        )

        // ==========================================
        // 6. Escola e Aprendizagem
        // ==========================================
        addSeed(
            "escola",
            "escola",
            "A escola é o lugar onde descobrimos super-poderes: ler histórias, fazer contas e brincar com os amigos no recreio! O que mais gostas de aprender? 📚✏️"
        )
        addSeed(
            "ler livros",
            "escola",
            "Ler um livro é como viajar num tapete voador sem sair do lugar! Cada página leva-nos a castelos, florestas e galáxias distantes! 📖✨"
        )
        addSeed(
            "portugues",
            "escola",
            "O Português tem palavras tão bonitas! Sabias que a palavra 'Saudade' é tão especial que quase só existe na nossa língua? 🇵🇹"
        )

        // ==========================================
        // 7. Piadas e Adivinhas
        // ==========================================
        addSeed(
            "conta me uma piada",
            "piada",
            "Sabes o que o zero disse para o oito? 'Que cinto tão elegante tens aí!' Ah ah ah! 😃"
        )
        addSeed(
            "outra piada",
            "piada",
            "O que disse um lápis para a borracha? 'Comigo não passas uma borracha na nossa grande amizade!' Hi hi hi! ✏️😄"
        )
        addSeed(
            "adivinha",
            "adivinha",
            "O que é, o que é: cai em pé e corre deitado? Dica: enche as poças onde nós saltamos com botas de borracha! ... É a chuva! 🌧️👢"
        )
        addSeed(
            "outra adivinha",
            "adivinha",
            "O que é, o que é: tem dentes mas não morde e deixa o cabelo bem penteado e bonito? ... É o pente! 🪮✨"
        )
        addSeed(
            "mais uma adivinha",
            "adivinha",
            "O que é, o que é: tem capa mas não é super-herói, tem folhas mas não é árvore? ... É um livro! 📖⭐"
        )

        // ==========================================
        // 8. Emoções e Amizade
        // ==========================================
        addSeed(
            "estou triste",
            "emocoes",
            "Oh, amiguinho... Dá cá um abraço bem forte! 🤗 Todos nós temos momentos assim, mas eu estou aqui contigo. Respira fundo e lembra-te que és muito especial! 🌈✨"
        )
        addSeed(
            "gosto de ti",
            "emocoes",
            "E eu adoro ser teu amigo! 🌟 Fazer esta viagem contigo no Universo Zé Traquina enche-me o coração de alegria! Somos a melhor equipa do mundo! 🤝"
        )
        addSeed(
            "amizade",
            "emocoes",
            "Um bom amigo é aquele que sabe ouvir, partilha os brinquedos e põe um sorriso no nosso rosto quando precisamos! Obrigado por seres meu amigo! 💛"
        )

        // ==========================================
        // 9. Histórias e Imaginação
        // ==========================================
        addSeed(
            "conta me uma historia",
            "historia",
            "Era uma vez uma estrelinha muito curiosa chamada Faísca, que desceu numa nuvem de algodão doce para dar um abraço aos meninos da Terra e espalhar pozinhos de alegria! ⭐☁️"
        )

        return list
    }

    /**
     * Gera resposta inteligente offline baseada em palavras-chave e contexto de data/hora
     */
    fun buildSmartOfflineResponse(query: String): String {
        val norm = normalizeQuery(query)
        val dateFormat = SimpleDateFormat("EEEE, d 'de' MMMM", Locale.forLanguageTag("pt-PT"))
        val timeFormat = SimpleDateFormat("HH:mm", Locale.forLanguageTag("pt-PT"))
        val now = Date()

        return when {
            norm.contains("hora") || norm.contains("horas") || norm.contains("relogio") ->
                "Agora são precisamente ${timeFormat.format(now)}! ⏰ Sabias que saber ler as horas nos ajuda a planear a hora do recreio e dos desenhos animados?"

            norm.contains("dia") || norm.contains("data") || norm.contains("hoje") ->
                "Hoje é ${dateFormat.format(now)}! 📅 Um dia maravilhoso para inventar jogos novos e aprender coisas incríveis!"

            norm.contains("jogo") || norm.contains("jogar") || norm.contains("brincar") ->
                "Boa ideia! 🎮 No separador de Jogos temos o Jogo do Galo, Sopa de Letras, Stop e muitos outros! A qual queres jogar primeiro?"

            norm.contains("musica") || norm.contains("canta") || norm.contains("cancao") ->
                "Lá no alto a brilhar, uma estrelinha a dançar! 🎵 Tra-la-la, tra-la-la, com o Zé Traquina vamos todos cantar e celebrar! 🎤✨"

            norm.contains("comida") || norm.contains("fruta") || norm.contains("maca") || norm.contains("sopa") ->
                "Comer fruta colorida e uma boa sopinha cheia de legumes dá-nos super energia para correr e pensar rápido! Qual é a tua fruta favorita? 🍎🥕"

            norm.contains("dentes") || norm.contains("escovar") || norm.contains("higiene") ->
                "Escovar os dentes de manhã e à noite deixa o sorriso a brilhar e afasta os bichinhos das cáries! Dois minutos com a escova a dançar! 🪥✨"

            else -> {
                val variacoes = listOf(
                    "Hum, de momento estou no meu modo offline (sem ligação ao meu super-cérebro na nuvem) e não sei tudo sobre isso. Mas podes perguntar-me sobre Animais, Espaço, Matemática, Corpo Humano, ou pedir-me uma Piada ou Adivinha! 🧠✨",
                    "Ena, que pergunta tão curiosa! 💡 Como estou sem internet, o meu cérebro está um bocadinho mais pequenino agora. Queres perguntar-me algo sobre dinossauros, o Sol, o coração ou queres que te conte uma piada divertida? 🦕☀️",
                    "Fantástico! 🌟 Adoro conversar contigo mesmo offline! Diz-me lá: o que achas que seria mais divertido, explorar o fundo do mar ou ir à Lua num foguetão? 🚀🌊",
                    "Muito bem pensado, amiguinho! ⭐ Cada ideia tua é cheia de imaginação! Queres tentar resolver uma adivinha muito gira comigo? 🎨"
                )
                variacoes.random()
            }
        }
    }
}
