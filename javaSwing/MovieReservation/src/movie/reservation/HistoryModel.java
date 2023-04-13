package movie.reservation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import movie.domain.MovieMember;
import movie.domain.Product;
import movie.domain.History;

public class HistoryModel extends AbstractTableModel {
	List<History> historyList = new ArrayList<History> (); // 사이즈 0
	String[] reservate_col = {"예매번호","제목","상영 날짜","상영 시간","좌석"};

	public int getRowCount() {
		return historyList.size();
	}

	public int getColumnCount() {
		return reservate_col.length;
	}

	public String getColumnName(int col) {
		return reservate_col[col];
	}

	
	public Object getValueAt(int row, int col) {
		History res = historyList.get(row);
		
		Product product = res.getProduct();
		MovieMember movieMember=res.getMovieMember();
		
		String value=null;
		switch(col) {
		//member_idx별로 예매한 product의 소유 컬럼들을 뽑아내야함 > movie.title, dateTable.date, TimeTable.time, seatTable.seat 을 가져와야해~~~~
		case 0: value =  Integer.toString(res.getHistory_idx()); break;
		case 1: value = product.getMovie().getTitle(); break;
		case 2: value = product.getDate().getRunningDate(); break;
		case 3: value = product.getTime().getRunningTime(); break;
		case 4: value = product.getSeat().getSeatRow() + product.getSeat().getSeatCol(); break;
		}
		
		return value;
	}

}
