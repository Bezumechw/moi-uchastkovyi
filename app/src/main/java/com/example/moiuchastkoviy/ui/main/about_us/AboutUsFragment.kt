package com.example.moiuchastkoviy.ui.main.about_us

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moiuchastkoviy.App

import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.gone
import com.example.moiuchastkoviy.model.AboutUsContext
import com.example.moiuchastkoviy.model.ResponseModel
import com.example.moiuchastkoviy.visible
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_faq.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutUsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var noInternetLayout: View

//    private lateinit var title: TextView
//    private lateinit var content: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_faq, container, false)
        noInternetLayout = v.findViewById(R.id.no_internet)
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = true

//        title = v.findViewById(R.id.title)
//        content = v.findViewById(R.id.content)
        getArticles()
        return v
    }


    override fun onRefresh() {

        getArticles()
        Handler().postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 3000)
    }

    private fun getArticles(){
        App.getClient()
            .getAboutUsInfo()
            .enqueue(object: Callback<ResponseModel<AboutUsContext>?> {
                override fun onFailure(call: Call<ResponseModel<AboutUsContext>?>, t: Throwable) {
                    swipeRefreshLayout.isRefreshing = false
                        noInternetLayout.visible()
                }

                @SuppressLint("ResourceAsColor")
                override fun onResponse(
                    call: Call<ResponseModel<AboutUsContext>?>,
                    response: Response<ResponseModel<AboutUsContext>?>
                ) {
                    noInternetLayout.gone()
                    swipeRefreshLayout.isRefreshing = false
                    if (response.isSuccessful) {
                        val item = response.body()!!.results[0]

//                        title.text = item.title


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            content.text = Html.fromHtml(item.context, Html.FROM_HTML_MODE_LEGACY)
                            wV.getSettings().setBuiltInZoomControls(true)
                            wV.getSettings().setJavaScriptEnabled(true)
//                            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                            wV.getSettings().setPluginState(WebSettings.PluginState.ON)
                            wV.setBackgroundColor(Color.TRANSPARENT)
                            wV.getSettings().setSupportZoom(true)
//                            wV.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)
//                            var myTable = "<table border=1>" +
//                                    "<tr>" +
//                                    "<td>row 1, cell 1</td>" +
//                                    "<td>row 1, cell 2</td>" +
//                                    "</tr>" +
//                                    "<tr>" +
//                                    "<td>row 2, cell 1</td>" +
//                                    "<td>row 2, cell 2</td>" +
//                                    "</tr>" +
//                                    "</table>"
//
                            var html = "<html>${item.title}${item.context}</h1></html>"

                            html = html.replace("src=\"","src=\"${App.BASE_URL}")
                            Log.e("HTML", html)

                            wV.loadDataWithBaseURL ("url", "<html>${html}</h1></html>", "text/html", "utf-8", "url")
                        } else {
//                            content.text = Html.fromHtml(item.context)
                        }
                    } else {

                    }
                }
            })
    }

}
