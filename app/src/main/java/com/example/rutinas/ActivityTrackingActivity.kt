package com.example.rutinas
import ActivityListAdapter
import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.cpcmovilfrontend.R
import com.example.rutinas.Contracts.ActivityContract
import com.example.rutinas.Contracts.ActivityStatus
import com.example.rutinas.DB.DatabaseHelper


class ActivityTrackingActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)

        dbHelper = DatabaseHelper(this)
        loadActivities()

        val addButton=findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val activityNameEditText=findViewById<EditText>(R.id.activityNameEditText)
            val activityName = activityNameEditText.text.toString()
            val dailyRadioButton=findViewById<RadioButton>(R.id.dailyRadioButton)
            val isDaily = dailyRadioButton.isChecked

            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put(ActivityContract.ActivityEntry.COLUMN_NAME, activityName)
            values.put(ActivityContract.ActivityEntry.COLUMN_IS_DAILY, isDaily)
            values.put(ActivityContract.ActivityEntry.COLUMN_STATUS, ActivityStatus.IN_PROGRESS.ordinal)

            val newRowId = db.insert(ActivityContract.ActivityEntry.TABLE_NAME, null, values)

            if (newRowId != -1L) {


            } else {
                // Activity addition failed, display an error message
            }

            db.close()
        }
    }
    private fun loadActivities() {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            ActivityContract.ActivityEntry.COLUMN_ID,
            ActivityContract.ActivityEntry.COLUMN_NAME,
            ActivityContract.ActivityEntry.COLUMN_IS_DAILY,
            ActivityContract.ActivityEntry.COLUMN_STATUS
        )

        val cursor: Cursor = db.query(
            ActivityContract.ActivityEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val adapter = ActivityListAdapter(this, cursor)

        val activityListView=findViewById<ListView>(R.id.activityListView)
        activityListView.adapter = adapter

        cursor.close()
        db.close()
    }
    private fun updateActivityStatus(activityId: Long, newStatus: Int) {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put(ActivityContract.ActivityEntry.COLUMN_STATUS, newStatus)

        val selection = "${ActivityContract.ActivityEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(activityId.toString())

        val updatedRows = db.update(
            ActivityContract.ActivityEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        if (updatedRows > 0) {
            // Activity status updated successfully, reload activities to reflect the changes
            loadActivities()
        } else {
            // Activity status update failed, display an error message
        }

        db.close()
    }
    private fun sendActivityStatusNotification(activityId: Long, newStatus: Int) {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ActivityContract.ActivityEntry.TABLE_NAME,
            arrayOf(ActivityContract.ActivityEntry.COLUMN_NAME),
            "${ActivityContract.ActivityEntry.COLUMN_ID} = ?",
            arrayOf(activityId.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val activityName =
                cursor.getString(cursor.getColumnIndexOrThrow(ActivityContract.ActivityEntry.COLUMN_NAME))
            val status = if (newStatus ==ActivityStatus.DONE.ordinal) "Done" else "In Progress"

            NotificationUtils.createNotificationChannel(this)
            NotificationUtils.sendNotification(this, activityName, status)
        }

        cursor.close()
    }
}
