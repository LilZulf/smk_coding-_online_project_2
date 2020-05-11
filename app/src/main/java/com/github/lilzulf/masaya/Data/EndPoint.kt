package com.github.lilzulf.masaya.Data

import com.github.lilzulf.masaya.Object.TargetResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndPoint {
    @POST("feed/gettarget")
    @FormUrlEncoded
    fun doTarget(
        @Field("idUser") id_user : String
    ): Call<TargetResponse>
}