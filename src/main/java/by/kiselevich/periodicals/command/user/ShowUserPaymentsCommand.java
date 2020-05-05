package by.kiselevich.periodicals.command.user;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.admin.DashboardPageOptionCommand;
import by.kiselevich.periodicals.command.admin.LongListUtil;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.payment.PaymentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowUserPaymentsCommand implements Command {

    private final PaymentService paymentService;
    private final LongListUtil<Payment> longListUtil;

    public ShowUserPaymentsCommand() {
        paymentService = ServiceFactory.getInstance().getPaymentService();
        longListUtil = new LongListUtil<>();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.USER_PAGE_OPTION.getValue(), DashboardPageOptionCommand.PAYMENTS);
        try {
            String login = (String) req.getSession().getAttribute(Attribute.LOGIN.getValue());
            List<Payment> paymentList = paymentService.getPaymentsByLogin(login);
            paymentList.sort(Comparator.comparing(Payment::getDate).reversed());
            paymentList = longListUtil.getSubListByPageFromRequest(req, paymentList);
            req.setAttribute(Attribute.PAYMENTS.getValue(), paymentList);
            req.setAttribute(Attribute.MESSAGE.getValue(), null);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources((String)req.getSession().getAttribute(Attribute.LANGUAGE.getValue()), e.getMessage());
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.USER_PAGE;
    }
}