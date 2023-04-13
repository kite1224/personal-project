package movie.reservation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import movie.domain.MovieMember;
import repository.MovieMemberDAO;

public class LoginPage extends Page implements ActionListener{
	JLabel la_login;
	JLabel la_id;
	JLabel la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_prev;
	JButton bt_logok;
	MovieMemberDAO movieMemberDAO;
	MovieMember movieMember;

	public LoginPage(ReservationMain reservationMain) {
		super(reservationMain);
		
		movieMemberDAO = new MovieMemberDAO();
		
		la_login = new JLabel("Login");
		la_id = new JLabel("ID");
		t_id = new JTextField();
		la_pass= new JLabel("PassWord");
		t_pass= new JPasswordField();
		bt_prev = new JButton("prev");
		bt_logok = new JButton("Login");
		
		//디자인
		la_login.setPreferredSize(new Dimension(500,200));
		la_login.setFont(new Font("Times New Roman", Font.BOLD, 45));
		la_login.setHorizontalAlignment(SwingConstants.CENTER);
		
		Font bt_font = new Font("Times New Roman",Font.BOLD,15);
		bt_prev.setFont(bt_font);
		bt_logok.setFont(bt_font);
		
		Dimension la=new Dimension(200,40);
		la_id.setPreferredSize(la);
		la_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
		la_pass.setPreferredSize(la);
		la_pass.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Dimension d = new Dimension(400, 30);
        t_id.setPreferredSize(d);
        t_pass.setPreferredSize(d);
        Dimension bt = new Dimension(100,30);
        bt_prev.setPreferredSize(bt);
        bt_logok.setPreferredSize(bt);
      
        
        //부착
        add(la_login);
        add(la_id);
        add(t_id);
        add(la_pass);
        add(t_pass);
        add(bt_prev);
        add(bt_logok);
     
       
        bt_prev.addActionListener(this);
        bt_logok.addActionListener(this);
       
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_prev) {
			reservationMain.showHide(reservationMain.MAINPAGE);
		}else if(obj == bt_logok) {
			loginCheck();
		}
	}
	
	public void loginCheck() {
		MovieMember movieMember_login = new MovieMember(); //빈 dto 선언
		movieMember_login.setId(t_id.getText());
		movieMember_login.setPass(new String(t_pass.getPassword()));
		movieMember = movieMemberDAO.select(movieMember_login);
		movieMember.getMoviemember_idx();
		
		String strId = t_id.getText();
		String strPass = new String(t_pass.getPassword());
		
		
			if(movieMember==null) {
				JOptionPane.showMessageDialog(this, "Login Fail");
			}else {
				JOptionPane.showMessageDialog(this, "Login Complete");
				reservationMain.showHide(reservationMain.LOGINCOMPLETE);
				t_id.setText("");
				t_pass.setText("");
				
				LoginComplete l_com = (LoginComplete)reservationMain.page[ReservationMain.LOGINCOMPLETE];
				l_com.getHistory();
				
			}
		
	}
}
