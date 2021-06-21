package com.saeidi.baseapplication.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.saeidi.baseapplication.R
import com.saeidi.baseapplication.utils.*

class CardItem : ConstraintLayout {

    var text: String = ""
        set(value) {
            field = value
            display.text = value
            display.isFocusableInTouchMode = false
            removeButton.isFocusableInTouchMode = false
        }

    private var display: AppCompatTextView
    private var removeButton: AppCompatImageView
    private var callBack: EventCallBack? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        background = AppCompatResources.getDrawable(context, R.drawable.rounded_border_bg_12dp)

        var layoutParams = getTextViewLayoutParam(isEnabled)

        display = AppCompatTextView(context).apply {
            gravity = Gravity.CENTER_VERTICAL
            typeface = Fonts.bold()
            setOnClickListener {
                if (this@CardItem.isEnabled) {
                    callBack?.onContentClick()
                }
            }
        }
        addView(display, layoutParams)

        removeButton = AppCompatImageView(context).apply {
            id = R.id.removeBtn
            setPadding(Screen.dp(4f), Screen.dp(4f), Screen.dp(4f), Screen.dp(4f))
            setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_cross))
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.secondary_color))
            )
            setBackgroundResource(LayoutUtil.getSelectableItemBackgroundBorderLess(context))
            if (isEnabled) {
                visible()
            } else {
                gone()
            }
            setOnClickListener {
                if (isEnabled) {
                    callBack?.onRemoveClick()
                }
            }
        }
        layoutParams = LayoutParams(Screen.dp(32f), Screen.dp(32f)).apply {
            endToEnd = LayoutParams.PARENT_ID
            topToTop = LayoutParams.PARENT_ID
            bottomToBottom = LayoutParams.PARENT_ID
        }
        addView(removeButton, layoutParams)
    }

    fun setCallBack(callBack: EventCallBack) {
        this.callBack = callBack
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        removeButton.apply {
            if (enabled) {
                visible()
            } else {
                gone()
            }
        }

        display.layoutParams = getTextViewLayoutParam(enabled)
    }

    private fun getTextViewLayoutParam(isEnable: Boolean) =
        LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            constrainedWidth = true
            startToStart = LayoutParams.PARENT_ID
            endToStart = R.id.removeBtn
            topToTop = LayoutParams.PARENT_ID
            bottomToBottom = LayoutParams.PARENT_ID
            LayoutUtil.setMargin(
                this,
                Screen.dp(12f),
                Screen.dp(4f),
                Screen.dp(if (isEnable) 8f else 12f),
                Screen.dp(4f)
            )
        }

    interface EventCallBack {
        fun onContentClick()
        fun onRemoveClick()
    }
}