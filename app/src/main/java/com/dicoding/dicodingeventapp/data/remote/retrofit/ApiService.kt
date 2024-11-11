package com.dicoding.dicodingeventapp.data.remote.retrofit

import com.dicoding.dicodingeventapp.data.remote.response.EventDetailResponse
import com.dicoding.dicodingeventapp.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getFinishedEvents(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: String
    ): Call<EventDetailResponse>

    @GET("events")
    fun searchEvents(
        @Query("q") query: String,
        @Query("active") active: Int
    ): Call<EventResponse>
}