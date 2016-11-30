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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static eldad.corem.exercises.birthdayDBHelper.birthdays.TABLE_NAME;
import static java.security.AccessController.getContext;

public class addBirthday extends AppCompatActivity {
    birthdayDBHelper dbHelper;
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
        dbHelper = new birthdayDBHelper(this);

    }

    public void addToDB(View view) {
        int upcomingYear;
        long epoch;
        String Name = name.getText().toString();
        String Comment = comment.getText().toString();
        int day = dp.getDayOfMonth();
        int month = dp.getMonth()+1;
        int year = dp.getYear();
        String BD = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
        Date date = new Date();
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentMonth = 1 + cal.get(Calendar.MONTH);
        if (month > currentMonth) {
            upcomingYear = cal.get(Calendar.YEAR);
        }
        else if (currentMonth > month) {
           upcomingYear = cal.get(Calendar.YEAR) + 1;
        }
        else{
            if (cal.get(Calendar.DAY_OF_MONTH) > day){
                upcomingYear = cal.get(Calendar.YEAR)+1;
            }
            else{
                upcomingYear = cal.get(Calendar.YEAR);
            }
        }

        String upcoming = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(upcomingYear);
        try {
            date = sdf.parse(upcoming);
            System.out.println(date + " - " + currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        epoch = (date.getTime() - currentDate.getTime()) / (1000*60*60*24);
        System.out.println(epoch);
        ContentValues values = new ContentValues();
        values.put(birthdayDBHelper.birthdays.COLUMN_NAME, Name);
        values.put(birthdayDBHelper.birthdays.COLUMN_BIRTHDATE, BD);
        values.put(birthdayDBHelper.birthdays.COLUMN_COMMENT, Comment);
        values.put(birthdayDBHelper.birthdays.COLUMN_DAYSTOBIRTHDAY,epoch);
        long newRowId = db.insert(TABLE_NAME, null, values);
        Toast.makeText(this, "Birthday added!", Toast.LENGTH_SHORT).show();
        name.setText("");
        comment.setText("");
    }
}
