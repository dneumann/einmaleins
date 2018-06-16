package dn.einmaleins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator calc = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateExercise(View view) {
        calc.createNewExercise();
        String exerciseString = calc.getMultiExercise();
        TextView tv = findViewById(R.id.textView);
        tv.setText(exerciseString);

        Button buttonToHide = findViewById(R.id.button);
        Button buttonToShow = findViewById(R.id.button2);

        buttonToHide.setVisibility(View.INVISIBLE);
        buttonToShow.setVisibility(View.VISIBLE);
    }

    public void showAnswer(View view) {
        String answerString = calc.getMultiAnswer() + " " + calc.getHint();
        TextView tv = findViewById(R.id.textView2);
        tv.setText(answerString);

        Button buttonToHide = findViewById(R.id.button2);
        Button buttonToShow = findViewById(R.id.button);

        buttonToHide.setVisibility(View.INVISIBLE);
        buttonToShow.setVisibility(View.VISIBLE);

    }
}
