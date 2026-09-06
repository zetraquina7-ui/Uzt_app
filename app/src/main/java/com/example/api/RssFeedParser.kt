package com.example.api

import android.util.Xml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

data class RssArticle(
    val title: String,
    val description: String,
    val link: String,
    val pubDate: String,
    val imageUrl: String? = null,
    val category: String = "Mundo Infantil"
)

object RssFeedParser {
    private val DEFAULT_FEEDS = listOf(
        "https://zigzag.rtp.pt/feed/",
        "https://www.cienciaviva.pt/rss/noticias.xml"
    )

    suspend fun fetchArticles(feedUrls: List<String> = DEFAULT_FEEDS): List<RssArticle> = withContext(Dispatchers.IO) {
        val articles = mutableListOf<RssArticle>()
        
        for (feedUrl in feedUrls) {
            try {
                val connection = (URL(feedUrl).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 6000
                    readTimeout = 6000
                    requestMethod = "GET"
                    setRequestProperty("User-Agent", "Mozilla/5.0 (Android; UniversoZéTraquina)")
                }
                
                if (connection.responseCode == 200) {
                    val stream = connection.getInputStream()
                    val parsed = parseXml(stream)
                    articles.addAll(parsed)
                    stream.close()
                }
                connection.disconnect()
            } catch (e: Exception) {
                // Ignore single feed error and try next or fall back
            }
            if (articles.size >= 12) break
        }

        if (articles.isEmpty()) {
            getFallbackArticles()
        } else {
            articles.map { article ->
                val enhancedTitle = if (!article.title.contains("☀️") && !article.title.contains("🐬") && !article.title.contains("🏰") && !article.title.contains("✈️") && !article.title.contains("🌱") && !article.title.contains("🌟")) {
                    "${article.title} 🌟"
                } else {
                    article.title
                }
                val enhancedDesc = if (article.description.length < 20 || !article.description.contains("Zé")) {
                    "O Zé e a Luna descobriram: ${article.description.take(130)}... Diverte-te a aprender com esta história fantástica!"
                } else {
                    article.description
                }
                article.copy(title = enhancedTitle, description = enhancedDesc, category = "Mundo Infantil 🌟")
            }
        }
    }

    private fun parseXml(inputStream: java.io.InputStream): List<RssArticle> {
        val list = mutableListOf<RssArticle>()
        try {
            val parser = Xml.newPullParser().apply {
                setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                setInput(inputStream, "UTF-8")
            }

            var eventType = parser.eventType
            var currentTitle = ""
            var currentDesc = ""
            var currentLink = ""
            var currentPubDate = ""
            var currentImage: String? = null
            var insideItem = false

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName.equals("item", ignoreCase = true)) {
                            insideItem = true
                            currentTitle = ""
                            currentDesc = ""
                            currentLink = ""
                            currentPubDate = ""
                            currentImage = null
                        } else if (insideItem) {
                            when (tagName.lowercase()) {
                                "title" -> currentTitle = parser.nextText().trim()
                                "description" -> {
                                    val descText = parser.nextText()
                                    if (currentImage.isNullOrBlank()) {
                                        currentImage = extractImgUrl(descText)
                                    }
                                    currentDesc = cleanHtml(descText)
                                }
                                "content:encoded" -> {
                                    val encodedText = parser.nextText()
                                    if (currentImage.isNullOrBlank()) {
                                        currentImage = extractImgUrl(encodedText)
                                    }
                                }
                                "link" -> currentLink = parser.nextText().trim()
                                "pubdate" -> currentPubDate = parser.nextText().trim()
                                "enclosure" -> {
                                    val url = parser.getAttributeValue(null, "url")
                                    if (!url.isNullOrBlank()) currentImage = url
                                }
                                "media:content", "media:thumbnail" -> {
                                    val url = parser.getAttributeValue(null, "url")
                                    if (!url.isNullOrBlank()) currentImage = url
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (tagName.equals("item", ignoreCase = true) && insideItem) {
                            if (currentTitle.isNotBlank()) {
                                list.add(
                                    RssArticle(
                                        title = currentTitle,
                                        description = currentDesc.ifBlank { "Descobre mais sobre esta curiosidade fascinante do Mundo Infantil!" },
                                        link = currentLink.ifBlank { "https://zigzag.rtp.pt/" },
                                        pubDate = formatPubDate(currentPubDate),
                                        imageUrl = currentImage,
                                        category = "Mundo Infantil 🌟"
                                    )
                                )
                            }
                            insideItem = false
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun extractImgUrl(html: String): String? {
        val matcher = Pattern.compile("src=[\"'](http[^\"']+)[\"']").matcher(html)
        return if (matcher.find()) matcher.group(1) else null
    }

    private fun cleanHtml(html: String): String {
        return html.replace(Regex("<[^>]*>"), "").trim().take(180)
    }

    private fun formatPubDate(rawDate: String): String {
        return if (rawDate.length > 16) rawDate.substring(0, 16) else rawDate.ifBlank { "Hoje" }
    }

    fun getFallbackArticles(): List<RssArticle> {
        return listOf(
            RssArticle(
                title = "Por que motivo o Céu é Azul? ☀️",
                description = "O Sol atira luz de todas as cores para a Terra, mas a cor azul adora saltitar no ar mais do que as outras! O Zé Traquina conta-te tudo.",
                link = "https://zigzag.rtp.pt/",
                pubDate = "Hoje • Curiosidades 🌟",
                imageUrl = "https://images.unsplash.com/photo-1534088568595-a066f410bcda?w=600",
                category = "Ciência Divertida 🌌"
            ),
            RssArticle(
                title = "Como os Golfinhos Falam? 🐬",
                description = "Debaixo de água não há telemóveis! A Luna descobriu que os golfinhos usam 'assobios mágicos' para conversar com os amigos.",
                link = "https://zigzag.rtp.pt/",
                pubDate = "Ontem • Animais 🐾",
                imageUrl = "https://images.unsplash.com/photo-1570481662006-a3a1374699e8?w=600",
                category = "Mundo Animal 🐬"
            ),
            RssArticle(
                title = "A Magia dos Castelos de Contos de Fadas 🏰",
                description = "Sabias que em Portugal há castelos gigantes onde viviam reis, rainhas e bravos cavaleiros? O Zé Traquina vai mostrar-te todos os segredos!",
                link = "https://zigzag.rtp.pt/",
                pubDate = "Esta semana • História 📜",
                imageUrl = "https://images.unsplash.com/photo-1599707367072-cd6ada2bc375?w=600",
                category = "Histórias do Mundo 🏰"
            ),
            RssArticle(
                title = "Como Voam os Aviões no Céu? ✈️",
                description = "A Luna descobriu que os aviões têm asas mágicas que apanham o vento para voar alto por cima das nuvens como passarinhos de metal!",
                link = "https://zigzag.rtp.pt/",
                pubDate = "Recentemente • Invenções 🚀",
                imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=600",
                category = "Grandes Invenções ✈️"
            ),
            RssArticle(
                title = "A Festa Secreta das Plantas 🌱",
                description = "Sabias que as plantas comem luz do Sol e bebem água pela terra para crescerem fortes e verdes? Ajudam a dar-nos ar fresco para respirar!",
                link = "https://zigzag.rtp.pt/",
                pubDate = "Esta semana • Natureza 🌿",
                imageUrl = "https://images.unsplash.com/photo-1518531933037-91b2f5f229cc?w=600",
                category = "Natureza Mágica 🌱"
            )
        )
    }
}
