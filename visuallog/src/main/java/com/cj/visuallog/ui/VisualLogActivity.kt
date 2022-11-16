package com.cj.visuallog.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.data.SearchParam
import com.cj.visuallog.data.SearchTimeOrderBy
import com.cj.visuallog.ext.addMenu
import com.cj.visuallog.manager.CommunicationManager
import com.cj.visuallog.ui.detail.VisualLogDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VisualLogActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisualLogAdapter
    private var dataList : List<CommunicationData> = mutableListOf()

    private val searchParam : SearchParam = SearchParam().apply { time = SearchTimeOrderBy.Desc }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_log)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        initToolbar()

        initRecyclerView()

        initEvent()

        refreshSearch()
    }

    private fun refreshSearch(){
        dataList = CommunicationManager.search(searchParam)
        lifecycleScope.launch(Dispatchers.Main) {
            adapter.notify(dataList)
        }
    }

    private val notifyCommunication = {data: CommunicationData ->
        refreshSearch()
    }

    private fun initEvent() {
        CommunicationManager.addListener(notifyCommunication)
    }

    override fun onDestroy() {
        super.onDestroy()
        CommunicationManager.removeListener(notifyCommunication)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter = VisualLogAdapter().apply {
            setItemClickListener{adapter, view, position, viewType ->
                val key = adapter.getItemKey(position)
                startActivity(Intent(this@VisualLogActivity,VisualLogDetailActivity::class.java).apply {
                    putExtra("key",key)
                })
            }
        }
        recyclerView.adapter = adapter
        adapter.notify(dataList)
    }

    private fun initToolbar() {
        toolbar.apply {
            title = "Request List"
            setTitleTextColor(Color.WHITE)

            addMenu("Delete",R.drawable.ic_delete_24,true,true){
                CommunicationManager.clear()
                refreshSearch()
                true
            }
            addMenu("Menu",R.drawable.ic_menu_24,true,true){

                true
            }
        }
    }

}