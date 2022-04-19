package br.com.allen.flashfood.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {
    @Test
    void testCanEqual() {
        assertFalse((new City()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        City city = new City();

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(state);
        assertTrue(city.canEqual(city1));
    }

    @Test
    void testConstructor() {
        City actualCity = new City();
        actualCity.setId(123L);
        actualCity.setName("Name");
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        actualCity.setState(state);
        assertEquals(123L, actualCity.getId().longValue());
        assertEquals("Name", actualCity.getName());
        assertSame(state, actualCity.getState());
        assertEquals("City(id=123, name=Name, state=State(id=123, name=Name))", actualCity.toString());
    }

    @Test
    void testEquals() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);
        assertNotEquals(null, city);
    }

    @Test
    void testEquals2() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);
        assertNotEquals("Different type to City", city);
    }

    @Test
    void testEquals3() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);
        assertEquals(city, city);
        int expectedHashCodeResult = city.hashCode();
        assertEquals(expectedHashCodeResult, city.hashCode());
    }

    @Test
    void testEquals4() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(state1);
        assertEquals(city, city1);
        int expectedHashCodeResult = city.hashCode();
        assertEquals(expectedHashCodeResult, city1.hashCode());
    }

    @Test
    void testEquals5() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(1L);
        city.setName("Name");
        city.setState(state);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(state1);
        assertNotEquals(city, city1);
    }

    @Test
    void testEquals6() {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(null);
        city.setName("Name");
        city.setState(state);

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(state1);
        assertNotEquals(city, city1);
    }
}

