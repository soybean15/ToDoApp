package com.devour.todolistapp


import androidx.room.*


@Entity
data class Items(@ColumnInfo(name = "item_name")val  name:String,
                 @ColumnInfo(name = "group_name")val groupName:String,
                 var completed: Boolean){
    @PrimaryKey(autoGenerate = true) var id = 0
}


@Entity
data class Groups(@ColumnInfo(name = "group_name")val name: String){
    @PrimaryKey(autoGenerate = true) var id = 0
}



data class  GroupWithItems(@Embedded val group:Groups,
                           @Relation(parentColumn ="group_name",
                               entityColumn ="group_name" )val items: MutableList<Items>)