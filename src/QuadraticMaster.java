import javax.swing.*;

/**
 * Basically the driver for the Quadratic class, I just named it Quadratic master based on the rules set by the assignment.
 */
public class QuadraticMaster{
    public static void main(String[] args) throws InterruptedException {
        RootFinderGUI application = new RootFinderGUI();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(800,600);
        application.setVisible(true);

    }
}
