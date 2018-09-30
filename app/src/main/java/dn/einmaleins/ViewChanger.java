package dn.einmaleins;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ViewChanger {

    public void applyNewStates(List<State> newStates, Activity activity) {
        for (State state : newStates) {
            applyState(state, activity);
        }
    }

    private <T extends View> void applyState(State state, Activity activity) {
        try {
            T genericView = activity.findViewById(R.id.class.getField(state.id).getInt(null));
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
