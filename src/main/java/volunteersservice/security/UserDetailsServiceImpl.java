package volunteersservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import volunteersservice.services.UserService;
import volunteersservice.models.entities.User;
import volunteersservice.utils.ServiceFactory;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserService users = ServiceFactory.getUserService();
		User user = users.getUserByLogin(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new UserDetailsImpl(user);
	}

}