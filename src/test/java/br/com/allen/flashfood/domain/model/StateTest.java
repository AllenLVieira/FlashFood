package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StateTest {
    @Test
    void testCanEqual() {
        assertFalse((new State()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        State state = new State();

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");
        assertTrue(state.canEqual(state1));
    }

    @Test
    void testConstructor() {
        State actualState = new State();
        actualState.setId(123L);
        actualState.setName("Name");
        assertEquals(123L, actualState.getId().longValue());
        assertEquals("Name", actualState.getName());
        assertEquals("State(id=123, name=Name)", actualState.toString());
    }

    @Test
    void testEquals() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        assertFalse(state.equals(null));
    }

    @Test
    void testEquals2() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        assertFalse(state.equals("Different type to State"));
    }

    @Test
    void testEquals3() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        assertTrue(state.equals(state));
        int expectedHashCodeResult = state.hashCode();
        assertEquals(expectedHashCodeResult, state.hashCode());
    }

    @Test
    void testEquals4() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");
        assertTrue(state.equals(state1));
        int expectedHashCodeResult = state.hashCode();
        assertEquals(expectedHashCodeResult, state1.hashCode());
    }

    @Test
    void testEquals5() {
        State state = new State();
        state.setId(1L);
        state.setName("Name");

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");
        assertFalse(state.equals(state1));
    }

    @Test
    void testEquals6() {
        State state = new State();
        state.setId(null);
        state.setName("Name");

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");
        assertFalse(state.equals(state1));
    }

    @Test
    void testEquals7() {
        State state = new State();
        state.setId(null);
        state.setName("Name");

        State state1 = new State();
        state1.setId(null);
        state1.setName("Name");
        assertTrue(state.equals(state1));
        int expectedHashCodeResult = state.hashCode();
        assertEquals(expectedHashCodeResult, state1.hashCode());
    }
}

