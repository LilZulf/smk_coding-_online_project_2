package com.github.lilzulf.masaya.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceRequest{
    private  var endpoint : EndPoint ? = null

    private val BASE_URL = "https://kreasianakbululawang.000webhostapp.com/api/MasayaApi/"

    fun get(): EndPoint {
        if (endpoint == null){
            var retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            endpoint = retrofit.create(EndPoint::class.java)
        }
        return endpoint!!
    }
}