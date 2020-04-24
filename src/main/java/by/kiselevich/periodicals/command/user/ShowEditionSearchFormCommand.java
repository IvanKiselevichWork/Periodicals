package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowEditionSearchFormCommand implements Command {

    private final EditionTypeService editionTypeService;
    private final EditionThemeService editionThemeService;

    public ShowEditionSearchFormCommand() {
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.EDITIONS);
        try {
            List<EditionTheme> editionThemeList = editionThemeService.getAllThemes();
            List<EditionType> editionTypeList = editionTypeService.getAllEditionsTypes();
            req.setAttribute(Attribute.EDITIONS_THEMES.getValue(), editionThemeList);
            req.setAttribute(Attribute.EDITIONS_TYPES.getValue(), editionTypeList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.USERS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}