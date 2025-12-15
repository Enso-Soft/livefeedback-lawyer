package com.lafi.lawyer.core.util.validation

import android.util.Patterns

object ValidationUtils {
    /**
     * 이메일 형식 검증 (Android 내장 Patterns 사용)
     * @param email 검증할 이메일 주소
     * @return 유효한 이메일이면 true, 아니면 false
     */
    fun isEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * 한국 전화번호 형식 검증 (하이픈 없이도 가능)
     * @param phoneNumber 검증할 전화번호
     * @return 유효한 전화번호이면 true, 아니면 false
     */
    fun isPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isBlank()) return false

        // 숫자만 추출
        val digitsOnly = phoneNumber.replace(Regex("[^0-9]"), "")

        // 길이 체크 (10~11자리)
        if (digitsOnly.length !in 10..11) return false

        // 유효한 시작 패턴 체크
        return when {
            // 휴대폰 번호 (010, 011, 016, 017, 018, 019로 시작하는 11자리)
            digitsOnly.matches(Regex("^01[01678][0-9]{7,8}$")) -> true

            // 서울 지역번호 (02로 시작하는 9~10자리)
            digitsOnly.matches(Regex("^02[0-9]{7,8}$")) -> true

            // 기타 지역번호 (0으로 시작하는 10자리)
            digitsOnly.matches(Regex("^0[3-9][0-9]{8}$")) -> true

            else -> false
        }

    }
}