package com.example.fly2;

import android.content.Context;

public class MinCombinedPriceAlgo extends AbstractAlgorithm {

    public MinCombinedPriceAlgo(Context context) {
        super(context);
    }

    public TicketPair findMin(TicketPair min, TicketPair current) {
        if (min.getTotalPrice().getPrice() < current.getTotalPrice().getPrice()) {
            return min;
        }
        else
            return current;
    }
}
