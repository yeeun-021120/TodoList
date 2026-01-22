package model;

/*
 * TodoDto
 * ------------------------------------
 * todo 테이블 + category 테이블을 JOIN 해서
 * 화면에 뿌릴 데이터를 담는 객체
 */
public class TodoDto {

    private int id;                 // todo.id
    private String content;         // 할 일 내용
    private int status;             // 완료 여부 (0,1)
    private String todoDate;        // 날짜 (YYYY-MM-DD)

    private int categoryId;         // category.id
    private String categoryName;    // 카테고리 이름
    private String categoryColor;   // 카테고리 색상

    // ===== 기본 todo =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getTodoDate() { return todoDate; }
    public void setTodoDate(String todoDate) { this.todoDate = todoDate; }

    // ===== category =====
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryColor() { return categoryColor; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
}
