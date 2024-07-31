package pay;


public class PaytmAdapter implements PaymentProcessor {
    private PaytmGateway paytmGateway;

    public PaytmAdapter() {
        this.paytmGateway = new PaytmGateway();
    }

    @Override
    public void processPayment(double amount) {
        paytmGateway.payUsingPaytm(amount);
    }
}

