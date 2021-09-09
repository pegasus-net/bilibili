package com.icarus.bilibili

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.collections.ArrayList
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

//    private val list = ArrayList<VideoInfo>()
//    private val refresh: SwipeRefreshLayout by lazy { findViewById(R.id.refresh) }
//    private val videoList: RecyclerView by lazy { findViewById(R.id.video_list) }
//
//    private lateinit var adapter: VideoAdapter
//    private val dynamicLoader = DynamicLoader()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        adapter = VideoAdapter(this, list)
//        videoList.adapter = adapter
//        videoList.layoutManager = LinearLayoutManager(this)
//        supportActionBar?.title = "排行榜"
//        loadFirstPage()
//        refresh.setOnRefreshListener {
//            loadFirstPage()
//        }
//        videoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                val manager = recyclerView.layoutManager as LinearLayoutManager
//                val lastItemPosition = manager.findLastVisibleItemPosition()
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 10 > adapter.itemCount) {
//                   loadNextPage()
//                }
//            }
//        })
//        mHelper.attachToRecyclerView(videoList)
    }
//
//    @Synchronized
//    private fun loadFirstPage() {
//        loadData(1)
//    }
//
//    private fun loadNextPage() {
//        if (currentPage < 20) {
//            loadData(currentPage + 1)
//        }
//    }
//
//    private var isLoad = false
//    private val client = OkHttpClient()
//    private var currentPage = 0
//    private fun loadData(page: Int) {
//        if (isLoad) return
//        Thread {
//            isLoad = true
////            currentPage = page
////            val request = Request.Builder()
////                .url("https://api.bilibili.com/x/web-interface/popular?pn=$page")
////                .build()
////            val response = client.newCall(request).execute()
////            val json = response.body?.string() ?: ""
////            val videos = PopularVideoParser(json).getVideoList().filter {
////                val blockAvId =
////                    LitePal.where("avId = ?", it.avID).findFirst(PopularBlock::class.java)
////                val blockMid =
////                    LitePal.where("mid = ?", it.author?.mid).findFirst(PopularBlock::class.java)
////                blockAvId == null && blockMid == null && if (page == 1) true else !list.contains(it)
////            }
//            val videos = dynamicLoader.load()
//            runOnUiThread {
//                if (refresh.isRefreshing) refresh.isRefreshing = false
//                isLoad = false
//                if (page == 1) {
//                    list.clear()
//                }
//                list.clear()
//                list.addAll(videos)
//                adapter.notifyDataSetChanged()
//                if (list.size < 10) {
//                    loadNextPage()
//                }
//            }
//        }.start()
//    }
//
//    private var mHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//        0,
//        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//    ) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            return true
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val pos = viewHolder.adapterPosition
//            val videoInfo = list[pos]
//            PopularBlock(videoInfo.avID, null).save()
//            list.removeAt(pos)
//            adapter.notifyItemRemoved(pos)
//            if (list.size < 10) {
//                loadNextPage()
//            }
//        }
//
//        override fun onChildDraw(
//            c: Canvas,
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            dX: Float,
//            dY: Float,
//            actionState: Int,
//            isCurrentlyActive: Boolean
//        ) {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                val alpha = 1 - abs(dX) / viewHolder.itemView.width
//                    .toFloat()
//                viewHolder.itemView.alpha = alpha
//            }
//        }
//    })


}