package org.yegorov.lab8;

public class Account {
	private String surname;
	private String name;
	private String patronymic;
	private double balance;
	private String verificationCode;
	
	public Account(	String surname, 
					String name, 
					String patronymic,
					double balance,
					String verificationCode) {
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.balance = balance;
		this.verificationCode = verificationCode;
	}
	
	public String getUserName() {
		return surname+" "+name+" "+patronymic;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}
}
