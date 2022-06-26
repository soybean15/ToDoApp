package com.devour.todolistapp

import androidx.annotation.TransitionRes
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface  TodoDao{

    @Insert
    suspend fun insertGroup(group:Groups)

    @Insert
    suspend fun insertItem(item:Items)

    @Transaction @Query("Select * from Groups")
    suspend fun getGroupWithItems(): MutableList<GroupWithItems>

    @Query("Select * from Items where group_name =:groupName")
    suspend fun getItemsOfGroup(groupName:String):MutableList<Items>

    @Query("Delete from Groups where group_name=:groupName")
    suspend fun deleteGroup(groupName: String)

    @Query("Delete from Items where group_name=:groupName and id=:id")
    suspend fun deleteItem(groupName: String, id:Int)

    @Query("Delete from Items where group_name=:groupName")
    suspend fun deleteItemOfGroup(groupName:String)

    @Query("Update Items set completed = :completedVal where item_name=:itemName and group_name=:groupName")
    suspend fun updateItem(groupName:String, itemName: String, completedVal:Boolean)
}