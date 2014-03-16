package org.yegorov.lab8;

import java.math.BigInteger;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Форма для ввода пароля
 */

public class VerificationUser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	// Поле для ввода пароля
	private JPasswordField passwordField;
	
	// Кнопка для продолжения
	private JButton okButton;
	
	// Кнопка для отмены
	private JButton cancelButton;

	// Конструктор с 2 параметрами (владелец окна, модальность окна)
	public VerificationUser(ActionUser actionUser, boolean isModal) {
		super(actionUser, isModal);
		
		// Обработка события при открытии окна
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				// Наведение фокуса на поле с паролем
				passwordField.requestFocus();
			}
		});
		
		setResizable(false);
		setBounds(100, 100, 338, 128);
		
		contentPanel.setBounds(0, 0, 332, 99);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		{
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Tahoma", Font.PLAIN, 24));
			passwordField.setBounds(24, 11, 284, 43);
			passwordField.setColumns(30);
			contentPanel.add(passwordField);
		}
		getContentPane().setLayout(null);
		{
			{
				getContentPane().add(contentPanel);
				cancelButton = new JButton("Cancel");
				cancelButton.setBounds(164, 65, 76, 23);
				contentPanel.add(cancelButton);
				cancelButton.setActionCommand("Cancel");
				{
					okButton = new JButton("OK");
					okButton.setBounds(99, 65, 55, 23);
					contentPanel.add(okButton);
					
					// Обработка события, если была нажата кнопка продолжения
					okButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							//hide();
							// Скрываем форму ввода пароля
							setVisible(false);
							
						}
					});
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
				}
				
				// Обработка события, если была нажата кнопка отмены
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//setVisible(false);	
						System.exit(0);
					}
				});
			}
		}
		setTitle(R.Enter_password);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{getContentPane(), contentPanel, passwordField, okButton, cancelButton}));
	}
	
	public void cleanPasswordField() {
		passwordField.setText("");
		passwordField.requestFocus();
	}
	
	public String getHashPassword() {
		//JOptionPane.showMessageDialog(this,
        //        "Success! You typed the right password.");
		
		// Хеш-строка пароля введенного пользователем
		String password = null;
		
		// Соль для удленения строки пароля
		char[] salt = new char[]{'d','o','n','n','t','u','c','s','t',')','*'};	
		
		// Чистый пароль, введенный пользователем через поле
		char[] p = passwordField.getPassword();
		
		// Общий массив для хранения удлененного пароля
		char[] pass = new char[salt.length + p.length];
		
		// Копирование в массив pass символов массива salt
		System.arraycopy(salt, 0, pass, 0, salt.length);
		
		// Добавление к массиву pass символов массива p
		System.arraycopy(p, 0, pass, salt.length, p.length);
		
		/* Если длина чистого пароля не равна 0
		*  то генерируем хеш пароля
		*  иначе возвращаем null
		*/
		if(p.length != 0) {
			MessageDigest cript = null;
			try {
				cript = MessageDigest.getInstance("SHA-1");
				cript.reset();
				cript.update(Charset.forName("UTF-8").encode(CharBuffer.wrap(pass)).array());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			password = new BigInteger(1, cript.digest()).toString(16);
			
			//посмотреть хешь 
			//JOptionPane.showMessageDialog(this, password);
		}
		
		return password;
	}
}
