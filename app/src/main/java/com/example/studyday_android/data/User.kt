package com.example.studyday_android.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("result_code")
    val result_code: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("data")
    val data: JsonObject,

)