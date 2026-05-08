package com.github.mrjimin.khangul

enum class Josa(val withBatchim: String, val withoutBatchim: String) {
    EUN_NEUN("은", "는"),
    I_GA("이", "가"),
    EUL_REUL("을", "를"),
    GWA_WA("과", "와"),
    IRANG_RANG("이랑", "랑"),
    EURO_RO("으로", "로");

    fun getSuffix(lastChar: Char): String {
        val hasBatchim = KHangul.hasJongsung(lastChar)
        if (this == EURO_RO) {
            val isRieul = isHangul(lastChar) && (lastChar.code - START) % 28 == 8
            val isNumericRieul = "178".contains(lastChar)
            return if (hasBatchim && !isRieul && !isNumericRieul) withBatchim else withoutBatchim
        }
        return if (hasBatchim) withBatchim else withoutBatchim
    }
}