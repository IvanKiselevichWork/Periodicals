package by.kiselevich.periodicals.filter;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.UserType;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * {@link Filter} implementation to prevent user activity if user has been banned
 */
public class UserBlockFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(UserBlockFilter.class);

    private final UserService userService;

    public UserBlockFilter() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession();
        String userLogin = (String) httpSession.getAttribute(Attribute.LOGIN.getValue());
        try {
            if (userLogin != null) {
                User user = userService.getUserByLogin(userLogin);
                if(user == null || !user.isAvailable()) {
                    httpServletRequest.getSession().setAttribute(Attribute.USER_TYPE.getValue(), UserType.GUEST);
                }
            }
        } catch (ServiceException e) {
            LOG.warn(e);
        }
        chain.doFilter(request, response);
    }
}
