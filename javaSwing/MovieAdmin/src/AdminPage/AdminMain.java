package AdminPage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import domain.Adminmember;
import repository.AdminmemberDAO;
import util.DBManager;

public class AdminMain extends JFrame {
	JPanel container;
	JPanel loginContainer;

	JLabel la_Admin;
	JLabel la_id;
	JLabel la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login;

	RegistPage registPage;

	DBManager dbManager = DBManager.getInstance();
	AdminmemberDAO adminDAO = new AdminmemberDAO();

	public AdminMain() {

		container = new JPanel();
		loginContainer = new JPanel();

		registPage = new RegistPage(this);

		la_Admin = new JLabel("Admin Page");
		la_id = new JLabel("ID");
		t_id = new JTextField();
		la_pass = new JLabel("PassWord");
		t_pass = new JPasswordField();
		bt_login = new JButton("Login");

		la_Admin.setPreferredSize(new Dimension(500, 200));
		la_Admin.setFont(new Font("Times New Roman", Font.BOLD, 45));
		la_Admin.setHorizontalAlignment(SwingConstants.CENTER);

		Font bt_font = new Font("Times New Roman", Font.BOLD, 15);
		bt_login.setFont(bt_font);

		Dimension la = new Dimension(200, 40);
		la_id.setPreferredSize(la);
		la_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
		la_pass.setPreferredSize(la);
		la_pass.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Dimension d = new Dimension(400, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		Dimension bt = new Dimension(100, 30);
		bt_login.setPreferredSize(bt);
		loginContainer.setPreferredSize(new Dimension(700, 450));

		// 부착
		loginContainer.add(la_Admin);
		loginContainer.add(la_id);
		loginContainer.add(t_id);
		loginContainer.add(la_pass);
		loginContainer.add(t_pass);
		loginContainer.add(bt_login);
		container.add(loginContainer);
		container.add(registPage);
		add(container);

		setVisible(true);
		setSize(700, 500);
		setResizable(false);
		setTitle("Admin Page");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});

		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
	}

	public void showRegist() {
		registPage.setVisible(true);
		loginContainer.setVisible(false);

	}

	public void loginCheck() {
		Adminmember adminmember = new Adminmember(); // 빈 dto
		adminmember.setId(t_id.getText());
		adminmember.setPass(new String(t_pass.getPassword()));
		adminmember = adminDAO.select(adminmember);

		if (adminmember == null) {
			JOptionPane.showMessageDialog(this, "Admin Login Fail");
		} else {
			JOptionPane.showMessageDialog(this, "Admin Login Complete");
			t_id.setText("");
			t_pass.setText("");

			showRegist();

		}

	}

	public static void main(String[] args) {
		new AdminMain();
	}
}
