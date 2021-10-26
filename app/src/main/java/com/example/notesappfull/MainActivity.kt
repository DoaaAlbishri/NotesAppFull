package com.example.notesappfull

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var button: Button
    lateinit var dbhlr :DBHlr
    lateinit var myRv :RecyclerView
    var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById<EditText>(R.id.editText)
        button = findViewById<Button>(R.id.button)
        myRv = findViewById<RecyclerView>(R.id.recyclerView)

        dbhlr = DBHlr(this)
        show()

        button.setOnClickListener {
            if (!editText.text.isEmpty()) {
                save()
            } else {
                Toast.makeText(this, "The text is empty", Toast.LENGTH_SHORT).show()
            }
            editText.text.clear()
        }
    }

    fun show(){
        list = dbhlr.retrive()
        //recycler view
        myRv()
    }

    fun save(){
        var note = editText.text.toString()
        dbhlr.savedata(note)
        Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show()
        //retrieve data and update recycler view
        show()
    }

    fun update(s1 : String){
         //first we create a variable to hold an AlertDialog builder
                val dialogBuilder = AlertDialog.Builder(this)
                // then we set up the input
                val input = EditText(this)
                input.hint="Enter new note"
                // here we set the message of our alert dialog
                //dialogBuilder.setMessage("Update Note")
                    // positive button text and action
                dialogBuilder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, id ->
                        var dbhlr = DBHlr(this)
                        val str = input.text.toString()
                        dbhlr.update(s1, str)
                    println("updated item")
                    //retrieve data and update recycler view
                    show()
                })
                    // negative button text and action
                    .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                    })
                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Update Note")
                // add the Edit Text
                alert.setView(input)
                // show alert dialog
                alert.show()
    }

    fun delete(s1:String){
                var dbhlr = DBHlr(this)
                dbhlr.delete(s1)
        println("deleted item")
        //retrieve data and update recycler view
        show()
    }
    fun myRv(){
        myRv.adapter = RecyclerViewAdapter(this, list)
        myRv.layoutManager = LinearLayoutManager(this)
    }
}