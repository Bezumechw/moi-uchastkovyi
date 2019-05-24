package com.example.moiuchastkoviy.ui.main.contacts


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.Region
import com.example.moiuchastkoviy.ui.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.blue_title_item.view.*
import java.util.*

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var data: List<Region> = ArrayList()

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.blue_title_item2, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun swapData(data: List<Region>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.name)
        private val imageView: ImageView = itemView.findViewById(R.id.imageList2)
        fun bind(item: Region) = with(itemView) {

            textView.text = item.title
            if (!item.image.isNullOrEmpty()) {
                Picasso.get().load(App.BASE_URL+item.image).into(imageView)
            } else {

            }

            setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition)
            }
            setOnLongClickListener {
                onItemClickListener?.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}