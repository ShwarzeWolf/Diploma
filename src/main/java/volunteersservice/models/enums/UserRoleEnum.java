package volunteersservice.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEnum implements GrantedAuthority {
	ORGANISER, MANAGER, COORDINATOR, ADMIN;

	public String getAuthority() {
		return this.toString();
	}
}