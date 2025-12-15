package com.lafi.lawyer.core.design_system.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<T : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> T
) : BottomSheetDialogFragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract val TAG: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingFactory(inflater)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // BottomSheet가 키보드에 의해 밀려나도록 설정
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

            // BottomSheet Dialog인 경우 추가 설정
            if (this is BottomSheetDialog) {
                setOnShowListener {
                    // BottomSheet의 루트 뷰에 WindowInsets 리스너 설정
                    val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    bottomSheet?.let { sheet ->
                        ViewCompat.setOnApplyWindowInsetsListener(sheet) { view, insets ->
                            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
                            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                            // IME가 표시될 때 bottom padding 적용
                            view.setPadding(
                                view.paddingLeft,
                                view.paddingTop,
                                view.paddingRight,
                                maxOf(ime.bottom, systemBars.bottom)
                            )
                            insets
                        }

                        // 키보드 애니메이션 처리
                        ViewCompat.setWindowInsetsAnimationCallback(sheet, object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                            override fun onProgress(
                                insets: WindowInsetsCompat,
                                runningAnimations: List<WindowInsetsAnimationCompat>
                            ): WindowInsetsCompat {
                                val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
                                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                                sheet.setPadding(
                                    sheet.paddingLeft,
                                    sheet.paddingTop,
                                    sheet.paddingRight,
                                    maxOf(ime.bottom, systemBars.bottom)
                                )
                                return insets
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
