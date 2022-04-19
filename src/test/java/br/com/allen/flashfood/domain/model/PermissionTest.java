package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PermissionTest {
    @Test
    void testCanEqual() {
        assertFalse((new Permission()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Permission permission = new Permission();

        Permission permission1 = new Permission();
        permission1.setDescription("The characteristics of someone or something");
        permission1.setId(123L);
        permission1.setName("Name");
        assertTrue(permission.canEqual(permission1));
    }

    @Test
    void testConstructor() {
        Permission actualPermission = new Permission();
        actualPermission.setDescription("The characteristics of someone or something");
        actualPermission.setId(123L);
        actualPermission.setName("Name");
        assertEquals("The characteristics of someone or something", actualPermission.getDescription());
        assertEquals(123L, actualPermission.getId().longValue());
        assertEquals("Name", actualPermission.getName());
        assertEquals("Permission(id=123, name=Name, description=The characteristics of someone or something)",
                actualPermission.toString());
    }

    @Test
    void testEquals() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(123L);
        permission.setName("Name");
        assertFalse(permission.equals(null));
    }

    @Test
    void testEquals2() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(123L);
        permission.setName("Name");
        assertFalse(permission.equals("Different type to Permission"));
    }

    @Test
    void testEquals3() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(123L);
        permission.setName("Name");
        assertTrue(permission.equals(permission));
        int expectedHashCodeResult = permission.hashCode();
        assertEquals(expectedHashCodeResult, permission.hashCode());
    }

    @Test
    void testEquals4() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(123L);
        permission.setName("Name");

        Permission permission1 = new Permission();
        permission1.setDescription("The characteristics of someone or something");
        permission1.setId(123L);
        permission1.setName("Name");
        assertTrue(permission.equals(permission1));
        int expectedHashCodeResult = permission.hashCode();
        assertEquals(expectedHashCodeResult, permission1.hashCode());
    }

    @Test
    void testEquals5() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(1L);
        permission.setName("Name");

        Permission permission1 = new Permission();
        permission1.setDescription("The characteristics of someone or something");
        permission1.setId(123L);
        permission1.setName("Name");
        assertFalse(permission.equals(permission1));
    }

    @Test
    void testEquals6() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(null);
        permission.setName("Name");

        Permission permission1 = new Permission();
        permission1.setDescription("The characteristics of someone or something");
        permission1.setId(123L);
        permission1.setName("Name");
        assertFalse(permission.equals(permission1));
    }

    @Test
    void testEquals7() {
        Permission permission = new Permission();
        permission.setDescription("The characteristics of someone or something");
        permission.setId(null);
        permission.setName("Name");

        Permission permission1 = new Permission();
        permission1.setDescription("The characteristics of someone or something");
        permission1.setId(null);
        permission1.setName("Name");
        assertTrue(permission.equals(permission1));
        int expectedHashCodeResult = permission.hashCode();
        assertEquals(expectedHashCodeResult, permission1.hashCode());
    }
}

