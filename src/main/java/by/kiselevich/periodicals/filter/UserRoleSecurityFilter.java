package by.kiselevich.periodicals.filter;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserRoleSecurityFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(UserRoleSecurityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession httpSession = httpServletRequest.getSession();

        Object userRoleObject = httpSession.getAttribute(Attribute.USER_TYPE.getValue());

        if (userRoleObject == null) {
            httpSession.setAttribute(Attribute.USER_TYPE.getValue(), UserType.GUEST);
            LOG.trace("Set role to guest");
        }

        chain.doFilter(request, response);
    }
}
