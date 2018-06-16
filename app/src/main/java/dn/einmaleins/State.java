package dn.einmaleins;

import java.util.HashMap;
import java.util.Map;

public class State<T> {

    public String id;
    public Class<T> clazz;
    public Map<String, Object> props = new HashMap<>();

    public static <T> State create(String id, Class<T> clazz) {
        State s = new State();
        s.id = id;
        s.clazz = clazz;
        return s;
    }

    public State with(String methodName, Object value) {
        props.put(methodName, value);
        return this;
    }
}
