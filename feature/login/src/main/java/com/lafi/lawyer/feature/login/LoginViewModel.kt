package com.lafi.lawyer.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lafi.lawyer.core.domain.usecase.auth.PostLoginSocialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginSocialUseCase: PostLoginSocialUseCase
) : ViewModel() {
    suspend fun test() {
        val response = postLoginSocialUseCase("kakao", "2349042das")
        Log.d("whk__", "good response : $response")
    }
}