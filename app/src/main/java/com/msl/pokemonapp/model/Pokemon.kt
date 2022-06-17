package com.msl.pokemonapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites,
    val stats: List<Stats>
): Parcelable

@Parcelize
data class Sprites(
    val front_default: String?,
    val back_default: String?
): Parcelable

@Parcelize
data class Stats(
    var base_stat: Int,
    var effort: Int,
    var stat: Stat,
): Parcelable
@Parcelize
data class Stat(
    var name: String,
    var url : String
): Parcelable
