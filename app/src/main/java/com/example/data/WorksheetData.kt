package com.example.data

sealed class FichaQuestao {
    data class Escrever(val enunciado: String) : FichaQuestao()
    data class Rodeia(val enunciado: String, val lista: List<String>) : FichaQuestao()
    data class Completa(val enunciado: String, val lacunas: List<String>) : FichaQuestao()
    data class Ilustrar(val enunciado: String) : FichaQuestao()
    data class Frase(val enunciado: String) : FichaQuestao()
}

data class FichaConteudo(
    val id: Int,
    val titulo: String,
    val questoes: List<FichaQuestao>
)

object WorksheetData {
    val fichasPortugues = listOf(
        FichaConteudo(
            id = 1,
            titulo = "Ficha 1: Vogais",
            questoes = listOf(
                FichaQuestao.Escrever("Escreve as vogais: a, A, e, E, i, I, o, O, u, U (letra manuscrita e maiúscula)."),
                FichaQuestao.Rodeia("Rodeia as vogais nesta lista", listOf("I", "k", "s", "a", "n", "A", "h", "o", "w", "i", "o", "i", "u", "d", "o", "i", "h", "ç")),
                FichaQuestao.Rodeia("Rodeia as palavras que se iniciam por vogais", listOf("Inês", "iguana", "Ulisses", "Olga", "Eva", "Ana", "índio", "Ivone", "ananás", "Paula", "Leonor", "Ivo")),
                FichaQuestao.Completa("Completa as palavras com as vogais em falta", listOf("_vas", "b_la", "min_na"))
            )
        ),
        FichaConteudo(
            id = 2,
            titulo = "Ficha 2: Ditongos",
            questoes = listOf(
                FichaQuestao.Rodeia("Pinta os ditongos com cores diferentes e rodeia-os nas palavras", listOf("iu", "ui", "oi", "eu", "ai", "ão", "au", "mau", "pai", "foi", "cão", "bebeu", "pavão", "aula", "fugiu", "pneu", "passarão", "cuidado", "papoila")),
                FichaQuestao.Escrever("Escreve os ditongos ditados pelo professor (ditado)."),
                FichaQuestao.Ilustrar("Rodeia os ditongos nestas palavras e escreve-as. Ilustra: baleia, carapau, papoila, pião, xaile, balões")
            )
        ),
        FichaConteudo(
            id = 3,
            titulo = "Ficha 3: Letras P e T",
            questoes = listOf(
                FichaQuestao.Escrever("Lê e copia: papaia, tio, papão, teia, patito"),
                FichaQuestao.Ilustrar("Copia e ilustra as frases: 'Ó pai, o apito é teu.' / 'A pata papa o pão.' / 'É a teia, o pato e o tapete.' / 'A tia tapa o pote.'"),
                FichaQuestao.Escrever("Lê e copia: 'É a Tieta e a Tita.' / 'É o pato, a pata e a patita.' / 'A tia põe o tapete.' / 'Ó tio, o pato é teu.'")
            )
        ),
        FichaConteudo(
            id = 4,
            titulo = "Ficha 4: Letras L e D",
            questoes = listOf(
                FichaQuestao.Escrever("Legenda as imagens com: leite 🥛, lata 🥫, tulipa 🌷, Paulo (menino 🧒), lupa 🔍, dado 🎲"),
                FichaQuestao.Rodeia("Classifica em 'Nomes' vs 'Animais'", listOf("Dalila", "leitão", "Adão", "pato", "Adélia", "Tadeu", "Otília", "leão", "pata", "lula")),
                FichaQuestao.Escrever("Lê e copia: 'O tio Paulo é piloto.' / 'A papoila é da tia Adélia.' / 'A Dalila tapa o pote.'")
            )
        ),
        FichaConteudo(
            id = 5,
            titulo = "Ficha 5: Letras M e V",
            questoes = listOf(
                FichaQuestao.Ilustrar("Copia e ilustra: melão, tomate, limão, mala"),
                FichaQuestao.Escrever("Conta as sílabas de: meta, mola, viola, avião"),
                FichaQuestao.Escrever("Escolhe a palavra certa e escreve a frase: 'A Violeta dá uma [papoila/tomate] à tia Ema.' / 'A mãe põe [lume/pomada] na mão da Amélia.' / 'O tio Vilela lê [meia/muito].'")
            )
        ),
        FichaConteudo(
            id = 6,
            titulo = "Ficha 6: Letras N e H",
            questoes = listOf(
                FichaQuestao.Rodeia("Rodeia a palavra certa e escreve-a", listOf("nove", "neve", "panela", "nota", "Rita", "roda", "pato", "rato")),
                FichaQuestao.Completa("Escolhe a palavra e completa a frase", listOf("A Nádia toca [novelo/neve/piano]", "O Nuno mora no [metade/número/nove] oito", "À [noite/dia/navio], ele vê a Lua", "A Natália [ata/nada/vai] no lago", "O avô Tomé vai na [mota/mala/Noé]")),
                FichaQuestao.Escrever("Responde de acordo com o emoji: noiva 👰 (É a noiva? Sim, é a ___.) / navio ⛴️ (É a avioneta? Não, ___.) / rio 🏞️ (É a vila? Não, ___.)")
            )
        ),
        FichaConteudo(
            id = 7,
            titulo = "Ficha 7: Letras RR e R (som fraco)",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: torre, Correia, arara, pereira"),
                FichaQuestao.Completa("Completa com r ou rr", listOf("capoeira", "Maria", "terra", "arreia", "torrez", "moro", "terreno", "amarelo", "torradeira", "vazio", "Aveiro", "Mara")),
                FichaQuestao.Frase("Completa as frases: 'A pereira dá a ___.' / 'A amoreira dá a ___.' / 'O limoeiro dá o ___.' / 'A videira dá a ___.'"),
                FichaQuestao.Escrever("Ordena as palavras e escreve frases: 'adora torrada. uma A Vera' / 'à A torre. Natália foi' / 'arara A avó Maria da voa muito.'")
            )
        ),
        FichaConteudo(
            id = 8,
            titulo = "Ficha 8: Letras C e Q",
            questoes = listOf(
                FichaQuestao.Rodeia("Assinala o nome de cada animal", listOf("camaleão", "cadela", "cuco", "camelo", "quivi", "cão", "pica-pau", "cavalo")),
                FichaQuestao.Completa("Completa com ca, co ou cu", listOf("cadela", "capa", "la", "po", "pão", "co", "neca", "deira")),
                FichaQuestao.Escrever("Junta as sílabas e escreve: qui+vi/eto/na; que+rido/da"),
                FichaQuestao.Escrever("Responde de acordo com o exemplo: 'O que é? É o queque.' / 'O que é? ___' (peixinho)")
            )
        ),
        FichaConteudo(
            id = 9,
            titulo = "Ficha 9: Letras B e G",
            questoes = listOf(
                FichaQuestao.Escrever("Legenda as imagens: bola ⚽, banana 🍌, gorila 🦍, papagaio 🦜"),
                FichaQuestao.Rodeia("Liga as palavras que rimam", listOf("gavião-gaiola", "gaiola-camaleão", "botão-barato", "gato-malão")),
                FichaQuestao.Completa("Completa as frases com: cágado, égua, lago, Benedita", listOf("O avô Galileu vê uma ___ na mata.", "A neta ___ leva a cadela ao ___ e vê um ___.")),
                FichaQuestao.Escrever("Escreve frases com: Ágata/cogumelo; Abílio/gaveta; Anabela/gaivota")
            )
        ),
        FichaConteudo(
            id = 10,
            titulo = "Ficha 10: gue e gui",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: guerra, foguetão, guitarra, guia"),
                FichaQuestao.Completa("Completa com gue ou gui", listOf("guerra", "água", "guitarra", "foguetão", "figueira", "noguira", "foguira", "apagui", "gua", "laguito", "guerreira", "amiguito")),
                FichaQuestao.Frase("Liga e forma frases: 'A iguana toca a águia.' / 'A Guida veio da savana.' / 'O Galileu vê guitarra.' / 'O papagaio adora a dona.'")
            )
        ),
        FichaConteudo(
            id = 11,
            titulo = "Ficha 11: Letras J e F",
            questoes = listOf(
                FichaQuestao.Frase("Liga e forma frases: 'A Júlia é muito bonita.' / 'O jogo do dómino é da Filipa.' / 'É a juba amarela do leão.' / 'O João joga dominó.'"),
                FichaQuestao.Escrever("Lê o texto 'A fada Juliana': A fada deu um fato bonito à Filipa. A Filipa falou à Fátima da fada. A Fátima deu um bolo à fada. A fada pegou no bolo e a fatia do bolo deitou fumo... A Fátima riu e comeu o bolo!"),
                FichaQuestao.Escrever("Responde: A amiga da Filipa é a Juliana? / O que deitou a fatia de bolo? / O que comeu a Fátima?")
            )
        ),
        FichaConteudo(
            id = 12,
            titulo = "Ficha 12: Letras GE e GI",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: gelo, página, génio, gira"),
                FichaQuestao.Completa("Completa com ge, gi", listOf("gelo", "tigela", "Rogério", "relógios", "gema", "magia", "gelado", "gemido")),
                FichaQuestao.Escrever("Ordena e escreve frases: 'gelatina. Come a O Júlio' / 'lê página. O Filipe a' / 'a A avó põe gema panela. Na' / 'Eugénia o A come tigela. na gelado' / 'Rogério O mágico. é'")
            )
        ),
        FichaConteudo(
            id = 13,
            titulo = "Ficha 13: Letras SS e S",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: osso, vassoura, Salomé, sofá"),
                FichaQuestao.Completa("Escolhe e escreve", listOf("O Simão [amassou/amassei] a massa para o pão.", "A Salomé [subi/subiu] ao pessegueiro.", "A Sara [sou/é] uma amiga muito sábia.")),
                FichaQuestao.Ilustrar("Copia e ilustra: 'O Salomão foi de jipe à savana e viu uma girafa e um leão.'")
            )
        ),
        FichaConteudo(
            id = 14,
            titulo = "Ficha 14: as, es, is, os, us",
            questoes = listOf(
                FichaQuestao.Completa("Completa: poeca, penta, mengo, gaita, penta", listOf("as", "es", "is", "os", "us")),
                FichaQuestao.Escrever("Escreve nas frases: 'A Inês vê o ___ no vaso.' / 'O pai do Vasco foi à ___.' / 'A Maria ___ da banana assada.' / 'O Matias corre na ___.' / 'A Filipa põe a ___ na escova.'"),
                FichaQuestao.Completa("Completa com as, es, is, os, us", listOf("os elefantes", "as raposas", "os cangurus", "os javalis", "os macacos")),
                FichaQuestao.Completa("Plurais", listOf("gata→gatas", "jacaré→jacarés", "papagaio→papagaios", "vaca→vacas", "cadela→cadelas", "galo→galos", "baleia→baleias", "foca→focas", "rato→ratos"))
            )
        ),
        FichaConteudo(
            id = 15,
            titulo = "Ficha 15: Ç",
            questoes = listOf(
                FichaQuestao.Escrever("Legenda as imagens: taça 🏆, ouriço 🦔, baloiço 🎠, rebuçado 🍬, poço ⛲, maçã 🍎"),
                FichaQuestao.Escrever("Lê o texto 'O mágico do laço': Hoje, o mágico foi à escola do Vasco. Disse: 'Olá, meus meninos e meninas!' Levava uma camisa e um laço no pescoço. Usava sapatos amarelos. Adora magia e os meninos estavam satisfeitos. Quando acabou, estavam fascinados."),
                FichaQuestao.Escrever("Responde: Quem foi à escola do Vasco? / Como estava vestido o mágico? / O que é que o mágico adora? / Como estavam os meninos?")
            )
        ),
        FichaConteudo(
            id = 16,
            titulo = "Ficha 16: ce e ci",
            questoes = listOf(
                FichaQuestao.Completa("Completa com ce ou ci", listOf("cebola", "cenoura", "cereja", "cidade", "cigarra", "circo")),
                FichaQuestao.Escrever("Ordena as sílabas: ci+va+na, ci+o+fi+na"),
                FichaQuestao.Escrever("Lê o texto 'A cigana': A Cidália é uma menina cigana. Vive com os pais numa cidade dos Açores. Sai da cama cedo e ajuda os pais. Um dia viu uma cigarra que ficou sua amiga."),
                FichaQuestao.Escrever("Responde: Quem é a Cidália? / Onde vive? / Levanta-se cedo? / Quem viu?")
            )
        ),
        FichaConteudo(
            id = 17,
            titulo = "Ficha 17: z e s (som z)",
            questoes = listOf(
                FichaQuestao.Frase("Liga e forma frases: 'Uma dúzia de búzios são doze.' / 'O cozido do Zeca e da Rosa leva azeite.' / 'A Zita da avó Zélia é bela.' / 'A amizade buzina ao amigo Zé.'"),
                FichaQuestao.Rodeia("Assinala o conjunto onde s tem som de z", listOf("sapo/sapato/Salomé/sabonete", "tesoura/Eliseu/música/casa", "Viseu/coseu/Elisabete/vaso")),
                FichaQuestao.Completa("Classifica: casa (casita, casarão, casota, casaco) vs camisa (camisola, camiseiro)", listOf("casa", "camisa"))
            )
        ),
        FichaConteudo(
            id = 18,
            titulo = "Ficha 18: az, ez, iz, oz, uz",
            questoes = listOf(
                FichaQuestao.Completa("Completa com az, ez, iz, oz, uz", listOf("nariz", "cabaz", "dez", "rapaz", "capuz", "arroz")),
                FichaQuestao.Escrever("Lê o texto 'Vaz visita os avós': O Vaz visitou os avós. O avô ficou feliz e a avó sorriu satisfeita. Passou a tarde no bosque e viu um ninho de perdiz. A perdiz assustou-se e voou depressa. Para o jantar, a avó fez arroz, pato assado e bolo de noz."),
                FichaQuestao.Escrever("Responde: Onde foi o Vaz? / Como ficou o avô? / O que viu no ramo? / O que cozinhou a avó?")
            )
        ),
        FichaConteudo(
            id = 19,
            titulo = "Ficha 19: Letra H",
            questoes = listOf(
                FichaQuestao.Completa("Completa com ha, he, hi, ho, hu", listOf("hipopótamo", "habilidade", "herói", "hera", "herbário", "humidade", "habitação", "holofote", "heroína", "horas")),
                FichaQuestao.Escrever("Responde ao exemplo: 'Na savana há hienas? Sim, na savana há hienas.' Repete para: girafas, gazelas, leões, hipopótamos"),
                FichaQuestao.Escrever("Lê 'O hipopótamo': vive no Zoo, nada na água do lago, é feio e mete medo. Na jaula há uma hiena; o leão habita ao pé dela. O hipopótamo é o único que vê todos os animais."),
                FichaQuestao.Escrever("Responde: Onde vive? / Onde nada? / Como é? / Que animais vê? / Quem vive ao pé da hiena?")
            )
        ),
        FichaConteudo(
            id = 20,
            titulo = "Ficha 20: Letra X e seus sons",
            questoes = listOf(
                FichaQuestao.Rodeia("Liga cada grupo ao som de x", listOf("máximo/próximo/auxílio (som x)", "xilofone/ameixa/xerife (som z)", "táxi/oxigénio/fixo (som s)", "exercício/exército/exame (som cs)")),
                FichaQuestao.Completa("Escolhe e escreve", listOf("No sábado, a Aleixa [deixa/mexe] a sua casa e vai ao [repuxo/texugo] com a Xana.", "A Xana é uma rapariga [baixa/roxa] e usa madeixas.")),
                FichaQuestao.Ilustrar("Copia e ilustra: 'O Aleixo bebe água no repuxo.' / 'O xerife tomou o xarope todo.' / 'O Ulisses toca xilofone.'")
            )
        ),
        FichaConteudo(
            id = 21,
            titulo = "Ficha 21: Letras H, W e Y",
            questoes = listOf(
                FichaQuestao.Rodeia("Liga as palavras às imagens", listOf("yoga", "skate", "kart", "walkie-talkie", "karaté")),
                FichaQuestao.Escrever("Separa e escreve frases: 'AYocoeaKellysãokaratecasevãoaokaraté.' (A Yoco e a Kelly são karatecas e vão ao karaté) / 'OWillygostadokart.' (O Willy gosta do kart) / 'OWilsonvaideskate.' (O Wilson vai de skate)"),
                FichaQuestao.Escrever("Escreve frases com: Yuri/karaté; Kelly/yoga")
            )
        ),
        FichaConteudo(
            id = 22,
            titulo = "Ficha 22: Sistematização do alfabeto",
            questoes = listOf(
                FichaQuestao.Escrever("Escreve o alfabeto minúsculo e maiúsculo (a-z, A-Z)."),
                FichaQuestao.Escrever("Escreve palavras que comecem por cada vogal (a, e, i, o, u)."),
                FichaQuestao.Escrever("Escreve palavras que comecem por cada consoante (b, c, d, f, g, h, j, l, m, n, p, q, r, s, t, v, w, x, y, z)."),
                FichaQuestao.Escrever("Escreve frases com as palavras acima.")
            )
        ),
        FichaConteudo(
            id = 23,
            titulo = "Ficha 23: al, el, il, ol, ul",
            questoes = listOf(
                FichaQuestao.Completa("Completa com al, el, il, ol, ul", listOf("algado", "Isabel", "Dilce", "olface", "farol", "funil", "solgado", "olmoço", "azul", "anil")),
                FichaQuestao.Escrever("Legenda as imagens: anel 💍, pulseira 📿, funil 🧪, alface 🥬, caracol 🐌"),
                FichaQuestao.Completa("Completa as frases", listOf("O Raul joga futebol.", "O polvo habita na água salgada.", "O pardal voa para o seu ramo.", "A Olga magoou-se no pulso.")),
                FichaQuestao.Ilustrar("Copia e ilustra: 'O Manuel usa calções azuis, camisola roxa e boné amarelo.' / 'O Miguel joga à bola no relvado e põe a bola na baliza.'")
            )
        ),
        FichaConteudo(
            id = 24,
            titulo = "Ficha 24: ar, er, ir, or, ur",
            questoes = listOf(
                FichaQuestao.Completa("Completa com ar, er, ir, or, ur", listOf("porco", "erva", "árvore", "barco", "irmão", "urso", "porta", "cartas")),
                FichaQuestao.Escrever("Divide em sílabas: carteiro, irmã, remar, turma, perna, borboleta"),
                FichaQuestao.Escrever("Escreve palavras que rimam: copiar apagar, sorrir ___, reler ___, pular ___, por ___, fazer ___, fugir ___, saltar ___, comer ___")
            )
        ),
        FichaConteudo(
            id = 25,
            titulo = "Ficha 25: an, en, in, on, un",
            questoes = listOf(
                FichaQuestao.Rodeia("Rodeia os sons nasais", listOf("húngaros", "tinta", "ponte", "banco", "pente")),
                FichaQuestao.Completa("Completa", listOf("pinto", "andorinha", "tenda", "laranja", "bandeira", "morango", "mundo", "montanha")),
                FichaQuestao.Escrever("Escreve frases com: quinta-feira, vento, elefante, ponte, varanda")
            )
        ),
        FichaConteudo(
            id = 26,
            titulo = "Ficha 26: am, em, im, om, um",
            questoes = listOf(
                FichaQuestao.Completa("Completa com am, em, im, om, um", listOf("nuvem", "ambulância", "jardim", "atum", "pombo")),
                FichaQuestao.Escrever("Divide em sílabas: tampa, computador, patim, som"),
                FichaQuestao.Completa("Completa com m ou n", listOf("zambarcação", "tampa", "limpeza", "dente", "também", "laranja", "mentiroso", "homem", "anterior", "valente", "conte", "tempo")),
                FichaQuestao.Ilustrar("Copia e ilustra: 'O comboio passa pela ponte da tua terra.' / 'O bombeiro apagou o incêndio.'")
            )
        ),
        FichaConteudo(
            id = 27,
            titulo = "Ficha 27: Plural de am, em, im, om, um",
            questoes = listOf(
                FichaQuestao.Completa("Escreve o plural", listOf("um→uns", "atum→atuns", "nuvem→nuvens", "pudim→pudins", "mandarim→mandarins", "jardim→jardins", "pinguim→pinguins", "homem→homens", "capim→capins", "garagem→garagens")),
                FichaQuestao.Escrever("Copia e rodeia os plurais: 'O avô Joaquim adora comer amendoins.' / 'A tia Filomena coloca os carros nas garagens.' / 'As princesas vão nas carruagens.' / 'Ontem, as amigas comeram pudins.' / 'A Matilde come uns deliciosos bombons.'"),
                FichaQuestao.Escrever("Passa para plural: 'O pai lavou o carro.' / 'O carro ficou muito lavado.'")
            )
        ),
        FichaConteudo(
            id = 28,
            titulo = "Ficha 28: Dígrafo NH",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: joaninha, libelinha, minhoca, cegonha, golfinho, aranha"),
                FichaQuestao.Rodeia("Palavras com sons de NH", listOf("galinha", "ovinho", "manhã")),
                FichaQuestao.Completa("Diminutivos", listOf("lata→latinha", "peixe→peixinho", "casa→casinha", "cavalo→cavalinho", "roda→rodinha", "carro→carrinho", "cama→caminha", "cadeira→cadeirinha")),
                FichaQuestao.Escrever("Lê e copia: 'A Aninha viu uma doninha castanha.' / 'A Carminho apanhou uma joaninha.' / 'A Madalena foi muito devagar e tocou na minhoca.'")
            )
        ),
        FichaConteudo(
            id = 29,
            titulo = "Ficha 29: Dígrafo LH",
            questoes = listOf(
                FichaQuestao.Completa("Completa com lha, lhe, lhi, lho, lhu", listOf("telhado", "coelhe", "joelho", "abelha", "toalha", "milho", "orelha", "alho", "abelhudo", "repolho", "ovelha", "sol", "ovelhinha", "aveira", "carvalho", "filho")),
                FichaQuestao.Completa("Completa as frases com: milho, folha, folhagem, bacalhau, vermelho", listOf("Como o ___ com batatas, couves e azeite.", "A galinha come o ___.", "A ___ é verdinha.", "No outono, a ___ das árvores muda de cor.", "O telhado é ___.")),
                FichaQuestao.Escrever("Lê o texto do coelho orelhudo. Responde: Onde morava? / De que materiais era feita a coelheira? / O que fez a abelha?")
            )
        ),
        FichaConteudo(
            id = 30,
            titulo = "Ficha 30: Dígrafo CH",
            questoes = listOf(
                FichaQuestao.Completa("Completa com cha, che, chi, cho, chu", listOf("chapéu", "cheque", "chocolate", "mocho", "mochila", "guarda-chuva", "chupeta", "borracha", "chinelos", "chaminé")),
                FichaQuestao.Escrever("Legenda: chapéu 🎩, cheque 🧾, chocolate 🍫, mocho 🦉, mochila 🎒, guarda-chuva ☂️, chupeta 🍼, borracha 🧼, chinelos 🩴, chaminé 🏭"),
                FichaQuestao.Completa("Completa com ch, lh ou nh", listOf("chave", "ovelha", "bolacha", "bilhete", "girafote", "rocha", "baunilha", "piteiro", "minhoca", "vezote", "bicarada", "andorinha")),
                FichaQuestao.Escrever("Ordena e escreve: 'Bebe A um Tucha chá chinesa. chávena numa' / 'acompanha Ela o bolacha chocolate. com de uma chá'")
            )
        ),
        FichaConteudo(
            id = 31,
            titulo = "Ficha 31: Grupos consonânticos br-vr",
            questoes = listOf(
                FichaQuestao.Completa("Completa com br, cr, dr, fr, gr, pr, tr, vr", listOf("cabra", "Escola", "grito", "abraço", "treze", "tigre", "zebra", "livro", "frigorífico", "presente", "preto", "tranquedo", "trigo", "abrigo", "trino", "avião", "dragão", "aterrado", "gravata", "fruta")),
                FichaQuestao.Rodeia("Rodeia a palavra certa", listOf("magro/mago", "fraca/faca", "baço/braço", "frita/fita", "dama/drama")),
                FichaQuestao.Escrever("Copia: 'Ontem, sonhei com um cavalo de crina branca.' / 'Fiquei muito alegre com aquilo que sonhei.' / 'A Estrela adora o relinchar dos cavalos.'")
            )
        ),
        FichaConteudo(
            id = 32,
            titulo = "Ficha 32: Grupos consonânticos bl-tl",
            questoes = listOf(
                FichaQuestao.Escrever("Divide em sílabas: atlas, ciclismo, atletismo, blusão, pluma, flecha"),
                FichaQuestao.Rodeia("Rodeia bl, cl, fl, pl, tl e copia", listOf("atleta", "planta", "flauta", "blusa", "ciclista")),
                FichaQuestao.Completa("Completa as frases", listOf("A [blusa] da tia Clara é de seda amarela.", "A bicicleta é do [ciclista].", "O [atleta] saltou muito alto.", "O Pedro toca melodias na sua [flauta].", "A Lígia plantou uma linda [planta].")),
                FichaQuestao.Escrever("Lê 'O Plutão'. Responde: Como se chama o cão? / Onde estava a Clarinha? / O que pisou o Plutão?")
            )
        ),
        FichaConteudo(
            id = 33,
            titulo = "Ficha 33: Compreensão da leitura (Lobo)",
            questoes = listOf(
                FichaQuestao.Escrever("Lê a história 'O lobo e os sete cabritinhos' e responde: Quantos filhotes tinha a cabra? / Onde foi a cabrinha? / Quem bateu à porta? / Onde foi o lobo buscar a farinha? / Onde se escondeu o irmão mais pequeno?")
            )
        ),
        FichaConteudo(
            id = 34,
            titulo = "Ficha 34: Compreensão da leitura (Casinha)",
            questoes = listOf(
                FichaQuestao.Escrever("Lê a história 'A casinha de chocolate' e responde (escolha múltipla): O pai era lavrador/lenhador/agricultor? Os irmãos brincavam no parque/bosque/quarto? A bruxa queria comer Gretel/Hansel/o lenhador?")
            )
        ),
        FichaConteudo(
            id = 35,
            titulo = "Ficha 35: Escrita – texto descritivo",
            questoes = listOf(
                FichaQuestao.Escrever("Observa o Sebastião 🧒. Assinala: é gordo/alto/baixo; cabelos ruivos/pretos/loiros; olhos verdes/azuis/pretos; boca grande/pequena/redonda."),
                FichaQuestao.Ilustrar("Descreve o Sebastião.")
            )
        ),
        FichaConteudo(
            id = 36,
            titulo = "Ficha 36: Escrita – texto narrativo",
            questoes = listOf(
                FichaQuestao.Ilustrar("Escreve uma história com título a partir das imagens (fada 🧚, princesa 👸, peixe 🐟, sol ☀️). Guião: 'Era uma vez... Então apareceu... De repente... Finalmente...'")
            )
        ),
        FichaConteudo(
            id = 37,
            titulo = "TPC Natal: Português",
            questoes = listOf(
                FichaQuestao.Escrever("Escreve em letra manuscrita: a, e, i, o, u, p, t, l, d, m, v"),
                FichaQuestao.Escrever("Escreve os ditongos que ouves."),
                FichaQuestao.Ilustrar("Rodeia os ditongos e ilustra: pão, põe, pai, pau, leite, oito"),
                FichaQuestao.Escrever("Legenda: pote 🏺, apito 📯, pato 🦆, tia 👩‍👦, lupa 🔍, pão 🍞, toalha 🧣, lua 🌙, mala 🧳, meia 🧦, mota 🏍️, mola 🖇️")
            )
        ),
        FichaConteudo(
            id = 38,
            titulo = "TPC Carnaval: Português",
            questoes = listOf(
                FichaQuestao.Rodeia("Rodeia a palavra certa", listOf("vela/veia/vala", "avô/ave/avião", "luva/lume/lula", "mota/mala/mola", "ovo/uva/Eva", "cola/couve/capa", "cuco/Caio/Cátia", "dedo/dia/data", "nota/neve/navio", "pato/Paulo/pateta", "roda/rato/ramo", "pera/peru/para")),
                FichaQuestao.Ilustrar("Lê, copia e ilustra: vaca, baleia, leque, amarelo")
            )
        )
    )
}
