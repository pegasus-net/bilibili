package com.icarus.bilibili

import com.google.gson.Gson

class PopularVideoParser(json: String) {
    private val bean: JsonBean = Gson().fromJson(json, JsonBean::class.java)

    data class JsonBean(
        var code: Int = -1,
        var data: Data? = null
    )

    data class Data(
        var no_more: Boolean = false,
        var list: List<VideoInfo>? = null
    )

    fun getVideoList(): List<VideoInfo> {
        return bean.data?.list ?: ArrayList()
    }
}