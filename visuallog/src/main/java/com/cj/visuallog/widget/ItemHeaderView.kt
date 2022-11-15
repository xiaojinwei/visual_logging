package com.cj.visuallog.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import com.cj.visuallog.utils.Utils

class ItemHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mTitleTv: TextView
    private var copyValueFun:(()->String?)? = null
    private var copyValue:String? = null

    init {
        mTitleTv = TextView(context).apply {
            setTextColor(Color.BLACK)
            setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            if(copyValue != null || copyValueFun != null){
                setOnClickListener {
                    val value = copyValue?:copyValueFun?.invoke()
                    if(value != null){
                        //copy
                        Utils.copyToClipboard(context,value)
                    }
                }
            }
        }
        addView(mTitleTv)

        setPadding(0,Utils.dp2px(getContext(),4.0f).toInt(),0,Utils.dp2px(getContext(),4.0f).toInt())
    }

    fun setTitleAndCopyValue(title:String,copyValue:String?,copyValueFun:(()->String?)?){
        mTitleTv.text = title
        this.copyValue = copyValue
        this.copyValueFun = copyValueFun
    }

}