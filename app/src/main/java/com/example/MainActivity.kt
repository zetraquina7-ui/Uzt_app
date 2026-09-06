package com.example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.example.ui.components.ZeAudioPermissionDialog
import com.example.ui.components.ZeListeningPulseIndicator
import com.example.util.AudioPermissionHelper
import com.example.util.SpeechRecognitionHelper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import com.example.ui.components.SafeAsyncImage
import com.example.util.AppImageLoader
import com.example.util.PreviewConfig
import com.example.util.AssetDiagnostic
import com.example.util.ResourceDiagnostics
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.MoreScreen
import com.example.ui.components.UniversoBottomNavBar
import com.example.ui.components.FundoApp
import com.example.ui.navigation.Screen
import com.example.ui.navigation.mainNavScreens
import com.example.ui.screens.AtualidadeScreen
import com.example.ui.screens.ZeAIScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.GamesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.MaisScreen
import com.example.ui.screens.MusicScreen
import com.example.ui.screens.VideosScreen
import com.example.ui.screens.StatsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.PlayfulBackgroundGradient
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.UniversoZeTraquinaTheme
import com.example.viewmodel.MainViewModel
import androidx.lifecycle.lifecycleScope
import coil.Coil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val TAG = "DEBUG_APP"
    private val mainViewModel: MainViewModel by viewModels()

    @androidx.media3.common.util.UnstableApi
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Log.d(TAG, "=== MainActivity.onCreate INICIADO ===")

        // Background preloading and verification
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                com.example.util.ZeAvatarCacheManager.preloadAll(this@MainActivity)
                com.example.data.UserPreferences(this@MainActivity).incrementSessionCount()
                
                // Run diagnostics to identify missing or problematic resources
                verifyWebpAssets()
                ResourceDiagnostics.checkCriticalAssets(this@MainActivity)
                ResourceDiagnostics.checkAllDrawables(this@MainActivity)
                
            } catch (e: Throwable) {
                Log.e(TAG, "Error in background initialization or diagnostics", e)
            }
        }
        
        try {
            Log.d(TAG, "Habilitando EdgeToEdge...")
            enableEdgeToEdge()
            
            Log.d(TAG, "Definindo setContent...")
            setContent {
                UniversoZeTraquinaTheme {
                    // Global Error Boundary
                    var errorState by remember { mutableStateOf<Throwable?>(null) }
                    
                    if (errorState != null) {
                        Box(modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Ops! Ocorreu um erro na interface 😭", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Red)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(errorState?.localizedMessage ?: "Erro desconhecido", textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(onClick = { errorState = null }) {
                                    Text("Tentar Novamente")
                                }
                            }
                        }
                    } else {
                        MainAppContent(
                            viewModel = mainViewModel,
                            onError = { errorState = it }
                        )
                    }
                }
            }
            Log.d(TAG, "setContent configurado com sucesso")
        } catch (e: Throwable) {
            Log.e(TAG, "ERRO CRÍTICO em MainActivity.onCreate", e)
            try {
                setContentView(android.widget.TextView(this).apply {
                    text = "Erro no arranque da aplicação: ${e.localizedMessage}\nConsulte os registos de Logcat (DEBUG_APP)."
                    setPadding(64, 128, 64, 64)
                    textSize = 16f
                })
            } catch (_: Throwable) {}
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "=== MainActivity.onStart ===")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "=== MainActivity.onResume ===")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "=== MainActivity.onPause ===")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "=== MainActivity.onDestroy ===")
    }

    /**
     * Verifica a presença física e acessibilidade de cada ficheiro .webp esperado.
     */
    private fun verifyWebpAssets() {
        val webpAssets = listOf(
            "bg_ze_traquina", "header_games_ze_traquina", "header_pixar_polo_verde",
            "header_ze_traquina_3d", "hero_banner_pixar", "hero_green_polo",
            "ic_spot_diff_base", "ic_spot_diff_modified",
            "img_app_icon", "img_background_art", "img_header_escola_magica", "img_header_escola_pt",
            "img_header_escola_pt_v2", "img_header_home", "img_header_home_alt",
            "img_header_learn", "img_header_parquinho", "img_header_parquinho_pt",
            "img_universo_bg", "img_header_universo",
            "img_parquinho_banner", "img_ze_ai_mascot_nobg",
            "img_ze_face_beret_alt",
            "img_ze_mascot", "img_ze_mascot_png_test_png",
            "img_ze_mascot_transp",
            "ze_ai_futuristic_bg", "ze_ai_mascot_transparent_left",
            "ze_bg_left_aligned", "ze_mascot_green_polo", "ze_mascot_pixar",
            "ze_traquina_full_body", "app_logo"
        ).distinct()

        Log.i("ASSET_VERIFICATION", "--- INICIANDO VERIFICAÇÃO DE FICHEIROS .WEBP ---")
        var successCount = 0
        var failureCount = 0

        for (assetName in webpAssets) {
            val resId = resources.getIdentifier(assetName, "drawable", packageName)
            if (resId == 0) {
                Log.e("ASSET_VERIFICATION", "❌ FALHA CRÍTICA: '$assetName.webp' NÃO ENCONTRADO (Mapeamento de ID falhou)")
                failureCount++
                continue
            }

            try {
                resources.openRawResource(resId).use { 
                    Log.d("ASSET_VERIFICATION", "✅ OK: '$assetName.webp' (ID: $resId) carregado com sucesso.")
                    successCount++
                }
            } catch (e: Exception) {
                Log.e("ASSET_VERIFICATION", "❌ ERRO DE LEITURA: '$assetName.webp' existe mas falhou ao abrir!", e)
                failureCount++
            }
        }

        Log.i("ASSET_VERIFICATION", "--- VERIFICAÇÃO CONCLUÍDA ---")
        Log.i("ASSET_VERIFICATION", "Resultados: Sucesso: $successCount | Falhas: $failureCount")
    }
}

@androidx.media3.common.util.UnstableApi
@Composable
fun MainAppContent(
    viewModel: MainViewModel,
    onError: (Throwable) -> Unit
) {
    val TAG = "DEBUG_APP"
    val currentScreen = viewModel.currentScreen
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    // Shared ChatViewModel for Chat and ParentalControl
    val chatViewModel: com.example.viewmodel.ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // Voice recognition states from ViewModel
    val isListening by viewModel.isListening.collectAsStateWithLifecycle()
    val voiceMessageStatus by viewModel.voiceResult.collectAsStateWithLifecycle()
    val voiceError by viewModel.voiceError.collectAsStateWithLifecycle()
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }

    fun startListeningVoiceCommand() {
        AudioPermissionHelper.checkAndRequestAudioPermission(
            context = context,
            onPermissionGranted = {
                viewModel.startListening { text ->
                    viewModel.handleRecognizedCommand(text)
                }
            },
            onShowRationale = {
                showPermissionRationaleDialog = true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startListening { text ->
                viewModel.handleRecognizedCommand(text)
            }
        }
    }

    val performNavigate: (Screen) -> Unit = remember(viewModel) {
        { screen ->
            viewModel.navigateTo(screen)
        }
    }

    // Handle System Back press to return to Home screen if on another tab
    androidx.activity.compose.BackHandler(enabled = currentScreen != Screen.Home) {
        viewModel.navigateTo(Screen.Home)
    }

    Log.d(TAG, "MainAppContent renderizando tela: ${currentScreen.route}")

    val showRateDialog by viewModel.showRateAppDialog.collectAsStateWithLifecycle()

    if (showRateDialog) {
        com.example.ui.components.RateAppDialog(
            onDismissRequest = { viewModel.onRateAppDismissed() },
            onRateClicked = {
                viewModel.onRateAppDismissed()
                // TODO: Launch store intent here if needed, but not requested. Just dismiss for now.
            }
        )
    }

    FundoApp(drawImage = true) {
        val configuration = androidx.compose.ui.platform.LocalConfiguration.current
        val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier.weight(1f),
                bottomBar = {
                    if (!isLandscape || currentScreen != Screen.Media) {
                        val activeRoute = when (currentScreen.route) { 
                            Screen.Learn.route, Screen.Games.route -> Screen.Educar.route 
                            else -> currentScreen.route 
                        }
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .navigationBarsPadding()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                            ) {
                                UniversoBottomNavBar(
                                    screens = mainNavScreens,
                                    activeRoute = activeRoute,
                                    onScreenSelected = performNavigate
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(if (isLandscape && currentScreen == Screen.Media) PaddingValues(0.dp) else innerPadding)
                ) {
                    when (currentScreen) {
                        Screen.Home -> HomeScreen(viewModel = viewModel, onNavigate = performNavigate)
                        Screen.Educar -> com.example.ui.screens.EducarScreen(viewModel = viewModel)
                        Screen.Stories -> com.example.ui.screens.HistoriasScreen(viewModel = viewModel, onBack = { performNavigate(Screen.Home) })
                        Screen.Learn -> LearnScreen(viewModel = viewModel)
                        Screen.Games -> GamesScreen(mainViewModel = viewModel)
                        Screen.Media -> VideosScreen(mainViewModel = viewModel)
                        Screen.Music -> MusicScreen(mediaViewModel = viewModel())
                        Screen.ParentalControl -> MaisScreen(mainViewModel = viewModel)
                        Screen.Chat -> ZeAIScreen(mainViewModel = viewModel, chatViewModel = chatViewModel)
                        Screen.Stats -> StatsScreen(mainViewModel = viewModel)
                        Screen.Atualidade -> AtualidadeScreen(mainViewModel = viewModel)
                        Screen.More -> MaisScreen(mainViewModel = viewModel)
                    }

                // Active Listening Pulse Indicator overlay
                // Only show global overlay if NOT on the Chat (ZeAI) screen, as it has its own UI
                if (isListening && currentScreen != Screen.Chat) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.55f))
                            .clickable {
                                viewModel.cancelListening()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        ZeListeningPulseIndicator(
                            isListening = isListening,
                            spokenTextPreview = voiceMessageStatus,
                            onStopListening = {
                                viewModel.cancelListening()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

    if (showPermissionRationaleDialog) {
        ZeAudioPermissionDialog(
            onDismissRequest = {
                showPermissionRationaleDialog = false
            },
            onConfirmPermission = {
                showPermissionRationaleDialog = false
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        )
    }
}


