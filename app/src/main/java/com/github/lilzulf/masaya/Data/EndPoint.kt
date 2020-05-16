package com.github.lilzulf.masaya.Data

import com.github.lilzulf.masaya.Object.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndPoint {
    @POST("feed/gettarget")
    @FormUrlEncoded
    fun doTarget(
        @Field("idUser") id_user : String,
        @Field("date") date: String
    ): Call<TargetResponse>
    @POST("auth/login")
    @FormUrlEncoded
    fun doLogin(
        @Field("email") email : String,
        @Field("pass") pass : String
    ): Call<ResponseAuth>
    @POST("auth/register")
    @FormUrlEncoded
    fun doRegister( @Field("username") username : String,
                    @Field("nickname") nickname : String,
        @Field("email") email : String,
        @Field("pass") pass : String
    ): Call<ResponseAuth>
    @POST("feed/addtarget")
    @FormUrlEncoded
    fun addTarget(
        @Field("tittle") tittle : String,
        @Field("date") date:String,
        @Field("idUser") id_user : String
    ): Call<ResponseAuth>
    @POST("feed/addmood")
    @FormUrlEncoded
    fun addMood(
        @Field("state") state : String,
        @Field("date") date:String,
        @Field("idUser") id_user : String
    ):Call<MoodResponse>
    @POST("feed/getmoodbydate")
    @FormUrlEncoded
    fun getMood(
        @Field("date") date:String,
        @Field("idUser") id_user : String
    ):Call<MoodResponse>
    @POST("feed/getmood")
    @FormUrlEncoded
    fun getMoodDetail(
        @Field("idUser") id_user : String
    ):Call<MoodDetail>
    @POST("feed/addgrate")
    @FormUrlEncoded
    fun addGrate(
        @Field("idUser") id_user : String,
        @Field("text") text:String,
        @Field("date") date:String
    ):Call<ResponseGrate>
}