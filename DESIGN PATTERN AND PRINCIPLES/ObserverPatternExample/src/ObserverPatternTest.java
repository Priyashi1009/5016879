
public class ObserverPatternTest {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        MobileApp mobileApp = new MobileApp("MobileApp");
        WebApp webApp = new WebApp("WebApp");

        stockMarket.register(mobileApp);
        stockMarket.register(webApp);

        stockMarket.setStockPrice(300.0);

        System.out.println();

        stockMarket.deregister(mobileApp);

        stockMarket.setStockPrice(900.0);
    }
}
