package com.icarus.bilibili

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.RecyclerView
import java.util.*


class Helper {
    //构造函数需要传一个Callback,有一个简单实现类
    //ItemTouchHelper.SimpleCallback需要两个参数，第一个表示拖拽的方向，
    //第二个表示移动的方向
    var mHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        //拖动的方法
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
//            Collections.swap(mStrings, viewHolder.adapterPosition, target.adapterPosition)
//            mAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        //移动的方法
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
//            mStrings.remove(pos)
//            mAdapter.notifyItemRemoved(pos)
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            //表示拖拽的方向，为零表示不能拖动
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            //表示移动的方向，为零表示不能移动
            val swipeFlags = 0 //ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        //绘制item，actionState有三种状态SWIPE（滑动）、IDLE（静止）、DRAG（拖动）我们可以根据相应的状态来绘制Item的一些效果
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
                val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width
                    .toFloat()
                viewHolder.itemView.alpha = alpha
            } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.alpha = 0.5f
            }
        }
    })
}