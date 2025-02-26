package com.lafi.laywer.core.data.repository

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.NetworkResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

) : AuthRepository {
    override suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): NetworkResult<Boolean> {
        TODO("Not yet implemented")
    }
}