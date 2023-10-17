package com.example.rutinas.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rutinas.Contracts.ActivityContract
import com.example.rutinas.Contracts.UserContract

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(UserContract.SQL_CREATE_USER_TABLE)
        db.execSQL(ActivityContract.SQL_CREATE_ACTIVITY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(UserContract.SQL_DELETE_USER_TABLE)
        db.execSQL(ActivityContract.SQL_DELETE_ACTIVITY_TABLE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyAppDatabase.db"
    }
}