package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Jih0316_Board_DAO {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERID = "scott";
    private static final String PASSWORD = "tiger";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERID, PASSWORD);
    }

    public boolean deleteUser(int empno) throws SQLException {
        // 게시글 삭제 쿼리
        String deletePostsSQL = "DELETE FROM board WHERE empno = ?";
        // 사원 삭제 쿼리 (emp 테이블)
        String deleteUserSQL = "DELETE FROM emp WHERE empno = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);  // 트랜잭션 시작

            try (PreparedStatement pstmtPosts = conn.prepareStatement(deletePostsSQL);
                 PreparedStatement pstmtUser = conn.prepareStatement(deleteUserSQL)) {

                // 게시글 삭제
                pstmtPosts.setInt(1, empno);
                int postsAffectedRows = pstmtPosts.executeUpdate();

                // 사원 삭제
                pstmtUser.setInt(1, empno);
                int userAffectedRows = pstmtUser.executeUpdate();

                // 둘 다 성공적으로 삭제되었을 경우 커밋
                if (postsAffectedRows > 0 && userAffectedRows > 0) {
                    conn.commit();  // 트랜잭션 커밋
                    return true;
                } else {
                    conn.rollback();  // 실패 시 롤백
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();  // 예외 발생 시 롤백
                throw e;
            }
        }
    }

}
