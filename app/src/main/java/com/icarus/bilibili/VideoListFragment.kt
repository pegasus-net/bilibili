package com.icarus.bilibili

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var videoList: RecyclerView
    private val list = ArrayList<VideoInfo>()
    private lateinit var adapter: VideoAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        refresh = view.findViewById(R.id.refresh)
        videoList = view.findViewById(R.id.video_list)
        refresh.setOnRefreshListener {
            loadData(true)
        }
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
        adapter = VideoAdapter(activity!!, list)
        videoList.adapter = adapter
        videoList.layoutManager = LinearLayoutManager(activity)
        dataLoader.filter = { it ->
            val title = it.title ?: ""
            val mid = it.author?.mid ?: ""
            val filter = LitePal.where("mid = ?", mid).find(KeyWord::class.java)
            var allow = true
            filter.forEach { keyWord ->
                if (title.contains(keyWord.key)) {
                    allow = keyWord.allow
                } else {
                    allow = !keyWord.allow
                }
            }
            allow
        }
        loadData(true)
    }

    private var isLoad = false

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
                if (refresh.isRefreshing) refresh.isRefreshing = false
                isLoad = false
                if (reload) {
                    list.clear()
                }
                list.addAll(videos)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }


    private fun getFilter(it: VideoInfo) {

    }
}