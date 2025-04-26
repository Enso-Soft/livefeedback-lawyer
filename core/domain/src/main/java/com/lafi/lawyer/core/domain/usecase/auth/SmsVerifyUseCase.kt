package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class SmsVerifyUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        code: String
    ): DataResult<Unit> {
        return authRepository.smsVerify(
            smsVerifyType = smsVerifyType,
            phoneNumber = phoneNumber,
            code = code
        )
    }
}