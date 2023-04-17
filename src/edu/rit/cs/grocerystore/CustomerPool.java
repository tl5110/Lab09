package edu.rit.cs.grocerystore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible for creating Customers and
 * starting each one on a separate thread.
 *
 * @author Tiffany Lee
 */
public class CustomerPool {
    /** The checkout line */
    private final TSQueue<Cart> checkoutLine;
    /** The number of customers in line */
    private final int numCustomers;
    /** The mean number of groceries in each Cart */
    private final int avgLoad;
    /** The mean amount of time between Customer arrivals at checkout */
    private final double avgDelay;
    /** Random number generator */
    private final Random random;

    /**
     * Store all the parameter values for later use.
     * Also, initialize a Random number generator.
     *
     * @param checkoutLine the provided queue of Carts
     * @param numCustomers the number of customers to create
     * @param avgLoad the mean number of groceries in each Cart
     * @param avgDelay the mean amount of time between Customer arrivals at checkout
     */
    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers,
                        int avgLoad, int avgDelay){
        this.checkoutLine = checkoutLine;
        this.numCustomers = numCustomers;
        this.avgLoad = avgLoad;
        this.avgDelay = avgDelay;
        this.random = new Random();
    }

    /**
     * Create the given number of customers, start them all up on
     * separate threads, and wait for them to finish. In the process,
     * a Cart will be created for each Customer. Each cart has a
     * random number of groceries placed in it
     * (avgLoad from constructor is the mean).
     * Each customer is told to wait a random amount of time more than
     * the previous customer. The additional time to wait is a random value
     * based on the avgDelay from the constructor. Note that the time for
     * each customer to wait is measured from roughly the start of this
     * method. Therefore, times assigned to each successive customer go
     * steadily upwards.
     *
     * @return the list of Cart objects made for all the Customers. These
     * get returned because they have timing information in them
     */
    public List<Cart> simulateCustomers(){
        List<Cart> cartList = new ArrayList<>();
        List<Thread> customerList = new ArrayList<>();
        double prevDelay = 0;
        for(int i = 0; i < numCustomers; i++){
            int load = (int) Utilities.sinePDFDelay(random, avgLoad);
            Cart cart = new Cart(load);
            cartList.add(cart);

            prevDelay = Utilities.sinePDFDelay(random, avgDelay) + prevDelay;
            Customer customer = new Customer(prevDelay, cart, checkoutLine);
            Thread customerThread = new Thread(customer);
            customerThread.start();
            customerList.add(customerThread);
        }
        for(Thread customer : customerList){
            try {
                customer.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return cartList;
    }
}
