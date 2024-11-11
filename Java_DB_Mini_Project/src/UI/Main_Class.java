package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import DAO.LSH0708_Project_DAO;
import DTO.EMP_DTO;

public class Main_Class extends JFrame {
	
	LSH0708_Project_DAO lsh_dao=new LSH0708_Project_DAO();
	
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
		updateButton.addActionListener(e -> {});
		deleteButton.addActionListener(e -> LSH0708());
		clearButton.addActionListener(e -> {});

		setVisible(true);
	}

	public void LSH0708() {
		 ArrayList<EMP_DTO> list = lsh_dao.select();
		 
		 resultArea.setText("실행하고자 하는 문장 : SELECT empno, ename , deptno , sal  FROM emp WHERE deptno = ( SELECT deptno FROM emp WHERE ename = 'SCOTT') AND sal > ( SELECT sal FROM emp WHERE ename = 'SMITH')\n");
		 for(EMP_DTO dto:list) {
			
			 String result ="EMPNO:"+ dto.getEmpno()+", ENAME:"+ dto.getEname()+", DEPTNO:"+dto.getDeptno()+", SAL:"+dto.getSal();
			 resultArea.append(result+"\n"); 
			 
		 }
		 resultArea.revalidate();
		 resultArea.repaint();
		 
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main_Class::new);
	}
}