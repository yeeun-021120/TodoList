package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    public static Connection getConnection() {
        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // DB 연결 생성
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "jsl26",
                "1234"
            );

            /*
             * ⭐ 중요 ⭐
             * Oracle JDBC는 기본적으로 autoCommit이 false일 수 있다.
             * 그래서 INSERT / UPDATE / DELETE 후
             * commit이 안 되어 데이터가 저장되지 않는 문제가 발생한다.
             *
             * autoCommit을 true로 설정하면
             * SQL 실행 시마다 자동으로 commit된다.
             * (개인/학습 프로젝트에 가장 안전한 설정)
             */
            conn.setAutoCommit(true);

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
