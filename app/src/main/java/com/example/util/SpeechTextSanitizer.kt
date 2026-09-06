package com.example.util

/**
 * SpeechTextSanitizer
 * Ensures that all text sent to Text-To-Speech (TTS) engines is sanitized by removing
 * emojis, symbols, pictographs, markdown asterisks, and special characters.
 * This guarantees that Zé Traquina speaks only pure words in European Portuguese
 * and never reads out emoji descriptions (e.g. "rosto a sorrir", "troféu", etc.).
 */
object SpeechTextSanitizer {

    fun cleanForSpeech(text: String): String {
        if (text.isBlank()) return ""

        var inputToSanitize = text
        if (text.trim().startsWith("{") && text.trim().endsWith("}")) {
            try {
                val json = org.json.JSONObject(text)
                inputToSanitize = json.optString("resposta", text)
            } catch (_: Exception) {}
        }

        val sb = StringBuilder()
        var i = 0
        while (i < inputToSanitize.length) {
            val codePoint = inputToSanitize.codePointAt(i)
            val charCount = Character.charCount(codePoint)
            val type = Character.getType(codePoint)

            val isEmojiOrSymbol =
                (codePoint in 0x1F600..0x1F64F) || // Emoticons
                (codePoint in 0x1F300..0x1F5FF) || // Misc Symbols and Pictographs
                (codePoint in 0x1F680..0x1F6FF) || // Transport and Map
                (codePoint in 0x1F700..0x1F77F) || // Alchemical Symbols
                (codePoint in 0x1F780..0x1F7FF) || // Geometric Shapes Extended
                (codePoint in 0x1F800..0x1F8FF) || // Supplemental Arrows-C
                (codePoint in 0x1F900..0x1F9FF) || // Supplemental Symbols and Pictographs
                (codePoint in 0x1FA00..0x1FA6F) || // Chess Symbols
                (codePoint in 0x1FA70..0x1FAFF) || // Symbols and Pictographs Extended-A
                (codePoint in 0x2600..0x26FF) ||   // Misc symbols (☀️, ⭐, ⚽, 🏆, etc)
                (codePoint in 0x2700..0x27BF) ||   // Dingbats (✨, ❓, ❗, etc)
                (codePoint in 0xFE00..0xFE0F) ||   // Variation Selectors
                (codePoint in 0x1F1E6..0x1F1FF) || // Regional indicator flags (e.g. 🇵🇹)
                (codePoint == 0x200D) ||           // Zero-width joiner
                (codePoint in 0x20E3..0x20E3) ||   // Keycaps
                (codePoint in 0x2300..0x23FF) ||   // Misc Technical
                (codePoint in 0x2B50..0x2B55) ||   // Stars / shapes
                (type == Character.SURROGATE.toInt()) ||
                (type == Character.OTHER_SYMBOL.toInt() && codePoint > 0x7F)

            if (!isEmojiOrSymbol) {
                sb.appendCodePoint(codePoint)
            }
            i += charCount
        }

        // Strip markdown and clean excessive whitespaces
        return sb.toString()
            .replace("**", "")
            .replace("*", "")
            .replace("#", "")
            .replace("`", "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}
