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
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFormattedTextField;

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


	3.	Приложение-банкомат. 
		Реализовать вход, +
		выбор валюты, +
		просмотр баланса, +
		снятие средств, +
		печать чека на экран, +
		сохранение текущего баланса при выходе из программы. +

 */

public class ActionUser extends JFrame {
	// Формы
	private About dialog;
	private VerificationUser verificationUser;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	private JComboBox<String> comboBox;		// Выбор валюты
	private JButton btnNewButton_1;			// Снять средства
	private JButton btnNewButton_2;			// Просмотр баланса
	private JLabel label;					// Валюта
	private JLabel label_1;					// Приветствие
	private Font font;
	private Panel panel;
	private boolean menubarShowHide = false;
	
	private Core atm;
	private JLabel lblNewLabel;				// Чек
	private JFormattedTextField textField;	// Ввод суммы для снятия
	private JButton btnNewButton;			// Продолжение операции
	
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
		
		// Обработка события при открытии окна
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				panel.setVisible(false);	// Скрываем панель
				
				
				// Пытаемся подгрузить данные с файла
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
				
				int i = 0, n = 3;
				while(i < n) {
					if(chekVerification()) { 		// Если верификация успешана
						panel.setVisible(true);		// Показываем панель и приветствие и выходим из цикла
						label_1.setText(R.Wellcome+atm.getUserName());
						break;
					}
				++i;
				}
				if(i == 3) {
					JOptionPane.showMessageDialog(ActionUser.this, R.Incorrect_password, R.ATM, JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}	
			}
			
			// Обработка события при выходе (Нажатие на красный крестик)
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Пытаемся сохранить баланса при выходе
				try {
					atm.saveAccount();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//JOptionPane.showMessageDialog(ActionUser.this, "press red cross");
			}
		});
		
		font = new Font("Arial", Font.PLAIN, 16);	// Стандартный шрифт
		
		//setFocusable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 487);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.setVisible(menubarShowHide);
		
		JMenu mnNewMenu_2 = new JMenu(R.Settings);
		menuBar.add(mnNewMenu_2);
		
		// Пункт меню смены шрифта и обработка события
		JMenuItem mntmNewMenuItem_1 = new JMenuItem(R.Font);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeFont();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		mnNewMenu = new JMenu(R.Help);
		menuBar.add(mnNewMenu);
		
		// Пункт меню об авторе и обработка события
		JMenuItem mntmNewMenuItem = new JMenuItem(R.About);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		// Пункт меню перезагрузка и обработка события
		JMenu mnNewMenu_1 = new JMenu(R.Reloaded);
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose(); // Освобождаем текущую форму
				//Пытаемся сохранить баланса при выходе
				try {
					atm.saveAccount();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
						   						R.Exit, JOptionPane.YES_NO_OPTION)) {
					//Пытаемся сохранить баланса при выходе
					try {
						atm.saveAccount();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
						   	
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
		
		
		comboBox = new JComboBox<String>(); // Создания списка с выбором валюты
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
		
		// Кнопка снять средства и обработка события
		btnNewButton_1 = new JButton(R.Withdraw_funds);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				takeMoney();
			}
		});
		btnNewButton_1.setFont(font);
		btnNewButton_1.setBounds(10, 245, 238, 59);
		panel.add(btnNewButton_1);
		
		// Кнопка просмотр баланса и обработка события
		btnNewButton_2 = new JButton(R.Watch_balance);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				watchBalance();
			}
		});
		btnNewButton_2.setFont(font);
		btnNewButton_2.setBounds(416, 245, 238, 59);
		panel.add(btnNewButton_2);
		
		// Поле для отображения приветствия
		label_1 = new JLabel("");
		label_1.setFont(font);
		label_1.setBounds(189, 10, 445, 27);
		panel.add(label_1);
		
		// Поле для отображения баланса
		lblNewLabel = new JLabel("");
		lblNewLabel.setFont(font);
		lblNewLabel.setBounds(141, 83, 490, 59);
		lblNewLabel.setVisible(false);
		panel.add(lblNewLabel);
		
		// Текстовое поле для ввода суммы 
		/*
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setAllowsInvalid(false);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		// If you want the value to be committed on each keystroke instead of focus lost
		formatter.setCommitsOnValidEdit(true);
		*/
		textField = new JFormattedTextField();
		textField.setBounds(141, 153, 187, 50);
		panel.add(textField);
		textField.setColumns(10);
		textField.setFont(font);
		textField.setVisible(false);
		
		
		// Кнопка для продолжения операции снятия средств
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
		textField.setText("");
		textField.requestFocus();
		btnNewButton.setVisible(true);
		
	}
	
	private void confirmTakeMoney() {
		String text = textField.getText();
		text = text.replaceAll("\\p{L}", "");
		double money = 0;
		try {
			money = Math.abs(Double.parseDouble(text));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, R.String_error_cash, R.String_error, JOptionPane.ERROR_MESSAGE);
			return;
		}

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
