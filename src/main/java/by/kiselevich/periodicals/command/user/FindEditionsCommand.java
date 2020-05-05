package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.command.admin.LongListUtil;
import by.kiselevich.periodicals.entity.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.EditionToIfUserSubscribedThisEditionMapFactory;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.subscription.SubscriptionService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class FindEditionsCommand implements Command {

    private final EditionService editionService;
    private final EditionTypeService editionTypeService;
    private final EditionThemeService editionThemeService;
    private final SubscriptionService subscriptionService;
    private final LongListUtil<Edition> longListUtil;

    public FindEditionsCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
        longListUtil = new LongListUtil<>();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.EDITIONS);
        try {
            String name = req.getParameter(JspParameter.NAME.getValue());
            String typeIdString = req.getParameter(JspParameter.TYPE_ID.getValue());
            String themeIdString = req.getParameter(JspParameter.THEME_ID.getValue());

            Integer typeId = getIntegerFromString(typeIdString);
            Integer themeId = getIntegerFromString(themeIdString);
            List<Edition> editionList;
            editionList = editionService.getNotBlockedEditionsByNameAndTypeIdAndThemeId(name, typeId, themeId);
            editionList = longListUtil.getSubListByPageFromRequest(req, editionList);

            List<EditionType> editionTypeList = editionTypeService.getAllEditionsTypes();
            List<EditionTheme> editionThemeList = editionThemeService.getAllThemes();

            String userLogin = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptionsByUserLogin(userLogin);

            req.setAttribute(Attribute.EDITION_NAME_VALUE.getValue(), name);
            req.setAttribute(Attribute.EDITION_TYPE_ID_VALUE.getValue(), typeIdString);
            req.setAttribute(Attribute.EDITION_THEME_ID_VALUE.getValue(), themeIdString);
            req.setAttribute(Attribute.EDITIONS.getValue(), EditionToIfUserSubscribedThisEditionMapFactory.getEditionAndIfUserSubscribedMap(editionList, subscriptionList));
            req.setAttribute(Attribute.EDITIONS_TYPES.getValue(), editionTypeList);
            req.setAttribute(Attribute.EDITIONS_THEMES.getValue(), editionThemeList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
            return Page.USER_PAGE;
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
            return Page.WRONG_REQUEST;
        }
    }

    private Integer getIntegerFromString(String string) {
        try {
            if (StringUtils.isBlank(string)) {
                return null;
            }
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
