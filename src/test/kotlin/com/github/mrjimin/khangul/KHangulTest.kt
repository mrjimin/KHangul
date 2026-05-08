package com.github.mrjimin.khangul

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class KHangulTotalTest {

    @Test
    fun `스마트 검색 - 초성 검색 및 영타 보정`() {
        val target = "코틀린 라이브러리"

        // 일반 포함
        assertTrue(target.smartContains("코틀린"))
        // 초성 검색 (ㅋㅌㄹ)
        assertTrue(target.smartContains("ㅋㅌㄹ"))
        // 영타 검색 (코틀린 -> zhxmffls
        assertTrue(target.smartContains("zhxmffls"))
        // 대소문자 섞인 영타 (Zhxmffls)
        assertTrue(target.smartContains("Zhxmffls"))
        // 띄어쓰기 무시 검색
        assertTrue(target.smartContains("코틀린라이브러리"))
    }

    @Test
    fun `초성 추출 - 복합 문자열`() {
        assertEquals("ㅋㅌㄹ 123!", "코틀린 123!".toChosung())
        assertEquals("ㅇㄴㅎㅅㅇ", "안녕하세요".toChosung())
        assertEquals("", "".toChosung()) // 빈 문자열 처리
    }

    @Test
    fun `자소 분해 - 상세 확인`() {
        assertEquals("ㄱㅏㅇ", "강".disassemble())
        assertEquals("ㅎㅏㄴㄱㅡㄹ", "한글".disassemble())
        // 이중 모음 처리 확인
        assertEquals("ㅎㅏㅣ", "해".disassemble())
        assertEquals("ㄱㅏㅂㅅ", "값".disassemble())
    }

    @Test
    fun `조사 처리 - enum 클래스 종합 테스트`() {
        // 받침 유무에 따른 기본 변환 확인
        assertEquals("지민은", "지민".withJosa(Josa.EUN_NEUN))
        assertEquals("지수가", "지수".withJosa(Josa.I_GA))
        assertEquals("사과를", "사과".withJosa(Josa.EUL_REUL))

        // '으로/로'는 'ㄹ' 받침일 때만 특별 대접!
        assertEquals("강으로", "강".withJosa(Josa.EURO_RO))
        assertEquals("서울로", "서울".withJosa(Josa.EURO_RO))

        // 숫자는 소리 내서 읽었을 때의 받침을 따라가요
        assertEquals("10은", "10".withJosa(Josa.EUN_NEUN))
        assertEquals("3이", "3".withJosa(Josa.I_GA))
        assertEquals("2가", "2".withJosa(Josa.I_GA))

        // 숫자의 '으로/로' 예외 (1, 7, 8은 끝음이 'ㄹ')
        assertEquals("1로", "1".withJosa(Josa.EURO_RO))
        assertEquals("8로", "8".withJosa(Josa.EURO_RO))
        assertEquals("6으로", "6".withJosa(Josa.EURO_RO))

        // 영어 에러
        // assertEquals("Apple은", "Apple".withJosa(Josa.EUN_NEUN))
        // assertEquals("Smart가", "Smart".withJosa(Josa.I_GA))
    }

    @Test
    fun `조사 처리 - 와_과 및 이랑_랑`() {
        // 문어체와 구어체 조사들도 꼼꼼히 체크
        assertEquals("수박과", "수박".withJosa(Josa.GWA_WA))
        assertEquals("오렌지와", "오렌지".withJosa(Josa.GWA_WA))

        assertEquals("선생님이랑", "선생님".withJosa(Josa.IRANG_RANG))
        assertEquals("친구랑", "친구".withJosa(Josa.IRANG_RANG))
    }

    @Test
    fun `조사 처리 - String 파라미터 오버로딩`() {
        // 은/는
        assertEquals("사과는", "사과".withJosa("은"))
        assertEquals("연필은", "연필".withJosa("는"))

        // 이/가
        assertEquals("하늘이", "하늘".withJosa("가"))
        assertEquals("바다가", "바다".withJosa("이"))

        // 을/를
        assertEquals("커피를", "커피".withJosa("을"))
        assertEquals("밥을", "밥".withJosa("를"))
    }

    @Test
    fun `조사 처리 - ㄹ받침 및 숫자 특수 케이스`() {
        // '으로/로' 예외: ㄹ 받침일 때는 '로'
        assertEquals("서울로", "서울".withJosa(Josa.EURO_RO))
        assertEquals("물로", "물".withJosa("으로"))

        // 숫자 1, 7, 8 (일, 칠, 팔) 예외
        assertEquals("1로", "1".withJosa("으로"))
        assertEquals("7로", "7".withJosa("으로"))
        assertEquals("8로", "8".withJosa("으로"))

        // 숫자 그 외 (0, 3, 6)
        assertEquals("0으로", "0".withJosa("로"))
        assertEquals("3으로", "3".withJosa("로"))
    }

    @Test
    fun `숫자 한글화 - 큰 숫자 및 영 처리`() {
        assertEquals("영", 0.toHangul())
        assertEquals("일만이백", 10200.toHangul())
        assertEquals("일억이천삼백사십오만육천칠백팔십구", 123456789L.toHangul())
    }

    @Test
    fun `영타 변환 - 쿼티 맵핑 확인`() {
        assertEquals("ㅇㅏㄴㄴㅕㅇㅎㅏㅅㅔㅇㅛ", "dkssudgktpdy".engToKor())
        assertEquals("ㅋㅗㄷㄹㄹㅣㄴ", "zheffls".engToKor())
        assertEquals("!@#", "!@#".engToKor()) // 특수문자 유지
    }

    @Test
    fun `받침 판별 - 언어별 확인`() {
        // 한글
        assertTrue("한".hasJongsung())
        assertTrue("글".hasJongsung())
        assertTrue("글".hasJongsung())

        // 영어 (자음 피치 확인)
        assertTrue("smart".hasJongsung()) // t 받침
        assertFalse("banana".hasJongsung()) // a 끝

        // 숫자
        assertTrue("1".hasJongsung()) // 일 (ㄹ)
        assertFalse("2".hasJongsung()) // 이
    }

    @Test
    fun `한글 합성 - assemble`() {
        assertEquals('값', KHangul.assemble('ㄱ', 'ㅏ', 'ㅄ'))
        assertEquals('나', KHangul.assemble('ㄴ', 'ㅏ'))
    }

    @Test
    fun `빈 문자열 및 공백 처리`() {
        val blank = " "
        assertEquals(" ", blank.disassemble())
        assertEquals(" ", blank.toChosung())
        assertEquals(" ", blank.withJosa("이"))
    }
}