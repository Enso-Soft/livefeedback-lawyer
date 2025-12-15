package com.lafi.lawyer.core.util.keyboard_ovserver

import com.lafi.lawyer.core.util.keyboard_ovserver.listener.KeyboardVisibilityListener

interface KeyboardObserverBase {
    fun setListener(listener: KeyboardVisibilityListener)

    fun setListener(listener: (visible: Boolean, keyboardHeight: Int, percent: Float) -> Unit)

    fun setSingleEventListener(listener: KeyboardVisibilityListener)

    fun setSingleEventListener(listener: (visible: Boolean, keyboardHeight: Int, percent: Float) -> Unit)

    fun isKeyboardVisible(): Boolean
}