package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.entity.PaymentType;

/**
 * Class with pre-build {@link PaymentType} objects: refill and payment
 */
public class PaymentTypeFactory {
    private static final int REFILL_ID = 1;
    private static final int PAYMENT_ID = 2;
    private static final String REFILL_TYPE = "refill";
    private static final String PAYMENT_TYPE = "payment";

    private PaymentTypeFactory() {}

    private static final PaymentType REFILL = new PaymentType.PaymentTypeBuilder()
            .id(REFILL_ID)
            .type(REFILL_TYPE)
            .build();

    private static final PaymentType PAYMENT = new PaymentType.PaymentTypeBuilder()
            .id(PAYMENT_ID)
            .type(PAYMENT_TYPE)
            .build();

    public static PaymentType getRefill() {
        return REFILL;
    }

    public static PaymentType getPayment() {
        return PAYMENT;
    }
}
