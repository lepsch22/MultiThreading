/**
 * This is where we create an object Tuple that is used to stor everything related to it.
 * It is the input to our quadratic function and also stroes the outputs.
 * @author blepsch
 */
public class Tuple {
    /**
     * Double for a in the equation
     */
    private double a;
    /**
     * Double for b in the equation
     */
    private  double b;
    /**
     * Double for c in the equation
     */
    private  double c;
    /**
     * Double for root1 in the equation
     */
    private  double root1;
    /**
     * Double for root2 in the equation
     */
    private  double root2;
    /**
     * Double for imaginaryroot1 in the equation
     */
    private  double imaginaryRoot1;
    /**
     * Double for imaginaryroot2 in the equation
     */
    private  double imaginaryRoot2;
    /**
     * Number of tuples
     */
    private int numberOfTuples;

    /**
     * Tuple object
     * @param a
     * @param b
     * @param c
     */
    public Tuple(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * getter for a
     * @return a in equation
     */
    public double getA(){
        return a;
    }

    /**
     * getter for b
     * @return b in equation
     */
    public double getB(){
        return b;
    }

    /**
     * getter for c
     * @return c in equation
     */
    public double getC(){
        return c;
    }

    /**
     * setter for the first root
     * @param root1
     */
    public void setRoot1(double root1) {
        this.root1 = root1;
    }

    /**
     * Setter for root2
     * @param root2
     */
    public void setRoot2(double root2) {
        this.root2 = root2;
    }

    /**
     * get root1
     * @return root 1 from equation
     */
    public double getRoot1() {
        return root1;
    }

    /**
     * get root2
     * @return root 2 for equation
     */
    public double getRoot2() {
        return root2;
    }

    /**
     * get the 1st imaginary root
     * @return first imaginary root in the equation
     */
    public double getImaginaryRoot1() {
        return imaginaryRoot1;
    }

    /**
     * get the 2nd imaginary root
     * @return second imaginary root in the equation
     */
    public double getImaginaryRoot2() {
        return imaginaryRoot2;
    }

    /**
     * set the first imaginary root
     * @param imaginaryRoot1
     */
    public void setImaginaryRoot1(double imaginaryRoot1) {
        this.imaginaryRoot1 = imaginaryRoot1;
    }

    /**
     * set the second imaginary root
     * @param imaginaryRoot2
     */
    public void setImaginaryRoot2(double imaginaryRoot2) {
        this.imaginaryRoot2 = imaginaryRoot2;
    }

    /**
     * get number of tuples
     * @return the number of tuples
     */
    public int getNumberOfTuples() {
        return numberOfTuples;
    }

    /**
     * set the number of tuples
     * @param numberOfTuples
     */
    public void setNumberOfTuples(int numberOfTuples) {
        this.numberOfTuples = numberOfTuples;
    }

    /**
     * toString method to print out tuple
     * @return tuple imput variables
     */
    @Override
    public String toString(){
        String stringBuilder = "";
        stringBuilder += "A= "+a+ " B= "+b+" C= "+c;
        return stringBuilder;
    }

    /**
     * Final tuple results after getting imaginary
     * @return final tuple
     */
    public String toStringFinal(){
        String stringBuilder = "";
        stringBuilder = toString() + "  Roots: "+  Math.floor(getRoot1()*1e5)/1e5 + "+" + Math.floor(getImaginaryRoot1()*1e5)/1e5 +
                "j, Root2 " + Math.floor(getRoot2()*1e5)/1e5 + "+" + Math.floor(getImaginaryRoot2()*1e5)/1e5 + "j. ";
        return  stringBuilder;
    }
}
