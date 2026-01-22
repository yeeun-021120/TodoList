package model;

/*
 * CategoryDto
 * --------------------------------------
 * category 테이블의 1행을 담는 객체
 * (공부, 운동 같은 카테고리 정보)
 */
public class CategoryDto {

    private int id;         // category.id (PK)
    private int memberId;   // 이 카테고리를 만든 사용자
    private String name;    // 카테고리 이름 (공부, 운동 등)
    private String color;   // 표시 색상 (#4caf50 등)

    // getter / setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
