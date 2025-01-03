package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestCorporation extends TestCase {
	
	@Test
	public void testGetBillingInformation() {
        Corporation corporation = new Corporation("ABC Corp", "YTU", 1234, "ABC Bank", 1, 1, 2024, 20122012);

        String expectedBillingInfo = "Bank: ABC Bank, Account Number: 20122012";
        String actualBillingInfo = corporation.getBillingInformation();

        assertEquals(expectedBillingInfo, actualBillingInfo);
    }

	@Test
    public void testGetAccountNumber() {
        Corporation corporation = new Corporation("ABC Corp", "YTU", 1234, "ABC Bank", 1, 1, 2024, 20122012);

        int expectedAccountNumber = 20122012;
        int actualAccountNumber = corporation.getAccountNumber();

        assertEquals(expectedAccountNumber, actualAccountNumber);
    }
	
}
