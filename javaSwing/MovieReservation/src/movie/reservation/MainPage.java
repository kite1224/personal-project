package movie.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MainPage extends Page implements ActionListener{

	JLabel la_title;
	SlidePanel slidePanel;
	JButton bt_join;
	JButton bt_login;
	JButton bt_now;


	public MainPage(ReservationMain reservationMain) {
		super(reservationMain);
		la_title = new JLabel("Harry Potter Theater");

		slidePanel = new SlidePanel(reservationMain);

		bt_join = new JButton("JOIN");
		bt_login = new JButton("LOGIN");
		bt_now = new JButton("Now In Theater");
		

		la_title.setPreferredSize(new Dimension(700, 180));
		la_title.setFont(new Font("Times New Roman", Font.BOLD, 50));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		Font bt_font = new Font("Times New Roman",Font.BOLD,15);
		bt_join.setFont(bt_font);
		bt_login.setFont(bt_font);
		bt_now.setFont(bt_font);
		
		add(la_title);
		add(slidePanel, BorderLayout.CENTER);

		add(bt_join);
		add(bt_login);
		add(bt_now);
		
		//this.setBackground(new Color(164,163,201));
		
		slideImage();
		
		bt_join.addActionListener(this);
		bt_login.addActionListener(this);
		bt_now.addActionListener(this);
		
		
	}
	public void slideImage() {
		slidePanel.createImage();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_join) {
			reservationMain.showHide(reservationMain.JOINPAGE);
		}else if(obj == bt_login) {
			reservationMain.showHide(reservationMain.LOGINPAGE);
		}else if(obj == bt_now) {
			reservationMain.showHide(reservationMain.NOWINPAGE);
		}
	}
}
