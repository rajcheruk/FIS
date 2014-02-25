import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class FLAME {
	
	private ArrayList<Element> means;
	private Element elements[];
	private int kNN;
	private double distances[][];
	private int outlierThresh;
	
	private int noOfColorsUsed;

	double stdDeviation[] = new double[100];
	
	
	private static ArrayList<Color> colors = new ArrayList<Color>();
	static{
									colors.add(new Color(128, 128, 128));
									colors.add(new Color(  0,   0, 255));
									colors.add(new Color(  0, 255,   0));
									colors.add(new Color(255,   0,   0));
									colors.add(new Color(  0, 255, 255));
									colors.add(new Color(255,   0, 255));
									colors.add(new Color(255, 255,   0));
									colors.add(new Color(  0, 64, 192));
									colors.add(new Color(64,   0, 192));
									colors.add(new Color(64, 192,   0));
									colors.add(new Color(  0, 192, 64));
									colors.add(new Color(192,   0, 64));
									colors.add(new Color(192, 64,   0));
									colors.add(new Color(  0, 128, 255));
									colors.add(new Color(128,   0, 255));
									colors.add(new Color(128, 255,   0));
									colors.add(new Color(  0, 255, 128));
									colors.add(new Color(255,   0, 128));
									colors.add(new Color(255, 128,   0));
									colors.add(new Color(  0, 128, 128));
									colors.add(new Color(128,   0, 128));
									colors.add(new Color(128, 128,   0));
									colors.add(new Color(  0, 128, 128));
									colors.add(new Color(128,   0, 128));
									colors.add(new Color(128, 128,   0));
									colors.add(new Color(  0, 64, 64));
									colors.add(new Color(64,   0, 64));
									colors.add(new Color(64, 64,   0));
									colors.add(new Color(  0, 64, 64));
									colors.add(new Color(64,   0, 64));
									colors.add(new Color(64, 64,   0));
									colors.add(new Color(  0, 192, 192));
									colors.add(new Color(192,   0, 192));
									colors.add(new Color(192, 192,   0));
									colors.add(new Color(  0, 192, 192));
									colors.add(new Color(192,   0, 192));
									colors.add(new Color(192, 192,   0));

	}
		
	FLAME(Element elements[], int kNN, int outlierThresh)
	{

		means = new ArrayList<Element>();
		this.elements = elements;
		this.kNN = kNN;
		this.outlierThresh = outlierThresh;
		distances = new double[elements.length][elements.length];
		noOfColorsUsed = 0;
		
	}
	
	public void constructNeighborhoodGraph()
	{
		for(int i = 0; i < elements.length; i++)
		{
			for(int j = 0; j < elements.length; j++)
			{
				distances[i][j] = (int)distance( elements[i], elements[j] )*1000 + j;
			}
		}
		for(int i = 0; i < elements.length; i++)
			Arrays.sort(distances[i]);
	}
	
	public void estimateDensity()
	{
		double maxDensity = -32768;
		for(int i = 0; i < elements.length; i++)
		{
			double sum = 0;
			for(int j = 1; j <= kNN; j++)
			{
				sum += distances[i][j]/1000;
			}
			elements[i].setDensity( sum/kNN );
			if( sum/kNN > maxDensity)
				maxDensity = sum/kNN;
		}
		
		for(int i = 0; i < elements.length; i++)
		{
			elements[i].setDensity( 1 - elements[i].getDensity()/maxDensity );
		}
	}
	
	public void classifyObjects()
	{
		for(int i = 0; i < elements.length; i++)
		{
			if(elements[i].getDensity() < outlierThresh)
			{
				elements[i].setColor(colors.get(0));
				elements[i].setClusterNo(-1);
			}
			else
			{
				
					boolean higher = true, lower = true;
					for(int j = 1; j <= kNN; j++)
					{
						if( elements[i].getDensity() < elements[(int)(distances[i][j]%1000)].getDensity() )
						{
							higher = false;
						}
						if( elements[i].getDensity() > elements[(int)(distances[i][j]%1000)].getDensity() )
						{
							lower = false;
						}
					}
					if(higher)
					{
						noOfColorsUsed++;
						elements[i].setColor(colors.get(noOfColorsUsed));
						elements[i].setClusterNo(noOfColorsUsed);
					}
					if(lower)
					{
						elements[i].setColor(colors.get(0));
						elements[i].setClusterNo(-1);
					}
				
			}
		}
	}
	
	public void localApproximation()
	{
		for(int i = 0; i < elements.length; i++)
		{
			if(elements[i].getClusterNo() == 0 )//|| elements[i].getClusterNo() >= 9)
			{
				double red=0, green=0, blue=0, cluster=0;
				int count = 0;
				for(int j = 1; j <= kNN; j++)
				{
					if( elements[(int)(distances[i][j]%1000)].getClusterNo() != 0 )
					{
						red += elements[(int)(distances[i][j]%1000)].getColor().getRed();
						green += elements[(int)(distances[i][j]%1000)].getColor().getGreen();
						blue += elements[(int)(distances[i][j]%1000)].getColor().getBlue();
						
						count++;
					}
					cluster += Math.abs(elements[(int)(distances[i][j]%1000)].getClusterNo());
				}
				
				red = red/count;//*elements[i].getDensity();
				green = green/count;//*elements[i].getDensity();
				blue = blue/count;//*elements[i].getDensity();
				elements[i].setColor( new Color( (int)red, (int)green, (int)blue ));
				elements[i].setClusterNo((int)cluster*10);
			}
		}
	}

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element elements[]) {
		this.elements = elements;
	}

	public ArrayList<Element> getMeans() {
		return means;
	}

	public void setMeans(ArrayList<Element> means) {
		this.means = means;
	}
	
	public int getkNN()
	{
		return kNN;
	}

	public double[][] getDistances() {
		return distances;
	}
	
	private double distance(Element element1, Element element2) {
		return Math.sqrt( ( element1.getX() - element2.getX() ) * ( element1.getX() - element2.getX() ) + ( element1.getY() - element2.getY() ) * ( element1.getY() - element2.getY() ) );
	}
		
	private boolean same(Color color1, Color color2)
	{
		return (color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue());
	}

}
