package com.example.adopt_meandroid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class Chooser : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var namebox: TextView
    val db = FirebaseFirestore.getInstance()
    val pets = db.collection("Mascotas")
    lateinit var user_id: String
    lateinit var pet_id_show: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooser)
        val connector = intent.extras
        imageView = findViewById(R.id.PetPhoto)
        user_id= connector?.getString("user_id").toString()
        val btheart = findViewById<ImageButton>(R.id.btheart)
        val btcross = findViewById<ImageButton>(R.id.btcross)
        namebox = findViewById<TextView>(R.id.namebox)
        NextPet()
        btheart.setOnClickListener(){
            addLike()
        }
        btcross.setOnClickListener(){
            NextPet()
        }
    }

    private fun addLike() {
        val db = FirebaseFirestore.getInstance()
        val LikeList = db.collection("LikeList").document(user_id)
        LikeList.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentArray = documentSnapshot.get("pet_id") as ArrayList<String>
                currentArray.add(pet_id_show)
                LikeList.set(mapOf("pet_id" to currentArray))
                    .addOnSuccessListener { Log.d(TAG, "Documento actualizado") }
                    .addOnFailureListener { e -> Log.e(TAG, "Error al actualizar documento", e) }
            }
        }
        SendInfo()
        NextPet()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.chooser -> {
                Toast.makeText(this@Chooser,"Ya esta en la pagina principal", Toast.LENGTH_SHORT).show()
            }
            R.id.likeList -> {
                val connector = Intent(this@Chooser, LikeList::class.java)
                connector.putExtra("user_id", user_id)
                startActivity(connector)
            }
        }
        return true
    }
    private fun NextPet() {
        pets.get().addOnSuccessListener { documents ->
            val count = documents.size()
            val cantidad = (1..count).random()
            pet_id_show=("A000"+cantidad)
            pets.document(pet_id_show).get().addOnSuccessListener {
                if (it.exists()){
                    Picasso.get()
                        .load(it.get("Foto").toString())
                        .resize(200, 200)
                        .centerCrop()
                        .into(imageView)
                    namebox.text =it.get("Nombre").toString()
                    pet_id_show = it.id
                }
            }
        }
    }

    private fun SendInfo() {
        Toast.makeText(this@Chooser,"Le hemos enviado a la protectora que esta interesado por esta mascota", Toast.LENGTH_SHORT).show()
    }
}


