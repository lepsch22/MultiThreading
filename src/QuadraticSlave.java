import java.nio.Buffer;
import java.security.SecureRandom;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * THis is where we dfine all the logic for the threads, and also put the Tuples through the quadratic equation.
 * @author blepsch
 */
public class QuadraticSlave implements Runnable {
    /**
     * Buffer for the shared Inputs
     */
    private ArrayBlockingQueue<Tuple> shared;
    /**
     * Buffer for the shared root buffer
     */
    private ArrayBlockingQueue<Tuple> rootBuffer;
    /**
     * Worker ID
     */
    private int i;
    /**
     * User Input
     */
    private int userInput;
    /**
     * We need to write to the GUI in the Quadratic Slave class.
     */
    private RootFinderGUI gui;
    /**
     * Total amount of runs by a thread
     */
    private int totalAmount;

    /**
     * Constructor for thread.
     * @param shared shared input buffer
     * @param rootBuffer shared root buffer
     * @param i slave id
     */
    public QuadraticSlave(ArrayBlockingQueue<Tuple> shared, ArrayBlockingQueue<Tuple> rootBuffer, int i){
        this.shared = shared;
        this.rootBuffer= rootBuffer;
        this.i = i;
    }

    /**
     * The run method has to be implemented to multi thread. This is because each thread created will have its own run method.
     * In our run method our thread will take an input from the input buffer, and then find the correct roots. After it does that
     * it then takes the calculated roots and puts them into a shared final buffer.
     */
    @Override
    public void run() {
        int counter = 0;
        int numOfTuples = 0;
        if(userInput == 1){
            numOfTuples = 30;
        }
        if(userInput == 2){
            numOfTuples = 3000;
        }

        for (int j = 0; j < numOfTuples/10; j++) {
            try {
                //System.out.println(j);
                Tuple tuple;
                tuple = shared.take();
                if(userInput == 1) {
                    gui.appendTextArea("Slave " + i + " Takes " + tuple+"\n");
                    System.out.println("Slave " + i + " Takes " + tuple);
                }
                //math
                double a = tuple.getA();
                double b = tuple.getB();
                double c = tuple.getC();
                double d = b * b - 4 * a * c;

                double root1Real = 0;
                double root2Real = 0;
                double root1Imaginary = 0;
                double root2Imaginary = 0;

                if (d > 0) {
                    root1Real = (-b + Math.sqrt(d)) / (2 * a);
                    root2Real = (-b - Math.sqrt(d)) / (2 * a);
                    root1Imaginary = 0;
                    root2Imaginary = 0;
                } else if (d == 0) {
                    root1Real = -(b / (2 * a));
                    root2Real = -(b / (2 * a));
                    root1Imaginary = 0;
                    root2Imaginary = 0;
                } else {
                    root1Real = -b / (2 * a);
                    root2Real = -b / (2 * a);
                    root1Imaginary = Math.sqrt(-d) / (2 * a);
                    root2Imaginary = -Math.sqrt(-d) / (2 * a);
                }
                tuple.setRoot1(root1Real);
                tuple.setRoot2(root2Real);
                tuple.setImaginaryRoot1(root1Imaginary);
                tuple.setImaginaryRoot2(root2Imaginary);
                if(userInput == 1) {
                    gui.appendTextArea("Slave " + i + " puts " + tuple.toStringFinal() + " into final buffer.\n");
                    System.out.println("Slave " + i + " puts " + tuple.toStringFinal() + " into final buffer.");
                }
                rootBuffer.put(tuple);
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        totalAmount = counter;



    }

    /**
     * Used to set userinput
     * @param userInput used in conditionals
     */
    public void setUserInput(int userInput) {
        this.userInput = userInput;
    }

    /**
     * Sets GUI interface
     * @param in write to the GUI
     */
    public void setGUI(RootFinderGUI in){
        gui = in;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
