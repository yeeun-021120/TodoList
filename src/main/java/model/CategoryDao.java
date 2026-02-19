package model;

import java.sql.*;
import java.util.ArrayList;
import util.DBManager;

/**
 * 📁 CategoryDao
 * - 카테고리 DB 처리 전담 클래스
 */
public class CategoryDao {

    // ▶ 카테고리 목록 조회
    public ArrayList<CategoryDto> getListByMember(int memberId) {
        ArrayList<CategoryDto> list = new ArrayList<>();

        String sql = "SELECT * FROM category WHERE member_id=? ORDER BY id";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setColor(rs.getString("color"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ▶ 카테고리 추가
    public void insertCategory(int memberId, String name, String color) {
        String sql =
            "INSERT INTO category (id, member_id, name, color) " +
            "VALUES (category_seq.NEXTVAL, ?, ?, ?)";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ps.setString(2, name);
            ps.setString(3, color);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ▶ 카테고리 수정
    public void updateCategory(int id, String name, String color) {
        String sql = "UPDATE category SET name=?, color=? WHERE id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            ps.setString(2, color);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ▶ 카테고리 삭제
 // ▶ 카테고리 삭제
    public void deleteCategory(int id, int memberId) {
        String sql = "DELETE FROM category WHERE id=? AND member_id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.setInt(2, memberId);

            int result = ps.executeUpdate();
            System.out.println("삭제된 카테고리 수 = " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ▶ 회원 탈퇴 시 전체 삭제
    public void deleteByMember(int memberId) {
        String sql = "DELETE FROM category WHERE member_id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
