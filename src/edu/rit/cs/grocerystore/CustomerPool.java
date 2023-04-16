package edu.rit.cs.grocerystore;
import java.util.List;

public class CustomerPool {
    private final TSQueue<Cart> checkoutLine;
    private final int numCustomers;
    private final int avgLoad;
    private final double avgDelay;

    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers,
                        int avgLoad, int avgDelay){
        this.checkoutLine = checkoutLine;
        this.numCustomers = numCustomers;
        this.avgLoad = avgLoad;
        this.avgDelay = avgDelay;
    }

    public List<Cart> simulateCustomers(){
        // TODO
        return null;
    }
}
