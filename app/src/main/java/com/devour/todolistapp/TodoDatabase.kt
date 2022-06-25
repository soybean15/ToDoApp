package com.devour.todolistapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Items::class, Groups::class], version = 1)
abstract class TodoDatabase:RoomDatabase()
{
    abstract fun todoDao() : TodoDao

    companion object{
        var instance: TodoDatabase? = null
        fun getDatabase(context: Context):TodoDatabase?{
            if(instance==null){
                synchronized(TodoDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, AppData.dbFileName).build()
                }
            }
            return instance
        }
    }
}