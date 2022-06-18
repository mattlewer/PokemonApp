package com.msl.pokemonapp.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.msl.pokemonapp.R
import com.msl.pokemonapp.model.PokemonResult

class PokemonAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<PokemonAdapter.CardViewHolder>(){

    var pokemonList : List<PokemonResult> = emptyList()

    fun setData(list: List<PokemonResult>){
        pokemonList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_pokemon_list_item, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.cardText.text = pokemonList[position].name

        // Load image from sprite GitHub
        var urlSplit = pokemonList[position].url.split("/")
        var _id = urlSplit[urlSplit.size - 2]
        var imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${_id}.png"
        Glide.with(holder.card.context)
            .asBitmap()
            .load(imgUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(object: CustomTarget<Bitmap>(350,350){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set background colour of card to most common colour within bitmap image
                    holder.cardImage.setImageBitmap(resource)
                    var color = createPaletteSync(resource).vibrantSwatch
                    holder.card.setCardBackgroundColor(color?.rgb?: ContextCompat.getColor(holder.card.context, R.color.light_grey))
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

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