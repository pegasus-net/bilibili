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

class FilterDialog(context: Context, author: VideoInfo.Author) : BaseDialog(context, R.layout.dialog_filter) {
    private val editText: EditText by lazy { findViewById(R.id.editText) }
    private val weightBar: SeekBar by lazy { findViewById(R.id.weight_bar) }
    private val weight: TextView by lazy { findViewById(R.id.weight) }
    private val videoAuthor: TextView by lazy { findViewById(R.id.video_author) }
    private val add: TextView by lazy { findViewById(R.id.add) }
    private val reset: TextView by lazy { findViewById(R.id.reset) }

    init {
        weightBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                weight.text = (progress - 5).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        add.setOnClickListener {
            val weight = weightBar.progress - 5
            val key = editText.text.toString().trim()
            if (weight != 0 && key.isNotEmpty()) {
                VideoFilter(author.mid!!, key, weight).save()
            } else {
                ToastUtil.show("无效的过滤器")
            }
            dismiss()
        }

        reset.setOnClickListener {
            val filters = LitePal.where("mid = ?", author.mid).find(VideoFilter::class.java)
            filters.forEach {
                it.delete()
            }
            ToastUtil.show("过滤器已清空")
        }
        videoAuthor.text = author.name
    }
}