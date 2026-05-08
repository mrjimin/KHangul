package com.github.mrjimin.khangul

object KHangul {
    fun disassemble(text: String) = KHangulParser.disassemble(text)
    fun assemble(cho: Char, jung: Char, jong: Char = ' ') = KHangulParser.assemble(cho, jung, jong)

    fun toChosung(text: String) = StringBuilder(text.length).apply {
        for (c in text) append(getChosung(c))
    }.toString()

    fun getChosung(char: Char): Char = if (isHangul(char)) CHOSUNG[(char.code - START) / 588] else char

    fun hasJongsung(char: Char): Boolean = when {
        isHangul(char) -> (char.code - START) % 28 > 0
        char.isDigit() -> "013678".contains(char)
        char.isLetter() -> {
            val lower = char.lowercaseChar()
            "bcdfgjklmnpqrstvxz".contains(lower)
        }
        else -> false
    }

    fun smartContains(text: String, query: String) = KHangulMatcher.smartContains(text, query)
    fun engToKor(text: String) = KHangulTyper.convert(text)
    fun formatNumber(number: Long) = KHangulFormatter.formatNumber(number)
}