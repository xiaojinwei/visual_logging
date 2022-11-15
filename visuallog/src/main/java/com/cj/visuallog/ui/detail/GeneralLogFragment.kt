package com.cj.visuallog.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData

class GeneralLogFragment(private val data: CommunicationData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_general_log,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.findViewById<LinearLayout>(R.id.container)).apply {
            addItemHeaderView(this,"General",null,null)
            addItemView(this,"request url: ",data.request.url.toString())
            addItemView(this,"request method: ",data.request.method)
            addItemView(this,"http status: ",data.response?.code?.toString())
            addItemView(this,"request time: ",data.getStartTimeStringLong())
            addItemView(this,"response time: ",data.getEndTimeStringLong())
            addItemView(this,"duration: ",data.getDurationTimeString(false))
            addItemView(this,"request status: ",data.getStatusString(), data.getTextColor())
        }
    }


}