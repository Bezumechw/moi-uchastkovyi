package com.example.moiuchastkoviy.model

import java.io.Serializable

data class Category(
    val id: Int,
    val title: String,
    val icon: String
): Serializable