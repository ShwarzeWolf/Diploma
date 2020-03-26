package volunteersservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import volunteersservice.models.entities.User;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserService userService = ServiceFactory.getUserService();
		User user = userService.getUserByLogin(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new UserDetailsImpl(user);
	}

}