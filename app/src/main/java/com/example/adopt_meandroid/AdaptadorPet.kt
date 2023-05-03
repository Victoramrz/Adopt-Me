package com.example.adopt_meandroid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdaptadorPet(val pet: List<Pets>): RecyclerView.Adapter<AdaptadorPet.PetViewHolder>() {

    inner class PetViewHolder(item: View): RecyclerView.ViewHolder(item){
        val tvNombre= item.findViewById<TextView>(R.id.tvNombre)
        val tvRaza = item.findViewById<TextView>(R.id.tvRaza)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val View = LayoutInflater.from(parent.context).inflate(R.layout.like_item_layout, parent, false)
        return PetViewHolder(View)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentPet = pet[position]
        holder.tvNombre.text = currentPet.pet_name
        holder.tvRaza.text = currentPet.pet_race.toString()
    }
    override fun getItemCount() = pet.size
}

