package dn.einmaleins;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StateChanger stateChanger = new StateChanger();
    private StateChanger stateChangerForTimer = new StateChanger();
    private StateChanger stateChangerForButtonTimer = new StateChanger();
    private EditText answerEditText;
    private int wrongAnswers = 0;
    private int correctAnswers = 0;
    private boolean progressTimerRunning = false;
    private CountDownTimer buttonTimer;
    private ViewChanger viewChanger = new ViewChanger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answerEditText = findViewById(R.id.editText_enteredAnswer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        generateAndShowNewExercise();

        Bundle extras = getIntent().getExtras();
        final String testDifficulty = extras.getString("testDifficulty");
        if ("none".equals(testDifficulty)) {
            viewChanger.applyNewStates(stateChanger.hideProgressBar(), this);
        } else {
            final MainActivity referenceToThis = this;
            Thread timer = new Thread(new Runnable() {
                @Override
                public void run() {
                    progressTimerRunning = true;
                    long startTime = System.currentTimeMillis();
                    while (progressTimerRunning) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long millisLeft = 50000L - (System.currentTimeMillis() - startTime);
                        List<State> newStates = stateChangerForTimer
                                .computeSecondsLeft(millisLeft, correctAnswers, wrongAnswers, testDifficulty);
                        int secondsLeft = (int)newStates.get(0).getProp("setProgress");
                        if (secondsLeft <= 0) {
                            Intent startsResults = new Intent(referenceToThis, ResultsActivity.class);
                            startsResults.putExtra("gameWon", false);
                            startsResults.putExtra("correctAnswers", correctAnswers);
                            startsResults.putExtra("wrongAnswers", wrongAnswers);
                            startsResults.putExtra("testDifficulty", testDifficulty);
                            startsResults.putExtra("timePassed", System.currentTimeMillis() - startTime);
                            startActivity(startsResults);
                            progressTimerRunning = false;
                        } else if (secondsLeft >= 100) {
                            throw new RuntimeException("Woooohoooo: " + secondsLeft);
                        } else {
                            viewChanger.applyNewStates(newStates, referenceToThis);
                        }
                    }
                }
            });
            timer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        progressTimerRunning = false;
    }

    public void showExercise(View view) {
        generateAndShowNewExercise();
        showKeyboard();
    }

    public void showAnswer(View view) {
        if (buttonTimer != null)
            buttonTimer.cancel();

        String enteredAnswer = answerEditText.getText().toString();

        List<State> newStates = stateChanger.showAnswer(enteredAnswer);
        int correctVisibility = (int) getState(newStates, "textView_correct").getProp("setVisibility");
        if (correctVisibility == View.VISIBLE)
            correctAnswers++;
        else
            wrongAnswers++;
        viewChanger.applyNewStates(newStates, this);
        hideKeyboard();
    }



    private void generateAndShowNewExercise() {
        Bundle extras = getIntent().getExtras();
        final String testDifficulty = extras.getString("testDifficulty");
        if (!"none".equals(testDifficulty)) {
            int buttonSeconds = stateChangerForButtonTimer.getButtonSeconds(testDifficulty);
            final MainActivity referenceToThis = this;
            buttonTimer = new CountDownTimer(buttonSeconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    List<State> newTimerStates = stateChangerForButtonTimer.computeButtonSecondsLeft((int)millisUntilFinished/1000);
                    viewChanger.applyNewStates(newTimerStates, referenceToThis);
                }

                @Override
                public void onFinish() {
                    Button b = findViewById(R.id.button_showAnswer);
                    b.performClick();
                }
            };
            buttonTimer.start();
        }
        List<State> newStates = stateChanger.generateExercise();
        viewChanger.applyNewStates(newStates, this);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(answerEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(answerEditText.getWindowToken(), 0);
    }

    private State getState(List<State> states, String viewId) {
        for (State s : states) {
            if (s.id.equals(viewId))
                return s;
        }
        return null;
    }

}
