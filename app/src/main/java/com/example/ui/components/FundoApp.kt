package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.R
import com.example.util.AppImageLoader

@Composable
fun FundoApp(
    imageUrl: Any = R.drawable.bg_menu_inicial,
    contentScale: ContentScale = ContentScale.Crop,
    drawImage: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val imageModel = remember(imageUrl, isPreview) {
        AppImageLoader.buildImageRequest(
            context = context,
            data = imageUrl,
            placeholderRes = R.drawable.bg_menu_inicial,
            errorRes = R.drawable.bg_menu_inicial,
            crossfade = !isPreview,
            isPreviewMode = isPreview
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (drawImage) {
            SafeAsyncImage(
                model = imageModel,
                contentDescription = null,
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(-10f)
                    .align(Alignment.TopStart)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            content()
        }
    }
}

