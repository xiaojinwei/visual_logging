package com.cj.visuallog.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.utils.Utils

class RequestLogFragment(private val data: CommunicationData) : Fragment(),ILogRefresh {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_log,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewData(view)
    }

    private fun bindViewData(view: View?){
        val request = data.request
        (view?.findViewById<LinearLayout>(R.id.container))?.apply {
            removeAllViews()
            if(request.headers.size > 0){
                addItemHeaderView(this,"Request Header",null) { request.headers.toString() }
                request.headers.names().forEach {name->
                    addItemView(this,"${name}: ", request.headers[name])
                }
                addDivider(this)
            }
            val queryParameterNames = request.url.queryParameterNames
            if(queryParameterNames.isNotEmpty()){
                addItemHeaderView(this,"Query Parameters",null) { request.url.query}
                queryParameterNames.forEach { name->
                    addItemView(this,"${name}: ",request.url.queryParameterValues(name).toString())
                }
                addDivider(this)
            }
            val tags = request.tags
            if(tags.isNotEmpty()){
                addItemHeaderView(this,"Tags",null) { tags.toString()}
                tags.forEach { entry ->
                    addItemView(this,"${entry.key}: ",tags[entry.key].toString())
                }
                addDivider(this)
            }
            addItemHeaderView(this,"Request Other",null,null)
            addItemView(this,"method: ",request.method)
            addItemView(this,"path: ",request.url.encodedPath)
            addItemView(this,"base url: ","${if(request.url.isHttps)"https://" else "http://"}${request.url.host}")
            addItemView(this,"uri: ",request.url.toString())
            addItemView(this,"connect timeout millis: ",data.timeout.connectTimeoutMillis.toString())
            addItemView(this,"read timeout millis: ",data.timeout.readTimeoutMillis.toString())
            addItemView(this,"write timeout millis: ",data.timeout.writeTimeoutMillis.toString())
            addDivider(this)
            if(request.customMap?.isNotEmpty() == true){
                addItemHeaderView(this,"Custom",null,null)
                request.customMap!!.forEach { entry->
                    addItemView(this,"${entry.key}: ",entry.value.toString())
                }
                addDivider(this)
            }
            if(request.body != null){
                addItemHeaderView(this,"Request Body",null){request.body!!}
                addItemView(this,"mediaType: ",request.mediaType)
                addItemView(this,"contentLength: ",request.contentLength?.toString())
                addTextCopyView(this,Utils.tryJsonFormat(request.mediaType,request.body!!))
            }
        }
    }

    override fun onRefresh() {
        bindViewData(view)
    }

}