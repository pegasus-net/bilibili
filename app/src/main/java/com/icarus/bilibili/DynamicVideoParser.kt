package com.icarus.bilibili

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class DynamicVideoParser(json: String) {
    private val bean: JsonBean = Gson().fromJson(json, JsonBean::class.java)

    data class JsonBean(
        var code: Int = -1,
        var data: Data? = null

    )

    data class Data(
        var hasMore: Int = 1,
        var next_offset: String? = null,
        var history_offset: String? = null,
        var cards: List<Card>? = null
    )

    data class Card(
        @SerializedName("card")
        var videoInfo: String?
    )

    fun getVideoList(): List<VideoInfo> {
        if (bean.code != 0) {
            bean.code.log()
        }
        return bean.data?.cards?.map { Gson().fromJson(it.videoInfo, VideoInfo::class.java) }
            ?: ArrayList()
    }

    fun getOffset(): String? {
        bean.data?.run {
            log()
            if (next_offset != null) return next_offset
            if (history_offset != null) return history_offset
        }
        return null
    }
}