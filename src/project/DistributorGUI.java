package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class DistributorGUI implements Serializable {

	private static final long serialVersionUID = 1L;
	private Distributor distributor;

    public DistributorGUI(Distributor distributor) {
        this.distributor = distributor;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Distributor GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // TO APPEAR IN THE CENTER OF THE SCTEEN
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setLayout(new BorderLayout());

        JTextArea outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton listSubscribersButton = new JButton("List Subscribers");
        JButton listJournalsButton = new JButton("List Journals");
        JButton addSubscriptionButton = new JButton("Add Subscription");
        JButton listSubscriptionsButton = new JButton("List Subscriptions");
        JButton addJournalButton = new JButton("Add Journal");
        JButton addSubscriberButton = new JButton("Add Subscriber");
        JButton makePaymentButton = new JButton("Make Payment");
        JButton saveStateButton = new JButton("Save State");
        JButton loadStateButton = new JButton("Load State");
        JButton reportButton = new JButton("Generate Report");

        buttonPanel.add(addJournalButton);
        buttonPanel.add(addSubscriberButton);
        buttonPanel.add(addSubscriptionButton);
        buttonPanel.add(listJournalsButton);
        buttonPanel.add(listSubscribersButton);
        buttonPanel.add(listSubscriptionsButton);
        buttonPanel.add(makePaymentButton);
        buttonPanel.add(saveStateButton);
        buttonPanel.add(loadStateButton);
        buttonPanel.add(reportButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        listSubscribersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listSubscribers(outputTextArea);
            }
        });

        listJournalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listJournals(outputTextArea);
            }
        });

        addSubscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSubscriptionDialog(frame);
            }
        });

        listSubscriptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listSubscriptions(outputTextArea);
            }
        });

        addJournalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJournalDialog(frame);
            }
        });

        addSubscriberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSubscriberDialog(frame);
            }
        });
        
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePaymentDialog(frame);
            }
        });
        
        saveStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStateDialog(frame);
            }
        });

        loadStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStateDialog(frame);
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReportDialog(frame, outputTextArea);
            }
        });

        frame.setVisible(true);
    }

    private void listSubscribers(JTextArea outputTextArea) {
        outputTextArea.setText("List of Subscribers:\n");
        for (Subscriber subscriber : distributor.getSubscribers()) {
            outputTextArea.append(subscriber.getName() + " - " + subscriber.getAddress() + "\n");
        }
    }

    private void listJournals(JTextArea outputTextArea) {
        outputTextArea.setText("List of Journals:\n");
        for (Journal journal : distributor.getJournals().values()) {
            outputTextArea.append(journal.getName() + " - ISSN: " + journal.getISSN() +
                    ", Price: $" + journal.getIssuePrice() + "\n");
        }
    }

    private void addSubscriptionDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Add Subscription", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel subscriberNameLabel = new JLabel("Subscriber Name:");
        JTextField subscriberNameTextField = new JTextField();

        JLabel journalISSNLabel = new JLabel("Journal ISSN:");
        JTextField journalISSTextField = new JTextField();

        JLabel copiesLabel = new JLabel("Number of Copies:");
        JTextField copiesTextField = new JTextField();

        JButton addButton = new JButton("Add Subscription");

        dialog.add(subscriberNameLabel);
        dialog.add(subscriberNameTextField);
        dialog.add(journalISSNLabel);
        dialog.add(journalISSTextField);
        dialog.add(copiesLabel);
        dialog.add(copiesTextField);
        dialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subscriberName = subscriberNameTextField.getText();
                String journalISSN = journalISSTextField.getText();
                int copies = Integer.parseInt(copiesTextField.getText());

                Subscriber subscriber = distributor.searchSubscriber(subscriberName);
                Journal journal = distributor.searchJournal(journalISSN);

                if (subscriber != null && journal != null) {
                    Subscription newSubscription = new Subscription(new DateInfo(1, 12, 2023, 2024), copies, journal, subscriber);

                    if (distributor.addSubscription(journalISSN, subscriber, newSubscription)) {
                        JOptionPane.showMessageDialog(parentFrame, "Subscription added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Subscription already exists for the given subscriber and journal.");
                    }
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Subscriber or Journal not found.");
                }

                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    
    private Subscription findSubscription(Subscriber subscriber, Journal journal) {
        for (Subscription subscription : subscriber.getSubscriptions()) {
            if (subscription.getJournal().equals(journal)) {
                return subscription;
            }
        }
        return null;
    }

    private void listSubscriptions(JTextArea outputTextArea) {
        String subscriberName = JOptionPane.showInputDialog("Enter Subscriber Name:");
        Subscriber subscriber = distributor.searchSubscriber(subscriberName);

        if (subscriber != null) {
            outputTextArea.setText(subscriber.getName() + "'s Subscriptions:\n");
            for (Subscription subscription : subscriber.getSubscriptions()) {
                outputTextArea.append("Journal: " + subscription.getJournal().getName() +
                        ", Copies: " + subscription.getCopies() +
                        ", Payment: $" + subscription.getPayment().getReceivedPayment() + "\n");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Subscriber not found.");
        }
    }

    private void addJournalDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Add Journal", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel journalNameLabel = new JLabel("Journal Name:");
        JTextField journalNameTextField = new JTextField();

        JLabel journalISSNLabel = new JLabel("Journal ISSN:");
        JTextField journalISSTextField = new JTextField();

        JLabel journalFrequencyLabel = new JLabel("Journal Frequency:");
        JTextField journalFrequencyTextField = new JTextField();

        JLabel issuePriceLabel = new JLabel("Issue Price:");
        JTextField issuePriceTextField = new JTextField();

        JButton addButton = new JButton("Add Journal");

        dialog.add(journalNameLabel);
        dialog.add(journalNameTextField);
        dialog.add(journalISSNLabel);
        dialog.add(journalISSTextField);
        dialog.add(journalFrequencyLabel);
        dialog.add(journalFrequencyTextField);
        dialog.add(issuePriceLabel);
        dialog.add(issuePriceTextField);
        dialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String journalName = journalNameTextField.getText();
                String journalISSN = journalISSTextField.getText();
                int frequency = Integer.parseInt(journalFrequencyTextField.getText());
                double issuePrice = Double.parseDouble(issuePriceTextField.getText());

                Journal newJournal = new Journal(journalName, journalISSN, frequency, issuePrice);

                if (distributor.addJournal(newJournal)) {
                    JOptionPane.showMessageDialog(parentFrame, "Journal added successfully.");
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Journal with the same ISSN already exists.");
                }

                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void addSubscriberDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Add Subscriber", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new GridLayout(7, 2));

        JLabel subscriberTypeLabel = new JLabel("Subscriber Type:");
        String[] subscriberTypes = {"Individual", "Corporation"};

     JComboBox<String> subscriberTypeComboBox = new JComboBox<>(subscriberTypes);
     subscriberTypeComboBox.setSelectedItem("Corporation");

        JLabel subscriberNameLabel = new JLabel("Subscriber Name:");
        JTextField subscriberNameTextField = new JTextField();

        JLabel subscriberAddressLabel = new JLabel("Subscriber Address:");
        JTextField subscriberAddressTextField = new JTextField();

        JLabel bankCodeLabel = new JLabel("Bank Code:");
        JTextField bankCodeTextField = new JTextField();

        JLabel bankNameLabel = new JLabel("Bank Name:");
        JTextField bankNameTextField = new JTextField();

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberTextField = new JTextField();

        JButton addButton = new JButton("Add Subscriber");

        subscriberTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) subscriberTypeComboBox.getSelectedItem();
                boolean isCorporationSelected = "Corporation".equals(selectedType);

                bankCodeLabel.setVisible(isCorporationSelected);
                bankCodeTextField.setVisible(isCorporationSelected);
                bankNameLabel.setVisible(isCorporationSelected);
                bankNameTextField.setVisible(isCorporationSelected);
                accountNumberLabel.setVisible(isCorporationSelected);
                accountNumberTextField.setVisible(isCorporationSelected);

                if ("Individual".equals(selectedType)) {
                    bankCodeTextField.setText("");
                    bankNameTextField.setText("");
                    accountNumberTextField.setText("");
                }
            }
        });

        dialog.add(subscriberTypeLabel);
        dialog.add(subscriberTypeComboBox);

        dialog.add(subscriberNameLabel);
        dialog.add(subscriberNameTextField);
        dialog.add(subscriberAddressLabel);
        dialog.add(subscriberAddressTextField);

        dialog.add(bankCodeLabel);
        dialog.add(bankCodeTextField);
        dialog.add(bankNameLabel);
        dialog.add(bankNameTextField);
        dialog.add(accountNumberLabel);
        dialog.add(accountNumberTextField);

        dialog.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) subscriberTypeComboBox.getSelectedItem();

                if (selectedType == null || selectedType.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Please select a subscriber type.");
                    return;
                }

                String subscriberName = subscriberNameTextField.getText();
                String subscriberAddress = subscriberAddressTextField.getText();

                Subscriber newSubscriber;

                if ("Individual".equals(selectedType)) {
                    newSubscriber = new Individual(subscriberName, subscriberAddress);
                } else if ("Corporation".equals(selectedType)) {
                    int bankCode = Integer.parseInt(bankCodeTextField.getText());
                    String bankName = bankNameTextField.getText();
                    int accountNumber = Integer.parseInt(accountNumberTextField.getText());

                    newSubscriber = new Corporation(subscriberName, subscriberAddress, bankCode, bankName, 0, 0, 0, accountNumber);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Invalid subscriber type.");
                    return;
                }

                if (distributor.addSubscriber(newSubscriber)) {
                    JOptionPane.showMessageDialog(parentFrame, "Subscriber added successfully.");
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Subscriber with the same name already exists.");
                }

                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
    
    private void makePaymentDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Make Payment", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel subscriberNameLabel = new JLabel("Subscriber Name:");
        JTextField subscriberNameTextField = new JTextField();

        JLabel journalISSNLabel = new JLabel("Journal ISSN:");
        JTextField journalISSTextField = new JTextField();

        JLabel amountLabel = new JLabel("Payment Amount:");
        JTextField amountTextField = new JTextField();

        JButton makePaymentButton = new JButton("Make Payment");

        dialog.add(subscriberNameLabel);
        dialog.add(subscriberNameTextField);
        dialog.add(journalISSNLabel);
        dialog.add(journalISSTextField);
        dialog.add(amountLabel);
        dialog.add(amountTextField);
        dialog.add(makePaymentButton);

        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subscriberName = subscriberNameTextField.getText();
                String journalISSN = journalISSTextField.getText();
                double amount = Double.parseDouble(amountTextField.getText());

                Subscriber subscriber = distributor.searchSubscriber(subscriberName);
                Journal journal = distributor.searchJournal(journalISSN);

                if (subscriber != null && journal != null) {
                    Subscription subscription = findSubscription(subscriber, journal);
                    if (subscription != null) {
                        if (subscription.makePayment(amount)) {
                            JOptionPane.showMessageDialog(parentFrame, "Payment successful.");
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(parentFrame, "Invalid payment amount.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Subscription not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Subscriber or Journal not found.");
                }
            }
        });

        dialog.setVisible(true);
    }
    
    private void saveStateDialog(JFrame parentFrame) {
        distributor.saveState("distributor_state.ser");
        JOptionPane.showMessageDialog(parentFrame, "State saved successfully.");
    }

    private void loadStateDialog(JFrame parentFrame) {
        distributor.loadState("distributor_state.ser");
        JOptionPane.showMessageDialog(parentFrame, "State loaded successfully.");
    }

    private void generateReportDialog(JFrame parentFrame, JTextArea outputTextArea) {
        int startYear =2023;
        int endYear =2024;

        new Thread(() -> {
            distributor.report(outputTextArea, startYear, endYear);
            JOptionPane.showMessageDialog(null, "Report generation completed.");
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Distributor distributor = new Distributor();
                new DistributorGUI(distributor);
            }
        });
    }
}