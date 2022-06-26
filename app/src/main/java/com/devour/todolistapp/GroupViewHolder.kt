package com.devour.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GroupsViewHolder (inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.group_row,parent,false)){
    private var groupNameTextView: TextView? = null
    private var groupCountTextView: TextView? = null

    init {

        groupCountTextView = itemView.findViewById(R.id.groupCountTextView)
        groupNameTextView = itemView.findViewById(R.id.groupnameTextView)
    }

    fun bind(groupWithItems: GroupWithItems){

        groupNameTextView!!.text = groupWithItems.group.name
        groupCountTextView!!.text = "${groupWithItems.items.count()} items"
    }
}