package com.example.adopt_meandroid

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class Chooser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooser)
        var imageView = findViewById<ImageView>(R.id.PetPhoto)
        Picasso.get()
            .load("https://www.catalunyaplants.com/wp-content/uploads/2015/01/golden-retriever.jpg")
            .resize(200, 200)
            .centerCrop()
            .into(imageView)
    }
}