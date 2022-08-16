package com.example.androidintern


import com.google.gson.annotations.SerializedName

data class PhoneNumber(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("type")
    val type: String
)