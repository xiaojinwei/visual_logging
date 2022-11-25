package com.cj.visuallog.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.utils.DateUtil
import com.cj.visuallog.utils.Utils

class ResponseLogFragment(private val data: CommunicationData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_response_log,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val response = data.response
        (view.findViewById<LinearLayout>(R.id.container)).apply {
            if(response != null){
                if(response.headers.size > 0){
                    addItemHeaderView(this,"Response Header",null) { response.headers.toString() }
                    response.headers.names().forEach {name->
                        addItemView(this,"${name}: ", response.headers[name])
                    }
                    addDivider(this)
                }
                addItemHeaderView(this,"Response Other",null,null)
                addItemView(this,"status code: ",response.code.toString())
                addItemView(this,"status message: ",response.message)
                addItemView(this,"protocol: ",response.protocol)
                addItemView(this,"send request at millis: ",DateUtil.timestampToTime(response.sentRequestAtMillis))
                addItemView(this,"received response at millis: ",DateUtil.timestampToTime(response.receivedResponseAtMillis))
                addItemView(this,"duration: ",DateUtil.durationTime(response.sentRequestAtMillis,response.receivedResponseAtMillis,false))
                addDivider(this)
                addItemHeaderView(this,"Handshake",null,null)
                addTextCopyView(this,response.handshake?.toString()?:"")
                addDivider(this)
                if(response.customMap?.isNotEmpty() == true){
                    addItemHeaderView(this,"Custom",null,null)
                    response.customMap!!.forEach { entry->
                        addItemView(this,"${entry.key}: ",entry.value.toString())
                    }
                    addDivider(this)
                }
                if(response.body != null){
                    addItemHeaderView(this,"Response Body",null){response.body!!}
                    addItemView(this,"content type: ",response.contentType)
                    addItemView(this,"content length: ",response.contentLength?.toString())
                    addTextCopyView(this,Utils.tryJsonFormat(response.contentType,response.body!!))
                }
            }else if(data.error != null){
                val errorReport = Utils.getErrorReport(requireContext(), data.error!!.exception)
                addItemHeaderView(this,"Response Error",null,null)
                addTextCopyView(this,errorReport)
            }
        }

    }

}