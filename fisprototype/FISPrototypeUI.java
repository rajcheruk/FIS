package fisprototype;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import static java.awt.EventQueue.invokeLater;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import static java.lang.System.exit;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
import javax.swing.*;
import java.io.IOException;

import ij.IP_Demo;
import FISPrototype.LoG;
import FISPrototype.laplacian_Gaussian;
import FISPrototype.gaussianNoise;
import FISPrototype.gaussianFilter;
import FISPrototype.condimentNoise;
import FISPrototype.laplacian_GaussianImpl;

import ij.process.ImageProcessor;

//import fisprototype.RegionContourLabeling;
//import ij.process.ByteProcessor;

import ij.ImageJ;
import ij.ImagePlus;
import static ij.IJ.beep;
import static ij.IJ.showStatus;
import static ij.WindowManager.getCurrentImage;
import ij.plugin.filter.PlugInFilter;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

public class FISPrototypeUI {

    private static final long serialVersionUID = 1L;
    public static String command;
    public static String commandargs1;
    public static String commandargs2;
    public static String imagejapp;
     
    public static void main(String[] args) {
        
        invokeLater(new Runnable() {
            
            @Override
            public void run() {
                JFrame Frame = new FISAuthenticate();
                Frame.pack();
                Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //Frame.getContentPane().add(BorderLayout.CENTER, null);
                Frame.setSize(512, 512);
                Frame.setLocationByPlatform(true);
                Frame.setLocation(450, 100);
                Frame.setVisible(true);
                
                //command = args[0];
                //commandargs1 = args[1];
                //commandargs2 = args[2];
                //imagejapp = command + " " + commandargs1 + " " + commandargs2;
            }

            final class FISAuthenticate extends JFrame {

                private static final long serialVersionUID = 1L;
                private static final int DEFAULT_WIDTH = 512;
                private static final int DEFAULT_HEIGHT = 512;
                
                public String myijpath="-ijpath /usr/local/ImageJ/plugins";
                
                public BufferedImage bufferedImage = null;
                
                protected ij.IP_Demo CustomCommands = null;
                
                protected ImageJ FISImageJ=null;
                protected laplacian_Gaussian LaplacianGaussian = null;
                
                // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
                // Generated using JFormDesigner Evaluation license - Rajabhushanam Cherukuri
                private JMenuBar menuBar1;
                private JMenu ImageryMnu;
                private JCheckBoxMenuItem FileRetChkBox;
                private JCheckBoxMenuItem FileSaveChk;
                private JCheckBoxMenuItem FileExitChk;
                private JMenu EditMnu;
                private JCheckBoxMenuItem LOGChk;
                private JCheckBoxMenuItem Wavelet_TransformChk;
                private JCheckBoxMenuItem CannyChk;
                //private JCheckBoxMenuItem SharpenChk;
                private JCheckBoxMenuItem MultiresolutionChk;
                private JCheckBoxMenuItem ScalingChk;
                private JCheckBoxMenuItem BroveyChk;
                private JMenu FISMnu;
                private JCheckBoxMenuItem FuzzyCMeansChk;
                private JCheckBoxMenuItem FuzzyKMeansChk;
                private JCheckBoxMenuItem FuzzySCMChk;
                private JCheckBoxMenuItem IsodataChk;
                private JCheckBoxMenuItem MaxmLikeChk;
                private JMenu MachineMnu;
                private JCheckBoxMenuItem FuzzyIntChk;
                private JCheckBoxMenuItem SpatialClusteringChk;
                private JCheckBoxMenuItem SegmentationChk;
                private JCheckBoxMenuItem ClassificationChk;
                private JMenu MergeMnu;
                private JCheckBoxMenuItem RegionGrowChk;
                private JCheckBoxMenuItem RegionMergeChk;
                private JCheckBoxMenuItem FNEA_SinglePixelChk;
                private JCheckBoxMenuItem ErosionChk;
                private JCheckBoxMenuItem DilationChk;
                private JMenu DisplayMnu;
                private JCheckBoxMenuItem TrueColorChk;
                private JCheckBoxMenuItem FalseColorChk;
                private JCheckBoxMenuItem PanchromaticChk;
                private JCheckBoxMenuItem HSIChk;
                private JMenu ReportsMnu;
               
                FISAuthenticate() {
                    this.FISImageJ = null;
                    initAuthComponents();
                }

                public  void initAuthComponents() {

                    final Image iconImg;
                    
                    JInternalFrame FISPrototypeWindow = new JInternalFrame();
                    JInternalFrame AuthenticationFrame = new JInternalFrame();
                    JInternalFrame ImageFISFrame = new JInternalFrame();
                    
                    JLabel Username = new JLabel();
                    JLabel password = new JLabel();
                    
                    JFormattedTextField usernametxt = new JFormattedTextField();
                    JPasswordField passwordtxt = new JPasswordField();
                    
                    JButton submit = new JButton();
                    JButton cancel = new JButton();
                    
                    menuBar1 = new JMenuBar();
                    ImageryMnu = new JMenu();
                    FileRetChkBox = new JCheckBoxMenuItem();
                    FileSaveChk = new JCheckBoxMenuItem();
                    FileExitChk = new JCheckBoxMenuItem();
                    EditMnu = new JMenu();
                    LOGChk = new JCheckBoxMenuItem();
                    Wavelet_TransformChk = new JCheckBoxMenuItem();
                    CannyChk = new JCheckBoxMenuItem();
                    MultiresolutionChk = new JCheckBoxMenuItem();
                    BroveyChk = new JCheckBoxMenuItem();
                    ScalingChk = new JCheckBoxMenuItem();
                    //SharpenChk = new JCheckBoxMenuItem();
                    FISMnu = new JMenu();
                    FuzzyCMeansChk = new JCheckBoxMenuItem();
                    FuzzyKMeansChk = new JCheckBoxMenuItem();
                    FuzzySCMChk = new JCheckBoxMenuItem();
                    IsodataChk = new JCheckBoxMenuItem();
                    MaxmLikeChk = new JCheckBoxMenuItem();
                    MachineMnu = new JMenu();
                    FuzzyIntChk = new JCheckBoxMenuItem();
                    SpatialClusteringChk = new JCheckBoxMenuItem();
                    SegmentationChk = new JCheckBoxMenuItem();
                    ClassificationChk = new JCheckBoxMenuItem();
                    MergeMnu = new JMenu();
                    RegionGrowChk = new JCheckBoxMenuItem();
                    RegionMergeChk = new JCheckBoxMenuItem();
                    FNEA_SinglePixelChk = new JCheckBoxMenuItem();
                    ErosionChk = new JCheckBoxMenuItem();
                    DilationChk = new JCheckBoxMenuItem();
                    DisplayMnu = new JMenu();
                    TrueColorChk = new JCheckBoxMenuItem();
                    FalseColorChk = new JCheckBoxMenuItem();
                    PanchromaticChk = new JCheckBoxMenuItem();
                    HSIChk = new JCheckBoxMenuItem();
                    ReportsMnu = new JMenu();
                   
                    
                    //======== AuthenticationFrame ========                    
                    {   
                    
                        int inset = 50;
                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        //setBounds(inset, inset, screenSize.width, screenSize.height);
                        
                        JDesktopPane desktop = new JDesktopPane();
                        
                        desktop.add(AuthenticationFrame);
                        
                        AuthenticationFrame.pack();                    
                        AuthenticationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        AuthenticationFrame.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
                        AuthenticationFrame.setTitle("FIS Authentication");
                        AuthenticationFrame.setSize(50,50);
                        //AuthenticationFrame.getContentPane().add(BorderLayout.CENTER, this);
                        AuthenticationFrame.setLocation(50,50);
                        AuthenticationFrame.setVisible(true);
                        
                        try {
                            AuthenticationFrame.setSelected(true);
                        } catch (java.beans.PropertyVetoException e) {
                        }
                       
                        iconImg=null;
                        //ImagePanel FISImagePanel = new ImagePanel(iconImg);
                        
                        setContentPane(desktop);
                        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
                       
                        Container AuthenticationFrameContentPane = AuthenticationFrame.getContentPane();
                        Container ImageFISFrameContentPane = ImageFISFrame.getContentPane();  
                        
                        //---- Username ----
                        Username.setText("Username");
                        //---- password ----
                        password.setText("Password");
                        //---- submit ----
                        submit.setText("Submit");
                        submit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                submitActionPerformed(e);
                            }

                            void submitActionPerformed(ActionEvent e) {
                                String FISuser = usernametxt.getText();
                                String FISpwd = new String(passwordtxt.getPassword());
                                if (FISuser.equals("admin") && FISpwd.equals("admin")) {
                                    //try {
                                    //        Process pr = Runtime.getRuntime().exec(imagejapp);
                                    //    } catch (IOException ex) {
                                    //        System.err.println("ImageJ Exception:" + ex);
                                    //    }
                                    Class<?> plugin1Class = laplacian_GaussianImpl.class;
                                    String URL = plugin1Class.getResource("/" + plugin1Class.getName().replace('.', '/') + ".class").toString();
                                    String pluginsDir = URL.substring(5, URL.length() - plugin1Class.getName().length() - 6);
                                    System.out.println("PLUGINS DIR:" + pluginsDir);
                                    System.setProperty("plugins.dir", pluginsDir);
                                    
                                    FISImageJ = new ImageJ(null, 1);
                                    CustomCommands = new IP_Demo();

                                    //ImageFISFrame.pack();                    
                                    //ImageFISFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                    //ImageFISFrame.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 14));
                                    //ImageFISFrame.setTitle("FIS Image Display Window");
                                    //ImageFISFrame.setLocation(150, 100);
                                    //ImageFISFrame.setVisible(true);
                                    //ImageFISFrame.setOpaque(true);
                                    
                                    //desktop.add(FISPrototypeWindow);
                                    //FISPrototypeWindow.setVisible(true);
                                } else {
                                    showMessageDialog(AuthenticationFrame, "Incorrect Username OR Password!");
                                    System.err.println("Incorrect Username OR Password!");
                                    exit(0);
                                }
                                
                                AuthenticationFrame.setVisible(false);
                            }
                             
                         
                        });
                        //---- cancel ----
                        cancel.setText("Cancel");
                        cancel.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cancelActionPerformed(e);
                            }

                            void cancelActionPerformed(ActionEvent e) {
                                exit(0);
                            }
                        });
                        GroupLayout AuthenticationFrameContentPaneLayout = new GroupLayout(AuthenticationFrameContentPane);
                        AuthenticationFrameContentPane.setLayout(AuthenticationFrameContentPaneLayout);
                        AuthenticationFrameContentPaneLayout.setHorizontalGroup(
                                AuthenticationFrameContentPaneLayout.createParallelGroup()
                                .add(AuthenticationFrameContentPaneLayout.createSequentialGroup()
                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup()
                                                .add(AuthenticationFrameContentPaneLayout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup(GroupLayout.LEADING, false)
                                                                .add(Username, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                                                .add(password, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                                        .add(45, 45, 45)
                                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup(GroupLayout.LEADING, false)
                                                                .add(usernametxt, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                                                .add(passwordtxt, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                                                .add(AuthenticationFrameContentPaneLayout.createSequentialGroup()
                                                        .add(39, 39, 39)
                                                        .add(submit, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                                        .add(58, 58, 58)
                                                        .add(cancel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap(22, Short.MAX_VALUE))
                        );
                        AuthenticationFrameContentPaneLayout.setVerticalGroup(
                                AuthenticationFrameContentPaneLayout.createParallelGroup()
                                .add(AuthenticationFrameContentPaneLayout.createSequentialGroup()
                                        .add(36, 36, 36)
                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup()
                                                .add(Username)
                                                .add(usernametxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .add(25, 25, 25)
                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
                                                .add(password)
                                                .add(passwordtxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.RELATED, 47, Short.MAX_VALUE)
                                        .add(AuthenticationFrameContentPaneLayout.createParallelGroup(GroupLayout.BASELINE)
                                                .add(submit)
                                                .add(cancel))
                                        .add(33, 33, 33))
                        );
                        //ImageFISFrameContentPane.add(FISImagePanel);
                        ImageFISFrame.pack();
                        
                        AuthenticationFrame.pack();
                        
                        Container FISPrototypeWindowContentPane = FISPrototypeWindow.getContentPane(); 

                        //======== menuBar1 ========
                        {
                            menuBar1.setName("menuBar1");
                            
                            //======== EditMnu ========
                            {
                                EditMnu.setText("Edit");
                                EditMnu.setName("EditMnu");
                                //---- LOGChk ----
                                LOGChk.setText("Laplacian Of Gaussian");
                                LOGChk.setName("LOGChk");
                                LOGChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Laplacian_GaussianChkActionPerformed(e);
                                    }

                                    public void Laplacian_GaussianChkActionPerformed(ActionEvent e)    {
                                        //LaplacianGaussian = new laplacianGaussian();
                                    }
                                });
                                EditMnu.add(LOGChk);
                                //---- Wavelet ----
                                Wavelet_TransformChk.setText("Wavelet Transform");
                                Wavelet_TransformChk.setName("Wavelet_TransformChk");
                                Wavelet_TransformChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Wavelet_TransformChkActionPerformed(e);
                                    }

                                    void Wavelet_TransformChkActionPerformed(ActionEvent e) {
                                        //ij.IJ.write("Running Specialized Plugin: Image Pyramids");
                                        ij.IJ.runPlugIn("Image_Pyramid", "");
                                        ij.IJ.showProgress(1.0);
                                        ij.IJ.showMessage("Finished.", "Thank you for running this plugin");
                                    }
                                });
                                EditMnu.add(Wavelet_TransformChk);
                                //---- Canny Edge Detector ----
                                CannyChk.setText("Canny Operator");
                                CannyChk.setName("CannyChk");
                                CannyChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        CannyChkActionPerformed(e);
                                    }

                                    void CannyChkActionPerformed(ActionEvent e1) {
                                        ImagePlus imp = getCurrentImage();

                                        if (imp == null) {
                                            beep();
                                            showStatus("No image");
                                        }
                                        
                                        CustomCommands = new IP_Demo();
                                    }
                                });
                                EditMnu.add(CannyChk);
                               
                                //---- MultiResolution Segmentation ----
                                MultiresolutionChk.setText("Multiresolution");
                                MultiresolutionChk.setName("MultiresolutionChk");
                                MultiresolutionChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MultiresolutionChkActionPerformed(e);
                                    }

                                    void MultiresolutionChkActionPerformed(ActionEvent e) {
                                        ImagePlus imp = getCurrentImage();

                                        if (imp == null) {
                                            beep();
                                            showStatus("No image");
                                        }
                                         CustomCommands = new IP_Demo();
                                    }
                                });
                                EditMnu.add(MultiresolutionChk);
                                //---- ScalingChk ----
                                ScalingChk.setText("Scaling");
                                ScalingChk.setName("ScalingChk");
                                ScalingChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        //float[] ScalePyramidKernel = {0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f};
                                             CustomCommands = new IP_Demo();
                                            //ScalingChkActionPerformed(ScalePyramidKernel);

                                    }
                                });
                                EditMnu.add(ScalingChk);
                               
                                    
                                //---- Brovey Transform----
                                BroveyChk.setText("Brovey");
                                BroveyChk.setName("BroveyChk");
                                BroveyChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        BroveyChkActionPerformed(e);
                                    }

                                    void BroveyChkActionPerformed(ActionEvent e) {
                                        scaleInstance();
                                    }

                                    public int scaleInstance() {
                                        try {
                                            int myWidth, myHeight;

                                            myWidth = images[0].getWidth((ImageObserver)images[0]);
                                            myHeight = images[0].getHeight((ImageObserver)images[0]);

                                            convolvedImage = new BufferedImage(myWidth, myHeight, BufferedImage.TYPE_INT_ARGB);
                                            
                                            Graphics2D big = convolvedImage.createGraphics();
                                            
                                            //AffineTransform affineTransform = new AffineTransform();
                                            AffineTransform affineTransform = AffineTransform.getScaleInstance(2.0, 2.0);
                                            
                                            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BICUBIC);
                                            
                                            affineTransform.scale(0.95,0.95);
                                            
                                            big.drawImage(images[0], affineTransform, (ImageObserver)convolvedImage);
                                            
                                            //paintComponent(big, 2);

                                        } catch (Exception cause) {
                                            StackTraceElement elements[] = cause.getStackTrace();
                                            for (int i = 0, n = elements.length; i < n; i++) {
                                                System.err.println(elements[i].getFileName() + ":" + elements[i].getLineNumber() + ">>" + elements[i].getMethodName() + "()");
                                            }
                                        }
                                        return (0);
                                    }
                                });
                                EditMnu.add(BroveyChk);
                            }
                            menuBar1.add(EditMnu);

                            //======== FISMnu ========
                            {
                                FISMnu.setText("FIS");
                                FISMnu.setName("FISMnu");

                                //---- FuzzyCMeansChk ----
                                FuzzyCMeansChk.setText("Fuzzy C Means Clustering");
                                FuzzyCMeansChk.setName("FuzzyCMeansChk");
                                FuzzyCMeansChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FuzzyCMeansChkActionPerformed(e);
                                    }

                                    void FuzzyCMeansChkActionPerformed(ActionEvent e) {
                                // TODO add your code here
                                    }
                                });
                                FISMnu.add(FuzzyCMeansChk);

                                //---- FuzzyKMeansChk ----
                                FuzzyKMeansChk.setText("Fuzzy K Means Clustering");
                                FuzzyKMeansChk.setName("FuzzyKMeansChk");
                                FuzzyKMeansChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FuzzyKMeansChkActionPerformed(e);
                                    }

                                    void FuzzyKMeansChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                FISMnu.add(FuzzyKMeansChk);

                                //---- FuzzySCMChk ----
                                FuzzySCMChk.setText("Fuzzy SCM Operator");
                                FuzzySCMChk.setName("FuzzySCMChk");
                                FuzzySCMChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FuzzySCMChkActionPerformed(e);
                                    }

                                    void FuzzySCMChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                FISMnu.add(FuzzySCMChk);

                                //---- IsodataChk ----
                                IsodataChk.setText("Isodata");
                                IsodataChk.setName("IsodataChk");
                                IsodataChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        IsodataChkActionPerformed(e);
                                    }

                                    void IsodataChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                FISMnu.add(IsodataChk);

                                //---- MaxmLikeChk ----
                                MaxmLikeChk.setText("Maximum Likelihood");
                                MaxmLikeChk.setName("MaxmLikeChk");
                                MaxmLikeChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MaxmLikeChkActionPerformed(e);
                                    }

                                    void MaxmLikeChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                FISMnu.add(MaxmLikeChk);
                            }
                            menuBar1.add(FISMnu);
                            //======== MachineMnu ========
                            {
                                MachineMnu.setText("Machine Classification");
                                MachineMnu.setName("MachineMnu");

                                //---- FuzzyIntChk ----
                                FuzzyIntChk.setText("Fuzzy Interface");
                                FuzzyIntChk.setName("FuzzyIntChk");
                                FuzzyIntChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FuzzyIntChkActionPerformed(e);
                                    }

                                    void FuzzyIntChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MachineMnu.add(FuzzyIntChk);

                                //---- SpatialClusteringChk ----
                                SpatialClusteringChk.setText("Spatial Clustering");
                                SpatialClusteringChk.setName("SpatialClusteringChk");
                                SpatialClusteringChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SpatialClusteringChkActionPerformed(e);
                                    }

                                    void SpatialClusteringChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MachineMnu.add(SpatialClusteringChk);

                                //---- SegmentationChk ----
                                SegmentationChk.setText("Segmentation");
                                SegmentationChk.setName("SegmentationChk");
                                SegmentationChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SegmentationChkActionPerformed(e);
                                    }

                                    void SegmentationChkActionPerformed(ActionEvent e) {
                                        //FISImageJ = new ImageJ();
                                        //try {
                                        //    Process pr = Runtime.getRuntime().exec(imagejapp);
                                        //} catch (IOException ex) {
                                        //    getLogger(FISPrototypeUI.class.getName()).log(Level.SEVERE, null, ex);
                                        //}
                                        // RegionContourLabel = new FISPrototype.DemoRegionsAndContours();
                                    }
                                });
                                MachineMnu.add(SegmentationChk);

                                //---- ClassificationChk ----
                                ClassificationChk.setText("Classification");
                                ClassificationChk.setName("ClassificationChk");
                                ClassificationChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        ClassificationChkActionPerformed(e);
                                    }

                                    void ClassificationChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MachineMnu.add(ClassificationChk);
                            }
                            menuBar1.add(MachineMnu);
                            //======== MergeMnu ========
                            {
                                MergeMnu.setText("Merging");
                                MergeMnu.setName("MergeMnu");

                                //---- RegionGrowChk ----
                                RegionGrowChk.setText("Region Growing");
                                RegionGrowChk.setName("RegionGrowChk");
                                RegionGrowChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        RegionGrowChkActionPerformed(e);
                                    }

                                    void RegionGrowChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MergeMnu.add(RegionGrowChk);

                                //---- RegionMergeChk ----
                                RegionMergeChk.setText("Region Merging");
                                RegionMergeChk.setName("RegionMergeChk");
                                RegionMergeChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        RegionMergeChkActionPerformed(e);
                                    }

                                    void RegionMergeChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MergeMnu.add(RegionMergeChk);

                                //---- FNEA_SinglePixel ----
                                FNEA_SinglePixelChk.setText("FNEA-Pixel");
                                FNEA_SinglePixelChk.setName("FNEASinglePixelChk");
                                FNEA_SinglePixelChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FNEA_SinglePixelChkActionPerformed(e);
                                    }

                                    void FNEA_SinglePixelChkActionPerformed(ActionEvent e) {
                                        // TODO add your code here
                                    }
                                });
                                MergeMnu.add(FNEA_SinglePixelChk);
                                
                                //---- ErosionChk ----
                                ErosionChk.setText("Morphology - Erosion");
                                ErosionChk.setName("ErosionChk");
                                ErosionChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        ErosionChkActionPerformed(e);
                                    }

                                    void ErosionChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MergeMnu.add(ErosionChk);

                                //---- DilationChk ----
                                DilationChk.setText("Morphology - Dilation");
                                DilationChk.setName("DilationChk");
                                DilationChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        DilationChkActionPerformed(e);
                                    }

                                    void DilationChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                MergeMnu.add(DilationChk);
                            }
                            menuBar1.add(MergeMnu);
                            //======== DisplayMnu ========
                            {
                                DisplayMnu.setText("Display");
                                DisplayMnu.setName("DisplayMnu");

                                //---- TrueColorChk ----
                                TrueColorChk.setText("True Color");
                                TrueColorChk.setName("TrueColorChk");
                                TrueColorChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        TrueColorChkActionPerformed(e);
                                    }

                                    void TrueColorChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                DisplayMnu.add(TrueColorChk);

                                //---- FalseColorChk ----
                                FalseColorChk.setText("False Color");
                                FalseColorChk.setName("FalseColorChk");
                                FalseColorChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        FalseColorChkActionPerformed(e);
                                    }

                                    void FalseColorChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                DisplayMnu.add(FalseColorChk);

                                //---- PanchromaticChk ----
                                PanchromaticChk.setText("Panchromatic");
                                PanchromaticChk.setName("PanchromaticChk");
                                PanchromaticChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        PanchromaticChkActionPerformed(e);
                                    }

                                    void PanchromaticChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                DisplayMnu.add(PanchromaticChk);

                                //---- HSVChk ----
                                HSIChk.setText("HSI");
                                HSIChk.setName("HSIChk");
                                HSIChk.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        HSIChkActionPerformed(e);
                                    }

                                    void HSIChkActionPerformed(ActionEvent e) {
                                    // TODO add your code here
                                    }
                                });
                                DisplayMnu.add(HSIChk);
                            }
                            menuBar1.add(DisplayMnu);
                            //======== ReportsMnu ========
                            {
                                ReportsMnu.setText("Reports");
                                ReportsMnu.setName("ReportsMnu");
                            }
                            menuBar1.add(ReportsMnu);
                        }
                        FISPrototypeWindow.setJMenuBar(menuBar1);
                        AuthenticationFrameContentPane.isVisible();
                        //ImageFISFrameContentPane.isVisible();
                        
                        FISPrototypeWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        FISPrototypeWindow.setFont(new Font("Franklin Gothic Book", Font.BOLD, 14));
                      
                        FISPrototypeWindow.setTitle("Climate Change Analysis And Prediction System");
                        FISPrototypeWindow.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                       
                        FISPrototypeWindow.setName("FISPrototypeWindow");
                        
                        FISPrototypeWindow.setContentPane(FISPrototypeWindowContentPane);
                        FISPrototypeWindow.setSize(500,100);
                        FISPrototypeWindow.setLocation(0, 0);
                        
                        }
                }
                
                public BufferedImage[] images;
                public BufferedImage convolvedImage=null;
                public Image scaleImg=null;
          }
        });
    }
    
    private static final Logger LOG = Logger.getLogger(FISPrototypeUI.class.getName());
    
}