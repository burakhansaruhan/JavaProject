package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestJournal extends TestCase {

    @Test
    public void testGetISSN() {
        Journal journal = new Journal("GAME", "ISSN123", 12, 10);

        String expectedISSN = "ISSN123";
        String actualISSN = journal.getISSN();

        assertEquals(expectedISSN, actualISSN);
    }

    @Test
    public void testGetName() {
        Journal journal = new Journal("GAME", "ISSN123", 12, 10);

        String expectedName = "GAME";
        String actualName = journal.getName();

        assertEquals(expectedName, actualName);
    }
}