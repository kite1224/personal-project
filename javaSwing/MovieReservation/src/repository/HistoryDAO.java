package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.DateTable;
import movie.domain.History;
import movie.domain.Movie;
import movie.domain.MovieMember;
import movie.domain.Product;
import movie.domain.History;
import movie.domain.SeatTable;
import movie.domain.TimeTable;
import util.DBManager;

//예매내역 (history)만의 crud담당할 dao
public class HistoryDAO {
	DBManager dbManager = DBManager.getInstance();
	
	//예매할때 추가하자
	public int insert(History history) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into history(history_idx,product_idx,moviemember_idx)";
		sql += " values(seq_history.nextval,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, history.getProduct().getProduct_idx());
			pstmt.setInt(2, history.getMovieMember().getMoviemember_idx());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}

		return result;
	}

	// select All
	public List selectAll(int h_m_idx) {
		ArrayList<History> list = new ArrayList<History>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		StringBuilder sb = new StringBuilder();
		//해당 회원의 예매내역 
		sb.append(" select history_idx,mm.moviemember_idx ,  h.product_idx, title, runningdate, runningtime, seatrow, seatcol");
		sb.append(" from history h, ");
		sb.append(" ( ");
		sb.append(" select p.product_idx AS product_idx, m.title AS title, d.runningdate AS runningdate, ");
		sb.append(" t.runningtime AS runningtime, s.seatrow AS seatrow, s.seatcol as seatcol");
		sb.append(" from product p , movie m, datetable d, timetable t, seattable s");
		sb.append(" where p.movie_idx=m.movie_idx and");
		sb.append(" p.date_idx=d.date_idx and");
		sb.append(" p.time_idx=t.time_idx and");
		sb.append(" p.seat_idx=s.seat_idx");
		sb.append(" ) temp, moviemember mm ");
		sb.append(" where h.product_idx = temp.product_idx and");
		sb.append(" h.moviemember_idx=mm.moviemember_idx and");
		sb.append(" mm.moviemember_idx=? order by history_idx asc");
	
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, h_m_idx);
			rs = pstmt.executeQuery();
			//System.out.println(rs+"rs");
			
			while (rs.next()) {
				// 새 dto를 생성할 때 위의 값을 모두 가진 dto가 없음
				// 테이블 병합으로 새로운 dto 생성? 아니면 history가 다른 dto를 모두 가진다?
				// 그냥.. 필요한 모든 dto를 다 new해주자
				History  history = new History();
				MovieMember movieMember = new MovieMember();
				Product product = new Product();
				Movie movie = new Movie();
				DateTable date = new DateTable();
				TimeTable time = new TimeTable();
				SeatTable seat = new SeatTable();
				//객체간 엮기 
				
				history.setHistory_idx(rs.getInt("history_idx"));
				movieMember.setMoviemember_idx(rs.getInt("moviemember_idx"));
				product.setProduct_idx(rs.getInt("product_idx"));
				movie.setTitle(rs.getString("title"));
				date.setRunningDate(rs.getString("runningDate"));
				time.setRunningTime(rs.getString("runningTime"));
				seat.setSeatRow(rs.getString("seatRow"));
				seat.setSeatCol(rs.getInt("seatCol"));
				
				product.setMovie(movie);
				product.setDate(date);
				product.setTime(time);
				product.setSeat(seat);
				
				history.setProduct(product);
				history.setMovieMember(movieMember);
				
				list.add(history);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// select>한 건 가져오기
	public History select(int history_idx) {
		System.out.println("history_idx is "+history_idx);
		
		History history = null;
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		StringBuilder sb = new StringBuilder();
		
		sb.append(" select history_idx,mm.moviemember_idx ,h.product_idx, title, runningdate, runningtime, seatrow, seatcol");
		sb.append(" from history h, ");
		sb.append(" ( ");
		sb.append(" select p.product_idx AS product_idx, m.title AS title, d.runningdate AS runningdate, ");
		sb.append(" t.runningtime AS runningtime, s.seatrow AS seatrow, s.seatcol as seatcol");
		sb.append(" from product p , movie m, datetable d, timetable t, seattable s");
		sb.append(" where p.movie_idx=m.movie_idx and");
		sb.append(" p.date_idx=d.date_idx and");
		sb.append(" p.time_idx=t.time_idx and");
		sb.append(" p.seat_idx=s.seat_idx");
		sb.append(" ) temp, moviemember mm ");
		sb.append(" where h.product_idx = temp.product_idx and");
		sb.append(" h.moviemember_idx=mm.moviemember_idx and");
		sb.append(" history_idx=? order by history_idx asc");
		
		System.out.println(sb.toString());

		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, history_idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history = new History();
				Product product = new Product();
				Movie movie = new Movie();
				DateTable date = new DateTable();
				TimeTable time = new TimeTable();
				SeatTable seat = new SeatTable();
				//객체간 엮기 
				history.setHistory_idx(rs.getInt("history_idx"));
				product.setProduct_idx(rs.getInt("product_idx"));
				movie.setTitle(rs.getString("title"));
				date.setRunningDate(rs.getString("runningDate"));
				time.setRunningTime(rs.getString("runningTime"));
				seat.setSeatRow(rs.getString("seatRow"));
				seat.setSeatCol(rs.getInt("seatCol"));
				
				product.setMovie(movie);
				product.setDate(date);
				product.setTime(time);
				product.setSeat(seat);
				
				history.setProduct(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return history;
	}

	// delete

	public int delete(int history_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		con = dbManager.getConnection();
		String sql = "delete history where history_idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, history_idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	//모든 예약내역 가져오기
	public List selectAll() {
		ArrayList<History> list = new ArrayList<History>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		StringBuilder sb = new StringBuilder();
		//해당 회원의 예매내역 
		sb.append(" select mm.moviemember_idx ,  h.product_idx, title, runningdate, runningtime, seatrow, seatcol");
		sb.append(" from history h, ");
		sb.append(" ( ");
		sb.append(" select p.product_idx AS product_idx, m.title AS title, d.runningdate AS runningdate, ");
		sb.append(" t.runningtime AS runningtime, s.seatrow AS seatrow, s.seatcol as seatcol");
		sb.append(" from product p , movie m, datetable d, timetable t, seattable s");
		sb.append(" where p.movie_idx=m.movie_idx and");
		sb.append(" p.date_idx=d.date_idx and");
		sb.append(" p.time_idx=t.time_idx and");
		sb.append(" p.seat_idx=s.seat_idx");
		sb.append(" ) temp, moviemember mm ");
		sb.append(" where h.product_idx = temp.product_idx");
		sb.append(" order by history_idx asc");
	
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			//System.out.println(rs+"rs");
			
			while (rs.next()) {
				// 새 dto를 생성할 때 위의 값을 모두 가진 dto가 없음
				// 테이블 병합으로 새로운 dto 생성? 아니면 history가 다른 dto를 모두 가진다?
				// 그냥.. 필요한 모든 dto를 다 new해주자
				History  history = new History();
				MovieMember movieMember = new MovieMember();
				Product product = new Product();
				Movie movie = new Movie();
				DateTable date = new DateTable();
				TimeTable time = new TimeTable();
				SeatTable seat = new SeatTable();
				//객체간 엮기 
				
				movieMember.setMoviemember_idx(rs.getInt("moviemember_idx"));
				product.setProduct_idx(rs.getInt("product_idx"));
				movie.setTitle(rs.getString("title"));
				date.setRunningDate(rs.getString("runningDate"));
				time.setRunningTime(rs.getString("runningTime"));
				seat.setSeatRow(rs.getString("seatRow"));
				seat.setSeatCol(rs.getInt("seatCol"));
				
				product.setMovie(movie);
				product.setDate(date);
				product.setTime(time);
				product.setSeat(seat);
				
				history.setProduct(product);
				history.setMovieMember(movieMember);
				
				list.add(history);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}
	
}
