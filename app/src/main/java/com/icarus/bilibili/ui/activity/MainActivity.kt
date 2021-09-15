package com.icarus.bilibili.ui.activity

import a.icarus.component.BaseActivity
import a.icarus.impl.FragmentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.icarus.bilibili.R
import com.icarus.bilibili.ui.fragment.PopularListFragment
import com.icarus.bilibili.ui.fragment.VideoListFragment

class MainActivity : BaseActivity() {
    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    private val fragmentPager: ViewPager by lazy { findViewById(R.id.fragment_pager) }
    private val title: TextView by lazy { findViewById(R.id.title) }
    override fun initView() {

    }

    override fun initData() {
        title.text = "排行榜"
        fragmentPager.adapter =
            FragmentAdapter(this, listOf(PopularListFragment(), VideoListFragment()))
    }

    override fun initListener() {
        fragmentPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                title.text = when (position) {
                    0 -> "排行榜"
                    else -> "我的关注"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        title.setOnClickListener {
            fragmentPager.currentItem = when (fragmentPager.currentItem) {
                0 -> 1
                else -> 0
            }
        }
    }

}