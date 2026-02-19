package model;

import java.sql.*;
import util.DBManager;

/*
 * MemberDao
 * -----------------------------------
 * 회원 관련 DB 처리 클래스
 */
public class MemberDao {

    /* ===========================
     * 로그인
     * =========================== */
    public MemberDto login(String username, String password) {

        String sql = "SELECT * FROM member WHERE username=? AND password=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MemberDto dto = new MemberDto();
                dto.setId(rs.getInt("id"));
                dto.setUsername(rs.getString("username"));
                return dto;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ===========================
     * 아이디 중복 체크
     * =========================== */
    public boolean existsUsername(String username) {
        String sql = "SELECT COUNT(*) FROM member WHERE username=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===========================
     * 회원가입
     * =========================== */
    public void insertMember(String username, String password) {
    	String sql = "INSERT INTO member (id, username, password) VALUES (member_seq.NEXTVAL, ?, ?)";
        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===========================
     * 현재 비밀번호 확인
     * =========================== */
    public boolean checkPassword(int id, String password) {
        String sql = "SELECT COUNT(*) FROM member WHERE id=? AND password=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    /* ===========================
     * 비밀번호 변경
     * =========================== */
    public void updatePassword(int id, String newPw) {
        String sql = "UPDATE member SET password=? WHERE id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, newPw);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /* ===========================
     * 회원 탈퇴 (회원만 삭제)
     * =========================== */
    public void deleteMember(int id) {
        String sql = "DELETE FROM member WHERE id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
