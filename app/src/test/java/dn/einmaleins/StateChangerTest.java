package dn.einmaleins;

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

        assertEquals(5, states.size());
    }

}
