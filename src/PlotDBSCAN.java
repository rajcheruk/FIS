import java.applet.Applet;
import java.awt.*;
import java.util.Random;
 
// Applet code for the "Hello, world!" example.
// This should be saved in a file named as "HelloWorld.java".
public class PlotDBSCAN extends Applet {
    // This method is mandatory, but can be empty (i.e., have no actual code).
	static Element elements[];
	static DBSCAN dbscan;
	
	final static int POINT_SIZE = 5;
	final static int DIAMETER = 125;
	
	static double timeTaken = 0;
	static int iterations = 0;
	
    public void init() {
    	
    	this.setSize(540, 540);
    	
    	elements = generateInterestingData();
    	
    	timeTaken = System.currentTimeMillis();
    	dbscan = new DBSCAN(elements, 3, DIAMETER/2);
    	
    	repaint();
    }
    private Element[] generateData() {
    	Element elements[] = new Element[50];
    	Random r = new Random();
    	for(int i = 0; i<elements.length; i++)
    	{
    		elements[i] =  new Element(r.nextInt(100), r.nextInt(100));
    		elements[i].setColor(Color.BLACK);
    		elements[i].setIndex(i);
    	}
    	
    	ElementAdjust.Init(20, 520, 20, 520);
    	ElementAdjust.adjust(elements);
    	return elements;
	}
    private Element[] generateInterestingData() {
    	Element elements[] = new Element[100];
    	Random r = new Random();
    	int i;
    	int lim = elements.length/10;
    	for(i = 0; i<lim; i++)
    	{
    		elements[i] =  new Element(r.nextInt(10), r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    		elements[i].setIndex(i);
    	}
    	for(; i<3*lim; i++)
    	{
    		elements[i] =  new Element(50+r.nextInt(20), 0+r.nextInt(20));
    		elements[i].setColor(Color.BLACK);
    		elements[i].setIndex(i);
    	}
    	for(; i<6*lim; i++)
    	{
    		elements[i] =  new Element(0+r.nextInt(20), 50+r.nextInt(20));
    		elements[i].setColor(Color.BLACK);
    		elements[i].setIndex(i);
    	}
    	for(; i<10*lim; i++)
    	{
    		elements[i] =  new Element(50+r.nextInt(50), 50+r.nextInt(50));
    		elements[i].setColor(Color.BLACK);
    		elements[i].setIndex(i);
    	}
    	ElementAdjust.Init(20, 520, 20, 520);
    	ElementAdjust.adjust(elements);
    	return elements;
	}
    // This method is mandatory, but can be empty.(i.e., have no actual code).
    public void stop() {
    	
    }
 
    // Print a message on the screen (x=20, y=10).
    public void paint(Graphics g) {
    	/*
        g.drawString("Hello, world!", 20, 10);
 
        // Draws a circle on the screen (x=40, y=30).
        g.drawArc(40, 30, 20, 20, 0, 360);*/
    	//draw axes
    	//X
    	g.drawLine(20, 20, 520, 20);
    	g.drawLine(520, 20, 515, 15);
    	g.drawLine(520, 20, 515, 25);
    	g.drawString("X", 520, 25);
    	
    	//Y
    	g.drawLine(20, 20, 20, 520);
    	g.drawLine(20, 520, 15, 515);
    	g.drawLine(20, 520, 25, 515);
    	g.drawString("Y", 15, 530);
    	//dbscan.startClustering();
    	elements = dbscan.getElements();
    	
    	for(int i = 0; i<elements.length; i++)
    	{
    		g.setColor(elements[i].getColor());
    		g.drawOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    		g.drawOval((int)elements[i].getX()-DIAMETER/2, (int)elements[i].getY()-DIAMETER/2, DIAMETER, DIAMETER);
    		g.fillOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    	}
    	
    	//dbscan.startClustering();
    	iterations++;
    	
    	if( dbscan.startClusteringAnimationSlow() )
    	{

    		try {
    			Thread.sleep(50);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}

    		repaint();
    	}
    	else
    	{
    		timeTaken = System.currentTimeMillis() - timeTaken;
    		System.out.println("Time Taken" + timeTaken);
    		System.out.println("Iterations" + iterations);
    		
    		g.setColor(Color.BLACK);
    		g.drawString("Final Clusters", 0, 10);
    		int count = 0;
    		for(int i = 0; i<elements.length; i++)
    		{
    			if(elements[i].getColor().equals(new Color(128, 128, 128)))
    				count++;
    		}
    		System.out.println("Outliers"+count);
    	}
    }
}