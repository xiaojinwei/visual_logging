package com.cj.visuallog.ext

import com.cj.visuallog.utils.DeepCopy


//深拷贝
fun <T: Any> T.deepCopy(): T {
    return DeepCopy.deepCopy(this)
}
