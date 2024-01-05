//Ellison Yufenyuy
//Working with java graphics to create a groovy lava lamp



import javax.swing.*; //Imports 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LavaLamp implements ActionListener {
    private JPanel panel;
    private Timer timer;
    private Color backgroundColor;
    private boolean isCurrChanging;

    private int buttonClickerCnt = 0;

    LavaLamp() { //For GUI setup

        JFrame frame = new JFrame("Lava Lamp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 500));

        JButton button = new JButton("Toggle Colors Button"); //Printed name for button


        button.addActionListener(this);
        panel.add(button);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        backgroundColor = panel.getBackground();

        isCurrChanging = true;

        timer = new Timer(100, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent ae) { //Action performed method for handling button clicks and timer 
        if (ae.getSource() instanceof JButton) {
            buttonClickerCnt++;

            if (buttonClickerCnt % 4 == 1) { //Changing/Switching color changes based on the button clicks
                isCurrChanging = !isCurrChanging;
                if (!isCurrChanging) {
                    System.out.println(backgroundColor.getRed() + "," +
                            backgroundColor.getGreen() + "," +
                            backgroundColor.getBlue());
                }
            } else if (buttonClickerCnt % 4 == 0) {
                isCurrChanging = true;


            }
        } else if (ae.getSource() instanceof Timer) {
            if (isCurrChanging) {
                Random random = new Random();
                int red = backgroundColor.getRed() + random.nextInt(11) - 5;



                int green = backgroundColor.getGreen() + random.nextInt(11) - 5;
                int blue = backgroundColor.getBlue() + random.nextInt(11) - 5;

                red = Math.min(255, Math.max(0, red)); //Color parts to the range 0, 255
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                backgroundColor = new Color(red, green, blue); //Setting the new background color
                panel.setBackground(backgroundColor);
            }
        }
    }




    public static void main(String[] args) { //Main method to initialize the LavaLamp object
        new LavaLamp();
    }
}
