package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.DateTable;
import util.DBManager;

//상영날짜만의 CRUD 담당
public class DateTableDAO {
DBManager dbManager = DBManager.getInstance();
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DateTable> list= new ArrayList<DateTable>();
		
		con = dbManager.getConnection();
		
		String sql = "select * from datetable order by date_idx asc";
		try {
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				DateTable date = new DateTable();//빈 dto
				date.setDate_idx(rs.getInt("date_idx"));
				date.setRunningDate(rs.getString("runningDate"));
				
				list.add(date);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	//한 건 가져오기
	public DateTable select(int date_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DateTable dateTable = null;
		
		con = dbManager.getConnection();
		String sql = "select * from datetable where date_idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, date_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dateTable = new DateTable();//빈 dto
				dateTable.setDate_idx(rs.getInt("date_idx"));
				dateTable.setRunningDate(rs.getString("runningDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return dateTable;
	}
}
