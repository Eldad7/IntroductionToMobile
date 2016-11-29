package eldad.corem.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static android.support.constraint.ConstraintSet.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        ArrayItem[] hw = new ArrayItem[]{
                new ArrayItem("HW#2: Simple Calculator", lessonTwo.class),
                new ArrayItem("HW#3.a: ConstraintLayout", lessonThree.class),
                new ArrayItem("HW#3.b: ConstraintLayout", lessonThreeB.class),
                new ArrayItem("HW#3.c: ConstraintLayout", lessonThreeC.class),
                new ArrayItem("HW#4: Birthday List", MyBirthdays.class)
        };

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
                }
            }
        });
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                showAbout();
                return true;
            default: return false;
        }
    }

    private void showAbout() {
        Intent intent = new Intent(getBaseContext(), about.class);
        startActivity(intent);
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
}
