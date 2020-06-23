package com.ideasfactory.greenprojectapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ideasfactory.greenprojectapp.model.Plant
import com.ideasfactory.greenprojectapp.databinding.ItemExploreeBinding

class FriutsRecyclerAdapter : RecyclerView.Adapter<FriutsRecyclerAdapter.ExploreViewHolder>() {

    private var plants : List<Plant> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FriutsRecyclerAdapter.ExploreViewHolder{
        return ExploreViewHolder(ItemExploreeBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun getItemCount(): Int {
        return plants.size
    }

    override fun onBindViewHolder(holder: FriutsRecyclerAdapter.ExploreViewHolder, position: Int) {
            val element = plants [position]
            holder.onBind(element)

    }

    fun setList (newListPlants : List<Plant>){
        plants = newListPlants
    }



    inner class ExploreViewHolder (val binding : ItemExploreeBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(plant : Plant){
            binding.tvExplore.text = plant.namePlant
            Glide.with(binding.ivExplore.context)
                .load(plant.photoPlant)
                .into(binding.ivExplore)

        }
    }
}