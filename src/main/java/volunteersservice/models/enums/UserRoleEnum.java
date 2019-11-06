package volunteersservice.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEnum implements GrantedAuthority {
	ORGANISER, MANAGER, COORDINATOR, VOLUNTEER;

	public String getAuthority() {
		return this.toString().toLowerCase();
	}
}