package volunteersservice.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import volunteersservice.models.entities.User;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = Logger.getLogger(UserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserService userService = ServiceFactory.getUserService();
		User user = userService.getUserByLogin(username);
		LOG.info("Found user: " + user);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new UserDetailsImpl(user);
	}

}