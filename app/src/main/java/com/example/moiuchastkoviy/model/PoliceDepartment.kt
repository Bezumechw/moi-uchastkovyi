package com.example.moiuchastkoviy.model

data class PoliceDepartment(
    val id: Int,
    val title: String,
    val slug: String,
    val address: String,
    val phone: String,
    val first_phone: String,
    val helpline: String,
    val lat: String?,
    val long:String?
)