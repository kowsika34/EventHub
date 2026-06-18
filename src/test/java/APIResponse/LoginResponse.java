package APIResponse;

import Pojo.User;

public class LoginResponse {
	
	    private boolean success;
	    private String token;
	    private User user;
	    private int statuscode;
	    private String StatusLine;
		
	    public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
		
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public int getStatuscode() {
			
			return statuscode;
		}
		
       public void setStatuscode(int statuscode) {
			
			this.statuscode= statuscode;
		}
	public String getStatusLine() {
		 return StatusLine;
	}
	
	public void setStatusLine(String statusLine) {
		StatusLine = statusLine;
	}

	
}
