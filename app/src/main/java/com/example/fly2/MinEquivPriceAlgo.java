package com.example.fly2;

import android.content.Context;

public class MinEquivPriceAlgo extends AbstractAlgorithm {

    public MinEquivPriceAlgo(Context context) {
        super(context);
    }

    public TicketPair findMin(TicketPair min, TicketPair current) {

        double first = current.getFirstTicket().getPrice();
        double second = current.getSecondTicket().getPrice();

        double difference = Math.abs(second - first);
        double sum = first + second - difference;
        if (min.getTotalPrice() < sum)
            return min;
        else
            return current;
    }
}
