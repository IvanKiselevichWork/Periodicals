package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.specification.payment.FindAllPayments;
import by.kiselevich.periodicals.specification.payment.FindPaymentsByLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of {@link PaymentService}
 */
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl() {
        paymentRepository = RepositoryFactory.getInstance().getPaymentRepository();
    }

    @Override
    public List<Payment> getAllPayments() throws ServiceException {
        try {
            return paymentRepository.query(new FindAllPayments());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Payment> getPaymentsByLogin(String userLogin) throws ServiceException {
        try {
            return paymentRepository.query(new FindPaymentsByLogin(userLogin));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
