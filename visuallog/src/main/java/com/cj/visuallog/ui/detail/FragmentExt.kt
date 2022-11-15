package com.cj.visuallog.ui.detail

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cj.visuallog.utils.Utils
import com.cj.visuallog.widget.ItemHeaderView
import com.cj.visuallog.widget.ItemView
import com.cj.visuallog.widget.TextCopyView

fun Fragment.addTextCopyView(container: ViewGroup, text:String){
    container.addView(TextCopyView(requireActivity()).apply {
        setText(text)
    })
}

fun Fragment.addDivider(container: ViewGroup){
    val view = View(requireActivity()).apply {
        setBackgroundColor(Color.LTGRAY)
    }
    container.addView(view)
    val lp = view.layoutParams as ViewGroup.MarginLayoutParams
    lp.height = Utils.dp2px(requireContext(),1f).toInt()
    lp.width = ViewGroup.LayoutParams.MATCH_PARENT
    lp.topMargin = Utils.dp2px(requireContext(),4f).toInt()
    lp.bottomMargin = Utils.dp2px(requireContext(),4f).toInt()
    view.layoutParams = lp
}

fun Fragment.addItemHeaderView(container: ViewGroup, title:String, copyValue:String?, copyValueFun:(()->String?)?){
    container.addView(ItemHeaderView(requireActivity()).apply {
        setTitleAndCopyValue(title,copyValue, copyValueFun)
    })
}

fun Fragment.addItemView(container: ViewGroup, name:String, value:String?, valueColor:Int? = null){
    container.addView(ItemView(requireActivity()).apply {
        setNameValue(name, value)
        if(valueColor != null){
            setValueColor(valueColor)
        }
    })
}