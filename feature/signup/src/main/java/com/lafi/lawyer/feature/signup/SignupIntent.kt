package com.lafi.lawyer.feature.signup

sealed class SignupIntent {
    data class RequestSmsVerifyCode(val phoneNumber: String) : SignupIntent()
}