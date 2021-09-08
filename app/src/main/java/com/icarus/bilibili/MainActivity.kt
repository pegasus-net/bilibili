package com.icarus.bilibili

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<VideoInfo>()
    private val refresh: SwipeRefreshLayout by lazy { findViewById(R.id.refresh) }
    private val videoList: RecyclerView by lazy { findViewById(R.id.video_list) }

    private lateinit var adapter: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = VideoAdapter(this, list)
        videoList.adapter = adapter
        videoList.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "排行榜"
        loadFirstPage()
        refresh.setOnRefreshListener {
            loadFirstPage()
        }
        videoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                val lastItemPosition = manager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 5 > adapter.itemCount) {
                    Log.e("TAG","loadNext")
                    loadNextPage()
                }
            }
        })
    }

    @Synchronized
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
            val videos = PopularVideoParser(json).getVideoList()
            runOnUiThread {
                if (page == 1) {
                    if (refresh.isRefreshing) refresh.isRefreshing = false
                    list.clear()
                }
                list.addAll(videos)
                adapter.notifyDataSetChanged()
                isLoad = false
            }
        }.start()
    }
}