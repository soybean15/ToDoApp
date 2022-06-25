package com.devour.todolistapp

class AppData {

    companion object DataHolder{


        var dbFileName="todo_db"
        lateinit var db:TodoDatabase

        var groups: MutableList<GroupWithItems> = mutableListOf()

        fun initialize(){
            val group1 = Groups("Home")
            val item1 = Items("Bread", group1.name, false)
            val item2 = Items("Milk", group1.name,false)
            val groupWithItems1 = GroupWithItems(group1, mutableListOf(item1,item2))

            val group2 = Groups("Training")
            val item3 = Items("Tap to Cross",group2.name, true)
            val item4 = Items("Long Press to Delete", group2.name,false)

            val groupWithItems2 = GroupWithItems(group2, mutableListOf(item3,item4))





            groups = mutableListOf(groupWithItems1, groupWithItems2)

        }
    }
}