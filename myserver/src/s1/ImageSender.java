/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s1;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author chandel
 */
public class ImageSender {
    private static final String ACKNOWLEDGE = "<ACK>";
    private static final String ACKNOWLEDGE_IMG_CAN_RECEIVE = "<ACK-IMG-CAN-RECEIVE>";
    private static final String ACKNOWLEDGE_IMG_SENDING = "<ACK-IMG-SENDING>";
    private static final String ACKNOWLEDGE_IMG_RECEIVED = "<ACK-IMG-RECEIVED>";

       public void sendScreenshot(InputStream mInputStream,OutputStream mOutputStream) {
        byte[] buffer = new byte[512];
        int bytes;
        
        try {
            // Send acknowledgment that image will be sent
            mOutputStream.write(ACKNOWLEDGE_IMG_SENDING.getBytes());
            
            // Receive acknowledgment
            bytes = mInputStream.read(buffer);
            if (!ACKNOWLEDGE.equals(new String(buffer, 0, bytes)))
                return;
            
            // Send image
            Thread.sleep(800);
            BufferedImage bImg = sendSlideScreenshot();
            ImageIO.write(bImg, "png", mOutputStream);
            mOutputStream.flush();
            
            // Receive acknowledgment that image has been received
            bytes = mInputStream.read(buffer);
            if (!ACKNOWLEDGE_IMG_RECEIVED.equals(new String(buffer, 0, bytes)))
                return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

private BufferedImage sendSlideScreenshot() {
        try {
            Robot r = new Robot();
            Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            return r.createScreenCapture(captureSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
