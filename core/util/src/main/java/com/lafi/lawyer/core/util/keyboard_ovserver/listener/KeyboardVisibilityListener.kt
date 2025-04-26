package com.lafi.lawyer.core.util.keyboard_ovserver.listener

interface KeyboardVisibilityListener {
    /**
     * 키보드 애니메이션 진행 중 및 최종 상태에서 호출됩니다.
     *
     * @param visible 키보드가 보이는지 여부.
     * @param keyboardHeight 현재 키보드 높이 (px).
     * @param percent 정규화된 진행률 (키보드가 완전히 나타나면 1.0, 완전히 숨겨지면 0.0).
     */
    fun onKeyboardVisibilityChanged(visible: Boolean, keyboardHeight: Int, percent: Float)
}