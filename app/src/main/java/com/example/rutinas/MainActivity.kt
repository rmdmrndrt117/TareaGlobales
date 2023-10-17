package com.example.rutinas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.cpcmovilfrontend.R
import com.example.rutinas.Contracts.UserContract
import com.example.rutinas.DB.DatabaseHelper

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val login=findViewById<Button>(R.id.btnLogin)
        val registrar=findViewById<Button>(R.id.btnRegistrar)
        val user=findViewById<EditText>(R.id.txtUsuario)
        val password=findViewById<EditText>(R.id.txtContrasena)


        login.setOnClickListener{
            val usu=user.toString()
            val contra=password.toString()

            val db = DatabaseHelper(this).readableDatabase
            val selection = "${UserContract.UserEntry.COLUMN_USERNAME} = ? AND ${UserContract.UserEntry.COLUMN_PASSWORD} = ?"
            val selectionArgs = arrayOf(usu,contra )

            val cursor = db.query(
                UserContract.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                val intent = Intent(this, ActivityTrackingActivity::class.java)
                startActivity(intent)
            } else {
                // User not found in the database, display an error message.
            }

            cursor.close()
            db.close()


        }
        registrar.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}