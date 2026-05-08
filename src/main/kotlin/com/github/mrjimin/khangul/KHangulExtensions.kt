package com.github.mrjimin.khangul

fun String.disassemble(): String = KHangul.disassemble(this)

fun String.toChosung(): String = KHangul.toChosung(this)

fun String.smartContains(query: String): Boolean = KHangul.smartContains(this, query)

fun String.engToKor(): String = KHangul.engToKor(this)

fun String.hasJongsung(): Boolean = this.isNotEmpty() && KHangul.hasJongsung(this.last())

fun String.withJosa(josa: Josa): String {
    if (this.isEmpty()) return this
    return this + josa.getSuffix(this.last())
}

fun String.withJosa(josa: String): String {
    val trimmed = this.trimEnd()
    if (trimmed.isEmpty()) return this

    val lastChar = trimmed.last()
    val targetJosa = when (josa) {
        "은", "는" -> Josa.EUN_NEUN
        "이", "가" -> Josa.I_GA
        "을", "를" -> Josa.EUL_REUL
        "과", "와" -> Josa.GWA_WA
        "으로", "로" -> Josa.EURO_RO
        "이랑", "랑" -> Josa.IRANG_RANG
        else -> return this + josa
    }

    return this + targetJosa.getSuffix(lastChar)
}

fun Long.toHangul(): String = KHangul.formatNumber(this)

fun Int.toHangul(): String = KHangul.formatNumber(this.toLong())