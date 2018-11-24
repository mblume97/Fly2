package com.example.fly2;

import android.content.Context;

public class MinEquivPriceAlgo extends AbstractAlgorithm {

    public MinEquivPriceAlgo(Context context) {
        super(context);
    }

    public TicketPair findMin(TicketPair min, TicketPair current) {

        double first = current.getFirstTicket().getPrice().getPrice();
        double second = current.getSecondTicket().getPrice().getPrice();

        double difference = Math.abs(second - first);
        double sum = first + second - difference;
        if (min.getTotalPrice().getPrice() < sum)
            return min;
        else
            return current;
    }
}
