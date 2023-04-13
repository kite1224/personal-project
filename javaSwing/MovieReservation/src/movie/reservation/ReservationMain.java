package movie.reservation;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import repository.MovieDAO;
import repository.HistoryDAO;
import util.DBManager;

public class ReservationMain extends JFrame{
	JPanel container;
	
	Page[] page = new Page[7];
	public static final int MAINPAGE=0;
	public static final int LOGINPAGE=1;
	public static final int JOINPAGE=2;
	public static final int NOWINPAGE=3;
	public static final int LOGINCOMPLETE=4;
	public static final int RESERVATEPAGE=5;
	public static final int SEATSELECTEPAGE=6;
	
	MovieDAO movieDAO = new MovieDAO();
	
	DBManager dbManager = DBManager.getInstance();
	
	public ReservationMain() {
		container = new JPanel();
		
		page[0]=new MainPage(this);
		page[1]=new LoginPage(this);
		page[2]=new JoinPage(this);
		page[3]=new NowInPage(this);
		page[4]=new LoginComplete(this);
		page[5]=new ReservatePage(this);
		page[6]=new SeatSelectPage(this);
		
		for(int i =0; i< page.length; i++) {
			container.add(page[i]);
		}
		
		add(container);
		
		showHide(MAINPAGE);
		
		setSize(700,500);
		setVisible(true);
		setResizable(false);
		setTitle("Harry Poter Theater");
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
		
	}
	
	public void showHide(int n) {
		for(int i=0; i<page.length; i++) {
			if(i==n) {
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ReservationMain();
	}
}
