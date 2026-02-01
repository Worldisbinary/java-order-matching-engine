package engine;

import engine.core.OrderBook;
import engine.model.*;

public class Main {

    public static void main(String[] args) {

        OrderBook orderBook = new OrderBook();

        System.out.println("=== STARTING ORDER MATCHING ENGINE ===");

        orderBook.placeOrder(new Order(Side.BUY, 100, 10, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.BUY, 101, 5, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.SELL, 105, 7, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.SELL, 103, 8, OrderType.LIMIT));

        System.out.println("\n--- Sending MARKET BUY ---");
        orderBook.placeOrder(new Order(Side.BUY, 0, 12, OrderType.MARKET));

        orderBook.printOrderBook();
        orderBook.printTrades();

        System.out.println("\nBest Bid: " + orderBook.getBestBid());
        System.out.println("Best Ask: " + orderBook.getBestAsk());
    }
}
