package com.devour.todolistapp


import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val groupWithItems: GroupWithItems, listenerContext: OnItemClickListener):RecyclerView.Adapter<ItemsViewHolder>(){

    private var myInterface :OnItemClickListener  = listenerContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item: Items = groupWithItems.items[position]

        holder.bind(item)

        holder.itemView.setOnClickListener {
            myInterface.itemClicked(position)


        }



        holder.itemView.setOnLongClickListener {
            myInterface.itemLongClicked(position)
            true

        }
    }

    override fun getItemCount(): Int = groupWithItems.items.size

}