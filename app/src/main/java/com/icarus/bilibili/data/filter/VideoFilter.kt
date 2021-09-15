package com.icarus.bilibili.data.filter

import org.litepal.crud.LitePalSupport

data class VideoFilter(var mid: String, var titleKey: String, var weight: Int):LitePalSupport()
