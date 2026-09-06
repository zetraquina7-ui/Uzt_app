package com.example.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*

// -------------------- MODELOS E ENUMS --------------------

enum class ModoJogo(val etiqueta: String, val icone: String) {
    CLASSICO("Clássico", "🫧"),
    LETRAS("Letras", "🔤"),
    NUMEROS("Números", "🔢")
}

enum class AmbientTheme(
    val nome: String,
    val icone: String,
    val corFundoTop: Color,
    val corFundoBottom: Color,
    val corXadrezEscuro: Color,
    val corXadrezClaro: Color,
    val corMetal: Color,
    val corMetalBorda: Color
) {
    GALAXIA(
        nome = "Galáxia Mágica",
        icone = "🔮",
        corFundoTop = Color(0xFF1E0B36),
        corFundoBottom = Color(0xFF0F061E),
        corXadrezEscuro = Color(0xFF3B0B59),
        corXadrezClaro = Color(0xFF521378),
        corMetal = Color(0xFF2C6D6A),
        corMetalBorda = Color(0xFF4EE3D9)
    ),
    OCEANO(
        nome = "Oceano Bubbly",
        icone = "🌊",
        corFundoTop = Color(0xFF0A2E5C),
        corFundoBottom = Color(0xFF04152E),
        corXadrezEscuro = Color(0xFF0D3B66),
        corXadrezClaro = Color(0xFF145086),
        corMetal = Color(0xFF00897B),
        corMetalBorda = Color(0xFF80CBC4)
    ),
    FLORESTA(
        nome = "Floresta Mágica",
        icone = "🌲",
        corFundoTop = Color(0xFF1B4332),
        corFundoBottom = Color(0xFF081C15),
        corXadrezEscuro = Color(0xFF2D6A4F),
        corXadrezClaro = Color(0xFF40916C),
        corMetal = Color(0xFF52B788),
        corMetalBorda = Color(0xFFB7E4C7)
    )
}

data class TipoBolha(val cor: Color, val corEscura: Color, val texto: String? = null)

data class BolhaGrelha(val tipo: TipoBolha, val linha: Int, val coluna: Int)

data class BolhaVoo(
    val x: Float,
    val y: Float,
    val dx: Float,
    val dy: Float,
    val tipo: TipoBolha
)

data class ExplosionParticle(
    val id: Long = System.currentTimeMillis() + (0..10000).random(),
    var x: Float,
    var y: Float,
    val vx: Float,
    var vy: Float,
    val color: Color,
    val size: Float,
    var alpha: Float = 1f
)

data class AmbientBackgroundBubble(
    var x: Float,
    var y: Float,
    val radius: Float,
    val vy: Float,
    val color: Color,
    var alpha: Float,
    val pulseSpeed: Float = 0.02f
)

data class FloatingScoreText(
    val id: Long = System.currentTimeMillis() + (0..10000).random(),
    val text: String,
    val color: Color,
    var x: Float,
    var y: Float,
    var alpha: Float = 1f,
    var scale: Float = 0.6f,
    val isCombo: Boolean = false
)

// -------------------- CORES ESTILO ARCADES --------------------

private val CorVermelha = TipoBolha(Color(0xFFFF1A1A), Color(0xFF990000))
private val CorAmarela = TipoBolha(Color(0xFFFFD700), Color(0xFF998000))
private val CorAzul = TipoBolha(Color(0xFF0066FF), Color(0xFF003399))
private val CorVerde = TipoBolha(Color(0xFF00CC44), Color(0xFF006622))
private val CorRoxa = TipoBolha(Color(0xFFA24CE0), Color(0xFF5C1C8C))
private val CorLaranja = TipoBolha(Color(0xFFFF8C00), Color(0xFFB36200))

private fun coresBase() = listOf(CorVermelha, CorAmarela, CorAzul, CorVerde, CorRoxa, CorLaranja)

private val letras = ('A'..'Z').map { it.toString() }
private val numeros = (1..9).map { it.toString() }

private fun gerarTipos(modo: ModoJogo, quantidade: Int): List<TipoBolha> {
    val base = coresBase().take(quantidade.coerceIn(3, 6))
    return when (modo) {
        ModoJogo.CLASSICO -> base
        ModoJogo.LETRAS -> base.mapIndexed { i, t -> t.copy(texto = letras[i % letras.size]) }
        ModoJogo.NUMEROS -> base.mapIndexed { i, t -> t.copy(texto = numeros[i % numeros.size]) }
    }
}

// -------------------- CONFIGURAÇÃO --------------------

private const val COLUNAS = 8
private const val LINHAS_INICIAIS = 4
private const val LINHAS_MAX = 14

// -------------------- ECRÃ PRINCIPAL --------------------

@Composable
fun BubbleShooterScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    var modo by remember { mutableStateOf(ModoJogo.CLASSICO) }
    var tema by remember { mutableStateOf(AmbientTheme.GALAXIA) }
    var pontos by remember { mutableIntStateOf(0) }
    var ronda by remember { mutableIntStateOf(1) }
    var comboCount by remember { mutableIntStateOf(0) }
    var fimDeJogo by remember { mutableStateOf(false) }
    var mostrarVitoria by remember { mutableStateOf(false) }
    var somAmbienteAtivo by remember { mutableStateOf(true) }

    var raio by remember { mutableFloatStateOf(0f) }
    var larguraCanvas by remember { mutableFloatStateOf(0f) }
    var alturaCanvas by remember { mutableFloatStateOf(0f) }

    val frameThickness = 24f
    val gridMargin = 16f
    val offsetBorda = frameThickness + gridMargin

    val grelha = remember { mutableStateMapOf<Pair<Int, Int>, BolhaGrelha>() }
    val explosionParticles = remember { mutableStateListOf<ExplosionParticle>() }
    val ambientBubbles = remember { mutableStateListOf<AmbientBackgroundBubble>() }
    val floatingTexts = remember { mutableStateListOf<FloatingScoreText>() }
    var tiposAtivos by remember { mutableStateOf(gerarTipos(modo, 4)) }

    var bolhaAtual by remember { mutableStateOf(tiposAtivos.random()) }
    var proximaBolha by remember { mutableStateOf(tiposAtivos.random()) }

    var bolhaEmVoo by remember { mutableStateOf<BolhaVoo?>(null) }
    var anguloMira by remember { mutableFloatStateOf(-PI.toFloat() / 2f) }
    var podeDisparar by remember { mutableStateOf(true) }
    var mascotMessage by remember { mutableStateOf("MIRA COM ATENÇÃO! 🎯") }

    // Gestão do Som Ambiente (Música de Fundo)
    DisposableEffect(somAmbienteAtivo, fimDeJogo) {
        if (somAmbienteAtivo && !fimDeJogo) {
            mainViewModel?.startAmbientMusic(0.28f)
        } else {
            mainViewModel?.stopAmbientMusic()
        }
        onDispose {
            mainViewModel?.stopAmbientMusic()
        }
    }

    // Inicializar Bolhas de Fundo Ambiente
    LaunchedEffect(larguraCanvas, alturaCanvas) {
        if (larguraCanvas > 0f && ambientBubbles.isEmpty()) {
            repeat(15) {
                ambientBubbles.add(
                    AmbientBackgroundBubble(
                        x = kotlin.random.Random.nextFloat() * larguraCanvas,
                        y = kotlin.random.Random.nextFloat() * alturaCanvas,
                        radius = kotlin.random.Random.nextFloat() * 18f + 8f,
                        vy = -(kotlin.random.Random.nextFloat() * 0.8f + 0.3f),
                        color = listOf(Color.Cyan, Color.Magenta, Color.Yellow, Color.White, Color(0xFF80CBC4)).random(),
                        alpha = kotlin.random.Random.nextFloat() * 0.35f + 0.1f
                    )
                )
            }
        }
    }

    fun colunasNaLinha(linha: Int) = if (linha % 2 == 0) COLUNAS else COLUNAS - 1

    fun posicaoCentro(linha: Int, coluna: Int, r: Float): Offset {
        val isEven = linha % 2 == 0
        val startX = if (isEven) offsetBorda + r else offsetBorda + 2f * r
        val x = startX + coluna * (r * 2f)
        val y = offsetBorda + r + linha * (r * 1.732f)
        return Offset(x, y)
    }

    fun iniciarGrelha() {
        grelha.clear()
        comboCount = 0
        val numCores = (3 + (ronda - 1) / 2).coerceIn(3, 5)
        tiposAtivos = gerarTipos(modo, numCores)
        val linhasIniciaisGrelha = (LINHAS_INICIAIS + (ronda - 1)).coerceIn(4, 7)
        for (linha in 0 until linhasIniciaisGrelha) {
            val cols = colunasNaLinha(linha)
            for (coluna in 0 until cols) {
                val tipo = tiposAtivos.random()
                grelha[linha to coluna] = BolhaGrelha(tipo, linha, coluna)
            }
        }
        bolhaAtual = tiposAtivos.random()
        proximaBolha = tiposAtivos.random()
        mascotMessage = "VAMOS A ISSO! 🫧"
    }

    LaunchedEffect(raio) {
        if (raio > 0f && grelha.isEmpty()) {
            iniciarGrelha()
        }
    }

    fun vizinhos(linha: Int, coluna: Int): List<Pair<Int, Int>> {
        val par = linha % 2 == 0
        return listOf(
            linha to (coluna - 1), linha to (coluna + 1),
            (linha - 1) to (if (par) coluna - 1 else coluna),
            (linha - 1) to (if (par) coluna else coluna + 1),
            (linha + 1) to (if (par) coluna - 1 else coluna),
            (linha + 1) to (if (par) coluna else coluna + 1)
        )
    }

    fun mesmoTipo(a: TipoBolha, b: TipoBolha): Boolean {
        return if (a.texto != null || b.texto != null) a.texto == b.texto else a.cor == b.cor
    }

    fun encontrarGrupo(inicioLinha: Int, inicioColuna: Int): Set<Pair<Int, Int>> {
        val alvo = grelha[inicioLinha to inicioColuna]?.tipo ?: return emptySet()
        val visitados = mutableSetOf<Pair<Int, Int>>()
        val pilha = ArrayDeque<Pair<Int, Int>>()
        pilha.add(inicioLinha to inicioColuna)
        while (pilha.isNotEmpty()) {
            val atual = pilha.removeLast()
            if (atual in visitados) continue
            val bolha = grelha[atual] ?: continue
            if (!mesmoTipo(bolha.tipo, alvo)) continue
            visitados.add(atual)
            vizinhos(atual.first, atual.second).forEach { viz ->
                if (viz !in visitados && grelha.containsKey(viz)) pilha.add(viz)
            }
        }
        return visitados
    }

    fun spawnExplosion(center: Offset, tipo: TipoBolha) {
        val cores = listOf(tipo.cor, tipo.corEscura, Color.Yellow, Color.Cyan, Color.Magenta, Color.White)
        repeat(14) {
            val ang = kotlin.random.Random.nextFloat() * (2 * PI.toFloat())
            val speed = kotlin.random.Random.nextFloat() * 7f + 2f
            explosionParticles.add(
                ExplosionParticle(
                    x = center.x,
                    y = center.y,
                    vx = cos(ang) * speed,
                    vy = sin(ang) * speed,
                    color = cores.random(),
                    size = kotlin.random.Random.nextFloat() * 5f + 2.5f
                )
            )
        }
    }

    fun spawnFloatingText(text: String, x: Float, y: Float, color: Color, isCombo: Boolean = false) {
        floatingTexts.add(
            FloatingScoreText(
                text = text,
                color = color,
                x = x,
                y = y,
                isCombo = isCombo
            )
        )
    }

    fun removerFlutuantes(): Int {
        val ligadas = mutableSetOf<Pair<Int, Int>>()
        val pilha = ArrayDeque<Pair<Int, Int>>()
        grelha.keys.filter { it.first == 0 }.forEach { pilha.add(it) }
        while (pilha.isNotEmpty()) {
            val atual = pilha.removeLast()
            if (atual in ligadas) continue
            ligadas.add(atual)
            vizinhos(atual.first, atual.second).forEach { viz ->
                if (viz !in ligadas && grelha.containsKey(viz)) pilha.add(viz)
            }
        }
        val soltas = grelha.keys.filter { it !in ligadas }.toList()
        soltas.forEach { pos ->
            grelha[pos]?.let { b ->
                val centro = posicaoCentro(b.linha, b.coluna, raio)
                spawnExplosion(centro, b.tipo)
            }
            grelha.remove(pos)
        }
        return soltas.size
    }

    fun disparar() {
        if (!podeDisparar || bolhaEmVoo != null || raio <= 0f) return
        podeDisparar = false
        mainViewModel?.playClickSound()
        val velocidade = 22f + (ronda - 1) * 2.5f
        val dx = cos(anguloMira) * velocidade
        val dy = sin(anguloMira) * velocidade
        val origemX = larguraCanvas / 2f
        val origemY = alturaCanvas - offsetBorda - raio * 3.0f

        bolhaEmVoo = BolhaVoo(
            x = origemX,
            y = origemY,
            dx = dx,
            dy = dy,
            tipo = bolhaAtual
        )
    }

    fun encaixarNaGrelha(voo: BolhaVoo) {
        var melhorDist = Float.MAX_VALUE
        var melhorLinha = 0
        var melhorColuna = 0
        for (linha in 0..LINHAS_MAX) {
            val cols = colunasNaLinha(linha)
            for (coluna in 0 until cols) {
                if (grelha.containsKey(linha to coluna)) continue
                val centro = posicaoCentro(linha, coluna, raio)
                val d = hypot(centro.x - voo.x, centro.y - voo.y)
                if (d < melhorDist) {
                    melhorDist = d
                    melhorLinha = linha
                    melhorColuna = coluna
                }
            }
        }
        grelha[melhorLinha to melhorColuna] = BolhaGrelha(voo.tipo, melhorLinha, melhorColuna)
        val impactCenter = posicaoCentro(melhorLinha, melhorColuna, raio)

        val grupo = encontrarGrupo(melhorLinha, melhorColuna)
        if (grupo.size >= 3) {
            comboCount++
            val isBigCombo = grupo.size >= 5 || comboCount >= 2
            if (isBigCombo) {
                mainViewModel?.playComboSound()
            } else {
                mainViewModel?.playBubbleSound()
            }

            grupo.forEach { pos ->
                grelha[pos]?.let { b ->
                    val centro = posicaoCentro(b.linha, b.coluna, raio)
                    spawnExplosion(centro, b.tipo)
                }
                grelha.remove(pos)
            }
            val earnedPoints = (grupo.size * 30) * comboCount
            pontos += earnedPoints
            mainViewModel?.addStars(grupo.size)

            val textLabel = if (comboCount >= 2) "🔥 COMBO x$comboCount! +$earnedPoints" else "+$earnedPoints"
            spawnFloatingText(textLabel, impactCenter.x, impactCenter.y - 10f, Color(0xFFFFD700), isCombo = isBigCombo)

            mascotMessage = when {
                comboCount >= 3 -> "INCRÍVEL! 🔥"
                grupo.size >= 5 -> "SENSACIONAL! ⭐"
                else -> "BOA JOGADA! 🎯"
            }

            val extra = removerFlutuantes()
            if (extra > 0) {
                mainViewModel?.playStarSound()
                val extraPoints = extra * 50
                pontos += extraPoints
                spawnFloatingText("🌟 SOLTAS +$extraPoints", impactCenter.x, impactCenter.y + 30f, Color(0xFF00E676), isCombo = true)
            }
            mainViewModel?.recordActivityCompletion("Jogos", "Puzzle Bubble Classic - $pontos Pontos")
        } else {
            comboCount = 0
            mascotMessage = "CONTINUA! 🫧"
        }

        if (grelha.isEmpty()) {
            mostrarVitoria = true
            mascotMessage = "GANHASTE! 🎉"
            mainViewModel?.playVictorySound()
            scope.launch {
                delay(2400)
                mostrarVitoria = false
                ronda += 1
                iniciarGrelha()
            }
        }

        if (grelha.keys.any { it.first >= LINHAS_MAX - 3 }) {
            fimDeJogo = true
            mascotMessage = "OH NÃO! 😅"
            mainViewModel?.playErrorSound()
        }

        bolhaAtual = proximaBolha
        proximaBolha = tiposAtivos.random()
        podeDisparar = true
    }

    // Loop de Animação de Partículas e Textos Flutuantes
    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            // Atualizar partículas de explosão
            if (explosionParticles.isNotEmpty()) {
                val iterator = explosionParticles.iterator()
                while (iterator.hasNext()) {
                    val p = iterator.next()
                    p.x += p.vx
                    p.y += p.vy
                    p.vy += 0.22f
                    p.alpha -= 0.035f
                    if (p.alpha <= 0f) {
                        iterator.remove()
                    }
                }
            }
            // Atualizar bolhas ambiente no fundo
            if (ambientBubbles.isNotEmpty()) {
                ambientBubbles.forEach { b ->
                    b.y += b.vy
                    if (b.y < -30f) {
                        b.y = alturaCanvas + 30f
                        b.x = kotlin.random.Random.nextFloat() * larguraCanvas
                    }
                }
            }
            // Atualizar textos flutuantes de pontos
            if (floatingTexts.isNotEmpty()) {
                val iterator = floatingTexts.iterator()
                while (iterator.hasNext()) {
                    val t = iterator.next()
                    t.y -= 1.8f
                    t.alpha -= 0.025f
                    t.scale = (t.scale + 0.05f).coerceAtMost(1.2f)
                    if (t.alpha <= 0f) {
                        iterator.remove()
                    }
                }
            }
        }
    }

    // Loop do Voo da Bolha Disparada
    LaunchedEffect(bolhaEmVoo != null) {
        if (bolhaEmVoo == null) return@LaunchedEffect
        while (bolhaEmVoo != null) {
            delay(16)
            val currentVoo = bolhaEmVoo ?: break
            var novoX = currentVoo.x + currentVoo.dx
            var novoY = currentVoo.y + currentVoo.dy
            var novoDx = currentVoo.dx
            var novoDy = currentVoo.dy

            val limiteEsquerdo = offsetBorda + raio
            val limiteDireito = larguraCanvas - offsetBorda - raio
            val limiteSuperior = offsetBorda + raio

            // Rebater nas paredes laterais com som de ricochete
            if (novoX <= limiteEsquerdo) {
                novoX = limiteEsquerdo
                novoDx = abs(novoDx)
                mainViewModel?.playBounceSound()
            }
            if (novoX >= limiteDireito) {
                novoX = limiteDireito
                novoDx = -abs(novoDx)
                mainViewModel?.playBounceSound()
            }

            // Encaixar no topo da moldura
            if (novoY <= limiteSuperior) {
                val vooFinal = currentVoo.copy(x = novoX, y = novoY, dx = novoDx, dy = novoDy)
                encaixarNaGrelha(vooFinal)
                bolhaEmVoo = null
                break
            }

            // Colisão com bolhas existentes
            var colidiu = false
            for ((_, b) in grelha) {
                val centro = posicaoCentro(b.linha, b.coluna, raio)
                if (hypot(centro.x - novoX, centro.y - novoY) < raio * 1.85f) {
                    colidiu = true
                    break
                }
            }
            if (colidiu) {
                val vooFinal = currentVoo.copy(x = novoX, y = novoY, dx = novoDx, dy = novoDy)
                encaixarNaGrelha(vooFinal)
                bolhaEmVoo = null
                break
            }

            bolhaEmVoo = currentVoo.copy(x = novoX, y = novoY, dx = novoDx, dy = novoDy)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. Topo (Barra de Controlo do Jogo & Som)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botão de Voltar
                Button(
                    onClick = {
                        mainViewModel?.playClickSound()
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Voltar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                // Alternador de Som Ambiente (🔊/🔇)
                IconButton(
                    onClick = {
                        somAmbienteAtivo = !somAmbienteAtivo
                        mainViewModel?.playClickSound()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            if (somAmbienteAtivo) Color(0xFF00E676).copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
                            CircleShape
                        )
                ) {
                    Text(
                        text = if (somAmbienteAtivo) "🎵" else "🔇",
                        fontSize = 16.sp
                    )
                }

                // Selector de Tema Rápido
                IconButton(
                    onClick = {
                        val proxTema = when (tema) {
                            AmbientTheme.GALAXIA -> AmbientTheme.OCEANO
                            AmbientTheme.OCEANO -> AmbientTheme.FLORESTA
                            AmbientTheme.FLORESTA -> AmbientTheme.GALAXIA
                        }
                        tema = proxTema
                        mainViewModel?.playClickSound()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Text(tema.icone, fontSize = 16.sp)
                }

                // Placar de Pontuação Global
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF0F172A),
                    border = BorderStroke(1.5.dp, Color(0xFFFFD700))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("⭐", fontSize = 12.sp)
                        Text(
                            text = "$pontos PTS",
                            color = Color(0xFFFFD700),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Ronda
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF334155)
                ) {
                    Text(
                        "R${ronda.toString().padStart(2, '0')}",
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // 2. Tabuleiro do Jogo (Canvas Envolvente)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                val origemX = larguraCanvas / 2f
                                val origemY = alturaCanvas - offsetBorda - raio * 3.0f
                                anguloMira = atan2(change.position.y - origemY, change.position.x - origemX)
                                    .coerceMiraVertical()
                            }
                        }
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val origemX = larguraCanvas / 2f
                                val origemY = alturaCanvas - offsetBorda - raio * 3.0f
                                anguloMira = atan2(offset.y - origemY, offset.x - origemX)
                                    .coerceMiraVertical()
                                disparar()
                            }
                        }
                ) {
                    larguraCanvas = size.width
                    alturaCanvas = size.height
                    val larguraUtil = size.width - (offsetBorda * 2f)
                    raio = larguraUtil / (COLUNAS * 2f)

                    // 1. Fundo Xadrez de Acordo com o Tema
                    desenharFundoXadrezTema(this, offsetBorda, tema)

                    // 2. Bolhas Flutuantes de Fundo (Ambiente 3D)
                    ambientBubbles.forEach { b ->
                        drawCircle(
                            color = b.color.copy(alpha = b.alpha),
                            radius = b.radius,
                            center = Offset(b.x, b.y)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = b.alpha * 0.7f),
                            radius = b.radius * 0.3f,
                            center = Offset(b.x - b.radius * 0.3f, b.y - b.radius * 0.3f)
                        )
                    }

                    // 3. Aura de Luz Atrás do Disparador
                    val origemX = larguraCanvas / 2f
                    val origemY = alturaCanvas - offsetBorda - raio * 3.0f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFD700).copy(alpha = 0.35f), Color.Transparent),
                            center = Offset(origemX, origemY),
                            radius = raio * 4.5f
                        ),
                        radius = raio * 4.5f,
                        center = Offset(origemX, origemY)
                    )

                    // 4. Guia de Trajetória / Mira com Reflexo de Parede
                    desenharGuiaMiraLaser(this, Offset(origemX, origemY), anguloMira, offsetBorda, raio, larguraCanvas)

                    // 5. Bolhas na Grelha
                    for ((_, b) in grelha) {
                        val centro = posicaoCentro(b.linha, b.coluna, raio)
                        desenharBolhaGlossy(this, centro, raio, b.tipo)
                    }

                    // 6. Partículas de Explosão
                    explosionParticles.forEach { p ->
                        drawCircle(
                            color = p.color.copy(alpha = p.alpha.coerceIn(0f, 1f)),
                            radius = p.size,
                            center = Offset(p.x, p.y)
                        )
                    }

                    // 7. Textos Flutuantes de Pontuação / Combos
                    floatingTexts.forEach { t ->
                        this.drawContext.canvas.nativeCanvas.apply {
                            val paint = android.graphics.Paint().apply {
                                color = if (t.isCombo) android.graphics.Color.YELLOW else android.graphics.Color.WHITE
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = (raio * 0.95f) * t.scale
                                isFakeBoldText = true
                                alpha = (t.alpha.coerceIn(0f, 1f) * 255).toInt()
                                setShadowLayer(8f, 0f, 2f, android.graphics.Color.BLACK)
                            }
                            drawText(t.text, t.x, t.y, paint)
                        }
                    }

                    // 8. Linha Limite Inferior
                    drawLine(
                        color = Color(0xFFFFD700).copy(alpha = 0.85f),
                        start = Offset(offsetBorda, alturaCanvas - offsetBorda - raio * 5.2f),
                        end = Offset(larguraCanvas - offsetBorda, alturaCanvas - offsetBorda - raio * 5.2f),
                        strokeWidth = 3f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                    )

                    // 9. Disparador Dourado & Mascote Zé Traquina
                    desenharLançadorDouradoEBoy(this, Offset(origemX, origemY), anguloMira, raio, mascotMessage)

                    // 10. Bolhas (Em Voo / Preparada)
                    bolhaEmVoo?.let { voo ->
                        desenharBolhaGlossy(this, Offset(voo.x, voo.y), raio, voo.tipo)
                    }
                    if (bolhaEmVoo == null) {
                        desenharBolhaGlossy(this, Offset(origemX, origemY), raio, bolhaAtual)
                    }

                    // 11. Moldura Metálica com Rebites
                    desenharMolduraMetalica(this, frameThickness, tema)
                }
            }

            // 3. Rodapé de Estado e Próxima Bolha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Indicador Próxima Bolha
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "PRÓXIMA:",
                        color = Color(0xFF38BDF8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Surface(
                        shape = CircleShape,
                        color = proximaBolha.corEscura,
                        border = BorderStroke(2.dp, proximaBolha.cor),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (proximaBolha.texto != null) {
                                Text(
                                    text = proximaBolha.texto!!,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            } else {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.85f),
                                    modifier = Modifier
                                        .size(7.dp)
                                        .align(Alignment.TopStart)
                                        .padding(start = 3.dp, top = 3.dp)
                                ) {}
                            }
                        }
                    }
                }

                // Dica
                Text(
                    text = "🎯 Toca ou arrasta para mirar",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                // Placar Rápido
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF0F172A),
                    border = BorderStroke(1.dp, Color(0xFFFFD700))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$pontos PTS",
                            color = Color(0xFFFFD700),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (mostrarVitoria) {
            ConfettiEffect()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 PARABÉNS! 🎉",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Limpaste a Ronda $ronda!\nA carregar próximo nível...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3B0B59),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        if (fimDeJogo) {
            EcraFimDeJogo(
                pontos = pontos,
                onJogarNovamente = {
                    mainViewModel?.playClickSound()
                    iniciarGrelha()
                    pontos = 0
                    ronda = 1
                    fimDeJogo = false
                },
                onMenu = {
                    mainViewModel?.playClickSound()
                    onBack()
                }
            )
        }
    }
}

private fun Float.coerceMiraVertical(): Float {
    return this.coerceIn(-PI.toFloat() + 0.15f, -0.15f)
}

// -------------------- DESENHO GRÁFICO E AMBIENTE --------------------

private fun desenharFundoXadrezTema(scope: DrawScope, offsetBorda: Float, tema: AmbientTheme) {
    val tamQuadrado = 44f
    var y = offsetBorda
    var i = 0
    while (y < scope.size.height - offsetBorda) {
        var x = offsetBorda
        var j = 0
        while (x < scope.size.width - offsetBorda) {
            val cor = if ((i + j) % 2 == 0) tema.corXadrezEscuro else tema.corXadrezClaro
            scope.drawRect(
                color = cor,
                topLeft = Offset(x, y),
                size = Size(
                    min(tamQuadrado, scope.size.width - offsetBorda - x),
                    min(tamQuadrado, scope.size.height - offsetBorda - y)
                )
            )
            x += tamQuadrado
            j++
        }
        y += tamQuadrado
        i++
    }
}

private fun desenharGuiaMiraLaser(
    scope: DrawScope,
    origem: Offset,
    angulo: Float,
    offsetBorda: Float,
    raio: Float,
    larguraCanvas: Float
) {
    val limiteEsquerdo = offsetBorda + raio
    val limiteDireito = larguraCanvas - offsetBorda - raio
    val limiteSuperior = offsetBorda + raio

    var curX = origem.x
    var curY = origem.y
    var curDx = cos(angulo) * 12f
    var curDy = sin(angulo) * 12f

    val pontosLinha = mutableListOf<Offset>()
    pontosLinha.add(Offset(curX, curY))

    var maxSteps = 45
    while (maxSteps > 0 && curY > limiteSuperior) {
        maxSteps--
        curX += curDx
        curY += curDy

        if (curX <= limiteEsquerdo) {
            curX = limiteEsquerdo
            curDx = abs(curDx)
            pontosLinha.add(Offset(curX, curY))
        } else if (curX >= limiteDireito) {
            curX = limiteDireito
            curDx = -abs(curDx)
            pontosLinha.add(Offset(curX, curY))
        }

        if (maxSteps % 2 == 0) {
            pontosLinha.add(Offset(curX, curY))
        }
    }

    // Desenhar pontos da mira com brilho
    pontosLinha.forEachIndexed { idx, p ->
        val alpha = (1f - (idx.toFloat() / pontosLinha.size)).coerceIn(0.2f, 1f)
        scope.drawCircle(
            color = Color(0xFFFFD700).copy(alpha = alpha),
            radius = 3.5f,
            center = p
        )
    }

    // Desenhar Anel Alvo no ponto final da trajetória
    if (pontosLinha.isNotEmpty()) {
        val targetPos = pontosLinha.last()
        scope.drawCircle(
            color = Color(0xFFFFD700).copy(alpha = 0.6f),
            radius = raio * 0.9f,
            center = targetPos,
            style = Stroke(width = 2.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f), 0f))
        )
    }
}

private fun desenharMolduraMetalica(scope: DrawScope, frameThickness: Float, tema: AmbientTheme) {
    val corMetal = tema.corMetal
    val corMetalEscuro = Color.Black.copy(alpha = 0.4f)
    val corBordaClara = tema.corMetalBorda

    // Molduras principais
    scope.drawRect(corMetal, Offset(0f, 0f), Size(scope.size.width, frameThickness))
    scope.drawRect(corMetal, Offset(0f, scope.size.height - frameThickness), Size(scope.size.width, frameThickness))
    scope.drawRect(corMetal, Offset(0f, 0f), Size(frameThickness, scope.size.height))
    scope.drawRect(corMetal, Offset(scope.size.width - frameThickness, 0f), Size(frameThickness, scope.size.height))

    // Borda interna brilhante
    scope.drawRect(
        color = corBordaClara,
        topLeft = Offset(frameThickness, frameThickness),
        size = Size(scope.size.width - frameThickness * 2, scope.size.height - frameThickness * 2),
        style = Stroke(width = 3f)
    )

    // Parafusos metálicos
    val corParafuso = Color(0xFF13302E)
    var y = 36f
    while (y < scope.size.height - 24f) {
        scope.drawCircle(corParafuso, 3.8f, Offset(frameThickness / 2f, y))
        scope.drawCircle(Color.White.copy(alpha = 0.5f), 1.5f, Offset(frameThickness / 2f - 1f, y - 1f))
        scope.drawCircle(corParafuso, 3.8f, Offset(scope.size.width - frameThickness / 2f, y))
        scope.drawCircle(Color.White.copy(alpha = 0.5f), 1.5f, Offset(scope.size.width - frameThickness / 2f - 1f, y - 1f))
        y += 56f
    }
    var x = 36f
    while (x < scope.size.width - 24f) {
        scope.drawCircle(corParafuso, 3.8f, Offset(x, frameThickness / 2f))
        scope.drawCircle(Color.White.copy(alpha = 0.5f), 1.5f, Offset(x - 1f, frameThickness / 2f - 1f))
        scope.drawCircle(corParafuso, 3.8f, Offset(x, scope.size.height - frameThickness / 2f))
        scope.drawCircle(Color.White.copy(alpha = 0.5f), 1.5f, Offset(x - 1f, scope.size.height - frameThickness / 2f - 1f))
        x += 56f
    }
}

private fun desenharBolhaGlossy(scope: DrawScope, centro: Offset, raio: Float, tipo: TipoBolha) {
    // Sombra
    scope.drawCircle(Color.Black.copy(alpha = 0.35f), raio * 0.95f, centro + Offset(3f, 4f))

    // Corpo Gel / Vidro 3D
    scope.drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color.White, tipo.cor, tipo.corEscura),
            center = centro + Offset(-raio * 0.35f, -raio * 0.35f),
            radius = raio * 1.5f
        ),
        radius = raio * 0.92f,
        center = centro
    )

    // Reflexo de Luz Curvado
    scope.drawCircle(
        color = Color.White.copy(alpha = 0.8f),
        radius = raio * 0.25f,
        center = centro + Offset(-raio * 0.35f, -raio * 0.35f)
    )

    // Texto ou Expressão
    if (tipo.texto != null) {
        scope.drawContext.canvas.nativeCanvas.apply {
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = raio * 1.1f
                isFakeBoldText = true
                setShadowLayer(4f, 0f, 0f, android.graphics.Color.BLACK)
            }
            drawText(tipo.texto, centro.x, centro.y + raio * 0.38f, paint)
        }
    } else {
        // Rosto fofinho estilo dragãozinho
        val olhoCor = Color.Black.copy(alpha = 0.85f)
        scope.drawCircle(olhoCor, raio * 0.12f, centro + Offset(-raio * 0.22f, -raio * 0.05f))
        scope.drawCircle(olhoCor, raio * 0.12f, centro + Offset(raio * 0.22f, -raio * 0.05f))
        scope.drawCircle(Color.White, raio * 0.04f, centro + Offset(-raio * 0.24f, -raio * 0.08f))
        scope.drawCircle(Color.White, raio * 0.04f, centro + Offset(raio * 0.20f, -raio * 0.08f))

        val pathSorriso = Path().apply {
            moveTo(centro.x - raio * 0.18f, centro.y + raio * 0.15f)
            quadraticTo(centro.x, centro.y + raio * 0.32f, centro.x + raio * 0.18f, centro.y + raio * 0.15f)
        }
        scope.drawPath(pathSorriso, Color.Black, style = Stroke(width = 3f, cap = StrokeCap.Round))
    }
}

private fun desenharLançadorDouradoEBoy(
    scope: DrawScope,
    origem: Offset,
    angulo: Float,
    raio: Float,
    mascotMessage: String
) {
    // Seta de Mira Dourada
    val tamSeta = 170f
    val fimX = origem.x + cos(angulo) * tamSeta
    val fimY = origem.y + sin(angulo) * tamSeta

    scope.drawLine(
        brush = Brush.linearGradient(listOf(Color(0xFFFFD700), Color(0xFFFF8C00))),
        start = origem,
        end = Offset(fimX, fimY),
        strokeWidth = 9f,
        cap = StrokeCap.Round
    )

    // Ponta da Seta
    val pathPonta = Path().apply {
        moveTo(fimX, fimY)
        lineTo(fimX - 18f * cos(angulo - 0.4f), fimY - 18f * sin(angulo - 0.4f))
        lineTo(fimX - 18f * cos(angulo + 0.4f), fimY - 18f * sin(angulo + 0.4f))
        close()
    }
    scope.drawPath(pathPonta, Color(0xFFFFD700))

    // Base do Disparador Dourado
    scope.drawCircle(
        brush = Brush.radialGradient(listOf(Color(0xFFFFE57F), Color(0xFFB8860B))),
        radius = raio * 1.5f,
        center = origem
    )
    scope.drawCircle(
        color = Color(0xFF5D4037),
        radius = raio * 1.25f,
        center = origem,
        style = Stroke(width = 5f)
    )

    // Mascote Zé Traquina
    val boyX = origem.x - raio * 2.8f
    val boyY = origem.y + raio * 0.3f

    // Camisa Verde
    scope.drawCircle(Color(0xFF2E7D32), raio * 0.65f, Offset(boyX, boyY + 18f))
    // Cabeça
    scope.drawCircle(Color(0xFFFFCC80), raio * 0.55f, Offset(boyX, boyY - 18f))
    // Boné
    val pathBone = Path().apply {
        moveTo(boyX - raio * 0.65f, boyY - 22f)
        quadraticTo(boyX, boyY - raio * 1.1f, boyX + raio * 0.65f, boyY - 22f)
        close()
    }
    scope.drawPath(pathBone, Color(0xFF5D4037))

    // Balão de Fala do Mascote
    scope.drawContext.canvas.nativeCanvas.apply {
        val paintText = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = 22f
            isFakeBoldText = true
            setShadowLayer(4f, 0f, 2f, android.graphics.Color.BLACK)
        }
        drawText(mascotMessage, boyX, boyY - raio * 1.3f, paintText)
    }
}

// -------------------- ECRÃ FINAL --------------------

@Composable
private fun EcraFimDeJogo(pontos: Int, onJogarNovamente: () -> Unit, onMenu: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Fim de Jogo!", color = Color(0xFFFFD54A), fontSize = 32.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Pontuação Total: $pontos", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onJogarNovamente,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00CC44)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("🔄 Jogar Novamente", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onMenu) {
                Text("Menu Principal", color = Color.White)
            }
        }
    }
}
