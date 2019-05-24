package com.example.moiuchastkoviy.model

import com.google.gson.annotations.SerializedName

data class Region(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val image: String
)
