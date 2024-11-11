package DAO;

import DTO.Csb806Dto;
import DTO.EMP_DTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Csb806Dao {

    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String userid = "scott";
    String passwd = "tiger";
    
    public Csb806Dao() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 부서별 통계 조회 메서드
    public List<Csb806Dto> selectDeptStatistics() {
        List<Csb806Dto> deptStatsList = new ArrayList<>();
        String query = "SELECT deptno AS 부서번호, COUNT(*) AS 사원수, MAX(sal) AS 최고급여, " +
                       "MIN(sal) AS 최소급여, SUM(sal) AS 급여합계, ROUND(AVG(sal)) AS 평균급여 " +
                       "FROM emp GROUP BY deptno ORDER BY 급여합계 DESC";
        
        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            // 결과가 있을 경우 DTO로 변환하여 리스트에 추가
            while (rs.next()) {
                int deptno = rs.getInt("부서번호");
                int empCount = rs.getInt("사원수");
                int maxSal = rs.getInt("최고급여");
                int minSal = rs.getInt("최소급여");
                int totalSal = rs.getInt("급여합계");
                int avgSal = rs.getInt("평균급여");

                // EMP_DTO 객체 생성 후 리스트에 추가
                Csb806Dto empStat = new Csb806Dto(deptno, empCount, maxSal, minSal, totalSal, avgSal);
                deptStatsList.add(empStat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deptStatsList;
    }
}
