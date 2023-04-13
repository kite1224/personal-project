package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import movie.domain.MovieMember;
import util.DBManager;


//무비멤버의 CRUD만을 담당할 DAO 설정한다
public class MovieMemberDAO {
	DBManager dbManager = DBManager.getInstance(); 
	
		public int insert(MovieMember movieMember) {
			Connection con = null;
			PreparedStatement pstmt =null;
			
			con =  dbManager.getConnection();
			
			String sql="insert into moviemember(moviemember_idx,id,pass,name)";
			sql += " values(seq_moviemember.nextval,?,?,?)";
			int result = 0;
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,movieMember.getId());
				pstmt.setString(2,new String(movieMember.getPass()));
				pstmt.setString(3,movieMember.getName());
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
	
	
		public MovieMember select(MovieMember movieMember) {
			Connection con =null;
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			MovieMember obj = null;
		
			con = dbManager.getConnection();
			String sql = "select  * from moviemember where id=? and pass=?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, movieMember.getId());
				pstmt.setString(2, new String(movieMember.getPass()));
				rs = pstmt.executeQuery();
				
				//한 건을 가져오는 것이라 while 없음> 한 건으로 넣는다
				if(rs.next()) {
					obj = new MovieMember(); //빈 dto
					obj.setMoviemember_idx(rs.getInt("movieMember_idx"));
					obj.setId(rs.getString("id"));
					obj.setPass(rs.getString("pass"));
					obj.setName(rs.getString("name"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(pstmt, rs);
			}
			return obj;
		}
		
		//중복체크
		 public boolean idCheck(String id) {
		        Connection con = null;
		        PreparedStatement pstmt = null;
		        ResultSet rs = null;

		        con = dbManager.getConnection();

		        String sql = "select id from moviemember where id=?";
		        boolean result = false;
		        try {
		            pstmt = con.prepareStatement(sql);
		            pstmt.setString(1, id);
		            rs = pstmt.executeQuery();
		            result = rs.next();

		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            dbManager.release(pstmt, rs);
		        }
		        return result;
		    }
}
