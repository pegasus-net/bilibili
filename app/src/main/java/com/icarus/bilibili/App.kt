package com.icarus.bilibili

import android.app.Application
import org.litepal.LitePal

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
//        KeyWord("29440965","直播录像",false).save()
//        KeyWord("10462362","天天素材库",true).save()
//        KeyWord("10462362","逗鱼时刻",true).save()
    }
}