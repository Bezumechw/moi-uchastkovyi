package com.example.moiuchastkoviy.ui.main.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moiuchastkoviy.*
import com.example.moiuchastkoviy.model.Category
import com.example.moiuchastkoviy.model.ListOfArticles
import com.example.moiuchastkoviy.ui.OnItemClickListener
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.no_internet_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        const val CATEGORY_ID = "category_id"
        const val CATEGORY = "category"
    }

    private lateinit var adapter: TitleWithViewAdapter

    private var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_gradient))

        swipeRefresh.setOnRefreshListener(this)
        initRV()

        id = intent.getIntExtra(CATEGORY_ID, -1)
        val category = intent.getSerializableExtra(CATEGORY) as Category

        if (category != null) {
            supportActionBar?.title = category.title
        }
        getArticles(id)
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
        adapter = TitleWithViewAdapter()
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        adapter.setItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                 startActivity(
                     Intent(this@SubCategoryActivity, DetailActivity::class.java)
                         .putExtra(CATEGORY_ID, id)
                         .putExtra(DetailActivity.SLUG, adapter.data[position].slug)
                         .putExtra(DetailActivity.TITLE, adapter.data[position].title)

                 )
            }

            override fun onItemLongClick(position: Int) {}
        })


    }

    override fun onRefresh() {
        getArticles(id)
        Handler().postDelayed({
            swipeRefresh.isRefreshing = false
        }, 3000)
    }


    private fun getArticles(id: Int){
        if (id == -1) {
            toast("Категории по такому ID не существует")
            return
        }

        App.getClient()
            .getArticles(id)
            .enqueue(object: Callback<ListOfArticles?> {
                override fun onFailure(call: Call<ListOfArticles?>, t: Throwable) {
                    swipeRefresh.isRefreshing = false
                    if (adapter.data.isNullOrEmpty()) {
                        no_internet.visible()
                        info_text.text = getString(R.string.error_message_bad_internet_connection)

                    } else {
                        toast(R.string.error_message_bad_internet_connection)
                    }
                }

                override fun onResponse(call: Call<ListOfArticles?>, response: Response<ListOfArticles?>) {
                    no_internet.gone()
                    swipeRefresh.isRefreshing = false
                    if (response.isSuccessful && response.body() != null) {
                        val list = response.body()!!.articles
                        adapter.swapData(list)
                    } else {
                        no_internet.visible()
                        info_text.text = "нет элементов"

                    }
                }
            })
    }
}
