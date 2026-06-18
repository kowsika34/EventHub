package APIResponse;

public class EventResponse {
	
	private boolean success;
	private DataResponse data;
	private String message;
	
	public boolean isSuccess() {
	    return success;
	}

	public void setSuccess(boolean success) {
	    this.success = success;
	}
	public DataResponse getData() {
		return data;
	}
	public void setData() {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
