package UI;

import javax.swing.*;

import DAO.Csb806Dao;
import DTO.Csb806Dto;

import java.util.List;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main_Class extends JFrame {
private JTextArea resultArea;
private JButton insertButton;
private JButton selectButton;
private JButton updateButton;
private JButton deleteButton;
private JButton clearButton;
private Csb806Dao dao;
private Connection conn;

public Main_Class() {
setTitle("Database Control Panel");
setSize(600, 400);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLayout(new BorderLayout());

// Initialize UI components
JPanel buttonPanel = new JPanel(new FlowLayout());
insertButton = new JButton("강준영");
selectButton = new JButton("조이한");
updateButton = new JButton("김진영");
deleteButton = new JButton("이상현");
clearButton = new JButton("조수빈");

buttonPanel.add(insertButton);
buttonPanel.add(selectButton);
buttonPanel.add(updateButton);
buttonPanel.add(deleteButton);
buttonPanel.add(clearButton);
add(buttonPanel, BorderLayout.NORTH);

resultArea = new JTextArea(10, 50);
resultArea.setEditable(false);
add(new JScrollPane(resultArea), BorderLayout.CENTER);


dao = new Csb806Dao();
// Database connection setup
setupDatabaseConnection();

// Button actions
insertButton.addActionListener(e -> performInsert());
selectButton.addActionListener(e -> performSelect());
updateButton.addActionListener(e -> performUpdate());
deleteButton.addActionListener(e -> performDelete());
clearButton.addActionListener(e -> showDeptStatistics());

setVisible(true);
}

private void setupDatabaseConnection() {
try {
String driver = "oracle.jdbc.driver.OracleDriver";
String url = "jdbc:oracle:thin:@localhost:1521:xe";
String user = "scott";
String password = "tiger";
Class.forName(driver);
conn = DriverManager.getConnection(url, user, password);
resultArea.append("Connected to the database.\n");
} catch (Exception e) {
resultArea.append("Database connection failed.\n");
e.printStackTrace();
}
}

private void performInsert() {
try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO members (name, email, password) VALUES (?, ?, ?)")) {
pstmt.setString(1, "SampleName");
pstmt.setString(2, "sample@example.com");
pstmt.setString(3, "password123");
int rowsAffected = pstmt.executeUpdate();
resultArea.append("Inserted " + rowsAffected + " row(s) into members table.\n");
} catch (SQLException e) {
resultArea.append("Insert operation failed.\n");
e.printStackTrace();
}
}

private void performSelect() {
try (PreparedStatement pstmt = conn.prepareStatement("SELECT name, email, password FROM members");
ResultSet rs = pstmt.executeQuery()) {
resultArea.append("Select results:\n");
while (rs.next()) {
String name = rs.getString("name");
String email = rs.getString("email");
String password = rs.getString("password");
resultArea.append("Name: " + name + ", Email: " + email + ", Password: " + password + "\n");
}
} catch (SQLException e) {
resultArea.append("Select operation failed.\n");
e.printStackTrace();
}
}

private void performUpdate() {
try (PreparedStatement pstmt = conn.prepareStatement("UPDATE members SET email = ? WHERE name = ?")) {
pstmt.setString(1, "updated@example.com");
pstmt.setString(2, "SampleName");
int rowsAffected = pstmt.executeUpdate();
resultArea.append("Updated " + rowsAffected + " row(s) in members table.\n");
} catch (SQLException e) {
resultArea.append("Update operation failed.\n");
e.printStackTrace();
}
}

private void performDelete() {
try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM members WHERE name = ?")) {
pstmt.setString(1, "SampleName");
int rowsAffected = pstmt.executeUpdate();
resultArea.append("Deleted " + rowsAffected + " row(s) from members table.\n");
} catch (SQLException e) {
resultArea.append("Delete operation failed.\n");
e.printStackTrace();
}
}

private void showDeptStatistics() {
    resultArea.setText("그룹함수 예제:\n");
    List<Csb806Dto> deptStatsList = dao.selectDeptStatistics();
    
    for (Csb806Dto deptStat : deptStatsList) {
        resultArea.append("부서번호: " + deptStat.getDeptno() +
                          ", 사원수: " + deptStat.getEmpCount() +
                          ", 최고 급여: " + deptStat.getMaxSal() +
                          ", 최소 급여: " + deptStat.getMinSal() +
                          ", 급여 합계: " + deptStat.getTotalSal() +
                          ", 평균 급여: " + deptStat.getAvgSal() + "\n");
    }
}


public static void main(String[] args) {
SwingUtilities.invokeLater(Main_Class::new);
}
}