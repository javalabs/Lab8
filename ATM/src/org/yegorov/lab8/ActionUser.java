package org.yegorov.lab8;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JComboBox;

import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.DropMode;
/*
Задание на лабораторную работу:
1. Разработать ООП модель предметной области.
2. Написать приложение с графическим пользовательским интерфейсом
 (с использованием библиотеки Swing), которое будет содержать:
	- главный фрейм;
	- поток диспетчера событий;
	- минимум четыре компонента графического интерфейса (кнопки, текстовые поля, меню, переключатели, флаги,…);
	- установку шрифта для вывода текста;
	- обработку минимум двух событий.

 * 
3.	Приложение-банкомат. 
Реализовать вход, +
выбор валюты, +
просмотр баланса, +
снятие средств, +
печать чека на экран, +
сохранение текущего баланса при выходе из программы.+
 */

public class ActionUser extends JFrame {
	
	About dialog = null;
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VerificationUser verificationUser;
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	private JComboBox<String> comboBox;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel label;//Валюта
	private JLabel label_1;//приветствие
	private Font font;
	private Panel panel;
	private boolean menubarShowHide = false;
	
	private Core atm;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JButton btnNewButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActionUser frame = new ActionUser();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ActionUser() {
		atm = new Core();
		
		
		
		setResizable(false);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				//textField.setText(arg0.getKeyText(arg0.getKeyCode()));
				if(arg0.getKeyCode()==KeyEvent.VK_F1)
					menuHideShow();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
				try {
					atm.initAccount();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(ActionUser.this, R.Not_fount_file_error);
					System.exit(0);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(ActionUser.this, R.Unknown_error);
					System.exit(0);
					e.printStackTrace();
				}
				
				panel.setVisible(false);
				
				int i = 0, n = 3;
				while(i<3) {
					if(chekVerification()) {
						panel.setVisible(true);
						label_1.setText(R.Wellcome+atm.getUserName());
						break;
					}
				++i;
				}
				if(i==3) {
					JOptionPane.showMessageDialog(ActionUser.this, R.Incorrect_password, R.ATM, JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
					

					
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				//TODO Сохранение баланса при выходе
				
				try {
					atm.saveAccount();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//JOptionPane.showMessageDialog(ActionUser.this, "press red cross");
			}
		});
		
		font = new Font("Arial", Font.PLAIN, 16);
		
		setFocusable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 487);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.setVisible(menubarShowHide);
		
		JMenu mnNewMenu_2 = new JMenu(R.Settings);
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem(R.Font);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeFont();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		mnNewMenu = new JMenu(R.Help);
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem(R.About);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu(R.Reloaded);
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				
				ActionUser frame = new ActionUser();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		menuBar.add(mnNewMenu_1);
		
		JMenu mnExit = new JMenu(R.Exit);
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.OK_OPTION == 
				   JOptionPane.showConfirmDialog(null,
						   	R.Ask_quit, 
						   	R.Exit, JOptionPane.YES_NO_OPTION))
						   	System.exit(0);
			}
		});
		menuBar.add(mnExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setLocation(57, 91);
		addPopup(contentPane, popupMenu);
		
		JMenuItem mntmShowMenu = new JMenuItem(R.Menu_item_show_hide);
		mntmShowMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuHideShow();
			}
		});
		popupMenu.add(mntmShowMenu);
		
		panel = new Panel();
		panel.setBounds(70, 58, 664, 315);
		contentPane.add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(font);
		comboBox.setBounds(10, 45, 95, 27);
		panel.add(comboBox);
		comboBox.addItem(R.CURRENCY_UAH);
		comboBox.addItem(R.CURRENCY_RUB);
		comboBox.addItem(R.CURRENCY_USD);
		comboBox.addItem(R.CURRENCY_EUR);
		
		label = new JLabel(R.Currency+": ");
		label.setFont(font);
		label.setBounds(10, 10, 95, 29);
		panel.add(label);
		
		btnNewButton_1 = new JButton(R.Withdraw_funds);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				takeMoney();
			}
		});
		btnNewButton_1.setFont(font);
		btnNewButton_1.setBounds(10, 245, 238, 59);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton(R.Watch_balance);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				watchBalance();
			}
		});
		btnNewButton_2.setFont(font);
		btnNewButton_2.setBounds(416, 245, 238, 59);
		panel.add(btnNewButton_2);
		
		label_1 = new JLabel("");
		label_1.setFont(font);
		label_1.setBounds(189, 10, 445, 27);
		panel.add(label_1);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setFont(font);
		lblNewLabel.setBounds(141, 83, 490, 59);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(141, 153, 187, 50);
		panel.add(textField);
		textField.setColumns(10);
		textField.setFont(font);
		textField.setVisible(false);
		
		
		btnNewButton = new JButton(R.String_next);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmTakeMoney();
			}
		});
		btnNewButton.setBounds(416, 153, 169, 50);
		panel.add(btnNewButton);
		btnNewButton.setVisible(false);
		btnNewButton.setFont(font);
		
		
		
		
		setTitle(R.ATM);
	}
	private void menuHideShow() {
		if(menubarShowHide)
			menubarShowHide = false;
		else
			menubarShowHide = true;
		
		menuBar.setVisible(menubarShowHide);
	}
	
	private void changeFont() {
		String font = null;

		font = JOptionPane.showInputDialog(R.Input_font+": ");
		if(font.equals("") || font == null)
			return;

		Font newFont = new Font(font, Font.PLAIN, 16);
		comboBox.setFont(newFont);
		btnNewButton_1.setFont(newFont);
		btnNewButton_2.setFont(newFont);
		label.setFont(newFont);
		label_1.setFont(newFont);
		lblNewLabel.setFont(newFont);
		btnNewButton.setFont(newFont);
		textField.setFont(newFont);
		
	}
	private void showAbout() {
		if(dialog == null) //Создаем окно, если оно не создано
			dialog = new About(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private boolean chekVerification() {
		if(verificationUser == null)
			verificationUser = new VerificationUser(this, true);
		verificationUser.setLocationRelativeTo(null);
		verificationUser.setVisible(true);
		
		if(verificationUser.getHashPassword() == null) {
			verificationUser.setTitle(R.Repeat_password);
			return false;
		}
		
		boolean isVerificate = atm.verifyAccount(verificationUser.getHashPassword());
		
		if(!isVerificate) {
			verificationUser.setTitle(R.Repeat_password);
			verificationUser.cleanPasswordField();
		}
		
		return isVerificate;
	}
	
	private void watchBalance() {
		textField.setVisible(false);
		btnNewButton.setVisible(false);
		lblNewLabel.setText(R.String_balance + atm.getBalance((String)comboBox.getSelectedItem()) + " " + comboBox.getSelectedItem());
		lblNewLabel.setVisible(true);	
	}
	
	private void takeMoney() {
		lblNewLabel.setText(R.String_take_money);
		lblNewLabel.setVisible(true);
		textField.setVisible(true);
		btnNewButton.setVisible(true);
		
	}
	
	private void confirmTakeMoney() {
		double money = Double.parseDouble(textField.getText());
		JOptionPane.showMessageDialog(this, atm.takeMoney((String)comboBox.getSelectedItem(), money), R.String_cash, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
