package com.ozancanguz.kotlintodolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var item:EditText
    lateinit var add:Button
    lateinit var listview:ListView

    var itemlist=ArrayList<String>()

    var fileHelper=FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item=findViewById(R.id.edittext)
        add=findViewById(R.id.addButton)
        listview=findViewById(R.id.list)

            itemlist=fileHelper.readData(this)

        var arrayAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemlist)
        listview.adapter=arrayAdapter


        add.setOnClickListener {
            var itemName=item.text.toString()
            itemlist.add(itemName)
            item.setText("")
            fileHelper.writeData(itemlist,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listview.setOnItemClickListener { adapterView, view, position, l ->

            var alert=AlertDialog.Builder(this)
            alert.setTitle("SİL")
            alert.setMessage("Listeden silmek istiyor musunuz ?")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.cancel()

            })
            alert.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->

                itemlist.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemlist,applicationContext)



            })
                   alert.create().show()

        }
    }
}