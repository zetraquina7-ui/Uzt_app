package com.example

sealed class FichaBloco {
    data class Instrucao(val texto: String) : FichaBloco()
    data class LetrasGrandes(val letras: List<String>) : FichaBloco()
    data class CaixaPalavras(val palavras: List<String>) : FichaBloco()
    data class LinhasEscrita(val quantidade: Int) : FichaBloco()
    data class TabelaDuasColunas(val esquerda: List<String>, val direita: List<String>) : FichaBloco()
    data class CaixinhasCompletar(val itens: List<String>) : FichaBloco()
    data class TextoLeitura(val texto: String) : FichaBloco()
    data class Imagem(val nomeRecurso: String, val legenda: String) : FichaBloco()
    data class EspacoDesenho(val alturaDp: Int, val tipoGrelha: String = "") : FichaBloco()
    data class Link(val url: String, val texto: String) : FichaBloco()
}

data class Ficha(
    val numero: Int,
    val titulo: String,
    val area: String,
    val corTemaHex: String,
    val blocos: List<FichaBloco>
)
