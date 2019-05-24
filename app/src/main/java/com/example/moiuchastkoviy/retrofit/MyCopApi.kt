package com.example.moiuchastkoviy.retrofit


import com.example.moiuchastkoviy.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyCopApi {

    @GET("api/articles-category/")
    fun getCategories(@Query("page") page: Int): Call<ResponseModel<Category>>

    @GET("api/articles-category/{id}/")
    fun getArticles(@Path("id") id: Int): Call<ListOfArticles>

    @GET("api/articles-category/{id}/article/{slug}/")
    fun getArticle(@Path("id") id: Int, @Path("slug") slug: String): Call<ArticleDetail>

    @GET("api/contact-category/")
    fun getCitiesAndRegions(): Call<CitiesAndRegions>

    @GET("api/contact-category/{id}/")
    fun getContacts(@Path("id")id: Int): Call<RegionContact>

    @GET("api/statement/")
    fun getStatements(@Query("page") page: Int): Call<ResponseModel<Statement>>

    @GET("api/about-us/")
    fun getAboutUsInfo(): Call<ResponseModel<AboutUsContext>>

    @GET("api/articles-category/-1/article/o-mobilnom-prilozheniii/")
    fun getAboutApp(): Call<AboutApp>

    @GET("api/articles-category/1/article/zakonodatelstvoo/")
    fun getJopakonodatelstvo(): Call<Jopakonodatelstvo>

    @GET("/api/search/")
    fun search(@Query("page") page: Int, @Query("search") search: String): Call<ResponseModel<ShortArticle>>
}