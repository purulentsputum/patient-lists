

package inpatientlists;

import com.novell.ldap.util.Base64;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;


/**
 *
 * @author ross sellars
 * @created 27/06/2013 22:30
 */
public class QRCodeGenerator {

    public static BufferedImage getQRCode(String strCode){
        BufferedImage img = null;
        try {
            ByteArrayOutputStream baoCode = QRCode.from(strCode).to(ImageType.PNG).stream();
            String base64String=Base64.encode(baoCode.toByteArray());
            byte[] bytearray = Base64.decode(base64String);
            img = ImageIO.read(new ByteArrayInputStream(bytearray));
            
        } catch (IOException ex) {
            Logger.getLogger(QRCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
    //
}
