package project;

import java.io.Serializable;

public class Individual extends Subscriber implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String creditCardNr;
	private int expireMonth;
	private int expireYear;
	private int CCV;
	
	public Individual(String name, String address) {
		super(name, address);
	}
	
    @Override
    public String getBillingInformation() {
        return "Credit Card: " + creditCardNr + ", Expire Date: " + expireMonth + "/" + expireYear + ", CCV: " + CCV;
    }

	public String getCreditCardNr() {
		return creditCardNr;
	}

	public int getExpireMonth() {
		return expireMonth;
	}

	public int getExpireYear() {
		return expireYear;
	}

	public int getCCV() {
		return CCV;
	}
}
