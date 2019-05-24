package com.example.moiuchastkoviy.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShortArticle(
    val id: Int,
    val category: Int?,
    val title: String,
    val slug: String,

    @Expose
    @SerializedName("short_context")
    val shortContext: String
)