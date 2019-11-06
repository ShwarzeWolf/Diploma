package volunteersservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import volunteersservice.models.enums.UserTypeEnum;
import volunteersservice.models.entities.User;

public class UserDetailsImpl implements UserDetails {

	private final static Logger LOG = LogManager.getLogger(UserDetailsImpl.class.getName());

	private static final long serialVersionUID = -5604409417012553128L;
	private User userModel;

	public UserDetailsImpl(User user) {
		this.userModel = user;
	}

	@Override
	public Collection<UserTypeEnum> getAuthorities() {
		List<UserTypeEnum> res = new ArrayList<>();
		res.add(userModel.getUserType().getEnum());
		LOG.info("Get authorities: {}", res.get(0));
		return res;
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