package com.cj.visuallog.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.cj.visuallog.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TextCopyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setTextColor(Color.BLACK)
        setTextSize(TypedValue.COMPLEX_UNIT_SP,14f)

        setOnClickListener {
            val value = text.toString()
            if(value.isNotBlank()){
                MaterialAlertDialogBuilder(context)
                    .setItems(arrayOf("Copy")) { dialog, what ->
                        if (what == 0) {
                            Utils.copyToClipboard(context, value, true)
                        }
                    }.show()
            }
        }
    }

}