package com.icarus.bilibili

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class VideoInfo(

    var title: String?,
    @SerializedName("pic")
    var cover: String?,
    @SerializedName("owner")
    var author: Author?,
    @SerializedName("aid")
    var avID: String?,
    @SerializedName("bvid")
    var bvID: String?,
    @SerializedName("short_link")
    var link: String?,
    var duration: Long = 0,
    @SerializedName("rcmd_reason")
    var reason: Reason? = null,
    var ctime: Long = 0,
    var pubdate: Long = 0,
    var stat: Stat?

) {
    val linkAv: String
        get() {
            return "https://www.bilibili.com/video/av$avID"
        }
    val linkBv: String
        get() {
            return "https://www.bilibili.com/video/$bvID"
        }

    val view: String
        get() {
            val view = stat?.view ?: 0
            return if (view < 10000) {
                "${view}观看"
            } else {
                val f = view / 1000 / 10F
                f.toString().replace(".0", "") + "万观看"
            }
        }
    val time: String
        get() {
            val current = System.currentTimeMillis() / 1000
            return when ((current - pubdate) / 3600) {
                0L -> "刚刚"
                in 1 until 24 -> "${(current - pubdate) / 3600}小时前"
                else -> (pubdate * 1000).formatDate("MM-dd")
            }
        }
    val durationStr: String
        get() {
            val minute = duration / 60
            val second = duration % 60
            return String.format("%d:%02d",minute,second)
        }

    data class Author(var mid: String?, var name: String?, var face: String?)
    data class Reason(var content: String?, var corner_mark: Int)
    data class Stat(var view: Int, var favorite: Int, var coin: Int, var like: Int)

    private fun Long.formatDate(pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.CHINA)
        return dateFormat.format(Date(this))
    }
}
