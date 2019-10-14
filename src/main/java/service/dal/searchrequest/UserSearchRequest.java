package service.dal.searchrequest;
import service.dal.entity.UserEntity;

public class UserSearchRequest implements SearchRequest<UserEntity>{
	private Integer id;
	private String name, email;
	public UserSearchRequest() {
		id = null;
		name = null;
		email = null;
	}
	public UserSearchRequest setID(Integer userID) {
		id = userID;
		return this;
	}
	public UserSearchRequest setName(String userName) throws IllegalArgumentException {
		if (userName.contains("'"))
			throw new IllegalArgumentException("Name cannot contain \"'\"");
		name = userName;
		return this;
	}
	public UserSearchRequest setemail(String email) throws IllegalArgumentException {
		if (email.contains("'"))
			throw new IllegalArgumentException("Email cannot contain \"'\"");
		this.email = email;
		return this;
	}
	@Override
	public String whereString() {
		if (name == null && email == null && id == null)
			return "";
		boolean first = false;
		String ans = " where";
		if (id != null) {
			ans += " UserID = \'" + id + "\'";
			first = true;
		}
		if (name != null) {
			ans += (first ? " and" : "") + " Name = \'" + name + "\'";
			first = true;
		}
		if (email != null)
			ans += (first ? " and" : "") + " Email=\'" + email + "\'";
		return ans;
	}
	@Override
	public String toString() {
		if (name == null && email == null && id == null)
			return "(empty UserSearchRequest)";
		return whereString().substring(7);
	}
}