package com.ebookfrenzy.firebasedatabase

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private var pets = ArrayList<Pet>()

    val changeListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                // database is populated
                var count = snapshot.childrenCount
                pets.clear()
                for (child in snapshot.children){
                    val holdData = child.getValue(Pet::class.java)
                    pets.add(holdData!!)


//                    child.key?.let { Log.i("child", it) }
//                    Log.i("value", child.getValue().toString())
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }


    var petName = ""
    var petSpecies = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val addButton: Button = findViewById(R.id.btnAdd)
        val pNameInput: EditText = findViewById(R.id.pNameTxt)
        val pSpeciesInput: EditText = findViewById(R.id.pSpeciesTxt)
        val petList = createList(25)

        recyclerView.adapter = RecyclerAdapter(petList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        addButton.setOnClickListener {
            petName = pNameInput.text.toString()
            petSpecies = pSpeciesInput.text.toString()

            writeToDB(petName,petSpecies)
        }

        database = Firebase.database.getReference("/pets")
        database.addValueEventListener(changeListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(changeListener)
    }


    private fun writeToDB(name:String,species:String) {
        database = Firebase.database.reference
        val usersPet = Pet(name,species)
        database.child("pets").child(usersPet.pName).setValue(usersPet)

    }
    private fun createList(size: Int): List<ListItem> {
        val list = ArrayList<ListItem>()

        for (i in 0 until size) {
            val item = ListItem("item1","item2")
            list += item
        }
        return list
    }


}