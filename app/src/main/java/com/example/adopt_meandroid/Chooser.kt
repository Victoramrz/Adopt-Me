package com.example.adopt_meandroid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

class Chooser : AppCompatActivity() {
    lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooser)
        imageView = findViewById(R.id.PetPhoto)
        val btheart = findViewById<ImageButton>(R.id.btheart)
        val btcross = findViewById<ImageButton>(R.id.btcross)
        Picasso.get()
            .load("https://www.catalunyaplants.com/wp-content/uploads/2015/01/golden-retriever.jpg")
            .resize(200, 200)
            .centerCrop()
            .into(imageView)
        btheart.setOnClickListener(){
            SendInfo();
            PassImage();
        }
        btcross.setOnClickListener(){
            PassImage();
        }
    }

    private fun PassImage() {
        Picasso.get()
            .load("https://static.eldiario.es/clip/71d118ff-5ef2-449c-be8a-6c321304fa70_16-9-aspect-ratio_default_0.jpg")
            .resize(200, 200)
            .centerCrop()
            .into(imageView)
    }

    private fun SendInfo() {
        Toast.makeText(this@Chooser,"Le hemos enviado a la protectora que esta interesado por esta mascota", Toast.LENGTH_LONG).show()
    }
}