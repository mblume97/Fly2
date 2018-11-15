package com.example.fly2;

public class MinEquivPriceAlgo extends AbstractAlgorithm {

    public TicketPair findMin(TicketPair min, TicketPair current) {

        float first = current.getFirstTicket().getPrice().getPrice();
        float second = current.getSecondTicket().getPrice().getPrice();

        float difference = Math.abs(second - first);
        float sum = first + second - difference;
        if (min.getTotalPrice().getPrice() < sum)
            return min;
        else
            return current;
    }
}
