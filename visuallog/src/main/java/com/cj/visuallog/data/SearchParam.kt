package com.cj.visuallog.data

data class SearchParam(
    var scheme :SearchScheme = SearchScheme.ALL,
    var status :SearchStatus = SearchStatus.ALL,
    var time :SearchTimeOrderBy = SearchTimeOrderBy.None, //请求时间排序，优先级高于duration
    var duration :SearchDurationOrderBy = SearchDurationOrderBy.None, //持续时间排序，只有在请求时间不排序时生效
    var search :String = "",//模糊搜索
){

    fun isSearch(data: CommunicationData):Boolean{
        var f = true
        if(!search.isBlank()){
            f = data.request.url.toString().contains(search)
            if(!f) return f
        }
        when(status){
            SearchStatus.ALL -> {}
            SearchStatus.Request -> {
                f = data.status == Status.Requested
                if(!f) return f
            }
            SearchStatus.Response -> {
                f = data.status == Status.Completed
                if(!f) return f
            }
            SearchStatus.Error -> {
                f = data.status == Status.Failed
                if(!f) return f
            }
        }
        when(scheme){
            SearchScheme.ALL -> {}
            SearchScheme.Https -> {
                f = data.request.url.scheme == "https"
                if(!f) return f
            }
            SearchScheme.Http -> {
                f = data.request.url.scheme == "http"
                if(!f) return f
            }
        }
        return f
    }

    fun getOrderBy(a: CommunicationData,b: CommunicationData):Int{
        return when(time){
            SearchTimeOrderBy.None -> {
                return when(duration){
                    SearchDurationOrderBy.None -> 0
                    SearchDurationOrderBy.Asc -> a.getDurationTime().compareTo(b.getDurationTime())
                    SearchDurationOrderBy.Desc -> b.getDurationTime().compareTo(a.getDurationTime())
                }
            }
            SearchTimeOrderBy.Asc -> a.startTime.compareTo(b.startTime)
            SearchTimeOrderBy.Desc -> b.startTime.compareTo(a.startTime)
        }
    }

}

///协议
 enum class SearchScheme {
    ALL,  //所有协议
    Https,  //https
    Http, //http
}

///请求状态
 enum class SearchStatus {
    ALL,  //所有状态
    Request,  //请求状态
    Response,  //响应状态
    Error, //错误状态
}

///请求开始时间排序
 enum class SearchTimeOrderBy {
    None,  //不排序
    Asc,  //升序
    Desc, //降序
}

///请求持续时间排序
 enum class SearchDurationOrderBy {
    None,  //不排序
    Asc,  //升序
    Desc, //降序
}
