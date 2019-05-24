package com.example.moiuchastkoviy.model

data class ArticleDetail(
    val id: Int,
    val category: Int,
    val image: String,
//    val image: String?,
    val title: String,
    val context: String
)