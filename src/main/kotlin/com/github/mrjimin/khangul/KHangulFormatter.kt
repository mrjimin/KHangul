package com.github.mrjimin.khangul

import java.text.Normalizer

object KHangulFormatter {
    private const val NUMS = " 일이삼사오육칠팔구"
    private val UNITS = arrayOf("", "십", "백", "천")
    private val BIG_UNITS = arrayOf("", "만", "억", "조", "경")

    fun formatNumber(number: Long): String {
        if (number == 0L) return "영"
        val s = number.toString()
        val len = s.length
        val res = StringBuilder()
        var hasChunkValue = false

        for (i in s.indices) {
            val n = s[i] - '0'
            val pos = len - i - 1

            if (n != 0) {
                res.append(NUMS[n]).append(UNITS[pos % 4])
                hasChunkValue = true
            }

            if (pos % 4 == 0) {
                if (hasChunkValue) res.append(BIG_UNITS[pos / 4])
                hasChunkValue = false
            }
        }
        return res.toString()
    }

    fun normalize(text: String): String = Normalizer.normalize(text, Normalizer.Form.NFC)
}

fun Long.toHangul() = KHangulFormatter.formatNumber(this)
fun Int.toHangul() = KHangulFormatter.formatNumber(this.toLong())
