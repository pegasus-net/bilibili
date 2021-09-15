package com.icarus.bilibili

import a.icarus.component.MonitorApplication
import android.app.Application
import org.litepal.LitePal

class App:MonitorApplication(){
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
    }
}