package movie.reservation;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class DesignPage extends JFrame {
	// 북쪽 영역
	JPanel p_north;
	// JButton bt_logo; // 정우투어
	JLabel l_logo; // 정우투어
	JButton bt_login; // 로그인 버튼

	// 센터 영역
	JPanel p_center;
	JLabel l_hi;
	JTextField t_dep; // 출발지
	JTextField t_arr; // 도착지
	JTextField t_start; // 가는날
	JTextField t_end; // 오는날
	JTextField t_person; // 인원수
	JButton bt_search; // 검색 버튼
	JPanel p_mainsearch; // 센터 패널 나올 패널
	
	Image image;

	public DesignPage() {
		// 북쪽영역
		p_north = new JPanel();
		l_logo = new JLabel("JW투어");
		bt_login = new JButton("로그인");

		// p_north.setBackground(Color.YELLOW);
		p_north.setPreferredSize(new Dimension(900, 80));
		p_north.setLayout(null);

		l_logo.setSize(90, 40);
		l_logo.setBounds(20, 15, 85, 40);
		l_logo.setFont(new Font("Dotum", Font.BOLD, 20));

		bt_login.setBounds(1100, 15, 75, 40); // setBounds(x, y, w, h)

		p_north.add(l_logo);
		p_north.add(bt_login);

		add(p_north, BorderLayout.NORTH);

		// 센터영역
		 createImage();
		
		p_center = new JPanel() {
		public void paintComponent(Graphics g) {// 그리는 함수
			 Graphics2D g2 = (Graphics2D) g;
			 g2.drawImage(image, 0, 0, null);// background를 그려줌
		}
		};
		
		p_center.setPreferredSize(new Dimension(1000,500));

		p_mainsearch = new JPanel();

		Dimension d = new Dimension(200, 50);
		// Font f=new

		l_hi = new JLabel("이제 여행을 시작하세요");
		l_hi.setFont(new Font("Dotum", Font.BOLD, 40));
		l_hi.setPreferredSize(new Dimension(1200, 100));
		l_hi.setHorizontalAlignment(SwingConstants.CENTER);

		t_dep = new JTextField();
		t_dep.setFont(new Font("Dotum", Font.BOLD, 30));
		t_dep.setPreferredSize(d);

		t_arr = new JTextField();
		t_arr.setFont(new Font("Dotum", Font.BOLD, 30));
		t_arr.setPreferredSize(d);

		t_start = new JTextField();
		t_start.setFont(new Font("Dotum", Font.BOLD, 30));
		t_start.setPreferredSize(d);

		t_end = new JTextField();
		t_end.setFont(new Font("Dotum", Font.BOLD, 30));
		t_end.setPreferredSize(d);

		t_person = new JTextField();
		t_person.setFont(new Font("Dotum", Font.BOLD, 30));
		t_person.setPreferredSize(d);

		bt_search = new JButton("검색");
		
		p_center.add(l_hi);

		p_mainsearch.add(t_dep);
		p_mainsearch.add(t_arr);
		p_mainsearch.add(t_start);
		p_mainsearch.add(t_end);
		p_mainsearch.add(t_person);
		p_mainsearch.add(bt_search);

		p_center.add(p_mainsearch);
		add(p_center);

		setTitle("JWTour");
		setSize(1200, 1000);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void createImage() {
		Class myClass = this.getClass();
		URL url = myClass.getClassLoader().getResource("res2/app/beach2.jpg");
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new DesignPage();
	}
}

