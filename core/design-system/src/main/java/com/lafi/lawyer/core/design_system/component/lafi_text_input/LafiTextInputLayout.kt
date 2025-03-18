package com.lafi.lawyer.core.design_system.component.lafi_text_input

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.lafi.lawyer.core.design_system.R

class LafiTextInputLayout : FrameLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private var LABEL_DEFAULT_SIZE = 16f
    private var LABEL_FOCUS_SIZE = 12f

    private var labelText: String? = null
    private var _animationDuration: Long? = null
    private val animationDuration: Long get() = _animationDuration ?: 200L


    private lateinit var flTextInputField: FrameLayout
    private lateinit var etInputText: EditText
    private lateinit var tvLabel: TextView
    private lateinit var tvErrorText: TextView

    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        // TypedArray 설정
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossTextInputLayout)
            setTypeArray(typedArray)
            typedArray.recycle()
        }

        // 레이아웃 인플레이션
        LayoutInflater.from(context)
            .inflate(R.layout.core_design_system_layout_lafi_text_input, this, true)

        // 뷰 참조
        flTextInputField = findViewById(R.id.fl_component_text_input_field)
        etInputText = findViewById(R.id.et_component_text_input_field)
        tvLabel = findViewById(R.id.tv_component_text_input_field_label)
        tvErrorText = findViewById(R.id.tv_component_text_error)

        setupUi()
        initListener()
    }

    private fun setTypeArray(typedArray: TypedArray) {
        labelText = typedArray.getString(R.styleable.CrossTextInputLayout_labelText)
        _animationDuration = typedArray.getInt(R.styleable.CrossTextInputLayout_duration, 200).toLong()
    }

    private fun setupUi() {
        tvLabel.text = labelText ?: "Label"
        tvErrorText.text = labelText ?: "label"
    }

    private fun initListener() {
        setOnClickListener { etInputText.requestFocus() }

        etInputText.setOnFocusChangeListener { _, hasFocus ->
            labelAnimation(etInputText)

            if (hasFocus) {
                tvLabel.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_900, null))
                flTextInputField.isSelected = true
                tvErrorText.visibility = View.GONE
            } else {
                tvLabel.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_700, null))
                flTextInputField.isSelected = false
            }
        }
    }

    private fun labelAnimation(editText: EditText) {
        if (editText.isFocused) {
            showKeyboard(editText = editText)

            if (editText.text.isBlank()) {
                // 텍스트 크기 애니메이션
                ValueAnimator.ofFloat(LABEL_DEFAULT_SIZE, LABEL_FOCUS_SIZE).apply {
                    duration = animationDuration
                    addUpdateListener { animation ->
                        val textSize = animation.animatedValue as Float
                        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                    }
                    start()

                    editText.post {
                        // TextView 이동 애니메이션
                        ObjectAnimator.ofFloat(tvLabel, "y", tvLabel.y, dpToPx(16))
                            .apply {
                                duration = animationDuration
                                start()
                            }
                    }
                }
            }
        } else {
            if (editText.text.isBlank()) {
                tvLabel.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationX(0f)
                    .translationY(0f)
                    .setDuration(animationDuration)
                    .start()

                ValueAnimator.ofFloat(LABEL_FOCUS_SIZE, LABEL_DEFAULT_SIZE).apply {
                    duration = animationDuration
                    addUpdateListener { animation ->
                        val textSize = animation.animatedValue as Float
                        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                    }
                    start()
                }
            }
        }
    }

    private fun showKeyboard(editText: EditText) {
        context?.let {
            editText.requestFocus()
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                ?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(editText: EditText? = null) {
        context?.let {
            windowToken?.let {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                    ?.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    fun setError(text: String) {
        tvErrorText.visibility = View.VISIBLE
        tvErrorText.text = text
        tvLabel.setTextColor(ResourcesCompat.getColor(resources, R.color.red, null))
    }

    private fun dpToPx(dp: Int): Float {
        return (dp * resources.displayMetrics.density)
    }

    // SavedState 구현
    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.inputText = etInputText.text.toString()
        savedState.errorText = tvErrorText.text.toString()
        savedState.isErrorVisible = tvErrorText.visibility == View.VISIBLE
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)
        post {
            etInputText.setText(state.inputText)
            tvErrorText.text = state.errorText
            tvErrorText.visibility = if (state.isErrorVisible) View.VISIBLE else View.GONE

            // 텍스트가 있으면 라벨 애니메이션 적용
            if (state.inputText?.isNotEmpty() == true) {
                tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LABEL_FOCUS_SIZE)
                tvLabel.y = dpToPx(16)
            }
        }
    }

    private class SavedState : BaseSavedState {
        var inputText: String? = null
        var errorText: String? = null
        var isErrorVisible: Boolean = false

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            inputText = parcel.readString()
            errorText = parcel.readString()
            isErrorVisible = parcel.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(inputText)
            out.writeString(errorText)
            out.writeInt(if (isErrorVisible) 1 else 0)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}