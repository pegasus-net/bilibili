package com.icarus.bilibili.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.icarus.bilibili.DynamicLoader
import com.icarus.bilibili.Loader
import com.icarus.bilibili.R
import com.icarus.bilibili.adapter.VideoAdapter
import com.icarus.bilibili.adapter.VideoAdapter2
import com.icarus.bilibili.data.VideoInfo
import com.icarus.bilibili.data.filter.VideoFilter
import org.litepal.LitePal

class VideoListFragment(private val dataLoader: Loader<VideoInfo>) : Fragment() {

    constructor() : this(DynamicLoader())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private lateinit var rootView: View
    private lateinit var videoList: RecyclerView
    private val list = ArrayList<VideoInfo>()
    private lateinit var adapter: VideoAdapter2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        videoList = view.findViewById(R.id.video_list)
        videoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager
                val lastItemPosition = manager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 10 > adapter.itemCount) {
                    loadData(false)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = VideoAdapter2(activity!!, list)
        videoList.adapter = adapter
        videoList.layoutManager = LinearLayoutManager(activity)
        dataLoader.filter = {
            getFilter(it)
        }
        loadData(true)
    }

    private var isLoad = false

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(reload: Boolean) {
        if (isLoad) return
        if (!dataLoader.hasMore) return
        Thread {
            isLoad = true
            val videos = if (reload) {
                dataLoader.reload()
            } else {
                dataLoader.load()
            }
            activity?.runOnUiThread {
                isLoad = false
                if (reload) {
                    list.clear()
                }
                list.addAll(videos)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }


    private fun getFilter(videoInfo: VideoInfo): Boolean {
        val title = videoInfo.title ?: ""
        val mid = videoInfo.author?.mid ?: ""
        val filters = LitePal.where("mid = ?", mid).find(VideoFilter::class.java)
        var videoWeight = 0
        filters.forEach { filter ->
            if (title.contains(filter.titleKey)) videoWeight += filter.weight
            if (filter.titleKey == "全部") videoWeight += filter.weight
        }
        return videoWeight >= 0
    }
}