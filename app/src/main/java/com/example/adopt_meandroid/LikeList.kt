package com.example.adopt_meandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LikeList : AppCompatActivity() {
    private  lateinit var recView: RecyclerView
    private  lateinit var userId: String
    private lateinit var adaptador: AdaptadorPet
    private val db = FirebaseFirestore.getInstance()
    private var petList = mutableListOf<Pets>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_list)
        val connector = intent.extras
        userId= connector?.getString("user_id").toString()
        recView = findViewById(R.id.recView)
        fetchFavoritePet(userId)
        recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun fetchFavoritePet(userId: String) {
       userId?.let {
           db.collection("LikeList").document(userId).get().addOnSuccessListener { document->
               val favorites = document["pet_id"]as List<String>
                    petList.clear()
                    val pets =db.collection("Mascotas")
               for (i in 0 .. (favorites.size-1)){
                   pets.document(favorites.get(i)).get().addOnSuccessListener {
                       petList.add(Pets(it.get("Nombre").toString(), it.get("Especie").toString()))
                       if (i == (favorites.size - 1)) {
                           adaptador = AdaptadorPet(petList)
                           recView.adapter = adaptador
                       }
                   }

               }
           }.addOnFailureListener { exception ->
               Log.e("LikeList", "Error fetching favorite pet: ", exception)
           }
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.chooser -> {
                val connector = Intent(this@LikeList, Chooser::class.java)
                connector.putExtra("user_id",userId)
                startActivity(connector)
            }
            R.id.likeList -> {
                Toast.makeText(this@LikeList,"Ya esta en la pagina de me gusta", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}

