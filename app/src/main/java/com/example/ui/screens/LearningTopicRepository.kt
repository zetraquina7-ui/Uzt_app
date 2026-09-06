package com.example.ui.screens

import androidx.compose.ui.graphics.Color

object LearningTopicRepository {

    fun getTopicById(id: String): EducationalTopicData? {
        return allTopicsMap[id.lowercase().trim()]
    }

    val allTopics: List<EducationalTopicData> by lazy {
        listOf(
            animaisTopic,
            sistemaSolarTopic,
            corpoHumanoTopic,
            matematicaTopic,
            portugalTopic,
            dinossaurosTopic,
            alfabetoTopic,
            vogaisTopic,
            numerosTopic,
            frutasTopic,
            profissoesTopic,
            emocoesTopic,
            diasSemanaTopic,
            mesesAnoTopic,
            transportesTopic,
            instrumentosTopic,
            historiasTopic,
            palavrasNovasTopic,
            dicionarioTopic,
            livroMagicoTopic,
            saudeHigieneTopic,
            coresFormasTopic,
            naturezaTopic
        )
    }

    private val allTopicsMap: Map<String, EducationalTopicData> by lazy {
        val map = mutableMapOf<String, EducationalTopicData>()
        allTopics.forEach { topic ->
            map[topic.id.lowercase()] = topic
        }
        // Aliases for indexing / ids from previous mappings
        map["animais"] = animaisTopic
        map["sistema_solar"] = sistemaSolarTopic
        map["corpo_humano"] = corpoHumanoTopic
        map["matematica"] = matematicaTopic
        map["portugal"] = portugalTopic
        map["dinossauros"] = dinossaurosTopic
        map["alfabeto"] = alfabetoTopic
        map["vogais"] = vogaisTopic
        map["numeros"] = numerosTopic
        map["frutas"] = frutasTopic
        map["profissoes"] = profissoesTopic
        map["emocoes"] = emocoesTopic
        map["dias_semana"] = diasSemanaTopic
        map["dias"] = diasSemanaTopic
        map["meses_ano"] = mesesAnoTopic
        map["meses"] = mesesAnoTopic
        map["transportes"] = transportesTopic
        map["instrumentos"] = instrumentosTopic
        map["historias"] = historiasTopic
        map["palavras_novas"] = palavrasNovasTopic
        map["dicionario"] = dicionarioTopic
        map["livro_magico"] = livroMagicoTopic
        map["saude_higiene"] = saudeHigieneTopic
        map["saude"] = saudeHigieneTopic
        map["habitos_saudaveis"] = saudeHigieneTopic
        map["cores_formas"] = coresFormasTopic
        map["cores"] = coresFormasTopic
        map["formas"] = coresFormasTopic
        map["natureza"] = naturezaTopic
        map
    }

    // ==========================================
    // 1. ANIMAIS DO ZÉ TRAQUINA
    // ==========================================
    private val animaisTopic = EducationalTopicData(
        id = "animais",
        title = "Animais",
        subtitle = "Descobre os animais e a natureza 🐾",
        iconEmoji = "🦁",
        accentColor = Color(0xFF2E7D32),
        headerGradient = listOf(Color(0xFF1B5E20), Color(0xFF4CAF50)),
        searchPlaceholder = "Procurar animal...",
        customTabName = "Animais",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🐾", Color(0xFF2E7D32)),
            EducationalCategoryFilter("Mamíferos", "🦁", Color(0xFFE65100)),
            EducationalCategoryFilter("Aves", "🦅", Color(0xFF0288D1)),
            EducationalCategoryFilter("Répteis", "🐊", Color(0xFF388E3C)),
            EducationalCategoryFilter("Marinhos", "🐬", Color(0xFF0097A7)),
            EducationalCategoryFilter("Insetos", "🐝", Color(0xFFF57F17))
        ),
        items = listOf(
            EducationalItem(
                id = "an_leao",
                name = "Leão",
                category = "Mamíferos",
                emoji = "🦁",
                subtitle = "Rei da Savana",
                fact = "O rugido de um leão pode ser ouvido a 8 quilómetros de distância!",
                details = mapOf("Habitat" to "Savanas Africanas", "Alimentação" to "Carnívoro", "Som" to "Rugido Forte", "Velocidade" to "Até 80 km/h"),
                speechText = "O Leão é o rei da savana! O seu rugido ouve-se a muitos quilómetros.",
                syllables = "LE • ÃO",
                badge = "SELVAGEM 👑",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "an_elefante",
                name = "Elefante",
                category = "Mamíferos",
                emoji = "🐘",
                subtitle = "Gigante Gentil",
                fact = "O elefante tem uma memória fantástica e bebe água usando a tromba!",
                details = mapOf("Habitat" to "Florestas e Savanas", "Alimentação" to "Herbívoro", "Peso" to "Até 6000 kg", "Destaque" to "Tromba Longa"),
                speechText = "O elefante é o maior animal terrestre e tem uma tromba incrível!",
                syllables = "E • LE • FAN • TE",
                badge = "GIGANTE 🌿",
                colorHex = 0xFFA5D6A7
            ),
            EducationalItem(
                id = "an_golfinho",
                name = "Golfinho",
                category = "Marinhos",
                emoji = "🐬",
                subtitle = "Acrobata dos Oceanos",
                fact = "Os golfinhos comunicam por assobios e dormem com um olho aberto!",
                details = mapOf("Habitat" to "Oceanos e Mares", "Alimentação" to "Peixes e Lulas", "Superpoder" to "Ecolocalização", "Família" to "Cetáceos"),
                speechText = "O golfinho é super inteligente, brincalhão e adora saltar nas ondas!",
                syllables = "GOL • FI • NHO",
                badge = "OCEANO 🌊",
                colorHex = 0xFF80DEEA
            ),
            EducationalItem(
                id = "an_lobo",
                name = "Lobo Ibérico",
                category = "Mamíferos",
                emoji = "🐺",
                subtitle = "Guardião das Serras",
                fact = "O lobo ibérico vive em alcateias muito unidas nas montanhas do norte e centro de Portugal!",
                details = mapOf("Habitat" to "Serras de Portugal", "Comunicação" to "Uivo Melódico", "Proteção" to "Espécie Protegida", "Alimentação" to "Carnívoro"),
                speechText = "O lobo ibérico é um animal nobre e muito importante para a natureza de Portugal!",
                syllables = "LO • BO",
                badge = "PORTUGAL 🇵🇹",
                colorHex = 0xFFCFD8DC
            ),
            EducationalItem(
                id = "an_aguia",
                name = "Águia Real",
                category = "Aves",
                emoji = "🦅",
                subtitle = "Visão Aguçada",
                fact = "A águia consegue avistar uma lebre a mais de 3 quilómetros de altitude!",
                details = mapOf("Habitat" to "Altas Montanhas", "Alimentação" to "Carnívoro", "Asas" to "Mais de 2 metros", "Velocidade" to "Mais de 200 km/h"),
                speechText = "A águia tem uma visão espantosa e voa bem alto nos céus.",
                syllables = "Á • GUIA",
                badge = "VÔO 🏔️",
                colorHex = 0xFFFFCC80
            ),
            EducationalItem(
                id = "an_tartaruga",
                name = "Tartaruga Marinha",
                category = "Répteis",
                emoji = "🐢",
                subtitle = "Navegadora Antiga",
                fact = "As tartarugas marinhas podem viver mais de 100 anos e viajam milhares de quilómetros.",
                details = mapOf("Habitat" to "Mares Tropicais", "Alimentação" to "Algas e Medusas", "Longevidade" to "+100 anos", "Casco" to "Armadura Óssea"),
                speechText = "A tartaruga marinha nada suavemente e viaja por todos os oceanos.",
                syllables = "TAR • TA • RU • GA",
                badge = "LONGEVA 🏝️",
                colorHex = 0xFFC8E6C9
            ),
            EducationalItem(
                id = "an_abelha",
                name = "Abelha",
                category = "Insetos",
                emoji = "🐝",
                subtitle = "Polinizadora Incansável",
                fact = "Para fazer um pote de mel, as abelhas visitam mais de um milhão de flores!",
                details = mapOf("Casa" to "Colmeia", "Produção" to "Mel e Cera", "Importância" to "Polinização das Flores", "Organização" to "Rainha e Obreiras"),
                speechText = "A abelha faz mel doce e ajuda todas as flores a nascer!",
                syllables = "A • BE • LHA",
                badge = "MEL 🍯",
                colorHex = 0xFFFFF59D
            ),
            EducationalItem(
                id = "an_girafa",
                name = "Girafa",
                category = "Mamíferos",
                emoji = "🦒",
                subtitle = "Mais Alta do Mundo",
                fact = "A língua da girafa é azul-escura e mede cerca de 45 centímetros!",
                details = mapOf("Altura" to "Até 6 metros", "Alimentação" to "Folhas de Acácia", "Coração" to "Pesa 11 kg", "Sono" to "Dorme em pé"),
                speechText = "A girafa alcança as folhas mais altas das árvores graças ao seu pescoço!",
                syllables = "GI • RA • FA",
                badge = "ALTA 🌳",
                colorHex = 0xFFFFD54F
            ),
            EducationalItem(
                id = "an_tubarao",
                name = "Tubarão Branco",
                category = "Marinhos",
                emoji = "🦈",
                subtitle = "Guardião dos Mares",
                fact = "Os tubarões têm um esqueleto feito de cartilagem e milhares de dentes que se renovam!",
                details = mapOf("Habitat" to "Águas Temperadas", "Sentido" to "Olfato Super Aguçado", "Dentes" to "Múltiplas Filas", "Respiração" to "Guelras"),
                speechText = "O tubarão é um nadador veloz e mantém o equilíbrio dos oceanos!",
                syllables = "TU • BA • RÃO",
                badge = "VELOZ ⚡",
                colorHex = 0xFFB0BEC5
            ),
            EducationalItem(
                id = "an_polvo",
                name = "Polvo",
                category = "Marinhos",
                emoji = "🐙",
                subtitle = "Génio das Profundezas",
                fact = "O polvo tem 3 corações, sangue azul e consegue mudar de cor e textura para se camuflar!",
                details = mapOf("Braços" to "8 Tentáculos", "Corações" to "3 Corações", "Sangue" to "Azul", "Disfarce" to "Camuflagem Instantânea"),
                speechText = "O polvo tem oito tentáculos com ventosas e é muito inteligente!",
                syllables = "POL • VO",
                badge = "OCEANO 🌊",
                colorHex = 0xFFCE93D8
            ),
            EducationalItem(
                id = "an_pinguim",
                name = "Pinguim Imperador",
                category = "Aves",
                emoji = "🐧",
                subtitle = "Mestre do Gelo",
                fact = "O pinguim não voa no ar, mas voa debaixo de água a grande velocidade!",
                details = mapOf("Habitat" to "Antártida e Polo Sul", "Alimentação" to "Peixes e Krill", "Proteção" to "Penas Impermeáveis", "Família" to "Cuida dos Ovos"),
                speechText = "O pinguim desliza na neve e mergulha nas águas geladas com grande estilo!",
                syllables = "PIN • GUIM",
                badge = "GELO ❄️",
                colorHex = 0xFFCFD8DC
            ),
            EducationalItem(
                id = "an_camaleao",
                name = "Camaleão",
                category = "Répteis",
                emoji = "🦎",
                subtitle = "Mestre do Disfarce",
                fact = "Os olhos do camaleão movem-se de forma independente para ver em duas direções ao mesmo tempo!",
                details = mapOf("Habilidade" to "Muda de Cor", "Língua" to "Mais longa que o corpo", "Habitat" to "Florestas e Arbustos", "Visão" to "360 Graus"),
                speechText = "O camaleão muda de cor conforme o seu humor e ambiente!",
                syllables = "CA • MA • LE • ÃO",
                badge = "COR 🎨",
                colorHex = 0xFFA5D6A7
            ),
            EducationalItem(
                id = "an_joaninha",
                name = "Joaninha",
                category = "Insetos",
                emoji = "🐞",
                subtitle = "Amiga dos Jardins",
                fact = "As pintinhas pretas na joaninha avisam os outros animais de que ela tem um sabor amargo!",
                details = mapOf("Cor" to "Vermelha com pintas pretas", "Função" to "Protege as plantas de pragas", "Asas" to "4 Asas (2 protetoras)", "Alimentação" to "Pulgões"),
                speechText = "A joaninha dá sorte e ajuda os agricultores a cuidar das plantas!",
                syllables = "JO • A • NI • NHA",
                badge = "JARDIM 🌸",
                colorHex = 0xFFFF8A80
            ),
            EducationalItem(
                id = "an_canguru",
                name = "Canguru",
                category = "Mamíferos",
                emoji = "🦘",
                subtitle = "Saltador Incrível",
                fact = "O canguru transporta a sua cria na bolsa da barriga (marsúpio) até ela crescer!",
                details = mapOf("Habitat" to "Austrália", "Movimento" to "Grandes Saltos", "Bolsa" to "Marsúpio Protetor", "Cauda" to "Equilíbrio Forte"),
                speechText = "O canguru dá saltos gigantescos e guarda o bebé na sua bolsa fofinha!",
                syllables = "CAN • GU • RU",
                badge = "SALTO 🦘",
                colorHex = 0xFFFFCC80
            )
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_an1", "Qual destes animais é conhecido como o Rei da Selva?", listOf("Leão", "Girafa", "Tartaruga"), "Leão", "O leão é o rei da selva e da savana!", "🦁"),
            EducationalQuizQuestion("q_an2", "Como comunicam os golfinhos entre si nos oceanos?", listOf("Por assobios e sons", "Por cartas", "Com fumo"), "Por assobios e sons", "Os golfinhos usam assobios e cliques aquáticos!", "🐬"),
            EducationalQuizQuestion("q_an3", "Que animal produz mel delicioso na colmeia?", listOf("Abelha", "Águia", "Camaleão"), "Abelha", "A abelha visita flores e produz o doce mel!", "🐝"),
            EducationalQuizQuestion("q_an4", "Qual é o animal terrestre mais alto do planeta?", listOf("Girafa", "Elefante", "Leão"), "Girafa", "A girafa pode medir até 6 metros de altura!", "🦒"),
            EducationalQuizQuestion("q_an5", "Quantos corações tem um polvo?", listOf("3 Corações", "1 Coração", "Nenhum"), "3 Corações", "O polvo tem 3 corações e sangue azul!", "🐙")
        )
    )

    // ==========================================
    // 2. SISTEMA SOLAR DO ZÉ TRAQUINA
    // ==========================================
    private val sistemaSolarTopic = EducationalTopicData(
        id = "sistema_solar",
        title = "Sistema Solar",
        subtitle = "Explora o universo e os planetas 🪐",
        iconEmoji = "🪐",
        accentColor = Color(0xFF5E35B1),
        headerGradient = listOf(Color(0xFF311B92), Color(0xFF673AB7)),
        searchPlaceholder = "Procurar planeta ou estrela...",
        customTabName = "Universo",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🌌", Color(0xFF5E35B1)),
            EducationalCategoryFilter("Sol e Lua", "☀️", Color(0xFFF57F17)),
            EducationalCategoryFilter("Planetas Rochosos", "🌍", Color(0xFF1E88E5)),
            EducationalCategoryFilter("Gigantes Gasosos", "🪐", Color(0xFFE65100)),
            EducationalCategoryFilter("Exploração", "👨‍🚀", Color(0xFF00897B))
        ),
        items = listOf(
            EducationalItem(
                id = "ss_sol",
                name = "O Sol",
                category = "Sol e Lua",
                emoji = "☀️",
                subtitle = "A Nossa Estrela",
                fact = "O Sol é uma esfera de plasma gigantesca que contém 99,8% de toda a massa do Sistema Solar!",
                details = mapOf("Tipo" to "Estrela Anã Amarela", "Temperatura" to "5.500°C na superfície", "Luz até à Terra" to "8 minutos e 20 segundos", "Importância" to "Dá luz e vida"),
                speechText = "O Sol é a grande estrela que ilumina e aquece todos os planetas!",
                syllables = "SOL",
                badge = "ESTRELA 🌟",
                colorHex = 0xFFFFD54F
            ),
            EducationalItem(
                id = "ss_mercurio",
                name = "Mercúrio",
                category = "Planetas Rochosos",
                emoji = "🌑",
                subtitle = "O Mais Próximo do Sol",
                fact = "Mercúrio é o menor planeta do Sistema Solar e o mais rápido a dar uma volta ao Sol!",
                details = mapOf("Posição" to "1º do Sol", "Ano em Mercúrio" to "88 Dias Terrestres", "Superfície" to "Cheia de Crateras", "Temperatura" to "De -180°C a 430°C"),
                speechText = "Mercúrio é pequenino, veloz e muito perto do Sol!",
                syllables = "MER • CÚ • RIO",
                badge = "VELOZ ☄️",
                colorHex = 0xFFB0BEC5
            ),
            EducationalItem(
                id = "ss_venus",
                name = "Vénus",
                category = "Planetas Rochosos",
                emoji = "🟡",
                subtitle = "Estrela da Manhã",
                fact = "Vénus é o planeta mais quente do Sistema Solar devido ao efeito de estufa das suas nuvens!",
                details = mapOf("Posição" to "2º do Sol", "Brilho" to "O mais brilhante no céu", "Temperatura" to "465°C constantes", "Rotação" to "Gira ao contrário"),
                speechText = "Vénus brilha intensamente no céu ao amanhecer e entardecer!",
                syllables = "VÉ • NUS",
                badge = "BRILHANTE ✨",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "ss_terra",
                name = "Planeta Terra",
                category = "Planetas Rochosos",
                emoji = "🌍",
                subtitle = "O Nosso Lar Azul",
                fact = "A Terra é o único lugar do Universo onde sabemos que existe vida e oceanos de água líquida!",
                details = mapOf("Posição" to "3º a contar do Sol", "Água" to "Cobre 71% da superfície", "Satélites" to "1 (A Lua)", "Rotação" to "24 Horas"),
                speechText = "A Terra é a nossa casa mágica e preciosa cheia de natureza!",
                syllables = "TER • RA",
                badge = "CASA 🌿",
                colorHex = 0xFF80DEEA
            ),
            EducationalItem(
                id = "ss_lua",
                name = "A Lua",
                category = "Sol e Lua",
                emoji = "🌙",
                subtitle = "Satélite Natural",
                fact = "A Lua não tem luz própria; ela reflete a luz do Sol e comanda as marés dos oceanos.",
                details = mapOf("Fases" to "Nova, Crescente, Cheia, Minguante", "Distância" to "384.400 km", "Gravidade" to "1/6 da Terra", "Atmosfera" to "Não tem"),
                speechText = "A Lua brilha no céu noturno e acompanha a Terra em todas as noites!",
                syllables = "LU • A",
                badge = "SATÉLITE ✨",
                colorHex = 0xFFECEFF1
            ),
            EducationalItem(
                id = "ss_marte",
                name = "Marte",
                category = "Planetas Rochosos",
                emoji = "🔴",
                subtitle = "O Planeta Vermelho",
                fact = "Marte tem o Monte Olimpo, o vulcão mais alto de todo o Sistema Solar com 22 km de altura!",
                details = mapOf("Cor" to "Vermelho por causa do óxido de ferro", "Luas" to "2 (Fobos e Deimos)", "Dia" to "24 horas e 37 minutos", "Exploração" to "Robôs Rovers"),
                speechText = "Marte é conhecido como o planeta vermelho e tem grandes vulcões!",
                syllables = "MAR • TE",
                badge = "VERMELHO 🚀",
                colorHex = 0xFFFF8A80
            ),
            EducationalItem(
                id = "ss_jupiter",
                name = "Júpiter",
                category = "Gigantes Gasosos",
                emoji = "🪐",
                subtitle = "O Rei dos Planetas",
                fact = "Júpiter é tão gigantesco que mais de 1.300 planetas Terra caberiam no seu interior!",
                details = mapOf("Tamanho" to "O maior do Sistema Solar", "Mancha Vermelha" to "Uma tempestade gigante", "Luas" to "Mais de 90 luas!", "Tipo" to "Gigante Gasoso"),
                speechText = "Júpiter é o maior de todos os planetas e gira super rápido!",
                syllables = "JÚ • PI • TER",
                badge = "GIGANTE 👑",
                colorHex = 0xFFFFCC80
            ),
            EducationalItem(
                id = "ss_saturno",
                name = "Saturno",
                category = "Gigantes Gasosos",
                emoji = "🪐",
                subtitle = "Senhor dos Anéis",
                fact = "Os belos anéis de Saturno são formados por milhões de pedaços de gelo, poeira e rochas brilhantes!",
                details = mapOf("Anéis" to "Milhares de anéis finos", "Densidade" to "Mais leve que a água", "Luas" to "Mais de 140 luas", "Ventos" to "Até 1.800 km/h"),
                speechText = "Saturno tem os anéis mais deslumbrantes do espaço sideral!",
                syllables = "SA • TUR • NO",
                badge = "ANÉIS 💍",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "ss_urano",
                name = "Urano",
                category = "Gigantes Gasosos",
                emoji = "🌐",
                subtitle = "O Planeta Deitado",
                fact = "Urano gira completamente deitado de lado, como se estivesse a rebolar à volta do Sol!",
                details = mapOf("Posição" to "7º a contar do Sol", "Rotação" to "Gira deitado de lado", "Temperatura" to "-224°C (o mais frio)", "Luas" to "28 Luas conhecidas"),
                speechText = "Urano é um gigante de gelo azul pálido que gira deitado de lado!",
                syllables = "U • RA • NO",
                badge = "GELADO ❄️",
                colorHex = 0xFF80CBC4
            ),
            EducationalItem(
                id = "ss_neptuno",
                name = "Neptuno",
                category = "Gigantes Gasosos",
                emoji = "🔵",
                subtitle = "O Gigante de Gelo Azul",
                fact = "Neptuno tem os ventos mais rápidos do Sistema Solar, atingindo mais de 2.000 km/h!",
                details = mapOf("Posição" to "8º a contar do Sol", "Cor" to "Azul profundo devido ao metano", "Temperatura" to "-214°C", "Luas" to "14 Luas (Tritão)"),
                speechText = "Neptuno é um planeta azul gelado com ventos supersónicos!",
                syllables = "NEP • TU • NO",
                badge = "GELADO ❄️",
                colorHex = 0xFF81D4FA
            ),
            EducationalItem(
                id = "ss_astronauta",
                name = "Astronauta",
                category = "Exploração",
                emoji = "👨‍🚀",
                subtitle = "Viajante do Espaço",
                fact = "No espaço, devido à microgravidade, os astronautas flutuam e crescem cerca de 3 a 5 centímetros!",
                details = mapOf("Fato Espacial" to "Protege do vácuo e frio", "Casa no Espaço" to "Estação Espacial Internacional", "Comida" to "Especial desidratada", "Treino" to "Mergulho e simuladores"),
                speechText = "O astronauta viaja em foguetões para investigar os mistérios do cosmos!",
                syllables = "AS • TRO • NAU • TA",
                badge = "HERÓI 🚀",
                colorHex = 0xFFB0BEC5
            ),
            EducationalItem(
                id = "ss_foguetao",
                name = "Foguetão Espacial",
                category = "Exploração",
                emoji = "🚀",
                subtitle = "Veículo Interplanetário",
                fact = "Um foguetão precisa de atingir mais de 28.000 km/h para escapar à gravidade da Terra!",
                details = mapOf("Combustível" to "Oxigénio e Hidrogénio líquidos", "Motores" to "Super potentes", "Destino" to "Órbita e Lua", "Velocidade" to "+28.000 km/h"),
                speechText = "O foguetão sobe veloz até às estrelas deixando um rasto de luz!",
                syllables = "FO • GUE • TÃO",
                badge = "VELOZ 🚀",
                colorHex = 0xFFFF8A80
            )
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_ss1", "Qual é a estrela que fica no centro do Sistema Solar?", listOf("O Sol", "A Lua", "Marte"), "O Sol", "O Sol é a nossa estrela central!", "☀️"),
            EducationalQuizQuestion("q_ss2", "Que planeta é famoso pelos seus anéis de gelo?", listOf("Saturno", "Terra", "Vénus"), "Saturno", "Saturno tem anéis majestosos e brilhantes!", "🪐"),
            EducationalQuizQuestion("q_ss3", "Quantos satélites naturais tem a Terra?", listOf("1 (A Lua)", "5 Luas", "Nenhuma"), "1 (A Lua)", "A Lua é o único satélite natural da Terra!", "🌙"),
            EducationalQuizQuestion("q_ss4", "Qual é o planeta conhecido como o Planeta Vermelho?", listOf("Marte", "Júpiter", "Neptuno"), "Marte", "Marte tem solo avermelhado!", "🔴"),
            EducationalQuizQuestion("q_ss5", "Qual é o maior planeta do Sistema Solar?", listOf("Júpiter", "Mercúrio", "Terra"), "Júpiter", "Júpiter é o maior planeta gigante!", "🪐")
        )
    )

    // ==========================================
    // 3. CORPO HUMANO DO ZÉ TRAQUINA
    // ==========================================
    private val corpoHumanoTopic = EducationalTopicData(
        id = "corpo_humano",
        title = "Corpo Humano",
        subtitle = "Descobre como funciona o teu corpo! 💡",
        iconEmoji = "👀",
        accentColor = Color(0xFF0288D1),
        headerGradient = listOf(Color(0xFF01579B), Color(0xFF0288D1)),
        searchPlaceholder = "Procurar parte do corpo ou órgão...",
        customTabName = "Corpo",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🫀", Color(0xFF0288D1)),
            EducationalCategoryFilter("Órgãos Vitais", "❤️", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Digestivo e Filtros", "🥣", Color(0xFFE65100)),
            EducationalCategoryFilter("Estrutura e Força", "🦴", Color(0xFF5E35B1)),
            EducationalCategoryFilter("Sentidos e Membros", "👀", Color(0xFF388E3C))
        ),
        items = listOf(
            // Linha 1
            EducationalItem(
                id = "ch_cerebro",
                name = "Cérebro",
                category = "Órgãos Vitais",
                emoji = "🧠",
                subtitle = "O Supercomputador da Mente",
                fact = "O cérebro controla todos os teus pensamentos, movimentos, sentimentos e memórias!",
                details = mapOf("Função" to "Comandar o corpo e pensar", "Localização" to "Dentro da cabeça (Crânio)", "Superpoder" to "Guarda memórias e cria ideias"),
                speechText = "O cérebro! É o centro de controlo do teu corpo, onde nascem as tuas ideias e pensamentos!",
                syllables = "CÉ • RE • BRO",
                badge = "CONTROLO 💡",
                colorHex = 0xFF8B5CF6
            ),
            EducationalItem(
                id = "ch_coracao",
                name = "Coração",
                category = "Órgãos Vitais",
                emoji = "❤️",
                subtitle = "A Bomba da Vida",
                fact = "O teu coração bate sem parar cerca de 100.000 vezes por dia para enviar sangue com oxigénio a todo o corpo!",
                details = mapOf("Função" to "Bombear sangue", "Localização" to "No centro do peito", "Batimentos" to "Mais de 100 mil por dia", "Som" to "Tum-tum, tum-tum"),
                speechText = "O coração! Bate tum-tum, tum-tum, bombeando sangue e vida para todo o corpo!",
                syllables = "CO • RA • ÇÃO",
                badge = "VITAL ❤️",
                colorHex = 0xFFEF4444
            ),
            EducationalItem(
                id = "ch_pulmoes",
                name = "Pulmões",
                category = "Órgãos Vitais",
                emoji = "🫁",
                subtitle = "Fábrica de Ar Puro",
                fact = "Os pulmões absorvem o oxigénio do ar quando inspiras e libertam o ar quando expiras!",
                details = mapOf("Função" to "Respirar oxigénio", "Quantidade" to "2 pulmões (esquerdo e direito)", "Ação" to "Inspirar e expirar"),
                speechText = "Os pulmões! Enchem-se de ar puro e fresco cada vez que respiras fundo!",
                syllables = "PUL • MÕES",
                badge = "AR 🌬️",
                colorHex = 0xFF06B6D4
            ),
            // Linha 2
            EducationalItem(
                id = "ch_estomago",
                name = "Estômago",
                category = "Digestivo e Filtros",
                emoji = "🥣",
                subtitle = "Processador de Alimentos",
                fact = "O estômago recebe a comida deliciosa e mistura-a para transformar os alimentos em energia!",
                details = mapOf("Função" to "Digerir a comida", "Forma" to "Uma bolsa elástica", "Combustível" to "Alimentos saudáveis e água"),
                speechText = "O estômago! Transforma a comida saborosa em energia para poderes correr e brincar!",
                syllables = "ES • TÔ • MA • GO",
                badge = "DIGESTÃO 🍏",
                colorHex = 0xFFF59E0B
            ),
            EducationalItem(
                id = "ch_intestinos",
                name = "Intestinos",
                category = "Digestivo e Filtros",
                emoji = "➰",
                subtitle = "Caminho dos Nutrientes",
                fact = "Os intestinos absorvem as vitaminas, nutrientes e água de que o teu corpo precisa para crescer com saúde!",
                details = mapOf("Função" to "Absorver nutrientes essenciais", "Tipos" to "Intestino Delgado e Grosso", "Importância" to "Dá energia ao organismo"),
                speechText = "Os intestinos! Retiram todas as vitaminas e água da comida para ficares super forte!",
                syllables = "IN • TES • TI • NOS",
                badge = "NUTRIÇÃO 🥗",
                colorHex = 0xFFEC4899
            ),
            EducationalItem(
                id = "ch_rins",
                name = "Rins",
                category = "Digestivo e Filtros",
                emoji = "🫘",
                subtitle = "Filtros Naturais do Corpo",
                fact = "Os rins funcionam como filtros mágicos que limpam o sangue e mantêm a água do corpo equilibrada!",
                details = mapOf("Função" to "Filtrar e limpar o sangue", "Quantidade" to "2 rins em forma de feijão", "Amigo" to "Beber muita água fresca"),
                speechText = "Os rins! São filtros pequeninos que limpam o teu corpo. Bebe sempre bastante água!",
                syllables = "RINS",
                badge = "FILTRO 💧",
                colorHex = 0xFF3B82F6
            ),
            // Linha 3
            EducationalItem(
                id = "ch_esqueleto",
                name = "Esqueleto",
                category = "Estrutura e Força",
                emoji = "🦴",
                subtitle = "Armadura Forte de Ossos",
                fact = "O esqueleto tem 206 ossos fortes que sustentam o corpo, protegem os órgãos e ajudam a movimentar!",
                details = mapOf("Função" to "Sustentar e proteger", "Número de Ossos" to "206 ossos no adulto", "Cuidado" to "Cálcio do leite e sol para vitamina D"),
                speechText = "O esqueleto! É a armadura de ossos que te mantém em pé e protege os teus órgãos!",
                syllables = "ES • QUE • LE • TO",
                badge = "OSSOS 🦴",
                colorHex = 0xFF64748B
            ),
            EducationalItem(
                id = "ch_musculos",
                name = "Músculos",
                category = "Estrutura e Força",
                emoji = "💪",
                subtitle = "Motores do Movimento",
                fact = "Os músculos esticam e encolhem para nos permitir andar, saltar, sorrir e dar abraços!",
                details = mapOf("Função" to "Permitir todos os movimentos", "Quantidade" to "Mais de 600 músculos", "Treino" to "Exercício e brincadeiras ativas"),
                speechText = "Os músculos! Dão-te força para correr, saltar, levantar coisas e dar abraços apertados!",
                syllables = "MÚS • CU • LOS",
                badge = "FORÇA 💪",
                colorHex = 0xFFDC2626
            ),
            EducationalItem(
                id = "ch_pele",
                name = "Pele",
                category = "Estrutura e Força",
                emoji = "🧴",
                subtitle = "O Nosso Escudo Protetor",
                fact = "A pele cobre todo o corpo, protege-nos contra micróbios e ajuda a sentir calor, frio e carinho!",
                details = mapOf("Função" to "Proteger e sentir o tato", "Tamanho" to "O maior órgão de todos", "Proteção" to "Usar protetor solar e lavar com água"),
                speechText = "A pele! É o escudo protetor do teu corpo e o órgão do sentido do tato!",
                syllables = "PE • LE",
                badge = "ESCUDO 🛡️",
                colorHex = 0xFFD97706
            ),
            // Linha 4
            EducationalItem(
                id = "ch_olhos",
                name = "Olhos",
                category = "Sentidos e Membros",
                emoji = "👀",
                subtitle = "Sentido da Visão",
                fact = "Os olhos captam a luz e as cores para vermos os amigos, a família, os brinquedos e a natureza!",
                details = mapOf("Sentido" to "Visão", "Cores" to "Milhões de cores diferentes", "Proteção" to "Pálpebras e pestanas"),
                speechText = "Os olhos! Permitem-te ver todas as cores mágicas, formas e paisagens do mundo!",
                syllables = "O • LHOS",
                badge = "VISÃO 👁️",
                colorHex = 0xFF2563EB
            ),
            EducationalItem(
                id = "ch_ouvidos",
                name = "Ouvidos",
                category = "Sentidos e Membros",
                emoji = "👂",
                subtitle = "Sentido da Audição",
                fact = "Os ouvidos captam os sons e músicas, e também ajudam a manter o nosso equilíbrio!",
                details = mapOf("Sentido" to "Audição e Equilíbrio", "Função" to "Ouvir sons e melodias", "Curiosidade" to "Ajudam no equilíbrio do corpo"),
                speechText = "Os ouvidos! Com eles consegues ouvir canções divertidas e as vozes dos teus amigos!",
                syllables = "OU • VI • DOS",
                badge = "AUDIÇÃO 🎵",
                colorHex = 0xFF7C3AED
            ),
            EducationalItem(
                id = "ch_nariz",
                name = "Nariz",
                category = "Sentidos e Membros",
                emoji = "👃",
                subtitle = "Sentido do Olfato",
                fact = "O nariz sente os cheiros bons das flores e da comida, e ainda filtra o ar que respiras!",
                details = mapOf("Sentido" to "Olfato e Respiração", "Função" to "Cheirar e filtrar o ar", "Curiosidade" to "Aquece o ar que entra nos pulmões"),
                speechText = "O nariz! Sente os cheirinhos gostosos da natureza, dos bolos e das flores!",
                syllables = "NA • RIZ",
                badge = "OLFATO 🌸",
                colorHex = 0xFF059669
            ),
            // Linha 5
            EducationalItem(
                id = "ch_boca_dentes",
                name = "Boca e Dentes",
                category = "Sentidos e Membros",
                emoji = "👄",
                subtitle = "Paladar, Fala e Mastigação",
                fact = "A boca tem dentes para mastigar e a língua para sentir sabores e falar com clareza!",
                details = mapOf("Sentido" to "Paladar e Fala", "Dentes" to "Mastigar os alimentos", "Higiene" to "Lavar os dentes 3 vezes ao dia"),
                speechText = "A boca e dentes! Servem para falar, sorrir, saborear e mastigar a comida saudável!",
                syllables = "BO • CA",
                badge = "PALADAR 🦷",
                colorHex = 0xFFE11D48
            ),
            EducationalItem(
                id = "ch_maos",
                name = "Mãos",
                category = "Sentidos e Membros",
                emoji = "🖐️",
                subtitle = "Criatividade e Tato",
                fact = "As mãos têm dez dedos ágeis para desenhar, pintar, segurar brinquedos e dar palmas!",
                details = mapOf("Função" to "Segurar objetos e desenhar", "Dedos" to "5 dedos em cada mão", "Tato" to "Sentir texturas macias e frias"),
                speechText = "As mãos! Com dez dedinhos podes desenhar, pintar, tocar instrumentos e dar palmas!",
                syllables = "MÃOS",
                badge = "CRIAR ✍️",
                colorHex = 0xFF0284C7
            ),
            EducationalItem(
                id = "ch_pes",
                name = "Pés",
                category = "Sentidos e Membros",
                emoji = "🦶",
                subtitle = "Passos e Equilíbrio",
                fact = "Os pés sustentam todo o peso do teu corpo e dão o impulso para correr, dançar e saltar!",
                details = mapOf("Função" to "Caminhar, saltar e equilibrar", "Dedos" to "5 dedos em cada pé", "Equilíbrio" to "Base para todas as brincadeiras"),
                speechText = "Os pés! Levam-te a passear por todo o lado e dão equilíbrio para grandes corridas!",
                syllables = "PÉS",
                badge = "PASSO 🏃",
                colorHex = 0xFF16A34A
            )
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_ch1", "Que órgão bombeia o sangue para todo o corpo?", listOf("Coração", "Estômago", "Pele"), "Coração", "O coração bombeia o sangue dia e noite!", "❤️"),
            EducationalQuizQuestion("q_ch2", "Qual é o órgão responsável pelos nossos pensamentos e memória?", listOf("Cérebro", "Rins", "Pulmões"), "Cérebro", "O cérebro é o centro de controlo do corpo!", "🧠"),
            EducationalQuizQuestion("q_ch3", "Com que órgão sentimos os cheirinhos das flores?", listOf("Nariz", "Ouvidos", "Pés"), "Nariz", "O nariz é o órgão do sentido do olfato!", "👃"),
            EducationalQuizQuestion("q_ch4", "Quantos ossos tem o esqueleto de um adulto?", listOf("206 ossos", "10 ossos", "500 ossos"), "206 ossos", "O esqueleto humano tem 206 ossos!", "🦴"),
            EducationalQuizQuestion("q_ch5", "Que parte do corpo usamos para desenhar e segurar objetos?", listOf("Mãos", "Pés", "Ouvidos"), "Mãos", "As mãos e os dedos permitem desenhar e criar!", "🖐️")
        )
    )

    // ==========================================
    // 4. MATEMÁTICA DO ZÉ TRAQUINA
    // ==========================================
    private val matematicaTopic = EducationalTopicData(
        id = "matematica",
        title = "Matemática",
        subtitle = "Aprende números, somas e desafios divertidos ➕",
        iconEmoji = "➕",
        accentColor = Color(0xFFE65100),
        headerGradient = listOf(Color(0xFFBF360C), Color(0xFFF57C00)),
        searchPlaceholder = "Procurar operação ou número...",
        customTabName = "Desafios",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🧮", Color(0xFFE65100)),
            EducationalCategoryFilter("Contar", "🔢", Color(0xFF1976D2)),
            EducationalCategoryFilter("Adição", "➕", Color(0xFF388E3C)),
            EducationalCategoryFilter("Subtração", "➖", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Desafios", "🎯", Color(0xFF7B1FA2))
        ),
        items = listOf(
            EducationalItem(
                id = "mat_soma1",
                name = "1 + 1 = 2",
                category = "Adição",
                emoji = "🍎",
                subtitle = "Juntar Maçãs",
                fact = "Somar é juntar quantidades: uma maçã mais uma maçã é igual a duas maçãs!",
                details = mapOf("Operação" to "Adição (+)", "Parcelas" to "1 e 1", "Resultado" to "2", "Exemplo" to "1 bola + 1 bola = 2 bolas"),
                speechText = "Um mais um é igual a dois! Muito fácil e divertido.",
                syllables = "U • MA • MAIS • UM",
                badge = "BÁSICO ⭐",
                colorHex = 0xFFFFD54F
            ),
            EducationalItem(
                id = "mat_soma2",
                name = "2 + 2 = 4",
                category = "Adição",
                emoji = "🎈",
                subtitle = "Pares Coloridos",
                fact = "Dois balões azuis mais dois balões amarelos fazem quatro balões de festa!",
                details = mapOf("Operação" to "Adição (+)", "Parcelas" to "2 e 2", "Resultado" to "4", "Exemplo" to "2 pares de sapatos"),
                speechText = "Dois mais dois é igual a quatro!",
                syllables = "DOIS • MAIS • DOIS",
                badge = "SOMA 🎈",
                colorHex = 0xFF80DEEA
            ),
            EducationalItem(
                id = "mat_soma3",
                name = "5 + 5 = 10",
                category = "Adição",
                emoji = "🖐️",
                subtitle = "Dedos das Mãos",
                fact = "Cinco dedinhos numa mão mais cinco dedinhos na outra perfazem dez dedos!",
                details = mapOf("Operação" to "Adição (+)", "Parcelas" to "5 e 5", "Resultado" to "10", "Dica" to "Duas mãos cheias"),
                speechText = "Cinco mais cinco é igual a dez! Como os dez dedos das nossas mãos.",
                syllables = "CIN • CO • MAIS • CIN • CO",
                badge = "DEZENA 🔟",
                colorHex = 0xFFA5D6A7
            ),
            EducationalItem(
                id = "mat_sub1",
                name = "5 - 1 = 4",
                category = "Subtração",
                emoji = "⭐",
                subtitle = "Tirar Estrelas",
                fact = "Se tens 5 estrelas e ofereces 1 ao teu amigo Zé, ficas com 4 estrelas brilhantes!",
                details = mapOf("Operação" to "Subtração (-)", "Início" to "5", "Retirado" to "1", "Resto" to "4"),
                speechText = "Cinco menos um é igual a quatro!",
                syllables = "CIN • CO • ME • NOS • UM",
                badge = "SUBTRAÇÃO ➖",
                colorHex = 0xFFFF8A80
            ),
            EducationalItem(
                id = "mat_sub2",
                name = "10 - 2 = 8",
                category = "Subtração",
                emoji = "🍪",
                subtitle = "Comer Bolachinhas",
                fact = "Se tens 10 bolachas deliciosas e comes 2 no lanche, sobram 8 bolachas crocantes!",
                details = mapOf("Operação" to "Subtração (-)", "Início" to "10", "Comidas" to "2", "Sobram" to "8"),
                speechText = "Dez menos dois é igual a oito bolachinhas!",
                syllables = "DEZ • ME • NOS • DOIS",
                badge = "LANCHE 🍪",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "mat_dobro",
                name = "O Dobro",
                category = "Desafios",
                emoji = "🚀",
                subtitle = "Multiplicar por 2",
                fact = "O dobro de um número é esse número somado a si próprio: o dobro de 3 é 3 + 3 = 6!",
                details = mapOf("Dobro de 1" to "2", "Dobro de 2" to "4", "Dobro de 3" to "6", "Dobro de 5" to "10"),
                speechText = "O dobro significa duas vezes a mesma quantidade!",
                syllables = "DO • BRO",
                badge = "DOBRO ✖️",
                colorHex = 0xFFA5D6A7
            ),
            EducationalItem(
                id = "mat_pares",
                name = "Pares e Ímpares",
                category = "Contar",
                emoji = "🎲",
                subtitle = "Grupos Amigos",
                fact = "Números pares fazem pares perfeitos sem sobrar nada (0, 2, 4, 6, 8, 10).",
                details = mapOf("Pares" to "2, 4, 6, 8, 10...", "Ímpares" to "1, 3, 5, 7, 9...", "Dica" to "Pares dividem-se ao meio", "Exemplo" to "2 sapatos são 1 par"),
                speechText = "Os números pares dividem-se em dois grupos iguaizinhos!",
                syllables = "PA • RES",
                badge = "NÚMEROS 🔢",
                colorHex = 0xFFC5CAE9
            ),
            EducationalItem(
                id = "mat_formas",
                name = "Formas Geométricas",
                category = "Desafios",
                emoji = "📐",
                subtitle = "Círculo, Quadrado e Triângulo",
                fact = "O círculo é redondo como uma moeda, o triângulo tem 3 lados e o quadrado tem 4 lados iguais!",
                details = mapOf("Círculo" to "0 Cantos (Redondo)", "Triângulo" to "3 Cantos e 3 Lados", "Quadrado" to "4 Cantos e 4 Lados Iguais", "Retângulo" to "2 Lados Longos e 2 Curtos"),
                speechText = "As formas geométricas estão em todo o lado: nos relógios, portas e telhados!",
                syllables = "FOR • MAS",
                badge = "GEOMETRIA 🔷",
                colorHex = 0xFF80DEEA
            )
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_mat1", "Quanto é 2 + 2?", listOf("4", "3", "5"), "4", "Dois mais dois é igual a quatro!", "🎈"),
            EducationalQuizQuestion("q_mat2", "Se tiveres 5 bolachas e comeres 1, quantas sobram?", listOf("4 bolachas", "2 bolachas", "6 bolachas"), "4 bolachas", "5 menos 1 é igual a 4!", "🍪"),
            EducationalQuizQuestion("q_mat3", "Qual é o número que vem a seguir ao 9?", listOf("10", "8", "11"), "10", "A seguir ao nove vem o dez!", "🔟"),
            EducationalQuizQuestion("q_mat4", "Quantos lados tem um triângulo?", listOf("3 Lados", "4 Lados", "2 Lados"), "3 Lados", "O triângulo tem 3 lados e 3 cantos!", "🔺")
        )
    )

    // ==========================================
    // 5. PORTUGAL DO ZÉ TRAQUINA
    // ==========================================
    private val portugalTopic = EducationalTopicData(
        id = "portugal",
        title = "Portugal",
        subtitle = "Descobre castelos, cidades e tradições de Portugal 🇵🇹",
        iconEmoji = "🏰",
        accentColor = Color(0xFFC62828),
        headerGradient = listOf(Color(0xFFB71C1C), Color(0xFF2E7D32)),
        searchPlaceholder = "Procurar monumento ou símbolo...",
        customTabName = "Tradições",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🇵🇹", Color(0xFFC62828)),
            EducationalCategoryFilter("Monumentos", "🏰", Color(0xFFE65100)),
            EducationalCategoryFilter("Símbolos", "🐓", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Cidades", "🚋", Color(0xFF1976D2)),
            EducationalCategoryFilter("História", "🛡️", Color(0xFF5E35B1)),
            EducationalCategoryFilter("Gastronomia", "🥧", Color(0xFFF57F17))
        ),
        items = listOf(
            EducationalItem(
                id = "pt_torre",
                name = "Torre de Belém",
                category = "Monumentos",
                emoji = "🏰",
                subtitle = "Lisboa e o Tejo",
                fact = "A Torre de Belém foi construída há mais de 500 anos no estilo Manuelino junto ao Rio Tejo!",
                details = mapOf("Cidade" to "Lisboa", "Rio" to "Tejo", "Estilo" to "Manuelino", "Património" to "UNESCO"),
                speechText = "A Torre de Belém vigiava as caravelas que partiam para descobrir o mundo!",
                syllables = "TOR • RE • DE • BE • LÉM",
                badge = "LISBOA 🌊",
                colorHex = 0xFFFFD54F
            ),
            EducationalItem(
                id = "pt_galo",
                name = "Galo de Barcelos",
                category = "Símbolos",
                emoji = "🐓",
                subtitle = "Símbolo de Justiça",
                fact = "O Galo de Barcelos é famoso pelas suas cores vivas, corações vermelhos e lenda mágica!",
                details = mapOf("Origem" to "Barcelos (Minho)", "Significado" to "Sorte e Verdade", "Cores" to "Preto com corações", "Artesanato" to "Barro pintado"),
                speechText = "O Galo de Barcelos é o símbolo de alegria e tradição mais conhecido de Portugal!",
                syllables = "GA • LO",
                badge = "TRADIÇÃO ❤️",
                colorHex = 0xFFFF8A80
            ),
            EducationalItem(
                id = "pt_santa_luzia",
                name = "Santuário de Santa Luzia",
                category = "Símbolos",
                emoji = "⛰️",
                subtitle = "Viana do Castelo",
                fact = "O Santuário de Santa Luzia coroa o monte sobranceiro a Viana do Castelo, oferecendo uma das vistas panorâmicas mais deslumbrantes de Portugal!",
                details = mapOf("Cidade" to "Viana do Castelo", "Local" to "Monte de Santa Luzia", "Estilo" to "Neo-bizantino", "Destaque" to "Vista soberba sobre o mar e o rio Lima"),
                speechText = "O Santuário de Santa Luzia em Viana do Castelo coroa o monte com uma vista maravilhosa sobre o mar e o rio!",
                syllables = "SAN • TU • Á • RI • O",
                badge = "VIANA 🌊",
                colorHex = 0xFFFFEE58
            ),
            EducationalItem(
                id = "pt_eletrico",
                name = "Elétrico 28",
                category = "Cidades",
                emoji = "🚋",
                subtitle = "Pelas Ruas Históricas",
                fact = "O Elétrico 28 sobe as colinas de Lisboa passando pelos bairros típicos de Alfama e Graça.",
                details = mapOf("Cidade" to "Lisboa", "Cor" to "Amarelo Tradicional", "Tipo" to "Transporte sobre carris", "Som" to "Tin-tin da sineta"),
                speechText = "O elétrico amarelo sobe e desce as colinas estreitas de Lisboa!",
                syllables = "E • LÉ • TRI • CO",
                badge = "TRANSPORTE 💛",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "pt_guimaraes",
                name = "Castelo de Guimarães",
                category = "História",
                emoji = "🛡️",
                subtitle = "Berço da Nação",
                fact = "Aqui nasceu D. Afonso Henriques, o primeiro rei de Portugal, no ano de 1109!",
                details = mapOf("Cidade" to "Guimarães", "Primeiro Rei" to "D. Afonso Henriques", "Ano de Fundação" to "1143 (Tratado de Zamora)", "Destaque" to "Torre de Menagem"),
                speechText = "Guimarães é o berço de Portugal, onde tudo começou com o nosso primeiro rei!",
                syllables = "CAS • TE • LO",
                badge = "HISTÓRIA ⚔️",
                colorHex = 0xFFCFD8DC
            ),
            EducationalItem(
                id = "pt_azulejo",
                name = "Azulejo Português",
                category = "Símbolos",
                emoji = "🖼️",
                subtitle = "Arte Ilustrada",
                fact = "Os azulejos contam histórias do mar e do campo em belíssimas pinturas a azul e branco!",
                details = mapOf("Cores" to "Azul Cobalto e Branco", "Onde Ver" to "Estações, Igrejas e Fachadas", "Tradição" to "Séculos de Mestria", "Material" to "Cerâmica Vidrada"),
                speechText = "Os azulejos portugueses enfeitam as paredes com desenhos maravilhosos!",
                syllables = "A • ZU • LEI • JO",
                badge = "ARTE 🎨",
                colorHex = 0xFF82B1FF
            ),
            EducationalItem(
                id = "pt_pastel",
                name = "Pastel de Nata",
                category = "Gastronomia",
                emoji = "🥧",
                subtitle = "Doce Tradicional",
                fact = "O Pastel de Nata nasceu no Mosteiro dos Jerónimos em Belém e é polvilhado com canela e açúcar!",
                details = mapOf("Origem" to "Belém (Lisboa)", "Ingredientes" to "Massa folhada, nata, ovos e canela", "Fama" to "Famoso no mundo inteiro", "Servido" to "Quentinho com canela"),
                speechText = "O pastel de nata é crocante por fora e cremoso por dentro, uma delícia portuguesa!",
                syllables = "PAS • TEL • DE • NA • TA",
                badge = "DOCE 🥧",
                colorHex = 0xFFFFD54F
            ),
            EducationalItem(
                id = "pt_fado",
                name = "Guitarra e Fado",
                category = "Tradições",
                emoji = "🎸",
                subtitle = "Música da Alma",
                fact = "O Fado é a canção tradicional de Portugal, acompanhado pela bela Guitarra Portuguesa de 12 cordas!",
                details = mapOf("Instrumento" to "Guitarra Portuguesa em forma de coração", "Património" to "UNESCO Imaterial", "Origem" to "Bairros Históricos de Lisboa e Coimbra", "Sentimento" to "Saudade e Amor"),
                speechText = "O fado e a guitarra portuguesa tocam o coração com melodias inesquecíveis!",
                syllables = "GUI • TAR • RA",
                badge = "MÚSICA 🎶",
                colorHex = 0xFFFFCC80
            ),
            EducationalItem(
                id = "pt_porto",
                name = "Ponte D. Luís e Porto",
                category = "Cidades",
                emoji = "🌉",
                subtitle = "A Invicta e o Rio Douro",
                fact = "A Ponte D. Luís liga o Porto a Vila Nova de Gaia sobre o magnífico Rio Douro!",
                details = mapOf("Cidade" to "Porto", "Rio" to "Douro", "Barcos" to "Rabelos", "Destaque" to "Torre dos Clérigos e Livraria Lello"),
                speechText = "A cidade do Porto tem pontes grandiosas, barcos rabelos e a bela Torre dos Clérigos!",
                syllables = "PON • TE",
                badge = "PORTO 🍷",
                colorHex = 0xFF90CAF9
            ),
            EducationalItem(
                id = "pt_bandeira",
                name = "Bandeira de Portugal",
                category = "Símbolos",
                emoji = "🇵🇹",
                subtitle = "Verde, Vermelho e Esfera",
                fact = "A bandeira nacional tem o verde da esperança, o vermelho da coragem, o Escudo das Quinas e a Esfera Armilar dos descobrimentos!",
                details = mapOf("Cores" to "Verde e Vermelho", "Símbolo" to "Esfera Armilar e Escudo", "Significado" to "Pátria, História e Heroísmo", "Uso" to "Símbolo máximo da Nação"),
                speechText = "A bandeira de Portugal brilha com o verde e o vermelho, as quinas e a esfera armilar!",
                syllables = "BAN • DEI • RA",
                badge = "PÁTRIA 🇵🇹",
                colorHex = 0xFFFFCDD2
            ),
            EducationalItem(
                id = "pt_padrao",
                name = "Padrão dos Descobrimentos",
                category = "Monumentos",
                emoji = "⛵",
                subtitle = "Homenagem aos Navegadores",
                fact = "Situado à beira do Tejo em Lisboa, o Padrão dos Descobrimentos tem a forma de uma caravela com o Infante D. Henrique à frente!",
                details = mapOf("Local" to "Belém (Lisboa)", "Homenagem" to "Navegadores portugueses", "Figura Principal" to "Infante D. Henrique", "Destaque" to "Rosa dos Ventos gigante no chão"),
                speechText = "O Padrão dos Descobrimentos lembra os heróis que navegaram pelos mares desconhecidos!",
                syllables = "PA • DRÃO",
                badge = "HISTÓRIA 🌍",
                colorHex = 0xFFFFE082
            ),
            EducationalItem(
                id = "pt_caravela",
                name = "Caravela Portuguesa",
                category = "Símbolos",
                emoji = "⛵",
                subtitle = "O Navio das Descobertas",
                fact = "A caravela era um navio rápido e ágil inventado pelos portugueses que permitiu explorar novos mundos e oceanos!",
                details = mapOf("Tipo" to "Embarcação à vela", "Uso" to "Viagens de Descoberta (séculos XV e XVI)", "Vantagem" to "Navegar contra o vento", "Heróis" to "Vasco da Gama, Bartolomeu Dias"),
                speechText = "A caravela desbravava os oceanos rumo a novos mundos!",
                syllables = "CA • RA • VE • LA",
                badge = "MAR 🌊",
                colorHex = 0xFF80CBC4
            ),
            EducationalItem(
                id = "pt_lencos",
                name = "Lenços dos Namorados",
                category = "Tradições",
                emoji = "💌",
                subtitle = "Bordados do Minho",
                fact = "Os lenços dos namorados do Minho são feitos de linho e bordados à mão com versos de amor e corações coloridos!",
                details = mapOf("Origem" to "Minho (Viana, Braga, Guimarães)", "Material" to "Linho bordado", "Significado" to "Declaração de amor", "Destaque" to "Erros de ortografia originais cheios de carinho"),
                speechText = "Os lenços dos namorados trazem lindos bordados e versinhos de amor do Minho!",
                syllables = "LEN • ÇOS",
                badge = "AMOR ❤️",
                colorHex = 0xFFFF8A80
            ),
            EducationalItem(
                id = "pt_cabo",
                name = "Cabo da Roca",
                category = "Monumentos",
                emoji = "🌊",
                subtitle = "Onde a Terra Acaba e o Mar Começa",
                fact = "O Cabo da Roca é o ponto mais ocidental da Europa continental, com falésias altíssimas sobre o Oceano Atlântico!",
                details = mapOf("Localização" to "Sintra", "Destaque" to "Farol histórico e monumento de pedra", "Frase Célebre" to "Aqui... onde a terra se acaba e o mar começa (Camões)", "Vistas" to "Oceano Atlântico a perder de vista"),
                speechText = "No Cabo da Roca, a terra termina e o oceano Atlântico estende-se sem fim!",
                syllables = "CA • BO • DA • RO • CA",
                badge = "EUROPA 🗺️",
                colorHex = 0xFFB3E5FC
            ),
            EducationalItem(
                id = "pt_queijo",
                name = "Queijo da Serra",
                category = "Gastronomia",
                emoji = "🧀",
                subtitle = "O Sabor da Montanha",
                fact = "O Queijo da Serra da Estrela é um queijo artesanal delicioso feito com leite de ovelha e flor de cardo na montanha mais alta de Portugal!",
                details = mapOf("Origem" to "Serra da Estrela", "Textura" to "Cremosa e amanteigada", "Ingredientes" to "Leite de ovelha Bordaleira", "Tradição" to "Um dos queijos mais antigos do mundo"),
                speechText = "O queijo da serra da estrela é cremoso e muito saboroso!",
                syllables = "QUEI • JO",
                badge = "QUEIJO 🧀",
                colorHex = 0xFFFFECB3
            ),
            EducationalItem(
                id = "pt_caldo",
                name = "Caldo Verde",
                category = "Gastronomia",
                emoji = "🥣",
                subtitle = "A Sopa Tradicional",
                fact = "O caldo verde é uma sopa reconfortante feita com batata, couve-galega cortada muito fininha e rodelas de chouriço!",
                details = mapOf("Origem" to "Minho e Tradição Portuguesa", "Ingredientes" to "Batata, couve, azeite e chouriço", "Companhia" to "Broa de milho quentinha", "Festa" to "Prato obrigatório nos Santos Populares"),
                speechText = "Uma tigela de caldo verde quentinho conforta qualquer coração!",
                syllables = "CAL • DO • VER • DE",
                badge = "SOPA 🍲",
                colorHex = 0xFFC8E6C9
            ),
            EducationalItem(
                id = "pt_clerigos",
                name = "Torre dos Clérigos",
                category = "Monumentos",
                emoji = "⛪",
                subtitle = "O Coração do Porto",
                fact = "A Torre dos Clérigos é uma torre barroca altíssima no Porto donde se avista toda a cidade e o rio Douro!",
                details = mapOf("Cidade" to "Porto", "Estilo" to "Barroco", "Arquiteto" to "Nicolau Nasoni", "Altura" to "Mais de 75 metros de altura"),
                speechText = "A Torre dos Clérigos destaca-se nos céus do Porto com a sua grande altura!",
                syllables = "CLÉ • RI • GOS",
                badge = "PORTO 🏛️",
                colorHex = 0xFFFFCC80
            )
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_pt1", "Quem foi o primeiro Rei de Portugal?", listOf("D. Afonso Henriques", "D. Manuel I", "D. Dinis"), "D. Afonso Henriques", "D. Afonso Henriques fundou Portugal!", "🛡️"),
            EducationalQuizQuestion("q_pt2", "Qual é o doce tradicional famoso de Belém?", listOf("Pastel de Nata", "Croissant", "Donut"), "Pastel de Nata", "O Pastel de Nata de Belém é um tesouro nacional!", "🥧"),
            EducationalQuizQuestion("q_pt3", "Que instrumento com formato de coração acompanha o Fado?", listOf("Guitarra Portuguesa", "Bateria", "Flauta"), "Guitarra Portuguesa", "A Guitarra Portuguesa tem 12 cordas!", "🎸"),
            EducationalQuizQuestion("q_pt4", "Que cidade é conhecida como o Berço da Nação?", listOf("Guimarães", "Lisboa", "Faro"), "Guimarães", "Guimarães é o berço onde nasceu Portugal!", "🏰")
        )
    )

    // ==========================================
    // 6. DINOSSAUROS DO ZÉ TRAQUINA
    // ==========================================
    private val dinossaurosTopic = EducationalTopicData(
        id = "dinossauros",
        title = "Dinossauros",
        subtitle = "Viaja no tempo até à era dos gigantes pré-históricos 🦖",
        iconEmoji = "🦖",
        accentColor = Color(0xFF2E7D32),
        headerGradient = listOf(Color(0xFF1B5E20), Color(0xFF66BB6A)),
        searchPlaceholder = "Procurar dinossauro ou fóssil...",
        customTabName = "Dinossauros",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🦕", Color(0xFF2E7D32)),
            EducationalCategoryFilter("Carnívoros", "🦖", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Herbívoros", "🌿", Color(0xFF388E3C)),
            EducationalCategoryFilter("Voadores", "🦅", Color(0xFF0288D1)),
            EducationalCategoryFilter("Fósseis", "🦴", Color(0xFF795548))
        ),
        items = listOf(
            EducationalItem("dino_tiranossauro", "Tiranossauro", "Carnívoro", "🦖", "Rei dos Predadores", "O T-Rex tinha dentes enormes e braços muito pequeninos.", mapOf("Tipo" to "Carnívoro", "Ordem" to "1"), "O T-Rex tinha dentes do tamanho de bananas!", "TI • RA • NOS • SAU • RO", "CARNÍVORO", 0xFFFFAB91),
            EducationalItem("dino_triceratops", "Triceratops", "Herbívoro", "🦏", "Três Cornos Fortes", "O Triceratops tinha três cornos grandes para se proteger dos predadores.", mapOf("Tipo" to "Herbívoro", "Ordem" to "2"), "O Triceratops parecia um rinoceronte gigante!", "TRI • CE • RA • TOPS", "HERBÍVORO", 0xFFC8E6C9),
            EducationalItem("dino_braquiossauro", "Braquiossauro", "Herbívoro", "🦕", "Pescoço Longo", "O Braquiossauro tinha um pescoço tão longo que comia as folhas do topo das árvores.", mapOf("Tipo" to "Herbívoro", "Ordem" to "3"), "O Braquiossauro era mais alto que um prédio!", "BRA • QUI • OS • SAU • RO", "HERBÍVORO", 0xFFA5D6A7),
            EducationalItem("dino_estegossauro", "Estegossauro", "Herbívoro", "🦎", "Placas nas Costas", "O Estegossauro tinha placas ósseas nas costas e espinhos na cauda.", mapOf("Tipo" to "Herbívoro", "Ordem" to "4"), "O Estegossauro usava a cauda espinhosa para se defender!", "ES • TE • GOS • SAU • RO", "HERBÍVORO", 0xFFFFD54F),
            EducationalItem("dino_pterodactilo", "Pterodáctilo", "Voador", "🦅", "Réptil Voador", "O Pterodáctilo não era bem um dinossauro, mas um réptil que voava no céu.", mapOf("Tipo" to "Voador", "Ordem" to "5"), "O Pterodáctilo voava como um grande pássaro pré-histórico!", "PTE • RO • DÁC • TI • LO", "VOADOR", 0xFF81D4FA),
            EducationalItem("dino_velociraptor", "Velociraptor", "Carnívoro", "🦖", "Predador Veloz", "O Velociraptor era pequeno, mas corria muito depressa e caçava em grupo.", mapOf("Tipo" to "Carnívoro", "Ordem" to "6"), "O Velociraptor era muito inteligente e rápido!", "VE • LO • CI • RAP • TOR", "CARNÍVORO", 0xFFEF9A9A),
            EducationalItem("dino_ancilossauro", "Ancilossauro", "Herbívoro", "🐢", "Armadura Pesada", "O Ancilossauro era o tanque dos dinossauros, coberto de ossos fortes.", mapOf("Tipo" to "Herbívoro", "Ordem" to "7"), "O Ancilossauro tinha uma cauda que parecia um martelo!", "AN • CI • LOS • SAU • RO", "HERBÍVORO", 0xFFBCAAA4),
            EducationalItem("dino_espinossauro", "Espinossauro", "Carnívoro", "🐊", "Vela nas Costas", "O Espinossauro era ainda maior que o T-Rex e adorava caçar peixes no rio.", mapOf("Tipo" to "Carnívoro", "Ordem" to "8"), "O Espinossauro passava muito tempo na água a nadar!", "ES • PI • NOS • SAU • RO", "CARNÍVORO", 0xFFCE93D8),
            EducationalItem("dino_alossauro", "Alossauro", "Carnívoro", "🦖", "Leão do Jurássico", "O Alossauro era um caçador feroz que viveu antes do T-Rex existir.", mapOf("Tipo" to "Carnívoro", "Ordem" to "9"), "O Alossauro era o predador mais perigoso da sua altura!", "A • LOS • SAU • RO", "CARNÍVORO", 0xFFFFCC80),
            EducationalItem("dino_parassaurolofo", "Parassaurolofo", "Herbívoro", "🦕", "Crista de Trombeta", "Tinha uma crista comprida na cabeça que usava para emitir sons muito altos.", mapOf("Tipo" to "Herbívoro", "Ordem" to "10"), "A sua cabeça funcionava como uma trombeta gigante!", "PA • RAS • SAU • RO • LO • FO", "HERBÍVORO", 0xFFFFF59D),
            EducationalItem("dino_estiracossauro", "Estiracossauro", "Herbívoro", "🦏", "Gola com Espinhos", "Parecido com o Triceratops, mas com muitos espinhos longos na sua gola óssea.", mapOf("Tipo" to "Herbívoro", "Ordem" to "11"), "O Estiracossauro tinha uma verdadeira coroa de espinhos!", "ES • TI • RA • COS • SAU • RO", "HERBÍVORO", 0xFFE6EE9C),
            EducationalItem("dino_plessiossauro", "Plessiossauro", "Aquático", "🐋", "Monstro Marinho", "O Plessiossauro era um gigante dos oceanos com um pescoço muito comprido.", mapOf("Tipo" to "Aquático", "Ordem" to "12"), "O Plessiossauro usava quatro barbatanas para nadar nos mares antigos!", "PLES • SI • OS • SAU • RO", "AQUÁTICO", 0xFF90CAF9)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_din1", "Quantos cornos tinha o dinossauro Triceratops?", listOf("3 Cornos", "1 Corno", "5 Cornos"), "3 Cornos", "O Triceratops tinha exatamente 3 cornos!", "🦏"),
            EducationalQuizQuestion("q_din2", "Como se chama o cientista que estuda os fósseis dos dinossauros?", listOf("Paleontólogo", "Astronauta", "Marinheiro"), "Paleontólogo", "O paleontólogo descobre e estuda fósseis!", "🔍"),
            EducationalQuizQuestion("q_din3", "O que comia o gigante Braquiossauro?", listOf("Folhas e plantas", "Peixes do mar", "Gelados"), "Folhas e plantas", "O Braquiossauro era um dinossauro herbívoro!", "🦕")
        )
    )

    // ==========================================
    // 7. ALFABETO DO ZÉ TRAQUINA
    // ==========================================
    private val alfabetoTopic = EducationalTopicData(
        id = "alfabeto",
        title = "Alfabeto",
        subtitle = "Aprende as 26 letras de A a Z com palavras mágicas 🔤",
        iconEmoji = "🔤",
        accentColor = Color(0xFF3F51B5),
        headerGradient = listOf(Color(0xFF1A237E), Color(0xFF3F51B5)),
        searchPlaceholder = "Procurar letra ou palavra...",
        enableSyllables = true,
        customTabName = "Letras",
        categories = emptyList(),
        items = listOf(
            EducationalItem("A", "Abelha", "Vogais", "🐝", "Letra A", "A de Abelha. A abelha faz mel docinho!", mapOf("Letra" to "A", "Maiúscula" to "A", "Minúscula" to "a"), "A de Abelha! Voa de flor em flor.", "A • BE • LHA", "LETRA A", 0xFFFFE082),
            EducationalItem("B", "Bola", "Objetos", "⚽", "Letra B", "B de Bola. Vamos jogar com amigos!", mapOf("Letra" to "B", "Maiúscula" to "B", "Minúscula" to "b"), "B de Bola! Redonda e saltitona.", "BO • LA", "LETRA B", 0xFF80DEEA),
            EducationalItem("C", "Casa", "Objetos", "🏠", "Letra C", "C de Casa. O nosso lar acolhedor!", mapOf("Letra" to "C", "Maiúscula" to "C", "Minúscula" to "c"), "C de Casa! Onde vive a família.", "CA • SA", "LETRA C", 0xFFFFAB91),
            EducationalItem("D", "Dado", "Objetos", "🎲", "Letra D", "D de Dado. Seis faces com números!", mapOf("Letra" to "D", "Maiúscula" to "D", "Minúscula" to "d"), "D de Dado! Lança e diverte-te.", "DA • DO", "LETRA D", 0xFFC5CAE9),
            EducationalItem("E", "Elefante", "Vogais", "🐘", "Letra E", "E de Elefante. Tromba comprida e amiga!", mapOf("Letra" to "E", "Maiúscula" to "E", "Minúscula" to "e"), "E de Elefante! O gigante da savana.", "E • LE • FAN • TE", "LETRA E", 0xFFA5D6A7),
            EducationalItem("F", "Foguete", "Objetos", "🚀", "Letra F", "F de Foguete. Voa até às estrelas!", mapOf("Letra" to "F", "Maiúscula" to "F", "Minúscula" to "f"), "F de Foguete! Rumo ao espaço.", "FO • GUE • TE", "LETRA F", 0xFFF48FB1),
            EducationalItem("G", "Gato", "Animais", "🐱", "Letra G", "G de Gato. Miau, miau carinhoso!", mapOf("Letra" to "G", "Maiúscula" to "G", "Minúscula" to "g"), "G de Gato! Ronrona com festinhas.", "GA • TO", "LETRA G", 0xFFFFCC80),
            EducationalItem("H", "Hipopótamo", "Animais", "🦛", "Letra H", "H de Hipopótamo. Toma banho no rio!", mapOf("Letra" to "H", "Maiúscula" to "H", "Minúscula" to "h"), "H de Hipopótamo! Adora água fresca.", "HI • PO • PÓ • TA • MO", "LETRA H", 0xFFB39DDB),
            EducationalItem("I", "Ilha", "Vogais", "🏝️", "Letra I", "I de Ilha. Terra rodeada de mar azul!", mapOf("Letra" to "I", "Maiúscula" to "I", "Minúscula" to "i"), "I de Ilha! Um paraíso no oceano.", "I • LHA", "LETRA I", 0xFF80CBC4),
            EducationalItem("J", "Jacaré", "Animais", "🐊", "Letra J", "J de Jacaré. Nada perto das lagoas!", mapOf("Letra" to "J", "Maiúscula" to "J", "Minúscula" to "j"), "J de Jacaré! Nada com escamas fortes.", "JA • CA • RÉ", "LETRA J", 0xFFC8E6C9),
            EducationalItem("K", "Kiwi", "Objetos", "🥝", "Letra K", "K de Kiwi. Fruta saborosa e verde!", mapOf("Letra" to "K", "Maiúscula" to "K", "Minúscula" to "k"), "K de Kiwi! Fruta verde e cheia de vitaminas.", "KI • WI", "LETRA K", 0xFFA5D6A7),
            EducationalItem("L", "Leão", "Animais", "🦁", "Letra L", "L de Leão. O rei majestoso!", mapOf("Letra" to "L", "Maiúscula" to "L", "Minúscula" to "l"), "L de Leão! Tem uma juba linda.", "LE • ÃO", "LETRA L", 0xFFFFE082),
            EducationalItem("M", "Macaco", "Animais", "🐒", "Letra M", "M de Macaco. Salta entre os ramos!", mapOf("Letra" to "M", "Maiúscula" to "M", "Minúscula" to "m"), "M de Macaco! Come bananas doces.", "MA • CA • CO", "LETRA M", 0xFFD7CCC8),
            EducationalItem("N", "Nuvem", "Objetos", "☁️", "Letra N", "N de Nuvem. Gotinhas de chuva no céu!", mapOf("Letra" to "N", "Maiúscula" to "N", "Minúscula" to "n"), "N de Nuvem! Parece algodão fofo.", "NU • VEM", "LETRA N", 0xFFB3E5FC),
            EducationalItem("O", "Ovelha", "Vogais", "🐑", "Letra O", "O de Ovelha. Dá lã quentinha!", mapOf("Letra" to "O", "Maiúscula" to "O", "Minúscula" to "o"), "O de Ovelha! Faz méé no campo.", "O • VE • LHA", "LETRA O", 0xFFE1BEE7),
            EducationalItem("P", "Pato", "Animais", "🦆", "Letra P", "P de Pato. Quack quack no lago!", mapOf("Letra" to "P", "Maiúscula" to "P", "Minúscula" to "p"), "P de Pato! Nada com pés de pato.", "PA • TO", "LETRA P", 0xFFFFF59D),
            EducationalItem("Q", "Queijo", "Objetos", "🧀", "Letra Q", "Q de Queijo. Feito com leite bom!", mapOf("Letra" to "Q", "Maiúscula" to "Q", "Minúscula" to "q"), "Q de Queijo! Nutritivo e saboroso.", "QUEI • JO", "LETRA Q", 0xFFFFE082),
            EducationalItem("R", "Robô", "Objetos", "🤖", "Letra R", "R de Robô. Máquina engenhosa!", mapOf("Letra" to "R", "Maiúscula" to "R", "Minúscula" to "r"), "R de Robô! Pisca as suas luzinhas.", "RO • BÔ", "LETRA R", 0xFFCFD8DC),
            EducationalItem("S", "Sol", "Objetos", "☀️", "Letra S", "S de Sol. Luz e calor para todos!", mapOf("Letra" to "S", "Maiúscula" to "S", "Minúscula" to "s"), "S de Sol! Ilumina todo o dia.", "SOL", "LETRA S", 0xFFFFE082),
            EducationalItem("T", "Tartaruga", "Animais", "🐢", "Letra T", "T de Tartaruga. Anda devagarinho!", mapOf("Letra" to "T", "Maiúscula" to "T", "Minúscula" to "t"), "T de Tartaruga! Tem uma carapaça dura.", "TAR • TA • RU • GA", "LETRA T", 0xFF80DEEA),
            EducationalItem("U", "Uva", "Vogais", "🍇", "Letra U", "U de Uva. Cresce em cachos roxos!", mapOf("Letra" to "U", "Maiúscula" to "U", "Minúscula" to "u"), "U de Uva! Doce e sumarenta.", "U • VA", "LETRA U", 0xFFD1C4E9),
            EducationalItem("V", "Vaca", "Animais", "🐮", "Letra V", "V de Vaca. Dá leite fresquinho!", mapOf("Letra" to "V", "Maiúscula" to "V", "Minúscula" to "v"), "V de Vaca! Come ervinha no prado.", "VA • CA", "LETRA V", 0xFFFFCC80),
            EducationalItem("W", "Wi-Fi", "Objetos", "📶", "Letra W", "W de Wi-Fi. Ligação ao mundo digital!", mapOf("Letra" to "W", "Maiúscula" to "W", "Minúscula" to "w"), "W de Wi-Fi! Coneta a tecnologia.", "WI • FI", "LETRA W", 0xFF80DEEA),
            EducationalItem("X", "Xadrez", "Objetos", "♟️", "Letra X", "X de Xadrez. Jogo de estratégia e mente!", mapOf("Letra" to "X", "Maiúscula" to "X", "Minúscula" to "x"), "X de Xadrez! Desafio de inteligência.", "XA • DREZ", "LETRA X", 0xFFFFAB91),
            EducationalItem("Y", "Yoga", "Objetos", "🧘", "Letra Y", "Y de Yoga. Exercício para corpo e mente!", mapOf("Letra" to "Y", "Maiúscula" to "Y", "Minúscula" to "y"), "Y de Yoga! Calma, foco e equilíbrio.", "YO • GA", "LETRA Y", 0xFFC5CAE9),
            EducationalItem("Z", "Zebra", "Animais", "🦓", "Letra Z", "Z de Zebra. Riscas pretas e brancas!", mapOf("Letra" to "Z", "Maiúscula" to "Z", "Minúscula" to "z"), "Z de Zebra e Zé Traquina!", "ZE • BRA", "LETRA Z", 0xFFEEEEEE)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_alf1", "Qual é a primeira letra do alfabeto?", listOf("A", "B", "Z"), "A", "A letra A é a primeira letra!", "🔤"),
            EducationalQuizQuestion("q_alf2", "Com que letra começa o nome do nosso amigo Zé Traquina?", listOf("Z", "M", "P"), "Z", "Zé começa com a letra Z!", "🦓"),
            EducationalQuizQuestion("q_alf3", "Qual destas palavras começa pela letra B?", listOf("Bola", "Casa", "Gato"), "Bola", "Bola começa pela letra B!", "⚽")
        )
    )

    // ==========================================
    // 8. VOGAIS DO ZÉ TRAQUINA
    // ==========================================
    private val vogaisTopic = EducationalTopicData(
        id = "vogais",
        title = "Vogais",
        subtitle = "As 5 letras mágicas da fala: A, E, I, O, U 🔠",
        iconEmoji = "🔠",
        accentColor = Color(0xFF00897B),
        headerGradient = listOf(Color(0xFF004D40), Color(0xFF00897B)),
        searchPlaceholder = "Procurar vogal...",
        enableSyllables = true,
        customTabName = "Vogais",
        categories = listOf(
            EducationalCategoryFilter("Todas", "🔠", Color(0xFF00897B)),
            EducationalCategoryFilter("Abertas", "👄", Color(0xFFE65100)),
            EducationalCategoryFilter("Fechadas", "⭐", Color(0xFF1976D2))
        ),
        items = listOf(
            EducationalItem("v_a", "Vogal A", "Abertas", "🌳", "Árvore / Abelha", "Abre bem a boca e diz: Á!", mapOf("Som" to "Aberto", "Exemplos" to "Árvore, Abelha, Anel"), "Vogal A! Abre a boca bem redonda e diz Á.", "ÁR • VO • RE", "A", 0xFFFFE082),
            EducationalItem("v_e", "Vogal E", "Abertas", "⭐", "Estrela / Elefante", "Faz um sorriso alegre e diz: É!", mapOf("Som" to "Médio", "Exemplos" to "Estrela, Elefante, Escola"), "Vogal E! Sorri e pronuncia É.", "ES • TRE • LA", "E", 0xFF80DEEA),
            EducationalItem("v_i", "Vogal I", "Fechadas", "🏝️", "Ilha / Iglu", "Estica os lábios de lado e diz: Í!", mapOf("Som" to "Agudo", "Exemplos" to "Ilha, Iglu, Índio"), "Vogal I! Um sonzinho fino e agudo.", "I • LHA", "I", 0xFFFFAB91),
            EducationalItem("v_o", "Vogal O", "Abertas", "👓", "Óculos / Ovelha", "Faz uma boca redonda como um círculo: Ó!", mapOf("Som" to "Redondo", "Exemplos" to "Óculos, Ovelha, Ovo"), "Vogal O! Boca redonda como uma roda.", "Ó • CU • LOS", "O", 0xFFC5CAE9),
            EducationalItem("v_u", "Vogal U", "Fechadas", "🐻", "Urso / Uva", "Faz um biquinho engraçado e diz: Ú!", mapOf("Som" to "Fechado", "Exemplos" to "Urso, Uva, Universo"), "Vogal U! Faz um biquinho e diz Ú.", "UR • SO", "U", 0xFFA5D6A7)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_vog1", "Quantas vogais existem no alfabeto português?", listOf("5 Vogais (A, E, I, O, U)", "3 Vogais", "10 Vogais"), "5 Vogais (A, E, I, O, U)", "São exatamente 5 vogais mágicas!", "🔠"),
            EducationalQuizQuestion("q_vog2", "Com que vogal começa a palavra Estrela?", listOf("E", "A", "U"), "E", "Estrela começa com a vogal E!", "⭐"),
            EducationalQuizQuestion("q_vog3", "Com que vogal começa a palavra Urso?", listOf("U", "I", "O"), "U", "Urso começa com a vogal U!", "🐻")
        )
    )

    // ==========================================
    // 9. NÚMEROS DO ZÉ TRAQUINA
    // ==========================================
    private val numerosTopic = EducationalTopicData(
        id = "numeros",
        title = "Números",
        subtitle = "Conta com dedinhos e descobre os números de 1 a 10! 🔢",
        iconEmoji = "🔢",
        accentColor = Color(0xFFFB8C00),
        headerGradient = listOf(Color(0xFFE65100), Color(0xFFFB8C00)),
        searchPlaceholder = "Procurar número...",
        customTabName = "Números",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🔢", Color(0xFFFB8C00)),
            EducationalCategoryFilter("1 a 50", "🎈", Color(0xFF1976D2)),
            EducationalCategoryFilter("51 a 100", "🌟", Color(0xFF388E3C))
        ),
        items = (1..100).map { num ->
            val emoji = when(num % 10) {
                1 -> "🎈"
                2 -> "🐱"
                3 -> "⭐"
                4 -> "🍎"
                5 -> "🚀"
                6 -> "🎨"
                7 -> "🍓"
                8 -> "🐥"
                9 -> "🦋"
                else -> "🌟"
            }
            val word = numberToPortugueseWord(num)
            val category = if (num <= 50) "1 a 50" else "51 a 100"
            EducationalItem(
                id = "num_$num",
                name = "Número $num ($word)",
                category = category,
                emoji = emoji,
                subtitle = "$word $emoji",
                fact = "Podes contar $num com os teus dedinhos das mãos!",
                details = mapOf("Número" to "$num", "Por extenso" to word, "Contagem" to "$num $emoji"),
                speechText = "Número $num! $word.",
                syllables = word.uppercase(),
                badge = "$num",
                colorHex = if (num % 2 == 0) 0xFF80DEEA else 0xFFFFD54F
            )
        },
        quizQuestions = listOf(
            EducationalQuizQuestion("q_num1", "Quantos dedos tens numa mão?", listOf("5 dedos", "3 dedos", "10 dedos"), "5 dedos", "Cada mão tem 5 dedinhos!", "✋"),
            EducationalQuizQuestion("q_num2", "Qual é o número que vem a seguir ao 3?", listOf("4", "2", "6"), "4", "A seguir ao três vem o quatro!", "🔢"),
            EducationalQuizQuestion("q_num3", "Se contares 1, 2, 3, 4... qual é o próximo?", listOf("5", "6", "1"), "5", "Muito bem, é o número 5!", "🎈")
        )
    )

    // ==========================================
    // 10. FRUTAS DO ZÉ TRAQUINA
    // ==========================================
    private val frutasTopic = EducationalTopicData(
        id = "frutas",
        title = "Frutas",
        subtitle = "Descobre as frutas mais doces, sumarentas e cheias de vitaminas 🍎",
        iconEmoji = "🍎",
        accentColor = Color(0xFFD81B60),
        headerGradient = listOf(Color(0xFF880E4F), Color(0xFFD81B60)),
        searchPlaceholder = "Procurar fruta...",
        customTabName = "Frutas",
        categories = listOf(
            EducationalCategoryFilter("Todas", "🍎", Color(0xFFD81B60)),
            EducationalCategoryFilter("Cítricas", "🍊", Color(0xFFF57C00)),
            EducationalCategoryFilter("Doces", "🍓", Color(0xFFC2185B)),
            EducationalCategoryFilter("Tropicais", "🍌", Color(0xFFFBC02D))
        ),
        items = listOf(
            EducationalItem("fr_maca", "Maçã", "Doces", "🍎", "Crocante e Doce", "A maçã pode ser vermelha ou verde e faz muito bem aos dentes!", mapOf("Vitamina" to "Vitamina C e Fibras", "Cor" to "Vermelha/Verde"), "A maçã é estaladiça e doce!", "MA • ÇÃ", "DOCE", 0xFFFF8A80),
            EducationalItem("fr_banana", "Banana", "Tropicais", "🍌", "Energia Rápida", "A banana é rica em potássio e dá super energia para brincar!", mapOf("Vitamina" to "Potássio e B6", "Cor" to "Amarela"), "A banana é macia e cheia de energia!", "BA • NA • NA", "ENERGIA", 0xFFFFE082),
            EducationalItem("fr_laranja", "Laranja", "Cítricas", "🍊", "Sumo e Vitamina C", "A laranja fortalece as defesas do teu corpo contra constipações!", mapOf("Vitamina" to "Rica em Vitamina C", "Sabor" to "Sumarenta"), "A laranja dá um sumo fresco delicioso!", "LA • RAN • JA", "SUMO", 0xFFFFCC80),
            EducationalItem("fr_morango", "Morango", "Doces", "🍓", "Fruta do Bosque", "O morango tem sementinhas pelo lado de fora e um perfume doce!", mapOf("Cor" to "Vermelho Vivo", "Sabor" to "Doce e Aveludado"), "O morango é uma delícia da natureza!", "MO • RAN • GO", "DELÍCIA", 0xFFF48FB1),
            EducationalItem("fr_uva", "Uva", "Doces", "🍇", "Pequenas e Doces", "As uvas crescem em cachos e podem ser roxas ou verdes!", mapOf("Cor" to "Roxa/Verde", "Tipo" to "Cacho"), "As uvas são docinhas e ótimas para partilhar!", "U • VA", "CACHO", 0xFFE1BEE7),
            EducationalItem("fr_pera", "Pêra", "Doces", "🍐", "Sumarenta e Macia", "A pêra é uma fruta muito hidratante e doce quando está madura.", mapOf("Vitamina" to "Fibras", "Textura" to "Macia"), "A pêra é doce e muito sumarenta!", "PÊ • RA", "HIDRATAÇÃO", 0xFFC8E6C9),
            EducationalItem("fr_ananas", "Ananás", "Tropicais", "🍍", "Rei dos Trópicos", "O ananás tem uma coroa e uma casca com picos, mas é uma delícia!", mapOf("Vitamina" to "Vitamina C", "Sabor" to "Tropical"), "O ananás é o rei das frutas tropicais!", "A • NA • NÁS", "COROA", 0xFFFFF59D),
            EducationalItem("fr_melancia", "Melancia", "Tropicais", "🍉", "Fresca no Verão", "A melancia é 92% água e é perfeita para refrescar nos dias quentes!", mapOf("Água" to "92% Água pura", "Tamanho" to "Grande e redonda"), "A melancia é super fresca e sumarenta!", "ME • LAN • CI • A", "REFRESCO", 0xFFA5D6A7),
            EducationalItem("fr_kiwi", "Kiwi", "Tropicais", "🥝", "Pequeno e Verde", "O kiwi é castanho por fora e verde brilhante por dentro com sementinhas!", mapOf("Vitamina" to "Vitamina C", "Cor" to "Verde"), "O kiwi é pequeno mas cheio de vitaminas!", "KI • WI", "VITALIDADE", 0xFFC8E6C9),
            EducationalItem("fr_manga", "Manga", "Tropicais", "🥭", "Rainha das Frutas", "A manga é muito doce, amarela e tem um cheirinho tropical maravilhoso!", mapOf("Sabor" to "Doce Tropical", "Cor" to "Laranja/Amarela"), "A manga é doce e muito aveludada!", "MAN • GA", "TROPICAL", 0xFFFFE082),
            EducationalItem("fr_pessego", "Pêssego", "Doces", "🍑", "Pele de Veludo", "O pêssego tem uma pele macia como veludo e um caroço grande no meio.", mapOf("Vitamina" to "A e C", "Textura" to "Aveludada"), "O pêssego é doce e tem a pele macia!", "PÊS • SE • GO", "VELUDO", 0xFFFFCC80),
            EducationalItem("fr_mirtilo", "Mirtilo", "Doces", "🫐", "Pequeno e Azul", "O mirtilo é uma baga azul muito pequena que ajuda a memória!", mapOf("Cor" to "Azul Escuro", "Benefício" to "Antioxidante"), "Os mirtilos são pequenos super-alimentos!", "MIR • TI • LO", "AZUL", 0xFFC5CAE9),
            EducationalItem("fr_limao", "Limão", "Cítricas", "🍋", "Azedo e Fresco", "O limão é azedo mas faz limonadas frescas e ajuda a curar gripes!", mapOf("Sabor" to "Ácido/Azedo", "Vitamina" to "Vitamina C"), "O limão dá uma limonada super fresca!", "LI • MÃO", "ACIDEZ", 0xFFFFF9C4),
            EducationalItem("fr_cereja", "Cereja", "Doces", "🍒", "Pequena e Vermelha", "As cerejas costumam vir aos pares e são muito docinhas no Verão!", mapOf("Cor" to "Vermelho Escuro", "Tipo" to "Par"), "As cerejas são as jóias doces do Verão!", "CE • RE • JA", "VERÃO", 0xFFFF8A80),
            EducationalItem("fr_framboesa", "Framboesa", "Doces", "🍓", "Baga Delicada", "A framboesa é uma baga vermelha e muito delicada com um sabor único!", mapOf("Cor" to "Rosa/Vermelho", "Sabor" to "Agridoce"), "A framboesa é linda e muito saborosa!", "FRAM • BO • E • SA", "BAGA", 0xFFF8BBD0)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_fr1", "Que fruta amarela os macaquinhos adoram comer?", listOf("Banana", "Laranja", "Kiwi"), "Banana", "Os macacos adoram bananas doces!", "🍌"),
            EducationalQuizQuestion("q_fr2", "Que fruta vermelha tem pintinhas de sementes por fora?", listOf("Morango", "Maçã", "Pera"), "Morango", "O morango tem sementinhas na casca!", "🍓")
        )
    )

    // ==========================================
    // 11. PROFISSÕES DO ZÉ TRAQUINA
    // ==========================================
    private val profissoesTopic = EducationalTopicData(
        id = "profissoes",
        title = "Profissões",
        subtitle = "Explora o que faz cada profissional para ajudar o mundo! 👨‍✈️",
        iconEmoji = "👨‍⚕️",
        accentColor = Color(0xFF8E24AA),
        headerGradient = listOf(Color(0xFF4A148C), Color(0xFF8E24AA)),
        searchPlaceholder = "Procurar profissão...",
        customTabName = "Profissões",
        categories = listOf(
            EducationalCategoryFilter("Todas", "👨‍⚕️", Color(0xFF8E24AA)),
            EducationalCategoryFilter("Saúde e Cuidado", "🩺", Color(0xFF0288D1)),
            EducationalCategoryFilter("Segurança", "🚒", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Educação e Ciência", "🔭", Color(0xFFE65100)),
            EducationalCategoryFilter("Artes e Comida", "🎨", Color(0xFF43A047)),
            EducationalCategoryFilter("Construção e Natureza", "🏗️", Color(0xFFFDD835))
        ),
        items = listOf(
            EducationalItem("prof_professor", "Professor", "Educação e Ciência", "👨‍🏫", "Ensina coisas novas", "Os professores ensinam na escola. Ajudam a ler, escrever e aprender sobre o mundo!", mapOf("Local" to "Escola", "Ferramenta" to "Quadro e Livros"), "O professor ensina com carinho e dedicação!", "PRO • FES • SOR", "ESCOLA", 0xFFFFD54F),
            EducationalItem("prof_doutora", "Doutora", "Saúde e Cuidado", "👩‍⚕️", "Cuida da nossa saúde", "As doutoras tratam de nós quando estamos doentes e ajudam-nos a crescer fortes.", mapOf("Local" to "Hospital", "Ferramenta" to "Estetoscópio"), "A doutora ajuda-nos a ficar rapidamente curados!", "DOU • TO • RA", "SAÚDE", 0xFF80DEEA),
            EducationalItem("prof_bombeiro", "Bombeiro", "Segurança", "🚒", "Apaga fogos e salva pessoas", "Os bombeiros conduzem grandes carros vermelhos e são heróis muito corajosos.", mapOf("Veículo" to "Carro de Bombeiros", "Ferramenta" to "Mangueira"), "O bombeiro é um herói muito corajoso!", "BOM • BEI • RO", "HERÓI", 0xFFFF8A80),
            EducationalItem("prof_enfermeiro", "Enfermeiro", "Saúde e Cuidado", "👨‍⚕️", "Ajuda os doutores", "Os enfermeiros dão vacinas, medem a febre e tratam das nossas feridas.", mapOf("Local" to "Centro de Saúde", "Ferramenta" to "Termómetro"), "O enfermeiro cuida de ti com muito amor!", "EN • FER • MEI • RO", "CUIDADO", 0xFF81D4FA),
            EducationalItem("prof_policial", "Polícia", "Segurança", "👮", "Protege a cidade", "A polícia ajuda a manter as nossas ruas seguras e ajuda as pessoas perdidas.", mapOf("Veículo" to "Carro Patrulha", "Missão" to "Segurança"), "A polícia ajuda a proteger as nossas famílias!", "PO • LÍ • CI • A", "PROTEÇÃO", 0xFF90CAF9),
            EducationalItem("prof_veterinaria", "Veterinária", "Saúde e Cuidado", "👩‍⚕️🐾", "Trata dos animais", "As veterinárias são doutoras especiais que curam cães, gatos e outros animais.", mapOf("Local" to "Clínica Veterinária", "Doentes" to "Animais"), "A veterinária cuida dos nossos amigos de quatro patas!", "VE • TE • RI • NÁ • RIA", "ANIMAIS", 0xFFA5D6A7),
            EducationalItem("prof_cientista", "Cientista", "Educação e Ciência", "👩‍🔬", "Faz descobertas", "Os cientistas usam microscópios e fazem experiências para descobrir coisas novas.", mapOf("Local" to "Laboratório", "Ferramenta" to "Microscópio"), "Um cientista adora inventar coisas novas!", "CI • EN • TIS • TA", "CIÊNCIA", 0xFFCE93D8),
            EducationalItem("prof_engenheiro", "Engenheiro", "Construção e Natureza", "👷", "Constrói coisas seguras", "Os engenheiros planeiam pontes, estradas e máquinas muito fortes e seguras.", mapOf("Local" to "Obra", "Ferramenta" to "Plano e Capacete"), "Um engenheiro constrói o nosso futuro!", "EN • GE • NHEI • RO", "FUTURO", 0xFFFFCC80),
            EducationalItem("prof_arquiteta", "Arquiteta", "Construção e Natureza", "📐", "Desenha casas e prédios", "As arquitetas desenham no papel como vão ser as casas onde vamos viver.", mapOf("Local" to "Escritório", "Ferramenta" to "Régua e Lápis"), "Uma arquiteta faz desenhos fantásticos de prédios!", "AR • QUI • TE • TA", "CASAS", 0xFFFFF59D),
            EducationalItem("prof_astronauta", "Astronauta", "Educação e Ciência", "👨‍🚀", "Viaja pelo espaço", "Os astronautas voam em foguetões para explorar as estrelas e a Lua.", mapOf("Veículo" to "Foguetão", "Local" to "Espaço"), "O astronauta flutua no meio das estrelas!", "AS • TRO • NAU • TA", "ESPAÇO", 0xFFCFD8DC),
            EducationalItem("prof_chef", "Chef", "Artes e Comida", "🧑‍🍳", "Faz comida deliciosa", "O Chef de cozinha mistura ingredientes para fazer pratos muito saborosos.", mapOf("Local" to "Restaurante", "Ferramenta" to "Panelas"), "O chef faz comidas que nos deixam de barriga cheia!", "CHEF", "COMIDA", 0xFFFFAB91),
            EducationalItem("prof_padeira", "Padeira", "Artes e Comida", "🥖", "Faz pão e bolos", "A padeira acorda muito cedo para amassar a farinha e fazer o pão quentinho.", mapOf("Local" to "Padaria", "Produto" to "Pão e Croissants"), "A padeira faz o nosso pão quentinho e saboroso!", "PA • DEI • RA", "PÃO", 0xFFFFE082),
            EducationalItem("prof_agricultor", "Agricultor", "Construção e Natureza", "🧑‍🌾", "Planta e colhe", "O agricultor cuida da terra e das plantas para termos frutas e legumes na mesa.", mapOf("Local" to "Quinta", "Veículo" to "Trator"), "O agricultor traz comida da terra para a nossa mesa!", "A • GRI • CUL • TOR", "NATUREZA", 0xFFAED581),
            EducationalItem("prof_artista", "Artista", "Artes e Comida", "👩‍🎨", "Pinta quadros bonitos", "A artista usa tintas coloridas e pincéis para criar telas maravilhosas.", mapOf("Local" to "Atelier", "Ferramenta" to "Pincel e Paleta"), "Uma artista enche o mundo de cores bonitas!", "AR • TIS • TA", "CORES", 0xFFF48FB1),
            EducationalItem("prof_musico", "Músico", "Artes e Comida", "🎸", "Toca instrumentos", "O músico toca viola, piano e bateria para criar canções divertidas.", mapOf("Local" to "Palco", "Ferramenta" to "Instrumentos"), "O músico põe toda a gente a dançar!", "MÚ • SI • CO", "MÚSICA", 0xFFB39DDB)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_pr1", "Quem usa uma mangueira e apaga fogos?", listOf("Bombeiro", "Professor", "Pintor"), "Bombeiro", "O bombeiro apaga o fogo e salva pessoas!", "🚒"),
            EducationalQuizQuestion("q_pr2", "Quem viaja de foguetão para o espaço?", listOf("Astronauta", "Agricultor", "Chef"), "Astronauta", "O astronauta viaja pelas estrelas no espaço!", "👨‍🚀")
        )
    )

    // ==========================================
    // 12. EMOÇÕES DO ZÉ TRAQUINA
    // ==========================================
    private val emocoesTopic = EducationalTopicData(
        id = "emocoes",
        title = "Emoções",
        subtitle = "Compreende a alegria, a tristeza e como expressar o que sentes! ❤️",
        iconEmoji = "😊",
        accentColor = Color(0xFFFF8F00),
        headerGradient = listOf(Color(0xFFE65100), Color(0xFFFFB300)),
        searchPlaceholder = "Procurar emoção...",
        customTabName = "Emoções",
        categories = listOf(
            EducationalCategoryFilter("Todas", "😊", Color(0xFFFF8F00)),
            EducationalCategoryFilter("Alegres", "✨", Color(0xFFFBC02D)),
            EducationalCategoryFilter("Calmas", "🌊", Color(0xFF0288D1)),
            EducationalCategoryFilter("Cuidadosas", "❤️", Color(0xFFD32F2F))
        ),
        items = listOf(
            EducationalItem("em_alegria", "Alegria", "Alegres", "😄", "Sorriso de Orelha a Orelha", "Quando brincamos e estamos felizes, sentimos o coração quentinho!", mapOf("Expressão" to "Sorriso Largo", "Ação" to "Cantar e rir"), "A alegria faz os olhos brilharem!", "A • LE • GRI • A", "FELIZ", 0xFFFFE082),
            EducationalItem("em_tristeza", "Tristeza", "Calmas", "😢", "Lágrimas que caem", "É normal chorar quando estamos tristes. As lágrimas lavam o coração.", mapOf("Expressão" to "Lábio caído", "Ação" to "Abraçar forte"), "A tristeza passa com um abraço apertado!", "TRIS • TE • ZA", "CHORO", 0xFF90CAF9),
            EducationalItem("em_raiva", "Raiva", "Cuidadosas", "😡", "Vulcão a explodir", "Quando estamos zangados ficamos quentes por dentro, como um vulcão!", mapOf("Expressão" to "Sobrolho franzido", "Ação" to "Respirar fundo"), "A raiva vai embora se contares até dez!", "RAI • VA", "FOGO", 0xFFEF9A9A),
            EducationalItem("em_medo", "Medo", "Cuidadosas", "😨", "Coração a bater", "O medo protege-nos do perigo, mas podemos ser corajosos!", mapOf("Expressão" to "Olhos arregalados", "Ação" to "Dar a mão"), "O medo foge quando acendemos a luz!", "ME • DO", "SUSTO", 0xFFB39DDB),
            EducationalItem("em_surpresa", "Surpresa", "Alegres", "😲", "Boca aberta de espanto", "Quando acontece algo que não esperávamos, como um presente surpresa!", mapOf("Expressão" to "Boca em 'O'", "Ação" to "Saltar de espanto"), "A surpresa é como abrir uma prenda!", "SUR • PRE • SA", "UAU", 0xFFFFCC80),
            EducationalItem("em_nojo", "Nojo", "Cuidadosas", "🤢", "Língua de fora", "Sentimos nojo quando a comida sabe mal ou algo cheira estranho.", mapOf("Expressão" to "Nariz torcido", "Ação" to "Afastar-se"), "O nojo ajuda-nos a não comer coisas estragadas!", "NO • JO", "BLEH", 0xFFA5D6A7),
            EducationalItem("em_calma", "Calma e Paz", "Calmas", "😌", "Respiração Serena", "Respirar fundo como se cheirássemos uma flor traz muita calma.", mapOf("Expressão" to "Rosto sereno", "Dica" to "Inspirar e expirar"), "A calma ajuda a pensar melhor e a descansar!", "CAL • MA", "SERENO", 0xFF80CBC4),
            EducationalItem("em_vergonha", "Vergonha", "Cuidadosas", "😳", "Bochechas vermelhas", "Ficamos com vergonha quando fazemos algo desastrado à frente dos outros.", mapOf("Expressão" to "Cara vermelha", "Ação" to "Esconder o rosto"), "A vergonha passa quando nos rimos de nós próprios!", "VER • GO • NHA", "TÍMIDO", 0xFFF48FB1),
            EducationalItem("em_orgulho", "Orgulho", "Alegres", "😤", "Peito cheio de ar", "Sentimos orgulho quando conseguimos fazer algo muito difícil sozinhos!", mapOf("Expressão" to "Sorriso de lado", "Ação" to "Mostrar a todos"), "O orgulho é um abraço a nós próprios!", "OR • GU • LHO", "FORTE", 0xFFFFAB91),
            EducationalItem("em_confusao", "Confusão", "Calmas", "🤔", "Cabeça a pensar", "Quando não percebemos algo, coçamos a cabeça para tentar entender.", mapOf("Expressão" to "Testa franzida", "Ação" to "Fazer perguntas"), "A confusão resolve-se quando fazemos perguntas!", "CON • FU • SÃO", "DÚVIDA", 0xFFE6EE9C),
            EducationalItem("em_ansiedade", "Ansiedade", "Cuidadosas", "😬", "Borboletas na barriga", "Sentimos nervosismo antes de um teste ou algo muito importante.", mapOf("Expressão" to "Morder os lábios", "Ação" to "Pedir colo"), "A ansiedade acalma com palavras amigas!", "AN • SIE • DA • DE", "NERVOS", 0xFFCE93D8),
            EducationalItem("em_amor", "Amor", "Calmas", "🥰", "Abraço Quentinho", "O amor é cuidar dos amigos, da família e dos animais de estimação.", mapOf("Gesto" to "Dar a mão", "Sentimento" to "Carinho profundo"), "O amor torna o mundo mais bonito!", "A • MOR", "AFETO", 0xFFF8BBD0),
            EducationalItem("em_curiosidade", "Curiosidade", "Alegres", "🧐", "Lupa na mão", "Queremos saber tudo e descobrir como as coisas funcionam!", mapOf("Expressão" to "Olhar atento", "Ação" to "Explorar e mexer"), "A curiosidade faz de nós pequenos cientistas!", "CU • RIO • SI • DA • DE", "SABER", 0xFF81D4FA),
            EducationalItem("em_cansaco", "Cansaço", "Calmas", "🥱", "Olhos pequeninos", "Quando brincamos muito, o nosso corpo pede para dormir e descansar.", mapOf("Expressão" to "Bocejar", "Ação" to "Ir para a cama"), "O cansaço passa com uma boa noite de sono!", "CAN • SA • ÇO", "SONO", 0xFFCFD8DC),
            EducationalItem("em_frustracao", "Frustração", "Cuidadosas", "😫", "Vontade de desistir", "Quando algo corre mal e não conseguimos fazer o que queríamos.", mapOf("Expressão" to "Bufar", "Ação" to "Tentar de novo"), "Com paciência conseguimos vencer a frustração!", "FRUS • TRA • ÇÃO", "DIFÍCIL", 0xFFFFCCBC)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_em1", "O que podemos fazer para nos acalmarmos quando estamos nervosos?", listOf("Respirar fundo devagar", "Gritar muito", "Correr em círculos"), "Respirar fundo devagar", "Respirar fundo oxigena o cérebro e traz serenidade!", "😌")
        )
    )

    // ==========================================
    // 13. DIAS DA SEMANA DO ZÉ TRAQUINA
    // ==========================================
    private val diasSemanaTopic = EducationalTopicData(
        id = "dias_semana",
        title = "Dias da Semana",
        subtitle = "Aprende a ordem dos 7 dias e organiza a tua semana! 📅",
        iconEmoji = "📅",
        accentColor = Color(0xFF6D4C41),
        headerGradient = listOf(Color(0xFF3E2723), Color(0xFF6D4C41)),
        searchPlaceholder = "Procurar dia...",
        customTabName = "Dias",
        categories = listOf(
            EducationalCategoryFilter("Todos", "📅", Color(0xFF6D4C41)),
            EducationalCategoryFilter("Dias de Escola", "🎒", Color(0xFF1976D2)),
            EducationalCategoryFilter("Fim de Semana", "🎈", Color(0xFF388E3C))
        ),
        items = listOf(
            EducationalItem("dia_seg", "Segunda-feira", "Dias de Escola", "📚", "Começo de Semana", "Dia de reencontrar os amigos na escola e aprender coisas novas.", mapOf("Ordem" to "1º dia de escola", "Atividade" to "Aprender e Brincar"), "Segunda-feira é o começo de uma semana cheia de aventuras!", "SE • GUN • DA", "ESCOLA", 0xFF80DEEA),
            EducationalItem("dia_ter", "Terça-feira", "Dias de Escola", "🎨", "Dia de Criatividade", "Dia de desenhar, fazer jogos e praticar desporto.", mapOf("Ordem" to "2º dia de escola", "Atividade" to "Desenho e Expressão"), "Terça-feira cheia de cores e energia!", "TER • ÇA", "ARTE", 0xFFFFD54F),
            EducationalItem("dia_qua", "Quarta-feira", "Dias de Escola", "🧩", "Meio da Semana", "Estamos a meio da semana e já aprendemos imensas coisas.", mapOf("Ordem" to "3º dia de escola", "Atividade" to "Jogos e Desafios"), "Quarta-feira, o meio da nossa semana mágica!", "QUAR • TA", "JOGOS", 0xFFA5D6A7),
            EducationalItem("dia_qui", "Quinta-feira", "Dias de Escola", "📖", "Histórias e Leitura", "Dia de ouvir contos encantados e descobrir novas palavras.", mapOf("Ordem" to "4º dia de escola", "Atividade" to "Leitura e Imaginação"), "Quinta-feira é dia de histórias fantásticas!", "QUIN • TA", "LEITURA", 0xFFFFCC80),
            EducationalItem("dia_sex", "Sexta-feira", "Dias de Escola", "🎉", "Festa da Semana", "Último dia de aulas da semana, quase a chegar o fim de semana!", mapOf("Ordem" to "5º dia de escola", "Atividade" to "Música e Alegria"), "Sexta-feira, viva! Amanhã é fim de semana!", "SEX • TA", "FESTA", 0xFFF48FB1),
            EducationalItem("dia_sab", "Sábado", "Fim de Semana", "⛺", "Passeios e Família", "Dia de passear no parque, andar de bicicleta e estar em família.", mapOf("Ordem" to "Fim de semana", "Atividade" to "Ar Livre e Família"), "Sábado é dia de passear e brincar livremente!", "SÁ • BA • DO", "PASSEIO", 0xFFFFF59D),
            EducationalItem("dia_dom", "Domingo", "Fim de Semana", "🥞", "Descanso e Carinho", "Dia de almoço em família, histórias de aconchego e preparar a nova semana.", mapOf("Ordem" to "Fim de semana", "Atividade" to "Descanso e Família"), "Domingo traz descanso e muito carinho em casa!", "DO • MIN • GO", "DESCANSO", 0xFFFFAB91)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_dia1", "Quantos dias tem uma semana completa?", listOf("7 Dias", "5 Dias", "10 Dias"), "7 Dias", "Uma semana tem exatamente 7 dias!", "📅"),
            EducationalQuizQuestion("q_dia2", "Quais são os dois dias do Fim de Semana?", listOf("Sábado e Domingo", "Segunda e Terça", "Quarta e Quinta"), "Sábado e Domingo", "Sábado e Domingo são os dias de descanso!", "🎈")
        )
    )

    // ==========================================
    // 14. MESES DO ANO DO ZÉ TRAQUINA
    // ==========================================
    private val mesesAnoTopic = EducationalTopicData(
        id = "meses_ano",
        title = "Meses do Ano",
        subtitle = "Viaja pelas 4 estações e descobre os 12 meses do ano! 🗓️",
        iconEmoji = "🗓️",
        accentColor = Color(0xFFD81B60),
        headerGradient = listOf(Color(0xFF880E4F), Color(0xFFC2185B)),
        searchPlaceholder = "Procurar mês...",
        customTabName = "Meses",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🗓️", Color(0xFFD81B60)),
            EducationalCategoryFilter("Primavera", "🌸", Color(0xFF4CAF50)),
            EducationalCategoryFilter("Verão", "☀️", Color(0xFFF57F17)),
            EducationalCategoryFilter("Outono", "🍂", Color(0xFFE64A19)),
            EducationalCategoryFilter("Inverno", "❄️", Color(0xFF0288D1))
        ),
        items = listOf(
            EducationalItem("mes_jan", "Janeiro", "Inverno", "❄️", "Mês 1 (Ano Novo)", "Janeiro traz o Ano Novo e o tempo frio. Em alguns lugares até cai neve!", mapOf("Estação" to "Inverno", "Ordem" to "1º Mês"), "Janeiro é o primeiro mês do ano!", "JA • NEI • RO", "ANO NOVO", 0xFF80DEEA),
            EducationalItem("mes_fev", "Fevereiro", "Inverno", "🎭", "Mês 2 (Carnaval)", "Em Fevereiro vestimos fatos divertidos e brincamos ao Carnaval!", mapOf("Estação" to "Inverno", "Ordem" to "2º Mês"), "Fevereiro é o mês do Carnaval!", "FE • VE • REI • RO", "CARNAVAL", 0xFFCE93D8),
            EducationalItem("mes_mar", "Março", "Primavera", "🌱", "Mês 3 (Primavera)", "Em Março a Primavera chega e os passarinhos começam a cantar.", mapOf("Estação" to "Primavera", "Ordem" to "3º Mês"), "Março traz os primeiros dias quentinhos!", "MAR • ÇO", "NATUREZA", 0xFFA5D6A7),
            EducationalItem("mes_abr", "Abril", "Primavera", "🌸", "Mês 4 (Flores)", "Em Abril as flores desabrocham e tudo fica cheio de cores.", mapOf("Estação" to "Primavera", "Ordem" to "4º Mês"), "Abril tem muitas flores e andorinhas!", "A • BRIL", "FLORES", 0xFF81C784),
            EducationalItem("mes_mai", "Maio", "Primavera", "🌷", "Mês 5 (Mãe)", "Em Maio celebramos o Dia da Mãe e a natureza está linda.", mapOf("Estação" to "Primavera", "Ordem" to "5º Mês"), "Maio é o mês das flores bonitas!", "MAI • O", "AMOR", 0xFFF48FB1),
            EducationalItem("mes_jun", "Junho", "Verão", "🎈", "Mês 6 (Santos Populares)", "Em Junho começam as férias grandes e cheira a manjerico!", mapOf("Estação" to "Verão", "Ordem" to "6º Mês"), "Junho é o mês das festas e balões!", "JU • NHO", "FESTAS", 0xFFFFD54F),
            EducationalItem("mes_jul", "Julho", "Verão", "🏖️", "Mês 7 (Praia)", "Julho é o mês das idas à praia e dos castelos na areia.", mapOf("Estação" to "Verão", "Ordem" to "7º Mês"), "Julho tem muito sol e brincadeiras na água!", "JU • LHO", "PRAIA", 0xFFFFB300),
            EducationalItem("mes_ago", "Agosto", "Verão", "🏕️", "Mês 8 (Férias)", "Agosto é o mês das férias em família, viagens e muito calor.", mapOf("Estação" to "Verão", "Ordem" to "8º Mês"), "Agosto é mês de descanso e gelados!", "A • GOS • TO", "CALOR", 0xFFFF9800),
            EducationalItem("mes_set", "Setembro", "Outono", "🎒", "Mês 9 (Escola)", "Em Setembro regressamos à escola para aprender coisas novas.", mapOf("Estação" to "Outono", "Ordem" to "9º Mês"), "Setembro traz a mochila e os livros!", "SE • TEM • BRO", "ESCOLA", 0xFF90CAF9),
            EducationalItem("mes_out", "Outubro", "Outono", "🍂", "Mês 10 (Folhas)", "Em Outubro as folhas caem das árvores pintadas de amarelo.", mapOf("Estação" to "Outono", "Ordem" to "10º Mês"), "Outubro tem tapetes de folhas secas!", "OU • TU • BRO", "FOLHAS", 0xFFFFCC80),
            EducationalItem("mes_nov", "Novembro", "Outono", "🌰", "Mês 11 (Castanhas)", "Em Novembro o tempo arrefece e comemos castanhas assadas.", mapOf("Estação" to "Outono", "Ordem" to "11º Mês"), "Novembro cheira a castanhas quentinhas!", "NO • VEM • BRO", "OUTONO", 0xFFBCAAA4),
            EducationalItem("mes_dez", "Dezembro", "Inverno", "🎄", "Mês 12 (Natal)", "Dezembro traz as luzes, a família e a magia do Natal.", mapOf("Estação" to "Inverno", "Ordem" to "12º Mês"), "Dezembro é cheio de alegria e presentes!", "DE • ZEM • BRO", "NATAL", 0xFFEF9A9A)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_mes1", "Quantos meses tem um ano inteiro?", listOf("12 Meses", "6 Meses", "24 Meses"), "12 Meses", "Um ano é composto por 12 meses!", "🗓️"),
            EducationalQuizQuestion("q_mes2", "Em que mês se celebra o Natal?", listOf("Dezembro", "Agosto", "Fevereiro"), "Dezembro", "O Natal celebra-se em Dezembro!", "🎄")
        )
    )

    // ==========================================
    // 15. TRANSPORTES DO ZÉ TRAQUINA
    // ==========================================
    private val transportesTopic = EducationalTopicData(
        id = "transportes",
        title = "Transportes",
        subtitle = "Viaja por terra, mar e ar com todos os tipos de veículos! 🚗",
        iconEmoji = "🚗",
        accentColor = Color(0xFFFBC02D),
        headerGradient = listOf(Color(0xFFF57F17), Color(0xFFFBC02D)),
        searchPlaceholder = "Procurar transporte...",
        customTabName = "Veículos",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🚗", Color(0xFFFBC02D)),
            EducationalCategoryFilter("Terra", "🚙", Color(0xFF388E3C)),
            EducationalCategoryFilter("Ar", "✈️", Color(0xFF0288D1)),
            EducationalCategoryFilter("Mar", "🚢", Color(0xFF0097A7))
        ),
        items = listOf(
            EducationalItem("tr_carro", "Carro", "Terra", "🚗", "Rodas na Estrada", "O carro anda pelas ruas e leva-nos a passear.", mapOf("Meio" to "Terra", "Ordem" to "1"), "O carro tem quatro rodas e anda na estrada!", "CAR • RO", "TERRA", 0xFFA5D6A7),
            EducationalItem("tr_bicicleta", "Bicicleta", "Terra", "🚲", "Pedalar com Saúde", "A bicicleta tem duas rodas e precisa das tuas pernas para andar.", mapOf("Meio" to "Terra", "Ordem" to "2"), "A bicicleta é ótima para a saúde!", "BI • CI • CLE • TA", "TERRA", 0xFFFFCC80),
            EducationalItem("tr_autocarro", "Autocarro", "Terra", "🚌", "Muitos Passageiros", "O autocarro é grande e pode levar muitas pessoas ao mesmo tempo.", mapOf("Meio" to "Terra", "Ordem" to "3"), "O autocarro leva as crianças para a escola!", "AU • TO • CAR • RO", "TERRA", 0xFFFFF59D),
            EducationalItem("tr_comboio", "Comboio", "Terra", "🚂", "Sobre os Carris", "O comboio faz piúí e viaja em cima de longos carris.", mapOf("Meio" to "Terra", "Ordem" to "4"), "O comboio é muito comprido e veloz!", "COM • BOI • O", "TERRA", 0xFFFFD54F),
            EducationalItem("tr_metro", "Metro", "Terra", "🚇", "Debaixo da Cidade", "O metro é um comboio que viaja rápido através de túneis subterrâneos.", mapOf("Meio" to "Terra", "Ordem" to "5"), "O metro passa por debaixo da terra!", "ME • TRO", "TERRA", 0xFFCE93D8),
            EducationalItem("tr_barco", "Barco", "Água", "⛵", "Navegar no Mar", "O barco flutua na água e usa os ventos ou motores para navegar.", mapOf("Meio" to "Água", "Ordem" to "6"), "O barco desliza suavemente nas ondas azuis!", "BAR • CO", "ÁGUA", 0xFF80CBC4),
            EducationalItem("tr_aviao", "Avião", "Ar", "✈️", "Asas nas Nuvens", "O avião voa alto no céu, no meio das nuvens, como um pássaro.", mapOf("Meio" to "Ar", "Ordem" to "7"), "O avião voa rápido de um país para o outro!", "A • VI • ÃO", "AR", 0xFF80DEEA),
            EducationalItem("tr_helicoptero", "Helicóptero", "Ar", "🚁", "Hélices a Rodar", "O helicóptero tem pás gigantes que rodam para o fazer subir.", mapOf("Meio" to "Ar", "Ordem" to "8"), "O helicóptero sobe e desce a direito!", "HE • LI • CÓP • TE • RO", "AR", 0xFF90CAF9),
            EducationalItem("tr_balao", "Balão de Ar Quente", "Ar", "🎈", "Cesto Voador", "O balão usa ar quente para flutuar suavemente pelo céu.", mapOf("Meio" to "Ar", "Ordem" to "9"), "O balão de ar quente flutua nas brisas suaves!", "BA • LÃO", "AR", 0xFFF48FB1),
            EducationalItem("tr_mota", "Motocicleta", "Terra", "🏍️", "Apenas Duas Rodas", "A mota tem um motor forte e transporta duas pessoas muito rápido.", mapOf("Meio" to "Terra", "Ordem" to "10"), "A mota é rápida e leva capacetes!", "MO • TO • CI • CLE • TA", "TERRA", 0xFFFFAB91),
            EducationalItem("tr_trator", "Trator", "Terra", "🚜", "Trabalho no Campo", "O trator tem rodas gigantes e ajuda o agricultor na quinta.", mapOf("Meio" to "Terra", "Ordem" to "11"), "O trator trabalha nos campos agrícolas!", "TRA • TOR", "TERRA", 0xFF81C784),
            EducationalItem("tr_foguete", "Foguete", "Espaço", "🚀", "Rumo às Estrelas", "O foguete descola da Terra e viaja pelo espaço para outros planetas.", mapOf("Meio" to "Espaço", "Ordem" to "12"), "O foguete vai muito alto, até às estrelas!", "FO • GUE • TE", "ESPAÇO", 0xFFBCAAA4)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_tr1", "Qual destes transportes voa no céu?", listOf("Avião", "Carro", "Barco"), "Avião", "O avião viaja pelos céus!", "✈️")
        )
    )

    // ==========================================
    // 16. INSTRUMENTOS DO ZÉ TRAQUINA
    // ==========================================
    private val instrumentosTopic = EducationalTopicData(
        id = "instrumentos",
        title = "Instrumentos",
        subtitle = "Explora os sons, cordas e sopros da orquestra musical! 🎸",
        iconEmoji = "🎸",
        accentColor = Color(0xFF546E7A),
        headerGradient = listOf(Color(0xFF263238), Color(0xFF546E7A)),
        searchPlaceholder = "Procurar instrumento...",
        customTabName = "Música",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🎸", Color(0xFF546E7A)),
            EducationalCategoryFilter("Cordas", "🎻", Color(0xFFE65100)),
            EducationalCategoryFilter("Teclas", "🎹", Color(0xFF1976D2)),
            EducationalCategoryFilter("Percussão", "🥁", Color(0xFFD32F2F)),
            EducationalCategoryFilter("Sopros", "🎺", Color(0xFFFBC02D))
        ),
        items = listOf(
            EducationalItem("inst_guitarra", "Guitarra", "Cordas", "🎸", "Seis Cordas", "A guitarra tem seis cordas que fazemos vibrar com os dedos.", mapOf("Família" to "Cordas", "Ordem" to "1"), "A guitarra toca músicas muito animadas!", "GUI • TAR • RA", "CORDAS", 0xFFFFCC80),
            EducationalItem("inst_violino", "Violino", "Cordas", "🎻", "Arco Mágico", "O violino toca-se passando um arco por cima das suas cordas.", mapOf("Família" to "Cordas", "Ordem" to "2"), "O violino tem um som muito doce e afinado!", "VI • O • LI • NO", "CORDAS", 0xFFFFAB91),
            EducationalItem("inst_flauta", "Flauta", "Sopro", "🪈", "Sopro Suave", "A flauta é um tubo com furinhos onde sopramos para fazer música.", mapOf("Família" to "Sopro", "Ordem" to "3"), "A flauta faz um som que parece um passarinho!", "FLAU • TA", "SOPRO", 0xFFA5D6A7),
            EducationalItem("inst_saxofone", "Saxofone", "Sopro", "🎷", "Sopro Dourado", "O saxofone é feito de metal brilhante e tem um som muito fixe.", mapOf("Família" to "Sopro", "Ordem" to "4"), "O saxofone é o rei do jazz!", "SA • XO • FO • NE", "SOPRO", 0xFFFFD54F),
            EducationalItem("inst_trompete", "Trompete", "Sopro", "🎺", "Som Forte", "O trompete tem três botões e faz um som muito forte e alegre.", mapOf("Família" to "Sopro", "Ordem" to "5"), "O trompete anuncia o início da festa!", "TROM • PE • TE", "SOPRO", 0xFFFFB300),
            EducationalItem("inst_piano", "Piano", "Teclas", "🎹", "Teclas Brancas e Pretas", "O piano tem muitas teclas que parecem dentes pretos e brancos.", mapOf("Família" to "Cordas Percurtidas", "Ordem" to "6"), "O piano parece uma orquestra inteira!", "PI • A • NO", "TECLAS", 0xFFCFD8DC),
            EducationalItem("inst_bateria", "Bateria", "Percussão", "🥁", "Ritmo Forte", "A bateria tem tambores e pratos que batemos com baquetas.", mapOf("Família" to "Percussão", "Ordem" to "7"), "A bateria marca o ritmo da canção!", "BA • TE • RI • A", "PERCUSSÃO", 0xFFEF9A9A),
            EducationalItem("inst_pandeireta", "Pandeireta", "Percussão", "🪇", "Som a Abanar", "A pandeireta tem rodelinhas de metal que fazem barulho quando a abanamos.", mapOf("Família" to "Percussão", "Ordem" to "8"), "A pandeireta é perfeita para dançar!", "PAN • DEI • RE • TA", "PERCUSSÃO", 0xFFCE93D8),
            EducationalItem("inst_triangulo", "Triângulo", "Percussão", "📐", "Forma Geométrica", "O triângulo é de metal e faz 'plim' quando lhe batemos com uma varinha.", mapOf("Família" to "Percussão", "Ordem" to "9"), "O triângulo tem o som de uma estrelinha!", "TRI • ÂN • GU • LO", "PERCUSSÃO", 0xFFFFF59D),
            EducationalItem("inst_acordeao", "Acordeão", "Teclas/Sopro", "🪗", "Fole a Abrir e Fechar", "O acordeão tem um fole no meio que respira para fazer som.", mapOf("Família" to "Sopro e Teclas", "Ordem" to "10"), "O acordeão põe toda a gente a dançar no bailarico!", "A • COR • DE • ÃO", "FOLE", 0xFF90CAF9),
            EducationalItem("inst_harpa", "Harpa", "Cordas", "🎶", "Muitas Cordas", "A harpa é gigante e tem imensas cordas que os dedos dedilham.", mapOf("Família" to "Cordas", "Ordem" to "11"), "A harpa tem um som que parece de fadas!", "HAR • PA", "CORDAS", 0xFFF48FB1),
            EducationalItem("inst_clarinete", "Clarinete", "Sopro", "🪄", "Tubo Preto", "O clarinete é comprido e preto, com um som muito suave e misterioso.", mapOf("Família" to "Sopro", "Ordem" to "12"), "O clarinete canta melodias encantadas!", "CLA • RI • NE • TE", "SOPRO", 0xFFBCAAA4)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_ins1", "Que instrumento se toca batendo com baquetas para marcar o ritmo?", listOf("Tambor", "Flauta", "Guitarra"), "Tambor", "O tambor marca o ritmo da música!", "🥁")
        )
    )

    // ==========================================
    // 17. HISTÓRIAS DO ZÉ TRAQUINA
    // ==========================================
    private val historiasTopic = EducationalTopicData(
        id = "historias",
        title = "Histórias",
        subtitle = "Contos mágicos, fábulas e aventuras com lições de vida 📖",
        iconEmoji = "📖",
        accentColor = Color(0xFF673AB7),
        headerGradient = listOf(Color(0xFF311B92), Color(0xFF673AB7)),
        searchPlaceholder = "Procurar história...",
        customTabName = "Contos",
        categories = listOf(
            EducationalCategoryFilter("Todas", "📖", Color(0xFF673AB7)),
            EducationalCategoryFilter("Aventura", "🚀", Color(0xFF1976D2)),
            EducationalCategoryFilter("Amizade", "❤️", Color(0xFFD81B60)),
            EducationalCategoryFilter("Natureza", "🌲", Color(0xFF2E7D32))
        ),
        items = listOf(
            EducationalItem("hist_1", "O Zé e a Estrela Perdida", "Aventura", "⭐", "Uma Viagem Espacial", "O Zé Traquina constrói um foguetão de papelão e ajuda uma estrelinha a voltar para junto da Lua.", mapOf("Lição" to "Generosidade e Amizade", "Personagens" to "Zé e Estrela Cintilante"), "Uma história mágica sobre ajudar quem precisa!", "ES • TRE • LA", "CONTO", 0xFFFFE082),
            EducationalItem("hist_2", "O Segredo da Floresta Encantada", "Natureza", "🌲", "Amigos dos Animais", "Na floresta mágica, o Zé descobre que quando partilha o seu lanche, as árvores florescem.", mapOf("Lição" to "Cuidar da Natureza", "Personagens" to "Zé, Esquilo e Coruja"), "Uma história sobre o respeito pelas árvores e animais!", "FLO • RES • TA", "NATUREZA", 0xFFA5D6A7),
            EducationalItem("hist_3", "A Tartaruga que Queria Voar", "Amizade", "🐢", "Sonhos que se Realizam", "Com a ajuda das andorinhas e de uma folha de plátano, a tartaruga viu as nuvens do alto!", mapOf("Lição" to "Trabalho em Equipa", "Personagens" to "Tartaruga e Andorinhas"), "Quando nos unimos, todos os sonhos são possíveis!", "TAR • TA • RU • GA", "AMIZADE", 0xFF80DEEA)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_his1", "Qual é a lição mais importante da história da Floresta Encantada?", listOf("Cuidar e proteger a natureza", "Deixar lixo no chão", "Correr sem olhar"), "Cuidar e proteger a natureza", "Proteger a natureza mantém a nossa floresta viva!", "🌲")
        )
    )

    // ==========================================
    // 18. PALAVRAS NOVAS DO ZÉ TRAQUINA
    // ==========================================
    private val palavrasNovasTopic = EducationalTopicData(
        id = "palavras_novas",
        title = "Palavras Novas",
        subtitle = "Enriquece o teu vocabulário com palavras bonitas e sábias 🗣️",
        iconEmoji = "🗣️",
        accentColor = Color(0xFF00897B),
        headerGradient = listOf(Color(0xFF004D40), Color(0xFF00897B)),
        searchPlaceholder = "Procurar palavra...",
        enableSyllables = true,
        customTabName = "Vocabulário",
        categories = listOf(
            EducationalCategoryFilter("Todas", "🗣️", Color(0xFF00897B)),
            EducationalCategoryFilter("Sentimentos", "❤️", Color(0xFFD81B60)),
            EducationalCategoryFilter("Ciência", "🔬", Color(0xFF1976D2)),
            EducationalCategoryFilter("Natureza", "🌿", Color(0xFF388E3C))
        ),
        items = listOf(
            EducationalItem("pal_empatia", "Empatia", "Sentimentos", "🤝", "Colocar-se no Lugar do Outro", "Empatia é a capacidade mágica de compreender e sentir o que o outro amigo está a passar.", mapOf("Significado" to "Compreensão e Afeto", "Exemplo" to "Dar um abraço quando alguém está triste"), "Empatia é colocar o nosso coração no lugar do amigo!", "EM • PA • TI • A", "VALOR", 0xFFFFCC80),
            EducationalItem("pal_gratidao", "Gratidão", "Sentimentos", "🙏", "Dizer Obrigado com o Coração", "Gratidão é reconhecer e agradecer as coisas boas e as pessoas que nos ajudam todos os dias.", mapOf("Significado" to "Agradecimento Sincero", "Exemplo" to "Agradecer o carinho dos pais"), "Gratidão enche a nossa vida de luz e alegria!", "GRA • TI • DÃO", "VALOR", 0xFFFFD54F),
            EducationalItem("pal_biodiv", "Biodiversidade", "Natureza", "🦋", "Variedade de Vida", "É a grande riqueza de todos os animais, plantas e ecossistemas do nosso planeta Terra.", mapOf("Significado" to "Variedade de seres vivos", "Importância" to "Equilíbrio da Terra"), "Biodiversidade é a beleza de todas as espécies vivas!", "BIO • DI • VER • SI • DA • DE", "CIÊNCIA", 0xFFA5D6A7),
            EducationalItem("pal_constelacao", "Constelação", "Ciência", "✨", "Desenhos no Céu Estrelado", "Grupo de estrelas no céu noturno que formam desenhos imaginários de heróis e animais.", mapOf("Significado" to "Conjunto de estrelas", "Exemplo" to "Ursa Maior e Orion"), "As constelações são mapas de luz desenhados nas estrelas!", "CONS • TE • LA • ÇÃO", "ASTRONOMIA", 0xFF80DEEA)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_pal1", "O que significa ter Empatia?", listOf("Compreender e ajudar o amigo", "Comer sozinho", "Fugir das brincadeiras"), "Compreender e ajudar o amigo", "Empatia é saber escutar e apoiar com carinho!", "🤝")
        )
    )

    // ==========================================
    // 19. DICIONÁRIO DO ZÉ TRAQUINA
    // ==========================================
    private val dicionarioTopic = EducationalTopicData(
        id = "dicionario",
        title = "Dicionário",
        subtitle = "Aprende palavras incríveis de A a Z e o seu significado! 📖",
        iconEmoji = "📖",
        accentColor = Color(0xFF43A047),
        headerGradient = listOf(Color(0xFF1B5E20), Color(0xFF43A047)),
        searchPlaceholder = "Procurar termo no dicionário...",
        enableSyllables = true,
        customTabName = "Dicionário",
        categories = listOf(
            EducationalCategoryFilter("Todos", "📖", Color(0xFF43A047)),
            EducationalCategoryFilter("A a E", "🔤", Color(0xFF1976D2)),
            EducationalCategoryFilter("F a M", "📚", Color(0xFFE65100)),
            EducationalCategoryFilter("N a Z", "✨", Color(0xFF7B1FA2))
        ),
        items = listOf(
            EducationalItem("dic_amizade", "Amizade", "A a E", "👫", "Laço de Carinho", "Relação especial de carinho, lealdade e partilha entre duas pessoas.", mapOf("Letra" to "A", "Sinónimo" to "Companheirismo"), "Amizade é um dos maiores tesouros do mundo!", "A • MI • ZA • DE", "A", 0xFFFFE082),
            EducationalItem("dic_bondade", "Bondade", "A a E", "💚", "Coração Bom", "Qualidade de quem é benévolo, compreensivo e faz o bem aos outros.", mapOf("Letra" to "B", "Virtude" to "Fazer o Bem"), "A bondade ilumina o dia de quem a recebe!", "BON • DA • DE", "B", 0xFFC8E6C9),
            EducationalItem("dic_coragem", "Coragem", "A a E", "🦁", "Força Interior", "Capacidade de enfrentar o medo com bravura e determinação para fazer o que é certo.", mapOf("Letra" to "C", "Valor" to "Bravura"), "A coragem ajuda-nos a superar qualquer desafio!", "CO • RA • GEM", "C", 0xFFFFCC80),
            EducationalItem("dic_curiosidade", "Curiosidade", "A a E", "🔍", "Vontade de Aprender", "O desejo maravilhoso de descobrir coisas novas, fazer perguntas e explorar o mundo.", mapOf("Letra" to "C", "Superpoder" to "Gera cientistas"), "A curiosidade faz-nos descobrir mistérios incríveis!", "CU • RIO • SI • DA • DE", "C", 0xFF80DEEA),
            EducationalItem("dic_empatia", "Empatia", "A a E", "🫂", "Colocar-se no Lugar", "A habilidade de sentir e compreender o que os outros estão a sentir.", mapOf("Letra" to "E", "Sentimento" to "Compreensão"), "A empatia cria pontes de amor entre as pessoas!", "EM • PA • TIA", "E", 0xFFE1BEE7),
            EducationalItem("dic_esperança", "Esperança", "A a E", "⭐", "Confiança no Futuro", "A certeza e expectativa positiva de que coisas boas vão acontecer.", mapOf("Letra" to "E", "Força" to "Optimismo"), "A esperança dá asas aos nossos maiores sonhos!", "ES • PE • RAN • ÇA", "E", 0xFFFFF9C4),
            EducationalItem("dic_generosidade", "Generosidade", "F a M", "🎁", "Partilhar com Alegria", "A virtude de partilhar brinquedos, tempo e sorrisos com quem está connosco.", mapOf("Letra" to "G", "Gesto" to "Partilha"), "Ser generoso torna o nosso coração gigante!", "GE • NE • RO • SI • DA • DE", "G", 0xFFFFAB91),
            EducationalItem("dic_gratidão", "Gratidão", "F a M", "🙏", "Agradecer de Coração", "O sentimento de reconhecimento e agradecimento por tudo o que recebemos e vivemos.", mapOf("Letra" to "G", "Atitude" to "Agradecer"), "A gratidão transforma o que temos em suficiente!", "GRA • TI • DÃO", "G", 0xFFB2DFDB),
            EducationalItem("dic_harmonia", "Harmonia", "F a M", "🎵", "Paz e Equilíbrio", "A convivência pacífica, equilibrada e feliz entre pessoas e o ambiente.", mapOf("Letra" to "H", "Estado" to "Paz"), "A harmonia traz serenidade ao nosso dia a dia!", "HAR • MO • NIA", "H", 0xFFD1C4E9),
            EducationalItem("dic_imaginação", "Imaginação", "F a M", "🎨", "Criar Mundos Novos", "A faculdade de inventar histórias, imagens e ideias fantásticas na mente.", mapOf("Letra" to "I", "Poder" to "Criatividade"), "A imaginação não tem limites no mundo dos sonhos!", "I • MA • GI • NA • ÇÃO", "I", 0xFFFFCDD2),
            EducationalItem("dic_justiça", "Justiça", "F a M", "⚖️", "Equidade", "O princípio de tratar todos com honestidade e respeito pelas regras.", mapOf("Letra" to "J", "Valor" to "Honestidade"), "A justiça garante que todos são tratados com igualdade!", "JUS • TI • ÇA", "J", 0xFFD7CCC8),
            EducationalItem("dic_paciência", "Paciência", "F a M", "🌱", "Saber Esperar", "A calma e persistência para aguardar o momento certo sem perder a serenidade.", mapOf("Letra" to "P", "Virtude" to "Calma"), "A paciência transforma sementes em árvores fortes!", "PA • CIÊN • CIA", "P", 0xFFDCEDC8),
            EducationalItem("dic_respeito", "Respeito", "N a Z", "🤝", "Valorizar os Outros", "Tratar todas as pessoas, animais e a natureza com consideração e civismo.", mapOf("Letra" to "R", "Regra" to "Civismo"), "O respeito abre todas as portas na vida!", "RES • PEI • TO", "R", 0xFFB3E5FC),
            EducationalItem("dic_sinceridade", "Sinceridade", "N a Z", "💎", "Dizer a Verdade", "A qualidade de ser honesto, transparente e verdadeiro nas palavras e ações.", mapOf("Letra" to "S", "Virtude" to "Verdade"), "A sinceridade constrói laços de confiança eternos!", "SIN • CE • RI • DA • DE", "S", 0xFFE1F5FE),
            EducationalItem("dic_solidariedade", "Solidariedade", "N a Z", "🤝", "Ajudar quem Precisa", "O apoio mútuo e a união para ajudar amigos e comunidade em momentos difíceis.", mapOf("Letra" to "S", "Ação" to "Apoio Mútuo"), "Juntos somos mais fortes quando nos ajudamos!", "SO • LI • DA • RIE • DA • DE", "S", 0xFFFFF59D),
            EducationalItem("dic_ternura", "Ternura", "N a Z", "🧸", "Carinho e Doçura", "A expressão de afeto terno, delicado e amoroso para com os outros.", mapOf("Letra" to "T", "Afeto" to "Carinho"), "A ternura aquece os corações mais frios!", "TER • NU • RA", "T", 0xFFFFCCBC),
            EducationalItem("dic_união", "União", "N a Z", "🧩", "Juntos somos Mais Fortes", "A força que surge quando cooperamos e trabalhamos em equipa.", mapOf("Letra" to "U", "Força" to "Trabalho em Equipa"), "A união faz a força em todas as aventuras!", "U • NIÃO", "U", 0xFFC5CAE9),
            EducationalItem("dic_sabedoria", "Sabedoria", "N a Z", "🦉", "Bom Senso", "A capacidade de tomar boas decisões aprendendo com a experiência e o estudo.", mapOf("Letra" to "S", "Virtude" to "Discernimento"), "A sabedoria guia os nossos passos pelo caminho certo!", "SA • BE • DO • RIA", "S", 0xFFD1C4E9)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_dic1", "Como se chama a vontade de aprender e descobrir coisas novas?", listOf("Curiosidade", "Sono", "Preguiça"), "Curiosidade", "A curiosidade é o motor dos grandes exploradores!", "🔍")
        )
    )

    // ==========================================
    // 20. LIVRO MÁGICO DO ZÉ TRAQUINA
    // ==========================================
    private val livroMagicoTopic = EducationalTopicData(
        id = "livro_magico",
        title = "Livro Mágico",
        subtitle = "Páginas encantadas com lições práticas, valores e segredos 📚",
        iconEmoji = "📚",
        accentColor = Color(0xFFE65100),
        headerGradient = listOf(Color(0xFFBF360C), Color(0xFFE65100)),
        searchPlaceholder = "Procurar página mágica...",
        customTabName = "Páginas",
        categories = listOf(
            EducationalCategoryFilter("Todas", "📚", Color(0xFFE65100)),
            EducationalCategoryFilter("Valores", "🌟", Color(0xFFFBC02D)),
            EducationalCategoryFilter("Sabedoria", "🦉", Color(0xFF5E35B1)),
            EducationalCategoryFilter("Criatividade", "🎨", Color(0xFF0288D1))
        ),
        items = listOf(
            EducationalItem("lm_1", "Página 1: A Magia da Verdade", "Valores", "🌟", "Dizer Sempre a Verdade", "Dizer a verdade faz com que todos confiem em nós e deixa o coração leve como uma pluma.", mapOf("Página" to "1", "Regra de Ouro" to "Sinceridade"), "A verdade ilumina qualquer caminho!", "VER • DA • DE", "PÁG 1", 0xFFFFE082),
            EducationalItem("lm_2", "Página 2: O Poder do Respeito", "Valores", "🤝", "Tratar Bem Todas as Pessoas", "Respeitar os mais velhos, os amigos e a natureza cria harmonia no mundo.", mapOf("Página" to "2", "Regra de Ouro" to "Educação e Gentileza"), "O respeito abre todas as portas da vida!", "RES • PEI • TO", "PÁG 2", 0xFF80DEEA),
            EducationalItem("lm_3", "Página 3: A Paciência Sábia", "Sabedoria", "🌱", "Saber Esperar", "Como uma semente que precisa de tempo e água para virar flor, tudo tem o seu momento certo.", mapOf("Página" to "3", "Regra de Ouro" to "Calma e Persistência"), "A paciência transforma sementes em árvores majestosas!", "PA • CIÊN • CIA", "PÁG 3", 0xFFA5D6A7),
            EducationalItem("lm_4", "Página 4: O Sonho e a Arte", "Criatividade", "🎨", "Inventar sem Limites", "Desenhar, cantar e criar mundos novos no caderno exercita o cérebro e diverte.", mapOf("Página" to "4", "Regra de Ouro" to "Imaginação Livre"), "A imaginação não tem limites no Livro Mágico!", "SO • NHO", "PÁG 4", 0xFFFFCC80)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_lm1", "O que acontece a uma semente quando temos paciência e a regamos com carinho?", listOf("Cresce e dá flores", "Fica deitada para sempre", "Desaparece"), "Cresce e dá flores", "Com paciência e amor tudo cresce forte!", "🌱")
        )
    )

    // ==========================================
    // 21. SAÚDE E HIGIENE DO ZÉ TRAQUINA
    // ==========================================
    private val saudeHigieneTopic = EducationalTopicData(
        id = "saude_higiene",
        title = "Saúde e Higiene",
        subtitle = "Aprende os hábitos diários para crescer forte, limpo e saudável 🦷",
        iconEmoji = "🦷",
        accentColor = Color(0xFF00695C),
        headerGradient = listOf(Color(0xFF004D40), Color(0xFF00695C)),
        searchPlaceholder = "Procurar hábito de saúde...",
        customTabName = "Hábitos",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🦷", Color(0xFF00695C)),
            EducationalCategoryFilter("Higiene", "🧼", Color(0xFF0288D1)),
            EducationalCategoryFilter("Alimentação", "🍎", Color(0xFF388E3C)),
            EducationalCategoryFilter("Descanso", "😴", Color(0xFF5E35B1))
        ),
        items = listOf(
            EducationalItem("sh_dentes", "Lavar os Dentes", "Higiene", "🦷", "Sorriso Brilhante e Protegido", "Escovar os dentes após cada refeição durante 2 minutos afasta as cáries e bactérias.", mapOf("Tempo" to "2 Minutos", "Frequência" to "3x ao dia (após comer)"), "Dentes lavados e limpos garantem um sorriso lindo!", "DEN • TES", "HIGIENE", 0xFF80DEEA),
            EducationalItem("sh_maos", "Lavar as Mãos", "Higiene", "🧼", "Com Água e Sabão", "Lavar bem as palmas, dedos e unhas elimina germes antes das refeições e depois de brincar.", mapOf("Duração" to "20 Segundos com sabão", "Quando" to "Antes de comer e ao chegar a casa"), "Mãos limpas com sabão protegem a tua saúde!", "MÃ • OS", "DEFESA", 0xFFC8E6C9),
            EducationalItem("sh_agua", "Beber Água", "Alimentação", "💧", "Hidratação do Corpo", "Beber água fresca durante todo o dia mantém a mente rápida e o corpo hidratado.", mapOf("Meta" to "Vários copos ao dia", "Importância" to "Energia e digestão"), "A água é a bebida mais saudável da vida!", "Á • GUA", "VIDA", 0xFF82B1FF),
            EducationalItem("sh_fruta", "Comer Frutas e Legumes", "Alimentação", "🍎", "Superpoderes Naturais", "Pratos coloridos com cenoura, brócolos e fruta dão vitaminas essenciais.", mapOf("Cor" to "Prato bem colorido", "Benefício" to "Vitaminas e Minerais"), "Fruta e legumes dão super energia para brincar!", "FRU • TA", "NUTRIÇÃO", 0xFFFFAB91),
            EducationalItem("sh_sono", "Dormir Cedo", "Descanso", "😴", "Crescer Enquanto Dormes", "Durante o sono profundo a hormona do crescimento trabalha e o corpo recarrega a bateria.", mapOf("Horas" to "9 a 10 horas por noite", "Benefício" to "Crescimento e Memória"), "Dormir bem dá bom humor e força para a manhã!", "SO • NO", "RECARREGAR", 0xFFD1C4E9)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_sh1", "Durante quanto tempo devemos escovar os dentes?", listOf("2 Minutos", "5 Segundos", "Uma hora"), "2 Minutos", "Dois minutos de escovagem limpam todos os dentes!", "🦷"),
            EducationalQuizQuestion("q_sh2", "O que devemos usar sempre para lavar as mãos?", listOf("Água e Sabão", "Sumo de maçã", "Areia"), "Água e Sabão", "Água e sabão eliminam os micróbios!", "🧼")
        )
    )

    // ==========================================
    // 22. CORES E FORMAS DO ZÉ TRAQUINA
    // ==========================================
    private val coresFormasTopic = EducationalTopicData(
        id = "cores_formas",
        title = "Cores e Formas",
        subtitle = "Descobre as cores do arco-íris e as figuras geométricas 🎨",
        iconEmoji = "🎨",
        accentColor = Color(0xFF00897B),
        headerGradient = listOf(Color(0xFF004D40), Color(0xFF00897B)),
        searchPlaceholder = "Procurar cor ou forma...",
        customTabName = "Cores",
        categories = listOf(
            EducationalCategoryFilter("Todas", "🎨", Color(0xFF00897B)),
            EducationalCategoryFilter("Cores", "🌈", Color(0xFFE65100)),
            EducationalCategoryFilter("Formas", "📐", Color(0xFF1976D2))
        ),
        items = listOf(
            EducationalItem("cf_circulo", "Círculo", "Formas", "🔴", "Redondo como a Bola", "O círculo não tem cantos e roda como uma roda de bicicleta!", mapOf("Forma" to "Redonda", "Cantos" to "0 Cantos"), "O círculo é redondinho como o Sol!", "CÍR • CU • LO", "FORMA", 0xFFFF8A80),
            EducationalItem("cf_quadrado", "Quadrado", "Formas", "🟦", "4 Lados Iguais", "O quadrado tem quatro lados do mesmo tamanho e quatro cantos perfeitos.", mapOf("Forma" to "Quadrada", "Lados" to "4 Lados iguais"), "O quadrado é perfeito com quatro lados iguaizinhos!", "QUA • DRA • DO", "FORMA", 0xFF80DEEA),
            EducationalItem("cf_triangulo", "Triângulo", "Formas", "🔺", "3 Lados e 3 Pontas", "O triângulo parece uma fatia de pizza ou o teto de uma casinha!", mapOf("Forma" to "Triangular", "Lados" to "3 Lados"), "O triângulo tem três pontinhas como uma fatia de pizza!", "TRI • ÂN • GU • LO", "FORMA", 0xFFFFD54F),
            EducationalItem("cf_amarelo", "Cor Amarela", "Cores", "💛", "A Cor do Sol", "O amarelo é a cor da luz do Sol, das bananas e do girassol!", mapOf("Tipo" to "Cor Primária", "Sensação" to "Alegria e Luz"), "Amarelo brilhante como o Sol no céu!", "A • MA • RE • LO", "COR", 0xFFFFE082),
            EducationalItem("cf_azul", "Cor Azul", "Cores", "💙", "A Cor do Mar", "O azul é a cor do céu sem nuvens e das águas profundas do mar.", mapOf("Tipo" to "Cor Primária", "Sensação" to "Calma e Serenidade"), "Azul infinito como o céu e o mar!", "A • ZUL", "COR", 0xFF80CBC4)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_cf1", "Quantos lados tem um triângulo?", listOf("3 Lados", "4 Lados", "Nenhum"), "3 Lados", "O triângulo tem 3 lados e 3 pontas!", "🔺")
        )
    )

    // ==========================================
    // 23. NATUREZA E CLIMA DO ZÉ TRAQUINA
    // ==========================================
    private val naturezaTopic = EducationalTopicData(
        id = "natureza",
        title = "Natureza",
        subtitle = "Descobre a chuva, o arco-íris, os rios, o sol e as florestas 🌿",
        iconEmoji = "🌿",
        accentColor = Color(0xFF2E7D32),
        headerGradient = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32)),
        searchPlaceholder = "Procurar elemento natural...",
        customTabName = "Natureza",
        categories = listOf(
            EducationalCategoryFilter("Todos", "🌿", Color(0xFF2E7D32)),
            EducationalCategoryFilter("Clima", "🌦️", Color(0xFF0288D1)),
            EducationalCategoryFilter("Plantas", "🌳", Color(0xFF388E3C)),
            EducationalCategoryFilter("Água e Rios", "🌊", Color(0xFF0097A7)),
            EducationalCategoryFilter("Sol e Céu", "☀️", Color(0xFFF57F17))
        ),
        items = listOf(
            EducationalItem("nat_arcoiris", "Arco-Íris", "Clima", "🌈", "7 Cores no Céu", "Quando a luz do Sol atravessa gotinhas de chuva no ar, nasce um arco-íris mágico!", mapOf("Cores" to "7 Cores (Vermelho a Violeta)", "Condição" to "Sol + Chuva"), "O arco-íris pinta o céu de alegria depois da chuva!", "AR • CO • Í • RIS", "MÁGICO", 0xFFFFD54F),
            EducationalItem("nat_arvore", "Árvores e Florestas", "Plantas", "🌳", "Pulmões do Planeta", "As árvores dão sombra, fruta e transformam o ar para podermos respirar oxigénio puro.", mapOf("Importância" to "Produz Oxigénio", "Raízes" to "Seguram a terra"), "As árvores são amigas da vida e do ar puro!", "ÁR • VO • RE", "VIDA", 0xFFA5D6A7),
            EducationalItem("nat_chuva", "Chuva e Gotinhas", "Clima", "🌧️", "Água para a Terra", "A chuva rega os campos, enche os rios e faz as flores e árvores crescerem felizes.", mapOf("Origem" to "Nuvens no céu", "Importância" to "Rega da Natureza"), "A chuva traz vida e frescura à terra!", "CHU • VA", "ÁGUA", 0xFF80DEEA),
            EducationalItem("nat_rio", "Rio Cristalino", "Água e Rios", "🌊", "Corrente de Vida", "Os rios correm pelas montanhas levando água cristalina para os vales e o mar.", mapOf("Origem" to "Nascente na serra", "Destino" to "O Oceano"), "O rio corre alegremente cantarolando por entre as pedras!", "RI • O", "ÁGUA", 0xFF81D4FA),
            EducationalItem("nat_sol", "Sol Radiante", "Sol e Céu", "☀️", "Energia e Calor", "O Sol é a estrela gigante que aquece a Terra, dá luz ao dia e faz crescer as plantas.", mapOf("Tipo" to "Estrela", "Função" to "Luz e Calor"), "O Sol brilha forte e aquece todos os nossos dias!", "SOL", "ENERGIA", 0xFFFFEE58),
            EducationalItem("nat_vento", "Vento e Brisa", "Clima", "💨", "Ar em Movimento", "O vento sopra suavemente pelas árvores, faz girar cataventos e refresca nos dias quentes.", mapOf("Efeito" to "Movimento nas folhas", "Força" to "Brisa suave"), "Sentes o vento a soprar suavemente no teu rosto?", "VEN • TO", "AR", 0xFFCFD8DC),
            EducationalItem("nat_flores", "Flores do Campo", "Plantas", "🌻", "Cores e Perfume", "As flores enfeitam os prados com cores vivas e atraem as abelhas com o seu perfume doce.", mapOf("Função" to "Polinização", "Beleza" to "Cores da Terra"), "As flores enchem os campos de alegria e cor!", "FLO • RES", "BELEZA", 0xFFFFCCBC),
            EducationalItem("nat_montanha", "Montanhas Altas", "Plantas", "⛰️", "Gigantes de Pedra", "As montanhas erguem-se imponentes até ao céu, onde nasce a neve e as nascentes de água fresca.", mapOf("Altitude" to "Altas elevações", "Natureza" to "Rocha e Neve"), "As montanhas tocam o céu com majestade!", "MON • TA • NHAS", "TERRA", 0xFFB0BEC5),
            EducationalItem("nat_borboleta", "Borboletas Coloridas", "Plantas", "🦋", "Danças no Jardim", "As borboletas nascem de lagartas e voam graciosamente de flor em flor ajudando a natureza.", mapOf("Transformação" to "Metamorfose", "Habitat" to "Jardins e Prados"), "A borboleta dança levemente no ar!", "BOR • BO • LE • TA", "VIDA", 0xFFF8BBD0)
        ),
        quizQuestions = listOf(
            EducationalQuizQuestion("q_nat1", "O que faz aparecer o arco-íris no céu?", listOf("Sol e chuva ao mesmo tempo", "Vento muito forte", "Noite escura"), "Sol e chuva ao mesmo tempo", "A luz do Sol nas gotinhas cria o arco-íris!", "🌈"),
            EducationalQuizQuestion("q_nat2", "O que nos dá luz e calor durante o dia?", listOf("O Sol", "A Lua", "As estrelas"), "O Sol", "O Sol é a nossa estrela que ilumina e aquece a Terra!", "☀️")
        )
    )

    private fun numberToPortugueseWord(num: Int): String {
        if (num == 100) return "Cem"
        val dezenas = listOf("", "Dez", "Vinte", "Trinta", "Quarenta", "Cinquenta", "Sessenta", "Setenta", "Oitenta", "Noventa")
        val unidades = listOf("", "Um", "Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove")
        val especiais = mapOf(
            11 to "Onze", 12 to "Doze", 13 to "Treze", 14 to "Catorze", 15 to "Quinze",
            16 to "Dezasseis", 17 to "Dezassete", 18 to "Dezoito", 19 to "Dezanove"
        )
        if (num in 1..9) return unidades[num]
        if (num == 10) return "Dez"
        if (num in 11..19) return especiais[num] ?: ""
        val dez = num / 10
        val uni = num % 10
        return if (uni == 0) dezenas[dez] else "${dezenas[dez]} e ${unidades[uni].lowercase()}"
    }
}
