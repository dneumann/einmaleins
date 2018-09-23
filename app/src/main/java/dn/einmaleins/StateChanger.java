package dn.einmaleins;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateChanger {

    private Calculator calc = new Calculator();

    public List<State> generateExercise() {
        calc.createNewExercise();

        State textView = State.create("textView_exercise", TextView.class)
                .with("setText", calc.getMultiExercise());

        State textView2 = State.create("textView_answer", TextView.class)
                .with("setText", "");

        State editText = State.create("editText_enteredAnswer", EditText.class)
                .with("setVisibility", View.VISIBLE)
                .with("setText", "")
                .with("setTextColor", Color.BLACK);

        State button = State.create("button_newExercise", Button.class)
                .with("setVisibility", View.INVISIBLE);

        State button2 = State.create("button_showAnswer", Button.class)
                .with("setVisibility", View.VISIBLE);

        State image = State.create("imageView_drawnAnswer", ImageView.class)
                .with("setVisibility", View.INVISIBLE);

        State correctness = State.create("textView_correct", TextView.class)
                .with("setVisibility", View.INVISIBLE);
        State incorrectness = State.create("textView_incorrect", TextView.class)
                .with("setVisibility", View.INVISIBLE);

        return list(textView, textView2, editText, button, button2, image, correctness, incorrectness);
    }

    public List<State> showAnswer(String enteredAnswer) {
        String correctAnswer = calc.getMultiAnswer();

        State editText = State.create("editText_enteredAnswer", EditText.class);
        State correctness = State.create("textView_correct", TextView.class);
        State incorrectness = State.create("textView_incorrect", TextView.class);
        if (correctAnswer.equals(enteredAnswer)) {
            editText.with("setTextColor", Color.GREEN);
            correctness.with("setVisibility", View.VISIBLE);
        } else {
            editText.with("setTextColor", Color.RED);
            incorrectness.with("setVisibility", View.VISIBLE);
            correctness.with("setVisibility", View.INVISIBLE);
        }

        State textView = State.create("textView_answer", TextView.class)
                .with("setText", calc.getHint());

        State button2 = State.create("button_showAnswer", Button.class)
                .with("setVisibility", View.INVISIBLE);

        State button = State.create("button_newExercise", Button.class)
                .with("setVisibility", View.VISIBLE);

        State image = State.create("imageView_drawnAnswer", ImageView.class)
                .with("setVisibility", View.VISIBLE)
                .with("setImageResource", calc.getAnswerImage());

        return list(editText, textView, button, button2, image, correctness, incorrectness);
    }

    public List<State> computeSecondsLeft(long millisLeft, int correctAnswers, int wrongAnswers, String testDifficulty) {
        int secondsLeft = (int) millisLeft / 1000 + correctAnswers * 10 - wrongAnswers * 10;
        State progressBar = State.create("progressBar", ProgressBar.class)
                .with("setProgress", secondsLeft);
        return list(progressBar);
    }


    private List<State> list(State... states) {
        return new ArrayList<>(Arrays.asList(states));
    }


    // for unit testing
    void setCalculator(Calculator newCalc) {
        calc = newCalc;
    }

}
