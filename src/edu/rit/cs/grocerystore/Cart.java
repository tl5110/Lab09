package edu.rit.cs.grocerystore;

/**
 * An object that holds a number of groceries. It is "created" in the
 * CustomerPool, enqueued in a checkout line (see TSQueue) and retrieved
 * by the Clerk. Timing functionality is inherited from TimedObject.
 *
 * @author Tiffany Lee
 */
public class Cart extends TimedObject implements Comparable< Cart > {
    /** The number of items in the Cart */
    private final int numItems;

    /**
     * Construct the Sentinel Cart that is enqueued to indicate that
     * it is the end of the simulation. It is guaranteed to be lower
     * priority than any other Cart because its "grocery load" is
     * Integer.MAX_VALUE items. To be invoked only by Utilities
     */
    public Cart() { this.numItems = Integer.MAX_VALUE; }

    /**
     * The normal customer use to create grocery carts in the simulation
     * @param numItems the number of groceries in the cart
     */
    public Cart( int numItems ) { this.numItems = numItems; }

    /**
     * How much of a load is in this cart?
     *
     * @return the number of items, as specified in the constructor
     */
    public int getCartSize() { return numItems; }

    /**
     * Compare two carts, for use in a priority queue.
     *
     * @param other the object to be compared.
     * @return a positive number if this cart has more groceries,
     * a negative number if the other cart has more groceries,
     * otherwise, 0
     */
    @Override
    public int compareTo(Cart other) {
        return numItems - other.numItems;
    }

    /**
     * Convert the object to a string
     *
     * @return a string of format "Cart(#items)"
     */
    @Override
    public String toString() {
        return "Cart(" + this.numItems + ")";
    }

}
