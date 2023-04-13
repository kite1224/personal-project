package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.DateTable;
import movie.domain.TimeTable;
import util.DBManager;

//상영시간표만의 CRUD담당
public class TimeTableDAO {
	DBManager dbManager = DBManager.getInstance();
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<TimeTable> list= new ArrayList<TimeTable>();
		
		con = dbManager.getConnection();
		
		String sql = "select * from timetable order by time_idx asc";
		try {
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TimeTable time = new TimeTable();//빈 dto
				time.setTime_idx(rs.getInt("time_idx"));
				time.setRunningTime(rs.getString("runningTime"));
				
				list.add(time);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
	//한 건 가져오기
		public TimeTable select(int time_idx) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			TimeTable timeTable = null;
			
			con = dbManager.getConnection();
			String sql = "select * from timetable where time_idx=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, time_idx);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					timeTable = new TimeTable();//빈 dto
					timeTable.setTime_idx(rs.getInt("time_idx"));
					timeTable.setRunningTime(rs.getString("runningTime"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(pstmt, rs);
			}
			return timeTable;
		}
	
	
}
