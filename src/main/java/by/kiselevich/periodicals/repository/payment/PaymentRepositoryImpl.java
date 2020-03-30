package by.kiselevich.periodicals.repository.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepository {

    private static final Logger LOG = LogManager.getLogger(PaymentRepositoryImpl.class);

    @Override
    public void add(Payment payment) throws RepositoryException {
        //todo
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(Payment payment) throws RepositoryException {
        //todo
    }

    @Override
    public List<Payment> query(Specification<Payment> specification) throws RepositoryException {
        return specification.query();
    }
}
