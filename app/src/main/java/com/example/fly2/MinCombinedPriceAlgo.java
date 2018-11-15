package com.example.fly2;

public class MinCombinedPriceAlgo extends AbstractAlgorithm {

    public TicketPair findMin(TicketPair min, TicketPair current) {
        if (min.getTotalPrice().getPrice() < current.getTotalPrice().getPrice()) {
            return min;
        }
        else
            return current;
    }
}
