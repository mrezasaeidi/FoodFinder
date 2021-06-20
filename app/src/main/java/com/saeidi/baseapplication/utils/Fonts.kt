package com.saeidi.baseapplication.utils

import android.graphics.Typeface
import com.saeidi.baseapplication.AndroidContext
import java.util.*

object Fonts {
    private val typefaces = HashMap<String, Typeface>()
    private fun load(name: String): Typeface {
        if (typefaces.containsKey(name)) {
            return typefaces[name]!!
        }
        val typeface = Typeface.createFromAsset(
            AndroidContext.context.assets,
            "vazir$name.ttf"
        )
        if (typeface != null) {
            typefaces[name] = typeface
        }
        return typeface
    }

    fun bold(): Typeface {
        return load("Bold")
    }

    fun black(): Typeface {
        return load("Black")
    }

    fun medium(): Typeface {
        return load("Medium")
    }

    fun regular(): Typeface {
        return load("")
    }

    fun light(): Typeface {
        return load("Light")
    }

    fun thin(): Typeface {
        return load( "Thin")
    }
}