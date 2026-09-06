package com.example.util

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.Ficha
import com.example.FichaBloco
import com.example.ui.screens.Worksheet
import com.example.ui.screens.ExerciseType

object WorksheetPrintManager {

    fun printFicha(context: Context, ficha: Ficha, userInputs: Map<String, String> = emptyMap(), fichaIndex: Int = 0) {
        val html = generateFichaHtml(ficha, userInputs, fichaIndex)
        doPrint(context, html, "Ficha_${ficha.numero}_${ficha.titulo}")
    }

    fun printWorksheet(context: Context, worksheet: Worksheet) {
        val html = generateWorksheetHtml(worksheet)
        doPrint(context, html, "Worksheet_${worksheet.title}")
    }

    private fun doPrint(context: Context, html: String, jobName: String) {
        val webView = WebView(context)
        com.example.util.EmulatorUtils.optimizeWebViewForEmulator(webView)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                val printAdapter = view.createPrintDocumentAdapter(jobName)
                printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
            }

            override fun onRenderProcessGone(
                view: WebView?,
                detail: android.webkit.RenderProcessGoneDetail?
            ): Boolean {
                try {
                    view?.destroy()
                } catch (_: Exception) {}
                return true
            }
        }
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    private fun generateFichaHtml(ficha: Ficha, userInputs: Map<String, String>, fichaIndex: Int): String {
        val sb = StringBuilder()
        sb.append("<html><head><style>")
        sb.append("@media print { @page { size: A4; margin: 2cm; } .no-print { display: none; } }")
        sb.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; padding: 0; margin: 0; color: #333; line-height: 1.6; }")
        sb.append(".header { display: flex; justify-content: space-between; align-items: flex-end; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 30px; }")
        sb.append(".header-info { font-size: 0.9em; color: #666; }")
        sb.append(".header-field { border-bottom: 1px solid #ccc; min-width: 150px; display: inline-block; margin-left: 5px; height: 20px; }")
        sb.append("h1 { color: ${ficha.corTemaHex}; margin: 0; font-size: 24px; }")
        sb.append(".bloco { margin-bottom: 25px; page-break-inside: avoid; }")
        sb.append(".instrucao { font-weight: bold; font-size: 1.1em; color: #1a1a1a; margin-bottom: 10px; }")
        sb.append(".letras-container { display: flex; gap: 20px; justify-content: center; margin: 15px 0; }")
        sb.append(".letra-box { text-align: center; }")
        sb.append(".letra-target { font-size: 32px; font-weight: 900; color: #ccc; border: 1px solid #eee; padding: 5px 15px; border-radius: 8px; margin-bottom: 5px; }")
        sb.append(".letra-input { font-size: 28px; font-weight: bold; color: ${ficha.corTemaHex}; border: 2px solid ${ficha.corTemaHex}; border-radius: 8px; width: 50px; height: 50px; display: flex; align-items: center; justify-content: center; margin: 0 auto; }")
        sb.append(".espaco { border: 1px solid #eee; border-radius: 8px; margin-top: 10px; background: #fafafa; }")
        sb.append(".user-response { color: ${ficha.corTemaHex}; font-weight: bold; font-family: 'Courier New', Courier, monospace; }")
        sb.append(".palavra-tag { display: inline-block; padding: 5px 12px; border: 1px solid #ccc; border-radius: 20px; margin: 5px; font-size: 14px; }")
        sb.append(".palavra-selected { background-color: ${ficha.corTemaHex}22; border-color: ${ficha.corTemaHex}; color: ${ficha.corTemaHex}; font-weight: bold; }")
        sb.append(".table-res { border-bottom: 1px dashed #ccc; min-width: 100px; display: inline-block; padding: 0 10px; }")
        sb.append("</style></head><body>")
        
        // Student Header
        sb.append("<div class='header'>")
        sb.append("<div>")
        sb.append("<h1>${ficha.titulo}</h1>")
        sb.append("<p style='margin: 5px 0 0 0; color: #666; font-weight: bold;'>Disciplina: ${ficha.area} | Ficha nº ${ficha.numero}</p>")
        sb.append("</div>")
        sb.append("<div class='header-info'>")
        sb.append("<div>Nome: <span class='header-field' style='width: 250px;'></span></div>")
        sb.append("<div style='margin-top: 8px;'>Data: <span class='header-field'>____/____/________</span></div>")
        sb.append("</div>")
        sb.append("</div>")
        
        ficha.blocos.forEachIndexed { blocoIndex, bloco ->
            sb.append("<div class='bloco'>")
            when (bloco) {
                is FichaBloco.Instrucao -> sb.append("<p class='instrucao'>${bloco.texto}</p>")
                is FichaBloco.LetrasGrandes -> {
                    sb.append("<div class='letras-container'>")
                    bloco.letras.forEachIndexed { subIndex, letra ->
                        val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                        val res = userInputs[key] ?: ""
                        sb.append("<div class='letra-box'>")
                        sb.append("<div class='letra-target'>$letra</div>")
                        sb.append("<div class='letra-input'>$res</div>")
                        sb.append("</div>")
                    }
                    sb.append("</div>")
                }
                is FichaBloco.CaixaPalavras -> {
                    sb.append("<div>")
                    bloco.palavras.forEachIndexed { subIndex, palavra ->
                        val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                        val isSelected = userInputs[key] == "selected"
                        val extraClass = if (isSelected) "palavra-selected" else ""
                        sb.append("<span class='palavra-tag $extraClass'>$palavra</span>")
                    }
                    sb.append("</div>")
                }
                is FichaBloco.LinhasEscrita -> {
                    repeat(bloco.quantidade) { subIndex ->
                        val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                        val res = userInputs[key] ?: ""
                        sb.append("<div style='border-bottom: 1px solid #aaa; min-height: 35px; margin-top: 10px; display: flex; align-items: flex-end; padding: 0 10px;'>")
                        sb.append("<span class='user-response' style='font-size: 1.2em;'>$res</span>")
                        sb.append("</div>")
                    }
                }
                is FichaBloco.TabelaDuasColunas -> {
                    sb.append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>")
                    bloco.esquerda.zip(bloco.direita).forEachIndexed { index, (esq, dir) ->
                        val key = "${fichaIndex}_${blocoIndex}_${index}"
                        val res = userInputs[key] ?: ""
                        
                        val leftContent = if (esq.startsWith("mascote_")) "<i>[Imagem: $esq]</i>" else esq
                        
                        sb.append("<tr>")
                        sb.append("<td style='border: 1px solid #eee; padding: 12px; width: 45%; font-weight: bold;'>$leftContent</td>")
                        sb.append("<td style='border: 1px solid #eee; padding: 12px; width: 10%; text-align: center; color: #ccc;'>&rarr;</td>")
                        sb.append("<td style='border: 1px solid #eee; padding: 12px; width: 45%;'>")
                        if (res.isNotEmpty()) {
                            sb.append("<span class='user-response'>$res</span>")
                        } else {
                            sb.append("<span style='color: #eee;'>................................</span>")
                        }
                        sb.append("</td>")
                        sb.append("</tr>")
                    }
                    sb.append("</table>")
                }
                is FichaBloco.CaixinhasCompletar -> {
                    sb.append("<div style='display: flex; flex-wrap: wrap; gap: 15px; margin-top: 10px;'>")
                    bloco.itens.forEachIndexed { itemIndex, item ->
                        sb.append("<div style='border: 1px solid #eee; padding: 10px 20px; border-radius: 8px; font-size: 1.3em;'>")
                        var underscoreCount = 0
                        item.split("_").forEachIndexed { partIndex, part ->
                            sb.append(part)
                            if (partIndex < item.split("_").size - 1 || item.endsWith("_")) {
                                val key = "${fichaIndex}_${blocoIndex}_${itemIndex}_$underscoreCount"
                                val res = userInputs[key] ?: ""
                                sb.append("<span style='border: 2px solid ${ficha.corTemaHex}; border-radius: 4px; padding: 0 8px; min-width: 20px; display: inline-block; text-align: center;' class='user-response'>")
                                sb.append(if (res.isEmpty()) "&nbsp;" else res)
                                sb.append("</span>")
                                underscoreCount++
                            }
                        }
                        sb.append("</div>")
                    }
                    sb.append("</div>")
                }
                is FichaBloco.TextoLeitura -> sb.append("<p style='font-style: italic; border-left: 4px solid ${ficha.corTemaHex}; padding: 15px 20px; background: #fafafa; margin: 15px 0; font-size: 1.1em;'>${bloco.texto}</p>")
                is FichaBloco.Link -> sb.append("<p>Link: <a href='${bloco.url}'>${bloco.texto}</a></p>")
                is FichaBloco.Imagem -> sb.append("<div style='text-align: center; margin: 20px 0;'><div style='border: 2px dashed #eee; padding: 30px; color: #aaa;'>[Desenho / Imagem: ${bloco.legenda}]</div><p style='color: #666; margin-top: 8px;'><i>${bloco.legenda}</i></p></div>")
                is FichaBloco.EspacoDesenho -> {
                    val key = "${fichaIndex}_${blocoIndex}_drawing"
                    val res = userInputs[key] ?: ""
                    sb.append("<div class='espaco' style='height: ${bloco.alturaDp}px; padding: 15px;'>")
                    if (res.isNotEmpty()) {
                        sb.append("<div class='user-response' style='white-space: pre-wrap;'>$res</div>")
                    }
                    sb.append("</div>")
                }
            }
            sb.append("</div>")
        }
        
        sb.append("<div style='margin-top: 50px; text-align: center; font-size: 0.8em; color: #aaa; border-top: 1px solid #eee; padding-top: 10px;'>")
        sb.append("Universo Educativo Zé Traquina - Ficha Digital")
        sb.append("</div>")
        
        sb.append("</body></html>")
        return sb.toString()
    }

    private fun generateWorksheetHtml(worksheet: Worksheet): String {
        val sb = StringBuilder()
        sb.append("<html><head><style>")
        sb.append("body { font-family: sans-serif; padding: 20px; }")
        sb.append(".exercise { margin-bottom: 30px; border-bottom: 1px solid #ccc; padding-bottom: 10px; }")
        sb.append(".question { font-weight: bold; font-size: 1.1em; }")
        sb.append(".options { margin-left: 20px; }")
        sb.append(".answer-space { border-bottom: 1px solid #000; width: 200px; height: 25px; display: inline-block; }")
        sb.append("</style></head><body>")
        
        sb.append("<h1>${worksheet.title}</h1>")
        sb.append("<p>${worksheet.description}</p>")
        
        worksheet.exercises.forEachIndexed { index, ex ->
            sb.append("<div class='exercise'>")
            sb.append("<p class='question'>${index + 1}. ${ex.question}</p>")
            
            when (ex.type) {
                ExerciseType.MULTIPLE_CHOICE -> {
                    sb.append("<ul class='options'>")
                    ex.options.forEach { opt ->
                        sb.append("<li>[ ] $opt</li>")
                    }
                    sb.append("</ul>")
                }
                ExerciseType.TRUE_FALSE -> {
                    sb.append("<p>[ ] Verdadeiro &nbsp; &nbsp; [ ] Falso</p>")
                }
                ExerciseType.FILL_IN_BLANKS -> {
                    sb.append("<p>Resposta: <span class='answer-space'></span></p>")
                }
                ExerciseType.TEXT_RESPONSE, ExerciseType.NUMERICAL_RESPONSE -> {
                    sb.append("<p>Resposta: <span class='answer-space' style='width: 100%;'></span></p>")
                }
                ExerciseType.MATCHING, ExerciseType.ASSOCIATION -> {
                    sb.append("<table style='width: 100%;'>")
                    val left = ex.pairs.keys.toList()
                    val right = ex.pairs.values.toList().shuffled()
                    for (i in left.indices) {
                        sb.append("<tr><td>${left[i]} . . . . . . .</td><td>. . . . . . . ${right[i]}</td></tr>")
                    }
                    sb.append("</table>")
                }
                else -> {
                    sb.append("<div class='answer-space' style='width: 100%; height: 60px;'></div>")
                }
            }
            sb.append("</div>")
        }
        
        sb.append("</body></html>")
        return sb.toString()
    }
}
