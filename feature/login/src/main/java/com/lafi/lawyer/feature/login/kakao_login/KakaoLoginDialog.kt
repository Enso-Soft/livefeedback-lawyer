package com.lafi.lawyer.feature.login.kakao_login

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.lafi.lawyer.core.design_system.fragment.BaseDialogFragment
import com.lafi.lawyer.feature.login.databinding.FeatureLoginKakaoLoginDialogBinding

class KakaoLoginDialog : BaseDialogFragment<FeatureLoginKakaoLoginDialogBinding>(FeatureLoginKakaoLoginDialogBinding::inflate) {
    private var onKakaoLoginDialogListener: OnKakaoLoginDialogListener? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            // Dialog 크기를 전체화면으로 설정
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            // 배경을 투명하게 설정
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        with(binding) {
            llKakaoLogin.setOnClickListener {
                onKakaoLoginDialogListener?.onKakaoLogin()
                dismiss()
            }
            llKakaoLoginAnotherAccount.setOnClickListener {
                onKakaoLoginDialogListener?.onAnotherLogin()
                dismiss()
            }
            textViewCancel.setOnClickListener { dismiss() }
        }
    }

    fun setOnKakaoLoginDialogListener(listener: OnKakaoLoginDialogListener) {
        this.onKakaoLoginDialogListener = listener
    }

    companion object {
        const val TAG = "KakaoLoginDialog"
        fun newInstance() = KakaoLoginDialog()
    }
}