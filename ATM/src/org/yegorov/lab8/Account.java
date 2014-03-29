package org.yegorov.lab8;

/*
 * Класс, содержащий основную информацию о пользователе
 */

public class Account {
	
	// Фамилия
	private String surname;
	
	// Имя
	private String name;
	
	// Отчество
	private String patronymic;
	
	// Текущий баланс (в UAH)
	private double balance;
	
	// Код идентификации пользователя
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
	
	public Account() {
		
	}
	
	// Возвращает полное имя пользователя (ФИО)
	public String getUserName() {
		return surname+" "+name+" "+patronymic;
	}
	
	// Возвращает текущий баланс
	public double getBalance() {
		return balance;
	}
	
	// Устанавливает текущий баланс
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	// Возвращает код идентификации пользователя
	public String getVerificationCode() {
		return verificationCode;
	}
}