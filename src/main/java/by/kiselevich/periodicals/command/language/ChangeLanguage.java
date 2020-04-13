package by.kiselevich.periodicals.command.language;

import by.kiselevich.periodicals.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangeLanguage implements Command {

    private static final Set<String> LANGUAGES = new HashSet<>(Arrays.stream(Language.values()).map(Language::getValue).collect(Collectors.toList()));

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        String targetLanguage = req.getParameter(JspParameter.TARGET_LANGUAGE.getValue());
        if (targetLanguage != null && LANGUAGES.contains(targetLanguage)) {
            req.getSession().setAttribute(Attribute.LANGUAGE.getValue(), targetLanguage);
            UserType type = (UserType) req.getSession().getAttribute(Attribute.USER_TYPE.getValue());
            return type == UserType.ADMIN ? Page.ADMIN_PAGE : Page.HOME_PAGE;
        }
        return Page.WRONG_REQUEST;
    }
}
