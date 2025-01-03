package project;

import org.junit.Test;

import junit.framework.TestCase;

public class TestDateInfo extends TestCase {

    @Test
    public void testGetStartMonth() {
        DateInfo dateInfo = new DateInfo(1, 6, 2022, 2023);

        int expectedStartMonth = 1;
        int actualStartMonth = dateInfo.getStartMonth();

        assertEquals(expectedStartMonth, actualStartMonth);
    }

    @Test
    public void testGetEndMonth() {
        DateInfo dateInfo = new DateInfo(1, 6, 2022, 2023);

        int expectedEndMonth = 6;
        int actualEndMonth = dateInfo.getEndMonth();

        assertEquals(expectedEndMonth, actualEndMonth);
    }
}
