package edu.rit.cs.grocerystore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerPool {
    private final TSQueue<Cart> checkoutLine;
    private final int numCustomers;
    private final int avgLoad;
    private final double avgDelay;
    private final Random random;

    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers,
                        int avgLoad, int avgDelay){
        this.checkoutLine = checkoutLine;
        this.numCustomers = numCustomers;
        this.avgLoad = avgLoad;
        this.avgDelay = avgDelay;
        this.random = new Random();
    }

    public List<Cart> simulateCustomers(){
        List<Cart> cartList = new ArrayList<>();
        for(int i = 0; i < numCustomers; i++){
            Cart cart = new Cart(avgLoad);
            cartList.add(cart);
            Customer customer = new Customer(avgDelay+ random.nextDouble(),
                    cart, checkoutLine);
            customer.run();
        }
        return cartList;
    }
}
