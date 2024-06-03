package com.mubin.starwars.ui.character.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mubin.starwars.data.model.CharacterListResponse
import com.mubin.starwars.databinding.ItemViewCharacterBinding

class CharacterListAdapter : RecyclerView.Adapter<ViewHolder>() {


    private val diffUtilsCallBack =
        object : DiffUtil.ItemCallback<CharacterListResponse.Result?>() {
            override fun areItemsTheSame(
                oldItem: CharacterListResponse.Result,
                newItem: CharacterListResponse.Result,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CharacterListResponse.Result,
                newItem: CharacterListResponse.Result,
            ): Boolean {
                return oldItem == newItem
            }
        }
    private var differ: AsyncListDiffer<CharacterListResponse.Result?> =
        AsyncListDiffer(this, diffUtilsCallBack)

    @SuppressLint("NotifyDataSetChanged")
    fun addDataSet(
        dataList: List<CharacterListResponse.Result?>,
        onSuccess: (Int) -> Unit = {},
    ) {
        val previousList =
            ArrayList(differ.currentList.toMutableList()
                .toMutableSet().toMutableList())
        previousList.addAll(dataList)
        differ.submitList(previousList)
        onSuccess.invoke(differ.currentList.size)
    }

    internal fun collectDataSet(): MutableList<CharacterListResponse.Result?> =
        differ.currentList.toMutableList()


    @SuppressLint("NotifyDataSetChanged")
    fun clearDataSet() {
        this.differ.submitList(null)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CharacterViewHolder(ItemViewCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            holder.bind(position)
        }
    }

    inner class CharacterViewHolder(private val binding: ItemViewCharacterBinding): ViewHolder(binding.root){

        fun bind(position: Int) {

            val item = collectDataSet()[position]

            binding.characterNameTv.text = item?.name
            binding.characterGenderTv.text = item?.gender
            binding.characterBirthYearTv.text = item?.birthYear

        }

    }
}