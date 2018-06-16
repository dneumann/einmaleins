package dn.einmaleins;

import java.util.HashMap;
import java.util.Map;

public class State<T> {

    public String id;
    public Class<T> clazz;
    public Map<String, Object> props = new HashMap<>();

    public State(String id, Class<T> clazz) {
        this.id = id;
        this.clazz = clazz;
    }
}
