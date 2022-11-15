package com.cj.visuallog.data

import okhttp3.Handshake
import okhttp3.Headers

data class ResponseData(
    val request:RequestData,
    val protocol:String,
    val message: String,
    val code: Int,
    var handshake: Handshake?,
    val headers: Headers,
    var body:String?,
    var contentType: String?,
    var contentLength: Long?,
    val sentRequestAtMillis: Long,
    val receivedResponseAtMillis: Long,
){
    companion object{

    }
}
