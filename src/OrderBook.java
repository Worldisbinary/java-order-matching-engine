import java.util.*;

public class OrderBook {

    PriorityQueue<Order> buyOrders = new PriorityQueue<>(
        (a, b) -> a.price != b.price ? Double.compare(b.price, a.price)
                                     : Long.compare(a.timestamp, b.timestamp)
    );

    PriorityQueue<Order> sellOrders = new PriorityQueue<>(
        (a, b) -> a.price != b.price ? Double.compare(a.price, b.price)
                                     : Long.compare(a.timestamp, b.timestamp)
    );

    List<Trade> trades = new ArrayList<>();

    public void placeOrder(Order order) {
        if (order.type == Order.Type.BUY) matchBuy(order);
        else matchSell(order);
    }

    private void matchBuy(Order buy) {
        while (buy.quantity > 0 && !sellOrders.isEmpty() &&
               sellOrders.peek().price <= buy.price) {

            Order sell = sellOrders.poll();
            int tradedQty = Math.min(buy.quantity, sell.quantity);

            trades.add(new Trade(buy.id, sell.id, sell.price, tradedQty));

            buy.quantity -= tradedQty;
            sell.quantity -= tradedQty;

            if (sell.quantity > 0) sellOrders.add(sell);
        }
        if (buy.quantity > 0) buyOrders.add(buy);
    }

    private void matchSell(Order sell) {
        while (sell.quantity > 0 && !buyOrders.isEmpty() &&
               buyOrders.peek().price >= sell.price) {

            Order buy = buyOrders.poll();
            int tradedQty = Math.min(sell.quantity, buy.quantity);

            trades.add(new Trade(buy.id, sell.id, buy.price, tradedQty));

            sell.quantity -= tradedQty;
            buy.quantity -= tradedQty;

            if (buy.quantity > 0) buyOrders.add(buy);
        }
        if (sell.quantity > 0) sellOrders.add(sell);
    }

    public void printOrderBook() {
        System.out.println("----- FINAL ORDER BOOK -----");
        System.out.println("Pending BUY Orders: " + buyOrders.size());
        System.out.println("Pending SELL Orders: " + sellOrders.size());
        System.out.println("Total Trades Executed: " + trades.size());
    }

}
