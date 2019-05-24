package com.example.moiuchastkoviy.ui.main.contacts


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moiuchastkoviy.*

import com.example.moiuchastkoviy.model.CitiesAndRegions
import com.example.moiuchastkoviy.model.City
import com.example.moiuchastkoviy.model.Region
import com.example.moiuchastkoviy.model.ResponseModel
import com.example.moiuchastkoviy.ui.OnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class ContactsFragment : androidx.fragment.app.Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        val list = arrayListOf("Нормативная база", "Как действовать?", "Категория тем","Нормативная база", "Как действовать?", "Категория тем", "Нормативная база", "Как действовать?", "Категория тем")
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ContactAdapter
    private lateinit var cityRecView: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private lateinit var noInternetLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contacts, container, false)
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true

        noInternetLayout = v.findViewById(R.id.no_internet)

        initRV(v)
        getRegionsAndCities()
        return v
    }


    private fun initRV(v: View) {
        rv = v.findViewById(R.id.rv)
        adapter = ContactAdapter()
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        adapter.setItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                startActivity(
                    Intent(context, Contacts2LVLActivity::class.java)
                        .putExtra(Contacts2LVLActivity.REGION_ID, adapter.data[position].id)
                        .putExtra(Contacts2LVLActivity.REGION_TITLE, adapter.data[position].title)
                )
            }

            override fun onItemLongClick(position: Int) {}
        })


        cityRecView = v.findViewById(R.id.city_rv)
        cityRecView.layoutManager = GridLayoutManager(context, 2)
        cityAdapter = CityAdapter()
        cityRecView.adapter = cityAdapter

        cityAdapter.setItemClickListener(object: OnItemClickListener{
            override fun onItemClick(position: Int) {
                startActivity(Intent(context, Contacts2LVLActivity::class.java)
                    .putExtra(Contacts2LVLActivity.REGION_ID, cityAdapter.data[position].id)
                    .putExtra(Contacts2LVLActivity.REGION_TITLE, cityAdapter.data[position].title))
            }

            override fun onItemLongClick(position: Int) {

            }
        })

    }

    override fun onRefresh() {
        getRegionsAndCities()
        Handler().postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 3000)
    }


    private fun getRegionsAndCities(){
        App.getClient()
            .getCitiesAndRegions()
            .enqueue(object: Callback<CitiesAndRegions> {
                override fun onFailure(call: Call<CitiesAndRegions>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    noInternetLayout.visible()
                }

                override fun onResponse(
                    call: Call<CitiesAndRegions>,
                    response: Response<CitiesAndRegions>
                ) {
                    swipeRefreshLayout.isRefreshing = false
                    noInternetLayout.gone()

                    if (response.isSuccessful) {
                        try {
                            val cities = response.body()!!.city
                            val regions = response.body()!!.region

                            updateUI(cities, regions)
                        } catch (e: Exception) {
                            Log.e("TAG", e.localizedMessage)
                            Log.e("TAG", e.message)
                            Log.e("TAG", e.stackTrace.toString())
                            context!!.toast("Произошла ошибка")
                        }
                    } else {

                    }
                }
            })
    }

    private fun updateUI(cities: ArrayList<City>, regions: ArrayList<Region>) {
        adapter.swapData(regions)
        cityAdapter.swapData(cities)
    }

}
