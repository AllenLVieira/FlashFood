package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    void testCanEqual() {
        assertFalse((new User()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        User user = new User();

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(3, 3, 3, 3, 1));
        assertTrue(user.canEqual(user1));
    }

    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setEmail("jane.doe@example.org");
        ArrayList<Family> familyList = new ArrayList<>();
        actualUser.setGroups(familyList);
        actualUser.setId(123L);
        actualUser.setName("Name");
        actualUser.setPassword("iloveyou");
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualUser.setRegistrationDate(ofResult);
        assertEquals("jane.doe@example.org", actualUser.getEmail());
        assertSame(familyList, actualUser.getGroups());
        assertEquals(123L, actualUser.getId().longValue());
        assertEquals("Name", actualUser.getName());
        assertEquals("iloveyou", actualUser.getPassword());
        assertSame(ofResult, actualUser.getRegistrationDate());
        assertEquals(
                "User(id=123, name=Name, email=jane.doe@example.org, password=iloveyou, registrationDate=0001-01-01T01:01,"
                        + " groups=[])",
                actualUser.toString());
    }

    @Test
    void testEquals() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(user.equals(null));
    }

    @Test
    void testEquals2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(user.equals("Different type to User"));
    }

    @Test
    void testEquals3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertTrue(user.equals(user));
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user.hashCode());
    }

    @Test
    void testEquals4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(123L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertTrue(user.equals(user1));
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user1.hashCode());
    }

    @Test
    void testEquals5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(1L);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(user.equals(user1));
    }

    @Test
    void testEquals6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(null);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(123L);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertFalse(user.equals(user1));
    }

    @Test
    void testEquals7() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setGroups(new ArrayList<>());
        user.setId(null);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setGroups(new ArrayList<>());
        user1.setId(null);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRegistrationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        assertTrue(user.equals(user1));
        int expectedHashCodeResult = user.hashCode();
        assertEquals(expectedHashCodeResult, user1.hashCode());
    }
}

