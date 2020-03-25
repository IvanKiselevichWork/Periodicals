package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.PaymentRepositoryFactory;
import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.specification.payment.FindAllPayments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl() {
        paymentRepository = PaymentRepositoryFactory.getInstance().getPaymentRepository();
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
}
