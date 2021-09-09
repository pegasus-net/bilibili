@file:JvmName("Tool")

package com.icarus.bilibili

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.random.Random


fun Double.percent(): Boolean {
    return Random.nextDouble() <= this
}


fun <T> T.log() {
    Log.e("TAG", this.toString())
}

fun <T> T.print() {
    println(this)
}


fun View.visible(b: Boolean) {
    visibility = if (b) View.VISIBLE else View.GONE
}



