package project;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Distributor implements Runnable, Serializable {

	private static final long serialVersionUID = 1L;
	private static final int THREAD_POOL_SIZE = 3;
	private transient ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	private Hashtable<String, Journal> journals;
	private Vector<Subscriber> subscribers;
	
    public Distributor() {
        setJournals(new Hashtable<>());
        setSubscribers(new Vector<>());
    }
	
    public boolean addJournal(Journal j) {
        synchronized (this) {
            if (!getJournals().containsKey(j.getISSN())) {
                getJournals().put(j.getISSN(), j);
                return true;
            }
        }
        return false;
    }
	
    public Journal searchJournal(String ISSN) {
        synchronized (this) {
            return getJournals().get(ISSN);
        }
    }
	
    public boolean addSubscriber(Subscriber s) {
        synchronized (this) {
            if (!getSubscribers().contains(s)) {
                getSubscribers().add(s);
                return true;
            }
        }
        return false;
    }

    public Subscriber searchSubscriber(String name) {
        synchronized (this) {
            for (Subscriber subscriber : getSubscribers()) {
                if (subscriber.getName().equals(name)) {
                    return subscriber;
                }
            }
            return null;
        }
    }
	
    public boolean addSubscription(String ISSN, Subscriber subscriber, Subscription subscription) {
        synchronized (this) {
            Journal journal = searchJournal(ISSN);
            Subscriber existingSubscriber = searchSubscriber(subscriber.getName());

            if (journal != null && existingSubscriber != null) {
                for (Subscription existingSubscription : existingSubscriber.getSubscriptions()) {
                    if (existingSubscription.getJournal().equals(journal)) {
                        existingSubscription.increaseCopies();
                        return true;
                    }
                }

                Subscription newSubscription = new Subscription(new DateInfo(1, 12, 2023,2024), 1, journal, existingSubscriber);
                existingSubscriber.getSubscriptions().add(newSubscription);

                return true;
            }

            return false;
        }
    }
	
    public void listAllSendingOrders(int month, int year) {
        synchronized (this) {
            for (Subscriber subscriber : getSubscribers()) {
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    if (subscription.canSend(month)) {
                        System.out.println(subscriber.getName() + " will receive " + subscription.getCopies() + " copies of " + subscription.getJournal().getName());
                    }
                }
            }
        }
    }
	
    public void listSendingOrders(String ISSN, int month, int year) {
        synchronized (this) {
            Journal journal = searchJournal(ISSN);
            if (journal != null) {
                for (Subscriber subscriber : getSubscribers()) {
                    for (Subscription subscription : subscriber.getSubscriptions()) {
                        if (subscription.getJournal().equals(journal) && subscription.canSend(month)) {
                            System.out.println(subscriber.getName() + " will receive " + subscription.getCopies() + " copies of " + subscription.getJournal().getName());
                        }
                    }
                }
            }
        }
    }
	
    public void listIncompletePayments() {
        synchronized (this) {
            for (Subscriber subscriber : getSubscribers()) {
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    if (!subscription.canSend(subscription.getDates().getEndMonth())) {
                        System.out.println(subscriber.getName() + " has incomplete payment for " + subscription.getJournal().getName());
                    }
                }
            }
        }
    }
	
    public void listSubscriptions(String subscriberName) {
        synchronized (this) {
            Subscriber subscriber = searchSubscriber(subscriberName);
            if (subscriber != null) {
                System.out.println(subscriber.getName() + "'s Subscriptions:");
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    System.out.println("Journal: " + subscription.getJournal().getName() +
                            ", Copies: " + subscription.getCopies() +
                            ", Payment: $" + subscription.getPayment().getReceivedPayment());
                }
            }
        }
    }
	
    public void listSubscriptionsByISSN(String ISSN) {
        synchronized (this) {
            System.out.println("Subscriptions for Journal with ISSN " + ISSN + ":");
            for (Subscriber subscriber : getSubscribers()) {
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    if (subscription.getJournal().getISSN().equals(ISSN)) {
                        System.out.println("Subscriber: " + subscriber.getName() +
                                ", Copies: " + subscription.getCopies() +
                                ", Payment: $" + subscription.getPayment().getReceivedPayment());
                    }
                }
            }
        }
    }
	
	
    public synchronized void saveState(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving state.");
        }
    }

    public synchronized void loadState(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Distributor loadedDistributor = (Distributor) inputStream.readObject();
            this.setJournals(loadedDistributor.getJournals());
            this.setSubscribers(loadedDistributor.getSubscribers());
            System.out.println("State loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading state.");
        }
    }

    public void report(JTextArea outputTextArea, int startYear, int endYear) {
        StringBuilder report = new StringBuilder();
        report.append("Generating report...\n");

        Date currentDate = new Date();
        int currentYear = currentDate.getYear() + 1900;

        synchronized (this) {
            for (Subscriber subscriber : getSubscribers()) {
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    if (subscription.getDates().getStartYear() >= startYear && subscription.getDates().getEndYear() <= endYear) {
                        report.append(subscriber.getName() + "'s subscription for journal " + subscription.getJournal().getName() +
                                " will end between " + startYear + " and " + endYear + "\n");
                    }
                }
            }
        }

        report.append("Report generated successfully.");

        SwingUtilities.invokeLater(() -> {
            outputTextArea.setText(report.toString());
        });
    }



    @Override
    public void run() {
        while (true) {
            try {
                checkPaymentsInYear(2023, 2024);
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPaymentsInYear(int startYear, int endYear) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentYear = calendar.get(Calendar.YEAR);

        synchronized (this) {
            for (Subscriber subscriber : getSubscribers()) {
                for (Subscription subscription : subscriber.getSubscriptions()) {
                    int subscriptionEndYear = subscription.getDates().getEndYear();
                    if (subscriptionEndYear >= startYear && subscriptionEndYear <= endYear) {
                        double paymentAmount = subscription.getPayment().getReceivedPayment();
                        if (paymentAmount >= subscription.getJournal().getIssuePrice()) {
                            System.out.println(subscriber.getName() + "'s subscription for journal " +
                                    subscription.getJournal().getName() + " is paid in the specified year range.");
                        }
                    }
                }
            }
        }
    }
	
	public Vector<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Vector<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	public Hashtable<String, Journal> getJournals() {
		return journals;
	}

	public void setJournals(Hashtable<String, Journal> journals) {
		this.journals = journals;
	}
	
}
