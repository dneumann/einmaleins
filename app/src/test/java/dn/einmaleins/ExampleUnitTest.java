package dn.einmaleins;

import android.util.Log;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        StateChanger changer = new StateChanger();
        List<State> states = changer.generateExercise();
        State first = states.get(0);
        //assertEquals("bla", first.clazz.getMethod("setText", ""));
    }
}