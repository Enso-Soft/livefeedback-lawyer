package com.lafi.lawyer.core.design_system.component.top_bar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.lafi.lawyer.core.design_system.R

class TopBarLayout : FrameLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private lateinit var backButton: FrameLayout
    private lateinit var closeButton: FrameLayout
    private lateinit var tvTitle: TextView

    private var titleText: String? = null
    private var isBackVisible: Boolean? = null
    private var isCloseVisible: Boolean? = null

    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        // TypedArray 설정
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarLayout)
            setTypeArray(typedArray)
            typedArray.recycle()
        }

        // 레이아웃 인플레이션
        LayoutInflater.from(context)
            .inflate(R.layout.core_design_system_top_bar, this, true)

        backButton = findViewById(R.id.fl_back_button)
        closeButton = findViewById(R.id.fl_close_button)
        tvTitle = findViewById(R.id.tv_title)

        setupUi()
    }

    private fun setTypeArray(typedArray: TypedArray) {
        titleText = typedArray.getString(R.styleable.TopBarLayout_title)
        isBackVisible = typedArray.getBoolean(R.styleable.TopBarLayout_isBackVisible, false)
        isCloseVisible = typedArray.getBoolean(R.styleable.TopBarLayout_isCloseVisible, false)
    }

    private fun setupUi() {
        titleText?.let {
            tvTitle.text = it
            tvTitle.visibility = View.VISIBLE
        } ?: run {
            tvTitle.visibility = View.GONE
        }

        if (isBackVisible == true) {
            backButton.visibility = View.VISIBLE
        } else {
            backButton.visibility = View.INVISIBLE
        }

        if (isCloseVisible == true) {
            closeButton.visibility = View.VISIBLE
        } else {
            closeButton.visibility = View.INVISIBLE
        }
    }

    fun setTitle(titleText: String) {
        if (::tvTitle.isInitialized) {
            this.titleText = titleText
            tvTitle.text = titleText
        }
    }

    fun setVisibleBackButton(isVisible: Boolean) {
        if (::backButton.isInitialized) {
            this.isBackVisible = isVisible

            if (isVisible) {
                backButton.visibility = View.VISIBLE
            } else {
                backButton.visibility = View.INVISIBLE
            }
        }
    }

    fun setVisibleCloseButton(isVisible: Boolean) {
        if (::closeButton.isInitialized) {
            this.isCloseVisible = isVisible

            if (isVisible) {
                closeButton.visibility = View.VISIBLE
            } else {
                closeButton.visibility = View.INVISIBLE
            }
        }
    }

    fun setOnBackClickListener(listener: OnClickListener) {
        backButton.setOnClickListener(listener)
    }

    fun setOnCloseClickListener(listener: OnClickListener) {
        closeButton.setOnClickListener(listener)
    }
}