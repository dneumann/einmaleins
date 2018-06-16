package dn.einmaleins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private StateChanger stateChanger = new StateChanger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateExercise(View view) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<State> newStates = stateChanger.generateExercise();
        applyNewStates(newStates);
    }

    public void showAnswer(View view) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<State> newStates = stateChanger.showAnswer();
        applyNewStates(newStates);
    }


    private void applyNewStates(List<State> newStates) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (State state : newStates) {
            applyState(state);
        }
    }

    private <T extends View> void applyState(State state) {
        try {
            T genericView = findViewById(R.id.class.getField(state.id).getInt(null));
            Map<String, Object> viewProperties = state.props;
            for (Map.Entry<String, Object> entry : viewProperties.entrySet()) {
                String methodName = entry.getKey();
                Object value = entry.getValue();
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
