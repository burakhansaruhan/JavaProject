package project;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAll {
	public static Test suite( ) {
		TestSuite suite = new TestSuite("All Tests");
		
		suite.addTestSuite(TestDistributor.class);
		suite.addTestSuite(TestJournal.class);
		suite.addTestSuite(TestSubscription.class);
		suite.addTestSuite(TestPaymentInfo.class);
		suite.addTestSuite(TestDateInfo.class);
		suite.addTestSuite(TestIndividual.class);
		suite.addTestSuite(TestCorporation.class);
		suite.addTestSuite(TestSubscriber.class);
		return suite;
	}
}
