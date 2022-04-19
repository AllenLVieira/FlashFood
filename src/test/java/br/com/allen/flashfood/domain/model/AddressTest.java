package br.com.allen.flashfood.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void testCanEqual() {
        assertFalse((new Address()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Address address = new Address();

        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);

        Address address1 = new Address();
        address1.setCity(city);
        address1.setComplement("Complement");
        address1.setDistrict("District");
        address1.setNumber("42");
        address1.setStreet("Street");
        address1.setZipCode("21654");
        assertTrue(address.canEqual(address1));
    }

    @Test
    void testConstructor() {
        Address actualAddress = new Address();
        State state = new State();
        state.setId(123L);
        state.setName("Name");
        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);
        actualAddress.setCity(city);
        actualAddress.setComplement("Complement");
        actualAddress.setDistrict("District");
        actualAddress.setNumber("42");
        actualAddress.setStreet("Street");
        actualAddress.setZipCode("21654");
        assertSame(city, actualAddress.getCity());
        assertEquals("Complement", actualAddress.getComplement());
        assertEquals("District", actualAddress.getDistrict());
        assertEquals("42", actualAddress.getNumber());
        assertEquals("Street", actualAddress.getStreet());
        assertEquals("21654", actualAddress.getZipCode());
        assertEquals("Address(zipCode=21654, street=Street, number=42, city=City(id=123, name=Name, state=State(id=123,"
                + " name=Name)), district=District, complement=Complement)", actualAddress.toString());
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

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");
        assertNotEquals(null, address);
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

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");
        assertNotEquals("Different type to Address", address);
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

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");
        assertEquals(address, address);
        int expectedHashCodeResult = address.hashCode();
        assertEquals(expectedHashCodeResult, address.hashCode());
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

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        State state1 = new State();
        state1.setId(123L);
        state1.setName("Name");

        City city1 = new City();
        city1.setId(123L);
        city1.setName("Name");
        city1.setState(state1);

        Address address1 = new Address();
        address1.setCity(city1);
        address1.setComplement("Complement");
        address1.setDistrict("District");
        address1.setNumber("42");
        address1.setStreet("Street");
        address1.setZipCode("21654");
        assertEquals(address, address1);
        int expectedHashCodeResult = address.hashCode();
        assertEquals(expectedHashCodeResult, address1.hashCode());
    }
}

