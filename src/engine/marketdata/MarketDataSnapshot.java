package engine.marketdata;

public class MarketDataSnapshot {
    public double bestBid;
    public int bestBidQty;
    public double bestAsk;
    public int bestAskQty;
    public double lastTradePrice;
    public int lastTradeQty;

    public MarketDataSnapshot(double bid, int bidQty, double ask, int askQty,
            double lastPrice, int lastQty) {
        this.bestBid = bid;
        this.bestBidQty = bidQty;
        this.bestAsk = ask;
        this.bestAskQty = askQty;
        this.lastTradePrice = lastPrice;
        this.lastTradeQty = lastQty;
    }

    public void print() {
        System.out.println("MARKET DATA UPDATE");
        System.out.println("Best Bid: " + bestBid + " Qty: " + bestBidQty);
        System.out.println("Best Ask: " + bestAsk + " Qty: " + bestAskQty);
        System.out.println("Last Trade: " + lastTradePrice + " Qty: " + lastTradeQty);
        System.out.println("-----------------------------");
    }
}
