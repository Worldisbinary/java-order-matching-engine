package engine.model;

public class Order {
    private static int counter = 1;

    private int id;
    private Side side;
    private double price;
    private int quantity;
    private OrderType type;

    public Order(Side side, double price, int quantity, OrderType type) {
        this.id = counter++;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
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

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public OrderType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", side=" + side + ", price=" + price +
                ", qty=" + quantity + ", type=" + type + "}";
    }
}
