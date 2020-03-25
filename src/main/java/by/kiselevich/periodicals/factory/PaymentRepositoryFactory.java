package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.repository.payment.PaymentRepository;
import by.kiselevich.periodicals.repository.payment.PaymentRepositoryImpl;

public class PaymentRepositoryFactory {

    private PaymentRepository paymentRepository;

    private PaymentRepositoryFactory(){
        paymentRepository = new PaymentRepositoryImpl();
    }

    private static class PaymentRepositoryFactoryHolder {
        private static final PaymentRepositoryFactory INSTANCE = new PaymentRepositoryFactory();
    }

    public static PaymentRepositoryFactory getInstance() {
        return PaymentRepositoryFactoryHolder.INSTANCE;
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }
}
