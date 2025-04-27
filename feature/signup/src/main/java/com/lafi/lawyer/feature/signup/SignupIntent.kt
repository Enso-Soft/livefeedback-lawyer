package com.lafi.lawyer.feature.signup

sealed class SignupIntent {
    data class SmsVerifyRequestCode(val phoneNumber: String) : SignupIntent()
    data class SmsVerifyCode(val phoneNumber: String, val code: String) : SignupIntent()
}