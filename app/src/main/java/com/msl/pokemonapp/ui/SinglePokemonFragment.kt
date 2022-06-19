package com.msl.pokemonapp.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.rgb
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.msl.pokemonapp.R
import com.msl.pokemonapp.adapters.PokemonAdapter
import com.msl.pokemonapp.adapters.TypeAdapter
import com.msl.pokemonapp.databinding.FragmentSinglePokemonBinding
import com.msl.pokemonapp.model.Pokemon
import com.msl.pokemonapp.viewmodel.PokemonViewModel


class SinglePokemonFragment : Fragment() {

    lateinit var singlePokemonImage : ImageView
    lateinit var singlePokemonName: TextView

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
            println(pokemon)
            preparePage()
            initRecyclerView()

        })
        // Inflate the layout for this fragment
        return binding.root
    }


    private fun preparePage(){
        Glide.with(binding.singlePokemonImage.context)
                .asBitmap()
                .load(pokemon.sprites.front_default)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(object: CustomTarget<Bitmap>(400,400){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        // Ensure loading of data is complete before preparing radar graph
                        binding.singlePokemonImage.setImageBitmap(resource)
                        binding.singlePokemonName.text = pokemon.name
                        var color = createPaletteSync(resource).vibrantSwatch
                        var colorRgb = color?.rgb
                        if(colorRgb == null){
                            colorRgb = rgb(220,220,220)
                        }
                        prepareRadar(colorRgb)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
    }

    // Prepare recyclerView for types - flexbox manager to keep centered
    private fun initRecyclerView(){
        var recyclerView: RecyclerView = binding.typeRecyclerView
        var adapter = TypeAdapter()
        recyclerView.adapter = adapter
        var layoutManager = FlexboxLayoutManager(context)
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.layoutManager = layoutManager
        var types = pokemon.types.map{type -> type.type}
        adapter.setData(types)
    }


    private fun prepareRadar(rgb: Int?) {
        var chart = binding.singlePokemonChart

        // Chart options
        chart.description.isEnabled = false
        chart.setWebLineWidth(1f)
        chart.setWebColor(Color.WHITE)
        chart.setWebLineWidthInner(0.5f)
        chart.setWebColorInner(Color.WHITE)
        chart.setWebAlpha(100)
        chart.legend.isEnabled = false
        chart.setTouchEnabled(false)
        chart.animateY(500, Easing.EaseInCirc)
        chart.scaleX = 1.1F
        chart.scaleY = 1.1F

        // Pull data out of Pokemon object
        var statNames = pokemon.stats.map{stat -> stat.stat.name}
        var statValues = pokemon.stats.map{stat -> stat.base_stat}

        // Create RadarEntries - create RadarDataSet from entries
        var radarValues = mutableListOf<RadarEntry>()
        for(x in statValues){
            radarValues.add(RadarEntry(x.toFloat()))
        }
        var radarDataSet = RadarDataSet(radarValues, "Base Stats")

        // Radar RadarDataSet options
        radarDataSet.setLineWidth(2f)
        radarDataSet.setColor(rgb!!)
        radarDataSet.valueTextColor = Color.WHITE
        radarDataSet.valueTextSize = 10F
        radarDataSet.setDrawFilled(true)
        radarDataSet.fillColor = rgb!!

        // Add RadarDataSet to RadarData
        var radarData = RadarData()
        radarData.addDataSet(radarDataSet)

        // Axis options
        var xAxis = chart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.textSize = 10F
        xAxis.valueFormatter = IndexAxisValueFormatter(statNames)
        var yAxis = chart.yAxis
        yAxis.textColor = Color.WHITE
        yAxis.setDrawLabels(false)
        yAxis.setAxisMinimum(0f)

        // Add data to chart
        chart.data = radarData
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

}