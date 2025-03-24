package com.lafi.lawyer.core.design_system.component.keyboard_sync_layout

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.lafi.lawyer.core.design_system.R
import com.lafi.lawyer.core.util.keyboard_ovserver.LafiKeyboardObserver

class KeyboardSyncLayout : FrameLayout {
    companion object {
        private const val TAG = "KeyboardSyncLayout"
        const val SYNC_TYPE_KEYBOARD_UP = 0
        const val SYNC_TYPE_KEYBOARD_DOWN = 1
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    // 속성 값들
    private var startMarginDp = 0
    private var endMarginDp = 0
    private var topMarginDp = 0
    private var bottomMarginDp = 0
    private var radiusDp = 0
    private var syncType = SYNC_TYPE_KEYBOARD_UP

    // 원래 값 저장용
    private var originalHeight = 0
    private var isInitialized = false
    private var currentRadius = 0f

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeyboardSyncLayout)
            setTypeArray(typedArray)
        }

        clipToOutline = true

        post {
            originalHeight = height
            isInitialized = true
            setupKeyboardObserver()
            applyKeyboardChanges(0f)
        }
    }

    private fun setTypeArray(typedArray: TypedArray) {
        try {
            val marginAll = typedArray.getDimension(R.styleable.KeyboardSyncLayout_margin, -1f)

            // margin 속성이 설정되었으면 모든 마진에 적용
            if (marginAll >= 0) {
                startMarginDp = pxToDp(marginAll.toInt())
                endMarginDp = pxToDp(marginAll.toInt())
                topMarginDp = pxToDp(marginAll.toInt())
                bottomMarginDp = pxToDp(marginAll.toInt())
            } else {
                // 개별 마진 설정
                startMarginDp = pxToDp(typedArray.getDimension(R.styleable.KeyboardSyncLayout_startMargin, 0f).toInt())
                endMarginDp = pxToDp(typedArray.getDimension(R.styleable.KeyboardSyncLayout_endMargin, 0f).toInt())
                topMarginDp = pxToDp(typedArray.getDimension(R.styleable.KeyboardSyncLayout_topMargin, 0f).toInt())
                bottomMarginDp = pxToDp(typedArray.getDimension(R.styleable.KeyboardSyncLayout_bottomMargin, 0f).toInt())
            }

            // 코너 반경 읽기
            radiusDp = pxToDp(typedArray.getDimension(R.styleable.KeyboardSyncLayout_radius, 0f).toInt())

            // 동기화 타입 읽기
            syncType = typedArray.getInt(R.styleable.KeyboardSyncLayout_syncType, SYNC_TYPE_KEYBOARD_UP)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setupKeyboardObserver() {
        LafiKeyboardObserver.with(this)
            .setListener { _, _, percent ->
                applyKeyboardChanges(percent)
            }
    }

    private fun applyKeyboardChanges(percent: Float) {
        if (!isInitialized) return

        val currentPercent = when (syncType) {
            SYNC_TYPE_KEYBOARD_UP -> percent
            SYNC_TYPE_KEYBOARD_DOWN -> 1f - percent
            else -> percent
        }

        // 퍼센트에 따라 마진 조정 (dp 값에 퍼센트를 곱한 후 px로 변환)
        val startMargin = dpToPx(startMarginDp * currentPercent).toInt()
        val endMargin = dpToPx(endMarginDp * currentPercent).toInt()
        val topMargin = dpToPx(topMarginDp * currentPercent).toInt()
        val bottomMargin = dpToPx(bottomMarginDp * currentPercent).toInt()
        updateMargins(startMargin, endMargin, topMargin, bottomMargin)

        // 높이 조정
        val newHeight = originalHeight - bottomMargin
        layoutParams = layoutParams.apply {
            height = newHeight
        }

        // ViewOutlineProvider로 radius 적용
        val newRadius = dpToPx(radiusDp * currentPercent)
        updateRadius(newRadius)
    }

    // radius 업데이트 함수
    private fun updateRadius(radius: Float) {
        if (currentRadius != radius) {
            currentRadius = radius

            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, radius)
                }
            }
        }
    }

    // View의 마진을 설정하는 함수
    private fun updateMargins(start: Int, end: Int, top: Int, bottom: Int) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        params.apply {
            marginStart = start
            topMargin = top
            marginEnd = end
            bottomMargin = bottom
        }
        layoutParams = params
    }

    // px를 dp로 변환
    private fun pxToDp(px: Int): Int {
        return (px / resources.displayMetrics.density).toInt()
    }

    // dp를 px로 변환
    private fun dpToPx(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}
