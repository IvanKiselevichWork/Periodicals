package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.PaymentServiceFactory;
import by.kiselevich.periodicals.service.payment.PaymentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.kiselevich.periodicals.util.HttpUtil.getLocalizedMessageFromResources;

public class ShowPayments implements Command {

    private PaymentService paymentService;

    public ShowPayments() {
        paymentService = PaymentServiceFactory.getInstance().getPaymentService();
    }

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), AdminPageOption.PAYMENTS);
        try {
            List<Payment> paymentList = paymentService.getAllPayments();
            req.setAttribute(Attribute.PAYMENTS.getValue(), paymentList);
        } catch (ServiceException e) {
            String message = getLocalizedMessageFromResources(req.getSession(), e.getMessage());
            req.setAttribute(Attribute.PAYMENTS.getValue(), null);
            req.setAttribute(Attribute.MESSAGE.getValue(), message);
        }
        return Page.ADMIN_PAGE;
    }
}
