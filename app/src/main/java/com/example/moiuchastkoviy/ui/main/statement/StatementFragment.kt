package com.example.moiuchastkoviy.ui.main.statement


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moiuchastkoviy.App

import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.model.ResponseModel
import com.example.moiuchastkoviy.model.Statement
import com.example.moiuchastkoviy.toast
import com.example.moiuchastkoviy.ui.Constants
import com.example.moiuchastkoviy.ui.utils.EndlessRecyclerOnScrollListener
import com.example.moiuchastkoviy.visible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StatementFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private lateinit var adapter: StatementsAdapter
    private lateinit var noInternetLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_article, container, false)
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true
        noInternetLayout = v.findViewById(R.id.no_internet)

        initRV(v)
        getStatements(1)
        return v
    }


    private fun initRV(v: View) {
        rv = v.findViewById(R.id.rv)
        adapter = StatementsAdapter()
        val layoutManager = LinearLayoutManager(context)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        rv.addOnScrollListener(object: EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                getStatements(currentPage)
            }
        })

    }

    override fun onRefresh() {
        getStatements(1)
        adapter.data = arrayListOf()
        Handler().postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 3000)
    }


    fun getStatements(page: Int){
        App.getClient()
            .getStatements(page)
            .enqueue(object: Callback<ResponseModel<Statement>?> {
                override fun onFailure(call: Call<ResponseModel<Statement>?>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    if (adapter.data.isNullOrEmpty()) {
                        noInternetLayout.visible()
                    } else {
                        context?.toast(R.string.error_message_bad_internet_connection)
                    }
                }

                override fun onResponse(
                    call: Call<ResponseModel<Statement>?>,
                    response: Response<ResponseModel<Statement>?>
                ) {
                    swipeRefreshLayout.isRefreshing = false
                    if (response.isSuccessful) {
                        val statements = response.body()!!.results
                        adapter.addData(statements)
                    }
                }
            })
    }
}
