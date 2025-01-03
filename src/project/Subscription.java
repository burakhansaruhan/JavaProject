package project;

import java.io.Serializable;

public class Subscription implements Serializable {

	private static final long serialVersionUID = 1L;
	private final DateInfo dates;
	private PaymentInfo payment;
	private int copies;
	private final Journal journal;
	private final Subscriber subscriber;
	private int paymentCount;
	
    public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber) {
        this.dates = dates;
        this.copies = copies;
        this.journal = journal;
        this.subscriber = subscriber;
        this.payment = new PaymentInfo(0, 0);
        this.paymentCount = 0; 
    }
	
    public void acceptPayment(double amount) {
        payment.increasePayment(amount);
    }
    
    public boolean makePayment(double amount) {
        if (amount < journal.getIssuePrice()) {
            System.out.println("Invalid payment amount.");
            return false;
        }

        payment.increasePayment(amount);
        System.out.println("Payment successful. Amount: $" + amount);
        paymentCount++;

        return true;
    }

    public boolean canSend(int issueMonth) {
        return paymentCount >= issueMonth;
    }

	public DateInfo getDates() {
		return dates;
	}

	public PaymentInfo getPayment() {
		return payment;
	}

	public int getCopies() {
		return copies;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public Journal getJournal() {
		return journal;
	}

    public void increaseCopies() {
        copies++;
    }

}
