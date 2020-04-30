package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class UpdateEditionCommand extends AbstractEditionCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateEditionCommand.class);

    private final EditionService editionService;

    public UpdateEditionCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Edition edition = getEditionFromRequest(req);
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            edition.setId(id);
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
