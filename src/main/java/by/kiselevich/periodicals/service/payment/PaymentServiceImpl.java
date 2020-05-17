package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.payment.PaymentDao;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of {@link PaymentService}
 */
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);

    private final PaymentDao paymentDao;

    public PaymentServiceImpl() {
        paymentDao = DaoFactory.getInstance().getPaymentDao();
    }

    @Override
    public List<Payment> getAllPayments() throws ServiceException {
        try {
            return paymentDao.getAllPayments();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
