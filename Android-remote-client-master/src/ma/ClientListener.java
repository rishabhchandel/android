package ma;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import messages.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ClientListener implements Runnable{

	private InetAddress serverAddr;
	private int serverPort;
	private DatagramSocket socket;
	byte[] buf = new byte[65000];
	private DatagramPacket dgp;
	private AppDelegate delegate;
	private int framesPerSecond = -1;
	public boolean connected = false;
	
	public static int deviceWidth = 100;
	public static int deviceHeight = 100;
	
	public ClientListener(int port, int fps, AppDelegate del){
		delegate = del;
		framesPerSecond = fps;
		
		try{
			
			serverAddr = getLocalIpAddress();
			dgp = new DatagramPacket(buf, buf.length);

		}catch (Exception e){
			Log.e("ClientActivity", "C: Error", e);
		}
		serverPort = port;
	}
	
	
	
	public void run() {
	       try {
	           socket = new DatagramSocket(serverPort, serverAddr);
	           connected = true;
	           
	           Timer timer = new Timer();
	           int frames = 1000/framesPerSecond;
	           
	           Log.e("FRAMES", ""+frames);
	           timer.scheduleAtFixedRate(getImageTask, 0, frames);
	           
	           listen();	           
	       }
	       catch (Exception e) {
	           Log.e("ClientActivity", "Client Connection Error", e);
	       }
	 }
	
	
	
	private TimerTask getImageTask = new TimerTask(){

		@Override
		public void run() {
			String message = new String(""+
					Constants.REQUESTIMAGE+
					 Constants.DELIMITER+
					 deviceWidth+
					 Constants.DELIMITER+
					 deviceHeight+"");
			
			Log.d("MESSAGE", message);
			
			 delegate.sendMessage(message);
		}
	};
	
	
	
	private void listen()
	{
	   	Log.e("LISTENING", "LISTEING at "+serverAddr.getHostAddress()+" on "+serverPort);

	   	while(connected){

	   		try{
	   			socket.receive(dgp);
	   			Bitmap bm = BitmapFactory.decodeByteArray(dgp.getData(), 0, 65000);
	   			delegate.getController().setImage(bm);
	   			
	   			Log.e("Testing", "Received image");

	   		}catch(Exception e){
	   			Log.e("Error", "Could not receive image");
	   			e.printStackTrace();
	   		}
	   	}
	}

	
	
   public static InetAddress getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && !inetAddress.toString().contains(":"))
	                {
	                	Log.e("IP Address", ""+inetAddress);
	                    return inetAddress;
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("", ex.toString());
	    }
	    return null;
	}
   
   
   
   public void closeSocket(){
	   	socket.close();
	   	connected = false;
	   	getImageTask.cancel();
   }
}
