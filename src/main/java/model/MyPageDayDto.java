package model;

/*
 * 하루 완료량 DTO
 */
public class MyPageDayDto {

    private String day;   // 2026-01-13
    private int count;   // 그날 완료한 개수

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
