package com.cj.visuallog.data

import okhttp3.Handshake
import okhttp3.Headers

data class ResponseData(
    var request:RequestData,
    var protocol:String,
    var message: String,
    var code: Int,
    var handshake: Handshake?,
    var headers: Headers,
    var body:String?,
    var contentType: String?,
    var contentLength: Long?,
    var sentRequestAtMillis: Long,
    var receivedResponseAtMillis: Long,
    var customMap:Map<String,Any>? = null,//用户自定义展示数据
){
    companion object{

    }
}
