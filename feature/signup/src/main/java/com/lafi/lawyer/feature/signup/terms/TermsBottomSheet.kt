package com.lafi.lawyer.feature.signup.terms

import android.os.Bundle
import android.view.View
import com.lafi.lawyer.core.design_system.component.scale_ripple.setOnScaleClickListener
import com.lafi.lawyer.core.design_system.fragment.BaseBottomSheetFragment
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupTermsBottomSheetBinding

class TermsBottomSheet : BaseBottomSheetFragment<FeatureSignupTermsBottomSheetBinding>(FeatureSignupTermsBottomSheetBinding::inflate) {
    override val TAG: String = TermsBottomSheet.TAG

    enum class Terms {
        GENERAL_TERMS_OF_SERVICE,
        PRIVACY_POLICY,
        INTELLECTUAL_PROPERTY_POLICY,
        MARKETING_CONSENT_TERMS
    }

    private var listener: OnTermsListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.tvContinue.setOnScaleClickListener(96) {
            listener?.onTerms(*Terms.entries.toTypedArray())
            dismiss()
        }
    }

    fun setOnTermsListener(listener: OnTermsListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "TermsBottomSheet"
        fun newInstance() = TermsBottomSheet()
    }
}