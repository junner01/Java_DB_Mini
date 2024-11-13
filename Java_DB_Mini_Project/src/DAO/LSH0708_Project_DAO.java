package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.BOARD_DTO;
import DTO.EMP_DTO;

public class LSH0708_Project_DAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "scott";
	String passwd = "tiger";

	public LSH0708_Project_DAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<EMP_DTO> select() {
		ArrayList<EMP_DTO> list = new ArrayList<EMP_DTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, userid, passwd);

			String query = "SELECT empno, ename , deptno , sal  FROM emp WHERE deptno = ( SELECT deptno FROM emp WHERE ename = 'SCOTT') "
					+ "AND sal > ( SELECT sal FROM emp WHERE ename = 'SMITH')";
			System.out.println(query);
			pstmt = con.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				EMP_DTO dto = new EMP_DTO();
				dto.setEmpno(rs.getInt("empno"));
				dto.setEname(rs.getString("ename"));
				dto.setDeptno(rs.getInt("deptno"));
				dto.setSal(rs.getInt("sal"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	};

	public int userCount() {
		int userNum = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT count(*) from emp";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userNum = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userNum;
	};

	public ArrayList<EMP_DTO> SalTopN(int TopN) {
		ArrayList<EMP_DTO> list = new ArrayList<EMP_DTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int userNum = 0;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * from emp where ROWNUM <=? Order by sal desc";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, TopN);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				EMP_DTO dto = new EMP_DTO();
				dto.setEmpno(rs.getInt("empno"));
				dto.setEname(rs.getString("ename"));
				dto.setJob(rs.getString("job"));
				dto.setMgr(rs.getInt("mgr"));
				dto.setHiredate(rs.getString("hiredate"));
				dto.setSal(rs.getInt("sal"));
				dto.setComm(rs.getInt("comm"));
				dto.setDeptno(rs.getInt("deptno"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	};

	// 게시글 추가
	public int insertBoard(String boardNo, String title, String content, String writer, String empNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result1 = 0;
		int lastId = 0;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			// 마지막 사용자 id 이용하기 위해 내림차순으로 조회

			String sql = "INSERT INTO board(boardno,title,content,writer,empno,regdate)" + "VALUES(?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setString(4, writer);
			pstmt.setString(5, empNo);
			result1 = pstmt.executeUpdate();
			System.out.println(result1 + "개의 레코드가 저장");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return result1;
	} // insert

	public ArrayList<String> searchUserId() {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			// 마지막 사용자 id 이용하기 위해 내림차순으로 조회

			String query = "SELECT empno from emp";

			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String result = rs.getString(1);
				list.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally

		return list;
	}

	public boolean deleteBoard(int delBoardNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try {

			con = DriverManager.getConnection(url, userid, passwd);
			String sql = "DELETE FROM board WHERE boardno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, delBoardNo);
			rs = pstmt.executeQuery();
			System.out.println(sql);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return result;
	} // delete

	// 수정할 게시글 데이터 검색
	public ArrayList<BOARD_DTO> selectedModifyBoard(int boardNo) {
		ArrayList<BOARD_DTO> list = new ArrayList<BOARD_DTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int userNum = 0;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * from board where boardno = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BOARD_DTO dto = new BOARD_DTO();
				dto.setBoardNo(rs.getInt("boardno"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setEmpno(rs.getInt("empno"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 게시글 수정
	public int modifyBoard(String boardNo, String title, String content, String writer, String empNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result1 = 0;
		int lastId = 0;
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			// 마지막 사용자 id 이용하기 위해 내림차순으로 조회

			String sql = "update board set title=?,content=?,writer=?,empno=?,regdate=sysdate where boardno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, empNo);
			pstmt.setString(5, boardNo);
			result1 = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		return result1;
	} // modify
	
	
	//게시판 조회
		public Object[][] getUserList() {
			List<EMP_DTO> result = new ArrayList<>();
		    Object[][] rowData = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
		        con = DriverManager.getConnection(url, userid, passwd);
		        String query = "SELECT * FROM emp";
		        pstmt = con.prepareStatement(query);
		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            EMP_DTO dto = new EMP_DTO(
		                    rs.getInt("empno"),
		                    rs.getString("ename"),
		                    rs.getString("job"),
		                    rs.getInt("mgr"),
		                    rs.getString("hiredate"),
		                    rs.getInt("sal"),
		                    rs.getInt("comm"),
		                    rs.getInt("deptno")
		            );
		            result.add(dto);
		        }
		        
		        // List에서 Object[][]로 변환
		        rowData = new Object[result.size()][8];
		        for (int i = 0; i < result.size(); i++) {
		            EMP_DTO dto = result.get(i);
		            rowData[i][0] = dto.getEmpno();
		            rowData[i][1] = dto.getEname();
		            rowData[i][2] = dto.getJob();
		            rowData[i][3] = dto.getMgr();
		            rowData[i][4] = dto.getHiredate();
		            rowData[i][5] = dto.getSal();
		            rowData[i][6] = dto.getComm();
		            rowData[i][7] = dto.getDeptno();
		            
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (pstmt != null) pstmt.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
			
			return rowData;
		}
	// 김진영

	// 이상현

	// 조이한

	// 강준영

	// 조수빈

}
