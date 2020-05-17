package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

/**
 * Service to work with {@link Payment}
 */
public interface PaymentService {

    /**
     * Returns all payments from data source
     * @return {@link List} with all {@link Payment}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Payment> getAllPayments() throws ServiceException;
}
