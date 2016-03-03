import java.awt.Image;
import javax.swing.ImageIcon;

public class Sprite {
	public static Image getImage(String string) {
		ImageIcon ii = new ImageIcon("sprites/" + string + ".png");
	    return ii.getImage();
	}
}
