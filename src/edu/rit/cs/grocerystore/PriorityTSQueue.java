package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

// TODO
// This code is provided so that the initial code compiles.
// It is up to you to fill it in properly and add comments.

public class PriorityTSQueue< E extends TimedObject & Comparable< E > >
        implements TSQueue< E > {

    public PriorityTSQueue() {
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
