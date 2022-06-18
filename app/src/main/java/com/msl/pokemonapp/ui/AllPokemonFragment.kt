package com.msl.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    lateinit var adapter : PokemonAdapter
    lateinit var pokemon: List<PokemonResult>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPokemonBinding.inflate(inflater,container,false)
        binding.searchPokemonField.addTextChangedListener { handleSearch() }
        initRecyclerView()
        return binding.root
    }

    // Prepare navController for navigation to single Pokemon
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
    }

    // Prepare recyclerView - observe live data
    private fun initRecyclerView(){
        var recyclerView: RecyclerView = binding.allPokemonRecycler
        adapter = PokemonAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        pokemonViewModel.getAllPokemon()
        pokemonViewModel.allPokemon().observe(viewLifecycleOwner, Observer {
            pokemon = it
            adapter.setData(it)
        })
    }

    // Handle item click within recycler view - create bundle and pass to SinglePokemonFragment
    override fun onItemClick(v: View?, pokemon: PokemonResult) {
        // If card clicked
        val bundle = Bundle()
        bundle.putString("pokemon", pokemon.name)
        navController!!.navigate(R.id.singlePokemonFragment, bundle)
    }

    // Reduce pokemon down to those with the text in their name - Send to adapter
    private fun handleSearch(){
        var searchedPokemon = pokemon.filter { pokemon -> binding.searchPokemonField.text in pokemon.name}
        adapter.setData(searchedPokemon)
    }
}