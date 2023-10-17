package com.example.rutinas.Contracts

import android.provider.BaseColumns

object ActivityContract {
    // Table and column names
    const val TABLE_NAME = "activities"
    const val COLUMN_ID="id"
    const val COLUMN_NAME = "name"
    const val COLUMN_IS_DAILY = "is_daily"
    const val COLUMN_STATUS = "status"


    object ActivityEntry : BaseColumns {
        const val TABLE_NAME = "activities"
        const val COLUMN_ID="id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IS_DAILY = "is_daily"
        const val COLUMN_STATUS = "status"
    }
    // SQL statements
    const val SQL_CREATE_ACTIVITY_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $COLUMN_ID INTEGER AUTOINCREMENT PRIMARY KEY,
            $COLUMN_NAME TEXT ,
            $COLUMN_IS_DAILY INTEGER,
            $COLUMN_STATUS INTEGER
        )
    """

    const val SQL_DELETE_ACTIVITY_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}