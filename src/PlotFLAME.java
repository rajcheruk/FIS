import java.applet.Applet;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
 
// Applet code for the "Hello, world!" example.
// This should be saved in a file named as "HelloWorld.java".
public class PlotFLAME extends Applet {
    // This method is mandatory, but can be empty (i.e., have no actual code).
	static Element elements[];
	static FLAME flame;
	static int iterations;
	
	final static int POINT_SIZE = 5;
	
	static double timeTaken = 0;
	
    public void init() {
    	
    	this.setSize(540, 540);
    	
    	elements = generateRelevantData();
    	
    	timeTaken = System.currentTimeMillis();
    	flame = new FLAME(elements, 30, 0);
    	
    	iterations = 0;
    	
    	repaint();
    }
 
    private Element[] generateData() {
    	Element elements[] = new Element[500];
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
    		elements[i] =  new Element(50+r.nextInt(10), 50+r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    	}
    	ElementAdjust.Init(20, 520, 20, 520);
    	ElementAdjust.adjust(elements);
    	return elements;
	}
    
    private Element[] generateRelevantData() {
    	Element elements[] = new Element[100];
    	Random r = new Random();
    	int i, lim = elements.length/10;
    	for(i = 0; i<lim; i++)
    	{
    		elements[i] =  new Element(25-5+r.nextInt(10), 25-5+r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<2*lim; i++)
    	{
    		elements[i] =  new Element(75-5+r.nextInt(10), 25-5+r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<3*lim; i++)
    	{
    		elements[i] =  new Element(50-5+r.nextInt(10), 75-5+r.nextInt(10));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<4*lim; i++)
    	{
    		elements[i] =  new Element(25-12+r.nextInt(25), 25-12+r.nextInt(25));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<5*lim; i++)
    	{
    		elements[i] =  new Element(75-12+r.nextInt(25), 25-12+r.nextInt(25));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<6*lim; i++)
    	{
    		elements[i] =  new Element(50-12+r.nextInt(25), 75-12+r.nextInt(25));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<7*lim; i++)
    	{
    		elements[i] =  new Element(25-25+r.nextInt(50), 25-25+r.nextInt(50));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<8*lim; i++)
    	{
    		elements[i] =  new Element(75-25+r.nextInt(50), 25-25+r.nextInt(50));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<9*lim; i++)
    	{
    		elements[i] =  new Element(50-25+r.nextInt(50), 75-25+r.nextInt(50));
    		elements[i].setColor(Color.BLACK);
    	}
    	for(; i<10*lim; i++)
    	{
    		elements[i] =  new Element(50-50+r.nextInt(100), 50-50+r.nextInt(100));
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
    	//isodata.updateClusters();
    	
    	
    	elements = flame.getElements();
    	
    	if(iterations == 0)
    	{
    		for(int i = 0; i<elements.length; i++)
    		{
    			g.setColor(elements[i].getColor());
    			g.drawOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    			g.fillOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    		}
    		flame.constructNeighborhoodGraph();
    	}
    	else if(iterations == 1)
    	{    		
    		double distances[][] = flame.getDistances();
    		for(int i = 0; i<elements.length; i++)
    		{
    			for(int j = 1; j <= flame.getkNN(); j++)
    			{
    				g.setColor(new Color(128, 128, 128));
    				g.drawLine((int)elements[i].getX(), (int)elements[i].getY(),
    						(int)elements[(int)(distances[i][j]%1000)].getX(), (int)elements[(int)distances[i][j]%1000].getY());
    			}
    		}
    		for(int i = 0; i<elements.length; i++)
    		{
    			g.setColor(elements[i].getColor());
    			g.drawOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    			g.fillOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    		}
    		flame.estimateDensity();
    		flame.classifyObjects();
    	}
    	else //if(iterations == 2)
    	{
    		double distances[][] = flame.getDistances();
    		for(int i = 0; i<elements.length; i++)
    		{
    			for(int j = 1; j <= flame.getkNN(); j++)
    			{
    				g.setColor(new Color(128, 128, 128));
    				g.drawLine((int)elements[i].getX(), (int)elements[i].getY(),
    						(int)elements[(int)(distances[i][j]%1000)].getX(), (int)elements[(int)distances[i][j]%1000].getY());
    			}
    		}
    		for(int i = 0; i<elements.length; i++)
    		{
    			g.setColor(elements[i].getColor());
    			g.drawOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    			g.fillOval((int)elements[i].getX(), (int)elements[i].getY(), POINT_SIZE, POINT_SIZE);
    		}
    		flame.localApproximation();
    	}
    	
    	if(iterations < 5)
    	{

    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		iterations++;
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