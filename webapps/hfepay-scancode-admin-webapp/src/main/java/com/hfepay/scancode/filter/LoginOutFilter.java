package com.hfepay.scancode.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 自定义用户拦截器
 * 
 * @author ssd
 * @date 2015-4-30 下午3:49:09
 */
public class LoginOutFilter extends LogoutFilter {
	private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		/*Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();*/
		session.removeAttribute("currentScanAdminUser");
		session.removeAttribute("rootScanAdminMenu");
		session.removeAttribute("secondScanAdminMenu");
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);
		// try/catch added for SHIRO-298:
		try {
			subject.logout();
		} catch (SessionException ise) {
			log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
		}
		issueRedirect(request, response, redirectUrl);
		return false;
	}
	
}
