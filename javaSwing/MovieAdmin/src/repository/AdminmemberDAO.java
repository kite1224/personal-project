package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Adminmember;
import movie.domain.Movie;
import util.DBManager;

//관리자의 CRUD 담당 > 로그인 체크
public class AdminmemberDAO {

	DBManager dbManager = DBManager.getInstance();

	public Adminmember select(Adminmember adminmember) {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		Adminmember obj = null;
	
		con = dbManager.getConnection();
		String sql = "select  * from adminmember where id=? and pass=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, adminmember.getId());
			pstmt.setString(2, new String(adminmember.getPass()));
			rs = pstmt.executeQuery();
			
			//한 건을 가져오는 것이라 while 없음> 한 건으로 넣는다
			if(rs.next()) {
				obj = new Adminmember(); //빈 dto
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
	
}
