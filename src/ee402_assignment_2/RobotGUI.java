package ee402_assignment_2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class RobotGUI extends Frame implements ActionListener {	
	public Button rotateRight, rotateLeft;
	String speed = "Slow";
	int speedFactor=1;
	CheckboxGroup cbg1;
	Checkbox cb1,cb2;
	Label l;
	int rotateValue=540;
	String robotID = "001234";
	String direction = "forward";
	Client client = new Client("localhost");
	DateTimeService dateTimeNow = new DateTimeService()  ;
	public RobotGUI() {
		int height = 500;
		int width = 500;
		setTitle("Robot Control");
		setSize(height,width);
		setVisible(true);
		setLayout (new FlowLayout ());
		Button rotateRight = new Button("rotate right");
		//rotateRight.setBounds(5,475,125,25);
		rotateRight.addActionListener(this);
		
		Button rotateLeft = new Button("rotate left");
		//rotateLeft.setBounds(125,475,125,25);
		rotateLeft.addActionListener(this);
		
		Button u = new Button("forward");
		//u.setBounds(250,475,125,25);
		u.addActionListener(this);
		
		Button d = new Button("backward");
		//d.setBounds(375,475,125,25);
		d.addActionListener(this);
		add(d);
		
		l=new Label ("Set Speed : ");
		cbg1=new CheckboxGroup(); 
        cb1=new Checkbox ("Fast", cbg1, false); 
        cb2=new Checkbox ("Slow", cbg1, true);
        cb1.addItemListener (new itl ()); 
        cb2.addItemListener (new itl ()); 
        //add(cbg1);
		
        add(rotateRight);
        add(rotateLeft);
        add(u);
        add(d);
        add(l);
        add(cb1);
        add(cb2);
        
		setResizable(false);
		setLocationRelativeTo(null);

		this.addWindowListener (new WinAdap ());
		
	
	}
	class itl implements ItemListener
    {
        public void itemStateChanged (ItemEvent ie)
        {
            Object obj=ie.getItemSelectable (); 
            Checkbox cb=(Checkbox) obj;
            if (cb.getState ())
            {
                speed = cb.getLabel ();              
				if(speed == "Fast") {
                	speedFactor = 2;
                } else speedFactor = 1;
				client.send(Integer.toString(speedFactor));
                
            }
           
        }
    }
	
	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "rotate right") {
			rotateValue = rotateValue+90;
			client.send(Integer.toString(rotateValue));
			repaint();
			
		} else if (e.getActionCommand() == "rotate left") {
			rotateValue = rotateValue-90;
			if(rotateValue < 0)  rotateValue = rotateValue+360;
			client.send(Integer.toString(rotateValue));
			repaint();
		}else if(e.getActionCommand() == "forward") {
				direction = "forward";
				client.send(direction);
				repaint();
					
		} else if (e.getActionCommand() == "backward") {
				direction = "backward";
				client.send(direction);
				repaint();
				
		} else {
			System.out.println("You clicked the button "+e.getActionCommand());
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
		AffineTransform at = AffineTransform.getTranslateInstance(100,100);
		
		BufferedImage robot = LoadImage("robot.png");
		Graphics2D g2d = (Graphics2D) g;
		at.rotate(Math.toRadians(rotateValue),robot.getWidth()/2,robot.getHeight()/2);
		g2d.drawImage(robot, at, null);
		g2d.drawString("Robot ID:"+robotID, 80, 80);
		g2d.drawString("Direction:"+direction, 200, 80);
		g2d.drawString("Speed status:"+speed, 320, 80);
		g2d.drawString("ro:"+rotateValue, 430, 80);
		
	}
	
	BufferedImage LoadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;	
	}
	
	public static void main(String[] args) {
		new RobotGUI();
	}
}

