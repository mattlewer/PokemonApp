package com.msl.pokemonapp.util

import com.msl.pokemonapp.model.ApiResponse
import com.msl.pokemonapp.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("pokemon/{id}")
    @Headers("Accept-type: application/json")
    fun getSinglePokemon(@Path("id")id:Int): Call<Pokemon>

    @GET("pokemon")
    fun getAllPokemon(@Query("limit") limit: Int, @Query("offset")offset:Int): Call<ApiResponse>
}