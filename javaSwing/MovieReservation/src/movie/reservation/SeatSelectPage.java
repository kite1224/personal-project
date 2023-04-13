package movie.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import movie.domain.History;
import movie.domain.Product;
import movie.domain.SeatTable;
import repository.HistoryDAO;
import repository.ProductDAO;
import repository.SeatTableDAO;

//좌석 선택 패이지
public class SeatSelectPage extends Page {
	JLabel la_select;// 좌석선택 라벨
	JPanel p_center; // 센터에 붙을 패널
	JPanel p_seat; // 센터에 붙을 패널
	JLabel la_screen;
	JButton bt_prev;
	JButton bt_reservate;
	String seatrow;

	SeatTable selecSeat;// 선택좌석

	SeatTable seatTable;
	SeatTableDAO seatTableDAO = new SeatTableDAO();
	ArrayList<ArrayList> seatList; // 좌석에 idx추가
	SeatDesign[][] seatDesign = new SeatDesign[7][5];

	ProductDAO productDAO =  new ProductDAO();
	Product product; //1건의 product를 담을 dto
	
	History history; //예매내역 들어갈 dto
	HistoryDAO historyDAO;
	ArrayList<History> historyList;
	
	ReservatePage reservatePage;
	ArrayList<SeatTable> rowList;
	
	boolean colorFlag=true;
	int currval; 
	
	public SeatSelectPage(ReservationMain reservationMain) {
		super(reservationMain);
		reservatePage = (ReservatePage)reservationMain.page[ReservationMain.RESERVATEPAGE];
		historyDAO= new HistoryDAO();

		la_select = new JLabel("seat selectsion");
		p_center = new JPanel();
		p_seat = new JPanel();
		la_screen = new JLabel("screen");
		bt_prev = new JButton("prev");
		bt_reservate = new JButton("Reservation");

		// 디자인
		la_select.setPreferredSize(new Dimension(700, 60));
		la_select.setFont(new Font("Times New Roman", Font.BOLD, 35));
		la_select.setHorizontalAlignment(SwingConstants.CENTER);

		la_screen.setPreferredSize(new Dimension(400, 60));
		la_screen.setFont(new Font("Times New Roman", Font.BOLD, 25));
		la_screen.setForeground(Color.white);
		la_screen.setBackground(new Color(164,164,201));
		la_screen.setOpaque(true);
		la_screen.setHorizontalAlignment(SwingConstants.CENTER);
		
		Font bt_font = new Font("Times New Roman",Font.BOLD,15);
		bt_prev.setFont(bt_font);
		bt_reservate.setFont(bt_font);

		p_center.setPreferredSize(new Dimension(700, 430));

		Border border = new LineBorder(Color.DARK_GRAY);
		la_screen.setBorder(border);
		p_seat.setBorder(border);
		p_seat.setLayout(new GridLayout(5, 7));
		p_seat.setPreferredSize(new Dimension(640, 200));

		// 부착
		p_center.add(la_screen);
		p_center.add(p_seat);

		p_center.add(bt_prev);
		p_center.add(bt_reservate);

		add(la_select, BorderLayout.NORTH);
		add(p_center);

		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservationMain.showHide(reservationMain.RESERVATEPAGE);
			}
		});

		// 예매 버튼 연결
		bt_reservate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String confirmSeat = selecSeat.getSeatRow() + selecSeat.getSeatCol();
				if (JOptionPane.showConfirmDialog(reservationMain, confirmSeat + "좌석을 예매하겠습니까?", "좌석 예매",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					insertReservate();
					if(JOptionPane.showConfirmDialog(reservationMain, "예매 내역을 확인하시겠습니까?", "예매 완료", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						LoginComplete l_com = (LoginComplete)reservationMain.page[ReservationMain.LOGINCOMPLETE];
						l_com.getHistory();
						reservationMain.showHide(ReservationMain.LOGINCOMPLETE);
					}else {
						reservationMain.showHide(ReservationMain.MAINPAGE);
					}
				}
			}
		});

		getSeat();
		createSeat();
		getList();
	}

	//좌석 생성
	public void createSeat() {
		for (int i = 0; i < seatList.size(); i++) {
			rowList = seatList.get(i);
			for (int a = 0; a < rowList.size(); a++) {
				SeatTable seatTable = rowList.get(a); // 하나의 dto
				SeatDesign seatDesign = new SeatDesign(seatTable, 20, this);
				p_seat.add(seatDesign);
			}
		}

	}

	// 선택한 좌석 번호를 받아와보자
	public void getSeat() {
		seatList = (ArrayList) seatTableDAO.selectAll();
	}
	
	//등록된 상품을 구매하자  > 선택한 조합으로 이루어진 procut_idx를 구해오는 것
	public Product getProduct() {
		product=reservatePage.deliverMovie();
		product.setSeat(selecSeat);
		System.out.println(product.getMovie().getMovie_idx()+"m_idx");
		System.out.println(product.getDate().getDate_idx()+"d_idx");
		System.out.println(product.getTime().getTime_idx()+"t_idx");
		System.out.println(product.getSeat().getSeat_idx()+"s_idx");
		
		Product getProduct = productDAO.getP_idx(product);
		int p_idx = getProduct.getProduct_idx();
		System.out.println(p_idx+"p_idx");
		
		return getProduct;
	}
	
	
	
	//예매내역 테이블에 한 건 추가하자(reservate_list)
	public void insertReservate() {
		history = new History();
		//1) moviemember idx받아오기 
		LoginPage loginPage = (LoginPage)reservationMain.page[ReservationMain.LOGINPAGE];
		history.setMovieMember(loginPage.movieMember);
		System.out.println(loginPage.movieMember.getMoviemember_idx()+" member_idx");
		
		//2. product_idx 가져오기
		Product getProduct = getProduct();
		history.setProduct(getProduct);
		
		int result = historyDAO.insert(history);
		if(result>0) {
			System.out.println("history에 1건 추가 완료");
			
		}
	}

	public void getList() {
		historyList =(ArrayList) historyDAO.selectAll();
	}
	
}
