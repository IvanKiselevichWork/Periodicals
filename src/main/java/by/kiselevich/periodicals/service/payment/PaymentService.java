package by.kiselevich.periodicals.service.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments() throws ServiceException;

    List<Payment> getPaymentsByLogin(String userLogin) throws ServiceException;
}
