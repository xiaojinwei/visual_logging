package com.cj.visuallog.codec

import com.cj.visuallog.data.CommunicationData

interface IHandleLog {
    fun handle(data: CommunicationData)

    companion object{
        val Default = DefaultHandleLog()
    }

    class DefaultHandleLog : IHandleLog{
        override fun handle(data: CommunicationData) {
        }
    }
}