package dn.einmaleins;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private StateChanger stateChanger = new StateChanger();
    private ViewChanger viewChanger = new ViewChanger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        String testDifficulty = extras.getString("testDifficulty");
        boolean gameWon = extras.getBoolean("gameWon");
        int correct = extras.getInt("correctAnswers");
        int wrong = extras.getInt("wrongAnswers");
        long timeMillis = extras.getLong("timePassed");

        List<State> newStates = stateChanger.showEndResults(gameWon, correct, wrong, timeMillis);
        viewChanger.applyNewStates(newStates, this);

        if (!gameWon) {
            MediaPlayer gameOver = MediaPlayer.create(this, R.raw.game_over);
            gameOver.start();
        } else if (gameWon && "easy".equals(testDifficulty)) {
            MediaPlayer win = MediaPlayer.create(this, R.raw.win_easy);
            win.start();
        }
    }

}
