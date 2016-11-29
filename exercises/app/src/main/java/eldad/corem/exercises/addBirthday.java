package eldad.corem.exercises;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static eldad.corem.exercises.birthdayDBHelper.birthdays.TABLE_NAME;
import static java.security.AccessController.getContext;

public class addBirthday extends AppCompatActivity {
    birthdayDBHelper dbHelper = new birthdayDBHelper(this);
    EditText name;
    DatePicker dp;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);
        name = (EditText) findViewById(R.id.name);
        comment = (EditText) findViewById(R.id.comment);
        dp = (DatePicker) findViewById(R.id.datePicker);
    }

    public void addToDB(View view) {
        String Name = name.getText().toString();
        String Comment = comment.getText().toString();
        String BD = String.valueOf(dp.getYear()) + "/" + String.valueOf(dp.getMonth()) + "/" + String.valueOf(dp.getDayOfMonth());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(birthdayDBHelper.birthdays.COLUMN_NAME, Name);
        values.put(birthdayDBHelper.birthdays.COLUMN_BIRTHDATE, BD);
        values.put(birthdayDBHelper.birthdays.COLUMN_COMMENT, comment);
        long newRowId = db.insert(TABLE_NAME, null, values);
        Toast.makeText(this, "Birthday added!", Toast.LENGTH_SHORT).show();
    }
}
