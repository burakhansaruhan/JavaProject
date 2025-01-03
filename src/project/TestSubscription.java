package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSubscription extends TestCase {

    @Test
    public void testGetDates() {
        DateInfo dateInfo = new DateInfo(1, 12, 2023, 2024);
        Subscription subscription = new Subscription(dateInfo, 1, new Journal("Journal1", "ISSN123", 12, 9.99), new Individual("BURAKHAN", "YTU"));

        DateInfo expectedDates = dateInfo;
        DateInfo actualDates = subscription.getDates();

        assertEquals(expectedDates, actualDates);
    }

    @Test
    public void testIncreaseCopies() {
        DateInfo dateInfo = new DateInfo(1, 12, 2023, 2024);
        Subscription subscription = new Subscription(dateInfo, 1, new Journal("Journal2", "ISSN345", 12, 9.99), new Individual("SARUHAN", "ISTANBUL"));

        int initialCopies = subscription.getCopies();
        subscription.increaseCopies();

        int expectedCopies = initialCopies + 1;
        int actualCopies = subscription.getCopies();

        assertEquals(expectedCopies, actualCopies);
    }
}