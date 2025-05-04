package com.lafi.lawyer.core.design_system.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.lafi.lawyer.core.util.keyboard_ovserver.KeyboardObserver

abstract class BaseActivity<T: ViewBinding>(
    private val bindingFactory: (layoutInflater: LayoutInflater) -> T
) : AppCompatActivity() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    /** Activity 최초 생성인지 체크 */
    private var _isFirstCreate: Boolean = true
    val isFirstCreate: Boolean get() = _isFirstCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _isFirstCreate = savedInstanceState == null

        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 초기에 시스템 바 패딩만 적용
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // 한번 적용 후 리스너 제거 (선택적)
            ViewCompat.setOnApplyWindowInsetsListener(binding.root, null)
            insets
        }

        KeyboardObserver.with(this).setListener { _, _, percent ->
            if (percent != 0.0f) return@setListener

            val rootView = window.decorView.rootView
            val windowInsets = ViewCompat.getRootWindowInsets(rootView)

            if (windowInsets != null) {
                // 시스템 바 인셋 가져오기
                val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                // IME(키보드) 인셋 가져오기
                val ime = windowInsets.getInsets(WindowInsetsCompat.Type.ime())

                // 둘 중 더 큰 값으로 bottom 패딩 설정
                val bottom = maxOf(systemBars.bottom, ime.bottom)
                // 콘텐츠 뷰의 패딩 업데이트
                binding.root.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom)
            }
        }

        ViewCompat.setWindowInsetsAnimationCallback(binding.root, object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            // 애니메이션 시작 시 호출
            override fun onPrepare(animation: WindowInsetsAnimationCompat) {}

            // 애니메이션 진행 중 호출
            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: List<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                // 시스템 바 인셋 가져오기
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                // IME(키보드) 인셋 가져오기
                val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

                // 둘 중 더 큰 값으로 bottom 패딩 설정
                val bottom = maxOf(systemBars.bottom, ime.bottom)
                // 콘텐츠 뷰의 패딩 업데이트
                binding.root.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom)
                return insets
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
