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

        State textView = new State("textView", TextView.class);
        textView.props.put("setText", calc.getMultiExercise());

        State textView2 = new State("textView2", TextView.class);
        textView2.props.put("setText", "");

        State button = new State("button", Button.class);
        button.props.put("setVisibility", View.INVISIBLE);

        State button2 = new State("button2", Button.class);
        button2.props.put("setVisibility", View.VISIBLE);

        return list(textView, textView2, button, button2);
    }

    private List<State> list(State... states) {
        return new ArrayList<State>(Arrays.asList(states));
    }

    public List<State> showAnswer() {
        State textView = new State("textView2", TextView.class);
        CharSequence text = calc.getMultiAnswer() + " -> " + calc.getHint();
        textView.props.put("setText", text);

        State button2 = new State("button2", Button.class);
        button2.props.put("setVisibility", View.INVISIBLE);

        State button = new State("button", Button.class);
        button.props.put("setVisibility", View.VISIBLE);

        return list(textView, button, button2);
    }
}
