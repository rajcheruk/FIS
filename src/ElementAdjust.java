
public class ElementAdjust {
	static int xMin = 0;
	static int yMin = 0;
	static int xMax = 500;
	static int yMax = 500;
	static int xSpan, ySpan;
	
	public static void Init(int xMin, int xMax, int yMin, int yMax)
	{
		ElementAdjust.xMin = xMin;
		ElementAdjust.yMin = yMin;
		ElementAdjust.xMax = xMax;
		ElementAdjust.yMax = yMax;
		
		xSpan = xMax - xMin;
		ySpan = yMax - yMin;
	}
	
	public static void adjust(Element elements[])
	{
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
		
		double dataSpanX = maxX - minX, dataSpanY = maxY - minY;
		
		for(int i = 0; i<elements.length; i++)
		{
			double x = (elements[i].getX() - minX) * xSpan / dataSpanX + xMin ;
			double y = (elements[i].getY() - minY) * ySpan / dataSpanY + yMin ;
			
			elements[i].setX(x);
			elements[i].setY(y);
			
		}
	}
}
