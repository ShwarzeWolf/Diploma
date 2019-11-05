package volunteersservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import volunteersservice.businesslogic.ManagerFabric;
import volunteersservice.businesslogic.services.UserManager;
import volunteersservice.models.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserManager users = ManagerFabric.getUserManager();
		User user = users.getUserByLogin(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new UserDetailsImpl(user);
	}

}