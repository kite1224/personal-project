package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.DateTable;
import movie.domain.Movie;
import movie.domain.Product;
import movie.domain.SeatTable;
import movie.domain.TimeTable;
import util.DBManager;

//Product의 crud 담당 >본 테이블의 idx 제외하고 외래키 4개 있음
public class ProductDAO {
	DBManager dbManager = DBManager.getInstance();

	// 예매완료처리 될 때 product에 한 건 추가 > 안씀


	// select All
	public List selectAll() {
		ArrayList<Product> list = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();

		String sql = "select * from product order by product_idx asc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setProduct_idx(rs.getInt("product_idx"));
				Movie movie = new Movie();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				DateTable date = new DateTable();
				date.setDate_idx(rs.getInt("date_idx"));
				TimeTable time = new TimeTable();
				time.setTime_idx(rs.getInt("time_idx"));
				SeatTable seat = new SeatTable();
				seat.setSeat_idx(rs.getInt("seat_idx"));

				// dto간 엮기
				product.setMovie(movie);
				product.setDate(date);
				product.setTime(time);
				product.setSeat(seat);

				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// select
	public Product select(int product_idx) {
		Product product = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		String sql = "select * from product where product_idx=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setProduct_idx(rs.getInt("product_idx"));
				Movie movie = new Movie();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				DateTable date = new DateTable();
				date.setDate_idx(rs.getInt("date_idx"));
				TimeTable time = new TimeTable();
				time.setTime_idx(rs.getInt("time_idx"));
				SeatTable seat = new SeatTable();
				seat.setSeat_idx(rs.getInt("seat_idx"));

				// dto간 엮기
				product.setMovie(movie);
				product.setDate(date);
				product.setTime(time);
				product.setSeat(seat);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return product;
	}
	
	public Product getP_idx(Product p) {
		Product product = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		con = dbManager.getConnection();
		
		String sql="select product_idx from product where movie_idx=? and date_idx=? and time_idx=? and seat_idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, p.getMovie().getMovie_idx());
			pstmt.setInt(2, p.getDate().getDate_idx());
			pstmt.setInt(3, p.getTime().getTime_idx());
			pstmt.setInt(4, p.getSeat().getSeat_idx());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = new Product();
				product.setProduct_idx(rs.getInt("product_idx"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return product;
	}
}
