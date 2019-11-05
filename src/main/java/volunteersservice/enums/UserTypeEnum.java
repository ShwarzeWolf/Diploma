package volunteersservice.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserTypeEnum implements GrantedAuthority {
	ORGANISER, MANAGER, COORDINATOR, VOLUNTEER;

	public String getAuthority() {
		return this.toString().toLowerCase();
	}
}