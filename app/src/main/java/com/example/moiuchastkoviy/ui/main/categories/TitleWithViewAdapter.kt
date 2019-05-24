package com.example.moiuchastkoviy.ui.main.categories

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.ShortArticle
import com.example.moiuchastkoviy.ui.OnItemClickListener
import java.util.*
import kotlin.collections.ArrayList

class TitleWithViewAdapter : RecyclerView.Adapter<TitleWithViewAdapter.ViewHolder>() {

    var data: ArrayList<ShortArticle> = ArrayList()

    private var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.title_with_desc_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun swapData(data: ArrayList<ShortArticle>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<ShortArticle>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun resetData(){
        this.data = arrayListOf()
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val description = itemView.findViewById<TextView>(R.id.description)
        fun bind(item: ShortArticle) = with(itemView) {
            title.text = item.title
            description.text = item.shortContext
            setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition)
            }
        }
    }
}