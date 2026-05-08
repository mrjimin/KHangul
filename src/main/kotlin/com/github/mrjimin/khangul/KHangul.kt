package com.github.mrjimin.khangul

object KHangul {
    private const val START = 0xAC00
    private const val END = 0xD7A3
    private const val CHOSUNG = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
    private const val JUNGSUNG = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ"
    private const val JONGSUNG = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ"

    fun getChosung(char: Char): Char =
        if (char.code in START..END) CHOSUNG[(char.code - START) / 588] else char

    fun disassemble(char: Char): List<Char> {
        if (char.code !in START..END) return listOf(char)
        val unit = char.code - START
        val cho = CHOSUNG[unit / 588]
        val jung = JUNGSUNG[(unit % 588) / 28]
        val jong = JONGSUNG[unit % 28]
        return if (jong == ' ') listOf(cho, jung) else listOf(cho, jung, jong)
    }

    fun hasJongsung(char: Char): Boolean = when {
        char.code in START..END -> (char.code - START) % 28 > 0
        char.isDigit() -> "013678".contains(char)
        char.isLetter() -> !"aeiouyhw".contains(char.lowercaseChar())
        else -> false
    }
}

fun String.toChosung() =
    map { KHangul.getChosung(it) }.joinToString("")

fun String.disassemble() =
    flatMap { KHangul.disassemble(it) }.joinToString("")

fun String.smartContains(query: String): Boolean {
    val t = this.replace(" ", "").lowercase()
    val q = query.replace(" ", "").lowercase()
    return t.contains(q) || t.toChosung().contains(q) || t.disassemble().contains(q.disassemble())
}

fun String.withJosa(josa: String): String {
    if (isEmpty()) return this
    val hasBatchim = KHangul.hasJongsung(last())
    val res = when (josa) {
        "이", "가" -> if (hasBatchim) "이" else "가"
        "은", "는" -> if (hasBatchim) "은" else "는"
        "을", "를" -> if (hasBatchim) "을" else "를"
        "으로", "로" -> {
            val jong = if (last().code in 0xAC00..0xD7A3) (last().code - 0xAC00) % 28 else -1
            if (hasBatchim && jong != 8) "으로" else "로"
        }
        else -> josa
    }
    return "$this$res"
}