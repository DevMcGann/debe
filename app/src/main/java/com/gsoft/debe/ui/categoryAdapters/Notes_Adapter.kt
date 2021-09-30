package com.gsoft.debe.ui.categoryAdapters

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsoft.debe.databinding.ImageItemRowBinding
import com.gsoft.debe.databinding.NoteItemRowBinding
import com.gsoft.debe.utils.BaseViewHolder

class Notes_Adapter(private val context: Context,
                    private val itemClickListener: Notes_Adapter.OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var itemList: MutableList<String> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(item: String, position: Int)
    }

    fun setListaDeItems(lista: MutableList<String>) {
        itemList = lista
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int) {
        itemList.removeAt(index)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val NoteBinding =
            NoteItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = ItemViewHolder(NoteBinding)

        NoteBinding.root.setOnClickListener {
            val position =
                holder.adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onItemClick(itemList[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is Notes_Adapter.ItemViewHolder -> holder.bind(itemList[position])
        }
    }

    private inner class ItemViewHolder(val binding: NoteItemRowBinding) :
        BaseViewHolder<String>(binding.root) {
        override fun bind(item: String) = with(binding) {
            etNote.text = item
        }
    }


}
