package edu.rit.cs.grocerystore;

import java.util.List;

/**
 * The main programs that creates and runs the simulation of a checkout line
 * at a grocery store.
 *
 * @author RIT CS
 * @author Tiffany Lee
 */
public class GroceryLine {

    /**
     * Value of command line argument to indicate choice of a standard
     * first-in-first-out checkout line behavior.
     */
    public static final String FIFO_Q = "FIFO";

    /**
     * Value of command line argument to indicate choice of a checkout line
     * that services customers with fewer groceries first.
     */
    public static final String PRIO_Q = "Priority";

    /**
     * Run a simulation of the grocery store checkout line.
     *
     * @param args Strings from the command line:
     *             [0] How many customers to simulate;
     *             [1] The average grocery cart load;
     *             [2] The average time between customer arrivals at the line
     *             [3] "fifo" for ordinary queue, "prio" for small-cart priority
     */
    public static void main( String[] args ) {
        try {

            /*
             * First, process the command line arguments.
             * All errors are converted to exceptions, which are
             * caught at the end of this method.
             * A checkout queue is created based on the 4th argument.
             */
            if ( args.length != 4 ) {
                throw new IndexOutOfBoundsException( args.length );
            }
            int numCustomers = Integer.parseInt( args[ 0 ] );
            int avgLoad = Integer.parseInt( args[ 1 ] );
            double avgDelay = Double.parseDouble( args[ 2 ] );
            TSQueue<Cart> checkoutLine;
            switch( args[ 3 ] ) {
                case FIFO_Q -> checkoutLine = new FIFOTSQueue<>();
                case PRIO_Q -> checkoutLine = new PriorityTSQueue<>();
                default -> throw new IllegalArgumentException( args[ 3 ] );
            }
            Utilities.println("Generating " + numCustomers + " customers.");
            Utilities.println("Average cart size is " + avgLoad);
            Utilities.println("Average inter-customer arrival time is " + avgDelay);
            Utilities.println( "Using a " + args[ 3 ] + " queue.\n" );

            // Record the time that the simulation started.
            long beginTime = System.currentTimeMillis();

            CustomerPool customerPool = new CustomerPool(checkoutLine, numCustomers, avgLoad, (int) avgDelay);
            Clerk newClerk = new Clerk(checkoutLine);
            Thread clerkThread = new Thread(newClerk);

            clerkThread.start();
            List<Cart> carts = customerPool.simulateCustomers();

            checkoutLine.enqueue(Utilities.NO_MORE_CARTS);
            clerkThread.join();

            // Record the time that the simulation started.
            long endTime = System.currentTimeMillis();

            /*
             * Using the features of the TimedObject abstract class,
             * compute the average wait times and show total time as well.
             */
            double avgQueueWaitTime =
                    carts.stream()
                                 .mapToDouble( Cart::getInQueueTime )
                                 .sum()
                         / numCustomers;
            double avgServiceTime =
                    carts.stream()
                                 .mapToDouble( Cart::getServiceTime )
                                 .sum()
                         / numCustomers;
            double avgTotalWaitTime =
                    carts.stream()
                                 .mapToDouble( Cart::getTotalWaitTime )
                                 .sum()
                         / numCustomers;

            Utilities.println( System.lineSeparator() + "------------------" );
            String format =
                    "Average %10s time was %8.1f msec." +
                    System.lineSeparator();
            Utilities.printf( format, "queue wait", avgQueueWaitTime );
            Utilities.printf( format, "service", avgServiceTime );
            Utilities.printf( format, "total wait", avgTotalWaitTime );
            Utilities.println( "---" );
            Utilities.printf( "Total simulation time was %d msec.",
                              ( endTime - beginTime ) );
        }
        catch( IndexOutOfBoundsException | IllegalArgumentException e ) {
            System.err.println( e.getClass().getSimpleName() );
            System.err.println(
                    "Usage: java " +
                    "GroceryLine #custs avg-load avg-inter-arrival-time " +
                    FIFO_Q + '/' + PRIO_Q );
            System.exit( 1 );
        }
        catch( InterruptedException ie ) {
            System.err.println( "InterruptedException in main() join()?" );
            ie.printStackTrace();
        }
    }
}
