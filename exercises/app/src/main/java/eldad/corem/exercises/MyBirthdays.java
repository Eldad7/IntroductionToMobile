package eldad.corem.exercises;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MyBirthdays extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner, spinnerType;
    ArrayList<String> list;
    birthdayDBHelper dbHelper;
    ListView listView;
    ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_birthdays);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter1);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.view_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> viewAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(viewAdapter);
        dbHelper = new birthdayDBHelper(this);
    }




    public void addBirthday(View view) {
        Intent intent = new Intent(getBaseContext(), addBirthday.class);
        startActivity(intent);
        //listMaker();
        //adapter1.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter1.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                listMaker();
            }
        }).start();
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void listMaker(){
        System.out.println("Starting ");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String order = spinner.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String[] projection = {
                birthdayDBHelper.birthdays.COLUMN_NAME,
                birthdayDBHelper.birthdays.COLUMN_BIRTHDATE,
                birthdayDBHelper.birthdays.COLUMN_COMMENT,
                birthdayDBHelper.birthdays.COLUMN_DAYSTOBIRTHDAY
        };
        if (type.equalsIgnoreCase("Date"))
            type = birthdayDBHelper.birthdays.COLUMN_DAYSTOBIRTHDAY;
        if (order.equalsIgnoreCase("Ascending"))
            order = " ASC";
        else{
            order = " DESC";
        }

        String sortOrder = type + " " + order;
        Cursor c = db.query(
                birthdayDBHelper.birthdays.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        int count=c.getCount();
        System.out.println("Count - " + count);
        int j;
        try {
            for (j=0; j<count; j++) {
                String name = c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_NAME));
                int daysToBirthday = c.getInt(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_DAYSTOBIRTHDAY));
                BDkeeper temp = new BDkeeper(name, daysToBirthday);
                temp.toString();
                list.add(temp.toString());
                c.moveToNext();
            }
        } finally {
            c.close();
        }
    }

    static class BDkeeper{
        String name;
        int days;

        private BDkeeper(String _name, int _days){
            name = _name;
            days = _days;
        }

        @Override
        public String toString(){
            return (name + " days to birthday: " + String.valueOf(days));
        }
    }
}
