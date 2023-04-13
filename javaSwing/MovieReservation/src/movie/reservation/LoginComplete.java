package movie.reservation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import movie.domain.History;
import repository.HistoryDAO;

public class LoginComplete extends Page {
	JLabel la_complete;

	// 서쪽
	JTable table;
	JScrollPane scroll;

	// 동쪽
	JPanel p_east;
	JLabel la_detail;
	JTextField t_title;
	JTextField t_date;
	JTextField t_time;
	JTextField t_seat;

	JButton bt_reservate;
	JButton bt_del;
	JButton bt_prev;

	HistoryModel model;
	HistoryDAO historyDAO;

	LoginPage loginPage;
	int h_m_idx;
	History detail;
	History history;
	

	public LoginComplete(ReservationMain reservationMain) {
		super(reservationMain);
		historyDAO = new HistoryDAO();
		
		la_complete = new JLabel("Login Complete");

		table = new JTable(model = new HistoryModel());
		scroll = new JScrollPane(table);

		p_east = new JPanel();
		la_detail = new JLabel("Reservate List");
		t_title = new JTextField("Title");
		t_date = new JTextField("Date");
		t_time = new JTextField("Time");
		t_seat = new JTextField("Seat");

		bt_reservate = new JButton("Reservation");
		bt_del = new JButton("cancellation");
		bt_prev = new JButton("Main");

		// 디자인
		la_complete.setPreferredSize(new Dimension(700, 60));
		la_complete.setFont(new Font("", Font.BOLD, 30));
		la_complete.setHorizontalAlignment(SwingConstants.CENTER);
	
		la_detail.setFont(new Font("Times New Roman", Font.BOLD, 15));
		t_title.setFont(new Font("Dotum", Font.BOLD, 10));
		t_date.setFont(new Font("Times New Roman", Font.BOLD, 15));
		t_time.setFont(new Font("Times New Roman", Font.BOLD, 15));
		t_seat.setFont(new Font("Times New Roman", Font.BOLD, 15));

		Font bt_font = new Font("Times New Roman", Font.BOLD, 15);
		bt_reservate.setFont(bt_font);
		bt_del.setFont(bt_font);
		bt_prev.setFont(bt_font);

		p_east.setPreferredSize(new Dimension(180, 400));
		Dimension d = new Dimension(150, 20);
		la_detail.setHorizontalAlignment(SwingConstants.CENTER);
		la_detail.setPreferredSize(d);
		t_title.setPreferredSize(d);
		t_date.setPreferredSize(d);
		t_time.setPreferredSize(d);
		t_seat.setPreferredSize(d);

		scroll.setPreferredSize(new Dimension(480, 380));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);

		// 패널에 부착
		p_east.add(la_detail);
		p_east.add(t_title);
		p_east.add(t_date);
		p_east.add(t_time);
		p_east.add(t_seat);
		p_east.add(bt_reservate);
		p_east.add(bt_del);
		p_east.add(bt_prev);

		
		
		add(la_complete, BorderLayout.NORTH);
		add(scroll, BorderLayout.WEST);
		add(p_east, BorderLayout.EAST);

		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationMain.showHide(reservationMain.MAINPAGE);
			}
		});
		bt_reservate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
					reservationMain.showHide(reservationMain.RESERVATEPAGE);
				
			}
		});
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				getDetail();
			}
		});
	
	}

	// 멤버별로 예약내역을 가져올 메서드
	public void getHistory() {
		history = new History();
		loginPage = (LoginPage) reservationMain.page[ReservationMain.LOGINPAGE];
		history.setMovieMember(loginPage.movieMember);
		int h_m_idx = history.getMovieMember().getMoviemember_idx();
		// System.out.println(h_m_idx + "getHistory로 쓸거임");

		la_complete.setText(history.getMovieMember().getName()+"'s Reservate List");
		
		model.historyList = historyDAO.selectAll(h_m_idx);
		table.updateUI();
		
	}

	// 예매내역 상세보기
	public void getDetail() {
		// String value = (String) table.getValueAt(table.getSelectedRow(), 0);
		// 모델이 보유한 리스트에서
		detail = model.historyList.get(table.getSelectedRow());

		// System.out.println(value);
		// detail = historyDAO.select(Integer.parseInt(value));
		// System.out.println(detail.getHistory_idx());
		t_title.setText(detail.getProduct().getMovie().getTitle());
		t_date.setText(detail.getProduct().getDate().getRunningDate());
		t_time.setText(detail.getProduct().getTime().getRunningTime());
		t_seat.setText(detail.getProduct().getSeat().getSeatRow() + detail.getProduct().getSeat().getSeatCol());

	}

	//예매내역 취소하기	
	public void delete() {
		if (JOptionPane.showConfirmDialog(this, "예매를 취소하시겠습니까?", "예매 취소",
				JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			int result = historyDAO.delete(detail.getHistory_idx());

			if (result > 0) {
				JOptionPane.showMessageDialog(this, "예매가 취소되었습니다");
				getHistory();
			}

		}
	}

}
