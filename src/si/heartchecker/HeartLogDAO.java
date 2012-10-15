package si.heartchecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HeartLogDAO {
    private SQLiteDatabase db = null;

    public HeartLogDAO(final SQLiteDatabase db) {
        this.db = db;
    }

    // insert data
    public final void recordHeart(final String heartType) {
        try {
            ContentValues values = new ContentValues();
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            values.put("Time", df.format(date));
            values.put("HeartType", heartType);
            db.insert("HeartLog", null, values);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    // heartTypeのTap数を取得
    public final int countHeart(final String heartType) {
        try {
            String[] columns = new String[] { "Time", "HeartType" };
            String where = "HeartType=?";
            Cursor cursor = db.query("HeartLog", columns, where,
                    new String[] { heartType }, null, null, null);

            return cursor.getCount();

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            return 0;
        }
    }

    public final int countHeartInDate(final String heartType, final String date) {
        try {
            String[] columns = new String[] { "Time", "HeartType" };
            String where = "HeartType=? AND Time LIKE ?";
            Cursor cursor = db.query("HeartLog", columns, where, new String[] {
                    heartType, date + "%" }, null, null, null);

            return cursor.getCount();

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            return 0;
        }
    }
}
