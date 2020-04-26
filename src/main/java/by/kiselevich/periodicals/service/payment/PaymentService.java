package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface PaymentService {

    /**
     * Returns all payments from data source
     * @return {@link List} with all {@link Payment}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Payment> getAllPayments() throws ServiceException;

    /**
     * Returns all payments by {@link User} {@code login} from data source
     * @param userLogin {@link User} {@code login}
     * @return {@link List} with {@link Payment}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Payment> getPaymentsByLogin(String userLogin) throws ServiceException;
}
