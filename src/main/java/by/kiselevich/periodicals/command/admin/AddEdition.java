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
import java.math.BigDecimal;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class AddEdition implements Command {

    private static final Logger LOG = LogManager.getLogger(AddEdition.class);

    private EditionService editionService;

    public AddEdition() {
        editionService = ServiceFactory.getInstance().getEditionService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String name = req.getParameter(JspParameter.NAME.getValue());
            int type = Integer.parseInt(req.getParameter(JspParameter.TYPE_ID.getValue()));
            int themeId = Integer.parseInt(req.getParameter(JspParameter.THEME_ID.getValue()));
            int periodicityPerYear = Integer.parseInt(req.getParameter(JspParameter.PERIODICITY_PER_YEAR.getValue()));
            int minimumSubscriptionPeriodInMonths = Integer.parseInt(req.getParameter(JspParameter.MINIMUM_SUBSCRIPTION_PERIOD.getValue()));
            BigDecimal priceForMinimumSubscriptionPeriod = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getValue())));

            //todo edition builder
            Edition edition = new Edition();
            edition.setName(name);
            edition.setTypeId(type);
            edition.setThemeId(themeId);
            edition.setPeriodicityPerYear(periodicityPerYear);
            edition.setMinimumSubscriptionPeriodInMonths(minimumSubscriptionPeriodInMonths);
            edition.setPriceForMinimumSubscriptionPeriod(priceForMinimumSubscriptionPeriod);
            editionService.add(edition);
            return CommandProvider.getInstance().getCommand(CommandName.SHOW_EDITIONS).execute(req,resp);
        } catch (NumberFormatException e) {
            LOG.info(e);
            String message = getLocalizedMessageFromResources(req.getSession(), ResourceBundleMessages.INVALID_DATA_FORMAT.getKey());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            writeMessageToResponse(resp, message);
            return Page.EMPTY_PAGE;
        }
    }
}
