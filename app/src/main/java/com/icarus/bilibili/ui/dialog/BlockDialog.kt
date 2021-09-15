package com.icarus.bilibili.ui.dialog

import a.icarus.component.BaseDialog
import a.icarus.utils.ToastUtil
import android.content.Context
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.icarus.bilibili.R
import com.icarus.bilibili.data.VideoInfo
import com.icarus.bilibili.data.filter.VideoFilter
import org.litepal.LitePal

class BlockDialog(context: Context, name:String) : BaseDialog(context, R.layout.dialog_block) {
    private val blockUp: TextView by lazy { findViewById(R.id.block_up) }
    private val blockVideo: TextView by lazy { findViewById(R.id.block_video) }

    init {
        blockUp.text = "屏蔽up主：${name}"
        blockVideo.text = "屏蔽该视频"
    }
}