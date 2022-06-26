package com.devour.todolistapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsActivity : AppCompatActivity(), OnItemClickListener {


    lateinit var groupWithItems:GroupWithItems
    var itemsAdapter:ItemsAdapter? = null


    override fun itemClicked(index: Int) {

        val item = groupWithItems.items[index]
        item.completed=!(item.completed)

        CoroutineScope(Dispatchers.IO).launch {
            AppData.db.todoDao().updateItem(item.groupName, item.name, item.completed)
        }




        itemsAdapter!!.notifyItemChanged(index)
    }

    override fun itemLongClicked(index: Int) {
        val groupName = groupWithItems.group.name
        val id = groupWithItems.items[index].id

        CoroutineScope(Dispatchers.IO).launch {
            AppData.db.todoDao().deleteItem(groupName,id)
        }


        groupWithItems.items.removeAt(index)
        itemsAdapter!!.notifyItemRemoved(index)
        itemsAdapter!!.notifyDataSetChanged()
        //itemsAdapter!!.notifyItemRangeChanged(index,groupWithItems.items.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.items)


        var selectedIndex = intent.getIntExtra("groupIndex",0)
        groupWithItems =AppData.groups[selectedIndex]

        val toolbarTitle :TextView = findViewById(R.id.toolbarTitle)
        toolbarTitle.text = groupWithItems.group.name



        val toolBar  =findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val newItemEditText = findViewById<EditText>(R.id.newItemEditText)


        newItemEditText.setOnKeyListener { view, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                if(event.action == KeyEvent.ACTION_DOWN){
                    val name :String = newItemEditText.text.toString()
                    val item =Items(name, groupWithItems.group.name,false)
                    groupWithItems.items.add(item)

                    itemsAdapter!!.notifyItemInserted(groupWithItems.items.count())

                    CoroutineScope(Dispatchers.IO).launch{
                        AppData.db.todoDao().insertItem(item)

                    }



                    newItemEditText.text.clear()

                    val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

                    inputManager.hideSoftInputFromWindow(view.windowToken,0)

                }
            }
            false
        }




        val itemsRecyclerView:RecyclerView = findViewById(R.id.itemsRecyclerView)

        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsAdapter =ItemsAdapter(groupWithItems,this)

        itemsRecyclerView.adapter =itemsAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

    }

}