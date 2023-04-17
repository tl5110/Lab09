package edu.rit.cs.grocerystore;

/**
 * In this simulation, a customer's only job is to wait for the
 * specified time, then enqueue its shopping cart in the checkout
 * line queue. Every customer has an integer ID. They are automatically
 * numbered consecutively starting at 1.
 *
 * @author Tiffany Lee
 */
public class Customer implements Runnable{
    /** Number of msec. to wait before enqueuing the cart at the checkout line */
    private final double delay;
    /** The cart of groceries */
    private final Cart cart;
    /** The checkout line */
    private final TSQueue<Cart> queue;
    /** Customer ID */
    private final int id;
    /** Next customer's ID */
    private static int next_id = 1;

    /**
     * Creates a customer object, storing all parameters provided and
     * assigning the next consecutive integer as this object's ID.
     * First Customer is #1.
     *
     * @param delay how many msec. to wait before enqueuing the cart at the checkout line
     * @param cart the cart of groceries (already filled with goodies)
     * @param queue the checkout line
     */
    public Customer(double delay,  Cart cart, TSQueue<Cart> queue){
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;
        this.id = next_id;
        next_id++;
    }

    /**
     * This method should be executed by a thread uniquely assigned
     * to this Customer. This is what the method does.
     *      1. Sleep for the given delay time.
     *      2. Put the given Cart in the checkout queue.
     *      3. Print a message announcing the above has been done.
     * The format of the message is:
     *      "Customer id with cart has entered the line, with N
     *      customers in front."
     */
    public void run(){
        try{
            Thread.sleep((long) delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int customersAhead = this.queue.enqueue(cart) - 1;
        Utilities.println("Customer " + id + " with " + cart +
                " has entered the line, with " + customersAhead + " customers in front.");
    }
}
