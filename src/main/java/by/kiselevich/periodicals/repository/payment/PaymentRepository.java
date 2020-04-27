package by.kiselevich.periodicals.repository.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface PaymentRepository extends Repository<Payment> {

    /**
     * Adds {@code Payment} to data source
     * @param payment {@link Payment} to add
     * @throws RepositoryException  if error occurs
     */
    void add(Payment payment) throws RepositoryException;
}
