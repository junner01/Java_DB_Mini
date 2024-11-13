package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.BOARD_DTO;
import DTO.DEPT_DTO;
import DTO.EMP_DEPT_DTO;
import DTO.EMP_DTO;

public class Project_DAO {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "scott";
	String passwd = "tiger";

	public Project_DAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//김진영
	public List<EMP_DEPT_DTO> getEmpDeptListKjy() {
		List<EMP_DEPT_DTO> result = new ArrayList<EMP_DEPT_DTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT e.empno, e.ename, e.deptno, d.dname, d.loc, e.comm "
					+ "FROM emp e "
					+ "LEFT OUTER JOIN dept d ON e.deptno = d.deptno";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				EMP_DEPT_DTO dto = new EMP_DEPT_DTO(
						rs.getInt("empno"),
						rs.getString("ename"),
						null,
						0,
						null,
						0,
						rs.getInt("comm"),
						rs.getInt("deptno"),
						rs.getString("dname"),
						rs.getString("loc")
					);
				
				result.add(dto);
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
		
		return result;
	}
	
	//게시판 조회
	public Object[][] getBoardList() {
		List<BOARD_DTO> result = new ArrayList<>();
	    Object[][] rowData = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
	        con = DriverManager.getConnection(url, userid, passwd);
	        String query = "SELECT * FROM BOARD ORDER BY regdate DESC";
	        pstmt = con.prepareStatement(query);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            BOARD_DTO dto = new BOARD_DTO(
	                    rs.getInt("boardNo"),
	                    rs.getString("title"),
	                    rs.getString("content"),
	                    rs.getString("writer"),
	                    rs.getInt("empno"),
	                    rs.getDate("regdate").toLocalDate()
	            );
	            result.add(dto);
	        }
	        
	        // List에서 Object[][]로 변환
	        rowData = new Object[result.size()][6];
	        for (int i = 0; i < result.size(); i++) {
	            BOARD_DTO dto = result.get(i);
	            rowData[i][0] = dto.getBoardNo();
	            rowData[i][1] = dto.getTitle();
	            rowData[i][2] = dto.getContent();
	            rowData[i][3] = dto.getWriter();
	            rowData[i][4] = dto.getEmpno();
	            rowData[i][5] = dto.getRegdate();
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
	
	//회원가입
	public int registerEmp(EMP_DTO newEmp) {
		int result = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String sql = "INSERT INTO EMP VALUES(?,?,?,null,sysdate,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,newEmp.getEmpno());
			pstmt.setString(2,newEmp.getEname());
			pstmt.setString(3,newEmp.getJob());
			pstmt.setInt(4,newEmp.getSal());
			pstmt.setInt(5,newEmp.getDeptno());
			result = pstmt.executeUpdate();
			System.out.println(result + "개의 레코드가 저장");
			
		} catch (Exception e) {
			result = 0;
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//부서목록
	public List<String> getDeptList() {
		List<String> result = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT deptno FROM DEPT";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				result.add(rs.getString("deptno"));
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
		
		return result;
	}
	
	//회원정보 가져오기
	public EMP_DTO getEmpByEmpNo(int empno) {
		EMP_DTO result = new EMP_DTO();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String query = "SELECT * FROM EMP WHERE empno = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				result.setEmpno(rs.getInt("empno"));
				result.setEname(rs.getString("ename"));
				result.setJob(rs.getString("job"));
				result.setMgr(rs.getInt("mgr"));
				result.setSal(rs.getInt("sal"));
				result.setComm(rs.getInt("comm"));
				result.setDeptno(rs.getInt("deptno"));
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
		
		return result;
	}
	
	//회원수정
	public void modifyEmp(EMP_DTO empDto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			String sql = "UPDATE EMP SET ename = ?, job = ?, mgr = ?, sal = ?, comm = ?, deptno = ? WHERE empno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,empDto.getEname());
			pstmt.setString(2,empDto.getJob());
			pstmt.setInt(3,empDto.getMgr());
			pstmt.setInt(4,empDto.getSal());
			pstmt.setInt(5,empDto.getComm());
			pstmt.setInt(6,empDto.getDeptno());
			pstmt.setInt(7,empDto.getEmpno());
			pstmt.executeUpdate();
			
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
	}
}
