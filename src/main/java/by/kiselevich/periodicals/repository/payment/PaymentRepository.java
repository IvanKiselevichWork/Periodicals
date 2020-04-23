package by.kiselevich.periodicals.repository.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface PaymentRepository extends Repository<Payment> {

    void add(Payment payment) throws RepositoryException;
}
