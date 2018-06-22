package dn.einmaleins;

import java.util.ArrayList;
import java.util.List;

public class State<T> {

    public String id;
    public Class<T> clazz;
    public List<Property> props = new ArrayList<>();

    public static <T> State create(String id, Class<T> clazz) {
        State s = new State();
        s.id = id;
        s.clazz = clazz;
        return s;
    }

    public State with(String methodName, Object value) {
        props.add(new Property(methodName, value));
        return this;
    }

    public Object getProp(String key) {
        for (Property prop : props) {
            if (prop.key.equals(key)) {
                return prop.value;
            }
        }
        return null;
    }

    public static class Property {

        public String key;
        public Object value;

        public Property(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
