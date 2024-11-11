package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jih0316_DAO {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERID = "scott";
    private static final String PASSWORD = "tiger";

    // Employee 클래스 정의
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

        public void setEmpNo(int empNo) { this.empNo = empNo; }
        public void setEName(String eName) { this.eName = eName; }
        public void setDeptNo(int deptNo) { this.deptNo = deptNo; }
        public void setDName(String dName) { this.dName = dName; }

        @Override
        public String toString() {
            return "Employee [empNo=" + empNo + ", eName=" + eName + ", deptNo=" + deptNo + ", dName=" + dName + "]";
        }
    }

    // 직원 데이터를 조회하여 Employee 객체 목록 반환
    public List<Employee> selectEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        
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
