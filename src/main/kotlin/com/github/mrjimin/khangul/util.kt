package com.github.mrjimin.khangul

const val START = 0xAC00
const val END = 0xD7A3
const val CHOSUNG = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
const val JUNGSUNG = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ"
const val JONGSUNG = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ"

val COMPLEX_JUNGSUNG = mapOf(
    'ㅐ' to "ㅏㅣ", 'ㅒ' to "ㅑㅣ", 'ㅔ' to "ㅓㅣ", 'ㅖ' to "ㅕㅣ",
    'ㅘ' to "ㅗㅏ", 'ㅙ' to "ㅗㅐ", 'ㅚ' to "ㅗㅣ", 'ㅝ' to "ㅜㅓ",
    'ㅞ' to "ㅜㅔ", 'ㅟ' to "ㅜㅣ", 'ㅢ' to "ㅡㅣ"
)

val COMPLEX_JONGSUNG = mapOf(
    'ㄲ' to "ㄱㄱ",
    'ㄳ' to "ㄱㅅ",
    'ㄵ' to "ㄴㅈ",
    'ㄶ' to "ㄴㅎ",
    'ㄸ' to "ㄷㄷ",
    'ㄺ' to "ㄹㄱ",
    'ㄻ' to "ㄹㅁ",
    'ㄼ' to "ㄹㅂ",
    'ㄽ' to "ㄹㅅ",
    'ㄾ' to "ㄹㅌ",
    'ㄿ' to "ㄹㅍ",
    'ㅀ' to "ㄹㅎ",
    'ㅃ' to "ㅂㅂ",
    'ㅄ' to "ㅂㅅ",
    'ㅆ' to "ㅅㅅ",
    'ㅉ' to "ㅈㅈ"
)

val DOUBLE_JUNGSUNG = mapOf(
    "ㅗㅏ" to 'ㅘ', "ㅗㅐ" to 'ㅙ', "ㅗㅣ" to 'ㅚ',
    "ㅜㅓ" to 'ㅝ', "ㅜㅔ" to 'ㅞ', "ㅜㅣ" to 'ㅟ',
    "ㅡㅣ" to 'ㅢ'
)

val DOUBLE_JONGSUNG = mapOf(
    "ㄱㅅ" to 'ㄳ',
    "ㄴㅈ" to 'ㄵ', "ㄴㅎ" to 'ㄶ',
    "ㄹㄱ" to 'ㄺ', "ㄹㅁ" to 'ㄻ', "ㄹㅂ" to 'ㄼ', "ㄹㅅ" to 'ㄽ', "ㄹㅌ" to 'ㄾ', "ㄹㅍ" to 'ㄿ', "ㄹㅎ" to 'ㅀ',
    "ㅂㅅ" to 'ㅄ'
)

val ENG_TO_KOR = mapOf(
    'q' to 'ㅂ',
    'w' to 'ㅈ',
    'e' to 'ㄷ',
    'r' to 'ㄱ',
    't' to 'ㅅ',
    'y' to 'ㅛ',
    'u' to 'ㅕ',
    'i' to 'ㅑ',
    'o' to 'ㅐ',
    'p' to 'ㅔ',
    'a' to 'ㅁ',
    's' to 'ㄴ',
    'd' to 'ㅇ',
    'f' to 'ㄹ',
    'g' to 'ㅎ',
    'h' to 'ㅗ',
    'j' to 'ㅓ',
    'k' to 'ㅏ',
    'l' to 'ㅣ',
    'z' to 'ㅋ',
    'x' to 'ㅌ',
    'c' to 'ㅊ',
    'v' to 'ㅍ',
    'b' to 'ㅠ',
    'n' to 'ㅜ',
    'm' to 'ㅡ',
    'Q' to 'ㅃ',
    'W' to 'ㅉ',
    'E' to 'ㄸ',
    'R' to 'ㄲ',
    'T' to 'ㅆ',
    'O' to 'ㅒ',
    'P' to 'ㅖ'
)

fun isHangul(char: Char) = char.code in START..END
fun isChosung(char: Char) = char in CHOSUNG
fun isJungsung(char: Char) = char in JUNGSUNG