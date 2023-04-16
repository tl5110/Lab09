package edu.rit.cs.grocerystore;

public class Customer {
    private final TSQueue<Cart> queue;
    private final int numGroceries;
    private final long waitTime;

    public Customer(TSQueue<Cart> queue, int numGroceries, long waitTime){
        this.queue = queue;
        this.numGroceries = numGroceries;
        this.waitTime = waitTime;
    }

    public void run(){
        try{
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Cart cart = new Cart(numGroceries);
        this.queue.enqueue(cart);
    }
}
