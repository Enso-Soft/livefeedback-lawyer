package com.lafi.lawyer.core.domain.model.auth

import java.time.Instant

data class SmsVerifyRequestData(
    val code: String,
    val expiresAt: Instant
)