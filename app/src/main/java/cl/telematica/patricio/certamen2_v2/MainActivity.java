package cl.telematica.patricio.certamen2_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button boton;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                String dato = editText.getText().toString();
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra("DATO",dato);
                startActivity(i);
                break;
        }
    }
}
