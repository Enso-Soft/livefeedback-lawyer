package com.lafi.lawyer.feature.login

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.lafi.lawyer.core.design_system.fragment.BaseDialogFragment
import com.lafi.lawyer.feature.login.databinding.FeatureLoginKakaoLoginDialogBinding

class KakaoLoginDialog : BaseDialogFragment<FeatureLoginKakaoLoginDialogBinding>(FeatureLoginKakaoLoginDialogBinding::inflate) {
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
        binding.textViewCancel.setOnClickListener { dismiss() }
    }


    companion object {
        const val TAG = "KakaoLoginDialog"
        fun newInstance() = KakaoLoginDialog()
    }
}