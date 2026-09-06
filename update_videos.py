code = """
fun getFallbackPlaylistTracks(playlistId: String): List<YouTubeVideoTrack> {
    return when (playlistId) {
        "PLHz1Xt0IaQWM" -> listOf(
            YouTubeVideoTrack("fe4HmhQRCUg", "Um mundo melhor", "fe4HmhQRCUg", "https://i3.ytimg.com/vi/fe4HmhQRCUg/hqdefault.jpg", "Música", "🎵"),
            YouTubeVideoTrack("FunPMvy6He8", "Os melhores avós do mundo", "FunPMvy6He8", "https://i3.ytimg.com/vi/FunPMvy6He8/hqdefault.jpg", "Música", "🎵"),
            YouTubeVideoTrack("jYYvwC3L2kI", "Férias de verão", "jYYvwC3L2kI", "https://i3.ytimg.com/vi/jYYvwC3L2kI/hqdefault.jpg", "Música", "☀️"),
            YouTubeVideoTrack("Cdys2zuYpVs", "É NATAL!", "Cdys2zuYpVs", "https://i3.ytimg.com/vi/Cdys2zuYpVs/hqdefault.jpg", "Música", "🎄"),
            YouTubeVideoTrack("GXDSVN0nfJo", "Marcha dos Santos populares", "GXDSVN0nfJo", "https://i3.ytimg.com/vi/GXDSVN0nfJo/hqdefault.jpg", "Música", "🎉"),
            YouTubeVideoTrack("JT5dhPkaXLI", "É Halloween, que divertido!", "JT5dhPkaXLI", "https://i3.ytimg.com/vi/JT5dhPkaXLI/hqdefault.jpg", "Música", "🎃"),
            YouTubeVideoTrack("Ce6QVBTkSUI", "Juntos somos o mundo", "Ce6QVBTkSUI", "https://i3.ytimg.com/vi/Ce6QVBTkSUI/hqdefault.jpg", "Música", "🌍"),
            YouTubeVideoTrack("VbUX6EODgc0", "Um coração para ti - Dia da mãe", "VbUX6EODgc0", "https://i3.ytimg.com/vi/VbUX6EODgc0/hqdefault.jpg", "Música", "❤️"),
            YouTubeVideoTrack("npjny0rOVok", "A luz de Jesus venceu", "npjny0rOVok", "https://i3.ytimg.com/vi/npjny0rOVok/hqdefault.jpg", "Música", "✨"),
            YouTubeVideoTrack("Xkbtbam05_w", "Dia da criança - Magia no ar", "Xkbtbam05_w", "https://i3.ytimg.com/vi/Xkbtbam05_w/hqdefault.jpg", "Música", "🪄"),
            YouTubeVideoTrack("hrpeW39DYTM", "Festa de Carnaval", "hrpeW39DYTM", "https://i3.ytimg.com/vi/hrpeW39DYTM/hqdefault.jpg", "Música", "🎭"),
            YouTubeVideoTrack("DhvP0v6uZEI", "Dia da família", "DhvP0v6uZEI", "https://i3.ytimg.com/vi/DhvP0v6uZEI/hqdefault.jpg", "Música", "🏡"),
            YouTubeVideoTrack("pmMVHEF0zQg", "Animais do ABC", "pmMVHEF0zQg", "https://i3.ytimg.com/vi/pmMVHEF0zQg/hqdefault.jpg", "Música", "🐶"),
            YouTubeVideoTrack("a_Ole8Cbl9M", "Eu sou o Zé Traquina", "a_Ole8Cbl9M", "https://i3.ytimg.com/vi/a_Ole8Cbl9M/hqdefault.jpg", "Música", "🇵🇹")
        )
        "PLT7ZV5QsDKA4" -> listOf(
            YouTubeVideoTrack("alLXEHfi4BM", "O capuchinho vermelho (adaptado)", "alLXEHfi4BM", "https://i3.ytimg.com/vi/alLXEHfi4BM/hqdefault.jpg", "Educativo", "📖"),
            YouTubeVideoTrack("11U9jVVyrYc", "Os números de 1 a 9", "11U9jVVyrYc", "https://i3.ytimg.com/vi/11U9jVVyrYc/hqdefault.jpg", "Educativo", "🔢"),
            YouTubeVideoTrack("1WL6ynGRY2Y", "A letra i", "1WL6ynGRY2Y", "https://i3.ytimg.com/vi/1WL6ynGRY2Y/hqdefault.jpg", "Educativo", "🔤"),
            YouTubeVideoTrack("wr8fKW8nlug", "O corpo humano", "wr8fKW8nlug", "https://i3.ytimg.com/vi/wr8fKW8nlug/hqdefault.jpg", "Educativo", "🧍"),
            YouTubeVideoTrack("4F1P2WDNfXk", "O nascimento de Jesus", "4F1P2WDNfXk", "https://i3.ytimg.com/vi/4F1P2WDNfXk/hqdefault.jpg", "Educativo", "⭐"),
            YouTubeVideoTrack("JESIHHP0iqE", "O primeiro rei de Portugal", "JESIHHP0iqE", "https://i3.ytimg.com/vi/JESIHHP0iqE/hqdefault.jpg", "Educativo", "👑"),
            YouTubeVideoTrack("S5gzoD269E4", "Os planetas do sistema solar", "S5gzoD269E4", "https://i3.ytimg.com/vi/S5gzoD269E4/hqdefault.jpg", "Educativo", "🪐"),
            YouTubeVideoTrack("qd0DxrOngz4", "Volta ao mundo... PORTUGAL", "qd0DxrOngz4", "https://i3.ytimg.com/vi/qd0DxrOngz4/hqdefault.jpg", "Educativo", "🌍"),
            YouTubeVideoTrack("_SVda7zfTx8", "Restauração da independência", "_SVda7zfTx8", "https://i3.ytimg.com/vi/_SVda7zfTx8/hqdefault.jpg", "Educativo", "📜"),
            YouTubeVideoTrack("tPgO6lxf0pM", "A roda dos alimentos", "tPgO6lxf0pM", "https://i3.ytimg.com/vi/tPgO6lxf0pM/hqdefault.jpg", "Educativo", "🍎"),
            YouTubeVideoTrack("5U-cinIL2uY", "Reciclagem", "5U-cinIL2uY", "https://i3.ytimg.com/vi/5U-cinIL2uY/hqdefault.jpg", "Educativo", "♻️"),
            YouTubeVideoTrack("wnr1gdlgJQk", "FELIZ NATAL em várias línguas", "wnr1gdlgJQk", "https://i3.ytimg.com/vi/wnr1gdlgJQk/hqdefault.jpg", "Educativo", "❄️")
        )
        "PLHXyMYX6Yxxc" -> listOf(
            YouTubeVideoTrack("m4gFmngzMHE", "O pedido especial do Zé Traquina ao Pai Natal", "m4gFmngzMHE", "https://i3.ytimg.com/vi/m4gFmngzMHE/hqdefault.jpg", "Diversão", "🌟"),
            YouTubeVideoTrack("Jo-zxdS7iZQ", "UNIVERSO EDUCATIVO PT", "Jo-zxdS7iZQ", "https://i3.ytimg.com/vi/Jo-zxdS7iZQ/hqdefault.jpg", "Diversão", "🎈"),
            YouTubeVideoTrack("DsaedkDd6KU", "Carta ao Pai Natal", "DsaedkDd6KU", "https://i3.ytimg.com/vi/DsaedkDd6KU/hqdefault.jpg", "Diversão", "🎅")
        )
        else -> emptyList()
    }
}"""

with open("app/src/main/java/com/example/ui/screens/VideosScreen.kt", "r") as f:
    content = f.read()

import re
new_content = re.sub(r'fun getFallbackPlaylistTracks\(playlistId: String\): List<YouTubeVideoTrack> \{.*?\n\}', code, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/VideosScreen.kt", "w") as f:
    f.write(new_content)
