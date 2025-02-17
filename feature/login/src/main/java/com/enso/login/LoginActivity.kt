package com.enso.login

import android.os.Bundle
import com.enso.core.model.MyClass
import com.enso.design_system.activity.BaseActivity
import com.enso.login.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyClass("das")
    }
}