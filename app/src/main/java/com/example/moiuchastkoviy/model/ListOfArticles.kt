package com.example.moiuchastkoviy.model

data class ListOfArticles(
    val id: Int,
    val title: String,
    val articles: ArrayList<ShortArticle>
)