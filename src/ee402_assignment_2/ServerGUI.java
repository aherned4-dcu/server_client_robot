package ee402_assignment_2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class ServerGUI extends Frame implements RobotCommandListener {	
	int rotateValue = 540;
	static int speedFactor = 1;
	int x =100,y=100;	
	Map<String,XYPositions> map = new HashMap<String,XYPositions>();
	
	
	public ServerGUI() {
		int height = 1000;
		int width = 1000;
		setTitle("Robot Locations");
		setSize(height,width);
		setVisible(true);
		setLayout (new FlowLayout ());
		setResizable(false);
		setLocationRelativeTo(null);
		this.addWindowListener (new WinAdap ());
		ThreadedServer.start(this);
		this.addMouseListener(new MouseClicker ()); 
	}
	
	
    
	class MouseClicker extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent me) {
            super.mouseClicked(me);
            System.out.println("Clicked ");//+me.getPoint());
            setBackground(Color.RED);
            repaint();
		} 
		public void mouseReleased(MouseEvent me) {
            //super.mouseClicked(me);
            System.out.println("Clicked ");//+me.getPoint());
            setBackground(Color.RED);
            repaint();
		} 
	}
	
	class WinAdap extends WindowAdapter
    {
        public void windowClosing (WindowEvent we)
        {
            System.exit (0);
        }
    }
	
	public void paint(Graphics g) {
		map.put("robot",new XYPositions(x, y));
		System.out.println("Initial Mappings are: " + map.get("robot"));
		AffineTransform at = AffineTransform.getTranslateInstance(100,100);
		BufferedImage robot = LoadImage("robot.png");
		Graphics2D g2d = (Graphics2D) g;
		at.scale(0.3,0.3);
		at.translate(x,y);
		at.rotate(Math.toRadians(rotateValue),robot.getWidth()/2,robot.getHeight()/2);
		g2d.drawImage(robot, at,null);
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(6f));
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(50, 50, 900, 900);
		
		for (Entry<String, XYPositions> entry : map.entrySet()) {
		    System.out.println(entry.getKey().toString() + "/" + entry.getValue().x+","+entry.getValue().y);
		    g2d.drawOval(x, y,1,1);
		    System.out.println(robot.getWidth()/2 + "/" + robot.getHeight()/2);
		}
		
		
	}
	
	BufferedImage LoadImage(String filename) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	static int[] forward_calc(int angle,int x,int y) {
		angle = Math.abs(angle);
		int[] ans = new int[2];
		if(angle == 90) {
			ans[0]=x-50;
			ans[1]=y;
		} else if (angle == 0) {
			ans[0]=x;
			ans[1]=y+50;
		} else if (angle == 270) {
			ans[0]=x+50;
			ans[1]=y;	
		} else {
			ans[0]=x;
			ans[1]=y-50;		
		}
		return ans;
		
	}
	
    static int[] backward_calc(int angle,int x,int y) {
    	angle = Math.abs(angle);
		int[] ans = new int[2];
		if(angle == 90) {
			ans[0]=x+50;
			ans[1]=y;
		} else if (angle == 0) {
			ans[0]=x;
			ans[1]=y-50;
		} else if (angle == 270) {
			ans[0]=x-50;
			ans[1]=y;	
		} else {
			ans[0]=x;
			ans[1]=y+50;		
		}
		return ans;
	}
    
	@Override
	public void onCommandReceived(String s) {
		int directionOfFace = rotateValue%360;
		int[] coords;
		if(s.contains("forward")) {
			coords = forward_calc(directionOfFace,x,y);
			x=coords[0];
			y=coords[1];
		} else if (s.contains("backward")) {
			coords = backward_calc(directionOfFace,x,y);
			x=coords[0];
			y=coords[1];
		} else if (s.contentEquals("1")) {
			speedFactor=1;
		
		} else if (s.contentEquals("2")) {
			speedFactor=2;
		} 
		
		else {
			rotateValue = Integer.parseInt(s.trim());
		};
		repaint();
		
	}
	
	
	
	public static void main(String[] args) {
		new ServerGUI();
	}
}
