package by.kiselevich.periodicals.command.language;

import by.kiselevich.periodicals.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangeLanguageCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);
    private static final Set<String> LANGUAGES = new HashSet<>(Arrays.stream(Language.values()).map(Language::getValue).collect(Collectors.toList()));
    private static final String REFERER_HEADER = "referer";

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        String targetLanguage = req.getParameter(JspParameter.TARGET_LANGUAGE.getValue());
        if (targetLanguage != null && LANGUAGES.contains(targetLanguage)) {
            req.getSession().setAttribute(Attribute.LANGUAGE.getValue(), targetLanguage);
            String prev = req.getHeader(REFERER_HEADER);
            try {
                resp.sendRedirect(prev);
                return Page.EMPTY_PAGE;
            } catch (IOException e) {
                LOG.warn(e);
            }
        }
        return Page.WRONG_REQUEST;
    }
}
