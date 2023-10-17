package com.example.rutinas.Contracts

import android.provider.BaseColumns

object UserContract {
    // Table and column names
    const val TABLE_NAME = "users"
    const val COLUMN_USERNAME = "username"
    const val COLUMN_PASSWORD = "password"

    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    // SQL statements
    const val SQL_CREATE_USER_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $COLUMN_USERNAME TEXT PRIMARY KEY,
            $COLUMN_PASSWORD TEXT
        )
    """

    const val SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
