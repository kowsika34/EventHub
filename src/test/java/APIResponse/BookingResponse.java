package APIResponse;

public class BookingResponse {
	
private String success;
private	Integer id;
private	Integer eventId;
private	String customerName;
private	String customerEmail;
private	Integer customerPhone;
private	Integer quantity;
private	Integer totalPrice;
private	String status;
private	String  bookingRef;
private	Integer createdAt;
private	Integer updatedAt;
private	 EventResponse data;




public String getSuccess() {
	return success;
}
public void setSuccess(String success) {
	this.success = success;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getEventId() {
	return eventId;
}
public void setEventId(Integer eventId) {
	this.eventId = eventId;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getCustomerEmail() {
	return customerEmail;
}
public void setCustomerEmail(String customerEmail) {
	this.customerEmail = customerEmail;
}
public Integer getCustomerPhone() {
	return customerPhone;
}
public void setCustomerPhone(Integer customerPhone) {
	this.customerPhone = customerPhone;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}
public Integer getTotalPrice() {
	return totalPrice;
}
public void setTotalPrice(Integer totalPrice) {
	this.totalPrice = totalPrice;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getBookingRef() {
	return bookingRef;
}
public void setBookingRef(String bookingRef) {
	this.bookingRef = bookingRef;
}
public Integer getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(Integer createdAt) {
	this.createdAt = createdAt;
}
public Integer getUpdatedAt() {
	return updatedAt;
}
public void setUpdatedAt(Integer updatedAt) {
	this.updatedAt = updatedAt;
}
public EventResponse getData() {
	return data;
}
public void setData(EventResponse data) {
	this.data = data;
}
	
	

}



