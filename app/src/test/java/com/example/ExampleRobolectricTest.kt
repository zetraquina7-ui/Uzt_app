package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Zé Traquina", appName)
  }

  @Test
  fun `test voice response JSON parsing with Robolectric`() {
    val jsonString = """
      {
        "transcricao": "Olá Zé Traquina",
        "resposta": "Olá amiguinho! É tão bom ouvir-te! ⭐"
      }
    """.trimIndent()
    val json = org.json.JSONObject(jsonString)
    val transcription = json.optString("transcricao", "")
    val resposta = json.optString("resposta", "")

    val voiceResponse = com.example.util.VoiceResponse(
      transcription = transcription,
      responseText = resposta
    )

    assertEquals("Olá Zé Traquina", voiceResponse.transcription)
    assertEquals("Olá amiguinho! É tão bom ouvir-te! ⭐", voiceResponse.responseText)
    assertTrue(voiceResponse.responseText.contains("amiguinho"))
  }
}
