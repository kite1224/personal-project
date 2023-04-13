package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.Movie;
import util.DBManager;

//무비 테이블의 CRUD 담당
public class MovieDAO {
	DBManager dbManager = DBManager.getInstance();
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Movie> list = new ArrayList<Movie>();

		con = dbManager.getConnection();
		
		String sql = "select * from movie order by movie_idx asc";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//rs 곧 죽으니 list에 담자
			while(rs.next()) {
				Movie moviedto = new Movie();
				moviedto.setMovie_idx(rs.getInt("movie_idx"));
				moviedto.setTitle(rs.getString("title"));
				moviedto.setGenre(rs.getString("genre"));
				moviedto.setGrade(rs.getString("grade"));
				moviedto.setRelease_year(rs.getString("release_year"));
				
				list.add(moviedto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}	
		return list;
	}
	
	//한 건 가져오는 쿼리문
	public Movie select(int movie_idx) {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Movie movie = null;
		
		con = dbManager.getConnection();
		
		String sql = "select * from movie where movie_idx=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			rs  = pstmt.executeQuery(); // 쿼리 실행
			
			//조건에 맞으면
			if(rs.next()) {
				movie = new Movie(); //빈 dto
				movie.setMovie_idx(rs.getInt("movie_idx"));
				movie.setTitle(rs.getString("title"));
				movie.setGenre(rs.getString("genre"));
				movie.setGrade(rs.getString("grade"));
				movie.setRelease_year(rs.getString("release_year"));
				movie.setPoster(rs.getString("poster"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return movie;
	}
	
	
	
	
}
