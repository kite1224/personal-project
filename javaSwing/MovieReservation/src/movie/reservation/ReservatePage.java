package movie.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import movie.domain.DateTable;
import movie.domain.Movie;
import movie.domain.Product;
import movie.domain.TimeTable;
import repository.DateTableDAO;
import repository.TimeTableDAO;

//영화 및 시간 선택하는 예매 페이지
public class ReservatePage extends Page {
	// 위쪽
	JLabel la_reservate;
	
	//상세페이지
	Movie_detail movie_detail;

	// 센터
	JTable table;
	JScrollPane scroll;

	// 동쪽
	JPanel p_east;
	JPanel p_combo;
	JLabel la_select;
	JComboBox combo_date;
	JComboBox combo_time;
	JButton bt_reservate;
	JButton bt_prev;

	MovieModel model;
	DateTableDAO dateTableDAO;
	TimeTableDAO timeTableDAO;

	Image image;
	
	//유저가 선택한 영화 정보들을 담을 변수=상품
	Movie selectedMovie;
	DateTable selectedDate;
	TimeTable selectedTime;

	List<DateTable> dateList;
	List<TimeTable> timeList;

	public ReservatePage(ReservationMain reservationMain) {
		super(reservationMain);
		dateTableDAO = new DateTableDAO();
		timeTableDAO  = new TimeTableDAO();
		movie_detail = new Movie_detail(this);

		la_reservate = new JLabel("Reservation");
		
		table = new JTable(model = new MovieModel());
		scroll = new JScrollPane(table);
		bt_prev = new JButton("Prev");

		p_east = new JPanel();
		la_select = new JLabel("Time select");
		p_combo=new JPanel();
		combo_date = new JComboBox();
		combo_time = new JComboBox();
		bt_reservate = new JButton("Seat Select");
		bt_prev = new JButton("Prev");

		// 디자인
		la_reservate.setPreferredSize(new Dimension(700, 60));
		la_reservate.setFont(new Font("Times New Roman", Font.BOLD, 35));
		la_reservate.setHorizontalAlignment(SwingConstants.CENTER);
		la_select.setHorizontalAlignment(SwingConstants.CENTER);

		Font bt_font = new Font("Times New Roman",Font.BOLD,15);
		bt_reservate.setFont(bt_font);
		bt_prev.setFont(bt_font);
		
		Dimension d = new Dimension(118, 20);

		scroll.setPreferredSize(new Dimension(500, 400));

		p_east.setPreferredSize(new Dimension(160, 400));
		Border border = BorderFactory.createLineBorder(Color.black);
		p_east.setBorder(border);
		p_combo.setBorder(border);
		p_combo.setPreferredSize(new Dimension(150, 100));
		
		la_select.setPreferredSize(d);
		la_select.setFont(new Font("Times New Roman", Font.BOLD, 15));
		combo_date.setPreferredSize(d);
		combo_time.setPreferredSize(d);
		scroll.setBorder(border);

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);

		p_combo.add(la_select);
		p_combo.add(combo_date);
		p_combo.add(combo_time);
		p_east.add(p_combo);
		p_east.add(bt_reservate);
		p_east.add(bt_prev);

		add(la_reservate, BorderLayout.NORTH);
		add(movie_detail,  BorderLayout.CENTER);
		add(scroll, BorderLayout.CENTER);
		add(p_east, BorderLayout.EAST);

		getMovieList();
		createDate();
		createTime();
		
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationMain.showHide(reservationMain.LOGINCOMPLETE);
			}
		});

		// 마우스 리스너 > 상세보기 사용
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				selectedMovie = model.movieList.get(row);//유저가 선택한 영화
				//getDetail();
				/*
				 * 사용자가 마음에 드는 영화를 선택하면 , 좌석을  예악하는 화면으로 가기전에 만반의 분비를 해야 됨 
				 * 1) 어떤 영화를 선탟햇는지 Movie DTO
				 * 2) 어떤 날짜의 Date DTO 
				 * 3) 어떤 시간대를 선택햇는지 Time DTO
				 *    이 두가지를 알ㅇ야 하는  이유? 에약 history에 이미 존재하는 상품인지 비교할려구  
				 * */
						
				movie_detail.getDetail();
			}
		});
		
		combo_date.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 int index=combo_date.getSelectedIndex();
				 selectedDate = dateList.get(index-1);//선택한 날짜정보
				
			}
		});
		
		combo_time.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 int index=combo_time.getSelectedIndex();
				 selectedTime = timeList.get(index-1);//선택한 시간정보
				
			}
		});
		
		bt_reservate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showConfirm();
			}
		});
	}
	//날짜 콤보박스에 추가하기
	public void createDate() {
		dateList = dateTableDAO.selectAll();
		
		combo_date.addItem("날짜 선택 ▼");
		for(DateTable date : dateList) {
			combo_date.addItem(date.getRunningDate());
		}
	}
	public void createTime() {
		timeList = timeTableDAO.selectAll();
		combo_time.addItem("시간 선택 ▼");
		for(TimeTable time : timeList) {
			combo_time.addItem(time.getRunningTime());
		}
	
		
	}

	public void getMovieList() {
		model.movieList = reservationMain.movieDAO.selectAll();
		table.updateUI();
	}
	
	//예매 컨펌받기
	public void showConfirm() {
		String value = (String) table.getValueAt(table.getSelectedRow(), 0);
		Movie movie = reservationMain.movieDAO.select(Integer.parseInt(value));
		String movieTitle=movie.getTitle();
		
		System.out.println(movieTitle);
		
		String date = (String)combo_date.getSelectedItem();
		System.out.println(date);
		
		String time = (String)combo_time.getSelectedItem();
		System.out.println(time);
		
		if(	JOptionPane.showConfirmDialog(this, "선택 영화 : "+ movieTitle+"\n"+"선택 날짜 : "+date+"\n"+"선택 시간 : " +time + " 에 예매하시겠습니까?", "영화선택",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
			reservationMain.showHide(reservationMain.SEATSELECTEPAGE);
		}
	
		//System.out.println(deliverMovie());
	}
	
	//선택한 dto를 seat 테이블ㄴ에 전달하기
	public Product deliverMovie() {
		Product product = new Product();

		String m_value = (String) table.getValueAt(table.getSelectedRow(), 0);
		Movie movie = reservationMain.movieDAO.select(Integer.parseInt(m_value));
		
		int d_value = combo_date.getSelectedIndex();
		DateTable dateTable = dateTableDAO.select(d_value);
		
		int t_value = combo_time.getSelectedIndex();
		TimeTable timeTable = timeTableDAO.select(t_value);
		
		int m_idx = movie.getMovie_idx();
		int d_idx = dateTable.getDate_idx();
		int t_idx = timeTable.getTime_idx();
		
		product.setMovie(movie);
		product.setDate(dateTable);
		product.setTime(timeTable);
		
		return product;
	}
}
