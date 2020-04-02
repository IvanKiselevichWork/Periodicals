package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.entity.Theme;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.EntityMapsFactory;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.theme.ThemeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowEditions implements Command {

    private EditionService editionService;
    private ThemeService themeService;
    private EditionTypeService editionTypeService;

    public ShowEditions() {
        editionService = ServiceFactory.getInstance().getEditionService();
        themeService = ServiceFactory.getInstance().getThemeService();
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), AdminPageOption.EDITIONS);
        try {
            List<Edition> editionList = editionService.getAllEditions();
            List<Theme> themeList = themeService.getAllThemes();
            List<EditionType> editionTypeList = editionTypeService.getAllEditionsTypes();

            Map<Edition, Map.Entry<Theme, EditionType>> editionThemeTypeMap = EntityMapsFactory.getInstance()
                    .getEditionAndThemeAndTypeMap(editionList, themeList, editionTypeList);


            req.setAttribute(Attribute.EDITION_MAP.getValue(), editionThemeTypeMap);
            req.setAttribute(Attribute.EDITIONS_THEMES.getValue(), themeList);
            req.setAttribute(Attribute.EDITIONS_TYPES.getValue(), editionTypeList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.EDITION_MAP.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
