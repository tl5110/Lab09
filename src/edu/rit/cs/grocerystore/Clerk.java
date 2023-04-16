package edu.rit.cs.grocerystore;

public class Clerk {
    private final TSQueue<Cart> checkoutLine;

    public Clerk(TSQueue<Cart> checkoutLine){
        this.checkoutLine = checkoutLine;
    }

    public void run(){
        // TODO
    }
}
