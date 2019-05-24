package com.example.moiuchastkoviy.ui.main.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import com.example.moiuchastkoviy.*
import com.example.moiuchastkoviy.model.RegionContact
import com.example.moiuchastkoviy.ui.Constants
import kotlinx.android.synthetic.main.activity_contacts2_lvl.*
import kotlinx.android.synthetic.main.no_internet_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Contacts2LVLActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        const val REGION_ID = "region id"
        const val REGION_TITLE = "region title"
    }

    private var id: Int = -1

    private lateinit var adapter:PoliceStationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts2_lvl)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_gradient))

        id = intent.getIntExtra(REGION_ID, -1)
        if (id != -1) {
            getContacts(id)
        } else {
            toast("Неправильный ID")
        }

        val title = intent.getStringExtra(REGION_TITLE)

        supportActionBar?.title = title

        swipeRefresh.setOnRefreshListener(this)
        initRV()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return super.onOptionsItemSelected(item)
        when (item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initRV() {
        adapter = PoliceStationAdapter(this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onRefresh() {

        if (id != -1) {
            getContacts(id)
        } else {
            toast("Неправильный ID")
        }

        Handler().postDelayed({
            swipeRefresh.isRefreshing = false
        }, 3000)
    }

    private fun getContacts(id: Int){
        App.getClient()
            .getContacts(id)
            .enqueue(object: Callback<RegionContact?> {
                override fun onFailure(call: Call<RegionContact?>, t: Throwable) {
                    swipeRefresh.isRefreshing = false
                    if (adapter.data.isNullOrEmpty()) {
                        no_internet.visible()
                        info_text.text = getString(R.string.error_message_bad_internet_connection)

                    } else {
                        toast(R.string.error_message_bad_internet_connection)
                    }
                }

                override fun onResponse(call: Call<RegionContact?>, response: Response<RegionContact?>) {
                    no_internet.gone()
                    swipeRefresh.isRefreshing = false
                    if (response.isSuccessful && response.body() != null) {
                        val list = response.body()!!.contacts
                        adapter.swapData(list)
                    } else {
                        no_internet.visible()
                        info_text.text = "нет элементов"

                    }
                }
            })
    }
}
