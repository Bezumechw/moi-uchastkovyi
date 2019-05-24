package com.example.moiuchastkoviy.ui.main.contacts

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.City
import com.example.moiuchastkoviy.ui.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    var data: List<City> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.city_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun swapData(data: List<City>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image)
        private val textView = itemView.findViewById<TextView>(R.id.name)
        fun bind(item: City) = with(itemView) {
            textView.text = item.title
            if (!item.image.isNullOrEmpty())
                Picasso.get()
                    .load(App.BASE_URL+item.image)
                    .into(imageView)
            else if (item.image.isNullOrEmpty())
                if (position == 0){
                    imageView.setImageResource(R.drawable.bishkek)
                }else if (position == 1){
                    imageView.setImageResource(R.drawable.osh)
                }


            setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition)
            }
        }
    }
}