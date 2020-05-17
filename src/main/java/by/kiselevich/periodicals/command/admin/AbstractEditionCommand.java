package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.editiontheme.EditionThemeService;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public abstract class AbstractEditionCommand {

    protected final EditionTypeService editionTypeService;
    protected final EditionThemeService editionThemeService;

    public AbstractEditionCommand() {
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
    }

    protected Edition getEditionFromRequest(HttpServletRequest req) throws ServiceException {
        String name = req.getParameter(JspParameter.NAME.getValue());
        EditionType editionType = getEditionTypeFromRequest(req);
        EditionTheme editionTheme = getEditionThemeFromRequest(req);
        int periodicityPerYear = Integer.parseInt(req.getParameter(JspParameter.PERIODICITY_PER_YEAR.getValue()));
        int minimumSubscriptionPeriodInMonths = Integer.parseInt(req.getParameter(JspParameter.MINIMUM_SUBSCRIPTION_PERIOD.getValue()));
        BigDecimal priceForMinimumSubscriptionPeriod = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getValue())));
        return new Edition.EditionBuilder()
                .name(name)
                .editionType(editionType)
                .editionTheme(editionTheme)
                .periodicityPerYear(periodicityPerYear)
                .minimumSubscriptionPeriodInMonths(minimumSubscriptionPeriodInMonths)
                .priceForMinimumSubscriptionPeriod(priceForMinimumSubscriptionPeriod)
                .build();
    }

    private EditionTheme getEditionThemeFromRequest(HttpServletRequest req) throws ServiceException {
        int themeId = Integer.parseInt(req.getParameter(JspParameter.THEME_ID.getValue()));
        EditionTheme editionTheme = editionThemeService.getThemeById(themeId);
        if (editionTheme == null) {
            throw new ServiceException(ResourceBundleMessages.INVALID_THEME.getKey());
        }
        return editionTheme;
    }

    private EditionType getEditionTypeFromRequest(HttpServletRequest req) throws ServiceException {
        int typeId = Integer.parseInt(req.getParameter(JspParameter.TYPE_ID.getValue()));
        EditionType editionType = editionTypeService.getEditionTypeById(typeId);
        if (editionType == null) {
            throw new ServiceException(ResourceBundleMessages.INVALID_TYPE.getKey());
        }
        return editionType;
    }
}
