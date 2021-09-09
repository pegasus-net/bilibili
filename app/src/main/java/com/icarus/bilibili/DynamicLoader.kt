package com.icarus.bilibili

import okhttp3.OkHttpClient
import okhttp3.Request

class DynamicLoader : Loader<VideoInfo>() {
    val DYNAMIC_NEW =
        "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/dynamic_new?type_list=8"
    val DYNAMIC_HISTORY =
        "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/dynamic_history?type_list=8&offset_dynamic_id="

    private val client = OkHttpClient()

    override fun load(offset: String?): List<VideoInfo> {
        val response = client.newCall(getRequest(offset)).execute()
        val json = response.body?.string() ?: ""
        val parser = DynamicVideoParser(json)
        this.offset = parser.getOffset()
        hasMore = this.offset != null
        return parser.getVideoList()
    }


    private fun getRequest(offset: String?): Request {
        return if (offset == null) {
            Request.Builder()
                .url(DYNAMIC_NEW)
                .header("Cookie", "SESSDATA=dc12be4e,1646727137,ddbbe*91")
                .build()
        } else {
            Request.Builder()
                .url(DYNAMIC_HISTORY + offset)
                .header("Cookie", "SESSDATA=dc12be4e,1646727137,ddbbe*91")
                .build()
        }
    }


}