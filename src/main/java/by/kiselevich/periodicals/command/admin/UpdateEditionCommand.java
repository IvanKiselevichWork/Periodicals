package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class UpdateEditionCommand extends AbstractEditionCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateEditionCommand.class);

    private EditionService editionService;
    private EditionTypeService editionTypeService;
    private EditionThemeService editionThemeService;

    public UpdateEditionCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Edition edition = getEditionFromRequest(req);

            List<EditionType> editionTypeList = editionTypeService.getEditionTypeById(edition.getEditionType().getId());
            if (editionTypeList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.INVALID_TYPE.getKey());
            }
            List<EditionTheme> editionThemeList = editionThemeService.getThemeById(edition.getEditionTheme().getId());
            if (editionThemeList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.INVALID_THEME.getKey());
            }
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));

            edition.setId(id);
            edition.setEditionType(editionTypeList.get(0));
            edition.setEditionTheme(editionThemeList.get(0));

            editionService.update(edition);
        } catch (NumberFormatException e) {
            LOG.info(e);
            String message = getLocalizedMessageFromResources(req.getSession(), ResourceBundleMessages.INVALID_DATA_FORMAT.getKey());
            writeMessageToResponse(resp, message);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
