package si.heartchecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateHeartLogHelper extends SQLiteOpenHelper {
	public CreateHeartLogHelper(final Context context) {
		super(context, "HertLog", null, 1);
	}

	@Override
    public final void onCreate(final SQLiteDatabase db) {
		try {
			String sql = "create table HeartLog(" + "Time text primary key,"
					+ "HeartType text not null)";

			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// TODO Auto-generated method stub
	}
}
