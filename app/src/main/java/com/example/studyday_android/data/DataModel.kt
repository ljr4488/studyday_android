package com.example.studyday_android.data

import com.google.gson.JsonObject

data class User(
    val result_code: String,
    val description: String,
    val data: JsonObject,
)

data class Join(
    val `data`: JoinData,
    val description: String
)

data class JoinData(
    val address: String,
    val login_id: String,
    val name: String,
    val password: String
)

data class InfoChg(
    val `data`: InfoChgData,
    val description: String
)

data class InfoChgData(
    val user_id: Int,
    val login_id: String,
    val password: String,
    val name: String,
    val address: String
)

data class PostResult(
    var result_code:String? = null,
    var description:String? = null
)

