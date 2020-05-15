package by.kiselevich.periodicals.dao.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface PaymentDao {

    /**
     * Adds {@link Payment} to data source
     * @param payment {@link Payment} to add
     * @throws DaoException if error occurs
     */
    void add(Payment payment) throws DaoException;

    /**
     * Get all {@link Payment} from data source
     * @return {@code List} of {@link Payment}
     * @throws DaoException if error occurs
     */
    List<Payment> getAllPayments() throws DaoException;
}
