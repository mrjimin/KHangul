# KHangul

[![](https://jitpack.io/v/mrjimin/KHangul.svg)](https://jitpack.io/#mrjimin/KHangul)
[![GitHub License](https://img.shields.io/github/license/mrjimin/KHangul?style=flat-square)](LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

자모 분해, 초성·영타 매칭, 조사 자동 선택을 지원하는 가장 직관적인 Kotlin 한글 유틸리티

## 🚀 주요 기능

### 1. 한글 자모 분리 및 추출

* **초성 추출**: 문자열에서 초성만 추출하여 인덱싱이나 퀵 서치에 활용합니다. (예: `사과` → `ㅅㄱ`)
* **자모 완전 해체**: `disassemble`을 통해 복합 자모까지 최소 단위로 분해합니다. (예: `닭` → `ㄷㅏㄹㄱ`)

### 2. 스마트 매칭 (Smart Match)

단순한 텍스트 포함 여부를 넘어, 사용자의 다양한 입력 패턴을 인식합니다.

* **초성 검색**: `바나나` 검색 시 `ㅂㄴㄴ` 입력만으로 매칭됩니다.
* **미완성 음절 검색**: 음절이 완성되지 않아도 자모 순서가 일치하면 검색됩니다. (예: `코틀린` 검색 시 `ㅋㅗㅌㄹ` 매칭)
* **영문 자판 입력 대응**: 영문 자판 상태에서 입력된 텍스트를 한글 자모로 해석하여 검색을 수행합니다. (예: `zhxmffls` 입력 시 `코틀린` 인식)

### 3. 문맥 기반 조사(Josa) 자동 선택

* **다국어 끝소리 판정**: 한글 종성뿐만 아니라 숫자(`1, 3, 6...`)와 영어(`smart`, `apple` 등)의 끝소리 받침 유무를 판정합니다.
* **ㄹ 받침 특수 규칙**: `으로/로` 선택 시, 한글 `ㄹ` 받침과 숫자 `1, 7, 8` 뒤에 `로`가 붙는 예외 규정을 준수합니다. (예: `서울로`, `1로`)

### 4. 숫자 한글 표기 (toHangul)

* 정수(`Int`, `Long`)를 실제 한국어 읽기 방식(`일만 이천...`)으로 변환합니다. 최대 **경(京)** 단위까지 지원합니다.

---

## 🛠 사용 방법

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.mrjimin:KHangul:1.1.0")
}
```

### 스마트 매칭 (Smart Match)

```kotlin
val target = "코틀린 라이브러리"

target.smartContains("ㅋㅌㄹ") // true (초성 검색)
target.smartContains("ㅋㅗㅌㄹ") // true (자모 분해 검색)
target.smartContains("zhxmffls") // true (영문 자판 입력 대응: ㅋㅗㅌㅡㄹㄹㅣㄴ)
target.smartContains("Zhxmffls") // true (대소문자 무관)
```

### 조사 처리 (Josa Extension)

```kotlin
// Enum 및 문자열 파라미터 지원
"지민".withJosa(Josa.EUN_NEUN) // 지민은
"서울".withJosa("으로") // 서울로 (ㄹ 받침 예외)
"1".withJosa("이") // 1이 (일)
"smart".hasJongsung() // true (t 받침)
```

### 숫자 읽기

```kotlin
1234567.toHangul() // 일백이십삼만사천오백육십칠
0.toHangul() // 영
```

---

## ⚙️ 상세 구조

### 영문 받침 판정 기준

영어 단어의 마지막 철자가 자음인지 모음인지에 따라 받침 유무를 결정합니다.

* **받침 있음**: `b, c, d, f, g, j, k, l, m, n, p, q, r, s, t, v, x, z`
* **받침 없음**: `a, e, i, o, u` 및 `y, h, w`

### 유니코드 연산 및 정규화

KHangul은 현대 한글 유니코드 조합 방식인 NFC(Normalization Form C)를 기준으로 설계되어 데이터 일관성을 보장합니다.

* **한글 조합 공식**:
  $$한글\;코드 = 0xAC00 + (초성 \times 588) + (중성 \times 28) + 종성$$


* **텍스트 정규화**: `KHangulFormatter.normalize()`를 통해 서로 다른 OS 환경(macOS NFD 등)에서 유입되는 한글 데이터를 NFC로 변환하여 안정적으로 처리합니다.

---

## 📄 License

이 프로젝트는 MIT License를 따릅니다.