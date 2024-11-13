package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import DAO.Csb806Dao;
import DAO.Jih0316_Board_DAO;
import DAO.Jih0316_DAO;
import DAO.Jih0316_DAO.Employee;
import DAO.Kjy1122DAO;
import DAO.LSH0708_Project_DAO;
import DAO.Project_DAO;
import DTO.BOARD_DTO;
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
	
	private JTextField boardNoField;
	private JTextField titleField;
	private JTextArea contentField;
	private JTextField writerField;
	private JComboBox empNoField;
	private DefaultTableModel tableModel;
	private JTable table;
	
	
	
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
        
        
        searchBoardButton = new JButton("게시판");
        deleteUserButton = new JButton("유저 삭제");
        salTopNButton = new JButton("급여 TOP N");

        buttonPanel.add(kjy1122Button);
        buttonPanel.add(jihButton);
        buttonPanel.add(kjy0227Button);
        buttonPanel.add(lshButton);
        buttonPanel.add(csbButton);
        
       
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
        deleteUserButton.addActionListener(e -> deleteUser());

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
		int N = 0;
		String input = JOptionPane.showInputDialog("Top N을 입력하세요. 최대 사원 수 : "+userNum);
		if(input ==null) {
			return;
		}else {
			N = Integer.parseInt(input);
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
    
    private void deleteUser() {
        // 사용자에게 empno (사원 번호)를 입력받음
        String empnoStr = JOptionPane.showInputDialog("삭제할 사원 번호를 입력하세요:");
        if (empnoStr == null || empnoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "유효한 사원 번호를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // empno를 정수로 변환
        int empnoToDelete;
        try {
            empnoToDelete = Integer.parseInt(empnoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "잘못된 번호 형식입니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 사용자 삭제 및 게시글 삭제 수행
        boolean isDeleted = false;
        try {
            // empno에 해당하는 사원과 게시글 삭제
            isDeleted = new Jih0316_Board_DAO().deleteUser(empnoToDelete);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 삭제 성공 여부에 따른 메시지 표시
        String message = isDeleted ? "사원과 해당 사원의 게시글이 성공적으로 삭제되었습니다." : "사원 또는 사원의 게시글 삭제에 실패했습니다.";
        JOptionPane.showMessageDialog(null, message);
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
        tableModel = new DefaultTableModel(data, columnNames) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                // 모든 셀을 수정 불가하게 설정 (false 반환)
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JDialog dialog = new JDialog();
        dialog.setTitle("게시판 목록");
        dialog.setSize(700, 300);
        dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 상단 버튼 패널
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBoardButton = new JButton("게시글 추가");
        addBoardButton.addActionListener(e -> openAddUserWindow());
        topPanel.add(addBoardButton);
        JButton deleteBoardButton = new JButton("게시글 삭제");
        deleteBoardButton.addActionListener(e ->deleteBoard());
        topPanel.add(deleteBoardButton);
        JButton modifyBoardButton = new JButton("게시글 수정");
        modifyBoardButton.addActionListener(e ->openModifyWindow());
        topPanel.add(modifyBoardButton);

        // 팝업 창에 상단 패널과 테이블을 포함한 JScrollPane 추가
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }
    
    // 게시글 수정 창 열기
    public void openModifyWindow() {
    	int selectedRow = table.getSelectedRow();
    	if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "수정할 게시글을 선택하세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	int boardNo = (int)table.getValueAt(selectedRow, 0);
    	ArrayList<DTO.BOARD_DTO> list = lsh_dao.selectedModifyBoard(boardNo);
    	String modifyBoardNo="";
    	String modifyTitle="";
    	String modifyContent="";
    	String modifyWriter="";
    	String modifyEmpno="";
    	
    	
    	for(BOARD_DTO dto: list) {
    		modifyBoardNo=Integer.toString(dto.getBoardNo());
    		modifyTitle=dto.getTitle();
    		modifyContent=dto.getContent();
    		modifyWriter=dto.getWriter();
    		modifyEmpno=Integer.toString(dto.getEmpno());
		 }
    	
    	
    	JFrame newFrame = new JFrame("게시글 수정");
		newFrame.setSize(350, 450);
		newFrame.setLocationRelativeTo(null);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 새 창만 닫기
		newFrame.setLayout(new GridBagLayout());
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5); // 패딩 설정
		
		// 입력 필드 및 레이블 생성
		gbc.gridx = 0;
		gbc.gridy = 0;
		newFrame.add(new JLabel("글번호:"), gbc);

		gbc.gridx = 1;
		boardNoField = new JTextField();
		boardNoField.setText(modifyBoardNo);
		boardNoField.setEnabled(false);
		boardNoField.setPreferredSize(new Dimension(200, 25));
		boardNoField.setBorder(border);
		newFrame.add(boardNoField, gbc);


		gbc.gridx = 0;
		gbc.gridy = 1;
		newFrame.add(new JLabel("제목:"), gbc);
		gbc.gridx = 1;
		titleField = new JTextField();
		titleField.setText(modifyTitle);
		titleField.setPreferredSize(new Dimension(200, 25));
		titleField.setBorder(border);
		newFrame.add(titleField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		newFrame.add(new JLabel("내용:"), gbc);
		gbc.gridx = 1;
		contentField = new JTextArea(10,10);
		contentField.setText(modifyContent);
		contentField.setPreferredSize(new Dimension(200, 25));
		contentField.setBorder(border);
		newFrame.add(contentField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		newFrame.add(new JLabel("작성자명:"), gbc);
		gbc.gridx = 1;
		writerField = new JTextField();
		writerField.setText(modifyWriter);
		writerField.setPreferredSize(new Dimension(200, 25));
		writerField.setBorder(border);
		newFrame.add(writerField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		newFrame.add(new JLabel("작성자 번호:"), gbc);
		gbc.gridx = 1;
		empNoField = new JComboBox<String>();
		
		ArrayList<String> searchList = lsh_dao.searchUserId();
		
		for(String list1: searchList) {
			empNoField.addItem(list1);
		}
		empNoField.setSelectedItem(modifyEmpno);
		empNoField.setEnabled(false);
		empNoField.setPreferredSize(new Dimension(200, 25));
		empNoField.setBorder(border);
		newFrame.add(empNoField, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2; // 버튼이 두 열을 차지하도록 설정
		gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
		JButton addBoardButton = new JButton("게시글 수정");
		addBoardButton.setPreferredSize(new Dimension(120, 40));
		newFrame.add(addBoardButton, gbc);
		addBoardButton.addActionListener(e -> modifyBoard());
		newFrame.setVisible(true);
    }
    //게시글 수정 
    public void modifyBoard() {
    	if (boardNoField.getText().isEmpty() || titleField.getText().isEmpty() || contentField.getText().isEmpty()
				|| writerField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "모든 필드를 채워주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return; // 메서드 종료
		}
    	int confirm = JOptionPane.showConfirmDialog(this, "게시글을 수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
    	if (confirm != JOptionPane.YES_OPTION) {
			return;
		} else {			
	    	String boardNo = boardNoField.getText();
			String title = titleField.getText();
			String content = contentField.getText();
			String writer = writerField.getText();
			String empNo = (String)empNoField.getSelectedItem();
			
			int modCount =lsh_dao.modifyBoard(boardNo, title, content, writer, empNo);
			if(modCount>0) {
				JOptionPane.showMessageDialog(this, "게시글 데이터가 수정되었습니다.", "수정 완료", JOptionPane.INFORMATION_MESSAGE);
				String[] columnNames = {"글번호", "제목", "내용", "작성자명", "작성자번호", "작성일"};
				Object[][] newData = dao.getBoardList();

			    // 기존의 테이블 모델을 새 데이터로 교체
			    DefaultTableModel newTableModel = new DefaultTableModel(newData, columnNames);
			    table.setModel(newTableModel);

			    // 테이블 새로 고침
			    table.revalidate();
			    table.repaint();
			}
			
			
		}
    }
    
    // 게시글 추가 창 열기
    public void openAddUserWindow() {    	
    	JFrame newFrame = new JFrame("게시글 추가");
		newFrame.setSize(350, 450);
		newFrame.setLocationRelativeTo(null);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 새 창만 닫기
		newFrame.setLayout(new GridBagLayout());
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5); // 패딩 설정
		
		// 입력 필드 및 레이블 생성
		gbc.gridx = 0;
		gbc.gridy = 0;
		newFrame.add(new JLabel("글번호:"), gbc);

		gbc.gridx = 1;
		boardNoField = new JTextField();
		boardNoField.setPreferredSize(new Dimension(200, 25));
		boardNoField.setBorder(border);
		newFrame.add(boardNoField, gbc);


		gbc.gridx = 0;
		gbc.gridy = 1;
		newFrame.add(new JLabel("제목:"), gbc);
		gbc.gridx = 1;
		titleField = new JTextField();
		titleField.setPreferredSize(new Dimension(200, 25));
		titleField.setBorder(border);
		newFrame.add(titleField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		newFrame.add(new JLabel("내용:"), gbc);
		gbc.gridx = 1;
		contentField = new JTextArea(10,10);
		contentField.setPreferredSize(new Dimension(200, 25));
		contentField.setBorder(border);
		newFrame.add(contentField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		newFrame.add(new JLabel("작성자명:"), gbc);
		gbc.gridx = 1;
		writerField = new JTextField();
		writerField.setPreferredSize(new Dimension(200, 25));
		writerField.setBorder(border);
		newFrame.add(writerField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		newFrame.add(new JLabel("작성자 번호:"), gbc);
		gbc.gridx = 1;
		empNoField = new JComboBox<String>();
		ArrayList<String> searchList = lsh_dao.searchUserId();
		for(String list: searchList) {
			empNoField.addItem(list);
		}
		empNoField.setPreferredSize(new Dimension(200, 25));
		empNoField.setBorder(border);
		newFrame.add(empNoField, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2; // 버튼이 두 열을 차지하도록 설정
		gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
		JButton addBoardButton = new JButton("게시글 추가");
		addBoardButton.setPreferredSize(new Dimension(120, 40));
		newFrame.add(addBoardButton, gbc);
		addBoardButton.addActionListener(e -> addBoard());
		newFrame.setVisible(true);
    }
    
    //게시글 추가
    public void addBoard() {
    	if (boardNoField.getText().isEmpty() || titleField.getText().isEmpty() || contentField.getText().isEmpty()
				|| writerField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "모든 필드를 채워주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
			return; // 메서드 종료
		}
    	String boardNo = boardNoField.getText();
		String title = titleField.getText();
		String content = contentField.getText();
		String writer = writerField.getText();
		String empNo = (String)empNoField.getSelectedItem();
		
		int result = lsh_dao.insertBoard(boardNo, title, content, writer, empNo);
		if(result>0) {
			JOptionPane.showMessageDialog(this, "게시글이 추가 되었습니다.", "게시글 추가 완료", JOptionPane.INFORMATION_MESSAGE);
			boardNoField.setText("");
			titleField.setText("");
			contentField.setText("");
			writerField.setText("");
			
			String[] columnNames = {"글번호", "제목", "내용", "작성자명", "작성자번호", "작성일"};
			Object[][] newData = dao.getBoardList();

		    // 기존의 테이블 모델을 새 데이터로 교체
		    DefaultTableModel newTableModel = new DefaultTableModel(newData, columnNames);
		    table.setModel(newTableModel);

		    // 테이블 새로 고침
		    table.revalidate();
		    table.repaint();
		}
		
		
    }
    //게시글 삭제
    public void deleteBoard() {
    	int selectedRow = table.getSelectedRow();
    	if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "삭제할 게시글을 선택하세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	int confirm = JOptionPane.showConfirmDialog(this, "선택된 게시글을 삭제하시겠습니까?", "게시글 삭제", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
    	
    	if (confirm != JOptionPane.YES_OPTION) {
			return;
		} else {
			int boardNo = (int)table.getValueAt(selectedRow, 0);
			boolean result = lsh_dao.deleteBoard(boardNo);
			if(result) {
				JOptionPane.showMessageDialog(this, "게시글이 삭제 되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
				tableModel.removeRow(selectedRow);
		        // 테이블 새로고침
		        table.revalidate();
		        table.repaint();
			}
			
		}
    
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main_Class::new);
    }
}
