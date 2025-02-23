package com.lafi.lawyer

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.lafi.lawyer.feature.login.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LafiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_OAUTH_KEY)
    }
}