package test;

import pay.PaymentProcessor;
import pay.GPayAdapter;
import pay.PaytmAdapter;

public class AdapterPatternTest {
    public static void main(String[] args) {

        PaymentProcessor gPayProcessor = new GPayAdapter();
        gPayProcessor.processPayment(600.0);

        System.out.println();

        PaymentProcessor paytmProcessor = new PaytmAdapter();
        paytmProcessor.processPayment(4000.0);
    }
}

