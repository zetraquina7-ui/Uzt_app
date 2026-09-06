package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfEditorScreen(pdfUrl: String, onBack: () -> Unit) {
    val context = LocalContext.current
    
    // Local cache path based on URL hash to prevent collisions
    val cacheFile = remember(pdfUrl) {
        File(context.cacheDir, "pdf_cache_" + pdfUrl.hashCode().toString() + ".pdf")
    }

    var downloadProgress by remember { mutableStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var pdfFileState by remember { mutableStateOf<File?>(null) }
    var retryTrigger by remember { mutableStateOf(0) }

    // Download / Load from Cache logic inside LaunchedEffect
    LaunchedEffect(pdfUrl, retryTrigger) {
        isLoading = true
        errorMessage = null
        downloadProgress = 0f
        
        withContext(Dispatchers.IO) {
            try {
                if (cacheFile.exists() && cacheFile.length() > 0) {
                    // Success from Cache!
                    pdfFileState = cacheFile
                    isLoading = false
                } else {
                    // Start download with real-time progress
                    val url = URL(pdfUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android 14; Mobile; rv:109.0) Gecko/118.0 Firefox/118.0")
                    connection.connectTimeout = 15000
                    connection.readTimeout = 15000
                    connection.connect()

                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        throw IOException("O servidor respondeu com o código HTTP ${connection.responseCode}")
                    }

                    val fileLength = connection.contentLength
                    val tempFile = File(context.cacheDir, "temp_" + pdfUrl.hashCode().toString() + ".pdf")
                    
                    connection.inputStream.use { input ->
                        FileOutputStream(tempFile).use { output ->
                            val data = ByteArray(8192)
                            var total: Long = 0
                            var count: Int
                            
                            while (input.read(data).also { count = it } != -1) {
                                total += count
                                if (fileLength > 0) {
                                    downloadProgress = total.toFloat() / fileLength.toFloat()
                                }
                                output.write(data, 0, count)
                            }
                            output.flush()
                        }
                    }

                    // Atomically rename temporary file to cached file upon success
                    if (tempFile.renameTo(cacheFile)) {
                        pdfFileState = cacheFile
                    } else {
                        // Fallback copy if rename fails
                        tempFile.copyTo(cacheFile, overwrite = true)
                        tempFile.delete()
                        pdfFileState = cacheFile
                    }
                    isLoading = false
                }
            } catch (e: Exception) {
                android.util.Log.e("PdfEditorScreen", "Erro no download do PDF: ${e.message}", e)
                errorMessage = "Erro ao carregar Ficha (${e.javaClass.simpleName}: ${e.message?.take(100)}...)"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ficha de Trabalho", fontWeight = FontWeight.Bold, color = Color(0xFF1E293B)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1E293B)
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color(0xFF1E293B))
                    }
                },
                actions = {
                    pdfFileState?.let { file ->
                        TextButton(onClick = {
                            try {
                                val printManager = context.getSystemService(android.content.Context.PRINT_SERVICE) as PrintManager
                                val printAdapter = FilePrintAdapter(file)
                                printManager.print("Ficha_${file.nameWithoutExtension}", printAdapter, PrintAttributes.Builder().build())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }) {
                            Text("Imprimir", fontWeight = FontWeight.Bold, color = Color(0xFF4F46E5))
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC)),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { downloadProgress },
                            color = Color(0xFF4F46E5),
                            strokeWidth = 6.dp,
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "A descarregar ficha...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val progressPercentage = (downloadProgress * 100).toInt()
                        Text(
                            text = "$progressPercentage%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF4F46E5)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            color = Color(0xFF4F46E5),
                            trackColor = Color(0xFFE2E8F0),
                            modifier = Modifier
                                .width(200.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
                errorMessage != null -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFFEE2E2), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Erro de carregamento",
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Text(
                                text = "Ops! Ocorreu um erro",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = errorMessage ?: "Erro desconhecido ao carregar o PDF.",
                                fontSize = 14.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { retryTrigger++ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Tentar Novamente",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text("Tentar novamente", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
                pdfFileState != null -> {
                    NativePdfListRenderer(file = pdfFileState!!)
                }
            }
        }
    }
}

@Composable
fun NativePdfListRenderer(file: File) {
    val renderer = remember(file) {
        try {
            android.util.Log.d("PdfEditorScreen", "Opening PDF file: ${file.absolutePath}, exists: ${file.exists()}, size: ${file.length()}")
            val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)
            android.util.Log.d("PdfEditorScreen", "PDF renderer created, pages: ${pdfRenderer.pageCount}")
            pdfRenderer
        } catch (e: Exception) {
            android.util.Log.e("PdfEditorScreen", "Error opening PDF renderer: ${e.message}", e)
            null
        }
    }

    DisposableEffect(renderer) {
        onDispose {
            try {
                renderer?.close()
            } catch (_: Exception) {}
        }
    }

    if (renderer == null || renderer.pageCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Não foi possível abrir o ficheiro PDF de forma nativa.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFEF4444)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(renderer.pageCount) { pageIndex ->
                PdfPageItem(renderer = renderer, pageIndex = pageIndex)
            }
        }
    }
}

@Composable
fun PdfPageItem(renderer: PdfRenderer, pageIndex: Int) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var loadError by remember { mutableStateOf(false) }

    LaunchedEffect(renderer, pageIndex) {
        withContext(Dispatchers.IO) {
            try {
                val page = renderer.openPage(pageIndex)
                
                // Scale factor for rendering the PDF pages nicely.
                // Standard A4 is normally 595x842pt. Scale of 2.0 or 2.5 is perfect for high density displays.
                val scale = 2.0f
                val width = (page.width * scale).toInt()
                val height = (page.height * scale).toInt()
                
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = android.graphics.Canvas(bmp)
                canvas.drawColor(android.graphics.Color.WHITE)
                
                page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                
                bitmap = bmp
            } catch (e: Exception) {
                e.printStackTrace()
                loadError = true
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .aspectRatio(
                    if (bitmap != null) {
                        bitmap!!.width.toFloat() / bitmap!!.height.toFloat()
                    } else {
                        0.707f // standard aspect ratio for A4 portrait
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                loadError -> {
                    Text(
                        text = "Erro ao renderizar a página ${pageIndex + 1}",
                        color = Color(0xFFEF4444),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                bitmap != null -> {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = "Página ${pageIndex + 1} da Ficha de Trabalho",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    CircularProgressIndicator(
                        color = Color(0xFF4F46E5),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

class FilePrintAdapter(private val file: File) : PrintDocumentAdapter() {
    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback,
        extras: android.os.Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback.onLayoutCancelled()
            return
        }
        val info = PrintDocumentInfo.Builder("Ficha_${file.nameWithoutExtension}.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .build()
        callback.onLayoutFinished(info, true)
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback
    ) {
        var input: FileInputStream? = null
        var output: FileOutputStream? = null
        try {
            input = FileInputStream(file)
            output = FileOutputStream(destination.fileDescriptor)
            val buf = ByteArray(16384)
            var bytesRead: Int
            while (input.read(buf).also { bytesRead = it } >= 0) {
                if (cancellationSignal?.isCanceled == true) {
                    callback.onWriteCancelled()
                    return
                }
                output.write(buf, 0, bytesRead)
            }
            callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: Exception) {
            callback.onWriteFailed(e.toString())
        } finally {
            try {
                input?.close()
                output?.close()
            } catch (_: Exception) {}
        }
    }
}
