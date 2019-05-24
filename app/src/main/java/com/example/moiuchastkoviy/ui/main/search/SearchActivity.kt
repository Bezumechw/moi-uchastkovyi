package com.example.moiuchastkoviy.ui.main.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moiuchastkoviy.*
import com.example.moiuchastkoviy.model.ResponseModel
import com.example.moiuchastkoviy.model.ShortArticle
import com.example.moiuchastkoviy.ui.OnItemClickListener
import com.example.moiuchastkoviy.ui.main.categories.DetailActivity
import com.example.moiuchastkoviy.ui.main.categories.SubCategoryActivity.Companion.CATEGORY_ID
import com.example.moiuchastkoviy.ui.main.categories.TitleWithViewAdapter
import com.example.moiuchastkoviy.ui.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.no_internet_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    companion object{
        const val SEARCH_TEXT = "SEARCH TEXT"
    }
    private lateinit var adapter: TitleWithViewAdapter

    private var searchText = ""

    private lateinit var listener: EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        hideKeyboard()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_gradient))
        supportActionBar?.title = "Поиск"

        initRV()

        searchText = intent.getStringExtra(SEARCH_TEXT)
        search_edit.setText(searchText)
        search_edit.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchAction()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        search.setOnClickListener {
            searchAction()
        }

        search(1, searchText)
    }

    private fun searchAction() {
        if (search_edit.validate({ s -> s.isNotEmpty() }, null)){
            adapter.resetData()
            searchText = search_edit.text.toString()

            listener.reset()
            search(1, searchText)
        }
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
                    Intent(this@SearchActivity, DetailActivity::class.java)
                        .putExtra(CATEGORY_ID, adapter.data[position].category)
                        .putExtra(DetailActivity.SLUG, adapter.data[position].slug)
                        .putExtra(DetailActivity.TITLE, adapter.data[position].title)

                )
            }

            override fun onItemLongClick(position: Int) {}
        })

        listener = object: EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                search(currentPage, searchText)
            }
        }

        rv.addOnScrollListener(listener)


    }


    private fun search(page: Int, search: String) {
        App.getClient()
            .search(page, search)
            .enqueue(object: Callback<ResponseModel<ShortArticle>?> {
                override fun onFailure(call: Call<ResponseModel<ShortArticle>?>, t: Throwable) {
                    if (adapter.data.isNullOrEmpty()) {
                        no_internet.visible()
                        info_text.text = getString(R.string.error_message_bad_internet_connection)

                    } else {
                        toast(R.string.error_message_bad_internet_connection)
                    }
                }

                override fun onResponse(
                    call: Call<ResponseModel<ShortArticle>?>,
                    response: Response<ResponseModel<ShortArticle>?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val list = response.body()!!.results
                        adapter.addData(list)
                    } else {
                        if (adapter.itemCount < 1) {
                            no_internet.visible()
                            info_text.text = "нет элементов"
                        }

                    }
                }
            })
    }
}
