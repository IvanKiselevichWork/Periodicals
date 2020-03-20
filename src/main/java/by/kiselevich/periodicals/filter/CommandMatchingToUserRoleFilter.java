package by.kiselevich.periodicals.filter;

import by.kiselevich.periodicals.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static by.kiselevich.periodicals.command.CommandName.*;

public class CommandMatchingToUserRoleFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CommandMatchingToUserRoleFilter.class);

    private static final Set<CommandName> guestCommands = EnumSet.of(
            WRONG_REQUEST,
            HOME,
            SIGN_IN,
            SIGN_UP,
            CHANGE_LANGUAGE
    );

    private static final Set<CommandName> userCommands = EnumSet.of(
            WRONG_REQUEST,
            HOME,
            SIGN_OUT,
            CHANGE_LANGUAGE
    );

    private static final Set<CommandName> emptySet = new HashSet<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        CommandName commandName = null;
        String commandString = httpServletRequest.getParameter(JspParameter.COMMAND.getValue());

        try {
            if (commandString != null) {
                commandName = CommandName.valueOf(commandString.toUpperCase());
            } else {
                commandName = HOME;
            }

            Set<CommandName> commandNames = null;
            switch ((UserRole) httpServletRequest.getSession().getAttribute(Attribute.USER_ROLE.getValue())) {
                case USER:
                    commandNames = userCommands;
                    break;
                case GUEST:
                    commandNames = guestCommands;
                    break;
                default:
                    commandNames = emptySet;
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
