package model;

import java.sql.*;
import java.util.*;
import util.DBManager;

/*
 * TodoDao
 * -------------------------------------------------
 * 할 일(Todo) 관련 DB 처리 클래스
 *
 * 기능
 * 1. 사용자 전체 할 일 조회
 * 2. 날짜별 할 일 조회
 * 3. 할 일 추가
 * 4. 할 일 상태 변경
 * 5. 할 일 삭제
 * 6. 월별 히트맵
 * 7. 회원 탈퇴 시 할 일 전체 삭제
 */
public class TodoDao {

    /* ==============================
     * 전체 할 일 조회 (사용자 기준)
     * ============================== */
    public ArrayList<TodoDto> getTodoListByUser(int memberId) {

        ArrayList<TodoDto> list = new ArrayList<>();

        String sql = """
            SELECT t.id, t.content, t.status, t.todo_date,
                   c.name AS category_name,
                   c.color AS category_color
            FROM todo t
            LEFT JOIN category c ON t.category_id = c.id
            WHERE t.member_id = ?
            ORDER BY t.todo_date DESC, t.id DESC
        """;

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TodoDto dto = new TodoDto();
                dto.setId(rs.getInt("id"));
                dto.setContent(rs.getString("content"));
                dto.setStatus(rs.getInt("status"));
                dto.setTodoDate(rs.getString("todo_date"));
                dto.setCategoryName(rs.getString("category_name"));
                dto.setCategoryColor(rs.getString("category_color"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* ==============================
     * 날짜별 할 일 조회
     * ============================== */
    public ArrayList<TodoDto> getTodoListByDay(int memberId, String day) {

        ArrayList<TodoDto> list = new ArrayList<>();

        String sql = """
            SELECT t.id, t.content, t.status, t.todo_date,
                   c.name AS category_name,
                   c.color AS category_color
            FROM todo t
            LEFT JOIN category c ON t.category_id = c.id
            WHERE t.member_id = ? AND t.todo_date = ?
            ORDER BY t.id
        """;

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ps.setString(2, day);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TodoDto dto = new TodoDto();
                dto.setId(rs.getInt("id"));
                dto.setContent(rs.getString("content"));
                dto.setStatus(rs.getInt("status"));
                dto.setTodoDate(rs.getString("todo_date"));
                dto.setCategoryName(rs.getString("category_name"));
                dto.setCategoryColor(rs.getString("category_color"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* ==============================
     * 할 일 추가
     * ============================== */
    public void insertTodo(int memberId, String content, String day, int categoryId) {

        String sql = """
            INSERT INTO todo
            (id, member_id, content, status, todo_date, category_id)
            VALUES (todo_seq.NEXTVAL, ?, ?, 0, ?, ?)
        """;

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ps.setString(2, content);
            ps.setString(3, day);
            ps.setInt(4, categoryId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ==============================
     * 완료 / 미완료 변경
     * ============================== */
    public void updateTodoStatus(int todoId, int status) {

        String sql = "UPDATE todo SET status=? WHERE id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, status);
            ps.setInt(2, todoId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ==============================
     * 할 일 삭제
     * ============================== */
    public void deleteTodo(int todoId) {
        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("DELETE FROM todo WHERE id=?")
        ) {
            ps.setInt(1, todoId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ==============================
     * 히트맵
     * ============================== */
    public Map<String, Integer> getMonthlyHeatmap(int memberId, String month) {

        Map<String, Integer> map = new HashMap<>();

        String sql = """
            SELECT todo_date, COUNT(*) cnt
            FROM todo
            WHERE member_id = ?
              AND status = 1
              AND todo_date LIKE ?
            GROUP BY todo_date
        """;

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ps.setString(2, month + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("todo_date"), rs.getInt("cnt"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /* ==============================
     * 회원 탈퇴 시 Todo 전체 삭제
     * ============================== */
    public void deleteByMember(int memberId) {
        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("DELETE FROM todo WHERE member_id=?")
        ) {
            ps.setInt(1, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
