package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSubscriber extends TestCase {

    @Test
    public void testGetName() {
        Subscriber subscriber = new Individual("BURAKHAN", "YTU");

        String expectedName = "BURAKHAN";
        String actualName = subscriber.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetAddress() {
        Subscriber subscriber = new Individual("SARUHAN", "ISTANBUL");

        String expectedAddress = "ISTANBUL";
        String actualAddress = subscriber.getAddress();

        assertEquals(expectedAddress, actualAddress);
    }
}