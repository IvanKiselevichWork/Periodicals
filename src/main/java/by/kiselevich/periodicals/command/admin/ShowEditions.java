package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Theme;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.EntityMapsFactory;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.theme.ThemeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowEditions implements Command {

    private EditionService editionService;
    private ThemeService themeService;

    public ShowEditions() {
        editionService = ServiceFactory.getInstance().getEditionService();
        themeService = ServiceFactory.getInstance().getThemeService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), AdminPageOption.EDITIONS);
        try {
            List<Edition> editionList = editionService.getAllEditions();
            List<Theme> themeList = themeService.getAllThemes();

            Map<Edition, Theme> editionThemeMap = EntityMapsFactory.getInstance()
                    .getEditionAndThemeMap(editionList, themeList);

            req.setAttribute(Attribute.EDITION_AND_THEME_MAP.getValue(), editionThemeMap);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.EDITION_AND_THEME_MAP.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
