package com.example

import com.example.util.VoiceResponse
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testVoiceResponseCreation() {
    val voiceResponse = VoiceResponse(
      transcription = "Olá Zé Traquina",
      responseText = "Olá amiguinho! É tão bom ouvir-te! ⭐"
    )

    assertEquals("Olá Zé Traquina", voiceResponse.transcription)
    assertEquals("Olá amiguinho! É tão bom ouvir-te! ⭐", voiceResponse.responseText)
    assertTrue(voiceResponse.responseText.contains("amiguinho"))
  }
}
