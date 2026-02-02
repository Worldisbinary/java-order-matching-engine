package engine;

import engine.book.OrderBook;
import engine.model.*;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        OrderBook orderBook = new OrderBook();
        Random random = new Random();

        System.out.println("=== STARTING MARKET SIMULATION ===");

        // Add some initial liquidity (like market makers)
        orderBook.placeOrder(new Order(Side.SELL, 105, 10, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.SELL, 106, 15, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.BUY, 99, 12, OrderType.LIMIT));
        orderBook.placeOrder(new Order(Side.BUY, 98, 8, OrderType.LIMIT));

        orderBook.printOrderBookDepth();

        // Simulate live order flow
        for (int i = 0; i < 10; i++) {

            Side side = random.nextBoolean() ? Side.BUY : Side.SELL;
            OrderType type = random.nextInt(4) == 0 ? OrderType.MARKET : OrderType.LIMIT;

            double price = side == Side.BUY
                    ? 100 + random.nextInt(5)
                    : 100 - random.nextInt(5);

            int quantity = 1 + random.nextInt(10);

            Order order = new Order(side, price, quantity, type);

            orderBook.placeOrder(order);

            // Randomly cancel an order
            if (i == 5) {
                System.out.println("--- Cancelling Order ID 2 ---");
                orderBook.cancelOrder(2);
            }

            if (i % 3 == 0) {
                orderBook.printOrderBookDepth();
            }

            Thread.sleep(500);
        }

        System.out.println("=== FINAL TRADES EXECUTED ===");
        orderBook.getTrades().forEach(System.out::println);

        orderBook.printOrderBookDepth();

        System.out.println("=== SIMULATION COMPLETE ===");
    }
}
