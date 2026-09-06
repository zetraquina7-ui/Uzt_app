package com.example.ui.screens

import com.example.Ficha
import com.example.FichaBloco

object FichasPortuguesData {
    val fichasPortugues1Ano = listOf(
        Ficha(
            1, "Ficha 1: Vogais", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Escreve as vogais: a, A, e, E, i, I, o, O, u, U (letra manuscrita e maiúscula)."),
                FichaBloco.LinhasEscrita(2),
                FichaBloco.Instrucao("Rodeia as vogais nesta lista: I, k, s, a, n, A, h, o, w, i, o, i, u, d, o, i, h, ç"),
                FichaBloco.CaixaPalavras(listOf("I", "k", "s", "a", "n", "A", "h", "o", "w", "i", "o", "i", "u", "d", "o", "i", "h", "ç")),
                FichaBloco.Instrucao("Rodeia as palavras que se iniciam por vogais: Inês, iguana, Ulisses, Olga, Eva, Ana, índio, Ivone, ananás, Paula, Leonor, Ivo"),
                FichaBloco.Instrucao("Completa as palavras com as vogais em falta: _vas (uvas), b_la (bola), min_na (menina)"),
                FichaBloco.LinhasEscrita(3)
            )
        ),
        Ficha(
            2, "Ficha 2: Ditongos", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Pinta os ditongos com cores diferentes e rodeia-os nas palavras: iu, ui, oi, eu, ai, ão, au, mau, pai, foi, cão, bebeu, pavão, aula, fugiu, pneu, passarão, cuidado, papoila"),
                FichaBloco.Instrucao("Escreve os ditongos ditados pelo professor (ditado)."),
                FichaBloco.LinhasEscrita(2),
                FichaBloco.Instrucao("Rodeia os ditongos nestas palavras e escreve-as: baleia, carapau, papoila, pião, xaile, balões"),
                FichaBloco.EspacoDesenho(150)
            )
        ),
        Ficha(
            3, "Ficha 3: Letras P e T", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Lê e copia: papaia, tio, papão, teia, patito"),
                FichaBloco.LinhasEscrita(3),
                FichaBloco.Instrucao("Copia e ilustra as frases: 'Ó pai, o apito é teu.' / 'A pata papa o pão.' / 'É a teia, o pato e o tapete.' / 'A tia tapa o pote.'"),
                FichaBloco.EspacoDesenho(150),
                FichaBloco.Instrucao("Lê e copia: 'É a Tieta e a Tita.' / 'É o pato, a pata e a patita.' / 'A tia põe o tapete.' / 'Ó tio, o pato é teu.'"),
                FichaBloco.LinhasEscrita(4)
            )
        ),
        Ficha(
            4, "Ficha 4: Letras L e D", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Legenda as imagens com: leite 🥛, lata 🥫, tulipa 🌷, Paulo (menino 🧒), lupa 🔍, dado 🎲"),
                FichaBloco.LinhasEscrita(6),
                FichaBloco.Instrucao("Classifica em 'Nomes' vs 'Animais': Dalila, leitão, Adão, pato, Adélia, Tadeu, Otília, leão, pata, lula"),
                FichaBloco.LinhasEscrita(5),
                FichaBloco.Instrucao("Lê e copia: 'O tio Paulo é piloto.' / 'A papoila é da tia Adélia.' / 'A Dalila tapa o pote.'"),
                FichaBloco.LinhasEscrita(3)
            )
        ),
        Ficha(
            5, "Ficha 5: Letras M e V", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Copia e ilustra: melão, tomate, limão, mala"),
                FichaBloco.EspacoDesenho(150),
                FichaBloco.Instrucao("Conta as sílabas de: meta, mola, viola, avião"),
                FichaBloco.LinhasEscrita(4),
                FichaBloco.Instrucao("Escolhe a palavra certa e escreve a frase: 'A Violeta dá uma [papoila/tomate] à tia Ema.' / 'A mãe põe [lume/pomada] na mão da Amélia.' / 'O tio Vilela lê [meia/muito].'"),
                FichaBloco.LinhasEscrita(3)
            )
        ),
        Ficha(
            6, "Ficha 6: Letras N e H", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Rodeia a palavra certa: nove/neve, panela/nota, Rita/roda, pato/rato"),
                FichaBloco.Instrucao("Escolhe a palavra e completa a frase: 'A Nádia toca [novelo/neve/piano].' / 'O Nuno mora no [metade/número/nove] oito.' / 'À [noite/dia/navio], ele vê a Lua.' / 'A Natália [ata/nada/vai] no lago.' / 'O avô Tomé vai na [mota/mala/Noé].'"),
                FichaBloco.Instrucao("Responde de acordo com o emoji: noiva 👰 → 'É a noiva? Sim, é a ___.' / navio ⛴️ → 'É a avioneta? Não, ___.' / rio 🏞️ → 'É a vila? Não, ___.'")
            )
        ),
        Ficha(
            7, "Ficha 7: Letras RR e R (som fraco)", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: torre, Correia, arara, pereira"),
                FichaBloco.Instrucao("Completa com r ou rr: capoeira, Maria, terra, arreia, torrez, moro, terreno, amarelo, torradeira, vazio, Aveiro, Mara"),
                FichaBloco.Instrucao("Completa as frases: 'A pereira dá a ___.' / 'A amoreira dá a ___.' / 'O limoeiro dá o ___.' / 'A videira dá a ___.'"),
                FichaBloco.Instrucao("Ordena as palavras e escreve frases: 'adora torrada. uma A Vera' / 'à A torre. Natália foi' / 'arara A avó Maria da voa muito.'")
            )
        ),
        Ficha(
            8, "Ficha 8: Letras C e Q", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Assinala o nome de cada animal: camaleão/cadela, cuco/camelo, quivi/cão, pica-pau/cavalo"),
                FichaBloco.Instrucao("Completa com ca, co ou cu: dela→cadela, pa→capa, la, po, pão, co, neca, deira"),
                FichaBloco.Instrucao("Junta as sílabas e escreve: qui+vi/eto/na; que+rido/da"),
                FichaBloco.Instrucao("Responde: 'O que é? É o queque.' / 'O que é? ___' (peixinho)")
            )
        ),
        Ficha(
            9, "Ficha 9: Letras B e G", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Legenda as imagens: bola ⚽, banana 🍌, gorila 🦍, papagaio 🦜"),
                FichaBloco.Instrucao("Liga as palavras que rimam: gavião-viola, gaiola-camaleão, botão-barato, gato-malão"),
                FichaBloco.Instrucao("Completa as frases com: cágado, égua, lago, Benedita — 'O avô Galileu vê uma ___ na mata.' / 'A neta ___ leva a cadela ao ___ e vê um ___.'"),
                FichaBloco.Instrucao("Escreve frases com: Ágata/cogumelo; Abílio/gaveta; Anabela/gaivota")
            )
        ),
        Ficha(
            10, "Ficha 10: gue e gui", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: guerra, foguetão, guitarra, guia"),
                FichaBloco.Instrucao("Completa com gue ou gui: guerra, água, guitarra, foguetão, figueira, noguira, foguira, apagui, gua, laguito, guerreira, amiguito"),
                FichaBloco.Instrucao("Liga e forma frases: 'A iguana toca a águia.' / 'A Guida veio da savana.' / 'O Galileu vê guitarra.' / 'O papagaio adora a dona.'")
            )
        ),
        Ficha(
            11, "Ficha 11: Letras J e F", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Liga e forma frases: 'A Júlia é muito bonita.' / 'O jogo do dómino é da Filipa.' / 'É a juba amarela do leão.' / 'O João joga dominó.'"),
                FichaBloco.Instrucao("Lê o texto 'A fada Juliana': A fada deu um fato bonito à Filipa. A Filipa falou à Fátima da fada. A Fátima deu um bolo à fada. A fada pegou no bolo e a fatia do bolo deitou fumo... A Fátima riu e comeu o bolo!"),
                FichaBloco.Instrucao("Responde: A amiga da Filipa é a Juliana? / O que deitou a fatia de bolo? / O que comeu a Fátima?")
            )
        ),
        Ficha(
            12, "Ficha 12: Letras GE e GI", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: gelo, página, génio, gira"),
                FichaBloco.Instrucao("Completa com ge, gi: gelo, tigela, Rogério, relógios, gema, magia, gelado, gemido"),
                FichaBloco.Instrucao("Ordena e escreve frases: 'gelatina. Come a O Júlio' / 'lê página. O Filipe a' / 'a A avó põe gema panela. Na' / 'Eugénia o A come tigela. na gelado' / 'Rogério O mágico. é'")
            )
        ),
        Ficha(
            13, "Ficha 13: Letras SS e S", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: osso, vassoura, Salomé, sofá"),
                FichaBloco.Instrucao("Escolhe e escreve: 'O Simão [amassou/amassei] a massa para o pão.' / 'A Salomé [subi/subiu] ao pessegueiro.' / 'A Sara [sou/é] uma amiga muito sábia.'"),
                FichaBloco.Instrucao("Copia e ilustra: 'O Salomão foi de jipe à savana e viu uma girafa e um leão.'")
            )
        ),
        Ficha(
            14, "Ficha 14: as, es, is, os, us", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: poeca, penta, mengo, gaita, penta"),
                FichaBloco.Instrucao("Escreve nas frases: 'A Inês vê o ___ no vaso.' / 'O pai do Vasco foi à ___.' / 'A Maria ___ da banana assada.' / 'O Matias corre na ___.' / 'A Filipa põe a ___ na escova.'"),
                FichaBloco.Instrucao("Completa com as, es, is, os, us: os elefantes, as raposas, os cangurus, os javalis, os macacos"),
                FichaBloco.Instrucao("Plurais: gata→gatas, jacaré→jacarés, papagaio→papagaios, vaca→vacas, cadela→cadelas, galo→galos, baleia→baleias, foca→focas, rato→ratos")
            )
        ),
        Ficha(
            15, "Ficha 15: Ç", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Legenda as imagens: taça 🏆, ouriço 🦔, baloiço 🎠, rebuçado 🍬, poço ⛲, maçã 🍎"),
                FichaBloco.Instrucao("Lê o texto 'O mágico do laço': Hoje, o mágico foi à escola do Vasco. Disse: 'Olá, meus meninos e meninas!' Levava uma camisa e um laço no pescoço. Usava sapatos amarelos. Adora magia e os meninos estavam satisfeitos. Quando acabou, estavam fascinados."),
                FichaBloco.Instrucao("Responde: Quem foi à escola do Vasco? / Como estava vestido o mágico? / O que é que o mágico adora? / Como estavam os meninos?")
            )
        ),
        Ficha(
            16, "Ficha 16: ce e ci", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa com ce ou ci: cebola, cenoura, cereja, cidade, cigarra, circo"),
                FichaBloco.Instrucao("Ordena as sílabas: ci+va+na, ci+o+fi+na"),
                FichaBloco.Instrucao("Lê o texto 'A cigana': A Cidália é uma menina cigana. Vive com os pais numa cidade dos Açores. Sai da cama cedo e ajuda os pais. Um dia viu uma cigarra que ficou sua amiga."),
                FichaBloco.Instrucao("Responde: Quem é a Cidália? / Onde vive? / Levanta-se cedo? / Quem viu?")
            )
        ),
        Ficha(
            17, "Ficha 17: z e s (som z)", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Liga e forma frases: 'Uma dúzia de búzios são doze.' / 'O cozido do Zeca e da Rosa leva azeite.' / 'A Zita da avó Zélia é bela.' / 'A amizade buzina ao amigo Zé.'"),
                FichaBloco.Instrucao("Assinala o conjunto onde s tem som de z: sapo/sapato/Salomé/sabonete; tesoura/Eliseu/música/casa; Viseu/coseu/Elisabete/vaso"),
                FichaBloco.Instrucao("Classifica: casa (casita, casarão, casota, casaco) vs camisa (camisola, camiseiro)")
            )
        ),
        Ficha(
            18, "Ficha 18: az, ez, iz, oz, uz", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: nariz, cabaz, dez, rapaz, capuz, arroz"),
                FichaBloco.Instrucao("Lê o texto 'Vaz visita os avós': O Vaz visitou os avós. O avô ficou feliz e a avó sorriu satisfeita. Passou a tarde no bosque e viu um ninho de perdiz. A perdiz assustou-se e voou depressa. Para o jantar, a avó fez arroz, pato assado e bolo de noz."),
                FichaBloco.Instrucao("Responde: Onde foi o Vaz? / Como ficou o avô? / O que viu no ramo? / O que cozinhou a avó?")
            )
        ),
        Ficha(
            19, "Ficha 19: Letra H", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa com ha, he, hi, ho, hu: hipopótamo, habilidade, herói, hera, herbário, humidade, habitação, holofote, heroína, horas"),
                FichaBloco.Instrucao("Responde ao exemplo: 'Na savana há hienas? Sim, na savana há hienas.' Repete para: girafas, gazelas, leões, hipopótamos"),
                FichaBloco.Instrucao("Lê 'O hipopótamo': vive no Zoo, nada na água do lago, é feio e mete medo. Na jaula há uma hiena; o leão habita ao pé dela. O hipopótamo é o único que vê todos os animais."),
                FichaBloco.Instrucao("Responde: Onde vive? / Onde nada? / Como é? / Que animais vê? / Quem vive ao pé da hiena?")
            )
        ),
        Ficha(
            20, "Ficha 20: Letra X e seus sons", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Liga cada grupo ao som de x: máximo/próximo/auxílio, xilofone/ameixa/xerife, táxi/oxigénio/fixo, exercício/exército/exame"),
                FichaBloco.Instrucao("Escolhe e escreve: 'No sábado, a Aleixa [deixa/mexe] a sua casa e vai ao [repuxo/texugo] com a Xana.' / 'A Xana é uma rapariga [baixa/roxa] e usa madeixas.'"),
                FichaBloco.Instrucao("Copia e ilustra: 'O Aleixo bebe água no repuxo.' / 'O xerife tomou o xarope todo.' / 'O Ulisses toca xilofone.'")
            )
        ),
        Ficha(
            21, "Ficha 21: Letras H, W e Y", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Liga as palavras às imagens: yoga, skate, kart, walkie-talkie, karaté"),
                FichaBloco.Instrucao("Separa e escreve frases: 'A Yoco e a Kelly são karatecas e vão ao karaté.' / 'O Willy gosta do kart.' / 'O Wilson vai de skate.'"),
                FichaBloco.Instrucao("Escreve frases com: Yuri/karaté; Kelly/yoga")
            )
        ),
        Ficha(
            22, "Ficha 22: Sistematização do alfabeto", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Escreve o alfabeto minúsculo e maiúsculo (a-z, A-Z)."),
                FichaBloco.Instrucao("Escreve palavras que comecem por cada vogal (a, e, i, o, u)."),
                FichaBloco.Instrucao("Escreve palavras que comecem por cada consoante (b, c, d, f, g, h, j, l, m, n, p, q, r, s, t, v, w, x, y, z)."),
                FichaBloco.Instrucao("Escreve frases com as palavras acima.")
            )
        ),
        Ficha(
            23, "Ficha 23: al, el, il, ol, ul", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: a[l]gado, Isab[el], D[il]ce, [ol]face, far[ol], fun[il], s[ol]gado, [ol]moço, az[ul], an[il]"),
                FichaBloco.Instrucao("Legenda as imagens: anel 💍, pulseira 📿, funil 🧪, alface 🥬, caracol 🐌"),
                FichaBloco.Instrucao("Completa: 'O Raul joga futebol.' / 'O polvo habita na água salgada.' / 'O pardal voa para o seu ramo.' / 'A Olga magoou-se no pulso.'"),
                FichaBloco.Instrucao("Copia e ilustra: 'O Manuel usa calções azuis, camisola roxa e boné amarelo.' / 'O Miguel joga à bola no relvado e põe a bola na baliza.'")
            )
        ),
        Ficha(
            24, "Ficha 24: ar, er, ir, or, ur", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: porco, erva, árvore, barco, irmão, urso, porta, cartas"),
                FichaBloco.Instrucao("Divide em sílabas: carteiro, irmã, remar, turma, perna, borboleta"),
                FichaBloco.Instrucao("Escreve palavras que rimam: copiar apagar, sorrir ___, reler ___, pular ___, por ___, fazer ___, fugir ___, saltar ___, comer ___")
            )
        ),
        Ficha(
            25, "Ficha 25: an, en, in, on, un", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Rodeia os sons nasais em: húngaros, tinta, ponte, banco, pente"),
                FichaBloco.Instrucao("Completa: pinto, andorinha, tenda, laranja, bandeira, morango, mundo, montanha"),
                FichaBloco.Instrucao("Escreve frases com: quinta-feira, vento, elefante, ponte, varanda")
            )
        ),
        Ficha(
            26, "Ficha 26: am, em, im, om, um", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: nuvem, ambulância, jardim, atum, pombo"),
                FichaBloco.Instrucao("Divide em sílabas: tampa, computador, patim, som"),
                FichaBloco.Instrucao("Completa com m ou n: zambarcação, tampa, limpeza, dente, também, laranja, mentiroso, homem, anterior, valente, conte, tempo"),
                FichaBloco.Instrucao("Copia e ilustra: 'O comboio passa pela ponte da tua terra.' / 'O bombeiro apagou o incêndio.'")
            )
        ),
        Ficha(
            27, "Ficha 27: Plural de am, em, im, om, um", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Plurais: um→uns, atum→atuns, nuvem→nuvens, pudim→pudins, mandarim→mandarins, jardim→jardins, pinguim→pinguins, homem→homens, capim→capins, garagem→garagens"),
                FichaBloco.Instrucao("Copia e rodeia os plurais: 'O avô Joaquim adora comer amendoins.' / 'A tia Filomena coloca os carros nas garagens.' / 'As princesas vão nas carruagens.' / 'Ontem, as amigas comeram pudins.' / 'A Matilde come uns deliciosos bombons.'"),
                FichaBloco.Instrucao("Passa para plural: 'O pai lavou o carro.' / 'O carro ficou muito lavado.'")
            )
        ),
        Ficha(
            28, "Ficha 28: Dígrafo NH", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: joaninha, libelinha, minhoca, cegonha, golfinho, aranha"),
                FichaBloco.Instrucao("Palavras: galinha, ovinho, manhã (contar sons)"),
                FichaBloco.Instrucao("Diminutivos: lata→latinha, peixe→peixinho, casa→casinha, cavalo→cavalinho, roda→rodinha, carro→carrinho, cama→caminha, cadeira→cadeirinha"),
                FichaBloco.Instrucao("Lê e copia: 'A Aninha viu uma doninha castanha.' / 'A Carminho apanhou uma joaninha.' / 'A Madalena foi muito devagar e tocou na minhoca.'")
            )
        ),
        Ficha(
            29, "Ficha 29: Dígrafo LH", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa com lha, lhe, lhi, lho, lhu: telhado, coelhe, joelho, abelha, toalha, milho, orelha, alho, abelhudo, repolho, ovelha, sol, ovelhinha, aveira, carvalho, filho"),
                FichaBloco.Instrucao("Completa com: milho, folha, folhagem, bacalhau, vermelho — 'Como o ___ com batatas, couves e azeite.' / 'A galinha come o ___.' / 'A ___ é verdinha.' / 'No outono, a ___ das árvores muda de cor.' / 'O telhado é ___.'"),
                FichaBloco.Instrucao("Lê 'O coelho orelhudo'. Responde: Onde morava? / De que materiais era feita a coelheira? / O que fez a abelha?")
            )
        ),
        Ficha(
            30, "Ficha 30: Dígrafo CH", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa com cha, che, chi, cho, chu: chapéu, cheque, chocolate, mocho, mochila, guarda-chuva, chupeta, borracha, chinelos, chaminé"),
                FichaBloco.Instrucao("Legenda as imagens com as palavras acima: chapéu 🎩, cheque 🧾, chocolate 🍫, mocho 🦉, mochila 🎒, guarda-chuva ☂️, chupeta 🍼, borracha 🧼, chinelos 🩴, chaminé 🏭"),
                FichaBloco.Instrucao("Completa com ch, lh ou nh: chave, ovelha, bolacha, bilhete, girafote, rocha, baunilha, piteiro, minhoca, vezote, bicarada, andorinha"),
                FichaBloco.Instrucao("Ordena e escreve: 'Bebe A um Tucha chá chinesa. chávena numa' / 'acompanha Ela o bolacha chocolate. com de uma chá'")
            )
        ),
        Ficha(
            31, "Ficha 31: br, cr, dr, fr, gr, pr, tr, vr", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Completa: cabra, Escola, grito, abraço, treze, tigre, zebra, livro, frigorífico, presente, preto, tranquedo, trigo, abrigo, trino, avião, dragão, aterrado, gravata, fruta"),
                FichaBloco.Instrucao("Rodeia a palavra certa: magro/mago, fraca/faca, baço/braço, frita/fita, dama/drama"),
                FichaBloco.Instrucao("Copia: 'Ontem, sonhei com um cavalo de crina branca.' / 'Fiquei muito alegre com aquilo que sonhei.' / 'A Estrela adora o relinchar dos cavalos.'")
            )
        ),
        Ficha(
            32, "Ficha 32: bl, cl, dl, fl, pl, tl", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Divide em sílabas: atlas, ciclismo, atletismo, blusão, pluma, flecha"),
                FichaBloco.Instrucao("Rodeia bl, cl, fl, pl, tl e copia: atleta, planta, flauta, blusa, ciclista"),
                FichaBloco.Instrucao("Completa: 'A [blusa] da tia Clara é de seda amarela.' / 'A bicicleta é do [ciclista].' / 'O [atleta] saltou muito alto.' / 'O Pedro toca melodias na sua [flauta].' / 'A Lígia plantou uma linda [planta].'"),
                FichaBloco.Instrucao("Lê 'O Plutão': é o cão da Clarinha. Estava a cuidar do jardim quando o Plutão apareceu rápido como uma flecha, pisou as flores e sujou a blusa dela de terra. Responde: Como se chama o cão? / Onde estava a Clarinha? / O que pisou o Plutão?")
            )
        ),
        Ficha(
            33, "Ficha 33: Compreensão da leitura - O lobo e os sete cabritinhos", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Lê o texto: Era uma vez uma cabra com sete cabritinhos. Foi à floresta e avisou-os do lobo. O lobo bateu à porta fingindo ser a mãe, mas os cabritinhos perceberam pela voz. O lobo pôs farinha nas patas e voltou; os cabritinhos abriram ao ver as patas brancas. Ficaram apavorados; só o mais pequeno escapou, escondido na caixa do relógio."),
                FichaBloco.Instrucao("Perguntas: Quantos filhotes tinha a cabra? / Onde foi a cabrinha? / Quem bateu à porta? / Onde foi o lobo buscar a farinha? / Onde se escondeu o irmão mais pequeno?")
            )
        ),
        Ficha(
            34, "Ficha 34: Compreensão da leitura - A casinha de chocolate", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Lê o texto: No bosque vivia um lenhador com dois filhos, Hansel e Gretel. Perderam-se no bosque e encontraram uma casa de chocolate. Uma bruxa má prendeu Hansel numa gaiola e queria comê-lo. Gretel empurrou a bruxa para o forno, libertou o irmão e fugiram, encontrando o pai."),
                FichaBloco.Instrucao("Perguntas: O pai era lavrador/lenhador/agricultor? Os irmãos brincavam no parque/bosque/quarto? A bruxa queria comer Gretel/Hansel/o lenhador?")
            )
        ),
        Ficha(
            35, "Ficha 35: Escrita – texto descritivo", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Observa o emoji do Sebastião: 🧒 (menino com camisa aos quadrados, calças de ganga, mochila azul). Assinala: é gordo/alto/baixo; cabelos ruivos/pretos/loiros; olhos verdes/azuis/pretos; boca grande/pequena/redonda."),
                FichaBloco.EspacoDesenho(150),
                FichaBloco.Instrucao("Depois, escreve um texto a descrever o Sebastião.")
            )
        ),
        Ficha(
            36, "Ficha 36: Escrita – texto narrativo", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Observa as imagens: uma fada, uma princesa, um peixe, o sol."),
                FichaBloco.Instrucao("Escreve uma história com título, seguindo o guião: 'Era uma vez uma princesa ___ que ___. Então apareceu ___. De repente ___. Finalmente ___.'"),
                FichaBloco.EspacoDesenho(150)
            )
        ),
        Ficha(
            37, "TPC Natal: Português", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Escreve em letra manuscrita: a, e, i, o, u, p, t, l, d, m, v"),
                FichaBloco.Instrucao("Escreve os ditongos que ouves nos balões de fala."),
                FichaBloco.Instrucao("Rodeia os ditongos e ilustra: pão, põe, pai, pau, leite, oito"),
                FichaBloco.EspacoDesenho(100),
                FichaBloco.Instrucao("Escreve as palavras: pote, apito, pato, tia"),
                FichaBloco.Instrucao("Legenda: lupa, pão, toalha, lua, mala, meia, mota, mola")
            )
        ),
        Ficha(
            38, "TPC Carnaval: Português", "Português", "#4F46E5",
            listOf(
                FichaBloco.Instrucao("Rodeia a palavra certa e copia: vela/veia/vala, avô/ave/avião, luva/lume/lula, mota/mala/mola, ovo/uva/Eva, cola/couve/capa, cuco/Caio/Cátia, dedo/dia/data, nota/neve/navio, pato/Paulo/pateta, roda/rato/ramo, peru/pera/para"),
                FichaBloco.Instrucao("Lê, copia e ilustra: vaca, baleia, leque, amarelo"),
                FichaBloco.EspacoDesenho(150)
            )
        )
    )
}
