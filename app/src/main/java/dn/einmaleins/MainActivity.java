package dn.einmaleins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StateChanger stateChanger = new StateChanger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateExercise(View view) {
        List<State> newStates = stateChanger.generateExercise();
        applyNewStates(newStates);
    }

    public void showAnswer(View view) {
        EditText edText = findViewById(R.id.editText_enteredAnswer);
        String enteredAnswer = edText.getText().toString();

        List<State> newStates = stateChanger.showAnswer(enteredAnswer);
        applyNewStates(newStates);
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
