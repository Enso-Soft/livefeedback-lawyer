package com.lafi.lawyer.feature.signup.sms_verifiy

import com.lafi.lawyer.core.design_system.fragment.BaseBottomSheetFragment
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupSmsVerifyBottomSheetBinding

class SmsVerifyBottomSheet(override val TAG: String = SmsVerifyBottomSheet.TAG) : BaseBottomSheetFragment<FeatureSignupSmsVerifyBottomSheetBinding>(FeatureSignupSmsVerifyBottomSheetBinding::inflate) {

    companion object {
        const val TAG = "SmsVerifyBottomSheet"
        fun newInstance() = SmsVerifyBottomSheet()
    }
}