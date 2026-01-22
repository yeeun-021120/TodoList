package model;

/*
 * MyPageDto
 * 마이페이지에서 보여줄 사용자 통계 데이터
 */
public class MyPageDto {

    private String username;   // 사용자 이름
    private int totalCount;    // 전체 할 일 개수
    private int doneCount;     // 완료한 할 일 개수

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }
}
