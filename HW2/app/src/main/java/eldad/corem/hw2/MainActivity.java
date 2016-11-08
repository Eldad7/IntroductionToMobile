package eldad.corem.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText firstNum;
    EditText secondNum;
    //Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Go(View view) {
        int results[] = new int[2];
        firstNum = (EditText) findViewById(R.id.firstNumber);
        secondNum = (EditText) findViewById(R.id.secondNumber);
        results[0] = Integer.valueOf(firstNum.getText().toString());
        results[1] = Integer.valueOf(secondNum.getText().toString());
        Bundle b = new Bundle();
        b.putIntArray("mathOp", results);
        Intent intent = new Intent(this, calculate.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }
}
