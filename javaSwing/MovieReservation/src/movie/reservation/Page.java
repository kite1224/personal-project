package movie.reservation;

import java.awt.Dimension;

import javax.swing.JPanel;

//각 페이지(횐가입, 로그인, 상영중)이 담아야할 기능 정의
public class Page extends JPanel{
	ReservationMain reservationMain;
	
	public Page(ReservationMain reservationMain) {
			this.reservationMain = reservationMain;
			
			this.setPreferredSize(new Dimension(700,500));
	}
}
