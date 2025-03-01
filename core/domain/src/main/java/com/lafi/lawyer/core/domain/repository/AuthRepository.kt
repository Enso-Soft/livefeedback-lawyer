package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.model.common.NetworkResult

interface AuthRepository {
    suspend fun loginSocial(provider: String, accessToken: String): NetworkResult<Boolean>
}