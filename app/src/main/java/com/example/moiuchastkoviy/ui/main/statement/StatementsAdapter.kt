package com.example.moiuchastkoviy.ui.main.statement

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.Statement
import java.util.*
import kotlin.collections.ArrayList

class StatementsAdapter : RecyclerView.Adapter<StatementsAdapter.ViewHolder>() {

    var data: ArrayList<Statement> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.statement_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: ArrayList<Statement>) {
        this.data = data
        notifyDataSetChanged()
    }
    fun addData(data: ArrayList<Statement>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.button)
        private val textView: TextView = itemView.findViewById(R.id.title)

        fun bind(item: Statement) = with(itemView) {

            textView.text = item.title
            button.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.file)))
            }
        }
    }
}