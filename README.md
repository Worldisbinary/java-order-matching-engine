Real-Time Order Matching Engine (Java)
A high-performance price–time priority order matching engine built in Java to simulate the core mechanics of modern electronic financial markets.
This project models how exchanges match buy and sell orders in real time, focusing on data structures, system design, and low-latency trade execution.

What This Project Demonstrates
Low-latency order matching logic
Price–Time priority (used in real exchanges)
Clean modular system design
Efficient use of Java data structures
Real-time trade generation from order flow
This is the core engine behind what trading platforms, stock exchanges, and market data systems use.

System Design Overview
The engine processes orders in the following flow:
Incoming Order → OrderBook → Matching Engine → Trade Execution → Market Data Update

Order Types Supported
LIMIT Orders — matched based on price priority
MARKET Orders — immediately execute against best available price
Matching Rule (Price–Time Priority)
(1) Best Price First
     Highest BUY price wins
     Lowest SELL price wins
(2) First In First Out at the same price level


CORE COMPONENTS
| Class       | Responsibility                                     |
| ----------- | -------------------------------------------------- |
| `Order`     | Represents an order with ID, side, price, quantity |
| `Trade`     | Represents an executed trade between two orders    |
| `OrderBook` | Maintains buy/sell queues and runs matching logic  |
| `Side`      | Enum for BUY / SELL                                |
| `OrderType` | Enum for LIMIT / MARKET                            |
| `Main`      | Simulates order flow and runs the engine           |


Data Structures Used
PriorityQueue for maintaining best bid/ask levels
FIFO ordering within the same price level
Efficient order removal and re-insertion after partial fills
This simulates how real exchanges optimize for speed and fairness.

Future Improvements
Planned upgrades to simulate real-world complexity:
      Real-time market data feed
      Order cancellations and modifications
      Level 2 order book depth
      Multi-threaded order ingestion
      Latency measurement and
