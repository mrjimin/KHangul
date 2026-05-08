package com.github.mrjimin.khangul

import java.text.Normalizer

object KHangulFormatter {
    fun formatNumber(number: Long): String {
        if (number == 0L) return "영"
        val nums = " 일이삼사오육칠팔구"
        val units = arrayOf("", "십", "백", "천")
        val bigUnits = arrayOf("", "만", "억", "조", "경")
        val s = number.toString()
        val res = StringBuilder()
        var hasChunkValue = false

        for (i in s.indices) {
            val n = s[i] - '0'
            val pos = s.length - i - 1
            if (n != 0) {
                res.append(nums[n]).append(units[pos % 4])
                hasChunkValue = true
            }
            if (pos % 4 == 0) {
                if (hasChunkValue) res.append(bigUnits[pos / 4])
                hasChunkValue = false
            }
        }
        return res.toString()
    }

    fun normalize(text: String): String = Normalizer.normalize(text, Normalizer.Form.NFC)
}