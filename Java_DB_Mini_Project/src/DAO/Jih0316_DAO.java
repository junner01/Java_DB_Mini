package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jih0316_DAO {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERID = "scott";
    private static final String PASSWORD = "tiger";

    public Jih0316_DAO() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class Employee {
        private int empNo;
        private String eName;
        private int deptNo;
        private String dName;

        public Employee(int empNo, String eName, int deptNo, String dName) {
            this.empNo = empNo;
            this.eName = eName;
            this.deptNo = deptNo;
            this.dName = dName;
        }

        @Override
        public String toString() {
            return "Employee [empNo=" + empNo + ", eName=" + eName + ", deptNo=" + deptNo + ", dName=" + dName + "]";
        }
    }

    // Fetch employee data with a JOIN query
    public List<Employee> selectEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        
        // The SQL query to join emp and dept tables and get the required columns
        String sqlQuery = "SELECT e.empno, e.ename, e.deptno, d.dname " +
                          "FROM emp e " +
                          "JOIN dept d ON e.deptno = d.deptno";

        try (Connection conn = DriverManager.getConnection(URL, USERID, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            while (rs.next()) {
                int empNo = rs.getInt("empno");
                String eName = rs.getString("ename");
                int deptNo = rs.getInt("deptno");
                String dName = rs.getString("dname");

                Employee emp = new Employee(empNo, eName, deptNo, dName);
                employeeList.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
