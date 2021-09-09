package com.icarus.bilibili

import org.litepal.crud.LitePalSupport

data class KeyWord(var mid: String, var key: String, var allow: Boolean):LitePalSupport()
