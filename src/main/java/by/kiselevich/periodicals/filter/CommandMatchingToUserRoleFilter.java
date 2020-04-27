package by.kiselevich.periodicals.filter;

import by.kiselevich.periodicals.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

import static by.kiselevich.periodicals.command.CommandName.*;

/**
 * {@link Filter} implementation to check if user command match to user role
 */
public class CommandMatchingToUserRoleFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CommandMatchingToUserRoleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        CommandName commandName;
        String commandString = httpServletRequest.getParameter(JspParameter.COMMAND.getValue());

        try {
            if (commandString != null) {
                commandName = CommandName.valueOf(commandString.toUpperCase());
            } else {
                commandName = HOME;
            }

            Set<CommandName> commandNames;
            switch ((UserType) httpServletRequest.getSession().getAttribute(Attribute.USER_TYPE.getValue())) {
                case USER:
                    commandNames = CommandNames.USER.getNames();
                    break;
                case ADMIN:
                    commandNames = CommandNames.ADMIN.getNames();
                    break;
                case GUEST:
                default:
                    commandNames = CommandNames.GUEST.getNames();
                    break;
            }

            if (!commandNames.contains(commandName)) {
                LOG.info("Forbidden command {}", commandName);
                httpServletRequest.getRequestDispatcher(Page.WRONG_REQUEST.getPath()).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }

        } catch (IllegalArgumentException e) {
            LOG.warn("Invalid command string: {}", commandString);
            httpServletRequest.getRequestDispatcher(Page.WRONG_REQUEST.getPath()).forward(request, response);
        }
    }
}
