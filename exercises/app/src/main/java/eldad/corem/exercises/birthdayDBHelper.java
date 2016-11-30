package eldad.corem.exercises;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.security.AccessControlContext;

/**
 * Created by eldadc on 28/11/2016.
 */

public class birthdayDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + birthdays.TABLE_NAME + " (" + birthdays.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                birthdays.COLUMN_BIRTHDATE + TEXT_TYPE + COMMA_SEP +  birthdays.COLUMN_COMMENT + TEXT_TYPE + COMMA_SEP +
                birthdays.COLUMN_DAYSTOBIRTHDAY + " integer)";

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
        public static final String COLUMN_DAYSTOBIRTHDAY = "daysToBirthday";
    }
}
