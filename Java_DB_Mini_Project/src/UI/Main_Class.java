package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import DAO.Csb806Dao;
import DAO.Jih0316_DAO;
import DAO.Jih0316_DAO.Employee;
import DAO.Kjy1122DAO;
import DAO.LSH0708_Project_DAO;
import DAO.Project_DAO;
import DTO.Csb806Dto;
import DTO.EMP_DEPT_DTO;
import DTO.EMP_DTO;

public class Main_Class extends JFrame {
	
	LSH0708_Project_DAO lsh_dao=new LSH0708_Project_DAO();
	Project_DAO dao = new Project_DAO();
	Kjy1122DAO kjy_dao = new Kjy1122DAO();
	Jih0316_DAO jih_dao = new Jih0316_DAO();
	Csb806Dao csb_dao = new Csb806Dao();
	
	private JTextArea resultArea;
	private JButton kjy1122Button;
	private JButton jihButton;
	private JButton kjy0227Button;
	private JButton lshButton;
	private JButton csbButton;
	private JButton addBoardButton;
	private JButton searchBoardButton;
	private JButton deleteUserButton;
	private JButton salTopNButton;

    public Main_Class() {
        setTitle("Database Control Panel");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // UI 컴포넌트 초기화
        JPanel buttonPanel = new JPanel(new FlowLayout());
        kjy1122Button = new JButton("강준영");
        jihButton = new JButton("조이한");
        kjy0227Button = new JButton("김진영");
        lshButton = new JButton("이상현");
        csbButton = new JButton("조수빈");
        
        addBoardButton = new JButton("게시판 추가");
        searchBoardButton = new JButton("게시판 조회");
        deleteUserButton = new JButton("유저 삭제");
        salTopNButton = new JButton("급여 TOP N");

        buttonPanel.add(kjy1122Button);
        buttonPanel.add(jihButton);
        buttonPanel.add(kjy0227Button);
        buttonPanel.add(lshButton);
        buttonPanel.add(csbButton);
        
        buttonPanel.add(addBoardButton);
        buttonPanel.add(searchBoardButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(salTopNButton);
        add(buttonPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

		// Button actions
        kjy1122Button.addActionListener(e -> KJY1122());
        jihButton.addActionListener(e -> fetchAndDisplayEmployees());
        kjy0227Button.addActionListener(e -> KJY0227());
        lshButton.addActionListener(e -> LSH0708());
        csbButton.addActionListener(e -> showDeptStatistics());

		setVisible(true);
	}
	
	//김진영
	public void KJY0227() {
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

	//이상현
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
	
	//강준영
	public void KJY1122() {
		List<String[]> list = kjy_dao.getEmpDataByDept();
		 
		resultArea.setText("");
		for(String[] dto : list) {
			String result = "EMPNO:" + dto[0] + ", ENAME:"+ dto[1] + ", DEPTNO:" + dto[7] + ", SAL:" + dto[5];
			resultArea.append(result+"\n"); 
		}
		
		resultArea.revalidate();
		resultArea.repaint();
	}

	//조이한
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
    
    //조수빈
    private void showDeptStatistics() {
        resultArea.setText("그룹함수 예제:\n");
        List<Csb806Dto> deptStatsList = csb_dao.selectDeptStatistics();
        
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
