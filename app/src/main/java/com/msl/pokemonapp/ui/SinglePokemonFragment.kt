package com.msl.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.msl.pokemonapp.R
import com.msl.pokemonapp.databinding.FragmentAllPokemonBinding
import com.msl.pokemonapp.databinding.FragmentSinglePokemonBinding
import com.msl.pokemonapp.model.Pokemon
import com.msl.pokemonapp.viewmodel.PokemonViewModel


class SinglePokemonFragment : Fragment() {

    private var _binding: FragmentSinglePokemonBinding? = null
    private val binding get() = _binding!!
    private val pokemonViewModel: PokemonViewModel by viewModels()
    lateinit var pokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSinglePokemonBinding.inflate(inflater,container,false)
        var pokemonName = requireArguments().getString("pokemon")!!
        pokemonViewModel.getSinglePokemon(pokemonName)
        pokemonViewModel.singlePokemon().observe(viewLifecycleOwner,{
            pokemon = it
            binding.singlePokemonName.text = it.name
        })
        // Inflate the layout for this fragment
        return binding.root
    }

}