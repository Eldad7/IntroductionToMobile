package eldad.corem.exercises;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MyBirthdays extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    List<Map> list;
    birthdayDBHelper dbHelper = new birthdayDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_birthdays);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.view_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        list = new ArrayList<>();
    }


    public void addBirthday(View view) {
        Intent intent = new Intent(getBaseContext(), addBirthday.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private void retrieveBD(List<Map> list){
//        db = openOrCreateDatabase("birthdayDB.db",MODE_PRIVATE,null);
//
//        Cursor resultSet = db.rawQuery("Select * from birthdays",null);
//        resultSet.moveToFirst();
//        String username = resultSet.getString(1);
//        String password = resultSet.getString(2);
//    }
}
