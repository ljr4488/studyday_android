package com.example.studyday_android.apis

import android.icu.number.IntegerWidth
import com.example.studyday_android.data.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.LocalDate

interface APIS {
    // RESTful API 호출 interface부
    @GET("user/{id}")
    fun login(@Path("id") id:String) : Call<User>

    @POST("user")
    fun userCreate(@Body join:Join) : Call<PostResult>

    @PUT("user")
    fun userInfoChg(@Body infoChg:InfoChg) : Call<PostResult>

    // room현황 조회
    @GET("reserve")
    fun roomCheck(@Query("roomId") roomId:Int, @Query("reserveDate") reserveDate:String, @Query("startTime") startTime:Int) : Call<Reserve>

    @POST("reserve")
    fun reserveCreate(@Body reserve: Reserve) : Call<PostResult>

    //////공유 객체로서 사용
    companion object {
        private const val BASE_URL = "http://59.9.134.142:8080/api/"

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