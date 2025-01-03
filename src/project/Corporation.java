package project;

import java.io.Serializable;

public class Corporation extends Subscriber implements Serializable {

	private static final long serialVersionUID = 1L;
	private int bankCode;
	private String bankName;
	private int issueDay,issueMonth,issueYear;
	private int accountNumber;
	
    public Corporation(String name, String address, int bankCode, String bankName, int issueDay, int issueMonth, int issueYear, int accountNumber) {
        super(name, address);
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.issueDay = issueDay;
        this.issueMonth = issueMonth;
        this.issueYear = issueYear;
        this.accountNumber = accountNumber;
    }
	
    @Override
    public String getBillingInformation() {
        return "Bank: " + bankName + ", Account Number: " + accountNumber;
    }

	public int getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public int getIssueDay() {
		return issueDay;
	}

	public int getIssueMonth() {
		return issueMonth;
	}

	public int getIssueYear() {
		return issueYear;
	}

	public int getAccountNumber() {
		return accountNumber;
	}


}
