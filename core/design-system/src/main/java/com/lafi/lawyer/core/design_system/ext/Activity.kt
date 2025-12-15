package com.lafi.lawyer.core.design_system.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.reflect.Modifier

inline fun <reified T : Fragment> FragmentActivity.createFragment(
    tag: String? = null
): T {
    val fragmentTag = tag ?: try {
        T::class.java.getField("TAG").get(null) as String
    } catch (e: Exception) {
        T::class.java.simpleName
    }

    // 기존 Fragment 찾기 후 즉시 반환하거나 새로 생성
    return (supportFragmentManager.findFragmentByTag(fragmentTag) as? T)
        ?: createNewFragment<T>()
}

// Fragment 생성 로직을 별도 함수로 분리
inline  fun <reified T : Fragment> createNewFragment(): T {
    return try {
        // newInstance() 메서드가 있는지 확인
        val newInstanceMethod = T::class.java.getMethod("newInstance")
        if (Modifier.isStatic(newInstanceMethod.modifiers)) {
            newInstanceMethod.invoke(null) as T
        } else {
            T::class.java.newInstance()
        }
    } catch (e: Exception) {
        // newInstance() 메서드가 없거나 실패하면 기본 생성자 사용
        T::class.java.newInstance()
    }
}
