package edu.rit.cs.grocerystore;

public class Clerk extends Object implements Runnable{
    private final TSQueue<Cart> checkoutLine;

    public Clerk(TSQueue<Cart> checkoutLine){
        this.checkoutLine = checkoutLine;
        Clerk newClerk = new Clerk(this.checkoutLine);
    }

    public void run(){
        while(true){
            Cart time = checkoutLine.dequeue();
            try {
                Thread.sleep(time.getCartSize()*Utilities.TIME_PER_CART_ITEM);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time.servicingDone();
        }
    }
}
