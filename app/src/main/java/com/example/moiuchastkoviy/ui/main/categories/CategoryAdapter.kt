package com.example.moiuchastkoviy.ui.main.categories

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.Category
import com.example.moiuchastkoviy.ui.OnItemClickListener
import com.example.moiuchastkoviy.ui.main.MainActivity
import com.example.moiuchastkoviy.ui.main.about_us.AboutUsFragment
import com.example.moiuchastkoviy.ui.main.search.SearchActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.blue_title_item.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var data: ArrayList<Category> = ArrayList()

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.blue_title_item, parent, false)
        )
    }
    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
    fun swapData(data: ArrayList<Category>) {
        this.data = data
        notifyDataSetChanged()
    }inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.name)
        private val imageView: ImageView = itemView.findViewById(R.id.imageList)
        fun bind(item: Category) = with(itemView) {

            textView.text = item.title

//            Picasso.get().load(item.icon).into(imageView)
            if (!item.icon.isNullOrEmpty()) {
                Picasso.get().load(App.BASE_URL+item.icon).into(imageView)
            } else {
                if (adapterPosition == 0) {
                    imageList.setImageResource(R.drawable.v1)
                } else if (adapterPosition == 1) {
                    imageList.setImageResource(R.drawable.v9)
                } else if (adapterPosition == 2) {
                    imageList.setImageResource(R.drawable.v8)
                } else if (adapterPosition == 3) {
                    imageList.setImageResource(R.drawable.v7)
                } else if (adapterPosition == 4) {
                    imageList.setImageResource(R.drawable.v6)
                } else if (adapterPosition == 5) {
                    imageList.setImageResource(R.drawable.v4)
                } else if (adapterPosition == 6) {
                    imageList.setImageResource(R.drawable.v5)
                } else if (adapterPosition == 7) {
                    imageList.setImageResource(R.drawable.v3)
                } else if (adapterPosition == 8) {
                    imageList.setImageResource(R.drawable.v2)
                } else if (adapterPosition == 9) {
                    imageList.setImageResource(R.drawable.v10)
                }
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

//            if (adapterPosition == 0){
//                imageList.setImageResource(R.drawable.v2)
//            }else if (adapterPosition == 1){
//                imageList.setImageResource(R.drawable.v3)
//            }else if (adapterPosition == 2){
//                imageList.setImageResource(R.drawable.v5)
//            }else if (adapterPosition == 3){
//                imageList.setImageResource(R.drawable.v4)
//            }else if (adapterPosition == 4){
//                imageList.setImageResource(R.drawable.v6)
//            }else if (adapterPosition == 5){
//                imageList.setImageResource(R.drawable.v7)
//            }else if (adapterPosition == 6){
//                imageList.setImageResource(R.drawable.v8)
//            }else if (adapterPosition == 7){
//                imageList.setImageResource(R.drawable.v9)
//            }else if (adapterPosition == 8){
//                imageList.setImageResource(R.drawable.v9)
//            }else if (adapterPosition == 9){
//                imageList.setImageResource(R.drawable.v10)
//            }


