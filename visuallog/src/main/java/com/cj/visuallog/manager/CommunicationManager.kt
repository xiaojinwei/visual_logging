package com.cj.visuallog.manager

import com.cj.visuallog.data.CommunicationData
import com.cj.visuallog.data.SearchParam
import java.util.LinkedHashMap
import java.util.concurrent.CopyOnWriteArraySet
import java.util.HashSet

object CommunicationManager {

    private var maxCount = 200

    private val dataMap : LinkedHashMap<Int,CommunicationData> = object : LinkedHashMap<Int,CommunicationData>(100,0.75f,true){
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, CommunicationData>): Boolean {
            if(size > maxCount){
                this.remove(eldest.key)
                return true
            }
            return super.removeEldestEntry(eldest)
        }
    }

    //private val listens : CopyOnWriteArraySet<(CommunicationData)->Unit> = CopyOnWriteArraySet()
    private val listens : HashSet<(CommunicationData)->Unit> = HashSet()

    @Synchronized
    fun getCommunicationData(key:Int):CommunicationData? = dataMap[key]

    @Synchronized
    fun setCommunicationData(key:Int,data:CommunicationData) {
        dataMap[key] = data
    }

    @Synchronized
    fun clear(){
        dataMap.clear()
    }

    @Synchronized
    fun search(param: SearchParam):List<CommunicationData>{
        val list = dataMap.values.toList().filter { param.isSearch(it) }.sortedWith(Comparator{a,b->
            param.getOrderBy(a,b)
        })
        return list
    }

    @Synchronized
    fun notify(data: CommunicationData){
        val iterator = listens.iterator()
        while (iterator.hasNext()){
            val listen = iterator.next()
            listen.invoke(data)
        }
    }

    @Synchronized
    fun addListener(listen:(CommunicationData)->Unit){
        if(!listens.contains(listen)){
            listens.add(listen)
        }
    }

    @Synchronized
    fun removeListener(listen:(CommunicationData)->Unit){
        listens.remove(listen)
    }

}