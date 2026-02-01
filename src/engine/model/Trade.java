package engine.model;

public class Trade {
    private int buyOrderId;
    private int sellOrderId;
    private double price;
    private int quantity;

    public Trade(int buyId, int sellId, double price, int qty) {
        this.buyOrderId = buyId;
        this.sellOrderId = sellId;
        this.price = price;
        this.quantity = qty;
    }

    @Override
    public String toString() {
        return "TRADE: Buy#" + buyOrderId + " Sell#" + sellOrderId +
                " Price=" + price + " Qty=" + quantity;
    }
}
