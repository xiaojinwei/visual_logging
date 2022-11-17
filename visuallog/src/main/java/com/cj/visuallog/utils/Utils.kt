package com.cj.visuallog.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException

import org.json.JSONObject





class Utils {
    companion object {
        /**
         * 复制到剪切板
         */
        fun copyToClipboard(context:Context,text:String,isShowToast:Boolean = false){
            val cmb: ClipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cmb.setPrimaryClip(ClipData.newPlainText(null, text))
            if(isShowToast){
                Toast.makeText(context,"copy success",Toast.LENGTH_SHORT).show()
            }
        }

        fun dp2px(context: Context,size:Float):Float{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.resources.displayMetrics)
        }

        /**
         * 获取异常报告
         *
         * @param ex
         * @return
         */
        fun getErrorReport(context: Context, ex: Throwable): String {
            val exceptionStr = StringBuffer()
            val pinfo: PackageInfo
            try {
                pinfo = context.packageManager.getPackageInfo(context.packageName, 0)
                exceptionStr.append(
                    """
                Version: ${pinfo.versionName}
                
                """.trimIndent()
                )
                exceptionStr.append(
                    """
                VersionCode: ${pinfo.versionCode}
                
                """.trimIndent()
                )
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                exceptionStr.append("the application not found \n")
            }
            exceptionStr.append(
                """
            Android: ${Build.VERSION.RELEASE}(${Build.MODEL})
            
            """.trimIndent()
            )
            val stackTraceString: String = Log.getStackTraceString(ex)
            if (stackTraceString != null && stackTraceString.length > 0) {
                exceptionStr.append(
                    """
                $stackTraceString
                
                """.trimIndent()
                )
            } else {
                exceptionStr.append(
                    """
                Exception: ${ex.message}
                
                """.trimIndent()
                )
                val elements = ex.stackTrace
                for (i in elements.indices) {
                    exceptionStr.append(
                        """
                    ${elements[i]}
                    
                    """.trimIndent()
                    )
                }
            }
            return exceptionStr.toString()
        }

        fun tryJsonFormat(contentType:String?,bodyString: String):String{
            return if(isJsonContentType(contentType)){
                jsonFormat(bodyString)
            }else{
                bodyString
            }
        }

        fun isJsonContentType(contentType:String?):Boolean{
            return contentType?.contains("application/json")?:false
        }

        /**
         * json格式化
         */
        fun jsonFormat(bodyString: String): String {
            return try {
                if (bodyString.startsWith("{")) {
                    val jsonObject = JSONObject(bodyString)
                    jsonObject.toString(4)
                } else if (bodyString.startsWith("[")) {
                    val jsonArray = JSONArray(bodyString)
                    jsonArray.toString(4)
                } else {
                    bodyString
                }
            } catch (e: JSONException) {
                bodyString
            }
        }
    }
}