package com.saeidi.baseapplication.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import com.saeidi.baseapplication.Application
import com.saeidi.baseapplication.R

object Style {

    fun getAccentColor(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.data
    }

    fun getBackgroundColor(context: Context): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
        return typedValue.data
    }

    fun inNightMode(context: Context): Boolean {
        val currentNightMode =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    fun getTextSizeNano(context: Context): Float {
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_nano) // sample:10.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }

    fun getTextSizeUltralight(context: Context): Float {
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_ultralight) // sample:12.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }

    fun getTextSizeLight(context: Context): Float {
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_light) // sample:14.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }

    fun getTextSizeRegular(context: Context): Float {
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_regular) // sample:16.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }

    fun getTextSizeMedium(context: Context): Float {
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_medium) // sample:18.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }


    fun getTextSizeBold(context: Context): Float {
        //todo("get font size style from pref")
        val ta: TypedArray = context.obtainStyledAttributes(
            Application.currentFontScaleStyle,
            R.styleable.FontStyle
        )
        var text = ta.getString(R.styleable.FontStyle_text_size_bold) // sample:20.0sp
        ta.recycle()
        text = text!!.replace("sp", "")
        return text.toFloat()
    }

    fun getAccentCircle8dp(context: Context): Drawable {
        val bg = AppCompatResources.getDrawable(context,R.drawable.ic_circle_8dp) as GradientDrawable
        bg.setColor(getAccentColor(context))
        return bg
    }

    fun getAccentCircle12dp(context: Context): Drawable {
        val bg = AppCompatResources.getDrawable(context,R.drawable.ic_circle_12dp) as GradientDrawable
        bg.setColor(getAccentColor(context))
        return bg
    }

    fun getRectangle2dp(context: Context, color: Int): Drawable {
        val bg = AppCompatResources.getDrawable(context,R.drawable.btn_bg) as GradientDrawable
        bg.setColor(color)
        return bg
    }

    fun getAccentRectangle18dp(context: Context): Drawable {
        val bg = AppCompatResources.getDrawable(context,R.drawable.ic_rectangle_18dp) as GradientDrawable
        bg.setColor(getAccentColor(context))
        return bg
    }

    enum class FontStyle(val resId: Int) {
        Small(R.style.FontStyle_Small), Normal(R.style.FontStyle_Normal), Large(R.style.FontStyle_Large), Xlarge(
            R.style.FontStyle_XLarge
        );
    }
}