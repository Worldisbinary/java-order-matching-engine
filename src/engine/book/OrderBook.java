package engine.book;

import engine.model.*;
import engine.marketdata.MarketDataSnapshot;
import java.util.*;

public class OrderBook {

    // BUY orders: highest price first, then oldest timestamp
    private PriorityQueue<Order> buyOrders = new PriorityQueue<>(
            (a, b) -> a.getPrice() != b.getPrice()
                    ? Double.compare(b.getPrice(), a.getPrice())
                    : Long.compare(a.getTimestamp(), b.getTimestamp()));

    // SELL orders: lowest price first, then oldest timestamp
    private PriorityQueue<Order> sellOrders = new PriorityQueue<>(
            (a, b) -> a.getPrice() != b.getPrice()
                    ? Double.compare(a.getPrice(), b.getPrice())
                    : Long.compare(a.getTimestamp(), b.getTimestamp()));

    private List<Trade> trades = new ArrayList<>();
    private double lastTradedPrice = 0;
    private long totalVolume = 0;

    public void placeOrder(Order order) {
        long start = System.nanoTime();

        System.out.println("Placing Order: " + order.getSide() +
                " " + order.getQuantity() + " @ " + order.getPrice());

        if (order.getType() == OrderType.MARKET) {
            matchMarketOrder(order);
        } else {
            matchLimitOrder(order);
        }

        long end = System.nanoTime();
        System.out.println("Matching latency: " + (end - start) + " ns\n");
    }

    private void matchLimitOrder(Order order) {
        if (order.getSide() == Side.BUY) {
            while (order.getQuantity() > 0 && !sellOrders.isEmpty()
                    && sellOrders.peek().getPrice() <= order.getPrice()) {
                executeTrade(order, sellOrders.peek());
            }
            if (order.getQuantity() > 0)
                buyOrders.add(order);
        } else {
            while (order.getQuantity() > 0 && !buyOrders.isEmpty()
                    && buyOrders.peek().getPrice() >= order.getPrice()) {
                executeTrade(buyOrders.peek(), order);
            }
            if (order.getQuantity() > 0)
                sellOrders.add(order);
        }
    }

    private void matchMarketOrder(Order order) {
        if (order.getSide() == Side.BUY) {
            while (order.getQuantity() > 0 && !sellOrders.isEmpty()) {
                executeTrade(order, sellOrders.peek());
            }
        } else {
            while (order.getQuantity() > 0 && !buyOrders.isEmpty()) {
                executeTrade(buyOrders.peek(), order);
            }
        }
    }

    private void executeTrade(Order buy, Order sell) {
        int tradeQty = Math.min(buy.getQuantity(), sell.getQuantity());
        double tradePrice = sell.getPrice();

        buy.reduceQuantity(tradeQty);
        sell.reduceQuantity(tradeQty);

        Trade trade = new Trade(buy.getId(), sell.getId(), tradePrice, tradeQty);
        trades.add(trade);

        lastTradedPrice = tradePrice;
        totalVolume += tradeQty;

        System.out.println("TRADE EXECUTED: " + tradeQty + " @ " + tradePrice);

        publishMarketData(tradePrice, tradeQty);

        if (sell.getQuantity() == 0)
            sellOrders.poll();
        if (buy.getQuantity() == 0)
            buyOrders.poll();
    }

    private void publishMarketData(double lastTradePrice, int lastTradeQty) {
        double bestBid = buyOrders.isEmpty() ? 0 : buyOrders.peek().getPrice();
        int bestBidQty = buyOrders.isEmpty() ? 0 : buyOrders.peek().getQuantity();

        double bestAsk = sellOrders.isEmpty() ? 0 : sellOrders.peek().getPrice();
        int bestAskQty = sellOrders.isEmpty() ? 0 : sellOrders.peek().getQuantity();

        MarketDataSnapshot snapshot = new MarketDataSnapshot(
                bestBid, bestBidQty, bestAsk, bestAskQty,
                lastTradePrice, lastTradeQty);

        snapshot.print();
    }

    public void cancelOrder(int orderId) {
        buyOrders.removeIf(o -> o.getId() == orderId);
        sellOrders.removeIf(o -> o.getId() == orderId);
        System.out.println("Order Cancelled: " + orderId);
    }

    public void printOrderBookDepth() {
        System.out.println("\n===== ORDER BOOK DEPTH =====");

        System.out.println("BIDS:");
        buyOrders.stream()
                .sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice()))
                .limit(5)
                .forEach(o -> System.out.println(o.getPrice() + " x " + o.getQuantity()));

        System.out.println("ASKS:");
        sellOrders.stream()
                .sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                .limit(5)
                .forEach(o -> System.out.println(o.getPrice() + " x " + o.getQuantity()));

        System.out.println("============================\n");
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public double getLastTradedPrice() {
        return lastTradedPrice;
    }

    public long getTotalVolume() {
        return totalVolume;
    }
}
