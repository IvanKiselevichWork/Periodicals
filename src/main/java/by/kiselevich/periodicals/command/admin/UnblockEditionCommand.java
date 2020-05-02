package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class UnblockEditionCommand implements Command {

    private final EditionService editionService;

    public UnblockEditionCommand() {
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            editionService.unblockEdition(id);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            HttpUtil.writeMessageToResponse(resp, message);
        }
        return Page.EMPTY_PAGE;
    }
}
