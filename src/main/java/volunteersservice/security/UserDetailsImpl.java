package volunteersservice.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import volunteersservice.models.entities.User;
import volunteersservice.models.enums.UserRoleEnum;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

	private final static Logger LOG = Logger.getLogger(UserDetailsImpl.class.getName());

	private static final long serialVersionUID = -5604409417012553128L;
	@NotNull
	private User userModel;

	public UserDetailsImpl(User user) {
		this.userModel = user;
	}

	@Override
	public Collection<UserRoleEnum> getAuthorities() {
		List<UserRoleEnum> res = new ArrayList<>();
		res.add(userModel.getUserRole().getEnum());
		LOG.info("Get authorities: " + res);
		return res;
	}

	public User getUser() {
		return userModel;
	}

	@Override
	public String getPassword() {
		return userModel.getHash1() + userModel.getHash2();
	}

	@Override
	public String getUsername() {
		return userModel.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}