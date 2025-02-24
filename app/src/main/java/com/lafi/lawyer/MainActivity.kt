package com.lafi.lawyer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.lafi.lawyer.core.network.retrofit.RetrofitLafiAuth
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.feature.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var ddd: RetrofitLafiAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            val response = ddd.postLoginSocial(
                AuthLoginSocialRequest(
                    provider = "123",
                    accessToken = "dkfsdk"
                )
            )

            Log.d("whk__", "response : $response")

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}