package com.cj.visuallog

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.cj.visuallog.codec.IHandleLog
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.data.Status
import com.cj.visuallog.ext.addMenu
import com.cj.visuallog.ui.VisualLogActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private val okHttpClient:OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(VisualLogInterceptor(object :IHandleLog{
            override fun handle(data: CommunicationData) {
                when(data.status){
                    Status.Requested -> {
                        if(data.request.body == null){
                            data.request.mediaType = "application/json"
                            data.request.body = "{\"test\":\"测试数据\"}"
                        }
                    }
                    Status.Completed -> {}
                    Status.Failed -> {}
                }
            }
        }))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar()
    }

    private fun initToolbar() {
        toolbar.apply {
            title = "Visual Log"
            setTitleTextColor(Color.WHITE)

            addMenu("Log",true){
                startActivity(Intent(this@MainActivity,VisualLogActivity::class.java))
                true
            }
        }
    }

    fun requestGet(view: View){
        requestHttp()
    }

    private fun requestHttp(){
        val url = "https://www.wanandroid.com/article/top/json"
        val request = Request.Builder().url(url).build()
        val newCall = okHttpClient.newCall(request)
        newCall.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.body?.string())
            }

        })
    }
}