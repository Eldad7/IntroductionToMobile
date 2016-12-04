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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyBirthdays extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner, spinnerType;
    ArrayList<String> list;
    birthdayDBHelper dbHelper;
    ListView listView;
    ArrayAdapter<String> adapter1;
    static BDkeeper popUpHelper[];

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String clicked = (String) listView.getItemAtPosition(position);
                int i=0;
                while(i<list.size()){
                    if (clicked.equals(popUpHelper[i].toString())){
                        Intent intent = new Intent(getBaseContext(), birthdayInfo.class);
                        intent.putExtra("index", i);
                        startActivity(intent);
                        break;
                    }
                    else {
                        i++;
                    }
                }
                adapter1.notifyDataSetChanged();
            }
        });
    }


    public void addBirthday(View view) {
        Intent intent = new Intent(getBaseContext(), addBirthday.class);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                listMaker();
            }
        }).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        listMaker();
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void listMaker(){
        list.clear();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String order = spinner.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String[] projection = {
                birthdayDBHelper.birthdays.COLUMN_NAME,
                birthdayDBHelper.birthdays.COLUMN_BIRTHDATE,
                birthdayDBHelper.birthdays.COLUMN_COMMENT,
                birthdayDBHelper.birthdays.COLUMN_UPCOMINGBIRTHDAY,
                birthdayDBHelper.birthdays.COLUMN_EPOCH
        };

        if (type.equalsIgnoreCase("Date"))
            type = birthdayDBHelper.birthdays.COLUMN_EPOCH;
        else{
            type = birthdayDBHelper.birthdays.COLUMN_NAME;
        }
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
        popUpHelper = new BDkeeper[count];
        int j;
        long epoch=0;
        Date date = new Date();
        Date upcomingDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            for (j=0; j<count; j++) {
                popUpHelper[j] = new BDkeeper(c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_NAME)), c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_UPCOMINGBIRTHDAY)),
                        c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_COMMENT)), c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_BIRTHDATE)));
                list.add(popUpHelper[j].toString());
                upcomingDate = sdf.parse(c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_UPCOMINGBIRTHDAY)));
                epoch = upcomingDate.getTime() - date.getTime();
                if (epoch<-86400000) {
                    dbHelper.update(c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_BIRTHDATE)), c.getString(c.getColumnIndexOrThrow(birthdayDBHelper.birthdays.COLUMN_UPCOMINGBIRTHDAY)), db);
                    adapter1.notifyDataSetChanged();
                }
                c.moveToNext();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
    }

    static class BDkeeper{
        String name;
        String upcomingBirthday;
        String comment;
        String birthDate;
        ArrayList<String> al;

        private BDkeeper(String _name, String _upcomingBirthday, String _comment, String _birthDate){
            name = _name;
            upcomingBirthday = _upcomingBirthday;

            if (_comment.equals(""))
                comment = "No comment provided";
            else {
                comment = _comment;
            }
            birthDate = _birthDate;
            al = new ArrayList<>();
            al.add(name);
            al.add(birthDate);
            al.add(upcomingBirthday);
            al.add(comment);
        }

        public ArrayList<String> getInfo(){
            return al;
        }


        @Override
        public String toString(){
            return (name + " - " + upcomingBirthday);
        }
    }

    public static BDkeeper getBDkeeper(int index){
        return popUpHelper[index];
    }

}
