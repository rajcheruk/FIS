package fisprototype;

/**
 *
 * @author RAJABHUSHANAM
 */

import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JApplet;
import javax.imageio.*;
import javax.swing.event.*;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public abstract class laplacian_Gaussian extends JFrame implements PlugInFilter {
        
    private static final long serialVersionUID = 1L;
    ImagePlus imp;
    
    public laplacian_Gaussian()
    {
       setSize(850, 500);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       init();
       setVisible(true);
    }
    
    Image edgeImage, accImage, outputImage;
    MediaTracker tracker = null;
    PixelGrabber grabber = null;
    int width = 0, height = 0;
    String[] fileNames = {"/home/raj/evolution_research/Images/waxlake_2014298_lrg.jpg",
                          "/home/raj/evolution_research/Images/14OCT15234408-P2AS-053951064020_01_P001.jpg",
                          "/home/raj/evolution_research/Images/L5142052_05220090215_B10.TIF",
                          "/home/raj/evolution_research/Images/L5142052_05220090215_B30.TIF",
                          "/home/raj/evolution_research/Images/p142r050_7t20001028_z44_nn30.tif",
                          "/home/raj/evolution_research/Images/p142r051_5dt20060207.SR.b03.tif",
                          "/home/raj/evolution_research/Images/p142r051_5dt20060207.SR.b04.tif",
                          "/home/raj/evolution_research/Images/p142r051_5dt20090215.SR.b03.tif",
                          "/home/raj/evolution_research/Images/p142r051_5dt20090215.SR.b04.tif"};
 
    javax.swing.Timer timer;

    int sigmavalue = 5;
    int imageNumber = 0;
    static int progress = 0;
    static int noise = 5;
    public int[] orig = null;

    Image[] image = new Image[fileNames.length];

    static Image Marr, Lap;

    JProgressBar progressBar;
    JPanel controlPanel, noisePanel, modePanel, imagePanel, progressPanel;
    JLabel origLabel, outputLabel, modelLabel, noiseLabel, noiseLabel2, noiseLabel3, comboLabel, templateLabel, processing;
    JSlider sigmaSlider, radiusSlider, noiseSlider;
    JRadioButton gaussianRadio, condimentRadio;
    JRadioButton MarrRadio, LapRadio;
    ButtonGroup radiogroup;
    ButtonGroup radiogroup2;
    JComboBox imSel;
    static LoG edgeDetector;
    static gaussianNoise gNoise;
    static condimentNoise cNoise;
    static gaussianFilter filter;
    String noisemode = "Gaussian";
    String Lapmode="Laplacian-Marr";
    String outputmode = "Lap";
    
    //public static void main(String[] args)
    //{
    //    laplacianGaussian guiWindow = new laplacianGaussian();
     //   guiWindow.setVisible(true);
    //    guiWindow.init();
    //}
    
    // Applet init function	
    @SuppressWarnings("unchecked")
    public int init() {   
        tracker = new MediaTracker(this);
        for (int i = 0; i < fileNames.length; i++) {            
            image[i] = getImage(fileNames[i]);
            image[i] = image[i].getScaledInstance(image[i].getWidth(this), image[i].getHeight(this), Image.SCALE_AREA_AVERAGING);
            tracker.addImage(image[i], i);
        }
            
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            System.out.println("error: " + e);
        }

        
        Container cont = getContentPane();
        cont.removeAll();
        cont.setBackground(Color.black);
        cont.setLayout(new BorderLayout());

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 4, 15, 0));
        controlPanel.setBackground(new Color(192, 204, 226));
        imagePanel = new JPanel();
        imagePanel.setBackground(new Color(192, 204, 226));
        progressPanel = new JPanel();
        progressPanel.setBackground(new Color(192, 204, 226));
        progressPanel.setLayout(new GridLayout(2, 1));
        noisePanel = new JPanel();
        noisePanel.setBackground(new Color(192, 204, 226));
        noisePanel.setLayout(new GridLayout(2, 1));
        modePanel = new JPanel();
        modePanel.setBackground(new Color(192, 204, 226));
        modePanel.setLayout(new GridLayout(2, 1));

        comboLabel = new JLabel("IMAGE");
        comboLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(comboLabel);

        noiseLabel3 = new JLabel("NOISE TYPE");
        noiseLabel3.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(noiseLabel3);

        noiseLabel = new JLabel("NOISE");
        noiseLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(noiseLabel);

        templateLabel = new JLabel("GAUSSIAN FILTER SIZE");
        templateLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(templateLabel);

        modelLabel = new JLabel("OUTPUT MODE");
        modelLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanel.add(modelLabel);
        
        
        processing = new JLabel("Processing...");
        processing.setHorizontalAlignment(JLabel.LEFT);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true); //get space for the string
        progressBar.setString("");          //but don't paint it
        progressPanel.add(processing);
        progressPanel.add(progressBar);

        imSel = new JComboBox(fileNames);
        imageNumber = imSel.getSelectedIndex();

        width = image[imageNumber].getWidth(null);
        height = image[imageNumber].getHeight(null);
        
        imSel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imageNumber = imSel.getSelectedIndex();
                        origLabel.setIcon(new ImageIcon(image[imageNumber]));
                        processImage();
                    }
                }
        );
        controlPanel.add(imSel, BorderLayout.PAGE_START);

        timer = new javax.swing.Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue((edgeDetector.getProgress()));
            }
        });

        origLabel = new JLabel("Original Image", new ImageIcon(image[imageNumber]), JLabel.CENTER);
        origLabel.setVerticalTextPosition(JLabel.BOTTOM);
        origLabel.setHorizontalTextPosition(JLabel.CENTER);
        origLabel.setForeground(Color.blue);
        imagePanel.add(origLabel);

        outputLabel = new JLabel("Marr Hildreth", new ImageIcon(image[imageNumber]), JLabel.CENTER);
        outputLabel.setVerticalTextPosition(JLabel.BOTTOM);
        outputLabel.setHorizontalTextPosition(JLabel.CENTER);
        outputLabel.setForeground(Color.blue);
        imagePanel.add(outputLabel);

        gaussianRadio = new JRadioButton("Gaussian");
        gaussianRadio.setActionCommand("Gaussian");
        gaussianRadio.setBackground(new Color(192, 204, 226));
        gaussianRadio.setHorizontalAlignment(SwingConstants.CENTER);
        condimentRadio = new JRadioButton("Condiment");
        condimentRadio.setActionCommand("Condiment");
        condimentRadio.setHorizontalAlignment(SwingConstants.CENTER);
        condimentRadio.setBackground(new Color(192, 204, 226));
        gaussianRadio.setSelected(true);
        radiogroup = new ButtonGroup();
        radiogroup.add(condimentRadio);
        radiogroup.add(gaussianRadio);
        condimentRadio.addActionListener(new radiolistener());
        gaussianRadio.addActionListener(new radiolistener());
        noisePanel.add(gaussianRadio);
        noisePanel.add(condimentRadio);
        controlPanel.add(noisePanel);

        noiseSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 5);
        noiseSlider.addChangeListener(new noiseListener());
        noiseSlider.setMajorTickSpacing(10);
        noiseSlider.setMinorTickSpacing(2);
        noiseSlider.setPaintTicks(true);
        noiseSlider.setPaintLabels(true);
        noiseSlider.setBackground(new Color(192, 204, 226));
        controlPanel.add(noiseSlider);

        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 10 ), new JLabel("1.0") );
        labelTable.put( new Integer( 20 ), new JLabel("2.0") );
        labelTable.put( new Integer( 30 ), new JLabel("3.0") );
        labelTable.put( new Integer( 40 ), new JLabel("4.0") );
        
                
        sigmaSlider = new JSlider(JSlider.HORIZONTAL, 10, 40, 20);
        sigmaSlider.addChangeListener(new templateListener());
        sigmaSlider.setMajorTickSpacing(10);
        sigmaSlider.setMinorTickSpacing(2);
        sigmaSlider.setPaintTicks(true);
        sigmaSlider.setPaintLabels(true);
        sigmaSlider.setBackground(new Color(192, 204, 226));
        
        sigmaSlider.setLabelTable( labelTable );
        controlPanel.add(sigmaSlider);

        LapRadio = new JRadioButton("Laplacian");
        LapRadio.setActionCommand("Lap");
        LapRadio.setBackground(new Color(192,204,226));
        LapRadio.setHorizontalAlignment(SwingConstants.CENTER);
        LapRadio.setSelected(true);
        MarrRadio = new JRadioButton("Marr Hildreth");
        MarrRadio.setActionCommand("Marr");
        MarrRadio.setHorizontalAlignment(SwingConstants.CENTER);
        MarrRadio.setBackground(new Color(192,204,226));

        radiogroup2 = new ButtonGroup();
        radiogroup2.add(LapRadio);
        radiogroup2.add(MarrRadio);
        
        LapRadio.addActionListener(new radiolistener2());
        MarrRadio.addActionListener(new radiolistener2());
        
        modePanel.add(LapRadio);
        modePanel.add(MarrRadio);
        controlPanel.add(modePanel);
        
        
        cont.add(controlPanel, BorderLayout.NORTH);
        cont.add(imagePanel, BorderLayout.CENTER);
        cont.add(progressPanel, BorderLayout.SOUTH);

        processImage();
        
        return(0);
    }
    
    Image getImage(String ImageFile) {
        ImageIcon icon = new ImageIcon(ImageFile);
    return icon.getImage();
    }

    @Override
    public int setup(String string, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run(ImageProcessor ip) {
        IJ.showStatus("Start Processing...");
        IJ.wait(2000);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    class radiolistener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            noisemode = e.getActionCommand();
            processImage();
        }
    }

    class radiolistener2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Lapmode = e.getActionCommand();
            processImage();
        }
    }
        
    class templateListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int val = source.getValue();
                System.out.println("template=" + val);
                sigmavalue = val;
                templateLabel.setText("GAUSSIAN FILTER SIZE = " + val);
                processImage();
            }
        }
    }

    class noiseListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                System.out.println("noise=" + source.getValue() + "%");
                noise = source.getValue();
                noiseLabel.setText("NOISE = " + source.getValue() + "%");
                processImage();
            }
        }
    }

    public void processImage() {
        
        orig = new int[width * height];
        PixelGrabber grabber = new PixelGrabber(image[imageNumber], 0, 0, width, height, orig, 0, width);
      
        try {
            grabber.grabPixels();
        } catch (InterruptedException e2) {
            System.out.println("error: " + e2);
        }
        progressBar.setMaximum(width - sigmavalue);

        processing.setText("Processing...");
        sigmaSlider.setEnabled(false);
        imSel.setEnabled(false);
        noiseSlider.setEnabled(false);
        gaussianRadio.setEnabled(false);
        condimentRadio.setEnabled(false);

        gNoise = new gaussianNoise();
        cNoise = new condimentNoise();
        edgeDetector = new LoG();

        gNoise.init(orig, width, height, (float) noise);
        cNoise.init(orig, width, height, (float) noise / 100);
        if (noisemode == "Gaussian") {
            orig = gNoise.process();
            origLabel.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, orig, 0, width))));
            origLabel.setText("Original Image with Gaussian Noise");
        }
        if (noisemode == "Condiment") {
            orig = cNoise.process();
            origLabel.setIcon(new ImageIcon(createImage(new MemoryImageSource(width, height, orig, 0, width))));
            origLabel.setText("Original Image with Condiment Noise");
        }

           timer.start();
        new Thread() {
            @Override
            public void run() {
                int tempsz=9;
                
                edgeDetector.init(orig, sigmavalue, tempsz, width, height);
                edgeDetector.generateTemplate();

                Lap = createImage(new MemoryImageSource(width, height, edgeDetector.process(), 0, width));
                Marr = createImage(new MemoryImageSource(width, height, edgeDetector.process(), 0, width));
                //Marr = createImage(new MemoryImageSource(width, height, edgeDetector.getMH(), 0, width));
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        outputLabel.setIcon(new ImageIcon(Lap));
                        ImagePlus imp;
                        imp = new ImagePlus("", Lap);
                        imp.show();
                        processing.setText("Done");
                        sigmaSlider.setEnabled(true);
                        imSel.setEnabled(true);
                        noiseSlider.setEnabled(true);
                        gaussianRadio.setEnabled(true);
                        condimentRadio.setEnabled(true);
                    }
                });
            }
        }.start();
    }
}