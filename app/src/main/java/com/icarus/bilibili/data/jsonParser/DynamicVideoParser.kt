package com.icarus.bilibili.data.jsonParser

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.icarus.bilibili.data.VideoInfo
import com.icarus.bilibili.log

class DynamicVideoParser(json: String):JsonParser<List<VideoInfo>> ,PagerLoader{
    private val gson = Gson()
    private val bean: JsonBean = gson.fromJson(json, JsonBean::class.java)

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

    override fun getParserResult(): List<VideoInfo> {
        return bean.data?.cards?.map { gson.fromJson(it.videoInfo, VideoInfo::class.java) }
            ?: ArrayList()
    }

    override fun nextPagerIndex(): String? {
        bean.data?.run {
            if (next_offset != null) return next_offset
            if (history_offset != null) return history_offset
        }
        return null
    }
}