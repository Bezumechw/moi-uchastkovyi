package com.example.moiuchastkoviy.model

import com.google.gson.annotations.SerializedName

data class City (
    val id: Int,
    val title: String,

    @SerializedName("image")
    val image: String
)