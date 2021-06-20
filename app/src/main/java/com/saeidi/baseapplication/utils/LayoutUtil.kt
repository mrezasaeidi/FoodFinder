package com.saeidi.baseapplication.utils

import android.app.Activity
import android.content.Context
import com.saeidi.baseapplication.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object LayoutUtil {

    private lateinit var locale: String
    var rtl = false
    private var lang = Lang.FA

    fun init(context: Context) {
        rtl = context.resources.getBoolean(R.bool.rtl)
        locale = Locale.getDefault().language.lowercase(Locale.ROOT)
        lang = if (locale == Lang.EN.lang) {
            Lang.EN
        } else {
            Lang.FA
        }
    }

    enum class Lang(val lang: String, val country: String) {
        FA("fa", "IR"), EN("en", "US")
    }

    fun getLanguage(): Lang {
        return lang
    }

    fun formatNumber(value: Int): String {
        return NumberFormatting.toLocale(value.toString(), locale)
    }

    fun formatNumber(value: String): String {
        return NumberFormatting.toLocale(value, locale)
    }

    fun recreate(activity: Activity) {
        activity.recreate()
    }

    object NumberFormatting {
        fun toLocale(text: String, localeName: String): String {
            return if (localeName == Lang.FA.lang) {
                toFa(text)
            } else {
                text
            }
        }

        fun toFa(oldText: String): String {
            var text = oldText
            val eNums = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            val fNums = charArrayOf(
                '\u06f0',
                '\u06f1',
                '\u06f2',
                '\u06f3',
                '\u06f4',
                '\u06f5',
                '\u06f6',
                '\u06f7',
                '\u06f8',
                '\u06f9'
            )
            for (i in 0..9) {
                text = text.replace(eNums[i], fNums[i])
            }
            return text
        }

        fun toEn(oldText: String): String {
            var text = oldText
            val eNums = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            val fNums = charArrayOf(
                '\u06f0',
                '\u06f1',
                '\u06f2',
                '\u06f3',
                '\u06f4',
                '\u06f5',
                '\u06f6',
                '\u06f7',
                '\u06f8',
                '\u06f9'
            )
            for (i in 0..9) {
                text = text.replace(fNums[i], eNums[i])
            }
            return text
        }
    }

    fun formatPrice(price: Any?): String? {
        if (price == null) {
            return ""
        }
        val df = NumberFormat.getInstance() as DecimalFormat
        df.minimumFractionDigits = 0
        df.maximumFractionDigits = 0
        df.roundingMode = RoundingMode.FLOOR
        return df.format(price)
    }
}