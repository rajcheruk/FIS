package ij;

import ij.ImageJ;
import ij.IJ;
import ij.process.ByteProcessor;
import static ij.IJ.beep;
import static ij.IJ.escapePressed;
import static ij.IJ.getInstance;
import ij.OtherInstance.ImageJInstance;
import static ij.IJ.outOfMemory;
import static ij.IJ.resetEscape;
import static ij.IJ.showStatus;
import ij.ImagePlus;
import static ij.gui.GUI.center;
import ij.gui.Roi;
import ij.gui.*;
import ij.plugin.frame.PlugInFrame;
import ij.process.ImageProcessor;
import java.awt.*;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.lang.Math.max;
import static java.lang.System.currentTimeMillis;
import java.util.concurrent.ExecutorService;
import java.io.IOException;

import fisprototype.CannyEdgeDetector;
import fisprototype.GaussianScaleSpace;
import fisprototype.HierarchicalScaleSpace;
import ij.process.FloatProcessor;
import fisprototype.Demo_RegionLabeling;



/**
	Image Processing Demo. Demonstrates how to create a custom user interface, how
	to use ImageJ's ImageProcessor class, and how to run commands in a separate thread.
*/
public final class IP_Demo extends PlugInFrame implements ActionListener {

    private static Frame instance;
    private static final long serialVersionUID = 1L;
        
        public double sigma_s=10.006820, sigma_0=5.0;
        public int P=2, Q=4, botLevel=3, topLevel=5;
        
        public ExecutorService concurrent_Thread;
        public String label="";
        private final double scale=5.0;
	private Panel panel;
	private int previousID;
        
        public ImageJ FISImageJ=null;
        public CannyEdgeDetector temp = null;
        public CannyEdgeDetector.Parameters tempParams = new CannyEdgeDetector.Parameters();
        public ByteProcessor edgeImg;
        
        public GaussianScaleSpace FISGaussSpace=null;
        public HierarchicalScaleSpace FISHierSpace;
        public FloatProcessor FISFP = null;
        public Demo_RegionLabeling RegionLabel = null;
        
        public final String appname="/usr/local/ImageJ/ImageJ";
        public final String filename="/home/raj/evolution_research/Images/waxlake_2014298_lrg_bw.jpg";
        //public String commandName;
        public String runtimeCmd;
        
        public IP_Demo() {

            super("IP Demo");
		if (instance!=null) {
			instance.toFront();
			return;
		}
		instance = this;
		addKeyListener(getInstance());

		setLayout(new FlowLayout());
                
		panel = new Panel();
		panel.setLayout(new GridLayout(4, 4, 5, 5));
                
		addButton("Canny Edge Detector");
                addButton("Laplacian Gaussian");

                addButton("Multi Scale");
		addButton("Multi Resolution");
                addButton("Wavelets Image Pyramids");

		addButton("Region Labeling");
		addButton("Region Contouring");
		
		addButton("Custom Watershed");
                addButton("IsoData Classifier");
                
                addButton("R Reports");
                
                add(panel);
		
		pack();
		center(this);
		setVisible(true);
	}
	
	void addButton(String label) {
		Button b = new Button(label);
		b.addActionListener(this);
		b.addKeyListener(getInstance());
		panel.add(b);
	}

        @Override
	public void actionPerformed(ActionEvent e) {
                FISImageJ = new ImageJ();
                IJ.open();
                ImageJ instance1 = ij.IJ.getInstance();
		ImagePlus imp = WindowManager.getCurrentImage();
                imp.show();

                if (imp == null) {
			beep();
			showStatus("No image");
			previousID = 0;
			return;
		}
		if (!imp.lock())
			{previousID = 0; return;}
		int id = imp.getID();
		if (id!=previousID)
			imp.getProcessor().snapshot();
		previousID = id;
		label = e.getActionCommand();
                if (label==null)
			return;
               
                switch(label) {
                    case "Canny Edge Detector":        
                            new Runner(label, imp);
                            break;
                    case "Laplacian Gaussian":
                            new Runner(label, imp);
                            break;
                    case "Multi Scale":
                            new Runner(label, imp);
                            break;
                    case "Multi Resolution":
                            new Runner(label, imp);
                            break;
                    case "Wavelets Image Pyramids":
                            new Runner(label, imp);
                            break;
                    case "Region Labeling":
                            new Runner(label, imp);
                            break;
                    case "Region Contouring":
                            new Runner(label, imp);
                            break;
                    case "Custom Watershed":
                            new Runner(label, imp);
                            break;
                    case "IsoData Classifier":
                            new Runner(label, imp);
                            break;
                    case "R Reports":
                            new Runner(label, imp);
                            break;
                }       
	}

    @Override
	public void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID()==WINDOW_CLOSING) {
			instance = null;	
		}
	}

	class Runner extends Thread { // inner class
		private final String command;
		public ImagePlus imp;
	
		Runner(String command, ImagePlus imp) {
			super(command);
			this.command = command;
                        imp = WindowManager.getCurrentImage();
			this.imp = imp;
			setPriority(max(getPriority()-2, MIN_PRIORITY));
			start();
		}
	
                @Override
		public void run() {
                    switch(label)  
                    {
                        case "Canny Edge Detector":
			try {
                                runCommand(label, imp);
			} catch(OutOfMemoryError e) {
				outOfMemory(command);
				if (imp!=null) imp.unlock();
			} 
                        break;
                        case "Laplacian Gaussian":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                        }
                        break;
                        case "Multi Scale":
                        try {
                                runCommand(label, imp); 
                        } catch(OutOfMemoryError e)  {
                                outOfMemory(command);
                                if (imp!=null) imp.unlock();
                        }
                        break;
                        case "Multi Resolution":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                        break;
                        case "Wavelets Image Pyramids":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                        break;
                        case "Region Labeling":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                            break;
                        case "Region Contouring":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                            break;
                        case "Custom Watershed":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                            break;
                        case "IsoData Classifier":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                            break;
                        case "R Reports":
                            try {
                                runCommand(label, imp);
                            } catch (OutOfMemoryError e) {
                                outOfMemory(command);
                                if (imp != null) {
                                    imp.unlock();
                                }
                            }
                            break;
                    }
                    
                        if (imp!=null) imp.unlock();
                    }
                 
		void runCommand(String command, ImagePlus imp) {
                    
			ImageProcessor ip = imp.getProcessor();
                        ImageCanvas ic = imp.getCanvas();

                        if (ic == null) return;
                       
                        int floatWidth = imp.getWidth();
                        int floatHeight = imp.getHeight();
                        
                        FISFP = new FloatProcessor(floatWidth, floatHeight);
                        FISFP = (FloatProcessor) ip.convertToFloat();
                       
                        //Region of Interest and Point Operations for Zoom Commands
                        Point loc = ic.getCursorLoc();
                        if (!ic.cursorOverImage()) {
                            Rectangle srcRect = ic.getSrcRect();
                            loc.x = srcRect.x + srcRect.width/2;
                            loc.y = srcRect.y + srcRect.height/2;
                        }
                        
                        int x = ic.screenX(loc.x);
                        int y = ic.screenY(loc.y);
                        
			showStatus(command + "...");
			long startTime = currentTimeMillis();
			Roi roi = imp.getRoi();
			if (command.equals("Zoom In")||command.equals("Zoom Out")||command.equals("ZoomToFit")||command.equals("ZoomToScale"))
				{roi = null; ip.resetRoi();}
			ImageProcessor mask =  roi!=null?roi.getMask():null;
                        
            switch (command) {
                case "Multi Resolution":
                    ip.scale(1.5, 1.5);
                    (new ImagePlus("Simple MultiResolution", ip)).show();
                    //imp.updateAndDraw();
                    break;
                case "Multi Scale":
                    //FISHierSpace = new HierarchicalScaleSpace(P, Q, sigma_s, sigma_0, botLevel, topLevel) {};
                    FISGaussSpace=new GaussianScaleSpace(FISFP, sigma_s, sigma_0, P, Q, botLevel, topLevel);
                    if (FISGaussSpace != null) {
                          (new ImagePlus("Hierarchical Scale Space Applied Image", FISFP)).show();
                    }
                    break;
                case "Canny Edge Detector":
                    temp = new CannyEdgeDetector(ip, tempParams);
                    if (temp != null)  {
                        edgeImg = temp.getEdgeBinary();
                        (new ImagePlus("Canny Edge Detector Applied Image", edgeImg)).show();
                        //imp.updateAndDraw();
                    }
                    break;
                case "Laplacian Gaussian":
                    try
                    {
                    String commandName="laplacian";
                    String commandName1="GaussianImpl";
                    String cmd=commandName + " " + commandName1;
                    String commandName2="-ijpath";
                    String commandName3="/usr/local/ImageJ/Plugins";
                    //String Command=commandName + " " + commandName1;
                    
                    runtimeCmd=appname + " " + commandName2 + " " + commandName3 + " " + filename + " " + "-run" + " "  + "\"" + cmd + "\"";
                    //runtimeCmd=appname + " " + commandName2 + " " + commandName3 + " " + filename + " " + "-run" + " "  + "crop";
                    System.out.println(runtimeCmd);
                    Process pr = Runtime.getRuntime().exec(runtimeCmd);
                    } catch (IOException err) {
                        System.err.println("Caught Exception from routine Laplacian Guassian" + err);
                    }
                    //IJ.run("laplacian GaussianImpl");
                    //IJ.runUserPlugIn("laplacian GaussianImpl", "laplacian_GaussianImpl", "", true);
                    //ij.IJ.runPlugIn("laplacian_GaussianImpl", "");                    
                    break;
                case "Wavelets Image Pyramids":
                    try {
                    String commandName="Image" + " " + "Pyramid";
                    runtimeCmd=appname + " " + filename + " " + "-run" + " " + commandName;
                    Process pr = Runtime.getRuntime().exec(runtimeCmd);
                    } catch (IOException err) {
                        System.err.println("Caught Exception from routine Laplacian Guassian" + err);
                    }
                    //IJ.run("Image Pyramid");
                    //ij.IJ.runPlugIn("Image_Pyramid", "");
                    ij.IJ.showProgress(1.0);
                    ij.IJ.showMessage("Finished.", "Thank you for running Image Pyramid");
                    break;
                case "Region Labeling":
                    try {
                    String commandName="Demo" + " " + "RegionLabeling";
                    runtimeCmd=appname + " " + filename + " " + "-run" + " " + commandName;
                    Process pr = Runtime.getRuntime().exec(runtimeCmd);
                    } catch (IOException err) {
                        System.err.println("Caught Exception from routine Laplacian Guassian" + err);
                    }
                    imp.unlock();
                    //IJ.run("Demo RegionLabeling");
                    //ij.IJ.runPlugIn("Demo_RegionLabeling", "");
                    ij.IJ.showProgress(1.0);
                    ij.IJ.showMessage("Completed.", "Thank you for running Region Labeling");
                    break;
                case "Region Contouring":
                    try {
                    String commandName="Demo" + " " + "RegionsAndContours";
                    runtimeCmd=appname + " " + filename + " " + "-run" + " " + commandName;
                    Process pr = Runtime.getRuntime().exec(runtimeCmd);
                    } catch (IOException err) {
                        System.err.println("Caught Exception from routine Laplacian Guassian" + err);
                    }
                    imp.unlock();
                    IJ.run("Demo RegionsAndContours");
                    //ij.IJ.runPlugIn("Demo_RegionsAndContours", "");
                    ij.IJ.showProgress(1.0);
                    ij.IJ.showMessage("Completed.", "Thank you for running Region Contouring");
                    break;
                case "Custom Watershed":
                    
                    break;
                case "IsoData Classifier":
                   
                    break;
                case "R Reports":
                  
                    break;
            }
            
			//if (mask!=null) ip.reset(mask);
			imp.updateAndDraw();
			imp.unlock();
			showStatus((currentTimeMillis()-startTime)+" milliseconds");
		}
	
		void macro1(ImagePlus imp, ImageProcessor ip) {
			resetEscape();
			for (int i=10; i<=360; i+=10) {
				ip.reset();
				ip.rotate(i);
				imp.updateAndDraw();
				if (escapePressed()) return;
				IJ.wait(10);
			}
		}
		
		void macro2(ImagePlus imp, ImageProcessor ip) {
			resetEscape();
			double scale = 1, m = 1.2;
			for (int i=0; i<20; i++) {
				ip.reset();
				scale *= m;
				ip.scale(scale, scale);
				imp.updateAndDraw();
				if (escapePressed()) return;
				IJ.wait(10);
			}
			for (int i=0; i <20; i++) {
				ip.reset();
				scale /= m;
				ip.scale(scale, scale);
				imp.updateAndDraw();
				if (escapePressed()) return;
				IJ.wait(10);
			}
		}
	
                private void scaleToFit(ImagePlus imp)
                {
                    ImageCanvas ic = imp.getCanvas();
                    if (ic == null) return;
                    
                    if (ic.getScaleToFit())  
                    {
                        ic.setScaleToFit(false);
                        ic.unzoom();
                        IJ.showStatus("Unzooming from scale to fit");
                    } else {
                        ic.setScaleToFit(true);
                        ic.fitToWindow();
                        IJ.showStatus("Zooming to Window/Frame Scale");
                    }
                }
	} // Runner inner class

} //IP_Demo class
