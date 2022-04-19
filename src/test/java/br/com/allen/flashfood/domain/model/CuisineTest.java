package br.com.allen.flashfood.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CuisineTest {
    @Test
    void testCanEqual() {
        assertFalse((new Cuisine()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Cuisine cuisine = new Cuisine();

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());
        assertTrue(cuisine.canEqual(cuisine1));
    }

    @Test
    void testConstructor() {
        Cuisine actualCuisine = new Cuisine();
        actualCuisine.setId(123L);
        actualCuisine.setName("Name");
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        actualCuisine.setRestaurant(restaurantList);
        assertEquals(123L, actualCuisine.getId().longValue());
        assertEquals("Name", actualCuisine.getName());
        assertSame(restaurantList, actualCuisine.getRestaurant());
        assertEquals("Cuisine(id=123, name=Name, restaurant=[])", actualCuisine.toString());
    }

    @Test
    void testEquals() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());
        assertNotEquals(null, cuisine);
    }

    @Test
    void testEquals2() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());
        assertNotEquals("Different type to Cuisine", cuisine);
    }

    @Test
    void testEquals3() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());
        assertTrue(cuisine.equals(cuisine));
        int expectedHashCodeResult = cuisine.hashCode();
        assertEquals(expectedHashCodeResult, cuisine.hashCode());
    }

    @Test
    void testEquals4() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());
        assertEquals(cuisine, cuisine1);
        int expectedHashCodeResult = cuisine.hashCode();
        assertEquals(expectedHashCodeResult, cuisine1.hashCode());
    }

    @Test
    void testEquals5() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(1L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());
        assertNotEquals(cuisine, cuisine1);
    }

    @Test
    void testEquals6() {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(null);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Cuisine cuisine1 = new Cuisine();
        cuisine1.setId(123L);
        cuisine1.setName("Name");
        cuisine1.setRestaurant(new ArrayList<>());
        assertNotEquals(cuisine, cuisine1);
    }
}

