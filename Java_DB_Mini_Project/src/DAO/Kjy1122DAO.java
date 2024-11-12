package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Kjy1122DAO {
    String driver = "oracle.jdbc.driver.OracleDriver"; 
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String userid = "system";
    String passwd = "1234";
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	String userid = "scott";
//	String passwd = "tiger";
    
    public boolean insertBoard(String title, String content, String author) {
        
        try (Connection conn = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO board (title, content, author) VALUES (?, ?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, author);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getEmpDataByDept() {
        List<String[]> empList = new ArrayList<>();
        String query = "SELECT * FROM emp WHERE deptno IN (20,30)";

        try (Connection con = DriverManager.getConnection(url, userid, passwd);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String[] row = {
                    rs.getString("empno"),
                    rs.getString("ename"),
                    rs.getString("job"),
                    rs.getString("mgr"),
                    rs.getString("hiredate"),
                    rs.getString("sal"),
                    rs.getString("comm"),
                    rs.getString("deptno")
                };
                empList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;
    }
    

    public boolean addBoard(int boardNo, String title, String content, String writer, int empno) {
        String query = "INSERT INTO board (boardNo, title, content, writer, empno, regdate) VALUES (?, ?, ?, ?, ?, SYSDATE)";
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, boardNo);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setString(4, writer);
            pstmt.setInt(5, empno);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        try {
           
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(url, userid, passwd);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결에 실패했습니다.");
            e.printStackTrace();
        }
        return null; 
    }
   

    }

