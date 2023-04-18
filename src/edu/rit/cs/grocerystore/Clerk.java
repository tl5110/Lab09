package edu.rit.cs.grocerystore;

/**
 * In the simulation, the person who checks out customers' groceries.
 * In terms of Producer/Consumer architectures, this is the consumer.
 * In terms of queuing theory, this is the service.
 *
 * @author Tiffany Lee
 */
public class Clerk implements Runnable{
    /** The checkout line */
    private final TSQueue<Cart> checkoutLine;

    /**
     * Create a Clerk and connect it to its checkout line.
     *
     * @param checkoutLine the queue from which the clerk gets carts to check out
     */
    public Clerk(TSQueue<Cart> checkoutLine){
        this.checkoutLine = checkoutLine;
    }

    /**
     * This method, running on a separate thread, continuously removes
     * carts from the checkout line and sleeps for a time period to
     * simulate the checkout process. When the sleep time for a cart is
     * complete, it then "tells" the cart that it has finished servicing
     * the cart so that times can be saved. See TimedObject.servicingDone().
     * The sleep time is the number of items in the cart, multiplied by
     * Utilities.TIME_PER_CART_ITEM. The method exits when it removes the
     * special cart Utilities.NO_MORE_CARTS, which is not included in any
     * timing measurements.
     */
    public void run(){
        Cart cartRemoved = checkoutLine.dequeue();
        while(cartRemoved != Utilities.NO_MORE_CARTS){
            try {
                Utilities.println("Clerk got " + cartRemoved);
                Thread.sleep(cartRemoved.getCartSize()*Utilities.TIME_PER_CART_ITEM);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cartRemoved.servicingDone();
            cartRemoved = checkoutLine.dequeue();
        }
    }
}
