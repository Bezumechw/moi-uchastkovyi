package com.example.moiuchastkoviy.model


data class ResponseModel<T>(
    val count: Int,
    val next: String?,
    val results: ArrayList<T>
)