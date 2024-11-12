package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Kjy1122DAO {
//    String driver = "oracle.jdbc.driver.OracleDriver"; 
//    String url = "jdbc:oracle:thin:@localhost:1521:xe";
//    String userid = "system";
//    String passwd = "1234";
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "scott";
	String passwd = "tiger";

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
}
