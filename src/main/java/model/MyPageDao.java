package model;

import java.sql.*;
import java.util.*;
import util.DBManager;

public class MyPageDao {

    // 전체 통계 (완료 / 전체)
    public MyPageDto getMyPageInfo(int memberId) {

        MyPageDto dto = new MyPageDto();

        String sql =
            "SELECT COUNT(*) AS total, " +
            "SUM(CASE WHEN status=1 THEN 1 ELSE 0 END) AS done " +
            "FROM todo WHERE member_id=?";

        try (
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                dto.setTotalCount(rs.getInt("total"));
                dto.setDoneCount(rs.getInt("done"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return dto;
    }

    // 날짜별 완료 히스토리 (히트맵용)
    public Map<String,Integer> getDailyDone(int memberId){

        Map<String,Integer> map = new HashMap<>();

        String sql =
            "SELECT TO_CHAR(todo_date,'YYYY-MM-DD') AS d, COUNT(*) c " +
            "FROM todo WHERE member_id=? AND status=1 " +
            "GROUP BY TO_CHAR(todo_date,'YYYY-MM-DD')";

        try(
            Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                map.put(rs.getString("d"), rs.getInt("c"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return map;
    }
}
