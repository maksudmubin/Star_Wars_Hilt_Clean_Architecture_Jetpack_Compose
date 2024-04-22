package com.mubin.starwars.ui.character.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mubin.starwars.databinding.ItemViewCharacterBinding

class CharacterListAdapter : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CharacterViewHolder(ItemViewCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    inner class CharacterViewHolder(binding: ItemViewCharacterBinding): ViewHolder(binding.root){

        fun bind() {

        }

    }
}