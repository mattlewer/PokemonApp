package com.msl.pokemonapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonResult>
): Parcelable


@Parcelize
data class PokemonResult(
    val name: String,
    val url: String
): Parcelable