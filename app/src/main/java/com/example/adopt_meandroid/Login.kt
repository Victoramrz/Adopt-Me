package com.example.adopt_meandroid

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auten: FirebaseAuth
    lateinit var user_id: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailbox = findViewById<EditText>(R.id.EmailBox)
        val passwordbox = findViewById<EditText>(R.id.PasswordBox)
        val btlogin = findViewById<Button>(R.id.btLogin)
        val btregister = findViewById<Button>(R.id.btRegister)
        auten = FirebaseAuth.getInstance()

        btlogin.setOnClickListener {
            if (emailbox.text.isNotEmpty() && passwordbox.text.isNotEmpty()){
                email= emailbox.text.toString().trim()
                password= passwordbox.text.toString().trim()
                auten.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        user_id = FirebaseAuth.getInstance().getCurrentUser()?.getUid().toString()
                        `login-user`()
                    }else{
                        showMessage("El email o la contraseña estan mal")
                    }
                }
            }else{
                showMessage("El email o la contraseña estan mal")
            }
        }
        btregister.setOnClickListener {
            if (emailbox.text.isNotEmpty() && passwordbox.text.isNotEmpty()){
                email= emailbox.text.toString().trim()
                password= passwordbox.text.toString().trim()
                auten.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        createLikeList()
                    }else{
                        showMessage("Error al crear el usuario")
                    }
                }
            }else{
                showMessage("Debes completar ambos campos")
            }
        }
    }

    private fun createLikeList() {
        val myArray = arrayListOf("")
        user_id = FirebaseAuth.getInstance().getCurrentUser()?.getUid().toString()
        createCollectionWithArray(myArray)
        `login-user`()
    }

    private fun createCollectionWithArray(array: ArrayList<String>) {
        val db = FirebaseFirestore.getInstance()
        val LikeList = db.collection("LikeList").document(user_id)
        array.clear()
        LikeList.set(mapOf("pet_id" to array))
            .addOnSuccessListener { Log.d(TAG, "Collection created successfully") }
            .addOnFailureListener { e -> Log.e(TAG, "Error creating collection", e) }
    }

    private fun `login-user`() {
        val connector = Intent(this@Login, Chooser::class.java)
        connector.putExtra("user_id", user_id)
        startActivity(connector)
    }
    private fun showMessage(message: String) {
        Toast.makeText(this@Login,"${message}", Toast.LENGTH_LONG).show()
    }
}
