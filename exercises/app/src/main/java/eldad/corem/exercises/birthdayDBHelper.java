package eldad.corem.exercises;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eldadc on 28/11/2016.
 */

public class birthdayDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMBER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + birthdays.TABLE_NAME + " (" + birthdays.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                birthdays.COLUMN_BIRTHDATE + TEXT_TYPE + COMMA_SEP +  birthdays.COLUMN_COMMENT + TEXT_TYPE + COMMA_SEP +
                birthdays.COLUMN_EPOCH + NUMBER_TYPE + COMMA_SEP + birthdays.COLUMN_UPCOMINGBIRTHDAY + TEXT_TYPE  +  " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + birthdays.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "birthdays.db";

    public birthdayDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static class birthdays implements BaseColumns {
        public static final String TABLE_NAME = "birthdays";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_BIRTHDATE = "birthdate";
        public static final String COLUMN_COMMENT = "comment";
        public static final String COLUMN_UPCOMINGBIRTHDAY = "upcomingBirthday";
        public static final String COLUMN_EPOCH = "epoch";
    }

    public void update(String birthDate, String upcoming, SQLiteDatabase db) {
        this.getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        Date date;
        String newUpcoming;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(upcoming);
            cal.setTime(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(cal.YEAR, +1);
        newUpcoming = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(cal.get(Calendar.MONTH)+1) + "/" + (cal.get(Calendar.YEAR));
        ContentValues values = new ContentValues();
        values.put(birthdays.COLUMN_UPCOMINGBIRTHDAY, newUpcoming);
        values.put(birthdays.COLUMN_EPOCH, cal.getTime().getTime());
        int rows = db.update(birthdays.TABLE_NAME, values, birthdays.COLUMN_BIRTHDATE + " = ? ", new String[]{birthDate});
    }
}
