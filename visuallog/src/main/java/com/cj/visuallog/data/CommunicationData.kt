package com.cj.visuallog.data

import android.graphics.Color
import com.cj.visuallog.utils.DateUtil

data class CommunicationData(
    val key:Int,
    val startTime:Long,
    var endTime:Long?,
    val request:RequestData,
    var response:ResponseData?,
    var error:ErrorData?,
    var status: Status,
    var timeout:TimeoutData,
){
    companion object{
        fun create(key: Int,startTime: Long,request: RequestData,status: Status,timeout: TimeoutData) =
            CommunicationData(key,startTime,null,request,null,null,status,timeout)
    }

    fun getHttpStatusString():String = when(status){
        Status.Requested -> "requesting"
        Status.Completed -> response?.code?.toString()?:""
        Status.Failed -> "error"
    }

    fun getStatusString():String = when(status){
        Status.Requested -> "requesting"
        Status.Completed -> "request completed"
        Status.Failed -> "request exception: ${error?.exception?.message}"
    }

    fun getStartTimeString() = DateUtil.formatTimestamp(startTime)

    fun getEndTimeString() = if(endTime != null) DateUtil.formatTimestamp(endTime!!) else ""

    fun getStartTimeStringLong() = DateUtil.timestampToTime(startTime)

    fun getEndTimeStringLong() = if(endTime != null) DateUtil.timestampToTime(endTime!!) else ""

    fun getDurationTimeString(isShort :Boolean = true) = if(endTime != null) DateUtil.durationTime(startTime,endTime!!,isShort) else ""

    /**
     * 如果endTime是null，返回-1
     */
    fun getDurationTime():Long {
        if(endTime == null) return -1
        return endTime!! - startTime
    }

    fun getTextColor():Int = when(status){
        Status.Requested -> Color.BLUE
        Status.Completed -> Color.GREEN
        Status.Failed -> Color.RED
    }
}

enum class Status {
    Requested, Completed, Failed
}