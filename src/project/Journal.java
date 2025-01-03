package project;

import java.io.Serializable;

public class Journal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private final String ISSN;
	private int frequency;
	private double issuePrice;
	
	public Journal(String name, String iSSN, int frequency, double issuePrice) {
		this.name = name;
		ISSN = iSSN;
		this.frequency = frequency;
		this.issuePrice = issuePrice;
	}

	public void addSubscription(Subscription s) {
        boolean subscriptionExists = false;
        for (Subscription existingSubscription : s.getSubscriber().getSubscriptions()) {
            if (existingSubscription.getJournal().equals(this)) {
                existingSubscription.increaseCopies();
                subscriptionExists = true;
                break;
            }
        }

        if (!subscriptionExists) {
            Subscription newSubscription = new Subscription(s.getDates(), 1, this, s.getSubscriber());
            s.getSubscriber().getSubscriptions().size();
        }
    }

	public String getISSN() {
		return ISSN;
	}

	public String getName() {
		return name;
	}

	public int getFrequency() {
		return frequency;
	}

	public double getIssuePrice() {
		return issuePrice;
	}

	@Override
	public String toString() {
		return "Journal [name=" + name + ", ISSN=" + ISSN + ", frequency=" + frequency + ", issuePrice=" + issuePrice
				+ "]";
	}
	
}
