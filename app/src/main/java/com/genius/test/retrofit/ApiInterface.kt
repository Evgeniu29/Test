package com.genius.test.retrofit

import com.genius.test.retrofit.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("curated")
    fun getSearchList(

            @Query("page") page:Int?=1,
            @Query("per_page") per_page:Int?=80



    ): Call<SearchListResponse>
}