package com.lafi.lawyer.core.util.keyboard_ovserver

import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.lafi.lawyer.core.util.keyboard_ovserver.impl.KeyboardObserverImplApi30

// TODO: 마지막 키보드 로컬에 저장하고 기본 셋팅 추가하기.
object LafiKeyboardObserver {
        /**
         * Activity를 사용하여 KeyboardObserver 인스턴스 생성
         *
         * @param activity 키보드 변화를 감지할 Activity
         * @return KeyboardObserver 인스턴스
         */
        fun with(activity: FragmentActivity): KeyboardObserver {
            val rootView = activity.findViewById<View>(android.R.id.content)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                KeyboardObserverImplApi30(rootView)
            } else {
                TODO("")
            }
        }

        /**
         * Fragment를 사용하여 KeyboardObserver 인스턴스 생성
         *
         * @param fragment 키보드 변화를 감지할 Fragment
         * @return KeyboardObserver 인스턴스
         */
        fun with(fragment: Fragment): KeyboardObserver {
            fragment.view ?: throw IllegalStateException("Fragment's view is null. Make sure to call this method after onViewCreated.")
            val activity = fragment.activity ?: throw IllegalStateException("Fragment is not attached to an activity.")
            val rootView = activity.findViewById<View>(android.R.id.content)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                KeyboardObserverImplApi30(rootView)
            } else {
                TODO("")
            }
        }
}
