package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowEditionsCommand implements Command {

    private final EditionService editionService;
    private final EditionThemeService editionThemeService;
    private final EditionTypeService editionTypeService;
    private final LongListUtil<Edition> longListUtil;

    public ShowEditionsCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        longListUtil = new LongListUtil<>();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), DashboardPageOptionCommand.EDITIONS);
        try {
            List<Edition> editionList = editionService.getAllEditions();
            List<EditionTheme> editionThemeList = editionThemeService.getAllThemes();
            List<EditionType> editionTypeList = editionTypeService.getAllEditionsTypes();

            editionList.sort(Comparator.comparing(Edition::getId).reversed());
            editionThemeList.sort(Comparator.comparing(EditionTheme::getId));
            editionTypeList.sort(Comparator.comparing(EditionType::getId));
            editionList = longListUtil.getSubListByPageFromRequest(req, editionList);

            req.setAttribute(Attribute.EDITIONS.getValue(), editionList);
            req.setAttribute(Attribute.EDITIONS_THEMES.getValue(), editionThemeList);
            req.setAttribute(Attribute.EDITIONS_TYPES.getValue(), editionTypeList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.EDITIONS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
