package com.example.moiuchastkoviy.ui.main.contacts

import android.Manifest
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.PoliceDepartment
import java.util.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PoliceStationAdapter(val activity: AppCompatActivity) : RecyclerView.Adapter<PoliceStationAdapter.ViewHolder>() {

    var data: List<PoliceDepartment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.police_station_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<PoliceDepartment>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.name)
        private val address = itemView.findViewById<TextView>(R.id.address_field)
        private val phone = itemView.findViewById<TextView>(R.id.phone_field)
        private val helpline = itemView.findViewById<TextView>(R.id.helpline_field)
        private val button = itemView.findViewById<Button>(R.id.search_in_map)
        fun bind(item: PoliceDepartment) = with(itemView) {

            title.text = item.title
            address.text = item.address
            phone.paintFlags = (phone.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
            phone.text = item.phone
            helpline.paintFlags = (helpline.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

            helpline.text = item.helpline


            button.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:0,0?q=${item.address}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                context.startActivity(mapIntent)
            }

            phone.setOnClickListener {
                phoneDial(item.first_phone)
            }

            helpline.setOnClickListener{
                phoneDial(item.helpline)
            }

            setOnClickListener {
            }
        }
    }

    private fun phoneDial(phone: String){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,  arrayOf(Manifest.permission.CALL_PHONE),1)
        }
        else
        {
            activity.startActivity(intent)
        }
    }
}