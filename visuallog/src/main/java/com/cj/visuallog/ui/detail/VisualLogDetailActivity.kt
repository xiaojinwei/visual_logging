package com.cj.visuallog.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.cj.visuallog.R
import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.manager.CommunicationManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.jessyan.autosize.internal.CancelAdapt

class VisualLogDetailActivity : AppCompatActivity(),CancelAdapt {

    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private lateinit var communicationData: CommunicationData

    private lateinit var fragments: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_log_detail)

        if(!initArguments()) return

        initView()

        initToolbar()

        tabFragment()

        initEvent()
    }

    private fun initEvent() {
        CommunicationManager.addListener(notifyCommunication)
    }

    private val notifyCommunication = {data: CommunicationData ->
        if(data.key == communicationData.key){
            refreshLog()
        }
    }

    private fun refreshLog() {
        lifecycleScope.launch(Dispatchers.Main) {
            if(::fragments.isInitialized){
                fragments.forEach { fragment ->
                    if(fragment is ILogRefresh){
                        fragment.onRefresh()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CommunicationManager.removeListener(notifyCommunication)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("${communicationData.request.method} ${communicationData.request.url.encodedPath}")
//        toolbar.apply {
//            title = "${communicationData.request.method} ${communicationData.request.url.encodedPath}"
//            setTitleTextColor(Color.WHITE)
//        }
    }

    private fun initArguments():Boolean{
        val key = intent?.getIntExtra("key", -1)
        if(key == -1){
            Toast.makeText(this,"key缺失",Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        val data = CommunicationManager.getCommunicationData(key!!)
        if(data == null){
            Toast.makeText(this,"无此请求数据",Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        this.communicationData = data
        return true
    }

    private fun tabFragment() {
        fragments = mutableListOf<Fragment>(GeneralLogFragment(communicationData),RequestLogFragment(communicationData),ResponseLogFragment(communicationData))
        val tabs = mutableListOf<String>("General","Request","Response")
        val adapter = FragmentAdapter(this,fragments)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager2,true,
            TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = tabs[position] }).attach()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager2)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}