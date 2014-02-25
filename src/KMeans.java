import java.awt.Color;
import java.util.Random;


public class KMeans {
	
	private Element means[];
	private Element elements[];
		
	KMeans(Element elements[], int noOfMeans)
	{
		means = new Element[noOfMeans];
		this.elements = elements;
		
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
		for(int i = 0; i<means.length; i++)
		{
			double x = minX + r.nextInt((int)(maxX - minX));
			double y = minY + r.nextInt((int)(maxY - minY));
			
			means[i] = new Element(x, y);
			//System.out.println(x+" "+y);
		}
		
		setColorsForMeans();
	}

	private void setColorsForMeans() {
		if(means.length >= 1)
			means[0].setColor(new Color(0, 0, 255));
		if(means.length >= 2)
			means[1].setColor(new Color(0, 255, 0));
		if(means.length >= 3)
			means[2].setColor(new Color(0, 255, 255));
		if(means.length >= 4)
			means[3].setColor(new Color(255, 0, 0));
		if(means.length >= 5)
			means[4].setColor(new Color(255, 0, 255));
		if(means.length >= 6)
			means[5].setColor(new Color(255, 255, 0));
	}

	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element elements[]) {
		this.elements = elements;
	}

	public Element[] getMeans() {
		return means;
	}

	public void setMeans(Element means[]) {
		this.means = means;
	}

	public void updateClusters()
	{
		for(int i = 0; i<elements.length; i++)
		{
			double d = distance(elements[i], means[0]);
			elements[i].setColor(means[0].getColor());
			
			for(int j = 1; j<means.length; j++)
			{
				if( distance(elements[i], means[j]) < d)
				{
					d = distance(elements[i], means[j]);
					elements[i].setColor(means[j].getColor());
				}
			}
		}
	}

	private double distance(Element element1, Element element2) {
		return Math.sqrt( ( element1.getX() - element2.getX() ) * ( element1.getX() - element2.getX() ) + ( element1.getY() - element2.getY() ) * ( element1.getY() - element2.getY() ) );
	}
	
	public boolean updateMeans()
	{
		Element oldmeans[] = new Element[means.length];
		for(int j = 0; j<means.length; j++)
		{
			oldmeans[j] = new Element(means[j].getX(), means[j].getY());
		}
		
		boolean meansChanged = false;
		
		for(int j = 0; j<means.length; j++)
		{
			double sumX = 0, sumY=0, count = 0;
			for(int i = 0; i<elements.length; i++)
			{
				if(same(means[j].getColor(), elements[i].getColor()))
				{
					count++;
					sumX += elements[i].getX();
					sumY += elements[i].getY();
				}
			}
			
			sumX = (int)(sumX/count);
			sumY = (int)(sumY/count);
			
			means[j].setX(sumX);
			means[j].setY(sumY);
		}
		
		for(int j = 0; j<means.length; j++)
		{
			if( !means[j].equals(oldmeans[j]))
			{
				meansChanged = true;
				break;
			}
		}
			
		return meansChanged;
	}

	private boolean same(Color color1, Color color2)
	{
		return (color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue());
	}
	
}
