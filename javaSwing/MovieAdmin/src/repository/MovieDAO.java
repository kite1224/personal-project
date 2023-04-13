package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.Movie;
import util.DBManager;

//영화 관련 작업을 진행
public class MovieDAO {
	
	DBManager dbManager = DBManager.getInstance();
	
	//영화 한 건 추가
		public int insert(Movie movie) {
			int result=0;
			Connection con = null;
			PreparedStatement pstmt=null;
			
			con = dbManager.getConnection();
			String sql = "insert into movie(movie_idx, title, grade, genre, release_year, poster)";
			sql+=" values(seq_movie.nextval,?,?,?,?,?)";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, movie.getTitle());
				pstmt.setString(2, movie.getGrade());
				pstmt.setString(3, movie.getGenre());
				pstmt.setString(4, movie.getRelease_year());
				pstmt.setString(5, movie.getPoster());
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(pstmt);
			}
			return result;
		}
		
		//select All. 기존 영화테이블의 내용을 가져온다
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
		
		//등록된 영화 삭제
		public int delete(int movie_idx) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int result = 0;

			con = dbManager.getConnection();
			String sql = "delete movie where movie_idx=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, movie_idx);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbManager.release(pstmt);
			}
			return result;
		}
		
}
