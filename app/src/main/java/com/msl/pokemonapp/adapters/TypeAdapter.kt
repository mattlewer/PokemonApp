package com.msl.pokemonapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.msl.pokemonapp.R
import com.msl.pokemonapp.model.Type

class TypeAdapter: RecyclerView.Adapter<TypeAdapter.CardViewHolder>() {

    var typeList : List<Type> = emptyList()

    fun setData(list: List<Type>){
        typeList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_pokemon_type_list_item, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardText.text = typeList[position].name
        val color = when (typeList[position].name) {
            "grass", "bug" -> R.color.teal
            "fire" -> R.color.red
            "water", "fighting", "normal" -> R.color.blue
            "electric", "psychic" -> R.color.yellow
            "poison", "ghost" -> R.color.purple
            "ground", "rock" -> R.color.brown
            "dark" -> R.color.black
            else -> R.color.blue
        }
        holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, color))
    }

    override fun getItemCount() = typeList.size

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.typeCard)
        var cardText = itemView.findViewById<TextView>(R.id.typeCardText)
    }

}