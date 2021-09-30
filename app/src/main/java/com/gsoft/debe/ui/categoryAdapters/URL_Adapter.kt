package com.gsoft.debe.ui.categoryAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gsoft.debe.databinding.ImageItemRowBinding
import com.gsoft.debe.utils.BaseViewHolder

class URL_Adapter (
    private val context: Context,
    private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var itemList : MutableList<String> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(item: String, position: Int)
    }

    fun setListaDeItems(lista: MutableList<String>) {
        itemList = lista
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int){
        itemList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBInding = ImageItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = ItemViewHolder(itemBInding)

        itemBInding.root.setOnClickListener {
            val position =
                holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onItemClick(itemList[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(itemList[position])
        }
    }


    private inner class ItemViewHolder(val binding: ImageItemRowBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String): Unit = with(binding) {
            Glide.with(context).load(item)
                .centerCrop().into(imgItem)

        }
    }
}