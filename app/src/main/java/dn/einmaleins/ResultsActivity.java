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

        Bundle extras = getIntent().getExtras();
        String testDifficulty = extras.getString("testDifficulty");
        boolean gameWon = extras.getBoolean("gameWon");
        int correct = extras.getInt("correctAnswers");
        int wrong = extras.getInt("wrongAnswers");
        long timeMillis = extras.getLong("timePassed");

        List<State> newStates = stateChanger.showEndResults(gameWon, correct, wrong, timeMillis, testDifficulty);
        viewChanger.applyNewStates(newStates, this);

        if (!gameWon) {
            startSound(R.raw.game_over);
        } else if (gameWon && "easy".equals(testDifficulty)) {
            startSound(R.raw.win_easy);
        } else if (gameWon && "normal".equals(testDifficulty)) {
            startSound(R.raw.win_normal);
        } else if (gameWon && "hard".equals(testDifficulty)) {
            int totalSeconds = (int) timeMillis / 1000;
            boolean extraQuick = totalSeconds <= 2 * 60;
            if (extraQuick) {
                startSound(R.raw.win_hard_dirty);
            } else {
                startSound(R.raw.win_hard);
            }
        }

    }

    private void startSound(int soundId) {
        MediaPlayer sound = MediaPlayer.create(this, soundId);
        sound.start();
    }

}
