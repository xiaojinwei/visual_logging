package com.cj.visuallog

import com.cj.visuallog.codec.IHandleLog
import com.cj.visuallog.data.*
import com.cj.visuallog.manager.CommunicationManager
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class VisualLogInterceptor(private var handle:IHandleLog = IHandleLog.Default) : Interceptor {

    fun setHandleLog(handle:IHandleLog){
        this.handle = handle
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val key = request.hashCode()
        val communicationData = CommunicationData.create(
            key,
            System.currentTimeMillis(),
            RequestData.toRequestData(request),
            Status.Requested
        )
        CommunicationManager.apply {
            setCommunicationData(key,communicationData)
            handle.handle(communicationData)
            notify(communicationData)
        }
        var response:Response
        try{
            response = chain.proceed(request)
            response = processResponseBody(response,communicationData)
        }catch (e: IOException){
            processError(e,communicationData)
            throw e
        }
        return response
    }

    fun processError(e: IOException,communicationData:CommunicationData){
        communicationData.apply {
            endTime = System.currentTimeMillis()
            status = Status.Failed
            error = ErrorData(e)
        }
        handle.handle(communicationData)
        CommunicationManager.notify(communicationData)
    }

    fun processResponseBody(response:Response,communicationData:CommunicationData):Response{

        val responseData = ResponseData(communicationData.request,response.protocol.toString(),
        response.message,response.code,response.handshake,response.headers.newBuilder().build(),
        null,null,null,response.sentRequestAtMillis,response.receivedResponseAtMillis)
        communicationData.apply {
            endTime = System.currentTimeMillis()
            status = Status.Completed
            this.response = responseData
        }

        val body = response.body
        if(body != null){
            val bodyString = body.string()
            responseData.body = bodyString
            responseData.contentType = body.contentType().toString()
            responseData.contentLength = body.contentLength()
            val buildResponse =
                response.newBuilder().body(bodyString.toResponseBody(body!!.contentType())).build()
            handle.handle(communicationData)
            CommunicationManager.notify(communicationData)
            return buildResponse
        }else{
            handle.handle(communicationData)
            CommunicationManager.notify(communicationData)
            return response
        }
    }
}