package edu.rit.cs.grocerystore;

import java.util.PriorityQueue;

/**
 * A TSQueue with priority queue extraction order.
 *
 * @param <E> the type of elements that will be in the queue (must be Comparable)
 * @author Tiffany Lee
 */

public class PriorityTSQueue<E extends TimedObject & Comparable<E>>
        implements TSQueue< E > {
    /** The queue, or checkout line */
    private final PriorityQueue<E> contents;

    /** Initialize the underlying data structure used for the queue */
    public PriorityTSQueue() {
        this.contents = new PriorityQueue<>();
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on
     * the value. This method is synchronized because its body is a
     * critical region
     *
     * @param value the value to be enqueued
     * @return the size of the queue after the value was added
     */
    @Override
    public synchronized int enqueue( E value ) {
        contents.add(value);
        value.enterQueue();
        notifyAll();
        return contents.size();
    }

    /**
     * Removes a value from the queue and calls TimedObject.exitQueue()
     * on the value. This method is expected to block(wait) if the queue is
     * empty, rather than throwing an exception or returning a null value.
     * This method is synchronized because its body is a critical region.
     *
     * @return the minimum value in the queue, according to E's natural ordering
     */
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
