package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.payment.PaymentDao;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link PaymentService}
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);

    private PaymentDao paymentDao;

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    @Transactional
    public List<Payment> getAllPayments() throws ServiceException {
        try {
            return paymentDao.getAllPayments();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
