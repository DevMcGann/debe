package com.gsoft.debe.ui.categoryAdapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsoft.debe.databinding.ImageItemRowBinding
import com.gsoft.debe.utils.BaseViewHolder

class URI_Adapter (
    private val context: Context,
    private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var itemList : MutableList<Uri> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(item: Uri, position: Int)
    }

    fun setListaDeItems(lista: MutableList<Uri>) {
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
        BaseViewHolder<Uri>(binding.root) {
        override fun bind(item: Uri) = with(binding) {
            imgItem.setImageURI(item)
        }
    }
}