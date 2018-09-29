package dn.einmaleins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void startPractice(View view) {
        Intent startsMain = new Intent(this, MainActivity.class);
        startActivity(startsMain);
    }

    public void startEasyTest(View view) {
        startTest("easy");
    }
    public void startNormalTest(View view) {
        startTest("normal");
    }
    public void startHardTest(View view) {
        startTest("hard");
    }

    private void startTest(String difficulty) {
        Intent startsMain = new Intent(this, MainActivity.class);
        startsMain.putExtra("testDifficulty", difficulty);
        startActivity(startsMain);
    }
}
