package com.msl.pokemonapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msl.pokemonapp.R
import com.msl.pokemonapp.model.PokemonResult

class PokemonAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<PokemonAdapter.CardViewHolder>(){

    var pokemonList : List<PokemonResult> = emptyList()

    fun setData(list: List<PokemonResult>){
        pokemonList = list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_pokemon_list_item, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardText.text = pokemonList[position].name
        var urlSplit = pokemonList[position].url.split("/")
        var _id = urlSplit[urlSplit.size - 2]
        var imgUrl = "https://github.com/PokeAPI/sprites/tree/master/sprites/pokemon/" + _id
        Glide.with(holder.card.context)
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.cardImage);
    }

    override fun getItemCount() = pokemonList.size

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var card = itemView.findViewById<CardView>(R.id.pokemonListCard)
        var cardImage = itemView.findViewById<ImageView>(R.id.pokemonListImage)
        var cardText = itemView.findViewById<TextView>(R.id.pokemonListName)

        init{
            card.setOnClickListener(this)
        }

        override fun onClick(v: View, ) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(v, pokemonList[position])
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(v: View?, pokemon: PokemonResult)
    }
}