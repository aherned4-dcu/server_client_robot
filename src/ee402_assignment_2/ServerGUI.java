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

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;


@SuppressWarnings("serial")
public class ServerGUI extends Frame implements RobotCommandListener,ActionListener {	
	int rotateValue = 540;
	static int speedFactor = 1;
	int x =100,y=100;	
	Map<String,XYPositions> map = new HashMap<String,XYPositions>();
	private Frame mainFrame;
	private Panel controlPanel;
	
	public ServerGUI() {
		/*int height = 1000;
		int width = 1000;
		setTitle("Robot Locations");
		setSize(height,width);
		setVisible(true);
		Panel panel = new Panel();      
	    panel.setBackground(Color.magenta);
	    panel.setLayout(new FlowLayout());  
	    panel.setSize(1000,1000);
	    
	    panel.addMouseListener(new MouseClicker());
		//setLayout (new FlowLayout ());
		setResizable(false);
		setLocationRelativeTo(null);
		this.addWindowListener (new WinAdap ());
		ThreadedServer.start(this);
		this.addMouseListener(new MouseClicker ()); 
		*/
		mainFrame = new Frame("Java AWT Examples");
	      mainFrame.setSize(1000,1000);
	      mainFrame.setLayout(new GridLayout(2, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    

	      controlPanel = new Panel();
	      controlPanel.setLayout(new GridLayout(2,2));
	      controlPanel.setSize(1000,1000);
	      controlPanel.setBackground(Color.blue);
	      
	      
	      Button addRob = new Button("Add Robot");
		  //u.setBounds(250,475,125,25);
	      addRob.addActionListener(this);
	      mainFrame.add(addRob);
	      mainFrame.add(controlPanel);
	      mainFrame.setResizable(false);
	      mainFrame.setVisible(true);  
	   
	      
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "Add Robot") {
			addRobot(1);
			repaint();
					
		} else {
			System.out.println("You clicked the button "+e.getActionCommand());
		}
	}
	
	
	private void addRobot(int n){	         
	      RobotPanel panel = new RobotPanel(n);      
	      panel.setBackground(Color.gray);
	      //panel.setLayout(new FlowLayout()); 
	      panel.setSize(200, 200);
	     
	      panel.addMouseListener(new MouseAdapter(){
	         public void mouseClicked(MouseEvent e) {
	        	 System.out.println("Clicked ");
	         }                
	      });

	      
	      //repaint();
	      Label msglabel = new Label();
	      msglabel.setAlignment(Label.CENTER);
	      msglabel.setText("Welcome to TutorialsPoint AWT Tutorial.");
	      
	      //controlPanel.add(msglabel);
	      controlPanel.add(panel);
	   }
	
	
	
    
	class MouseClicker extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent me) {
            //super.mouseClicked(me);
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
	/*
	
	*/
	
	
	
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
