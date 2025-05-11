package com.lafi.lawyer.feature.signup.sms_verifiy

import android.os.Bundle
import android.view.View
import com.lafi.lawyer.core.design_system.component.scale_ripple.setOnScaleClickListener
import com.lafi.lawyer.core.design_system.fragment.BaseBottomSheetFragment
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupSmsVerifyBottomSheetBinding

class SmsVerifyBottomSheet(override val TAG: String = SmsVerifyBottomSheet.TAG) : BaseBottomSheetFragment<FeatureSignupSmsVerifyBottomSheetBinding>(FeatureSignupSmsVerifyBottomSheetBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        binding.tiVerifyCode.requestFocus()
    }

    private fun initListener() {
        binding.tvContinue.setOnScaleClickListener(96) {}
    }

    companion object {
        const val TAG = "SmsVerifyBottomSheet"
        fun newInstance() = SmsVerifyBottomSheet()
    }
}