package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.EMP_DEPT_DTO;

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
	
//	//예시, DTO 바꿔야함
//	// 부서, 검색 조건에 따라 검색하기
//		public ArrayList<Project_DAO> select(String dept, ArrayList<String> selectedItem) {
//			ArrayList<Lsh0708_project_DTO> list = new ArrayList<Lsh0708_project_DTO>();
//			ArrayList<String> nullRemoveList = new ArrayList<String>();
//			for (String item : selectedItem) {
//				if (item != null) {
//					nullRemoveList.add(item);
//				}
//			}
//			// DB 검색을 위한 조건 나열
//			StringBuilder stringBuilder = new StringBuilder();
//			for (int i = 0; i < nullRemoveList.size(); i++) {
//				stringBuilder.append(nullRemoveList.get(i));
//				// 마지막 요소가 아니면 ", " 추가
//				if (i < nullRemoveList.size() - 1) {
//					stringBuilder.append(", ");
//				}
//			}
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			String query;
//			try {
//				con = DriverManager.getConnection(url, userid, passwd);
//				// 오름차순으로 검색하기
//				if (dept.equals("전체")) {
//					query = "SELECT " + stringBuilder + " FROM employee order by id ASC";
//				} else {
//					query = "SELECT " + stringBuilder + " FROM employee WHERE department=\'" + dept + "\' order by id ASC";
//				}
//				pstmt = con.prepareStatement(query);
//
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					Lsh0708_project_DTO dto = new Lsh0708_project_DTO();
//					for (String column : nullRemoveList) {
//						switch (column) {
//						case "id":
//							dto.setId(rs.getInt("id"));
//							break;
//						case "name":
//							dto.setName(rs.getString("name"));
//							break;
//						case "department":
//							dto.setDepartment(rs.getString("department"));
//							break;
//						case "birthdate":
//							dto.setBirthdate(rs.getString("birthdate"));
//							break;
//						case "address":
//							dto.setAddress(rs.getString("address"));
//							break;
//						case "telNum":
//							dto.setTelNum(rs.getString("telNum"));
//							break;
//						case "sex":
//							dto.setSex(rs.getString("sex"));
//							break;
//						default:
//							break;
//						}
//					}
//					list.add(dto);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (rs != null)
//						rs.close();
//					if (pstmt != null)
//						pstmt.close();
//					if (con != null)
//						con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			return list;
//		};
	
	
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
		
	//이상현
	
	//조이한
	
	//강준영
	
	//조수빈
	
}
