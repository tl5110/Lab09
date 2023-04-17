package edu.rit.cs.grocerystore;

import java.util.LinkedList;

/**
 * An implementation of TSQueue that follows first-in-first-out behavior
 *
 * @param <E> The type of elements that will be in the queue
 * @author Tiffany Lee
 */

public class FIFOTSQueue<E extends TimedObject> implements TSQueue<E> {
    /** The queue, or checkout line */
    private final LinkedList<E> contents;

    /** Initialize the underlying data structure used for the queue */
    public FIFOTSQueue() {
        this.contents = new LinkedList<>();
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on
     * the value. This method is synchronized because its body is a
     * critical region.
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
     * Removes a value from the queue and calls TimedObject.exitQueue() on
     * the value. This method is expected to block(wait) if the queue is empty,
     * rather than throwing an exception or returning a null value. This method
     * is synchronized because its body is a critical region.
     *
     * @return the value that has been in the queue for the longest period of time
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
