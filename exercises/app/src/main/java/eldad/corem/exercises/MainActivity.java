package eldad.corem.exercises;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    ListView list;
    static boolean flag;
    private int toActivity, fromActivity, resumeToActivity, resumeFromActivity;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        list = (ListView) findViewById(R.id.list);
        ArrayItem[] hw = new ArrayItem[]{
                new ArrayItem("HW#2: Simple Calculator", lessonTwo.class),
                new ArrayItem("HW#3.a: ConstraintLayout", lessonThree.class),
                new ArrayItem("HW#3.b: ConstraintLayout", lessonThreeB.class),
                new ArrayItem("HW#3.c: ConstraintLayout", lessonThreeC.class),
                new ArrayItem("HW#4: Birthday List", MyBirthdays.class),
                new ArrayItem("HW#5: Settings+Transitions", MySettingsActivity.class),
                new ArrayItem("HW#6: Simple custom view#1", lessonSix.class)
        };
        flag=true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayAdapter<ArrayItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hw);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayItem clicked = (ArrayItem) list.getItemAtPosition(position);
                if (clicked.activity == null) {
                    Toast.makeText(getBaseContext(), "Activity is not attached", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), clicked.activity);
                    startActivity(intent);
                    overridePendingTransition(toActivity, fromActivity);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if (flag!=true)
            overridePendingTransition(resumeToActivity, resumeFromActivity);
        else{
            flag=false;
        }
        setTransition(prefs);
    }

    static class ArrayItem {
        private Class activity;
        private String label;

        public ArrayItem(String label, Class activity) {
            this.label = label;
            this.activity = activity;
        }

        @Override
        public String toString() {
            if (activity != null) {
                return "Open " + label;
            }
            return label;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.my_settings) {
            flag=true;
            Intent intent = new Intent(getBaseContext(), MySettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTransition(SharedPreferences prefs){
        if (prefs.getString("list_preference", "0").equals("Fade")) {
            resumeToActivity = toActivity = R.anim.fade_in;
            resumeFromActivity = fromActivity = R.anim.fade_out;
        }
        else if (prefs.getString("list_preference", "0").equals("Slide Left-Right")) {
            toActivity = R.anim.slide_left_in;
            fromActivity = R.anim.slide_left_out;
            resumeToActivity = R.anim.slide_right_in;
            resumeFromActivity = R.anim.slide_right_out;
        }
        else if (prefs.getString("list_preference", "0").equals("Slide Up-Down")){
                resumeToActivity = toActivity = R.anim.slide_down;
                resumeFromActivity = fromActivity = R.anim.slide_up;
        }
    }

}
