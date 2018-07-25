package maze;

import java.awt.Image;
import javax.swing.ImageIcon;


public class Player {
	private double tileX,tileY;
	private Image player;
	public boolean pressOut = false;
	
	public Player() {
		ImageIcon img= new ImageIcon("risorse/images/player.png");
		player = img.getImage();
		
		tileX = 32;
		tileY = 32;		
		
	}
	
	public Image getPlayer() {
		return player;
	}
	
	
	public double getTileX() {
		return tileX;
	}
	public double getTileY() {
		return tileY;
	}
	
	public void move(double dx, double dy) {
		tileX += dx;
		tileY += dy;
	}
}
