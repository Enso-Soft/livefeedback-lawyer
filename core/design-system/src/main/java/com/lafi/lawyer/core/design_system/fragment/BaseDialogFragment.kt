package com.lafi.lawyer.core.design_system.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<T : ViewBinding>(
    private val bindingFactory: (LayoutInflater) -> T
) : DialogFragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

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
            // Dialog가 취소되지 않도록 설정 (선택적)
            // setCanceledOnTouchOutside(false)
            // setCancelable(false)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            // Dialog 크기 설정
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            // 배경을 투명하게 설정
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
