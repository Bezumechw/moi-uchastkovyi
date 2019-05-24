package com.example.moiuchastkoviy.ui.main.categories


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.moiuchastkoviy.*
import com.example.moiuchastkoviy.model.AboutAppActivity

import com.example.moiuchastkoviy.model.ResponseModel
import com.example.moiuchastkoviy.model.Category
import com.example.moiuchastkoviy.model.JopaActivity
import com.example.moiuchastkoviy.ui.OnItemClickListener
import com.example.moiuchastkoviy.ui.main.search.SearchActivity
import com.example.moiuchastkoviy.ui.utils.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CategoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        const val CATEGORY_ID1 = "category_id1"

    }
    private var id2 = -1

    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoryAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var searchImage: ImageView
    private lateinit var searchEditText: EditText
    private lateinit var noInternetLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_category, container, false)

        initViews(v)

        initRV(v)
        swipeRefreshLayout.isRefreshing = true
        getCategories(1)
        return v
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.toolbar_menu, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cut -> {

//                text_view.text = "Cut"
                return true
            }
            R.id.action_copy -> {
//                text_view.text = "Copy"
                return true
            }
            R.id.action_paste -> {
//                text_view.text = "Paste"
                return true
            }
            R.id.action_new -> {
//                text_view.text = "New"
                return true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun initViews(v: View){
        searchEditText = v.findViewById(R.id.search_edit)
        searchImage = v.findViewById(R.id.search)
        searchImage.setOnClickListener {openSearchAct()}

        searchEditText.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                openSearchAct()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        toolbar = v.findViewById(R.id.toolbar)
        noInternetLayout = v.findViewById(R.id.no_internet)
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun openSearchAct(){
        if (searchEditText.validate({ s -> s.isNotEmpty() }, null)) {
            context?.startActivity(
                Intent(context, SearchActivity::class.java)
                    .putExtra(SearchActivity.SEARCH_TEXT, searchEditText.text.toString())
            )

            activity?.hideKeyboard()
        }
    }

    private fun initRV(v: View) {
        rv = v.findViewById(R.id.rv)
        adapter = CategoryAdapter()
        val layoutManager = LinearLayoutManager(context)

        rv.layoutManager = layoutManager
        rv.adapter = adapter
//        languare.setOnClickListener {
//            val locale = new Locale("ky")
//            Locale.setDefault(locale)
//            val configuration = new Configuration()
//            configuration.locale = locale
//            getBaseContext().getResouces().updateConfiguration(configuration, null)
//            setTitle(R.string.app_name)
//        }

        adapter.setItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (position == 9){
                    startActivity(
                        Intent(context, AboutAppActivity::class.java)
                    )
                }
                else if (position == 0){
                    startActivity(
                        Intent(context, JopaActivity::class.java)
                    )
                }
                else{
                    startActivity(
                        Intent(context,
                            SubCategoryActivity::class.java)
                            .putExtra(SubCategoryActivity.CATEGORY_ID, adapter.data[position].id)
                            .putExtra(SubCategoryActivity.CATEGORY, adapter.data[position])

                    )
                }
                }


            override fun onItemLongClick(position: Int) {}
        })



        rv.addOnScrollListener(object: EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                getCategories(currentPage)
            }
        })
    }

    override fun onRefresh() {
        adapter.data = arrayListOf()
        getCategories(1)
        Handler().postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 3000)
    }


    private fun getCategories(page: Int){
        App.getClient()
            .getCategories(page)
            .enqueue(object: Callback<ResponseModel<Category>?> {
                override fun onFailure(call: Call<ResponseModel<Category>?>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                    if (adapter.data.isNullOrEmpty()) {
                        noInternetLayout.visible()
                    } else {
                        context?.toast(R.string.error_message_bad_internet_connection)
                    }
                }

                override fun onResponse(
                    call: Call<ResponseModel<Category>?>,
                    response: Response<ResponseModel<Category>?>
                ) {
                    noInternetLayout.gone()
                    swipeRefreshLayout.isRefreshing = false
                    if (response.isSuccessful && response.body() != null) {
                        val list = response.body()!!.results
                        adapter.swapData(list)
                    } else {

                    }
                }
            })
    }

}
