package com.lafi.lawyer.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafi.lawyer.core.domain.usecase.auth.PostLoginSocialUseCase
import com.lafi.lawyer.core.model.common.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginSocialUseCase: PostLoginSocialUseCase
) : ViewModel() {
    private val _errorToast = MutableSharedFlow<String>()
    val errorToast: SharedFlow<String> get() = _errorToast

    fun checkSocialLogin(
        provider: String,
        socialAccessToken: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            postLoginSocialUseCase(provider = provider, accessToken = socialAccessToken).let { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _errorToast.emit(result.data.toString())
                        }
                    }

                    is NetworkResult.Error -> {
                        withContext(Dispatchers.Main) {
                            _errorToast.emit(result.message)
                        }
                    }
                }
            }
        }
    }
}