package com.msl.pokemonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msl.pokemonapp.viewmodel.PokemonViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        viewModel.getAllPokemon()
        viewModel.allPokemon().observe(this, Observer{list ->
            println(list)
        })

        viewModel.getSinglePokemon(1)
        viewModel.singlePokemon().observe(this, Observer{pokemon ->
            println(pokemon)
        })
    }
}