package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DAO.Jih0316_DAO;
import DAO.Jih0316_DAO.Employee;

public class Main_Class extends JFrame {
    private JPanel resultPanel;
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

        // 결과 패널 생성
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 클릭 이벤트 처리
        insertButton.addActionListener(e -> {});
        
        // "조이한" 버튼 클릭 시 직원 데이터 조회
        selectButton.addActionListener(e -> Jih0316());
        updateButton.addActionListener(e -> {});
        deleteButton.addActionListener(e -> {});
        clearButton.addActionListener(e -> {
            resultPanel.removeAll();
            resultPanel.revalidate();
            resultPanel.repaint();
        });

        setVisible(true);
    }

    // 직원 데이터를 결과 패널에 표시하는 메소드
    private void Jih0316() {
        Jih0316_DAO dao = new Jih0316_DAO();
        
        // 직원 데이터 조회
        try {
            List<Employee> employees = dao.selectEmployees(); // 부서 입력 없이 조회
            resultPanel.removeAll(); // 이전 결과 삭제

            // 직원 정보 레이블로 추가
            for (Employee emp : employees) {
                JLabel employeeLabel = new JLabel(emp.toString());
                resultPanel.add(employeeLabel);
            }
            resultPanel.revalidate();
            resultPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "데이터 조회 중 오류 발생: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main_Class::new);
    }
}
