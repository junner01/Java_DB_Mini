package UI;

import DAO.Jih0316_DAO;
import DAO.Jih0316_DAO.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main_Class extends JFrame {
    private JTextArea resultArea;
    private JButton insertButton;
    private JButton selectButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    public Main_Class() {
        setTitle("Database Control Panel");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // UI 컴포넌트 초기화
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

        // 버튼 기능 할당
        insertButton.addActionListener(e -> {});
        selectButton.addActionListener(e -> fetchAndDisplayEmployees());
        updateButton.addActionListener(e -> {});
        deleteButton.addActionListener(e -> {});
        clearButton.addActionListener(e -> {});

        setVisible(true);
    }

    // 직원 데이터를 조회하고 결과를 출력하는 메서드
    private void fetchAndDisplayEmployees() {
        resultArea.setText(""); // 이전 결과 초기화
        Jih0316_DAO dao = new Jih0316_DAO();

        // 직원 데이터를 조회하여 텍스트 영역에 출력
        List<Employee> employees = dao.selectEmployees(); // 예외 처리는 DAO에서 처리됨
        for (Employee emp : employees) {
            resultArea.append(emp.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main_Class::new);
    }
}
