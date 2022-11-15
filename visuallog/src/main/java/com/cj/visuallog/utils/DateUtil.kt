package com.cj.visuallog.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatTimestamp(timestamp: Long):String{
            if(isToday(timestamp)){
                return timestampToTimeHMS(timestamp)
            }else{
                return timestampToTime(timestamp)
            }
        }

        fun isToday(timeStamp : Long):Boolean{
            val date = Date(timeStamp)
            val today = Date()
            val sf = SimpleDateFormat("yyyyMMdd")
            val day = sf.format(date)
            val nowDay = sf.format(today)
            return day == nowDay
        }

        fun timestampToTime(timeStamp : Long):String{
            val date = Date(timeStamp)
            val pattern = "yyyy-MM-dd HH:mm:ss.SSS"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val dateStr = simpleDateFormat.format(date)
            return dateStr
        }

        fun timestampToTimeHMS(timeStamp : Long):String{
            val date = Date(timeStamp)
            val pattern = "HH:mm:ss.SSS"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val dateStr = simpleDateFormat.format(date)
            return dateStr
        }

        fun durationTime(startTimeStamp:Long,endTimeStamp:Long,isShort:Boolean = true):String {
            var diff = endTimeStamp - startTimeStamp
            if(diff > 0){
                val h = 1000 * 60 * 60
                val m = 1000 * 60
                val s = 1000
                val hours = diff / h
                diff %= h
                val minutes = diff / m
                diff %= m
                val seconds = diff / s
                diff %= s
                val milliseconds = diff
                if(isShort){
                    if(hours > 0){
                        return "${hours}h"
                    }else if(minutes > 0){
                        return "${minutes}m"
                    }else if(seconds > 0){
                        return "${seconds}s"
                    }else{
                        return "${milliseconds}ms"
                    }
                }else{
                    if(hours > 0){
                        return "${hours}h${minutes}m${seconds}s${milliseconds}ms"
                    }else if(minutes > 0){
                        return "${minutes}m${seconds}s${milliseconds}ms"
                    }else if(seconds > 0){
                        return "${seconds}s${milliseconds}ms"
                    }else{
                        return "${milliseconds}ms"
                    }
                }
            }else{
                return ""
            }
        }
    }
}