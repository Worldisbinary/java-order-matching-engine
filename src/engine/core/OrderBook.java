package engine.core;

import engine.model.*;
import java.util.*;

public class OrderBook {

    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;
    private List<Trade> trades = new ArrayList<>();

    public OrderBook() {
        buyOrders = new PriorityQueue<>(
                (a, b) -> Double.compare(b.getPrice(), a.getPrice()));

        sellOrders = new PriorityQueue<>(
                Comparator.comparingDouble(Order::getPrice));
    }

    public void placeOrder(Order order) {
        System.out.println("Placing: " + order);

        if (order.getType() == OrderType.MARKET) {
            executeMarketOrder(order);
        } else {
            executeLimitOrder(order);
        }
    }

    private void executeLimitOrder(Order order) {
        if (order.getSide() == Side.BUY) {
            matchBuy(order);
            if (order.getQuantity() > 0)
                buyOrders.add(order);
        } else {
            matchSell(order);
            if (order.getQuantity() > 0)
                sellOrders.add(order);
        }
    }

    private void executeMarketOrder(Order order) {
        if (order.getSide() == Side.BUY) {
            matchBuy(order);
        } else {
            matchSell(order);
        }
    }

    private void matchBuy(Order buy) {
        while (buy.getQuantity() > 0 && !sellOrders.isEmpty()
                && (buy.getType() == OrderType.MARKET ||
                        sellOrders.peek().getPrice() <= buy.getPrice())) {

            Order sell = sellOrders.poll();
            int tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());

            trades.add(new Trade(buy.getId(), sell.getId(), sell.getPrice(), tradedQty));

            buy.setQuantity(buy.getQuantity() - tradedQty);
            sell.setQuantity(sell.getQuantity() - tradedQty);

            if (sell.getQuantity() > 0)
                sellOrders.add(sell);
        }
    }

    private void matchSell(Order sell) {
        while (sell.getQuantity() > 0 && !buyOrders.isEmpty()
                && (sell.getType() == OrderType.MARKET ||
                        buyOrders.peek().getPrice() >= sell.getPrice())) {

            Order buy = buyOrders.poll();
            int tradedQty = Math.min(sell.getQuantity(), buy.getQuantity());

            trades.add(new Trade(buy.getId(), sell.getId(), buy.getPrice(), tradedQty));

            sell.setQuantity(sell.getQuantity() - tradedQty);
            buy.setQuantity(buy.getQuantity() - tradedQty);

            if (buy.getQuantity() > 0)
                buyOrders.add(buy);
        }
    }

    public void printOrderBook() {
        System.out.println("\n--- ORDER BOOK ---");
        System.out.println("SELL ORDERS:");
        sellOrders.forEach(System.out::println);
        System.out.println("BUY ORDERS:");
        buyOrders.forEach(System.out::println);
    }

    public void printTrades() {
        System.out.println("\n--- TRADES ---");
        trades.forEach(System.out::println);
    }

    public double getBestBid() {
        return buyOrders.isEmpty() ? 0 : buyOrders.peek().getPrice();
    }

    public double getBestAsk() {
        return sellOrders.isEmpty() ? 0 : sellOrders.peek().getPrice();
    }
}
