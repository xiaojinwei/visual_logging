package com.cj.visuallog.ext

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar

fun Toolbar.addMenu(title:String,isAlwaysShow:Boolean = true,click:(item: MenuItem?)->Boolean):MenuItem{
    val saveMenu = menu.add(title)
    saveMenu.setOnMenuItemClickListener {item-> click(item) }
    saveMenu.setShowAsAction(if(isAlwaysShow)MenuItem.SHOW_AS_ACTION_ALWAYS else MenuItem.SHOW_AS_ACTION_NEVER)
    return saveMenu
}

fun Toolbar.addMenu(title:String,iconRes:Int,iconAlwaysShow:Boolean = true,isAlwaysShow:Boolean = true,click:(item: MenuItem?)->Boolean):MenuItem{
    val saveMenu = menu.add(title)
    menu.setIconsVisible(iconAlwaysShow)
    saveMenu.setIcon(iconRes)
    saveMenu.setOnMenuItemClickListener {item-> click(item) }
    saveMenu.setShowAsAction(if(isAlwaysShow)MenuItem.SHOW_AS_ACTION_ALWAYS else MenuItem.SHOW_AS_ACTION_NEVER)
    return saveMenu
}

@SuppressLint("RestrictedApi")
fun Menu.setIconsVisible(visible:Boolean){
    if(this is MenuBuilder){
        setOptionalIconsVisible(visible)
    }
}


fun Toolbar.setTitleAndBack(title:String, @DrawableRes backResId:Int,back:()->Unit){
    this.title = title
    setNavigationIcon(backResId)
    setNavigationOnClickListener { view ->
       back()
    }
}