package eldad.corem.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class birthdayInfo extends AppCompatActivity {
    TextView name;
    TextView birthdate;
    TextView upcoming;
    TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_info);
        Intent intent  = getIntent();
        int index = intent.getIntExtra("index", 0);
        name = (TextView) findViewById(R.id.name);
        name.setText(MyBirthdays.getBDkeeper(index).getInfo().get(0));
        birthdate = (TextView) findViewById(R.id.birthdate);
        birthdate.setText(MyBirthdays.getBDkeeper(index).getInfo().get(1));
        upcoming = (TextView) findViewById(R.id.upcoming);
        upcoming.setText(MyBirthdays.getBDkeeper(index).getInfo().get(2));
        comment = (TextView) findViewById(R.id.comment);
        comment.setText(MyBirthdays.getBDkeeper(index).getInfo().get(3));
    }
}
