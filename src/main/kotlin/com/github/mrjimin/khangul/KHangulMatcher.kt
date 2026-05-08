package com.github.mrjimin.khangul

object KHangulMatcher {
    fun isChosungOnly(text: String) = text.isNotEmpty() && text.all { it in CHOSUNG }

    fun smartContains(text: String, query: String): Boolean {
        val t = text.replace(" ", "").lowercase()
        val q = query.replace(" ", "").lowercase()

        if (t.contains(q)) return true
        if (isChosungOnly(q) && KHangul.toChosung(t).contains(q)) return true

        val convertedQ = KHangulTyper.convert(q)
        val disassembledT = KHangulParser.disassemble(t)

        if (disassembledT.contains(convertedQ)) return true
        if (disassembledT.contains(KHangulParser.disassemble(q))) return true

        return false
    }
}