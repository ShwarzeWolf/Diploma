package volunteersservice.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import volunteersservice.utils.Utils;

public class PasswordEncoderImpl implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		// LOG.info("encoding " + rawPassword.toString() + " as " + Utils.calcSHA256(rawPassword.toString())
		// 		+ Utils.calcMD5(rawPassword.toString()));
		return Utils.calcSHA256(rawPassword.toString()) + Utils.calcMD5(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// LOG.info("Matches? " + encode(rawPassword.toString()) + " == " + encodedPassword.toString());
		return encode(rawPassword).equals(encodedPassword);
	}

}