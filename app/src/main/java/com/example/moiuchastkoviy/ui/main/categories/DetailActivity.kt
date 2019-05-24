package com.example.moiuchastkoviy.ui.main.categories

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.moiuchastkoviy.App
import com.example.moiuchastkoviy.R
import com.example.moiuchastkoviy.gone
import com.example.moiuchastkoviy.model.ArticleDetail
import com.example.moiuchastkoviy.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.no_internet_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.webkit.WebSettings
import android.annotation.SuppressLint
import android.opengl.ETC1.getWidth
import android.view.Display



class DetailActivity : AppCompatActivity() {

    companion object {
        const val SLUG = "slug"
        const val TITLE = "title"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.background_gradient))

        val id = intent.getIntExtra(SubCategoryActivity.CATEGORY_ID, -1)
//        val id2 = intent.getIntExtra(CategoryFragment.CATEGORY_ID1, -1)
        val slug = intent.getStringExtra(SLUG)
        val title = intent.getStringExtra(TITLE)

        if (!title.isNullOrEmpty()) {
            supportActionBar?.title = title
        }
        getArticleDetail(id, slug)
//        getArticleDetail(id2, slug)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getArticleDetail(id: Int, slug: String) {
        App.getClient().getArticle(id, slug).enqueue(object : Callback<ArticleDetail?> {
                override fun onFailure(call: Call<ArticleDetail?>, t: Throwable) {
                    progress.gone()
                    no_internet.visible()
                }

                @SuppressLint("SetJavaScriptEnabled")
                override fun onResponse(call: Call<ArticleDetail?>, response: Response<ArticleDetail?>) {
                    progress.visible()
                    if (response.isSuccessful && response.body() != null) {
                        val article = response.body()!!
                        if (article.image.isNullOrEmpty()) {
                            imageDetal.gone()
                            imageDetal.setImageResource(R.drawable.images)
                        } else if (!article.image.isNullOrEmpty()) {
                            Picasso.get()
                                .load(App.BASE_URL+article.image)
                                .into(imageDetal)
                        }
//                        name.text = article.title

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            description.text = Html.fromHtml(article.context, Html.FROM_HTML_MODE_LEGACY)
//                            webView.getSettings().setLoadWithOverviewMode(true)
//                            webView.getSettings().setUseWideViewPort(false)
                            webView.getSettings().setBuiltInZoomControls(true)
                            webView.getSettings().setJavaScriptEnabled(true)
//                            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//                            webView.getSettings().setPluginState(WebSettings.PluginState.ON)
                            webView.getSettings().setSupportZoom(true);
                            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)
//                            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
//                            webView.loadUrl("")
//                            webView.loadDataWithBaseURL("", "<html>${article.title}${article.context}</html>",
//                                "text/html", "", "")

//                            webView.loadUrl("https://www.google.com/search?q=google&client=opera&hs=oBQ&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiJhZrykqriAhWjtYsKHb0bBdAQ_AUIDigB&biw=1989&bih=1002#imgrc=JLVwTVjTZB9pNM:")

//                            val String = ("<html> <head></head> "
//                                    + " <body>  <img src=\"file:///android_asset/img/x.jpg\"> "
//                                    + "</body> </html>")
                            var html = "<html>${article.title}${article.context}</h1></html>"

                           html = html.replace("src=\"","src=\"${App.BASE_URL}")
                            Log.e("HTML", html)

                            webView.loadDataWithBaseURL ("url", html,
                                "text/html", "utf-8", "url")
                            progress.gone()


                        } else {
//                             webView.loadDataWithBaseURL(null, "<html>${article.context}</html>", "text/html", "utf-8", null);
//                            description.text = Html.fromHtml(article.context)
                        }
                    }
                }
            })
    }
}

