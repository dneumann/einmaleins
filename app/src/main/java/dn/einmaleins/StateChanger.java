package dn.einmaleins;

import android.view.View;
import android.widget.Button;
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

        State button = State.create("button_newExercise", Button.class)
                .with("setVisibility", View.INVISIBLE);

        State button2 = State.create("button_showAnswer", Button.class)
                .with("setVisibility", View.VISIBLE);

        return list(textView, textView2, button, button2);
    }

    public List<State> showAnswer() {
        CharSequence text = calc.getMultiAnswer() + " -> " + calc.getHint();
        State textView = State.create("textView_answer", TextView.class)
                .with("setText", text);

        State button2 = State.create("button_showAnswer", Button.class)
                .with("setVisibility", View.INVISIBLE);

        State button = State.create("button_newExercise", Button.class)
                .with("setVisibility", View.VISIBLE);

        return list(textView, button, button2);
    }


    private List<State> list(State... states) {
        return new ArrayList<>(Arrays.asList(states));
    }

}
