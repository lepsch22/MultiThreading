import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is where we build the GUI and define action events.
 */
public class RootFinderGUI extends JFrame implements ActionListener {
    /**
     * Button to generate 30 Tuples
     */
    private JButton tuple30Button;
    /**
     * Button to generate 3000 tuples
     */
    private JButton tuple3000Button;
    /**
     * Text area
     */
    private JTextArea eventTextArea;
    /**
     * Label for Tuple 30 Button
     */
    private JLabel tuple30Label;
    /**
     * Label for Tuple 3000 button
     */
    private JLabel tuple3000Label;
    /**
     * Jpanel for the buttons
     */
    private JPanel buttonPanel;
    /**
     * Pane for the scroll bar
     */
    private JScrollPane scrollPane;

    /**
     * This is here the actual building of the GUI and layout is setup.
     */
    public RootFinderGUI(){
        buttonPanel = new JPanel(new GridLayout(2,2));



        add(buttonPanel,BorderLayout.NORTH);





        tuple30Button = new JButton();
        tuple30Button.setText("30 Tuples");
        tuple30Button.addActionListener(this);


        tuple30Label = new JLabel();
        tuple30Label.setText("Click the button to generate 30 tuples.");


        tuple3000Button = new JButton();
        tuple3000Button.setText("3000 Tuples");
        tuple3000Button.addActionListener(this);


        tuple3000Label = new JLabel();
        tuple3000Label.setText("Click the button to generate 3000 tuples.");

        eventTextArea = new JTextArea();
        scrollPane =  new JScrollPane(eventTextArea);


        add(scrollPane,BorderLayout.CENTER);
        buttonPanel.add(tuple30Label);
        buttonPanel.add(tuple30Button);
        buttonPanel.add(tuple3000Label);
        buttonPanel.add(tuple3000Button);



    }

    @Override
    /**
     * Define all the events. The first event is to generate 30 tuples on button event, and then the next event is
     * to generate 3000 tuples on button event click
     */
    public void actionPerformed(ActionEvent actionEvent){
        //Generate 30 tuples
        if(actionEvent.getSource() == tuple30Button){
            //Define buffers and slaves
            ExecutorService execute = Executors.newFixedThreadPool(10);
            ArrayBlockingQueue<Tuple> tupleInputs = new ArrayBlockingQueue<Tuple>(3000);
            ArrayBlockingQueue<Tuple> rootOut =  new ArrayBlockingQueue<Tuple>(3000);
            QuadraticSlave[] worker = new QuadraticSlave[10];

            int userInput = 1;
            //create 10 slaves
            for (int i = 0; i < 10; i++) {
                worker[i] = new QuadraticSlave(tupleInputs,rootOut,i);
                worker[i].setUserInput(1);
                worker[i].setGUI(this);
            }
            //Start the 10 slaves
            for (int i = 0; i < 10; i++) {
                execute.execute(worker[i]);
            }
            //Create 30 tuples into the input buffer
            for (int i = 0; i < 30; i++) {
                Random rand = new Random();
                Tuple tuple =  new Tuple(rand.nextInt(10)+1,rand.nextInt(10)+1,rand.nextInt(10)+1);
                try {
                    tupleInputs.put(tuple);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eventTextArea.append("Writing: "+tuple+"\n");
                System.out.println("Writing: "+tuple);
            }
            //Take the roots of the buffer of roots
            for (int i = 0; i < 30; i++) {
                Tuple tuple = new Tuple(0,0,0);
                try {
                    tuple = rootOut.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eventTextArea.append("Grabbing roots "+tuple.toStringFinal()+ "tuple number "+i+"\n");
                System.out.println("Grabbing roots "+tuple.toStringFinal()+ "tuple number "+i);
            }
            //Shutdown threads
            execute.shutdown();

        }
        else if(actionEvent.getSource() == tuple3000Button){
            //Make the buffers and slaves
            ExecutorService execute = Executors.newFixedThreadPool(10);
            ArrayBlockingQueue<Tuple> tupleInputs = new ArrayBlockingQueue<Tuple>(3000);
            ArrayBlockingQueue<Tuple> rootOut =  new ArrayBlockingQueue<Tuple>(3000);
            QuadraticSlave[] worker = new QuadraticSlave[10];
            int userInput = 2;

            //Make the 10 slaves
            for (int i = 0; i < 10; i++) {
                worker[i] = new QuadraticSlave(tupleInputs,rootOut,i);
                worker[i].setUserInput(2);
            }
            //Start the 10 slaves
            for (int i = 0; i < 10; i++) {
                execute.execute(worker[i]);
            }
            //Check the time it takes
            long startTime = System.currentTimeMillis();
            int total = 0;
            for (int i = 0; i < 3000; i++) {
                Random rand = new Random();
                Tuple tuple = new Tuple(rand.nextInt(10) + 1, rand.nextInt(10) + 1, rand.nextInt(10) + 1);
                try {
                    tupleInputs.put(tuple);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                total++;
                //System.out.println("Writing: "+tuple);
            }
            eventTextArea.append("Total amount of written tuples: "+total+"\n");
            System.out.println("Total amount of written tuples: "+total);
            total = 0;

            for (int i = 0; i < 3000; i++) {
                Tuple tuple;
                try {
                    tuple = rootOut.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                total++;
            }
            String stringBuilder = "";
            for (int i = 0; i < 10; i++) {
                appendTextArea( "Worker "+ i +" "+worker[i].getTotalAmount()+"\n");
            }
            eventTextArea.append("total amount of fully calculated tuples taken from buffer: "+total+"\n");
            System.out.println("total amount of fully calculated tuples taken from buffer: "+total);
            long durationofAll = System.currentTimeMillis()-startTime;

            eventTextArea.append("It took "+durationofAll/1000d+" seconds to run all of the reading, writing, and calculating.");
            System.out.println("It took "+durationofAll/1000d+" seconds to run all of the reading, writing, and calculating.");
            execute.shutdown();

        }

    }
    public void appendTextArea(String i){
        eventTextArea.append(i);
    }
}
