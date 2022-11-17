package com.cj.visuallog.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.widget.LinearLayout
import android.widget.TextView
import com.cj.visuallog.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mNameTv:TextView
    private val mValueTv:TextView

    init {
        mNameTv = TextView(context).apply {
            setTextColor(Color.DKGRAY)
            setTextSize(COMPLEX_UNIT_SP,14f)
        }
        mValueTv = TextView(context).apply {
            setTextColor(Color.BLACK)
            setTextSize(COMPLEX_UNIT_SP,14f)
        }
        addView(mNameTv)
        addView(mValueTv)

        setPadding(0,getDimension(4.0f),0,getDimension(4.0f))

        setOnClickListener {
            val value = mValueTv.text.toString()
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

    private fun getDimension(size:Float):Int{
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, size, resources.displayMetrics).toInt()
    }

    fun setNameValue(name:String,value:String?){
        mNameTv.text = name
        mValueTv.text = value
    }

    fun setValueColor(color:Int){
        mValueTv.setTextColor(color)
    }

}