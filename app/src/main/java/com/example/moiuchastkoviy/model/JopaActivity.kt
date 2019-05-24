package com.example.moiuchastkoviy.model

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.gone
import com.example.moiuchastkoviy.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about_app.*
import kotlinx.android.synthetic.main.activity_jopa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JopaActivity : AppCompatActivity() {

    private lateinit var noInternetLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jopa)
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
            .getJopakonodatelstvo()
            .enqueue(object: Callback<Jopakonodatelstvo> {
                override fun onFailure(call: Call<Jopakonodatelstvo>, t: Throwable) {
                    noInternetLayout.visible()
                }

                @SuppressLint("ResourceAsColor")
                override fun onResponse(call: Call<Jopakonodatelstvo>, response: Response<Jopakonodatelstvo>) {
                    noInternetLayout.gone()
                    if (response.isSuccessful) {
                        val item = response.body()!!
                        if (item.image.isNullOrEmpty()) {
                            imageJopa.gone()
                            imageJopa.setImageResource(R.drawable.images)
                        } else if (!item.image.isNullOrEmpty()) {
                            Picasso.get()
                                .load(App.BASE_URL+item.image)
                                .into(imageJopa)
                        }


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            content.text = Html.fromHtml(item.context, Html.FROM_HTML_MODE_LEGACY)
//                            wV.getSettings().setBuiltInZoomControls(true)
                            wV3.getSettings().setJavaScriptEnabled(true)
//                            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                            wV3.getSettings().setPluginState(WebSettings.PluginState.ON)
                            wV3.setBackgroundColor(Color.TRANSPARENT)
                            wV3.getSettings().setSupportZoom(true)
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
                            var html = "<html>${""}${item.title}${item.context}</h1></html>"

                            html = html.replace("src=\"","src=\"${App.BASE_URL}")
                            Log.e("HTML", html)

                            wV3.loadDataWithBaseURL ("url", "<html>${""}${html}</h1></html>", "text/html", "utf-8", "url")
                            progressB.gone()
                        } else {
//                            content.text = Html.fromHtml(item.context)
                        }
                    } else {

                    }
                }
            })
    }

}
