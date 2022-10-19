package com.example.studyday_android.apis

import com.example.studyday_android.data.InfoChg
import com.example.studyday_android.data.Join
import com.example.studyday_android.data.PostResult
import com.example.studyday_android.data.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIS {
    // RESTful API 호출 interface부
    @GET("user/{id}")
    fun login(@Path("id") id:String) : Call<User>

    @POST("user")
    fun userCreate(@Body join:Join) : Call<PostResult>

    @PUT("user")
    fun userInfoChg(@Body infoChg:InfoChg) : Call<PostResult>

    //////공유 객체로서 사용
    companion object {
        private const val BASE_URL = "http://192.168.0.65:8080/api/"

        fun create(): APIS {
            val gson :Gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}