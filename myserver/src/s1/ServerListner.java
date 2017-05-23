package s1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import javax.microedition.io.StreamConnection;

public class ServerListner implements Runnable {

    // Member fields
    private StreamConnection connection;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private String mOS = "";
    private String mConnectedDeviceName = "";

    // Command constants sent from the mobile device.
    private static final String DEVICE_CONNECTED = "DEVICE_CONNECTED";
    private static final String CMD = "CMD";

    // Acknowledge from the server
    private static final String ACKNOWLEDGE = "<ACK>";
    private static final String ACKNOWLEDGE_CMD_RECEIVED = "<ACK-CMD-RECEIVED>";
    private static final String ACKNOWLEDGE_IMG_CAN_RECEIVE = "<ACK-IMG-CAN-RECEIVE>";
    private static final String ACKNOWLEDGE_IMG_SENDING = "<ACK-IMG-SENDING>";
    private static final String ACKNOWLEDGE_IMG_RECEIVED = "<ACK-IMG-RECEIVED>";

    // Regex for parsing commands
    private static final String COLON = ":";

   
    public ServerListner(StreamConnection conn) {
        connection = conn;
        mOS = System.getProperty("os.name").toLowerCase();
        
    }

    @Override
    public void run() {
        try {
            // Open up InputStream and OutputStream to send and receive data
            mInputStream = connection.openDataInputStream();
            mOutputStream = connection.openDataOutputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            String[] inputCmd;

            // Read for connected device name
            bytes = mInputStream.read(buffer);
            inputCmd = parseInputCommand(new String(buffer, 0, bytes));
            if (inputCmd[0].equals(DEVICE_CONNECTED)) {
                mConnectedDeviceName = inputCmd[1];
                System.out.println("\nThis Device is Connected to: " + inputCmd[1]);
            }

            System.out.println("Waiting for commands.....");

            while (true) {
                bytes = mInputStream.read(buffer);
                
                if (bytes == -1) {
                    System.out.println("==============APPLICATION ENDED==============");
                    break;
                } if (ACKNOWLEDGE_IMG_CAN_RECEIVE.equals(new String(buffer, 0, bytes))) {
                    ImageSender o = new ImageSender();
                    o.sendScreenshot(mInputStream,mOutputStream);
                } else {
                    inputCmd = parseInputCommand(new String(buffer, 0, bytes));
                    if (inputCmd[0].equals(CMD)) {
                        ProcessInputCommand o = new ProcessInputCommand();
                        
                        o.processCommand(inputCmd);
                        mOutputStream.write(ACKNOWLEDGE_CMD_RECEIVED.getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private String[] parseInputCommand(String cmd) {
        return cmd.split(COLON);
    }
}