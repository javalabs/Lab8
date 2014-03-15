package org.yegorov.lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import com.thoughtworks.xstream.XStream;

/*
 * Класс для управления аккаунтом (выполнения операций над ним)
 */
public class Core {
	private Map<String, Double> curse;

	private Account account;
	private File file;
	private PrintWriter writer;
	private FileReader reader;
	private XStream xmlStream;
	
	public Core() {
		curse = new HashMap<String,Double>();
		curse.put(R.CURRENCY_USD, R.CURSE_USD);
		curse.put(R.CURRENCY_EUR, R.CURSE_EUR);
		curse.put(R.CURRENCY_RUB, R.CURSE_RUB);
		curse.put(R.CURRENCY_UAH, R.CURSE_UAH);
		
		account = new Account();
		
		file = new File(R.ACCOUNT_FILE_PATH);
		
		xmlStream = new XStream();
		xmlStream.alias(R.ALIAS_XML, Account.class);
		
	}
	
	public void initAccount() throws FileNotFoundException, IOException {
		read();
	}
	
	public boolean verifyAccount(String code) {
		return code.equals(account.getVerificationCode());
	}
	
	public String getBalance() {
		return String.valueOf(account.getBalance());
	}

	public String getBalance(String currency) {
		return String.format("%.2f",account.getBalance()/curse.get(currency));
	}
	
	public String getUserName() {
		return account.getUserName();
	}
	
	public String takeMoney(String currency, double money) {
		if(account.getBalance() - money * curse.get(currency) < 0)
			return R.ERROR_money_tight;
		
		double given = money * curse.get(currency);
		double balance = account.getBalance() - given;
		account.setBalance(balance);
		//По идее тут нужно записывать в файл все изменения
		return R.Given + ": " + String.format("%.2f", given)   + " " + R.CURRENCY_UAH + " \n"
			+R.Balance + ": " + String.format("%.2f", balance) + " " + R.CURRENCY_UAH;
	}
	public void saveAccount() throws FileNotFoundException {
		write();
	}
	
	private void read() throws IOException, FileNotFoundException {
		if(!file.exists())
			throw new FileNotFoundException();
		
		reader = new FileReader(file);
		deserialize(reader);			
		reader.close();
	}
	
	private void deserialize(Reader r) {
		account = (Account)xmlStream.fromXML(r);
	}
	
	private void write() throws FileNotFoundException {
		if(!file.exists())
			throw new FileNotFoundException();
		
		writer = new PrintWriter(file);
		serialize(writer);
		writer.close();
	}
	
	private void serialize(Writer w) {
		xmlStream.toXML(account, w);
	}
}