package com.example.util

import android.util.Log
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.request.SuccessResult
import kotlinx.coroutines.delay

/**
 * Custom Coil Interceptor that implements an automatic retry policy with exponential backoff.
 * 
 * @param maxRetries Maximum number of attempts including the first one.
 * @param baseDelayMs Initial delay before the first retry in milliseconds.
 */
class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val baseDelayMs: Long = 500
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        var attempts = 0
        var latestResult: ImageResult? = null
        val request = chain.request
        val data = request.data

        // FAST PATH: Skip retries for local resources or already loaded drawables
        if (data is Int || data is android.graphics.Bitmap || data is android.graphics.drawable.Drawable) {
            return chain.proceed(request)
        }

        while (attempts < maxRetries) {
            attempts++
            try {
                latestResult = chain.proceed(request)
                
                if (latestResult is SuccessResult) {
                    if (attempts > 1) {
                        Log.d("RetryInterceptor", "Successfully loaded $data on attempt #$attempts")
                    }
                    return latestResult
                }
                
                // If it's an ErrorResult, we might want to retry
                val errorMsg = (latestResult as? coil.request.ErrorResult)?.throwable?.message ?: "Unknown error"
                Log.w("RetryInterceptor", "Attempt #$attempts failed for $data. Error: $errorMsg")
                
                // Do not retry for non-recoverable network errors like 404 or Unauthorized
                if (errorMsg.contains("404") || errorMsg.contains("unauthorized", ignoreCase = true)) {
                    return latestResult
                }
                
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) {
                    throw e
                }
                Log.e("RetryInterceptor", "Exception on attempt #$attempts for $data: ${e.message}")
            }

            if (attempts < maxRetries) {
                // Exponential backoff: 500ms, 1000ms, 2000ms...
                val delayTime = baseDelayMs * (1 shl (attempts - 1))
                Log.v("RetryInterceptor", "Waiting ${delayTime}ms before retrying...")
                delay(delayTime)
            }
        }

        return latestResult ?: chain.proceed(request)
    }
}
