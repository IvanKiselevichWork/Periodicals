package by.kiselevich.periodicals.command.signing;

import by.kiselevich.periodicals.command.Attribute;
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
        String language = (String) req.getSession().getAttribute(Attribute.LANGUAGE.getValue());
        req.getSession().invalidate();
        req.getSession().setAttribute(Attribute.LANGUAGE.getValue(), language);
        try {
            resp.sendRedirect(req.getContextPath());
        } catch (IOException e) {
            LOG.error(e);
        }
        return Page.EMPTY_PAGE;
    }
}
