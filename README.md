# 🇰🇷 KHangul


[![](https://jitpack.io/v/mrjimin/KHangul.svg)](https://jitpack.io/#mrjimin/KHangul)
[![GitHub License](https://img.shields.io/github/license/mrjimin/KHangul?style=flat-square)](LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

**KHangul**은 Kotlin/JVM 환경에서 한글 데이터를 더 스마트하게 다루기 위한 유틸리티 라이브러리입니다. 자모 분리, 초성 검색, 문맥에 맞는 조사 선택, 숫자 한글 변환 기능을 제공합니다.

## 🚀 주요 기능

### 1. 한글 자모 분리 및 추출

* **초성 추출**: 문자열에서 초성만 뽑아낼 수 있습니다. (예: `사과` -> `ㅅㄱ`)
* **완전 분리**: 한글을 초성, 중성, 종성 단위로 해체합니다. (예: `강` -> `ㄱㅏㅇ`)

### 2. 스마트 검색 (`smartContains`)

* 단순 포함 여부뿐만 아니라 **초성 검색**과 **자모 단위 검색**을 지원합니다.
* `바나나`에서 `ㅂㄴㄴ` 검색 시 `true`
* `안녕`에서 `ㅇㅏㄴㄴ` 검색 시 `true`

### 3. 문법에 맞는 조사(Josa) 자동 붙이기

* 앞 단어의 **받침 유무**를 판단하여 적절한 조사를 선택합니다.
* 숫자(`1`, `3` 등)와 영어 알파벳의 끝소리까지 고려하여 자연스러운 문장을 만듭니다.
* **특별 규칙**: `ㄹ` 받침으로 끝나는 경우 `로/으로` 선택 로직을 완벽히 처리합니다. (예: `서울로`, `부산으로`)

### 4. 숫자 한글 표기 (`toHangul`)

* `Long` 또는 `Int` 숫자를 한국어 읽기 방식(`일만 이천...`)으로 변환합니다. 경 단위 이상의 큰 숫자도 처리가 가능합니다.

---

## 🛠 사용 방법

### 기본 활용

```kotlin
val text = "안녕하세요"

// 1. 초성 추출
println(text.toChosung()) // ㅇㄴㅎㅅㅇ

// 2. 자모 분리
println(text.disassemble()) // ㅇㅏㄴㄴㅕㅇㅎㅏㅅㅔㅇㅛ
```

### 스마트 검색

```kotlin
val list = listOf("사과", "바나나", "포도")

// 초성으로 검색
val result = list.filter { it.smartContains("ㅅㄱ") } // [사과]
```

### 조사 처리

```kotlin
println("사과".withJosa("을")) // 사과를
println("수박".withJosa("을")) // 수박을

// ㄹ 받침 및 숫자 예외 처리
println("서울".withJosa("으로")) // 서울로 (ㄹ 받침은 '로'가 붙음)
println("1".withJosa("이"))    // 1이 (일 -> 받침 있음)
println("2".withJosa("이"))    // 2가 (이 -> 받침 없음)
```

### 숫자 읽기

```kotlin
val money = 1234567
println(money.toHangul()) // 일백이십삼만사천오백육십칠

val bigNum = 100000000L
println(bigNum.toHangul()) // 일억
```

---

## ⚙️ 상세 구조

KHangul은 한글 유니코드 조합 원리(

$$가 = START + (Chosung \times 588) + (Jungsung \times 28) + Jongsung$$

)를 기반으로 설계되어 정확하고 빠릅니다.

* **Unicode Range**: `0xAC00` ~ `0xD7A3`
* **Normalization**: `KHangulFormatter.normalize()`를 통해 조합형/완성형 텍스트를 안정적으로 처리합니다.

## 📄 License

이 프로젝트는 MIT License를 따릅니다.