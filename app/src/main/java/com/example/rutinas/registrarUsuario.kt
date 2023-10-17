package com.example.rutinas

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cpcmovilfrontend.R
import com.example.rutinas.Contracts.UserContract
import com.example.rutinas.DB.DatabaseHelper
import com.example.rutinas.Contracts.UserContract.UserEntry
class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        dbHelper = DatabaseHelper(this)

        val registerButton=findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val usernameEditText=findViewById<EditText>(R.id.usernameEditText)
            val username = usernameEditText.text.toString()
            val passwordEditText=findViewById<EditText>(R.id.passwordEditText)
            val password = passwordEditText.text.toString()

            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put(UserContract.UserEntry.COLUMN_USERNAME, username)
            values.put(UserContract.UserEntry.COLUMN_PASSWORD, password)

            val newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values)

            if (newRowId != -1L) {
                // Registration successful, navigate to the activity tracking screen
                startActivity(Intent(this, ActivityTrackingActivity::class.java))
                finish()
            } else {
                // Registration failed, display an error message
            }

            db.close()
        }
    }
}