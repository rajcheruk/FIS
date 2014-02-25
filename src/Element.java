import java.awt.Color;

public class Element {
	
	private double x;
	private double y;
	private int index;
	private Color color;
	private int clusterNo;
	private double density;
	
	Element(double x, double y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	Element(double x, double y, Color color)
	{
		this.setX(x);
		this.setY(y);
		this.setColor(color);
		this.setClusterNo(0);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(! (other instanceof Element))
			return false;
		
		return ( this.getX() == ((Element)other).getX() && this.getY() == ((Element)other).getY() );
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(int clusterNo) {
		this.clusterNo = clusterNo;
	}
	
	public String toString()
	{
		return clusterNo+" with " + "X = " + x + " Y = " + y;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}
}
