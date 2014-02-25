import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class ISODATA {
	
	private ArrayList<Element> means;
	private Element elements[];
	private int stdDevThresh;
	private int distThresh;
	private int noOfColorsUsed;

	double stdDeviation[] = new double[100];
	
	private int iteration;
	private static ArrayList<Color> colors = new ArrayList<Color>();
	static{
	colors.add(new Color(  0,   0, 255));
									colors.add(new Color(  0, 255,   0));
									colors.add(new Color(  0, 255, 255));
									colors.add(new Color(255,   0,   0));
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
		
	ISODATA(Element elements[], int noOfMeans, int stdDevThresh, int distThresh)
	{

		means = new ArrayList<Element>();
		this.elements = elements;
		iteration = 0;
		this.stdDevThresh = stdDevThresh;
		this.distThresh = distThresh;
		
		double maxX = -32768, minX = 32767, maxY = -32768, minY = 32767;
		
		//set the range
		for(int i = 0; i<elements.length; i++)
		{
			if(elements[i].getX() < minX)
				minX = elements[i].getX();
			else if(elements[i].getX() > maxX)
				maxX = elements[i].getX();
			
			if(elements[i].getY() < minY)
				minY = elements[i].getY();
			else if(elements[i].getY() > maxY)
				maxY = elements[i].getY();			
		}
		
		//set new means
		Random r = new Random();
		for(int i = 0; i<noOfMeans; i++)
		{
			double x = minX + r.nextInt((int)(maxX - minX));
			double y = minY + r.nextInt((int)(maxY - minY));
			
			means.add(new Element(x, y));
			means.get(i).setClusterNo(means.size()-1);
			means.get(i).setColor(colors.get(means.get(i).getClusterNo()));
			noOfColorsUsed++;
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

	public void updateClusters()
	{
		//if(iteration == 0)
		{
			
			
			
			//decide clusters for elements
			for(int i = 0; i<elements.length; i++)
			{
				double d = distance(elements[i], means.get(0));
				elements[i].setColor(means.get(0).getColor());
				elements[i].setClusterNo(means.get(0).getClusterNo());
				
				for(int j = 1; j<means.size(); j++)
				{
					if( distance(elements[i], means.get(j)) < d)
					{
						d = distance(elements[i], means.get(j));
						elements[i].setColor(means.get(j).getColor());
						elements[i].setClusterNo(means.get(j).getClusterNo());
					}
				}
			}
			
			//adjustMeans();

			iteration++;
		}

		
		
	}

	private void adjustMeans() {
		for(int j = 0; j<means.size(); j++)
		{
			double sumX=0, sumY=0, count=0;
			for(int i = 0; i<elements.length; i++)
			{
				if(elements[i].getClusterNo() == means.get(j).getClusterNo())
				{
					sumX += elements[i].getX();
					sumY += elements[i].getY();
					count++;
				}
			}
			if(count != 0)
			{
				means.get(j).setX(sumX/count);
				means.get(j).setY(sumY/count);
			}
		}
		
	}

	private double distance(Element element1, Element element2) {
		return Math.sqrt( ( element1.getX() - element2.getX() ) * ( element1.getX() - element2.getX() ) + ( element1.getY() - element2.getY() ) * ( element1.getY() - element2.getY() ) );
	}
	
	public boolean updateMeans()
	{
		ArrayList<Element> oldMeans = new ArrayList<Element>();
		for(Element e:means)
			oldMeans.add( new Element(e.getX(), e.getY()));
		
		double avgDistance = 0;
		for(int j = 0; j<means.size() - 1; j++)
			for(int i = j+1; i<means.size(); i++)
				avgDistance += distance(means.get(i), means.get(j)) ;
		avgDistance = avgDistance/(means.size()*(means.size()-1)/2);
		
		System.out.println("\nAverage distance "+avgDistance);
		//if(iteration > 2) return false;
		
		
		
		ArrayList<Element> toBeDeleted = new ArrayList<Element>();
		//delete empty clusters
		for(int j = 0; j<means.size(); j++)
		{
			int count = 0;
			for(int k = 0; k<elements.length; k++)
				if(elements[k].getClusterNo() == means.get(j).getClusterNo())
					count++;

			System.out.println("Cluster " + means.get(j).getClusterNo() + " has "+count+" elements");
			if(count==0)
			{
				toBeDeleted.add(means.get(j));
				colors.add(means.get(j).getColor());
			}
		}		
		System.out.print(toBeDeleted);
		for(Object o: toBeDeleted)
			means.remove(o);
		
		toBeDeleted = new ArrayList<Element>();
		//delete if no elements
		for(int j = 0; j<means.size(); j++)
		{
			if(Double.isNaN(stdDeviation[j]) )
			{
				colors.add(means.get(j).getColor());
				toBeDeleted.add(means.get(j));
			}
		}
		for(Object o: toBeDeleted)
			means.remove(o);
		
		
		
		//////////////////////////////
		/////////////////////////////
		//calculate average for standard deviation
		double avg[] = new double[means.size()];
		int count[] = new int[means.size()];
		double distanceFromAvg[] = new double[elements.length];
		double distance[] = new double[elements.length];
		int clusterNo[] = new int[elements.length];
		
		for(int i = 0; i<elements.length; i++)
		{			
			for(int j = 0; j<means.size(); j++)
			{
				if(elements[i].getClusterNo() == means.get(j).getClusterNo())
				{
					clusterNo[i] = j;
					distance[i] = distance(elements[i], means.get(j));
				}
			}
		}
		for(int j = 0; j<means.size(); j++)
		{
			for(int i = 0; i<elements.length; i++)
			{
				if(elements[i].getClusterNo() == means.get(j).getClusterNo())
				{
					avg[j] += distance[i];
					count[j]++;
				}
			}
			avg[j] = avg[j]/count[j];
		}
		//calculate square of distance from avg
		for(int i = 0; i<elements.length; i++)
		{
			distanceFromAvg[i] = avg[clusterNo[i]] - distance[i];
			distanceFromAvg[i] = distanceFromAvg[i]*distanceFromAvg[i];
		}
		//calculate variance = avg of square of distance from avg
		for(int j = 0; j<means.size(); j++)
		{
			for(int i = 0; i<elements.length; i++)
			{
				if(elements[i].getClusterNo() == means.get(j).getClusterNo())
					stdDeviation[j] += distanceFromAvg[i];
			}
			stdDeviation[j] = stdDeviation[j]/count[j];
			//convert variance to standard deviation
			stdDeviation[j] = Math.sqrt(stdDeviation[j]);
			System.out.print(stdDeviation[j]+" ");
		}
		
		
		//split clusters if > stdDevThresh
		int limit = means.size();
		toBeDeleted = new ArrayList<Element>();
		for(int j = 0; j<limit; j++)
		{
			if(stdDeviation[j] > stdDevThresh)
			{
				double minX = 32767, minY = 32767, maxX = -32768, maxY = -32768;
				for(int i = 0; i<elements.length; i++)
				{
					if(elements[i].getClusterNo() == means.get(j).getClusterNo())
					{
						if(elements[i].getX() < minX)
							minX = elements[i].getX();
						if(elements[i].getX() > maxX)
							maxX = elements[i].getX();
						
						if(elements[i].getY() < minY)
							minY = elements[i].getY();
						if(elements[i].getY() > maxY)
							maxY = elements[i].getY();
					}
				}
				
				double spanX = maxX-minX, spanY = maxY-minY;
				means.add(new Element(minX + (int)(spanX/4), minY + (int)(spanY/4) ));
				means.get(means.size()-1).setClusterNo(noOfColorsUsed++);
				means.get(means.size()-1).setColor(colors.get(means.size()-1));
				means.add(new Element(minX + (int)(spanX/4), minY + (int)(spanY*3/4) ));
				means.get(means.size()-1).setClusterNo(noOfColorsUsed++);
				means.get(means.size()-1).setColor(colors.get(means.size()-1));
				means.add(new Element(minX + (int)(spanX*3/4), minY + (int)(spanY/4) ));
				means.get(means.size()-1).setClusterNo(noOfColorsUsed++);
				means.get(means.size()-1).setColor(colors.get(means.size()-1));
				means.add(new Element(minX + (int)(spanX*3/4), minY + (int)(spanY*3/4) ));
				means.get(means.size()-1).setClusterNo(noOfColorsUsed++);
				means.get(means.size()-1).setColor(colors.get(means.size()-1));
				
				System.out.println("Splitting"+means.get(j).getClusterNo());
				colors.add(means.get(j).getColor());
				toBeDeleted.add(means.get(j));
			}
		}

		for(Object o: toBeDeleted)
			means.remove(o);
		
		//merge if <=distThresh
		toBeDeleted = new ArrayList<Element>();
		for(int j = 0; j < means.size()-1; j++)
		{
			for(int i = j+1; i < means.size(); i++)
			{
				if(distance(means.get(i), means.get(j)) <= distThresh)
				{
					for(int k = 0; k<elements.length; k++)
					{
						if(elements[k].getClusterNo() == means.get(i).getClusterNo() )
						{
							elements[k].setColor(means.get(j).getColor());
							elements[k].setClusterNo(means.get(j).getClusterNo());
						}
					}
					colors.add(means.get(i).getColor());
					System.out.println("Merging" + means.get(j).getClusterNo() +" with "+means.get(i).getClusterNo());
					toBeDeleted.add(means.get(i));
				}
			}
		}
		for(Object o: toBeDeleted)
			means.remove(o);
		
		
		adjustMeans();
		
		if(means.size() != oldMeans.size())
			return true;
		else
		{
			boolean changed = false;
			for(int i = 0; i < means.size(); i++)
				if(means.get(i).getX() != oldMeans.get(i).getX() || means.get(i).getY() != oldMeans.get(i).getY())
					changed = true;
			return changed;
		}
	}

	private boolean same(Color color1, Color color2)
	{
		return (color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue());
	}
	
}
