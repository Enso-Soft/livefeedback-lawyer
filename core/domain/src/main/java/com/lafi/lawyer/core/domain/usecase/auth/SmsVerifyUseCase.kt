package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class SmsVerifyUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        code: String
    ): SmsVerifyResult {
        return when (
            val response = authRepository.smsVerify(
                smsVerifyType = smsVerifyType,
                phoneNumber = phoneNumber,
                code = code
            )
        ) {
            is DataResult.Success -> SmsVerifyResult.Success
            is DataResult.Error -> {
                when (val errorCode = response.error.code) {
                    40005 -> SmsVerifyResult.InvalidFormatPhoneNumber
                    40009 -> SmsVerifyResult.InvalidVerifyCode
                    else -> SmsVerifyResult.Error(error = response.error)
                }
            }
        }
    }
}