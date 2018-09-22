package dn.einmaleins;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StateChanger stateChanger = new StateChanger();
    private StateChanger stateChangerForTimer = new StateChanger();
    private EditText answerEditText;
    private String testDifficulty = "";
    private int wrongAnswers = 0;

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
        if (extras != null) {
            String bla = extras.getString("testDifficulty");
            CountDownTimer timer = new CountDownTimer(50000, 1000) {
                @Override
                public void onTick(long millisLeft) {
                    List<State> newStates = stateChangerForTimer.computeSecondsLeft(millisLeft, wrongAnswers, testDifficulty);
                    applyNewStates(newStates);
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }

    public void showExercise(View view) {
        generateAndShowNewExercise();
        showKeyboard();
    }

    public void showAnswer(View view) {
        String enteredAnswer = answerEditText.getText().toString();

        List<State> newStates = stateChanger.showAnswer(enteredAnswer);
        applyNewStates(newStates);
        hideKeyboard();
    }



    private void generateAndShowNewExercise() {
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
