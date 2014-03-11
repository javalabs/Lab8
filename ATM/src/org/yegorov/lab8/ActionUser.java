package org.yegorov.lab8;


import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

import javax.swing.JTextArea;
import javax.swing.DropMode;
/*
 * 
 * 
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
просмотр баланса,
снятие средств, 
печать чека на экран, 
сохранение текущего баланса при выходе из программы.
 * 
 */
public class ActionUser extends JFrame {

	/**
	 * 	
	 */
	final public static String CURRENCY_UAH = "UAH г.";
	final public static String CURRENCY_RUB = "RUB р.";
	final public static String CURRENCY_USD = "USD $ ";
	final public static String CURRENCY_EUR = "EUR € ";
	
	About dialog = null;
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VerificationUser verificationUser;
	private JTextArea textField;
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	private JComboBox<String> comboBox;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel label;//Валюта
	private Panel panel;
	private boolean menubarShowHide = false;
	
	private Core atm;
	
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
				panel.setVisible(false);
				chekVerification();
				panel.setVisible(true);
				
				
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				//TODO Сохранение баланса при выходе
				JOptionPane.showMessageDialog(ActionUser.this, "press red cross");
			}
		});
		
		setFocusable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 572);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.setVisible(menubarShowHide);
		
		JMenu mnNewMenu_2 = new JMenu("Settings");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Font");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeFont();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Reloaded");
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
		
		JMenu mnExit = new JMenu("Exit");
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.OK_OPTION == 
				   JOptionPane.showConfirmDialog(null,
						   	"Are you sure you want to quit?", 
						   	"Exit", JOptionPane.YES_NO_OPTION))
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
		
		JMenuItem mntmShowMenu = new JMenuItem("Show/Hide menu");
		mntmShowMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuHideShow();
			}
		});
		popupMenu.add(mntmShowMenu);
		
		textField = new JTextArea();
		textField.setBounds(179, 336, 390, 175);
		contentPane.add(textField);
		textField.setColumns(10);
		//TEst BUTTON
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				textField.setText((String)comboBox.getSelectedItem());
				if("RUB р.".equals((String)comboBox.getSelectedItem()))
					textField.setText("Вы выбрали рубль!)");
					*/
				textField.setText(atm.Serialization());
				atm.DeSerialization(textField.getText());
				textField.setText(atm.Serialization());
			}
		});
		btnNewButton.setBounds(10, 356, 89, 23);
		contentPane.add(btnNewButton);
		
		panel = new Panel();
		panel.setBounds(81, 74, 531, 256);
		contentPane.add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox.setBounds(10, 45, 95, 27);
		panel.add(comboBox);
		comboBox.addItem(CURRENCY_UAH);
		comboBox.addItem(CURRENCY_RUB);
		comboBox.addItem(CURRENCY_USD);
		comboBox.addItem(CURRENCY_EUR);
		
		label = new JLabel("Валюта:");
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setBounds(10, 10, 95, 29);
		panel.add(label);
		
		btnNewButton_1 = new JButton("Снять средства");
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton_1.setBounds(10, 194, 203, 51);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Просмотр баланса");
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton_2.setBounds(318, 194, 203, 51);
		panel.add(btnNewButton_2);
		
		
		setTitle("ATM");
	}
	private void menuHideShow() {
		if(menubarShowHide)
			menubarShowHide = false;
		else
			menubarShowHide = true;
		
		menuBar.setVisible(menubarShowHide);
	}
	private void changeFont() {
		String font = "";

		font = JOptionPane.showInputDialog("Input Font:");
		if(font.length()==0)
			return;
		textField.setFont(new Font(font, Font.PLAIN, 14));
		Font newFont = new Font(font, Font.PLAIN, 16);
		comboBox.setFont(newFont);
		btnNewButton_1.setFont(newFont);
		btnNewButton_2.setFont(newFont);
		label.setFont(newFont);
		
	}
	private void showAbout() {
		if(dialog == null) //Создаем окно, если оно не создано
			dialog = new About(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	private boolean chekVerification() {
		verificationUser = new VerificationUser(this, true);
		verificationUser.setLocationRelativeTo(null);
		verificationUser.setVisible(true);
		textField.setText(verificationUser.getHashPassword());
		verificationUser.dispose();
		return true;
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
