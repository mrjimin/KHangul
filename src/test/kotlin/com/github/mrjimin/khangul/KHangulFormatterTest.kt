package com.github.mrjimin.com.github.mrjimin.khangul

import com.github.mrjimin.khangul.toHangul
import org.junit.jupiter.api.Test

class KHangulFormatterTest {

    @Test
    fun `Int 타입 한글 변환 테스트`() {
        println(19000.toHangul())
    }
}
