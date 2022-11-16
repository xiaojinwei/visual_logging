package com.cj.visuallog.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.data.Status

class VisualLogAdapter : RecyclerView.Adapter<VisualLogAdapter.VH>() {

    private val mData:MutableList<CommunicationData> = mutableListOf()

    private var mItemClickListener:((adapter:VisualLogAdapter,view:View,position:Int,viewType:Int)->Unit)? = null

    fun setItemClickListener(l:(adapter:VisualLogAdapter,view:View,position:Int,viewType:Int)->Unit){
        this.mItemClickListener = l
    }

    fun notify(data:List<CommunicationData>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visual_log_list, parent, false)
        return VH(view).apply {
            itemView.setOnClickListener {
                mItemClickListener?.invoke(this@VisualLogAdapter,it,layoutPosition,viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun getItemKey(position: Int): Int {
        return mData[position].key
    }

    class VH(view: View) : RecyclerView.ViewHolder(view){
        private val mStatusTv : TextView
        private val mSchemeTv : ImageView
        private val mMethodPathTv : TextView
        private val mHostTv : TextView
        private val mStartTimeTv : TextView
        private val mDurationTimeTv : TextView

        init {
            mStatusTv = view.findViewById(R.id.status)
            mSchemeTv = view.findViewById(R.id.scheme)
            mMethodPathTv = view.findViewById(R.id.method_path)
            mHostTv = view.findViewById(R.id.host)
            mStartTimeTv = view.findViewById(R.id.start_time)
            mDurationTimeTv = view.findViewById(R.id.duration_time)

            mSchemeTv.imageTintList = ColorStateList.valueOf(Color.BLACK)
        }

        fun bind(data:CommunicationData){
            mStatusTv.text = data.getHttpStatusString()
            mSchemeTv.setImageResource(if(data.request.url.isHttps) R.drawable.ic_https_24 else R.drawable.ic_http_24)
            mMethodPathTv.text = "${data.request.method} ${data.request.url.encodedPath}"
            mHostTv.text = data.request.url.host
            mStartTimeTv.text = data.getStartTimeString()
            mDurationTimeTv.text = data.getDurationTimeString()

            mStatusTv.setTextColor(data.getTextColor())
            mStatusTv.textSize = if(data.status == Status.Requested) 11f else 14f
        }
    }


}