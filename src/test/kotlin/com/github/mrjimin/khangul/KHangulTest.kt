package com.github.mrjimin.com.github.mrjimin.khangul

import com.github.mrjimin.khangul.smartContains
import com.github.mrjimin.khangul.toChosung
import org.junit.jupiter.api.Test

class KHangulTest {

    @Test
    fun `한글 초성 검색`() {
        println("라면".smartContains("ㄹㅁㅁ"))
    }

    @Test
    fun `한글 초성 변환`() {
        println("a안녕하세요. 한글!".toChosung())
    }
}