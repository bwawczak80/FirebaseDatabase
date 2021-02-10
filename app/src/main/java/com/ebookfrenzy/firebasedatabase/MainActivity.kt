package com.ebookfrenzy.firebasedatabase

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
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


    private var pets = ArrayList<ListItem>()


    private val changeListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                // database is populated
                var count = snapshot.childrenCount

                pets.clear()
                for (child in snapshot.children){
                    val holdData = child.getValue(ListItem::class.java)
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

        val petList = createList(5)

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
        val usersPet =ListItem(name,species)
        database.child("pets").child(usersPet.nameTxt).setValue(usersPet)

    }


    //create a placeholder dummy list for recyclerView
    private fun createList(count: Int): List<ListItem> {
        val list = ArrayList<ListItem>()

        for (i in 0 until count) {

            //for (i in pets){
            //val item = pets

            val item = ListItem("item1","item2")

            list += item
        }
        return list
    }


    //get rid of keyboard on touch
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
        }
        return super.dispatchTouchEvent(ev)
    }



}