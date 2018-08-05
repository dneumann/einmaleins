package dn.einmaleins;

import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StateChangerTest {

    Calculator calcMock = mock(Calculator.class);
    StateChanger stateChanger = new StateChanger();

    @Before
    public void beforeEach() {
        stateChanger.setCalculator(calcMock);
    }

    @Test
    public void generateExercise_numberOfStates() throws Exception {
        List<State> states = stateChanger.generateExercise();

        assertEquals(6, states.size());
    }

    @Test
    public void showAnswer_correctAnswer() throws Exception {
        when(calcMock.getMultiAnswer()).thenReturn("12");
        List<State> states = stateChanger.showAnswer("12");

        assertEquals(Color.GREEN, states.get(0).getProp("setTextColor"));
    }

    @Test
    public void showAnswer_wrongAnswer() throws Exception {
        when(calcMock.getMultiAnswer()).thenReturn("12");
        List<State> states = stateChanger.showAnswer("13");

        assertEquals(Color.RED, states.get(0).getProp("setTextColor"));
    }

}
