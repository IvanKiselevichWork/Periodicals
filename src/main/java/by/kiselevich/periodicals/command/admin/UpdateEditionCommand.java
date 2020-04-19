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
import java.math.BigDecimal;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;
import static by.kiselevich.periodicals.util.HttpUtil.writeMessageToResponse;

public class UpdateEditionCommand implements Command {

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
            int id = Integer.parseInt(req.getParameter(JspParameter.ID.getValue()));
            String name = req.getParameter(JspParameter.NAME.getValue());
            int typeId = Integer.parseInt(req.getParameter(JspParameter.TYPE_ID.getValue()));
            int themeId = Integer.parseInt(req.getParameter(JspParameter.THEME_ID.getValue()));
            int periodicityPerYear = Integer.parseInt(req.getParameter(JspParameter.PERIODICITY_PER_YEAR.getValue()));
            int minimumSubscriptionPeriodInMonths = Integer.parseInt(req.getParameter(JspParameter.MINIMUM_SUBSCRIPTION_PERIOD.getValue()));
            BigDecimal priceForMinimumSubscriptionPeriod = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getValue())));

            List<EditionType> editionTypeList = editionTypeService.getEditionTypeById(typeId);
            if (editionTypeList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.INVALID_TYPE.getKey());
            }
            List<EditionTheme> editionThemeList = editionThemeService.getThemeById(themeId);
            if (editionThemeList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.INVALID_THEME.getKey());
            }
            Edition edition = new Edition.EditionBuilder()
                    .id(id)
                    .name(name)
                    .editionType(editionTypeList.get(0))
                    .editionTheme(editionThemeList.get(0))
                    .periodicityPerYear(periodicityPerYear)
                    .minimumSubscriptionPeriodInMonths(minimumSubscriptionPeriodInMonths)
                    .priceForMinimumSubscriptionPeriod(priceForMinimumSubscriptionPeriod)
                    .build();
            editionService.update(edition);
            return Page.EMPTY_PAGE;
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
