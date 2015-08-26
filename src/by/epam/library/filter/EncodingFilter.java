package by.epam.library.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Sets requests and responses encoding as UTF-8.
 */
public final class EncodingFilter implements Filter {

	private String encoding;

	public EncodingFilter() {
	}

	/**
	 * Sets requests and responses encoding as UTF-8.
	 * 
	 * @param request
	 *          The servlet request we are processing
	 * @param response
	 *          The servlet response we are creating
	 * @param chain
	 *          The filter chain we are processing
	 *          <p>
	 * @exception IOException
	 *              if an input/output error occurs
	 * @exception ServletException
	 *              if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String codeRequest = request.getCharacterEncoding();
		if (encoding != null && !encoding.equalsIgnoreCase(codeRequest)) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	/**
	 * Destroy method for this filter
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 * 
	 * @param filterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		encoding = filterConfig.getInitParameter("encoding");
	}

}
