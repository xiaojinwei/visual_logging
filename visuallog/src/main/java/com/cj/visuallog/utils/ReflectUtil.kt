package com.cj.visuallog.utils

class ReflectUtil {
    companion object{
        fun <T>getFieldValue(clazz: Class<*>,obj:Any?,fieldName:String):T{
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(obj) as T
        }
    }
}