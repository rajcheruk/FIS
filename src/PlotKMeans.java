import java.applet.Applet;
import java.awt.*;
import java.util.Random;
 
// Applet code for the "Hello, world!" example.
// This should be saved in a file named as "HelloWorld.java".
public class PlotKMeans extends Applet {
    // This method is mandatory, but can be empty (i.e., have no actual code).
	static Element elements[];
	static KMeans kmeans;
	
	final static int POINT_SIZE = 5;
	
	static double timeTaken = 0;
	static int iterations = 0;
	
    public void init() {
    	
    	this.setSize(540, 540);
    	
    	elements = generateInterestingData();
    	
    	timeTaken = System.currentTimeMillis();
    	kmeans = new KMeans(elements, 3);
    	
    	repaint();
    }
    
    private Element[] generateData() {
    	Element elements[] = new Element[100];
    	Random r = new Random();
    	for(int i = 0; i<elements.length; i++)
    	{
    		elements[i] =  new Element(r.nextInt(100), r.nextInt(100));
    		elements[i].setColor(Color.BLACK);
    	}
    	
    	ElementAdjust.Init(20, 520, 20, 520);
    	ElementAdjust.adjust(elements);
    	return elements;
	}
    private Element[] generateInterestingData() {
    	Element elements[] = new Element[100];
    	Random r = new Random();
    	int i;
    	for(i = 0; i<10; i++)
    	{
    		elements[i] =  new Element(r.nextInt(10), r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<30; i++)
    	{
    		elements[i] =  new Element(50+r.nextInt(20), 0+r.nextInt(20));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<60; i++)
    	{
    		elements[i] =  new Element(0+r.nextInt(20), 50+r.nextInt(20));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<100; i++)
    	{
    		elements[i] =  new Element(50+r.nextInt(50), 50+r.nextInt(50));
    		elements[i].setColor(Color.BLACK);
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
    	
    	elements = kmeans.getElements();
    	
    	for(int i = 0; i<elements.length; i++)
    	{
    		g.setColor(elements[i].getColor());
    		g.drawOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    		g.fillOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    	}
    	
    	Element means[] = kmeans.getMeans();
    	for(int i = 0; i<means.length; i++)
    	{
    		g.setColor(means[i].getColor());
    		g.drawOval((int)means[i].getX() - POINT_SIZE, (int)means[i].getY() - POINT_SIZE, 2*POINT_SIZE, 2*POINT_SIZE);
    		g.fillOval((int)means[i].getX() - POINT_SIZE/2, (int)means[i].getY() - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
    	}
    	
    	
    	kmeans.updateClusters();
    	iterations++;
    	if( kmeans.updateMeans() )
    	{

    		try {
    			Thread.sleep(1000);
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
    	}
    }
}