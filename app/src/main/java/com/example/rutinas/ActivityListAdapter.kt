import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.cpcmovilfrontend.R
import com.example.rutinas.Contracts.ActivityContract
import com.example.rutinas.Contracts.ActivityStatus


class ActivityListAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.activity_list_item, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val activityNameTextView = view.findViewById<TextView>(R.id.activityNameTextView)
        val statusTextView = view.findViewById<TextView>(R.id.statusTextView)

        val activityName = cursor.getString(cursor.getColumnIndexOrThrow(ActivityContract.ActivityEntry.COLUMN_NAME))
        val status = cursor.getInt(cursor.getColumnIndexOrThrow(ActivityContract.ActivityEntry.COLUMN_STATUS))

        activityNameTextView.text = activityName
        statusTextView.text = if (status == ActivityStatus.DONE.ordinal) "Done" else "In Progress"
    }
}
