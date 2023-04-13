package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import movie.domain.SeatTable;
import util.DBManager;

//seat Table에 대한 crud만을 담당
public class SeatTableDAO {
	DBManager dbManager = DBManager.getInstance();

	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ArrayList> list = new ArrayList<ArrayList>();

		con = dbManager.getConnection();

		String sql = "select * from seattable order by seat_idx asc";
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();

			int total = rs.getRow();
			int col = 7; // 열수
			int row = (total / col); // 행수
			rs.beforeFirst(); // rs다시 위로 보냄
			for (int a = 0; a < row; a++) {
				ArrayList rowList = new ArrayList();
				for (int i = 0; i < col; i++) {
					rs.next();
					SeatTable seat = new SeatTable();// 빈 dto
					seat.setSeat_idx(rs.getInt("seat_idx"));
					seat.setSeatRow(rs.getString("seatRow"));
					seat.setSeatCol(rs.getInt("seatCol"));

					rowList.add(seat);
				}
				list.add(rowList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 한 건 가져오기
	public SeatTable select(int seat_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SeatTable seatTable = null;

		con = dbManager.getConnection();
		String sql = "select * from seattable where seat_idx=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seat_idx);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seatTable = new SeatTable();
				seatTable.setSeat_idx(rs.getInt("seat_idx"));
				seatTable.setSeatRow(rs.getString("seatRow"));
				seatTable.setSeatCol(rs.getInt("seatCol"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}

		return seatTable;
	}
}
