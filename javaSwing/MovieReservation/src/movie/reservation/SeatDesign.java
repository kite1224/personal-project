package movie.reservation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import movie.domain.History;
import movie.domain.SeatTable;

public class SeatDesign extends JPanel {
	SeatTable seatTable; //dto를 가진다
	String title; 
	int fontSize;
	Color color=Color.white;
	Color color2 = new Color(164,164,201);
	SeatSelectPage seatSelectPage;
	ReservatePage reservatePage;
	//boolean colorFlag;
	

	public SeatDesign(SeatTable seatTable, int fontSize, SeatSelectPage seatSelectPage) {
		this.seatTable = seatTable;
		this.fontSize = fontSize;
		this.seatSelectPage = seatSelectPage;
		
		
		reservatePage=(ReservatePage)this.seatSelectPage.reservationMain.page[ReservationMain.RESERVATEPAGE];
		/*if(reservatePage.selectedMovie!=null) {
			System.out.println("movie_idx"+reservatePage.selectedMovie.getMovie_idx());
			System.out.println("Date_idx"+reservatePage.selectedDate.getDate_idx());
			System.out.println("Time_idx"+reservatePage.selectedTime.getTime_idx());
		}*/
		
		Border border = new LineBorder(Color.DARK_GRAY);
		setBorder(border);
		
		//System.out.println(seatTable.getSeat_idx());
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e){
				
				if(color==Color.white && seatSelectPage.colorFlag) {
					seatSelectPage.selecSeat = SeatDesign.this.seatTable;
					System.out.println("됨");
					color=color2;
					seatSelectPage.colorFlag=false;					
				
				}else if(color==color2 && seatSelectPage.colorFlag==false){
					color=Color.white;
					System.out.println("안됨");
					seatSelectPage.colorFlag=true;					
				}
				repaint();
			}
		});
	}
	
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0, 0, 130, 120);
		
		g2.setColor(color);
		g2.fillRect(0, 0, 120, 100);
		
		g2.setFont(new Font("Times New Roman",Font.BOLD,fontSize));
		g2.setColor(Color.DARK_GRAY);
		
		//System.out.println("movie_idx"+reservatePage.selectedMovie.getMovie_idx());
		//System.out.println("Date_idx"+reservatePage.selectedDate.getDate_idx());
		//System.out.println("Time_idx"+reservatePage.selectedTime.getTime_idx());
		
	/*	if(reservatePage.selectedMovie!=null) {
			for(int i=0; i<seatSelectPage.historyList.size(); i++)	{
				History history = seatSelectPage.historyList.get(i);
				
				//System.out.println("history'movie_idx "+history.getProduct().getMovie().getMovie_idx()+"history'date"+history.getProduct().getDate().getDate_idx()+"history's time_idx"+history.getProduct().getTime().getTime_idx()+""
						//+ "history's seat"+history.getProduct().getSeat().getSeat_idx());
				
				boolean result1 = history.getProduct().getMovie().getMovie_idx()==reservatePage.selectedMovie.getMovie_idx();
				boolean result2 = history.getProduct().getDate().getDate_idx()==reservatePage.selectedDate.getDate_idx();
				boolean result3 = history.getProduct().getTime().getTime_idx()==reservatePage.selectedTime.getTime_idx();
				boolean result4 = history.getProduct().getSeat().getSeat_idx()==seatTable.getSeat_idx();
				if(result1&&result2&&result3&&result4) {
					g2.setColor(Color.gray);
					g2.fillRect(0, 0, 120, 100);					
				}
				g2.drawString(seatTable.getSeatRow()+seatTable.getSeatCol(), 50, 17);
			}
		}*/
		
		g2.drawString(seatTable.getSeatRow()+seatTable.getSeatCol(), 35, 25);
}

}
