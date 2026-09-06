package com.example.util

import android.content.Context
import android.util.Log
import com.example.R
import java.io.IOException
import java.lang.reflect.Field

/**
 * Utilitário para diagnosticar a disponibilidade de recursos drawable em tempo de execução.
 */
object AssetDiagnostic {
    private const val TAG = "AssetDiagnostic"

    /**
     * Percorre todos os campos em R.drawable, tenta abrir o recurso e regista o resultado.
     */
    fun performDiagnostic(context: Context) {
        Log.i(TAG, "=== Iniciando Diagnóstico de Assets (res/drawable) ===")
        
        val drawableClass = R.drawable::class.java
        val fields: Array<Field> = drawableClass.fields
        
        var totalCount = 0
        var successCount = 0
        var failureCount = 0

        for (field in fields) {
            try {
                // Filtra campos que não são IDs de recursos (geralmente todos os campos em R.drawable são Int)
                if (field.type != Int::class.javaPrimitiveType) continue
                
                val resourceId = field.getInt(null)
                val resourceName = field.name
                totalCount++

                try {
                    // Tenta abrir o stream do recurso para validar a sua existência e integridade no APK
                    context.resources.openRawResource(resourceId).use { 
                        successCount++
                        // Log em nível VERBOSE para evitar poluir excessivamente o Logcat
                        Log.v(TAG, "Sucesso: $resourceName (ID: $resourceId) carregado corretamente.")
                    }
                } catch (e: IOException) {
                    failureCount++
                    Log.e(TAG, "ERRO: Falha de leitura (IOException) no asset: $resourceName (ID: $resourceId)", e)
                } catch (e: Exception) {
                    failureCount++
                    Log.e(TAG, "ERRO: Falha inesperada ao aceder ao asset: $resourceName (ID: $resourceId)", e)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao processar campo de reflexão: ${field.name}", e)
            }
        }

        Log.i(TAG, "=== Diagnóstico de Assets Concluído ===")
        Log.i(TAG, "Total de drawables verificados: $totalCount")
        Log.i(TAG, "Sucessos: $successCount")
        if (failureCount > 0) {
            Log.e(TAG, "Falhas de carregamento: $failureCount")
        } else {
            Log.i(TAG, "Todos os assets foram carregados com sucesso.")
        }
    }
}
