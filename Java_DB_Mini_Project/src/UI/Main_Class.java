package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        searchBoardButton.addActionListener(e -> showBoardList());
        salTopNButton.addActionListener(e -> showSalTopN());

		setVisible(true);
	}
	
	//김진영
	public void KJY0227() {
		resultArea.setText("--- LEFT OUTER JOIN 예시 ---\n");
		
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
	public void showSalTopN() {
		int userNum = lsh_dao.userCount();
		int N = Integer.parseInt(JOptionPane.showInputDialog("Top N을 입력하세요. 최대 사원 수 : "+userNum));
		
		if(userNum<N) {
			JOptionPane.showMessageDialog(null, "최대 사원수 보다 입력을 작게 해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return; // 메서드 종료
		}
		resultArea.setText("");
		ArrayList<EMP_DTO> list = lsh_dao.SalTopN(N);
		int count=1;
		resultArea.append("급여 TOP " + N + "\n");
		for(EMP_DTO dto:list) {
			String result = count+ ". EMPNO:"+ dto.getEmpno()+", ENAME:"+ dto.getEname()+", JOB:"+ dto.getJob()+", "
					+ "MGR:"+dto.getMgr()+", HIREDATE:"+dto.getHiredate()+", SAL:"+dto.getSal()+", "
							+ "COMM:"+dto.getComm()+", DEPTNO:"+dto.getDeptno();
			resultArea.append(result+"\n");
			count++;
		}
		
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
    
    //게시판 조회
    public void showBoardList() {
    	// 데이터 가져오기
        String[] columnNames = {"글번호", "제목", "내용", "작성자명", "작성자번호", "작성일"};
        Object[][] data = dao.getBoardList();

        // JTable
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JDialog dialog = new JDialog();
        dialog.setTitle("게시판 목록");
        dialog.setSize(700, 300);
        dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 상단 버튼 패널
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBoardButton = new JButton("게시판 추가");
        addBoardButton.addActionListener(e -> {});
        topPanel.add(addBoardButton);

        // 팝업 창에 상단 패널과 테이블을 포함한 JScrollPane 추가
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main_Class::new);
    }
}
