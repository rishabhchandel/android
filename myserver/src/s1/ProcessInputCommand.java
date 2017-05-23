package s1;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.StreamConnection;
import java.awt.MouseInfo;

/**
 *
 * @author chandel
 */
public class ProcessInputCommand {
    private String mOS = "";
    private String mConnectedDeviceName = "";

    // Command constants sent from the mobile device.
    private static final String DEVICE_CONNECTED = "DEVICE_CONNECTED";
    private static final String APP_STARTED = "APP_STARTED";
    private static final String EXIT_CMD = "EXIT";
    private static final String CMD = "CMD";

    // Acknowledge from the server
    private static final String ACKNOWLEDGE = "<ACK>";
    private static final String ACKNOWLEDGE_CMD_RECEIVED = "<ACK-CMD-RECEIVED>";
    private static final String ACKNOWLEDGE_IMG_CAN_RECEIVE = "<ACK-IMG-CAN-RECEIVE>";
    private static final String ACKNOWLEDGE_IMG_SENDING = "<ACK-IMG-SENDING>";
    private static final String ACKNOWLEDGE_IMG_RECEIVED = "<ACK-IMG-RECEIVED>";

    // Regex for parsing commands
    private static final String COLON = ":";

    // Operating Systems
    private static final String WINDOWS = "window";
    private static final String MAC_OS = "mac";

    // Presentation programs
    private static final String BROWSER = "BROWSER";
    private static final String MICROSOFT_POWERPOINT = "MICRO_PPT";
    private static final String ADOBE_READER = "ADOBE_PDF";

    

    
    
    public void processCommand(String[] inputCmd) {

        if (inputCmd[2].equals("OPEN")) {
            process1C(inputCmd, KeyEvent.VK_WINDOWS);
        } else if (inputCmd[2].equals("CLOSE")) {
            process2C(KeyEvent.VK_ALT, KeyEvent.VK_F4);
            System.out.println("CLOSE\n");
        } else if (inputCmd[2].equals("OK")) {
            process1C(inputCmd, KeyEvent.VK_ENTER);
        } else if (inputCmd[2].equals("RC")) {
            try{
                Robot r = new Robot();
                r.mousePress( InputEvent.BUTTON3_MASK );
                r.mouseRelease( InputEvent.BUTTON3_MASK );
                }catch(Exception e){
                }
                System.out.println("RIGHT CLICK\n");
        } else if (inputCmd[2].equals("UP")) {
            process1C(inputCmd, KeyEvent.VK_UP);
        } else if (inputCmd[2].equals("DOWN")) {
            process1C(inputCmd, KeyEvent.VK_DOWN);
        }else if (inputCmd[2].equals("LEFT")) {
            process1C(inputCmd, KeyEvent.VK_LEFT);
        } else if (inputCmd[2].equals("RIGHT")) {
            process1C(inputCmd, KeyEvent.VK_RIGHT);
        } else if (inputCmd[2].equals("GO_FULLSCREEN") || inputCmd[2].equals("EXIT_FULLSCREEN")) {
            handleFullScreenCmd(inputCmd);
        } else if(inputCmd[2].equals(APP_STARTED)) {
            System.out.println(mConnectedDeviceName + " is in Presentation Mode!!\n");
        } else if (inputCmd[2].equals(EXIT_CMD)) {
            System.out.println("==============APPLICATION ENDED==============");
        }
    }
    private void process2C(int key, int key1) {
        try {
            Robot r = new Robot();
          
            r.keyPress(key);
            r.keyPress(key1);
            r.keyRelease(key1);
            r.keyRelease(key);
        } catch (Exception e) {
            return;
        }
    }

    private void process1C(String[] inputCmd, int key) {
        try {
            Robot robot = new Robot();
          
            robot.keyPress(key);
            robot.keyRelease(key);

            System.out.println(inputCmd[0] + ": " + inputCmd[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void handleFullScreenCmd(String[] inputCmd) {
            if (inputCmd[2].equals("GO_FULLSCREEN")) {
                process1C(inputCmd, KeyEvent.VK_F5);
                process1C(inputCmd, KeyEvent.VK_F11);
                
            } else if (inputCmd[2].equals("EXIT_FULLSCREEN")) {
                process1C(inputCmd, KeyEvent.VK_ESCAPE);
                process1C(inputCmd, KeyEvent.VK_F11);
            }
        
        System.out.println(inputCmd[1] + ": " + inputCmd[2]);
    }

}
    
    
    
    
    
    