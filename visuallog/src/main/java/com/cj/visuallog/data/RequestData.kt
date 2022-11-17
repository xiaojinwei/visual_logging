package com.cj.visuallog.data

import com.cj.visuallog.utils.ReflectUtil
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer

data class RequestData(
    var url: HttpUrl,
    var method: String,
    var headers: Headers,
    var body: String?,
    var mediaType: String?,
    var contentLength:Long?,
    var tags: Map<Class<*>, Any>,
){
    companion object {
        fun toRequestData(request: Request):RequestData{
            return RequestData(request.url.newBuilder().build(),request.method,request.headers.newBuilder().build(),
                readRequestBodyToString(request.body),request.body?.contentType()?.toString(),
                request.body?.contentLength(),ReflectUtil.getFieldValue(request.javaClass,request,"tags"))
        }

        fun readRequestBodyToString(body: RequestBody?):String?{
            if(body != null){
                val buffer = Buffer()
                body.writeTo(buffer)
                return buffer.readUtf8()
            }
            return null
        }
    }
}
