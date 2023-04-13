package movie.reservation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import movie.domain.MovieMember;
import repository.MovieMemberDAO;

public class JoinPage extends Page {
	JLabel la_join;
	JLabel la_id;
	JLabel la_pass;
	JLabel la_name;
	JTextField t_id;
	JPasswordField t_pass;
	JTextField t_name;
	JButton bt_prev;
	JButton bt_join;
	
	MovieMemberDAO movieMemberDAO;
	boolean idFlag;

	public JoinPage(ReservationMain reservationMain) {
		super(reservationMain);
		movieMemberDAO = new MovieMemberDAO();
		// 생성
		la_join = new JLabel("Join");
		la_id = new JLabel("ID");
		t_id = new JTextField();
		la_pass = new JLabel("PassWord");
		t_pass = new JPasswordField();
		la_name = new JLabel("Name");
		t_name = new JTextField();
		bt_prev = new JButton("prev");
		bt_join = new JButton("join");

		// 디자인
		la_join.setPreferredSize(new Dimension(500, 200));
		la_join.setFont(new Font("Times New Roman", Font.BOLD, 45));
		la_join.setHorizontalAlignment(SwingConstants.CENTER);
		

		Dimension la = new Dimension(200, 40);
		la_id.setPreferredSize(la);
		la_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
		la_pass.setPreferredSize(la);
		la_pass.setFont(new Font("Times New Roman", Font.BOLD, 20));
		la_name.setPreferredSize(la);
		la_name.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Dimension d = new Dimension(400, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);
		Dimension bt = new Dimension(100, 30);
		bt_prev.setPreferredSize(bt);
		bt_prev.setFont(new Font("Times New Roman",Font.BOLD,15));
		bt_join.setPreferredSize(bt);
		bt_join.setFont(new Font("Times New Roman",Font.BOLD,15));

		// 부착
		add(la_join);
		add(la_id);
		add(t_id);
		add(la_pass);
		add(t_pass);
		add(la_name);
		add(t_name);
		add(bt_prev);
		add(bt_join);

		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationMain.showHide(reservationMain.MAINPAGE);
			}
		});

		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTest();
			}
		});

	}

	//회원가입 메서드
	public void regist() {
		MovieMember movieMember = new MovieMember(); // 새 dto
		String str1 = t_id.getText();
		String str2 = new String(t_pass.getPassword());
		String str3 = t_name.getText();

		if (str1.isEmpty() != true && str2.isEmpty() != true && str3.isEmpty() != true) {
			JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
			movieMember.setId(str1);
			movieMember.setPass(str2);
			movieMember.setName(str3);

			int result = movieMemberDAO.insert(movieMember);
			if (result > 0) {
				JOptionPane.showMessageDialog(this, "Join Complete");
				t_id.setText("");
				t_pass.setText("");
				t_name.setText("");
				reservationMain.showHide(reservationMain.LOGINPAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "모든 사항을 입력해주세요");

		}
	}

	//아이디 중복검사
	public void idTest() {
		String id = t_id.getText();

		boolean result = movieMemberDAO.idCheck(id);
		if (result) {
			JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
		} else {
			regist();
			idFlag = true;
		}

	}
}
