package com.gsoft.debe.ui.newScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.gsoft.debe.databinding.ItemRowBinding
import com.gsoft.debe.utils.BaseViewHolder

class listAdapter  (
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
        val itemBInding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = ItemViewHolder(itemBInding)

        itemBInding.root.setOnClickListener {
            val position =
                holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
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


    private inner class ItemViewHolder(val binding: ItemRowBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            tItemRow.text = item

        }
    }
}