package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of TSQueue that follows first-in-first-out behavior
 *
 * @param <E> The type of elements that will be in the queue
 * @author Tiffany Lee
 */

public class FIFOTSQueue< E extends TimedObject > implements TSQueue< E > {
    private final LinkedList<E> contents;

    public FIFOTSQueue() {
        this.contents = new LinkedList<>();
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
