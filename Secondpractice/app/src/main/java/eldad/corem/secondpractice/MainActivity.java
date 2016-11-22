package eldad.corem.secondpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static eldad.corem.secondpractice.R.id.editText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void buttonClicked(View view){
        String s = editText.getText().toString();
        Toast.makeText(this, "Hello " + s, Toast.LENGTH_LONG).show();

    }

    public void textDelete(View view) {
        editText.setText("");
    }


}
