package com.lafi.lawyer.core.util.text_watcher

import android.text.Editable
import android.text.TextWatcher

class PhoneNumberTextWatcher : TextWatcher {
    private var isFormatting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return

        isFormatting = true

        try {
            // 숫자만 추출
            val digits = s?.toString()?.replace(Regex("[^0-9]"), "") ?: ""

            // 포맷팅 적용
            val formatted = safeFormatPhoneNumber(digits)
            // 텍스트 업데이트
            s?.replace(0, s.length, formatted)
        } catch (e: Exception) {
            // 에러 발생 시 원래 텍스트 유지
            e.printStackTrace()
        } finally {
            isFormatting = false
        }
    }

    private fun safeFormatPhoneNumber(phoneNumber: String): String {
        return when {
            phoneNumber.isEmpty() -> ""
            phoneNumber.length <= 3 -> phoneNumber
            phoneNumber.length <= 7 -> formatWithHyphen(phoneNumber, 3)
            phoneNumber.length <= 10 -> formatWithHyphen(phoneNumber, 3, 6)
            else -> formatWithHyphen(phoneNumber, 3, 7, 11)
        }
    }

    private fun formatWithHyphen(phoneNumber: String, vararg positions: Int): String {
        val result = StringBuilder()
        var lastPos = 0

        for (pos in positions) {
            if (pos <= phoneNumber.length) {
                result.append(phoneNumber.substring(lastPos, pos))
                if (pos < phoneNumber.length) {
                    result.append("-")
                }
                lastPos = pos
            }
        }

        if (lastPos < phoneNumber.length) {
            result.append(phoneNumber.substring(lastPos))
        }

        return result.toString()
    }
}