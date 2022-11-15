package com.cj.visuallog.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue

class TextCopyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setTextColor(Color.BLACK)
        setTextSize(TypedValue.COMPLEX_UNIT_SP,14f)
        setOnClickListener {

        }
    }

}