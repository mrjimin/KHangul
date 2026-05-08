package com.github.mrjimin.khangul

object KHangulParser {
    fun disassemble(text: String): String = StringBuilder(text.length * 3).apply {
        for (c in text) {
            if (isHangul(c)) {
                val unit = c.code - START
                val cho = CHOSUNG[unit / 588]
                val jung = JUNGSUNG[(unit % 588) / 28]
                val jongIdx = unit % 28

                append(cho)
                append(COMPLEX_JUNGSUNG[jung] ?: jung)
                if (jongIdx > 0) {
                    val jong = JONGSUNG[jongIdx]
                    append(COMPLEX_JONGSUNG[jong] ?: jong)
                }
            } else {
                append(c)
            }
        }
    }.toString()

    fun assemble(cho: Char, jung: Char, jong: Char = ' '): Char {
        val cIdx = CHOSUNG.indexOf(cho)
        val rIdx = JUNGSUNG.indexOf(jung)
        val oIdx = JONGSUNG.indexOf(jong).let { if (it == -1) 0 else it }
        return if (cIdx == -1 || rIdx == -1) cho
        else (START + (cIdx * 588) + (rIdx * 28) + oIdx).toChar()
    }

    fun combineJungsung(first: Char, second: Char): Char = DOUBLE_JUNGSUNG["$first$second"] ?: ' '
    fun combineJongsung(first: Char, second: Char): Char = DOUBLE_JONGSUNG["$first$second"] ?: ' '
}