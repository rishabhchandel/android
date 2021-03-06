package s1;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BluetoothServer implements Runnable {

   
    private static final String BLUETOOTH_EXCEPTION = "Bluetooth on this device cannot be changed!!";
    
    private static final String _UUID = "C46C11A93E424F64AB1EFC892E87B9DE"; // C46C-11A9-3E42-4F64-AB1E-FC89-2E87-B9DE

    @Override
    public void run() {
        waitForConnection();
    }

    private void waitForConnection() {
        LocalDevice localDevice = null;

        StreamConnectionNotifier streamConnNotifier = null;
        StreamConnection connection = null;

        // Device setup to listen for Bluetooth connection
        try {
            // Set local Bluetooth to be generally discoverable.
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            System.out.println("Name of this Device: " + localDevice.getFriendlyName());
            System.out.println("Bluetooth Address of this Device: " + localDevice.getBluetoothAddress());

            // Create UUID for SPP and service URL  serial port profile
            UUID uuid = new UUID(_UUID, false);
            String connectionURL = "btspp://localhost:" + uuid + ";name=BluetoothRemoteApp";

            // Open server URL
            streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionURL);
        } catch (BluetoothStateException e) {
            System.out.println(BLUETOOTH_EXCEPTION);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Wait for client connection
        while (true) {
            try {
                System.out.println("\n" + "WAITING FOR BLUETOOTH CONNECTION.................");
                connection = streamConnNotifier.acceptAndOpen();
                
                // Start thread to process input commands
                Thread processInputConnThread = new Thread(new ServerListner(connection));
                processInputConnThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
    public static void main(String[] args) {
        // Initiates the server and waits for a connection from the client
        Thread serverStartThread = new Thread(new BluetoothServer());
        serverStartThread.start();
    }
}
