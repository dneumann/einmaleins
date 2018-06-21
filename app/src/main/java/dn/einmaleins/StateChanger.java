package dn.einmaleins;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        return list(textView, textView2, editText, button, button2);
    }

    public List<State> showAnswer(String enteredAnswer) {
        String correctAnswer = calc.getMultiAnswer();

        State editText = State.create("editText_enteredAnswer", EditText.class);
        if (correctAnswer.equals(enteredAnswer)) {
            editText.with("setTextColor", Color.GREEN);
        } else {
            editText.with("setTextColor", Color.RED);
        }

        State textView = State.create("textView_answer", TextView.class)
                .with("setText", correctAnswer + " -> " + calc.getHint());

        State button2 = State.create("button_showAnswer", Button.class)
                .with("setVisibility", View.INVISIBLE);

        State button = State.create("button_newExercise", Button.class)
                .with("setVisibility", View.VISIBLE);

        return list(editText, textView, button, button2);
    }


    private List<State> list(State... states) {
        return new ArrayList<>(Arrays.asList(states));
    }


    // for unit testing
    void setCalculator(Calculator newCalc) {
        calc = newCalc;
    }

}
