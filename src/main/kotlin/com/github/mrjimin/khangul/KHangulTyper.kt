package com.github.mrjimin.khangul

object KHangulTyper {
    fun convert(text: String): String = StringBuilder(text.length).apply {
        for (c in text) {
            val kor = ENG_TO_KOR[c] ?: ENG_TO_KOR[c.lowercaseChar()] ?: c
            append(kor)
        }
    }.toString()
}