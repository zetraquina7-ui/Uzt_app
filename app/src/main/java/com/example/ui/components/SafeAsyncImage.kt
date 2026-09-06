package com.example.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/**
 * A wrapper around Coil image loading that provides explicit error logging and loading state handling without recomposition loops.
 */
@Composable
fun SafeAsyncImage(
    model: ImageRequest,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = 1.0f,
    onSuccess: (() -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null
) {
    val context = LocalContext.current
    val currentOnSuccess by rememberUpdatedState(onSuccess)
    val currentOnError by rememberUpdatedState(onError)

    // Enforce software rendering to avoid MESA driver issues in virtualized environments
    val safeModel = remember(model) {
        if (model.allowHardware) {
            model.newBuilder().allowHardware(false).build()
        } else {
            model
        }
    }

    val painter = rememberAsyncImagePainter(
        model = safeModel,
        onSuccess = {
            currentOnSuccess?.invoke()
        },
        onError = { result ->
            val error = result.result.throwable
            val errorMsg = error.message ?: "Unknown Coil Error"
            if (model.data is Int) {
                val resName = try { context.resources.getResourceName(model.data as Int) } catch (e: Exception) { "UnknownID" }
                Log.w("SafeAsyncImage", "Failed to load Resource: $resName (${model.data}): $errorMsg")
            } else {
                Log.w("SafeAsyncImage", "Failed to load asset: ${model.data}: $errorMsg")
            }
            currentOnError?.invoke(error)
        }
    )

    val state = painter.state

    Box(modifier = modifier, contentAlignment = alignment) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha
        )

        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.LightGray,
                strokeWidth = 2.dp
            )
        }
    }
}


