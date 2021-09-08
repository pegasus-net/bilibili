package com.icarus.bilibili

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(private val context: Context, private val list: List<VideoInfo>) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView by lazy { view.findViewById<ImageView>(R.id.cover) }
        val title: TextView by lazy { view.findViewById<TextView>(R.id.title) }
        val reason: TextView by lazy { view.findViewById<TextView>(R.id.reason) }
        val authorName: TextView by lazy { view.findViewById<TextView>(R.id.author_name) }
        val other: TextView by lazy { view.findViewById<TextView>(R.id.other) }
        val duration: TextView by lazy { view.findViewById<TextView>(R.id.duration) }
    }

    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.authorName.text = item.author?.name
        Glide.with(context).load(item.cover).error(R.mipmap.ic_launcher).into(holder.cover)
        val reason = item.reason
        if (reason != null && !reason.content.isNullOrEmpty()) {
            holder.reason.visibility = View.VISIBLE
            holder.reason.text = reason.content
            if (reason.corner_mark == 0) {
                holder.reason.setBackgroundResource(R.drawable.reason_bg_2)
            } else {
                holder.reason.setBackgroundResource(R.drawable.reason_bg_1)
            }
        } else {
            holder.reason.visibility = View.INVISIBLE
        }
        val otherInfo = item.view + " · " + item.time
        holder.other.text = otherInfo
        holder.duration.text = item.durationStr
        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(item.linkAv)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}