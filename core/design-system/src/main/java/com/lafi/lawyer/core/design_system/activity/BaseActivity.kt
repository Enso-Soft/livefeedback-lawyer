package com.lafi.lawyer.core.design_system.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

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

        // WindowInsets 리스너와 애니메이션 콜백을 함께 설정
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            // 초기 시스템 바 인셋만 적용 (키보드는 애니메이션 콜백에서 처리)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

            // 둘 중 더 큰 값으로 bottom 패딩 설정
            val bottom = maxOf(systemBars.bottom, ime.bottom)
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom)

            // WindowInsets를 소비하지 않고 반환하여 하위 뷰에서도 처리 가능하도록 함
            insets
        }

        ViewCompat.setWindowInsetsAnimationCallback(binding.root, object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            override fun onPrepare(animation: WindowInsetsAnimationCompat) {
                super.onPrepare(animation)
            }

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

            override fun onEnd(animation: WindowInsetsAnimationCompat) {
                super.onEnd(animation)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
