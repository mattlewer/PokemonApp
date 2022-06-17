package com.msl.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msl.pokemonapp.R
import com.msl.pokemonapp.adapters.PokemonAdapter
import com.msl.pokemonapp.databinding.FragmentAllPokemonBinding
import com.msl.pokemonapp.model.PokemonResult
import com.msl.pokemonapp.viewmodel.PokemonViewModel

class AllPokemonFragment : Fragment(), PokemonAdapter.OnItemClickListener  {

    private var _binding: FragmentAllPokemonBinding? = null
    private val binding get() = _binding!!
    private var navController : NavController? = null
    private val pokemonViewModel: PokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPokemonBinding.inflate(inflater,container,false)
        initRecyclerView()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }


    // Prepare recyclerView for buttons, using Google's FlexboxLayoutManager to keep things dynamic
    private fun initRecyclerView(){
        var recyclerView: RecyclerView = binding.allPokemonRecycler
        var adapter = PokemonAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        pokemonViewModel.getAllPokemon()
        pokemonViewModel.allPokemon().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    override fun onItemClick(v: View?, pokemon: PokemonResult) {
        TODO("Not yet implemented")
    }
}