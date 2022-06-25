package com.devour.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GroupsAdapter(private val list: List<GroupWithItems>, listenerContext: OnGroupClickListener) : RecyclerView.Adapter<GroupsViewHolder>() {

    private var myInterface:OnGroupClickListener = listenerContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GroupsViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        val groupWithItems = list[position]

        holder.bind(groupWithItems)

        holder.itemView.setOnClickListener {
            myInterface.groupClicked(position)
        }

        holder.itemView.setOnLongClickListener {
            myInterface.groupLongClicked(position)
            true

        }
    }

    override fun getItemCount(): Int = list.size

}