import java.awt.Color;
import java.util.ArrayList;

public class DBSCAN {
	private Element elements[];
	private int minPts;
	private double eps;
	private boolean visited[];
	private static int count = 0;
	private static int cIndex = 0;
	private static boolean inMethodCall = false;
	private static int loopVar;
	private static ArrayList<Element> innerList;
	
	private static Color colors[] = {new Color(128, 128, 128),
									new Color(  0,   0, 255),
									new Color(  0, 255,   0),
									new Color(  0, 255, 255),
									new Color(255,   0,   0),
									new Color(255,   0, 255),
									new Color(255, 255,   0),
									new Color(  0, 64, 192),
									new Color(64,   0, 192),
									new Color(64, 192,   0),
									new Color(  0, 192, 64),
									new Color(192,   0, 64),
									new Color(192, 64,   0),
									new Color(  0, 128, 255),
									new Color(128,   0, 255),
									new Color(128, 255,   0),
									new Color(  0, 255, 128),
									new Color(255,   0, 128),
									new Color(255, 128,   0),
									new Color(  0, 128, 128),
									new Color(128,   0, 128),
									new Color(128, 128,   0),
									new Color(  0, 128, 128),
									new Color(128,   0, 128),
									new Color(128, 128,   0)
									/*new Color(  0, 64, 64),
									new Color(64,   0, 64),
									new Color(64, 64,   0),
									new Color(  0, 64, 64),
									new Color(64,   0, 64),
									new Color(64, 64,   0)*/
									/*new Color(  0, 192, 192),
									new Color(192,   0, 192),
									new Color(192, 192,   0),
									new Color(  0, 192, 192),
									new Color(192,   0, 192),
									new Color(192, 192,   0)*/
	};
	
	public DBSCAN(Element elements[], int minPts, double eps)
	{
		this.elements = elements;
		this.minPts = minPts;
		this.eps = eps;
		
		visited = new boolean[elements.length];
		for(int v = 0; v < visited.length; v++)
			visited[v] = false;
	}
	
	public void startClustering()
	{
		int c = 0;
		for(Element P : elements)
		{
			if(!visited[P.getIndex()])
			{
				visited[P.getIndex()] = true;
				ArrayList<Element> neighborPts = regionQuery(P);
				if(neighborPts.size() < minPts)
					P.setColor(colors[0]);
				else
				{
					c++;
					//if(c==13) c=12;
					expandCluster(P, neighborPts, c);
				}
			}
		}
	}
	
	public boolean startClusteringAnimation()
	{
		if(count == elements.length)
			return false;
		
		Element P = elements[count++];
		{
			if(!visited[P.getIndex()])
			{
				visited[P.getIndex()] = true;
				ArrayList<Element> neighborPts = regionQuery(P);
				if(neighborPts.size() < minPts)
					P.setColor(colors[0]);
				else
				{
					cIndex++;
					//if(c==13) c=12;
					expandCluster(P, neighborPts, cIndex);
				}
			}
		}
		return true;
	}
	
	private void expandCluster(Element P, ArrayList<Element> neighborPts, int clusterIndex)
	{
		P.setColor(colors[clusterIndex]);
		for(int i = 0; i < neighborPts.size(); i++)
		{
			//System.out.println(neighborPts.size());
			Element Pdash = neighborPts.get(i);
			
			if( !visited[Pdash.getIndex()] )
			{
				visited[Pdash.getIndex()] = true;
				ArrayList<Element> neighborPtsDash = regionQuery(Pdash);
				if( neighborPtsDash.size() >= minPts )
				{
					//neighborPts.addAll(neighborPtsDash);
					for(Element j : neighborPtsDash)
						neighborPts.add(j);
				}
			}
			if(same( Pdash.getColor(), new Color(0,0,0)) || same( Pdash.getColor(), colors[0]))
				Pdash.setColor(colors[clusterIndex]);
		}
	}
	
	public boolean startClusteringAnimationSlow()
	{
		
		if(inMethodCall)
		{
			expandClusterAnimation(elements[count-1], innerList, cIndex);
			return true;
		}
		if(count == elements.length)
			return false;
		
		Element P = elements[count++];
		{
			if(!visited[P.getIndex()])
			{
				visited[P.getIndex()] = true;
				ArrayList<Element> neighborPts = regionQuery(P);
				if(neighborPts.size() < minPts)
					P.setColor(colors[0]);
				else
				{
					cIndex++;
					//if(c==13) c=12;
					expandClusterAnimation(P, neighborPts, cIndex);
				}
			}
		}
		return true;
	}
	
	private void expandClusterAnimation(Element P, ArrayList<Element> neighborPts, int clusterIndex)
	{
		P.setColor(colors[clusterIndex]);
		if(!inMethodCall)
		{
			inMethodCall = true;
			loopVar = 0;
			innerList = neighborPts;
		}
		if( loopVar < neighborPts.size() )
		{
			//System.out.println(neighborPts.size());
			Element Pdash = neighborPts.get(loopVar);
			
			if( !visited[Pdash.getIndex()] )
			{
				visited[Pdash.getIndex()] = true;
				ArrayList<Element> neighborPtsDash = regionQuery(Pdash);
				if( neighborPtsDash.size() >= minPts )
				{
					//neighborPts.addAll(neighborPtsDash);
					for(Element j : neighborPtsDash)
						innerList.add(j);
				}
			}
			if(same( Pdash.getColor(), new Color(0,0,0)) || same( Pdash.getColor(), colors[0]))
				Pdash.setColor(colors[clusterIndex]);
			
			loopVar++;
		}
		else
			inMethodCall = false;
	}
	
	private boolean same(Color color1, Color color2)
	{
		return (color1.getRed() == color2.getRed() && color1.getGreen() == color2.getGreen() && color1.getBlue() == color2.getBlue());
	}

	public ArrayList<Element> regionQuery(Element P)
	{
		ArrayList<Element> neighborPts = new ArrayList<Element>();
		for(int i = 0; i<elements.length; i++)
		{
			if(distance(P, elements[i]) <= eps)
				neighborPts.add(elements[i]);
		}
		return neighborPts;
	}
	
	private double distance(Element element1, Element element2) {
		return Math.sqrt( ( element1.getX() - element2.getX() ) * ( element1.getX() - element2.getX() ) + ( element1.getY() - element2.getY() ) * ( element1.getY() - element2.getY() ) );
	}
	
	public Element[] getElements() {
		return elements;
	}

	public void setElements(Element elements[]) {
		this.elements = elements;
	}

	public void setMinPts(int minPts) {
		this.minPts = minPts;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}
	
}
