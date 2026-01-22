package model;

import java.sql.*;
import java.util.ArrayList;

import util.DBManager;

/*
 * CategoryDao
 * -------------------------------------------------
 * category 테이블과 통신하는 DAO
 *
 * 기능
 *  1) 특정 회원의 카테고리 목록 조회
 *  2) 카테고리 추가
 *  3) 카테고리 삭제(단일)
 *  4) 회원 탈퇴 시 해당 회원 카테고리 전체 삭제  ✅ deleteByMember()
 */
public class CategoryDao {

    // =====================================================
    // 1) 카테고리 목록 조회 (내 카테고리만)
    // =====================================================
    public ArrayList<CategoryDto> getCategoryList(int memberId) {

        ArrayList<CategoryDto> list = new ArrayList<>();

        String sql =
            "SELECT id, member_id, name, color " +
            "FROM category " +
            "WHERE member_id = ? " +
            "ORDER BY id";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setId(rs.getInt("id"));
                dto.setMemberId(rs.getInt("member_id"));
                dto.setName(rs.getString("name"));
                dto.setColor(rs.getString("color"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 2) 카테고리 추가
    // =====================================================
    public void insertCategory(int memberId, String name, String color) {

        String sql =
            "INSERT INTO category (id, member_id, name, color) " +
            "VALUES (category_seq.NEXTVAL, ?, ?, ?)";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, memberId);
            ps.setString(2, name);
            ps.setString(3, color);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================
    // 3) 카테고리 삭제 (단일)
    //    - 안전하게 하려면 member_id도 같이 조건으로 거는게 좋음
    // =====================================================
    public void deleteCategory(int categoryId, int memberId) {

        String sql =
            "DELETE FROM category " +
            "WHERE id = ? AND member_id = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, categoryId);
            ps.setInt(2, memberId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================
    // 4) 회원 탈퇴 시 카테고리 전체 삭제 ✅
    //    - 해당 회원이 만든 category 전부 삭제
    // =====================================================
    public void deleteByMember(int memberId) {

        String sql = "DELETE FROM category WHERE member_id = ?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setInt(1, memberId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // 카테고리 수정
    public void update(int id, String name, String color) {
        String sql = "UPDATE category SET name = ?, color = ? WHERE id = ?";

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
 // 회원별 카테고리 조회
    public ArrayList<CategoryDto> getListByMember(int memberId) {
        ArrayList<CategoryDto> list = new ArrayList<>();

        String sql = "SELECT * FROM category WHERE member_id = ? ORDER BY id";

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

}