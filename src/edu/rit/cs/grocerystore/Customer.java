package edu.rit.cs.grocerystore;

public class Customer extends Object implements Runnable{
    private final double delay;
    private final Cart cart;
    private final TSQueue<Cart> queue;
    private final int id;
    private static int next_id = 1;

    public Customer(double delay,  Cart cart, TSQueue<Cart> queue){
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;
        this.id = next_id;
        next_id++;
    }

    public void run(){
        try{
            Thread.sleep((long) delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.queue.enqueue(cart);
        System.out.println("Customer " + id + " with " + cart +
                " has entered the line, with ?? customers in front." );
    }
}
