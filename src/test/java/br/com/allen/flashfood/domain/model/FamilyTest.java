package br.com.allen.flashfood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FamilyTest {
    @Test
    void testCanEqual() {
        assertFalse((new Family()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Family family = new Family();

        Family family1 = new Family();
        family1.setId(123L);
        family1.setName("Name");
        family1.setPermission(new ArrayList<>());
        assertTrue(family.canEqual(family1));
    }

    @Test
    void testConstructor() {
        Family actualFamily = new Family();
        actualFamily.setId(123L);
        actualFamily.setName("Name");
        ArrayList<Permission> permissionList = new ArrayList<>();
        actualFamily.setPermission(permissionList);
        assertEquals(123L, actualFamily.getId());
        assertEquals("Name", actualFamily.getName());
        assertSame(permissionList, actualFamily.getPermission());
        assertEquals("Family(id=123, name=Name, permission=[])", actualFamily.toString());
    }

    @Test
    void testEquals() {
        Family family = new Family();
        family.setId(123L);
        family.setName("Name");
        family.setPermission(new ArrayList<>());
        assertFalse(family.equals(null));
    }

    @Test
    void testEquals2() {
        Family family = new Family();
        family.setId(123L);
        family.setName("Name");
        family.setPermission(new ArrayList<>());
        assertFalse(family.equals("Different type to Family"));
    }

    @Test
    void testEquals3() {
        Family family = new Family();
        family.setId(123L);
        family.setName("Name");
        family.setPermission(new ArrayList<>());
        assertTrue(family.equals(family));
        int expectedHashCodeResult = family.hashCode();
        assertEquals(expectedHashCodeResult, family.hashCode());
    }

    @Test
    void testEquals4() {
        Family family = new Family();
        family.setId(123L);
        family.setName("Name");
        family.setPermission(new ArrayList<>());

        Family family1 = new Family();
        family1.setId(123L);
        family1.setName("Name");
        family1.setPermission(new ArrayList<>());
        assertTrue(family.equals(family1));
        int expectedHashCodeResult = family.hashCode();
        assertEquals(expectedHashCodeResult, family1.hashCode());
    }

    @Test
    void testEquals5() {
        Family family = new Family();
        family.setId(1L);
        family.setName("Name");
        family.setPermission(new ArrayList<>());

        Family family1 = new Family();
        family1.setId(123L);
        family1.setName("Name");
        family1.setPermission(new ArrayList<>());
        assertFalse(family.equals(family1));
    }
}

