package com.example.fly2;

import java.util.ArrayList;

public abstract class AbstractAlgorithm {

    public abstract TicketPair findMin(TicketPair min, TicketPair current);

    private ArrayList<TicketPair> makeTicketPairs() {
        return null;
    }

    public TicketPair findOptimal() {
        ArrayList<TicketPair> pairs = makeTicketPairs(); // Makes the TravelPayout API calls
        TicketPair min = pairs.get(0);
        for (int i = 1; i < pairs.size(); i++) {
            min = findMin(min, pairs.get(i)); // Calls the concrete class’ findMin()
        }
        return min;
    }
}
