package pay;


public class GPayAdapter implements PaymentProcessor {
    private GPayGateway gPayGateway;

    public GPayAdapter() {
        this.gPayGateway = new GPayGateway();
    }

    @Override
    public void processPayment(double amount) {
        gPayGateway.payUsingGPay(amount);
    }
}

