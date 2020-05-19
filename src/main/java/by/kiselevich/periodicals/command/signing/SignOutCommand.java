package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOutCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(SignOutCommand.class);

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        try {
            resp.sendRedirect(Page.HOME_PAGE.getPath());
        } catch (IOException e) {
            LOG.error(e);
        }
        return Page.EMPTY_PAGE;
    }
}
