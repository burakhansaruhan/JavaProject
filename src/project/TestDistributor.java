package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDistributor extends TestCase {

    @Test
    public void testAddJournal() {
        Distributor distributor = new Distributor();
        Journal journal1 = new Journal("ISSN123", "Journal1", 0, 0);
        Journal journal2 = new Journal("ISSN456", "Journal2", 0, 0);

        assertTrue(distributor.addJournal(journal1));
        assertFalse(distributor.addJournal(journal1)); // Adding the same journal again should return false
        assertTrue(distributor.addJournal(journal2));
    }

    @Test
    public void testAddSubscriber() {
        Distributor distributor = new Distributor();
        Subscriber subscriber1 = new Individual("Subscriber1", "Address1");
        Subscriber subscriber2 = new Individual("Subscriber2", "Address2");

        assertTrue(distributor.addSubscriber(subscriber1));
        assertFalse(distributor.addSubscriber(subscriber1)); // Adding the same subscriber again should return false
        assertTrue(distributor.addSubscriber(subscriber2));
    }
}
