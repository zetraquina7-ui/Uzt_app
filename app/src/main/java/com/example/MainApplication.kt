package com.example

import android.app.Application
import android.util.Log

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MainApplication", "MainApplication initialized.")

        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MainApplication", "FATAL UNCAUGHT EXCEPTION in thread ${thread.name}", throwable)
            defaultHandler?.uncaughtException(thread, throwable)
        }

        try {
            com.example.util.EmulatorUtils.initWebViewEnvironment(this)
        } catch (e: Throwable) {
            Log.e("MainApplication", "Error initializing WebView environment", e)
        }

        try {
            com.google.firebase.FirebaseApp.initializeApp(this)
        } catch (e: Throwable) {
            Log.e("MainApplication", "Error initializing FirebaseApp in Application", e)
        }

        try {
            // Initialize Coil with optimized caching and prefetch educational images
            coil.Coil.setImageLoader(com.example.util.AppImageLoader.get(this))
            com.example.util.AppImageLoader.prefetchLearningImages(this)
        } catch (e: Throwable) {
            Log.e("MainApplication", "Error initializing Coil", e)
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d("MainApplication", "onTrimMemory called with level: $level")
    }
}
