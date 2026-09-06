package com.example.ui.screens

import com.example.Ficha
import com.example.FichaBloco

object FichasEstudoMeioData {
    val fichasEstudoMeio1Ano = listOf(
        Ficha(
            1, "Quem sou eu?", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Preenche com os teus dados:"),
                FichaBloco.TabelaDuasColunas(
                    listOf("O meu nome:", "Tenho ____ anos", "Nasci no dia:", "Cor favorita:"),
                    listOf("Escreve aqui", "Número", "Data", "Cor")
                ),
                FichaBloco.Instrucao("Desenha-te a ti próprio!"),
                FichaBloco.EspacoDesenho(150, "autorretrato")
            )
        ),
        Ficha(
            2, "O meu Corpo", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Partes do corpo humano:"),
                FichaBloco.CaixinhasCompletar(listOf("C_beça", "M_o", "P_rn_", "P_")),
                FichaBloco.Instrucao("Quantos tens?"),
                FichaBloco.TabelaDuasColunas(
                    listOf("Olhos", "Orelhas", "Nariz", "Mãos"),
                    listOf("_", "_", "_", "_")
                )
            )
        ),
        Ficha(
            3, "Os Cinco Sentidos", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Liga o órgão ao sentido:"),
                FichaBloco.TabelaDuasColunas(
                    listOf("Olhos", "Ouvidos", "Nariz", "Língua", "Mãos/Pele"),
                    listOf("Visão", "Audição", "Olfato", "Paladar", "Tato")
                ),
                FichaBloco.Instrucao("Qual é o sentido que usas para ouvir música?"),
                FichaBloco.CaixaPalavras(listOf("Visão", "Audição", "Tato"))
            )
        ),
        Ficha(
            4, "A minha Família", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("A minha família é composta por:"),
                FichaBloco.CaixaPalavras(listOf("Pai", "Mãe", "Irmão", "Irmã", "Avô", "Avó")),
                FichaBloco.Instrucao("Desenha a tua família:"),
                FichaBloco.EspacoDesenho(200, "familia")
            )
        ),
        Ficha(
            5, "Higiene e Saúde", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Bons hábitos:"),
                FichaBloco.TabelaDuasColunas(
                    listOf("Lavar os dentes", "Tomar banho", "Lavar as mãos", "Dormir bem"),
                    listOf("Depois de comer", "Todos os dias", "Antes de comer", "10 horas")
                ),
                FichaBloco.Instrucao("Escreve um hábito de higiene:"),
                FichaBloco.CaixinhasCompletar(listOf("Hábito de higiene: _"))
            )
        ),
        Ficha(
            6, "A minha Escola", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Sobre a minha escola:"),
                FichaBloco.TabelaDuasColunas(
                    listOf("Nome da Escola:", "Nome do Professor:", "A minha turma:"),
                    listOf("Escreve aqui", "Nome", "Turma")
                ),
                FichaBloco.Instrucao("O que levas na mochila?"),
                FichaBloco.CaixaPalavras(listOf("Lápis", "Caderno", "Livro", "Lanche", "Brinquedo"))
            )
        ),
        Ficha(
            7, "Ficha 7: TPC Natal", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Completa o teu autorretrato: desenha a tua cara na moldura abaixo:"),
                FichaBloco.EspacoDesenho(160, "autorretrato"),
                FichaBloco.Instrucao("Completa a frase com os teus dados reais:"),
                FichaBloco.CaixinhasCompletar(listOf("Eu chamo-me _", "Tenho _ anos", "Sou do sexo _")),
                FichaBloco.Instrucao("Segue os caminhos e descobre de que gosta cada criança nos tempos livres:"),
                FichaBloco.CaixinhasCompletar(listOf("Damião gosta de (💃/📖/🚲/🏃/⚽): _", "Dalila gosta de (💃/📖/🚲/🏃/⚽): _", "Paulo gosta de (💃/📖/🚲/🏃/⚽): _", "Odete gosta de (💃/📖/🚲/🏃/⚽): _", "Ema gosta de (💃/📖/🚲/🏃/⚽): _")),
                FichaBloco.Instrucao("Qual é a tua atividade preferida das cinco? Quem a pratica?"),
                FichaBloco.CaixinhasCompletar(listOf("A minha atividade preferida é praticada por: _")),
                FichaBloco.Instrucao("Escreve SIM para os materiais que consegues encontrar numa sala de aula típica:"),
                FichaBloco.CaixinhasCompletar(listOf("Livro 📖: _", "Cola 🧴: _", "Tesoura ✂️: _", "Caderno 📓: _", "Computador 💻: _", "Lápis de cor 🖍️: _", "Lupa 🔍: _", "Agrafador 📎: _")),
                FichaBloco.Instrucao("Completa a árvore genealógica da Lídia com os nomes sugeridos (Amadeu, Amélia, Paulo, Odete, Adão):"),
                FichaBloco.CaixinhasCompletar(listOf("Avô 👴: _", "Avó 👵: _", "Pai 👨: _", "Mãe 👩: _", "Irmão 👦: _")),
                FichaBloco.Instrucao("O familiar que faz anos tem um presente (🎁). Qual o seu grau de parentesco?"),
                FichaBloco.CaixinhasCompletar(listOf("Grau de parentesco (🎁): _")),
                FichaBloco.Instrucao("Pinta e completa as silhuetas dos teus familiares:") ,
                FichaBloco.EspacoDesenho(150, "familia")
            )
        ),
        Ficha(
            8, "Ficha 8: TPC Carnaval", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Planta da Casa com Dois Andares. Observa e identifica cada objeto/veículo:"),
                FichaBloco.CaixinhasCompletar(listOf("O objeto que está em baixo do relógio de parede: _", "O que está entre o frigorífico e o fogão: _", "O que está atrás do carro: _", "O que está à esquerda da cama: _")),
                FichaBloco.CaixinhasCompletar(listOf("O que está em cima do tapete da sala: _", "O que está à direita do sofá: _", "O que está ao lado da banheira: _", "O veículo que está dentro da garagem: _")),
                FichaBloco.Instrucao("No quadro abaixo, desenha uma bicicleta fora da casa, à frente da porta:"),
                FichaBloco.EspacoDesenho(160, "casa_porta"),
                FichaBloco.Instrucao("Associa os números (1 a 5) às respetivas divisões descritas abaixo:"),
                FichaBloco.CaixinhasCompletar(listOf("Cozinha (Preparar refeições) 🍳: _", "Casa de banho (Higiene) 🛁: _", "Quarto (Dormir) 🛏️: _", "Garagem (Arrumar carro) 🚗: _", "Sala (Descansar) 🛋️: _")),
                FichaBloco.Instrucao("Indica em que parte do corpo (cabeça, pés, tronco, braço) usamos cada acessório:"),
                FichaBloco.CaixinhasCompletar(listOf("Boné 🧢: _", "Sapatos 👞: _", "Casaco 🧥: _", "Relógio ⌚: _")),
                FichaBloco.Instrucao("Identifica a etapa da vida (1-Infância, 2-Adolescência, 3-Idade adulta, 4-Terceira idade):"),
                FichaBloco.CaixinhasCompletar(listOf("Bebé/Criança 🧒: _", "Jovem/Adolescente 🧑: _", "Pais 👨👩: _", "Avós 👴👵: _")),
                FichaBloco.Instrucao("Desenha-te a ti próprio no espaço em branco para fechar a roda de crianças:"),
                FichaBloco.EspacoDesenho(160, "roda_crianças")
            )
        ),
        Ficha(
            9, "Ficha 9: TPC Páscoa", "Estudo do Meio", "#F59E0B",
            listOf(
                FichaBloco.Instrucao("Refeição equilibrada! Classifica os alimentos no prato saudável (Proteína, Hidrato, Vegetal/Fruta, Bebida):"),
                FichaBloco.CaixinhasCompletar(listOf("Bebida saudável por excelência 💧: _", "Bife 🥩 / Peixe 🐟 / Frango 🍗: _", "Massa 🍝 / Arroz 🍚 / Pão 🍞: _", "Legumes 🥦 / Cenoura 🥕 / Morango 🍓: _")),
                FichaBloco.Instrucao("Segurança: escreve V para SIM (seguro) ou X para NÃO (perigoso):"),
                FichaBloco.CaixinhasCompletar(listOf("Atravessar na passadeira com adulto 🚸: _", "Andar de bicicleta sem cuidado perto de carros 🚴🚗: _", "Mexer em armário de produtos de limpeza 🧴: _", "Mexer em medicamentos sozinho 💊: _")),
                FichaBloco.Instrucao("Após usar a casa de banho, puxamos o autoclismo. Desenha o que fazemos a seguir:"),
                FichaBloco.EspacoDesenho(150, "lavar_maos"),
                FichaBloco.Instrucao("Para fazermos sumo de laranja espremido, desenha o que aconteceu antes de espremer:"),
                FichaBloco.EspacoDesenho(150, "antes_sumo"),
                FichaBloco.Instrucao("Identifica o período do dia correspondente à atividade da ave (amanhecer 🌅, sol alto ☀️, noite estrelada 🌙):"),
                FichaBloco.CaixinhasCompletar(listOf("Ave a dormir no ninho 🌙: _", "Ave a acordar ao amanhecer 🌅: _", "Ave no arbusto com sol forte ☀️: _")),
                FichaBloco.Instrucao("O que gostarias de ser quando fores grande? Desenha a tua profissão de sonho no quadro abaixo:"),
                FichaBloco.EspacoDesenho(160, "profissao"),
                FichaBloco.Instrucao("Ajuda as crianças a encontrar os ovos de Páscoa 🥚 no centro do labirinto. Traça o caminho com o dedo:"),
                FichaBloco.EspacoDesenho(200, "labirinto_pascoa")
            )
        )
    )
}
