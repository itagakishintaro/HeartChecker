package si.heartchecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CreateHeartLogHelper extends SQLiteOpenHelper {
	public CreateHeartLogHelper(Context context) {
		super(context, "HertLog", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String sql = "create table HeartLog(" + "Time text primary key,"
					+ "HeartType text not null)";

			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
