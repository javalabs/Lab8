package org.yegorov.lab8;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class About extends JDialog {

	private static final long serialVersionUID = 4266290955705298383L;
	private final JPanel contentPanel = new JPanel();

	public About(ActionUser owner) {
		super(owner, true);
		setResizable(false);
		setBounds(100, 100, 539, 335);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setToolTipText("About how does it work )");
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		{
			ImageIcon image = new ImageIcon("iconProj.png");
			JLabel lblNewLabel = new JLabel(image);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblYegorovArtem = new JLabel("<html>Development Yegorov Artem <br>"
					+ "\r\nPowered by Java <br>\r\nFor noncommercial use\r\n</html>\r\n");
			getContentPane().add(lblYegorovArtem, BorderLayout.CENTER);
		}
		{
			JLabel lblDonntu = new JLabel("DonNTU 2014");
			lblDonntu.setHorizontalAlignment(SwingConstants.CENTER);
			getContentPane().add(lblDonntu, BorderLayout.SOUTH);
		}
	}
}