package com.github.lilzulf.masaya.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceRequest{
    private  var endpoint : EndPoint ? = null

    //private val BASE_URL = "https://capitular-defeat.000webhostapp.com/api-skeleton/MasayaApi/"
   //private var BASE_URL = "https://masaya-api.000webhostapp.com/api/MasayaApi/"
    private var BASE_URL = "https://api.quotable.io/"

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