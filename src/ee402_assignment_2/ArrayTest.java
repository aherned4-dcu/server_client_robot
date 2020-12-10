package ee402_assignment_2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;



class ArrayTest {   
	addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent me) {
            super.mouseClicked(me);
            for (Shape s : shapes) {
                
                if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                    
                    //we can either just print out the object class name
                    System.out.println("Clicked a "+s.getClass().getName());
                    
                    //or check the shape class we are dealing with using instance of with nested if
                    if (s instanceof Rectangle2D) {
                        System.out.println("Clicked a rectangle");
                    } else if (s instanceof Ellipse2D) {
                        System.out.println("Clicked a circle");
                    }
                    
                }
            }
        }
    })
}