package volunteersservice.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

class AllowFromStrategyImpl implements AllowFromStrategy {

	@Override
	public String getAllowFromValue(HttpServletRequest request) {
		return "http://www.kanootoko.org";
	}

}

public class HeaderWriterImpl implements HeaderWriter {
	private final static XFrameOptionsHeaderWriter xFrameOptionsHeaderWriter = new XFrameOptionsHeaderWriter(new AllowFromStrategyImpl());

	public HeaderWriterImpl() {
	}

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		xFrameOptionsHeaderWriter.writeHeaders(request, response);
	}
}