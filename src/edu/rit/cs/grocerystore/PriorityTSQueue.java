package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A TSQueue with priority queue extraction order.
 *
 * @param <E> the type of elements that will be in the queue (must be Comparable)
 * @author Tiffany Lee
 */

public class PriorityTSQueue< E extends TimedObject & Comparable< E > >
        implements TSQueue< E > {
    private final PriorityQueue<E> contents;
    public PriorityTSQueue() {
        this.contents = new PriorityQueue<>();
    }

    @Override
    public synchronized int enqueue( E value ) {
        contents.add(value);
        value.enterQueue();
        notifyAll();
        return contents.size();
    }

    @Override
    public synchronized E dequeue() {
        while(contents.isEmpty()){
            try{
                wait();
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        E removed = contents.remove();
        removed.exitQueue();
        notifyAll();
        return removed;
    }
}
