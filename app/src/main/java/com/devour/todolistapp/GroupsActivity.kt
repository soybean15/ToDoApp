package com.devour.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception
import java.util.zip.Inflater

class GroupsActivity : AppCompatActivity(), OnGroupClickListener {

    private var groupsAdapter:GroupsAdapter?=null

    override fun groupClicked(index: Int) {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra("groupIndex", index)
        startActivity(intent)

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
    }

    override fun groupLongClicked(index: Int) {

        var groupName = AppData.groups[index].group.name

        CoroutineScope(Dispatchers.IO).launch {
            AppData.db.todoDao().deleteGroup(groupName)
            AppData.db.todoDao().deleteItemOfGroup(groupName)
        }


        AppData.groups.removeAt(index)
        groupsAdapter!!.notifyItemRemoved(index)
        groupsAdapter!!.notifyItemRangeChanged(index,AppData.groups.size)
    }




    private fun databaseFileExist():Boolean{
        return try {
            File(getDatabasePath(AppData.dbFileName).absolutePath).exists()
        }catch (e: Exception){
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.groups)

        val groupRecyclerView :RecyclerView = findViewById(R.id.groupsRecyclerView)

        groupRecyclerView.layoutManager = LinearLayoutManager(this)

        AppData.initialize()
        groupsAdapter = GroupsAdapter(AppData.groups, this)
        groupRecyclerView.adapter = groupsAdapter

        AppData.db = TodoDatabase.getDatabase(this)!!

        dbConnect(groupRecyclerView)
    }


    private fun dbConnect(recyclerView: RecyclerView){

        if(databaseFileExist()){//read content
            CoroutineScope(Dispatchers.IO).launch {
                AppData.groups =AppData.db.todoDao().getGroupWithItems()

                withContext(Dispatchers.Main){
                    groupsAdapter = GroupsAdapter(AppData.groups, this@GroupsActivity)
                    recyclerView.adapter = groupsAdapter
                }
            }


        }else{//first Time opening the app
            AppData.initialize()
            groupsAdapter = GroupsAdapter(AppData.groups, this)
            recyclerView.adapter = groupsAdapter

            CoroutineScope(Dispatchers.IO).launch {
                for (groupWithItems in AppData.groups) {
                    AppData.db.todoDao().insertGroup(groupWithItems.group)

                    for(item in groupWithItems.items){
                        AppData.db.todoDao().insertItem(item)
                    }
                }
            }
        }
    }






    fun createNewGroup(v: View){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("New Group")
        builder.setMessage("Please enter a name for your new group")

        val myInput =EditText(this)
        myInput.inputType = InputType.TYPE_CLASS_TEXT

        builder.setView(myInput)

        builder.setPositiveButton("Save") {dialogue, which->

            val groupName:String = myInput.text.toString()

            val newGroup = Groups(groupName)
            val newGroupWithItems=GroupWithItems(Groups(groupName), mutableListOf())


            AppData.groups.add(newGroupWithItems)



            CoroutineScope(Dispatchers.IO).launch {
                AppData.db.todoDao().insertGroup(newGroup)

            }
            groupsAdapter!!.notifyItemInserted(AppData.groups.count())

        }
        builder.setNegativeButton("Cancel") {dialogue, which->

        }

        val dialouge :AlertDialog =builder.create()
        dialouge.show()
    }

    override fun onResume() {
        super.onResume()
        val groupRecyclerView :RecyclerView = findViewById(R.id.groupsRecyclerView)
        dbConnect(groupRecyclerView)

        groupsAdapter!!.notifyDataSetChanged()
    }


}


