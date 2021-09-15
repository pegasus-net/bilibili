package com.icarus.bilibili.ui.fragment

import a.icarus.component.BaseFragment
import android.annotation.SuppressLint
import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icarus.bilibili.R
import com.icarus.bilibili.adapter.VideoAdapter
import com.icarus.bilibili.data.VideoInfo
import com.icarus.bilibili.data.filter.PopularBlock
import com.icarus.bilibili.data.jsonParser.PopularVideoParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.litepal.LitePal
import kotlin.math.abs


class PopularListFragment : BaseFragment() {

    override fun setLayout(): Int {
        return R.layout.fragment_list
    }

    private lateinit var videoList: RecyclerView

    override fun initView(view: View) {
        videoList = findViewById(R.id.video_list)
    }

    private lateinit var adapter: VideoAdapter
    private val dataList = ArrayList<VideoInfo>()
    private var mHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val videoInfo = dataList[pos]
            PopularBlock(videoInfo.avID, null).save()
            dataList.removeAt(pos)
            adapter.notifyItemRemoved(pos)
            if (dataList.size < 10) {
                loadNextPage()
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val alpha = 1 - abs(dX) / viewHolder.itemView.width
                    .toFloat()
                viewHolder.itemView.alpha = alpha
            }
        }
    })


    override fun initData() {
        adapter = VideoAdapter(mActivity, dataList)
        videoList.adapter = adapter
        videoList.layoutManager = LinearLayoutManager(mActivity)
        mHelper.attachToRecyclerView(videoList)
        loadFirstPage()
    }

    override fun initListener() {
        videoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                val lastItemPosition = manager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 10 > adapter.itemCount) {
                    loadNextPage()
                }
            }
        })
    }

    private fun loadFirstPage() {
        loadData(1)
    }

    private fun loadNextPage() {
        if (currentPage < 20) {
            loadData(currentPage + 1)
        }
    }

    private var isLoad = false
    private val client = OkHttpClient()
    private var currentPage = 0

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(page: Int) {
        if (isLoad) return
        Thread {
            isLoad = true
            currentPage = page
            val request = Request.Builder()
                .url("https://api.bilibili.com/x/web-interface/popular?pn=$page")
                .build()
            val response = client.newCall(request).execute()
            val json = response.body?.string() ?: ""
            val videos = PopularVideoParser(json).getParserResult().filter {
                val blockAvId =
                    LitePal.where("avId = ?", it.avID).findFirst(PopularBlock::class.java)
                val blockMid =
                    LitePal.where("mid = ?", it.author?.mid).findFirst(PopularBlock::class.java)
                blockAvId == null && blockMid == null && (if (page == 1) true else !dataList.contains(
                    it
                ))
            }
            mActivity.runOnUiThread {
                isLoad = false
                if (page == 1) {
                    dataList.clear()
                }
                dataList.addAll(videos)
                adapter.notifyDataSetChanged()
                if (dataList.size < 10) {
                    loadNextPage()
                }
            }
        }.start()
    }
}