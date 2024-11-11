package DTO;

public class Csb806Dto {
    private int empno;
    private String ename;
    private String job;
    private int mgr;
    private String hiredate;
    private int sal;
    private int comm;
    private int deptno;
    private int empCount;
    private int maxSal;
    private int minSal;
    private int totalSal;
    private int avgSal;

    // 기존 생성자들
    public Csb806Dto(int empno, String ename, String job, int mgr, String hiredate, int sal, int comm, int deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }

    public Csb806Dto(int deptno, int empCount, int maxSal, int minSal, int totalSal, int avgSal) {
        this.deptno = deptno;
        this.empCount = empCount;
        this.maxSal = maxSal;
        this.minSal = minSal;
        this.totalSal = totalSal;
        this.avgSal = avgSal;
    }

    // getter 메서드들
    public int getDeptno() {
        return deptno;
    }

    public int getEmpCount() {
        return empCount;
    }

    public int getMaxSal() {
        return maxSal;
    }

    public int getMinSal() {
        return minSal;
    }

    public int getTotalSal() {
        return totalSal;
    }

    public int getAvgSal() {
        return avgSal;
    }

    // 기존 getter와 setter 메서드들...
}
