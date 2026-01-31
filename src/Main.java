public class Main {
    public static void main(String[] args) {
        OrderBook ob = new OrderBook();

        ob.placeOrder(new Order(Order.Type.BUY, 100, 10));
        ob.placeOrder(new Order(Order.Type.SELL, 99, 5));
        ob.placeOrder(new Order(Order.Type.SELL, 100, 10));

        ob.printOrderBook();
    }
}
