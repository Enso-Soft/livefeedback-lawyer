package com.lafi.lawyer.feature.signup.terms

import android.os.Bundle
import android.view.View
import com.lafi.lawyer.core.design_system.component.scale_ripple.setOnScaleClickListener
import com.lafi.lawyer.core.design_system.fragment.BaseBottomSheetFragment
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupTermsBottomSheetBinding

class TermsBottomSheet : BaseBottomSheetFragment<FeatureSignupTermsBottomSheetBinding>(FeatureSignupTermsBottomSheetBinding::inflate) {
    override val TAG: String = TermsBottomSheet.TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvContinue.setOnScaleClickListener(96) { }
    }

    companion object {
        const val TAG = "TermsBottomSheet"
        fun newInstance() = TermsBottomSheet()
    }
}