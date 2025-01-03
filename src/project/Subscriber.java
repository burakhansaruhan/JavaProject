package project;

import java.io.Serializable;
import java.util.Vector;

public abstract class Subscriber implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private Vector<Subscription> subscriptions;
	
	public Subscriber(String name, String address) {
		this.name = name;
		this.address = address;
		this.subscriptions = new Vector<>();
	}
	
	public abstract String getBillingInformation();

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

    public Vector<Subscription> getSubscriptions() {
        return subscriptions;
    }

}
