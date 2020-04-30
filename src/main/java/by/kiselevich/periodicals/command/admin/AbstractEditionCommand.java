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
import java.util.List;

public class AbstractEditionCommand {

    protected final EditionTypeService editionTypeService;
    protected final EditionThemeService editionThemeService;

    public AbstractEditionCommand() {
        editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
        editionThemeService = ServiceFactory.getInstance().getEditionThemeService();
    }

    protected Edition getEditionFromRequest(HttpServletRequest req) throws ServiceException {
        String name = req.getParameter(JspParameter.NAME.getValue());
        int typeId = Integer.parseInt(req.getParameter(JspParameter.TYPE_ID.getValue()));
        List<EditionType> editionTypeList = editionTypeService.getEditionTypeById(typeId);
        if (editionTypeList.isEmpty()) {
            throw new ServiceException(ResourceBundleMessages.INVALID_TYPE.getKey());
        }
        int themeId = Integer.parseInt(req.getParameter(JspParameter.THEME_ID.getValue()));
        List<EditionTheme> editionThemeList = editionThemeService.getThemeById(themeId);
        if (editionThemeList.isEmpty()) {
            throw new ServiceException(ResourceBundleMessages.INVALID_THEME.getKey());
        }
        int periodicityPerYear = Integer.parseInt(req.getParameter(JspParameter.PERIODICITY_PER_YEAR.getValue()));
        int minimumSubscriptionPeriodInMonths = Integer.parseInt(req.getParameter(JspParameter.MINIMUM_SUBSCRIPTION_PERIOD.getValue()));
        BigDecimal priceForMinimumSubscriptionPeriod = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getValue())));

        return new Edition.EditionBuilder()
                .name(name)
                .editionType(editionTypeList.get(0))
                .editionTheme(editionThemeList.get(0))
                .periodicityPerYear(periodicityPerYear)
                .minimumSubscriptionPeriodInMonths(minimumSubscriptionPeriodInMonths)
                .priceForMinimumSubscriptionPeriod(priceForMinimumSubscriptionPeriod)
                .build();
    }
}
