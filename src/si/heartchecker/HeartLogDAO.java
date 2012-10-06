package si.heartchecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HeartLogDAO {
    CreateHeartLogHelper helper = null;
    SQLiteDatabase db = null;

    public HeartLogDAO(final CreateHeartLogHelper helper) {
        this.helper = helper;
    }

    // insert data
    public final void recordHeart(final String heartType) {
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();

            ContentValues values = new ContentValues();
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            values.put("Time", df.format(date));
            values.put("HeartType", heartType);

            db.insert("HeartLog", null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        } finally {
            db.close();
        }
    }

    // heartTypeのTap数を取得
    public final int countHeart(final String heartType) {
        try {
            db = helper.getWritableDatabase();
            String columns[] = new String[] { "Time", "HeartType" };
            String where = "HeartType=?";
            Cursor cursor = db.query("HeartLog", columns, where,
                    new String[] { heartType }, null, null, null);

            return cursor.getCount();

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            return 0;
        } finally {
            db.close();
        }
    }

    public final int countHeartInDate(final String heartType, final String date) {
        try {
            db = helper.getWritableDatabase();
            String[] columns = new String[] { "Time", "HeartType" };
            String where = "HeartType=? AND Time LIKE ? || '%'";
            Cursor cursor = db.query("HeartLog", columns, where, new String[] {
                    heartType, date }, null, null, null);

            return cursor.getCount();

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            return 0;
        } finally {
            db.close();
        }
    }
}
