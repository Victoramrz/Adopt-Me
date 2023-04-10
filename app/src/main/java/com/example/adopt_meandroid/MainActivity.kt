package com.example.adopt_meandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    private lateinit var barraprogreso: ProgressBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barraprogreso = findViewById(R.id.barraprogreso)
        conasynctask()
    }
    private fun conasynctask() {
        val tarea = TareaProgreso()
        tarea.execute()
    }
    fun simulaTarea() {
        try{
            Thread.sleep(500);
        }catch (e: InterruptedException) {}
    }
    inner class TareaProgreso: AsyncTask<Void, Int, Void>(){
        override fun onPreExecute() {
            barraprogreso.max = 100
            barraprogreso.setProgress(0)
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 1 .. 10){
                publishProgress(10)
                simulaTarea()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            barraprogreso.incrementProgressBy(values[0]!!)
        }

        override fun onPostExecute(result: Void?) {
            val conector = Intent(this@MainActivity, Login::class.java)
            startActivity(conector);
        }

        override fun onCancelled() {
        }

    }
}