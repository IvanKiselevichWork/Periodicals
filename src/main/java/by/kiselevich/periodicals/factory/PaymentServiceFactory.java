package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.service.payment.PaymentService;
import by.kiselevich.periodicals.service.payment.PaymentServiceImpl;

public class PaymentServiceFactory {

    private PaymentService paymentService;

    private PaymentServiceFactory(){
        paymentService = new PaymentServiceImpl();
    }

    private static class PaymentServiceFactoryHolder {
        private static final PaymentServiceFactory INSTANCE = new PaymentServiceFactory();
    }

    public static PaymentServiceFactory getInstance() {
        return PaymentServiceFactoryHolder.INSTANCE;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}
