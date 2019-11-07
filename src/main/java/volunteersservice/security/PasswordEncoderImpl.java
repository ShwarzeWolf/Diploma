package volunteersservice.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import volunteersservice.utils.Utils;

public class PasswordEncoderImpl implements PasswordEncoder {
	private static final Logger LOG = LogManager.getLogger(PasswordEncoderImpl.class.getName());

	@Override
	public String encode(CharSequence rawPassword) {
		LOG.info("encoding {} as {}", rawPassword.toString(),
				Utils.calcSHA256(rawPassword.toString()) + Utils.calcMD5(rawPassword.toString()));
		return Utils.calcSHA256(rawPassword.toString()) + Utils.calcMD5(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		LOG.info("Matches? {} == {}", encode(rawPassword.toString()), encodedPassword.toString());
		return encode(rawPassword).equals(encodedPassword);
	}

}