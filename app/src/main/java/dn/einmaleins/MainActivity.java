package dn.einmaleins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calc = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bla(View view) {
        calc.createNewExercise();
        String exerciseString = calc.getMultiExercise();
        TextView tv = findViewById(R.id.textView);
        tv.setText(exerciseString);
    }
}
