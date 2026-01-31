public class Trade {
    int buyOrderId;
    int sellOrderId;
    double price;
    int quantity;

    public Trade(int buyId, int sellId, double price, int qty) {
        this.buyOrderId = buyId;
        this.sellOrderId = sellId;
        this.price = price;
        this.quantity = qty;
    }
}
