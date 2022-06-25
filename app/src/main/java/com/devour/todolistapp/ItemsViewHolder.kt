package com.devour.todolistapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :RecyclerView.ViewHolder(inflater.inflate(R.layout.item_row,parent, false)){

    private var itemNameTextView: TextView? =null
    private var itemCheckBox: CheckBox? =null

    init {
        itemNameTextView =itemView.findViewById(R.id.itemNameTextView)
        itemCheckBox = itemView.findViewById(R.id.itemCheckBox)
    }
    fun bind(item:Items){
       itemNameTextView!!.text = item.name
        itemCheckBox!!.isChecked = item.completed
        if(item.completed){
            itemNameTextView!!.paintFlags = itemNameTextView!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            itemNameTextView!!.paintFlags = Paint.ANTI_ALIAS_FLAG
        }
    }
}