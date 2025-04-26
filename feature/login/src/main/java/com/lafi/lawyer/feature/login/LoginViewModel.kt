package com.lafi.lawyer.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafi.lawyer.core.domain.usecase.auth.PostLoginSocialUseCase
import com.lafi.lawyer.core.domain.model.auth.SocialLoginResult
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginSocialUseCase: PostLoginSocialUseCase
) : ViewModel() {
    private val _socialLoginResultEvent = MutableSharedFlow<SocialLoginResult>()
    val socialLoginResultEvent: SharedFlow<SocialLoginResult> get() = _socialLoginResultEvent

    fun checkSocialLogin(
        provider: SocialProvider,
        socialAccessToken: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        postLoginSocialUseCase(provider = provider, accessToken = socialAccessToken).let {
            _socialLoginResultEvent.emit(it)
        }
    }
}