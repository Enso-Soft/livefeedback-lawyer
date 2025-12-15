package com.lafi.lawyer.feature.signup.terms

interface OnTermsListener {
    fun onTerms(vararg terms: TermsBottomSheet.Terms)
}