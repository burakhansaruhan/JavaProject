package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestIndividual extends TestCase {

	@Test
    public void testGetName() {
        Individual individual = new Individual("BURAKHAN", "ISTANBUL");

        String expectedName = "BURAKHAN";
        String actualName = individual.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetAddress() {
        Individual individual = new Individual("SARUHAN", "YTU");

        String expectedAddress = "YTU";
        String actualAddress = individual.getAddress();

        assertEquals(expectedAddress, actualAddress);
    }
}
