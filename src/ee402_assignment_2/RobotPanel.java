package ee402_assignment_2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RobotPanel extends Panel {
	int rotateValue = 540;
	int x =100,y=100;	
	int number = 0;
	
	public RobotPanel(int n) {
		// TODO Auto-generated constructor stub
		super();
		number = n;
	}

	public RobotPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics g) {
		//map.put("robot",new XYPositions(x, y));
		//System.out.println("Initial Mappings are: " + map.get("robot"));
		AffineTransform at = AffineTransform.getTranslateInstance(100,100);
		BufferedImage robot = LoadImage("robot.png");
		Graphics2D g2d = (Graphics2D) g;
		at.scale(0.3,0.3);
		at.translate(x,y);
		at.rotate(Math.toRadians(rotateValue),robot.getWidth()/2,robot.getHeight()/2);
		//g2d.drawImage(robot, at,null);
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(6f));
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawRect(50, 50, robot.getWidth(), robot.getHeight());
		g2d.drawString(String.format("%d", number), 50+robot.getWidth()/2, 50+robot.getHeight()/2);
		
		
		
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

}
