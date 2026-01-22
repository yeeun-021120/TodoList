package model;

public class MemberDto { //회원 정보 DTO
	
	 	private int id ; // 회원 고유 번호
	    private String username ; // 로그인에 사용하는 아이디
	    private String password ; // Bcrypt 로 암호화된 비밀번호
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	    
	    
}
