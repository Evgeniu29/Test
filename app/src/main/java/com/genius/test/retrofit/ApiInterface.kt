package com.genius.test.retrofit

import com.genius.test.retrofit.response.SearchListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("curated")
    fun getSearchList(
            @Query("page") pageIndex: Int = 0,
            @Query("per_page") perPage: Int = 15


    ): Call<SearchListResponse>
}