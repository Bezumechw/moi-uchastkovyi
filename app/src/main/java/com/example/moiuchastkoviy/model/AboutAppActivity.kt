package com.example.moiuchastkoviy.model

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.gone
import com.example.moiuchastkoviy.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about_app.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_faq.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutAppActivity : AppCompatActivity() {



    private lateinit var noInternetLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        noInternetLayout = findViewById(R.id.no_internet)
//        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
//        swipeRefreshLayout.setOnRefreshListener(this)
//        swipeRefreshLayout.isRefreshing = true

//        title = v.findViewById(R.id.title)
//        content = v.findViewById(R.id.content)
        getArticles()
        return
    }

    private fun getArticles(){
        App.getClient()
            .getAboutApp()
            .enqueue(object: Callback<AboutApp>{
                override fun onFailure(call: Call<AboutApp>, t: Throwable) {
                    noInternetLayout.visible()
                }

                @SuppressLint("ResourceAsColor")
                override fun onResponse(call: Call<AboutApp>, response: Response<AboutApp>) {
                    noInternetLayout.gone()
                    if (response.isSuccessful) {
                        val item = response.body()!!
                        if (item.image.isNullOrEmpty()) {
                            imageApp.gone()
                            imageApp.setImageResource(R.drawable.images)
                        } else if (!item.image.isNullOrEmpty()) {
                            Picasso.get()
                                .load(App.BASE_URL+item.image)
                                .into(imageApp)
                        }


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            content.text = Html.fromHtml(item.context, Html.FROM_HTML_MODE_LEGACY)
//                            wV.getSettings().setBuiltInZoomControls(true)
                            wV2.getSettings().setJavaScriptEnabled(true)
//                            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                            wV2.getSettings().setPluginState(WebSettings.PluginState.ON)
                            wV2.setBackgroundColor(Color.TRANSPARENT)
                            wV2.getSettings().setSupportZoom(true)
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
                            var html = "<html>${item.image}${item.title}${item.context}</h1></html>"

                            html = html.replace("src=\"","src=\"${App.BASE_URL}")
                            Log.e("HTML", html)

                            wV2.loadDataWithBaseURL ("url", "<html>$${html}</h1></html>", "text/html", "utf-8", "url")
                            progressA.gone()
                        } else {
//                            content.text = Html.fromHtml(item.context)
                        }
                    } else {

                    }
                }
            })
    }

}
