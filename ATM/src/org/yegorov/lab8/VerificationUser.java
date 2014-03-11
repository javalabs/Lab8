package org.yegorov.lab8;

import java.math.BigInteger;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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

public class VerificationUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public VerificationUser(ActionUser actionUser, boolean modal) {
		super(actionUser, modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
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
					okButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							//hide();
							setVisible(false);
							
						}
					});
					okButton.setActionCommand("OK");
					getRootPane().setDefaultButton(okButton);
				}
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);	
					}
				});
			}
		}
		setTitle("Enter Password!");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{getContentPane(), contentPanel, passwordField, okButton, cancelButton}));
	}

	public String getHashPassword() {
		//JOptionPane.showMessageDialog(this,
        //        "Success! You typed the right password.");
		String password = "null";
		
		char[] salt = new char[]{'d','o','n','n','t','u','c','s','t',')','*'};
		char[] p = passwordField.getPassword();
		char[] pass = new char[salt.length+p.length];
		System.arraycopy(salt, 0, pass, 0, salt.length);
		System.arraycopy(p, 0, pass, salt.length, p.length);
		if(p.length!=0) {
			MessageDigest cript = null;
			try {
				cript = MessageDigest.getInstance("SHA-1");
				cript.reset();
				cript.update(Charset.forName("UTF-8").encode(CharBuffer.wrap(pass)).array());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			password = new BigInteger(1, cript.digest()).toString(16);
			JOptionPane.showMessageDialog(this,
				password);
		}
		return password;
	}

}
