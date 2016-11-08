package eldad.corem.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class calculate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        Intent intent = getIntent();
        Bundle b=intent.getExtras();
        int[] results = b.getIntArray("mathOp");
        int number = results[0] + results[1];
        System.out.println(number);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(String.valueOf(number));
        ViewGroup layout = (ViewGroup)findViewById(R.id.activity_calculate);
        layout.addView(textView);
    }


}
