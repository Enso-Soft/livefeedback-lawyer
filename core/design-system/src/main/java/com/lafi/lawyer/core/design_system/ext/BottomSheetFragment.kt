package com.lafi.lawyer.core.design_system.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.lafi.lawyer.core.design_system.fragment.BaseBottomSheetFragment
import com.lafi.lawyer.core.util.keyboard_ovserver.KeyboardObserver

fun BaseBottomSheetFragment<*>.show(activity: FragmentActivity, clearFocus: Boolean = false) {
    val keyboardObserver = KeyboardObserver.with(activity)

    if (keyboardObserver.isKeyboardVisible()) {
        keyboardObserver.setSingleEventListener { visible, _, _ ->
            if (!visible) {
                this@show.show(activity.supportFragmentManager, this@show.TAG)
            }
        }
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val view = activity.currentFocus ?: activity.window.decorView
        imm?.hideSoftInputFromWindow(view.windowToken, 0) ?: run {
            this@show.show(activity.supportFragmentManager, this@show.TAG)
        }

        if (clearFocus) {
            activity.currentFocus?.clearFocus()
        }
    } else {
        this@show.show(activity.supportFragmentManager, this@show.TAG)
    }
}