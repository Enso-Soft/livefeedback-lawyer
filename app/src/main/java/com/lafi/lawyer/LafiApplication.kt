package com.lafi.lawyer

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class LafiApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "842b02ff30406df041ac93f7cb435b49")
    }
}