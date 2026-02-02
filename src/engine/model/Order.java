package engine.model;

public class Order {

    private static int counter = 1;

    private final int id;
    private final Side side;
    private final double price;
    private int quantity;
    private final OrderType type;
    private final long timestamp;

    public Order(Side side, double price, int quantity, OrderType type) {
        this.id = counter++;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = System.nanoTime(); // used for price-time priority
    }

    public int getId() {
        return id;
    }

    public Side getSide() {
        return side;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderType getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void reduceQuantity(int qty) {
        this.quantity -= qty;
    }

    @Override
    public String toString() {
        return "Order{id=" + id +
                ", side=" + side +
                ", price=" + price +
                ", qty=" + quantity +
                ", type=" + type + "}";
    }
}
