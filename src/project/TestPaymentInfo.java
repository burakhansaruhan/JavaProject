package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestPaymentInfo extends TestCase {

    @Test
    public void testGetReceivedPayment() {
        PaymentInfo paymentInfo = new PaymentInfo(0.1, 50.0);

        double expectedReceivedPayment = 50.0;
        double actualReceivedPayment = paymentInfo.getReceivedPayment();

        assertEquals(expectedReceivedPayment, actualReceivedPayment);
    }

    @Test
    public void testIncreasePayment() {
        PaymentInfo paymentInfo = new PaymentInfo(0.1, 50.0);

        double amountToAdd = 25.0;
        paymentInfo.increasePayment(amountToAdd);

        double expectedReceivedPayment = 75.0;
        double actualReceivedPayment = paymentInfo.getReceivedPayment();

        assertEquals(expectedReceivedPayment, actualReceivedPayment); 
    }
    
}