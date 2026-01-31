public class Order {
    public enum Type { BUY, SELL }

    private static int counter = 0;
    int id;
    Type type;
    double price;
    int quantity;
    long timestamp;

    public Order(Type type, double price, int quantity) {
        this.id = ++counter;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = System.nanoTime();
    }
}
