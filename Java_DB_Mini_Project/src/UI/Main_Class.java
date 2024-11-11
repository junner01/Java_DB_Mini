package UI;

import javax.swing.*;

import DAO.Project_DAO;
import DTO.EMP_DEPT_DTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main_Class extends JFrame {
	private JTextArea resultArea;
	private JButton insertButton;
	private JButton selectButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JButton clearButton;
	
	Project_DAO dao = new Project_DAO();

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

		// Button actions
		insertButton.addActionListener(e -> {});
		selectButton.addActionListener(e -> {});
		updateButton.addActionListener(e -> clickUpdBtn());
		deleteButton.addActionListener(e -> {});
		clearButton.addActionListener(e -> {});

		setVisible(true);
	}
	
	//김진영
	public void clickUpdBtn() {
		resultArea.setText("");
		
		List<EMP_DEPT_DTO> empDeptListKjy = dao.getEmpDeptListKjy();
		for (EMP_DEPT_DTO empDept : empDeptListKjy) {
			String result = "EmpNo: " + empDept.getEmpno() + 
                    ", EmpName: " + empDept.getEname() + 
                    ", DeptNo: " + empDept.getDeptno() + 
                    ", DeptName: " + empDept.getDname() + 
                    ", Location: " + empDept.getLoc() + 
                    ", Commission: " + empDept.getComm() + "\n";

			resultArea.append(result);
	    }
		
		resultArea.revalidate();
		resultArea.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main_Class::new);
	}
}