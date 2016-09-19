package Interface;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * JPanel qui contient le fond table de poker de la vuePrincipale
 * @author Alexandre
 *
 */
public class FondPanel extends JPanel {
	Image img;
	/**
	 * 
	 * @param Chemin de la photo de fond
	 */
	public FondPanel(String cheminFond){
		
		super();
		img=null;
		try{
			 this.img = ImageIO.read((this.getClass().getResource("/"+cheminFond)));
			 }
			 catch (Exception e){
				 e.printStackTrace();
			 }
	
	}
	/**
	 * Redefinition de la méthode paint afin d'adapter au fond
	 */
	public void paintComponent(Graphics page){
		 
		 super.paintComponent(page);

		    int h = img.getHeight(null);
		    int w = img.getWidth(null);

		    // Scale Horizontally:
		    if ( w > this.getWidth() )
		    {
		        img = img.getScaledInstance( getWidth(), -1, Image.SCALE_DEFAULT );
		        h = img.getHeight(null);
		    }

		    // Scale Vertically:
		    if ( h > this.getHeight() )
		    {
		        img = img.getScaledInstance( -1, getHeight(), Image.SCALE_DEFAULT );
		    }

		    // Center Images
		    int x = (getWidth() - img.getWidth(null)) / 2;
		    int y = (getHeight() - img.getHeight(null)) / 2;

		    // Draw it
		    page.drawImage( img, x, y, null );
	 }
}
