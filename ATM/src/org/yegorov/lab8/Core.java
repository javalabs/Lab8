package org.yegorov.lab8;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

public class Core {
	private Map<String, Double> curse;

	private Account account;
	private Account testaccount;
	
	public Core() {
		curse = new HashMap<String,Double>();
		curse.put(R.CURRENCY_USD, 9.67);
		curse.put(R.CURRENCY_EUR, 13.47);
		curse.put(R.CURRENCY_RUB, 0.26);
		curse.put(R.CURRENCY_UAH, 1.0);
		testaccount = new Account(	"Цукерберг", 
									"Марк", 
									"Эллиот", 
									5000, 
									"9ab44eaf7fa715f9f84b75954e445d72547e2a22");
	}
	
	public String Serialization() {
		File f = new File("account.xml");
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		PrintWriter pf = null;
		try {
			pf = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	      XStream xstream = new XStream(); // require XPP3 library

	      xstream.alias("Account",  Account.class);
	      
	      String xml = xstream.toXML(testaccount);
	      xstream.toXML(testaccount, pf);
	      pf.close();

	      return xml;
	}
	public void DeSerialization(String xml) {
		
	      XStream xstream = new XStream(); // require XPP3 library

	      xstream.alias(R.ALIAS_XML,  Account.class);
	      testaccount = (Account) xstream.fromXML(xml);
	      
	      testaccount.setBalance(testaccount.getBalance()-10);
	      

	}
	
	public String printCheck(double givenMoney,String currency) {
		return "Выдано: "+String.valueOf(givenMoney)+" "+currency+"\nОстаток: ";
	}
}
