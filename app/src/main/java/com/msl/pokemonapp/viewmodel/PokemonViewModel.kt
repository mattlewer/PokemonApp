package com.msl.pokemonapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msl.pokemonapp.model.ApiResponse
import com.msl.pokemonapp.model.Pokemon
import com.msl.pokemonapp.model.PokemonResult
import com.msl.pokemonapp.util.RetrofitInstance
import com.msl.pokemonapp.util.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel(){
    private val retro = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
    private val _pokemon = MutableLiveData<List<PokemonResult>>()
    private val _singlePokemon = MutableLiveData<Pokemon>()

    fun allPokemon(): LiveData<List<PokemonResult>> {
        return _pokemon
    }

    fun singlePokemon(): LiveData<Pokemon> {
        return _singlePokemon
    }

    fun getAllPokemon(){
        val call = retro.getAllPokemon(100,0)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                response.body()?.results?.let{ list->
                    _pokemon.postValue(list)
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    fun getSinglePokemon(id: Int){
        val call = retro.getSinglePokemon(id)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let{ pokemon ->
                    _singlePokemon.postValue(pokemon)
                }
            }
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                call.cancel()
            }
        })
    }
}