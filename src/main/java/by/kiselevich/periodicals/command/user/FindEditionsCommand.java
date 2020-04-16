package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.EntityMapsFactory;
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

    private static final String EDITION_NAME_FORMAT = "%%%s%%";

    private EditionService editionService;
    private EditionTypeService editionTypeService;
    private EditionThemeService editionThemeService;
    private SubscriptionService subscriptionService;

    public FindEditionsCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
        subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.EDITIONS);
        try {
            String name = req.getParameter(JspParameter.NAME.getValue());
            String typeIdString = req.getParameter(JspParameter.TYPE_ID.getValue());
            String themeIdString = req.getParameter(JspParameter.THEME_ID.getValue());

            List<Edition> editionList;
            String sourceName = name;
            name = String.format(EDITION_NAME_FORMAT, name);

            if (StringUtils.isEmpty(typeIdString) && StringUtils.isEmpty(themeIdString)) {
                editionList = editionService.getEditionsByName(name);
            } else {
                if (StringUtils.isEmpty(typeIdString)) {
                    int themeId = Integer.parseInt(themeIdString);
                    editionList = editionService.getEditionsByNameAndThemeId(name, themeId);
                } else if (StringUtils.isEmpty(themeIdString)) {
                    int typeId = Integer.parseInt(typeIdString);
                    editionList = editionService.getEditionsByNameAndTypeId(name, typeId);
                } else {
                    int typeId = Integer.parseInt(typeIdString);
                    int themeId = Integer.parseInt(themeIdString);
                    editionList = editionService.getEditionsByNameAndTypeIdAndThemeId(name, typeId, themeId);
                }
            }

            List<EditionType> editionTypeList = editionTypeService.getAllEditionsTypes();
            List<EditionTheme> editionThemeList = editionThemeService.getAllThemes();

            String userLogin = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            List<Subscription> subscriptionList = subscriptionService.getAllSubscriptionsByUserLogin(userLogin);

            req.setAttribute(Attribute.EDITION_NAME_VALUE.getValue(), sourceName);
            req.setAttribute(Attribute.EDITION_TYPE_ID_VALUE.getValue(), typeIdString);
            req.setAttribute(Attribute.EDITION_THEME_ID_VALUE.getValue(), themeIdString);
            req.setAttribute(Attribute.EDITIONS.getValue(), EntityMapsFactory.getEditionAndIfUserSubscribedMap(editionList, subscriptionList));
            req.setAttribute(Attribute.EDITIONS_TYPES.getValue(), editionTypeList);
            req.setAttribute(Attribute.EDITIONS_THEMES.getValue(), editionThemeList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.USERS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}