package com.cj.visuallog.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.cj.visuallog.R
import com.cj.visuallog.utils.Utils

class ItemHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mTitleTv: TextView
    private var mCopyIv: ImageView? = null
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
        val lpTitle = mTitleTv.layoutParams as LinearLayout.LayoutParams
        lpTitle.weight = 1.0f
        mTitleTv.layoutParams = lpTitle

        setPadding(0,Utils.dp2px(getContext(),4.0f).toInt(),0,Utils.dp2px(getContext(),4.0f).toInt())
    }

    private fun showCopyView(){
        if(mCopyIv == null){
            mCopyIv = ImageView(context).apply {
                setImageResource(R.drawable.ic_content_copy_24)
            }
        }
        if(mCopyIv!!.parent != null){
            mCopyIv!!.isVisible = true
            return
        }
        addView(mCopyIv)
        val lpCopy = mCopyIv!!.layoutParams as LinearLayout.LayoutParams
        lpCopy.width = Utils.dp2px(context,18f).toInt()
        lpCopy.height = Utils.dp2px(context,18f).toInt()
        mCopyIv!!.layoutParams = lpCopy

        mCopyIv!!.setOnClickListener {
            val value = copyValue?:copyValueFun?.invoke()
            if(value?.isNotBlank() == true){
                Utils.copyToClipboard(context,value,true)
            }
        }
    }

    private fun hideCopyView(){
        if(mCopyIv != null){
            mCopyIv!!.isVisible = false
        }
    }

    fun setTitleAndCopyValue(title:String,copyValue:String?,copyValueFun:(()->String?)?){
        mTitleTv.text = title
        this.copyValue = copyValue
        this.copyValueFun = copyValueFun
        if(copyValue != null || copyValueFun != null){
            showCopyView()
        }else{
            hideCopyView()
        }
    }

}