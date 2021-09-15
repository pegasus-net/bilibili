package com.icarus.bilibili.data.jsonParser

import com.google.gson.Gson
import com.icarus.bilibili.data.VideoInfo

class PopularVideoParser(json: String):JsonParser<List<VideoInfo>>{
    private val bean: JsonBean = Gson().fromJson(json, JsonBean::class.java)

    data class JsonBean(
        var code: Int = -1,
        var data: Data? = null
    )

    data class Data(
        var no_more: Boolean = false,
        var list: List<VideoInfo>? = null
    )

    override fun getParserResult(): List<VideoInfo> {
        return bean.data?.list ?: ArrayList()
    }
}