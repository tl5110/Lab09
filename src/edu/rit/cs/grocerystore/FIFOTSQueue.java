package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

// TODO
// This code is provided so that the initial code compiles.
// It is up to you to fill it in properly and add comments.

public class FIFOTSQueue< E extends TimedObject > implements TSQueue< E > {

    public FIFOTSQueue() {
    }

    @Override
    public synchronized int enqueue( E value ) {
        return 0;
    }

    @Override
    public synchronized E dequeue() {
        return null;
    }
}
