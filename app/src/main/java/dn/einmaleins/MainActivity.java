package dn.einmaleins;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
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
        showKeyboard();

        Bundle extras = getIntent().getExtras();
        final String testDifficulty = extras.getString("testDifficulty");
        if ("none".equals(testDifficulty)) {
            applyNewStates(stateChanger.hideProgressBar());
        } else {
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
                            throw new RuntimeException("Time up: " + secondsLeft);
                        } else if (secondsLeft >= 100) {
                            throw new RuntimeException("Woooohoooo: " + secondsLeft);
                        } else {
                            applyNewStates(newStates);
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
        applyNewStates(newStates);
        hideKeyboard();
    }



    private void generateAndShowNewExercise() {
        Bundle extras = getIntent().getExtras();
        final String testDifficulty = extras.getString("testDifficulty");
        if (!"none".equals(testDifficulty)) {
            int buttonSeconds = stateChangerForButtonTimer.getButtonSeconds(testDifficulty);
            buttonTimer = new CountDownTimer(buttonSeconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    List<State> newTimerStates = stateChangerForButtonTimer.computeButtonSecondsLeft((int)millisUntilFinished/1000);
                    applyNewStates(newTimerStates);
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
        applyNewStates(newStates);
    }

    private void showKeyboard() {
        answerEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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

    private void applyNewStates(List<State> newStates) {
        for (State state : newStates) {
            applyState(state);
        }
    }

    private <T extends View> void applyState(State state) {
        try {
            T genericView = findViewById(R.id.class.getField(state.id).getInt(null));
            List<State.Property> viewProperties = state.props;
            for (State.Property prop : viewProperties) {
                String methodName = prop.key;
                Object value = prop.value;
                Class<T> genericViewClass = state.clazz;
                Class<?> valueClass;
                if (value instanceof String) {
                    valueClass = CharSequence.class;
                } else if (value instanceof Integer) {
                    valueClass = int.class;
                } else {
                    throw new RuntimeException("Unexpected type of value: " + value.getClass());
                }
                genericViewClass.getMethod(methodName, valueClass).invoke(genericView, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}
