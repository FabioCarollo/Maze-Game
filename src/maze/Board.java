package maze;

import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

/** 
 *
 * @author filippotessaro
 */



public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
	private Map m;
	private Player p;
	private String Message = "";
	private int counter;
	private boolean drag = false;
	private boolean stopGame = false;


	public int getCounter() {
		return counter;
	}
	public String getMessage() {
		return Message;
	}
	public void setstopGameTrue() {
		stopGame = true;
		timer.stop();
	}
	public void setstopGameFalse() {
		stopGame = false;
		timer.start();
	}

	public boolean getstopGame() {
		return stopGame;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public Board(){
		m= new Map();
		p= new Player();
		Ml m = new Ml();
		counter = 0;

		addMouseMotionListener(m);
		addMouseListener(m);
		setFocusable(true);

		timer = new Timer(25,this);
		timer.start();
	}

	public Board(int livello){
		m= new Map(livello);
		p= new Player();
		Ml m = new Ml();
		counter = 0;

		addMouseMotionListener(m);
		addMouseListener(m);
		setFocusable(true);

		timer = new Timer(25,this);
//		timer.start();
	}
	

	public void actionPerformed(ActionEvent e){

		int x=(int)(p.getTileX()/32);
		int y=(int)(p.getTileY()/32);
		//        if(m.getMap(x,y).equals("f")) {
		if(m.getMap(x+1, y+1).equals("f") || 
				m.getMap(x, y).equals("f") || 
				m.getMap(x+1, y).equals("f") || 
				m.getMap(x, y+1).equals("f")) {
			Message = "Winner";
			stopGame = true;
//			timer.stop();
		}
		repaint();
	}

	public void paint(Graphics g){
		super.paint(g);
		for(int y=0; y<14;y++){
			for(int x=0;x<14;x++){
				if(m.getMap(x, y).equals("g")){
					g.drawImage(m.getGrass(),x*32,y*32,null);
				}
				if(m.getMap(x, y).equals("w")){
					g.drawImage(m.getWall(),x*32,y*32,null);
				}
				if(m.getMap(x, y).equals("f")){
					g.drawImage(m.getFinish(),x*32,y*32,null);
				}
			}
		}
		g.drawString(Message, 50, 50);
		g.drawString(String.valueOf(counter),300,300);
		g.drawImage(p.getPlayer(), (int)p.getTileX(), (int)p.getTileY(), null);

	}

	public class Ml implements MouseListener,MouseMotionListener{

		public void mousePressed(MouseEvent e) {
			double x=p.getTileX();
			double y=p.getTileY();
			drag = true;
			if(!stopGame) {
				if(!(e.getX()>=x && e.getX()<=(x+32))&&(e.getY()>=y && e.getY()<=(y+32))) {
					p.pressOut = true;
					System.out.println("Premuto fuori dall'oggetto");
				}
			}


		}

		public void mouseDragged(MouseEvent e) {
			if(!stopGame) {
				if(drag) {
					if(!p.pressOut) {
						System.out.println("getX:"+e.getX()+"getY:"+e.getY());
						p.move(e.getX()-p.getTileX()-16,e.getY()-p.getTileY()-16);
						int x=(int)(p.getTileX()/32);
						int y=(int)(p.getTileY()/32);

						if((m.getMap(x+1, y+1).equals("w") || 
								m.getMap(x, y).equals("w") || 
								m.getMap(x+1, y).equals("w") || 
								m.getMap(x, y+1).equals("w"))) {
							counter++;
							drag = false;
							return;
						}

					}else {
						System.out.println("Fuori posizione");

					}
				}
			}

		}

		public void mouseReleased(MouseEvent e) {
			double x=p.getTileX();
			double y=p.getTileY();
			if(!stopGame) {
				if(!((x)<(e.getX()) && (x+32)>(e.getX())) && ((y)<(e.getY()) && (y+32)>(e.getY()))) {
					p.pressOut = false;
					System.out.println("Premuto fuori dall'oggetto");
				}
			}
		}
		public void mouseMoved(MouseEvent e) {	
		}
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		} 	
	} 
}
    


