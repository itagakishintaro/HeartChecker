package si.heartchecker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HeartCheckerActivity extends Activity {
    private CreateHeartLogHelper helper = null;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        helper = new CreateHeartLogHelper(HeartCheckerActivity.this);

        // view heart count
        for (HeartTypes heartType : HeartTypes.values()) {
            viewHeartCount(heartType.toString());
        }
    }

    public final void onHeartButtonClick(final View v) {
        Button clickedButton = (Button) v;
        String heartType = (String) clickedButton.getText();

        // insert
        SQLiteDatabase db = helper.getWritableDatabase();
        HeartLogDAO heartLogDAO = new HeartLogDAO(db);
        try {
            db.beginTransaction();
            heartLogDAO.recordHeart(heartType);
            db.setTransactionSuccessful();
            db.endTransaction();
        } finally {
            db.close();
        }

        // view heart count
        viewHeartCount(heartType);
    }

    public final void onGraphButtonClick(final View v) {
        Intent intent = new Intent(HeartCheckerActivity.this,
                ChartActivity.class);
        startActivity(intent);
    }

    // view heart count
    public final void viewHeartCount(final String heartType) {
        // set TextView
        TextView heartCountView = null;
        if (heartType.equals("HAPPY")) {
            heartCountView = (TextView) findViewById(R.id.happy_count);
        } else if (heartType.equals("SAD")) {
            heartCountView = (TextView) findViewById(R.id.sad_count);
        } else if (heartType.equals("ANGRY")) {
            heartCountView = (TextView) findViewById(R.id.angry_count);
        } else if (heartType.equals("DOKIDOKI")) {
            heartCountView = (TextView) findViewById(R.id.dokidoki_count);
        } else {
            throw new IllegalArgumentException();
        }

        // view
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        SQLiteDatabase db = helper.getWritableDatabase();
        HeartLogDAO heartLogDAO = new HeartLogDAO(db);
        try {
            heartCountView.setText(String.valueOf(heartLogDAO.countHeartInDate(
                    heartType, df.format(date))));
        } finally {
            db.close();
        }
    }

}
