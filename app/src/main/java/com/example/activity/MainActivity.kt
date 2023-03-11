package com.example.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    var count: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = findViewById<TextView>(R.id.textViewData)
        val button1 = findViewById<Button>(R.id.button1)
        val name = findViewById<TextView>(R.id.Name)
        val age = findViewById<TextView>(R.id.Age)
        val id = findViewById<TextView>(R.id.ID)
        val button2 = findViewById<Button>(R.id.button2)
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference()
        // This method is called once with the initial value and again
        // whenever data at this location is updated.

        button1.setOnClickListener {
            var name = name.text.toString()
            var age = age.text.toString()
            var id = id.text.toString()
            val person = hashMapOf(
                "name" to name,
                "age" to age,
                "id" to id
            )
            myRef.child("person").child("$count").setValue(person)
            count++
            Toast.makeText(applicationContext, "success", Toast.LENGTH_LONG).show()

        }
        button2.setOnClickListener {
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val value = snapshot.getValue()
                    data.text = value.toString()
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_LONG).show()


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "failler", Toast.LENGTH_LONG).show()
                    // Log.w(TAG, "Failed to read value.", error.toException())

                }

            })

        }

    }
}
