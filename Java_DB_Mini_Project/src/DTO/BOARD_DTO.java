package DTO;

import java.time.LocalDate;

public class BOARD_DTO {
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private int empno;
	private LocalDate regdate;
	
	public BOARD_DTO() {};
	
	public BOARD_DTO(int boardNo, String title, String content, String writer, int empno, LocalDate regdate) {
		super();
		this.boardNo = boardNo;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.empno = empno;
		this.regdate = regdate;
	}
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public LocalDate getRegdate() {
		return regdate;
	}
	public void setRegdate(LocalDate regdate) {
		this.regdate = regdate;
	}
}
