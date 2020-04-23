package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AbstractEditionCommand {

    protected Edition getEditionFromRequest(HttpServletRequest req) {
        String name = req.getParameter(JspParameter.NAME.getValue());
        int typeId = Integer.parseInt(req.getParameter(JspParameter.TYPE_ID.getValue()));
        int themeId = Integer.parseInt(req.getParameter(JspParameter.THEME_ID.getValue()));
        int periodicityPerYear = Integer.parseInt(req.getParameter(JspParameter.PERIODICITY_PER_YEAR.getValue()));
        int minimumSubscriptionPeriodInMonths = Integer.parseInt(req.getParameter(JspParameter.MINIMUM_SUBSCRIPTION_PERIOD.getValue()));
        BigDecimal priceForMinimumSubscriptionPeriod = BigDecimal.valueOf(Double.parseDouble(req.getParameter(JspParameter.PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getValue())));

        return new Edition.EditionBuilder()
                .name(name)
                .editionType(new EditionType.EditionTypeBuilder()
                        .id(typeId)
                        .build())
                .editionTheme(new EditionTheme.EditionThemeBuilder()
                        .id(themeId)
                        .build())
                .periodicityPerYear(periodicityPerYear)
                .minimumSubscriptionPeriodInMonths(minimumSubscriptionPeriodInMonths)
                .priceForMinimumSubscriptionPeriod(priceForMinimumSubscriptionPeriod)
                .build();
    }
}
